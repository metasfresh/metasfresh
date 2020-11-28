/** Generated Model - DO NOT CHANGE */
package de.metas.dao.selection.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for T_Query_Selection_Pagination
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_T_Query_Selection_Pagination extends org.compiere.model.PO implements I_T_Query_Selection_Pagination, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1131512314L;

    /** Standard Constructor */
    public X_T_Query_Selection_Pagination (Properties ctx, int T_Query_Selection_Pagination_ID, String trxName)
    {
      super (ctx, T_Query_Selection_Pagination_ID, trxName);
      /** if (T_Query_Selection_Pagination_ID == 0)
        {
			setPage_Identifier (null);
			setPage_Offset (0);
			setPage_Size (0);
			setResult_Time (new Timestamp( System.currentTimeMillis() ));
			setTotal_Size (0);
			setUUID (null);
        } */
    }

    /** Load Constructor */
    public X_T_Query_Selection_Pagination (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Page_Identifier.
		@param Page_Identifier Page_Identifier	  */
	@Override
	public void setPage_Identifier (java.lang.String Page_Identifier)
	{
		set_ValueNoCheck (COLUMNNAME_Page_Identifier, Page_Identifier);
	}

	/** Get Page_Identifier.
		@return Page_Identifier	  */
	@Override
	public java.lang.String getPage_Identifier () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Page_Identifier);
	}

	/** Set Page_Offset.
		@param Page_Offset Page_Offset	  */
	@Override
	public void setPage_Offset (int Page_Offset)
	{
		set_ValueNoCheck (COLUMNNAME_Page_Offset, Integer.valueOf(Page_Offset));
	}

	/** Get Page_Offset.
		@return Page_Offset	  */
	@Override
	public int getPage_Offset () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Page_Offset);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Page_Size.
		@param Page_Size Page_Size	  */
	@Override
	public void setPage_Size (int Page_Size)
	{
		set_ValueNoCheck (COLUMNNAME_Page_Size, Integer.valueOf(Page_Size));
	}

	/** Get Page_Size.
		@return Page_Size	  */
	@Override
	public int getPage_Size () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Page_Size);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Result_Time.
		@param Result_Time Result_Time	  */
	@Override
	public void setResult_Time (java.sql.Timestamp Result_Time)
	{
		set_ValueNoCheck (COLUMNNAME_Result_Time, Result_Time);
	}

	/** Get Result_Time.
		@return Result_Time	  */
	@Override
	public java.sql.Timestamp getResult_Time () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_Result_Time);
	}

	/** Set Total_Size.
		@param Total_Size Total_Size	  */
	@Override
	public void setTotal_Size (int Total_Size)
	{
		set_ValueNoCheck (COLUMNNAME_Total_Size, Integer.valueOf(Total_Size));
	}

	/** Get Total_Size.
		@return Total_Size	  */
	@Override
	public int getTotal_Size () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Total_Size);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set UUID.
		@param UUID UUID	  */
	@Override
	public void setUUID (java.lang.String UUID)
	{
		set_ValueNoCheck (COLUMNNAME_UUID, UUID);
	}

	/** Get UUID.
		@return UUID	  */
	@Override
	public java.lang.String getUUID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UUID);
	}
}