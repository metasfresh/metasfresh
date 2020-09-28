/** Generated Model - DO NOT CHANGE */
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for PP_Cost_Collector_ImportAuditItem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_PP_Cost_Collector_ImportAuditItem extends org.compiere.model.PO implements I_PP_Cost_Collector_ImportAuditItem, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1300347214L;

    /** Standard Constructor */
    public X_PP_Cost_Collector_ImportAuditItem (Properties ctx, int PP_Cost_Collector_ImportAuditItem_ID, String trxName)
    {
      super (ctx, PP_Cost_Collector_ImportAuditItem_ID, trxName);
    }

    /** Load Constructor */
    public X_PP_Cost_Collector_ImportAuditItem (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public void setErrorMsg (java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setImportStatus (java.lang.String ImportStatus)
	{
		set_Value (COLUMNNAME_ImportStatus, ImportStatus);
	}

	@Override
	public java.lang.String getImportStatus() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ImportStatus);
	}

	@Override
	public void setJsonRequest (java.lang.String JsonRequest)
	{
		set_Value (COLUMNNAME_JsonRequest, JsonRequest);
	}

	@Override
	public java.lang.String getJsonRequest() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JsonRequest);
	}

	@Override
	public void setJsonResponse (java.lang.String JsonResponse)
	{
		set_Value (COLUMNNAME_JsonResponse, JsonResponse);
	}

	@Override
	public java.lang.String getJsonResponse() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_JsonResponse);
	}

	@Override
	public org.eevolution.model.I_PP_Cost_Collector getPP_Cost_Collector()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class);
	}

	@Override
	public void setPP_Cost_Collector(org.eevolution.model.I_PP_Cost_Collector PP_Cost_Collector)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ID, org.eevolution.model.I_PP_Cost_Collector.class, PP_Cost_Collector);
	}

	@Override
	public void setPP_Cost_Collector_ID (int PP_Cost_Collector_ID)
	{
		if (PP_Cost_Collector_ID < 1) 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Cost_Collector_ID, Integer.valueOf(PP_Cost_Collector_ID));
	}

	@Override
	public int getPP_Cost_Collector_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Cost_Collector_ImportAudit getPP_Cost_Collector_ImportAudit()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Cost_Collector_ImportAudit_ID, org.eevolution.model.I_PP_Cost_Collector_ImportAudit.class);
	}

	@Override
	public void setPP_Cost_Collector_ImportAudit(org.eevolution.model.I_PP_Cost_Collector_ImportAudit PP_Cost_Collector_ImportAudit)
	{
		set_ValueFromPO(COLUMNNAME_PP_Cost_Collector_ImportAudit_ID, org.eevolution.model.I_PP_Cost_Collector_ImportAudit.class, PP_Cost_Collector_ImportAudit);
	}

	@Override
	public void setPP_Cost_Collector_ImportAudit_ID (int PP_Cost_Collector_ImportAudit_ID)
	{
		if (PP_Cost_Collector_ImportAudit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ImportAudit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ImportAudit_ID, Integer.valueOf(PP_Cost_Collector_ImportAudit_ID));
	}

	@Override
	public int getPP_Cost_Collector_ImportAudit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ImportAudit_ID);
	}

	@Override
	public void setPP_Cost_Collector_ImportAuditItem_ID (int PP_Cost_Collector_ImportAuditItem_ID)
	{
		if (PP_Cost_Collector_ImportAuditItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ImportAuditItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_Cost_Collector_ImportAuditItem_ID, Integer.valueOf(PP_Cost_Collector_ImportAuditItem_ID));
	}

	@Override
	public int getPP_Cost_Collector_ImportAuditItem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Cost_Collector_ImportAuditItem_ID);
	}

	@Override
	public org.eevolution.model.I_PP_Order getPP_Order()
	{
		return get_ValueAsPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class);
	}

	@Override
	public void setPP_Order(org.eevolution.model.I_PP_Order PP_Order)
	{
		set_ValueFromPO(COLUMNNAME_PP_Order_ID, org.eevolution.model.I_PP_Order.class, PP_Order);
	}

	@Override
	public void setPP_Order_ID (int PP_Order_ID)
	{
		if (PP_Order_ID < 1) 
			set_Value (COLUMNNAME_PP_Order_ID, null);
		else 
			set_Value (COLUMNNAME_PP_Order_ID, Integer.valueOf(PP_Order_ID));
	}

	@Override
	public int getPP_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PP_Order_ID);
	}
}