<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_M_InOut_Overdelivery_C_OrderLine_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_M_InOut_Overdelivery_C_OrderLine_v extends org.compiere.model.PO implements I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -735093375L;

    /** Standard Constructor */
    public X_EDI_M_InOut_Overdelivery_C_OrderLine_v (Properties ctx, int EDI_M_InOut_Overdelivery_C_OrderLine_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_M_InOut_Overdelivery_C_OrderLine_v extends org.compiere.model.PO implements I_EDI_M_InOut_Overdelivery_C_OrderLine_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 332969759L;

    /** Standard Constructor */
    public X_EDI_M_InOut_Overdelivery_C_OrderLine_v (final Properties ctx, final int EDI_M_InOut_Overdelivery_C_OrderLine_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_M_InOut_Overdelivery_C_OrderLine_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_M_InOut_Overdelivery_C_OrderLine_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_M_InOut_Overdelivery_C_OrderLine_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
<<<<<<< HEAD
	protected org.compiere.model.POInfo initPO(Properties ctx)
=======
	protected org.compiere.model.POInfo initPO(final Properties ctx)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
<<<<<<< HEAD
	public void setC_OrderLine_ID (int C_OrderLine_ID)
=======
	public void setC_OrderLine_ID (final int C_OrderLine_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_OrderLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, Integer.valueOf(C_OrderLine_ID));
=======
			set_ValueNoCheck (COLUMNNAME_C_OrderLine_ID, C_OrderLine_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_OrderLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderLine_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
=======
	public void setM_InOut(final org.compiere.model.I_M_InOut M_InOut)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOut_ID (int M_InOut_ID)
=======
	public void setM_InOut_ID (final int M_InOut_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
=======
			set_Value (COLUMNNAME_M_InOut_ID, M_InOut_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}
}