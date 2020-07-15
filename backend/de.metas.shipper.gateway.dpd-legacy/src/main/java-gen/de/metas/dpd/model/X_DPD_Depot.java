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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for DPD_Depot
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_DPD_Depot extends PO implements I_DPD_Depot, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100128L;

    /** Standard Constructor */
    public X_DPD_Depot (Properties ctx, int DPD_Depot_ID, String trxName)
    {
      super (ctx, DPD_Depot_ID, trxName);
      /** if (DPD_Depot_ID == 0)
        {
			setDPD_Depot_ID (0);
			setDPD_FileInfo_ID (0);
// 9
			setGeoPostDepotNumber (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_DPD_Depot (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_DPD_Depot[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Adresszeile 1.
		@param Address1 
		Adresszeile 1 fuer diesen Standort
	  */
	public void setAddress1 (String Address1)
	{
		set_Value (COLUMNNAME_Address1, Address1);
	}

	/** Get Adresszeile 1.
		@return Adresszeile 1 fuer diesen Standort
	  */
	public String getAddress1 () 
	{
		return (String)get_Value(COLUMNNAME_Address1);
	}

	/** Set Adresszeile 2.
		@param Address2 
		Adresszeile 2 fuer diesen Standort
	  */
	public void setAddress2 (String Address2)
	{
		set_Value (COLUMNNAME_Address2, Address2);
	}

	/** Get Adresszeile 2.
		@return Adresszeile 2 fuer diesen Standort
	  */
	public String getAddress2 () 
	{
		return (String)get_Value(COLUMNNAME_Address2);
	}

	/** Set Ort.
		@param City 
		Name des Ortes
	  */
	public void setCity (String City)
	{
		set_Value (COLUMNNAME_City, City);
	}

	/** Get Ort.
		@return Name des Ortes
	  */
	public String getCity () 
	{
		return (String)get_Value(COLUMNNAME_City);
	}

	/** Set ISO Laendercode.
		@param CountryCode 
		Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public void setCountryCode (String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Laendercode.
		@return Zweibuchstabiger ISO Laendercode gemaess ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public String getCountryCode () 
	{
		return (String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set DPD_Depot.
		@param DPD_Depot_ID DPD_Depot	  */
	public void setDPD_Depot_ID (int DPD_Depot_ID)
	{
		if (DPD_Depot_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_Depot_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_Depot_ID, Integer.valueOf(DPD_Depot_ID));
	}

	/** Get DPD_Depot.
		@return DPD_Depot	  */
	public int getDPD_Depot_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_Depot_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.dpd.model.I_DPD_FileInfo getDPD_FileInfo() throws RuntimeException
    {
		return (de.metas.dpd.model.I_DPD_FileInfo)MTable.get(getCtx(), de.metas.dpd.model.I_DPD_FileInfo.Table_Name)
			.getPO(getDPD_FileInfo_ID(), get_TrxName());	}

	/** Set DPD_FileInfo.
		@param DPD_FileInfo_ID DPD_FileInfo	  */
	public void setDPD_FileInfo_ID (int DPD_FileInfo_ID)
	{
		if (DPD_FileInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DPD_FileInfo_ID, Integer.valueOf(DPD_FileInfo_ID));
	}

	/** Get DPD_FileInfo.
		@return DPD_FileInfo	  */
	public int getDPD_FileInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DPD_FileInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EMail.
		@param EMail 
		EMail-Adresse
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return EMail-Adresse
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Fax.
		@param Fax 
		Faxnummer
	  */
	public void setFax (String Fax)
	{
		set_Value (COLUMNNAME_Fax, Fax);
	}

	/** Get Fax.
		@return Faxnummer
	  */
	public String getFax () 
	{
		return (String)get_Value(COLUMNNAME_Fax);
	}

	/** Set GeoPostDepotNumber.
		@param GeoPostDepotNumber GeoPostDepotNumber	  */
	public void setGeoPostDepotNumber (String GeoPostDepotNumber)
	{
		set_Value (COLUMNNAME_GeoPostDepotNumber, GeoPostDepotNumber);
	}

	/** Get GeoPostDepotNumber.
		@return GeoPostDepotNumber	  */
	public String getGeoPostDepotNumber () 
	{
		return (String)get_Value(COLUMNNAME_GeoPostDepotNumber);
	}

	/** Set GroupID.
		@param GroupID GroupID	  */
	public void setGroupID (String GroupID)
	{
		set_Value (COLUMNNAME_GroupID, GroupID);
	}

	/** Get GroupID.
		@return GroupID	  */
	public String getGroupID () 
	{
		return (String)get_Value(COLUMNNAME_GroupID);
	}

	/** Set IATALikeCode.
		@param IATALikeCode IATALikeCode	  */
	public void setIATALikeCode (String IATALikeCode)
	{
		set_Value (COLUMNNAME_IATALikeCode, IATALikeCode);
	}

	/** Get IATALikeCode.
		@return IATALikeCode	  */
	public String getIATALikeCode () 
	{
		return (String)get_Value(COLUMNNAME_IATALikeCode);
	}

	public I_M_Shipper getM_Shipper() throws RuntimeException
    {
		return (I_M_Shipper)MTable.get(getCtx(), I_M_Shipper.Table_Name)
			.getPO(getM_Shipper_ID(), get_TrxName());	}

	/** Set Lieferweg.
		@param M_Shipper_ID 
		Methode oder Art der Warenlieferung
	  */
	public void setM_Shipper_ID (int M_Shipper_ID)
	{
		if (M_Shipper_ID < 1) 
			set_Value (COLUMNNAME_M_Shipper_ID, null);
		else 
			set_Value (COLUMNNAME_M_Shipper_ID, Integer.valueOf(M_Shipper_ID));
	}

	/** Get Lieferweg.
		@return Methode oder Art der Warenlieferung
	  */
	public int getM_Shipper_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Shipper_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Name 2.
		@param Name2 
		Zusaetzliche Bezeichnung
	  */
	public void setName2 (String Name2)
	{
		set_Value (COLUMNNAME_Name2, Name2);
	}

	/** Get Name 2.
		@return Zusaetzliche Bezeichnung
	  */
	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_Name2);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	/** Set PLZ.
		@param Postal 
		Postleitzahl
	  */
	public void setPostal (String Postal)
	{
		set_Value (COLUMNNAME_Postal, Postal);
	}

	/** Get PLZ.
		@return Postleitzahl
	  */
	public String getPostal () 
	{
		return (String)get_Value(COLUMNNAME_Postal);
	}

	/** Set URL.
		@param URL 
		Full URL address - e.g. http://www.adempiere.org
	  */
	public void setURL (String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	/** Get URL.
		@return Full URL address - e.g. http://www.adempiere.org
	  */
	public String getURL () 
	{
		return (String)get_Value(COLUMNNAME_URL);
	}
}
