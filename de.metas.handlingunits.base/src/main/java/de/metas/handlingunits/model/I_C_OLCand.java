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




public interface I_C_OLCand extends de.metas.ordercandidate.model.I_C_OLCand
{
	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_ID = "M_HU_PI_Item_Product_ID";
	@Override
	void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	@Override
	int getM_HU_PI_Item_Product_ID();
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_Override_ID = "M_HU_PI_Item_Product_Override_ID";
	@Override
	void setM_HU_PI_Item_Product_Override_ID(int M_HU_PI_Item_Product_Override_ID);
	void setM_HU_PI_Item_Product_Override(I_M_HU_PI_Item_Product M_HU_PI_Item_Product_Override);
	@Override
	int getM_HU_PI_Item_Product_Override_ID();
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Override();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_M_HU_PI_Item_Product_Effective_ID = "M_HU_PI_Item_Product_Effective_ID";
	@Override
	void setM_HU_PI_Item_Product_Effective_ID(int M_HU_PI_Item_Product_Effective_ID);
	@Override
	int getM_HU_PI_Item_Product_Effective_ID();
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_Effective();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsManualQtyItemCapacity = "IsManualQtyItemCapacity";
	boolean isManualQtyItemCapacity();
	void setIsManualQtyItemCapacity(boolean isManualQtyItemCapacity);
	// @formatter:on
}
