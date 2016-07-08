/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_MailConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_MailConfig extends org.compiere.model.PO implements I_AD_MailConfig, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -78905219L;

    /** Standard Constructor */
    public X_AD_MailConfig (Properties ctx, int AD_MailConfig_ID, String trxName)
    {
      super (ctx, AD_MailConfig_ID, trxName);
      /** if (AD_MailConfig_ID == 0)
        {
			setAD_MailBox_ID (0);
			setAD_MailConfig_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_MailConfig (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_MailBox getAD_MailBox() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_MailBox_ID, org.compiere.model.I_AD_MailBox.class);
	}

	@Override
	public void setAD_MailBox(org.compiere.model.I_AD_MailBox AD_MailBox)
	{
		set_ValueFromPO(COLUMNNAME_AD_MailBox_ID, org.compiere.model.I_AD_MailBox.class, AD_MailBox);
	}

	/** Set Mail Box.
		@param AD_MailBox_ID Mail Box	  */
	@Override
	public void setAD_MailBox_ID (int AD_MailBox_ID)
	{
		if (AD_MailBox_ID < 1) 
			set_Value (COLUMNNAME_AD_MailBox_ID, null);
		else 
			set_Value (COLUMNNAME_AD_MailBox_ID, Integer.valueOf(AD_MailBox_ID));
	}

	/** Get Mail Box.
		@return Mail Box	  */
	@Override
	public int getAD_MailBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MailBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Mail Configuration.
		@param AD_MailConfig_ID Mail Configuration	  */
	@Override
	public void setAD_MailConfig_ID (int AD_MailConfig_ID)
	{
		if (AD_MailConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_MailConfig_ID, Integer.valueOf(AD_MailConfig_ID));
	}

	/** Get Mail Configuration.
		@return Mail Configuration	  */
	@Override
	public int getAD_MailConfig_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MailConfig_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID 
		Prozess oder Bericht
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Prozess oder Bericht
	  */
	@Override
	public int getAD_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * ColumnUserTo AD_Reference_ID=540662
	 * Reference name: AD_User choices
	 */
	public static final int COLUMNUSERTO_AD_Reference_ID=540662;
	/** AD_User_ID = U */
	public static final String COLUMNUSERTO_AD_User_ID = "U";
	/** Bill_User_ID = BU */
	public static final String COLUMNUSERTO_Bill_User_ID = "BU";
	/** Set Column User To.
		@param ColumnUserTo Column User To	  */
	@Override
	public void setColumnUserTo (java.lang.String ColumnUserTo)
	{

		set_Value (COLUMNNAME_ColumnUserTo, ColumnUserTo);
	}

	/** Get Column User To.
		@return Column User To	  */
	@Override
	public java.lang.String getColumnUserTo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ColumnUserTo);
	}

	/** 
	 * CustomType AD_Reference_ID=540142
	 * Reference name: AD_MailConfig_CustomType
	 */
	public static final int CUSTOMTYPE_AD_Reference_ID=540142;
	/** org.compiere.util.Login = L */
	public static final String CUSTOMTYPE_OrgCompiereUtilLogin = "L";
	/** Set Custom Type.
		@param CustomType Custom Type	  */
	@Override
	public void setCustomType (java.lang.String CustomType)
	{

		set_Value (COLUMNNAME_CustomType, CustomType);
	}

	/** Get Custom Type.
		@return Custom Type	  */
	@Override
	public java.lang.String getCustomType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CustomType);
	}

	/** 
	 * DocBaseType AD_Reference_ID=183
	 * Reference name: C_DocType DocBaseType
	 */
	public static final int DOCBASETYPE_AD_Reference_ID=183;
	/** GLJournal = GLJ */
	public static final String DOCBASETYPE_GLJournal = "GLJ";
	/** GLDocument = GLD */
	public static final String DOCBASETYPE_GLDocument = "GLD";
	/** APInvoice = API */
	public static final String DOCBASETYPE_APInvoice = "API";
	/** APPayment = APP */
	public static final String DOCBASETYPE_APPayment = "APP";
	/** ARInvoice = ARI */
	public static final String DOCBASETYPE_ARInvoice = "ARI";
	/** ARReceipt = ARR */
	public static final String DOCBASETYPE_ARReceipt = "ARR";
	/** SalesOrder = SOO */
	public static final String DOCBASETYPE_SalesOrder = "SOO";
	/** ARProFormaInvoice = ARF */
	public static final String DOCBASETYPE_ARProFormaInvoice = "ARF";
	/** MaterialDelivery = MMS */
	public static final String DOCBASETYPE_MaterialDelivery = "MMS";
	/** MaterialReceipt = MMR */
	public static final String DOCBASETYPE_MaterialReceipt = "MMR";
	/** MaterialMovement = MMM */
	public static final String DOCBASETYPE_MaterialMovement = "MMM";
	/** PurchaseOrder = POO */
	public static final String DOCBASETYPE_PurchaseOrder = "POO";
	/** PurchaseRequisition = POR */
	public static final String DOCBASETYPE_PurchaseRequisition = "POR";
	/** MaterialPhysicalInventory = MMI */
	public static final String DOCBASETYPE_MaterialPhysicalInventory = "MMI";
	/** APCreditMemo = APC */
	public static final String DOCBASETYPE_APCreditMemo = "APC";
	/** ARCreditMemo = ARC */
	public static final String DOCBASETYPE_ARCreditMemo = "ARC";
	/** BankStatement = CMB */
	public static final String DOCBASETYPE_BankStatement = "CMB";
	/** CashJournal = CMC */
	public static final String DOCBASETYPE_CashJournal = "CMC";
	/** PaymentAllocation = CMA */
	public static final String DOCBASETYPE_PaymentAllocation = "CMA";
	/** MaterialProduction = MMP */
	public static final String DOCBASETYPE_MaterialProduction = "MMP";
	/** MatchInvoice = MXI */
	public static final String DOCBASETYPE_MatchInvoice = "MXI";
	/** MatchPO = MXP */
	public static final String DOCBASETYPE_MatchPO = "MXP";
	/** ProjectIssue = PJI */
	public static final String DOCBASETYPE_ProjectIssue = "PJI";
	/** MaintenanceOrder = MOF */
	public static final String DOCBASETYPE_MaintenanceOrder = "MOF";
	/** ManufacturingOrder = MOP */
	public static final String DOCBASETYPE_ManufacturingOrder = "MOP";
	/** QualityOrder = MQO */
	public static final String DOCBASETYPE_QualityOrder = "MQO";
	/** Payroll = HRP */
	public static final String DOCBASETYPE_Payroll = "HRP";
	/** DistributionOrder = DOO */
	public static final String DOCBASETYPE_DistributionOrder = "DOO";
	/** ManufacturingCostCollector = MCC */
	public static final String DOCBASETYPE_ManufacturingCostCollector = "MCC";
	/** Gehaltsrechnung (Angestellter) = AEI */
	public static final String DOCBASETYPE_GehaltsrechnungAngestellter = "AEI";
	/** Interne Rechnung (Lieferant) = AVI */
	public static final String DOCBASETYPE_InterneRechnungLieferant = "AVI";
	/** Speditionsauftrag/Ladeliste = MST */
	public static final String DOCBASETYPE_SpeditionsauftragLadeliste = "MST";
	/** CustomerContract = CON */
	public static final String DOCBASETYPE_CustomerContract = "CON";
	/** Set Document BaseType.
		@param DocBaseType 
		Logical type of document
	  */
	@Override
	public void setDocBaseType (java.lang.String DocBaseType)
	{

		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	/** Get Document BaseType.
		@return Logical type of document
	  */
	@Override
	public java.lang.String getDocBaseType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocBaseType);
	}

	/** 
	 * DocSubType AD_Reference_ID=148
	 * Reference name: C_DocType SubType
	 */
	public static final int DOCSUBTYPE_AD_Reference_ID=148;
	/** OnCreditOrder = WI */
	public static final String DOCSUBTYPE_OnCreditOrder = "WI";
	/** POSOrder = WR */
	public static final String DOCSUBTYPE_POSOrder = "WR";
	/** WarehouseOrder = WP */
	public static final String DOCSUBTYPE_WarehouseOrder = "WP";
	/** StandardOrder = SO */
	public static final String DOCSUBTYPE_StandardOrder = "SO";
	/** Proposal = ON */
	public static final String DOCSUBTYPE_Proposal = "ON";
	/** Quotation = OB */
	public static final String DOCSUBTYPE_Quotation = "OB";
	/** ReturnMaterial = RM */
	public static final String DOCSUBTYPE_ReturnMaterial = "RM";
	/** PrepayOrder = PR */
	public static final String DOCSUBTYPE_PrepayOrder = "PR";
	/** Auftrag (Vorkasse) zur Disposition = PM */
	public static final String DOCSUBTYPE_AuftragVorkasseZurDisposition = "PM";
	/** Provisionskorrektur = CC */
	public static final String DOCSUBTYPE_Provisionskorrektur = "CC";
	/** Provisionsberechnung = CA */
	public static final String DOCSUBTYPE_Provisionsberechnung = "CA";
	/** FlatFee = FF */
	public static final String DOCSUBTYPE_FlatFee = "FF";
	/** HoldingFee = HF */
	public static final String DOCSUBTYPE_HoldingFee = "HF";
	/** Subscription = SU */
	public static final String DOCSUBTYPE_Subscription = "SU";
	/** NB - Mengendifferenz = AQ */
	public static final String DOCSUBTYPE_NB_Mengendifferenz = "AQ";
	/** NB - Preisdifferenz = AP */
	public static final String DOCSUBTYPE_NB_Preisdifferenz = "AP";
	/** GS - Lieferdifferenz = CQ */
	public static final String DOCSUBTYPE_GS_Lieferdifferenz = "CQ";
	/** GS - Preisdifferenz = CR */
	public static final String DOCSUBTYPE_GS_Preisdifferenz = "CR";
	/** QualityInspection = QI */
	public static final String DOCSUBTYPE_QualityInspection = "QI";
	/** Leergutanlieferung = ER */
	public static final String DOCSUBTYPE_Leergutanlieferung = "ER";
	/** Produktanlieferung = MR */
	public static final String DOCSUBTYPE_Produktanlieferung = "MR";
	/** Produktauslieferung = MS */
	public static final String DOCSUBTYPE_Produktauslieferung = "MS";
	/** Leergutausgabe = ES */
	public static final String DOCSUBTYPE_Leergutausgabe = "ES";
	/** GS - Retoure = CS */
	public static final String DOCSUBTYPE_GS_Retoure = "CS";
	/** VendorInvoice = VI */
	public static final String DOCSUBTYPE_VendorInvoice = "VI";
	/** DownPayment = DP */
	public static final String DOCSUBTYPE_DownPayment = "DP";
	/** Saldokorektur = EC */
	public static final String DOCSUBTYPE_Saldokorektur = "EC";
	/** Set Doc Sub Type.
		@param DocSubType 
		Document Sub Type
	  */
	@Override
	public void setDocSubType (java.lang.String DocSubType)
	{

		set_Value (COLUMNNAME_DocSubType, DocSubType);
	}

	/** Get Doc Sub Type.
		@return Document Sub Type
	  */
	@Override
	public java.lang.String getDocSubType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocSubType);
	}
}