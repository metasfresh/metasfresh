// Generated Model - DO NOT CHANGE
package de.metas.externalreference.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ExternalReference
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_S_ExternalReference extends org.compiere.model.PO implements I_S_ExternalReference, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -549777537L;

    /** Standard Constructor */
    public X_S_ExternalReference (final Properties ctx, final int S_ExternalReference_ID, @Nullable final String trxName)
    {
      super (ctx, S_ExternalReference_ID, trxName);
    }

    /** Load Constructor */
    public X_S_ExternalReference (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setExternalReference (final String ExternalReference)
	{
		set_Value (COLUMNNAME_ExternalReference, ExternalReference);
	}

	@Override
	public String getExternalReference() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalReference);
	}

	@Override
	public void setExternalReferenceURL (final @Nullable String ExternalReferenceURL)
	{
		set_Value (COLUMNNAME_ExternalReferenceURL, ExternalReferenceURL);
	}

	@Override
	public String getExternalReferenceURL() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalReferenceURL);
	}

	/** 
	 * ExternalSystem AD_Reference_ID=541117
	 * Reference name: ExternalSystem
	 */
	public static final int EXTERNALSYSTEM_AD_Reference_ID=541117;
	/** Github = Github */
	public static final String EXTERNALSYSTEM_Github = "Github";
	/** Everhour = Everhour */
	public static final String EXTERNALSYSTEM_Everhour = "Everhour";
	/** ALBERTA = ALBERTA */
	public static final String EXTERNALSYSTEM_ALBERTA = "ALBERTA";
	/** Shopware6 = Shopware6 */
	public static final String EXTERNALSYSTEM_Shopware6 = "Shopware6";
	/** Other = Other */
	public static final String EXTERNALSYSTEM_Other = "Other";
	/** WooCommerce = WooCommerce */
	public static final String EXTERNALSYSTEM_WooCommerce = "WooCommerce";
	/** GRSSignum = GRSSignum */
	public static final String EXTERNALSYSTEM_GRSSignum = "GRSSignum";
	/** LeichUndMehl = LeichUndMehl */
	public static final String EXTERNALSYSTEM_LeichUndMehl = "LeichUndMehl";
	/** ProCareManagement = ProCareManagement */
	public static final String EXTERNALSYSTEM_ProCareManagement = "ProCareManagement";
	@Override
	public void setExternalSystem (final String ExternalSystem)
	{
		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	@Override
	public String getExternalSystem() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalSystem);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setReferenced_AD_Table_ID (final int Referenced_AD_Table_ID)
	{
		if (Referenced_AD_Table_ID < 1) 
			set_Value (COLUMNNAME_Referenced_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Referenced_AD_Table_ID, Referenced_AD_Table_ID);
	}

	@Override
	public int getReferenced_AD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Referenced_AD_Table_ID);
	}

	@Override
	public void setReferenced_Record_ID (final int Referenced_Record_ID)
	{
		if (Referenced_Record_ID < 1) 
			set_Value (COLUMNNAME_Referenced_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Referenced_Record_ID, Referenced_Record_ID);
	}

	@Override
	public int getReferenced_Record_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Referenced_Record_ID);
	}

	@Override
	public void setS_ExternalReference_ID (final int S_ExternalReference_ID)
	{
		if (S_ExternalReference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ExternalReference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ExternalReference_ID, S_ExternalReference_ID);
	}

	@Override
	public int getS_ExternalReference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_S_ExternalReference_ID);
	}

	/** 
	 * Type AD_Reference_ID=541127
	 * Reference name: ExternalReferenceType
	 */
	public static final int TYPE_AD_Reference_ID=541127;
	/** UserID = UserID */
	public static final String TYPE_UserID = "UserID";
	/** IssueID = IssueID */
	public static final String TYPE_IssueID = "IssueID";
	/** Time booking ID = TimeBookingID */
	public static final String TYPE_TimeBookingID = "TimeBookingID";
	/** MilestoneId = MilestonId */
	public static final String TYPE_MilestoneId = "MilestonId";
	/** Bpartner = BPartner */
	public static final String TYPE_Bpartner = "BPartner";
	/** BPartnerLocation = BPartnerLocation */
	public static final String TYPE_BPartnerLocation = "BPartnerLocation";
	/** Product = Product */
	public static final String TYPE_Product = "Product";
	/** ProductCategory = ProductCategory */
	public static final String TYPE_ProductCategory = "ProductCategory";
	/** PriceList = PriceList */
	public static final String TYPE_PriceList = "PriceList";
	/** PriceListVersion = PriceListVersion */
	public static final String TYPE_PriceListVersion = "PriceListVersion";
	/** ProductPrice = ProductPrice */
	public static final String TYPE_ProductPrice = "ProductPrice";
	/** BPartnerValue = BPartnerValue */
	public static final String TYPE_BPartnerValue = "BPartnerValue";
	/** Shipper = Shipper */
	public static final String TYPE_Shipper = "Shipper";
	/** Warehouse = Warehouse */
	public static final String TYPE_Warehouse = "Warehouse";
	@Override
	public void setType (final String Type)
	{
		set_ValueNoCheck (COLUMNNAME_Type, Type);
	}

	@Override
	public String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setVersion (final @Nullable String Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	@Override
	public String getVersion() 
	{
		return get_ValueAsString(COLUMNNAME_Version);
	}
}