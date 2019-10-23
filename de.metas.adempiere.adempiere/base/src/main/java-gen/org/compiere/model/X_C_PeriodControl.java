/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_PeriodControl
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PeriodControl extends org.compiere.model.PO implements I_C_PeriodControl, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 404063698L;

    /** Standard Constructor */
    public X_C_PeriodControl (Properties ctx, int C_PeriodControl_ID, String trxName)
    {
      super (ctx, C_PeriodControl_ID, trxName);
      /** if (C_PeriodControl_ID == 0)
        {
			setC_PeriodControl_ID (0);
			setC_Period_ID (0);
			setDocBaseType (null);
			setPeriodAction (null);
// N
        } */
    }

    /** Load Constructor */
    public X_C_PeriodControl (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Periodenkontrolle.
		@param C_PeriodControl_ID Periodenkontrolle	  */
	@Override
	public void setC_PeriodControl_ID (int C_PeriodControl_ID)
	{
		if (C_PeriodControl_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PeriodControl_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PeriodControl_ID, Integer.valueOf(C_PeriodControl_ID));
	}

	/** Get Periodenkontrolle.
		@return Periodenkontrolle	  */
	@Override
	public int getC_PeriodControl_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PeriodControl_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	/** Set Periode.
		@param C_Period_ID 
		Period of the Calendar
	  */
	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Period of the Calendar
	  */
	@Override
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

		set_ValueNoCheck (COLUMNNAME_DocBaseType, DocBaseType);
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
	 * PeriodAction AD_Reference_ID=176
	 * Reference name: C_PeriodControl Action
	 */
	public static final int PERIODACTION_AD_Reference_ID=176;
	/** OpenPeriod = O */
	public static final String PERIODACTION_OpenPeriod = "O";
	/** ClosePeriod = C */
	public static final String PERIODACTION_ClosePeriod = "C";
	/** PermanentlyClosePeriod = P */
	public static final String PERIODACTION_PermanentlyClosePeriod = "P";
	/** NoAction = N */
	public static final String PERIODACTION_NoAction = "N";
	/** Set Period Action.
		@param PeriodAction 
		Action taken for this period
	  */
	@Override
	public void setPeriodAction (java.lang.String PeriodAction)
	{

		set_Value (COLUMNNAME_PeriodAction, PeriodAction);
	}

	/** Get Period Action.
		@return Action taken for this period
	  */
	@Override
	public java.lang.String getPeriodAction () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PeriodAction);
	}

	/** 
	 * PeriodStatus AD_Reference_ID=177
	 * Reference name: C_PeriodControl Status
	 */
	public static final int PERIODSTATUS_AD_Reference_ID=177;
	/** Open = O */
	public static final String PERIODSTATUS_Open = "O";
	/** Closed = C */
	public static final String PERIODSTATUS_Closed = "C";
	/** PermanentlyClosed = P */
	public static final String PERIODSTATUS_PermanentlyClosed = "P";
	/** NeverOpened = N */
	public static final String PERIODSTATUS_NeverOpened = "N";
	/** Set Period Status.
		@param PeriodStatus 
		Current state of this period
	  */
	@Override
	public void setPeriodStatus (java.lang.String PeriodStatus)
	{

		set_ValueNoCheck (COLUMNNAME_PeriodStatus, PeriodStatus);
	}

	/** Get Period Status.
		@return Current state of this period
	  */
	@Override
	public java.lang.String getPeriodStatus () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PeriodStatus);
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
}