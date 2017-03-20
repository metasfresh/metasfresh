package de.metas.handlingunits.document.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.storage.IProductStorage;

public abstract class AbstractHUDocumentLine implements IHUDocumentLine
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private final IProductStorage storage;
	private final Object referenceModel;
	private boolean readonly = false;

	public AbstractHUDocumentLine(final IProductStorage storage, final Object referenceModel)
	{
		Check.assumeNotNull(storage, "storage not null");
		this.storage = storage;

		Check.assumeNotNull(referenceModel, "referenceModel not null");
		this.referenceModel = referenceModel;
	}

	protected Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(referenceModel);
	}

	@Override
	public String getDisplayName()
	{
		final I_M_Product product = getM_Product();

		final StringBuilder name = new StringBuilder()
				.append(product.getName()).append("#").append(product.getM_Product_ID());
		return name.toString();
	}

	@Override
	public IHUDocumentLine getReversal()
	{
		throw new UnsupportedOperationException("Getting reversal for " + this + " is not supported");
	}

	@Override
	public I_M_Product getM_Product()
	{
		return storage.getM_Product();
	}

	@Override
	public BigDecimal getQty()
	{
		return storage.getQtyCapacity();
	}

	@Override
	public I_C_UOM getC_UOM()
	{
		return storage.getC_UOM();
	}

	@Override
	public BigDecimal getQtyAllocated()
	{
		return storage.getQtyFree();
	}

	@Override
	public BigDecimal getQtyToAllocate()
	{
		return storage.getQty();
	}

	@Override
	public Object getTrxReferencedModel()
	{
		return referenceModel;
	}

	protected IProductStorage getStorage()
	{
		return storage;
	}

	@Override
	public IAllocationSource createAllocationSource(final I_M_HU hu)
	{
		return HUListAllocationSourceDestination.of(hu);
	}

	@Override
	public boolean isReadOnly()
	{
		return readonly;
	}

	@Override
	public void setReadOnly(final boolean readonly)
	{
		this.readonly = readonly;
	}

	@Override
	public I_M_HU_Item getInnerHUItem()
	{
		return null;
	}
}
