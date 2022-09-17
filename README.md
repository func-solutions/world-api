# Cristalix World API DOCS (актуальная версия 1.0.7)

<h2>Как подключить?</h2>

<h3>Get Started with `gradle`</h3>

```groovy
repositories {
    mavenCentral()
    maven {
        url 'https://repo.c7x.dev/repository/maven-public/'
        credentials {
            username System.getenv("CRI_REPO_LOGIN")
            password System.getenv("CRI_REPO_PASSWORD")
        }
    }
}

dependencies {
    implementation 'me.func:world-api:1.0.7'
}
```

<h3>Как скачать мир с билды кристаликса?</h3>

`val worldMeta = MapLoader.load("Категория карты", "Название")`


<h3>Как получить что-то?</h3>

1. Мир - `worldMeta.getWorld()`
2. табличку с карты (есть версия со списком) - `worldMeta.label(key: String, tag: String)` 
3. Выделение области двумя точками (есть версия со списком) - `worldMeta.box(key: String, tag: String)` 

<h3>Что делать на билде?</h3>

1. Как сделать свой мир? `/newmap <РАЗДЕЛ> <НАЗВАНИЕ>`
2. Как зайти на карту? `/map <РАЗДЕЛ> <НОМЕР КАРТЫ>`
3. Как поставить label? 
![2022-09-17_21 03 08](https://user-images.githubusercontent.com/42806772/190870509-1b60f96b-d2c0-43cf-b9e8-407bc864adc5.png)
4. Как сохранить карту? `/save-all`, затем `/ur <КОЛИЧЕСТВО ЧАНКОВ>`, например 80 или 120
