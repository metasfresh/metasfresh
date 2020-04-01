/** Generated Model - DO NOT CHANGE */
package de.metas.serviceprovider.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for S_ExternalProjectReference
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_S_ExternalProjectReference extends org.compiere.model.PO implements I_S_ExternalProjectReference, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1251623490L;

    /** Standard Constructor */
    public X_S_ExternalProjectReference (Properties ctx, int S_ExternalProjectReference_ID, String trxName)
    {
      super (ctx, S_ExternalProjectReference_ID, trxName);
      /** if (S_ExternalProjectReference_ID == 0)
        {
			setExternalProjectOwner (null);
			setExternalReference (null);
			setExternalSystem (null);
			setProjectType (null);
			setS_ExternalProjectReference_ID (0);
        } */
    }

    /** Load Constructor */
    public X_S_ExternalProjectReference (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	@Override
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	@Override
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ExternalProjectOwner.
		@param ExternalProjectOwner ExternalProjectOwner	  */
	@Override
	public void setExternalProjectOwner (java.lang.String ExternalProjectOwner)
	{
		set_Value (COLUMNNAME_ExternalProjectOwner, ExternalProjectOwner);
	}

	/** Get ExternalProjectOwner.
		@return ExternalProjectOwner	  */
	@Override
	public java.lang.String getExternalProjectOwner () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalProjectOwner);
	}

	/** Set ExternalReference.
		@param ExternalReference ExternalReference	  */
	@Override
	public void setExternalReference (java.lang.String ExternalReference)
	{
		set_Value (COLUMNNAME_ExternalReference, ExternalReference);
	}

	/** Get ExternalReference.
		@return ExternalReference	  */
	@Override
	public java.lang.String getExternalReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalReference);
	}

	/** Set ExternalReferenceURL.
		@param ExternalReferenceURL ExternalReferenceURL	  */
	@Override
	public void setExternalReferenceURL (java.lang.String ExternalReferenceURL)
	{
		set_Value (COLUMNNAME_ExternalReferenceURL, ExternalReferenceURL);
	}

	/** Get ExternalReferenceURL.
		@return ExternalReferenceURL	  */
	@Override
	public java.lang.String getExternalReferenceURL () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalReferenceURL);
	}

	/** 
	 * ExternalSystem AD_Reference_ID=541117
	 * Reference name: ExternalSystem
	 */
	public static final int EXTERNALSYSTEM_AD_Reference_ID=541117;
	/** Github = Github */
	public static final String EXTERNALSYSTEM_Github = "Github";
	/** Set External system.
		@param ExternalSystem 
		Name of an external system (e.g. Github )
	  */
	@Override
	public void setExternalSystem (java.lang.String ExternalSystem)
	{

		set_Value (COLUMNNAME_ExternalSystem, ExternalSystem);
	}

	/** Get External system.
		@return Name of an external system (e.g. Github )
	  */
	@Override
	public java.lang.String getExternalSystem () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExternalSystem);
	}

	/** 
	 * ProjectType AD_Reference_ID=541118
	 * Reference name: ExternalProjectType
	 */
	public static final int PROJECTTYPE_AD_Reference_ID=541118;
	/** Budget = Budget */
	public static final String PROJECTTYPE_Budget = "Budget";
	/** Development = Effort */
	public static final String PROJECTTYPE_Development = "Effort";
	/** Set Project type.
		@param ProjectType 
		Specifies the type of a project. ( e.g. Budget, Development )
	  */
	@Override
	public void setProjectType (java.lang.String ProjectType)
	{

		set_Value (COLUMNNAME_ProjectType, ProjectType);
	}

	/** Get Project type.
		@return Specifies the type of a project. ( e.g. Budget, Development )
	  */
	@Override
	public java.lang.String getProjectType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProjectType);
	}

	/** Set S_ExternalProjectReference_ID.
		@param S_ExternalProjectReference_ID S_ExternalProjectReference_ID	  */
	@Override
	public void setS_ExternalProjectReference_ID (int S_ExternalProjectReference_ID)
	{
		if (S_ExternalProjectReference_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_S_ExternalProjectReference_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_S_ExternalProjectReference_ID, Integer.valueOf(S_ExternalProjectReference_ID));
	}

	/** Get S_ExternalProjectReference_ID.
		@return S_ExternalProjectReference_ID	  */
	@Override
	public int getS_ExternalProjectReference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_S_ExternalProjectReference_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}