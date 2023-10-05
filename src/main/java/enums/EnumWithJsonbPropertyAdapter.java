package enums;

import static java.util.Optional.ofNullable;

import java.lang.reflect.ParameterizedType;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import jakarta.json.bind.adapter.JsonbAdapter;
import jakarta.json.bind.annotation.JsonbProperty;

public class EnumWithJsonbPropertyAdapter<E extends Enum<E>> implements JsonbAdapter<E, String> {
	private final Map<String, E> jsonToJavaMapping = new HashMap<>();
	private final EnumMap<E, String> javaToJsonMapping;

	public EnumWithJsonbPropertyAdapter() {
		super();

		Class<E> enumType = getEnumType();
		javaToJsonMapping = new EnumMap<>(enumType);

		Stream.of(enumType.getEnumConstants()).forEach(constant -> {
			final String asString;
			try {
				asString = ofNullable(
						constant.getClass()
								.getDeclaredField(constant.name())
								.getAnnotation(JsonbProperty.class))
						.map(JsonbProperty::value)
						.orElseGet(constant::name);
			} catch (final NoSuchFieldException e) {
				throw new IllegalArgumentException(e);
			}
			javaToJsonMapping.put(constant, asString);
			jsonToJavaMapping.put(asString, constant);
		});
	}

	private Class<E> getEnumType() {
		return Class.class.cast(ParameterizedType.class.cast(
						getClass().getGenericSuperclass())
				.getActualTypeArguments()[0]);
	}

	@Override
	public String adaptToJson(final E obj) {
		return javaToJsonMapping.get(obj);
	}

	@Override
	public E adaptFromJson(final String obj) {
		return ofNullable(jsonToJavaMapping.get(obj))
				.orElseThrow(() -> new IllegalArgumentException("Unknown enum value: '" + obj + "'"));
	}
}
