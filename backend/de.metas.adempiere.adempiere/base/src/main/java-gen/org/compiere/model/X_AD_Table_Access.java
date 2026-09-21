// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Table_Access
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Table_Access extends org.compiere.model.PO implements I_AD_Table_Access, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 794828551L;

    /** Standard Constructor */
    public X_AD_Table_Access (final Properties ctx, final int AD_Table_Access_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Table_Access_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Table_Access (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Role getAD_Role()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(final org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	@Override
	public void setAD_Role_ID (final int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Role_ID, AD_Role_ID);
	}

	@Override
	public int getAD_Role_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Role_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	/** 
	 * IsCanCreateNewRecords AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISCANCREATENEWRECORDS_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISCANCREATENEWRECORDS_Yes = "Y";
	/** No = N */
	public static final String ISCANCREATENEWRECORDS_No = "N";
	@Override
	public void setIsCanCreateNewRecords (final @Nullable java.lang.String IsCanCreateNewRecords)
	{
		set_Value (COLUMNNAME_IsCanCreateNewRecords, IsCanCreateNewRecords);
	}

	@Override
	public java.lang.String getIsCanCreateNewRecords() 
	{
		return get_ValueAsString(COLUMNNAME_IsCanCreateNewRecords);
	}

	/** 
	 * IsCanExport AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISCANEXPORT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISCANEXPORT_Yes = "Y";
	/** No = N */
	public static final String ISCANEXPORT_No = "N";
	@Override
	public void setIsCanExport (final @Nullable java.lang.String IsCanExport)
	{
		set_Value (COLUMNNAME_IsCanExport, IsCanExport);
	}

	@Override
	public java.lang.String getIsCanExport() 
	{
		return get_ValueAsString(COLUMNNAME_IsCanExport);
	}

	/** 
	 * IsCanReport AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISCANREPORT_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISCANREPORT_Yes = "Y";
	/** No = N */
	public static final String ISCANREPORT_No = "N";
	@Override
	public void setIsCanReport (final @Nullable java.lang.String IsCanReport)
	{
		set_Value (COLUMNNAME_IsCanReport, IsCanReport);
	}

	@Override
	public java.lang.String getIsCanReport() 
	{
		return get_ValueAsString(COLUMNNAME_IsCanReport);
	}

	/** 
	 * IsExclude AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISEXCLUDE_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISEXCLUDE_Yes = "Y";
	/** No = N */
	public static final String ISEXCLUDE_No = "N";
	@Override
	public void setIsExclude (final @Nullable java.lang.String IsExclude)
	{
		set_Value (COLUMNNAME_IsExclude, IsExclude);
	}

	@Override
	public java.lang.String getIsExclude() 
	{
		return get_ValueAsString(COLUMNNAME_IsExclude);
	}

	/** 
	 * IsReadOnly AD_Reference_ID=319
	 * Reference name: _YesNo
	 */
	public static final int ISREADONLY_AD_Reference_ID=319;
	/** Yes = Y */
	public static final String ISREADONLY_Yes = "Y";
	/** No = N */
	public static final String ISREADONLY_No = "N";
	@Override
	public void setIsReadOnly (final @Nullable java.lang.String IsReadOnly)
	{
		set_Value (COLUMNNAME_IsReadOnly, IsReadOnly);
	}

	@Override
	public java.lang.String getIsReadOnly() 
	{
		return get_ValueAsString(COLUMNNAME_IsReadOnly);
	}
}