# correios-api

API de fácil instalação para buscar qualquer cep Brasileiro<br>

## Conceito
Basicamente, ao subir o container, ela baixa esse [csv](https://github.com/miltonhit/miltonhit/raw/main/public-assets/cep-20190602.csv) com 900k de endereços e salva na tabela 'correio.address' dentro do MySQL.<br>
Depois do setup é possível pesquisar de forma fácil, via API REST, qualquer cep Brasileiro.

## Stack
-> Java 11+<br>
-> MySQL<br>
-> Spring Family<br>
-> Docker Compose<br>
