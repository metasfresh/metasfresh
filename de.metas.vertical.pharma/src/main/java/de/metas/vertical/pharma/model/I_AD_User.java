package de.metas.vertical.pharma.model;

public interface I_AD_User extends org.compiere.model.I_AD_User
{
	// @formatter:off
	public static final String COLUMNNAME_IsDecider = "IsDecider";
	public void setIsDecider(boolean IsDecider);
	public boolean isDecider();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsManagement = "IsManagement";
	public void setIsManagement(boolean IsManagement);
	public boolean isManagement();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsMultiplier = "IsMultiplier";
	public void setIsMultiplier(boolean IsMultiplier);
	public boolean isMultiplier();
	// @formatter:on
}
