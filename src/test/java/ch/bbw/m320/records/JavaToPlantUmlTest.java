package ch.bbw.m320.records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Zeichne ein Object Diagram dieser Bibliothek mit PlantUML (https://plantuml.com/object-diagram).<br/>
 * Online: www.plantuml.com/plantuml/uml/ <br/>
 * Oder mit PlantUML Intellij plugin ein neues File {@code library.puml}.
 */
class JavaToPlantUmlTest {

	enum Genre {
		Action, Comic, Drama, Fable, Fantasy, Mystery, Horror, Romance, Essay, Poetry,
	}

	record Library(List<BookInstance> books, List<Account> customers) {
	}

	record Book(String isbn, String title, String summary, String publisher, Genre genre) {
	}

	record BookInstance(Book book, String publisher, String barcode) {
	}

	record Account(UUID id, LocalDate opened, List<BookInstance> borrowed) {
	}

	record BookBorrowEvent(BookInstance item, LocalDateTime borrowedAt){
	}

	record History(Account account, List<BookBorrowEvent> borrowed){
	}
}
