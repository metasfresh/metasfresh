// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_MigrationScript
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_MigrationScript extends org.compiere.model.PO implements I_AD_MigrationScript, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 133021957L;

    /** Standard Constructor */
    public X_AD_MigrationScript (final Properties ctx, final int AD_MigrationScript_ID, @Nullable final String trxName)
    {
      super (ctx, AD_MigrationScript_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_MigrationScript (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_MigrationScript_ID (final int AD_MigrationScript_ID)
	{
		if (AD_MigrationScript_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MigrationScript_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_MigrationScript_ID, AD_MigrationScript_ID);
	}

	@Override
	public int getAD_MigrationScript_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_MigrationScript_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDeveloperName (final @Nullable java.lang.String DeveloperName)
	{
		set_Value (COLUMNNAME_DeveloperName, DeveloperName);
	}

	@Override
	public java.lang.String getDeveloperName() 
	{
		return get_ValueAsString(COLUMNNAME_DeveloperName);
	}

	@Override
	public void setFileName (final java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	@Override
	public java.lang.String getFileName() 
	{
		return get_ValueAsString(COLUMNNAME_FileName);
	}

	@Override
	public void setIsApply (final boolean IsApply)
	{
		set_Value (COLUMNNAME_IsApply, IsApply);
	}

	@Override
	public boolean isApply() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApply);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setProjectName (final java.lang.String ProjectName)
	{
		set_Value (COLUMNNAME_ProjectName, ProjectName);
	}

	@Override
	public java.lang.String getProjectName() 
	{
		return get_ValueAsString(COLUMNNAME_ProjectName);
	}

	@Override
	public void setReference (final @Nullable java.lang.String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	@Override
	public java.lang.String getReference() 
	{
		return get_ValueAsString(COLUMNNAME_Reference);
	}

	@Override
	public void setReleaseNo (final java.lang.String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	@Override
	public java.lang.String getReleaseNo() 
	{
		return get_ValueAsString(COLUMNNAME_ReleaseNo);
	}

	@Override
	public void setScript (final @Nullable byte[] Script)
	{
		set_ValueNoCheck (COLUMNNAME_Script, Script);
	}

	@Override
	public byte[] getScript() 
	{
		return (byte[])get_Value(COLUMNNAME_Script);
	}

	@Override
	public void setScriptRoll (final @Nullable java.lang.String ScriptRoll)
	{
		set_Value (COLUMNNAME_ScriptRoll, ScriptRoll);
	}

	@Override
	public java.lang.String getScriptRoll() 
	{
		return get_ValueAsString(COLUMNNAME_ScriptRoll);
	}

	/** 
	 * Status AD_Reference_ID=53239
	 * Reference name: MigrationScriptStatus
	 */
	public static final int STATUS_AD_Reference_ID=53239;
	/** In Verarbeitung = IP */
	public static final String STATUS_InVerarbeitung = "IP";
	/** Fertiggestellt = CO */
	public static final String STATUS_Fertiggestellt = "CO";
	/** Fehler = ER */
	public static final String STATUS_Fehler = "ER";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_ValueNoCheck (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}
}