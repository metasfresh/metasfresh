package de.metas.camel.inventory;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

@UtilityClass
class MetasfreshInventoryCsvConstants
{
	public static final String COLUMNNAME_WarehouseValue = "WarehouseValue";
	public static final String COLUMNNAME_LocatorValue = "LocatorValue";
	public static final String COLUMNNAME_InventoryDate = "InventoryDate";
	public static final String COLUMNNAME_ProductValue = "ProductValue";
	public static final String COLUMNNAME_QtyCount = "QtyCount";
	public static final String COLUMNNAME_ExternalLineId = "ExternalLineId";
	public static final String COLUMNNAME_BestBeforeDate = "BestBeforeDate";
	public static final String COLUMNNAME_LotNumber = "LotNumber";
	public static final String COLUMNNAME_HUAggregationType = "HUAggregationType";
}
