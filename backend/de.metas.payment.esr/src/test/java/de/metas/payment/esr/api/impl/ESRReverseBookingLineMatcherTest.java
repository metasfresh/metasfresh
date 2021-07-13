package de.metas.payment.esr.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;

import org.compiere.util.Env;
import org.junit.jupiter.api.Test;

import de.metas.currency.CurrencyCode;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.refid.model.I_C_ReferenceNo_Type;
import de.metas.money.CurrencyId;
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

		final CurrencyId currencyEUR = PlainCurrencyDAO.createCurrencyId(CurrencyCode.EUR);

		final I_C_BP_BankAccount account = createBankAccount(true,
				Env.getAD_Org_ID(getCtx()),
				Env.getAD_User_ID(getCtx()),
				"01-062822-7",
				currencyEUR);

		esrImport.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		save(esrImport);

		final I_C_ReferenceNo_Type refNoType = newInstance(I_C_ReferenceNo_Type.class);
		refNoType.setName("InvoiceReference");
		save(refNoType);

		esrImportBL.loadAndEvaluateESRImportStream(esrImport, new ByteArrayInputStream(esrImportLineText.getBytes()));
		I_ESR_ImportLine esrImportLine = ESRTestUtil.retrieveSingleLine(esrImport);

		assertThat(esrImportLine.getESRTrxType()).isEqualTo(ESRConstants.ESRTRXTYPE_ReverseBooking);
		assertThat(esrImportLine.isValid()).isFalse();
		assertThat(esrImportLine.getMatchErrorMsg()).isNotNull();
		assertThat(esrImportLine.isProcessed()).isFalse();
	}

}
