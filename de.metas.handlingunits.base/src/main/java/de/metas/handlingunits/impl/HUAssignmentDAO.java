package de.metas.handlingunits.impl;

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

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Assignment;

public class HUAssignmentDAO implements IHUAssignmentDAO
{
	/**
	 * Adds a filter that makes sure only <b>top level-HU</b> records with the given <code>AD_Table_ID</code> and in addition with <code>M_LU_HU_ID</code>, <code>M_TU_HU_ID</code> and
	 * <code>VHU_ID</code> being <code>null</code> will be selected.
	 *
	 * @param queryBuilder the builder that is augmented by this method
	 * @param adTableId
	 * @return
	 */
	private IQueryBuilder<I_M_HU_Assignment> applyCommonTopLevelFilters(final IQueryBuilder<I_M_HU_Assignment> queryBuilder, final int adTableId)
	{
		queryBuilder
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				//
				// Filter out entries which are specifically for other levels
				//
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_LU_HU_ID, null)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, null);
		return queryBuilder;
	}

	private IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentQuery(final Properties ctx, final int huId, final int adTableId, final int recordId, final String trxName)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, huId);

		return applyCommonTopLevelFilters(queryBuilder, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId);
	}

	public IQueryBuilder<I_M_HU_Assignment> retrieveHUAssignmentsForModelQuery(
			final Properties ctx,
			final int adTableId,
			final int recordId,
			final String trxName)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName);

		applyCommonTopLevelFilters(queryBuilder, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId);

		queryBuilder.orderBy()
				.addColumn(I_M_HU_Assignment.COLUMN_M_HU_Assignment_ID);

		return queryBuilder;
	}

	@Override
	public void assertNoHUAssignmentsForModel(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final boolean foundHUAssignments = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Assignment.class, model)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId)
				.addOnlyActiveRecordsFilter()
				.create()
				.match();
		if (foundHUAssignments)
		{
			throw new HUException("@HUAssignmentsFoundForModelError@: " + model);
		}
	}

	@Override
	public I_M_HU_Assignment retrieveHUAssignmentOrNull(final Properties ctx, final int huId, final int adTableId, final int recordId, final String trxName)
	{
		return retrieveHUAssignmentQuery(ctx, huId, adTableId, recordId, trxName)
				.create()
				.firstOnly(I_M_HU_Assignment.class);
	}

	@Override
	public List<I_M_HU_Assignment> retrieveHUAssignmentsForModel(final Object model)
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

		return retrieveHUAssignmentsForModelQuery(ctx, adTableId, recordId, trxName);
	}

	@Override
	public List<I_M_HU_Assignment> retrieveHUAssignmentsForModel(final Properties ctx, final int adTableId, final int recordId, final String trxName)
	{
		return retrieveHUAssignmentsForModelQuery(ctx, adTableId, recordId, trxName)
				.create()
				.list(I_M_HU_Assignment.class);
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
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_HU> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId)
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
		for (final Iterator<I_M_HU> husTopLevelIterator = husTopLevel.iterator(); husTopLevelIterator.hasNext();)
		{
			final I_M_HU hu = husTopLevelIterator.next();
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				husTopLevelIterator.remove();
				continue;
			}
		}

		// NOTE: this method will NOT exclude destroyed HUs.
		// Before changing this, please carefully check the depending API.

		return husTopLevel;
	}

	@Override
	public List<I_M_HU> retrieveTUHUsForModel(final Object model)
	{
		final IQueryBuilder<I_M_HU> queryBuilder = retrieveTUHUAssignmentsForModelQuery(model)
				.andCollect(I_M_HU_Assignment.COLUMN_M_TU_HU_ID);

		queryBuilder.orderBy()
				.addColumn(I_M_HU.COLUMN_M_HU_ID);

		// NOTE: this method will NOT exclude destroyed HUs.
		// Before changing this, please carefully check the depending API.

		return queryBuilder.create().list();
	}

	@Override
	public IQueryBuilder<I_M_HU_Assignment> retrieveTUHUAssignmentsForModelQuery(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_M_HU_Assignment.class, model)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId)
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, null) // TU is set
				.addOnlyActiveRecordsFilter();
	}

	@Override
	public List<I_M_HU_Assignment> retrieveIncludedHUAssignments(final I_M_HU_Assignment assignment)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, assignment);

		queryBuilder
				// references same record..
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, assignment.getAD_Table_ID())
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, assignment.getRecord_ID())
				// ..and same toplevel-HU..
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, assignment.getM_HU_ID());

		// ..but additionally references one of the HU's components (TU or LU)
		final ICompositeQueryFilter<I_M_HU_Assignment> subFilter = queryBL.createCompositeQueryFilter(I_M_HU_Assignment.class);
		subFilter
				.setJoinOr()
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMN_M_LU_HU_ID, null)
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, null);

		queryBuilder.filter(subFilter);

		//
		return queryBuilder.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public void deleteHUAssignments(final Object model, final Collection<I_M_HU> husToUnAssign, final String trxName)
	{
		Check.assumeNotNull(model, "model not null");
		Check.assumeNotNull(husToUnAssign, "husToUnAssign not null");

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final Set<Integer> huIds = new HashSet<>(husToUnAssign.size());
		for (final I_M_HU hu : husToUnAssign)
		{
			huIds.add(hu.getM_HU_ID());
		}

		if (huIds.isEmpty())
		{
			return;
		}

		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName);

		//
		// Note that here we don't want to skip anything; we want the HUAssignmentBL to do it's job, so we clean everything up
		queryBuilder
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId)
				.addInArrayOrAllFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, huIds)
				.create()
				.delete();
	}

	@Override
	public boolean hasHUAssignmentsForModel(final Object model)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Assignment.class, model)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId)
				.create()
				.match();
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

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Assignment.class, luAssignment)
				.addOnlyActiveRecordsFilter() // only active assignments are counted
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId) // for same kind of model
				.addCompareFilter(I_M_HU_Assignment.COLUMN_Record_ID, Operator.LESS, recordId) // on a model which was created before ours
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_LU_HU_ID, luHUId) // for our LU
				.addEqualsFilter(I_M_HU_Assignment.COLUMNNAME_VHU_ID, luAssignment.getVHU_ID()) // 08564 : also check the vhu
				//
				.create()
				.match();

	}

	@Override
	public List<I_M_HU_Assignment> retrieveTableHUAssignments(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = retrieveTableHUAssignmentsQuery(contextProvider, adTableId, hu);
		return queryBuilder.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public List<I_M_HU_Assignment> retrieveTableHUAssignmentsNoTopFilter(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = retrieveTableHUAssignmentsQueryNoTopLevel(contextProvider, adTableId, hu);
		return queryBuilder.create()
				.list(I_M_HU_Assignment.class);
	}

	@Override
	public int retrieveTableHUAssignmentsCount(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = retrieveTableHUAssignmentsQuery(contextProvider, adTableId, hu);
		return queryBuilder.create()
				.count();
	}

	@Override
	public final IQueryBuilder<I_M_HU_Assignment> retrieveTableHUAssignmentsQuery(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Assignment.class, contextProvider)
		// .addOnlyActiveRecordsFilter()
		;

		applyCommonTopLevelFilters(queryBuilder, adTableId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, hu.getM_HU_ID());
		return queryBuilder;
	}

	private final IQueryBuilder<I_M_HU_Assignment> retrieveTableHUAssignmentsQueryNoTopLevel(final IContextAware contextProvider, final int adTableId, final I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Assignment.class, contextProvider);

		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId);
		queryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, hu.getM_HU_ID());
		return queryBuilder;
	}

	@Override
	public boolean hasDerivedTradingUnitAssignmentsOnLUTU(final Properties ctx, final Object model, final I_M_HU topLevelHU, final I_M_HU luHU, final I_M_HU tuHU, final String trxName)
	{
		return getDerivedTradingUnitAssignmentsQueryBuilder(ctx, model, topLevelHU, luHU, tuHU, trxName)
				.create()
				.match();
	}

	@Override
	public boolean hasDerivedTradingUnitAssignments(final Properties ctx, final Object model, final I_M_HU topLevelHU, final I_M_HU luHU, final I_M_HU tuHU, final String trxName)
	{
		final int recordId = InterfaceWrapperHelper.getId(model);

		return getDerivedTradingUnitAssignmentsQueryBuilder(ctx, model, topLevelHU, luHU, tuHU, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_Record_ID, recordId)
				//
				.create()
				.match();
	}

	private final IQueryBuilder<I_M_HU_Assignment> getDerivedTradingUnitAssignmentsQueryBuilder(final Properties ctx,
			final Object model,
			final I_M_HU topLevelHU,
			final I_M_HU luHU,
			final I_M_HU tuHU,
			final String trxName)
	{
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);

		final Integer luHUId = luHU == null ? null : luHU.getM_HU_ID();
		final Integer tuHUId = tuHU == null ? null : tuHU.getM_HU_ID();

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_HU_Assignment.class, ctx, trxName)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, adTableId)
				//
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, topLevelHU.getM_HU_ID())
				//
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_LU_HU_ID, luHUId)
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, tuHUId);
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
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int tableId = InterfaceWrapperHelper.getTableId(clazz);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(clazz);

		final IQueryBuilder<I_M_HU_Assignment> huAssigmentQueryBuilder = queryBL.createQueryBuilder(I_M_HU_Assignment.class, hu)
				.addOnlyContextClientOrSystem()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_Assignment.COLUMN_AD_Table_ID, tableId);
		if (topLevel)
		{
			huAssigmentQueryBuilder.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_HU_ID, hu.getM_HU_ID());
			applyCommonTopLevelFilters(huAssigmentQueryBuilder, tableId);
		}
		else
		{
			final ICompositeQueryFilter<I_M_HU_Assignment> filter = queryBL.createCompositeQueryFilter(I_M_HU_Assignment.class)
					.setJoinOr()
					.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_LU_HU_ID, hu.getM_HU_ID())
					.addEqualsFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, hu.getM_HU_ID())
					.addEqualsFilter(I_M_HU_Assignment.COLUMN_VHU_ID, hu.getM_HU_ID());
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
	public List<I_M_HU_Assignment> retrieveTableHUAssignmentsNoTopFilterTUMandatory(IContextAware contextProvider, int adTableId, I_M_HU hu)
	{
		final IQueryBuilder<I_M_HU_Assignment> queryBuilder = retrieveTableHUAssignmentsQueryNoTopLevel(contextProvider, adTableId, hu);
		return queryBuilder
				.addNotEqualsFilter(I_M_HU_Assignment.COLUMN_M_TU_HU_ID, null)
				.create()
				.list(I_M_HU_Assignment.class);
	}
}
