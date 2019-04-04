package de.metas.material.cockpit.availableforsales.model;

import java.math.BigDecimal;

import org.compiere.model.I_AD_Color;

/*
 * #%L
 * metasfresh-material-cockpit
 * %%
 * Copyright (C) 2019 metas GmbH
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

public interface I_C_OrderLine extends org.compiere.model.I_C_OrderLine
{
	// @formatter:off
	public static final String COLUMNNAME_QtyAvailableForSales = "QtyAvailableForSales";
	public BigDecimal getQtyAvailableForSales();
	public void setQtyAvailableForSales(BigDecimal qtyAvailableForSales);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_InsufficientQtyAvailaibleForSalesColor_ID = "InsufficientQtyAvailaibleForSalesColor_ID";
	public void setInsufficientQtyAvailableForSalesColor_ID(int insufficientQtyAvailableForSalesColor_ID);
	public void setInsufficientQtyAvailableForSalesColor(I_AD_Color insufficientQtyAvailableForSalesColor);
	public int getInsufficientQtyAvailableForSalesColor_ID();
	public I_AD_Color getInsufficientQtyAvailableForSalesColor();
	// @formatter:on
}
