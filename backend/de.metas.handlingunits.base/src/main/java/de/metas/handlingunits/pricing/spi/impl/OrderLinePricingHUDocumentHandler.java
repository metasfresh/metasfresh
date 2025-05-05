package de.metas.handlingunits.pricing.spi.impl;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.IHUDocumentHandler;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.order.IOrderLineBL;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.attributebased.IAttributePricingBL;
import de.metas.pricing.attributebased.IProductPriceAware;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListener;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList_Version;

import java.util.Optional;
import java.util.Properties;

/**
 * Note: currently this implementation is used to update a given record's ASI when its {@link I_M_HU_PI_Item_Product} changes.
 *
 *
 */
public class OrderLinePricingHUDocumentHandler implements IHUDocumentHandler
{
	/**
	 * Does nothing and returns <code>null</code>.
	 */
	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(final Object document, final ProductId productId)
	{
		// Not needed.
		return null;
	}

	/**
	 * Assumes that the given <code>document</code> is an order line.<br>
	 * It creates a new ASI using the default I_M_ProductPrice_Attribute for the given order line and set's the order line to reference the new ASI (ignoring a possible preexisting ASI).
	 * <p>
	 * The method does nothing if:
	 * <ul>
	 * <li><code>M_Product_ID</code> is not changed, or is changed, but the orderLine is new and already has another ASI. Rationale: in case the line is new and already has an ASI, we assume that this
	 * ASI was created and added for a reason, and is not stale, but as fresh as the product itself.
	 * <li>there is no default <code>M_ProductPrice_Attribute</code> to be found, or
	 * <li>the default <code>M_ProductPrice_Attribute</code> has a M_HU_PI_Item_Product_ID which differs from the order line's M_HU_PI_Item_Product_ID
	 * </ul>
	 *
	 *
	 * @see InterfaceWrapperHelper#isValueChanged(Object, String)
	 * @see IOrderLineBL#getPriceListVersionId(de.metas.interfaces.I_C_OrderLine)
	 * @see IAttributePricingBL#getDefaultAttributePriceOrNull(Object, int, I_M_PriceList_Version, boolean)
	 */
	@Override
	public void applyChangesFor(final Object document)
	{
		Check.assumeInstanceOf(document, I_C_OrderLine.class, "document");

		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(document, I_C_OrderLine.class);

		final boolean orderLineNew = InterfaceWrapperHelper.isNew(orderLine);
		final boolean productChanged = InterfaceWrapperHelper.isValueChanged(orderLine, org.compiere.model.I_C_OrderLine.COLUMNNAME_M_Product_ID);
		final boolean asiAlreadySet = orderLine.getM_AttributeSetInstance_ID() > 0;

		final boolean createNewASI = productChanged && (!orderLineNew || !asiAlreadySet);

		if (!createNewASI)
		{
			return;
		}

		final I_M_ProductPrice productPrice;

		// task 08839: get the productPriceAttribute, considering to get the explicid one the might be attached to this order line.
		// this is needed in case we create the order from an C_OCLand and do not wan tthe system to guess a default ASI and PIIP.
		final IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(orderLine);

		final IAttributePricingBL attributePricingBL = Services.get(IAttributePricingBL.class);
		final Optional<IProductPriceAware> orderLineHasExplicitASI = attributePricingBL.getDynAttrProductPriceAttributeAware(asiAware);
		if (orderLineHasExplicitASI.isPresent()
				&& orderLineHasExplicitASI.get().isExplicitProductPriceAttribute())
		{
			final IProductPriceAware productPriceAttributeAware = orderLineHasExplicitASI.get();
			final String trxName = InterfaceWrapperHelper.getTrxName(orderLine);
			final Properties ctx = InterfaceWrapperHelper.getCtx(orderLine);
			final int productPriceId = productPriceAttributeAware.getM_ProductPrice_ID();
			if (productPriceId > 0)
			{
				productPrice = InterfaceWrapperHelper.create(ctx, productPriceId, I_M_ProductPrice.class, trxName);
			}
			else
			{
				productPrice = null;
			}
		}
		else
		{
			productPrice = getDefaultProductPriceAttribute(orderLine);
		}

		if (productPrice == null)
		{
			// no default Product Price Attribute was found => nothing to do
			return;
		}

		final int pricePIItemProductId = productPrice.getM_HU_PI_Item_Product_ID();
		if (pricePIItemProductId > 0 && orderLine.getM_HU_PI_Item_Product_ID() != pricePIItemProductId)
		{
			// "HU PI Item Product" from Product Price Attribute does not match the one from Order Line
			return;
		}

		final I_M_AttributeSetInstance asi = attributePricingBL.generateASI(productPrice);
		orderLine.setM_AttributeSetInstance(asi);

		//dev-note: making sure price attributes are preserved
		if (asi != null)
		{
			IModelAttributeSetInstanceListener.DYNATTR_DisableASIUpdateOnModelChange.setValue(orderLine, true);
		}
	}

	/**
	 * Retrieves the <b>strict</b> default attribute price record for the given <code>orderLine</code>'s PLV and product.
	 *
	 * @param orderLine
	 * @return
	 * @see IOrderLineBL#getPriceListVersionId(de.metas.interfaces.I_C_OrderLine)
	 * @see IAttributePricingBL#getDefaultAttributePriceOrNull(Object, int, I_M_PriceList_Version, boolean)
	 */
	private I_M_ProductPrice getDefaultProductPriceAttribute(final I_C_OrderLine orderLine)
	{
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final PriceListVersionId plvId = orderLineBL.getPriceListVersionId(orderLine);

		final ProductId productId = ProductId.ofRepoIdOrNull(orderLine.getM_Product_ID());
		final HUPIItemProductId packingMaterialId = HUPIItemProductId.ofRepoIdOrNull(orderLine.getM_HU_PI_Item_Product_ID());

		//
		// Check if we have a product price specific to current PI Item Product
		if(packingMaterialId != null)
		{
			final I_M_ProductPrice huProductPrice = ProductPrices.newQuery(plvId)
					.setProductId(productId)
					.onlyAttributePricing()
					.onlyValidPrices(true)
					.matching(HUPricing.createHUPIItemProductMatcher(packingMaterialId))
					.retrieveDefault(I_M_ProductPrice.class);
			if(huProductPrice != null)
			{
				return huProductPrice;
			}
		}

		return ProductPrices.newQuery(plvId)
				.setProductId(productId)
				.onlyAttributePricing()
				.onlyValidPrices(true)
				.retrieveDefault(I_M_ProductPrice.class);
	}
}
