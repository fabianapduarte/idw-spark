<h1 align="center"> 
  🗺 Interpolação espacial (IDW)
</h1>

<p align="center">
  <a href="#-sobre-o-projeto">Sobre</a> •
  <a href="#-como-executar-o-projeto">Como executar</a>
</p>

<br>

## 💻 Sobre o projeto

Projeto desenvolvido na disciplina Programação Concorrente do curso de Tecnologia da Informação (IMD/UFRN) implementando o algortimo de interpolação espacial para previsão de temperatura com o Spark Framework.

A interpolação espacial é o processo de estimativa de valores desconhecidos em determinados pontos do espaço com base em informações conhecidas do ambiente. Os dados utilizados por essa técnica consistem em um conjunto de coordenadas espaciais, cada uma com um ou mais valores associados, como temperatura e precipitação, por exemplo.

Dentre os diversos métodos de interpolação espacial existentes, a aplicação desenvolvida utilizou o método Inverso da Distância à Potência (Inverse Distance Weighted – IDW). Esse algoritmo analisa todos os pontos do ambiente e atribui um peso (ou ponderação) a cada coordenada, de acordo com sua proximidade em relação ao ponto de interesse. Quanto mais próximo for o ponto conhecido, maior será seu peso e maior a influência sobre o cálculo da estimativa do valor do atributo.

---

## 🚀 Como executar o projeto

Para executar o projeto, siga os seguintes passos:

1. É preciso ter o Java JDK 21 instalado na sua máquina.
2. Para executar pelo terminal:

```bash
# Clone este repositório
$ git clone git@github.com:fabianapduarte/idw-spark.git

# Acesse a pasta do repositório
$ cd idw-spark

# Gerar o JAR
$ mvn clean package

# Execute o algoritmo, passando as coordenadas para cálculo da interpolação espacial e o tipo de arquivo lido (true = arquivo .txt, false = arquivo parquet)
# spark-submit --class br.ufrn.idw.Application  --master local[*] target/idw-1.0-SNAPSHOT.jar <x> <y> <true|false>
# Exemplo:
$ spark-submit --class br.ufrn.idw.Application  --master local[*] target/idw-1.0-SNAPSHOT.jar 1 1 true
```

3. Para ver as métricas da aplicação, acesse `localhost:4040` no navegador. O acesso só é possível até 200s após o fim da execução.

> ⚠ Sobre o dataset
>
> Para gerar o arquivo de dataset, execute a classe `utils.GenerateData.java` presente no [projeto Java](https://github.com/fabianapduarte/idw-java). O arquivo gerado possui extensão .txt. Caso queira rodar a aplicação com o tipo parquet, execute a classe `ConvertTextToParquet` deste projeto.
