/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for PP_Order
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Order extends org.compiere.model.PO implements I_PP_Order, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -600634647L;

    /** Standard Constructor */
    public X_PP_Order (Properties ctx, int PP_Order_ID, String trxName)
    {
      super (ctx, PP_Order_ID, trxName);
      /** if (PP_Order_ID == 0)
        {
			setAD_Workflow_ID (0);
			setC_DocTypeTarget_ID (0);
// 0
			setC_UOM_ID (0);
			setDateOrdered (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDatePromised (new Timestamp( System.currentTimeMillis() ));
			setDateStartSchedule (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setIsApproved (false);
// N
			setIsPrinted (false);
// N
			setIsSelected (false);
// N
			setIsSOTrx (false);
// N
			setLine (0);
			setM_Locator_ID (0);
			setM_Product_ID (0);
			setM_Warehouse_ID (0);
			setMRP_AllowCleanup (false);
// N
			setMRP_Generated (false);
// N
			setMRP_ToDelete (false);
// N
			setPlanningStatus (null);
// P
			setPP_Order_ID (0);
			setPP_Product_BOM_ID (0);
			setPriorityRule (null);
			setProcessed (false);
// N
			setQtyBeforeClose (BigDecimal.ZERO);
// 0
			setQtyDelivered (BigDecimal.ZERO);
// 0
			setQtyOrdered (BigDecimal.ZERO);
// 1
			setQtyReject (BigDecimal.ZERO);
// 0
			setQtyScrap (BigDecimal.ZERO);
// 0
			setS_Resource_ID (0);
			setYield (BigDecimal.ZERO);
// 100
        } */
    }

    /** Load Constructor */
    public X_PP_Order (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Org getAD_OrgTrx() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class);
	}

	@Override
	public void setAD_OrgTrx(org.compiere.model.I_AD_Org AD_OrgTrx)
	{
		set_ValueFromPO(COLUMNNAME_AD_OrgTrx_ID, org.compiere.model.I_AD_Org.class, AD_OrgTrx);
	}

	/** Set Buchende Organisation.
		@param AD_OrgTrx_ID 
		Performing or initiating organization
	  */
	@Override
	public void setAD_OrgTrx_ID (int AD_OrgTrx_ID)
	{
		if (AD_OrgTrx_ID < 1) 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, null);
		else 
			set_Value (COLUMNNAME_AD_OrgTrx_ID, Integer.valueOf(AD_OrgTrx_ID));
	}

	/** Get Buchende Organisation.
		@return Performing or initiating organization
	  */
	@Override
	public int getAD_OrgTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_OrgTrx_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Workflow getAD_Workflow() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class);
	}

	@Override
	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow)
	{
		set_ValueFromPO(COLUMNNAME_AD_Workflow_ID, org.compiere.model.I_AD_Workflow.class, AD_Workflow);
	}

	/** Set Workflow.
		@param AD_Workflow_ID 
		Workflow or combination of tasks
	  */
	@Override
	public void setAD_Workflow_ID (int AD_Workflow_ID)
	{
		if (AD_Workflow_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Workflow_ID, Integer.valueOf(AD_Workflow_ID));
	}

	/** Get Workflow.
		@return Workflow or combination of tasks
	  */
	@Override
	public int getAD_Workflow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Workflow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity Assay.
		@param Assay 
		Indicated the Quantity Assay to use into Quality Order
	  */
	@Override
	public void setAssay (java.math.BigDecimal Assay)
	{
		set_Value (COLUMNNAME_Assay, Assay);
	}

	/** Get Quantity Assay.
		@return Indicated the Quantity Assay to use into Quality Order
	  */
	@Override
	public java.math.BigDecimal getAssay () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Assay);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class);
	}

	@Override
	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity)
	{
		set_ValueFromPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class, C_Activity);
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Campaign getC_Campaign() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class);
	}

	@Override
	public void setC_Campaign(org.compiere.model.I_C_Campaign C_Campaign)
	{
		set_ValueFromPO(COLUMNNAME_C_Campaign_ID, org.compiere.model.I_C_Campaign.class, C_Campaign);
	}

	/** Set Werbemassnahme.
		@param C_Campaign_ID 
		Marketing Campaign
	  */
	@Override
	public void setC_Campaign_ID (int C_Campaign_ID)
	{
		if (C_Campaign_ID < 1) 
			set_Value (COLUMNNAME_C_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_C_Campaign_ID, Integer.valueOf(C_Campaign_ID));
	}

	/** Get Werbemassnahme.
		@return Marketing Campaign
	  */
	@Override
	public int getC_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocTypeTarget() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocTypeTarget_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocTypeTarget(org.compiere.model.I_C_DocType C_DocTypeTarget)
	{
		set_ValueFromPO(COLUMNNAME_C_DocTypeTarget_ID, org.compiere.model.I_C_DocType.class, C_DocTypeTarget);
	}

	/** Set Zielbelegart.
		@param C_DocTypeTarget_ID 
		Target document type for conversing documents
	  */
	@Override
	public void setC_DocTypeTarget_ID (int C_DocTypeTarget_ID)
	{
		if (C_DocTypeTarget_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocTypeTarget_ID, Integer.valueOf(C_DocTypeTarget_ID));
	}

	/** Get Zielbelegart.
		@return Target document type for conversing documents
	  */
	@Override
	public int getC_DocTypeTarget_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocTypeTarget_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	/** Set Auftrag.
		@param C_Order_ID 
		Auftrag
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		throw new IllegalArgumentException ("C_Order_ID is virtual column");	}

	/** Get Auftrag.
		@return Auftrag
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
	}

	/** Set Auftragsposition.
		@param C_OrderLine_ID 
		Sales Order Line
	  */
	@Override
	public void setC_OrderLine_ID (int C_OrderLine_ID)
	{
		if (C_OrderLine_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
	}

	/** Get Auftragsposition.
		@return Sales Order Line
	  */
	@Override
	public int getC_OrderLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine_MTO() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_MTO_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
	public void setC_OrderLine_MTO(org.compiere.model.I_C_OrderLine C_OrderLine_MTO)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_MTO_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine_MTO);
	}

	/** Set Auftragsposition (MTO).
		@param C_OrderLine_MTO_ID 
		Auftragsposition (Make to Order)
	  */
	@Override
	public void setC_OrderLine_MTO_ID (int C_OrderLine_MTO_ID)
	{
		if (C_OrderLine_MTO_ID < 1) 
			set_Value (COLUMNNAME_C_OrderLine_MTO_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderLine_MTO_ID, Integer.valueOf(C_OrderLine_MTO_ID));
	}

	/** Get Auftragsposition (MTO).
		@return Auftragsposition (Make to Order)
	  */
	@Override
	public int getC_OrderLine_MTO_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OrderLine_MTO_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Project getC_Project() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class);
	}

	@Override
	public void setC_Project(org.compiere.model.I_C_Project C_Project)
	{
		set_ValueFromPO(COLUMNNAME_C_Project_ID, org.compiere.model.I_C_Project.class, C_Project);
	}

	/** Set Projekt.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Projekt.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Copy From.
		@param CopyFrom 
		Copy From Record
	  */
	@Override
	public void setCopyFrom (java.lang.String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	/** Get Copy From.
		@return Copy From Record
	  */
	@Override
	public java.lang.String getCopyFrom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CopyFrom);
	}

	/** Set DateConfirm.
		@param DateConfirm DateConfirm	  */
	@Override
	public void setDateConfirm (java.sql.Timestamp DateConfirm)
	{
		set_ValueNoCheck (COLUMNNAME_DateConfirm, DateConfirm);
	}

	/** Get DateConfirm.
		@return DateConfirm	  */
	@Override
	public java.sql.Timestamp getDateConfirm () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateConfirm);
	}

	/** Set Lieferdatum.
		@param DateDelivered 
		Date when the product was delivered
	  */
	@Override
	public void setDateDelivered (java.sql.Timestamp DateDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_DateDelivered, DateDelivered);
	}

	/** Get Lieferdatum.
		@return Date when the product was delivered
	  */
	@Override
	public java.sql.Timestamp getDateDelivered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDelivered);
	}

	/** Set Projektabschluss.
		@param DateFinish 
		Finish or (planned) completion date
	  */
	@Override
	public void setDateFinish (java.sql.Timestamp DateFinish)
	{
		set_ValueNoCheck (COLUMNNAME_DateFinish, DateFinish);
	}

	/** Get Projektabschluss.
		@return Finish or (planned) completion date
	  */
	@Override
	public java.sql.Timestamp getDateFinish () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFinish);
	}

	/** Set DateFinishSchedule.
		@param DateFinishSchedule DateFinishSchedule	  */
	@Override
	public void setDateFinishSchedule (java.sql.Timestamp DateFinishSchedule)
	{
		set_Value (COLUMNNAME_DateFinishSchedule, DateFinishSchedule);
	}

	/** Get DateFinishSchedule.
		@return DateFinishSchedule	  */
	@Override
	public java.sql.Timestamp getDateFinishSchedule () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateFinishSchedule);
	}

	/** Set Auftragsdatum.
		@param DateOrdered 
		Date of Order
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Date of Order
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set Zugesagter Termin.
		@param DatePromised 
		Date Order was promised
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Zugesagter Termin.
		@return Date Order was promised
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set DateStart.
		@param DateStart DateStart	  */
	@Override
	public void setDateStart (java.sql.Timestamp DateStart)
	{
		set_ValueNoCheck (COLUMNNAME_DateStart, DateStart);
	}

	/** Get DateStart.
		@return DateStart	  */
	@Override
	public java.sql.Timestamp getDateStart () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateStart);
	}

	/** Set DateStartSchedule.
		@param DateStartSchedule DateStartSchedule	  */
	@Override
	public void setDateStartSchedule (java.sql.Timestamp DateStartSchedule)
	{
		set_Value (COLUMNNAME_DateStartSchedule, DateStartSchedule);
	}

	/** Get DateStartSchedule.
		@return DateStartSchedule	  */
	@Override
	public java.sql.Timestamp getDateStartSchedule () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateStartSchedule);
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
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse_Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse_Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re_Activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** None = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** WaitComplete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Belegverarbeitung.
		@param DocAction 
		The targeted status of the document
	  */
	@Override
	public void setDocAction (java.lang.String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return The targeted status of the document
	  */
	@Override
	public java.lang.String getDocAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	@Override
	public void setDocStatus (java.lang.String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	@Override
	public java.lang.String getDocStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Beleg Nr..
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Beleg Nr..
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set Float After.
		@param FloatAfter Float After	  */
	@Override
	public void setFloatAfter (java.math.BigDecimal FloatAfter)
	{
		set_Value (COLUMNNAME_FloatAfter, FloatAfter);
	}

	/** Get Float After.
		@return Float After	  */
	@Override
	public java.math.BigDecimal getFloatAfter () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FloatAfter);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Float Befored.
		@param FloatBefored Float Befored	  */
	@Override
	public void setFloatBefored (java.math.BigDecimal FloatBefored)
	{
		set_Value (COLUMNNAME_FloatBefored, FloatBefored);
	}

	/** Get Float Befored.
		@return Float Befored	  */
	@Override
	public java.math.BigDecimal getFloatBefored () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FloatBefored);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Freigegeben.
		@param IsApproved 
		Indicates if this document requires approval
	  */
	@Override
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
		@return Indicates if this document requires approval
	  */
	@Override
	public boolean isApproved () 
	{
		Object oo = get_Value(COLUMNNAME_IsApproved);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set andrucken.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	@Override
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get andrucken.
		@return Indicates if this document / line is printed
	  */
	@Override
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Is Qty Percentage.
		@param IsQtyPercentage 
		Indicate that this component is based in % Quantity
	  */
	@Override
	public void setIsQtyPercentage (boolean IsQtyPercentage)
	{
		set_Value (COLUMNNAME_IsQtyPercentage, Boolean.valueOf(IsQtyPercentage));
	}

	/** Get Is Qty Percentage.
		@return Indicate that this component is based in % Quantity
	  */
	@Override
	public boolean isQtyPercentage () 
	{
		Object oo = get_Value(COLUMNNAME_IsQtyPercentage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Selektiert.
		@param IsSelected Selektiert	  */
	@Override
	public void setIsSelected (boolean IsSelected)
	{
		set_Value (COLUMNNAME_IsSelected, Boolean.valueOf(IsSelected));
	}

	/** Get Selektiert.
		@return Selektiert	  */
	@Override
	public boolean isSelected () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelected);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
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
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Los-Nr..
		@param Lot 
		Los-Nummer (alphanumerisch)
	  */
	@Override
	public void setLot (java.lang.String Lot)
	{
		set_Value (COLUMNNAME_Lot, Lot);
	}

	/** Get Los-Nr..
		@return Los-Nummer (alphanumerisch)
	  */
	@Override
	public java.lang.String getLot () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Merkmale.
		@param M_AttributeSetInstance_ID 
		Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Merkmale.
		@return Merkmals Ausprägungen zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Locator getM_Locator() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class);
	}

	@Override
	public void setM_Locator(org.compiere.model.I_M_Locator M_Locator)
	{
		set_ValueFromPO(COLUMNNAME_M_Locator_ID, org.compiere.model.I_M_Locator.class, M_Locator);
	}

	/** Set Lagerort.
		@param M_Locator_ID 
		Lagerort im Lager
	  */
	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	/** Get Lagerort.
		@return Lagerort im Lager
	  */
	@Override
	public int getM_Locator_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Locator_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse(org.compiere.model.I_M_Warehouse M_Warehouse)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse);
	}

	/** Set Lager.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Lager.
		@return Storage Warehouse and Service Point
	  */
	@Override
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set MRP Allow Cleanup.
		@param MRP_AllowCleanup 
		MRP is allowed to remove this document
	  */
	@Override
	public void setMRP_AllowCleanup (boolean MRP_AllowCleanup)
	{
		set_Value (COLUMNNAME_MRP_AllowCleanup, Boolean.valueOf(MRP_AllowCleanup));
	}

	/** Get MRP Allow Cleanup.
		@return MRP is allowed to remove this document
	  */
	@Override
	public boolean isMRP_AllowCleanup () 
	{
		Object oo = get_Value(COLUMNNAME_MRP_AllowCleanup);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set MRP Generated Document.
		@param MRP_Generated 
		This document was generated by MRP
	  */
	@Override
	public void setMRP_Generated (boolean MRP_Generated)
	{
		set_Value (COLUMNNAME_MRP_Generated, Boolean.valueOf(MRP_Generated));
	}

	/** Get MRP Generated Document.
		@return This document was generated by MRP
	  */
	@Override
	public boolean isMRP_Generated () 
	{
		Object oo = get_Value(COLUMNNAME_MRP_Generated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set To be deleted (MRP).
		@param MRP_ToDelete 
		Indicates if this document is scheduled to be deleted by MRP
	  */
	@Override
	public void setMRP_ToDelete (boolean MRP_ToDelete)
	{
		set_Value (COLUMNNAME_MRP_ToDelete, Boolean.valueOf(MRP_ToDelete));
	}

	/** Get To be deleted (MRP).
		@return Indicates if this document is scheduled to be deleted by MRP
	  */
	@Override
	public boolean isMRP_ToDelete () 
	{
		Object oo = get_Value(COLUMNNAME_MRP_ToDelete);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set OrderType.
		@param OrderType OrderType	  */
	@Override
	public void setOrderType (java.lang.String OrderType)
	{
		set_Value (COLUMNNAME_OrderType, OrderType);
	}

	/** Get OrderType.
		@return OrderType	  */
	@Override
	public java.lang.String getOrderType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrderType);
	}

	@Override
	public org.compiere.model.I_AD_User getPlanner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Planner_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setPlanner(org.compiere.model.I_AD_User Planner)
	{
		set_ValueFromPO(COLUMNNAME_Planner_ID, org.compiere.model.I_AD_User.class, Planner);
	}

	/** Set Planner.
		@param Planner_ID Planner	  */
	@Override
	public void setPlanner_ID (int Planner_ID)
	{
		if (Planner_ID < 1) 
			set_Value (COLUMNNAME_Planner_ID, null);
		else 
			set_Value (COLUMNNAME_Planner_ID, Integer.valueOf(Planner_ID));
	}

	/** Get Planner.
		@return Planner	  */
	@Override
	public int getPlanner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Planner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * PlanningStatus AD_Reference_ID=540714
	 * Reference name: PP_Order_PlanningStatus
	 */
	public static final int PLANNINGSTATUS_AD_Reference_ID=540714;
	/** Planning = P */
	public static final String PLANNINGSTATUS_Planning = "P";
	/** Review = R */
	public static final String PLANNINGSTATUS_Review = "R";
	/** Complete = C */
	public static final String PLANNINGSTATUS_Complete = "C";
	/** Set Planning status.
		@param PlanningStatus Planning status	  */
	@Override
	public void setPlanningStatus (java.lang.String PlanningStatus)
	{

		set_Value (COLUMNNAME_PlanningStatus, PlanningStatus);
	}

	/** Get Planning status.
		@return Planning status	  */
	@Override
	public java.lang.String getPlanningStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PlanningStatus);
	}

	/** Set Verbucht.
		@param Posted 
		Posting status
	  */
	@Override
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Verbucht.
		@return Posting status
	  */
	@Override
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Produktionsauftrag.
		@param PP_Order_ID Produktionsauftrag	  */
	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	/** Get Produktionsauftrag.
		@return Produktionsauftrag	  */
	@Override
	public int getPP_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Product_BOM getPP_Product_BOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class);
	}

	@Override
	public void setPP_Product_BOM(org.eevolution.model.I_PP_Product_BOM PP_Product_BOM)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_BOM_ID, org.eevolution.model.I_PP_Product_BOM.class, PP_Product_BOM);
	}

	/** Set BOM & Formula.
		@param PP_Product_BOM_ID 
		BOM & Formula
	  */
	@Override
	public void setPP_Product_BOM_ID (int PP_Product_BOM_ID)
	{
		if (PP_Product_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_BOM_ID, Integer.valueOf(PP_Product_BOM_ID));
	}

	/** Get BOM & Formula.
		@return BOM & Formula
	  */
	@Override
	public int getPP_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.eevolution.model.I_PP_Product_Planning getPP_Product_Planning() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PP_Product_Planning_ID, org.eevolution.model.I_PP_Product_Planning.class);
	}

	@Override
	public void setPP_Product_Planning(org.eevolution.model.I_PP_Product_Planning PP_Product_Planning)
	{
		set_ValueFromPO(COLUMNNAME_PP_Product_Planning_ID, org.eevolution.model.I_PP_Product_Planning.class, PP_Product_Planning);
	}

	/** Set Product Planning.
		@param PP_Product_Planning_ID Product Planning	  */
	@Override
	public void setPP_Product_Planning_ID (int PP_Product_Planning_ID)
	{
		if (PP_Product_Planning_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Product_Planning_ID, Integer.valueOf(PP_Product_Planning_ID));
	}

	/** Get Product Planning.
		@return Product Planning	  */
	@Override
	public int getPP_Product_Planning_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_Product_Planning_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Bereitstellungsdatum.
		@param PreparationDate Bereitstellungsdatum	  */
	@Override
	public void setPreparationDate (java.sql.Timestamp PreparationDate)
	{
		set_Value (COLUMNNAME_PreparationDate, PreparationDate);
	}

	/** Get Bereitstellungsdatum.
		@return Bereitstellungsdatum	  */
	@Override
	public java.sql.Timestamp getPreparationDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PreparationDate);
	}

	/** 
	 * PriorityRule AD_Reference_ID=154
	 * Reference name: _PriorityRule
	 */
	public static final int PRIORITYRULE_AD_Reference_ID=154;
	/** High = 3 */
	public static final String PRIORITYRULE_High = "3";
	/** Medium = 5 */
	public static final String PRIORITYRULE_Medium = "5";
	/** Low = 7 */
	public static final String PRIORITYRULE_Low = "7";
	/** Urgent = 1 */
	public static final String PRIORITYRULE_Urgent = "1";
	/** Minor = 9 */
	public static final String PRIORITYRULE_Minor = "9";
	/** Set Priorität.
		@param PriorityRule 
		Priority of a document
	  */
	@Override
	public void setPriorityRule (java.lang.String PriorityRule)
	{

		set_Value (COLUMNNAME_PriorityRule, PriorityRule);
	}

	/** Get Priorität.
		@return Priority of a document
	  */
	@Override
	public java.lang.String getPriorityRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PriorityRule);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeiten.
		@param Processing Verarbeiten	  */
	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Verarbeiten.
		@return Verarbeiten	  */
	@Override
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Qty Batchs.
		@param QtyBatchs Qty Batchs	  */
	@Override
	public void setQtyBatchs (java.math.BigDecimal QtyBatchs)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatchs, QtyBatchs);
	}

	/** Get Qty Batchs.
		@return Qty Batchs	  */
	@Override
	public java.math.BigDecimal getQtyBatchs () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatchs);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty Batch Size.
		@param QtyBatchSize Qty Batch Size	  */
	@Override
	public void setQtyBatchSize (java.math.BigDecimal QtyBatchSize)
	{
		set_ValueNoCheck (COLUMNNAME_QtyBatchSize, QtyBatchSize);
	}

	/** Get Qty Batch Size.
		@return Qty Batch Size	  */
	@Override
	public java.math.BigDecimal getQtyBatchSize () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBatchSize);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty before close.
		@param QtyBeforeClose Qty before close	  */
	@Override
	public void setQtyBeforeClose (java.math.BigDecimal QtyBeforeClose)
	{
		set_Value (COLUMNNAME_QtyBeforeClose, QtyBeforeClose);
	}

	/** Get Qty before close.
		@return Qty before close	  */
	@Override
	public java.math.BigDecimal getQtyBeforeClose () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyBeforeClose);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Gelieferte Menge.
		@param QtyDelivered 
		Delivered Quantity
	  */
	@Override
	public void setQtyDelivered (java.math.BigDecimal QtyDelivered)
	{
		set_Value (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	/** Get Gelieferte Menge.
		@return Delivered Quantity
	  */
	@Override
	public java.math.BigDecimal getQtyDelivered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyDelivered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Menge.
		@param QtyEntered 
		The Quantity Entered is based on the selected UoM
	  */
	@Override
	public void setQtyEntered (java.math.BigDecimal QtyEntered)
	{
		set_Value (COLUMNNAME_QtyEntered, QtyEntered);
	}

	/** Get Menge.
		@return The Quantity Entered is based on the selected UoM
	  */
	@Override
	public java.math.BigDecimal getQtyEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Bestellte Menge.
		@param QtyOrdered 
		Ordered Quantity
	  */
	@Override
	public void setQtyOrdered (java.math.BigDecimal QtyOrdered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyOrdered, QtyOrdered);
	}

	/** Get Bestellte Menge.
		@return Ordered Quantity
	  */
	@Override
	public java.math.BigDecimal getQtyOrdered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyOrdered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Qty Reject.
		@param QtyReject Qty Reject	  */
	@Override
	public void setQtyReject (java.math.BigDecimal QtyReject)
	{
		set_Value (COLUMNNAME_QtyReject, QtyReject);
	}

	/** Get Qty Reject.
		@return Qty Reject	  */
	@Override
	public java.math.BigDecimal getQtyReject () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReject);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Reservierte Menge.
		@param QtyReserved 
		Reserved Quantity
	  */
	@Override
	public void setQtyReserved (java.math.BigDecimal QtyReserved)
	{
		set_Value (COLUMNNAME_QtyReserved, QtyReserved);
	}

	/** Get Reservierte Menge.
		@return Reserved Quantity
	  */
	@Override
	public java.math.BigDecimal getQtyReserved () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReserved);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set QtyScrap.
		@param QtyScrap 
		Scrap Quantity for this componet
	  */
	@Override
	public void setQtyScrap (java.math.BigDecimal QtyScrap)
	{
		set_Value (COLUMNNAME_QtyScrap, QtyScrap);
	}

	/** Get QtyScrap.
		@return Scrap Quantity for this componet
	  */
	@Override
	public java.math.BigDecimal getQtyScrap () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyScrap);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_S_Resource getS_Resource() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class);
	}

	@Override
	public void setS_Resource(org.compiere.model.I_S_Resource S_Resource)
	{
		set_ValueFromPO(COLUMNNAME_S_Resource_ID, org.compiere.model.I_S_Resource.class, S_Resource);
	}

	/** Set Ressource.
		@param S_Resource_ID 
		Resource
	  */
	@Override
	public void setS_Resource_ID (int S_Resource_ID)
	{
		if (S_Resource_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_Resource_ID, Integer.valueOf(S_Resource_ID));
	}

	/** Get Ressource.
		@return Resource
	  */
	@Override
	public int getS_Resource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_Resource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Planungs-Art.
		@param ScheduleType 
		Type of schedule
	  */
	@Override
	public void setScheduleType (java.lang.String ScheduleType)
	{
		set_Value (COLUMNNAME_ScheduleType, ScheduleType);
	}

	/** Get Planungs-Art.
		@return Type of schedule
	  */
	@Override
	public java.lang.String getScheduleType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ScheduleType);
	}

	/** Set Serien-Nr..
		@param SerNo 
		Product Serial Number 
	  */
	@Override
	public void setSerNo (java.lang.String SerNo)
	{
		set_Value (COLUMNNAME_SerNo, SerNo);
	}

	/** Get Serien-Nr..
		@return Product Serial Number 
	  */
	@Override
	public java.lang.String getSerNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SerNo);
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser1() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser1(org.compiere.model.I_C_ElementValue User1)
	{
		set_ValueFromPO(COLUMNNAME_User1_ID, org.compiere.model.I_C_ElementValue.class, User1);
	}

	/** Set Nutzer 1.
		@param User1_ID 
		User defined list element #1
	  */
	@Override
	public void setUser1_ID (int User1_ID)
	{
		if (User1_ID < 1) 
			set_Value (COLUMNNAME_User1_ID, null);
		else 
			set_Value (COLUMNNAME_User1_ID, Integer.valueOf(User1_ID));
	}

	/** Get Nutzer 1.
		@return User defined list element #1
	  */
	@Override
	public int getUser1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ElementValue getUser2() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setUser2(org.compiere.model.I_C_ElementValue User2)
	{
		set_ValueFromPO(COLUMNNAME_User2_ID, org.compiere.model.I_C_ElementValue.class, User2);
	}

	/** Set Nutzer 2.
		@param User2_ID 
		User defined list element #2
	  */
	@Override
	public void setUser2_ID (int User2_ID)
	{
		if (User2_ID < 1) 
			set_Value (COLUMNNAME_User2_ID, null);
		else 
			set_Value (COLUMNNAME_User2_ID, Integer.valueOf(User2_ID));
	}

	/** Get Nutzer 2.
		@return User defined list element #2
	  */
	@Override
	public int getUser2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_User2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Yield %.
		@param Yield 
		The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
	public void setYield (java.math.BigDecimal Yield)
	{
		set_Value (COLUMNNAME_Yield, Yield);
	}

	/** Get Yield %.
		@return The Yield is the percentage of a lot that is expected to be of acceptable wuality may fall below 100 percent
	  */
	@Override
	public java.math.BigDecimal getYield () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Yield);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}
}