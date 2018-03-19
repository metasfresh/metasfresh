/**
 *
 */
package de.metas.inout.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Warehouse;

/**
 * {@link org.compiere.model.I_M_InOutLine} extension with Swat/InOut module columns.
 */
public interface I_M_InOutLine extends org.compiere.model.I_M_InOutLine
{
	// @formatter:off
	public static String COLUMNNAME_ProductDescription = "ProductDescription";
	@Override
	public String getProductDescription();
	@Override
	public void setProductDescription(String ProductDescription);
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsIndividualDescription = "IsIndividualDescription";
	public boolean isIndividualDescription();
	public void setIsIndividualDescription(boolean individualDescription);
	// @formatter:on

	// @formatter:off
	// 06365:
	// Column IsInDispute
	public static final String COLUMNNAME_IsInDispute = "IsInDispute";
	public void setIsInDispute(boolean IsInDispute);
	public boolean isInDispute();
	// @formatter:on

	// @formatter:off
	// column QualityDiscountPercent
	public static final String COLUMNNAME_QualityDiscountPercent = "QualityDiscountPercent";
	public void setQualityDiscountPercent(BigDecimal QualityDiscountPercent);
	public BigDecimal getQualityDiscountPercent();
	// @formatter:on

	// @formatter:off
	// column QualityNote
	public static final String COLUMNNAME_QualityNote = "QualityNote";
	public void setQualityNote(String QualityNote);
	public String getQualityNote();
	// @formatter:on

	// @formatter:off
	public String COLUMNNAME_M_Warehouse_Dest_ID = "M_Warehouse_Dest_ID";
	public I_M_Warehouse getM_Warehouse_Dest();
	public void setM_Warehouse_Dest(I_M_Warehouse M_Warehouse_Dest);
	public void setM_Warehouse_Dest_ID(int M_Warehouse_Dest_ID);
	public int getM_Warehouse_Dest_ID();
	// @formatter:on

	//
	// SubProducer_BPartner_ID
	// @formatter:off
	public static final String COLUMNNAME_SubProducer_BPartner_ID = "SubProducer_BPartner_ID";
	public void setSubProducer_BPartner_ID(int SubProducer_BPartner_ID);
	public void setSubProducer_BPartner(I_C_BPartner SubProducer_BPartner);
	public int getSubProducer_BPartner_ID();
	public I_C_BPartner getSubProducer_BPartner() throws RuntimeException;
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_IsPackagingMaterial = "IsPackagingMaterial";
	public void setIsPackagingMaterial(boolean IsPackagingMaterial);
	public boolean isPackagingMaterial();
	// @formatter:on

	// @formatter:off
	public static final String COLUMNNAME_M_PackingMaterial_InOutLine_ID = "M_PackingMaterial_InOutLine_ID";
	public void setM_PackingMaterial_InOutLine_ID(int M_PackingMaterial_InOutLine_ID_ID);
	public void setM_PackingMaterial_InOutLine(I_M_InOutLine M_PackingMaterial_InOutLine_ID);
	public int getM_PackingMaterial_InOutLine_ID();
	public I_M_InOutLine getM_PackingMaterial_InOutLine();
	// @formatter:on

	//
	// VendorReturn_Origin_InOutLine_ID
	// @formatter:off
	public static final String COLUMNNAME_Return_Origin_InOutLine_ID = "Return_Origin_InOutLine_ID";
	public void setReturn_Origin_InOutLine_ID(int VendorReturn_Origin_InOutLine_ID);
	public void setReturn_Origin_InOutLine(I_M_InOutLine VendorReturn_Origin_InOutLine);
	public int getReturn_Origin_InOutLine_ID();
	public I_M_InOutLine getReturn_Origin_InOutLine() throws RuntimeException;
	// @formatter:on


	// @formatter:off
	public static final String COLUMNNAME_QtyEnteredTU = "QtyEnteredTU";
	public BigDecimal getQtyEnteredTU();
	public void setQtyEnteredTU(BigDecimal QtyEnteredTU);
	// @formatter:on
}
