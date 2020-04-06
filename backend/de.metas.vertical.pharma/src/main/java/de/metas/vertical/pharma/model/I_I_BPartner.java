package de.metas.vertical.pharma.model;

public interface I_I_BPartner extends org.compiere.model.I_I_BPartner
{
	public static final String COLUMNNAME_IsPharmaciePermission = "IsPharmaciePermission";

	public void setIsPharmaciePermission(boolean IsPharmaciePermission);

	public boolean isPharmaciePermission();

	public static final String COLUMNNAME_Pharmaproductpermlaw52 = "Pharmaproductpermlaw52";

	public void setPharmaproductpermlaw52(java.lang.String Pharmaproductpermlaw52);

	public java.lang.String getPharmaproductpermlaw52();

	// @formatter:off
	public static final String COLUMNNAME_Regions = "Region";
	public void setRegion(String Region);
	public String getRegion();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_SalesResponsible = "SalesResponsible";
	public void setSalesResponsible(String SalesResponsible);
	public String getSalesResponsible();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_PurchaseGroup = "PurchaseGroup";
	public void setPurchaseGroup(String PurchaseGroup);
	public String getPurchaseGroup();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_AssociationMembership = "AssociationMembership";
	public void setAssociationMembership(String AssociationMembership);
	public String getAssociationMembership();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ShipmentPermissionPharma_Old = "ShipmentPermissionPharma_Old";
	public void setShipmentPermissionPharma_Old(String ShipmentPermissionPharma_Old);
	public String getShipmentPermissionPharma_Old();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_PermissionPharmaType = "PermissionPharmaType";
	public void setPermissionPharmaType(String PermissionPharmaType);
	public String getPermissionPharmaType();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ShelfLifeMinDays = "ShelfLifeMinDays";
	@Override
	public void setShelfLifeMinDays(int ShelfLifeMinDays);
	@Override
	public int getShelfLifeMinDays();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_WeekendOpeningTimes = "WeekendOpeningTimes";
	public void setWeekendOpeningTimes(String ShelfLifeMinDays);
	public String getWeekendOpeningTimes();
	// @formatter:on

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

	// @formatter:off
	public static final String COLUMNNAME_VendorResponsible = "VendorResponsible";
	public void setVendorResponsible(String VendorResponsible);
	public String getVendorResponsible();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_MinimumOrderValue = "MinimumOrderValue";
	public void setMinimumOrderValue(String MinimumOrderValue);
	public String getMinimumOrderValue();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_RetourFax = "RetourFax";
	public void setRetourFax(String RetourFax);
	public String getRetourFax();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Pharma_Phone = "Pharma_Phone";
	public void setPharma_Phone(String Pharma_Phone);
	public String getPharma_Phone();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Contacts = "Contacts";
	public void setContacts(String Contacts);
	public String getContacts();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Pharma_Fax = "Pharma_Fax";
	public void setPharma_Fax(String Pharma_Fax);
	public String getPharma_Fax();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_StatusInfo = "StatusInfo";
	public void setStatusInfo(String StatusInfo);
	public String getStatusInfo();
	// @formatter:on
}
