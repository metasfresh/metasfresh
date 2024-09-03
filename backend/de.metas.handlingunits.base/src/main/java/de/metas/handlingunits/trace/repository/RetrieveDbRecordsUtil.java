package de.metas.handlingunits.trace.repository;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceEventQuery.RecursionMode;
import de.metas.handlingunits.trace.HUTraceType;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@UtilityClass
public class RetrieveDbRecordsUtil
{
	private static final String SYSCONFIG_MaxDepth = "HUTraceEventsLoaderInstance.RecursionMaxDepth";
	private static final int DEFAULT_MaxDepth = 50;

	public static List<HUTraceEvent> query(@NonNull final HUTraceEventQuery query)
	{
		final HUTraceEventsLoaderInstance loader = new HUTraceEventsLoaderInstance(new ListResultSupplier(Services.get(IQueryBL.class)));
		loader.load(query);
		final ListResult resultOut = (ListResult)loader.getResult();

		return resultOut.getList()
				.stream()
				.map(HuTraceEventToDbRecordUtil::fromDbRecord)
				.collect(Collectors.toList());
	}

	public static PInstanceId queryToSelection(@NonNull final HUTraceEventQuery query)
	{
		final HUTraceEventsLoaderInstance loader = new HUTraceEventsLoaderInstance(new SelectionResultSupplier(Services.get(IQueryBL.class)));
		loader.load(query);
		final SelectionResult resultOut = (SelectionResult)loader.getResult();

		return resultOut.getSelectionId();
	}

	//
	//
	// --------------------------------------------------------------------------------------------
	//
	//

	private interface EmptyResultSupplier
	{
		Result newEmptyResult();
	}

	//
	//
	// --------------------------------------------------------------------------------------------
	//
	//

	private interface Result
	{
		void executeQueryAndAddAll(IQuery<I_M_HU_Trace> query);

		void addAll(Result result);

		/**
		 * Get the {@code VHU_ID}s from the records we already found so far. Needed to recurse forwards.
		 */
		Collection<HuId> getVhuIds();

		/**
		 * Get the {@code VHU_Source_ID} from the records we already found so far. Needed to recurse backwards.
		 */
		Collection<HuId> getVhuSourceIds();

		@Nullable
		IQuery<I_M_HU> toVHUIdsQuery();
	}

	//
	//
	// --------------------------------------------------------------------------------------------
	//
	//

	@RequiredArgsConstructor
	private static class ListResultSupplier implements EmptyResultSupplier
	{
		@NonNull private final IQueryBL queryBL;

		@Override
		public Result newEmptyResult()
		{
			return new ListResult(queryBL);
		}
	}

	/**
	 * Used when we are going to return the result as a list.
	 */
	@RequiredArgsConstructor
	private static class ListResult implements Result
	{
		@NonNull private final IQueryBL queryBL;
		// use the tree set to make sure we have no duplicates
		private final Set<I_M_HU_Trace> set = new TreeSet<>(ModelByIdComparator.instance);

		@Override
		public void executeQueryAndAddAll(final IQuery<I_M_HU_Trace> query)
		{
			set.addAll(query.list());
		}

		@Override
		public void addAll(final Result result)
		{
			final ListResult listResult = (ListResult)result;
			set.addAll(listResult.getList());
		}

		@Override
		public List<HuId> getVhuIds()
		{
			return set.stream()
					.map(dbRecord -> HuId.ofRepoId(dbRecord.getVHU_ID()))
					.sorted()
					.distinct()
					.collect(ImmutableList.toImmutableList());
		}

		@Override
		public List<HuId> getVhuSourceIds()
		{
			return set.stream()
					.map(dbRecord -> HuId.ofRepoIdOrNull(dbRecord.getVHU_Source_ID()))
					.filter(Objects::nonNull)
					.sorted()
					.distinct()
					.collect(ImmutableList.toImmutableList());
		}

		public List<I_M_HU_Trace> getList()
		{
			return ImmutableList.copyOf(set);
		}

		@Override
		public IQuery<I_M_HU> toVHUIdsQuery()
		{
			final List<HuId> vhuIds = getVhuIds();
			if (vhuIds.isEmpty())
			{
				return null;
			}

			return queryBL.createQueryBuilder(I_M_HU.class)
					.addInArrayFilter(I_M_HU.COLUMNNAME_M_HU_ID, vhuIds)
					.create();
		}
	}

