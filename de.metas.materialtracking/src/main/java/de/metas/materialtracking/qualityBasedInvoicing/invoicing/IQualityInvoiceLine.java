package de.metas.materialtracking.qualityBasedInvoicing.invoicing;

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

import java.math.BigDecimal;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.pricing.IPricingResult;

/**
 * See https://drive.google.com/file/d/0B-AaY-YNDnR5b045VGJsdVhRUGc/view
 *
 * @author tsa
 */
public interface IQualityInvoiceLine
{
	/**
	 *
	 * @return the group to which this instance belongs
	 */
	IQualityInvoiceLineGroup getGroup();

	/**
	 * i.e. Artikel
	 *
	 * @return
	 */
	I_M_Product getM_Product();

	/**
	 * Alternative to {@link #getM_Product()}
	 *
	 * @return
	 */
	String getProductName();

	/**
	 * i.e. Abzug in %
	 *
	 * @return
	 */
	BigDecimal getPercentage();

	/**
	 * i.e. Menge
	 *
	 * @return
	 */
	BigDecimal getQty();

	/**
	 * i.e. Einheit
	 *
	 * @return
	 */
	I_C_UOM getC_UOM();

	/**
	 * i.e. Preis
	 *
	 * @return
	 */
	IPricingResult getPrice();

	/**
	 *
	 * @return true if this line will be displayed in invoice report; false if it's an internal detail used only for calculations or debugging
	 */
	boolean isDisplayed();

	String getDescription();

	/**
	 * @return Handling Units Informations (e.g. how many TUs, TU's name etc).
	 */
	IHandlingUnitsInfo getHandlingUnitsInfo();

	/**
	 * Returns the PP_Order associated with the given line (if any)
	 *
	 * @return
	 */
	I_PP_Order getPP_Order();
}
