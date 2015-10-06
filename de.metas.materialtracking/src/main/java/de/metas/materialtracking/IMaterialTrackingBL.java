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

import de.metas.materialtracking.model.I_M_Material_Tracking;

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
	 * Link given model to a material tracking record.
	 *
	 * If model was previously assigned to another material tracking, it will be unlinked first.
	 *
	 * @param req
	 */
	void linkModelToMaterialTracking(MTLinkRequest req);

	/**
	 * Unlink given model from ANY material tracking.
	 *
	 * @param model
	 */
	void unlinkModelFromMaterialTracking(Object model);

	/**
	 * Unlink given model from given material tracking (if exists).
	 *
	 * @param model
	 * @param materialTracking
	 */
	void unlinkModelFromMaterialTracking(Object model, I_M_Material_Tracking materialTracking);
}
