/**
 * 
 */
package de.metas.payment.sepa.api.impl;

import de.metas.payment.sepa.interfaces.I_C_Country;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

/**
 * @author cg
 *
 */
@ExtendWith(AdempiereTestWatcher.class)
public class IBANValidationBLTest
{

	private Properties ctx;

	@BeforeEach
	public void beforeTest()
	{
		AdempiereTestHelper.get().init();

		// Setup context
		ctx = Env.getCtx();
		ctx.clear();
		Env.setContext(ctx, "#AD_Client_ID", 1);
		Env.setContext(ctx, "#AD_Org_ID", 1);
		Env.setContext(ctx, "#AD_Role_ID", 1);
		Env.setContext(ctx, "#AD_User_ID", 1);

		// prepare countries: EU
		prepareCountry("AD", "n", "4", "10", "n", "4", "20", "c", "12", "30", null, null, null, null, null, null);
		prepareCountry("AT", "n", "5", "10", null, null, null, "n", "11", "20", null, null, null, null, null, null);
		prepareCountry("BE", "n", "3", "10", null, null, null, "n", "7", "20", null, null, null, null, null, null);
		prepareCountry("DE", "n", "8", "10", null, null, null, "n", "10", "20", null, null, null, null, null, null);
		prepareCountry("CH", "n", "5", "10", null, null, null, "n", "12", "20", null, null, null, null, null, null);
		prepareCountry("FR", "n", "5", "10", "n", "5", "20", "c", "11", "30", null, null, null, "n", "2", "40");
		prepareCountry("CZ", "n", "4", "10", null, null, null, "n", "16", "20", null, null, null, null, null, null);
		prepareCountry("HR", "n", "7", "10", null, null, null, "n", "10", "20", null, null, null, null, null, null);
		prepareCountry("CY", "n", "3", "10", "n", "5", "20", "c", "16", "30", null, null, null, null, null, null);
		prepareCountry("DK", "n", "4", "10", null, null, null, "n", "10", "20", null, null, null, null, null, null);
		prepareCountry("EE", "n", "2", "10", "n", "2", "20", "n", "11", "30", null, null, null, "n", "1", "40");
		prepareCountry("FI", "n", "6", "10", null, null, null, "n", "7", "20", null, null, null, "n", "1", "30");
		prepareCountry("LT", "n", "5", "10", null, null, null, "n", "11", "20", null, null, null, null, null, null);
		prepareCountry("HU", "n", "3", "10", "n", "4", "20", "n", "16", "30", null, null, null, "n", "1", "40");
		prepareCountry("LU", "n", "3", "10", null, null, null, "c", "13", "20", null, null, null, null, null, null);
		prepareCountry("PL", "n", "3", "10", "n", "4", "20", "n", "16", "40", null, null, null, "n", "1", "30");
		prepareCountry("PT", "n", "4", "10", "n", "4", "20", "n", "11", "30", null, null, null, "n", "2", "40");
		prepareCountry("SI", "n", "2", "10", "n", "3", "20", "n", "8", "30", null, null, null, "n", "2", "40");
		prepareCountry("ES", "n", "4", "10", "n", "4", "20", "n", "10", "40", null, null, null, "n", "2", "30");
		prepareCountry("SE", "n", "3", "10", null, null, null, "n", "17", "20", null, null, null, null, null, null);
		prepareCountry("GR", "n", "3", "10", "n", "4", "20", "c", "16", "30", null, null, null, null, null, null);
		prepareCountry("MC", "n", "5", "10", "n", "5", "20", "c", "11", "30", null, null, null, "n", "2", "40");
		prepareCountry("BG", "a", "4", "10", "n", "4", "20", "c", "8", "40", "n", "2", "30", null, null, null);
		prepareCountry("LV", "a", "4", "10", null, null, null, "c", "13", "20", null, null, null, null, null, null);
		prepareCountry("MT", "a", "4", "10", "n", "5", "20", "c", "18", "30", null, null, null, null, null, null);
		prepareCountry("SK", "n", "4", "10", null, null, null, "c", "16", "20", null, null, null, null, null, null);
		prepareCountry("RO", "a", "4", "10", null, null, null, "c", "16", "20", null, null, null, null, null, null);
		prepareCountry("NL", "a", "4", "10", null, null, null, "c", "10", "20", null, null, null, null, null, null);
		prepareCountry("IT", "n", "5", "20", "n", "5", "30", "c", "12", "40", null, null, null, "a", "1", "10");
		prepareCountry("SP", "n", "4", "10", "n", "4", "20", "n", "10", "40", null, null, null, "n", "2", "30");
		prepareCountry("IE", "a", "4", "10", "n", "6", "20", "n", "8", "30", null, null, null, null, null, null);
	}

