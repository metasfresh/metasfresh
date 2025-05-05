package de.metas.business;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.document.DocTypeId;
import de.metas.invoice.InvoiceId;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.Singular;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

@Data
public class TestInvoice
{
	private final OrgId orgId;

	private final BPartnerId customerId;

	private final LocalDate dateInvoiced;

	private final SOTrx soTrx;

	private final BPartnerId salesRepPartnerId;

	private final DocTypeId docTypeId;

	private final CurrencyId currencyId;

	private final ImmutableList<TestInvoiceLine> testInvoiceLines;

	private I_C_Invoice invoiceRecord;

	private InvoiceId invoiceId;

	@Builder
	private TestInvoice(
			@NonNull final SOTrx soTrx,
			@Nullable final OrgId orgId,
			@NonNull final BPartnerId customerId,
			@Nullable final DocTypeId docTypeId,
			@Nullable final BPartnerId salesRepPartnerId,
			@Nullable final CurrencyId currencyId,
			@Nullable final LocalDate dateInvoiced,
			@Singular final ImmutableList<TestInvoiceLine> testInvoiceLines)
	{
		this.soTrx = soTrx;
		this.orgId = orgId;
		this.docTypeId = docTypeId;
		this.customerId = customerId;
		this.salesRepPartnerId = salesRepPartnerId;
		this.currencyId = currencyId;
		this.dateInvoiced = coalesceSuppliers(() -> dateInvoiced, () -> SystemTime.asLocalDate());
		this.testInvoiceLines = testInvoiceLines;
	}

	public TestInvoice createInvoiceRecord()
	{
		invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setIsSOTrx(soTrx.toBoolean());
		if (orgId != null)
		{
			invoiceRecord.setAD_Org_ID(orgId.getRepoId());
		}
		invoiceRecord.setC_DocType_ID(DocTypeId.toRepoId(docTypeId));
		invoiceRecord.setC_BPartner_ID(BPartnerId.toRepoId(customerId));
		invoiceRecord.setC_BPartner_SalesRep_ID(BPartnerId.toRepoId(salesRepPartnerId));
		invoiceRecord.setDateInvoiced(TimeUtil.asTimestamp(dateInvoiced));
		invoiceRecord.setC_Currency_ID(CurrencyId.toRepoId(currencyId));
		saveRecord(invoiceRecord);

		invoiceId = InvoiceId.ofRepoId(invoiceRecord.getC_Invoice_ID());

		for (final TestInvoiceLine testInvoiceLine : testInvoiceLines)
		{
			testInvoiceLine.createInvoiceLineRecord(invoiceId);
		}

		return this;
	}

	public I_C_Invoice getInvoiceRecord()
	{
		return assumeNotNull(invoiceRecord, "invoice first needs to be created with createInvoiceLineRecord");
	}

	public InvoiceId getInvoiceLineId()
	{
		return assumeNotNull(invoiceId, "invoice first needs to be created with createInvoiceLineRecord");
	}

	public int getRepoId()
	{
		return getInvoiceLineId().getRepoId();
	}

	public int getLineRepoId(final int index)
	{
		return testInvoiceLines.get(index).getInvoiceAndLineId().getRepoId();
	}
}
