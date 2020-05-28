package de.metas.handlingunits.inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.PlainAttributeSetInstanceAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Inventory;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.isNew;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class InventoryLineRecordService
{

	private static final AdMessageKey MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE = AdMessageKey.of("de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType");

	private InventoryRepository inventoryLineRepository;

	private InventoryLineRecordService(@NonNull final InventoryRepository inventoryLineRepository)
	{
		this.inventoryLineRepository = inventoryLineRepository;
	}

	public void syncToHUs(@NonNull final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryLineRepository.toInventory(inventoryRecord);
		syncToHUs(inventory);
	}

	public void syncToHUs(@NonNull final Inventory inventory)
	{
		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);

		for (final InventoryLine inventoryLine : inventory.getLines())
		{
			// 'inventoryLine' might or might not have HUs..but inventoryLineWithHUs will have
			final InventoryLine inventoryLineWithHUs;

			final Quantity qtyDiff = inventoryLine.getMovementQty();
			if (qtyDiff.signum() == 0)
			{
				continue;
			}
			else if (qtyDiff.signum() > 0)
			{
				inventoryLineWithHUs = addQtyDiffToHU(inventoryLine, inventory.getId());
			}
			else // qtyDiff < 0
			{
				subtractQtyDiffFromHU(inventoryLine);
				inventoryLineWithHUs = inventoryLine;
			}

			//
			for (final InventoryLineHU inventoryLineHU : inventoryLineWithHUs.getInventoryLineHUs())
			{
				final HuId huId = Check.assumeNotNull(inventoryLineHU.getHuId(), "Every inventoryLineHU instance needs to have an HuId; inventoryLineHU={}", inventoryLineHU);

				final I_M_HU hu = handlingUnitsRepo.getById(huId);
				transferAttributesToHU(inventoryLine, hu);
				InterfaceWrapperHelper.save(hu);
			}
		}
	}

	private static PlainAttributeSetInstanceAware extractAttributeSetInstanceAware(final InventoryLine inventoryLine)
	{
		return PlainAttributeSetInstanceAware.forProductIdAndAttributeSetInstanceId(inventoryLine.getProductId(), inventoryLine.getAsiId());
	}

	private final void transferAttributesToHU(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final I_M_HU hu)
	{
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor();

		executor.run((IHUContextProcessor)huContext -> {

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

	private InventoryLine addQtyDiffToHU(@NonNull final InventoryLine inventoryLine, @NonNull final InventoryId inventoryId)
	{
		final InventoryLineBuilder result = inventoryLine.toBuilder()
				.clearInventoryLineHUs();

		boolean needToSaveInventoryLine = false;

		if (inventoryLine.isSingleHUAggregation())
		{
			final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);

			final Quantity qtyDiff = inventoryLine.getMovementQty();

			final IAllocationSource source = createInventoryLineAllocationSourceOrDestination(inventoryLineRecord);
			final IAllocationDestination huDestination = createHUAllocationDestination(inventoryLineRecord);

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
					.setDateAsToday()
					.setProduct(inventoryLine.getProductId())
					.setQuantity(qtyDiff)
					.setFromReferencedModel(inventoryLineRecord)
					.setForceQtyAllocation(true)
					.create();

			HULoader.of(source, huDestination)
					.load(request);

			if (inventoryLine.getSingleLineHU().getHuId() == null)
			{
				final InventoryLineHU resultInventoryLineHU = inventoryLine
						.getSingleLineHU()
						.toBuilder()
						.huId(extractSingleCreatedHUId(huDestination))
						.build();
				result.inventoryLineHU(resultInventoryLineHU);
				needToSaveInventoryLine = true;
			}
			else
			{
				result.inventoryLineHU(inventoryLine.getSingleLineHU());
			}
		}
		else
		{
			for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
			{
				final IAllocationDestination huDestination = syncQtyToInventoryLine(inventoryLine, inventoryLineHU);

				if (inventoryLineHU.getHuId() == null)
				{
					final InventoryLineHU resultInventoryLineHU = inventoryLine
							.getSingleLineHU()
							.toBuilder()
							.huId(extractSingleCreatedHUId(huDestination))
							.build();
					result.inventoryLineHU(resultInventoryLineHU);
					needToSaveInventoryLine = true;
				}
				else
				{
					result.inventoryLineHU(inventoryLineHU);
				}
			}
		}

		final InventoryLine resultInventoryLine = result.build();
		if (needToSaveInventoryLine)
		{
			inventoryLineRepository.saveInventoryLine(resultInventoryLine, inventoryId);
		}
		return resultInventoryLine;
	}

	private IAllocationDestination syncQtyToInventoryLine(
			@NonNull final InventoryLine inventoryLine,
			@NonNull final InventoryLineHU inventoryLineHU)
	{
		final Quantity qtyDiff = inventoryLineHU.getQtyCount().subtract(inventoryLineHU.getQtyBook());
		final ProductId productId = inventoryLine.getProductId();
		final PlainProductStorage productStorage = new PlainProductStorage(productId, qtyDiff.getUOM(), qtyDiff.toBigDecimal());

		final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);
		final IAllocationSource source = new GenericAllocationSourceDestination(productStorage, inventoryLineRecord);

		final IAllocationDestination huDestination;
		if (inventoryLineHU.getHuId() == null)
		{
			huDestination = HUProducerDestination.ofVirtualPI()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setLocatorId(inventoryLine.getLocatorId());
		}
		else
		{
			huDestination = HUListAllocationSourceDestination.ofHUId(inventoryLineHU.getHuId());
		}

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
				.setDateAsToday()
				.setProduct(inventoryLine.getProductId())
				.setQuantity(qtyDiff)
				.setFromReferencedModel(inventoryLineRecord)
				.setForceQtyAllocation(true)
				.create();

		HULoader.of(source, huDestination)
				.load(request);
		return huDestination;
	}

	private void subtractQtyDiffFromHU(final InventoryLine inventoryLine)
	{
		if (inventoryLine.isSingleHUAggregation())
		{
			final I_M_InventoryLine inventoryLineRecord = inventoryLineRepository.getInventoryLineRecordFor(inventoryLine);

			final HuId singleHuId = inventoryLine.getSingleLineHU().getHuId();
			if (singleHuId == null)
			{
				throw new FillMandatoryException(I_M_InventoryLine.COLUMNNAME_M_HU_ID)
						.setParameter(I_M_InventoryLine.COLUMNNAME_Line, inventoryLineRecord.getLine())
						.appendParametersToMessage();
			}

			final Quantity qtyDiff = inventoryLine.getMovementQty().negate();

			final IAllocationSource source = HUListAllocationSourceDestination.ofHUId(singleHuId);
			final IAllocationDestination destination = createInventoryLineAllocationSourceOrDestination(inventoryLineRecord);

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
					.setDateAsToday()
					.setProduct(inventoryLine.getProductId())
					.setQuantity(qtyDiff)
					.setFromReferencedModel(inventoryLineRecord)
					.setForceQtyAllocation(true)
					.create();

			HULoader.of(source, destination)
					.load(request);
		}
		else
		{
			for (final InventoryLineHU inventoryLineHU : inventoryLine.getInventoryLineHUs())
			{
				syncQtyToInventoryLine(inventoryLine, inventoryLineHU);
			}
		}
	}

	private static GenericAllocationSourceDestination createInventoryLineAllocationSourceOrDestination(final I_M_InventoryLine inventoryLine)
	{
		final ProductId productId = ProductId.ofRepoId(inventoryLine.getM_Product_ID());
		final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQty(inventoryLine);
		final PlainProductStorage productStorage = new PlainProductStorage(productId, qtyDiff.getUOM(), qtyDiff.toBigDecimal());
		return new GenericAllocationSourceDestination(productStorage, inventoryLine);
	}

	private static IAllocationDestination createHUAllocationDestination(final I_M_InventoryLine inventoryLine)
	{
		if (inventoryLine.getM_HU_ID() > 0)
		{
			return HUListAllocationSourceDestination.ofHUId(inventoryLine.getM_HU_ID());
		}
		// TODO handle: else if(inventoryLine.getM_HU_PI_Item_Product_ID() > 0)
		else
		{
			final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
			final LocatorId locatorId = warehousesRepo.getLocatorIdByRepoIdOrNull(inventoryLine.getM_Locator_ID());

			return HUProducerDestination.ofVirtualPI()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setLocatorId(locatorId);
		}
	}

	private static HuId extractSingleCreatedHUId(@NonNull final IAllocationDestination huDestination)
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

	public void updateHUAggregationTypeIfAllowed(@NonNull final I_M_Inventory inventoryRecord)
	{
		final Inventory inventory = inventoryLineRepository.toInventory(inventoryRecord);
		for (final InventoryLine inventoryLine : inventory.getLines())
		{
			final HUAggregationType huAggregationType = computeHUAggregationType(inventoryLine, inventory.getDocBaseAndSubType());
			inventoryLine.setHuAggregationType(huAggregationType);
		}

		inventoryLineRepository.saveInventoryLines(inventory);
	}

	public HUAggregationType computeHUAggregationType(
			@NonNull final I_M_InventoryLine inventoryLineRecord,
			@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		final InventoryLine inventoryLine;
		if (isNew(inventoryLineRecord))
		{
			// We might not even be able to create and inventoryLine since the record might not yet have e.g. a M_Product_ID.
			inventoryLine = null;
		}
		else
		{
			inventoryLine = inventoryLineRepository.toInventoryLine(inventoryLineRecord);
		}
		return computeHUAggregationType(inventoryLine, docBaseAndSubType);
	}

	private static HUAggregationType computeHUAggregationType(
			@Nullable final InventoryLine inventoryLine,
			@NonNull final DocBaseAndSubType baseAndSubType)
	{
		final HUAggregationType huAggregationTypeToUse = Optional
				.ofNullable(AggregationType.getByDocTypeOrNull(baseAndSubType))
				.map(AggregationType::getHuAggregationType)
				.orElse(HUAggregationType.SINGLE_HU); // the default

		if (inventoryLine == null)
		{
			return huAggregationTypeToUse; // nothing more to check
		}

		final HUAggregationType huAggregationTypeCurrent = inventoryLine.getHuAggregationType();
		if (huAggregationTypeCurrent == null)
		{
			return huAggregationTypeToUse;
		}
		else if (huAggregationTypeCurrent.equals(huAggregationTypeToUse))
		{
			return huAggregationTypeToUse;
		}
		else if (inventoryLine.getId() == null)
		{
			return huAggregationTypeToUse;
		}
		else
		{
			// this line already has a different aggregation type
			final ITranslatableString message = Services.get(IMsgBL.class)
					.getTranslatableMsgText(MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE);
			throw new AdempiereException(message).markAsUserValidationError();
		}
	}
}
