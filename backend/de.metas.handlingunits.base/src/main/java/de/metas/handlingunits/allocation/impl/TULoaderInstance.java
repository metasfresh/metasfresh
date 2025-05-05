package de.metas.handlingunits.allocation.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

class TULoaderInstance
{
	@NonNull private final HUTransformService huTransformService;

	@NonNull private final IHUContext huContext;
	@NonNull private final I_M_HU_PI tuPI;
	@NonNull private final Capacity capacity;
	@Nullable private final BPartnerId bpartnerId;
	private final int bpartnerLocationRepoId;
	@Nullable private final LocatorId locatorId;
	@Nullable private final String huStatus;
	@Nullable private final ClearanceStatusInfo clearanceStatusInfo;

	@Nullable private I_M_HU _currentTU = null;
	@Nullable private Quantity _currentTUQty = null;
	@Nullable private AttributeSetAggregator _currentAttributes;

	@Builder
	private TULoaderInstance(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_HU_PI tuPI,
			@NonNull final Capacity capacity,
			@Nullable final BPartnerId bpartnerId,
			final int bpartnerLocationRepoId,
			@Nullable final LocatorId locatorId,
			@Nullable final String huStatus,
			@Nullable final ClearanceStatusInfo clearanceStatusInfo)
	{
		this.huContext = huContext;
		this.tuPI = tuPI;
		this.capacity = capacity;
		this.bpartnerId = bpartnerId;
		this.bpartnerLocationRepoId = bpartnerLocationRepoId;
		this.locatorId = locatorId;
		this.huStatus = huStatus;
		this.clearanceStatusInfo = clearanceStatusInfo;

		this.huTransformService = HUTransformService.newInstance(huContext);
	}

	void addCU(@NonNull final I_M_HU cuHU)
	{
		closeCurrentTUIfCapacityExceeded();

		final IHUProductStorage cuProductStorage = huContext.getHUStorageFactory().getStorage(cuHU).getSingleHUProductStorage();
		Quantity cuQtyRemaining = cuProductStorage.getQty();

		for (int iteration = 1; iteration <= 1000; iteration++)
		{
			if (cuQtyRemaining.isZero())
			{
				return;
			}

			final Quantity tuQtyRemaining = getCurrentTURemainingQty();
			if (cuQtyRemaining.compareTo(tuQtyRemaining) <= 0)
			{
				addCUWithQtyToCurrentTU(cuHU, cuQtyRemaining);
				break;
			}
			else
			{
				final I_M_HU cuToAdd = splitCU(cuHU, cuProductStorage.getProductId(), tuQtyRemaining);
				addCUWithQtyToCurrentTU(cuToAdd, tuQtyRemaining);
				cuQtyRemaining = cuQtyRemaining.subtract(tuQtyRemaining);
			}
		}
	}

	private void addCUWithQtyToCurrentTU(final I_M_HU cuHU, final Quantity cuQty)
	{
		final I_M_HU currentTU = getOrCreateCurrentTU();
		huTransformService.addCUsToTU(ImmutableList.of(cuHU), currentTU);
		addToCurrentTUQty(cuQty);

		if (_currentAttributes == null)
		{
			_currentAttributes = new AttributeSetAggregator();
		}
		_currentAttributes.collect(getAttributeStorage(cuHU));

		closeCurrentTUIfCapacityExceeded();
	}

	private IAttributeStorage getAttributeStorage(final I_M_HU cuHU)
	{
		return huContext.getHUAttributeStorageFactory().getAttributeStorage(cuHU);
	}

	private Quantity getCurrentTURemainingQty()
	{
		if (capacity.isInfiniteCapacity())
		{
			return Quantity.infinite(capacity.getC_UOM());
		}

		Quantity qtyRemaining = capacity.toQuantity();
		if (this._currentTUQty != null)
		{
			qtyRemaining = qtyRemaining.subtract(this._currentTUQty);
		}
		return qtyRemaining;
	}

	private I_M_HU getOrCreateCurrentTU()
	{
		if (_currentTU == null)
		{
			_currentTU = newHUBuilder().create(tuPI);
		}
		return _currentTU;
	}

	private void closeCurrentTUIfCapacityExceeded()
	{
		if (_currentTU != null && getCurrentTURemainingQty().signum() <= 0)
		{
			closeCurrentTU();
		}
	}

	void closeCurrentTU()
	{
		if (_currentTU != null && _currentAttributes != null)
		{
			final IAttributeStorage tuAttributes = getAttributeStorage(_currentTU);
			tuAttributes.setSaveOnChange(true);

			_currentAttributes.updateAggregatedValuesTo(tuAttributes);
		}

		_currentTU = null;
		_currentTUQty = null;
		_currentAttributes = null;
	}

	private void addToCurrentTUQty(final Quantity qtyToAdd)
	{
		Check.assumeNotNull(_currentTU, "current TU is created");

		if (this._currentTUQty == null)
		{
			this._currentTUQty = Quantity.zero(capacity.getC_UOM());
		}
		this._currentTUQty = this._currentTUQty.add(qtyToAdd);
	}

	private IHUBuilder newHUBuilder()
	{
		final IHUBuilder huBuilder = Services.get(IHandlingUnitsDAO.class).createHUBuilder(huContext);
		huBuilder.setLocatorId(locatorId);
		if (!Check.isEmpty(huStatus, true))
		{
			huBuilder.setHUStatus(huStatus);
		}
		if (bpartnerId != null)
		{
			huBuilder.setBPartnerId(bpartnerId);
		}
		if (bpartnerLocationRepoId > 0)
		{
			huBuilder.setC_BPartner_Location_ID(bpartnerLocationRepoId);
		}

		huBuilder.setHUPlanningReceiptOwnerPM(true);

		huBuilder.setHUClearanceStatusInfo(clearanceStatusInfo);

		return huBuilder;
	}

	private I_M_HU splitCU(final I_M_HU fromCU, final ProductId cuProductId, final Quantity qtyToSplit)
	{
		Check.assume(qtyToSplit.signum() > 0, "qtyToSplit shall be greater than zero but it was {}", qtyToSplit);

		final HUProducerDestination destination = HUProducerDestination.ofVirtualPI();
		HULoader.builder()
				.source(HUListAllocationSourceDestination.of(fromCU))
				.destination(destination)
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(cuProductId)
						.setQuantity(qtyToSplit)
						.setForceQtyAllocation(false) // no need to force
						.setClearanceStatusInfo(clearanceStatusInfo)
						.create());

		return CollectionUtils.singleElement(destination.getCreatedHUs());
	}
}
