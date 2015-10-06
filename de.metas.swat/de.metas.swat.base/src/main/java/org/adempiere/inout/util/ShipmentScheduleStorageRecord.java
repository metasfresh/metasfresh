package org.adempiere.inout.util;

/*
 * #%L
 * de.metas.swat.base
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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.storage.IStorageRecord;

/**
 * This class wraps another {@link IStorageRecord} and delegates all methods calls besides those that change the Qty to the wrapped instance. <br>
 * This classe's <code>qtyOnHand</code> field is initialized from the wrapped instance and can be changed. <br>
 * The changes are <b>never</b> propagated back to the wrapped instance.
 *
 */
public class ShipmentScheduleStorageRecord implements IStorageRecord
{
	private final IStorageRecord storageRecord;
	private BigDecimal qtyOnHand;

	public ShipmentScheduleStorageRecord(final IStorageRecord storageRecord)
	{
		super();
		Check.assumeNotNull(storageRecord, "storageRecord not null");
		this.storageRecord = storageRecord;
		this.qtyOnHand = storageRecord.getQtyOnHand();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public String getSummary()
	{
		String storageRecordSummary = storageRecord.getSummary();

		// Show QtyOnHand only if differs from original
		if (this.qtyOnHand.compareTo(storageRecord.getQtyOnHand()) != 0)
		{
			storageRecordSummary += "\n @QtyOnHand@ (current): " + this.qtyOnHand;
		}
		return storageRecordSummary;
	}

	@Override
	public String getId()
	{
		return storageRecord.getId();
	}

	@Override
	public I_M_Product getProduct()
	{
		return storageRecord.getProduct();
	}

	@Override
	public I_M_Locator getLocator()
	{
		return storageRecord.getLocator();
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return storageRecord.getC_BPartner();
	}

	@Override
	public IAttributeSet getAttributes()
	{
		return storageRecord.getAttributes();
	}

	@Override
	public BigDecimal getQtyOnHand()
	{
		return qtyOnHand;
	}

	public void setQtyOnHand(final BigDecimal qtyOnHand)
	{
		Check.assumeNotNull(qtyOnHand, "qtyOnHand not null");
		this.qtyOnHand = qtyOnHand;
	}

	public void addQtyOnHand(final BigDecimal qtyOnHandToAdd)
	{
		final BigDecimal qtyOnHandOld = getQtyOnHand();
		final BigDecimal qtyOnHandNew = qtyOnHandOld.add(qtyOnHandToAdd);
		setQtyOnHand(qtyOnHandNew);
	}

	public void subtractQtyOnHand(final BigDecimal qtyOnHandToRemove)
	{
		final BigDecimal qtyOnHandOld = getQtyOnHand();
		final BigDecimal qtyOnHandNew = qtyOnHandOld.subtract(qtyOnHandToRemove);
		setQtyOnHand(qtyOnHandNew);
	}
}
