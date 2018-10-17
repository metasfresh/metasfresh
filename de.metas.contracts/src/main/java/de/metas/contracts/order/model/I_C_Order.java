package de.metas.contracts.order.model;

public interface I_C_Order extends org.compiere.model.I_C_Order
{
	public static String COLUMNNAME_Ref_FollowupOrder_ID = "Ref_FollowupOrder_ID";

	public int getRef_FollowupOrder_ID();

	public void setRef_FollowupOrder_ID(int Ref_FollowupOrder_ID);

	public static String COLUMNNAME_ContractStatus = "ContractStatus";
	public static final int CONTRACTSTATUS_AD_Reference_ID = 540918;
	public static final String CONTRACTSTATUS_Active = "A";
	public static final String CONTRACTSTATUS_Cancelled = "C";
	public static final String CONTRACTSTATUS_Extended = "E";

	public void setContractStatus(String ContractStatus);

	public java.lang.String getContractStatus();
}
