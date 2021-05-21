// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Generated Model for C_Tax
 *
 * @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public class X_C_Tax extends org.compiere.model.PO implements I_C_Tax, org.compiere.model.I_Persistent
{

	/**
	 * SOPOType AD_Reference_ID=287
	 * Reference name: C_Tax SPPOType
	 */
	public static final int SOPOTYPE_AD_Reference_ID = 287;
	/**
	 * Sales Tax = S
	 */
	public static final String SOPOTYPE_SalesTax = "S";
	/**
	 * Both  = B
	 */
	public static final String SOPOTYPE_Both = "B";
	/**
	 * Purchase Tax = P
	 */
	public static final String SOPOTYPE_PurchaseTax = "P";
	/**
	 * Inland = DOMESTIC
	 */
	public static final String TYPEOFDESTCOUNTRY_Domestic = "DOMESTIC";
	/**
	 * EU-Ausland = WITHIN_COUNTRY_AREA
	 */
	public static final String TYPEOFDESTCOUNTRY_EU_foreign = "WITHIN_COUNTRY_AREA";
	/**
	 * Nicht-EU-Ausland = OUTSIDE_COUNTRY_AREA
	 */
	public static final String TYPEOFDESTCOUNTRY_Non_EU_country = "OUTSIDE_COUNTRY_AREA";
	private static final long serialVersionUID = -1151858783L;

	/**
	 * TypeOfDestCountry AD_Reference_ID=541323
	 * Reference name: TypeDestCountry
	 */
	public static final int TYPEOFDESTCOUNTRY_AD_Reference_ID = 541323;

	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Rule getAD_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Rule_ID, org.compiere.model.I_AD_Rule.class);
	}

	@Override
	public void setAD_Rule(final org.compiere.model.I_AD_Rule AD_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Rule_ID, org.compiere.model.I_AD_Rule.class, AD_Rule);
	}

	@Override
	public org.compiere.model.I_C_Country getC_Country()
	{
		return get_ValueAsPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setC_Country(final org.compiere.model.I_C_Country C_Country)
	{
		set_ValueFromPO(COLUMNNAME_C_Country_ID, org.compiere.model.I_C_Country.class, C_Country);
	}

	@Override
	public org.compiere.model.I_C_Region getC_Region()
	{
		return get_ValueAsPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setC_Region(final org.compiere.model.I_C_Region C_Region)
	{
		set_ValueFromPO(COLUMNNAME_C_Region_ID, org.compiere.model.I_C_Region.class, C_Region);
	}

	/**
	 * Standard Constructor
	 */
	public X_C_Tax(final Properties ctx, final int C_Tax_ID, @Nullable final String trxName)
	{
		super(ctx, C_Tax_ID, trxName);
	}

	/**
	 * Load Constructor
	 */
	public X_C_Tax(final Properties ctx, final ResultSet rs, @Nullable final String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	public int getAD_BoilerPlate_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_BoilerPlate_ID);
	}

	@Override
	public void setAD_BoilerPlate_ID(final int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1)
			set_Value(COLUMNNAME_AD_BoilerPlate_ID, null);
		else
			set_Value(COLUMNNAME_AD_BoilerPlate_ID, AD_BoilerPlate_ID);
	}

	@Override
	public int getAD_Rule_ID()
	{
		return get_ValueAsInt(COLUMNNAME_AD_Rule_ID);
	}

	@Override
	public void setAD_Rule_ID(final int AD_Rule_ID)
	{
		if (AD_Rule_ID < 1)
			set_Value(COLUMNNAME_AD_Rule_ID, null);
		else
			set_Value(COLUMNNAME_AD_Rule_ID, AD_Rule_ID);
	}

	@Override
	public int getC_Country_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Country_ID);
	}

	@Override
	public void setC_Country_ID(final int C_Country_ID)
	{
		if (C_Country_ID < 1)
			set_Value(COLUMNNAME_C_Country_ID, null);
		else
			set_Value(COLUMNNAME_C_Country_ID, C_Country_ID);
	}

	@Override
	public int getC_Region_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Region_ID);
	}

	@Override
	public void setC_Region_ID(final int C_Region_ID)
	{
		if (C_Region_ID < 1)
			set_Value(COLUMNNAME_C_Region_ID, null);
		else
			set_Value(COLUMNNAME_C_Region_ID, C_Region_ID);
	}

	@Override
	public int getC_TaxCategory_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_TaxCategory_ID);
	}

	@Override
	public void setC_TaxCategory_ID(final int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1)
			set_Value(COLUMNNAME_C_TaxCategory_ID, null);
		else
			set_Value(COLUMNNAME_C_TaxCategory_ID, C_TaxCategory_ID);
	}

	@Override
	public int getC_Tax_ID()
	{
		return get_ValueAsInt(COLUMNNAME_C_Tax_ID);
	}

	@Override
	public void setC_Tax_ID(final int C_Tax_ID)
	{
		if (C_Tax_ID < 1)
			set_ValueNoCheck(COLUMNNAME_C_Tax_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_C_Tax_ID, C_Tax_ID);
	}

	@Override
	public java.lang.String getDescription()
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDescription(final @Nullable java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDuplicateTax()
	{
		return get_ValueAsString(COLUMNNAME_DuplicateTax);
	}

	@Override
	public void setDuplicateTax(final @Nullable java.lang.String DuplicateTax)
	{
		set_Value(COLUMNNAME_DuplicateTax, DuplicateTax);
	}

	@Override
	public void setIsDefault(final boolean IsDefault)
	{
		set_Value(COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsDocumentLevel(final boolean IsDocumentLevel)
	{
		set_Value(COLUMNNAME_IsDocumentLevel, IsDocumentLevel);
	}

	@Override
	public boolean isDocumentLevel()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDocumentLevel);
	}

	@Override
	public void setIsFiscalRepresentation(final boolean IsFiscalRepresentation)
	{
		set_Value(COLUMNNAME_IsFiscalRepresentation, IsFiscalRepresentation);
	}

	@Override
	public boolean isFiscalRepresentation()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFiscalRepresentation);
	}

	@Override
	public void setIsSalesTax(final boolean IsSalesTax)
	{
		set_Value(COLUMNNAME_IsSalesTax, IsSalesTax);
	}

	@Override
	public boolean isSalesTax()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSalesTax);
	}

	@Override
	public void setIsSmallbusiness(final boolean IsSmallbusiness)
	{
		set_Value(COLUMNNAME_IsSmallbusiness, IsSmallbusiness);
	}

	@Override
	public boolean isSmallbusiness()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSmallbusiness);
	}

	@Override
	public void setIsSummary(final boolean IsSummary)
	{
		set_Value(COLUMNNAME_IsSummary, IsSummary);
	}

	@Override
	public boolean isSummary()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSummary);
	}

	@Override
	public void setIsTaxExempt(final boolean IsTaxExempt)
	{
		set_Value(COLUMNNAME_IsTaxExempt, IsTaxExempt);
	}

	@Override
	public boolean isTaxExempt()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsTaxExempt);
	}

	@Override
	public void setIsWholeTax(final boolean IsWholeTax)
	{
		set_Value(COLUMNNAME_IsWholeTax, IsWholeTax);
	}

	@Override
	public boolean isWholeTax()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWholeTax);
	}

	@Override
	public java.lang.String getName()
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setName(final java.lang.String Name)
	{
		set_Value(COLUMNNAME_Name, Name);
	}

	@Override
	public int getParent_Tax_ID()
	{
		return get_ValueAsInt(COLUMNNAME_Parent_Tax_ID);
	}

	@Override
	public void setParent_Tax_ID(final int Parent_Tax_ID)
	{
		if (Parent_Tax_ID < 1)
			set_Value(COLUMNNAME_Parent_Tax_ID, null);
		else
			set_Value(COLUMNNAME_Parent_Tax_ID, Parent_Tax_ID);
	}

	@Override
	public BigDecimal getRate()
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Rate);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setRate(final BigDecimal Rate)
	{
		set_Value(COLUMNNAME_Rate, Rate);
	}

	@Override
	public boolean isRequiresTaxCertificate()
	{
		return get_ValueAsBoolean(COLUMNNAME_RequiresTaxCertificate);
	}

	@Override
	public void setRequiresTaxCertificate(final boolean RequiresTaxCertificate)
	{
		set_Value(COLUMNNAME_RequiresTaxCertificate, RequiresTaxCertificate);
	}

	@Override
	public org.compiere.model.I_C_Country getTo_Country()
	{
		return get_ValueAsPO(COLUMNNAME_To_Country_ID, org.compiere.model.I_C_Country.class);
	}

	@Override
	public void setTo_Country(final org.compiere.model.I_C_Country To_Country)
	{
		set_ValueFromPO(COLUMNNAME_To_Country_ID, org.compiere.model.I_C_Country.class, To_Country);
	}

	@Override
	public java.lang.String getSOPOType()
	{
		return get_ValueAsString(COLUMNNAME_SOPOType);
	}

	@Override
	public void setSOPOType(final java.lang.String SOPOType)
	{
		set_Value(COLUMNNAME_SOPOType, SOPOType);
	}

	@Override
	public org.compiere.model.I_C_Region getTo_Region()
	{
		return get_ValueAsPO(COLUMNNAME_To_Region_ID, org.compiere.model.I_C_Region.class);
	}

	@Override
	public void setTo_Region(final org.compiere.model.I_C_Region To_Region)
	{
		set_ValueFromPO(COLUMNNAME_To_Region_ID, org.compiere.model.I_C_Region.class, To_Region);
	}

	@Override
	public java.lang.String getTaxIndicator()
	{
		return get_ValueAsString(COLUMNNAME_TaxIndicator);
	}

	@Override
	public void setTaxIndicator(final @Nullable java.lang.String TaxIndicator)
	{
		set_Value(COLUMNNAME_TaxIndicator, TaxIndicator);
	}

	@Override
	public int getTo_Country_ID()
	{
		return get_ValueAsInt(COLUMNNAME_To_Country_ID);
	}

	@Override
	public void setTo_Country_ID(final int To_Country_ID)
	{
		if (To_Country_ID < 1)
			set_Value(COLUMNNAME_To_Country_ID, null);
		else
			set_Value(COLUMNNAME_To_Country_ID, To_Country_ID);
	}

	@Override
	public int getTo_Region_ID()
	{
		return get_ValueAsInt(COLUMNNAME_To_Region_ID);
	}

	@Override
	public void setTo_Region_ID(final int To_Region_ID)
	{
		if (To_Region_ID < 1)
			set_Value(COLUMNNAME_To_Region_ID, null);
		else
			set_Value(COLUMNNAME_To_Region_ID, To_Region_ID);
	}

	@Override
	public java.lang.String getTypeOfDestCountry()
	{
		return get_ValueAsString(COLUMNNAME_TypeOfDestCountry);
	}

	@Override
	public void setTypeOfDestCountry(final @Nullable java.lang.String TypeOfDestCountry)
	{
		set_Value(COLUMNNAME_TypeOfDestCountry, TypeOfDestCountry);
	}

	@Override
	public java.sql.Timestamp getValidFrom()
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidFrom(final java.sql.Timestamp ValidFrom)
	{
		set_Value(COLUMNNAME_ValidFrom, ValidFrom);
	}
}