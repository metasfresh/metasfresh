/*
 * #%L
 * metasfresh-material-dispo-service
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

package de.metas.material.dispo.service.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.stockcandidate.MaterialCandidateChangedEvent;
import de.metas.material.event.stockcandidate.StockCandidateChangedEvent;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static de.metas.material.dispo.model.X_MD_Candidate.MD_CANDIDATE_STATUS_Simulated;

@Interceptor(I_MD_Candidate.class)
@Component
public class MD_Candidate
{
	private final PostMaterialEventService materialEventService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public MD_Candidate(
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.materialEventService = materialEventService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_DELETE })
	public void fireStockChangedEvent(
			@NonNull final I_MD_Candidate candidate,
			@NonNull final ModelChangeType timingType)
	{
		if (!CandidateType.STOCK.getCode().equals(candidate.getMD_Candidate_Type()) 
				|| MD_CANDIDATE_STATUS_Simulated.equals(candidate.getMD_Candidate_Status()))
		{
			return;
		}

		final I_MD_Candidate oldCandidateRecord = InterfaceWrapperHelper.createOld(candidate, I_MD_Candidate.class);

		if (isUpdateOldStockRequired(timingType, oldCandidateRecord, candidate))
		{
			final CandidatesQuery findPreviousStockQuery = buildCandidateStockQueryForReplacingOld(oldCandidateRecord);

			final Candidate lastMatchingStockForOldMaterialDescriptor = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(findPreviousStockQuery);

			final EventDescriptor eventDescriptor = EventDescriptor.ofClientAndOrg(oldCandidateRecord.getAD_Client_ID(), oldCandidateRecord.getAD_Org_ID());

			final MaterialDescriptor materialDescriptor = getMaterialDescriptorBuilder(oldCandidateRecord)
					.quantity(lastMatchingStockForOldMaterialDescriptor != null ? lastMatchingStockForOldMaterialDescriptor.getQuantity() : BigDecimal.ZERO)
					.build();

			final StockCandidateChangedEvent stockCandidateChangedEvent = StockCandidateChangedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.materialDescriptor(materialDescriptor)
					.build();

			materialEventService.enqueueEventAfterNextCommit(stockCandidateChangedEvent);
		}
		
		if (isUpdateCurrentStockRequired(timingType))
		{
			final EventDescriptor eventDescriptor = EventDescriptor.ofClientAndOrg(candidate.getAD_Client_ID(), candidate.getAD_Org_ID());

			final StockCandidateChangedEvent stockCandidateChangedEvent = StockCandidateChangedEvent.builder()
					.eventDescriptor(eventDescriptor)
					.materialDescriptor(getMaterialDescriptor(candidate))
					.build();

			materialEventService.enqueueEventAfterNextCommit(stockCandidateChangedEvent);	
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_NEW }, ifColumnsChanged = I_MD_Candidate.COLUMNNAME_QtyFulfilled)
	public void fireQtyRequiredFulfilled(@NonNull final I_MD_Candidate candidate)
	{
		if (MD_CANDIDATE_STATUS_Simulated.equals(candidate.getMD_Candidate_Status()))
		{
			return;
		}

		final CandidateType candidateType = CandidateType.ofCode(candidate.getMD_Candidate_Type());

		if (!candidateType.isIncreasingStock())
		{
			return;
		}

		final EventDescriptor eventDescriptor = EventDescriptor.ofClientAndOrg(candidate.getAD_Client_ID(), candidate.getAD_Org_ID());

		final boolean isNew = InterfaceWrapperHelper.isNew(candidate);

		final BigDecimal qtyFulfilledDelta;
		if (isNew)
		{
			qtyFulfilledDelta = candidate.getQtyFulfilled();
		}
		else
		{
			final I_MD_Candidate oldCandidate = InterfaceWrapperHelper.createOld(candidate, I_MD_Candidate.class);

			final BigDecimal oldFulfilledData = oldCandidate.getQtyFulfilled();
			final BigDecimal newFulfilledData = candidate.getQtyFulfilled();
			qtyFulfilledDelta = newFulfilledData.subtract(oldFulfilledData);
		}

		if (qtyFulfilledDelta.signum() == 0)
		{
			return;
		}

		final MaterialCandidateChangedEvent materialCandidateChangedEvent = MaterialCandidateChangedEvent.builder()
				.eventDescriptor(eventDescriptor)
				.materialDescriptor(getMaterialDescriptor(candidate))
				.qtyFulfilledDelta(qtyFulfilledDelta)
				.build();

		materialEventService.enqueueEventAfterNextCommit(materialCandidateChangedEvent);
	}

	@NonNull
	private static MaterialDescriptor getMaterialDescriptor(@NonNull final I_MD_Candidate candidate)
	{
		return getMaterialDescriptorBuilder(candidate)
				.quantity(candidate.getQty())
				.build();
	}

	@NonNull
	private static MaterialDescriptor.MaterialDescriptorBuilder getMaterialDescriptorBuilder(@NonNull final I_MD_Candidate candidate)
	{
		final ProductDescriptor productDescriptor = ProductDescriptor.forProductAndAttributes(candidate.getM_Product_ID(),
																							  AttributesKey.ofString(candidate.getStorageAttributesKey()));

		return MaterialDescriptor.builder()
				.date(TimeUtil.asInstant(candidate.getDateProjected()))
				.productDescriptor(productDescriptor)
				.warehouseId(WarehouseId.ofRepoId(candidate.getM_Warehouse_ID()));
	}

	@NonNull
	private static CandidatesQuery buildCandidateStockQueryForReplacingOld(@NonNull final I_MD_Candidate candidateRecord)
	{
		return CandidatesQuery.builder()
				.materialDescriptorQuery(buildMaterialDescriptorQueryForReplacingOld(candidateRecord))
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.build();
	}

	@NonNull
	private static MaterialDescriptorQuery buildMaterialDescriptorQueryForReplacingOld(@NonNull final I_MD_Candidate oldCandidateRecord)
	{
		final Instant endOfTheDay = oldCandidateRecord.getDateProjected()
				.toInstant()
				.plus(1, ChronoUnit.DAYS)
				.truncatedTo(ChronoUnit.DAYS);
		
		return MaterialDescriptorQuery
				.builder()
				.warehouseId(WarehouseId.ofRepoId(oldCandidateRecord.getM_Warehouse_ID()))
				.productId(oldCandidateRecord.getM_Product_ID())
				.storageAttributesKey(AttributesKey.ofString(oldCandidateRecord.getStorageAttributesKey()))
				.customer(BPartnerClassifier.specificOrAny(BPartnerId.ofRepoIdOrNull(oldCandidateRecord.getC_BPartner_Customer_ID())))
				.customerIdOperator(MaterialDescriptorQuery.CustomerIdOperator.GIVEN_ID_ONLY)
				.timeRangeEnd(DateAndSeqNo.builder()
									  .date(endOfTheDay)
									  .operator(DateAndSeqNo.Operator.EXCLUSIVE)
									  .build())
				.build();
	}

	private static boolean isMaterialDescriptorChanged(
			@NonNull final I_MD_Candidate oldCandidateRecord,
			@NonNull final I_MD_Candidate candidateRecord)
	{
		return !candidateRecord.getDateProjected().equals(oldCandidateRecord.getDateProjected())
				|| !candidateRecord.getStorageAttributesKey().equals(oldCandidateRecord.getStorageAttributesKey())
				|| oldCandidateRecord.getM_Warehouse_ID() != candidateRecord.getM_Warehouse_ID()
				|| oldCandidateRecord.getM_Product_ID() != candidateRecord.getM_Product_ID();
	}
	
	private static boolean isUpdateOldStockRequired(
			@NonNull final ModelChangeType timingType,
			@NonNull final I_MD_Candidate oldCandidateRecord,
			@NonNull final I_MD_Candidate candidate)
	{
		return timingType.isDelete() || (timingType.isChange() && isMaterialDescriptorChanged(oldCandidateRecord, candidate));
	}

	private static boolean isUpdateCurrentStockRequired(@NonNull final ModelChangeType timingType)
	{
		return !timingType.isDelete();
	}
}
