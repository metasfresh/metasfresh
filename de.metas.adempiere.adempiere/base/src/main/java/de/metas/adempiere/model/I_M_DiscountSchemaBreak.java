package de.metas.adempiere.model;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


public interface I_M_DiscountSchemaBreak extends org.compiere.model.I_M_DiscountSchemaBreak
{
	/** Column name QualityIssuePercentage */
	public static final String COLUMNNAME_QualityIssuePercentage = "QualityIssuePercentage";

	public void setQualityIssuePercentage(java.math.BigDecimal QualityIssuePercentage);

	public java.math.BigDecimal getQualityIssuePercentage();

	/**
	 * Set Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: false <br>
	 * Virtual Column: false
	 */
	public void setM_Attribute_ID(int M_Attribute_ID);

	/**
	 * Get Merkmal.
	 * Produkt-Merkmal
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: false <br>
	 * Virtual Column: false
	 */
	public int getM_Attribute_ID();

	public org.compiere.model.I_M_Attribute getM_Attribute();

	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute);

	/** Column definition for M_Attribute_ID */
	public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Attribute> COLUMN_M_Attribute_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_Attribute>(
			I_M_DiscountSchemaBreak.class, "M_Attribute_ID", org.compiere.model.I_M_Attribute.class);
	/** Column name M_Attribute_ID */
	public static final String COLUMNNAME_M_Attribute_ID = "M_Attribute_ID";

	/**
	 * Set Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: false <br>
	 * Virtual Column: false
	 */
	public void setM_AttributeValue_ID(int M_AttributeValue_ID);

	/**
	 * Get Merkmals-Wert.
	 * Product Attribute Value
	 *
	 * <br>
	 * Type: TableDir <br>
	 * Mandatory: false <br>
	 * Virtual Column: false
	 */
	public int getM_AttributeValue_ID();

	public org.compiere.model.I_M_AttributeValue getM_AttributeValue();

	public void setM_AttributeValue(org.compiere.model.I_M_AttributeValue M_AttributeValue);

	/** Column definition for M_AttributeValue_ID */
	public static final org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_AttributeValue> COLUMN_M_AttributeValue_ID = new org.adempiere.model.ModelColumn<I_M_DiscountSchemaBreak, org.compiere.model.I_M_AttributeValue>(
			I_M_DiscountSchemaBreak.class, "M_AttributeValue_ID", org.compiere.model.I_M_AttributeValue.class);
	/** Column name M_AttributeValue_ID */
	public static final String COLUMNNAME_M_AttributeValue_ID = "M_AttributeValue_ID";

}
