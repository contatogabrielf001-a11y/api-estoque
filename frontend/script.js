
const API_URL = "http://localhost:8080/produtos";
const API_MOVIMENTACOES_URL = "http://localhost:8080/movimentacoes";

const botaoAbrir = document.getElementById("abrirFormulario");
const formulario = document.getElementById("formularioProduto");
const botaoFecharFormulario = document.getElementById("fecharFormulario");

const nomeProduto = document.getElementById("nomeProduto");
const precoProduto = document.getElementById("precoProduto");
const quantidadeProduto = document.getElementById("quantidadeProduto");
const botaoSalvar = document.getElementById("salvarProduto");

const tabela = document.querySelector(".tabela-produtos tbody");
const campoPesquisa = document.getElementById("pesquisarProduto");
const filtroStatus = document.getElementById("filtroStatus");

const formularioEdicao = document.getElementById("formularioEdicao");
const editarNomeProduto = document.getElementById("editarNomeProduto");
const editarPrecoProduto = document.getElementById("editarPrecoProduto");
const editarQuantidadeProduto = document.getElementById("editarQuantidadeProduto");
const botaoSalvarEdicao = document.getElementById("salvarEdicao");
const botaoCancelarEdicao = document.getElementById("cancelarEdicao");

const filtroTipoHistorico = document.getElementById("filtroTipoHistorico");
const dataInicial = document.getElementById("dataInicial");
const dataFinal = document.getElementById("dataFinal");
const botaoLimparFiltroData = document.getElementById("limparFiltroData");
const botaoLimparHistorico = document.getElementById("limparHistorico");
const botaoExportarCSV = document.getElementById("exportarCSV");

let produtos = [];
let historico = [];
let indiceEmEdicao = null;
let graficoMovimentacoes = null;

// ==========================================
// FUNÇÕES AUXILIARES
// ==========================================

function formatarMoeda(valor) {
    return valor.toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });
}

function normalizarTipo(tipo) {
    return String(tipo || "")
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "");
}

async function obterMensagemErro(resposta, mensagemPadrao) {
    const texto = await resposta.text();

    if (!texto) {
        return mensagemPadrao;
    }

    try {
        const dados = JSON.parse(texto);
        return dados.erro || mensagemPadrao;
    } catch {
        return texto;
    }
}

// ==========================================
// CARREGAR PRODUTOS DA API
// ==========================================

async function carregarProdutosAPI() {
    try {
        const resposta = await fetch(API_URL);

        if (!resposta.ok) {
            throw new Error("Erro ao consultar produtos: " + resposta.status);
        }

        produtos = await resposta.json();

        mostrarProdutos();
        atualizarResumo();
        atualizarRelatorioEstoque();

        return true;

    } catch (erro) {
        console.error("Erro ao carregar produtos:", erro);
        alert("Não foi possível carregar os produtos da API.");

        return false;
    }
}

// ==========================================
// CARREGAR HISTÓRICO DA API
// ==========================================

async function carregarHistoricoAPI() {
    try {
        const resposta = await fetch(API_MOVIMENTACOES_URL);

        if (!resposta.ok) {
            throw new Error("Erro ao consultar histórico: " + resposta.status);
        }

        historico = await resposta.json();

        mostrarHistorico();
        atualizarDashboard();

        return true;

    } catch (erro) {
        console.error("Erro ao carregar histórico:", erro);
        alert("Não foi possível carregar o histórico de movimentações.");

        return false;
    }
}

// ==========================================
// STATUS DOS PRODUTOS
// ==========================================

function obterStatus(produto) {
    if (produto.quantidade === 0) {
        return {
            texto: "Sem estoque",
            classe: "esgotado",
            valor: "esgotado"
        };
    }

    if (produto.quantidade < 5) {
        return {
            texto: "Estoque baixo",
            classe: "baixo",
            valor: "baixo"
        };
    }

    return {
        texto: "Disponível",
        classe: "disponivel",
        valor: "disponivel"
    };
}

// ==========================================
// RESUMO DO ESTOQUE
// ==========================================

