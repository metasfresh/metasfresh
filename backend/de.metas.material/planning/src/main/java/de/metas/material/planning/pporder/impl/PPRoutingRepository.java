package de.metas.material.planning.pporder.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPOrderTargetPlanningStatus;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingActivity;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingActivityTemplateId;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.material.planning.pporder.PPRoutingChangeRequest;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.material.planning.pporder.PPRoutingProduct;
import de.metas.material.planning.pporder.PPRoutingProductId;
import de.metas.material.planning.pporder.PPRoutingType;
import de.metas.material.planning.pporder.UserInstructions;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.WFDurationUnit;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_WF_Node_Asset;
import org.eevolution.model.I_PP_WF_Node_Product;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * metasfresh-material-planning
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

public class PPRoutingRepository implements IPPRoutingRepository
{
	private static final Logger logger = LogManager.getLogger(PPRoutingRepository.class);
	public final IProductBL productBL = Services.get(IProductBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	private static final AdMessageKey MSG_AD_Workflow_Missing_Node = AdMessageKey.of("AD_Workflow_StartNode_NotSet");

	private final CCache<PPRoutingId, PPRouting> routingsById = CCache.<PPRoutingId, PPRouting>builder()
			.tableName(I_AD_Workflow.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_Node.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_NodeNext.Table_Name)
			.additionalTableNameToResetFor(I_PP_WF_Node_Asset.Table_Name)
			.additionalTableNameToResetFor(I_PP_WF_Node_Product.Table_Name)
			.build();

	@Override
	public PPRouting getById(@NonNull final PPRoutingId routingId)
	{
		return routingsById.getOrLoad(routingId, this::retrieveById);
	}

	private PPRouting retrieveById(@NonNull final PPRoutingId routingId)
	{
		final I_AD_Workflow routingRecord = loadOutOfTrx(routingId, I_AD_Workflow.class);
		return toRouting(routingRecord);
	}

	private PPRouting toRouting(final I_AD_Workflow routingRecord)
	{
		final PPRoutingId routingId = PPRoutingId.ofRepoId(routingRecord.getAD_Workflow_ID());
		final WFDurationUnit durationUnit = extractDurationUnit(routingRecord);
		final Duration duration = Duration.of(routingRecord.getDuration(), durationUnit.getTemporalUnit());
		final BigDecimal qtyPerBatch = extractQtyPerBatch(routingRecord);

		final List<I_AD_WF_Node> activityRecords = retrieveNodes(routingRecord);
		final Set<PPRoutingActivityId> activityIds = activityRecords.stream().map(r -> PPRoutingActivityId.ofRepoId(routingId, r.getAD_WF_Node_ID())).collect(ImmutableSet.toImmutableSet());
		final List<I_PP_WF_Node_Product> productRecords = retrieveProducts(activityIds);
		final ImmutableSetMultimap<PPRoutingActivityId, PPRoutingActivityId> nextActivityIdByActivityId = retrieveNextActivityIdsIndexedByActivityId(activityIds);

		final ImmutableList<PPRoutingActivity> activities = activityRecords
				.stream()
				.map(activityRecord -> toRoutingActivity(activityRecord, durationUnit, qtyPerBatch, nextActivityIdByActivityId))
				.collect(ImmutableList.toImmutableList());

		final Map<Integer, PPRoutingActivityId> nodeIdToActivityIds = activityRecords
				.stream()
				.collect(Collectors.toMap(I_AD_WF_Node::getAD_WF_Node_ID, r -> PPRoutingActivityId.ofRepoId(routingId, r.getAD_WF_Node_ID())));
		final ImmutableList<PPRoutingProduct> products = productRecords
				.stream()
				.map(productRecord -> toRoutingProduct(productRecord, nodeIdToActivityIds))
				.collect(ImmutableList.toImmutableList());

		final int wfNodeId = routingRecord.getAD_WF_Node_ID();

		if (wfNodeId <= 0)
		{
			throw new AdempiereException(MSG_AD_Workflow_Missing_Node, routingRecord.getName());
		}
		final PPRoutingActivityId firstActivityId = PPRoutingActivityId.ofRepoId(routingId, wfNodeId);

		return PPRouting.builder()
				.id(routingId)
				.valid(routingRecord.isValid())
				.validDates(TimeUtil.toInstantsRange(routingRecord.getValidFrom(), routingRecord.getValidTo()))
				.code(routingRecord.getValue())
				.durationUnit(durationUnit)
				.duration(duration)
				.qtyPerBatch(qtyPerBatch)
				.yield(Percent.of(routingRecord.getYield()))
				.userInChargeId(UserId.ofRepoIdOrNull(routingRecord.getAD_User_InCharge_ID()))
				.firstActivityId(firstActivityId)
				.activities(activities)
				.products(products)
				.build();
	}

	private PPRoutingProduct toRoutingProduct(final I_PP_WF_Node_Product productRecord, final Map<Integer, PPRoutingActivityId> nodeIdToActivityIds)
	{
		return PPRoutingProduct.builder()
				.id(PPRoutingProductId.ofRepoId(productRecord.getPP_WF_Node_Product_ID()))
				.activityId(nodeIdToActivityIds.get(productRecord.getAD_WF_Node_ID()))
				.productId(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.qty(Quantity.of(productRecord.getQty(), productBL.getStockUOM(productRecord.getM_Product_ID())))
				.subcontracting(productRecord.isSubcontracting())
				.seqNo(productRecord.getSeqNo())
				.specification(productRecord.getSpecification())
				.build();
	}

	private static WFDurationUnit extractDurationUnit(final I_AD_Workflow routingRecord)
	{
		return WFDurationUnit.ofCode(Objects.requireNonNull(routingRecord.getDurationUnit()));
	}

	private static BigDecimal extractQtyPerBatch(final I_AD_Workflow routingRecord)
	{
		final BigDecimal qtyPerBatch = routingRecord.getQtyBatchSize();
		if (qtyPerBatch.signum() <= 0)
		{
			logger.warn("Routing {} has invalid qtyPerBatch={}. Considering it ONE.", routingRecord, qtyPerBatch);
			return BigDecimal.ONE;
		}
		else
		{
			return qtyPerBatch;
		}
	}

	private PPRoutingActivity toRoutingActivity(
			final I_AD_WF_Node activityRecord,
			final WFDurationUnit durationUnit,
			final BigDecimal qtyPerBatch,
			final ImmutableSetMultimap<PPRoutingActivityId, PPRoutingActivityId> nextActivityIdsByActivityId)
	{
		final PPRoutingId routingId = PPRoutingId.ofRepoId(activityRecord.getAD_Workflow_ID());
		final PPRoutingActivityId activityId = PPRoutingActivityId.ofRepoId(routingId, activityRecord.getAD_WF_Node_ID());

		final ImmutableSet<PPRoutingActivityId> nextActivityIds = nextActivityIdsByActivityId.get(activityId);

		return PPRoutingActivity.builder()
				.id(activityId)
				.type(PPRoutingActivityType.ofNullableCode(activityRecord.getPP_Activity_Type()).orElse(PPRoutingActivityType.WorkReport))
				.code(activityRecord.getValue())
				.name(activityRecord.getName())
				.validDates(TimeUtil.toInstantsRange(activityRecord.getValidFrom(), activityRecord.getValidTo()))
				//
				.resourceId(ResourceId.ofRepoId(activityRecord.getS_Resource_ID()))
				//
				.durationUnit(durationUnit)
				.queuingTime(Duration.of(activityRecord.getQueuingTime(), durationUnit.getTemporalUnit()))
				.setupTime(Duration.of(activityRecord.getSetupTime(), durationUnit.getTemporalUnit()))
				.waitingTime(Duration.of(activityRecord.getWaitingTime(), durationUnit.getTemporalUnit()))
				.movingTime(Duration.of(activityRecord.getMovingTime(), durationUnit.getTemporalUnit()))
				//
				.durationPerOneUnit(Duration.of(activityRecord.getDuration(), durationUnit.getTemporalUnit()))
				//
				.overlapUnits(activityRecord.getOverlapUnits())
				.unitsPerCycle(activityRecord.getUnitsCycles().intValueExact())
				.qtyPerBatch(qtyPerBatch)
				//
				.yield(Percent.of(activityRecord.getYield()))
				//
				.subcontracting(activityRecord.isSubcontracting())
				.subcontractingVendorId(BPartnerId.ofRepoIdOrNull(activityRecord.getC_BPartner_ID()))
				//
				.milestone(activityRecord.isMilestone())
				.alwaysAvailableToUser(PPAlwaysAvailableToUser.ofNullableCode(activityRecord.getPP_AlwaysAvailableToUser()))
				.userInstructions(UserInstructions.ofNullableString(activityRecord.getPP_UserInstructions()))
				.targetPlanningStatus(PPOrderTargetPlanningStatus.ofNullableCode(activityRecord.getTargetPlanningStatus()))
				//
				.nextActivityIds(nextActivityIds)
				//
				.activityTemplateId(PPRoutingActivityTemplateId.ofRepoIdOrNull(activityRecord.getAD_WF_Node_Template_ID()))
				//
				.build();
	}

	private ImmutableSetMultimap<PPRoutingActivityId, PPRoutingActivityId> retrieveNextActivityIdsIndexedByActivityId(final Set<PPRoutingActivityId> activityIds)
	{
		final ImmutableMap<Integer, PPRoutingActivityId> activityIdsByRepoId = Maps.uniqueIndex(activityIds, PPRoutingActivityId::getRepoId);

		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_WF_NodeNext.class)
				.addInArrayFilter(I_AD_WF_NodeNext.COLUMNNAME_AD_WF_Node_ID, activityIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						nodeNextRecord -> activityIdsByRepoId.get(nodeNextRecord.getAD_WF_Node_ID()),
						nodeNextRecord -> activityIdsByRepoId.get(nodeNextRecord.getAD_WF_Next_ID())));
	}

