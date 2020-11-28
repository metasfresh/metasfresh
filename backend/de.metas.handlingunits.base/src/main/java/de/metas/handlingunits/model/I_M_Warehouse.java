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

public interface I_M_Warehouse extends org.adempiere.warehouse.model.I_M_Warehouse
{
	//@formatter:off
	// task: http://dewiki908/mediawiki/index.php/08205_HU_Pos_Inventory_move_Button_%28105838505937%29
	public static final String COLUMNNAME_IsHUStorageDisabled = "IsHUStorageDisabled";
	public void setIsHUStorageDisabled(boolean IsHUStorageDisabled);
	public boolean isHUStorageDisabled();
	//@formatter:on

	//@formatter:off
	// task #1056
	public static final String COLUMNNAME_IsQualityReturnWarehouse = "IsQualityReturnWarehouse";
	public void setIsQualityReturnWarehouse(boolean isQualityReturnWarehouse);
	public boolean isQualityReturnWarehouse();
	//@formatter:on

}
