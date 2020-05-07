package de.metas.handlingunits;

import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Implementors give access to HU related features and properties. To be used from model validators, callouts etc.
 *
 * @see de.metas.handlingunits.IHUDocumentHandlerFactory
 */
public interface IHUDocumentHandler
{
	I_M_HU_PI_Item_Product getM_HU_PI_ItemProductFor(Object document, I_M_Product product);

	void applyChangesFor(Object document);
}
