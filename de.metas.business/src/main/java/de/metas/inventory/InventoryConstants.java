package de.metas.inventory;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class InventoryConstants
{
	/**
	 * Please keep in sync with list reference "HUAggregationType" {@code AD_Reference_ID=540976}
	 */
	public static final String HUAggregationType_SINGLE_HU = "S";
	public static final String HUAggregationType_MULTI_HU = "M";

	/**
	 * Please keep in sync with list reference "C_DocType SubType" {@code AD_Reference_ID=148}
	 */
	public static final String DocSubType_INVENTORY_WITH_SINGLE_HU = "ISH";
	public static final String DocSubType_INVENTORY_WITH_AGGREGATED_HUS = "IAH";

}
