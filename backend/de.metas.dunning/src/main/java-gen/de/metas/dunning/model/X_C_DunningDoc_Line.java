/** Generated Model - DO NOT CHANGE */
package de.metas.dunning.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DunningDoc_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_DunningDoc_Line extends org.compiere.model.PO implements I_C_DunningDoc_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1548067068L;

    /** Standard Constructor */
    public X_C_DunningDoc_Line (Properties ctx, int C_DunningDoc_Line_ID, String trxName)
    {
      super (ctx, C_DunningDoc_Line_ID, trxName);
      /** if (C_DunningDoc_Line_ID == 0)
        {
			setAmt (BigDecimal.ZERO);
			setC_BPartner_ID (0);
			setC_Currency_ID (0);
			setC_DunningDoc_ID (0);
			setC_DunningDoc_Line_ID (0);
			setC_DunningLevel_ID (0); // @C_DunningLevel_ID@
			setProcessed (false);
			setSalesRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_DunningDoc_Line (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Betrag.
		@param Amt 
		Betrag
	  */
	@Override
	public void setAmt (java.math.BigDecimal Amt)
	{
		set_Value (COLUMNNAME_Amt, Amt);
	}

	/** Get Betrag.
		@return Betrag
	  */
	@Override
	public java.math.BigDecimal getAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
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

	@Override
	public org.compiere.model.I_AD_User getC_Dunning_Contact() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Dunning_Contact_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setC_Dunning_Contact(org.compiere.model.I_AD_User C_Dunning_Contact)
	{
		set_ValueFromPO(COLUMNNAME_C_Dunning_Contact_ID, org.compiere.model.I_AD_User.class, C_Dunning_Contact);
	}

	/** Set Mahnkontakt.
		@param C_Dunning_Contact_ID Mahnkontakt	  */
	@Override
	public void setC_Dunning_Contact_ID (int C_Dunning_Contact_ID)
	{
		if (C_Dunning_Contact_ID < 1) 
			set_Value (COLUMNNAME_C_Dunning_Contact_ID, null);
		else 
			set_Value (COLUMNNAME_C_Dunning_Contact_ID, Integer.valueOf(C_Dunning_Contact_ID));
	}

	/** Get Mahnkontakt.
		@return Mahnkontakt	  */
	@Override
	public int getC_Dunning_Contact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Dunning_Contact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dunning.model.I_C_DunningDoc getC_DunningDoc() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningDoc_ID, de.metas.dunning.model.I_C_DunningDoc.class);
	}

	@Override
	public void setC_DunningDoc(de.metas.dunning.model.I_C_DunningDoc C_DunningDoc)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningDoc_ID, de.metas.dunning.model.I_C_DunningDoc.class, C_DunningDoc);
	}

	/** Set Dunning Document.
		@param C_DunningDoc_ID Dunning Document	  */
	@Override
	public void setC_DunningDoc_ID (int C_DunningDoc_ID)
	{
		if (C_DunningDoc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_ID, Integer.valueOf(C_DunningDoc_ID));
	}

	/** Get Dunning Document.
		@return Dunning Document	  */
	@Override
	public int getC_DunningDoc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningDoc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dunning Document Line.
		@param C_DunningDoc_Line_ID Dunning Document Line	  */
	@Override
	public void setC_DunningDoc_Line_ID (int C_DunningDoc_Line_ID)
	{
		if (C_DunningDoc_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningDoc_Line_ID, Integer.valueOf(C_DunningDoc_Line_ID));
	}

	/** Get Dunning Document Line.
		@return Dunning Document Line	  */
	@Override
	public int getC_DunningDoc_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningDoc_Line_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DunningLevel getC_DunningLevel() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class);
	}

	@Override
	public void setC_DunningLevel(org.compiere.model.I_C_DunningLevel C_DunningLevel)
	{
		set_ValueFromPO(COLUMNNAME_C_DunningLevel_ID, org.compiere.model.I_C_DunningLevel.class, C_DunningLevel);
	}

	/** Set Mahnstufe.
		@param C_DunningLevel_ID Mahnstufe	  */
	@Override
	public void setC_DunningLevel_ID (int C_DunningLevel_ID)
	{
		if (C_DunningLevel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_DunningLevel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DunningLevel_ID, Integer.valueOf(C_DunningLevel_ID));
	}

	/** Get Mahnstufe.
		@return Mahnstufe	  */
	@Override
	public int getC_DunningLevel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DunningLevel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information
	  */
	@Override
	public void setNote (java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information
	  */
	@Override
	public java.lang.String getNote () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Note);
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

	@Override
	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep)
	{
		set_ValueFromPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class, SalesRep);
	}

	/** Set Aussendienst.
		@param SalesRep_ID Aussendienst	  */
	@Override
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Aussendienst.
		@return Aussendienst	  */
	@Override
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}