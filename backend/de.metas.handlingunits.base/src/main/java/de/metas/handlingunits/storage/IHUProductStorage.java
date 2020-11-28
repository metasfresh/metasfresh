package de.metas.handlingunits.storage;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.quantity.Quantity;

public interface IHUProductStorage extends IProductStorage, IHUAware
{
	@Override I_M_HU getM_HU();

	default HuId getHuId()
	{
		return HuId.ofRepoId(getM_HU().getM_HU_ID());
	}

	/**
	 * @return quantity in product's stocking UOM
	 */
	Quantity getQtyInStockingUOM();
}
