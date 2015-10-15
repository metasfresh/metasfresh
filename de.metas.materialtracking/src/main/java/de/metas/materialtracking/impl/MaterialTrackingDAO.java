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


import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_AttributeValue;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.materialtracking.IMaterialTrackingQuery.OnMoreThanOneFound;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

public class MaterialTrackingDAO implements IMaterialTrackingDAO
{
	@Override
	public IMaterialTrackingQuery createMaterialTrackingQuery()
	{
		return new MaterialTrackingQuery();
	}

	@Override
	public I_M_Material_Tracking retrieveMaterialTracking(final Properties ctx, final IMaterialTrackingQuery queryVO)
	{
		final IQueryBuilder<I_M_Material_Tracking> queryBuilder = new MaterialTrackingQueryCompiler()
		.setCtx(ctx)
		.createQueryBuilder(queryVO);
		final IQuery<I_M_Material_Tracking> query = queryBuilder.create();

		//
		// Execute query
		final OnMoreThanOneFound onMoreThanOneFound = queryVO.getOnMoreThanOneFound();
		if (onMoreThanOneFound == OnMoreThanOneFound.ThrowException)
		{
			return query.firstOnly(I_M_Material_Tracking.class);
		}
		else if (onMoreThanOneFound == OnMoreThanOneFound.ReturnNull)
		{
			final List<I_M_Material_Tracking> materialTrackings = query.list();
			if (materialTrackings.isEmpty())
			{
				return null;
			}
			else if (materialTrackings.size() == 1)
			{
				return materialTrackings.get(0);
			}
			else
				// materialTrackings.size() > 1
			{
				return null;
			}
		}
		else
		{
			throw new IllegalArgumentException("Unsupported onMoreThanOneFound option: " + onMoreThanOneFound);
		}
	}

	@Override
	public I_M_Material_Tracking_Ref createMaterialTrackingRefNoSave(final I_M_Material_Tracking materialTracking, final Object model)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");
		Check.assumeNotNull(model, "model not null");

		final IContextAware threadContextAware = Services.get(ITrxManager.class).createThreadContextAware(model);

		final I_M_Material_Tracking_Ref ref = InterfaceWrapperHelper.newInstance(I_M_Material_Tracking_Ref.class, threadContextAware);
		ref.setM_Material_Tracking(materialTracking);
		TableRecordCacheLocal.setReferencedValue(ref, model);
		ref.setIsActive(true);

