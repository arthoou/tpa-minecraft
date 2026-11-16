<div align="center">

<img src="src/main/resources/logo.png" alt="Logo de Arthou’s TPA" width="120">

<h1>Arthou’s TPA</h1>
<p>Pedidos de teleporte com confirmação, casas, warps e destinos de servidor.</p>
<p><strong>Minecraft 1.20.1 · Forge · Java 17</strong></p>

</div>

[Instalação](#instalação) · [Uso](#uso) · [Como funciona](#como-funciona) · [Créditos](#créditos)

## Instalação

Coloque o JAR `build/libs/arthous-tpa-forge-1201-1.0.1.jar` na pasta `mods/` do servidor. Quem joga pode entrar com o cliente padrão.

| Minecraft | Loader | Java | Versão do mod |
| --- | --- | --- | --- |
| 1.20.1 | Forge 47.4.10 | 17 | 1.0.1 |

## Uso

Para ir até outra pessoa, use `/tpa <jogador>`. Para convidá-la a vir até você, use `/tpahere <jogador>` ou `/tphere <jogador>`. A outra pessoa pode clicar na decisão no chat ou digitar `/tpaccept` ou `/tpdeny`. O pedido expira em **30 segundos** e novos pedidos têm intervalo de **10 segundos**.

## Comandos

| Comando | Função |
| --- | --- |
| `/denys`, `/denys add <jogador>`, `/denys remove <jogador>` | Consulta ou altera quem pode enviar pedidos a você. |
| `/sethome <nome>`, `/homes`, `/home [nome]` | Salva, lista e visita casas pessoais. |
| `/setdefaulthome <nome>`, `/delhome <nome>` | Define a casa chamada por `/home` sem argumento e remove casas. |
| `/warp <nome>`, `/spawn` | Visita um ponto público ou o spawn configurado. |
| `/back`, `/rtp` | Volta ao local de retorno registrado ou procura um destino aleatório em um raio de 500 blocos. |
| `/setwarp <nome>`, `/delwarp <nome>`, `/setspawn`, `/delspawn` | Gerencia destinos públicos. Requer permissão de operador nível 2. |
| `/tpall [player <jogador>\|warp <nome>\|spawn]`, `/tplogout <jogador>` | Teleporta jogadores conectados em grupo ou leva o operador ao último local de saída de um jogador. Requer operador nível 2. |

## Como funciona

- Casas, warps, spawn, lista de bloqueios e posições de retorno ou saída são salvos no mundo pelo sistema `SavedData`, no arquivo `data/tpa_state.dat`.
- O **DuCurrency** é opcional. Quando integrado, o mod cobra **100 centavos** do lado que iniciou a solicitação aceita e nos outros comandos de destino que passam pela cobrança do mod. Os comandos administrativos de teleporte em grupo seguem seu próprio fluxo.
- O servidor executa os teleportes e envia os avisos e botões pelo chat do Minecraft.

## Créditos

Implementação e adaptação desta variante por [Arthou](https://github.com/arthoou). A integração com **DuCurrency** permite usar o saldo do servidor para custear os teleportes.

## Compilar

```bash
./gradlew build
```

A saída de distribuição aparece no caminho indicado em **Instalação**.