	//
	//
	// --------------------------------------------------------------------------------------------
	//
	//

	@RequiredArgsConstructor
	private static class SelectionResultSupplier implements EmptyResultSupplier
	{
		@NonNull private final IQueryBL queryBL;

		@Override
		public Result newEmptyResult()
		{
			return new SelectionResult(queryBL);
		}
	}

	/**
	 * Used when we are going to store the result in the {@code T_Selection} table and return the selection ID.
	 */
	private static class SelectionResult implements Result
	{
		@NonNull private final IQueryBL queryBL;
		@Nullable @Getter private PInstanceId selectionId;

		private SelectionResult(@NonNull final IQueryBL queryBL)
		{
			this.queryBL = queryBL;
		}

		@Override
		public void executeQueryAndAddAll(@NonNull final IQuery<I_M_HU_Trace> query)
		{
			if (selectionId == null)
			{
				selectionId = query.createSelection();
				//System.out.println("*** Created selection: " + selectionId + "\n\tSQL: " + query);
			}
			else
			{
				query.createSelection(selectionId);
				//System.out.println("*** Added " + count + " to selection " + selectionId + "\n\tSQL: " + query);
			}
		}

		@Override
		public void addAll(@NonNull final Result resultToAdd)
		{
			final SelectionResult selectionResultToAdd = (SelectionResult)resultToAdd;
			if (selectionResultToAdd.getSelectionId() == null)
			{
				return; // result contains nothing to add
			}

			executeQueryAndAddAll(selectionResultToAdd.toQuery());
		}

		private IQuery<I_M_HU_Trace> toQuery()
		{
			return toQueryBuilder().create();
		}

		private IQueryBuilder<I_M_HU_Trace> toQueryBuilder()
		{
			final PInstanceId selectionId = Check.assumeNotNull(this.selectionId, "selectionId shall be set");
			return queryBL.createQueryBuilder(I_M_HU_Trace.class).setOnlySelection(selectionId);
		}

		@Override
		public Collection<HuId> getVhuIds()
		{
			if (selectionId == null)
			{
				return ImmutableSet.of();
			}

			return toQueryBuilder()
					.addNotEqualsFilter(I_M_HU_Trace.COLUMN_VHU_ID, null)
					.create()
					.listDistinct(I_M_HU_Trace.COLUMNNAME_VHU_ID, HuId.class)
					.stream()
					.collect(ImmutableSet.toImmutableSet());
		}

		@Override
		public Collection<HuId> getVhuSourceIds()
		{
			if (selectionId == null)
			{
				return ImmutableSet.of();
			}

			return toQueryBuilder()
					.addNotEqualsFilter(I_M_HU_Trace.COLUMN_VHU_Source_ID, null)
					.create()
					.listDistinct(I_M_HU_Trace.COLUMNNAME_VHU_Source_ID, Integer.class)
					.stream()
					.map(HuId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());
		}

		@Nullable
		@Override
		public IQuery<I_M_HU> toVHUIdsQuery()
		{
			if (selectionId == null)
			{
				return null;
			}

			return toQueryBuilder()
					.addNotEqualsFilter(I_M_HU_Trace.COLUMNNAME_VHU_ID, null)
					.andCollect(I_M_HU_Trace.COLUMNNAME_VHU_ID, I_M_HU.class)
					.create();
		}
	}

	//
	//
	// --------------------------------------------------------------------------------------------
	//
	//

	@VisibleForTesting
	static class HUTraceEventsLoaderInstance
	{

		@NonNull private final EmptyResultSupplier emptyResultSupplier;
		private final int maxDepth;
		@NonNull @Getter private final Result result;

		HUTraceEventsLoaderInstance(@NonNull final EmptyResultSupplier emptyResultSupplier)
		{
			this.emptyResultSupplier = emptyResultSupplier;
			this.maxDepth = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_MaxDepth, DEFAULT_MaxDepth);
			this.result = emptyResultSupplier.newEmptyResult();
		}

		void load(@NonNull final HUTraceEventQuery query)
		{
			load(query, 0);
		}

