/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved. *
 * This program is free software, you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program, if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package de.metas.shipping.model;

import static de.metas.shipping.model.I_M_ShipperTransportation.COLUMNNAME_IsApproved;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * Generated Model for M_ShipperTransportation
 *
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_M_ShipperTransportation extends org.compiere.model.PO implements I_M_ShipperTransportation, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1239800686L;

	/** Standard Constructor */
	public X_M_ShipperTransportation(Properties ctx, int M_ShipperTransportation_ID, String trxName)
	{
		super(ctx, M_ShipperTransportation_ID, trxName);
		/**
		 * if (M_ShipperTransportation_ID == 0)
		 * {
		 * setC_DocType_ID (0);
		 * setCollectiveBillReport (null);
		 * setDateDoc (new Timestamp( System.currentTimeMillis() ));
		 * // @#Date@
		 * setDocAction (null);
		 * // CO
		 * setDocStatus (null);
		 * // DR
		 * setDocumentNo (null);
		 * setIsApproved (false);
		 * setM_ShipperTransportation_ID (0);
		 * setProcessed (false);
		 * setShipper_BPartner_ID (0);
		 * setShipper_Location_ID (0);
		 * }
		 */
	}

	/** Load Constructor */
	public X_M_ShipperTransportation(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * AccessLevel
	 *
	 * @return 1 - Org
	 */
	@Override
	protected int get_AccessLevel()
	{
		return accessLevel.intValue();
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
		return poi;
	}

	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer("X_M_ShipperTransportation[")
				.append(get_ID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Ladeliste erstellen.
	 *
	 * @param BillLadingReport Ladeliste erstellen
	 */
	@Override
	public void setBillLadingReport(java.lang.String BillLadingReport)
	{
		set_Value(COLUMNNAME_BillLadingReport, BillLadingReport);
	}

	/**
	 * Get Ladeliste erstellen.
	 *
	 * @return Ladeliste erstellen
	 */
	@Override
	public java.lang.String getBillLadingReport()
	{
		return (java.lang.String)get_Value(COLUMNNAME_BillLadingReport);
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

	/**
	 * Set Belegart.
	 *
	 * @param C_DocType_ID
	 *            Belegart oder Verarbeitungsvorgaben
	 */
	@Override
	public void setC_DocType_ID(int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_ValueNoCheck(COLUMNNAME_C_DocType_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/**
	 * Get Belegart.
	 *
	 * @return Belegart oder Verarbeitungsvorgaben
	 */
	@Override
	public int getC_DocType_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Sammelrechnung erstellen.
	 *
	 * @param CollectiveBillReport Sammelrechnung erstellen
	 */
	@Override
	public void setCollectiveBillReport(java.lang.String CollectiveBillReport)
	{
		set_Value(COLUMNNAME_CollectiveBillReport, CollectiveBillReport);
	}

	/**
	 * Get Sammelrechnung erstellen.
	 *
	 * @return Sammelrechnung erstellen
	 */
	@Override
	public java.lang.String getCollectiveBillReport()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CollectiveBillReport);
	}

	/**
	 * Set Packstücke erfassen.
	 *
	 * @param CreateShippingPackages Packstücke erfassen
	 */
	@Override
	public void setCreateShippingPackages(java.lang.String CreateShippingPackages)
	{
		set_Value(COLUMNNAME_CreateShippingPackages, CreateShippingPackages);
	}

	/**
	 * Get Packstücke erfassen.
	 *
	 * @return Packstücke erfassen
	 */
	@Override
	public java.lang.String getCreateShippingPackages()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreateShippingPackages);
	}

	/**
	 * Set Belegdatum.
	 *
	 * @param DateDoc
	 *            Datum des Belegs
	 */
	@Override
	public void setDateDoc(java.sql.Timestamp DateDoc)
	{
		set_Value(COLUMNNAME_DateDoc, DateDoc);
	}

	/**
	 * Get Belegdatum.
	 *
	 * @return Datum des Belegs
	 */
	@Override
	public java.sql.Timestamp getDateDoc()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateDoc);
	}

	/**
	 * Set Beschreibung.
	 *
	 * @param Description Beschreibung
	 */
	@Override
	public void setDescription(java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	/**
	 * Get Beschreibung.
	 *
	 * @return Beschreibung
	 */
	@Override
	public java.lang.String getDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/**
	 * DocAction AD_Reference_ID=135
	 * Reference name: _Document Action
	 */
	public static final int DOCACTION_AD_Reference_ID = 135;
	/** Fertigstellen = CO */
	public static final String DOCACTION_Fertigstellen = "CO";
	/** Genehmigen = AP */
	public static final String DOCACTION_Genehmigen = "AP";
	/** Ablehnen = RJ */
	public static final String DOCACTION_Ablehnen = "RJ";
	/** Buchen = PO */
	public static final String DOCACTION_Buchen = "PO";
	/** Loeschen = VO */
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

	/**
	 * Set Document Action.
	 *
	 * @param DocAction
	 *            The targeted status of the document
	 */
	@Override
	public void setDocAction(java.lang.String DocAction)
	{

		set_Value(COLUMNNAME_DocAction, DocAction);
	}

	/**
	 * Get Document Action.
	 *
	 * @return The targeted status of the document
	 */
	@Override
	public java.lang.String getDocAction()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocAction);
	}

	/**
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID = 131;
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
	/** Ungueltig = IN */
	public static final String DOCSTATUS_Ungueltig = "IN";
	/** Rueckgaengig = RE */
	public static final String DOCSTATUS_Rueckgaengig = "RE";
	/** Geschlossen = CL */
	public static final String DOCSTATUS_Geschlossen = "CL";
	/** Unbekannt = ?? */
	public static final String DOCSTATUS_Unbekannt = "??";
	/** In Verarbeitung = IP */
	public static final String DOCSTATUS_InVerarbeitung = "IP";
	/** Warten auf Zahlung = WP */
	public static final String DOCSTATUS_WartenAufZahlung = "WP";
	/** Warten auf Bestaetigung = WC */
	public static final String DOCSTATUS_WartenAufBestaetigung = "WC";

	/**
	 * Set Belegstatus.
	 *
	 * @param DocStatus
	 *            The current status of the document
	 */
	@Override
	public void setDocStatus(java.lang.String DocStatus)
	{

		set_Value(COLUMNNAME_DocStatus, DocStatus);
	}

	/**
	 * Get Belegstatus.
	 *
	 * @return The current status of the document
	 */
	@Override
	public java.lang.String getDocStatus()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocStatus);
	}

	/**
	 * Set Document No.
	 *
	 * @param DocumentNo
	 *            Document sequence number of the document
	 */
	@Override
	public void setDocumentNo(java.lang.String DocumentNo)
	{
		set_Value(COLUMNNAME_DocumentNo, DocumentNo);
	}

	/**
	 * Get Document No.
	 *
	 * @return Document sequence number of the document
	 */
	@Override
	public java.lang.String getDocumentNo()
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/**
	 * Get Record ID/ColumnName
	 *
	 * @return ID/ColumnName pair
	 */
	public org.compiere.util.KeyNamePair getKeyNamePair()
	{
		return new org.compiere.util.KeyNamePair(get_ID(), getDocumentNo());
	}

	/**
	 * Set Approved.
	 *
	 * @param IsApproved
	 *            Indicates if this document requires approval
	 */
	@Override
	public void setIsApproved(boolean IsApproved)
	{
		set_Value(COLUMNNAME_IsApproved, Boolean.valueOf(IsApproved));
	}

	/**
	 * Get Approved.
	 *
	 * @return Indicates if this document requires approval
	 */
	@Override
	public boolean isApproved()
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

	@Override
	public org.compiere.model.I_M_Shipper getM_Shipper() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class);
	}

	@Override
	public void setM_Shipper(org.compiere.model.I_M_Shipper M_Shipper)
	{
		set_ValueFromPO(COLUMNNAME_M_Shipper_ID, org.compiere.model.I_M_Shipper.class, M_Shipper);
	}

	/**
	 * Set Lieferweg.
	 *
	 * @param M_Shipper_ID
	 *            Methode oder Art der Warenlieferung
	 */
	@Override
	public void setM_Shipper_ID(int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1)
			set_Value(COLUMNNAME_M_Shipper_ID, null);
		else
			set_Value(COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/**
	 * Get Lieferweg.
	 *
	 * @return Methode oder Art der Warenlieferung
	 */
	@Override
	public int getM_Shipper_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Shipper Transportation.
	 *
	 * @param M_ShipperTransportation_ID Shipper Transportation
	 */
	@Override
	public void setM_ShipperTransportation_ID(int M_ShipperTransportation_ID)
	{
		if (M_ShipperTransportation_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_ShipperTransportation_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_ShipperTransportation_ID, Integer.valueOf(M_ShipperTransportation_ID));
	}

	/**
	 * Get Shipper Transportation.
	 *
	 * @return Shipper Transportation
	 */
	@Override
	public int getM_ShipperTransportation_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipperTransportation_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Package Net Total.
	 *
	 * @param PackageNetTotal Package Net Total
	 */
	@Override
	public void setPackageNetTotal(java.math.BigDecimal PackageNetTotal)
	{
		set_Value(COLUMNNAME_PackageNetTotal, PackageNetTotal);
	}

	/**
	 * Get Package Net Total.
	 *
	 * @return Package Net Total
	 */
	@Override
	public java.math.BigDecimal getPackageNetTotal()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PackageNetTotal);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Package Weight.
	 *
	 * @param PackageWeight
	 *            Weight of a package
	 */
	@Override
	public void setPackageWeight(java.math.BigDecimal PackageWeight)
	{
		set_Value(COLUMNNAME_PackageWeight, PackageWeight);
	}

	/**
	 * Get Package Weight.
	 *
	 * @return Weight of a package
	 */
	@Override
	public java.math.BigDecimal getPackageWeight()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PackageWeight);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Verarbeitet.
	 *
	 * @param Processed
	 *            Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public void setProcessed(boolean Processed)
	{
		set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/**
	 * Get Verarbeitet.
	 *
	 * @return Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public boolean isProcessed()
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

	/**
	 * Set Process Now.
	 *
	 * @param Processing Process Now
	 */
	@Override
	public void setProcessing(boolean Processing)
	{
		set_Value(COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/**
	 * Get Process Now.
	 *
	 * @return Process Now
	 */
	@Override
	public boolean isProcessing()
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

	@Override
	public org.compiere.model.I_C_BPartner getShipper_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Shipper_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setShipper_BPartner(org.compiere.model.I_C_BPartner Shipper_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Shipper_BPartner_ID, org.compiere.model.I_C_BPartner.class, Shipper_BPartner);
	}

	/**
	 * Set Shipper Partner.
	 *
	 * @param Shipper_BPartner_ID
	 *            Business Partner to be used as shipper
	 */
	@Override
	public void setShipper_BPartner_ID(int Shipper_BPartner_ID)
	{
		if (Shipper_BPartner_ID < 1)
			set_Value(COLUMNNAME_Shipper_BPartner_ID, null);
		else
			set_Value(COLUMNNAME_Shipper_BPartner_ID, Integer.valueOf(Shipper_BPartner_ID));
	}

	/**
	 * Get Shipper Partner.
	 *
	 * @return Business Partner to be used as shipper
	 */
	@Override
	public int getShipper_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Shipper_BPartner_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getShipper_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Shipper_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setShipper_Location(org.compiere.model.I_C_BPartner_Location Shipper_Location)
	{
		set_ValueFromPO(COLUMNNAME_Shipper_Location_ID, org.compiere.model.I_C_BPartner_Location.class, Shipper_Location);
	}

	/**
	 * Set Shipper Location.
	 *
	 * @param Shipper_Location_ID
	 *            Business Partner Location for Shipper
	 */
	@Override
	public void setShipper_Location_ID(int Shipper_Location_ID)
	{
		if (Shipper_Location_ID < 1)
			set_Value(COLUMNNAME_Shipper_Location_ID, null);
		else
			set_Value(COLUMNNAME_Shipper_Location_ID, Integer.valueOf(Shipper_Location_ID));
	}

	/**
	 * Get Shipper Location.
	 *
	 * @return Business Partner Location for Shipper
	 */
	@Override
	public int getShipper_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Shipper_Location_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Abholung am.
	 *
	 * @param DateToBeFetched
	 *            Date and time to fetch
	 */
	@Override
	public void setDateToBeFetched(java.sql.Timestamp DateToBeFetched)
	{
		set_Value(COLUMNNAME_DateToBeFetched, DateToBeFetched);
	}

	/**
	 * Get Abholung am.
	 *
	 * @return Date and time to fetch
	 */
	@Override
	public java.sql.Timestamp getDateToBeFetched()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateToBeFetched);
	}
}
