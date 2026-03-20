# Lista de Compras Inteligente
Um aplicativo **Android Nativo** robusto e prático, desenvolvido em *Kotlin*. A proposta deste projeto é facilitar o planejamento de idas ao mercado, permitindo um controle rigoroso sobre os itens selecionados e o orçamento total gasto.
## Funcionalidades

- Adição dinâmica de produtos com nome e valor.
- Cálculo automático do somatório total dos itens.
- Sistema de "check" para marcar itens já adicionados ao carrinho físico.
- Interface otimizada para uso com apenas uma mão.

## Status de Desenvolvimento
- [x] Layout responsivo com Material Design
- [x] Lógica de cálculo em tempo real
- [x] Listagem performática com RecyclerView e Adapters
- [x] Tratamento de entradas numéricas
- [ ] Integração com banco de dados SQLite para histórico
## Tecnologias Utilizadas
| Tecnologia | Aplicação |
| --- | --- |
| Kotlin | Linguagem de programação principal |
| Android SDK | Ferramentas de desenvolvimento nativo |
| RecyclerView | Exibição eficiente de grandes listas |
| XML | Definição de interface e estilos |
> [!TIP]
> O uso de View Binding neste projeto garante um acesso mais seguro e performático aos componentes de interface, evitando erros comuns de runtime.
## Como rodar o projeto
Clone o repositório e abra-o no **Android Studio**:
`git clone https://github.com/RicardoFiorini/Listade-Compras-Inteligente-App-Android-Nativo.git`
Para instalar no dispositivo via terminal:
```bash

./gradlew installDebug

```
