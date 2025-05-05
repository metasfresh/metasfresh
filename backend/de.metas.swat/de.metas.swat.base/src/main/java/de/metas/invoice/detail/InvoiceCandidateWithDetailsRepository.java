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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class InvoiceCandidateWithDetailsRepository
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final InvoiceCandidateWithDetails invoiceWithDetails)
	{
		final List<I_C_Invoice_Detail> existingInvoiceDetails = loadByInvoiceCandidateId(invoiceWithDetails.getInvoiceCandidateId());
		final ListMultimap<String, I_C_Invoice_Detail> existingRecordsByLabel = ArrayListMultimap.create(Multimaps.index(existingInvoiceDetails, I_C_Invoice_Detail::getLabel));

		invoiceWithDetails.getDetailItems()
				.forEach(invoiceDetailItem -> createOrUpdateRecord(
						invoiceDetailItem,
						invoiceWithDetails.getInvoiceCandidateId(),
						invoiceWithDetails.getInvoiceLineId(),
						existingRecordsByLabel));
		deleteAll(existingRecordsByLabel.values());
	}

	private void createOrUpdateRecord(
			@NonNull final InvoiceDetailItem detailItem,
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			@Nullable final InvoiceLineId invoiceAndLineId,
			@NonNull final ListMultimap<String, I_C_Invoice_Detail> existingRecordsByLabel)
	{
		final List<I_C_Invoice_Detail> existingRecords = existingRecordsByLabel.removeAll(detailItem.getLabel());
		final I_C_Invoice_Detail recordToUpdate;
		final List<I_C_Invoice_Detail> recordsToDelete;
		if (existingRecords.isEmpty())
		{
			recordToUpdate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class);
			recordsToDelete = ImmutableList.of();
		}
		else
		{
			recordToUpdate = existingRecords.get(0);
			recordsToDelete = existingRecords.subList(1, existingRecords.size());
		}

		final OrgId orgId = detailItem.getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		updateRecord(recordToUpdate, detailItem, timeZone);
		recordToUpdate.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		recordToUpdate.setC_InvoiceLine_ID(InvoiceLineId.toRepoId(invoiceAndLineId));
		recordToUpdate.setAD_Org_ID(orgId.getRepoId());

		saveRecord(recordToUpdate);
		deleteAll(recordsToDelete);
	}

	private static void updateRecord(
			@NonNull I_C_Invoice_Detail record,
			@NonNull final InvoiceDetailItem from,
			@NonNull final ZoneId timeZone)
	{
		record.setLabel(from.getLabel());
		record.setDescription(from.getDescription());
		final Quantity qty = from.getQty();
		record.setC_UOM_ID(Quantity.toUomRepoId(qty));
		record.setQty(Quantity.toBigDecimal(qty));
		record.setDate(TimeUtil.asTimestamp(from.getDate(), timeZone));
	}

	private List<I_C_Invoice_Detail> loadByInvoiceCandidateId(final @NonNull InvoiceCandidateId invoiceCandidateId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Detail.class)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				.create()
				.list();
	}

}
