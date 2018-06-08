package de.metas.material.dispo.commons.repository.repohelpers;

import static de.metas.material.dispo.commons.repository.repohelpers.RepositoryCommons.retrieveSingleCandidateDetail;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.dispo.model.I_MD_Candidate_Purchase_Detail;
import lombok.NonNull;

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

/** Intended to be used only be the "main" repository code */
@Service
public class PurchaseDetailRepoHelper
{
	public PurchaseDetail getSingleForCandidateRecordOrNull(@NonNull final I_MD_Candidate candidateRecord)
	{
		final I_MD_Candidate_Purchase_Detail purchaseDetailRecord = RepositoryCommons
				.createCandidateDetailQueryBuilder(candidateRecord, I_MD_Candidate_Purchase_Detail.class)
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
				.inoutLineRepoId(purchaseDetailRecord.getM_InOutLine_ID())
				.orderLineRepoId(purchaseDetailRecord.getC_OrderLinePO_ID())
				.orderedQty(purchaseDetailRecord.getQtyOrdered())
				.plannedQty(purchaseDetailRecord.getPlannedQty())
				.purchaseCandidateRepoId(purchaseDetailRecord.getC_PurchaseCandidate_ID())
				.productPlanningRepoId(purchaseDetailRecord.getPP_Product_Planning_ID())
				.receiptScheduleRepoId(purchaseDetailRecord.getM_ReceiptSchedule_ID())
				.vendorRepoId(purchaseDetailRecord.getC_BPartner_Vendor_ID())
				.advised(Flag.of(purchaseDetailRecord.isAdvised()))
				.build();
	}

	public void save(
			@Nullable final PurchaseDetail purchaseDetail,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		if (purchaseDetail == null)
		{
			return;
		}

		I_MD_Candidate_Purchase_Detail recordToUpdate = retrieveSingleCandidateDetail(
				candidateRecord,
				I_MD_Candidate_Purchase_Detail.class);
		if (recordToUpdate == null)
		{
			recordToUpdate = newInstance(I_MD_Candidate_Purchase_Detail.class, candidateRecord);
			recordToUpdate.setMD_Candidate_ID(candidateRecord.getMD_Candidate_ID());
		}

		if (purchaseDetail.getAdvised().isUpdateExistingRecord())
		{
			recordToUpdate.setIsAdvised(purchaseDetail.getAdvised().toBoolean());
		}

		recordToUpdate.setC_BPartner_Vendor_ID(purchaseDetail.getVendorRepoId());
		recordToUpdate.setC_OrderLinePO_ID(purchaseDetail.getOrderLineRepoId());
		recordToUpdate.setC_PurchaseCandidate_ID(purchaseDetail.getPurchaseCandidateRepoId());
		recordToUpdate.setM_InOutLine_ID(purchaseDetail.getInoutLineRepoId());
		recordToUpdate.setM_ReceiptSchedule_ID(purchaseDetail.getReceiptScheduleRepoId());
		recordToUpdate.setPlannedQty(purchaseDetail.getPlannedQty());
		recordToUpdate.setPP_Product_Planning_ID(purchaseDetail.getProductPlanningRepoId());
		recordToUpdate.setQtyOrdered(purchaseDetail.getOrderedQty());

		saveRecord(recordToUpdate);
	}
}
