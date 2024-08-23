package ch.bbw.m320.records;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

/**
 * Hier lernen wir, wie wir eine Library nutzen (hier Jackson) um
 * zwischen einem serialisierten Datenformat (hier JSON) und Java-DTOs hin und her zu wechseln.
 */
class SerializeWithJacksonTest implements WithAssertions {

	final ObjectMapper objectMapper = new ObjectMapper().setDefaultPrettyPrinter(new DefaultPrettyPrinter());

	public record Friend() {
		// TODO: implement me
	}

	/**
	 * wir schreiben einen einfachen Java-record
	 */
	@Test
	void serializeSimpleJson() throws JsonProcessingException {
		// TO NOT EDIT THIS METHOD, edit the Friend class above
		// language=JSON
		var json = """
				{
				   "name": "Tamara Mccullough",
				   "eyeColor": "blue",
				   "phone": "+41 79 862 58 72",
				   "isTrustworthy": false,
				   "age": 21
				 }
				""".stripIndent();
		assertThat(objectMapper.readValue(json, friendClass))
				.isInstanceOf(Record.class)
				.extracting("name", "isTrustworthy", "age")
				.containsExactly("Tamara Mccullough", false, 21);
	}

	/**
	 * man kann den Constructor customizen und darin Exceptions werfen.
	 */
	@Test
	void customConstructor() {
		// TO NOT EDIT THIS METHOD, edit the Friend class above
		// language=JSON
		var jsonWithStrangeAge = """
				{
					"name": "Gray Mccoy",
					"eyeColor": "green",
					"phone": "+41 79 860 47 52",
					"isTrustworthy": true,
					"age": -3
				  }
				""".stripIndent();
		assertThatThrownBy(() -> objectMapper.readValue(jsonWithStrangeAge, friendClass))
				.hasMessageContaining("too young");
	}

	interface HasAlias {
		String alias();
	}

	HasAlias friendWithAlias() {
		// TODO: implement me
		return null;
	}

	/**
	 * Records können Interfaces implementieren.
	 */
	@Test
	void implementAnInterface() {
		// TO NOT EDIT THIS METHOD, edit the friendWithAlias method above
		var friend = friendWithAlias();
		assertThat(friend).extracting("name").isEqualTo("John Doe");
		assertThat(friend).extracting("alias").isEqualTo("Jonny");
		assertThat(friend).isInstanceOf(HasAlias.class);
	}

	/**
	 * Jackson Serializierung kann customized werden mit Annotationen.
	 * Lektüre: <a href="https://www.concretepage.com/jackson-api/jackson-jsonignore-jsonignoreproperties-and-jsonignoretype">JsonIgnore</a>
	 */
	@Test
	void tellJacksonToIgnoreTheAlias() throws JsonProcessingException {
		// TO NOT EDIT THIS METHOD, edit the friendWithAlias method above
		var friend = friendWithAlias();
		assertThat(friend).extracting("alias").isEqualTo("Jonny");
		assertThat(objectMapper.writeValueAsString(friend)).doesNotContain("Jonny");
	}

	public record Gang() {
		// TODO: implement me
	}

	/**
	 * Klasse mit einer Liste einer anderen Klasse
	 */
	@Test
	void nested() throws JsonProcessingException {
		// TO NOT EDIT THIS METHOD, edit the Gang class above
		// language=JSON
		var json = """
				{
				 "uuid": "b0e30baa-4416-49de-ae7b-ef3fe00f9bbb",
				 "members": [
				   {
					 "name": "Barbara Alexander",
					 "eyeColor": "blue",
					 "phone": "+41 79 859 41 82",
					 "isTrustworthy": false,
					 "age": 24
				   },
				   {
					 "name": "Franklin Horton",
					 "eyeColor": "green",
					 "phone": "+41 79 827 59 52",
					 "isTrustworthy": true,
					 "age": 27
				   },
				   {
					 "name": "Grace Watts",
					 "eyeColor": "green",
					 "phone": "+41 79 953 47 93",
					 "isTrustworthy": true,
					 "age": 18
				   }
				 ]
				}
				""".stripIndent();
		assertThat(objectMapper.readValue(json, gangClass))
				.extracting("uuid")
				.isInstanceOf(UUID.class);
	}

	/**
	 * again...
	 */
	@Test
	void emptyGangIsLonely() {
		// TO NOT EDIT THIS METHOD, edit the Gang class above
		// language=JSON
		var json = """
				{
				 "uuid": "b0e30baa-4416-49de-ae7b-ef3fe00f9bbc",
				 "members": []
				}
				""".stripIndent();
		assertThatThrownBy(() -> objectMapper.readValue(json, gangClass))
				.hasMessageContaining("no members");
	}


	// ignore me
	static Class<?> friendClass = Friend.class;
	static Class<?> gangClass = Gang.class;
}
