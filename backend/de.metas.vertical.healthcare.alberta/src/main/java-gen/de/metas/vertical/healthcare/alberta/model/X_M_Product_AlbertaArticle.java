// Generated Model - DO NOT CHANGE
package de.metas.vertical.healthcare.alberta.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Product_AlbertaArticle
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Product_AlbertaArticle extends org.compiere.model.PO implements I_M_Product_AlbertaArticle, org.compiere.model.I_Persistent
{

	private static final long serialVersionUID = 966062343L;

    /** Standard Constructor */
    public X_M_Product_AlbertaArticle (final Properties ctx, final int M_Product_AlbertaArticle_ID, @Nullable final String trxName)
    {
      super (ctx, M_Product_AlbertaArticle_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Product_AlbertaArticle (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAdditionalDescription (final @Nullable String AdditionalDescription)
	{
		set_Value (COLUMNNAME_AdditionalDescription, AdditionalDescription);
	}

	@Override
	public String getAdditionalDescription()
	{
		return get_ValueAsString(COLUMNNAME_AdditionalDescription);
	}

	/** 
	 * ArticleInventoryType AD_Reference_ID=541276
	 * Reference name: ArticleInventoryType
	 */
	public static final int ARTICLEINVENTORYTYPE_AD_Reference_ID=541276;
	/** UnstockedArticle = 1 */
	public static final String ARTICLEINVENTORYTYPE_UnstockedArticle = "1";
	/** StockedArticle = 2 */
	public static final String ARTICLEINVENTORYTYPE_StockedArticle = "2";
	/** Unknown = 3 */
	public static final String ARTICLEINVENTORYTYPE_Unknown = "3";
	@Override
	public void setArticleInventoryType (final String ArticleInventoryType)
	{
		set_Value (COLUMNNAME_ArticleInventoryType, ArticleInventoryType);
	}

	@Override
	public String getArticleInventoryType()
	{
		return get_ValueAsString(COLUMNNAME_ArticleInventoryType);
	}

	@Override
	public void setArticleStars (final BigDecimal ArticleStars)
	{
		set_Value (COLUMNNAME_ArticleStars, ArticleStars);
	}

	@Override
	public BigDecimal getArticleStars() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ArticleStars);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ArticleStatus AD_Reference_ID=541277
	 * Reference name: ArticleStatus
	 */
	public static final int ARTICLESTATUS_AD_Reference_ID=541277;
	/** Unknown = 0 */
	public static final String ARTICLESTATUS_Unknown = "0";
	/** Available = 1 */
	public static final String ARTICLESTATUS_Available = "1";
	/** Blocked = 2 */
	public static final String ARTICLESTATUS_Blocked = "2";
	/** TemporarilyUnavailable = 3 */
	public static final String ARTICLESTATUS_TemporarilyUnavailable = "3";
	/** FinallyUnavailable = 4 */
	public static final String ARTICLESTATUS_FinallyUnavailable = "4";
	@Override
	public void setArticleStatus (final String ArticleStatus)
	{
		set_Value (COLUMNNAME_ArticleStatus, ArticleStatus);
	}

	@Override
	public String getArticleStatus()
	{
		return get_ValueAsString(COLUMNNAME_ArticleStatus);
	}

	/** 
	 * AssortmentType AD_Reference_ID=541278
	 * Reference name: AssortmentType
	 */
	public static final int ASSORTMENTTYPE_AD_Reference_ID=541278;
	/** Unknown = 0 */
	public static final String ASSORTMENTTYPE_Unknown = "0";
	/** FocusArticle = 1 */
	public static final String ASSORTMENTTYPE_FocusArticle = "1";
	/** StandardArticle = 2 */
	public static final String ASSORTMENTTYPE_StandardArticle = "2";
	@Override
	public void setAssortmentType (final String AssortmentType)
	{
		set_Value (COLUMNNAME_AssortmentType, AssortmentType);
	}

	@Override
	public String getAssortmentType()
	{
		return get_ValueAsString(COLUMNNAME_AssortmentType);
	}

	@Override
	public void setM_Product_AlbertaArticle_ID (final int M_Product_AlbertaArticle_ID)
	{
		if (M_Product_AlbertaArticle_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaArticle_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_AlbertaArticle_ID, M_Product_AlbertaArticle_ID);
	}

	@Override
	public int getM_Product_AlbertaArticle_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_AlbertaArticle_ID);
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
	public void setMedicalAidPositionNumber (final @Nullable String MedicalAidPositionNumber)
	{
		set_Value (COLUMNNAME_MedicalAidPositionNumber, MedicalAidPositionNumber);
	}

	@Override
	public String getMedicalAidPositionNumber()
	{
		return get_ValueAsString(COLUMNNAME_MedicalAidPositionNumber);
	}

	/** 
	 * PurchaseRating AD_Reference_ID=541279
	 * Reference name: PurchaseRating
	 */
	public static final int PURCHASERATING_AD_Reference_ID=541279;
	/** A = A */
	public static final String PURCHASERATING_A = "A";
	/** B = B */
	public static final String PURCHASERATING_B = "B";
	/** C = C */
	public static final String PURCHASERATING_C = "C";
	/** D = D */
	public static final String PURCHASERATING_D = "D";
	/** E = E */
	public static final String PURCHASERATING_E = "E";
	/** F = F */
	public static final String PURCHASERATING_F = "F";
	/** G = G */
	public static final String PURCHASERATING_G = "G";
	@Override
	public void setPurchaseRating (final @Nullable String PurchaseRating)
	{
		set_Value (COLUMNNAME_PurchaseRating, PurchaseRating);
	}

	@Override
	public String getPurchaseRating()
	{
		return get_ValueAsString(COLUMNNAME_PurchaseRating);
	}

	@Override
	public void setSize (final @Nullable String Size)
	{
		set_Value (COLUMNNAME_Size, Size);
	}

	@Override
	public String getSize()
	{
		return get_ValueAsString(COLUMNNAME_Size);
	}
}