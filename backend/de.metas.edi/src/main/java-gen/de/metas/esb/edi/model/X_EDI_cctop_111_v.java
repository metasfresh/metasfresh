/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_111_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_111_v extends org.compiere.model.PO implements I_EDI_cctop_111_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -236243370L;

    /** Standard Constructor */
    public X_EDI_cctop_111_v (Properties ctx, int EDI_cctop_111_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_111_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_111_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void setEDI_cctop_111_v_ID (int EDI_cctop_111_v_ID)
	{
		if (EDI_cctop_111_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_111_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_111_v_ID, Integer.valueOf(EDI_cctop_111_v_ID));
	}

	@Override
	public int getEDI_cctop_111_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_111_v_ID);
	}

	@Override
	public org.compiere.model.I_M_InOut getM_InOut()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class);
	}

	@Override
	public void setM_InOut(org.compiere.model.I_M_InOut M_InOut)
	{
		set_ValueFromPO(COLUMNNAME_M_InOut_ID, org.compiere.model.I_M_InOut.class, M_InOut);
	}

	@Override
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	@Override
	public int getM_InOut_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOut_ID);
	}

	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	@Override
	public void setshipment_documentno (java.lang.String shipment_documentno)
	{
		set_Value (COLUMNNAME_shipment_documentno, shipment_documentno);
	}

	@Override
	public java.lang.String getshipment_documentno() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_shipment_documentno);
	}
}