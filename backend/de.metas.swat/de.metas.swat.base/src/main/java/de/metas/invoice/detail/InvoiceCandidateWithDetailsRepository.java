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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
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
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.saveAll;

@Repository
public class InvoiceCandidateWithDetailsRepository
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void save(@NonNull final InvoiceCandidateWithDetails invoiceWithDetails)
	{
		final List<I_C_Invoice_Detail> existingInvoiceDetails = loadByInvoiceCandidateId(invoiceWithDetails.getInvoiceCandidateId());
		final ImmutableMap<String, I_C_Invoice_Detail> labelToExistingDetail = Maps.uniqueIndex(existingInvoiceDetails, I_C_Invoice_Detail::getLabel);

		final Set<I_C_Invoice_Detail> detailsToPersist = invoiceWithDetails.getDetailItems()
				.stream()
				.map(invoiceDetailItem -> createOrUpdateDetailItem(invoiceWithDetails.getInvoiceCandidateId(), invoiceWithDetails.getInvoiceLineId(), invoiceDetailItem, labelToExistingDetail))
				.collect(Collectors.toSet());
		saveAll(detailsToPersist);
	}

	private I_C_Invoice_Detail createOrUpdateDetailItem(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			@Nullable final InvoiceLineId invoiceAndLineId,
			@NonNull final InvoiceDetailItem invoiceDetailItem,
			@NonNull final ImmutableMap<String, I_C_Invoice_Detail> labelToExistingDetail)
	{
		final OrgId orgId = invoiceDetailItem.getOrgId();
		final I_C_Invoice_Detail recordToSave = syncToRecord(orgId, invoiceDetailItem, labelToExistingDetail);

		recordToSave.setC_Invoice_Candidate_ID(invoiceCandidateId.getRepoId());
		recordToSave.setC_InvoiceLine_ID(InvoiceLineId.toRepoId(invoiceAndLineId));
		recordToSave.setAD_Org_ID(orgId.getRepoId());
		return recordToSave;
	}

	@NonNull
	private I_C_Invoice_Detail syncToRecord(
			@NonNull final OrgId orgId,
			@NonNull final InvoiceDetailItem invoiceDetailItem,
			@NonNull final ImmutableMap<String, I_C_Invoice_Detail> labelToExistingDetail)
	{
		final I_C_Invoice_Detail recordToUpdate = CoalesceUtil.coalesceSuppliersNotNull(() -> labelToExistingDetail.get(invoiceDetailItem.getLabel()),
				() -> InterfaceWrapperHelper.newInstance(I_C_Invoice_Detail.class));
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

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

	private List<I_C_Invoice_Detail> loadByInvoiceCandidateId(final @NonNull InvoiceCandidateId invoiceCandidateId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice_Detail.class)
				.addEqualsFilter(I_C_Invoice_Detail.COLUMNNAME_C_Invoice_Candidate_ID, invoiceCandidateId)
				.create()
				.list();
	}

}
