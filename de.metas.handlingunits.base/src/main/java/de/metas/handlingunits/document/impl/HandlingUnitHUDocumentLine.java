package de.metas.handlingunits.document.impl;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.util.Check;

/* package */class HandlingUnitHUDocumentLine extends AbstractHUDocumentLine
{
	private final I_M_HU_Item item;

	public HandlingUnitHUDocumentLine(final I_M_HU_Item item, final IProductStorage storage)
	{
		super(storage, item);

		Check.assumeNotNull(item, "item not null");
		this.item = item;
	}

	@Override
	public IHUAllocations getHUAllocations()
	{
		throw new UnsupportedOperationException("not implemented");
	}

	@Override
	public I_M_HU_Item getInnerHUItem()
	{
		return item;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return -1; // N/A
	}
}
