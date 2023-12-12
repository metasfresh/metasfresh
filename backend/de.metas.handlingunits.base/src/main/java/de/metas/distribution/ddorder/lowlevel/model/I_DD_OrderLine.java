package de.metas.distribution.ddorder.lowlevel.model;

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


import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

import java.math.BigDecimal;

/**
 * @author cg
 *
 */
public interface I_DD_OrderLine extends org.eevolution.model.I_DD_OrderLine
{
	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product M_HU_PI_Item_Product);
	int getM_HU_PI_Item_Product_ID();
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException;
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_QtyEnteredTU = "QtyEnteredTU";
	BigDecimal getQtyEnteredTU();
	void setQtyEnteredTU(BigDecimal QtyEnteredTU);
	// @formatter:on
}
