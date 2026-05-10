# 🎮 Game Management System (REST API)

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=for-the-badge&logo=mysql)

Информационная система для каталогизации видеоигр, их разработчиков и категорий. Построена на стеке Spring Boot и реализует модель управления доступом на основе ролей (RBAC).

---

## 🛠 Технологический стек

* **Backend**: Java 17, Spring Boot 3.x (Web, Data JPA, Security)
* **Database**: MySQL 8.0
* **ORM**: Hibernate
* **Security**: Basic Auth + Role-Based Access Control
* **JSON**: Jackson (с защитой от рекурсии через `@JsonManagedReference`)

---

## 📊 Структура данных

Проект использует трехуровневую иерархию сущностей:
1.  **Category** (Категория) — Группирует разработчиков, имеет описание и иконку.
2.  **Developer** (Разработчик) — Принадлежит категории, содержит список игр.
3.  **Game** (Игра) — Содержит название, жанр и размер (формат: `10 GB`, `500 MB`).

### 🔐 Роли и доступ
| Роль | GET (Чтение) | POST (Создание) | PUT/DELETE (Правка) |
| :--- | :---: | :---: | :---: |
| **USER** | ✅ | ❌ | ❌ |
| **MANAGER** | ✅ | ❌ | ✅ |
| **ADMIN** | ✅ | ✅ | ✅ |

---

## 🔌 API Endpoints

### Игры (`/api/game`)
| Метод | Путь | Описание |
| :--- | :--- | :--- |
| `GET` | `/api/game/latest` | Список 5 последних игр |
| `GET` | `/api/game/search?term=...` | Поиск по названию |
| `GET` | `/api/game/type/{type}` | Фильтр по жанру |
| `POST` | `/api/game` | Добавить игру (ADMIN) |

### Разработчики и Категории
Аналогичные CRUD-методы доступны по путям `/api/developer` и `/api/category`.

---

## ⚙️ Установка и запуск

1.  **Настройте базу данных MySQL**:
    Создайте схему `lilubrary` и проверьте `src/main/resources/application.properties`:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/lilubrary
    spring.datasource.username=root
    spring.datasource.password=1982
    ```

2.  **Сборка проекта**:
    ```bash
    ./mvnw clean install
    ```

3.  **Запуск приложения**:
    ```bash
    ./mvnw spring-boot:run
    ```

---

## 📝 Валидация данных
В системе реализована строгая проверка данных:
* **Названия**: от 2 до 100 символов.
* **Типы игр**: строго `RPG`, `FPS`, `Strategy`, `Adventure`, `Simulation`.
* **Размер**: паттерн `\d+\s?(GB|MB)` (например, "15 GB").