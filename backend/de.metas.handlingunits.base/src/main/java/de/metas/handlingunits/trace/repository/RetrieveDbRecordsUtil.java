package de.metas.handlingunits.trace.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import de.metas.handlingunits.trace.HUTraceType;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.util.TimeUtil;

import com.google.common.annotations.VisibleForTesting;
import java.util.Objects;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_Trace;
import de.metas.handlingunits.trace.HUTraceEvent;
import de.metas.handlingunits.trace.HUTraceEventQuery;
import de.metas.handlingunits.trace.HUTraceEventQuery.RecursionMode;
import de.metas.process.PInstanceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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
	public static List<HUTraceEvent> query(@NonNull final HUTraceEventQuery query)
	{
		final ListResult resultOut = (ListResult)queryDbRecord(query, new ListResult());

		return resultOut.getList()
				.stream()
				.map(HuTraceEventToDbRecordUtil::fromDbRecord)
				.collect(Collectors.toList());
	}

	public static PInstanceId queryToSelection(@NonNull final HUTraceEventQuery query)
	{
		final SelectionResult resultOut = (SelectionResult)queryDbRecord(query, new SelectionResult());

		return resultOut.getSelectionId();
	}

	private interface EmptyResultSupplier
	{
		Result newEmptyResult();
	}

	private interface Result extends EmptyResultSupplier
	{
		void executeQueryAndAddAll(IQuery<I_M_HU_Trace> query);

		void addAll(Result result);

		/**
		 * Get the {@code VHU_ID}s from the records we already found so far. Needed to recurse forwards.
		 */
		List<HuId> getVhuIds();

		/**
		 * Get the {@code VHU_Source_ID} from the records we already found so far. Needed to recurse backwards.
		 */
		List<HuId> getVhuSourceIds();
	}

	/**
	 * Used when we are going to return the result as a list.
	 */
	private static class ListResult implements Result
	{
		// use the tree set to make sure we have no duplicates
		private final Set<I_M_HU_Trace> set = new TreeSet<>(ModelByIdComparator.instance);

		@Override
		public Result newEmptyResult()
		{
			return new ListResult();
		}

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
	}

	/**
	 * Used when we are going to store the result in the {@code T_Selection} table and return the selection ID.
	 */
	private static class SelectionResult implements Result
	{
		private PInstanceId selectionId;

		@Override
		public Result newEmptyResult()
		{
			return new SelectionResult();
		}

		@Override
		public void executeQueryAndAddAll(@NonNull final IQuery<I_M_HU_Trace> query)
		{
			if (selectionId == null)
			{
				selectionId = query.createSelection();
			}
			else
			{
				query.createSelection(selectionId);
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

			if (selectionId == null)
			{
				// performance: we don't really need to add anything but can just take the "pointer" from selectionResultToAdd
				selectionId = selectionResultToAdd.getSelectionId();
			}
			else
			{
				final IQuery<I_M_HU_Trace> query = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
						.setOnlySelection(selectionResultToAdd.getSelectionId())
						.create();
				executeQueryAndAddAll(query);
			}
		}

		@Override
		public List<HuId> getVhuIds()
		{
			if (selectionId == null)
			{
				return ImmutableList.of();
			}
			return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
					.setOnlySelection(selectionId)
					.addNotEqualsFilter(I_M_HU_Trace.COLUMN_VHU_ID, null)
					.create()
					.listDistinct(I_M_HU_Trace.COLUMNNAME_VHU_ID, Integer.class)
					.stream()
					.map(HuId::ofRepoId)
					.collect(ImmutableList.toImmutableList());
		}

		@Override
		public List<HuId> getVhuSourceIds()
		{
			if (selectionId == null)
			{
				return ImmutableList.of();
			}
			return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
					.setOnlySelection(selectionId)
					.addNotEqualsFilter(I_M_HU_Trace.COLUMN_VHU_Source_ID, null)
					.create()
					.listDistinct(I_M_HU_Trace.COLUMNNAME_VHU_Source_ID, Integer.class)
					.stream()
					.map(HuId::ofRepoId)
					.collect(ImmutableList.toImmutableList());
		}

		public PInstanceId getSelectionId()
		{
			return selectionId;
		}
	}

	private Result queryDbRecord(
			@NonNull final HUTraceEventQuery huTraceEventQuery,
			@NonNull final EmptyResultSupplier emptyResultSupplier)
	{
		final Result resultOut = emptyResultSupplier.newEmptyResult();

		final IQueryBuilder<I_M_HU_Trace> queryBuilder = createQueryBuilderOrNull(huTraceEventQuery);
		if (queryBuilder == null)
		{
			return resultOut;
		}

		final Result noRecursiveResult = emptyResultSupplier.newEmptyResult();
		final IQuery<I_M_HU_Trace> query = queryBuilder
				.orderBy().addColumn(I_M_HU_Trace.COLUMN_EventTime).endOrderBy()
				.create();
		noRecursiveResult.executeQueryAndAddAll(query);

		// no matter which recursion mode, we can always add the records we already have
		resultOut.addAll(noRecursiveResult);

		switch (huTraceEventQuery.getRecursionMode())
		{
			case NONE:
				break;
			case FORWARD:
				resultOut.addAll(recurseForwards(noRecursiveResult));
				break;
			case BACKWARD:
				// recurse and add the records whose M_HU_IDs show up as M_HU_Source_IDs in the records we already loaded
				resultOut.addAll(recurseBackwards(noRecursiveResult));
				break;
			case BOTH:
				resultOut.addAll(recurseForwards(noRecursiveResult));
				resultOut.addAll(recurseBackwards(noRecursiveResult));
				break;
			default:
				throw new AdempiereException("Unexpected RecursionMode=" + huTraceEventQuery.getRecursionMode())
						.appendParametersToMessage()
						.setParameter("HUTraceEventQuery", huTraceEventQuery);
		}
		return resultOut;
	}

	@VisibleForTesting
	static IQueryBuilder<I_M_HU_Trace> createQueryBuilderOrNull(@NonNull final HUTraceEventQuery query)
	{
		final IQueryBuilder<I_M_HU_Trace> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.addOnlyActiveRecordsFilter();

		boolean queryIsEmpty = updateQueryBuilderForQueryEventTime(queryBuilder, query);

		if (query.getHuTraceEventId().isPresent())
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_M_HU_Trace_ID, query.getHuTraceEventId().getAsInt());
			queryIsEmpty = false;
		}
		if(!query.getTypes().isEmpty())
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

		if(query.getLotNumber() != null)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMNNAME_LotNumber, query.getLotNumber());
			queryIsEmpty = false;
		}
		if (query.getQty() != null)
		{
			queryBuilder.addEqualsFilter(I_M_HU_Trace.COLUMN_Qty, query.getQty());
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
	private Result recurseBackwards(@NonNull final Result resultIn)
	{
		final Result resultOut = resultIn.newEmptyResult();
		final List<HuId> vhuSourceIds = resultIn.getVhuSourceIds();
		for (final HuId vhuSourceId : vhuSourceIds)
		{
			resultOut.addAll(queryDbRecord(HUTraceEventQuery
					.builder()
					.vhuId(vhuSourceId)
					.recursionMode(RecursionMode.BACKWARD)
					.build(),
					resultIn));
		}
		return resultOut;
	}

	private Result recurseForwards(@NonNull final Result resultIn)
	{
		final Result resultOut = resultIn.newEmptyResult();

		final List<HuId> vhuIDs = resultIn.getVhuIds();
		for (final HuId vhuId : vhuIDs)
		{
			resultOut.addAll(recuseForwardViaVhuLink(resultIn, vhuId));

			resultOut.addAll(recurseForwardViaSourceVhuLink(resultIn, vhuId));
		}
		return resultOut;
	}

	private Result recuseForwardViaVhuLink(
			@NonNull final EmptyResultSupplier emptyResultSupplier,
			final HuId vhuId)
	{

		final HUTraceEventQuery sameVhuIdRecordsQuery = HUTraceEventQuery.builder()
				.vhuId(vhuId)
				.recursionMode(RecursionMode.NONE)
				.build();
		final Result sameVhuIdRecordsResult = queryDbRecord(
				sameVhuIdRecordsQuery,
				emptyResultSupplier.newEmptyResult());

		final Result resultOut = emptyResultSupplier.newEmptyResult();
		resultOut.addAll(sameVhuIdRecordsResult);

		return resultOut;
	}

	/**
	 * Get the records where our vhuId is the vhuSourceId.
	 */
	private Result recurseForwardViaSourceVhuLink(
			@NonNull final EmptyResultSupplier emptyResultSupplier,
			final HuId vhuId)
	{
		final HUTraceEventQuery directFollowUpRecordsQuery = HUTraceEventQuery.builder()
				.vhuSourceId(vhuId)
				.recursionMode(RecursionMode.NONE)
				.build();
		final Result directFollowUpRecordsResult = queryDbRecord(
				directFollowUpRecordsQuery,
				emptyResultSupplier.newEmptyResult());
		final List<HuId> directFollowupVhuIDs = directFollowUpRecordsResult.getVhuIds();

		final Result resultOut = emptyResultSupplier.newEmptyResult();
		for (final HuId directFollowupVhuID : directFollowupVhuIDs)
		{
			final Result forwardResult = queryDbRecord(
					HUTraceEventQuery.builder()
							.vhuId(directFollowupVhuID)
							.recursionMode(RecursionMode.FORWARD)
							.build(),
					emptyResultSupplier);
			resultOut.addAll(forwardResult);
		}

		return resultOut;
	}

	/**
	 * Return all records; this makes absolutely no sense in production; Intended to be used only use for testing.
	 */
	@VisibleForTesting
	public List<HUTraceEvent> queryAll()
	{
		Check.errorUnless(Adempiere.isUnitTestMode(), "The method queryAll() shall only be invoked from test code");

		final IQueryBuilder<I_M_HU_Trace> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Trace.class)
				.orderBy().addColumn(I_M_HU_Trace.COLUMN_M_HU_Trace_ID).endOrderBy();

		return queryBuilder.create()
				.stream()
				.map(HuTraceEventToDbRecordUtil::fromDbRecord)
				.collect(Collectors.toList());
	}
}
