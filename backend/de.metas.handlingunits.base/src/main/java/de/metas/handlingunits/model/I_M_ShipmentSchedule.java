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

import com.google.common.annotations.VisibleForTesting;

import java.math.BigDecimal;

public interface I_M_ShipmentSchedule extends
		de.metas.inoutcandidate.model.I_M_ShipmentSchedule // ,
// IHUDeliveryQuantities
{
	// code formatter will be off to maintain aspect

	// @formatter:off
	void setQtyOrdered_TU (java.math.BigDecimal QtyOrdered_TU);
	java.math.BigDecimal getQtyOrdered_TU();
    String COLUMNNAME_QtyOrdered_TU = "QtyOrdered_TU";
    // @formatter:on

	// @formatter:off
	void setQtyOrdered_LU (java.math.BigDecimal QtyOrdered_LU);
	java.math.BigDecimal getQtyOrdered_LU();
    String COLUMNNAME_QtyOrdered_LU = "QtyOrdered_LU";
    // @formatter:on

	// begin task 05130
	// @formatter:off
	String COLUMNNAME_M_HU_PI_Version_ID = "M_HU_PI_Version_ID";
	void setM_HU_PI_Version_ID(int M_HU_PI_Version_ID);
	int getM_HU_PI_Version_ID();
	void setM_HU_PI_Version(I_M_HU_PI_Version M_HU_PI_Version) throws RuntimeException;
	I_M_HU_PI_Version getM_HU_PI_Version() throws RuntimeException;
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_QtyProvided = "QtyProvided";
	void setQtyProvided(BigDecimal QtyProvided);
	BigDecimal getQtyProvided();
	// @formatter:on
	// end task 05130

	// begin task 05097
	// @formatter:off
	String COLUMNNAME_PackDescription = "PackDescription";
	String getPackDescription();
	void setPackDescription(String packDescription);
	// @formatter:on

	// @formatter:off
	/** virtual column QtyItemCapacity **/
	String COLUMNNAME_QtyItemCapacity = "QtyItemCapacity";
	BigDecimal getQtyItemCapacity();
	@Deprecated @VisibleForTesting void setQtyItemCapacity(final BigDecimal qtyItemCapacity);
	// @formatter:on

	// @formatter:off
	/**
	 * This is the "effective" value, coming from either {@link #getM_HU_PI_Item_Product_Calculated_ID()}<br>
	 * or {@link #getM_HU_PI_Item_Product_Override_ID()}.
	 */
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Override
	void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	//void setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product M_HU_PI_Item_Product);
	@Override
	int getM_HU_PI_Item_Product_ID();
	// public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();
	// @formatter:on
	// end task 05097

	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";
	@Override
	void setM_HU_PI_Item_Product_Override_ID(int M_HU_PI_Item_Product_Override_ID);
	//void setM_HU_PI_Item_Product_Override(final I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Override);
	@Override
	int getM_HU_PI_Item_Product_Override_ID();
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Override() throws RuntimeException;
	// @formatter:on

	// 08255

	// @formatter:off
	String COLUMNNAME_QtyTU_Calculated = "QtyTU_Calculated";
	BigDecimal getQtyTU_Calculated();
	void setQtyTU_Calculated(BigDecimal QtyTU_Calculated);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_QtyTU_Override = "QtyTU_Override";
	BigDecimal getQtyTU_Override();
	void setQtyTU_Override(BigDecimal QtyTU_Override);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID = "M_HU_PI_Item_Product_Calculated_ID";
	@Override
	void setM_HU_PI_Item_Product_Calculated_ID(int M_HU_PI_Item_Product_Calculated_ID);
	@Override
	int getM_HU_PI_Item_Product_Calculated_ID();
	//void setM_HU_PI_Item_Product_Calculated(I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Calculated) throws RuntimeException;
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Calculated() throws RuntimeException;
	// @formatter:on
}
