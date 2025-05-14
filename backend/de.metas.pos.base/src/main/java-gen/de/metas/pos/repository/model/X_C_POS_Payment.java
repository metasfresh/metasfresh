// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_Payment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_Payment extends org.compiere.model.PO implements I_C_POS_Payment, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 663180181L;

    /** Standard Constructor */
    public X_C_POS_Payment (final Properties ctx, final int C_POS_Payment_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_Payment_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_Payment (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setAmountTendered (final BigDecimal AmountTendered)
	{
		set_Value (COLUMNNAME_AmountTendered, AmountTendered);
	}

	@Override
	public BigDecimal getAmountTendered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_AmountTendered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setChangeBackAmount (final BigDecimal ChangeBackAmount)
	{
		set_Value (COLUMNNAME_ChangeBackAmount, ChangeBackAmount);
	}

	@Override
	public BigDecimal getChangeBackAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ChangeBackAmount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public de.metas.pos.repository.model.I_C_POS_Order getC_POS_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_POS_Order_ID, de.metas.pos.repository.model.I_C_POS_Order.class);
	}

	@Override
	public void setC_POS_Order(final de.metas.pos.repository.model.I_C_POS_Order C_POS_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_POS_Order_ID, de.metas.pos.repository.model.I_C_POS_Order.class, C_POS_Order);
	}

	@Override
	public void setC_POS_Order_ID (final int C_POS_Order_ID)
	{
		if (C_POS_Order_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Order_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Order_ID, C_POS_Order_ID);
	}

	@Override
	public int getC_POS_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Order_ID);
	}

	@Override
	public void setC_POS_Payment_ID (final int C_POS_Payment_ID)
	{
		if (C_POS_Payment_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_Payment_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_Payment_ID, C_POS_Payment_ID);
	}

	@Override
	public int getC_POS_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Payment_ID);
	}

	@Override
	public void setExternalId (final java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setPOS_CardReader_ExternalId (final @Nullable java.lang.String POS_CardReader_ExternalId)
	{
		set_Value (COLUMNNAME_POS_CardReader_ExternalId, POS_CardReader_ExternalId);
	}

	@Override
	public java.lang.String getPOS_CardReader_ExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_POS_CardReader_ExternalId);
	}

	@Override
	public void setPOS_CardReader_Name (final @Nullable java.lang.String POS_CardReader_Name)
	{
		set_Value (COLUMNNAME_POS_CardReader_Name, POS_CardReader_Name);
	}

	@Override
	public java.lang.String getPOS_CardReader_Name() 
	{
		return get_ValueAsString(COLUMNNAME_POS_CardReader_Name);
	}

	/** 
	 * POSPaymentMethod AD_Reference_ID=541891
	 * Reference name: POSPaymentMethod
	 */
	public static final int POSPAYMENTMETHOD_AD_Reference_ID=541891;
	/** Cash = CASH */
	public static final String POSPAYMENTMETHOD_Cash = "CASH";
	/** Card = CARD */
	public static final String POSPAYMENTMETHOD_Card = "CARD";
	@Override
	public void setPOSPaymentMethod (final java.lang.String POSPaymentMethod)
	{
		set_Value (COLUMNNAME_POSPaymentMethod, POSPaymentMethod);
	}

	@Override
	public java.lang.String getPOSPaymentMethod() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentMethod);
	}

	/** 
	 * POSPaymentProcessingStatus AD_Reference_ID=541897
	 * Reference name: POSPaymentProcessingStatus
	 */
	public static final int POSPAYMENTPROCESSINGSTATUS_AD_Reference_ID=541897;
	/** SUCCESSFUL = SUCCESSFUL */
	public static final String POSPAYMENTPROCESSINGSTATUS_SUCCESSFUL = "SUCCESSFUL";
	/** CANCELLED = CANCELLED */
	public static final String POSPAYMENTPROCESSINGSTATUS_CANCELLED = "CANCELLED";
	/** FAILED = FAILED */
	public static final String POSPAYMENTPROCESSINGSTATUS_FAILED = "FAILED";
	/** PENDING = PENDING */
	public static final String POSPAYMENTPROCESSINGSTATUS_PENDING = "PENDING";
	/** NEW = NEW */
	public static final String POSPAYMENTPROCESSINGSTATUS_NEW = "NEW";
	/** DELETED = DELETED */
	public static final String POSPAYMENTPROCESSINGSTATUS_DELETED = "DELETED";
	@Override
	public void setPOSPaymentProcessingStatus (final java.lang.String POSPaymentProcessingStatus)
	{
		set_Value (COLUMNNAME_POSPaymentProcessingStatus, POSPaymentProcessingStatus);
	}

	@Override
	public java.lang.String getPOSPaymentProcessingStatus() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentProcessingStatus);
	}

	@Override
	public void setPOSPaymentProcessingSummary (final @Nullable java.lang.String POSPaymentProcessingSummary)
	{
		set_Value (COLUMNNAME_POSPaymentProcessingSummary, POSPaymentProcessingSummary);
	}

	@Override
	public java.lang.String getPOSPaymentProcessingSummary() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentProcessingSummary);
	}

	@Override
	public void setPOSPaymentProcessing_TrxId (final @Nullable java.lang.String POSPaymentProcessing_TrxId)
	{
		set_Value (COLUMNNAME_POSPaymentProcessing_TrxId, POSPaymentProcessing_TrxId);
	}

	@Override
	public java.lang.String getPOSPaymentProcessing_TrxId() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentProcessing_TrxId);
	}

	/** 
	 * POSPaymentProcessor AD_Reference_ID=541896
	 * Reference name: POSPaymentProcessor
	 */
	public static final int POSPAYMENTPROCESSOR_AD_Reference_ID=541896;
	/** SumUp = sumup */
	public static final String POSPAYMENTPROCESSOR_SumUp = "sumup";
	@Override
	public void setPOSPaymentProcessor (final @Nullable java.lang.String POSPaymentProcessor)
	{
		set_Value (COLUMNNAME_POSPaymentProcessor, POSPaymentProcessor);
	}

	@Override
	public java.lang.String getPOSPaymentProcessor() 
	{
		return get_ValueAsString(COLUMNNAME_POSPaymentProcessor);
	}

	@Override
	public void setSUMUP_Config_ID (final int SUMUP_Config_ID)
	{
		if (SUMUP_Config_ID < 1) 
			set_Value (COLUMNNAME_SUMUP_Config_ID, null);
		else 
			set_Value (COLUMNNAME_SUMUP_Config_ID, SUMUP_Config_ID);
	}

	@Override
	public int getSUMUP_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Config_ID);
	}
}