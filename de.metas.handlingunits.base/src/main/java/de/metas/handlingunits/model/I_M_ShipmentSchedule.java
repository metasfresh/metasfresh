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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

public interface I_M_ShipmentSchedule extends
		de.metas.inoutcandidate.model.I_M_ShipmentSchedule //,
		//IHUDeliveryQuantities
{
	// code formatter will be off to maintain aspect

	// @formatter:off
	public void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU);
	public java.math.BigDecimal getQtyOrdered_TU();
    public static final String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";
    // @formatter:on

	// @formatter:off
	public void setQtyOrdered_LU (java.math.BigDecimal QtyOrdered_LU);
	public java.math.BigDecimal getQtyOrdered_LU();
    public static final String COLUMNNAME_QtyOrdered_LU = "QtyOrdered_LU";
    // @formatter:on


	// begin task 05130
	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";
	public void setM_HU_PI_Version_ID(int M_HU_PI_Version_ID);
	public int getM_HU_PI_Version_ID();
	public void setM_HU_PI_Version(I_M_HU_PI_Version M_HU_PI_Version) throws RuntimeException;
	public I_M_HU_PI_Version getM_HU_PI_Version() throws RuntimeException;
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_QtyProvided = "QtyProvided";
	public void setQtyProvided(BigDecimal QtyProvided);
	public BigDecimal getQtyProvided();
	// @formatter:on
	// end task 05130

	// begin task 05097
	// @formatter:off
	public static final String COLUMNNAME_PackDescription = "PackDescription";
	public String getPackDescription();
	public void setPackDescription(String packDescription);
	// @formatter:on

	// @formatter:off
	/** virtual column QtyItemCapacity **/
	public static final String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";
	public BigDecimal getQtyItemCapacity();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	public void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	public void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product M_HU_PI_Item_Product);
	public int getM_HU_PI_Item_Product_ID();
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException;
	// @formatter:on
	// end task 05097

	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";
	public void setM_HU_PI_Item_Product_Override_ID(int M_HU_PI_Item_Product_Override_ID);
	public void setM_HU_PI_Item_Product_Override(final I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Override);
	public int getM_HU_PI_Item_Product_Override_ID();
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Override() throws RuntimeException;
	// @formatter:on

	// 08255

	// @formatter:off
	public static final String COLUMNNAME_QtyTU_Calculated = "QtyTU_Calculated";
	public BigDecimal getQtyTU_Calculated();
	public void setQtyTU_Calculated(BigDecimal QtyTU_Calculated);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_QtyTU_Override = "QtyTU_Override";
	public BigDecimal getQtyTU_Override();
	public void setQtyTU_Override(BigDecimal QtyTU_Override);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID = "M_HU_PI_Item_Product_Calculated_ID";
	// public void setM_HU_PI_Item_Product_Calculated_ID(int M_HU_PI_Item_Product_Calculated_ID);
	public int getM_HU_PI_Item_Product_Calculated_ID();
	public void setM_HU_PI_Item_Product_Calculated(I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Calculated) throws RuntimeException;
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Calculated() throws RuntimeException;
	// @formatter:on
}
