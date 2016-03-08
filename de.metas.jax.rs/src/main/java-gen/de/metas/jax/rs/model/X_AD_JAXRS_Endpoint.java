/** Generated Model - DO NOT CHANGE */
package de.metas.jax.rs.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_JAXRS_Endpoint
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_JAXRS_Endpoint extends org.compiere.model.PO implements I_AD_JAXRS_Endpoint, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1005669506L;

    /** Standard Constructor */
    public X_AD_JAXRS_Endpoint (Properties ctx, int AD_JAXRS_Endpoint_ID, String trxName)
    {
      super (ctx, AD_JAXRS_Endpoint_ID, trxName);
      /** if (AD_JAXRS_Endpoint_ID == 0)
        {
			setAD_JavaClass_ID (0);
			setAD_JAXRS_Endpoint_ID (0);
			setEndpointType (null);
			setEntityType (null);
// de.metas.jax.rs
			setIsEndpointActive (false);
// N
        } */
    }

    /** Load Constructor */
    public X_AD_JAXRS_Endpoint (Properties ctx, ResultSet rs, String trxName)
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

	@Override
	public de.metas.javaclasses.model.I_AD_JavaClass getAD_JavaClass() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_JavaClass_ID, de.metas.javaclasses.model.I_AD_JavaClass.class);
	}

	@Override
	public void setAD_JavaClass(de.metas.javaclasses.model.I_AD_JavaClass AD_JavaClass)
	{
		set_ValueFromPO(COLUMNNAME_AD_JavaClass_ID, de.metas.javaclasses.model.I_AD_JavaClass.class, AD_JavaClass);
	}

	/** Set AD_JavaClass.
		@param AD_JavaClass_ID AD_JavaClass	  */
	@Override
	public void setAD_JavaClass_ID (int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, Integer.valueOf(AD_JavaClass_ID));
	}

	/** Get AD_JavaClass.
		@return AD_JavaClass	  */
	@Override
	public int getAD_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set JAX-RS-Endpoint.
		@param AD_JAXRS_Endpoint_ID JAX-RS-Endpoint	  */
	@Override
	public void setAD_JAXRS_Endpoint_ID (int AD_JAXRS_Endpoint_ID)
	{
		if (AD_JAXRS_Endpoint_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_JAXRS_Endpoint_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_JAXRS_Endpoint_ID, Integer.valueOf(AD_JAXRS_Endpoint_ID));
	}

	/** Get JAX-RS-Endpoint.
		@return JAX-RS-Endpoint	  */
	@Override
	public int getAD_JAXRS_Endpoint_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JAXRS_Endpoint_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * EndpointType AD_Reference_ID=540646
	 * Reference name: EndpointType
	 */
	public static final int ENDPOINTTYPE_AD_Reference_ID=540646;
	/** client = C */
	public static final String ENDPOINTTYPE_Client = "C";
	/** server = S */
	public static final String ENDPOINTTYPE_Server = "S";
	/** Set Endpoint type.
		@param EndpointType Endpoint type	  */
	@Override
	public void setEndpointType (java.lang.String EndpointType)
	{

		set_Value (COLUMNNAME_EndpointType, EndpointType);
	}

	/** Get Endpoint type.
		@return Endpoint type	  */
	@Override
	public java.lang.String getEndpointType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EndpointType);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Endpoint active.
		@param IsEndpointActive Endpoint active	  */
	@Override
	public void setIsEndpointActive (boolean IsEndpointActive)
	{
		set_Value (COLUMNNAME_IsEndpointActive, Boolean.valueOf(IsEndpointActive));
	}

	/** Get Endpoint active.
		@return Endpoint active	  */
	@Override
	public boolean isEndpointActive () 
	{
		Object oo = get_Value(COLUMNNAME_IsEndpointActive);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}