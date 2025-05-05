// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_UserDefaults_Attribute3
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_UserDefaults_Attribute3 extends org.compiere.model.PO implements I_AD_UserDefaults_Attribute3, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1094867997L;

    /** Standard Constructor */
    public X_AD_UserDefaults_Attribute3 (final Properties ctx, final int AD_UserDefaults_Attribute3_ID, @Nullable final String trxName)
    {
      super (ctx, AD_UserDefaults_Attribute3_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_UserDefaults_Attribute3 (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_UserDefaults_Attribute3_ID (final int AD_UserDefaults_Attribute3_ID)
	{
		if (AD_UserDefaults_Attribute3_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_UserDefaults_Attribute3_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_UserDefaults_Attribute3_ID, AD_UserDefaults_Attribute3_ID);
	}

	@Override
	public int getAD_UserDefaults_Attribute3_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_UserDefaults_Attribute3_ID);
	}

	@Override
	public void setAD_User_ID (final int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, AD_User_ID);
	}

	@Override
	public int getAD_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_User_ID);
	}

	/** 
	 * Attributes3 AD_Reference_ID=541334
	 * Reference name: Attributes3
	 */
	public static final int ATTRIBUTES3_AD_Reference_ID=541334;
	/** Zürich Versicherung = Zürich Versicherung */
	public static final String ATTRIBUTES3_ZuerichVersicherung = "Zürich Versicherung";
	/** Sonstige Vergünstigungen = Sonstige Vergünstigungen */
	public static final String ATTRIBUTES3_SonstigeVerguenstigungen = "Sonstige Vergünstigungen";
	/** Bauherren = Bauherren */
	public static final String ATTRIBUTES3_Bauherren = "Bauherren";
	/** Handänderungen = Handaenderungen */
	public static final String ATTRIBUTES3_Handaenderungen = "Handaenderungen";
	@Override
	public void setAttributes3 (final java.lang.String Attributes3)
	{
		set_Value (COLUMNNAME_Attributes3, Attributes3);
	}

	@Override
	public java.lang.String getAttributes3() 
	{
		return get_ValueAsString(COLUMNNAME_Attributes3);
	}
}