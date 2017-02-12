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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderByBuilder;
import org.adempiere.ad.dao.cache.impl.TableRecordCacheLocal;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_AttributeValue;
import org.eevolution.model.I_PP_Order;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.materialtracking.IMaterialTrackingQuery.OnMoreThanOneFound;
import de.metas.materialtracking.ch.lagerkonf.interfaces.I_C_Flatrate_Conditions;
import de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report_Line;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_QualityInsp_LagerKonf_Version;
import de.metas.materialtracking.model.IMaterialTrackingAware;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

public class MaterialTrackingDAO implements IMaterialTrackingDAO
{
	@Override
	public IMaterialTrackingQuery createMaterialTrackingQuery()
	{
		return new MaterialTrackingQuery();
	}

	@Override
	public de.metas.materialtracking.model.I_M_Material_Tracking retrieveMaterialTracking(final Properties ctx, final IMaterialTrackingQuery queryVO)
	{
		final IQueryBuilder<de.metas.materialtracking.model.I_M_Material_Tracking> queryBuilder = new MaterialTrackingQueryCompiler()
				.setCtx(ctx)
				.createQueryBuilder(queryVO);
		final IQuery<de.metas.materialtracking.model.I_M_Material_Tracking> query = queryBuilder.create();

		//
		// Execute query
		final OnMoreThanOneFound onMoreThanOneFound = queryVO.getOnMoreThanOneFound();
		if (onMoreThanOneFound == OnMoreThanOneFound.ThrowException)
		{
			return query.firstOnly(de.metas.materialtracking.model.I_M_Material_Tracking.class);
		}
		else if (onMoreThanOneFound == OnMoreThanOneFound.ReturnNull)
		{
			return query.firstOnlyOrNull(de.metas.materialtracking.model.I_M_Material_Tracking.class);
		}
		else if (onMoreThanOneFound == OnMoreThanOneFound.ReturnFirst)
		{
			return query.first(de.metas.materialtracking.model.I_M_Material_Tracking.class);
		}
		else
		{
			throw new IllegalArgumentException("Unsupported onMoreThanOneFound option: " + onMoreThanOneFound);
		}
	}

