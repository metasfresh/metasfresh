package de.metas.handlingunits.hutransaction.impl;

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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.slf4j.Logger;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public final class HUTransactionCandidate implements IHUTransactionCandidate
{
	private static final transient Logger logger = LogManager.getLogger(HUTransactionCandidate.class);

	// Dimension
	@Getter
	@Setter
	private Object referencedModel;

	private final I_M_HU_Item huItem;

	private final I_M_HU_Item vhuItem;
	// Product/Qty/UOM

	@Getter
	private final I_M_Product product;

	@Getter
	private final Quantity quantity;

	// Physical position and status
	private final I_M_Locator locator;

	private final String huStatus;

	// Reference
	private final String id;

	@Getter
	private final Date date;

	private IHUTransactionCandidate counterpartTrx;

	@Getter
	private boolean skipProcessing = false;

	public HUTransactionCandidate(
			final Object referencedModel,
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
				request.getDate());
	}

	public HUTransactionCandidate(final Object model,
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

	public HUTransactionCandidate(final Object model,
			final I_M_HU_Item huItem,
			final I_M_HU_Item vhuItem,
			@NonNull final I_M_Product product,
			@NonNull final Quantity quantity,
			@NonNull final Date date,
			final I_M_Locator locator,
			final String huStatus)
	{
		id = UUID.randomUUID().toString();

		// Dimension
		// NOTE: model can be null
		// Known cases:
		// * in picking terminal when we move materials to an handling unit
		// * methods like HUTrxBL.transferMaterialToNewHUs are assuming this, but are used only in tests
		this.referencedModel = model;

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
		this.product = product;

		// Qty/UOM
		this.quantity = quantity;

		// Transaction date
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

		if (referencedModel != null)
		{
			final String modelTableName = InterfaceWrapperHelper.getModelTableName(referencedModel);
			final int modelRecordId = InterfaceWrapperHelper.getId(referencedModel);
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
				final String piName = Services.get(IHandlingUnitsBL.class).getPI(hu).getName();
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
				final String piName = Services.get(IHandlingUnitsBL.class).getPI(vhu).getName();
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
	public IHUTransactionCandidate getCounterpart()
	{
		return counterpartTrx;
	}

	private void setCounterpart(@NonNull final IHUTransactionCandidate counterpartTrx)
	{
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
	public void pair(final IHUTransactionCandidate counterpartTrx)
	{
		Check.errorUnless(counterpartTrx instanceof HUTransactionCandidate,
				"Param counterPartTrx needs to be a HUTransaction counterPartTrx={}",
				counterpartTrx);

		this.setCounterpart(counterpartTrx);

		// by casting to HUTransaction (which currently is the only implementation of IHUTransaction), we don't have to make setCounterpart() public.
		((HUTransactionCandidate)counterpartTrx).setCounterpart(this);
	}

	@Override
	public I_M_Locator getM_Locator()
	{
		return locator;
	}

	@Override
	public void setSkipProcessing()
	{
		skipProcessing = true;
	}

	@Override
	public String getHUStatus()
	{
		return huStatus;
	}
}
