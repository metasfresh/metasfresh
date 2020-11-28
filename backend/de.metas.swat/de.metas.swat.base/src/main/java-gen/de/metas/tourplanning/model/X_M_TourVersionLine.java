/** Generated Model - DO NOT CHANGE */
package de.metas.tourplanning.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_TourVersionLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_TourVersionLine extends org.compiere.model.PO implements I_M_TourVersionLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1808785119L;

    /** Standard Constructor */
    public X_M_TourVersionLine (Properties ctx, int M_TourVersionLine_ID, String trxName)
    {
      super (ctx, M_TourVersionLine_ID, trxName);
      /** if (M_TourVersionLine_ID == 0)
        {
			setM_TourVersionLine_ID (0);
			setSeqNo (0); // 0
        } */
    }

    /** Load Constructor */
    public X_M_TourVersionLine (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Puffer (Std).
		@param BufferHours Puffer (Std)	  */
	@Override
	public void setBufferHours (int BufferHours)
	{
		set_Value (COLUMNNAME_BufferHours, Integer.valueOf(BufferHours));
	}

	/** Get Puffer (Std).
		@return Puffer (Std)	  */
	@Override
	public int getBufferHours () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BufferHours);
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

	/** Set Abholung.
		@param IsToBeFetched Abholung	  */
	@Override
	public void setIsToBeFetched (boolean IsToBeFetched)
	{
		set_Value (COLUMNNAME_IsToBeFetched, Boolean.valueOf(IsToBeFetched));
	}

	/** Get Abholung.
		@return Abholung	  */
	@Override
	public boolean isToBeFetched () 
	{
		Object oo = get_Value(COLUMNNAME_IsToBeFetched);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public de.metas.tourplanning.model.I_M_TourVersion getM_TourVersion() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_TourVersion_ID, de.metas.tourplanning.model.I_M_TourVersion.class);
	}

	@Override
	public void setM_TourVersion(de.metas.tourplanning.model.I_M_TourVersion M_TourVersion)
	{
		set_ValueFromPO(COLUMNNAME_M_TourVersion_ID, de.metas.tourplanning.model.I_M_TourVersion.class, M_TourVersion);
	}

	/** Set Tour Version.
		@param M_TourVersion_ID Tour Version	  */
	@Override
	public void setM_TourVersion_ID (int M_TourVersion_ID)
	{
		if (M_TourVersion_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_TourVersion_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_TourVersion_ID, Integer.valueOf(M_TourVersion_ID));
	}

	/** Get Tour Version.
		@return Tour Version	  */
	@Override
	public int getM_TourVersion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TourVersion_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tour Version Line.
		@param M_TourVersionLine_ID Tour Version Line	  */
	@Override
	public void setM_TourVersionLine_ID (int M_TourVersionLine_ID)
	{
		if (M_TourVersionLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_TourVersionLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_TourVersionLine_ID, Integer.valueOf(M_TourVersionLine_ID));
	}

	/** Get Tour Version Line.
		@return Tour Version Line	  */
	@Override
	public int getM_TourVersionLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_TourVersionLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}