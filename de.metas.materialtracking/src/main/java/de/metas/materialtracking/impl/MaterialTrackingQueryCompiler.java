package de.metas.materialtracking.impl;

/*
 * #%L
 * de.metas.materialtracking
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.process.DocAction;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

/**
 * Helper class used to compile {@link IMaterialTrackingQuery} to actual {@link IQuery} or {@link IQueryBuilder}.
 *
 * @author tsa
 *
 */
/* package */class MaterialTrackingQueryCompiler
{
	// Services
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	// Parameters
	private Properties _ctx;
	private String _trxName = ITrx.TRXNAME_ThreadInherited;

	public MaterialTrackingQueryCompiler()
	{
		super();
	}

	public MaterialTrackingQueryCompiler setCtx(final Properties ctx)
	{
		_ctx = ctx;
		return this;
	}

	private Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "ctx not null");
		return _ctx;
	}

	public MaterialTrackingQueryCompiler setContext(final Object contextProvider)
	{
		_ctx = InterfaceWrapperHelper.getCtx(contextProvider);
		_trxName = InterfaceWrapperHelper.getTrxName(contextProvider);
		return this;
	}

	private String getTrxName()
	{
		return _trxName;
	}

	public IQueryBuilder<I_M_Material_Tracking> createQueryBuilder(final IMaterialTrackingQuery queryVO)
	{
		Check.assumeNotNull(queryVO, "queryVO not null");

		final IQueryBuilder<I_M_Material_Tracking> queryBuilder = queryBL.createQueryBuilder(I_M_Material_Tracking.class, getCtx(), getTrxName())
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter();

		final IQueryOrderByBuilder<I_M_Material_Tracking> orderBy = queryBuilder.orderBy();

		final int productId = queryVO.getM_Product_ID();
		final String lot = queryVO.getLot();
		Check.assume(productId > 0 || !Check.isEmpty(lot, true), "Either productId or lot has to be set, but is: productId={}, lot={}", productId, lot);

		//
		// M_Product_ID
		if (productId > 0)
		{
			queryBuilder.addEqualsFilter(I_M_Material_Tracking.COLUMN_M_Product_ID, productId);
		}

		//
		// C_BPartner_ID
		final int bpartnerId = queryVO.getC_BPartner_ID();
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Tracking.COLUMN_C_BPartner_ID, null, bpartnerId);
		orderBy.addColumn(I_M_Material_Tracking.COLUMN_C_BPartner_ID, Direction.Descending, Nulls.Last);

		// TODO: ValidFrom, ValidTo

		//
		// Lot
		if (lot != null)
		{
			queryBuilder.addEqualsFilter(I_M_Material_Tracking.COLUMN_Lot, lot);
		}

		//
		// Processed
		final Boolean processed = queryVO.getProcessed();
		if (processed != null)
		{
			queryBuilder.addEqualsFilter(I_M_Material_Tracking.COLUMN_Processed, processed);
		}

		final Boolean completeFlatrateTerm = queryVO.getCompleteFlatrateTerm();
		if (completeFlatrateTerm != null && completeFlatrateTerm.booleanValue() == true)
		{
			queryBuilder.addCompareFilter(I_M_Material_Tracking.COLUMN_C_Flatrate_Term_ID, Operator.NOT_EQUAL, null)
					.andCollect(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, I_C_Flatrate_Term.class)
					.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocAction.STATUS_Completed)
					.addOnlyActiveRecordsFilter()
					.andCollectChildren(I_M_Material_Tracking.COLUMN_C_Flatrate_Term_ID, I_M_Material_Tracking.class);
		}

		//
		// Linked documents
		final List<?> linkedModels = queryVO.getWithLinkedDocuments();
		if (linkedModels != null && !linkedModels.isEmpty())
		{
			final IQuery<I_M_Material_Tracking_Ref> materialTrackingRefQuery = createMaterialTrackingRefQueryForModels(linkedModels);
			if (materialTrackingRefQuery != null)
			{
				queryBuilder.addInSubQueryFilter(
						I_M_Material_Tracking.COLUMN_M_Material_Tracking_ID,
						I_M_Material_Tracking_Ref.COLUMN_M_Material_Tracking_ID,
						materialTrackingRefQuery);

			}
			// TODO
		}

		return queryBuilder;

	}

	private final IQuery<I_M_Material_Tracking_Ref> createMaterialTrackingRefQueryForModels(final List<?> models)
	{
		final IQueryBuilder<I_M_Material_Tracking_Ref> queryBuilder = createMaterialTrackingRefQueryBuilderForModels(models);
		if (queryBuilder == null)
		{
			return null;
		}

		return queryBuilder.create();
	}

	public final IQueryBuilder<I_M_Material_Tracking_Ref> createMaterialTrackingRefQueryBuilderForModels(final List<?> models)
	{
		if (models == null || models.isEmpty())
		{
			return null;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Iterate models and build model filters
		final ICompositeQueryFilter<I_M_Material_Tracking_Ref> modelFilters = queryBL.createCompositeQueryFilter(I_M_Material_Tracking_Ref.class)
				.setJoinOr();
		for (final Object model : models)
		{
			if (model == null)
			{
				continue;
			}

			final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
			final int recordId = InterfaceWrapperHelper.getId(model);
			final ICompositeQueryFilter<I_M_Material_Tracking_Ref> filter = queryBL
					.createCompositeQueryFilter(I_M_Material_Tracking_Ref.class)
					.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_AD_Table_ID, adTableId)
					.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_Record_ID, recordId);

			modelFilters.addFilter(filter);
		}

		// No models provided
		if (modelFilters.isEmpty())
		{
			return null;
		}

		//
		// Create M_Material_Tracking_Ref query
		final IQueryBuilder<I_M_Material_Tracking_Ref> materialTrackingRefQueryBuilder = queryBL
				.createQueryBuilder(I_M_Material_Tracking_Ref.class, getCtx(), getTrxName())
				.filter(modelFilters);

		return materialTrackingRefQueryBuilder;
	}

	public final <T> IQueryBuilder<I_M_Material_Tracking_Ref> createMaterialTrackingRefQueryBuilderForModels(final IQueryBuilder<T> modelsQuery)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Class<T> modelClass = modelsQuery.getModelClass();
		final int modelTableId = InterfaceWrapperHelper.getTableId(modelClass);
		final String modelKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(modelClass);

		//
		// Create M_Material_Tracking_Ref query
		final IQueryBuilder<I_M_Material_Tracking_Ref> materialTrackingRefQueryBuilder = queryBL
				.createQueryBuilder(I_M_Material_Tracking_Ref.class, getCtx(), getTrxName())
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_AD_Table_ID, modelTableId)
				.addInSubQueryFilter(I_M_Material_Tracking_Ref.COLUMNNAME_Record_ID, modelKeyColumnName, modelsQuery.create());

		return materialTrackingRefQueryBuilder;
	}

	public final IQueryBuilder<I_M_Material_Tracking_Ref> createMaterialTrackingRefQueryBuilderForModel(final Object model)
	{
		final List<Object> models = Collections.singletonList(model);
		return createMaterialTrackingRefQueryBuilderForModels(models);
	}

}
