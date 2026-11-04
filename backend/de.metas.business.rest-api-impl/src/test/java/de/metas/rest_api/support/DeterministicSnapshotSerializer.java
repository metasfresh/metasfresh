package de.metas.rest_api.support;

import au.com.origin.snapshots.jackson.serializers.DeterministicJacksonSnapshotSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * A snapshot serializer that renders deterministically <b>regardless of the JDK's reflection order</b>.
 * <p>
 * Background: the stock {@link DeterministicJacksonSnapshotSerializer} builds its {@link ObjectMapper} via
 * {@code findAndRegisterModules()}, which (re-)registers jackson's {@code ParameterNamesModule}. Combined with
 * {@code -parameters}-compiled code, that module supplies the implicit name of a {@code @JsonCreator} constructor
 * parameter and merges it with the matching field. When a parameter carries an explicit {@code @JsonProperty}
 * rename (e.g. {@code BPartnerComposite}'s {@code orgId} field whose ctor param is {@code @JsonProperty("org")}),
 * that rename then leaks into the <i>serialized</i> property name and shifts its position in the alphabetical sort.
 * Which of the two accessor names wins, and where the property sorts, depends on
 * {@code Class.getDeclaredFields()/getDeclaredMethods()} order, which changed in JDK25 - producing a snapshot that
 * differs from the stored (base-JDK) one by a property <i>name</i> and <i>position</i> flip.
 * <p>
 * This serializer pins accessor-name resolution deterministically by resetting the mapper's
 * {@link com.fasterxml.jackson.databind.AnnotationIntrospector} to the plain {@link JacksonAnnotationIntrospector},
 * i.e. dropping the parameter-names introspector that the auto-registered module inserted. Field-based serialization
 * (the snapshot mapper serializes fields only) then no longer picks up constructor-parameter names, so the stored,
 * intended output ({@code "orgId"}, alphabetically ordered) is reproduced on any JDK. This is a pure test-infrastructure
 * change: it mirrors the central {@code JsonObjectMapperHolder} fix (which also drops {@code ParameterNamesModule})
 * and leaves production JSON (de)serialization untouched.
 */
public class DeterministicSnapshotSerializer extends DeterministicJacksonSnapshotSerializer
{
	@Override
	public void configure(final ObjectMapper objectMapper)
	{
		super.configure(objectMapper);

		// Drop the parameter-names introspector that findAndRegisterModules() inserted, so that an explicitly
		// @JsonProperty-renamed constructor parameter can no longer be merged with (and rename) the field it shadows.
		objectMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector());
	}
}
