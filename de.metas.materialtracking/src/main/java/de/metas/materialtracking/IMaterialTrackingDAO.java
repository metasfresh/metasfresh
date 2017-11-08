package de.metas.materialtracking;

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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_M_AttributeValue;
import org.eevolution.model.I_PP_Order;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.materialtracking.ch.lagerkonf.interfaces.I_M_Material_Tracking;
import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

public interface IMaterialTrackingDAO extends ISingletonService
{

	IMaterialTrackingQuery createMaterialTrackingQuery();

	/**
	 *
	 * @param ctx
	 * @param query
	 * @return Material tracking or null
	 */
	de.metas.materialtracking.model.I_M_Material_Tracking retrieveMaterialTracking(Properties ctx, IMaterialTrackingQuery query);

	/**
	 * Retrieve the reference, using the threadContextAware of the given <code>model</code>. Background: new M_Material_Tracking_Refs are created in the thread's inherited transaction, so if we want
	 * to find then without a previous commit, we need to use that same trx.
	 *
	 * @param model
	 * @return
	 * @see org.adempiere.ad.trx.api.ITrxManager#createThreadContextAware(Object)
	 */
	I_M_Material_Tracking_Ref retrieveMaterialTrackingRefForModel(Object model);

	/**
	 * Create material tracking reference (draft). Set its trxName to "thread inherited"
	 *
	 * NOTE: this is an internal API method, don't call it
	 *
	 * @param materialTracking
	 * @param model
	 * @return
	 */
	I_M_Material_Tracking_Ref createMaterialTrackingRefNoSave(de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, Object model);

	/**
	 *
	 * Retrieve the material tracking refs for the given {@link de.metas.materialtracking.model.I_M_Material_Tracking} and model's {@link I_AD_Table}
	 *
	 * @param materialTracking
	 * @param modelClass
	 * @return
	 */
	List<I_M_Material_Tracking_Ref> retrieveMaterialTrackingRefForType(de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, Class<?> modelClass);

	/**
	 * If the given model has a <code>M_Material_Tracking_ID</code> column, then return that referenced material tracking (=> might be <code>null</code>).
	 * Otherwise, if the given model is referenced by a {@link I_M_Material_Tracking_Ref}, then return that reference's material tracking.
	 * Otherwise, return <code>null</code>.
	 *
	 * @param model
	 * @return material tracking or <code>null</code>
	 */
	de.metas.materialtracking.model.I_M_Material_Tracking retrieveMaterialTrackingForModel(Object model);

	/**
	 * Retrieves {@link de.metas.materialtracking.model.I_M_Material_Tracking}s list for models specified by a query builder.
	 *
	 * This method is very useful when performance is important and we don't want to fetch all the intermediary records.
	 *
	 * @param modelsQuery models query
	 * @return {@link de.metas.materialtracking.model.I_M_Material_Tracking}s
	 */
	<T> List<de.metas.materialtracking.model.I_M_Material_Tracking> retrieveMaterialTrackingForModels(IQueryBuilder<T> modelsQuery);

	/**
	 *
	 * @param attributeValue
	 * @return material tracking or null if <code>attributeValue</code> is null
	 * @throws AdempiereException if material tracking was not found
	 */
	de.metas.materialtracking.model.I_M_Material_Tracking retrieveMaterialTrackingByAttributeValue(I_M_AttributeValue attributeValue);

	/**
	 * Retrieves references of given type, order by their chronological order.
	 *
	 * @param materialTracking
	 * @param referenceType
	 * @return
	 */
	<T> List<T> retrieveReferences(de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, Class<T> referenceType);

	<T> T retrieveReference(de.metas.materialtracking.model.I_M_Material_Tracking materialTracking, Class<T> referenceType);

	/**
	 *
	 * @param ppOrder
	 * @return inspection number
	 * @throws AdempiereException in case given order has no tracking number or inspection number could not be found
	 */
	int retrieveNumberOfInspection(I_PP_Order ppOrder);

	/**
	 * Retrieve the <code>C_Flatrate_Term</code> that fits the partner, product and lager konf of the given <code>materialTracking</code>
	 *
	 * @param materialTracking
	 * @return
	 */
	List<I_C_Flatrate_Term> retrieveC_Flatrate_Terms_For_MaterialTracking(I_M_Material_Tracking materialTracking);

	/**
	 * Retrieve all active the material tracking entries that fit the given period and org. Of the org's <code>AD_Org_ID > 0</code>, then also records (if any exist!) with <code>AD_Org_ID = 0</code>
	 * are returned.<br>
	 * For this logic only the period's end-date is used: Material tracking's validFrom must be <= periodEnd and validTO >= periodEnd
	 *
	 * @param period
	 * @param org
	 * @return list of the Material trackings that were found, EMpty list if none was found
	 */
	List<de.metas.materialtracking.model.I_M_Material_Tracking> retrieveMaterialTrackingsForPeriodAndOrg(I_C_Period period, I_AD_Org org);

	/**
	 * Delete directly all the lines of the given {@link I_M_Material_Tracking_Report}
	 *
	 * @param report
	 */
	void deleteMaterialTrackingReportLines(I_M_Material_Tracking_Report report);

}