	/**
	 * create country
	 */
	private void prepareCountry(final String countryCode,
			final String bankCodeCharType, final String bankCodeLength, final String bankCodeSeqNo,
			final String branchCodeCharType, final String branchCodeLength, final String branchCodeSeqNo,
			final String accountNumberCharType, final String accountNumberLength, final String accountNumberSeqNo,
			final String accountTypeCharType, final String accountTypeLength, final String accountTypeSeqNo,
			final String nationalCheckDigitCharType, final String nationalCheckDigitLength, final String nationalCheckDigiSeqNo)
	{
		final I_C_Country country = InterfaceWrapperHelper.create(ctx, I_C_Country.class, ITrx.TRXNAME_None);
		country.setCountryCode(countryCode);

		country.setBankCodeCharType(bankCodeCharType);
		country.setBankCodeLength(bankCodeLength);
		country.setBankCodeSeqNo(bankCodeSeqNo);

		country.setBranchCodeCharType(branchCodeCharType);
		country.setBranchCodeLength(branchCodeLength);
		country.setBranchCodeSeqNo(branchCodeSeqNo);

		country.setAccountNumberCharType(accountNumberCharType);
		country.setAccountNumberLength(accountNumberLength);
		country.setAccountNumberSeqNo(accountNumberSeqNo);

		country.setAccountTypeCharType(accountTypeCharType);
		country.setAccountTypeLength(accountTypeLength);
		country.setAccountTypeSeqNo(accountTypeSeqNo);

		country.setNationalCheckDigitCharType(nationalCheckDigitCharType);
		country.setNationalCheckDigitLength(nationalCheckDigitLength);
		country.setNationalCheckDigitSeqNo(nationalCheckDigiSeqNo);

		InterfaceWrapperHelper.save(country);
	}

	@Test
	public void test_CheckDigit_GoodIBAN()
	{
		final IBANValidationBL ibanValidationBL = new IBANValidationBL();

		final int checkDigitAD = ibanValidationBL.ISO7064Mod97_10("AD1200012030200359100100");
		final int checkDigitAT = ibanValidationBL.ISO7064Mod97_10("AT611904300234573201");
		final int checkDigitDE = ibanValidationBL.ISO7064Mod97_10("DE45500105170041262312");
		final int checkDigitCH = ibanValidationBL.ISO7064Mod97_10("CH9300762011623852957");
		final int checkDigitFR = ibanValidationBL.ISO7064Mod97_10("FR1420041010050500013M02606");
		final int checkDigitCZ = ibanValidationBL.ISO7064Mod97_10("CZ6508000000192000145399");
		final int checkDigitHR = ibanValidationBL.ISO7064Mod97_10("HR1210010051863000160");
		final int checkDigitCY = ibanValidationBL.ISO7064Mod97_10("CY17002001280000001200527600");
		final int checkDigitDK = ibanValidationBL.ISO7064Mod97_10("DK5000400440116243");
		final int checkDigitEE = ibanValidationBL.ISO7064Mod97_10("EE382200221020145685");
		final int checkDigitHU = ibanValidationBL.ISO7064Mod97_10("HU42117730161111101800000000");
		final int checkDigitLT = ibanValidationBL.ISO7064Mod97_10("LT121000011101001000");
		final int checkDigitLU = ibanValidationBL.ISO7064Mod97_10("LU120010001234567891");
		final int checkDigitPL = ibanValidationBL.ISO7064Mod97_10("PL10105000997603123456789123");
		final int checkDigitPT = ibanValidationBL.ISO7064Mod97_10("PT50002700000001234567833");
		final int checkDigitSI = ibanValidationBL.ISO7064Mod97_10("SI56192001234567892");
		final int checkDigitES = ibanValidationBL.ISO7064Mod97_10("ES7921000813610123456789");
		final int checkDigitSE = ibanValidationBL.ISO7064Mod97_10("SE4550000000058398257466");
		final int checkDigitGR = ibanValidationBL.ISO7064Mod97_10("GR9608100010000001234567890");
		final int checkDigitMC = ibanValidationBL.ISO7064Mod97_10("MC5811222000010123456789030");
		final int checkDigitBG = ibanValidationBL.ISO7064Mod97_10("BG18RZBB91550123456789");
		final int checkDigitLV = ibanValidationBL.ISO7064Mod97_10("LV97HABA0012345678910");
		final int checkDigitMT = ibanValidationBL.ISO7064Mod97_10("MT31MALT01100000000000000000123");
		final int checkDigitSK = ibanValidationBL.ISO7064Mod97_10("SK8975000000000012345671");
		final int checkDigitRO = ibanValidationBL.ISO7064Mod97_10("RO09BCYP0000001234567890");
		final int checkDigitFI = ibanValidationBL.ISO7064Mod97_10("FI2112345600000785");
		final int checkDigitIT = ibanValidationBL.ISO7064Mod97_10("IT60X0542811101000000123456");
		final int checkDigitSP = ibanValidationBL.ISO7064Mod97_10("ES9121000418450200051332");
		final int checkDigitNL = ibanValidationBL.ISO7064Mod97_10("NL91ABNA0417164300");
		final int checkDigitIE = ibanValidationBL.ISO7064Mod97_10("IE29AIBK93115212345678");

		assertThat(checkDigitAD).isEqualTo(1);
		assertThat(checkDigitAT).isEqualTo(1);
		assertThat(checkDigitDE).isEqualTo(1);
		assertThat(checkDigitCH).isEqualTo(1);
		assertThat(checkDigitFR).isEqualTo(1);
		assertThat(checkDigitCZ).isEqualTo(1);
		assertThat(checkDigitHR).isEqualTo(1);
		assertThat(checkDigitCY).isEqualTo(1);
		assertThat(checkDigitDK).isEqualTo(1);
		assertThat(checkDigitEE).isEqualTo(1);
		assertThat(checkDigitHU).isEqualTo(1);
		assertThat(checkDigitLT).isEqualTo(1);
		assertThat(checkDigitLU).isEqualTo(1);
		assertThat(checkDigitPL).isEqualTo(1);
		assertThat(checkDigitPT).isEqualTo(1);
		assertThat(checkDigitSI).isEqualTo(1);
		assertThat(checkDigitES).isEqualTo(1);
		assertThat(checkDigitSE).isEqualTo(1);
		assertThat(checkDigitGR).isEqualTo(1);
		assertThat(checkDigitMC).isEqualTo(1);
		assertThat(checkDigitBG).isEqualTo(1);
		assertThat(checkDigitLV).isEqualTo(1);
		assertThat(checkDigitMT).isEqualTo(1);
		assertThat(checkDigitSK).isEqualTo(1);
		assertThat(checkDigitRO).isEqualTo(1);
		assertThat(checkDigitFI).isEqualTo(1);
		assertThat(checkDigitIT).isEqualTo(1);
		assertThat(checkDigitSP).isEqualTo(1);
		assertThat(checkDigitNL).isEqualTo(1);
		assertThat(checkDigitIE).isEqualTo(1);
	}

