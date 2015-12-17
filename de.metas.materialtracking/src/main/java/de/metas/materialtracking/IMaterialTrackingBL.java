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

import org.adempiere.util.ISingletonService;

import de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_M_Material_Tracking_Ref;

public interface IMaterialTrackingBL extends ISingletonService
{
	String C_DocType_INVOICE_DOCSUBTYPE_QI_DownPayment = "DP";

	String C_DocType_INVOICE_DOCSUBTYPE_QI_FinalSettlement = "QI";

	String C_DocType_PPORDER_DOCSUBTYPE_QualityInspection = "QI";

	/**
	 *
	 * @return true if material tracking module is enabled
	 */
	boolean isEnabled();

	/**
	 * Enable/Disable material tracking module.
	 *
	 * NOTE: this is just a flag, it won't prevent anything but depending functionalities can decide if they shall perform or not, based on this flag.
	 *
	 * @param enabled
	 */
	void setEnabled(boolean enabled);

	/**
	 * Registers material tracking listener for given table name.
	 *
	 * When a model from given table name is linked or unlinked to a material tracking this listener will be called.
	 *
	 * @param tableName
	 * @param listener
	 */
	void addModelTrackingListener(final String tableName, final IMaterialTrackingListener listener);

	/**
	 * Link given model to a material tracking record. The beforeModelLinked() and afterModelLinked() methods of all registered {@link IMaterialTrackingListener}s will be called before resp. after the
	 * new {@link I_M_Material_Tracking_Ref} record is saved.
	 *
	 * If model was previously assigned to another material tracking and {@link MTLinkRequest#isAssumeNotAlreadyAssigned()} is <code>false</code>, then it will be unlinked first.
	 *
	 * @param req
	 */
	void linkModelToMaterialTracking(MTLinkRequest req);

	/**
	 * Unlink given model from ANY material tracking.
	 *
	 * @param model
	 * @return <code>true</code> if there was a <code>M_MaterialTracking_Ref</code> to delete.
	 */
	boolean unlinkModelFromMaterialTracking(Object model);

	/**
	 * Unlink given given <code>model</code> from the given <code>materialTracking </code>, if such a link exists.
	 *
	 * @param model
	 * @param materialTracking
	 * @return <code>true</code> if there was a <code>M_MaterialTracking_Ref</code> to delete.
	 */
	boolean unlinkModelFromMaterialTracking(Object model, I_M_Material_Tracking materialTracking);

	/**
	 * This method deletes the already existing lines and creates new ones based on the given {@link I_M_Material_Tracking_Report}
	 * 
	 * @param report
	 */
	void createOrUpdateMaterialTrackingReportLines(I_M_Material_Tracking_Report report);
}
