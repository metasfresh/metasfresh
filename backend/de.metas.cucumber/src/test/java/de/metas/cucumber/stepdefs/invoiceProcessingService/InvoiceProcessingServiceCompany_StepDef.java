/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.invoiceProcessingService;

import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.doctype.C_DocType_StepDefData;
import de.metas.document.DocTypeId;
import de.metas.product.ProductId;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_InvoiceProcessingServiceCompany;
import org.compiere.model.I_InvoiceProcessingServiceCompany_BPartnerAssignment;

@RequiredArgsConstructor
public class InvoiceProcessingServiceCompany_StepDef
{
	private final C_BPartner_StepDefData bpartnerTable;
	private final C_DocType_StepDefData docTypeTable;
	private final M_Product_StepDefData productTable;
	private final InvoiceProcessingServiceCompany_StepDefData invoiceProcessingServiceCompanyTable;

	@And("metasfresh contains InvoiceProcessingServiceCompany")
	public void metasfreshContainsInvoiceProcessingServiceCompany(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createInvoiceProcessingServiceCompany);
	}

	private void createInvoiceProcessingServiceCompany(@NonNull final DataTableRow dataTableRow)
	{
		final I_InvoiceProcessingServiceCompany record = InterfaceWrapperHelper.newInstance(I_InvoiceProcessingServiceCompany.class);

		final BPartnerId bPartnerId = dataTableRow
				.getAsIdentifier(I_InvoiceProcessingServiceCompany.COLUMNNAME_ServiceCompany_BPartner_ID)
				.lookupNotNullIdIn(bpartnerTable);
		record.setServiceCompany_BPartner_ID(bPartnerId.getRepoId());

		final ProductId productId = dataTableRow
				.getAsIdentifier(I_InvoiceProcessingServiceCompany.COLUMNNAME_ServiceFee_Product_ID)
				.lookupNotNullIdIn(productTable);
		record.setServiceFee_Product_ID(productId.getRepoId());

		final DocTypeId docTypeId = dataTableRow
				.getAsIdentifier(I_InvoiceProcessingServiceCompany.COLUMNNAME_ServiceInvoice_DocType_ID)
				.lookupNotNullIdIn(docTypeTable);
		record.setServiceInvoice_DocType_ID(docTypeId.getRepoId());

		InterfaceWrapperHelper.saveRecord(record);
		dataTableRow
				.getAsOptionalIdentifier()
				.ifPresent(identifier -> invoiceProcessingServiceCompanyTable.putOrReplace(identifier, record));
	}

	@And("metasfresh contains InvoiceProcessingServiceCompany_BPartnerAssignment")
	public void metasfreshContainsInvoiceProcessingServiceCompany_BPartnerAssignment(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createInvoiceProcessingServiceCompany_BPartnerAssignment);
	}

	private void createInvoiceProcessingServiceCompany_BPartnerAssignment(@NonNull final DataTableRow dataTableRow)
	{
		final I_InvoiceProcessingServiceCompany_BPartnerAssignment record = InterfaceWrapperHelper.newInstance(I_InvoiceProcessingServiceCompany_BPartnerAssignment.class);

		final I_InvoiceProcessingServiceCompany hdr = dataTableRow
				.getAsIdentifier(I_InvoiceProcessingServiceCompany_BPartnerAssignment.COLUMNNAME_InvoiceProcessingServiceCompany_ID)
				.lookupNotNullIn(invoiceProcessingServiceCompanyTable);
		record.setInvoiceProcessingServiceCompany_ID(hdr.getInvoiceProcessingServiceCompany_ID());

		final BPartnerId bPartnerId = dataTableRow
				.getAsIdentifier(I_InvoiceProcessingServiceCompany_BPartnerAssignment.COLUMNNAME_C_BPartner_ID)
				.lookupNotNullIdIn(bpartnerTable);
		record.setC_BPartner_ID(bPartnerId.getRepoId());

		dataTableRow.getAsOptionalBigDecimal(I_InvoiceProcessingServiceCompany_BPartnerAssignment.COLUMNNAME_FeePercentageOfGrandTotal)
				.ifPresent(record::setFeePercentageOfGrandTotal);

		InterfaceWrapperHelper.saveRecord(record);
	}
}
