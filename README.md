# Controle de Gastos Pessoais

**Aluno:** Saullo Paulo Dantas Felipe
**Matrícula:** _(preencher)_
**Data de entrega:** 09/10/2026

## Justificativa da escolha do tema

Escolhi o tema "Controle de Gastos Pessoais" porque é um problema real e do dia a dia, que se encaixa bem nos conceitos exigidos: um cadastro (CRUD) com dados estruturados (descrição, valor, categoria e data), que precisam ser persistidos localmente, listados e consultados em detalhe. Também permite exercitar cálculos simples (total gasto) e validação de formulário, mantendo o app simples, mas completo.

## Descrição do funcionamento

O app possui 3 telas, ligadas por Navigation Compose:

1. **Lista de gastos** – mostra o total gasto e a lista de gastos cadastrados (descrição, categoria e valor). Um botão **+** abre o cadastro; tocar em um item abre os detalhes.
2. **Cadastro/Edição** – campos de descrição, valor e categoria, com validação (campos obrigatórios e valor maior que zero). O mesmo formulário é usado para criar um novo gasto ou editar um existente.
3. **Detalhes** – exibe todas as informações do gasto, incluindo a data de registro, com botões para **editar** e **excluir**.

Os dados ficam salvos localmente com **Room** e permanecem após fechar o app.

## Arquitetura (MVVM)

```
com.saullo.controlegastos
├── data/            # Camada de dados
│   ├── Gasto.kt            (Entity)
│   ├── GastoDao.kt         (DAO com Flow)
│   ├── AppDatabase.kt      (Room Database)
│   └── GastoRepository.kt  (Repository)
├── viewmodel/       # Camada de lógica
│   └── GastoViewModel.kt   (ViewModel + Factory)
├── ui/              # Camada de interface (Jetpack Compose)
│   ├── navigation/AppNavigation.kt
│   └── screens/
│       ├── ListaGastosScreen.kt
│       ├── CadastroGastoScreen.kt
│       └── DetalhesGastoScreen.kt
├── ControleGastosApp.kt  # Application (cria banco e repositório)
└── MainActivity.kt
```

- **View (Compose):** apenas exibe o estado e dispara ações.
- **ViewModel:** expõe `StateFlow` e executa as operações em corrotinas.
- **Repository + Room:** única fonte de dados do app.

## Tecnologias

Kotlin · Jetpack Compose (Material 3) · Navigation Compose · Room (KSP) · ViewModel · Coroutines/Flow

## Como executar

1. Abra o projeto no Android Studio.
2. Aguarde o Gradle sincronizar.
3. Execute em um emulador ou dispositivo (minSdk 24).

## Observações

Projeto desenvolvido como entrega final do módulo de desenvolvimento Android.
