package de.metas.pricing.attributebased;

import de.metas.pricing.IPricingAttribute;
import de.metas.util.ISingletonService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ProductPrice;

import java.util.List;
import java.util.Optional;

public interface IAttributePricingBL extends ISingletonService
{
	/**
	 * Generates a new ASI based on {@link I_M_ProductPrice}'s attributes.
	 *
	 * @return new generated ASI
	 */
	I_M_AttributeSetInstance generateASI(I_M_ProductPrice productPrice);

	/**
	 * For each {@link IPricingAttribute}, add or update one {@link I_M_AttributeInstance} to the given <code>asiAware</code>'s ASI.<br>
	 * If the given asiAware had no ASI or if the given {@link IPricingAttribute}s is empty, then do nothing.
	 *
	 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	void addToASI(IAttributeSetInstanceAware asiAware, List<IPricingAttribute> pricingAttributes);

	/**
	 * Extract {@link IPricingAttribute}s from given product price
	 *
	 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	List<IPricingAttribute> extractPricingAttributes(I_M_ProductPrice productPrice);

	/**
	 * Attach the given <code>productPriceAware</code> to the given <code>asiAware</code>.
	 *
	 * Note that the implementation uses {@link org.adempiere.model.InterfaceWrapperHelper} at the core,
	 * so the given asiAware needs to be handled by its internal wrappers (PO or GridTab or POJO).
	 *
	 * @task http://dewiki908/mediawiki/index.php/08839_Import_of_Orders_per_Excel-Pricelist_%28100553254746%29
	 */
	void setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware, Optional<IProductPriceAware> productPriceAware);

	/**
	 * @see #setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware, Optional)
	 */
	void setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware, IProductPriceAware productPriceAware);

	/**
	 * See {@link #setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware, Optional)}.
	 */
	Optional<IProductPriceAware> getDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware);
}
