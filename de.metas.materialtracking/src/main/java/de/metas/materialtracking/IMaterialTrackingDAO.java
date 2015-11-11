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
import org.compiere.model.I_M_AttributeValue;
import org.eevolution.model.I_PP_Order;

import de.metas.materialtracking.model.I_M_Material_Tracking;
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
	I_M_Material_Tracking retrieveMaterialTracking(Properties ctx, IMaterialTrackingQuery query);

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
	I_M_Material_Tracking_Ref createMaterialTrackingRefNoSave(I_M_Material_Tracking materialTracking, Object model);

	List<I_M_Material_Tracking_Ref> retrieveMaterialTrackingRefForType(I_M_Material_Tracking materialTracking, Class<?> modelClass);

	/**
	 * If the given model has a <code>M_Material_Tracking_ID</code> column, then return that referenced material tracking.
	 * Otherwise, if the given model is referenced by a {@link I_M_Material_Tracking_Ref}, then return that reference's material tracking.
	 * Otherwise, return <code>null</code>.
	 * 
	 * @param model
	 * @return material tracking or <code>null</code>
	 */
	I_M_Material_Tracking retrieveMaterialTrackingForModel(Object model);

	/**
	 * Retrieves {@link I_M_Material_Tracking}s list for models specified by a query builder.
	 * 
	 * This method is very useful when performance is important and we don't want to fetch all the intermediary records.
	 * 
	 * @param modelsQuery models query
	 * @return {@link I_M_Material_Tracking}s
	 */
	<T> List<I_M_Material_Tracking> retrieveMaterialTrackingForModels(IQueryBuilder<T> modelsQuery);

	/**
	 *
	 * @param attributeValue
	 * @return material tracking or null if <code>attributeValue</code> is null
	 * @throws AdempiereException if material tracking was not found
	 */
	I_M_Material_Tracking retrieveMaterialTrackingByAttributeValue(I_M_AttributeValue attributeValue);

	/**
	 * Retrieves references of given type, order by their chronological order.
	 *
	 * @param materialTracking
	 * @param referenceType
	 * @return
	 */
	<T> List<T> retrieveReferences(I_M_Material_Tracking materialTracking, Class<T> referenceType);

	<T> T retrieveReference(I_M_Material_Tracking materialTracking, Class<T> referenceType);

	/**
	 *
	 * @param ppOrder
	 * @return inspection number
	 * @throws AdempiereException in case given order has no tracking number or inspection number could not be found
	 */
	int retrieveNumberOfInspection(I_PP_Order ppOrder);
}
