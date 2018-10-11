package de.metas.picking.service;

import java.util.Properties;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.ISingletonService;

public interface IPackingService extends ISingletonService
{
	void removeProductQtyFromHU(Properties ctx, I_M_HU hu, PackingItemParts parts);
}
