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
package de.metas.dpd.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_M_Package;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for DPD_StatusData
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_StatusData extends PO implements I_DPD_StatusData, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_StatusData (Properties ctx, int DPD_StatusData_ID, String trxName)
    {
      super (ctx, DPD_StatusData_ID, trxName);
      /** if (DPD_StatusData_ID == 0)
        {
			setDPD_StatusData_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DPD_StatusData (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_DPD_StatusData[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.dpd.model.I_DPD_ExceptionCode getAddService1() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_ExceptionCode)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_ExceptionCode.Table_Name)
			.getPO(getAddService1_ID(), get_TrxName());	}

	/** Set Zusatzcode-1.
		@param AddService1_ID 
		Zusatzcode oder Differenzcode
	  */
	public void setAddService1_ID (int AddService1_ID)
	{
		if (AddService1_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AddService1_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AddService1_ID, Integer.valueOf(AddService1_ID));
	}

	/** Get Zusatzcode-1.
		@return Zusatzcode oder Differenzcode
	  */
	public int getAddService1_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AddService1_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.dpd.model.I_DPD_ExceptionCode getAddService2() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_ExceptionCode)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_ExceptionCode.Table_Name)
			.getPO(getAddService2_ID(), get_TrxName());	}

	/** Set Zusatzcode-2.
		@param AddService2_ID 
		Zusatzcode oder Differenzcode
	  */
	public void setAddService2_ID (int AddService2_ID)
	{
		if (AddService2_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AddService2_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AddService2_ID, Integer.valueOf(AddService2_ID));
	}

	/** Get Zusatzcode-2.
		@return Zusatzcode oder Differenzcode
	  */
	public int getAddService2_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AddService2_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.dpd.model.I_DPD_ExceptionCode getAddService3() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_ExceptionCode)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_ExceptionCode.Table_Name)
			.getPO(getAddService3_ID(), get_TrxName());	}

	/** Set Zusatzcode-3.
		@param AddService3_ID 
		Zusatzcode oder Differenzcode
	  */
	public void setAddService3_ID (int AddService3_ID)
	{
		if (AddService3_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AddService3_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AddService3_ID, Integer.valueOf(AddService3_ID));
	}

	/** Get Zusatzcode-3.
		@return Zusatzcode oder Differenzcode
	  */
	public int getAddService3_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AddService3_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Empfaenger-Laendercode.
		@param ConsigneeCountryCode 
		3-stellig numerischer Laendercode nach ISO 3166 des Empfaengers
	  */
	public void setConsigneeCountryCode (String ConsigneeCountryCode)
	{
		set_ValueNoCheck (COLUMNNAME_ConsigneeCountryCode, ConsigneeCountryCode);
	}

	/** Get Empfaenger-Laendercode.
		@return 3-stellig numerischer Laendercode nach ISO 3166 des Empfaengers
	  */
	public String getConsigneeCountryCode () 
	{
		return (String)get_Value(COLUMNNAME_ConsigneeCountryCode);
	}

	/** Set Empfaenger-PLZ.
		@param ConsigneeZip 
		Postleitzahl des Empfaengers
	  */
	public void setConsigneeZip (String ConsigneeZip)
	{
		set_ValueNoCheck (COLUMNNAME_ConsigneeZip, ConsigneeZip);
	}

	/** Get Empfaenger-PLZ.
		@return Postleitzahl des Empfaengers
	  */
	public String getConsigneeZip () 
	{
		return (String)get_Value(COLUMNNAME_ConsigneeZip);
	}

	/** Set Kundenreferenz.
		@param CustomerReference Kundenreferenz	  */
	public void setCustomerReference (String CustomerReference)
	{
		set_ValueNoCheck (COLUMNNAME_CustomerReference, CustomerReference);
	}

	/** Get Kundenreferenz.
		@return Kundenreferenz	  */
	public String getCustomerReference () 
	{
		return (String)get_Value(COLUMNNAME_CustomerReference);
	}

	/** Set Scannendes Depot.
		@param DepotCode Scannendes Depot	  */
	public void setDepotCode (String DepotCode)
	{
		set_ValueNoCheck (COLUMNNAME_DepotCode, DepotCode);
	}

	/** Get Scannendes Depot.
		@return Scannendes Depot	  */
	public String getDepotCode () 
	{
		return (String)get_Value(COLUMNNAME_DepotCode);
	}

	/** Set Depotname.
		@param DepotName 
		Name des Depots laut Routentabelle
	  */
	public void setDepotName (String DepotName)
	{
		set_ValueNoCheck (COLUMNNAME_DepotName, DepotName);
	}

	/** Get Depotname.
		@return Name des Depots laut Routentabelle
	  */
	public String getDepotName () 
	{
		return (String)get_Value(COLUMNNAME_DepotName);
	}

	public de.metas.dpd.model.I_DPD_ScanCode getDPD_ScanCode() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_ScanCode)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_ScanCode.Table_Name)
			.getPO(getDPD_ScanCode_ID(), get_TrxName());	}

	/** Set Scanart.
		@param DPD_ScanCode_ID Scanart	  */
	public void setDPD_ScanCode_ID (int DPD_ScanCode_ID)
	{
		if (DPD_ScanCode_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_ScanCode_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_ScanCode_ID, Integer.valueOf(DPD_ScanCode_ID));
	}

	/** Get Scanart.
		@return Scanart	  */
	public int getDPD_ScanCode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_ScanCode_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DPD-Statusdaten.
		@param DPD_StatusData_ID DPD-Statusdaten	  */
	public void setDPD_StatusData_ID (int DPD_StatusData_ID)
	{
		if (DPD_StatusData_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_StatusData_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_StatusData_ID, Integer.valueOf(DPD_StatusData_ID));
	}

	/** Get DPD-Statusdaten.
		@return DPD-Statusdaten	  */
	public int getDPD_StatusData_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_StatusData_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Scanzeitpunkt.
		@param EventDateTime 
		Scandatum und -zeit
	  */
	public void setEventDateTime (Timestamp EventDateTime)
	{
		set_ValueNoCheck (COLUMNNAME_EventDateTime, EventDateTime);
	}

	/** Get Scanzeitpunkt.
		@return Scandatum und -zeit
	  */
	public Timestamp getEventDateTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EventDateTime);
	}

	/** Set Infotext.
		@param InfoText 
		Infotext fuer Scanart 18
	  */
	public void setInfoText (String InfoText)
	{
		set_ValueNoCheck (COLUMNNAME_InfoText, InfoText);
	}

	/** Get Infotext.
		@return Infotext fuer Scanart 18
	  */
	public String getInfoText () 
	{
		return (String)get_Value(COLUMNNAME_InfoText);
	}

	/** Set Ort.
		@param Location Ort	  */
	public void setLocation (String Location)
	{
		set_ValueNoCheck (COLUMNNAME_Location, Location);
	}

	/** Get Ort.
		@return Ort	  */
	public String getLocation () 
	{
		return (String)get_Value(COLUMNNAME_Location);
	}

	public I_M_Package getM_Package() throws RuntimeException
    {
		return (I_M_Package)MTable.get(getCtx(), I_M_Package.Table_Name)
			.getPO(getM_Package_ID(), get_TrxName());	}

	/** Set Packstueck.
		@param M_Package_ID 
		Shipment Package
	  */
	public void setM_Package_ID (int M_Package_ID)
	{
		if (M_Package_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Package_ID, Integer.valueOf(M_Package_ID));
	}

	/** Get Packstueck.
		@return Shipment Package
	  */
	public int getM_Package_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Package_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Paketscheinnummer.
		@param ParcellNo Paketscheinnummer	  */
	public void setParcellNo (String ParcellNo)
	{
		set_ValueNoCheck (COLUMNNAME_ParcellNo, ParcellNo);
	}

	/** Get Paketscheinnummer.
		@return Paketscheinnummer	  */
	public String getParcellNo () 
	{
		return (String)get_Value(COLUMNNAME_ParcellNo);
	}

	/** Set Produkt Code.
		@param PCode Produkt Code	  */
	public void setPCode (String PCode)
	{
		set_ValueNoCheck (COLUMNNAME_PCode, PCode);
	}

	/** Get Produkt Code.
		@return Produkt Code	  */
	public String getPCode () 
	{
		return (String)get_Value(COLUMNNAME_PCode);
	}

	/** Set Ref-Ausrollistennummer.
		@param PodImageRef 
		Referenz zur Ausrollistennummer
	  */
	public void setPodImageRef (String PodImageRef)
	{
		set_ValueNoCheck (COLUMNNAME_PodImageRef, PodImageRef);
	}

	/** Get Ref-Ausrollistennummer.
		@return Referenz zur Ausrollistennummer
	  */
	public String getPodImageRef () 
	{
		return (String)get_Value(COLUMNNAME_PodImageRef);
	}

	/** Set Unterzeichner-Name.
		@param ReceiverName 
		Name des Unterzeichners/Empfaengers der Lieferung
	  */
	public void setReceiverName (String ReceiverName)
	{
		set_ValueNoCheck (COLUMNNAME_ReceiverName, ReceiverName);
	}

	/** Get Unterzeichner-Name.
		@return Name des Unterzeichners/Empfaengers der Lieferung
	  */
	public String getReceiverName () 
	{
		return (String)get_Value(COLUMNNAME_ReceiverName);
	}

	/** Set Route.
		@param Route Route	  */
	public void setRoute (String Route)
	{
		set_ValueNoCheck (COLUMNNAME_Route, Route);
	}

	/** Get Route.
		@return Route	  */
	public String getRoute () 
	{
		return (String)get_Value(COLUMNNAME_Route);
	}

	/** Set Servicecode.
		@param Service Servicecode	  */
	public void setService (String Service)
	{
		set_ValueNoCheck (COLUMNNAME_Service, Service);
	}

	/** Get Servicecode.
		@return Servicecode	  */
	public String getService () 
	{
		return (String)get_Value(COLUMNNAME_Service);
	}

	/** Set Tournummer.
		@param Tour Tournummer	  */
	public void setTour (String Tour)
	{
		set_ValueNoCheck (COLUMNNAME_Tour, Tour);
	}

	/** Get Tournummer.
		@return Tournummer	  */
	public String getTour () 
	{
		return (String)get_Value(COLUMNNAME_Tour);
	}

	/** Set Gewicht.
		@param Weight 
		Gewicht eines Produktes
	  */
	public void setWeight (BigDecimal Weight)
	{
		set_ValueNoCheck (COLUMNNAME_Weight, Weight);
	}

	/** Get Gewicht.
		@return Gewicht eines Produktes
	  */
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
