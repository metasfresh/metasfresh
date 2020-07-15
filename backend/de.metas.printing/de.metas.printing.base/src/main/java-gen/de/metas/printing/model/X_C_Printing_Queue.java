/** Generated Model - DO NOT CHANGE */
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Printing_Queue
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Printing_Queue extends org.compiere.model.PO implements I_C_Printing_Queue, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1655397069L;

    /** Standard Constructor */
    public X_C_Printing_Queue (Properties ctx, int C_Printing_Queue_ID, String trxName)
    {
      super (ctx, C_Printing_Queue_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Printing_Queue (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	@Override
	public int getAD_Archive_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
	}

	/** 
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	@Override
	public int getBill_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setC_Async_Batch_ID (int C_Async_Batch_ID)
	{
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
			set_Value (COLUMNNAME_C_Async_Batch_ID, Integer.valueOf(C_Async_Batch_ID));
	}

	@Override
	public int getC_Async_Batch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setCopies (int Copies)
	{
		set_Value (COLUMNNAME_Copies, Integer.valueOf(Copies));
	}

	@Override
	public int getCopies() 
	{
		return get_ValueAsInt(COLUMNNAME_Copies);
	}

	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	@Override
	public int getC_Printing_Queue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Printing_Queue_ID);
	}

	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
	public java.sql.Timestamp getDeliveryDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setIsDifferentInvoicingPartner (boolean IsDifferentInvoicingPartner)
	{
		throw new IllegalArgumentException ("IsDifferentInvoicingPartner is virtual column");	}

	@Override
	public boolean isDifferentInvoicingPartner() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDifferentInvoicingPartner);
	}

	@Override
	public void setIsForeignCustomer (boolean IsForeignCustomer)
	{
		throw new IllegalArgumentException ("IsForeignCustomer is virtual column");	}

	@Override
	public boolean isForeignCustomer() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsForeignCustomer);
	}

	@Override
	public void setIsPrintoutForOtherUser (boolean IsPrintoutForOtherUser)
	{
		set_Value (COLUMNNAME_IsPrintoutForOtherUser, Boolean.valueOf(IsPrintoutForOtherUser));
	}

	@Override
	public boolean isPrintoutForOtherUser() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrintoutForOtherUser);
	}

	/** 
	 * ItemName AD_Reference_ID=540735
	 * Reference name: ItemName
	 */
	public static final int ITEMNAME_AD_Reference_ID=540735;
	/** Rechnung = Rechnung */
	public static final String ITEMNAME_Rechnung = "Rechnung";
	/** Mahnung = Mahnung */
	public static final String ITEMNAME_Mahnung = "Mahnung";
	/** Mitgliedsausweis = Mitgliedsausweis */
	public static final String ITEMNAME_Mitgliedsausweis = "Mitgliedsausweis";
	/** Brief = Brief */
	public static final String ITEMNAME_Brief = "Brief";
	/** Sofort-Druck PDF = Sofort-Druck PDF */
	public static final String ITEMNAME_Sofort_DruckPDF = "Sofort-Druck PDF";
	/** PDF = PDF */
	public static final String ITEMNAME_PDF = "PDF";
	/** Versand/Wareneingang = Versand/Wareneingang */
	public static final String ITEMNAME_VersandWareneingang = "Versand/Wareneingang";
	@Override
	public void setItemName (java.lang.String ItemName)
	{

		set_Value (COLUMNNAME_ItemName, ItemName);
	}

	@Override
	public java.lang.String getItemName() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ItemName);
	}

	@Override
	public void setPrintingQueueAggregationKey (java.lang.String PrintingQueueAggregationKey)
	{
		set_Value (COLUMNNAME_PrintingQueueAggregationKey, PrintingQueueAggregationKey);
	}

	@Override
	public java.lang.String getPrintingQueueAggregationKey() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintingQueueAggregationKey);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}