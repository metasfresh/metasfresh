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

/** Generated Model for EDI_C_BPartner_Product_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_C_BPartner_Product_v extends org.compiere.model.PO implements I_EDI_C_BPartner_Product_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 675228558L;

    /** Standard Constructor */
    public X_EDI_C_BPartner_Product_v (Properties ctx, int EDI_C_BPartner_Product_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_C_BPartner_Product_v extends org.compiere.model.PO implements I_EDI_C_BPartner_Product_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -8881456L;

    /** Standard Constructor */
    public X_EDI_C_BPartner_Product_v (final Properties ctx, final int EDI_C_BPartner_Product_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_C_BPartner_Product_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_C_BPartner_Product_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_C_BPartner_Product_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_ID (int C_BPartner_ID)
=======
	public void setC_BPartner_ID (final int C_BPartner_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
=======
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_OrderLine getC_OrderLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_OrderLine(org.compiere.model.I_C_OrderLine C_OrderLine)
=======
	public void setC_OrderLine(final org.compiere.model.I_C_OrderLine C_OrderLine)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_OrderLine_ID, org.compiere.model.I_C_OrderLine.class, C_OrderLine);
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
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
=======
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
<<<<<<< HEAD
	public void setM_InOutLine_ID (int M_InOutLine_ID)
=======
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
=======
			set_ValueNoCheck (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
<<<<<<< HEAD
	public void setM_Product_ID (int M_Product_ID)
=======
	public void setM_Product_ID (final int M_Product_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
=======
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
<<<<<<< HEAD
	public void setProductNo (java.lang.String ProductNo)
=======
	public void setProductNo (final @Nullable java.lang.String ProductNo)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ProductNo, ProductNo);
	}

	@Override
	public java.lang.String getProductNo() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ProductNo);
	}

	@Override
	public void setUPC (java.lang.String UPC)
=======
		return get_ValueAsString(COLUMNNAME_ProductNo);
	}

	@Override
	public void setUPC (final @Nullable java.lang.String UPC)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_UPC, UPC);
	}

	@Override
	public java.lang.String getUPC() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_UPC);
=======
		return get_ValueAsString(COLUMNNAME_UPC);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}