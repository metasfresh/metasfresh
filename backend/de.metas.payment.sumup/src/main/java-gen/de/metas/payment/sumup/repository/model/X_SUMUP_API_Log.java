// Generated Model - DO NOT CHANGE
package de.metas.payment.sumup.repository.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for SUMUP_API_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_SUMUP_API_Log extends org.compiere.model.PO implements I_SUMUP_API_Log, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1645854555L;

    /** Standard Constructor */
    public X_SUMUP_API_Log (final Properties ctx, final int SUMUP_API_Log_ID, @Nullable final String trxName)
    {
      super (ctx, SUMUP_API_Log_ID, trxName);
    }

    /** Load Constructor */
    public X_SUMUP_API_Log (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Issue_ID (final int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, AD_Issue_ID);
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setC_POS_Order_ID (final int C_POS_Order_ID)
	{
		if (C_POS_Order_ID < 1) 
			set_Value (COLUMNNAME_C_POS_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Order_ID, C_POS_Order_ID);
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
			set_Value (COLUMNNAME_C_POS_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_POS_Payment_ID, C_POS_Payment_ID);
	}

	@Override
	public int getC_POS_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_Payment_ID);
	}

	/** 
	 * Method AD_Reference_ID=541306
	 * Reference name: Http_Method
	 */
	public static final int METHOD_AD_Reference_ID=541306;
	/** GET = GET */
	public static final String METHOD_GET = "GET";
	/** POST = POST */
	public static final String METHOD_POST = "POST";
	/** PUT = PUT */
	public static final String METHOD_PUT = "PUT";
	/** DELETE = DELETE */
	public static final String METHOD_DELETE = "DELETE";
	/** OPTIONS = OPTIONS */
	public static final String METHOD_OPTIONS = "OPTIONS";
	/** PATCH = PATCH */
	public static final String METHOD_PATCH = "PATCH";
	/** HEAD = HEAD */
	public static final String METHOD_HEAD = "HEAD";
	/** TRACE = TRACE */
	public static final String METHOD_TRACE = "TRACE";
	/** CONNECT = CONNECT */
	public static final String METHOD_CONNECT = "CONNECT";
	@Override
	public void setMethod (final @Nullable java.lang.String Method)
	{
		set_Value (COLUMNNAME_Method, Method);
	}

	@Override
	public java.lang.String getMethod() 
	{
		return get_ValueAsString(COLUMNNAME_Method);
	}

	@Override
	public void setRequestBody (final @Nullable java.lang.String RequestBody)
	{
		set_Value (COLUMNNAME_RequestBody, RequestBody);
	}

	@Override
	public java.lang.String getRequestBody() 
	{
		return get_ValueAsString(COLUMNNAME_RequestBody);
	}

	@Override
	public void setRequestURI (final @Nullable java.lang.String RequestURI)
	{
		set_Value (COLUMNNAME_RequestURI, RequestURI);
	}

	@Override
	public java.lang.String getRequestURI() 
	{
		return get_ValueAsString(COLUMNNAME_RequestURI);
	}

	@Override
	public void setResponseBody (final @Nullable java.lang.String ResponseBody)
	{
		set_Value (COLUMNNAME_ResponseBody, ResponseBody);
	}

	@Override
	public java.lang.String getResponseBody() 
	{
		return get_ValueAsString(COLUMNNAME_ResponseBody);
	}

	@Override
	public void setResponseCode (final int ResponseCode)
	{
		set_Value (COLUMNNAME_ResponseCode, ResponseCode);
	}

	@Override
	public int getResponseCode() 
	{
		return get_ValueAsInt(COLUMNNAME_ResponseCode);
	}

	@Override
	public void setSUMUP_API_Log_ID (final int SUMUP_API_Log_ID)
	{
		if (SUMUP_API_Log_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SUMUP_API_Log_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SUMUP_API_Log_ID, SUMUP_API_Log_ID);
	}

	@Override
	public int getSUMUP_API_Log_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_API_Log_ID);
	}

	@Override
	public de.metas.payment.sumup.repository.model.I_SUMUP_Config getSUMUP_Config()
	{
		return get_ValueAsPO(COLUMNNAME_SUMUP_Config_ID, de.metas.payment.sumup.repository.model.I_SUMUP_Config.class);
	}

	@Override
	public void setSUMUP_Config(final de.metas.payment.sumup.repository.model.I_SUMUP_Config SUMUP_Config)
	{
		set_ValueFromPO(COLUMNNAME_SUMUP_Config_ID, de.metas.payment.sumup.repository.model.I_SUMUP_Config.class, SUMUP_Config);
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

	@Override
	public void setSUMUP_merchant_code (final @Nullable java.lang.String SUMUP_merchant_code)
	{
		set_Value (COLUMNNAME_SUMUP_merchant_code, SUMUP_merchant_code);
	}

	@Override
	public java.lang.String getSUMUP_merchant_code() 
	{
		return get_ValueAsString(COLUMNNAME_SUMUP_merchant_code);
	}
}