package de.metas.vertical.pharma.model;

import java.sql.Timestamp;

import javax.annotation.Nullable;

public interface I_C_BPartner extends org.compiere.model.I_C_BPartner
{
	// @formatter:off
	public static final String COLUMNNAME_IsPharmaciePermission = "IsPharmaciePermission";
	public void setIsPharmaciePermission(boolean IsPharmaciePermission);
	public boolean isPharmaciePermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Pharmaproductpermlaw52 = "Pharmaproductpermlaw52";
	public void setPharmaproductpermlaw52(java.lang.String Pharmaproductpermlaw52);
	public java.lang.String getPharmaproductpermlaw52();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaWholesalePermission = "IsPharmaWholesalePermission";
	public void setIsPharmaWholesalePermission(boolean IsPharmaWholesalePermission);
	public boolean isPharmaWholesalePermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaAgentPermission = "IsPharmaAgentPermission";
	public void setIsPharmaAgentPermission(boolean IsPharmaAgentPermission);
	public boolean isPharmaAgentPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsVeterinaryPharmacyPermission = "IsVeterinaryPharmacyPermission";
	public void setIsVeterinaryPharmacyPermission(boolean IsVeterinaryPharmacyPermission);
	public boolean isVeterinaryPharmacyPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaManufacturerPermission = "IsPharmaManufacturerPermission";
	public void setIsPharmaManufacturerPermission(boolean IsPharmaManufacturerPermission);
	public boolean isPharmaManufacturerPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaVendorManufacturerPermission = "IsPharmaVendorManufacturerPermission";

	public void setIsPharmaVendorManufacturerPermission(boolean IsPharmaVendorManufacturerPermission);
	public boolean isPharmaVendorManufacturerPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaVendorAgentPermission = "IsPharmaVendorAgentPermission";
	public void setIsPharmaVendorAgentPermission(boolean IsPharmaVendorAgentPermission);
	public boolean isPharmaVendorAgentPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaVendorWholesalePermission = "IsPharmaVendorWholesalePermission";
	public void setIsPharmaVendorWholesalePermission(boolean IsPharmaVendorWholesalePermission);
	public boolean isPharmaVendorWholesalePermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaVendorNarcoticsPermission = "IsPharmaVendorNarcoticsPermission";
	public void setIsPharmaVendorNarcoticsPermission(boolean IsPharmaVendorNarcoticsPermission);
	public boolean isPharmaVendorNarcoticsPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPharmaCustomerNarcoticsPermission = "IsPharmaCustomerNarcoticsPermission";
	public void setIsPharmaCustomerNarcoticsPermission(boolean IsPharmaCustomerNarcoticsPermission);
	public boolean isPharmaCustomerNarcoticsPermission();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ShipmentPermissionPharma = "ShipmentPermissionPharma";
	public void setShipmentPermissionPharma(@Nullable String ShipmentPermissionPharma);
	public String getShipmentPermissionPharma();

	public static final String ShipmentPermissionPharma_TypeA = "A";
	public static final String ShipmentPermissionPharma_TypeB = "B";
	public static final String ShipmentPermissionPharma_TypeC = "C";
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ReceiptPermissionPharma = "ReceiptPermissionPharma";
	public void setReceiptPermissionPharma(@Nullable String ReceiptPermissionPharma);
	public String getReceiptPermissionPharma();

	public static final String ReceiptPermissionPharma_TypeA = "A";
	public static final String ReceiptPermissionPharma_TypeB = "B";
	public static final String ReceiptPermissionPharma_TypeC = "C";
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ShipmentPermissionChangeDate = "ShipmentPermissionChangeDate";
	public void setShipmentPermissionChangeDate(@Nullable Timestamp ShipmentPermissionChangeDate);
	public Timestamp getShipmentPermissionChangeDate();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ReceiptPermissionChangeDate = "ReceiptPermissionChangeDate";
	public void setReceiptPermissionChangeDate(@Nullable Timestamp ReceiptPermissionChangeDate);
	public Timestamp getReceiptPermissionChangeDate();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_Region = "Region";
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
	public void setShelfLifeMinDays(int ShelfLifeMinDays);
	public int getShelfLifeMinDays();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_WeekendOpeningTimes = "WeekendOpeningTimes";
	public void setWeekendOpeningTimes(String ShelfLifeMinDays);
	public String getWeekendOpeningTimes();
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

	// @formatter:off
	public static final String COLUMNNAME_IFA_Manufacturer = "IFA_Manufacturer";
	public void setIFA_Manufacturer(String IFA_Manufacturer);
	public String getIFA_Manufacturer();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_BTM = "BTM";
	public void setBTM(String BTM);
	public String getBTM();
	// @formater:on
}
