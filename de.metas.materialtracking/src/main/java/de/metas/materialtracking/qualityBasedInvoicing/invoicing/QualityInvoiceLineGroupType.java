package de.metas.materialtracking.qualityBasedInvoicing.invoicing;

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
	Scrap,

	/**
	 * By-Product(s)
	 *
	 * e.g. Futterkarotten
	 */
	ProducedByProducts,

	/**
	 * Additional fees
	 *
	 * e.g.
	 * <ul>
	 * <li>Abzug für Beitrag Basic-Linie
	 * <li>Abzug für Beitrag Verkaufsförderung
	 * </ul>
	 */
	AdditionalFee,

	/**
	 * Main Produced product
	 *
	 * i.e. Karotten mittel
	 */
	ProducedMainProduct,

	/**
	 * Co-Products
	 *
	 * i.e. Karotten gross
	 */
	ProducedCoProduct,

	/**
	 * Withholding
	 *
	 * e.g. Akontozahlung 50 %
	 */
	WithholdingAmount,

	/**
	 * Preceeding regular orders deduction
	 *
	 * i.e. Auslagerung per 30.09.2014
	 */
	PreceeedingRegularOrderDeduction,
}
