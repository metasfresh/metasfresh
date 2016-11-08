/** Generated Model - DO NOT CHANGE */
package de.metas.inout.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_QualityNote
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_QualityNote extends org.compiere.model.PO implements I_M_QualityNote, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1156252630L;

    /** Standard Constructor */
    public X_M_QualityNote (Properties ctx, int M_QualityNote_ID, String trxName)
    {
      super (ctx, M_QualityNote_ID, trxName);
      /** if (M_QualityNote_ID == 0)
        {
			setM_QualityNote_ID (0);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_QualityNote (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Qualität-Notiz.
		@param M_QualityNote_ID Qualität-Notiz	  */
	@Override
	public void setM_QualityNote_ID (int M_QualityNote_ID)
	{
		if (M_QualityNote_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityNote_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_QualityNote_ID, Integer.valueOf(M_QualityNote_ID));
	}

	/** Get Qualität-Notiz.
		@return Qualität-Notiz	  */
	@Override
	public int getM_QualityNote_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_QualityNote_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** 
	 * PerformanceType AD_Reference_ID=540689
	 * Reference name: R_Request.PerformanceType
	 */
	public static final int PERFORMANCETYPE_AD_Reference_ID=540689;
	/** Liefer Performance = LP */
	public static final String PERFORMANCETYPE_LieferPerformance = "LP";
	/** Quality Performance = QP */
	public static final String PERFORMANCETYPE_QualityPerformance = "QP";
	/** Set PerformanceType.
		@param PerformanceType PerformanceType	  */
	@Override
	public void setPerformanceType (java.lang.String PerformanceType)
	{

		set_Value (COLUMNNAME_PerformanceType, PerformanceType);
	}

	/** Get PerformanceType.
		@return PerformanceType	  */
	@Override
	public java.lang.String getPerformanceType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PerformanceType);
	}

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}
}