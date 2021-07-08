// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_WF_NextCondition
 *  @author metasfresh (generated) 
 */
public class X_AD_WF_NextCondition extends org.compiere.model.PO implements I_AD_WF_NextCondition, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 559080587L;

    /** Standard Constructor */
    public X_AD_WF_NextCondition (final Properties ctx, final int AD_WF_NextCondition_ID, @Nullable final String trxName)
    {
      super (ctx, AD_WF_NextCondition_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_WF_NextCondition (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(final org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	@Override
	public void setAD_Column_ID (final int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, AD_Column_ID);
	}

	@Override
	public int getAD_Column_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Column_ID);
	}

	@Override
	public void setAD_WF_NextCondition_ID (final int AD_WF_NextCondition_ID)
	{
		if (AD_WF_NextCondition_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NextCondition_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NextCondition_ID, AD_WF_NextCondition_ID);
	}

	@Override
	public int getAD_WF_NextCondition_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_NextCondition_ID);
	}

	@Override
	public void setAD_WF_NodeNext_ID (final int AD_WF_NodeNext_ID)
	{
		if (AD_WF_NodeNext_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NodeNext_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NodeNext_ID, AD_WF_NodeNext_ID);
	}

	@Override
	public int getAD_WF_NodeNext_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_WF_NodeNext_ID);
	}

	/** 
	 * AndOr AD_Reference_ID=204
	 * Reference name: AD_Find AndOr
	 */
	public static final int ANDOR_AD_Reference_ID=204;
	/** And = A */
	public static final String ANDOR_And = "A";
	/** OR = O */
	public static final String ANDOR_OR = "O";
	@Override
	public void setAndOr (final java.lang.String AndOr)
	{
		set_Value (COLUMNNAME_AndOr, AndOr);
	}

	@Override
	public java.lang.String getAndOr() 
	{
		return get_ValueAsString(COLUMNNAME_AndOr);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	/** 
	 * Operation AD_Reference_ID=205
	 * Reference name: AD_Find Operation
	 */
	public static final int OPERATION_AD_Reference_ID=205;
	/**  = = == */
	public static final String OPERATION_Eq = "==";
	/** >= = >= */
	public static final String OPERATION_GtEq = ">=";
	/** > = >> */
	public static final String OPERATION_Gt = ">>";
	/** < = << */
	public static final String OPERATION_Le = "<<";
	/**  ~ = ~~ */
	public static final String OPERATION_Like = "~~";
	/** <= = <= */
	public static final String OPERATION_LeEq = "<=";
	/** |<x>| = AB */
	public static final String OPERATION_X = "AB";
	/** sql = SQ */
	public static final String OPERATION_Sql = "SQ";
	/** != = != */
	public static final String OPERATION_NotEq = "!=";
	@Override
	public void setOperation (final java.lang.String Operation)
	{
		set_Value (COLUMNNAME_Operation, Operation);
	}

	@Override
	public java.lang.String getOperation() 
	{
		return get_ValueAsString(COLUMNNAME_Operation);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setValue2 (final java.lang.String Value2)
	{
		set_Value (COLUMNNAME_Value2, Value2);
	}

	@Override
	public java.lang.String getValue2() 
	{
		return get_ValueAsString(COLUMNNAME_Value2);
	}
}