package de.metas.handlingunits.model;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public interface I_M_InventoryLine extends org.compiere.model.I_M_InventoryLine
{
	// @formatter:off
		public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
		// public void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
		public int getM_HU_PI_Item_Product_ID();
		public void setM_HU_PI_Item_Product(I_M_HU_PI_Item_Product M_HU_PI_Item_Product) throws RuntimeException;
		public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException;
		// @formatter:on
		
		// @formatter:off
		public static final String COLUMNNAME_QtyTU = "QtyTU";
		public BigDecimal getQtyTU();
		public void setQtyTU(BigDecimal QtyTU);
		// @formatter:on
		
		// @formatter:off
		// Column IsInDispute
		public static final String COLUMNNAME_IsInDispute = "IsInDispute";
		public void setIsInDispute(boolean IsInDispute);
		public boolean isInDispute();
		// @formatter:on

		// @formatter:off
		// column QualityDiscountPercent
		public static final String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";
		public void setQualityDiscountPercent(BigDecimal QualityDiscountPercent);
		public BigDecimal getQualityDiscountPercent();
		// @formatter:on

		// @formatter:off
		// column QualityNote
		public static final String COLUMNNAME_QualityNote = "QualityNote";
		public void setQualityNote(String QualityNote);
		public String getQualityNote();
		// @formatter:on

}
