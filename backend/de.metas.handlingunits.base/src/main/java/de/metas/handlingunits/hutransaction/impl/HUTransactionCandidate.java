package de.metas.handlingunits.hutransaction.impl;

import java.time.ZonedDateTime;
import java.util.UUID;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.slf4j.Logger;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTransactionCandidate;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.logging.LogManager;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
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
	private final ProductId productId;

	@Getter
	private final Quantity quantity;

	// Physical position and status
	private final LocatorId locatorId;

	private final String huStatus;

	// Reference
	private final String id;

	@Getter
	private final ZonedDateTime date;

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
				request.getProductId(),
				// Transaction Quantity/UOM
				// NOTE: we use source quantity/uom because those are in storage's internal UOM
				// to avoid precision errors while converting again from working UOM to internal storage UOM
				request.getQuantity().switchToSource().negateIf(outTrx),
				// Transaction Date:
				request.getDate());
	}

	public HUTransactionCandidate(
			final Object model,
			final I_M_HU_Item huItem,
			final I_M_HU_Item vhuItem,
			final ProductId productId,
			final Quantity quantity,
			final ZonedDateTime date)
	{
		this(model,
				huItem,
				vhuItem,
				productId,
				quantity,
				date,
				null, // locator, will be handled
				null); // huStatus, will be handled
	}

	@Builder
	public HUTransactionCandidate(
			final Object model,
			final I_M_HU_Item huItem,
			final I_M_HU_Item vhuItem,
			@NonNull final ProductId productId,
			@NonNull final Quantity quantity,
			@NonNull final ZonedDateTime date,
			@Nullable final LocatorId locatorId,
			@Nullable final String huStatus)
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
		this.productId = productId;

		// Qty/UOM
		this.quantity = quantity;

		// Transaction date
		this.date = date;

		final I_M_HU effectiveHU = getEffectiveHU();

		// Locator
		if (locatorId != null)
		{
			this.locatorId = locatorId;
		}
		else if (effectiveHU != null)
		{
			final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
			this.locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(effectiveHU.getM_Locator_ID());
		}
		else
		{
			this.locatorId = null;
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
				+ "product=" + productId
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
	public I_M_HU getVHU()
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
	public LocatorId getLocatorId()
	{
		return locatorId;
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
