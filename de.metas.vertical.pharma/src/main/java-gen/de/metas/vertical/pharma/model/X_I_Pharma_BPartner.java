/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Pharma_BPartner
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Pharma_BPartner extends org.compiere.model.PO implements I_I_Pharma_BPartner, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2048494802L;

    /** Standard Constructor */
    public X_I_Pharma_BPartner (Properties ctx, int I_Pharma_BPartner_ID, String trxName)
    {
      super (ctx, I_Pharma_BPartner_ID, trxName);
      /** if (I_Pharma_BPartner_ID == 0)
        {
			setI_IsImported (null); // N
			setI_Pharma_BPartner_ID (0);
			setProcessed (false); // N
        } */
    }

    /** Load Constructor */
    public X_I_Pharma_BPartner (Properties ctx, ResultSet rs, String trxName)
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

	/** Set b00adrnr.
		@param b00adrnr b00adrnr	  */
	@Override
	public void setb00adrnr (java.lang.String b00adrnr)
	{
		set_Value (COLUMNNAME_b00adrnr, b00adrnr);
	}

	/** Get b00adrnr.
		@return b00adrnr	  */
	@Override
	public java.lang.String getb00adrnr () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00adrnr);
	}

	/** Set b00email.
		@param b00email b00email	  */
	@Override
	public void setb00email (java.lang.String b00email)
	{
		set_Value (COLUMNNAME_b00email, b00email);
	}

	/** Get b00email.
		@return b00email	  */
	@Override
	public java.lang.String getb00email () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00email);
	}

	/** Set b00email2.
		@param b00email2 b00email2	  */
	@Override
	public void setb00email2 (java.lang.String b00email2)
	{
		set_Value (COLUMNNAME_b00email2, b00email2);
	}

	/** Get b00email2.
		@return b00email2	  */
	@Override
	public java.lang.String getb00email2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00email2);
	}

	/** Set b00fax1.
		@param b00fax1 b00fax1	  */
	@Override
	public void setb00fax1 (java.lang.String b00fax1)
	{
		set_Value (COLUMNNAME_b00fax1, b00fax1);
	}

	/** Get b00fax1.
		@return b00fax1	  */
	@Override
	public java.lang.String getb00fax1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00fax1);
	}

	/** Set b00fax2.
		@param b00fax2 b00fax2	  */
	@Override
	public void setb00fax2 (java.lang.String b00fax2)
	{
		set_Value (COLUMNNAME_b00fax2, b00fax2);
	}

	/** Get b00fax2.
		@return b00fax2	  */
	@Override
	public java.lang.String getb00fax2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00fax2);
	}

	/** Set b00gdat.
		@param b00gdat b00gdat	  */
	@Override
	public void setb00gdat (java.sql.Timestamp b00gdat)
	{
		set_Value (COLUMNNAME_b00gdat, b00gdat);
	}

	/** Get b00gdat.
		@return b00gdat	  */
	@Override
	public java.sql.Timestamp getb00gdat () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_b00gdat);
	}

	/** 
	 * b00gherlau AD_Reference_ID=540964
	 * Reference name: Pharma_B00HERSTER_B00GHERLAU
	 */
	public static final int B00GHERLAU_AD_Reference_ID=540964;
	/** 00 = 00 */
	public static final String B00GHERLAU_00 = "00";
	/** 01 = 01 */
	public static final String B00GHERLAU_01 = "01";
	/** 02 = 02 */
	public static final String B00GHERLAU_02 = "02";
	/** Set b00gherlau.
		@param b00gherlau b00gherlau	  */
	@Override
	public void setb00gherlau (java.lang.String b00gherlau)
	{

		set_Value (COLUMNNAME_b00gherlau, b00gherlau);
	}

	/** Get b00gherlau.
		@return b00gherlau	  */
	@Override
	public java.lang.String getb00gherlau () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00gherlau);
	}

	/** 
	 * b00herster AD_Reference_ID=540964
	 * Reference name: Pharma_B00HERSTER_B00GHERLAU
	 */
	public static final int B00HERSTER_AD_Reference_ID=540964;
	/** 00 = 00 */
	public static final String B00HERSTER_00 = "00";
	/** 01 = 01 */
	public static final String B00HERSTER_01 = "01";
	/** 02 = 02 */
	public static final String B00HERSTER_02 = "02";
	/** Set b00herster.
		@param b00herster b00herster	  */
	@Override
	public void setb00herster (java.lang.String b00herster)
	{

		set_Value (COLUMNNAME_b00herster, b00herster);
	}

	/** Get b00herster.
		@return b00herster	  */
	@Override
	public java.lang.String getb00herster () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00herster);
	}

	/** Set b00hnrb.
		@param b00hnrb b00hnrb	  */
	@Override
	public void setb00hnrb (java.lang.String b00hnrb)
	{
		set_Value (COLUMNNAME_b00hnrb, b00hnrb);
	}

	/** Get b00hnrb.
		@return b00hnrb	  */
	@Override
	public java.lang.String getb00hnrb () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00hnrb);
	}

	/** Set b00hnrbz.
		@param b00hnrbz b00hnrbz	  */
	@Override
	public void setb00hnrbz (java.lang.String b00hnrbz)
	{
		set_Value (COLUMNNAME_b00hnrbz, b00hnrbz);
	}

	/** Get b00hnrbz.
		@return b00hnrbz	  */
	@Override
	public java.lang.String getb00hnrbz () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00hnrbz);
	}

	/** Set b00hnrv.
		@param b00hnrv b00hnrv	  */
	@Override
	public void setb00hnrv (java.lang.String b00hnrv)
	{
		set_Value (COLUMNNAME_b00hnrv, b00hnrv);
	}

	/** Get b00hnrv.
		@return b00hnrv	  */
	@Override
	public java.lang.String getb00hnrv () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00hnrv);
	}

	/** Set b00hnrvz.
		@param b00hnrvz b00hnrvz	  */
	@Override
	public void setb00hnrvz (java.lang.String b00hnrvz)
	{
		set_Value (COLUMNNAME_b00hnrvz, b00hnrvz);
	}

	/** Get b00hnrvz.
		@return b00hnrvz	  */
	@Override
	public java.lang.String getb00hnrvz () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00hnrvz);
	}

	/** Set b00homepag.
		@param b00homepag b00homepag	  */
	@Override
	public void setb00homepag (java.lang.String b00homepag)
	{
		set_Value (COLUMNNAME_b00homepag, b00homepag);
	}

	/** Get b00homepag.
		@return b00homepag	  */
	@Override
	public java.lang.String getb00homepag () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00homepag);
	}

	/** Set b00land.
		@param b00land b00land	  */
	@Override
	public void setb00land (java.lang.String b00land)
	{
		set_Value (COLUMNNAME_b00land, b00land);
	}

	/** Get b00land.
		@return b00land	  */
	@Override
	public java.lang.String getb00land () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00land);
	}

	/** Set b00lkz.
		@param b00lkz b00lkz	  */
	@Override
	public void setb00lkz (java.lang.String b00lkz)
	{
		set_Value (COLUMNNAME_b00lkz, b00lkz);
	}

	/** Get b00lkz.
		@return b00lkz	  */
	@Override
	public java.lang.String getb00lkz () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00lkz);
	}

	/** Set b00name1.
		@param b00name1 b00name1	  */
	@Override
	public void setb00name1 (java.lang.String b00name1)
	{
		set_Value (COLUMNNAME_b00name1, b00name1);
	}

	/** Get b00name1.
		@return b00name1	  */
	@Override
	public java.lang.String getb00name1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00name1);
	}

	/** Set b00name2.
		@param b00name2 b00name2	  */
	@Override
	public void setb00name2 (java.lang.String b00name2)
	{
		set_Value (COLUMNNAME_b00name2, b00name2);
	}

	/** Get b00name2.
		@return b00name2	  */
	@Override
	public java.lang.String getb00name2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00name2);
	}

	/** Set b00name3.
		@param b00name3 b00name3	  */
	@Override
	public void setb00name3 (java.lang.String b00name3)
	{
		set_Value (COLUMNNAME_b00name3, b00name3);
	}

	/** Get b00name3.
		@return b00name3	  */
	@Override
	public java.lang.String getb00name3 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00name3);
	}

	/** Set b00ortpf.
		@param b00ortpf b00ortpf	  */
	@Override
	public void setb00ortpf (java.lang.String b00ortpf)
	{
		set_Value (COLUMNNAME_b00ortpf, b00ortpf);
	}

	/** Get b00ortpf.
		@return b00ortpf	  */
	@Override
	public java.lang.String getb00ortpf () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00ortpf);
	}

	/** Set b00ortzu.
		@param b00ortzu b00ortzu	  */
	@Override
	public void setb00ortzu (java.lang.String b00ortzu)
	{
		set_Value (COLUMNNAME_b00ortzu, b00ortzu);
	}

	/** Get b00ortzu.
		@return b00ortzu	  */
	@Override
	public java.lang.String getb00ortzu () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00ortzu);
	}

	/** Set b00pf1.
		@param b00pf1 b00pf1	  */
	@Override
	public void setb00pf1 (java.lang.String b00pf1)
	{
		set_Value (COLUMNNAME_b00pf1, b00pf1);
	}

	/** Get b00pf1.
		@return b00pf1	  */
	@Override
	public java.lang.String getb00pf1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00pf1);
	}

	/** Set b00plzgk1.
		@param b00plzgk1 b00plzgk1	  */
	@Override
	public void setb00plzgk1 (java.lang.String b00plzgk1)
	{
		set_Value (COLUMNNAME_b00plzgk1, b00plzgk1);
	}

	/** Get b00plzgk1.
		@return b00plzgk1	  */
	@Override
	public java.lang.String getb00plzgk1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00plzgk1);
	}

	/** Set b00plzpf1.
		@param b00plzpf1 b00plzpf1	  */
	@Override
	public void setb00plzpf1 (java.lang.String b00plzpf1)
	{
		set_Value (COLUMNNAME_b00plzpf1, b00plzpf1);
	}

	/** Get b00plzpf1.
		@return b00plzpf1	  */
	@Override
	public java.lang.String getb00plzpf1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00plzpf1);
	}

	/** Set b00plzzu1.
		@param b00plzzu1 b00plzzu1	  */
	@Override
	public void setb00plzzu1 (java.lang.String b00plzzu1)
	{
		set_Value (COLUMNNAME_b00plzzu1, b00plzzu1);
	}

	/** Get b00plzzu1.
		@return b00plzzu1	  */
	@Override
	public java.lang.String getb00plzzu1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00plzzu1);
	}

	/** Set b00regnr9.
		@param b00regnr9 b00regnr9	  */
	@Override
	public void setb00regnr9 (java.lang.String b00regnr9)
	{
		set_Value (COLUMNNAME_b00regnr9, b00regnr9);
	}

	/** Get b00regnr9.
		@return b00regnr9	  */
	@Override
	public java.lang.String getb00regnr9 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00regnr9);
	}

	/** Set b00sname.
		@param b00sname b00sname	  */
	@Override
	public void setb00sname (java.lang.String b00sname)
	{
		set_Value (COLUMNNAME_b00sname, b00sname);
	}

	/** Get b00sname.
		@return b00sname	  */
	@Override
	public java.lang.String getb00sname () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00sname);
	}

	/** Set b00ssatz.
		@param b00ssatz b00ssatz	  */
	@Override
	public void setb00ssatz (java.lang.String b00ssatz)
	{
		set_Value (COLUMNNAME_b00ssatz, b00ssatz);
	}

	/** Get b00ssatz.
		@return b00ssatz	  */
	@Override
	public java.lang.String getb00ssatz () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00ssatz);
	}

	/** Set b00str.
		@param b00str b00str	  */
	@Override
	public void setb00str (java.lang.String b00str)
	{
		set_Value (COLUMNNAME_b00str, b00str);
	}

	/** Get b00str.
		@return b00str	  */
	@Override
	public java.lang.String getb00str () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00str);
	}

	/** Set b00tel1.
		@param b00tel1 b00tel1	  */
	@Override
	public void setb00tel1 (java.lang.String b00tel1)
	{
		set_Value (COLUMNNAME_b00tel1, b00tel1);
	}

	/** Get b00tel1.
		@return b00tel1	  */
	@Override
	public java.lang.String getb00tel1 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00tel1);
	}

	/** Set b00tel2.
		@param b00tel2 b00tel2	  */
	@Override
	public void setb00tel2 (java.lang.String b00tel2)
	{
		set_Value (COLUMNNAME_b00tel2, b00tel2);
	}

	/** Get b00tel2.
		@return b00tel2	  */
	@Override
	public java.lang.String getb00tel2 () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_b00tel2);
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
	public org.compiere.model.I_C_Country getC_Country() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	/** Set Land.
		@param C_Country_ID 
		Land
	  */
	@Override
	public void setC_Country_ID (int C_Country_ID)
	{
		if (C_Country_ID < 1) 
			set_Value (COLUMNNAME_C_Country_ID, null);
		else 
			set_Value (COLUMNNAME_C_Country_ID, Integer.valueOf(C_Country_ID));
	}

	/** Get Land.
		@return Land
	  */
	@Override
	public int getC_Country_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	/** Set Data import.
		@param C_DataImport_ID Data import	  */
	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	/** Get Data import.
		@return Data import	  */
	@Override
	public int getC_DataImport_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DataImport_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Import-Fehlermeldung.
		@param I_ErrorMsg 
		Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import-Fehlermeldung.
		@return Meldungen, die durch den Importprozess generiert wurden
	  */
	@Override
	public java.lang.String getI_ErrorMsg () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	/** Set Importiert.
		@param I_IsImported 
		Ist dieser Import verarbeitet worden?
	  */
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	/** Get Importiert.
		@return Ist dieser Import verarbeitet worden?
	  */
	@Override
	public java.lang.String getI_IsImported () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	/** Set Import Pharma BPartners.
		@param I_Pharma_BPartner_ID Import Pharma BPartners	  */
	@Override
	public void setI_Pharma_BPartner_ID (int I_Pharma_BPartner_ID)
	{
		if (I_Pharma_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Pharma_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Pharma_BPartner_ID, Integer.valueOf(I_Pharma_BPartner_ID));
	}

	/** Get Import Pharma BPartners.
		@return Import Pharma BPartners	  */
	@Override
	public int getI_Pharma_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Pharma_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}