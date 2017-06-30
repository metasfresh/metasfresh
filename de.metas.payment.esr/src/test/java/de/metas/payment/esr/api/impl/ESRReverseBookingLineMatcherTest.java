package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;

import org.compiere.util.Env;
import org.junit.Test;

import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.ESRTestUtil;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class ESRReverseBookingLineMatcherTest extends ESRTestBase
{
	@Test
	public void test_ReverseBookingLine()
	{
		final String esrImportLineText = "00501062822700000001100212500300890548600000080000010  220015081915082515082600388203500000000000000                          ";

		final I_ESR_Import esrImport = createImport();

		final I_C_BP_BankAccount account = newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-062822-7");
		save(account);

		esrImport.setC_BP_BankAccount(account);
		save(esrImport);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertEquals("Invalid TrxType", ESRConstants.ESRTRXTYPE_ReverseBooking, esrImportLine.getESRTrxType());
		assertEquals("Valid IsValid", false, esrImportLine.isValid());
		assertNotNull("ErrorMsg not null", esrImportLine.getMatchErrorMsg());
		assertEquals("Invalid Processed", false, esrImportLine.isProcessed());
	}


}
