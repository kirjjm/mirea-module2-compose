# Модуль 2 — Jetpack Compose

Федоров К.А., ИКБО-62-24. Практические задания 1–10.

## Открытие в Android Studio

1. Распакуйте проект в отдельную папку. На Windows предпочтителен короткий путь латиницей, например `C:\AndroidProjects\Module2Compose`.
2. В Android Studio выберите **Open** и укажите папку проекта с `settings.gradle.kts`.
3. Дождитесь синхронизации Gradle. Gradle JDK: встроенный JBR 21 или JDK 17+.
4. Если SDK не определился автоматически, выберите свой Android SDK. Файл `local.properties` создаётся на вашем компьютере и в архив не включён.
5. Откройте **Build Variants** и выберите `namedDebug`. Запустите `app` на устройстве Android 7.0+ или эмуляторе.
6. Переключайте задания кнопками 1–10 сверху. Задания 8 и 9 используют один трекер воды: для задания 9 добавлен набор UI-тестов.

## Отдельные сборки для задания 1

- `namedDebug`: `Hello("Кирилл")`, пакет `ru.mirea.module2`.
- `unnamedDebug`: `Hello(null)`, пакет `ru.mirea.module2.unnamed`.

Обе сборки можно установить одновременно. `HELLO_NAME` — константа BuildConfig, определяемая при сборке, поэтому это две самостоятельные компиляции.

```powershell
.\gradlew.bat assembleNamedDebug assembleUnnamedDebug
```

## Предпросмотр

Откройте `Tasks01to07.kt` в режиме **Split** или **Design**. Функция `HelloPreview` имеет три аннотации `@Preview`: `portrait`, `landscape`, `round` (200 × 200 dp, жёлтый фон, круглый экран). Для каждой включён `showSystemUi`.
В этом же файле есть предпросмотры текста, кнопки, инициалов, векторных изображений и контактов.

## Тесты

```powershell
.\gradlew.bat testNamedDebugUnitTest
.\gradlew.bat connectedNamedDebugAndroidTest
```

Вторая команда требует запущенного эмулятора или подключённого устройства с USB-отладкой.

- `BusinessLogicTest.kt`: 7 локальных проверок порога 1500 мл и стоимости корзины.
- `ModuleUiTest.kt`: 17 инструментальных проверок, включая кнопки трекера, серию успешных дней, сброс серии, восстановление состояния, пустую корзину, порог строго больше 500 и повторную композицию.
- HTML-отчёты Gradle: `app/build/reports/tests/testNamedDebugUnitTest/index.html` и `app/build/reports/androidTests/connected/debug/named/index.html`.
- Во время части UI-тестов сохраняются реальные снимки экрана во внутреннюю папку `files/screenshots` тестируемого приложения.

## Файлы заданий

| Задания | Файл |
| --- | --- |
| Запуск, переключение экранов | `MainActivity.kt` |
| 1–7 | `Tasks01to07.kt` |
| 3 | `res/values/strings.xml` |
| 6 | `res/drawable/circle.xml` |
| 8 | `WaterTracker.kt` |
| 9 | `src/androidTest/.../ModuleUiTest.kt` |
| 10 | `ShoppingCart.kt` |

В исходной корзине три товара на сумму 306 рублей. Добавляемые товары имеют случайную цену 1–99 рублей. Toast вызывается при добавлении, если новая сумма больше 500; перерисовка и удаление сообщение не вызывают.

Трекер начинает с 0 мл. «Завершить день» увеличивает серию при результате от 1500 мл включительно и сбрасывает её при меньшем результате. Оба счётчика используют `rememberSaveable`. Корзина также сохраняется при восстановлении состояния Activity.

## Зафиксированные версии

Gradle 8.14; Android Gradle Plugin 8.11.1; Kotlin и Compose Compiler 2.2.0;
Compose BOM 2025.07.00; compileSdk/targetSdk 36; build-tools 35.0.0; minSdk 24.

## Документация

- https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler
- https://developer.android.com/develop/ui/compose/testing
- https://developer.android.com/build/releases/agp-8-11-0-release-notes
