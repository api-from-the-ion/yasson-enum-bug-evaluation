package enums;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.json.bind.annotation.JsonbTypeSerializer;

@JsonbTypeSerializer(ColorsSerializer.class)
@JsonbTypeDeserializer(ColorsDeserializer.class)
//@JsonbTypeAdapter(ColorsAdapter.class)
public enum Colors {
	@JsonbProperty("Red")
	RED,
	@JsonbProperty("Green")
	GREEN
}

