package de.metas.handlingunits.model;

/*
 * #%L
 * de.metas.handlingunits.base
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

public interface I_C_InvoiceLine extends de.metas.adempiere.model.I_C_InvoiceLine
{

	// @formatter:off
	public static final String COLUMNNAME_QtyEnteredTU = "QtyEnteredTU";
	public BigDecimal getQtyEnteredTU();
	public void setQtyEnteredTU(BigDecimal QtyEnteredTU);
	// @formatter:on
	
	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	public void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product M_HU_PI_Item_Product);
	public int getM_HU_PI_Item_Product_ID();
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException;
	// @formatter:on

}
