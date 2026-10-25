fun catalog(books: List<Book>) {
    println("№  Название   Автор   Год    Жанр    Статус")
    var number = 1
    for (book in books) {
        println("$number | ${book.title} | ${book.author} | ${book.year} | ${book.genre} | ${book.status}")
        number++
    }
}

fun report(books: List<Book>) {
    val total = books.size
    println()
    println("=== ОТЧЁТ ПО КАТАЛОГУ ===")
    println("Всего книг: $total")
    println("По статусам:")
    for (status in BookStatus.entries) {
        var count = 0
        for (book in books) {
            if (book.status == status) {
                count++
            }
        }
        val percent = if (total > 0) count * 100 / total else 0
        println(" ${status.name}: $count ($percent%)")
    }
    println("По жанрам:")
    for (genre in Genre.entries) {
        var count = 0
        for (book in books) {
            if (book.genre == genre) {
                count++
            }
        }
        val percent = if (total > 0) count * 100 / total else 0
        println(" ${genre.name}: $count ($percent%)")
    }
    if (total > 0) {
        var oldest = books[0]
        var newest = books[0]
        var sumYears = 0
        for (book in books) {
            if (book.year < oldest.year) {
                oldest = book
            }
            if (book.year > newest.year) {
                newest = book
            }
            sumYears += book.year
        }
        val avg = sumYears.toDouble() / total
        println("Самая старая книга: ${oldest.title} (${oldest.year})")
        println("Самая новая книга: ${newest.title} (${newest.year})")
        println("Средний год: $avg")
    }
}

fun main() {
    val catalog = mutableListOf(
        Book("Мастер и Маргарита", "Булгаков", 1967, Genre.FICTION, BookStatus.AVAILABLE),
        Book("Преступление и наказание", "Достоевский", 1866, Genre.FICTION, BookStatus.BORROWED),
        Book("Властелин Колец", "Толкин", 1954, Genre.FANTASY, BookStatus.RESERVED),
        Book("Шерлок Холмс", "Дойл", 1892, Genre.DETECTIVE, BookStatus.AVAILABLE),
        Book("Приключения Тома Сойера", "Твен", 1876, Genre.CHILDREN, BookStatus.AVAILABLE),
        Book("1984", "Оруэлл", 1949, Genre.FICTION, BookStatus.LOST),
        Book("Алхимик", "Коэльо", 1988, Genre.FICTION, BookStatus.ARCHIVED),
        Book("Код да Винчи", "Браун", 2003, Genre.DETECTIVE, BookStatus.RESERVED),
        Book("Гарри Поттер", "Роулинг", 1997, Genre.FANTASY, BookStatus.BORROWED),
        Book("Алиса в Стране чудес", "Кэрролл", 1865, Genre.CHILDREN, BookStatus.ARCHIVED)
    )
    println("=== ТАБЛИЦА КАТАЛОГА ===")
    catalog(catalog)
    report(catalog)
    println()
    println("=== ФИЛЬТРЫ ===")
    println("Доступные книги:")
    for (book in catalog) {
        if (book.status == BookStatus.AVAILABLE) {
            println(" - ${book.title} (${book.author})")
        }
    }
    print("Введите жанр для поиска: ")
    val inputGenre = readln().trim().uppercase()
    var foundGenre: Genre? = null
    for (g in Genre.entries) {
        if (g.name == inputGenre) {
            foundGenre = g
            break
        }
    }
    if (foundGenre == null) {
        println("Ошибка: такого жанра не существует.")
    } else {
        println("Книги в жанре $foundGenre:")
        var count = 0
        for (book in catalog) {
            if (book.genre == foundGenre) {
                println(" - ${book.title}")
                count++
            }
        }
        if (count == 0) {
            println("  (нет книг)")
        }
    }
    print("Введите год, после которого нужно найти книги: ")
    val inputYear = (readln()).toInt()
    println("Книги, изданные после $inputYear года:")
    for (book in catalog) {
        if (book.year > inputYear) {
            println(" - ${book.title} (${book.year})")
        }
    }
    println()
    println("=== ДЕЙСТВИЯ С КНИГАМИ ===")
    var foundBook: Book? = null
    for (book in catalog) {
        if (book.status == BookStatus.AVAILABLE) {
            foundBook = book
            break
        }
    }
    if (foundBook != null) {
        foundBook.status = BookStatus.BORROWED
        println("Книга ${foundBook.title} успешно выдана (статус изменен на BORROWED).")
    }
}