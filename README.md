# Cristalix World API DOCS (актуальная версия 1.0.2)

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
    implementation 'me.func:world-api:1.0.2'
}
```

<h3>Как скачать мир с билды кристаликса?</h3>

`val worldMeta = MapLoader.load("Категория карты", "Название")`

<h3>Как получить что-то?</h3>

1. Мир - `worldMeta.getWorld()`
2. табличку с карты (есть версия со списком) - `worldMeta.label(key: String, tag: String)` 
3. Выделение области двумя точками (есть версия со списком) - `worldMeta.box(key: String, tag: String)` 
