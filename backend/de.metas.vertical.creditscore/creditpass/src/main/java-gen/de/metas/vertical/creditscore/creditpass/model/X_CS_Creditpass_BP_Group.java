/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.creditscore.creditpass.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for CS_Creditpass_BP_Group
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_CS_Creditpass_BP_Group extends org.compiere.model.PO implements I_CS_Creditpass_BP_Group, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -368452604L;

    /** Standard Constructor */
    public X_CS_Creditpass_BP_Group (Properties ctx, int CS_Creditpass_BP_Group_ID, String trxName)
    {
      super (ctx, CS_Creditpass_BP_Group_ID, trxName);
      /** if (CS_Creditpass_BP_Group_ID == 0)
        {
			setC_BP_Group_ID (0);
			setCS_Creditpass_BP_Group_ID (0);
			setCS_Creditpass_Config_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CS_Creditpass_BP_Group (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_BP_Group getC_BP_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class);
	}

	@Override
	public void setC_BP_Group(org.compiere.model.I_C_BP_Group C_BP_Group)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_Group_ID, org.compiere.model.I_C_BP_Group.class, C_BP_Group);
	}

	/** Set Geschäftspartnergruppe.
		@param C_BP_Group_ID 
		Geschäftspartnergruppe
	  */
	@Override
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Geschäftspartnergruppe.
		@return Geschäftspartnergruppe
	  */
	@Override
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CS Creditpass business partner group.
		@param CS_Creditpass_BP_Group_ID CS Creditpass business partner group	  */
	@Override
	public void setCS_Creditpass_BP_Group_ID (int CS_Creditpass_BP_Group_ID)
	{
		if (CS_Creditpass_BP_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_BP_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_BP_Group_ID, Integer.valueOf(CS_Creditpass_BP_Group_ID));
	}

	/** Get CS Creditpass business partner group.
		@return CS Creditpass business partner group	  */
	@Override
	public int getCS_Creditpass_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config getCS_Creditpass_Config() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CS_Creditpass_Config_ID, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config.class);
	}

	@Override
	public void setCS_Creditpass_Config(de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config CS_Creditpass_Config)
	{
		set_ValueFromPO(COLUMNNAME_CS_Creditpass_Config_ID, de.metas.vertical.creditscore.creditpass.model.I_CS_Creditpass_Config.class, CS_Creditpass_Config);
	}

	/** Get Creditpass Einstellung.
		@return Creditpass Einstellung	  */
	@Override
	public int getCS_Creditpass_Config_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CS_Creditpass_Config_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Creditpass Einstellung.
		@param CS_Creditpass_Config_ID Creditpass Einstellung	  */
	@Override
	public void setCS_Creditpass_Config_ID (int CS_Creditpass_Config_ID)
	{
		if (CS_Creditpass_Config_ID < 1)
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_CS_Creditpass_Config_ID, Integer.valueOf(CS_Creditpass_Config_ID));
	}
}
