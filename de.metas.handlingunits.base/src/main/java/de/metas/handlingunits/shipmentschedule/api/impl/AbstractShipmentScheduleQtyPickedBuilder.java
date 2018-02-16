package de.metas.handlingunits.shipmentschedule.api.impl;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * Class responsible for creating {@link I_M_ShipmentSchedule_QtyPicked} records based on {@link I_M_HU}s.
 *
 * @author tsa
 *
 */
public abstract class AbstractShipmentScheduleQtyPickedBuilder
{
	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private List<I_M_HU> _fromHUs;
	private I_M_HU _targetHU;

	//
	// Status
	private IHUContext _huContext = null;
	private boolean _qtyToPackEnforced = false;
	private Quantity _qtyToPackRemaining = null;

	public AbstractShipmentScheduleQtyPickedBuilder()
	{
		super();
	}

	/**
	 * @return processing {@link IHUContext}; never returns <code>null</code>
	 */
	protected final IHUContext getHUContext()
	{
		Check.assumeNotNull(_huContext, "_huContext not null");
		return _huContext;
	}

	protected final void setHUContext(final IHUContext huContext)
	{
		Check.assumeNotNull(huContext, "huContext not null");
		Check.assumeNull(_huContext, "huContext not already configured");
		_huContext = huContext;
	}

	/**
	 * Creates initial {@link IHUContext} to be used when performing.
	 *
	 * @return
	 */
	protected abstract IHUContext createHUContextInitial();

	/**
	 *
	 * @param fromHUs the HUs to assign to the shipment schedule. The all need be !out of transaction", i.e. have {@link InterfaceWrapperHelper#getTrxName(Object)} {@code ==} {@link ITrx#TRXNAME_None}
	 * @return
	 */
	public final AbstractShipmentScheduleQtyPickedBuilder setFromHUs(final Collection<I_M_HU> fromHUs)
	{
		assertConfigurable();

		if (fromHUs == null || fromHUs.isEmpty())
		{
			_fromHUs = Collections.emptyList();
		}
		else
		{
			_fromHUs = new ArrayList<>(fromHUs);
		}
		return this;
	}

	protected final List<I_M_HU> getFromHUs()
	{
		return _fromHUs;
	}

	public final void setTargetHU(final I_M_HU targetHU)
	{
		assertConfigurable();

		_targetHU = targetHU;
	}

	protected final I_M_HU getTargetHU()
	{
		return _targetHU;
	}

	public void setQtyToPack(@NonNull final Quantity qtyToPack)
	{
		assertConfigurable();
		Check.assume(qtyToPack.signum() > 0, "qtyToPack > 0 but it was {}", qtyToPack);

		_qtyToPackRemaining = qtyToPack;
		_qtyToPackEnforced = true;
	}

	/**
	 * @return Qty that remains to be package (in itemToPack's UOM)
	 */
	protected final Quantity getQtyToPackRemaining()
	{
		Check.assumeNotNull(_qtyToPackRemaining, "_qtyToPackRemaining not null");
		return _qtyToPackRemaining;
	}

	private final void subtractFromQtyToPackRemaining(final Quantity qtyPicked)
	{
		// Do nothing if we are not tracking remaining qty to pack
		if (!isQtyToPackEnforced())
		{
			return;
		}

		_qtyToPackRemaining = _qtyToPackRemaining.subtract(qtyPicked);
	}

	protected final boolean isQtyToPackEnforced()
	{
		return _qtyToPackEnforced;
	}

	/**
	 * @return true if we still have enforced quantity to pack; if the quantity to pack is not enforced, this method will always return <code>true</code>.
	 */
	protected final boolean hasRemainingQtyToPack()
	{
		// If the qtyToPack is not enforced, then we can allocate as much as we want
		if (!isQtyToPackEnforced())
		{
			return true;
		}

		return _qtyToPackRemaining.signum() > 0;
	}

