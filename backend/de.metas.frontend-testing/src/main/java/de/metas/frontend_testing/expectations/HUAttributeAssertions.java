package de.metas.frontend_testing.expectations;

import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.NumberUtils;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static de.metas.frontend_testing.expectations.assertions.Assertions.assertThat;
import static de.metas.frontend_testing.expectations.assertions.Assertions.fail;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softly;
import static de.metas.frontend_testing.expectations.assertions.Assertions.softlyPutContext;

/**
 * Asserts an HU's attributes against a code -> expected-value map,
 * shared by the HU and the manufacturing expectations.
 */
final class HUAttributeAssertions
{
	private HUAttributeAssertions() {}

	public static void assertAttributes(
			@NonNull final AssertExpectationsCommandServices services,
			@NonNull final Map<String, String> expectations,
			@NonNull final HuId huId)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		assertAttributes(services, expectations, services.getHUById(huId));
	}

	public static void assertAttributes(
			@NonNull final AssertExpectationsCommandServices services,
			@NonNull final Map<String, String> expectations,
			@NonNull final I_M_HU hu)
	{
		if (expectations.isEmpty())
		{
			return;
		}

		final ImmutableAttributeSet actualAttributes = services.getAttributes(hu);

		softly(() -> {
			softlyPutContext("expectedAttributes", expectations);
			softlyPutContext("actualAttributes", actualAttributes);

			expectations.forEach((attributeCodeStr, expectedValueStr) -> {
				final AttributeCode attributeCode = AttributeCode.ofString(attributeCodeStr);
				softlyPutContext("attributeCode", attributeCode);

				if (actualAttributes.hasAttribute(attributeCode))
				{
					final AttributeValueType type = actualAttributes.getAttributeValueType(attributeCode);
					switch (type)
					{
						case STRING:
						case LIST:
							assertAttributeValue_String(expectedValueStr, actualAttributes, attributeCode);
							break;
						case NUMBER:
							assertAttributeValue_Number(expectedValueStr, actualAttributes, attributeCode);
							break;
						case DATE:
							assertAttributeValue_Date(expectedValueStr, actualAttributes, attributeCode);
							break;
						default:
							fail("Unknown attribute value type: " + type);
					}
				}
				else if (expectedValueStr != null)
				{
					fail("Expected missing attribute " + attributeCode + " to be <" + expectedValueStr + ">");
				}
			});
		});
	}

	private static void assertAttributeValue_String(final String expectedValueStr, final ImmutableAttributeSet actualAttributes, final AttributeCode attributeCode)
	{
		final String actualValueStr = actualAttributes.getValueAsString(attributeCode);
		assertThat(actualValueStr).as("String attribute " + attributeCode).isEqualTo(expectedValueStr);
	}

	private static void assertAttributeValue_Number(final String expectedValueStr, final ImmutableAttributeSet actualAttributes, final AttributeCode attributeCode)
	{
		final BigDecimal actualValue = actualAttributes.getValueAsBigDecimal(attributeCode);
		final BigDecimal expectedValue = NumberUtils.asBigDecimal(expectedValueStr);
		assertThat(actualValue).as("Number attribute " + attributeCode).isEqualTo(expectedValue);
	}

	private static void assertAttributeValue_Date(final String expectedValueStr, final ImmutableAttributeSet actualAttributes, final AttributeCode attributeCode)
	{
		final LocalDate actualValue = actualAttributes.getValueAsLocalDate(attributeCode);

		final LocalDate expectedValue;
		if (expectedValueStr == null || expectedValueStr.trim().equals("-"))
		{
			expectedValue = null;
		}
		else if (expectedValueStr.equalsIgnoreCase("today"))
		{
			expectedValue = SystemTime.asLocalDate();
		}
		else
		{
			expectedValue = TimeUtil.asLocalDate(expectedValueStr);
		}

		assertThat(actualValue).as("Date attribute " + attributeCode).isEqualTo(expectedValue);
	}
}