		private void load(
				@NonNull final HUTraceEventQuery query,
				final int depth)
		{
			final IQueryBuilder<I_M_HU_Trace> sqlQueryBuilder = createQueryBuilderOrNull(query);
			if (sqlQueryBuilder == null)
			{
				return;
			}

			final IQuery<I_M_HU> alreadyConsideredVHUIds = this.result.toVHUIdsQuery();
			if (alreadyConsideredVHUIds != null)
			{
				sqlQueryBuilder.addNotInSubQueryFilter(I_M_HU_Trace.COLUMNNAME_VHU_ID, I_M_HU.COLUMNNAME_M_HU_ID, alreadyConsideredVHUIds);
			}

			final Result noRecursiveResult = emptyResultSupplier.newEmptyResult();
			noRecursiveResult.executeQueryAndAddAll(sqlQueryBuilder.orderBy(I_M_HU_Trace.COLUMN_EventTime).create());

			// no matter which recursion mode, we can always add the records we already have
			this.result.addAll(noRecursiveResult);

			if (maxDepth <= 0 || depth < maxDepth)
			{
				switch (query.getRecursionMode())
				{
					case NONE:
						break;
					case FORWARD:
						recurseForwards(noRecursiveResult, depth + 1);
						break;
					case BACKWARD:
						// recurse and add the records whose M_HU_IDs show up as M_HU_Source_IDs in the records we already loaded
						recurseBackwards(noRecursiveResult, depth + 1);
						break;
					case BOTH:
						recurseForwards(noRecursiveResult, depth + 1);
						recurseBackwards(noRecursiveResult, depth + 1);
						break;
					default:
						throw new AdempiereException("Unexpected RecursionMode=" + query.getRecursionMode())
								.appendParametersToMessage()
								.setParameter("HUTraceEventQuery", query);
				}
			}
		}

		@VisibleForTesting
		static IQueryBuilder<I_M_HU_Trace> createQueryBuilderOrNull(@NonNull final HUTraceEventQuery query)
		{
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final IQueryBuilder<I_M_HU_Trace> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Trace.class)
					.addOnlyActiveRecordsFilter();

			boolean queryIsEmpty = updateQueryBuilderForQueryEventTime(queryBuilder, query);

