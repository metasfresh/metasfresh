package de.metas.handlingunits.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

public final class HUTransaction implements IHUTransaction
{
	private static final transient Logger logger = LogManager.getLogger(HUTransaction.class);

	// Dimension
	private Object model;
	private final I_M_HU_Item huItem;
	private final I_M_HU_Item vhuItem;
	// Product/Qty/UOM
	private final I_M_Product product;
	private final Quantity quantity;

	// Physical position and status
	private final I_M_Locator locator;
	private final String huStatus;

	// Reference
	private final String id;
	private final Date date;
	private IHUTransaction counterpartTrx;

	private boolean skipProcessing = false;

	public HUTransaction(final Object referencedModel,
			final I_M_HU_Item huItem,
			final I_M_HU_Item vhuItem,
			final IAllocationRequest request,
			final boolean outTrx)
	{
		this(
				referencedModel, // model
				huItem, // HU item
				vhuItem, // virtual HU Item
				request.getProduct(),
				// Transaction Quantity/UOM
				// NOTE: we use source quantity/uom because those are in storage's internal UOM
				// to avoid precision errors while converting again from working UOM to internal storage UOM
				request.getQuantity().switchToSource().negateIf(outTrx),
				// Transaction Date:
				request.getDate()
		//
		);
	}

	public HUTransaction(final Object model,
			final I_M_HU_Item huItem,
			final I_M_HU_Item vhuItem,
			final I_M_Product product,
			final Quantity quantity,
			final Date date)
	{
		this(model,
				huItem,
				vhuItem,
				product,
				quantity,
				date,
				null, // locator, will be handled
				null); // huStatus, will be handled
	}

	public HUTransaction(final Object model,
			final I_M_HU_Item huItem,
			final I_M_HU_Item vhuItem,
			final I_M_Product product,
			final Quantity quantity,
			final Date date,
			final I_M_Locator locator,
			final String huStatus)
	{
		id = UUID.randomUUID().toString();

		// Dimension
		// NOTE: model can be null
		// Known cases:
		// * in picking terminal when we move materials to an handling unit
		// * methods like HUTrxBL.transferMaterialToNewHUs are assuming this, but are used only in tests
		this.model = model;

		//
		// huItem
		if (huItem != null)
		{
			this.huItem = huItem;
		}
		else if (model == null)
		{
			// FIXME: workaround until we find out what else we need to fix
			final AdempiereException ex = new AdempiereException("No model was specified and referencedModel is null. Assuming NULL");
			logger.warn(ex.getLocalizedMessage(), ex);

			this.huItem = null;

		}
		else if (InterfaceWrapperHelper.isInstanceOf(model, I_M_HU_Item.class))
		{
			// FIXME: workaround until we find out what else we need to fix
			final AdempiereException ex = new AdempiereException("No HU Item was specified. Assuming referencedModel=" + model);
			logger.warn(ex.getLocalizedMessage(), ex);

			this.huItem = InterfaceWrapperHelper.create(model, I_M_HU_Item.class);
		}
		else
		{
			// Case: HU_Item is not set and referenced model is not null but is not instanceof I_M_HU_Item.
			// This is a common case which can happen, for example, on transactions where we transfer from a document storage to an HU Item storage.
			this.huItem = null;
		}

		// vhuItem
		this.vhuItem = vhuItem;

		// Product
		Check.assumeNotNull(product, "product not null");
		this.product = product;

		// Qty/UOM
		Check.assumeNotNull(quantity, "quantity not null");
		this.quantity = quantity;

		// Transaction date
		Check.assumeNotNull(date, "date not null");
		this.date = date;

		final I_M_HU effectiveHU = getEffectiveHU();

		// Locator
		if (locator != null)
		{
			this.locator = locator;
		}
		else if (effectiveHU != null)
		{
			this.locator = effectiveHU.getM_Locator();
		}
		else
		{
			this.locator = null;
		}

		// HUStatus
		if (huStatus != null)
		{
			this.huStatus = huStatus;
		}
		else if (effectiveHU != null)
		{
			this.huStatus = effectiveHU.getHUStatus();
		}
		else
		{
			this.huStatus = null;
		}
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName() + " ["
				+ "product=" + product.getValue()
				+ ", qty=" + quantity
				+ ", date=" + date);

