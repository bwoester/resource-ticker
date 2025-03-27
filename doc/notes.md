- Resource **Production** depends on
  - Resource *stock*
  - Workforce (population)
    - TODO is it another *resource*?
  - Available energy (another *resource*)
  - Game modifiers
  - Player modifiers
  - Planet modifiers
    - Sol modifiers
    - Gal modifiers
  - Available buildings

```mermaid
sequenceDiagram
    GameLoop->>Production: getProduction(tick)
    Production->>Stock: getStock(tick)
    Stock->>Production: StockMsg
    Production->>Modifiers: getModifiers(tick)
    Modifiers->>Production: ModifiersMsg
    Production->>Buildings: getBuildings(tick)
    Buildings->>Production: BuildingsMsg
    Production->>Production: Logic
    Production->>GameLoop: ProductionMsg

```

- Construction **Progress** depends on
  - Item specific construction time
  - Delayed by
    - Energy shortage
    - Workforce motivation
  - Modifiers

```mermaid
sequenceDiagram
    actor Alice
    Alice->>Buildings: getBuildings(tick)
    Buildings->>Construction: getProgress(tick)
    Construction->>Buildings: ProgressMsg
    Buildings->>Teardown: getProgress(tick)
    Teardown->>Buildings: ProgressMsg
    Buildings->>Alice: BuildingsMsg

```
