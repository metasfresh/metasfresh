package org.adempiere.warehouse.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import org.compiere.model.I_AD_User;

public interface I_M_Warehouse extends org.compiere.model.I_M_Warehouse
{
	//@formatter:off
	public static final String COLUMNNAME_isPickingWarehouse = "isPickingWarehouse";
	//public void setIsPickingWarehouse(boolean isPickingWarehouse);
	public boolean isPickingWarehouse();
	//@formatter:on

	//@formatter:off
	public static final String COLUMNNAME_IsIssueWarehouse = "IsIssueWarehouse";
	public boolean isIssueWarehouse();
	public void setIsIssueWarehouse(boolean IsIssueWarehouse);
	//@formatter:on

	//@formatter:off
	public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";
	//public void setAD_User_ID(int _AD_User_ID);
	public void setAD_User(I_AD_User AD_User_ID);
	public I_AD_User getAD_User();
	public int getAD_User_ID();
	//@formatter:on

	//@formatter:off
	public static final String COLUMNNAME_IsQuarantineWarehouse = "IsQuarantineWarehouse";
	public boolean isQuarantineWarehouse();
	public void setIsQuarantineWarehouse(boolean IsQuarantineWarehouse);
	//@formatter:on
}
