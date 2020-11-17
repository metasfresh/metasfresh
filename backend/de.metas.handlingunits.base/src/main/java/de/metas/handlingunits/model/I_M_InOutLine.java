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

public interface I_M_InOutLine extends de.metas.materialtracking.model.I_M_InOutLine
{
	// code formatter will be off to maintain aspect

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
	public static final String COLUMNNAME_QtyCU_Calculated = "QtyCU_Calculated";
	public BigDecimal getQtyCU_Calculated();
	public void setQtyCU_Calculated(BigDecimal QtyCU_Calculated);
	// @formatter:on

	// @formatter:off
	// task: http://dewiki908/mediawiki/index.php/08228_Packvorschrift_%C3%A4nderbar_in_Lieferschen_plus_Recalc_plus_Abweichende_Menge_plus_Prozess_plus_Rolle_Spedition
	public static final String COLUMNNAME_IsManualPackingMaterial = "IsManualPackingMaterial";
	public void setIsManualPackingMaterial(boolean IsManualPackingMaterial);
	public boolean isManualPackingMaterial();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	// public void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	public int getM_HU_PI_Item_Product_ID();
	public void setM_HU_PI_Item_Product(I_M_HU_PI_Item_Product M_HU_PI_Item_Product) throws RuntimeException;
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product() throws RuntimeException;
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_Calculated_ID = "M_HU_PI_Item_Product_Calculated_ID";
	// public void setM_HU_PI_Item_Product_Calculated_ID(int M_HU_PI_Item_Product_Calculated_ID);
	public int getM_HU_PI_Item_Product_Calculated_ID();
	public void setM_HU_PI_Item_Product_Calculated(I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Calculated) throws RuntimeException;
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Calculated() throws RuntimeException;
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";
	// public void setM_HU_PI_Item_Product_Override_ID(int M_HU_PI_Item_Product_Override_ID);
	public int getM_HU_PI_Item_Product_Override_ID();
	public void setM_HU_PI_Item_Product_Override(I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Override) throws RuntimeException;
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Override() throws RuntimeException;
	// @formatter:on

	// @formatter:off
		public static final String COLUMNNAME_M_HU_LUTU_Configuration_ID = "M_HU_LUTU_Configuration_ID";
		public void setM_HU_LUTU_Configuration_ID(int M_HU_LUTU_Configuration_ID);
		public void setM_HU_LUTU_Configuration(I_M_HU_LUTU_Configuration M_HU_LUTU_Configuration);
		public int getM_HU_LUTU_Configuration_ID();
		public I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration();
		// @formatter:on
}
