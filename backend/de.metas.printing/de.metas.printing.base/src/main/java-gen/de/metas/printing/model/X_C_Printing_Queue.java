// Generated Model - DO NOT CHANGE
package de.metas.printing.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Printing_Queue
<<<<<<< HEAD
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Printing_Queue extends org.compiere.model.PO implements I_C_Printing_Queue, org.compiere.model.I_Persistent 
=======
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_Printing_Queue extends org.compiere.model.PO implements I_C_Printing_Queue, org.compiere.model.I_Persistent
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
{

	private static final long serialVersionUID = 658998957L;

<<<<<<< HEAD
    /** Standard Constructor */
    public X_C_Printing_Queue (final Properties ctx, final int C_Printing_Queue_ID, @Nullable final String trxName)
    {
      super (ctx, C_Printing_Queue_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Printing_Queue (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }
=======
	/** Standard Constructor */
    public X_C_Printing_Queue (final Properties ctx, final int C_Printing_Queue_ID, @Nullable final String trxName)
	{
		super (ctx, C_Printing_Queue_ID, trxName);
	}

	/** Load Constructor */
    public X_C_Printing_Queue (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super (ctx, rs, trxName);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Archive getAD_Archive()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(final org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	@Override
	public void setAD_Archive_ID (final int AD_Archive_ID)
	{
<<<<<<< HEAD
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
=======
		if (AD_Archive_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, AD_Archive_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_Archive_ID() 
=======
	public int getAD_Archive_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
	}

<<<<<<< HEAD
	/** 
=======
	/**
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * AD_Language AD_Reference_ID=106
	 * Reference name: AD_Language
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getAD_Language() 
=======
	public java.lang.String getAD_Language()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW getAD_PrinterHW()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class);
	}

	@Override
	public void setAD_PrinterHW(final de.metas.printing.model.I_AD_PrinterHW AD_PrinterHW)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_ID, de.metas.printing.model.I_AD_PrinterHW.class, AD_PrinterHW);
	}

	@Override
	public void setAD_PrinterHW_ID (final int AD_PrinterHW_ID)
	{
<<<<<<< HEAD
		if (AD_PrinterHW_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else 
=======
		if (AD_PrinterHW_ID < 1)
			set_Value (COLUMNNAME_AD_PrinterHW_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_AD_PrinterHW_ID, AD_PrinterHW_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_PrinterHW_ID() 
=======
	public int getAD_PrinterHW_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_ID);
	}

	@Override
	public de.metas.printing.model.I_AD_PrinterHW_MediaTray getAD_PrinterHW_MediaTray()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class);
	}

	@Override
	public void setAD_PrinterHW_MediaTray(final de.metas.printing.model.I_AD_PrinterHW_MediaTray AD_PrinterHW_MediaTray)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrinterHW_MediaTray_ID, de.metas.printing.model.I_AD_PrinterHW_MediaTray.class, AD_PrinterHW_MediaTray);
	}

	@Override
	public void setAD_PrinterHW_MediaTray_ID (final int AD_PrinterHW_MediaTray_ID)
	{
<<<<<<< HEAD
		if (AD_PrinterHW_MediaTray_ID < 1) 
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else 
=======
		if (AD_PrinterHW_MediaTray_ID < 1)
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_AD_PrinterHW_MediaTray_ID, AD_PrinterHW_MediaTray_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_PrinterHW_MediaTray_ID() 
=======
	public int getAD_PrinterHW_MediaTray_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_PrinterHW_MediaTray_ID);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
<<<<<<< HEAD
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
=======
		if (AD_Process_ID < 1)
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_Process_ID() 
=======
	public int getAD_Process_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
<<<<<<< HEAD
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
=======
		if (AD_Role_ID < 0)
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_Role_ID() 
=======
	public int getAD_Role_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
<<<<<<< HEAD
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
=======
		if (AD_Table_ID < 1)
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_Table_ID() 
=======
	public int getAD_Table_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
<<<<<<< HEAD
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
=======
		if (AD_User_ID < 0)
			set_Value (COLUMNNAME_AD_User_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
<<<<<<< HEAD
	public int getAD_User_ID() 
=======
	public int getAD_User_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	@Override
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
<<<<<<< HEAD
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
=======
		if (Bill_BPartner_ID < 1)
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
	}

	@Override
<<<<<<< HEAD
	public int getBill_BPartner_ID() 
=======
	public int getBill_BPartner_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public void setBill_Location_ID (final int Bill_Location_ID)
	{
<<<<<<< HEAD
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
=======
		if (Bill_Location_ID < 1)
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_Bill_Location_ID, Bill_Location_ID);
	}

	@Override
<<<<<<< HEAD
	public int getBill_Location_ID() 
=======
	public int getBill_Location_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_Bill_Location_ID);
	}

	@Override
	public void setBill_User_ID (final int Bill_User_ID)
	{
<<<<<<< HEAD
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
=======
		if (Bill_User_ID < 1)
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_Bill_User_ID, Bill_User_ID);
	}

	@Override
<<<<<<< HEAD
	public int getBill_User_ID() 
=======
	public int getBill_User_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_Bill_User_ID);
	}

	@Override
	public void setC_Async_Batch_ID (final int C_Async_Batch_ID)
	{
<<<<<<< HEAD
		if (C_Async_Batch_ID < 1) 
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else 
=======
		if (C_Async_Batch_ID < 1)
			set_Value (COLUMNNAME_C_Async_Batch_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_C_Async_Batch_ID, C_Async_Batch_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_Async_Batch_ID() 
=======
	public int getC_Async_Batch_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_Async_Batch_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
<<<<<<< HEAD
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
=======
		if (C_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_BPartner_ID() 
=======
	public int getC_BPartner_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
<<<<<<< HEAD
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
=======
		if (C_BPartner_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_BPartner_Location_ID() 
=======
	public int getC_BPartner_Location_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
<<<<<<< HEAD
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
=======
		if (C_DocType_ID < 0)
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_Value (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_DocType_ID() 
=======
	public int getC_DocType_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}

	@Override
	public void setCopies (final int Copies)
	{
		set_Value (COLUMNNAME_Copies, Copies);
	}

	@Override
<<<<<<< HEAD
	public int getCopies() 
=======
	public int getCopies()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_Copies);
	}

	@Override
	public void setC_Printing_Queue_ID (final int C_Printing_Queue_ID)
	{
<<<<<<< HEAD
		if (C_Printing_Queue_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else 
=======
		if (C_Printing_Queue_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, C_Printing_Queue_ID);
	}

	@Override
<<<<<<< HEAD
	public int getC_Printing_Queue_ID() 
=======
	public int getC_Printing_Queue_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_C_Printing_Queue_ID);
	}

	@Override
	public void setDeliveryDate (final @Nullable java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	@Override
<<<<<<< HEAD
	public java.sql.Timestamp getDeliveryDate() 
=======
	public java.sql.Timestamp getDeliveryDate()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsTimestamp(COLUMNNAME_DeliveryDate);
	}

	@Override
	public void setIsDifferentInvoicingPartner (final boolean IsDifferentInvoicingPartner)
	{
		throw new IllegalArgumentException ("IsDifferentInvoicingPartner is virtual column");	}

	@Override
<<<<<<< HEAD
	public boolean isDifferentInvoicingPartner() 
=======
	public boolean isDifferentInvoicingPartner()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDifferentInvoicingPartner);
	}

	@Override
	public void setIsForeignCustomer (final boolean IsForeignCustomer)
	{
		throw new IllegalArgumentException ("IsForeignCustomer is virtual column");	}

	@Override
<<<<<<< HEAD
	public boolean isForeignCustomer() 
=======
	public boolean isForeignCustomer()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsForeignCustomer);
	}

	@Override
	public void setIsPrintoutForOtherUser (final boolean IsPrintoutForOtherUser)
	{
		set_Value (COLUMNNAME_IsPrintoutForOtherUser, IsPrintoutForOtherUser);
	}

	@Override
<<<<<<< HEAD
	public boolean isPrintoutForOtherUser() 
=======
	public boolean isPrintoutForOtherUser()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrintoutForOtherUser);
	}

<<<<<<< HEAD
	/** 
=======
	/**
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
	public void setItemName (final @Nullable java.lang.String ItemName)
	{
		set_Value (COLUMNNAME_ItemName, ItemName);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getItemName() 
=======
	public java.lang.String getItemName()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_ItemName);
	}

	@Override
	public void setPrintingQueueAggregationKey (final @Nullable java.lang.String PrintingQueueAggregationKey)
	{
		set_Value (COLUMNNAME_PrintingQueueAggregationKey, PrintingQueueAggregationKey);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getPrintingQueueAggregationKey() 
=======
	public java.lang.String getPrintingQueueAggregationKey()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_PrintingQueueAggregationKey);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
<<<<<<< HEAD
	public boolean isProcessed() 
=======
	public boolean isProcessed()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}
}