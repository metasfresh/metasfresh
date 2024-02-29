// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Document_Action_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Document_Action_Access extends org.compiere.model.PO implements I_AD_Document_Action_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 739447442L;

    /** Standard Constructor */
    public X_AD_Document_Action_Access (final Properties ctx, final int AD_Document_Action_Access_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Document_Action_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Document_Action_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Ref_List getAD_Ref_List()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Ref_List_ID, org.compiere.model.I_AD_Ref_List.class);
	}

	@Override
	public void setAD_Ref_List(final org.compiere.model.I_AD_Ref_List AD_Ref_List)
	{
		set_ValueFromPO(COLUMNNAME_AD_Ref_List_ID, org.compiere.model.I_AD_Ref_List.class, AD_Ref_List);
	}

	@Override
	public void setAD_Ref_List_ID (final int AD_Ref_List_ID)
	{
		if (AD_Ref_List_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Ref_List_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Ref_List_ID, AD_Ref_List_ID);
	}

	@Override
	public int getAD_Ref_List_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Ref_List_ID);
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setC_DocType_ID (final int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, C_DocType_ID);
	}

	@Override
	public int getC_DocType_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DocType_ID);
	}
}