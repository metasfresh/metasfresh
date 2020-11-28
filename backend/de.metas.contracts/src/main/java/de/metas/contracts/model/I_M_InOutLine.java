package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

public interface I_M_InOutLine extends org.compiere.model.I_M_InOutLine
{
	// code formatter will be off to maintain aspect

	// @formatter:off
	public static final String COLUMNNAME_C_SubscriptionProgress_ID = "C_SubscriptionProgress_ID";
	public static final ModelColumn<I_M_InOutLine, I_C_SubscriptionProgress> COLUMN_C_SubscriptionProgress = new ModelColumn<>(I_M_InOutLine.class, "C_SubscriptionProgress", I_C_SubscriptionProgress.class);

	public void setC_SubscriptionProgress_ID(int C_SubscriptionProgress_ID);
	public int getC_SubscriptionProgress_ID();
	public void setC_SubscriptionProgress(I_C_SubscriptionProgress C_SubscriptionProgress);
	public I_C_SubscriptionProgress getC_SubscriptionProgress();
	// @formatter:on
}
