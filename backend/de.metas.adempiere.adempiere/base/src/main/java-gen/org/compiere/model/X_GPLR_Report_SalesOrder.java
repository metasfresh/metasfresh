// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for GPLR_Report_SalesOrder
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_GPLR_Report_SalesOrder extends org.compiere.model.PO implements I_GPLR_Report_SalesOrder, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1005911228L;

    /** Standard Constructor */
    public X_GPLR_Report_SalesOrder (final Properties ctx, final int GPLR_Report_SalesOrder_ID, @Nullable final String trxName)
    {
      super (ctx, GPLR_Report_SalesOrder_ID, trxName);
    }

    /** Load Constructor */
    public X_GPLR_Report_SalesOrder (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBPartnerName (final @Nullable java.lang.String BPartnerName)
	{
		set_Value (COLUMNNAME_BPartnerName, BPartnerName);
	}

	@Override
	public java.lang.String getBPartnerName() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerName);
	}

	@Override
	public void setBPartnerValue (final @Nullable java.lang.String BPartnerValue)
	{
		set_Value (COLUMNNAME_BPartnerValue, BPartnerValue);
	}

	@Override
	public java.lang.String getBPartnerValue() 
	{
		return get_ValueAsString(COLUMNNAME_BPartnerValue);
	}

	@Override
	public void setBPartner_VatId (final @Nullable java.lang.String BPartner_VatId)
	{
		set_Value (COLUMNNAME_BPartner_VatId, BPartner_VatId);
	}

	@Override
	public java.lang.String getBPartner_VatId() 
	{
		return get_ValueAsString(COLUMNNAME_BPartner_VatId);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
	}

	@Override
	public void setFrameContractNo (final @Nullable java.lang.String FrameContractNo)
	{
		set_Value (COLUMNNAME_FrameContractNo, FrameContractNo);
	}

	@Override
	public java.lang.String getFrameContractNo() 
	{
		return get_ValueAsString(COLUMNNAME_FrameContractNo);
	}

	@Override
	public org.compiere.model.I_GPLR_Report getGPLR_Report()
	{
		return get_ValueAsPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class);
	}

	@Override
	public void setGPLR_Report(final org.compiere.model.I_GPLR_Report GPLR_Report)
	{
		set_ValueFromPO(COLUMNNAME_GPLR_Report_ID, org.compiere.model.I_GPLR_Report.class, GPLR_Report);
	}

	@Override
	public void setGPLR_Report_ID (final int GPLR_Report_ID)
	{
		if (GPLR_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_ID, GPLR_Report_ID);
	}

	@Override
	public int getGPLR_Report_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_ID);
	}

	@Override
	public void setGPLR_Report_SalesOrder_ID (final int GPLR_Report_SalesOrder_ID)
	{
		if (GPLR_Report_SalesOrder_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_SalesOrder_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GPLR_Report_SalesOrder_ID, GPLR_Report_SalesOrder_ID);
	}

	@Override
	public int getGPLR_Report_SalesOrder_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_GPLR_Report_SalesOrder_ID);
	}

	@Override
	public void setIncoterm_Code (final @Nullable java.lang.String Incoterm_Code)
	{
		set_Value (COLUMNNAME_Incoterm_Code, Incoterm_Code);
	}

	@Override
	public java.lang.String getIncoterm_Code() 
	{
		return get_ValueAsString(COLUMNNAME_Incoterm_Code);
	}

	@Override
	public void setIncotermLocation (final @Nullable java.lang.String IncotermLocation)
	{
		set_Value (COLUMNNAME_IncotermLocation, IncotermLocation);
	}

	@Override
	public java.lang.String getIncotermLocation() 
	{
		return get_ValueAsString(COLUMNNAME_IncotermLocation);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}
}