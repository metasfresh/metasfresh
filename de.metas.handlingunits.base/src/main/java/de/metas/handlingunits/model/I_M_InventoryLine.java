package de.metas.handlingunits.model;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface I_M_InventoryLine extends org.compiere.model.I_M_InventoryLine
{
	// @formatter:off
	String COLUMNNAME_M_HU_ID = "M_HU_ID";
	void setM_HU_ID(int M_HU_ID);
	int getM_HU_ID();
	//void setM_HU(I_M_HU M_HU);
	//I_M_HU getM_HU();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	// void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	int getM_HU_PI_Item_Product_ID();
	void setM_HU_PI_Item_Product(I_M_HU_PI_Item_Product M_HU_PI_Item_Product);
	// I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_QtyTU = "QtyTU";
	BigDecimal getQtyTU();
	void setQtyTU(BigDecimal QtyTU);
	// @formatter:on

	// @formatter:off
	// Column IsInDispute
	String COLUMNNAME_IsInDispute = "IsInDispute";
	void setIsInDispute(boolean IsInDispute);
	boolean isInDispute();
	// @formatter:on

	// @formatter:off
	// column QualityDiscountPercent
	String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";
	void setQualityDiscountPercent(BigDecimal QualityDiscountPercent);
	BigDecimal getQualityDiscountPercent();
	// @formatter:on

	// @formatter:off
	// column QualityNote
	String COLUMNNAME_QualityNote = "QualityNote";
	void setQualityNote(String QualityNote);
	String getQualityNote();
	// @formatter:on
}
