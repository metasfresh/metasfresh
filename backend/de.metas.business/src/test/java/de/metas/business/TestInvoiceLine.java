package de.metas.business;

import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.compiere.model.I_C_InvoiceLine;

import java.math.BigDecimal;

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
@Builder
public class TestInvoiceLine
{
	@NonNull
	private final ProductId productId;

	@NonNull
	private final UomId uomId;

	private I_C_InvoiceLine invoiceLineRecord;

	private InvoiceAndLineId invoiceAndLineId;

	private String externalIds;

	public void createInvoiceLineRecord(@NonNull final InvoiceId invoiceId)
	{
		invoiceLineRecord = newInstance(I_C_InvoiceLine.class);
		invoiceLineRecord.setC_Invoice_ID(invoiceId.getRepoId());
		invoiceLineRecord.setM_Product_ID(productId.getRepoId());
		invoiceLineRecord.setExternalIds(externalIds);
		invoiceLineRecord.setC_UOM_ID(uomId.getRepoId());
		invoiceLineRecord.setQtyInvoiced(BigDecimal.TEN);
		saveRecord(invoiceLineRecord);

		invoiceAndLineId = InvoiceAndLineId.ofRepoId(invoiceId, invoiceLineRecord.getC_InvoiceLine_ID());
	}

	public I_C_InvoiceLine getInvoiceLineRecord()
	{
		return assumeNotNull(invoiceLineRecord, "invoiceLine first needs to be created with createInvoiceLineRecord");
	}

	public InvoiceAndLineId getInvoiceAndLineId()
	{
		return assumeNotNull(invoiceAndLineId, "invoiceLine first needs to be created with createInvoiceLineRecord");
	}
}
