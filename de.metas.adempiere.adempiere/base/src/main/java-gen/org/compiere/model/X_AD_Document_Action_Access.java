/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Document_Action_Access
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Document_Action_Access extends org.compiere.model.PO implements I_AD_Document_Action_Access, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2013389825L;

    /** Standard Constructor */
    public X_AD_Document_Action_Access (Properties ctx, int AD_Document_Action_Access_ID, String trxName)
    {
      super (ctx, AD_Document_Action_Access_ID, trxName);
      /** if (AD_Document_Action_Access_ID == 0)
        {
			setAD_Document_Action_Access_ID (0);
			setAD_Ref_List_ID (0);
			setAD_Role_ID (0);
			setC_DocType_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Document_Action_Access (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Document Action Access.
		@param AD_Document_Action_Access_ID Document Action Access	  */
	@Override
	public void setAD_Document_Action_Access_ID (int AD_Document_Action_Access_ID)
	{
		if (AD_Document_Action_Access_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Document_Action_Access_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Document_Action_Access_ID, Integer.valueOf(AD_Document_Action_Access_ID));
	}

	/** Get Document Action Access.
		@return Document Action Access	  */
	@Override
	public int getAD_Document_Action_Access_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Document_Action_Access_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Ref_List getAD_Ref_List() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Ref_List_ID, org.compiere.model.I_AD_Ref_List.class);
	}

	@Override
	public void setAD_Ref_List(org.compiere.model.I_AD_Ref_List AD_Ref_List)
	{
		set_ValueFromPO(COLUMNNAME_AD_Ref_List_ID, org.compiere.model.I_AD_Ref_List.class, AD_Ref_List);
	}

	/** Set Referenzliste.
		@param AD_Ref_List_ID 
		Reference List based on Table
	  */
	@Override
	public void setAD_Ref_List_ID (int AD_Ref_List_ID)
	{
		if (AD_Ref_List_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Ref_List_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Ref_List_ID, Integer.valueOf(AD_Ref_List_ID));
	}

	/** Get Referenzliste.
		@return Reference List based on Table
	  */
	@Override
	public int getAD_Ref_List_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Ref_List_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID 
		Document type or rules
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Document type or rules
	  */
	@Override
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}