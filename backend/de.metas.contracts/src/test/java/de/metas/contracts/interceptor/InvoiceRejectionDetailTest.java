/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.contracts.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_Invoice_Rejection_Detail;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class InvoiceRejectionDetailTest
{
	public static final int C_INVOICE_ID = 11223344;
	public static final InvoiceId INVOICE_ID = InvoiceId.ofRepoId(C_INVOICE_ID);
	private static final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void setIsDone_true()
	{
		final I_C_Invoice_Rejection_Detail detail = createTestData();
		detail.setIsDone(true);

		new C_Invoice_Rejection_Detail().onUpdateIsDone(detail);

		final org.compiere.model.I_C_Invoice updatedInvoice = invoiceDAO.getByIdInTrx(INVOICE_ID);
		Assertions.assertThat(updatedInvoice.isInDispute()).isFalse();
	}

	@Test
	void setIsDone_false()
	{
		final I_C_Invoice_Rejection_Detail detail = createTestData();
		detail.setIsDone(false);

		new C_Invoice_Rejection_Detail().onUpdateIsDone(detail);

		final org.compiere.model.I_C_Invoice updatedInvoice = invoiceDAO.getByIdInTrx(INVOICE_ID);
		Assertions.assertThat(updatedInvoice.isInDispute()).isTrue();
	}

	@NonNull
	private I_C_Invoice_Rejection_Detail createTestData()
	{
		final Properties ctx = Env.getCtx();
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, I_C_Invoice.class, ITrx.TRXNAME_None);
		invoice.setIsInDispute(true);
		invoice.setC_Invoice_ID(C_INVOICE_ID);
		InterfaceWrapperHelper.saveRecord(invoice);
		final I_C_Invoice_Rejection_Detail detail = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Rejection_Detail.class, ITrx.TRXNAME_None);
		detail.setC_Invoice_ID(C_INVOICE_ID);
		return detail;
	}

}
