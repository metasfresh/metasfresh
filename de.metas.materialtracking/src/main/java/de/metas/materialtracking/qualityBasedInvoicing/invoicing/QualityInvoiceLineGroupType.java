package de.metas.materialtracking.qualityBasedInvoicing.invoicing;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

import de.metas.materialtracking.model.I_C_Invoice_Candidate;

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

/**
 * {@link IQualityInvoiceLineGroup} type.
 *
 * @author tsa
 *
 */
public enum QualityInvoiceLineGroupType
{
	/**
	 * Scrap
	 *
	 * i.e. Erdbesatz
	 */
	Scrap(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_Scrap)

	/**
	 * By-Product(s)
	 *
	 * e.g. Futterkarotten
	 */
	, ByProduct(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_ProducedByProducts)

	/**
	 * Additional fees
	 *
	 * e.g.
	 * <ul>
	 * <li>Abzug für Beitrag Basic-Linie
	 * <li>Abzug für Beitrag Verkaufsförderung
	 * </ul>
	 */
	, Fee(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_AdditionalFee)

	/**
	 * Main Produced product
	 *
	 * i.e. Karotten mittel
	 */
	, MainProduct(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_ProducedMainProduct)

	/**
	 * Co-Products
	 *
	 * i.e. Karotten gross
	 */
	, CoProduct(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_ProducedCoProduct)

	/**
	 * Withholding
	 *
	 * e.g. Akontozahlung 50 %
	 */
	, Withholding(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_WithholdingAmount)

	/**
	 * Preceeding regular orders deduction
	 *
	 * i.e. Auslagerung per 30.09.2014
	 */
	, ProductionOrder(I_C_Invoice_Candidate.QUALITYINVOICELINEGROUPTYPE_PreceeedingRegularOrderDeduction)

	;

	/** Map: AD_Ref_List_Value to {@link QualityInvoiceLineGroupType} */
	private static final Map<String, QualityInvoiceLineGroupType> adRefListValue2value;
	static
	{
		final ImmutableMap.Builder<String, QualityInvoiceLineGroupType> adRefListValue2valueBuilder = ImmutableMap.builder();
		for (final QualityInvoiceLineGroupType value : values())
		{
			adRefListValue2valueBuilder.put(value.getAD_Ref_List_Value(), value);
		}
		adRefListValue2value = adRefListValue2valueBuilder.build();
	}

	private final String adRefListValue;

	QualityInvoiceLineGroupType(final String adRefListValue)
	{
		this.adRefListValue = adRefListValue;
	}

	public final String getAD_Ref_List_Value()
	{
		return adRefListValue;
	}

	public static final QualityInvoiceLineGroupType ofAD_Ref_List_Value(final String adRefListValue)
	{
		final QualityInvoiceLineGroupType value = adRefListValue2value.get(adRefListValue);
		if (value == null)
		{
			throw new IllegalArgumentException("No " + QualityInvoiceLineGroupType.class + " found for AD_Ref_List_Value=" + adRefListValue);
		}
		return value;
	}
}
