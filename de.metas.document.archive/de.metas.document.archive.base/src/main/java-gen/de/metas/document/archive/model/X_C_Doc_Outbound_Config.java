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
package de.metas.document.archive.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Doc_Outbound_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Doc_Outbound_Config extends org.compiere.model.PO implements I_C_Doc_Outbound_Config, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -227964890L;

    /** Standard Constructor */
    public X_C_Doc_Outbound_Config (Properties ctx, int C_Doc_Outbound_Config_ID, String trxName)
    {
      super (ctx, C_Doc_Outbound_Config_ID, trxName);
      /** if (C_Doc_Outbound_Config_ID == 0)
        {
			setAD_Table_ID (0);
			setC_Doc_Outbound_Config_ID (0);
			setIsDirectEnqueue (true);
// Y
        } */
    }

    /** Load Constructor */
    public X_C_Doc_Outbound_Config (Properties ctx, ResultSet rs, String trxName)
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
			set_Value (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Druck - Format.
		@return Data Print Format
	  */
	@Override
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CC Path.
		@param CCPath CC Path	  */
	@Override
	public void setCCPath (java.lang.String CCPath)
	{
		set_Value (COLUMNNAME_CCPath, CCPath);
	}

	/** Get CC Path.
		@return CC Path	  */
	@Override
	public java.lang.String getCCPath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CCPath);
	}

	/** Set Ausgehende Belege Konfig.
		@param C_Doc_Outbound_Config_ID Ausgehende Belege Konfig	  */
	@Override
	public void setC_Doc_Outbound_Config_ID (int C_Doc_Outbound_Config_ID)
	{
		if (C_Doc_Outbound_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Doc_Outbound_Config_ID, Integer.valueOf(C_Doc_Outbound_Config_ID));
	}

	/** Get Ausgehende Belege Konfig.
		@return Ausgehende Belege Konfig	  */
	@Override
	public int getC_Doc_Outbound_Config_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Doc_Outbound_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * DocBaseType AD_Reference_ID=183
	 * Reference name: C_DocType DocBaseType
	 */
	public static final int DOCBASETYPE_AD_Reference_ID=183;
	/** Hauptbuch - Journal = GLJ */
	public static final String DOCBASETYPE_Hauptbuch_Journal = "GLJ";
	/** Hauptbuchbeleg = GLD */
	public static final String DOCBASETYPE_Hauptbuchbeleg = "GLD";
	/** Rechnung (Kreditorenkonten) = API */
	public static final String DOCBASETYPE_RechnungKreditorenkonten = "API";
	/** Zahlung (Kreditorenkonten) = APP */
	public static final String DOCBASETYPE_ZahlungKreditorenkonten = "APP";
	/** Rechnung (Debitorenkonten) = ARI */
	public static final String DOCBASETYPE_RechnungDebitorenkonten = "ARI";
	/** Zahlungseingang (Debitorenkonten) = ARR */
	public static final String DOCBASETYPE_ZahlungseingangDebitorenkonten = "ARR";
	/** Auftrag = SOO */
	public static final String DOCBASETYPE_Auftrag = "SOO";
	/** ProForma-Rechnung (Debitorenkonten) = ARF */
	public static final String DOCBASETYPE_ProForma_RechnungDebitorenkonten = "ARF";
	/** Warenllieferung = MMS */
	public static final String DOCBASETYPE_Warenllieferung = "MMS";
	/** Wareneingang = MMR */
	public static final String DOCBASETYPE_Wareneingang = "MMR";
	/** Warenbewegung = MMM */
	public static final String DOCBASETYPE_Warenbewegung = "MMM";
	/** Bestellung = POO */
	public static final String DOCBASETYPE_Bestellung = "POO";
	/** Bestell-Bedarf = POR */
	public static final String DOCBASETYPE_Bestell_Bedarf = "POR";
	/** Physischer Warenbestand = MMI */
	public static final String DOCBASETYPE_PhysischerWarenbestand = "MMI";
	/** Gutschrift (Lieferant) = APC */
	public static final String DOCBASETYPE_GutschriftLieferant = "APC";
	/** Gutschrift (Debitorenkonten) = ARC */
	public static final String DOCBASETYPE_GutschriftDebitorenkonten = "ARC";
	/** Bankauszug = CMB */
	public static final String DOCBASETYPE_Bankauszug = "CMB";
	/** Kassenjournal = CMC */
	public static final String DOCBASETYPE_Kassenjournal = "CMC";
	/** Zahlung-Zuordnung = CMA */
	public static final String DOCBASETYPE_Zahlung_Zuordnung = "CMA";
	/** Warenproduktion = MMP */
	public static final String DOCBASETYPE_Warenproduktion = "MMP";
	/** Abgleich Rechnung = MXI */
	public static final String DOCBASETYPE_AbgleichRechnung = "MXI";
	/** Abgleich Bestellung = MXP */
	public static final String DOCBASETYPE_AbgleichBestellung = "MXP";
	/** Projekt-Problem = PJI */
	public static final String DOCBASETYPE_Projekt_Problem = "PJI";
	/** Maintenance Order = MOF */
	public static final String DOCBASETYPE_MaintenanceOrder = "MOF";
	/** Manufacturing Order = MOP */
	public static final String DOCBASETYPE_ManufacturingOrder = "MOP";
	/** Quality Order = MQO */
	public static final String DOCBASETYPE_QualityOrder = "MQO";
	/** Payroll = HRP */
	public static final String DOCBASETYPE_Payroll = "HRP";
	/** Distribution Order = DOO */
	public static final String DOCBASETYPE_DistributionOrder = "DOO";
	/** Manufacturing Cost Collector = MCC */
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

	/** Set Is Direct Enqueue.
		@param IsDirectEnqueue Is Direct Enqueue	  */
	@Override
	public void setIsDirectEnqueue (boolean IsDirectEnqueue)
	{
		set_Value (COLUMNNAME_IsDirectEnqueue, Boolean.valueOf(IsDirectEnqueue));
	}

	/** Get Is Direct Enqueue.
		@return Is Direct Enqueue	  */
	@Override
	public boolean isDirectEnqueue () 
	{
		Object oo = get_Value(COLUMNNAME_IsDirectEnqueue);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}