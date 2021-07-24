package de.metas.payment.esr.api.impl;

import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportFile;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.assertj.core.api.Assertions.*;

public class ESRReceiptLineMatcherTest extends ESRTestBase
{
	@Test
	public void test_controlLine()
	{
		// the code under test should be able to deal with the trailing spaces
		final String esrImportLineText = "999010599310999999999999999999999999999000000092000000000000025130118000000000000000000                                       ";

		final I_ESR_Import esrImport = createImport();

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat(esrImportFile.getESR_Control_Amount()).isEqualByComparingTo("920");
		assertThat(esrImportFile.getESR_Control_Trx_Qty()).isEqualByComparingTo("25");

		// TODO: there needs to be an error because "missing trx lines"
		// Assert.assertEquals("Invalid TrxType", ESRConstants.ESRTRXTYPE_Receipt, esrImportLine.getESRTrxType());
		// Assert.assertEquals("Invalid IsValid", true, esrImportLine.isValid());
		// assertThat("Invalid ImportErrorMsg", esrImportLine.getImportErrorMsg(), nullValue());
		// assertThat("Invalid MatchErrorMsg", esrImportLine.getMatchErrorMsg(), nullValue());
		// Assert.assertEquals("Invalid Processed", true, esrImportLine.isProcessed());
	}

	@Test
	public void test_invalidLength()
	{
		final String esrImportLineText = "9990105993109999999999999999999999999990000000920000000000000";
		final I_ESR_Import esrImport = createImport();

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat(esrImportFile.isValid()).isFalse();
		assertThat(esrImportFile.getDescription()).contains("ESR_Wrong_Ctrl_Line_Length_[61]");
	}

	@Test
	public void test_invalidControlAmt()
	{
		final String esrImportLineText = "999010599310999999999999999999999999999000000092x00000000000025130118000000000000000000";
		final I_ESR_Import esrImport = createImport();

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat(esrImport.isValid()).isFalse();
	}

	@Test
	public void test_invalidControlQty()
	{
		final String esrImportLineText = "99901059931099999999999999999999999999900000009200000000000002x130118000000000000000000";
		final I_ESR_Import esrImport = createImport();

		final I_ESR_ImportFile esrImportFile = createImportFile(esrImport);

		esrImportBL.loadAndEvaluateESRImportStream(esrImportFile, new ByteArrayInputStream(esrImportLineText.getBytes()));

		assertThat(esrImport.isValid()).isFalse();
	}

}
