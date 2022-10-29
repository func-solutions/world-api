# Cristalix World API DOCS (актуальная версия 1.0.9)

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
    implementation 'me.func:world-api:1.0.9'
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
4. Как сохранить карту? `/save-all`, затем `/ur <КОЛИЧЕСТВО ЧАНКОВ>`, например 80 или 120 (от центра мира - 0 0 0)

<h3>Утилита для поворотов Rotation</h3>

```kotlin
val rotated = Rotation.rotate(
    30.0, // на какой угол поворачиваем ГРАДУСЫ
    V3(0.0, 1.0, 0.0), // ось поворота (вокруг какого вектора мы крутим, тут вектор смотрит вверх) (красным)
    V3(5.0, 0.0, 0.0) // координаты точки поворота (зеленым)
)
```

![image](https://user-images.githubusercontent.com/42806772/195987152-005c03e1-eaaa-47e4-98ee-de918f60b60f.png)
