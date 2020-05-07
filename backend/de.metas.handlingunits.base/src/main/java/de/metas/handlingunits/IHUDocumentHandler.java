package de.metas.handlingunits;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;

/**
 * Implementors give access to HU related features and properties. To be used from model validators, callouts etc.
 *
 * @see de.metas.handlingunits.IHUDocumentHandlerFactory
 */
public interface IHUDocumentHandler
{
	I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(Object document, ProductId productId);

	void applyChangesFor(Object document);
}
