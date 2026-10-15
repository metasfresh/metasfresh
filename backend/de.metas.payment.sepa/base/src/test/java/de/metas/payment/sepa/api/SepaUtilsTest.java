package de.metas.payment.sepa.api;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers the helpers shared by the pain.001.001.03.ch.02 and pain.001.001.09.ch.03 marshallers.
 */
class SepaUtilsTest
{
	@Test
	void isSwissIBAN()
	{
		assertThat(SepaUtils.isSwissIBAN("CH50 2197 7000 0043 3134 6")).isTrue();
		assertThat(SepaUtils.isSwissIBAN("LI21 0881 0000 2324 013A A")).isTrue();
		assertThat(SepaUtils.isSwissIBAN("NL31INGB0000000044")).isFalse();
		assertThat(SepaUtils.isSwissIBAN(" ")).isFalse();
		assertThat(SepaUtils.isSwissIBAN(null)).isFalse();
	}

	@Test
	void extractIIDFromSwissIbanOrNull()
	{
		assertThat(SepaUtils.extractIIDFromSwissIbanOrNull("CH50 2197 7000 0043 3134 6", () -> "line 1")).isEqualTo("21977");
		assertThat(SepaUtils.extractIIDFromSwissIbanOrNull("NL31INGB0000000044", () -> "line 1")).isNull();
		assertThat(SepaUtils.extractIIDFromSwissIbanOrNull(null, () -> "line 1")).isNull();

		assertThatThrownBy(() -> SepaUtils.extractIIDFromSwissIbanOrNull("CH50 21", () -> "line 1"))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("Given IBAN CH50 21 for line 1 is to short");
	}

	@Test
	void isValidQrReference()
	{
		assertThat(SepaUtils.isValidQrReference("210000000003139471430009017")).isTrue();
		assertThat(SepaUtils.isValidQrReference("333617000113546105930400000")).isTrue();
		assertThat(SepaUtils.isValidQrReference("210000000003139471430009018")).isFalse(); // check digit
		assertThat(SepaUtils.isValidQrReference("21000000000313947143000901")).isFalse(); // length
		assertThat(SepaUtils.isValidQrReference("21000000000313947143000901A")).isFalse(); // not a digit
		assertThat(SepaUtils.isValidQrReference("2100000000031394714300090١" + "7")).isFalse(); // non-ASCII digit
		assertThat(SepaUtils.isValidQrReference("")).isFalse();
		assertThat(SepaUtils.isValidQrReference(null)).isFalse();
	}

	@Test
	void isQrReferenceShaped()
	{
		assertThat(SepaUtils.isQrReferenceShaped("210000000003139471430009017")).isTrue();
		assertThat(SepaUtils.isQrReferenceShaped("210000000003139471430009018")).isTrue(); // the check digit does not matter
		assertThat(SepaUtils.isQrReferenceShaped("21000000000313947143000901")).isFalse(); // length
		assertThat(SepaUtils.isQrReferenceShaped("21000000000313947143000901A")).isFalse(); // not a digit
		assertThat(SepaUtils.isQrReferenceShaped("2100000000031394714300090١" + "7")).isFalse(); // non-ASCII digit
		assertThat(SepaUtils.isQrReferenceShaped(null)).isFalse();
	}

	@Test
	void getFirstNonEmpty()
	{
		assertThat(SepaUtils.getFirstNonEmpty(() -> null, () -> " ", () -> " name ", () -> "other")).isEqualTo("name");
		assertThat(SepaUtils.getFirstNonEmpty(() -> null, () -> " ")).isNull();
	}
}
