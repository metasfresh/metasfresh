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

/** Generated Model for EDI_cctop_111_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_111_v extends org.compiere.model.PO implements I_EDI_cctop_111_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -236243370L;

    /** Standard Constructor */
    public X_EDI_cctop_111_v (Properties ctx, int EDI_cctop_111_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_111_v extends org.compiere.model.PO implements I_EDI_cctop_111_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1352625043L;

    /** Standard Constructor */
    public X_EDI_cctop_111_v (final Properties ctx, final int EDI_cctop_111_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_111_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_111_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_111_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
<<<<<<< HEAD
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
=======
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
<<<<<<< HEAD
	public void setC_Order_ID (int C_Order_ID)
=======
	public void setC_Order_ID (final int C_Order_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
=======
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
<<<<<<< HEAD
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
=======
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
<<<<<<< HEAD
	public void setEDI_cctop_111_v_ID (int EDI_cctop_111_v_ID)
=======
	public void setEDI_cctop_111_v_ID (final int EDI_cctop_111_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_111_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_111_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_111_v_ID, Integer.valueOf(EDI_cctop_111_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_111_v_ID, EDI_cctop_111_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

	@Override
<<<<<<< HEAD
	public void setMovementDate (java.sql.Timestamp MovementDate)
=======
	public void setMovementDate (final @Nullable java.sql.Timestamp MovementDate)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
<<<<<<< HEAD
	public void setPOReference (java.lang.String POReference)
=======
	public void setPOReference (final @Nullable java.lang.String POReference)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
<<<<<<< HEAD
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
=======
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setShipment_DocumentNo (final @Nullable java.lang.String Shipment_DocumentNo)
	{
		set_Value (COLUMNNAME_Shipment_DocumentNo, Shipment_DocumentNo);
	}

	@Override
	public java.lang.String getShipment_DocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_Shipment_DocumentNo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}