	@Test
	public void test_CheckDigit_WrongIBAN()
	{
		final IBANValidationBL ibanValidationBL = new IBANValidationBL();

		final int checkDigitAD = ibanValidationBL.ISO7064Mod97_10("AD1200012030200359100099");
		final int checkDigitAT = ibanValidationBL.ISO7064Mod97_10("AT611904300234573200");
		final int checkDigitDE = ibanValidationBL.ISO7064Mod97_10("DE45500105170041262311");
		final int checkDigitCH = ibanValidationBL.ISO7064Mod97_10("CH9300762011623852956");
		final int checkDigitFR = ibanValidationBL.ISO7064Mod97_10("FR1420041010050500013M02605");
		final int checkDigitCZ = ibanValidationBL.ISO7064Mod97_10("CZ6508000000192000145398");
		final int checkDigitHR = ibanValidationBL.ISO7064Mod97_10("HR1210010051863000159");
		final int checkDigitCY = ibanValidationBL.ISO7064Mod97_10("CY17002001280000001200527601");
		final int checkDigitDK = ibanValidationBL.ISO7064Mod97_10("DK5000400440116242");
		final int checkDigitEE = ibanValidationBL.ISO7064Mod97_10("EE382200221020145684");
		final int checkDigitHU = ibanValidationBL.ISO7064Mod97_10("HU42117730161111101800000001");
		final int checkDigitLT = ibanValidationBL.ISO7064Mod97_10("LT121000011101001001");
		final int checkDigitLU = ibanValidationBL.ISO7064Mod97_10("LU120010001234567890");
		final int checkDigitPL = ibanValidationBL.ISO7064Mod97_10("PL10105000997603123456789122");
		final int checkDigitPT = ibanValidationBL.ISO7064Mod97_10("PT50002700000001234567832");
		final int checkDigitSI = ibanValidationBL.ISO7064Mod97_10("SI56192001234567891");
		final int checkDigitES = ibanValidationBL.ISO7064Mod97_10("ES7921000813610123456788");
		final int checkDigitSE = ibanValidationBL.ISO7064Mod97_10("SE4550000000058398257456");
		final int checkDigitGR = ibanValidationBL.ISO7064Mod97_10("GR9608100010000001234567891");
		final int checkDigitMC = ibanValidationBL.ISO7064Mod97_10("MC5811222000010123456789031");
		final int checkDigitBG = ibanValidationBL.ISO7064Mod97_10("BG18RZBB91550123456788");
		final int checkDigitLV = ibanValidationBL.ISO7064Mod97_10("LV97HABA0012345678911");
		final int checkDigitMT = ibanValidationBL.ISO7064Mod97_10("MT31MALT01100000000000000000122");
		final int checkDigitSK = ibanValidationBL.ISO7064Mod97_10("SK8975000000000012345670");
		final int checkDigitRO = ibanValidationBL.ISO7064Mod97_10("RO09BCYP0000001234567891");
		final int checkDigitFI = ibanValidationBL.ISO7064Mod97_10("FI2112345600000787");
		final int checkDigitIT = ibanValidationBL.ISO7064Mod97_10("IT60X0542811101000000123455");
		final int checkDigitSP = ibanValidationBL.ISO7064Mod97_10("ES9121000418450200051331");
		final int checkDigitNL = ibanValidationBL.ISO7064Mod97_10("NL91ABNA0417164301");
		final int checkDigitIE = ibanValidationBL.ISO7064Mod97_10("IE29AIBK93115212345677");

		assertThat(checkDigitAD).isNotEqualTo(1);
		assertThat(checkDigitAT).isNotEqualTo(1);
		assertThat(checkDigitDE).isNotEqualTo(1);
		assertThat(checkDigitCH).isNotEqualTo(1);
		assertThat(checkDigitFR).isNotEqualTo(1);
		assertThat(checkDigitCZ).isNotEqualTo(1);
		assertThat(checkDigitHR).isNotEqualTo(1);
		assertThat(checkDigitCY).isNotEqualTo(1);
		assertThat(checkDigitDK).isNotEqualTo(1);
		assertThat(checkDigitEE).isNotEqualTo(1);
		assertThat(checkDigitHU).isNotEqualTo(1);
		assertThat(checkDigitLT).isNotEqualTo(1);
		assertThat(checkDigitLU).isNotEqualTo(1);
		assertThat(checkDigitPL).isNotEqualTo(1);
		assertThat(checkDigitPT).isNotEqualTo(1);
		assertThat(checkDigitSI).isNotEqualTo(1);
		assertThat(checkDigitES).isNotEqualTo(1);
		assertThat(checkDigitSE).isNotEqualTo(1);
		assertThat(checkDigitGR).isNotEqualTo(1);
		assertThat(checkDigitMC).isNotEqualTo(1);
		assertThat(checkDigitBG).isNotEqualTo(1);
		assertThat(checkDigitLV).isNotEqualTo(1);
		assertThat(checkDigitMT).isNotEqualTo(1);
		assertThat(checkDigitSK).isNotEqualTo(1);
		assertThat(checkDigitRO).isNotEqualTo(1);
		assertThat(checkDigitFI).isNotEqualTo(1);
		assertThat(checkDigitIT).isNotEqualTo(1);
		assertThat(checkDigitSP).isNotEqualTo(1);
		assertThat(checkDigitNL).isNotEqualTo(1);
		assertThat(checkDigitIE).isNotEqualTo(1);
	}

