package enums;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class EnumTest {


	@Test
	void testSerialization(){
		Jsonb jsonb = JsonbBuilder.create();

		Colors expected = Colors.GREEN;

		String expectedJson = jsonb.toJson(expected);
		System.out.println(expectedJson);

		assertEquals(expected, jsonb.fromJson(expectedJson, Colors.class));

	}
}
