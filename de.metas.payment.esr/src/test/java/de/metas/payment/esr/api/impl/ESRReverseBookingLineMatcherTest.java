package de.metas.payment.esr.api.impl;

import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.model.I_C_BP_BankAccount;
import de.metas.payment.esr.model.I_ESR_ImportLine;

public class ESRReverseBookingLineMatcherTest extends ESRTestBase
{
	private ESRLineMatcher matcher;

	@Override
	protected void init()
	{
		matcher = new ESRLineMatcher();
	}


	@Test
	public void test_ReverseBookingLine()
	{
		final String esrImportLineText = "00501062822700000001100212500300890548600000080000010  220015081915082515082600388203500000000000000                          ";

		final I_ESR_ImportLine esrImportLine = createImportLine(esrImportLineText);

		final I_C_BP_BankAccount account = db.newInstance(I_C_BP_BankAccount.class);

		account.setIsEsrAccount(true);
		account.setAD_Org_ID(Env.getAD_Org_ID(getCtx()));
		account.setAD_User_ID(Env.getAD_User_ID(getCtx()));
		account.setESR_RenderedAccountNo("01-062822-7");
		db.save(account);

		esrImportLine.setC_BP_BankAccount(account);
		db.save(esrImportLine);

		final I_C_ReferenceNo_Type refNoType = db.newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		db.save(refNoType);

		matcher.match(esrImportLine);

		Assert.assertEquals("Invalid TrxType", ESRConstants.ESRTRXTYPE_ReverseBooking, esrImportLine.getESRTrxType());
		Assert.assertEquals("Valid IsValid", false, esrImportLine.isValid());
		Assert.assertNotNull("ErrorMsg not null", esrImportLine.getErrorMsg());
		Assert.assertEquals("Invalid Processed", false, esrImportLine.isProcessed());
	}


}