	@Test
	public void test_BBAN()
	{
		final IBANValidationBL ibanValidationBL = new IBANValidationBL();

		ibanValidationBL.validate("CZ6508000000192000145399");
		ibanValidationBL.validate("AD1200012030200359100100");
		ibanValidationBL.validate("AT611904300234573201");
		ibanValidationBL.validate("DE45500105170041262312");
		ibanValidationBL.validate("CH9300762011623852957");
		ibanValidationBL.validate("FR1420041010050500013M02606");
		ibanValidationBL.validate("HR1210010051863000160");
		ibanValidationBL.validate("CY17002001280000001200527600");
		ibanValidationBL.validate("DK5000400440116243");
		ibanValidationBL.validate("EE382200221020145685");
		ibanValidationBL.validate("HU42117730161111101800000000");
		ibanValidationBL.validate("LT121000011101001000");
		ibanValidationBL.validate("LU120010001234567891");
		ibanValidationBL.validate("PL10105000997603123456789123");
		ibanValidationBL.validate("PT50002700000001234567833");
		ibanValidationBL.validate("SI56191000000123438");
		ibanValidationBL.validate("ES7921000813610123456789");
		ibanValidationBL.validate("SE4550000000058398257466");
		ibanValidationBL.validate("GR9608100010000001234567890");
		ibanValidationBL.validate("MC5811222000010123456789030");
		ibanValidationBL.validate("BG18RZBB91550123456789");
		ibanValidationBL.validate("LV97HABA0012345678910");
		ibanValidationBL.validate("MT31MALT01100000000000000000123");
		ibanValidationBL.validate("SK8975000000000012345671");
		ibanValidationBL.validate("RO09BCYP0000001234567890");
		ibanValidationBL.validate("FI2112345600000785");
		ibanValidationBL.validate("IT60X0542811101000000123456");
		ibanValidationBL.validate("ES9121000418450200051332");
		ibanValidationBL.validate("NL91ABNA0417164300");
		ibanValidationBL.validate("IE29AIBK93115212345678");

	}

}
