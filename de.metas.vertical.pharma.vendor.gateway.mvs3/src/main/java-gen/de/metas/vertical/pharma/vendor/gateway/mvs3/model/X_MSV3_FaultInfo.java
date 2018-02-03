/** Generated Model - DO NOT CHANGE */
package de.metas.vertical.pharma.vendor.gateway.mvs3.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for MSV3_FaultInfo
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_MSV3_FaultInfo extends org.compiere.model.PO implements I_MSV3_FaultInfo, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1244118829L;

    /** Standard Constructor */
    public X_MSV3_FaultInfo (Properties ctx, int MSV3_FaultInfo_ID, String trxName)
    {
      super (ctx, MSV3_FaultInfo_ID, trxName);
      /** if (MSV3_FaultInfo_ID == 0)
        {
			setMSV3_EndanwenderFehlertext (null);
			setMSV3_ErrorCode (null);
			setMSV3_FaultInfo_ID (0);
        } */
    }

    /** Load Constructor */
    public X_MSV3_FaultInfo (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set EndanwenderFehlertext.
		@param MSV3_EndanwenderFehlertext EndanwenderFehlertext	  */
	@Override
	public void setMSV3_EndanwenderFehlertext (java.lang.String MSV3_EndanwenderFehlertext)
	{
		set_Value (COLUMNNAME_MSV3_EndanwenderFehlertext, MSV3_EndanwenderFehlertext);
	}

	/** Get EndanwenderFehlertext.
		@return EndanwenderFehlertext	  */
	@Override
	public java.lang.String getMSV3_EndanwenderFehlertext () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_EndanwenderFehlertext);
	}

	/** Set ErrorCode.
		@param MSV3_ErrorCode ErrorCode	  */
	@Override
	public void setMSV3_ErrorCode (java.lang.String MSV3_ErrorCode)
	{
		set_Value (COLUMNNAME_MSV3_ErrorCode, MSV3_ErrorCode);
	}

	/** Get ErrorCode.
		@return ErrorCode	  */
	@Override
	public java.lang.String getMSV3_ErrorCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_ErrorCode);
	}

	/** Set MSV3_FaultInfo.
		@param MSV3_FaultInfo_ID MSV3_FaultInfo	  */
	@Override
	public void setMSV3_FaultInfo_ID (int MSV3_FaultInfo_ID)
	{
		if (MSV3_FaultInfo_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_MSV3_FaultInfo_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_MSV3_FaultInfo_ID, Integer.valueOf(MSV3_FaultInfo_ID));
	}

	/** Get MSV3_FaultInfo.
		@return MSV3_FaultInfo	  */
	@Override
	public int getMSV3_FaultInfo_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MSV3_FaultInfo_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * MSV3_FaultInfoType AD_Reference_ID=540827
	 * Reference name: MSV3_FaultInfoType
	 */
	public static final int MSV3_FAULTINFOTYPE_AD_Reference_ID=540827;
	/** AuthorizationFaultInfo = AuthorizationFaultInfo */
	public static final String MSV3_FAULTINFOTYPE_AuthorizationFaultInfo = "AuthorizationFaultInfo";
	/** DenialOfServiceFault = DenialOfServiceFault */
	public static final String MSV3_FAULTINFOTYPE_DenialOfServiceFault = "DenialOfServiceFault";
	/** LieferscheinOderBarcodeUnbekanntFaultInfo = LieferscheinOderBarcodeUnbekanntFaultInfo */
	public static final String MSV3_FAULTINFOTYPE_LieferscheinOderBarcodeUnbekanntFaultInfo = "LieferscheinOderBarcodeUnbekanntFaultInfo";
	/** ServerFaultInfo = ServerFaultInfo */
	public static final String MSV3_FAULTINFOTYPE_ServerFaultInfo = "ServerFaultInfo";
	/** ValidationFaultInfo = ValidationFaultInfo */
	public static final String MSV3_FAULTINFOTYPE_ValidationFaultInfo = "ValidationFaultInfo";
	/** Set FaultInfoType.
		@param MSV3_FaultInfoType FaultInfoType	  */
	@Override
	public void setMSV3_FaultInfoType (java.lang.String MSV3_FaultInfoType)
	{

		set_Value (COLUMNNAME_MSV3_FaultInfoType, MSV3_FaultInfoType);
	}

	/** Get FaultInfoType.
		@return FaultInfoType	  */
	@Override
	public java.lang.String getMSV3_FaultInfoType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_FaultInfoType);
	}

	/** Set TechnischerFehlertext.
		@param MSV3_TechnischerFehlertext TechnischerFehlertext	  */
	@Override
	public void setMSV3_TechnischerFehlertext (java.lang.String MSV3_TechnischerFehlertext)
	{
		set_Value (COLUMNNAME_MSV3_TechnischerFehlertext, MSV3_TechnischerFehlertext);
	}

	/** Get TechnischerFehlertext.
		@return TechnischerFehlertext	  */
	@Override
	public java.lang.String getMSV3_TechnischerFehlertext () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MSV3_TechnischerFehlertext);
	}
}