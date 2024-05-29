/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.invoice.detail;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.time.ZoneId;

@Repository
public class InvoiceCandidateWithDetailsRepository
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public void save(@NonNull final InvoiceCandidateWithDetails invoiceWithDetails)
	{
		final OrgId orgId = invoiceWithDetails.getOrgId();

		for (final InvoiceDetailItem invoiceDetailItem : invoiceWithDetails.getDetailItems())
		{
			createDetailItem(orgId, invoiceWithDetails.getInvoiceCandidateId(), invoiceDetailItem);
		}
	}

	private void createDetailItem(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			@NonNull final InvoiceDetailItem invoiceDetailItem)
	{
		final I_C_Invoice_Detail recordToSave = syncToRecord(orgId, invoiceDetailItem);

		recordToSave.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		InterfaceWrapperHelper.saveRecord(recordToSave);
	}

	@NonNull
	private I_C_Invoice_Detail syncToRecord(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceDetailItem invoiceDetailItem)
	{
		final I_C_Invoice_Detail recordToUpdate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class);
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		recordToUpdate.setAD_Org_ID(orgId.getRepoId());
		recordToUpdate.setLabel(invoiceDetailItem.getLabel());
		recordToUpdate.setDescription(invoiceDetailItem.getDescription());
		final Quantity qty = invoiceDetailItem.getQty();
		if (qty != null)
		{
			recordToUpdate.setC_UOM_ID(qty.getUomId().getRepoId());
			recordToUpdate.setQty(qty.toBigDecimal());
		}
		recordToUpdate.setDate(TimeUtil.asTimestamp(invoiceDetailItem.getDate(), timeZone));
		return recordToUpdate;
	}
}
