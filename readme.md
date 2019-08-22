#Homework:

- Assume that games have multiple participants. Each participant in game can have none or multiple bets.
Such data is delivered in files of json lines like:
```
{"gameData": {"created": "2019-08-01 12:00:01","participants": [{"playerId": "player00001","playerCurrency": "EUR","bets": [{"payout": 0,"stake": 70.2,"code": "Bet_1"},{"payout": 100,"stake": 10,"code": "Bet_2"}]},{"playerId": "player00002","playerCurrency": "USD","bets": [{"payout": 2,"stake": 10,"code": "Bet_1"}]}]},"id": "game_0001"}
```
Write Scala/spark application that reads files containing such data and adds new field 'diff' in 'gameData.participants.bets' as difference between stake and payout ( gameDAta.participants.bets.diff = gameData.participants.stake - gameData.participants.payout).
```$xslt
{"gameData": {"created": "2019-08-01 12:00:01","participants": [{"playerId": "player00001","playerCurrency": "EUR","bets": [{"payout": 0,"stake": 70.2,"code": "Bet_1","diff": 70.2},{"payout": 100,"stake": 10,"code": "Bet_2","diff": -90}]},{"playerId": "player00002","playerCurrency": "USD","bets": [{"payout": 2,"stake": 10,"code": "Bet_1","diff": 8}]}]},"id": "game_0001"}
```

Data should be saved in json files partitioned by 'created' timestamp. Each partition should contain exactly one file.

- Write Scala/spark application that reads json files from output of task 1 and generates csv report on stake, payout and diff per game.