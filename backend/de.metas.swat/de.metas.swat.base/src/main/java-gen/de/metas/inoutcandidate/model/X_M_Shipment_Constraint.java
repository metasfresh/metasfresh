// Generated Model - DO NOT CHANGE
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Shipment_Constraint
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Shipment_Constraint extends org.compiere.model.PO implements I_M_Shipment_Constraint, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2045758258L;

    /** Standard Constructor */
    public X_M_Shipment_Constraint (final Properties ctx, final int M_Shipment_Constraint_ID, @Nullable final String trxName)
    {
      super (ctx, M_Shipment_Constraint_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Shipment_Constraint (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBill_BPartner_ID (final int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1) 
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_Bill_BPartner_ID, Bill_BPartner_ID);
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
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		throw new IllegalArgumentException ("C_Invoice_ID is virtual column");	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setIsDeliveryStop (final boolean IsDeliveryStop)
	{
		set_Value (COLUMNNAME_IsDeliveryStop, IsDeliveryStop);
	}

	@Override
	public boolean isDeliveryStop() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDeliveryStop);
	}

	@Override
	public void setIsPaid (final boolean IsPaid)
	{
		throw new IllegalArgumentException ("IsPaid is virtual column");	}

	@Override
	public boolean isPaid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPaid);
	}

	@Override
	public void setM_Shipment_Constraint_ID (final int M_Shipment_Constraint_ID)
	{
		if (M_Shipment_Constraint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Constraint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Shipment_Constraint_ID, M_Shipment_Constraint_ID);
	}

	@Override
	public int getM_Shipment_Constraint_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Shipment_Constraint_ID);
	}

	@Override
	public void setSourceDoc_Record_ID (final int SourceDoc_Record_ID)
	{
		if (SourceDoc_Record_ID < 1) 
			set_Value (COLUMNNAME_SourceDoc_Record_ID, null);
		else 
			set_Value (COLUMNNAME_SourceDoc_Record_ID, SourceDoc_Record_ID);
	}

	@Override
	public int getSourceDoc_Record_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SourceDoc_Record_ID);
	}

	@Override
	public void setSourceDoc_Table_ID (final int SourceDoc_Table_ID)
	{
		if (SourceDoc_Table_ID < 1) 
			set_Value (COLUMNNAME_SourceDoc_Table_ID, null);
		else 
			set_Value (COLUMNNAME_SourceDoc_Table_ID, SourceDoc_Table_ID);
	}

	@Override
	public int getSourceDoc_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SourceDoc_Table_ID);
	}
}