function atualizarResumo() {
    const totalProdutos = document.getElementById("totalProdutos");
    const estoqueBaixo = document.getElementById("estoqueBaixo");
    const semEstoque = document.getElementById("semEstoque");
    const valorEstoque = document.getElementById("valorEstoque");

    if (!totalProdutos || !estoqueBaixo || !semEstoque || !valorEstoque) {
        return;
    }

    totalProdutos.textContent = produtos.length;

    estoqueBaixo.textContent = produtos.filter(function (produto) {
        return produto.quantidade > 0 && produto.quantidade < 5;
    }).length;

    semEstoque.textContent = produtos.filter(function (produto) {
        return produto.quantidade === 0;
    }).length;

    const valorTotal = produtos.reduce(function (total, produto) {
        return total + produto.preco * produto.quantidade;
    }, 0);

    valorEstoque.textContent = formatarMoeda(valorTotal);
}

// ==========================================
// RELATÓRIO DE ESTOQUE
// ==========================================

function atualizarRelatorioEstoque() {
    const tabelaRelatorio = document.getElementById("tabelaRelatorioEstoque");
    const valorTotalRelatorio = document.getElementById("valorTotalRelatorio");

    if (!tabelaRelatorio || !valorTotalRelatorio) {
        return;
    }

    tabelaRelatorio.innerHTML = "";

    let valorTotalEstoque = 0;

    produtos.forEach(function (produto) {
        const valorProduto = produto.preco * produto.quantidade;
        const status = obterStatus(produto);

        valorTotalEstoque += valorProduto;

        const linha = document.createElement("tr");

        const colunaNome = document.createElement("td");
        colunaNome.textContent = produto.nome;

        const colunaPreco = document.createElement("td");
        colunaPreco.textContent = formatarMoeda(produto.preco);

        const colunaQuantidade = document.createElement("td");
        colunaQuantidade.textContent = produto.quantidade;

        const colunaValorTotal = document.createElement("td");
        colunaValorTotal.textContent = formatarMoeda(valorProduto);

        const colunaSituacao = document.createElement("td");
        colunaSituacao.textContent = status.texto;
        colunaSituacao.classList.add(status.classe);

        linha.appendChild(colunaNome);
        linha.appendChild(colunaPreco);
        linha.appendChild(colunaQuantidade);
        linha.appendChild(colunaValorTotal);
        linha.appendChild(colunaSituacao);

        tabelaRelatorio.appendChild(linha);
    });

    valorTotalRelatorio.textContent = formatarMoeda(valorTotalEstoque);
}

// ==========================================
// MOSTRAR PRODUTOS
// ==========================================

