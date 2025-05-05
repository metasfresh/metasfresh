package de.metas.handlingunits.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.getId;
import static org.adempiere.model.InterfaceWrapperHelper.getModelTableId;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public class HUAssignmentDAO implements IHUAssignmentDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Adds a filter that makes sure only <b>top level-HU</b> records with the given <code>AD_Table_ID</code> and in addition with <code>M_LU_HU_ID</code>, <code>M_TU_HU_ID</code> and
	 * <code>VHU_ID</code> being <code>null</code> will be selected.
	 *
	 * @param queryBuilder the builder that is augmented by this method
	 */
	private IQueryBuilder<I_M_HU_Assignment> applyCommonTopLevelFilters(final IQueryBuilder<I_M_HU_Assignment> queryBuilder, final int adTableId)
	{
		queryBuilder
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId)
				//
				// Filter out entries which are specifically for other levels
				//
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, null)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, null);
		return queryBuilder;
	}

	private IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentQuery(final Properties ctx, final int huId, final int adTableId, final int recordId, final String trxName)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = queryBL
				.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, huId);

		return applyCommonTopLevelFilters(queryBuilder, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordId);
	}

	public IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentsForModelQuery(
			@NonNull final Properties ctx,
			final int adTableId,
			@NonNull final Set<Integer> recordIds,
			@Nullable final String trxName)
	{
		Check.assumeNotEmpty(recordIds, "recordIds not empty");

		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = queryBL
				.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName);

		applyCommonTopLevelFilters(queryBuilder, adTableId)
				.addInArrayFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordIds);

		queryBuilder.orderBy(I_M_HU_Assignment.COLUMNNAME_M_HU_Assignment_ID);

		return queryBuilder;
	}

	@Override
	public void assertNoHUAssignmentsForModel(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final boolean foundHUAssignments = queryBL
				.createQueryBuilder(I_M_HU_Assignment.class, model)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
		if (foundHUAssignments)
		{
			throw new HUException("@HUAssignmentsFoundForModelError@: " + model);
		}
	}

	@Override
	@Nullable
	public I_M_HU_Assignment retrieveHUAssignmentOrNull(final Properties ctx, final int huId, final int adTableId, final int recordId, final String trxName)
	{
		return retrieveHUAssignmentQuery(ctx, huId, adTableId, recordId, trxName)
				.create()
				.firstOnly(I_M_HU_Assignment.class);
	}

	@Override
	public List<I_M_HU_Assignment> retrieveTopLevelHUAssignmentsForModel(final Object model)
	{
		return retrieveHUAssignmentsForModelQuery(model)
				.create()
				.list();
	}

	@Override
	public IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentsForModelQuery(final Object model)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);
		final String trxName = InterfaceWrapperHelper.getTrxName(model);

		return retrieveHUAssignmentsForModelQuery(ctx, adTableId, ImmutableSet.of(recordId), trxName);
	}

	@Override
	public List<I_M_HU_Assignment> retrieveHUAssignmentsForModel(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return retrieveHUAssignmentsForModelQuery(ctx, adTableId, ImmutableSet.of(recordId), trxName)
				.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public ImmutableSetMultimap<TableRecordReference, HuId> retrieveHUsByRecordRefs(@NonNull final Set<TableRecordReference> recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final HashMultimap<AdTableId, Integer> recordIdsByTableId = HashMultimap.create();
		recordRefs.forEach(recordRef -> recordIdsByTableId.put(recordRef.getAdTableId(), recordRef.getRecord_ID()));

		final Properties ctx = Env.getCtx();

		return recordIdsByTableId.keySet()
				.stream()
				.map(adTableId -> retrieveHUAssignmentsForModelQuery(ctx, adTableId.getRepoId(), recordIdsByTableId.get(adTableId), ITrx.TRXNAME_ThreadInherited).create())
				.reduce(IQuery.unionDistict())
				.get()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						huAssignment -> TableRecordReference.of(huAssignment.getAD_Table_ID(), huAssignment.getRecord_ID()),
						huAssignment -> HuId.ofRepoId(huAssignment.getM_HU_ID())));
	}

	@Override
	public List<I_M_HU> retrieveTopLevelHUsForModel(final Object model)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(model);
		return retrieveTopLevelHUsForModel(model, trxName);
	}

	@Override
	public List<I_M_HU> retrieveTopLevelHUsForModel(final Object model, final String trxName)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);
		return retrieveTopLevelHUsForModel(ctx, adTableId, recordId, trxName);
	}

	private List<I_M_HU> retrieveTopLevelHUsForModel(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordId)
				.addOnlyActiveRecordsFilter()
				//
				// Collect top level HUs
				.andCollect(I_M_HU_Assignment.COLUMN_M_HU_ID);

		//
		// 07612: Order by HU ID to preserve allocation order (i.e highest HU quantities / full HUs first)
		queryBuilder.orderBy()
				.addColumn(I_M_HU.COLUMN_M_HU_ID);

		final List<I_M_HU> husTopLevel = queryBuilder.create().list(I_M_HU.class);

		//
		// Guard: make sure all those HUs are really top level
		// Normally, this shall not happen. But we could have the case when the TU was joined to a LU later and the HU assignment was not updated.
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		husTopLevel.removeIf(hu -> !handlingUnitsBL.isTopLevel(hu));

		// NOTE: this method will NOT exclude destroyed HUs.
		// Before changing this, please carefully check the depending API.

		return husTopLevel;
	}

	@Override
	public IQueryBuilder<I_M_HU_Assignment> retrieveTUHUAssignmentsForModelQuery(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		return queryBL.createQueryBuilder(I_M_HU_Assignment.class, model)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordId)
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, null) // TU is set
				.addOnlyActiveRecordsFilter();
	}

	@Override
	public List<I_M_HU_Assignment> retrieveIncludedHUAssignments(final I_M_HU_Assignment assignment)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, assignment);

		queryBuilder
				// references same record..
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, assignment.getAD_Table_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, assignment.getRecord_ID())
				// ..and same toplevel-HU..
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, assignment.getM_HU_ID());

		// ..but additionally references one of the HU's components (TU or LU)
		final ICompositeQueryFilter<I_M_HU_Assignment> subFilter = queryBL.createCompositeQueryFilter(I_M_HU_Assignment.class);
		subFilter
				.setJoinOr()
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, null)
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, null);

		queryBuilder.filter(subFilter);

		//
		return queryBuilder.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public void deleteHUAssignments(@NonNull Properties ctx, @NonNull final TableRecordReference modelRef, @NonNull final Collection<HuId> huIds, final String trxName)
	{
		if (huIds.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, modelRef.getAD_Table_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, modelRef.getRecord_ID())
				.addInArrayOrAllFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.delete();
	}

	@Override
	public boolean hasHUAssignmentsForModel(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		return queryBL
				.createQueryBuilder(I_M_HU_Assignment.class, model)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, recordId)
				.andCollect(I_M_HU_Assignment.COLUMN_M_HU_ID)
				.addNotEqualsFilter(I_M_HU.COLUMNNAME_HUStatus, X_M_HU.HUSTATUS_Planning)
				.create()
				.anyMatch();
	}

	@Override
	public boolean hasMoreLUAssigmentsForSameModelType(final I_M_HU_Assignment luAssignment)
	{
		Check.assumeNotNull(luAssignment, "luAssignment not null");
		final int luHUId = luAssignment.getM_LU_HU_ID();
		if (luHUId <= 0)
		{
			return false;
		}

		final int adTableId = luAssignment.getAD_Table_ID();
		final int recordId = luAssignment.getRecord_ID();

		return queryBL
				.createQueryBuilder(I_M_HU_Assignment.class, luAssignment)
				.addOnlyActiveRecordsFilter() // only active assignments are counted
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId) // for same kind of model
				.addCompareFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, Operator.LESS, recordId) // on a model which was created before ours
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, luHUId) // for our LU
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_VHU_ID, luAssignment.getVHU_ID()) // 08564 : also check the vhu
				//
				.create()
				.anyMatch();

	}

	@Override
	public List<I_M_HU_Assignment> retrieveTableHUAssignmentsNoTopFilter(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = retrieveTableHUAssignmentsQueryNoTopLevel(contextProvider, adTableId, hu);
		return queryBuilder.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public final IQueryBuilder<I_M_HU_Assignment> retrieveTableHUAssignmentsQuery(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, contextProvider)
				// .addOnlyActiveRecordsFilter()
				;

		applyCommonTopLevelFilters(queryBuilder, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, hu.getM_HU_ID());
		return queryBuilder;
	}

	private IQueryBuilder<I_M_HU_Assignment> retrieveTableHUAssignmentsQueryNoTopLevel(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, contextProvider);

		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, adTableId);
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, hu.getM_HU_ID());
		return queryBuilder;
	}

	@Override
	public <T> List<T> retrieveModelsForHU(final I_M_HU hu, final Class<T> clazz)
	{
		final boolean topLevel = true;
		return retrieveModelsForHU(hu, clazz, topLevel);
	}

	@Override
	public <T> List<T> retrieveModelsForHU(final I_M_HU hu, final Class<T> clazz, final boolean topLevel)
	{
		final int tableId = InterfaceWrapperHelper.getTableId(clazz);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(clazz);

		final IQueryBuilder<I_M_HU_Assignment> huAssigmentQueryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, hu)
				.addOnlyContextClientOrSystem()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, tableId);
		if (topLevel)
		{
			huAssigmentQueryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, hu.getM_HU_ID());
			applyCommonTopLevelFilters(huAssigmentQueryBuilder, tableId);
		}
		else
		{
			final ICompositeQueryFilter<I_M_HU_Assignment> filter = queryBL.createCompositeQueryFilter(I_M_HU_Assignment.class)
					.setJoinOr()
					.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, hu.getM_HU_ID())
					.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, hu.getM_HU_ID())
					.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_VHU_ID, hu.getM_HU_ID());
			huAssigmentQueryBuilder.filter(filter);
		}

		final IQuery<I_M_HU_Assignment> huAssigmentQuery = huAssigmentQueryBuilder
				.create();

		// @formatter:off
		return queryBL.createQueryBuilder(clazz, hu)
					.addOnlyContextClientOrSystem()
					.addOnlyActiveRecordsFilter()
					.addInSubQueryFilter(keyColumnName, I_M_HU_Assignment.COLUMNNAME_Record_ID, huAssigmentQuery)
				.orderBy()
					.addColumn(InterfaceWrapperHelper.getKeyColumnName(clazz))
				.endOrderBy()
				.create()
				.list(clazz);
		// @formatter:on
	}

	@Override
	public List<I_M_HU_Assignment> retrieveTableHUAssignmentsNoTopFilterTUMandatory(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = retrieveTableHUAssignmentsQueryNoTopLevel(contextProvider, adTableId, hu);
		return queryBuilder
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, null)
				.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public List<HuAssignment> retrieveLowLevelHUAssignmentsForModel(@NonNull final Object model)
	{
		final List<I_M_HU_Assignment> huAssignmentRecords = retrieveOrderedHUAssignmentRecords(model);

		final Set<Integer> alreadySeenHuIds = new HashSet<>();
		final ImmutableList.Builder<HuAssignment> result = ImmutableList.builder();
		for (final I_M_HU_Assignment huAssignmentRecord : huAssignmentRecords)
		{
			if (huAssignmentRecord.getVHU_ID() > 0)
			{
				if (alreadySeenHuIds.add(huAssignmentRecord.getVHU_ID()))
				{
					result.add(HuAssignment.ofDataRecord(huAssignmentRecord));
					addIfNotZero(alreadySeenHuIds, huAssignmentRecord.getM_TU_HU_ID());
					addIfNotZero(alreadySeenHuIds, huAssignmentRecord.getM_LU_HU_ID());
				}
			}
			else if (huAssignmentRecord.getM_TU_HU_ID() > 0)
			{
				if (alreadySeenHuIds.add(huAssignmentRecord.getM_TU_HU_ID()))
				{
					result.add(HuAssignment.ofDataRecord(huAssignmentRecord));
					addIfNotZero(alreadySeenHuIds, huAssignmentRecord.getM_LU_HU_ID());
				}
			}
			else if (huAssignmentRecord.getM_LU_HU_ID() > 0)
			{
				if (alreadySeenHuIds.add(huAssignmentRecord.getM_LU_HU_ID()))
				{
					result.add(HuAssignment.ofDataRecord(huAssignmentRecord));
				}
			}
			else if (alreadySeenHuIds.add(huAssignmentRecord.getM_HU_ID()))
			{
				result.add(HuAssignment.ofDataRecord(huAssignmentRecord));
			}
		}
		return result.build();
	}

	@VisibleForTesting
	List<I_M_HU_Assignment> retrieveOrderedHUAssignmentRecords(@NonNull final Object model)
	{
		return queryBL
				.createQueryBuilder(I_M_HU_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_AD_Table_ID, getModelTableId(model))
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_Record_ID, getId(model))
				.orderBy() // the ordering is crucial; we need to see the most "specific" records first
				.addColumn(I_M_HU_Assignment.COLUMNNAME_VHU_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_HU_Assignment.COLUMNNAME_M_TU_HU_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_HU_Assignment.COLUMNNAME_M_LU_HU_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_HU_Assignment.COLUMNNAME_M_HU_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				.create()
				.list();
	}

	private void addIfNotZero(final Set<Integer> alreadySeenHuIds, final int id)
	{
		if (id > 0)
		{
			alreadySeenHuIds.add(id);
		}
	}
}
