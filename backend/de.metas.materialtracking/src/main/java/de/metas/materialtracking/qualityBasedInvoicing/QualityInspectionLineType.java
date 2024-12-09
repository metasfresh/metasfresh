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
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
=======
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Defines what type of lines we could have in our quality reports
 *
 * @author tsa
<<<<<<< HEAD
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
=======
 */
@AllArgsConstructor
@Getter
public enum QualityInspectionLineType implements ReferenceListAwareEnum
{
	/**
	 * RAW Material.
	 * <p>
	 * Occurrence: 1.
	 * <p>
	 * e.g. Karotten mittel ungewaschen
	 */
	Raw("Raw")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Scrap Material.
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Erdbesatz
	 */
<<<<<<< HEAD
	, Scrap
=======
	, Scrap("Scrap")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Total Produced (Main + Co-Products + By-Products).
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Karotten netto gewaschen
	 */
<<<<<<< HEAD
	, ProducedTotal
=======
	, ProducedTotal("ProducedTotal")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Produced By-Products.
	 *
	 * Occurrence: 0..n.
	 *
	 * e.g. Ausfall (Futterkarotten)
	 */
<<<<<<< HEAD
	, ProducedByProducts
=======
	, ProducedByProducts("ProducedByProducts")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Sum of all {@link #ProducedByProducts}
	 *
	 * Occurrence: 1.
	 */
<<<<<<< HEAD
	, ProducedByProductsTotal
=======
	, ProducedByProductsTotal("ProducedByProductsTotal")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Total Produced without By-Products (Main + Co-Products).
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Ausbeute (Marktfähige Ware).
	 */
<<<<<<< HEAD
	, ProducedTotalWithoutByProducts
=======
	, ProducedTotalWithoutByProducts("ProducedTotalWithoutByProducts")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Produced Main product (Main).
	 *
	 * Occurrence: 1.
	 *
	 * e.g. Karotten mittel, P000363_Karotten gewaschen
	 */
<<<<<<< HEAD
	, ProducedMain
=======
	, ProducedMain("ProducedMain")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Produced Co-Products.
	 *
	 * Occurrence: 0..n.
	 *
	 * e.g. Karotten gross
	 */
<<<<<<< HEAD
	, ProducedCoProducts
=======
	, ProducedCoProducts("ProducedCoProducts")
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Sum of all {@link #ProducedCoProducts}
	 *
	 * Occurrence: 1.
	 */
<<<<<<< HEAD
	, ProducedCoProductsTotal
=======
	, ProducedCoProductsTotal("ProducedCoProductsTotal");

	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<QualityInspectionLineType> index = ReferenceListAwareEnums.index(values());

	@NonNull
	public static QualityInspectionLineType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