function mostrarProdutos() {
    if (!tabela) {
        return;
    }

    tabela.innerHTML = "";

    const pesquisa = campoPesquisa
        ? campoPesquisa.value.toLowerCase().trim()
        : "";

    const statusSelecionado = filtroStatus
        ? filtroStatus.value
        : "todos";

    const produtosFiltrados = produtos
        .map(function (produto, indice) {
            return {
                produto: produto,
                indice: indice
            };
        })
        .filter(function (item) {
            const status = obterStatus(item.produto);

            const correspondePesquisa = item.produto.nome
                .toLowerCase()
                .includes(pesquisa);

            const correspondeStatus =
                statusSelecionado === "todos" ||
                status.valor === statusSelecionado;

            return correspondePesquisa && correspondeStatus;
        });

    produtosFiltrados.forEach(function (item) {
        const produto = item.produto;
        const indice = item.indice;
        const status = obterStatus(produto);

        const linha = document.createElement("tr");

        const colunaNome = document.createElement("td");
        colunaNome.textContent = produto.nome;

        const colunaPreco = document.createElement("td");
        colunaPreco.textContent = formatarMoeda(produto.preco);

        const colunaQuantidade = document.createElement("td");
        colunaQuantidade.textContent = produto.quantidade;

        const colunaStatus = document.createElement("td");
        const etiquetaStatus = document.createElement("span");

        etiquetaStatus.textContent = status.texto;
        etiquetaStatus.classList.add(status.classe);

        colunaStatus.appendChild(etiquetaStatus);

        const colunaAcoes = document.createElement("td");

        const botaoEntrada = document.createElement("button");
        botaoEntrada.textContent = "Entrada";
        botaoEntrada.classList.add("botao-entrada");

        botaoEntrada.addEventListener("click", function () {
            registrarMovimentacao(indice, "entrada");
        });

        const botaoSaida = document.createElement("button");
        botaoSaida.textContent = "Saída";
        botaoSaida.classList.add("botao-saida");

        botaoSaida.addEventListener("click", function () {
            registrarMovimentacao(indice, "saida");
        });

        const botaoEditar = document.createElement("button");
        botaoEditar.textContent = "Editar";
        botaoEditar.classList.add("botao-editar");

        botaoEditar.addEventListener("click", function () {
            abrirEdicao(indice);
        });

        const botaoExcluir = document.createElement("button");
        botaoExcluir.textContent = "Excluir";
        botaoExcluir.classList.add("botao-excluir");

        botaoExcluir.addEventListener("click", function () {
            excluirProduto(indice);
        });

        colunaAcoes.appendChild(botaoEntrada);
        colunaAcoes.appendChild(botaoSaida);
        colunaAcoes.appendChild(botaoEditar);
        colunaAcoes.appendChild(botaoExcluir);

        linha.appendChild(colunaNome);
        linha.appendChild(colunaPreco);
        linha.appendChild(colunaQuantidade);
        linha.appendChild(colunaStatus);
        linha.appendChild(colunaAcoes);

        tabela.appendChild(linha);
    });

    if (produtosFiltrados.length === 0) {
        const linhaVazia = document.createElement("tr");
        const celula = document.createElement("td");

        celula.colSpan = 5;
        celula.textContent = "Nenhum produto encontrado.";
        celula.style.textAlign = "center";

        linhaVazia.appendChild(celula);
        tabela.appendChild(linhaVazia);
    }
}

// ==========================================
// EDIÇÃO DE PRODUTOS
// ==========================================

function abrirEdicao(indice) {
    const produto = produtos[indice];

    if (!produto || !formularioEdicao) {
        return;
    }

    indiceEmEdicao = indice;

    editarNomeProduto.value = produto.nome;
    editarPrecoProduto.value = produto.preco;
    editarQuantidadeProduto.value = produto.quantidade;

    if (formulario) {
        formulario.hidden = true;
    }

    formularioEdicao.hidden = false;

    formularioEdicao.scrollIntoView({
        behavior: "smooth",
        block: "start"
    });
}

function fecharEdicao() {
    if (formularioEdicao) {
        formularioEdicao.hidden = true;
    }

    indiceEmEdicao = null;
}

if (botaoSalvarEdicao) {
    botaoSalvarEdicao.addEventListener("click", async function () {
        if (indiceEmEdicao === null) {
            return;
        }

        const produtoAtual = produtos[indiceEmEdicao];

        if (!produtoAtual) {
            fecharEdicao();
            return;
        }

        const nome = editarNomeProduto.value.trim();
        const precoTexto = editarPrecoProduto.value;
        const preco = Number(precoTexto);
        const quantidadeTexto = editarQuantidadeProduto.value;
        const quantidade = Number(quantidadeTexto);

        if (
            nome === "" ||
            precoTexto === "" ||
            !Number.isFinite(preco) ||
            preco <= 0 ||
            quantidadeTexto === "" ||
            !Number.isInteger(quantidade) ||
            quantidade < 0
        ) {
            alert("Preencha os campos corretamente!");
            return;
        }

        const produtoAtualizado = {
            id: produtoAtual.id,
            nome: nome,
            preco: preco,
            quantidade: quantidade
        };

        try {
            const resposta = await fetch(API_URL, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(produtoAtualizado)
            });

            if (!resposta.ok) {
                throw new Error(
                    await obterMensagemErro(
                        resposta,
                        "Não foi possível atualizar o produto."
                    )
                );
            }

            fecharEdicao();

            const carregou = await carregarProdutosAPI();

            if (carregou) {
                alert("Produto atualizado com sucesso!");
            }

        } catch (erro) {
            console.error("Erro ao atualizar produto:", erro);
            alert(erro.message || "Não foi possível atualizar o produto.");
        }
    });
}

