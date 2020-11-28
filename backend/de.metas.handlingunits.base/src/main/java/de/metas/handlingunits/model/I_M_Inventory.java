package de.metas.handlingunits.model;

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

public interface I_M_Inventory extends de.metas.adempiere.model.I_M_Inventory
{

	// @formatter:off
		public void setSnapshot_UUID (java.lang.String Snapshot_UUID);
		public java.lang.String getSnapshot_UUID();
		public static final org.adempiere.model.ModelColumn<I_M_Inventory, Object> COLUMN_Snapshot_UUID = new org.adempiere.model.ModelColumn<>(I_M_Inventory.class, "Snapshot_UUID", null);
		public static final String COLUMNNAME_Snapshot_UUID = "Snapshot_UUID";
	// @formatter:on
}
