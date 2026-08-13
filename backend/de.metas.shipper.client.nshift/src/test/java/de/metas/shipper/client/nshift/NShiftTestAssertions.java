package de.metas.shipper.client.nshift;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Shared test assertions for the nShift request DTOs, used by {@link NShiftShipmentServiceOrderAdviceTest}
 * and {@link NShiftShipAdvisorServiceTest}.
 */
@UtilityClass
public class NShiftTestAssertions
{
	private static final Pattern EMPTY_JSON_ARRAY = Pattern.compile("\\[\\s*\\]");

	/**
	 * Asserts the serialized nShift request JSON carries no empty list ({@code [ ]}): nShift rejects an
	 * empty array with "list index out of range".
	 */
	public void assertNoEmptyJsonArrays(final String json)
	{
		assertFalse(EMPTY_JSON_ARRAY.matcher(json).find(),
				"nShift request must not serialize empty lists (nShift fails with 'list index out of range'):\n" + json);
	}
}