if (botaoCancelarEdicao) {
    botaoCancelarEdicao.addEventListener("click", fecharEdicao);
}

// ==========================================
// EXCLUIR PRODUTOS
// ==========================================

async function excluirProduto(indice) {
    const produto = produtos[indice];

    if (!produto) {
        return;
    }

    const confirmar = confirm(
        `Deseja excluir o produto "${produto.nome}"?`
    );

    if (!confirmar) {
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/${produto.id}`, {
            method: "DELETE"
        });

        if (!resposta.ok) {
            throw new Error(
                await obterMensagemErro(
                    resposta,
                    "Não foi possível excluir o produto."
                )
            );
        }

        const carregou = await carregarProdutosAPI();

        if (carregou) {
            alert("Produto excluído com sucesso!");
        }

    } catch (erro) {
        console.error("Erro ao excluir produto:", erro);
        alert(erro.message || "Não foi possível excluir o produto.");
    }
}

// ==========================================
// PESQUISA E FILTROS DE PRODUTOS
// ==========================================

if (campoPesquisa) {
    campoPesquisa.addEventListener("input", mostrarProdutos);
}

if (filtroStatus) {
    filtroStatus.addEventListener("change", mostrarProdutos);
}

// ==========================================
// ABRIR E FECHAR CADASTRO
// ==========================================

if (botaoAbrir && formulario) {
    botaoAbrir.addEventListener("click", function () {
        fecharEdicao();
        formulario.hidden = false;
    });
}

if (botaoFecharFormulario && formulario) {
    botaoFecharFormulario.addEventListener("click", function () {
        formulario.hidden = true;
    });
}

// ==========================================
// CADASTRAR PRODUTOS
// ==========================================

if (botaoSalvar) {
    botaoSalvar.addEventListener("click", async function () {
        const nome = nomeProduto.value.trim();
        const precoTexto = precoProduto.value;
        const preco = Number(precoTexto);
        const quantidadeTexto = quantidadeProduto.value;
        const quantidade = Number(quantidadeTexto);

        if (
            nome === "" ||
            precoTexto === "" ||
            !Number.isFinite(preco) ||
            preco <= 0 ||
            quantidadeTexto === "" ||
            !Number.isInteger(quantidade) ||
            quantidade < 0
        ) {
            alert("Preencha os campos corretamente!");
            return;
        }

        const novoProduto = {
            nome: nome,
            preco: preco,
            quantidade: quantidade
        };

        try {
            const resposta = await fetch(API_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(novoProduto)
            });

            if (!resposta.ok) {
                throw new Error(
                    await obterMensagemErro(
                        resposta,
                        "Não foi possível cadastrar o produto."
                    )
                );
            }

            nomeProduto.value = "";
            precoProduto.value = "";
            quantidadeProduto.value = "";

            formulario.hidden = true;

            const carregou = await carregarProdutosAPI();

            if (carregou) {
                alert("Produto cadastrado com sucesso!");
            }

        } catch (erro) {
            console.error("Erro ao cadastrar produto:", erro);
            alert(erro.message || "Não foi possível cadastrar o produto.");
        }
    });
}

// ==========================================
// CONVERTER DATA DO HISTÓRICO
// ==========================================

function converterDataHistorico(dataTexto) {
    if (!dataTexto) {
        return null;
    }

    const partes = dataTexto.includes(",")
        ? dataTexto.split(",")
        : dataTexto.trim().split(/\s+/);

    const data = partes[0].trim().split("/");
    const horario = partes[1] ? partes[1].trim() : "00:00:00";

    if (data.length !== 3) {
        return null;
    }

    const dia = Number(data[0]);
    const mes = Number(data[1]);
    const ano = Number(data[2]);

    const [hora, minuto, segundo] = horario.split(":").map(Number);

    const resultado = new Date(
        ano,
        mes - 1,
        dia,
        hora || 0,
        minuto || 0,
        segundo || 0
    );

    if (Number.isNaN(resultado.getTime())) {
        return null;
    }

    return resultado;
}

// ==========================================
// MOSTRAR E FILTRAR HISTÓRICO
// ==========================================

function mostrarHistorico() {
    const tabelaHistorico = document.getElementById("historicoMovimentacoes");

    if (!tabelaHistorico) {
        return;
    }

    tabelaHistorico.innerHTML = "";

    const tipoSelecionado = filtroTipoHistorico
        ? filtroTipoHistorico.value
        : "todos";

    const historicoFiltrado = historico.filter(function (movimentacao) {
        const tipo = normalizarTipo(movimentacao.tipo);

        const correspondeTipo =
            tipoSelecionado === "todos" ||
            tipo === tipoSelecionado;

        const dataMovimentacao = converterDataHistorico(movimentacao.data);

        let dataMovimentacaoISO = "";

        if (dataMovimentacao) {
            const ano = dataMovimentacao.getFullYear();
            const mes = String(dataMovimentacao.getMonth() + 1).padStart(2, "0");
            const dia = String(dataMovimentacao.getDate()).padStart(2, "0");

            dataMovimentacaoISO = `${ano}-${mes}-${dia}`;
        }

        const correspondeDataInicial =
            !dataInicial ||
            !dataInicial.value ||
            dataMovimentacaoISO >= dataInicial.value;

        const correspondeDataFinal =
            !dataFinal ||
            !dataFinal.value ||
            dataMovimentacaoISO <= dataFinal.value;

        return (
            correspondeTipo &&
            correspondeDataInicial &&
            correspondeDataFinal
        );
    });

    historicoFiltrado.slice().reverse().forEach(function (movimentacao) {
        const linha = document.createElement("tr");

        const colunaProduto = document.createElement("td");
        colunaProduto.textContent = movimentacao.nomeProduto;

        const colunaTipo = document.createElement("td");
        const tipo = normalizarTipo(movimentacao.tipo);

        colunaTipo.textContent = tipo === "entrada" ? "Entrada" : "Saída";

        colunaTipo.classList.add(
            tipo === "entrada"
                ? "historico-entrada"
                : "historico-saida"
        );

        const colunaQuantidade = document.createElement("td");
        colunaQuantidade.textContent = movimentacao.quantidade;

        const colunaData = document.createElement("td");
        colunaData.textContent = movimentacao.data;

        linha.appendChild(colunaProduto);
        linha.appendChild(colunaTipo);
        linha.appendChild(colunaQuantidade);
        linha.appendChild(colunaData);

        tabelaHistorico.appendChild(linha);
    });

    if (historicoFiltrado.length === 0) {
        const linhaVazia = document.createElement("tr");
        const celula = document.createElement("td");

        celula.colSpan = 4;
        celula.textContent = "Nenhuma movimentação encontrada.";
        celula.style.textAlign = "center";

        linhaVazia.appendChild(celula);
        tabelaHistorico.appendChild(linhaVazia);
    }
}

if (filtroTipoHistorico) {
    filtroTipoHistorico.addEventListener("change", mostrarHistorico);
}

if (dataInicial) {
    dataInicial.addEventListener("change", mostrarHistorico);
}

if (dataFinal) {
    dataFinal.addEventListener("change", mostrarHistorico);
}

if (botaoLimparFiltroData) {
    botaoLimparFiltroData.addEventListener("click", function () {
        if (dataInicial) {
            dataInicial.value = "";
        }

        if (dataFinal) {
            dataFinal.value = "";
        }

        mostrarHistorico();
    });
}

// ==========================================
// REGISTRAR ENTRADAS E SAÍDAS
// ==========================================

async function registrarMovimentacao(indice, tipo) {
    const produto = produtos[indice];

    if (!produto) {
        return;
    }

    const operacao = tipo === "entrada" ? "entrada" : "saída";

    const quantidadeTexto = prompt(
        `Produto: ${produto.nome}\n` +
        `Estoque atual: ${produto.quantidade}\n\n` +
        `Informe a quantidade de ${operacao}:`
    );

    if (quantidadeTexto === null) {
        return;
    }

    const quantidade = Number(quantidadeTexto);

    if (
        quantidadeTexto.trim() === "" ||
        !Number.isInteger(quantidade) ||
        quantidade <= 0
    ) {
        alert("Informe uma quantidade inteira maior que zero!");
        return;
    }

    if (tipo !== "entrada" && tipo !== "saida") {
        alert("Tipo de movimentação inválido!");
        return;
    }

    if (tipo === "saida" && quantidade > produto.quantidade) {
        alert("Estoque insuficiente para essa saída!");
        return;
    }

    try {
        const resposta = await fetch(API_MOVIMENTACOES_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                idProduto: produto.id,
                tipo: tipo,
                quantidade: quantidade
            })
        });

        if (!resposta.ok) {
            throw new Error(
                await obterMensagemErro(
                    resposta,
                    "Erro ao registrar movimentação."
                )
            );
        }

        await carregarProdutosAPI();
        await carregarHistoricoAPI();

        const produtoAtualizado = produtos.find(function (item) {
            return item.id === produto.id;
        });

        const estoqueAtualizado = produtoAtualizado
            ? produtoAtualizado.quantidade
            : null;

        alert(
            `Movimentação de ${operacao} realizada com sucesso!\n` +
            (
                estoqueAtualizado !== null
                    ? `Estoque atual: ${estoqueAtualizado}`
                    : "Atualize a página para consultar o estoque."
            )
        );

    } catch (erro) {
        console.error("Erro na movimentação:", erro);

        alert(
            erro.message || "Não foi possível registrar a movimentação."
        );
    }
}

// ==========================================
// LIMPAR HISTÓRICO
// ==========================================

if (botaoLimparHistorico) {
    botaoLimparHistorico.addEventListener("click", async function () {
        if (historico.length === 0) {
            alert("Não existem movimentações para limpar!");
            return;
        }

        const confirmar = confirm(
            "Tem certeza que deseja apagar todo o histórico de movimentações?"
        );

        if (!confirmar) {
            return;
        }

        try {
            const resposta = await fetch(API_MOVIMENTACOES_URL, {
                method: "DELETE"
            });

            if (!resposta.ok) {
                throw new Error(
                    await obterMensagemErro(
                        resposta,
                        "Erro ao excluir histórico."
                    )
                );
            }

            const carregou = await carregarHistoricoAPI();

            if (carregou) {
                alert("Histórico apagado com sucesso!");
            }

        } catch (erro) {
            console.error("Erro ao limpar histórico:", erro);

            alert(
                erro.message ||
                "Não foi possível apagar o histórico no banco de dados."
            );
        }
    });
}

// ==========================================
// DASHBOARD DE MOVIMENTAÇÕES
// ==========================================

function atualizarDashboard() {
    const elementoEntradas = document.getElementById("totalEntradas");
    const elementoSaidas = document.getElementById("totalSaidas");

    const totalEntradas = historico
        .filter(function (movimentacao) {
            return normalizarTipo(movimentacao.tipo) === "entrada";
        })
        .reduce(function (total, movimentacao) {
            return total + movimentacao.quantidade;
        }, 0);

    const totalSaidas = historico
        .filter(function (movimentacao) {
            return normalizarTipo(movimentacao.tipo) === "saida";
        })
        .reduce(function (total, movimentacao) {
            return total + movimentacao.quantidade;
        }, 0);

    if (elementoEntradas) {
        elementoEntradas.textContent = totalEntradas;
    }

    if (elementoSaidas) {
        elementoSaidas.textContent = totalSaidas;
    }

    atualizarGrafico();
}

// ==========================================
// GRÁFICO DE MOVIMENTAÇÕES
// ==========================================

function atualizarGrafico() {
    const elementoGrafico = document.getElementById("graficoMovimentacoes");

    if (!elementoGrafico) {
        return;
    }

    if (typeof Chart === "undefined") {
        console.error("Chart.js não foi carregado.");
        return;
    }

    const totalEntradas = historico
        .filter(function (movimentacao) {
            return normalizarTipo(movimentacao.tipo) === "entrada";
        })
        .reduce(function (total, movimentacao) {
            return total + movimentacao.quantidade;
        }, 0);

    const totalSaidas = historico
        .filter(function (movimentacao) {
            return normalizarTipo(movimentacao.tipo) === "saida";
        })
        .reduce(function (total, movimentacao) {
            return total + movimentacao.quantidade;
        }, 0);

    if (graficoMovimentacoes) {
        graficoMovimentacoes.data.datasets[0].data = [
            totalEntradas,
            totalSaidas
        ];

        graficoMovimentacoes.update();
        return;
    }

    graficoMovimentacoes = new Chart(elementoGrafico, {
        type: "bar",

        data: {
            labels: ["Entradas", "Saídas"],

            datasets: [{
                label: "Quantidade de unidades",
                data: [totalEntradas, totalSaidas],
                backgroundColor: ["#16a36a", "#e5a642"],
                hoverBackgroundColor: ["#35d995", "#f0b44d"],
                borderRadius: 8,
                borderSkipped: false,
                maxBarThickness: 80,
                hoverBorderWidth: 2
            }]
        },

        options: {
            responsive: true,
            maintainAspectRatio: false,

            animation: {
                duration: 700,
                easing: "easeOutQuart"
            },

            plugins: {
                tooltip: {
                    backgroundColor: "#141414",
                    titleColor: "#35d995",
                    bodyColor: "#f5f5f5",
                    borderColor: "#16a36a",
                    borderWidth: 1,
                    padding: 12,
                    displayColors: true
                },

                legend: {
                    labels: {
                        color: "#f5f5f5",
                        font: {
                            size: 12,
                            weight: "600"
                        }
                    }
                }
            },

            scales: {
                x: {
                    ticks: {
                        color: "#a1a1aa",
                        font: {
                            size: 12,
                            weight: "600"
                        }
                    },

                    grid: {
                        display: false
                    }
                },

                y: {
                    beginAtZero: true,

                    ticks: {
                        color: "#a1a1aa",
                        precision: 0,
                        padding: 10,
                        font: {
                            size: 11,
                            weight: "500"
                        }
                    },

                    grid: {
                        color: "rgba(255,255,255,0.08)"
                    }
                }
            }
        }
    });
}

// ==========================================
// EXPORTAR RELATÓRIO CSV
// ==========================================

function escaparCampoCSV(valor) {
    const texto = String(valor ?? "");
    return `"${texto.replace(/"/g, '""')}"`;
}

