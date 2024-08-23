package ch.bbw.m320.records;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

/**
 * Hier lernen wir wie standard Java Klassen und Records zusammenhängen.
 */
class JavaRecordTest implements WithAssertions {

	/**
	 * Simple interface for all things containing a title.
	 * It is common to write public interfaces in its own file,
	 * but here the interface is embedded into the JavaRecordTest class.
	 */
	interface HasTitle {

		String title();
	}

	/**
	 * An (old school) class representing an immutable movie with title and year.
	 * It provides simple getters and standard Java methods for comparing and sorting.
	 */
	@SuppressWarnings("ClassCanBeRecord")
	static final class MovieAsSimpleClass implements HasTitle {

		private final String title;

		private final int year;

		public MovieAsSimpleClass(String title, int year) {
			if (title == null) {
				throw new IllegalArgumentException("a title is required");
			}
			if (year < 1878) {
				throw new IllegalArgumentException("no movies existed in year " + year);
			}
			this.title = title;
			this.year = year;
		}

		@Override
		public String title() {
			return title;
		}

		public int year() {
			return year;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			MovieAsSimpleClass that = (MovieAsSimpleClass) o;
			if (year != that.year) {
				return false;
			}
			return title.equals(that.title);
		}

		@Override
		public int hashCode() {
			int result = title.hashCode();
			result = 31 * result + year;
			return result;
		}

		@Override
		public String toString() {
			return "MovieAsSimpleClass{title='" + title + "', year=" + year + '}';
		}
	}

	@Test
	void oldVerboseStyleButWorks() {
		assertThat(new MovieAsSimpleClass("something", 2020)).isNotInstanceOf(Record.class)
				.satisfies(x -> assertThat(x.title()).isEqualTo("something"))
				.satisfies(x -> assertThat(x.year()).isEqualTo(2020))
				.satisfies(x -> assertThat(new MovieAsSimpleClass(x.title(), x.year())).isEqualTo(x))
				.isInstanceOf(HasTitle.class);
		assertThatThrownBy(() -> new MovieAsSimpleClass("", 1860)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("no movies existed in year 1860");
		assertThatThrownBy(() -> new MovieAsSimpleClass(null, 2020)).isInstanceOf(IllegalArgumentException.class)
				.hasMessage("a title is required");
	}

	/**
	 * Schreibe einen neuen Java-Record `MovieAsRecord`, der genau der {@link MovieAsSimpleClass} entspricht.
	 * Lektüre: https://www.baeldung.com/java-record-keyword
	 */
	@Test
	void fromClassToRecord() {
		// TODO: entferne die Kommentare (Shortcut: Ctrl-/)
		//assertThat(new MovieAsRecord("something", 2020)).isInstanceOf(Record.class)
		//		.satisfies(x -> assertThat(x.title()).isEqualTo("something"))
		//		.satisfies(x -> assertThat(x.year()).isEqualTo(2020))
		//		.satisfies(x -> assertThat(new MovieAsRecord(x.title(), x.year())).isEqualTo(x))
		//		.isInstanceOf(HasTitle.class);
		//assertThatThrownBy(() -> new MovieAsRecord("", 1860)).isInstanceOf(IllegalArgumentException.class)
		//		.hasMessage("no movies existed in year 1860");
		//assertThatThrownBy(() -> new MovieAsRecord(null, 2020)).isInstanceOf(IllegalArgumentException.class)
		//		.hasMessage("a title is required");
	}

	/**
	 * Schreibe einen neuen Java-Record `MusicAlbum`, der mit Attributen: title, artistName, numberOfTracks, previousMusicAlbum,
	 * sodass der Test grün wird.
	 */
	@Test
	void musicAlbumRecord() {
		// TODO: entferne die Kommentare (Shortcut: Ctrl-/)
		//var albumWithoutPrevious = new MusicAlbum("The Treasures Within Hearts", "Entwine", 8);
		//assertThat(albumWithoutPrevious.previousMusicAlbum()).isNull();
		//var secondAlbum = new MusicAlbum("Gone", "Entwine", 8, albumWithoutPrevious);
		//assertThat(secondAlbum.previousMusicAlbum().title()).isEqualTo("The Treasures Within Hearts");
		//assertThat(secondAlbum.artistAndTitle()).isEqualTo("Entwine - Gone");
	}
}
