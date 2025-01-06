/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.detail;

import com.google.common.collect.ImmutableMap;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.InvoiceId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice_Detail;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.time.ZoneId;
import java.util.List;

@Repository
public class InvoiceWithDetailsRepository
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	/**
	 * Assumes that the given invoice and its lines exist. only saves the respective {@link org.compiere.model.I_C_Invoice_Detail} records are persisted
	 */
	public void save(@NonNull final InvoiceWithDetails invoiceWithDetails)
	{

		final ImmutableMap<StagingRecordKey, I_C_Invoice_Detail> detailRecords = retrieveDetailRecords(invoiceWithDetails.getId());

		final OrgId orgId = invoiceWithDetails.getOrgId();

		for (InvoiceDetailItem invoiceDetailItem : invoiceWithDetails.getDetailItems())
		{
			createOrUpdateDetailItem(orgId, invoiceWithDetails.getId(), invoiceDetailItem, detailRecords);
		}
		for (InvoiceLineWithDetails line : invoiceWithDetails.getLines())
		{
			for (InvoiceDetailItem invoiceDetailItem : line.getDetailItems())
			{
				createOrUpdateDetailItem(orgId, line.getId(), invoiceDetailItem, detailRecords);
			}
		}
	}

	private ImmutableMap<StagingRecordKey, I_C_Invoice_Detail> retrieveDetailRecords(@NonNull final InvoiceId invoiceId)
	{
		final List<I_C_Invoice_Detail> detailRecords = getInvoiceDetailsListForInvoiceId(invoiceId);

		final ImmutableMap.Builder<StagingRecordKey, I_C_Invoice_Detail> invoiceId2Record = ImmutableMap.builder();

		for (final I_C_Invoice_Detail detailRecord : detailRecords)
		{
			final StagingRecordKey key = StagingRecordKey.forRecordOrNull(detailRecord);
			if (key == null)
			{
				continue;
			}
			invoiceId2Record.put(key, detailRecord);
		}
		return invoiceId2Record.build();
	}

	private void createOrUpdateDetailItem(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceId invoiceId,
			@NonNull final InvoiceDetailItem invoiceDetailItem,
			@NonNull final ImmutableMap<StagingRecordKey, I_C_Invoice_Detail> detailRecords)
	{
		final I_C_Invoice_Detail existingRecordOrNull = detailRecords.get(StagingRecordKey.forItemOrNull(invoiceId, invoiceDetailItem));
		final I_C_Invoice_Detail recordToSave = syncToRecord(orgId, invoiceDetailItem, existingRecordOrNull);

		recordToSave.setC_Invoice_ID(invoiceId.getRepoId());
		InterfaceWrapperHelper.saveRecord(recordToSave);
	}

	private void createOrUpdateDetailItem(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final InvoiceDetailItem invoiceDetailItem,
			@NonNull final ImmutableMap<StagingRecordKey, I_C_Invoice_Detail> detailRecords)
	{
		final I_C_Invoice_Detail existingRecordOrNull = detailRecords.get(StagingRecordKey.forItemOrNull(invoiceAndLineId, invoiceDetailItem));

		final I_C_Invoice_Detail recordToSave = syncToRecord(orgId, invoiceDetailItem, existingRecordOrNull);

		recordToSave.setC_Invoice_ID(invoiceAndLineId.getInvoiceId().getRepoId());
		recordToSave.setC_InvoiceLine_ID(invoiceAndLineId.getRepoId());
		InterfaceWrapperHelper.saveRecord(recordToSave);
	}

	@NonNull
	private I_C_Invoice_Detail syncToRecord(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceDetailItem invoiceDetailItem,
			@Nullable final I_C_Invoice_Detail existingInvoiceDetailRecord)
	{
		final I_C_Invoice_Detail recordToUpdate;
		if (existingInvoiceDetailRecord == null)
		{
			recordToUpdate = InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class);
		}
		else
		{
			recordToUpdate = existingInvoiceDetailRecord;
		}
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

	@Value
	private static class StagingRecordKey
	{
		@Nullable
		InvoiceId invoiceId;
		@Nullable
		InvoiceAndLineId invoiceAndLineId;
		@NonNull String label;

		public static StagingRecordKey forRecordOrNull(@NonNull final I_C_Invoice_Detail detailRecord)
		{
			final InvoiceAndLineId invoiceAndLineId = InvoiceAndLineId.ofRepoIdOrNull(detailRecord.getC_Invoice_ID(), detailRecord.getC_InvoiceLine_ID());
			if (invoiceAndLineId != null)
			{
				return new StagingRecordKey(null, invoiceAndLineId, detailRecord.getLabel());
			}

			final InvoiceId invoiceId = InvoiceId.ofRepoIdOrNull(detailRecord.getC_Invoice_ID());
			if (invoiceId != null)
			{
				return new StagingRecordKey(invoiceId, null, detailRecord.getLabel());
			}
			return null;
		}

		public static StagingRecordKey forItemOrNull(@NonNull final InvoiceId invoiceId, @NonNull final InvoiceDetailItem detailItem)
		{
			return new StagingRecordKey(invoiceId, null, detailItem.getLabel());
		}

		public static StagingRecordKey forItemOrNull(@NonNull final InvoiceAndLineId invoiceAndLineId, @NonNull final InvoiceDetailItem detailItem)
		{
			return new StagingRecordKey(null, invoiceAndLineId, detailItem.getLabel());
		}
	}

	// also used by InvoiceWithDetailsService
	List<I_C_Invoice_Detail> getInvoiceDetailsListForInvoiceId(@NonNull final InvoiceId invoiceId)
	{
		return queryBL
				.createQueryBuilder(I_C_Invoice_Detail.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.list();
	}
}
