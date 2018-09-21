package de.metas.handlingunits.attribute;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.util.ISingletonService;

/**
 * Common logic around {@link IWeightable}.
 *
 * @author tsa
 *
 */
public interface IWeightableBL extends ISingletonService
{
	/**
	 * Boolean property which if set, it will allow user to change weights but ONLY on VHU level
	 *
	 * @task http://dewiki908/mediawiki/index.php/08270_Wareneingang_POS_multiple_lines_in_1_TU_%28107035315495%29
	 */
	String PROPERTY_WeightableOnlyIfVHU = IWeightable.class.getName() + ".WeightableOnlyIfVHU";

	/**
	 *
	 * @param uomType UOM Type (i.e. {@link I_C_UOM#getUOMType()})
	 * @return true if it's a weightable UOM Type
	 */
	boolean isWeightableUOMType(String uomType);

	/**
	 *
	 * @param uom
	 * @return true if given uom is weightable
	 */
	boolean isWeightable(I_C_UOM uom);

	/**
	 *
	 * @param product
	 * @return true if product's stocking UOM is weightable
	 */
	boolean isWeightable(I_M_Product product);

}
