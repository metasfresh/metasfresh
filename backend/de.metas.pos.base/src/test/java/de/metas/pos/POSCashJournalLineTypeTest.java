package de.metas.pos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.JsonObjectMapperHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link POSCashJournalLineType} crosses the JSON boundary for the first time in
 * {@code JsonPOSExpectation}'s {@code cashJournalLines[].type} — the wire value must be the DB code
 * ({@code "CASH_INOUT"}), not the Java constant name ({@code "CASH_IN_OUT"}), matching every other
 * {@code ReferenceListAwareEnum} on the wire (e.g. {@code DocStatus}).
 */
class POSCashJournalLineTypeTest
{
	@Test
	void serializesAsItsCode_notItsJavaName() throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final String json = mapper.writeValueAsString(POSCashJournalLineType.CASH_IN_OUT);

		assertThat(json).isEqualTo("\"CASH_INOUT\"");
	}

	@Test
	void deserializesFromItsCode() throws JsonProcessingException
	{
		final ObjectMapper mapper = JsonObjectMapperHolder.newJsonObjectMapper();

		final POSCashJournalLineType type = mapper.readValue("\"CASH_INOUT\"", POSCashJournalLineType.class);

		assertThat(type).isEqualTo(POSCashJournalLineType.CASH_IN_OUT);
	}
}