	@Override
	public I_M_Material_Tracking_Ref createMaterialTrackingRefNoSave(final de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, final Object model)
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
	public List<I_M_Material_Tracking_Ref> retrieveMaterialTrackingRefForType(final de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, final Class<?> modelClass)
	{
		Check.assumeNotNull(materialTracking, "materialTracking not null");

		final int adTableId = InterfaceWrapperHelper.getTableId(modelClass);

		final IQueryBuilder<I_M_Material_Tracking_Ref> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Material_Tracking_Ref.class, materialTracking)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_M_Material_Tracking_ID, materialTracking.getM_Material_Tracking_ID())
				.addEqualsFilter(I_M_Material_Tracking_Ref.COLUMN_AD_Table_ID, adTableId);

		setDefaultOrderBy(queryBuilder.orderBy());

		return queryBuilder.create()
				.list(I_M_Material_Tracking_Ref.class);
	}

	@Override
	public de.metas.materialtracking.model.I_M_Material_Tracking retrieveMaterialTrackingForModel(final Object model)
	{
		final IMaterialTrackingAware materialTrackingAwareOrNull = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IMaterialTrackingAware.class);
		if (materialTrackingAwareOrNull != null)
		{
			return materialTrackingAwareOrNull.getM_Material_Tracking();
		}

		final I_M_Material_Tracking_Ref ref = retrieveMaterialTrackingRefForModel(model);
		if (ref == null)
		{
			return null;
		}
		return ref.getM_Material_Tracking();
	}

	@Override
	public <T> List<de.metas.materialtracking.model.I_M_Material_Tracking> retrieveMaterialTrackingForModels(final IQueryBuilder<T> modelsQuery)
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
				.list(de.metas.materialtracking.model.I_M_Material_Tracking.class);
	}

	@Override
	public de.metas.materialtracking.model.I_M_Material_Tracking retrieveMaterialTrackingByAttributeValue(final I_M_AttributeValue attributeValue)
	{
		if (attributeValue == null || attributeValue.getM_AttributeValue_ID() <= 0)
		{
			return null;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final de.metas.materialtracking.model.I_M_Material_Tracking materialTracking = queryBL.createQueryBuilder(de.metas.materialtracking.model.I_M_Material_Tracking.class, attributeValue)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.materialtracking.model.I_M_Material_Tracking.COLUMNNAME_M_AttributeValue_ID, attributeValue.getM_AttributeValue_ID())
				.create()
				.firstOnly(de.metas.materialtracking.model.I_M_Material_Tracking.class);

		if (materialTracking == null)
		{
			throw new AdempiereException("@NotFound@ @M_MaterialTracking_ID@"
					+ "\n @M_AttributeValue_ID@: " + attributeValue);
		}

		return materialTracking;
	}

	@Override
	public <T> List<T> retrieveReferences(final de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, final Class<T> referenceType)
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
	public <T> T retrieveReference(final de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, final Class<T> referenceType)
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

		final de.metas.materialtracking.model.I_M_Material_Tracking materialTracking = retrieveMaterialTrackingForModel(ppOrder);
		Check.assumeNotNull(materialTracking, "Inspection order has material tracking: {}", ppOrder);

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

	@Override
	public List<I_C_Flatrate_Term> retrieveC_Flatrate_Terms_For_MaterialTracking(final I_M_Material_Tracking materialTracking)
	{
		final int partnerID = materialTracking.getC_BPartner_ID();
		final int productID = materialTracking.getM_Product_ID();
		final I_M_QualityInsp_LagerKonf_Version lagerKonfVersion = materialTracking.getM_QualityInsp_LagerKonf_Version();
		final int lagerKonfID = lagerKonfVersion == null ? -1 : lagerKonfVersion.getM_QualityInsp_LagerKonf_ID();
		final Timestamp startDate = materialTracking.getValidFrom();
		final Timestamp endDate = materialTracking.getValidTo();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_C_Flatrate_Term> flatrateTerms = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class, materialTracking)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Conditions.COLUMNNAME_M_QualityInsp_LagerKonf_ID, lagerKonfID)
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_Flatrate_Term_ID, I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, partnerID)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_M_Product_ID, productID)
				.addOnlyActiveRecordsFilter();
		if (startDate != null)
		{
			flatrateTerms.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, startDate);
		}

		if (endDate != null)
		{
			flatrateTerms.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, endDate);
		}

		return flatrateTerms.create()
				.list(I_C_Flatrate_Term.class);

	}

	@Override
	public List<de.metas.materialtracking.model.I_M_Material_Tracking> retrieveMaterialTrackingsForPeriodAndOrg(
			final I_C_Period period,
			final I_AD_Org org)
	{
		final Timestamp periodEndDate = period.getEndDate();
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(de.metas.materialtracking.model.I_M_Material_Tracking.class, period)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(de.metas.materialtracking.model.I_M_Material_Tracking.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, periodEndDate)
				.addCompareFilter(de.metas.materialtracking.model.I_M_Material_Tracking.COLUMN_ValidTo, Operator.GREATER_OR_EQUAL, periodEndDate)
				.addInArrayOrAllFilter(I_M_Material_Tracking.COLUMN_AD_Org_ID, 0, org.getAD_Org_ID())
				.create()
				.list(de.metas.materialtracking.model.I_M_Material_Tracking.class);
	}

	@Override
	public void deleteMaterialTrackingReportLines(final I_M_Material_Tracking_Report report)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		queryBL.createQueryBuilder(I_M_Material_Tracking_Report_Line.class, report)
				.addEqualsFilter(I_M_Material_Tracking_Report_Line.COLUMN_M_Material_Tracking_Report_ID, report.getM_Material_Tracking_Report_ID())
				.create()
				.deleteDirectly();

	}

}
