package de.metas.handlingunits.inventory.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyType;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.PlainAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class SyncInventoryQtyToHUsCommand
{
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final InventoryRepository inventoryRepository;
	private final SourceHUsService sourceHUsService;
	private final HUQRCodesService huQRCodesService;

	private final Inventory inventory;

	private static final String SYSCONFIG_IgnoreOnInventoryMinusAndNoHU = "inventory.IgnoreOnInventoryMinusAndNoHU";

	@Builder
	private SyncInventoryQtyToHUsCommand(
			@NonNull final InventoryRepository inventoryRepository,
			@NonNull final SourceHUsService sourceHUsService, 
			@NonNull final HUQRCodesService huQRCodesService,
			//
			@NonNull final Inventory inventory)
	{
		this.inventoryRepository = inventoryRepository;
		this.sourceHUsService = sourceHUsService;
		this.huQRCodesService = huQRCodesService;
		this.inventory = inventory;
	}

	public void execute()
	{
		for (final InventoryLine inventoryLine : inventory.getLines())
		{
			final Quantity qtyDiff = inventoryLine.getMovementQty();
			if (qtyDiff.signum() == 0
					|| (inventoryLine.getQtyBookFixed().signum() < 0 && inventoryLine.getQtyCountFixed().signum() == 0))
			{
				continue;
			}

			executeForInventoryLine(inventoryLine);
		}
	}

	private void executeForInventoryLine(final InventoryLine inventoryLine)
	{
		try
		{
			final InventoryLine resultingInventoryLine = syncQtyFromInventoryLineToHUs(inventoryLine);
			transferAttributesToHUs(resultingInventoryLine);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("inventoryLine", inventoryLine);
		}
	}

	private void transferAttributesToHUs(final InventoryLine inventoryLine)
	{
		for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
		{
			final Quantity qtyCountMinusBooked = inventoryLineHU.getQtyCountMinusBooked();
			final boolean noHU = inventoryLineHU.getHuId() == null;
			final boolean isMinusQty = qtyCountMinusBooked.signum() > 0;

			final boolean doNotTransferAttributes = noHU && isMinusQty && isIgnoreOnInventoryMinusAndNoHU();

			if (!doNotTransferAttributes)
			{
				final HuId huId = Check.assumeNotNull(inventoryLineHU.getHuId(), "Every inventoryLineHU instance needs to have an HuId; inventoryLineHU={}", inventoryLineHU);

				final I_M_HU hu = handlingUnitsDAO.getById(huId);
				transferAttributesToHU(inventoryLine, hu);
				handlingUnitsDAO.saveHU(hu);
			}
		}
	}

	private void transferAttributesToHU(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final I_M_HU hu)
	{
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor();

		executor.run(huContext -> {

			final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();
			final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();

			//
			// Transfer ASI attributes from inventory line to our HU
			final IAttributeStorage asiAttributeStorageFrom = attributeStorageFactory.getAttributeStorageIfHandled(extractAttributeSetInstanceAware(inventoryLine));
			if (asiAttributeStorageFrom == null)
			{
				return IHUContextProcessor.NULL_RESULT; // can't transfer from nothing
			}
			final IAttributeStorage huAttributeStorageTo = attributeStorageFactory.getAttributeStorage(hu);

			final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
			final IHUStorage huStorageFrom = storageFactory.getStorage(hu);

			final IHUAttributeTransferRequest request = new HUAttributeTransferRequestBuilder(huContext)
					.setProductId(inventoryLine.getProductId())
					.setQuantity(inventoryLine.getMovementQty())
					.setAttributeStorageFrom(asiAttributeStorageFrom)
					.setAttributeStorageTo(huAttributeStorageTo)
					.setHUStorageFrom(huStorageFrom)
					.create();

			trxAttributesBuilder.transferAttributes(request);

			return IHUContextProcessor.NULL_RESULT; // we don't care
		});
	}

	private static PlainAttributeSetInstanceAware extractAttributeSetInstanceAware(final InventoryLine inventoryLine)
	{
		return PlainAttributeSetInstanceAware.forProductIdAndAttributeSetInstanceId(
				inventoryLine.getProductId(),
				inventoryLine.getAsiId());
	}

	private InventoryLine syncQtyFromInventoryLineToHUs(@NonNull final InventoryLine inventoryLine)
	{
		final ArrayList<InventoryLineHU> resultInventoryLineHUs = new ArrayList<>();
		for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
		{
			try
			{
				final InventoryLineHU resultInventoryLineHU = syncQtyFromInventoryLineToHU(inventoryLine, inventoryLineHU);
				resultInventoryLineHUs.add(resultInventoryLineHU);
			}
			catch (final RuntimeException e)
			{
				throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
						.setParameter("inventoryLineHU", inventoryLineHU);
			}
		}

		final InventoryLine resultInventoryLine = inventoryLine.withInventoryLineHUs(resultInventoryLineHUs);

		if (!Objects.equals(inventoryLine, resultInventoryLine)
				&& inventoryLine.getQtyCountFixed().equals(resultInventoryLine.getQtyCount()) // do not adjust quantity in inventory line
		)
		{
			inventoryRepository.saveInventoryLine(resultInventoryLine, inventory.getId());
		}

		return resultInventoryLine;
	}

	private @NonNull InventoryLineHU syncQtyFromInventoryLineToHU(
			final @NonNull InventoryLine inventoryLine,
			final @NonNull InventoryLineHU inventoryLineHU)
	{
		final Quantity qtyCountMinusBooked = inventoryLineHU.getQtyCountMinusBooked();
		if (qtyCountMinusBooked.signum() == 0)
		{
			return inventoryLineHU;
		}

		final ProductId productId = inventoryLine.getProductId();
		final I_M_InventoryLine inventoryLineRecord = inventoryRepository.getInventoryLineRecordFor(inventoryLine);

			final IAllocationSource source;
			final IAllocationDestination destination;
			final Quantity qtyToTransfer;

		//
		// Case: HU has less than counted
		// => increase HU qty; source=inventoryLine, dest=HU
		if (qtyCountMinusBooked.signum() > 0)
		{
			qtyToTransfer = qtyCountMinusBooked;

			source = new GenericAllocationSourceDestination(
					new PlainProductStorage(productId, qtyToTransfer),
					inventoryLineRecord);

			destination = createAllocationDestination(inventoryLine, inventoryLineHU);
		}
		//
		// Case: HU has more than counted
		// => decrease HU qty; source=HU; dest=inventoryLine
		else // qtyCountNotBooked < 0
		{
			qtyToTransfer = qtyCountMinusBooked.negate();

			if (inventoryLineHU.getHuId() == null)
			{
				if (isIgnoreOnInventoryMinusAndNoHU())
				{
					return inventoryLineHU;
				}

				throw new AdempiereException("HU field shall be set when Qty Count is less than Booked for " + inventoryLine + ", qtyCountMinusBooked=" + qtyCountMinusBooked);
			}
			else
			{
				final I_M_HU hu = handlingUnitsDAO.getById(inventoryLineHU.getHuId());
				source = HUListAllocationSourceDestination.of(hu, AllocationStrategyType.UNIFORM)
						.setDestroyEmptyHUs(true);
			}

			destination = new GenericAllocationSourceDestination(
					new PlainProductStorage(productId, qtyToTransfer),
					inventoryLineRecord);
		}

		final IContextAware contextAware = InterfaceWrapperHelper.getContextAware(inventoryLineRecord);
		final IMutableHUContext huContextwithOrgId = huContextFactory.createMutableHUContext(contextAware);

		HULoader.of(source, destination)
				.load(AllocationUtils.builder()
						.setHUContext(huContextwithOrgId)
						.setDateAsToday()
						.setProduct(inventoryLine.getProductId())
						.setQuantity(qtyToTransfer)
						.setFromReferencedModel(inventoryLineRecord)
						.setForceQtyAllocation(true)
						.create());

		if (inventoryLineHU.getHuId() == null)
		{
			final HuId createdHUId = extractSingleCreatedHUId(destination);
			if(inventoryLineHU.getHuQRCode() != null)
			{
				huQRCodesService.assign(inventoryLineHU.getHuQRCode(), ImmutableSet.of(createdHUId));
			}

			sourceHUsService.addSourceHUMarkerIfCarringComponents(
					createdHUId,
					inventoryLine.getProductId(),
					inventoryLine.getLocatorId().getWarehouseId());

			return inventoryLineHU.withHuId(createdHUId);
		}
		else
		{
			return inventoryLineHU;
		}
	}

	private IAllocationDestination createAllocationDestination(final @NonNull InventoryLine inventoryLine, final @NonNull InventoryLineHU inventoryLineHU)
	{
		if (inventoryLineHU.getHuId() == null)
		{
			if (inventoryLineHU.getHuQRCode() != null)
			{
				return HUProducerDestination.of(inventoryLineHU.getHuQRCode().getPackingInstructionsId())
						.setHUStatus(X_M_HU.HUSTATUS_Active)
						.setLocatorId(inventoryLine.getLocatorId());
			}
			else
			{
				// TODO use inventoryLine.getM_HU_PI_Item_Product_ID() if set
				return HUProducerDestination.ofVirtualPI()
						.setHUStatus(X_M_HU.HUSTATUS_Active)
						.setLocatorId(inventoryLine.getLocatorId());
			}
		}
		else
		{
			final I_M_HU hu = handlingUnitsDAO.getById(inventoryLineHU.getHuId());
			return HUListAllocationSourceDestination.of(hu, AllocationStrategyType.UNIFORM);
		}
	}

	private static HuId extractSingleCreatedHUId(
			@NonNull final IAllocationDestination huDestination)
	{
		if (huDestination instanceof IHUProducerAllocationDestination)
		{
			final List<I_M_HU> createdHUs = ((IHUProducerAllocationDestination)huDestination).getCreatedHUs();
			if (createdHUs.isEmpty())
			{
				throw new HUException("No HU was created by " + huDestination);
			}
			else if (createdHUs.size() > 1)
			{
				throw new HUException("Only one HU expected to be created by " + huDestination);
			}
			else
			{
				return HuId.ofRepoId(createdHUs.get(0).getM_HU_ID());
			}
		}
		else
		{
			throw new HUException("No HU was created by " + huDestination);
		}
	}

	private boolean isIgnoreOnInventoryMinusAndNoHU()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_IgnoreOnInventoryMinusAndNoHU, false);
	}
}