	/**
	 * If {@link #isQtyToPackEnforced()} then adjusts <code>qtyToPack</code> based on {@link #getQtyToPackRemaining()}.
	 *
	 * @param qtyToPack
	 * @param uom
	 * @return qtyToPack (adjusted)
	 */
	protected final Quantity adjustQtyToPackConsideringRemaining(final Quantity qtyToPack)
	{
		// If we are not tracking the remaining qty to pack,
		// then directly accept the given quantity
		if (!isQtyToPackEnforced())
		{
			return qtyToPack;
		}

		// Make sure there is something remaining to be packed
		if (_qtyToPackRemaining.signum() <= 0)
		{
			return qtyToPack.toZero();
		}

		// Make sure QtyToPick is greather then zero
		// shall not happen, but just to be sure
		if (qtyToPack == null || qtyToPack.signum() <= 0)
		{
			return  qtyToPack.toZero();
		}

		final Quantity qtyToPackActual = _qtyToPackRemaining.min(qtyToPack);
		return qtyToPackActual;
	}

	/**
	 * Asserts this builder is still in configuration stage
	 */
	protected final void assertConfigurable()
	{
		final boolean configurable = _huContext == null; // i.e. processing HUContext was not already set
		Check.assume(configurable, "Builder is in configurable mode: {}", this);
	}

	/**
	 * Allocate given Virtual HU.
	 *
	 * NOTE: implementations of this method are responsible for invoking {@link #onQtyAllocated(I_M_ShipmentSchedule, BigDecimal, I_C_UOM, I_M_HU)}.
	 *
	 * @param vhu
	 */
	protected abstract void allocateVHU(final I_M_HU vhu);

