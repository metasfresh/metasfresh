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

/** Generated Model for C_DocLine_Sort
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DocLine_Sort extends org.compiere.model.PO implements I_C_DocLine_Sort, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1826363935L;

    /** Standard Constructor */
    public X_C_DocLine_Sort (Properties ctx, int C_DocLine_Sort_ID, String trxName)
    {
      super (ctx, C_DocLine_Sort_ID, trxName);
      /** if (C_DocLine_Sort_ID == 0)
        {
			setC_DocLine_Sort_ID (0);
			setDocBaseType (null);
			setIsDefault (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_DocLine_Sort (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Document Line Sorting Preferences.
		@param C_DocLine_Sort_ID Document Line Sorting Preferences	  */
	@Override
	public void setC_DocLine_Sort_ID (int C_DocLine_Sort_ID)
	{
		if (C_DocLine_Sort_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocLine_Sort_ID, Integer.valueOf(C_DocLine_Sort_ID));
	}

	/** Get Document Line Sorting Preferences.
		@return Document Line Sorting Preferences	  */
	@Override
	public int getC_DocLine_Sort_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocLine_Sort_ID);
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
				 return ((Boolean)oo).booleanValue(); 
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
}