		return ref;
	}

	@Override
	public I_M_Material_Tracking_Ref retrieveMaterialTrackingRefForModel(final Object model)
	{
		Check.assumeNotNull(model, "model not null");

		// 07669: Use the transaction of the thread and do not rely on the model's transaction
		final IContextAware threadContextAware = Services.get(ITrxManager.class).createThreadContextAware(model);

		return new MaterialTrackingQueryCompiler()
			.setContext(threadContextAware)
			.createMaterialTrackingRefQueryBuilderForModel(model)
			.addOnlyActiveRecordsFilter()
			.create()
			.firstOnly(I_M_Material_Tracking_Ref.class);
	}

	/* package */final void setDefaultOrderBy(final IQueryOrderByBuilder<I_M_Material_Tracking_Ref> orderByBuilder)
	{
		orderByBuilder
		.clear()
		// FIXME: make sure this is the right order
		.addColumn(I_M_Material_Tracking_Ref.COLUMNNAME_M_Material_Tracking_Ref_ID);
	}

	@Override
	public List<I_M_Material_Tracking_Ref> retrieveMaterialTrackingRefForType(final I_M_Material_Tracking materialTracking, final Class<?> modelClass)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final int adTableId = InterfaceWrapperHelper.getTableId(modelClass);

		final IQueryBuilder<I_M_Material_Tracking_Ref> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Material_Tracking_Ref.class)
				.setContext(materialTracking)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_M_Material_Tracking_ID, materialTracking.getM_Material_Tracking_ID())
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_AD_Table_ID, adTableId);

		setDefaultOrderBy(queryBuilder.orderBy());

		return queryBuilder.create()
				.list(I_M_Material_Tracking_Ref.class);
	}

	@Override
	public I_M_Material_Tracking retrieveMaterialTrackingForModel(final Object model)
	{
		final I_M_Material_Tracking_Ref ref = retrieveMaterialTrackingRefForModel(model);
		if (ref == null)
		{
			return null;
		}
		return ref.getM_Material_Tracking();
	}
	
	@Override
	public <T> List<I_M_Material_Tracking> retrieveMaterialTrackingForModels(final IQueryBuilder<T> modelsQuery)
	{
		Check.assumeNotNull(modelsQuery, "modelsQuery not null");

		// 07669: Use the transaction of the thread and do not rely on the model's transaction
		final IContextAware threadContextAware = Services.get(ITrxManager.class).createThreadContextAware(modelsQuery.getCtx());

		return new MaterialTrackingQueryCompiler()
			.setContext(threadContextAware)
			.createMaterialTrackingRefQueryBuilderForModels(modelsQuery)
			.addOnlyActiveRecordsFilter()
			.andCollect(I_M_Material_Tracking_Ref.COLUMN_M_Material_Tracking_ID)
			//
			.create()
			.list(I_M_Material_Tracking.class);
	}


	@Override
	public I_M_Material_Tracking retrieveMaterialTrackingByAttributeValue(final I_M_AttributeValue attributeValue)
	{
		if (attributeValue == null || attributeValue.getM_AttributeValue_ID() <= 0)
		{
			return null;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final I_M_Material_Tracking materialTracking = queryBL.createQueryBuilder(I_M_Material_Tracking.class)
				.setContext(attributeValue)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Material_Tracking.COLUMNNAME_M_AttributeValue_ID, attributeValue.getM_AttributeValue_ID())
				.create()
				.firstOnly(I_M_Material_Tracking.class);

		if (materialTracking == null)
		{
			throw new AdempiereException("@NotFound@ @M_MaterialTracking_ID@"
					+ "\n @M_AttributeValue_ID@: " + attributeValue);
		}

		return materialTracking;
	}
	
	@Override
	public <T> List<T> retrieveReferences(final I_M_Material_Tracking materialTracking, final Class<T> referenceType)
	{
		final List<I_M_Material_Tracking_Ref> references = retrieveMaterialTrackingRefForType(materialTracking, referenceType);
		final List<T> models = new ArrayList<>(references.size());
		for (final I_M_Material_Tracking_Ref ref : references)
		{
			final T model = retrieveReference(ref, referenceType);
			models.add(model);
		}

		return models;
	}

	public <T> T retrieveReference(final I_M_Material_Tracking_Ref materialTrackingRef, final Class<T> referenceType)
	{
		return TableRecordCacheLocal.getReferencedValue(materialTrackingRef, referenceType);
	}

	@Override
	public <T> T retrieveReference(final I_M_Material_Tracking materialTracking, final Class<T> referenceType)
	{
		final List<T> references = retrieveReferences(materialTracking, referenceType);
		if (references == null || references.isEmpty())
		{
			return null;
		}
		else if (references.size() == 1)
		{
			return references.get(0);
		}
		else
		{
			throw new AdempiereException("More than one referenced record found."
					+ "\n @M_Material_Tracking_ID@: " + materialTracking
					+ "\n @Type@: " + referenceType.getName());
		}
	}

	@Override
	public int retrieveNumberOfInspection(final I_PP_Order ppOrder)
	{
		// TODO: optimize a lot here!

		final I_M_Material_Tracking materialTracking = retrieveMaterialTrackingForModel(ppOrder);
		Check.assumeNotNull(materialTracking, "Inspection order has material tracking: {0}", ppOrder);

		final List<I_M_Material_Tracking_Ref> references = retrieveMaterialTrackingRefForType(materialTracking, I_PP_Order.class);

		int inspectionNumber = 0;
		final int ppOrderId = ppOrder.getPP_Order_ID();
		for (final I_M_Material_Tracking_Ref ref : references)
		{
			// Skip those manufacturing orders which are not quality inspections
			if (!ref.isQualityInspectionDoc())
			{
				continue;
			}

			// Increment inspection number
			// NOTE: first inspection number will be 1.
			inspectionNumber++;

			// If we found our document, we can return the inspection number right away
			if (ref.getRecord_ID() == ppOrderId)
			{
				return inspectionNumber;
			}
		}

		// shall not happen
		throw new AdempiereException("Inspection order was not found for material tracking"
				+ "\n @PP_Order_ID: " + ppOrder
				+ "\n @M_Material_Tracking_ID@: " + materialTracking);
	}

}
