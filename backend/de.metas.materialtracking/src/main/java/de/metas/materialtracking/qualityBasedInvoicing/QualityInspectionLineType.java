package de.metas.materialtracking.qualityBasedInvoicing;

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
 * Defines what type of lines we could have in our quality reports
 *
 * @author tsa
 *
 */
public enum QualityInspectionLineType
{
	/**
	 * RAW Material.
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Karotten mittel ungewaschen
	 */
	Raw

	/**
	 * Scrap Material.
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Erdbesatz
	 */
	, Scrap

	/**
	 * Total Produced (Main + Co-Products + By-Products).
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Karotten netto gewaschen
	 */
	, ProducedTotal

	/**
	 * Produced By-Products.
	 *
	 * Occurrence: 0..n.
	 *
	 * e.g. Ausfall (Futterkarotten)
	 */
	, ProducedByProducts

	/**
	 * Sum of all {@link #ProducedByProducts}
	 *
	 * Occurrence: 1.
	 */
	, ProducedByProductsTotal

	/**
	 * Total Produced without By-Products (Main + Co-Products).
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Ausbeute (Marktfähige Ware).
	 */
	, ProducedTotalWithoutByProducts

	/**
	 * Produced Main product (Main).
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Karotten mittel, P000363_Karotten gewaschen
	 */
	, ProducedMain

	/**
	 * Produced Co-Products.
	 *
	 * Occurrence: 0..n.
	 *
	 * e.g. Karotten gross
	 */
	, ProducedCoProducts

	/**
	 * Sum of all {@link #ProducedCoProducts}
	 *
	 * Occurrence: 1.
	 */
	, ProducedCoProductsTotal
}
