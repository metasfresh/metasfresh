package de.metas.shipping.interfaces;

public interface I_M_Package extends org.compiere.model.I_M_Package
{
	// public static final String COLUMNNAME_IsClosed = "IsClosed";
	// public boolean isClosed();
	// public void setIsClosed(boolean IsClosed);
	
	public static final String COLUMNNAME_Processed = "Processed";
	public boolean isProcessed();
	public void setProcessed(boolean Processed);
	
	@Deprecated
	public int getM_PackagingContainer_ID();
	
	@Deprecated
	public static final String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";
	@Deprecated
	public int getM_Warehouse_Dest_ID();
}
