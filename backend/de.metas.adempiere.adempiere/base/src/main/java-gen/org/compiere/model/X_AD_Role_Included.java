/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Role_Included
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Role_Included extends org.compiere.model.PO implements I_AD_Role_Included, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1129266122L;

    /** Standard Constructor */
    public X_AD_Role_Included (Properties ctx, int AD_Role_Included_ID, String trxName)
    {
      super (ctx, AD_Role_Included_ID, trxName);
      /** if (AD_Role_Included_ID == 0)
        {
			setAD_Role_ID (0);
			setAD_Role_Included_ID (0);
			setIncluded_Role_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_Role_Included WHERE AD_Role_ID=@AD_Role_ID@
        } */
    }

    /** Load Constructor */
    public X_AD_Role_Included (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Included Role.
		@param AD_Role_Included_ID Included Role	  */
	@Override
	public void setAD_Role_Included_ID (int AD_Role_Included_ID)
	{
		if (AD_Role_Included_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_Included_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_Included_ID, Integer.valueOf(AD_Role_Included_ID));
	}

	/** Get Included Role.
		@return Included Role	  */
	@Override
	public int getAD_Role_Included_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_Included_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getIncluded_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Included_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setIncluded_Role(org.compiere.model.I_AD_Role Included_Role)
	{
		set_ValueFromPO(COLUMNNAME_Included_Role_ID, org.compiere.model.I_AD_Role.class, Included_Role);
	}

	/** Set Included Role.
		@param Included_Role_ID Included Role	  */
	@Override
	public void setIncluded_Role_ID (int Included_Role_ID)
	{
		if (Included_Role_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Included_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Included_Role_ID, Integer.valueOf(Included_Role_ID));
	}

	/** Get Included Role.
		@return Included Role	  */
	@Override
	public int getIncluded_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Included_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
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