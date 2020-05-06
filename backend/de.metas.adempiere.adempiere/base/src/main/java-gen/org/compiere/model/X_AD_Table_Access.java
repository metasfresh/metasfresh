/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Table_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Table_Access extends org.compiere.model.PO implements I_AD_Table_Access, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1006119357L;

    /** Standard Constructor */
    public X_AD_Table_Access (Properties ctx, int AD_Table_Access_ID, String trxName)
    {
      super (ctx, AD_Table_Access_ID, trxName);
      /** if (AD_Table_Access_ID == 0)
        {
			setAccessTypeRule (null);
// A
			setAD_Role_ID (0);
			setAD_Table_ID (0);
			setIsCanExport (false);
			setIsCanReport (false);
			setIsExclude (true);
// Y
			setIsReadOnly (false);
        } */
    }

    /** Load Constructor */
    public X_AD_Table_Access (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AccessTypeRule AD_Reference_ID=293
	 * Reference name: AD_Table_Access RuleType
	 */
	public static final int ACCESSTYPERULE_AD_Reference_ID=293;
	/** Accessing = A */
	public static final String ACCESSTYPERULE_Accessing = "A";
	/** Reporting = R */
	public static final String ACCESSTYPERULE_Reporting = "R";
	/** Exporting = E */
	public static final String ACCESSTYPERULE_Exporting = "E";
	/** Set Access Type.
		@param AccessTypeRule 
		The type of access for this rule
	  */
	@Override
	public void setAccessTypeRule (java.lang.String AccessTypeRule)
	{

		set_ValueNoCheck (COLUMNNAME_AccessTypeRule, AccessTypeRule);
	}

	/** Get Access Type.
		@return The type of access for this rule
	  */
	@Override
	public java.lang.String getAccessTypeRule () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccessTypeRule);
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

	/** Set Kann exportieren.
		@param IsCanExport 
		Users with this role can export data
	  */
	@Override
	public void setIsCanExport (boolean IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, Boolean.valueOf(IsCanExport));
	}

	/** Get Kann exportieren.
		@return Users with this role can export data
	  */
	@Override
	public boolean isCanExport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanExport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Kann Berichte erstellen.
		@param IsCanReport 
		Users with this role can create reports
	  */
	@Override
	public void setIsCanReport (boolean IsCanReport)
	{
		set_Value (COLUMNNAME_IsCanReport, Boolean.valueOf(IsCanReport));
	}

	/** Get Kann Berichte erstellen.
		@return Users with this role can create reports
	  */
	@Override
	public boolean isCanReport () 
	{
		Object oo = get_Value(COLUMNNAME_IsCanReport);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Ausschluß.
		@param IsExclude 
		Exclude access to the data - if not selected Include access to the data
	  */
	@Override
	public void setIsExclude (boolean IsExclude)
	{
		set_Value (COLUMNNAME_IsExclude, Boolean.valueOf(IsExclude));
	}

	/** Get Ausschluß.
		@return Exclude access to the data - if not selected Include access to the data
	  */
	@Override
	public boolean isExclude () 
	{
		Object oo = get_Value(COLUMNNAME_IsExclude);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Schreibgeschützt.
		@param IsReadOnly 
		Field is read only
	  */
	@Override
	public void setIsReadOnly (boolean IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, Boolean.valueOf(IsReadOnly));
	}

	/** Get Schreibgeschützt.
		@return Field is read only
	  */
	@Override
	public boolean isReadOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}