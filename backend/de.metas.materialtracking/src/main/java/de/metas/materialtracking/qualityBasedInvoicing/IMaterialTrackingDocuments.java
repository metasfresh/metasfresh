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

import java.util.Collection;
import java.util.List;

import org.compiere.model.I_M_PriceList_Version;

import de.metas.materialtracking.model.I_M_InOutLine;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;

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
	 * Return all "real" {@link IQualityInspectionOrder}s with {@link IQualityInspectionOrder#isQualityInspection()} <code>=true</code>, which linked to this material tracking,<br>
	 * ordered by the value of {@link IQualityInspectionOrder#getDateOfProduction()}.
	 *
	 * @return
	 */
	List<IQualityInspectionOrder> getQualityInspectionOrders();

	/**
	 * Gets {@link IQualityInspectionOrder} for the given model.
	 *
	 * NOTE: the returned {@link IQualityInspectionOrder} could contain another instance of given manufacturing order (identification is made by ID only)
	 *
	 * @return the actual quality inspection order or null if there is no quality inspection order
	 */
	IQualityInspectionOrder getQualityInspectionOrderOrNull();

	/**
	 * Links given model to material tracking.
	 *
	 * @param model
	 */
	void linkModelToMaterialTracking(Object model);

	List<IQualityInspectionOrder> getProductionOrdersForPLV(I_M_PriceList_Version plv);

	IVendorReceipt<I_M_InOutLine> getVendorReceiptForPLV(I_M_PriceList_Version plv);

	/**
	 * Allow this instance to threat the given <code>ppOrder</code> as if is was closed, even if that is not (yet) the case.
	 * Required when this we want to update the given ppOrder's data from is model interceptor right when the ppOrder is closed.
	 * At that stage, the PPOrder is not yet closed in the DB.
	 *
	 * @param ppOrder may not be <code>null</code>
	 * @task http://dewiki908/mediawiki/index.php/09657_WP-Auswertung_wird_beim_Schlie%C3%9Fen_nicht_erstellt_%28109750474442%29
	 */
	void considerPPOrderAsClosed(I_PP_Order ppOrder);

	/**
	 * This method is analog to {@link #considerPPOrderAsClosed(I_PP_Order)}.
	 * @param ppOrder
	 */
	void considerPPOrderAsNotClosed(I_PP_Order ppOrder);
	
	Collection<I_M_PriceList_Version> getPriceListVersions();

	IVendorInvoicingInfo getVendorInvoicingInfoForPLV(I_M_PriceList_Version plv);
}
