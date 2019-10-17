package org.adempiere.warehouse.model;

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
	public static final String COLUMNNAME_IsQuarantineWarehouse = "IsQuarantineWarehouse";
	public boolean isQuarantineWarehouse();
	public void setIsQuarantineWarehouse(boolean IsQuarantineWarehouse);
	//@formatter:on
}
