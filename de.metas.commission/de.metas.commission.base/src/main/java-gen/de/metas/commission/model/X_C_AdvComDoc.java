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
package de.metas.commission.model;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_AdvComDoc
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComDoc extends PO implements I_C_AdvComDoc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComDoc (Properties ctx, int C_AdvComDoc_ID, String trxName)
    {
      super (ctx, C_AdvComDoc_ID, trxName);
      /** if (C_AdvComDoc_ID == 0)
        {
			setAD_Table_ID (0);
			setC_AdvComDoc_ID (0);
			setC_DocType_Ref_ID (0);
			setDateDoc_Ref (new Timestamp( System.currentTimeMillis() ));
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo_Ref (null);
			setIsApproved (false);
// N
			setIsProcessedByComSystem (false);
// N
			setProcessed (false);
// N
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComDoc (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_AdvComDoc[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionsauslöser.
		@param C_AdvComDoc_ID Provisionsauslöser	  */
	public void setC_AdvComDoc_ID (int C_AdvComDoc_ID)
	{
		if (C_AdvComDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComDoc_ID, Integer.valueOf(C_AdvComDoc_ID));
	}

	/** Get Provisionsauslöser.
		@return Provisionsauslöser	  */
	public int getC_AdvComDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_AllocationLine getC_AllocationLine() throws RuntimeException
    {
		return (org.compiere.model.I_C_AllocationLine)MTable.get(getCtx(), org.compiere.model.I_C_AllocationLine.Table_Name)
			.getPO(getC_AllocationLine_ID(), get_TrxName());	}

	/** Set Zuordnungs-Position.
		@param C_AllocationLine_ID 
		Zuordnungs-Position
	  */
	public void setC_AllocationLine_ID (int C_AllocationLine_ID)
	{
		if (C_AllocationLine_ID < 1) 
			set_Value (COLUMNNAME_C_AllocationLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_AllocationLine_ID, Integer.valueOf(C_AllocationLine_ID));
	}

	/** Get Zuordnungs-Position.
		@return Zuordnungs-Position
	  */
	public int getC_AllocationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AllocationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_DocType getC_DocType_Ref() throws RuntimeException
    {
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_Ref_ID(), get_TrxName());	}

	/** Set Belegart.
		@param C_DocType_Ref_ID Belegart	  */
	public void setC_DocType_Ref_ID (int C_DocType_Ref_ID)
	{
		if (C_DocType_Ref_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Ref_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_Ref_ID, Integer.valueOf(C_DocType_Ref_ID));
	}

	/** Get Belegart.
		@return Belegart	  */
	public int getC_DocType_Ref_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_Ref_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Belegdatum.
		@param DateDoc_Ref Belegdatum	  */
	public void setDateDoc_Ref (Timestamp DateDoc_Ref)
	{
		set_ValueNoCheck (COLUMNNAME_DateDoc_Ref, DateDoc_Ref);
	}

	/** Get Belegdatum.
		@return Belegdatum	  */
	public Timestamp getDateDoc_Ref () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc_Ref);
	}

	/** Set Valuta-Datum.
		@param DateFact Valuta-Datum	  */
	public void setDateFact (Timestamp DateFact)
	{
		set_Value (COLUMNNAME_DateFact, DateFact);
	}

	/** Get Valuta-Datum.
		@return Valuta-Datum	  */
	public Timestamp getDateFact () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFact);
	}

	/** Set Abw. Valutadatum.
		@param DateFact_Override Abw. Valutadatum	  */
	public void setDateFact_Override (Timestamp DateFact_Override)
	{
		set_Value (COLUMNNAME_DateFact_Override, DateFact_Override);
	}

	/** Get Abw. Valutadatum.
		@return Abw. Valutadatum	  */
	public Timestamp getDateFact_Override () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFact_Override);
	}

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Fertigstellen = CO */
	public static final String DOCACTION_Fertigstellen = "CO";
	/** Genehmigen = AP */
	public static final String DOCACTION_Genehmigen = "AP";
	/** Ablehnen = RJ */
	public static final String DOCACTION_Ablehnen = "RJ";
	/** Buchen = PO */
	public static final String DOCACTION_Buchen = "PO";
	/** Löschen = VO */
	public static final String DOCACTION_Loeschen = "VO";
	/** Schliessen = CL */
	public static final String DOCACTION_Schliessen = "CL";
	/** Stornieren - Korrektur = RC */
	public static final String DOCACTION_Stornieren_Korrektur = "RC";
	/** Rückbuchung = RA */
	public static final String DOCACTION_Rueckbuchung = "RA";
	/** Annulieren = IN */
	public static final String DOCACTION_Annulieren = "IN";
	/** Reaktivieren = RE */
	public static final String DOCACTION_Reaktivieren = "RE";
	/** <Nichts> = -- */
	public static final String DOCACTION_Nichts = "--";
	/** Vorbereiten = PR */
	public static final String DOCACTION_Vorbereiten = "PR";
	/** Entsperren = XL */
	public static final String DOCACTION_Entsperren = "XL";
	/** Warten und fertigstellen = WC */
	public static final String DOCACTION_WartenUndFertigstellen = "WC";
	/** Set Belegverarbeitung.
		@param DocAction 
		Der zukünftige Status des Belegs
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return Der zukünftige Status des Belegs
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Entwurf = DR */
	public static final String DOCSTATUS_Entwurf = "DR";
	/** Fertiggestellt = CO */
	public static final String DOCSTATUS_Fertiggestellt = "CO";
	/** Genehmigt = AP */
	public static final String DOCSTATUS_Genehmigt = "AP";
	/** Nicht genehmigt = NA */
	public static final String DOCSTATUS_NichtGenehmigt = "NA";
	/** Storniert = VO */
	public static final String DOCSTATUS_Storniert = "VO";
	/** Ungültig = IN */
	public static final String DOCSTATUS_Ungueltig = "IN";
	/** Rückgängig = RE */
	public static final String DOCSTATUS_Rueckgaengig = "RE";
	/** Geschlossen = CL */
	public static final String DOCSTATUS_Geschlossen = "CL";
	/** Unbekannt = ?? */
	public static final String DOCSTATUS_Unbekannt = "??";
	/** In Verarbeitung = IP */
	public static final String DOCSTATUS_InVerarbeitung = "IP";
	/** Warten auf Zahlung = WP */
	public static final String DOCSTATUS_WartenAufZahlung = "WP";
	/** Warten auf Bestätigung = WC */
	public static final String DOCSTATUS_WartenAufBestaetigung = "WC";
	/** Set Belegstatus.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Belegstatus.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Belegnummer.
		@param DocumentNo_Ref Belegnummer	  */
	public void setDocumentNo_Ref (String DocumentNo_Ref)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo_Ref, DocumentNo_Ref);
	}

	/** Get Belegnummer.
		@return Belegnummer	  */
	public String getDocumentNo_Ref () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo_Ref);
	}

	/** Set Freigegeben.
		@param IsApproved 
		Zeigt an, ob dieser Beleg eine Freigabe braucht
	  */
	public void setIsApproved (boolean IsApproved)
	{
		set_Value (COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/** Get Freigegeben.
		@return Zeigt an, ob dieser Beleg eine Freigabe braucht
	  */
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

	/** Set Valutadatum Änderbar.
		@param IsDateFactOverridable Valutadatum Änderbar	  */
	public void setIsDateFactOverridable (boolean IsDateFactOverridable)
	{
		set_Value (COLUMNNAME_IsDateFactOverridable, Boolean.valueOf(IsDateFactOverridable));
	}

	/** Get Valutadatum Änderbar.
		@return Valutadatum Änderbar	  */
	public boolean isDateFactOverridable () 
	{
		Object oo = get_Value(COLUMNNAME_IsDateFactOverridable);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set In Buchauszug verbucht.
		@param IsProcessedByComSystem 
		Änderung wurde in den Buchauszug aufgenommen
	  */
	public void setIsProcessedByComSystem (boolean IsProcessedByComSystem)
	{
		set_Value (COLUMNNAME_IsProcessedByComSystem, Boolean.valueOf(IsProcessedByComSystem));
	}

	/** Get In Buchauszug verbucht.
		@return Änderung wurde in den Buchauszug aufgenommen
	  */
	public boolean isProcessedByComSystem () 
	{
		Object oo = get_Value(COLUMNNAME_IsProcessedByComSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verarbeitet.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return The document has been processed
	  */
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}