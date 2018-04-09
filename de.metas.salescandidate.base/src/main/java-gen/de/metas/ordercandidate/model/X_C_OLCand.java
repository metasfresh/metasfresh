/** Generated Model - DO NOT CHANGE */
package de.metas.ordercandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_OLCand
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_OLCand extends org.compiere.model.PO implements I_C_OLCand, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 364648425L;

    /** Standard Constructor */
    public X_C_OLCand (Properties ctx, int C_OLCand_ID, String trxName)
    {
      super (ctx, C_OLCand_ID, trxName);
      /** if (C_OLCand_ID == 0)
        {
			setAD_InputDataSource_ID (0);
			setAD_User_EnteredBy_ID (0); // @#AD_User@
			setC_BPartner_Location_ID (0);
			setC_OLCand_ID (0);
			setC_UOM_ID (0);
			setDateCandidate (new Timestamp( System.currentTimeMillis() )); // @Created@
			setDeliveryRule (null); // A
			setDeliveryViaRule (null); // P
			setIsError (false); // N
			setIsExplicitProductPriceAttribute (false); // N
			setIsManualDiscount (false); // N
			setIsManualPrice (false); // N
			setQty (BigDecimal.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_OLCand (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Data Destination.
		@param AD_DataDestination_ID Data Destination	  */
	@Override
	public void setAD_DataDestination_ID (int AD_DataDestination_ID)
	{
		if (AD_DataDestination_ID < 1) 
			set_Value (COLUMNNAME_AD_DataDestination_ID, null);
		else 
			set_Value (COLUMNNAME_AD_DataDestination_ID, Integer.valueOf(AD_DataDestination_ID));
	}

	/** Get Data Destination.
		@return Data Destination	  */
	@Override
	public int getAD_DataDestination_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_DataDestination_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Eingabequelle.
		@param AD_InputDataSource_ID Eingabequelle	  */
	@Override
	public void setAD_InputDataSource_ID (int AD_InputDataSource_ID)
	{
		if (AD_InputDataSource_ID < 1) 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, null);
		else 
			set_Value (COLUMNNAME_AD_InputDataSource_ID, Integer.valueOf(AD_InputDataSource_ID));
	}

	/** Get Eingabequelle.
		@return Eingabequelle	  */
	@Override
	public int getAD_InputDataSource_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_InputDataSource_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Note getAD_Note() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Note_ID, org.compiere.model.I_AD_Note.class);
	}

	@Override
	public void setAD_Note(org.compiere.model.I_AD_Note AD_Note)
	{
		set_ValueFromPO(COLUMNNAME_AD_Note_ID, org.compiere.model.I_AD_Note.class, AD_Note);
	}

	/** Set Statusmeldung.
		@param AD_Note_ID 
		System-Nachricht
	  */
	@Override
	public void setAD_Note_ID (int AD_Note_ID)
	{
		if (AD_Note_ID < 1) 
			set_Value (COLUMNNAME_AD_Note_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Note_ID, Integer.valueOf(AD_Note_ID));
	}

	/** Get Statusmeldung.
		@return System-Nachricht
	  */
	@Override
	public int getAD_Note_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Note_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	@Override
	public org.compiere.model.I_AD_User getAD_User_EnteredBy() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_EnteredBy_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_EnteredBy(org.compiere.model.I_AD_User AD_User_EnteredBy)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_EnteredBy_ID, org.compiere.model.I_AD_User.class, AD_User_EnteredBy);
	}

	/** Set Erfasst durch.
		@param AD_User_EnteredBy_ID Erfasst durch	  */
	@Override
	public void setAD_User_EnteredBy_ID (int AD_User_EnteredBy_ID)
	{
		if (AD_User_EnteredBy_ID < 1) 
			set_Value (COLUMNNAME_AD_User_EnteredBy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_EnteredBy_ID, Integer.valueOf(AD_User_EnteredBy_ID));
	}

	/** Get Erfasst durch.
		@return Erfasst durch	  */
	@Override
	public int getAD_User_EnteredBy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_EnteredBy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class, Bill_BPartner);
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID 
		Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setBill_Location(org.compiere.model.I_C_BPartner_Location Bill_Location)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_ID, org.compiere.model.I_C_BPartner_Location.class, Bill_Location);
	}

	/** Set Rechnungsstandort.
		@param Bill_Location_ID 
		Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1) 
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Rechnungsstandort.
		@return Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getBill_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setBill_User(org.compiere.model.I_AD_User Bill_User)
	{
		set_ValueFromPO(COLUMNNAME_Bill_User_ID, org.compiere.model.I_AD_User.class, Bill_User);
	}

	/** Set Rechnungskontakt.
		@param Bill_User_ID 
		Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_User_ID (int Bill_User_ID)
	{
		if (Bill_User_ID < 1) 
			set_Value (COLUMNNAME_Bill_User_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_User_ID, Integer.valueOf(Bill_User_ID));
	}

	/** Get Rechnungskontakt.
		@return Ansprechpartner des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BP_Location_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Location_Effective_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BP_Location_Effective(org.compiere.model.I_C_BPartner_Location C_BP_Location_Effective)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Location_Effective_ID, org.compiere.model.I_C_BPartner_Location.class, C_BP_Location_Effective);
	}

	/** Set Standort eff..
		@param C_BP_Location_Effective_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BP_Location_Effective_ID (int C_BP_Location_Effective_ID)
	{
		throw new IllegalArgumentException ("C_BP_Location_Effective_ID is virtual column");	}

	/** Get Standort eff..
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BP_Location_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Location_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BP_Location_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BP_Location_Override(org.compiere.model.I_C_BPartner_Location C_BP_Location_Override)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class, C_BP_Location_Override);
	}

	/** Set Standort abw..
		@param C_BP_Location_Override_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BP_Location_Override_ID (int C_BP_Location_Override_ID)
	{
		if (C_BP_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Location_Override_ID, Integer.valueOf(C_BP_Location_Override_ID));
	}

	/** Get Standort abw..
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BP_Location_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Location_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Effective_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner_Effective(org.compiere.model.I_C_BPartner C_BPartner_Effective)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Effective_ID, org.compiere.model.I_C_BPartner.class, C_BPartner_Effective);
	}

	/** Set Geschäftspartner eff..
		@param C_BPartner_Effective_ID Geschäftspartner eff.	  */
	@Override
	public void setC_BPartner_Effective_ID (int C_BPartner_Effective_ID)
	{
		throw new IllegalArgumentException ("C_BPartner_Effective_ID is virtual column");	}

	/** Get Geschäftspartner eff..
		@return Geschäftspartner eff.	  */
	@Override
	public int getC_BPartner_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Effective_ID);
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
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Override_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner_Override(org.compiere.model.I_C_BPartner C_BPartner_Override)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Override_ID, org.compiere.model.I_C_BPartner.class, C_BPartner_Override);
	}

	/** Set Geschäftspartner abw..
		@param C_BPartner_Override_ID 
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_Override_ID (int C_BPartner_Override_ID)
	{
		if (C_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Override_ID, Integer.valueOf(C_BPartner_Override_ID));
	}

	/** Get Geschäftspartner abw..
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Charge getC_Charge() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class);
	}

	@Override
	public void setC_Charge(org.compiere.model.I_C_Charge C_Charge)
	{
		set_ValueFromPO(COLUMNNAME_C_Charge_ID, org.compiere.model.I_C_Charge.class, C_Charge);
	}

	/** Set Kosten.
		@param C_Charge_ID 
		Zusätzliche Kosten
	  */
	@Override
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Kosten.
		@return Zusätzliche Kosten
	  */
	@Override
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Währung für diesen Eintrag
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Währung für diesen Eintrag
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vertragsbedingungen.
		@param C_Flatrate_Conditions_ID Vertragsbedingungen	  */
	@Override
	public void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID)
	{
		if (C_Flatrate_Conditions_ID < 1) 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, null);
		else 
			set_Value (COLUMNNAME_C_Flatrate_Conditions_ID, Integer.valueOf(C_Flatrate_Conditions_ID));
	}

	/** Get Vertragsbedingungen.
		@return Vertragsbedingungen	  */
	@Override
	public int getC_Flatrate_Conditions_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Conditions_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auftragskandidat.
		@param C_OLCand_ID Auftragskandidat	  */
	@Override
	public void setC_OLCand_ID (int C_OLCand_ID)
	{
		if (C_OLCand_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OLCand_ID, Integer.valueOf(C_OLCand_ID));
	}

	/** Get Auftragskandidat.
		@return Auftragskandidat	  */
	@Override
	public int getC_OLCand_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_OLCand_ID);
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
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM_Internal() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_Internal_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM_Internal(org.compiere.model.I_C_UOM C_UOM_Internal)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_Internal_ID, org.compiere.model.I_C_UOM.class, C_UOM_Internal);
	}

	/** Set Maßeinheit int..
		@param C_UOM_Internal_ID Maßeinheit int.	  */
	@Override
	public void setC_UOM_Internal_ID (int C_UOM_Internal_ID)
	{
		if (C_UOM_Internal_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_Internal_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_Internal_ID, Integer.valueOf(C_UOM_Internal_ID));
	}

	/** Get Maßeinheit int..
		@return Maßeinheit int.	  */
	@Override
	public int getC_UOM_Internal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_Internal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kand.-Datum.
		@param DateCandidate Kand.-Datum	  */
	@Override
	public void setDateCandidate (java.sql.Timestamp DateCandidate)
	{
		set_Value (COLUMNNAME_DateCandidate, DateCandidate);
	}

	/** Get Kand.-Datum.
		@return Kand.-Datum	  */
	@Override
	public java.sql.Timestamp getDateCandidate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateCandidate);
	}

	/** Set Zugesagter Termin.
		@param DatePromised 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised (java.sql.Timestamp DatePromised)
	{
		set_Value (COLUMNNAME_DatePromised, DatePromised);
	}

	/** Get Zugesagter Termin.
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised);
	}

	/** Set Zugesagter Termin eff..
		@param DatePromised_Effective 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised_Effective (java.sql.Timestamp DatePromised_Effective)
	{
		throw new IllegalArgumentException ("DatePromised_Effective is virtual column");	}

	/** Get Zugesagter Termin eff..
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised_Effective () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised_Effective);
	}

	/** Set Zugesagter Termin abw..
		@param DatePromised_Override 
		Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public void setDatePromised_Override (java.sql.Timestamp DatePromised_Override)
	{
		set_Value (COLUMNNAME_DatePromised_Override, DatePromised_Override);
	}

	/** Get Zugesagter Termin abw..
		@return Zugesagter Termin für diesen Auftrag
	  */
	@Override
	public java.sql.Timestamp getDatePromised_Override () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DatePromised_Override);
	}

	/** 
	 * DeliveryRule AD_Reference_ID=151
	 * Reference name: C_Order DeliveryRule
	 */
	public static final int DELIVERYRULE_AD_Reference_ID=151;
	/** AfterReceipt = R */
	public static final String DELIVERYRULE_AfterReceipt = "R";
	/** Availability = A */
	public static final String DELIVERYRULE_Availability = "A";
	/** CompleteLine = L */
	public static final String DELIVERYRULE_CompleteLine = "L";
	/** CompleteOrder = O */
	public static final String DELIVERYRULE_CompleteOrder = "O";
	/** Force = F */
	public static final String DELIVERYRULE_Force = "F";
	/** Manual = M */
	public static final String DELIVERYRULE_Manual = "M";
	/** MitNaechsterAbolieferung = S */
	public static final String DELIVERYRULE_MitNaechsterAbolieferung = "S";
	/** Set Lieferart.
		@param DeliveryRule 
		Definiert die zeitliche Steuerung von Lieferungen
	  */
	@Override
	public void setDeliveryRule (java.lang.String DeliveryRule)
	{

		set_Value (COLUMNNAME_DeliveryRule, DeliveryRule);
	}

	/** Get Lieferart.
		@return Definiert die zeitliche Steuerung von Lieferungen
	  */
	@Override
	public java.lang.String getDeliveryRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryRule);
	}

	/** 
	 * DeliveryViaRule AD_Reference_ID=152
	 * Reference name: C_Order DeliveryViaRule
	 */
	public static final int DELIVERYVIARULE_AD_Reference_ID=152;
	/** Pickup = P */
	public static final String DELIVERYVIARULE_Pickup = "P";
	/** Delivery = D */
	public static final String DELIVERYVIARULE_Delivery = "D";
	/** Shipper = S */
	public static final String DELIVERYVIARULE_Shipper = "S";
	/** Set Lieferung.
		@param DeliveryViaRule 
		Wie der Auftrag geliefert wird
	  */
	@Override
	public void setDeliveryViaRule (java.lang.String DeliveryViaRule)
	{

		set_Value (COLUMNNAME_DeliveryViaRule, DeliveryViaRule);
	}

	/** Get Lieferung.
		@return Wie der Auftrag geliefert wird
	  */
	@Override
	public java.lang.String getDeliveryViaRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DeliveryViaRule);
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

	/** Set Schlusstext.
		@param DescriptionBottom Schlusstext	  */
	@Override
	public void setDescriptionBottom (java.lang.String DescriptionBottom)
	{
		set_Value (COLUMNNAME_DescriptionBottom, DescriptionBottom);
	}

	/** Get Schlusstext.
		@return Schlusstext	  */
	@Override
	public java.lang.String getDescriptionBottom () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionBottom);
	}

	/** Set Description Header.
		@param DescriptionHeader Description Header	  */
	@Override
	public void setDescriptionHeader (java.lang.String DescriptionHeader)
	{
		set_Value (COLUMNNAME_DescriptionHeader, DescriptionHeader);
	}

	/** Get Description Header.
		@return Description Header	  */
	@Override
	public java.lang.String getDescriptionHeader () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DescriptionHeader);
	}

	/** Set Rabatt %.
		@param Discount 
		Abschlag in Prozent
	  */
	@Override
	public void setDiscount (java.math.BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Rabatt %.
		@return Abschlag in Prozent
	  */
	@Override
	public java.math.BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_BPartner getDropShip_BPartner_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_BPartner_Effective_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setDropShip_BPartner_Effective(org.compiere.model.I_C_BPartner DropShip_BPartner_Effective)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_BPartner_Effective_ID, org.compiere.model.I_C_BPartner.class, DropShip_BPartner_Effective);
	}

	/** Set Lieferempfänger eff..
		@param DropShip_BPartner_Effective_ID Lieferempfänger eff.	  */
	@Override
	public void setDropShip_BPartner_Effective_ID (int DropShip_BPartner_Effective_ID)
	{
		throw new IllegalArgumentException ("DropShip_BPartner_Effective_ID is virtual column");	}

	/** Get Lieferempfänger eff..
		@return Lieferempfänger eff.	  */
	@Override
	public int getDropShip_BPartner_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getDropShip_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setDropShip_BPartner(org.compiere.model.I_C_BPartner DropShip_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_BPartner_ID, org.compiere.model.I_C_BPartner.class, DropShip_BPartner);
	}

	/** Set Lieferempfänger.
		@param DropShip_BPartner_ID 
		Business Partner to ship to
	  */
	@Override
	public void setDropShip_BPartner_ID (int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, Integer.valueOf(DropShip_BPartner_ID));
	}

	/** Get Lieferempfänger.
		@return Business Partner to ship to
	  */
	@Override
	public int getDropShip_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getDropShip_BPartner_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_BPartner_Override_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setDropShip_BPartner_Override(org.compiere.model.I_C_BPartner DropShip_BPartner_Override)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_BPartner_Override_ID, org.compiere.model.I_C_BPartner.class, DropShip_BPartner_Override);
	}

	/** Set Lieferempfänger abw..
		@param DropShip_BPartner_Override_ID Lieferempfänger abw.	  */
	@Override
	public void setDropShip_BPartner_Override_ID (int DropShip_BPartner_Override_ID)
	{
		if (DropShip_BPartner_Override_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_Override_ID, Integer.valueOf(DropShip_BPartner_Override_ID));
	}

	/** Get Lieferempfänger abw..
		@return Lieferempfänger abw.	  */
	@Override
	public int getDropShip_BPartner_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_BPartner_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getDropShip_Location_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Location_Effective_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setDropShip_Location_Effective(org.compiere.model.I_C_BPartner_Location DropShip_Location_Effective)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Location_Effective_ID, org.compiere.model.I_C_BPartner_Location.class, DropShip_Location_Effective);
	}

	/** Set Lieferadresse eff..
		@param DropShip_Location_Effective_ID Lieferadresse eff.	  */
	@Override
	public void setDropShip_Location_Effective_ID (int DropShip_Location_Effective_ID)
	{
		throw new IllegalArgumentException ("DropShip_Location_Effective_ID is virtual column");	}

	/** Get Lieferadresse eff..
		@return Lieferadresse eff.	  */
	@Override
	public int getDropShip_Location_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getDropShip_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setDropShip_Location(org.compiere.model.I_C_BPartner_Location DropShip_Location)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Location_ID, org.compiere.model.I_C_BPartner_Location.class, DropShip_Location);
	}

	/** Set Lieferadresse.
		@param DropShip_Location_ID 
		Business Partner Location for shipping to
	  */
	@Override
	public void setDropShip_Location_ID (int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, Integer.valueOf(DropShip_Location_ID));
	}

	/** Get Lieferadresse.
		@return Business Partner Location for shipping to
	  */
	@Override
	public int getDropShip_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getDropShip_Location_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DropShip_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setDropShip_Location_Override(org.compiere.model.I_C_BPartner_Location DropShip_Location_Override)
	{
		set_ValueFromPO(COLUMNNAME_DropShip_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class, DropShip_Location_Override);
	}

	/** Set Lieferadresse abw..
		@param DropShip_Location_Override_ID Lieferadresse abw.	  */
	@Override
	public void setDropShip_Location_Override_ID (int DropShip_Location_Override_ID)
	{
		if (DropShip_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_Override_ID, Integer.valueOf(DropShip_Location_Override_ID));
	}

	/** Get Lieferadresse abw..
		@return Lieferadresse abw.	  */
	@Override
	public int getDropShip_Location_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DropShip_Location_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public java.lang.String getErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set External ID.
		@param ExternalId External ID	  */
	@Override
	public void setExternalId (java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	/** Get External ID.
		@return External ID	  */
	@Override
	public java.lang.String getExternalId () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalId);
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getHandOver_Location_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Location_Effective_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setHandOver_Location_Effective(org.compiere.model.I_C_BPartner_Location HandOver_Location_Effective)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Location_Effective_ID, org.compiere.model.I_C_BPartner_Location.class, HandOver_Location_Effective);
	}

	/** Set Übergabeadresse eff..
		@param HandOver_Location_Effective_ID Übergabeadresse eff.	  */
	@Override
	public void setHandOver_Location_Effective_ID (int HandOver_Location_Effective_ID)
	{
		throw new IllegalArgumentException ("HandOver_Location_Effective_ID is virtual column");	}

	/** Get Übergabeadresse eff..
		@return Übergabeadresse eff.	  */
	@Override
	public int getHandOver_Location_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Location_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getHandOver_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setHandOver_Location(org.compiere.model.I_C_BPartner_Location HandOver_Location)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Location_ID, org.compiere.model.I_C_BPartner_Location.class, HandOver_Location);
	}

	/** Set Übergabeadresse.
		@param HandOver_Location_ID Übergabeadresse	  */
	@Override
	public void setHandOver_Location_ID (int HandOver_Location_ID)
	{
		if (HandOver_Location_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_ID, Integer.valueOf(HandOver_Location_ID));
	}

	/** Get Übergabeadresse.
		@return Übergabeadresse	  */
	@Override
	public int getHandOver_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getHandOver_Location_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setHandOver_Location_Override(org.compiere.model.I_C_BPartner_Location HandOver_Location_Override)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Location_Override_ID, org.compiere.model.I_C_BPartner_Location.class, HandOver_Location_Override);
	}

	/** Set Übergabeadresse abw..
		@param HandOver_Location_Override_ID Übergabeadresse abw.	  */
	@Override
	public void setHandOver_Location_Override_ID (int HandOver_Location_Override_ID)
	{
		if (HandOver_Location_Override_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Location_Override_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Location_Override_ID, Integer.valueOf(HandOver_Location_Override_ID));
	}

	/** Get Übergabeadresse abw..
		@return Übergabeadresse abw.	  */
	@Override
	public int getHandOver_Location_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Location_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getHandOver_Partner_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Partner_Effective_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setHandOver_Partner_Effective(org.compiere.model.I_C_BPartner HandOver_Partner_Effective)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Partner_Effective_ID, org.compiere.model.I_C_BPartner.class, HandOver_Partner_Effective);
	}

	/** Set Übergabe an eff..
		@param HandOver_Partner_Effective_ID Übergabe an eff.	  */
	@Override
	public void setHandOver_Partner_Effective_ID (int HandOver_Partner_Effective_ID)
	{
		throw new IllegalArgumentException ("HandOver_Partner_Effective_ID is virtual column");	}

	/** Get Übergabe an eff..
		@return Übergabe an eff.	  */
	@Override
	public int getHandOver_Partner_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Partner_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getHandOver_Partner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Partner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setHandOver_Partner(org.compiere.model.I_C_BPartner HandOver_Partner)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Partner_ID, org.compiere.model.I_C_BPartner.class, HandOver_Partner);
	}

	/** Set Übergabe an.
		@param HandOver_Partner_ID Übergabe an	  */
	@Override
	public void setHandOver_Partner_ID (int HandOver_Partner_ID)
	{
		if (HandOver_Partner_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_ID, Integer.valueOf(HandOver_Partner_ID));
	}

	/** Get Übergabe an.
		@return Übergabe an	  */
	@Override
	public int getHandOver_Partner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Partner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getHandOver_Partner_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_HandOver_Partner_Override_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setHandOver_Partner_Override(org.compiere.model.I_C_BPartner HandOver_Partner_Override)
	{
		set_ValueFromPO(COLUMNNAME_HandOver_Partner_Override_ID, org.compiere.model.I_C_BPartner.class, HandOver_Partner_Override);
	}

	/** Set Übergabe an abw..
		@param HandOver_Partner_Override_ID Übergabe an abw.	  */
	@Override
	public void setHandOver_Partner_Override_ID (int HandOver_Partner_Override_ID)
	{
		if (HandOver_Partner_Override_ID < 1) 
			set_Value (COLUMNNAME_HandOver_Partner_Override_ID, null);
		else 
			set_Value (COLUMNNAME_HandOver_Partner_Override_ID, Integer.valueOf(HandOver_Partner_Override_ID));
	}

	/** Get Übergabe an abw..
		@return Übergabe an abw.	  */
	@Override
	public int getHandOver_Partner_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HandOver_Partner_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Fehler.
		@param IsError 
		Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public void setIsError (boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, Boolean.valueOf(IsError));
	}

	/** Get Fehler.
		@return Ein Fehler ist bei der Durchführung aufgetreten
	  */
	@Override
	public boolean isError () 
	{
		Object oo = get_Value(COLUMNNAME_IsError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Explicit Product Price Attributes.
		@param IsExplicitProductPriceAttribute Explicit Product Price Attributes	  */
	@Override
	public void setIsExplicitProductPriceAttribute (boolean IsExplicitProductPriceAttribute)
	{
		set_Value (COLUMNNAME_IsExplicitProductPriceAttribute, Boolean.valueOf(IsExplicitProductPriceAttribute));
	}

	/** Get Explicit Product Price Attributes.
		@return Explicit Product Price Attributes	  */
	@Override
	public boolean isExplicitProductPriceAttribute () 
	{
		Object oo = get_Value(COLUMNNAME_IsExplicitProductPriceAttribute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manueller Rabatt.
		@param IsManualDiscount 
		Ein Rabatt, der von Hand eingetragen wurde, wird vom Provisionssystem nicht überschrieben
	  */
	@Override
	public void setIsManualDiscount (boolean IsManualDiscount)
	{
		set_Value (COLUMNNAME_IsManualDiscount, Boolean.valueOf(IsManualDiscount));
	}

	/** Get Manueller Rabatt.
		@return Ein Rabatt, der von Hand eingetragen wurde, wird vom Provisionssystem nicht überschrieben
	  */
	@Override
	public boolean isManualDiscount () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualDiscount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Manueller Preis.
		@param IsManualPrice Manueller Preis	  */
	@Override
	public void setIsManualPrice (boolean IsManualPrice)
	{
		set_Value (COLUMNNAME_IsManualPrice, Boolean.valueOf(IsManualPrice));
	}

	/** Get Manueller Preis.
		@return Manueller Preis	  */
	@Override
	public boolean isManualPrice () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualPrice);
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
		Einzelne Zeile in dem Dokument
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeSet getM_AttributeSet() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class);
	}

	@Override
	public void setM_AttributeSet(org.compiere.model.I_M_AttributeSet M_AttributeSet)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_ID, org.compiere.model.I_M_AttributeSet.class, M_AttributeSet);
	}

	/** Set Merkmals-Satz.
		@param M_AttributeSet_ID 
		Merkmals-Satz zum Produkt
	  */
	@Override
	public void setM_AttributeSet_ID (int M_AttributeSet_ID)
	{
		if (M_AttributeSet_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSet_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSet_ID, Integer.valueOf(M_AttributeSet_ID));
	}

	/** Get Merkmals-Satz.
		@return Merkmals-Satz zum Produkt
	  */
	@Override
	public int getM_AttributeSet_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSet_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Packvorschrift-Produkt Zuordnung eff..
		@param M_HU_PI_Item_Product_Effective_ID Packvorschrift-Produkt Zuordnung eff.	  */
	@Override
	public void setM_HU_PI_Item_Product_Effective_ID (int M_HU_PI_Item_Product_Effective_ID)
	{
		throw new IllegalArgumentException ("M_HU_PI_Item_Product_Effective_ID is virtual column");	}

	/** Get Packvorschrift-Produkt Zuordnung eff..
		@return Packvorschrift-Produkt Zuordnung eff.	  */
	@Override
	public int getM_HU_PI_Item_Product_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_Effective_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packvorschrift.
		@param M_HU_PI_Item_Product_ID Packvorschrift	  */
	@Override
	public void setM_HU_PI_Item_Product_ID (int M_HU_PI_Item_Product_ID)
	{
		if (M_HU_PI_Item_Product_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_ID, Integer.valueOf(M_HU_PI_Item_Product_ID));
	}

	/** Get Packvorschrift.
		@return Packvorschrift	  */
	@Override
	public int getM_HU_PI_Item_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packvorschrift-Produkt Zuordnung abw..
		@param M_HU_PI_Item_Product_Override_ID Packvorschrift-Produkt Zuordnung abw.	  */
	@Override
	public void setM_HU_PI_Item_Product_Override_ID (int M_HU_PI_Item_Product_Override_ID)
	{
		if (M_HU_PI_Item_Product_Override_ID < 1) 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_HU_PI_Item_Product_Override_ID, Integer.valueOf(M_HU_PI_Item_Product_Override_ID));
	}

	/** Get Packvorschrift-Produkt Zuordnung abw..
		@return Packvorschrift-Produkt Zuordnung abw.	  */
	@Override
	public int getM_HU_PI_Item_Product_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_HU_PI_Item_Product_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_PricingSystem getM_PricingSystem() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class);
	}

	@Override
	public void setM_PricingSystem(org.compiere.model.I_M_PricingSystem M_PricingSystem)
	{
		set_ValueFromPO(COLUMNNAME_M_PricingSystem_ID, org.compiere.model.I_M_PricingSystem.class, M_PricingSystem);
	}

	/** Set Preissystem.
		@param M_PricingSystem_ID 
		Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public void setM_PricingSystem_ID (int M_PricingSystem_ID)
	{
		if (M_PricingSystem_ID < 1) 
			set_Value (COLUMNNAME_M_PricingSystem_ID, null);
		else 
			set_Value (COLUMNNAME_M_PricingSystem_ID, Integer.valueOf(M_PricingSystem_ID));
	}

	/** Get Preissystem.
		@return Ein Preissystem enthält beliebig viele, Länder-abhängige Preislisten.
	  */
	@Override
	public int getM_PricingSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PricingSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product_Effective() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Effective_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_Effective(org.compiere.model.I_M_Product M_Product_Effective)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Effective_ID, org.compiere.model.I_M_Product.class, M_Product_Effective);
	}

	/** Set Produkt eff..
		@param M_Product_Effective_ID Produkt eff.	  */
	@Override
	public void setM_Product_Effective_ID (int M_Product_Effective_ID)
	{
		throw new IllegalArgumentException ("M_Product_Effective_ID is virtual column");	}

	/** Get Produkt eff..
		@return Produkt eff.	  */
	@Override
	public int getM_Product_Effective_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Effective_ID);
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
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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
	public org.compiere.model.I_M_Product getM_Product_Override() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_Override_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product_Override(org.compiere.model.I_M_Product M_Product_Override)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_Override_ID, org.compiere.model.I_M_Product.class, M_Product_Override);
	}

	/** Set Produkt abw..
		@param M_Product_Override_ID Produkt abw.	  */
	@Override
	public void setM_Product_Override_ID (int M_Product_Override_ID)
	{
		if (M_Product_Override_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Override_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Override_ID, Integer.valueOf(M_Product_Override_ID));
	}

	/** Get Produkt abw..
		@return Produkt abw.	  */
	@Override
	public int getM_Product_Override_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Override_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Attribute price.
		@param M_ProductPrice_Attribute_ID Attribute price	  */
	@Override
	public void setM_ProductPrice_Attribute_ID (int M_ProductPrice_Attribute_ID)
	{
		if (M_ProductPrice_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_Attribute_ID, Integer.valueOf(M_ProductPrice_Attribute_ID));
	}

	/** Get Attribute price.
		@return Attribute price	  */
	@Override
	public int getM_ProductPrice_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_ProductPrice getM_ProductPrice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_ProductPrice_ID, org.compiere.model.I_M_ProductPrice.class);
	}

	@Override
	public void setM_ProductPrice(org.compiere.model.I_M_ProductPrice M_ProductPrice)
	{
		set_ValueFromPO(COLUMNNAME_M_ProductPrice_ID, org.compiere.model.I_M_ProductPrice.class, M_ProductPrice);
	}

	/** Set Produkt-Preis.
		@param M_ProductPrice_ID Produkt-Preis	  */
	@Override
	public void setM_ProductPrice_ID (int M_ProductPrice_ID)
	{
		if (M_ProductPrice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ProductPrice_ID, Integer.valueOf(M_ProductPrice_ID));
	}

	/** Get Produkt-Preis.
		@return Produkt-Preis	  */
	@Override
	public int getM_ProductPrice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductPrice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Warehouse getM_Warehouse_Dest() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Warehouse_Dest_ID, org.compiere.model.I_M_Warehouse.class);
	}

	@Override
	public void setM_Warehouse_Dest(org.compiere.model.I_M_Warehouse M_Warehouse_Dest)
	{
		set_ValueFromPO(COLUMNNAME_M_Warehouse_Dest_ID, org.compiere.model.I_M_Warehouse.class, M_Warehouse_Dest);
	}

	/** Set Ziel-Lager.
		@param M_Warehouse_Dest_ID Ziel-Lager	  */
	@Override
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID)
	{
		if (M_Warehouse_Dest_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, Integer.valueOf(M_Warehouse_Dest_ID));
	}

	/** Get Ziel-Lager.
		@return Ziel-Lager	  */
	@Override
	public int getM_Warehouse_Dest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_Dest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	@Override
	public org.compiere.model.I_C_UOM getPrice_UOM_Internal() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Price_UOM_Internal_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setPrice_UOM_Internal(org.compiere.model.I_C_UOM Price_UOM_Internal)
	{
		set_ValueFromPO(COLUMNNAME_Price_UOM_Internal_ID, org.compiere.model.I_C_UOM.class, Price_UOM_Internal);
	}

	/** Set Preiseinheit int..
		@param Price_UOM_Internal_ID 
		Interne Preiseinheit laut Stammdaten
	  */
	@Override
	public void setPrice_UOM_Internal_ID (int Price_UOM_Internal_ID)
	{
		if (Price_UOM_Internal_ID < 1) 
			set_Value (COLUMNNAME_Price_UOM_Internal_ID, null);
		else 
			set_Value (COLUMNNAME_Price_UOM_Internal_ID, Integer.valueOf(Price_UOM_Internal_ID));
	}

	/** Get Preiseinheit int..
		@return Interne Preiseinheit laut Stammdaten
	  */
	@Override
	public int getPrice_UOM_Internal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Price_UOM_Internal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Einzelpreis.
		@param PriceActual 
		Effektiver Preis
	  */
	@Override
	public void setPriceActual (java.math.BigDecimal PriceActual)
	{
		set_Value (COLUMNNAME_PriceActual, PriceActual);
	}

	/** Get Einzelpreis.
		@return Effektiver Preis
	  */
	@Override
	public java.math.BigDecimal getPriceActual () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceActual);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preisdifferenz (imp. - int.).
		@param PriceDifference Preisdifferenz (imp. - int.)	  */
	@Override
	public void setPriceDifference (java.math.BigDecimal PriceDifference)
	{
		throw new IllegalArgumentException ("PriceDifference is virtual column");	}

	/** Get Preisdifferenz (imp. - int.).
		@return Preisdifferenz (imp. - int.)	  */
	@Override
	public java.math.BigDecimal getPriceDifference () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceDifference);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preis.
		@param PriceEntered 
		Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	  */
	@Override
	public void setPriceEntered (java.math.BigDecimal PriceEntered)
	{
		set_Value (COLUMNNAME_PriceEntered, PriceEntered);
	}

	/** Get Preis.
		@return Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit
	  */
	@Override
	public java.math.BigDecimal getPriceEntered () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceEntered);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Preis int..
		@param PriceInternal 
		Interner Preis laut Stammdaten
	  */
	@Override
	public void setPriceInternal (java.math.BigDecimal PriceInternal)
	{
		set_Value (COLUMNNAME_PriceInternal, PriceInternal);
	}

	/** Get Preis int..
		@return Interner Preis laut Stammdaten
	  */
	@Override
	public java.math.BigDecimal getPriceInternal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PriceInternal);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	/** Set Produktbeschreibung.
		@param ProductDescription 
		Produktbeschreibung
	  */
	@Override
	public void setProductDescription (java.lang.String ProductDescription)
	{
		set_Value (COLUMNNAME_ProductDescription, ProductDescription);
	}

	/** Get Produktbeschreibung.
		@return Produktbeschreibung
	  */
	@Override
	public java.lang.String getProductDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductDescription);
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
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
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}