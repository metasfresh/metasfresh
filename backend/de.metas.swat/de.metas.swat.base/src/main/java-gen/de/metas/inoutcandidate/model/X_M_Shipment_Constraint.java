/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Shipment_Constraint
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Shipment_Constraint extends org.compiere.model.PO implements I_M_Shipment_Constraint, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -598711815L;

    /** Standard Constructor */
    public X_M_Shipment_Constraint (Properties ctx, int M_Shipment_Constraint_ID, String trxName)
    {
      super (ctx, M_Shipment_Constraint_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipment_Constraint (Properties ctx, ResultSet rs, String trxName)
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
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	@Override
	public int getBill_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Bill_BPartner_ID);
	}

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		throw new IllegalArgumentException ("C_Invoice_ID is virtual column");	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setIsDeliveryStop (boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, Boolean.valueOf(IsDeliveryStop));
	}

	@Override
	public boolean isDeliveryStop() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeliveryStop);
	}

	@Override
	public void setIsPaid (boolean IsPaid)
	{
		throw new IllegalArgumentException ("IsPaid is virtual column");	}

	@Override
	public boolean isPaid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPaid);
	}

	@Override
	public void setM_Shipment_Constraint_ID (int M_Shipment_Constraint_ID)
	{
		if (M_Shipment_Constraint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Constraint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Constraint_ID, Integer.valueOf(M_Shipment_Constraint_ID));
	}

	@Override
	public int getM_Shipment_Constraint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipment_Constraint_ID);
	}

	@Override
	public void setSourceDoc_Record_ID (int SourceDoc_Record_ID)
	{
		if (SourceDoc_Record_ID < 1) 
			set_Value (COLUMNNAME_SourceDoc_Record_ID, null);
		else 
			set_Value (COLUMNNAME_SourceDoc_Record_ID, Integer.valueOf(SourceDoc_Record_ID));
	}

	@Override
	public int getSourceDoc_Record_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SourceDoc_Record_ID);
	}

	@Override
	public void setSourceDoc_Table_ID (int SourceDoc_Table_ID)
	{
		if (SourceDoc_Table_ID < 1) 
			set_Value (COLUMNNAME_SourceDoc_Table_ID, null);
		else 
			set_Value (COLUMNNAME_SourceDoc_Table_ID, Integer.valueOf(SourceDoc_Table_ID));
	}

	@Override
	public int getSourceDoc_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SourceDoc_Table_ID);
	}
}