package de.metas.ui.web.window.datatypes.json;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import de.metas.JsonObjectMapperHolder;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.DebugProperties;
import de.metas.ui.web.window.datatypes.LookupValuesList;

public class JSONLookupValuesListTest
{
	@Test
	public void EMPTY_toString_does_not_fail()
	{
		final JSONLookupValuesList lookupValuesList = JSONLookupValuesList.ofLookupValuesList(LookupValuesList.EMPTY, "en_US");
		lookupValuesList.toString();
	}

	@Nested
	public class serializeDeserialize
	{
		private ObjectMapper jsonObjectMapper;

		@BeforeEach
		public void init()
		{
			jsonObjectMapper = JsonObjectMapperHolder.newJsonObjectMapper();
		}

		@Test
		public void standardTest() throws Exception
		{
			final LookupValuesList list = LookupValuesList.fromCollection(
					ImmutableList.of(
							StringLookupValue.of("Y", "Yes"),
							StringLookupValue.of("N", "No")));

			final JSONLookupValuesList jsonList = JSONLookupValuesList.ofLookupValuesList(list, "en_US");

			testSerializeDeserialize(jsonList);
		}

		private void testSerializeDeserialize(final JSONLookupValuesList obj) throws IOException
		{
			final String json = jsonObjectMapper.writeValueAsString(obj);
			System.out.println("JSON: " + json);

			final JSONLookupValuesList objDeserialized = jsonObjectMapper.readValue(json, JSONLookupValuesList.class);
			assertThat(objDeserialized.toString()).isEqualTo(obj.toString());
		}
	}

	@Nested
	public class ofLookupValuesList
	{
		private JSONLookupValuesList newJSONLookupValuesList(final JSONLookupValue... values)
		{
			final DebugProperties otherProperties = null;
			return new JSONLookupValuesList(ImmutableList.copyOf(values), otherProperties);
		}

		@Test
		public void empty()
		{
			assertThat(JSONLookupValuesList.ofLookupValuesList(LookupValuesList.EMPTY, "any_Lang"))
					.isSameAs(JSONLookupValuesList.EMPTY);
		}

		@Test
		public void orderedList()
		{
			final LookupValuesList list = LookupValuesList.fromCollection(ImmutableList.of(
					IntegerLookupValue.of(1, "Z"),
					IntegerLookupValue.of(2, "A")));
			assertThat(list.isOrdered()).isTrue(); // make sure our preconditions are right

			assertThat(JSONLookupValuesList.ofLookupValuesList(list, "any_Lang").toString())
					.isEqualTo(newJSONLookupValuesList(
							JSONLookupValue.of("1", "Z"),
							JSONLookupValue.of("2", "A")).toString());
		}

		@Test
		public void notOrderedList()
		{
			final LookupValuesList list = LookupValuesList.fromCollection(ImmutableList.of(
					IntegerLookupValue.of(1, "Z"),
					IntegerLookupValue.of(2, "A")))
					.notOrdered();
			assertThat(list.isOrdered()).isFalse(); // make sure our preconditions are right

			assertThat(JSONLookupValuesList.ofLookupValuesList(list, "any_Lang").toString())
					.isEqualTo(newJSONLookupValuesList(
							JSONLookupValue.of("2", "A"),
							JSONLookupValue.of("1", "Z")).toString());
		}

	}

}