			if (query.getHuTraceEventId().isPresent())
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_Trace_ID, query.getHuTraceEventId().getAsInt());
				queryIsEmpty = false;
			}
			if (!query.getTypes().isEmpty())
			{
				queryBuilder.addInArrayFilter(I_M_HU_Trace.COLUMN_HUTraceType, query.getTypes().stream().map(HUTraceType::getCode).collect(ImmutableList.toImmutableList()));
				queryIsEmpty = false;
			}
			if (query.getOrgId() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_AD_Org_ID, query.getOrgId());
				queryIsEmpty = false;
			}
			if (!query.getVhuIds().isEmpty())
			{
				queryBuilder.addInArrayFilter(I_M_HU_Trace.COLUMN_VHU_ID, query.getVhuIds());
				queryIsEmpty = false;
			}
			if (query.getProductId() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_M_Product_ID, query.getProductId());
				queryIsEmpty = false;
			}

			if (query.getLotNumber() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_LotNumber, query.getLotNumber());
				queryIsEmpty = false;
			}
			if (query.getQty() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_Qty, query.getQty().toBigDecimal());
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_C_UOM_ID, query.getQty().getUomId());
				queryIsEmpty = false;
			}

			if (!Check.isEmpty(query.getVhuStatus()))
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_VHUStatus, query.getVhuStatus());
				queryIsEmpty = false;
			}
			if (query.getVhuSourceId() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_VHU_Source_ID, query.getVhuSourceId());
				queryIsEmpty = false;
			}
			if (!query.getTopLevelHuIds().isEmpty())
			{
				queryBuilder.addInArrayFilter(I_M_HU_Trace.COLUMN_M_HU_ID, query.getTopLevelHuIds());
				queryIsEmpty = false;
			}
			if (query.getInOutId() > 0)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_InOut_ID, query.getInOutId());
				queryIsEmpty = false;
			}
			if (query.getMovementId() > 0)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_Movement_ID, query.getMovementId());
				queryIsEmpty = false;
			}

			if (query.getInventoryId() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_Inventory_ID, query.getInventoryId());
				queryIsEmpty = false;
			}
			if (query.getPpCostCollectorId() > 0)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_PP_Cost_Collector_ID, query.getPpCostCollectorId());
				queryIsEmpty = false;
			}
			if (query.getPpOrderId() > 0)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_PP_Order_ID, query.getPpOrderId());
				queryIsEmpty = false;
			}
			if (query.getShipmentScheduleId() != null)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_ShipmentSchedule_ID, query.getShipmentScheduleId());
				queryIsEmpty = false;
			}
			if (query.getDocTypeId().isPresent())
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_C_DocType_ID, query.getDocTypeId().get());
				queryIsEmpty = false;
			}
			if (!Check.isEmpty(query.getDocStatus()))
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_DocStatus, query.getDocStatus());
				queryIsEmpty = false;
			}
			if (query.getHuTrxLineId() > 0)
			{
				queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_Trx_Line_ID, query.getHuTrxLineId());
				queryIsEmpty = false;
			}

			if (queryIsEmpty)
			{
				return null;
			}

			return queryBuilder;
		}

		private static boolean updateQueryBuilderForQueryEventTime(
				@NonNull final IQueryBuilder<I_M_HU_Trace> queryBuilder,
				@NonNull final HUTraceEventQuery query)
		{
			if (query.getEventTime() == null)
			{
				return true;
			}

			final Timestamp eventTime = TimeUtil.asTimestamp(query.getEventTime());
			final Timestamp eventTimeTo = TimeUtil.asTimestamp(query.getEventTimeTo());

			switch (query.getEventTimeOperator())
			{
				case EQUAL:
					queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_EventTime, eventTime);
					break;
				case BETWEEN:
					queryBuilder.addBetweenFilter(I_M_HU_Trace.COLUMN_EventTime, eventTime, eventTimeTo);
					break;
				default:
					throw new AdempiereException("Unexpected EventTimeOperator=" + query.getEventTimeOperator())
							.appendParametersToMessage()
							.setParameter("HUTraceEventQuery", query)
							.setParameter("IQueryBuilder", queryBuilder);
			}
			return false;
		}

		/**
		 * Creates and executes a query in which the {@code vhuSourceIds} of the given {@code resultIn} are the  {@code vhuId}s of the additional records we want to load
		 */
		private void recurseBackwards(@NonNull final Result resultIn, int depth)
		{
			for (final HuId vhuSourceId : resultIn.getVhuSourceIds())
			{
				load(
						HUTraceEventQuery.builder().vhuId(vhuSourceId).recursionMode(RecursionMode.BACKWARD).build(),
						depth
				);
			}
		}

		private void recurseForwards(@NonNull final Result resultIn, final int depth)
		{
			for (final HuId vhuId : resultIn.getVhuIds())
			{
				recurseForwardViaVhuLink(vhuId, depth);
				recurseForwardViaSourceVhuLink(vhuId, depth);
			}
		}

		private void recurseForwardViaVhuLink(final HuId vhuId, int depth)
		{
			load(
					HUTraceEventQuery.builder().vhuId(vhuId).recursionMode(RecursionMode.NONE).build(),
					depth
			);
		}

		/**
		 * Get the records where our vhuId is the vhuSourceId.
		 */
		private void recurseForwardViaSourceVhuLink(@NonNull final HuId vhuId, final int depth)
		{
			final IQueryBuilder<I_M_HU_Trace> sqlQueryBuilder = createQueryBuilderOrNull(HUTraceEventQuery.builder().vhuSourceId(vhuId).recursionMode(RecursionMode.NONE).build());
			if (sqlQueryBuilder == null)
			{
				return;
			}

		final Collection<HuId> directFollowupVhuIds = sqlQueryBuilder.create().listDistinct(I_M_HU_Trace.COLUMNNAME_VHU_ID, HuId.class);

			for (final HuId directFollowupVhuId : directFollowupVhuIds)
			{
				load(HUTraceEventQuery.builder().vhuId(directFollowupVhuId).recursionMode(RecursionMode.FORWARD).build(), depth);
			}
		}
	}

	/**
	 * Return all records; this makes absolutely no sense in production; Intended to be used only use for testing.
	 */
	@VisibleForTesting
	public List<HUTraceEvent> queryAll()
	{
		Adempiere.assertUnitTestMode();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_HU_Trace> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Trace.class)
				.orderBy().addColumn(I_M_HU_Trace.COLUMN_M_HU_Trace_ID).endOrderBy();

		return queryBuilder.create()
				.stream()
				.map(HuTraceEventToDbRecordUtil::fromDbRecord)
				.collect(Collectors.toList());
	}
}
