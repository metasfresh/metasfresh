package de.metas.payment.sepa.api;

import de.metas.banking.BankAccount;
import de.metas.banking.BankAccountId;
import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStrings;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Covers the helpers shared by the pain.001.001.03.ch.02 and pain.001.001.09.ch.03 marshallers.
 */
class SepaUtilsTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

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
		assertThat(SepaUtils.extractIIDFromSwissIbanOrNull("CH50 2197 7000 0043 3134 6", () -> TranslatableStrings.constant("line 1"))).isEqualTo("21977");
		assertThat(SepaUtils.extractIIDFromSwissIbanOrNull("NL31INGB0000000044", () -> TranslatableStrings.constant("line 1"))).isNull();
		assertThat(SepaUtils.extractIIDFromSwissIbanOrNull(null, () -> TranslatableStrings.constant("line 1"))).isNull();

		// in unit tests, a message is rendered as its AD_Message value followed by its parameters (see PlainMsgBL)
		assertThatThrownBy(() -> SepaUtils.extractIIDFromSwissIbanOrNull("CH50 21", () -> TranslatableStrings.constant("line 1")))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("de.metas.payment.sepa.SEPA_Export_IbanTooShort")
				.hasMessageContaining("CH50 21")
				.hasMessageContaining("line 1");
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
	void isSepaSchemeCountryIban()
	{
		assertThat(SepaUtils.isSepaSchemeCountryIban("DE62007620110623852957")).isTrue();
		assertThat(SepaUtils.isSepaSchemeCountryIban("NL31INGB0000000044")).isTrue();
		assertThat(SepaUtils.isSepaSchemeCountryIban("CH50 2197 7000 0043 3134 6")).isTrue();
		assertThat(SepaUtils.isSepaSchemeCountryIban("LI21 0881 0000 2324 013A A")).isTrue();
		assertThat(SepaUtils.isSepaSchemeCountryIban("GB29 NWBK 6016 1331 9268 19")).isTrue(); // non-EEA SEPA country
		assertThat(SepaUtils.isSepaSchemeCountryIban("ME25505000012345678951")).isTrue(); // non-EEA SEPA country since v7.0 of the list
		assertThat(SepaUtils.isSepaSchemeCountryIban("RS35260005601001611379")).isTrue(); // non-EEA SEPA country since v8.0 of the list
		assertThat(SepaUtils.isSepaSchemeCountryIban("de62007620110623852957")).isTrue(); // lower case
		assertThat(SepaUtils.isSepaSchemeCountryIban("TR330006100519786457841326")).isFalse();
		assertThat(SepaUtils.isSepaSchemeCountryIban("AE070331234567890123456")).isFalse();
		assertThat(SepaUtils.isSepaSchemeCountryIban("US")).isFalse();
		assertThat(SepaUtils.isSepaSchemeCountryIban("D")).isFalse();
		assertThat(SepaUtils.isSepaSchemeCountryIban(" ")).isFalse();
		assertThat(SepaUtils.isSepaSchemeCountryIban(null)).isFalse();
	}

	@Test
	void getBankAccountInfo()
	{
		// the ID is always there; the number only if the account has one, so that the message never shows an empty gap
		assertThat(SepaUtils.getBankAccountInfo(bankAccount().build())).isEqualTo("ID 7");
		assertThat(SepaUtils.getBankAccountInfo(bankAccount().IBAN(" DE62007620110623852957 ").QR_IBAN("CH4431999123000889012").build())).isEqualTo("DE62007620110623852957 (ID 7)");
		assertThat(SepaUtils.getBankAccountInfo(bankAccount().QR_IBAN("CH4431999123000889012").accountNo("123").build())).isEqualTo("CH4431999123000889012 (ID 7)");
		assertThat(SepaUtils.getBankAccountInfo(bankAccount().IBAN(" ").accountNo("123").build())).isEqualTo("123 (ID 7)");

		// the info goes into a message that the process runs through parseTranslation; "@@" stands for a plain "@" there
		assertThat(SepaUtils.getBankAccountInfo(bankAccount().accountNo("12@34@").build())).isEqualTo("12@@34@@ (ID 7)");
	}

	@Test
	void assertValidAccountCountry_atSignInCountry_isEscaped()
	{
		assertThatThrownBy(() -> SepaUtils.assertValidAccountCountry(bankAccount().IBAN("CH@1").accountCountry("Schweiz@CH").build()))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("de.metas.payment.sepa.SEPA_Export_BankAccountCountryInvalid")
				.hasMessageContaining("CH@@1 (ID 7)")
				.hasMessageContaining("Schweiz@@CH");
	}

	@Test
	void extractIIDFromSwissIbanOrNull_tooShort_atSignInIban_isEscaped()
	{
		assertThatThrownBy(() -> SepaUtils.extractIIDFromSwissIbanOrNull("CH50@2", () -> TranslatableStrings.constant("line 1")))
				.isInstanceOf(SepaMarshallerException.class)
				.hasMessageContaining("de.metas.payment.sepa.SEPA_Export_IbanTooShort")
				.hasMessageContaining("CH50@@2");
	}

	@Test
	void escapeAtSignsForParseTranslation()
	{
		assertThat(SepaUtils.escapeAtSignsForParseTranslation("info@muster.ch / buchhaltung@muster.ch")).isEqualTo("info@@muster.ch / buchhaltung@@muster.ch");
		assertThat(SepaUtils.escapeAtSignsForParseTranslation("no at sign")).isEqualTo("no at sign");
		assertThat(Msg.parseTranslation("de_CH", SepaUtils.escapeAtSignsForParseTranslation("a@b@c@"))).isEqualTo("a@b@c@");
	}

	private static BankAccount.BankAccountBuilder bankAccount()
	{
		return BankAccount.builder()
				.id(BankAccountId.ofRepoId(7))
				.orgId(OrgId.ANY)
				.currencyId(CurrencyId.ofRepoId(102));
	}

	@Test
	void getFirstNonEmpty()
	{
		assertThat(SepaUtils.getFirstNonEmpty(() -> null, () -> " ", () -> " name ", () -> "other")).isEqualTo("name");
		assertThat(SepaUtils.getFirstNonEmpty(() -> null, () -> " ")).isNull();
	}

	private static I_SEPA_Export_Line sepaLine(final String description, final String name, final String iban, final String qrIban, final String accountNo)
	{
		final I_C_BP_BankAccount bankAccount = newInstance(I_C_BP_BankAccount.class);
		bankAccount.setDescription(description);
		bankAccount.setName(name);
		bankAccount.setIBAN(iban);
		bankAccount.setQR_IBAN(qrIban);
		bankAccount.setAccountNo(accountNo);
		save(bankAccount);

		final I_SEPA_Export_Line line = newInstance(I_SEPA_Export_Line.class);
		line.setC_BP_BankAccount_ID(bankAccount.getC_BP_BankAccount_ID());
		save(line);
		return line;
	}

	@Test
	void createInfo_bankAccountLabel()
	{
		// Description wins
		assertThat(SepaUtils.createInfo(sepaLine("my desc", "my name", "CH93 0076 2011 6238 5295 7", null, null))).endsWith("my desc");
		// no Description: Name
		assertThat(SepaUtils.createInfo(sepaLine(null, "my name", "CH93 0076 2011 6238 5295 7", null, null))).endsWith("my name");
		assertThat(SepaUtils.createInfo(sepaLine(" ", " my name ", null, null, null))).endsWith("my name");
		// no Description, no Name: IBAN, then QR-IBAN, then account no
		assertThat(SepaUtils.createInfo(sepaLine(null, null, "CH93 0076 2011 6238 5295 7", "CH44 3199 9123 0008 8901 2", "123"))).endsWith("CH93 0076 2011 6238 5295 7");
		assertThat(SepaUtils.createInfo(sepaLine(null, null, null, "CH44 3199 9123 0008 8901 2", "123"))).endsWith("CH44 3199 9123 0008 8901 2");
		assertThat(SepaUtils.createInfo(sepaLine(null, null, null, null, "123"))).endsWith("123");
	}

	@Test
	void createInfo_bankAccountWithoutAnyLabelShowsId()
	{
		final I_SEPA_Export_Line line = sepaLine(null, null, null, null, null);
		final String info = SepaUtils.createInfo(line);
		assertThat(info).doesNotContain("null").endsWith("ID " + line.getC_BP_BankAccount_ID());
	}

	@Test
	void createInfo_bankAccountLabelEscapesAtSigns()
	{
		assertThat(SepaUtils.createInfo(sepaLine("a@b", null, null, null, null))).endsWith("a@@b");
	}
}