	@Override
	public PPRoutingId getRoutingIdByProductId(@NonNull final ProductId productId)
	{
		final I_M_Product product = productDAO.getById(productId);
		final String productValue = product.getValue();
		final ClientId clientId = ClientId.ofRepoId(product.getAD_Client_ID());
		return retrievePPRoutingIdByProductValue(productValue, clientId);
	}

	@Cached(cacheName = I_AD_Workflow.Table_Name + "#by#" + I_AD_Workflow.COLUMNNAME_Value)
	@Nullable
	PPRoutingId retrievePPRoutingIdByProductValue(@NonNull final String productValue, @NonNull final ClientId clientId)
	{
		Check.assumeNotEmpty(productValue, "productValue is not empty");

		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_Workflow.class)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_Value, productValue)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_AD_Client_ID, clientId)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_WorkflowType, PPRoutingType.Manufacturing)
				// .addOnlyContextClient(ctx)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(PPRoutingId::ofRepoIdOrNull);
	}

	@Override
	public void changeRouting(@NonNull final PPRoutingChangeRequest changeRequest)
	{
		//
		// Load in current trx
		final PPRoutingId routingId = changeRequest.getRoutingId();
		final I_AD_Workflow routingRecord = load(routingId, I_AD_Workflow.class);
		final List<I_AD_WF_Node> routingActivityRecords = retrieveNodes(routingRecord);

		//
		// Apply changes
		{
			if (changeRequest.getYield() != null)
			{
				routingRecord.setYield(changeRequest.getYield().toInt());
			}

			final WFDurationUnit durationUnit = extractDurationUnit(routingRecord);
			if (changeRequest.getQueuingTime() != null)
			{
				routingRecord.setQueuingTime(durationUnit.toInt(changeRequest.getQueuingTime()));
			}
			if (changeRequest.getSetupTime() != null)
			{
				routingRecord.setSetupTime(durationUnit.toInt(changeRequest.getSetupTime()));
			}
			if (changeRequest.getDurationPerOneUnit() != null)
			{
				routingRecord.setDuration(durationUnit.toInt(changeRequest.getDurationPerOneUnit()));
			}
			if (changeRequest.getWaitingTime() != null)
			{
				routingRecord.setWaitingTime(durationUnit.toInt(changeRequest.getWaitingTime()));
			}
			if (changeRequest.getMovingTime() != null)
			{
				routingRecord.setMovingTime(durationUnit.toInt(changeRequest.getMovingTime()));
			}

			//
			// Update routing and activity costs
			routingRecord.setCost(changeRequest.getCost() != null ? changeRequest.getCost() : BigDecimal.ZERO);

			for (final I_AD_WF_Node routingActivityRecord : routingActivityRecords)
			{
				final PPRoutingActivityId activityId = PPRoutingActivityId.ofRepoId(routingId, routingActivityRecord.getAD_WF_Node_ID());
				final BigDecimal cost = changeRequest.getActivityCostOrNull(activityId);
				routingActivityRecord.setCost(cost != null ? cost : BigDecimal.ZERO);
			}
		}

		//
		// Save all
		routingActivityRecords.forEach(InterfaceWrapperHelper::saveRecord);
		save(routingRecord);
	}

	@Override
	public boolean nodesAlreadyExistInWorkflow(@NonNull final PPRoutingActivityId excludeActivityId)
	{
		return queryBL
				.createQueryBuilder(I_AD_WF_Node.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, excludeActivityId.getRoutingId())
				.addNotEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID, excludeActivityId.getRepoId())
				.create()
				.anyMatch();
	}

	private List<I_AD_WF_Node> retrieveNodes(@NonNull final I_AD_Workflow routingRecord)
	{
		final PPRoutingId routingId = PPRoutingId.ofRepoId(routingRecord.getAD_Workflow_ID());

		return queryBL
				.createQueryBuilder(I_AD_WF_Node.class, routingRecord)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, routingId)
				.addNotNull(I_AD_WF_Node.COLUMNNAME_S_Resource_ID) // in the context of production and product-planning, we can't work with a resource-less AD_WF_Node
				.orderBy(I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID)
				.create()
				.list();
	}

	private List<I_PP_WF_Node_Product> retrieveProducts(final Set<PPRoutingActivityId> activityIds)
	{
		if (Check.isEmpty(activityIds))
		{
			return Collections.emptyList();
		}
		return queryBL
				.createQueryBuilder(I_PP_WF_Node_Product.class)
				.addOnlyActiveRecordsFilter()
				//.addOnlyContextClient() // not needed
				.addInArrayFilter(I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID, activityIds)
				.create()
				.list();
	}

	@Override
	public Optional<PPRoutingId> getDefaultRoutingIdByType(@NonNull final PPRoutingType type)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_Workflow.class)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_WorkflowType, type)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_IsDefault, true)
				.create()
				.firstIdOnlyOptional(PPRoutingId::ofRepoIdOrNull);

	}

	@Override
	public void setFirstNodeToWorkflow(@NonNull final PPRoutingActivityId ppRoutingActivityId)
	{
		final I_AD_Workflow workflow = load(ppRoutingActivityId.getRoutingId(), I_AD_Workflow.class);

		workflow.setAD_WF_Node_ID(ppRoutingActivityId.getRepoId());

		save(workflow);
	}

	@Override
	public SeqNo getActivityProductNextSeqNo(@NonNull final PPRoutingActivityId activityId)
	{
		final int lastSeqNoInt = queryBL
				.createQueryBuilder(I_PP_WF_Node_Product.class)
				//.addOnlyActiveRecordsFilter() // let's include non active ones too
				.addEqualsFilter(I_PP_WF_Node_Product.COLUMNNAME_AD_WF_Node_ID, activityId)
				.create()
				.maxInt(I_PP_WF_Node_Product.COLUMNNAME_SeqNo);

		return SeqNo.ofInt(Math.max(lastSeqNoInt, 0)).next();
	}
}
