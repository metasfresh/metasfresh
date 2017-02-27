package de.metas.pricing.attributebased;

import java.util.List;
import java.util.Optional;

import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.pricing.api.IPricingAttribute;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_ProductPrice;

public interface IAttributePricingBL extends ISingletonService
{
	/**
	 * Generates a new ASI based on {@link I_M_ProductPrice}'s attributes.
	 * 
	 * @param productPriceAttribute
	 * @return new generated ASI
	 */
	I_M_AttributeSetInstance generateASI(I_M_ProductPrice productPrice);

	/**
	 * For each {@link IPricingAttribute}, add or update one {@link I_M_AttributeInstance} to the given <code>asiAware</code>'s ASI.<br>
	 * If the given asiAware had no ASI or if the given {@link IPricingAttribute}s is empty, then do nothing.
	 * 
	 * @param pricingResult
	 * @param asiAware
	 * 
	 * @task http://dewiki908/mediawiki/index.php/08803_ADR_from_Partner_versus_Pricelist
	 */
	void addToASI(IAttributeSetInstanceAware asiAware, List<IPricingAttribute> pricingAttributes);
	
	/**
	 * Extract {@link IPricingAttribute}s from given product price
	 * 
	 * @param productPrice
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
	 * @param asiAware
	 * @param productPriceAware
	 * @task http://dewiki908/mediawiki/index.php/08839_Import_of_Orders_per_Excel-Pricelist_%28100553254746%29
	 */
	void setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware, Optional<IProductPriceAware> productPriceAware);

	/**
	 * See {@link #setDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware, Optional)}.
	 * 
	 * @param asiAware
	 * @return
	 */
	Optional<IProductPriceAware> getDynAttrProductPriceAttributeAware(IAttributeSetInstanceAware asiAware);
}
