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

/** Generated Model for C_Sponsor_Cond
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Sponsor_Cond extends PO implements I_C_Sponsor_Cond, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_Sponsor_Cond (Properties ctx, int C_Sponsor_Cond_ID, String trxName)
    {
      super (ctx, C_Sponsor_Cond_ID, trxName);
      /** if (C_Sponsor_Cond_ID == 0)
        {
			setCondChangeDate (new Timestamp( System.currentTimeMillis() ));
// @#Date@
			setC_Sponsor_Cond_ID (0);
			setC_Sponsor_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setProcessed (false);
// N
			setProcessing (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_Sponsor_Cond (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Sponsor_Cond[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Belegdatum.
		@param CondChangeDate 
		Datum, zu dem die Ã„nderungen in Kraft treten
	  */
	public void setCondChangeDate (Timestamp CondChangeDate)
	{
		set_Value (COLUMNNAME_CondChangeDate, CondChangeDate);
	}

	/** Get Belegdatum.
		@return Datum, zu dem die Ã„nderungen in Kraft treten
	  */
	public Timestamp getCondChangeDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CondChangeDate);
	}

	/** Set Sponsor Konditionen.
		@param C_Sponsor_Cond_ID Sponsor Konditionen	  */
	public void setC_Sponsor_Cond_ID (int C_Sponsor_Cond_ID)
	{
		if (C_Sponsor_Cond_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Cond_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Cond_ID, Integer.valueOf(C_Sponsor_Cond_ID));
	}

	/** Get Sponsor Konditionen.
		@return Sponsor Konditionen	  */
	public int getC_Sponsor_Cond_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_Cond_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeilen.
		@param C_SponsorCondLine_IncludedTab Zeilen	  */
	public void setC_SponsorCondLine_IncludedTab (String C_SponsorCondLine_IncludedTab)
	{
		set_Value (COLUMNNAME_C_SponsorCondLine_IncludedTab, C_SponsorCondLine_IncludedTab);
	}

	/** Get Zeilen.
		@return Zeilen	  */
	public String getC_SponsorCondLine_IncludedTab () 
	{
		return (String)get_Value(COLUMNNAME_C_SponsorCondLine_IncludedTab);
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_ID(), get_TrxName());	}

	/** Set Sponsor.
		@param C_Sponsor_ID Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID)
	{
		if (C_Sponsor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, Integer.valueOf(C_Sponsor_ID));
	}

	/** Get Sponsor.
		@return Sponsor	  */
	public int getC_Sponsor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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
		Der zukÃ¼nftige Status des Belegs
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Belegverarbeitung.
		@return Der zukÃ¼nftige Status des Belegs
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

	/** Set Verarbeitet.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
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
}