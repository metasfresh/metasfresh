/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DocType
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_C_DocType extends org.compiere.model.PO implements I_C_DocType, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1772531968L;

    /** Standard Constructor */
    public X_C_DocType (Properties ctx, int C_DocType_ID, String trxName)
    {
      super (ctx, C_DocType_ID, trxName);
      /** if (C_DocType_ID == 0)
        {
			setC_DocType_ID (0);
			setDocBaseType (null);
			setDocumentCopies (0); // 1
			setEntityType (null);
			setGL_Category_ID (0);
			setHasCharges (false);
			setIsCopyDescriptionToDocument (true); // Y
			setIsCreateCounter (true); // Y
			setIsDefault (false);
			setIsDefaultCounterDoc (false);
			setIsDocNoControlled (true); // Y
			setIsIndexed (false);
			setIsInTransit (false);
			setIsPickQAConfirm (false);
			setIsShipConfirm (false);
			setIsSOTrx (false);
			setIsSplitWhenDifference (false); // N
			setName (null);
			setPrintName (null);
        } */
    }

    /** Load Constructor */
    public X_C_DocType (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Emailtext.
		@param AD_BoilerPlate_ID
		Standardtext bei Email-Versand
	  */
	@Override
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1)
		{
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_AD_BoilerPlate_ID, Integer.valueOf(AD_BoilerPlate_ID));
		}
	}

	/** Get Emailtext.
		@return Standardtext bei Email-Versand
	  */
	@Override
	public int getAD_BoilerPlate_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class);
	}

	@Override
	public void setAD_PrintFormat(org.compiere.model.I_AD_PrintFormat AD_PrintFormat)
	{
		set_ValueFromPO(COLUMNNAME_AD_PrintFormat_ID, org.compiere.model.I_AD_PrintFormat.class, AD_PrintFormat);
	}

	/** Set Druck - Format.
		@param AD_PrintFormat_ID
		Data Print Format
	  */
	@Override
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1)
		{
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
		}
	}

	/** Get Druck - Format.
		@return Data Print Format
	  */
	@Override
	public int getAD_PrintFormat_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Belegart.
		@param C_DocType_ID
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
		{
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
		}
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocTypeDifference() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocTypeDifference_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocTypeDifference(org.compiere.model.I_C_DocType C_DocTypeDifference)
	{
		set_ValueFromPO(COLUMNNAME_C_DocTypeDifference_ID, org.compiere.model.I_C_DocType.class, C_DocTypeDifference);
	}

	/** Set Difference Document.
		@param C_DocTypeDifference_ID
		Document type for generating in dispute Shipments
	  */
	@Override
	public void setC_DocTypeDifference_ID (int C_DocTypeDifference_ID)
	{
		if (C_DocTypeDifference_ID < 1)
		{
			set_Value (COLUMNNAME_C_DocTypeDifference_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_DocTypeDifference_ID, Integer.valueOf(C_DocTypeDifference_ID));
		}
	}

	/** Get Difference Document.
		@return Document type for generating in dispute Shipments
	  */
	@Override
	public int getC_DocTypeDifference_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeDifference_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocTypeInvoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocTypeInvoice_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocTypeInvoice(org.compiere.model.I_C_DocType C_DocTypeInvoice)
	{
		set_ValueFromPO(COLUMNNAME_C_DocTypeInvoice_ID, org.compiere.model.I_C_DocType.class, C_DocTypeInvoice);
	}

	/** Set Rechnungs-Belegart.
		@param C_DocTypeInvoice_ID
		Document type used for invoices generated from this sales document
	  */
	@Override
	public void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID)
	{
		if (C_DocTypeInvoice_ID < 1)
		{
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_DocTypeInvoice_ID, Integer.valueOf(C_DocTypeInvoice_ID));
		}
	}

	/** Get Rechnungs-Belegart.
		@return Document type used for invoices generated from this sales document
	  */
	@Override
	public int getC_DocTypeInvoice_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeInvoice_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocTypeProforma() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocTypeProforma_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocTypeProforma(org.compiere.model.I_C_DocType C_DocTypeProforma)
	{
		set_ValueFromPO(COLUMNNAME_C_DocTypeProforma_ID, org.compiere.model.I_C_DocType.class, C_DocTypeProforma);
	}

	/** Set Document Type for ProForma.
		@param C_DocTypeProforma_ID
		Document type used for pro forma invoices generated from this sales document
	  */
	@Override
	public void setC_DocTypeProforma_ID (int C_DocTypeProforma_ID)
	{
		if (C_DocTypeProforma_ID < 1)
		{
			set_Value (COLUMNNAME_C_DocTypeProforma_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_DocTypeProforma_ID, Integer.valueOf(C_DocTypeProforma_ID));
		}
	}

	/** Get Document Type for ProForma.
		@return Document type used for pro forma invoices generated from this sales document
	  */
	@Override
	public int getC_DocTypeProforma_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeProforma_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocTypeShipment() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocTypeShipment_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocTypeShipment(org.compiere.model.I_C_DocType C_DocTypeShipment)
	{
		set_ValueFromPO(COLUMNNAME_C_DocTypeShipment_ID, org.compiere.model.I_C_DocType.class, C_DocTypeShipment);
	}

	/** Set Document Type for Shipment.
		@param C_DocTypeShipment_ID
		Document type used for shipments generated from this sales document
	  */
	@Override
	public void setC_DocTypeShipment_ID (int C_DocTypeShipment_ID)
	{
		if (C_DocTypeShipment_ID < 1)
		{
			set_Value (COLUMNNAME_C_DocTypeShipment_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_DocTypeShipment_ID, Integer.valueOf(C_DocTypeShipment_ID));
		}
	}

	/** Get Document Type for Shipment.
		@return Document type used for shipments generated from this sales document
	  */
	@Override
	public int getC_DocTypeShipment_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeShipment_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Sequence getDefiniteSequence() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DefiniteSequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setDefiniteSequence(org.compiere.model.I_AD_Sequence DefiniteSequence)
	{
		set_ValueFromPO(COLUMNNAME_DefiniteSequence_ID, org.compiere.model.I_AD_Sequence.class, DefiniteSequence);
	}

	/** Set Definite Sequence.
		@param DefiniteSequence_ID Definite Sequence	  */
	@Override
	public void setDefiniteSequence_ID (int DefiniteSequence_ID)
	{
		if (DefiniteSequence_ID < 1)
		{
			set_Value (COLUMNNAME_DefiniteSequence_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_DefiniteSequence_ID, Integer.valueOf(DefiniteSequence_ID));
		}
	}

	/** Get Definite Sequence.
		@return Definite Sequence	  */
	@Override
	public int getDefiniteSequence_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DefiniteSequence_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
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
	/** Customs Invoice = CUI */
	public static final String DOCBASETYPE_CustomsInvoice = "CUI";
	/** Set Dokument Basis Typ.
		@param DocBaseType Dokument Basis Typ	  */
	@Override
	public void setDocBaseType (java.lang.String DocBaseType)
	{

		set_Value (COLUMNNAME_DocBaseType, DocBaseType);
	}

	/** Get Dokument Basis Typ.
		@return Dokument Basis Typ	  */
	@Override
	public java.lang.String getDocBaseType ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocBaseType);
	}

	@Override
	public org.compiere.model.I_AD_Sequence getDocNoSequence() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setDocNoSequence(org.compiere.model.I_AD_Sequence DocNoSequence)
	{
		set_ValueFromPO(COLUMNNAME_DocNoSequence_ID, org.compiere.model.I_AD_Sequence.class, DocNoSequence);
	}

	/** Set Nummernfolgen für Belege.
		@param DocNoSequence_ID
		Document sequence determines the numbering of documents
	  */
	@Override
	public void setDocNoSequence_ID (int DocNoSequence_ID)
	{
		if (DocNoSequence_ID < 1)
		{
			set_Value (COLUMNNAME_DocNoSequence_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_DocNoSequence_ID, Integer.valueOf(DocNoSequence_ID));
		}
	}

	/** Get Nummernfolgen für Belege.
		@return Document sequence determines the numbering of documents
	  */
	@Override
	public int getDocNoSequence_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocNoSequence_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
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
	/** Internal Use Inventory = IUI */
	public static final String DOCSUBTYPE_InternalUseInventory = "IUI";
	/** Rückvergütungsrechnung = RI */
	public static final String DOCSUBTYPE_Rueckverguetungsrechnung = "RI";
	/** Rückvergütungsgutschrift = RC */
	public static final String DOCSUBTYPE_Rueckverguetungsgutschrift = "RC";

	public static final String DOCSUBTYPE_VirtualInventory = "VIY";
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

	/** Set Kopien.
		@param DocumentCopies
		Number of copies to be printed
	  */
	@Override
	public void setDocumentCopies (int DocumentCopies)
	{
		set_Value (COLUMNNAME_DocumentCopies, Integer.valueOf(DocumentCopies));
	}

	/** Get Kopien.
		@return Number of copies to be printed
	  */
	@Override
	public int getDocumentCopies ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DocumentCopies);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Notiz / Zeilentext.
		@param DocumentNote
		Additional information for a Document
	  */
	@Override
	public void setDocumentNote (java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	/** Get Notiz / Zeilentext.
		@return Additional information for a Document
	  */
	@Override
	public java.lang.String getDocumentNote ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNote);
	}

	/**
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	@Override
	public org.compiere.model.I_GL_Category getGL_Category() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class);
	}

	@Override
	public void setGL_Category(org.compiere.model.I_GL_Category GL_Category)
	{
		set_ValueFromPO(COLUMNNAME_GL_Category_ID, org.compiere.model.I_GL_Category.class, GL_Category);
	}

	/** Set Hauptbuch - Kategorie.
		@param GL_Category_ID
		General Ledger Category
	  */
	@Override
	public void setGL_Category_ID (int GL_Category_ID)
	{
		if (GL_Category_ID < 1)
		{
			set_Value (COLUMNNAME_GL_Category_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_GL_Category_ID, Integer.valueOf(GL_Category_ID));
		}
	}

	/** Get Hauptbuch - Kategorie.
		@return General Ledger Category
	  */
	@Override
	public int getGL_Category_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Category_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Charges.
		@param HasCharges
		Charges can be added to the document
	  */
	@Override
	public void setHasCharges (boolean HasCharges)
	{
		set_Value (COLUMNNAME_HasCharges, Boolean.valueOf(HasCharges));
	}

	/** Get Charges.
		@return Charges can be added to the document
	  */
	@Override
	public boolean isHasCharges ()
	{
		Object oo = get_Value(COLUMNNAME_HasCharges);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pro forma Invoice.
		@param HasProforma
		Indicates if Pro Forma Invoices can be generated from this document
	  */
	@Override
	public void setHasProforma (boolean HasProforma)
	{
		set_Value (COLUMNNAME_HasProforma, Boolean.valueOf(HasProforma));
	}

	/** Get Pro forma Invoice.
		@return Indicates if Pro Forma Invoices can be generated from this document
	  */
	@Override
	public boolean isHasProforma ()
	{
		Object oo = get_Value(COLUMNNAME_HasProforma);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Copy description to document.
		@param IsCopyDescriptionToDocument Copy description to document	  */
	@Override
	public void setIsCopyDescriptionToDocument (boolean IsCopyDescriptionToDocument)
	{
		set_Value (COLUMNNAME_IsCopyDescriptionToDocument, Boolean.valueOf(IsCopyDescriptionToDocument));
	}

	/** Get Copy description to document.
		@return Copy description to document	  */
	@Override
	public boolean isCopyDescriptionToDocument ()
	{
		Object oo = get_Value(COLUMNNAME_IsCopyDescriptionToDocument);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Create Counter Document.
		@param IsCreateCounter
		Create Counter Document
	  */
	@Override
	public void setIsCreateCounter (boolean IsCreateCounter)
	{
		set_Value (COLUMNNAME_IsCreateCounter, Boolean.valueOf(IsCreateCounter));
	}

	/** Get Create Counter Document.
		@return Create Counter Document
	  */
	@Override
	public boolean isCreateCounter ()
	{
		Object oo = get_Value(COLUMNNAME_IsCreateCounter);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard.
		@param IsDefault
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault ()
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Default Counter Document.
		@param IsDefaultCounterDoc
		The document type is the default counter document type
	  */
	@Override
	public void setIsDefaultCounterDoc (boolean IsDefaultCounterDoc)
	{
		set_Value (COLUMNNAME_IsDefaultCounterDoc, Boolean.valueOf(IsDefaultCounterDoc));
	}

	/** Get Default Counter Document.
		@return The document type is the default counter document type
	  */
	@Override
	public boolean isDefaultCounterDoc ()
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultCounterDoc);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Document is Number Controlled.
		@param IsDocNoControlled
		The document has a document sequence
	  */
	@Override
	public void setIsDocNoControlled (boolean IsDocNoControlled)
	{
		set_Value (COLUMNNAME_IsDocNoControlled, Boolean.valueOf(IsDocNoControlled));
	}

	/** Get Document is Number Controlled.
		@return The document has a document sequence
	  */
	@Override
	public boolean isDocNoControlled ()
	{
		Object oo = get_Value(COLUMNNAME_IsDocNoControlled);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Indexed.
		@param IsIndexed
		Index the document for the internal search engine
	  */
	@Override
	public void setIsIndexed (boolean IsIndexed)
	{
		set_Value (COLUMNNAME_IsIndexed, Boolean.valueOf(IsIndexed));
	}

	/** Get Indexed.
		@return Index the document for the internal search engine
	  */
	@Override
	public boolean isIndexed ()
	{
		Object oo = get_Value(COLUMNNAME_IsIndexed);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set In Transit.
		@param IsInTransit
		Movement is in transit
	  */
	@Override
	public void setIsInTransit (boolean IsInTransit)
	{
		set_Value (COLUMNNAME_IsInTransit, Boolean.valueOf(IsInTransit));
	}

	/** Get In Transit.
		@return Movement is in transit
	  */
	@Override
	public boolean isInTransit ()
	{
		Object oo = get_Value(COLUMNNAME_IsInTransit);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Date on Complete.
		@param IsOverwriteDateOnComplete Overwrite Date on Complete	  */
	@Override
	public void setIsOverwriteDateOnComplete (boolean IsOverwriteDateOnComplete)
	{
		set_Value (COLUMNNAME_IsOverwriteDateOnComplete, Boolean.valueOf(IsOverwriteDateOnComplete));
	}

	/** Get Overwrite Date on Complete.
		@return Overwrite Date on Complete	  */
	@Override
	public boolean isOverwriteDateOnComplete ()
	{
		Object oo = get_Value(COLUMNNAME_IsOverwriteDateOnComplete);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Overwrite Sequence on Complete.
		@param IsOverwriteSeqOnComplete Overwrite Sequence on Complete	  */
	@Override
	public void setIsOverwriteSeqOnComplete (boolean IsOverwriteSeqOnComplete)
	{
		set_Value (COLUMNNAME_IsOverwriteSeqOnComplete, Boolean.valueOf(IsOverwriteSeqOnComplete));
	}

	/** Get Overwrite Sequence on Complete.
		@return Overwrite Sequence on Complete	  */
	@Override
	public boolean isOverwriteSeqOnComplete ()
	{
		Object oo = get_Value(COLUMNNAME_IsOverwriteSeqOnComplete);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pick/QA Confirmation.
		@param IsPickQAConfirm
		Require Pick or QA Confirmation before processing
	  */
	@Override
	public void setIsPickQAConfirm (boolean IsPickQAConfirm)
	{
		set_Value (COLUMNNAME_IsPickQAConfirm, Boolean.valueOf(IsPickQAConfirm));
	}

	/** Get Pick/QA Confirmation.
		@return Require Pick or QA Confirmation before processing
	  */
	@Override
	public boolean isPickQAConfirm ()
	{
		Object oo = get_Value(COLUMNNAME_IsPickQAConfirm);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bestätigung Versand/Wareneingang.
		@param IsShipConfirm
		Require Ship or Receipt Confirmation before processing
	  */
	@Override
	public void setIsShipConfirm (boolean IsShipConfirm)
	{
		set_Value (COLUMNNAME_IsShipConfirm, Boolean.valueOf(IsShipConfirm));
	}

	/** Get Bestätigung Versand/Wareneingang.
		@return Require Ship or Receipt Confirmation before processing
	  */
	@Override
	public boolean isShipConfirm ()
	{
		Object oo = get_Value(COLUMNNAME_IsShipConfirm);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verkaufs-Transaktion.
		@param IsSOTrx
		This is a Sales Transaction
	  */
	@Override
	public void setIsSOTrx (boolean IsSOTrx)
	{
		set_Value (COLUMNNAME_IsSOTrx, Boolean.valueOf(IsSOTrx));
	}

	/** Get Verkaufs-Transaktion.
		@return This is a Sales Transaction
	  */
	@Override
	public boolean isSOTrx ()
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Split when Difference.
		@param IsSplitWhenDifference
		Split document when there is a difference
	  */
	@Override
	public void setIsSplitWhenDifference (boolean IsSplitWhenDifference)
	{
		set_Value (COLUMNNAME_IsSplitWhenDifference, Boolean.valueOf(IsSplitWhenDifference));
	}

	/** Get Split when Difference.
		@return Split document when there is a difference
	  */
	@Override
	public boolean isSplitWhenDifference ()
	{
		Object oo = get_Value(COLUMNNAME_IsSplitWhenDifference);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
			{
				return ((Boolean)oo).booleanValue();
			}
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** Set Drucktext.
		@param PrintName
		The label text to be printed on a document or correspondence.
	  */
	@Override
	public void setPrintName (java.lang.String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Drucktext.
		@return The label text to be printed on a document or correspondence.
	  */
	@Override
	public java.lang.String getPrintName ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintName);
	}
}
