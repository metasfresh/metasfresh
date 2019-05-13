package de.metas.material.planning.pporder.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_WF_NodeNext;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_AD_Workflow;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_WF_Node_Asset;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.logging.LogManager;
import de.metas.material.planning.DurationUnitCodeUtils;
import de.metas.material.planning.pporder.IPPRoutingRepository;
import de.metas.material.planning.pporder.PPRouting;
import de.metas.material.planning.pporder.PPRoutingActivity;
import de.metas.material.planning.pporder.PPRoutingActivityId;
import de.metas.material.planning.pporder.PPRoutingChangeRequest;
import de.metas.material.planning.pporder.PPRoutingId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.time.DurationUtils;
import lombok.NonNull;

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

	private final CCache<PPRoutingId, PPRouting> routingsById = CCache.<PPRoutingId, PPRouting> builder()
			.tableName(I_AD_Workflow.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_Node.Table_Name)
			.additionalTableNameToResetFor(I_AD_WF_NodeNext.Table_Name)
			.additionalTableNameToResetFor(I_PP_WF_Node_Asset.Table_Name)
			// .additionalTableNameToResetFor(I_PP_WF_Node_Product.Table_Name)
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
		final TemporalUnit durationUnit = extractDurationUnit(routingRecord);
		final Duration duration = Duration.of(routingRecord.getDuration(), durationUnit);
		final BigDecimal qtyPerBatch = extractQtyPerBatch(routingRecord);

		final List<I_AD_WF_Node> activityRecords = retrieveNodes(routingRecord);
		final Set<PPRoutingActivityId> activityIds = activityRecords.stream().map(r -> PPRoutingActivityId.ofAD_WF_Node_ID(routingId, r.getAD_WF_Node_ID())).collect(ImmutableSet.toImmutableSet());
		final ImmutableSetMultimap<PPRoutingActivityId, PPRoutingActivityId> nextActivityIdByActivityId = retrieveNextActivityIdsIndexedByActivityId(activityIds);

		final ImmutableList<PPRoutingActivity> activities = activityRecords
				.stream()
				.map(activityRecord -> toRoutingActivity(activityRecord, durationUnit, qtyPerBatch, nextActivityIdByActivityId))
				.collect(ImmutableList.toImmutableList());

		final PPRoutingActivityId firstActivityId = PPRoutingActivityId.ofAD_WF_Node_ID(routingId, routingRecord.getAD_WF_Node_ID());

		return PPRouting.builder()
				.id(routingId)
				.valid(routingRecord.isValid())
				.validDates(toValidDateRange(routingRecord.getValidFrom(), routingRecord.getValidTo()))
				.code(routingRecord.getValue())
				.durationUnit(durationUnit)
				.duration(duration)
				.qtyPerBatch(qtyPerBatch)
				.yield(Percent.of(routingRecord.getYield()))
				.userInChargeId(UserId.ofRepoIdOrNull(routingRecord.getAD_User_InCharge_ID()))
				.firstActivityId(firstActivityId)
				.activities(activities)
				.build();
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

	private static Range<LocalDate> toValidDateRange(final Timestamp from, final Timestamp to)
	{
		if (from == null)
		{
			if (to == null)
			{
				return Range.all();
			}
			else
			{
				return Range.lessThan(TimeUtil.asLocalDate(to));
			}
		}
		else
		{
			if (to == null)
			{
				return Range.atLeast(TimeUtil.asLocalDate(from));
			}
			else
			{
				return Range.closedOpen(TimeUtil.asLocalDate(from), TimeUtil.asLocalDate(to));
			}
		}
	}

	private PPRoutingActivity toRoutingActivity(
			final I_AD_WF_Node activityRecord,
			final TemporalUnit durationUnit,
			final BigDecimal qtyPerBatch,
			final ImmutableSetMultimap<PPRoutingActivityId, PPRoutingActivityId> nextActivityIdsByActivityId)
	{
		final PPRoutingId routingId = PPRoutingId.ofRepoId(activityRecord.getAD_Workflow_ID());
		final PPRoutingActivityId activityId = PPRoutingActivityId.ofAD_WF_Node_ID(routingId, activityRecord.getAD_WF_Node_ID());

		final ImmutableSet<PPRoutingActivityId> nextActivityIds = nextActivityIdsByActivityId.get(activityId);

		return PPRoutingActivity.builder()
				.id(activityId)
				.code(activityRecord.getValue())
				.name(activityRecord.getName())
				.validDates(toValidDateRange(activityRecord.getValidFrom(), activityRecord.getValidTo()))
				//
				.resourceId(ResourceId.ofRepoId(activityRecord.getS_Resource_ID()))
				//
				.durationUnit(durationUnit)
				.queuingTime(Duration.of(activityRecord.getQueuingTime(), durationUnit))
				.setupTime(Duration.of(activityRecord.getSetupTime(), durationUnit))
				.waitingTime(Duration.of(activityRecord.getWaitingTime(), durationUnit))
				.movingTime(Duration.of(activityRecord.getMovingTime(), durationUnit))
				//
				.durationPerOneUnit(Duration.of(activityRecord.getDuration(), durationUnit))
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
				//
				.nextActivityIds(nextActivityIds)
				//
				.build();
	}

	private ImmutableSetMultimap<PPRoutingActivityId, PPRoutingActivityId> retrieveNextActivityIdsIndexedByActivityId(final Set<PPRoutingActivityId> activityIds)
	{
		final ImmutableMap<Integer, PPRoutingActivityId> activityIdsByRepoId = Maps.uniqueIndex(activityIds, PPRoutingActivityId::getRepoId);

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_WF_NodeNext.class)
				.addInArrayFilter(I_AD_WF_NodeNext.COLUMNNAME_AD_WF_Node_ID, activityIds)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						nodeNextRecord -> activityIdsByRepoId.get(nodeNextRecord.getAD_WF_Node_ID()),
						nodeNextRecord -> activityIdsByRepoId.get(nodeNextRecord.getAD_WF_Next_ID())));
	}

	private static TemporalUnit extractDurationUnit(final I_AD_Workflow routingRecord)
	{
		final String durationUnitCode = routingRecord.getDurationUnit();
		return DurationUnitCodeUtils.toTemporalUnit(durationUnitCode);
	}

	@Override
	public PPRoutingId getRoutingIdByProductId(@NonNull final ProductId productId)
	{
		final IProductDAO productsRepo = Services.get(IProductDAO.class);
		final I_M_Product product = productsRepo.getById(productId);
		final String productValue = product.getValue();
		final ClientId clientId = ClientId.ofRepoId(product.getAD_Client_ID());
		return retrievePPRoutingIdByProductValue(productValue, clientId);
	}

	@Cached(cacheName = I_AD_Workflow.Table_Name + "#by#" + I_AD_Workflow.COLUMNNAME_Value)
	/* package */ PPRoutingId retrievePPRoutingIdByProductValue(@NonNull final String productValue, @NonNull final ClientId clientId)
	{
		Check.assumeNotEmpty(productValue, "productValue is not empty");

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Workflow.class)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_Value, productValue)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_AD_Client_ID, clientId)
				.addEqualsFilter(I_AD_Workflow.COLUMNNAME_WorkflowType, X_AD_Workflow.WORKFLOWTYPE_Manufacturing)
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

			final TemporalUnit durationUnit = extractDurationUnit(routingRecord);
			if (changeRequest.getQueuingTime() != null)
			{
				routingRecord.setQueuingTime(DurationUtils.toInt(changeRequest.getQueuingTime(), durationUnit));
			}
			if (changeRequest.getSetupTime() != null)
			{
				routingRecord.setSetupTime(DurationUtils.toInt(changeRequest.getSetupTime(), durationUnit));
			}
			if (changeRequest.getDurationPerOneUnit() != null)
			{
				routingRecord.setDuration(DurationUtils.toInt(changeRequest.getDurationPerOneUnit(), durationUnit));
			}
			if (changeRequest.getWaitingTime() != null)
			{
				routingRecord.setWaitingTime(DurationUtils.toInt(changeRequest.getWaitingTime(), durationUnit));
			}
			if (changeRequest.getMovingTime() != null)
			{
				routingRecord.setMovingTime(DurationUtils.toInt(changeRequest.getMovingTime(), durationUnit));
			}

			//
			// Update routing and activity costs
			routingRecord.setCost(changeRequest.getCost() != null ? changeRequest.getCost() : BigDecimal.ZERO);

			for (final I_AD_WF_Node routingActivityRecord : routingActivityRecords)
			{
				final PPRoutingActivityId activityId = PPRoutingActivityId.ofAD_WF_Node_ID(routingId, routingActivityRecord.getAD_WF_Node_ID());
				final BigDecimal cost = changeRequest.getActivityCostOrNull(activityId);
				routingActivityRecord.setCost(cost != null ? cost : BigDecimal.ZERO);
			}
		}

		//
		// Save all
		routingActivityRecords.forEach(InterfaceWrapperHelper::saveRecord);
		InterfaceWrapperHelper.save(routingRecord);
	}

	private List<I_AD_WF_Node> retrieveNodes(@NonNull final I_AD_Workflow routingRecord)
	{
		final Object contextProvider = routingRecord;
		final PPRoutingId routingId = PPRoutingId.ofRepoId(routingRecord.getAD_Workflow_ID());

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_WF_Node.class, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_WF_Node.COLUMNNAME_AD_Workflow_ID, routingId)
				.orderBy(I_AD_WF_Node.COLUMNNAME_AD_WF_Node_ID)
				.create()
				.list();
	}

}
