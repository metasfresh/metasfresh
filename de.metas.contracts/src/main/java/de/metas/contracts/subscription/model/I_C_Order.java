package de.metas.contracts.subscription.model;

public interface I_C_Order extends org.compiere.model.I_C_Order
{
	public static String COLUMNNAME_Ref_FollowupOrder_ID = "Ref_FollowupOrder_ID";

	public int getRef_FollowupOrder_ID();

	public void setRef_FollowupOrder_ID(int Ref_FollowupOrder_ID);

}