		if (model != null)
		{
			final String modelTableName = InterfaceWrapperHelper.getModelTableName(model);
			final int modelRecordId = InterfaceWrapperHelper.getId(model);
			sb.append(", model=" + modelTableName + "/" + modelRecordId);
		}

		if (huItem != null)
		{
			final I_M_HU hu = huItem.getM_HU();
			if (hu == null)
			{
				// CASE: some JUnit tests are not setting the HU
				sb.append(", huItem=").append(huItem);
			}
			else
			{
				final int huId = hu.getM_HU_ID();
				final String piName = hu.getM_HU_PI_Version().getM_HU_PI().getName();
				sb.append(", hu=").append(huId).append("-").append(piName);
			}
		}
		else
		{
			sb.append(", no huItem");
		}

		if (vhuItem != null)
		{
			final I_M_HU vhu = vhuItem.getM_HU();
			if (vhu == null)
			{
				// CASE: some JUnit tests are not setting the HU
				sb.append(", vhuItem=").append(vhuItem);
			}
			else
			{
				final int vhuId = vhu.getM_HU_ID();
				final String piName = vhu.getM_HU_PI_Version().getM_HU_PI().getName();
				sb.append(", vhu=").append(vhuId).append("-").append(piName);
			}

		}
		else
		{
			sb.append(", no vhu");
		}

		sb.append("]");

		return sb.toString();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getReferencedModel()
	{
		return model;
	}

	@Override
	public void setReferencedModel(final Object referencedModel)
	{
		model = referencedModel;
	}

	@Override
	public I_M_HU getM_HU()
	{
		if (huItem == null)
		{
			return null;
		}
		return huItem.getM_HU();
	}

	@Override
	public final I_M_HU getVHU()
	{
		if (vhuItem == null)
		{
			return null;
		}
		return vhuItem.getM_HU();
	}

	private I_M_HU getEffectiveHU()
	{
		final I_M_HU hu = getM_HU();
		if (hu != null)
		{
			return hu;
		}
		return getVHU();
	}

	@Override
	public I_M_HU_Item getM_HU_Item()
	{
		return huItem;
	}

	@Override
	public I_M_HU_Item getVHU_Item()
	{
		return vhuItem;
	}

	@Override
	public I_M_Product getProduct()
	{
		return product;
	}

	@Override
	public Quantity getQuantity()
	{
		return quantity;
	}

	@Override
	public IHUTransaction getCounterpart()
	{
		return counterpartTrx;
	}

	private void setCounterpart(final IHUTransaction counterpartTrx)
	{
		Check.assumeNotNull(counterpartTrx, "counterpartTrx not null");
		Check.assume(this != counterpartTrx, "counterpartTrx != this");

		if (this.counterpartTrx == null)
		{
			this.counterpartTrx = counterpartTrx;
		}
		else if (this.counterpartTrx == counterpartTrx)
		{
			// do nothing; it was already set
		}
		else
		{
			throw new HUException("Posible development error: changing the counterpart transaction is not allowed"
					+ "\n Transaction: " + this
					+ "\n Counterpart trx (old): " + this.counterpartTrx
					+ "\n Counterpart trx (new): " + counterpartTrx);
		}
	}

	@Override
	public void pair(final IHUTransaction counterpartTrx)
	{
		Check.errorUnless(counterpartTrx instanceof HUTransaction,
				"Param counterPartTrx needs to be a HUTransaction counterPartTrx={}",
				counterpartTrx);

		this.setCounterpart(counterpartTrx);
		
		// by casting to HUTransaction (which currently is the only implementation of IHUTransaction), we don't have to make setCounterpart() public.
		((HUTransaction)counterpartTrx).setCounterpart(this);
	}

	@Override
	public Date getDate()
	{
		return date;
	}

	@Override
	public I_M_Locator getM_Locator()
	{
		return locator;
	}

	@Override
	public String getHUStatus()
	{
		return huStatus;
	}

	@Override
	public void setSkipProcessing()
	{
		skipProcessing = true;
	}

	@Override
	public boolean isSkipProcessing()
	{
		return skipProcessing;
	}
}
