package de.metas.vertical.pharma.model;

import java.sql.Timestamp;

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
	public static final String COLUMNNAME_ShipmentPermissionPharma = "ShipmentPermissionPharma";
	public void setShipmentPermissionPharma(String ShipmentPermissionPharma);
	public String getShipmentPermissionPharma();

	public static final String ShipmentPermissionPharma_TypeA = "A";
	public static final String ShipmentPermissionPharma_TypeB = "B";
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ReceiptPermissionPharma = "ReceiptPermissionPharma";
	public void setReceiptPermissionPharma(String ReceiptPermissionPharma);
	public String getReceiptPermissionPharma();

	public static final String ReceiptPermissionPharma_TypeA = "A";
	public static final String ReceiptPermissionPharma_TypeB = "B";
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ShipmentPermissionChangeDate = "ShipmentPermissionChangeDate";
	public void setShipmentPermissionChangeDate(Timestamp ShipmentPermissionChangeDate);
	public Timestamp getShipmentPermissionChangeDate();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_ReceiptPermissionChangeDate = "ReceiptPermissionChangeDate";
	public void setReceiptPermissionChangeDate(Timestamp ReceiptPermissionChangeDate);
	public Timestamp getReceiptPermissionChangeDate();
	// @formatter:on

}
