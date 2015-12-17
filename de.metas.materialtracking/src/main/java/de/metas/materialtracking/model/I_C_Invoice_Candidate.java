package de.metas.materialtracking.model;


/*
 * #%L
 * de.metas.materialtracking
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


public interface I_C_Invoice_Candidate extends de.metas.invoicecandidate.model.I_C_Invoice_Candidate
{
	// @formatter:off
	String COLUMNNAME_M_Material_Tracking_ID = "M_Material_Tracking_ID";
	int getM_Material_Tracking_ID();
	// void setM_Material_Tracking_ID(int M_Material_Tracking_ID); // shall not be used
	I_M_Material_Tracking getM_Material_Tracking();
	void setM_Material_Tracking(I_M_Material_Tracking M_Material_Tracking);
	// @formatter:on
	

	// @formatter:off
	public static final int QUALITYINVOICELINEGROUPTYPE_AD_Reference_ID=540617;
	/** Scrap = 01 */
	public static final String QUALITYINVOICELINEGROUPTYPE_Scrap = "01";
	/** ProducedByProducts = 02 */
	public static final String QUALITYINVOICELINEGROUPTYPE_ProducedByProducts = "02";
	/** AdditionalFee = 03 */
	public static final String QUALITYINVOICELINEGROUPTYPE_AdditionalFee = "03";
	/** ProducedMainProduct = 04 */
	public static final String QUALITYINVOICELINEGROUPTYPE_ProducedMainProduct = "04";
	/** ProducedCoProduct = 05 */
	public static final String QUALITYINVOICELINEGROUPTYPE_ProducedCoProduct = "05";
	/** WithholdingAmount = 06 */
	public static final String QUALITYINVOICELINEGROUPTYPE_WithholdingAmount = "06";
	/** PreceeedingRegularOrderDeduction = 07 */
	public static final String QUALITYINVOICELINEGROUPTYPE_PreceeedingRegularOrderDeduction = "07";

	String COLUMNNAME_QualityInvoiceLineGroupType = "QualityInvoiceLineGroupType";
	String getQualityInvoiceLineGroupType();
	void setQualityInvoiceLineGroupType(final String QualityInvoiceLineGroupType);
	// @formatter:on
}
