package de.metas.material.dispo.commons.repository.repohelpers;

import com.google.common.annotations.VisibleForTesting;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.query.PurchaseDetailsQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * Intended to be used only be the "main" repository code
 */
public final class PurchaseDetailRepoHelper
{
	private PurchaseDetailRepoHelper()
	{
	}

	public static PurchaseDetail getSingleForCandidateRecordOrNull(@NonNull final CandidateId candidateId)
	{
		final I_MD_Candidate_Purchase_Detail purchaseDetailRecord = RepositoryCommons
				.createCandidateDetailQueryBuilder(candidateId, I_MD_Candidate_Purchase_Detail.class)
				.firstOnly(I_MD_Candidate_Purchase_Detail.class);

		return ofRecord(purchaseDetailRecord);
	}

	@VisibleForTesting
	static PurchaseDetail ofRecord(@Nullable final I_MD_Candidate_Purchase_Detail purchaseDetailRecord)
	{
		if (purchaseDetailRecord == null)
		{
			return null;
		}

		return PurchaseDetail.builder()
				.orderLineRepoId(purchaseDetailRecord.getC_OrderLinePO_ID())
				.orderedQty(purchaseDetailRecord.getQtyOrdered())
				.qty(purchaseDetailRecord.getPlannedQty())
				.purchaseCandidateRepoId(purchaseDetailRecord.getC_PurchaseCandidate_ID())
				.productPlanningRepoId(purchaseDetailRecord.getPP_Product_Planning_ID())
				.receiptScheduleRepoId(purchaseDetailRecord.getM_ReceiptSchedule_ID())
				.vendorRepoId(purchaseDetailRecord.getC_BPartner_Vendor_ID())
				.advised(Flag.of(purchaseDetailRecord.isAdvised()))
				.build();
	}

	public static void save(
			@Nullable final PurchaseDetail purchaseDetail,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		if (purchaseDetail == null)
		{
			return;
		}

		final CandidateId candidateId = CandidateId.ofRepoId(candidateRecord.getMD_Candidate_ID());
		I_MD_Candidate_Purchase_Detail recordToUpdate = RepositoryCommons.retrieveSingleCandidateDetail(candidateId, I_MD_Candidate_Purchase_Detail.class);
		if (recordToUpdate == null)
		{
			recordToUpdate = newInstance(I_MD_Candidate_Purchase_Detail.class, candidateRecord);
			recordToUpdate.setMD_Candidate_ID(candidateRecord.getMD_Candidate_ID());
		}

		if (purchaseDetail.getAdvised().isUpdateExistingRecord())
		{
			recordToUpdate.setIsAdvised(purchaseDetail.getAdvised().isTrue());
		}

		// don't set anything to null just because it's missing in a partial purchase detail instance
		if (purchaseDetail.getProductPlanningRepoId() > 0)
		{
			recordToUpdate.setPP_Product_Planning_ID(purchaseDetail.getProductPlanningRepoId());
		}
		if (purchaseDetail.getOrderLineRepoId() > 0)
		{
			recordToUpdate.setC_OrderLinePO_ID(purchaseDetail.getOrderLineRepoId());
		}
		if (purchaseDetail.getPurchaseCandidateRepoId() > 0)
		{
			recordToUpdate.setC_PurchaseCandidate_ID(purchaseDetail.getPurchaseCandidateRepoId());
		}
		if (purchaseDetail.getReceiptScheduleRepoId() > 0)
		{
			recordToUpdate.setM_ReceiptSchedule_ID(purchaseDetail.getReceiptScheduleRepoId());
		}
		if (purchaseDetail.getVendorRepoId() > 0)
		{
			recordToUpdate.setC_BPartner_Vendor_ID(purchaseDetail.getVendorRepoId());
		}
		if (purchaseDetail.getOrderedQty() != null)
		{
			recordToUpdate.setQtyOrdered(purchaseDetail.getOrderedQty());
		}

		recordToUpdate.setPlannedQty(purchaseDetail.getQty());

		saveRecord(recordToUpdate);
	}

	public static void addPurchaseDetailsQueryToFilter(
			@Nullable final PurchaseDetailsQuery purchaseDetailsQuery,
			@NonNull final IQueryBuilder<I_MD_Candidate> builder)
	{
		if (purchaseDetailsQuery == null)
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_MD_Candidate_Purchase_Detail> purchaseDetailSubQueryBuilder = queryBL
				.createQueryBuilder(I_MD_Candidate_Purchase_Detail.class)
				.addOnlyActiveRecordsFilter();

		// note that at least one of them is > 0.
		if (purchaseDetailsQuery.getReceiptScheduleRepoId() > 0)
		{
			purchaseDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Purchase_Detail.COLUMN_M_ReceiptSchedule_ID, purchaseDetailsQuery.getReceiptScheduleRepoId());
		}
		if (purchaseDetailsQuery.getPurchaseCandidateRepoId() > 0)
		{
			purchaseDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Purchase_Detail.COLUMN_C_PurchaseCandidate_ID, purchaseDetailsQuery.getPurchaseCandidateRepoId());
		}
		if (purchaseDetailsQuery.getProductPlanningRepoId() > 0)
		{
			purchaseDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Purchase_Detail.COLUMN_PP_Product_Planning_ID, purchaseDetailsQuery.getProductPlanningRepoId());
		}

		if (purchaseDetailsQuery.isOrderLineRepoIdMustBeNull())
		{
			purchaseDetailSubQueryBuilder.addEqualsFilter(I_MD_Candidate_Purchase_Detail.COLUMN_C_OrderLinePO_ID, null);
		}

		builder.addInSubQueryFilter(
				I_MD_Candidate.COLUMN_MD_Candidate_ID,
				I_MD_Candidate_Purchase_Detail.COLUMN_MD_Candidate_ID,
				purchaseDetailSubQueryBuilder.create());
	}
}