function exportarRelatorioCSV() {
    const cabecalho = [
        "Produto",
        "Preço unitário",
        "Quantidade",
        "Valor total",
        "Situação"
    ];

    const linhas = [cabecalho];

    produtos.forEach(function (produto) {
        const valorTotal = produto.preco * produto.quantidade;
        const status = obterStatus(produto);

        linhas.push([
            produto.nome,
            produto.preco.toFixed(2).replace(".", ","),
            produto.quantidade,
            valorTotal.toFixed(2).replace(".", ","),
            status.texto
        ]);
    });

    const csv = linhas
        .map(function (linha) {
            return linha.map(escaparCampoCSV).join(";");
        })
        .join("\n");

    const arquivo = new Blob(["\uFEFF" + csv], {
        type: "text/csv;charset=utf-8;"
    });

    const url = URL.createObjectURL(arquivo);
    const link = document.createElement("a");

    link.href = url;
    link.download = "relatorio-estoque.csv";

    document.body.appendChild(link);
    link.click();
    link.remove();

    setTimeout(function () {
        URL.revokeObjectURL(url);
    }, 1000);
}

if (botaoExportarCSV) {
    botaoExportarCSV.addEventListener("click", exportarRelatorioCSV);
}

// ==========================================
// INICIALIZAR SISTEMA
// ==========================================

carregarProdutosAPI();
carregarHistoricoAPI();