	/**
	 * Allocates the given <code>hus</code> to this instance's current item to pack (see {@link #getItemToPack()}).
	 *
	 * The allocated qty will be the HUs' qty for the product that is currently packed (i.e. the qty will be defined by the handling units, not e.g. by the underlying shipment schedule's
	 * QtyToDeliver).
	 *
	 * The quantity that was allocated on HUs will be subtracted from {@link #getItemToPack()}.
	 *
	 * @param hus
	 */
	public void allocate()
	{
		// Make sure we did not run "allocate" before
		// i.e. this builder is still configurable
		assertConfigurable();

		//
		// Get the HUs which we need to allocate to shipment schedules
		// and make sure they are ok.
		final List<I_M_HU> hus = getFromHUs();
		// No HUs => nothing to
		if (hus != null && !hus.isEmpty())
		{
			// Make sure all HUs are out of transaction
			trxManager.assertModelsTrxName(ITrx.TRXNAME_None, hus);
		}

		//
		// Make sure we have remaining qty to pack
		if (!hasRemainingQtyToPack())
		{
			return;
		}

		//
		// Allocate
		final IHUContext huContextInitial = createHUContextInitial();
		huTrxBL.createHUContextProcessorExecutor(huContextInitial)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						setHUContext(huContext);
						allocate0();
						return NULL_RESULT;
					}
				});
	}

	private final void allocate0()
	{
		//
		// Iterate the HUs which we need to "back" allocate to our shipment schedules
		// and try allocating them.
		final List<I_M_HU> hus = getFromHUs();
		for (final I_M_HU hu : hus)
		{
			// Stop if we transfered everything
			if (!hasRemainingQtyToPack())
			{
				break;
			}

			allocateHU(hu);
		}

		//
		// If we have an enforced QtyToPack and if we did not allocate and then transfer everything to Target HU
		// then transfer the remaing qty now
		transferRemainingQtyToTargetHU();
	}

	protected abstract void transferRemainingQtyToTargetHU();

	/**
	 * Allocate given LU/TU
	 *
	 * @param hu LU or TU
	 */
	private final void allocateHU(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		//
		// Case: Loading Unit
		if (handlingUnitsBL.isLoadingUnit(hu))
		{
			final List<I_M_HU> tuHUs = handlingUnitsDAO.retrieveIncludedHUs(hu);
			for (final I_M_HU tuHU : tuHUs)
			{
				allocateTU(tuHU);
			}
		}
		//
		// Case: Transport Unit or Virtual HU
		else if (handlingUnitsBL.isTransportUnitOrVirtual(hu))
		{
			allocateTU(hu);
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @HU_UnitType@: " + handlingUnitsBL.getHU_UnitType(hu)
					+ "\n @M_HU_ID@: " + hu);
		}

		//
		// Make sure the HU (or some of its children) gets destroyed if the storage gets empty
		final IHUContext huContext = getHUContext();
		handlingUnitsBL.destroyIfEmptyStorage(huContext, hu);
	}

	/**
	 * Allocate given TU
	 *
	 * @param tuHU
	 */
	private final void allocateTU(final I_M_HU tuHU)
	{
		// Make sure we have remaining qty to pack
		if (!hasRemainingQtyToPack())
		{
			return;
		}

		// Case our TU is a VHU
		if (handlingUnitsBL.isVirtual(tuHU))
		{
			final I_M_HU vhu = tuHU;
			allocateVHU(vhu);
		}
		// Case our TU is really a TU (stricly speaking)
		else if (handlingUnitsBL.isTransportUnit(tuHU))
		{
			final List<I_M_HU> vhus = handlingUnitsDAO.retrieveIncludedHUs(tuHU);
			for (final I_M_HU vhu : vhus)
			{
				allocateVHU(vhu);
			}
		}
		else
		{
			throw new AdempiereException("@NotSupported@ @HU_UnitType@: " + handlingUnitsBL.getHU_UnitType(tuHU)
					+ "\n @M_HU_ID@: " + tuHU);
		}

	}

	protected final IProductStorage getProductStorage(final I_M_HU hu, final I_M_Product product)
	{
		final IHUContext huContext = getHUContext();
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		final IHUStorage huStorage = huStorageFactory.getStorage(hu);

		final IProductStorage productStorage = huStorage.getProductStorageOrNull(product);
		if (productStorage == null)
		{
			return null;
		}
		if (productStorage.isEmpty())
		{
			return null;
		}

		return productStorage;
	}

	/**
	 * Creates {@link I_M_ShipmentSchedule_QtyPicked} record to allocate given virtual HU.
	 *
	 * @param sched
	 * @param qtyPicked
	 * @param uom
	 * @param vhu
	 * @return created record
	 */
	protected final void onQtyAllocated(
			@NonNull final I_M_ShipmentSchedule sched,
			@NonNull final Quantity qtyPicked,
			@NonNull final I_M_HU vhu)
	{
		// "Back" allocate the qtyPicked from VHU to given shipment schedule
		huShipmentScheduleBL.addQtyPicked(sched, qtyPicked, vhu);

		// Transfer the qtyPicked from vhu to our target HU (if any)
		final I_M_HU targetHU = getTargetHU();
		if (targetHU != null)
		{
			final HUListAllocationSourceDestination source = HUListAllocationSourceDestination.of(vhu);
			final HUListAllocationSourceDestination destination = HUListAllocationSourceDestination.of(targetHU);

			final HULoader loader = HULoader.of(source, destination)
					.setAllowPartialUnloads(false)
					.setAllowPartialLoads(true);

			final IAllocationRequest request = createShipmentScheduleAllocationRequest(sched, qtyPicked);

			loader.load(request);
		}

		// Adjust remaining Qty to be packed
		subtractFromQtyToPackRemaining(qtyPicked);
	}

	protected final IAllocationRequest createShipmentScheduleAllocationRequest(
			final I_M_ShipmentSchedule sched,
			final Quantity qty)
	{
		// Force Qty Allocation: we need to allocate even if the HU is full
		// see http://dewiki908/mediawiki/index.php/05706_Meldung_im_Aktuellen_UAT%3F_Sollte_schon_weg_sein%2C_oder%3F_%28105871951705%29
		final boolean forceQtyAllocation = true;

		final IHUContext huContext = getHUContext();
		final I_M_Product product = sched.getM_Product();
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext,
				product,
				qty,
				huContext.getDate(), // date
				sched, // referenceModel,
				forceQtyAllocation);

		return request;
	}
}
