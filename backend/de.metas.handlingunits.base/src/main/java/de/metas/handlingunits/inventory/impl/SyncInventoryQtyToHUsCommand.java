package de.metas.handlingunits.inventory.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
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
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryLinePackingInstructions;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryAndLineId;
import de.metas.inventory.InventoryAndLineIdSet;
import de.metas.inventory.InventoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.PlainAttributeSetInstanceAware;
import org.adempiere.service.ISysConfigBL;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final InventoryRepository inventoryRepository;
	private final SourceHUsService sourceHUsService;
	private final HUQRCodesService huQRCodesService;

	private final Inventory inventory;

	private static final String SYSCONFIG_IgnoreOnInventoryMinusAndNoHU = "inventory.IgnoreOnInventoryMinusAndNoHU";

	private Map<InventoryAndLineId, I_M_InventoryLine> inventoryLineRecords; // lazy

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
		this.inventoryLineRecords = inventoryRepository.getInventoryLineRecordsByIds(extractInventoryAndLineIds(inventory));

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

	private InventoryAndLineIdSet extractInventoryAndLineIds(final Inventory inventory)
	{
		final InventoryId inventoryId = inventory.getId();
		return inventory.getLines()
				.stream()
				.map(inventoryLine -> InventoryAndLineId.of(inventoryId, inventoryLine.getIdNonNull()))
				.collect(InventoryAndLineIdSet.collect());
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
				transferAttributesToHU(inventoryLineHU, inventoryLine);
			}
		}
	}

	private void transferAttributesToHU(
			@NonNull final InventoryLineHU inventoryLineHU,
			@NonNull final InventoryLine inventoryLine)
	{
		final ProductId productId = inventoryLine.getProductId();
		final AttributeSetInstanceId lineAsiId = inventoryLineHU.getAsiId().orElseIfNone(inventoryLine.getAsiId());
		final PlainAttributeSetInstanceAware asiFrom = PlainAttributeSetInstanceAware.forProductIdAndAttributeSetInstanceId(productId, lineAsiId);

		final HuId huId = Check.assumeNotNull(inventoryLineHU.getHuId(), "Every inventoryLineHU instance needs to have an HuId; inventoryLineHU={}", inventoryLineHU);
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor();
		executor.run(huContext -> {
			final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();

			//
			// Transfer ASI attributes from the inventory line to our HU
			final IAttributeStorage asiAttributeStorageFrom = attributeStorageFactory.getAttributeStorageIfHandled(asiFrom);
			if (asiAttributeStorageFrom == null)
			{
				return IHUContextProcessor.NULL_RESULT; // nothing to transfer from
			}

			final IAttributeStorage huAttributeStorageTo = attributeStorageFactory.getAttributeStorage(hu);
			final IHUStorage huStorageFrom = huContext.getHUStorageFactory().getStorage(hu);

			executor.getTrxAttributesBuilder().transferAttributes(
					new HUAttributeTransferRequestBuilder(huContext)
							.setProductId(productId)
							.setQuantity(inventoryLineHU.getQtyCountMinusBooked())
							.setAttributeStorageFrom(asiAttributeStorageFrom)
							.setAttributeStorageTo(huAttributeStorageTo)
							.setHUStorageFrom(huStorageFrom)
							.create()
			);

			return IHUContextProcessor.NULL_RESULT; // we don't care
		});

		handlingUnitsBL.saveHU(hu);
	}

	private InventoryLine syncQtyFromInventoryLineToHUs(@NonNull final InventoryLine inventoryLine)
	{
		final ArrayList<InventoryLineHU> resultInventoryLineHUs = new ArrayList<>();
		for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
		{
			try
			{
				resultInventoryLineHUs.addAll(syncQtyFromInventoryLineToHU(inventoryLine, inventoryLineHU));
			}
			catch (final RuntimeException e)
			{
				throw AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
						.setParameter("inventoryLineHU", inventoryLineHU);
			}
		}

		recomputeQtyCountAndBook(resultInventoryLineHUs, inventoryLine.getQtyBookFixed());

		HUAggregationType huAggregationType = inventoryLine.getHuAggregationType();
		if (huAggregationType != null && huAggregationType.isSingleHU() && resultInventoryLineHUs.size() > 1)
		{
			huAggregationType = HUAggregationType.MULTI_HU;
		}

		final InventoryLine resultInventoryLine = inventoryLine.toBuilder()
				.huAggregationType(huAggregationType)
				.clearInventoryLineHUs()
				.inventoryLineHUs(resultInventoryLineHUs)
				.build();

		if (!Objects.equals(inventoryLine, resultInventoryLine)
				&& inventoryLine.getQtyCountFixed().equals(resultInventoryLine.getQtyCount()) // do not adjust quantity in inventory line
		)
		{
			inventoryRepository.saveInventoryLine(resultInventoryLine, inventory.getId());
		}

		return resultInventoryLine;
	}

	private void recomputeQtyCountAndBook(
			@NonNull final ArrayList<InventoryLineHU> inventoryLineHUs,
			@NonNull final Quantity qtyBookedStart)
	{
		for (int i = 0, size = inventoryLineHUs.size(); i < size; i++)
		{
			final InventoryLineHU lineHU = inventoryLineHUs.get(i);
			final Quantity lineQtyBooked;
			if (i == 0)
			{
				lineQtyBooked = qtyBookedStart;
			}
			else
			{
				lineQtyBooked = qtyBookedStart.toZero();
			}

			final Quantity lineQtyDiff = lineHU.getQtyCountMinusBooked();
			final Quantity lineQtyCount = lineQtyBooked.add(lineQtyDiff);

			inventoryLineHUs.set(i, lineHU.toBuilder()
					.qtyBook(lineQtyBooked)
					.qtyCount(lineQtyCount)
					.qtyInternalUse(null)
					.build());
		}
	}

	private @NonNull List<InventoryLineHU> syncQtyFromInventoryLineToHU(
			final @NonNull InventoryLine inventoryLine,
			final @NonNull InventoryLineHU inventoryLineHU)
	{
		final Quantity qtyCountMinusBooked = inventoryLineHU.getQtyCountMinusBooked();
		if (qtyCountMinusBooked.signum() == 0)
		{
			return ImmutableList.of(inventoryLineHU);
		}

		final ProductId productId = inventoryLine.getProductId();
		final I_M_InventoryLine inventoryLineRecord = getInventoryLineRecordFor(inventoryLine);

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
					return ImmutableList.of(inventoryLineHU);
				}

				throw new AdempiereException("HU field shall be set when Qty Count is less than Booked for " + inventoryLine + ", qtyCountMinusBooked=" + qtyCountMinusBooked);
			}
			else
			{
				final I_M_HU hu = handlingUnitsBL.getById(inventoryLineHU.getHuId());
				source = HUListAllocationSourceDestination.of(hu, AllocationStrategyType.UNIFORM)
						.setDestroyEmptyHUs(true);
			}

			destination = new GenericAllocationSourceDestination(
					new PlainProductStorage(productId, qtyToTransfer),
					inventoryLineRecord);
		}

		final IMutableHUContext huContext = huContextFactory.createMutableHUContext();
		HULoader.of(source, destination)
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setDateAsToday()
						.setProduct(inventoryLine.getProductId())
						.setQuantity(qtyToTransfer)
						.setFromReferencedModel(inventoryLineRecord)
						.setForceQtyAllocation(true)
						.create());

		if (inventoryLineHU.getHuId() == null)
		{
			final List<I_M_HU> createdHUs = extractCreatedHUs(destination);
			if (createdHUs.isEmpty())
			{
				throw new HUException("No HU was created by " + destination);
			}
			final ImmutableSet<HuId> createdHUIds = createdHUs.stream().map(hu -> HuId.ofRepoId(hu.getM_HU_ID())).collect(ImmutableSet.toImmutableSet());

			if (inventoryLineHU.getHuQRCode() != null)
			{
				huQRCodesService.assign(inventoryLineHU.getHuQRCode(), createdHUIds);
			}

			sourceHUsService.addSourceHUMarkerIfCarringComponents(
					createdHUIds,
					inventoryLine.getProductId(),
					inventoryLine.getLocatorId().getWarehouseId());

			final ArrayList<InventoryLineHU> result = new ArrayList<>(createdHUs.size());
			for (final I_M_HU createdHU : createdHUs)
			{
				final Quantity huQty = huContext.getHUStorageFactory().getStorage(createdHU).getProductStorage(inventoryLine.getProductId()).getQty();
				result.add(
						inventoryLineHU.toBuilder()
								.id(null)
								.huId(HuId.ofRepoId(createdHU.getM_HU_ID()))
								.qtyInternalUse(null)
								.qtyBook(huQty.toZero())
								.qtyCount(huQty)
								.build()
				);
			}
			return result;
		}
		else
		{
			return ImmutableList.of(inventoryLineHU);
		}
	}

	private I_M_InventoryLine getInventoryLineRecordFor(final @NotNull InventoryLine inventoryLine)
	{
		final InventoryAndLineId inventoryAndLineId = InventoryAndLineId.of(inventory.getId(), inventoryLine.getIdNonNull());
		final I_M_InventoryLine inventoryLineRecord = inventoryLineRecords.get(inventoryAndLineId);
		if (inventoryLineRecord == null)
		{
			throw new AdempiereException("No inventory line found for " + inventoryAndLineId);
		}
		return inventoryLineRecord;
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
				final InventoryLinePackingInstructions packingInstructions = inventoryLine.getPackingInstructions();
				if (packingInstructions.isVHU())
				{
					return HUProducerDestination.ofVirtualPI()
							.setHUStatus(X_M_HU.HUSTATUS_Active)
							.setLocatorId(inventoryLine.getLocatorId());
				}
				else
				{
					final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
					lutuProducer.setHUStatus(X_M_HU.HUSTATUS_Active);
					lutuProducer.setLocatorId(inventoryLine.getLocatorId());
					lutuProducer.setTUPI(packingInstructions.getTuPIItemProductId(), inventoryLine.getProductId());

					if (packingInstructions.getLuPIId() != null)
					{
						lutuProducer.setLUPI(packingInstructions.getLuPIId());
						lutuProducer.setMaxLUsInfinite();
					}
					else
					{
						lutuProducer.setNoLU();
					}

					return lutuProducer;
				}
			}
		}
		else
		{
			final I_M_HU hu = handlingUnitsBL.getById(inventoryLineHU.getHuId());
			return HUListAllocationSourceDestination.of(hu, AllocationStrategyType.UNIFORM);
		}
	}

	private static List<I_M_HU> extractCreatedHUs(
			@NonNull final IAllocationDestination huDestination)
	{
		if (huDestination instanceof IHUProducerAllocationDestination)
		{
			return ((IHUProducerAllocationDestination)huDestination).getCreatedHUs();
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
