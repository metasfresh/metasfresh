package de.metas.materialtracking.qualityBasedInvoicing;

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

import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;

import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;

/**
 * Material tracking documents: contains all quality inspection orders together with other relevant documents for a tracking number.
 *
 * @author tsa
 *
 */
public interface IMaterialTrackingDocuments
{
	/**
	 *
	 * @return material tracking; never return null
	 */
	I_M_Material_Tracking getM_Material_Tracking();

	/**
	 * @return all {@link IQualityInspectionOrder}s linked to this material tracking, ordered by the value of {@link IQualityInspectionOrder#getDateOfProduction()}
	 */
	List<IQualityInspectionOrder> getQualityInspectionOrders();

	/**
	 * Gets {@link IQualityInspectionOrder} for the given model.
	 *
	 * NOTE: the returned {@link IQualityInspectionOrder} could contain another instance of given manufacturing order (identification is made by ID only)
	 *
	 * @return
	 */
	IQualityInspectionOrder getQualityInspectionOrderOrNull();

	/**
	 * Links given model to material tracking.
	 *
	 * @param model
	 */
	void linkModelToMaterialTracking(Object model);

	/**
	 *
	 * @param pricingSystem
	 */
	List<I_M_PriceList_Version> setPricingSystemLoadPLVs(I_M_PricingSystem pricingSystem);

	List<IQualityInspectionOrder> getQualityInspectionOrdersForPLV(I_M_PriceList_Version plv);

	IVendorReceipt<I_M_InOutLine> getVendorReceiptForPLV(I_M_PriceList_Version plv);
}
