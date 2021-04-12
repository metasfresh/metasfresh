// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_AlbertaPackagingUnit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_AlbertaPackagingUnit extends org.compiere.model.PO implements I_M_Product_AlbertaPackagingUnit, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = -1129871042L;

    /** Standard Constructor */
    public X_M_Product_AlbertaPackagingUnit (final Properties ctx, final int M_Product_AlbertaPackagingUnit_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_AlbertaPackagingUnit_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_AlbertaPackagingUnit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	/** 
	 * ArticleUnit AD_Reference_ID=541280
	 * Reference name: ArticleUnit
	 */
	public static final int ARTICLEUNIT_AD_Reference_ID=541280;
	/** PCE = Stk */
	public static final String ARTICLEUNIT_PCE = "Stk";
	/** BOX = Ktn */
	public static final String ARTICLEUNIT_BOX = "Ktn";
	@Override
	public void setArticleUnit (final String ArticleUnit)
	{
		set_Value (COLUMNNAME_ArticleUnit, ArticleUnit);
	}

	@Override
	public String getArticleUnit()
	{
		return get_ValueAsString(COLUMNNAME_ArticleUnit);
	}

	@Override
	public void setM_Product_AlbertaPackagingUnit_ID (final int M_Product_AlbertaPackagingUnit_ID)
	{
		if (M_Product_AlbertaPackagingUnit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaPackagingUnit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaPackagingUnit_ID, M_Product_AlbertaPackagingUnit_ID);
	}

	@Override
	public int getM_Product_AlbertaPackagingUnit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_AlbertaPackagingUnit_ID);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}