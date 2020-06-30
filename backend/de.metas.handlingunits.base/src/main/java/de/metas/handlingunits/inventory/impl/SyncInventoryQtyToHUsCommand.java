package de.metas.handlingunits.inventory.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.PlainAttributeSetInstanceAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Env;

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
import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.InventoryLine;
import de.metas.handlingunits.inventory.InventoryLine.InventoryLineBuilder;
import de.metas.handlingunits.inventory.InventoryLineHU;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.inventory.AggregationType;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.InventoryId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

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
	private static final AdMessageKey MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE = AdMessageKey.of("de.metas.handlingunits.inventory.ExistingLinesWithDifferentHUAggregationType");

	private final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);
	private final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
	private final InventoryRepository inventoryRepository;

	private final Inventory inventory;

	@Builder
	private SyncInventoryQtyToHUsCommand(
			@NonNull final InventoryRepository inventoryRepository,
			@NonNull final Inventory inventory)
	{
		this.inventoryRepository = inventoryRepository;
		this.inventory = inventory;
	}

	public void execute()
	{
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
				handlingUnitsRepo.saveHU(hu);
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
			final I_M_InventoryLine inventoryLineRecord = inventoryRepository.getInventoryLineRecordFor(inventoryLine);

			final Quantity qtyDiff = inventoryLine.getMovementQty();

			final IAllocationSource source = createInventoryLineAllocationSourceOrDestination(inventoryLineRecord);
			final IAllocationDestination huDestination = createHUAllocationDestination(inventoryLineRecord);

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext(Env.getCtx(), ClientAndOrgId.ofClientAndOrg(inventoryLineRecord.getAD_Client_ID(), inventoryLineRecord.getAD_Org_ID())))
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
			inventoryRepository.saveInventoryLine(resultInventoryLine, inventoryId);
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

		final I_M_InventoryLine inventoryLineRecord = inventoryRepository.getInventoryLineRecordFor(inventoryLine);
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
			final I_M_HU hu = handlingUnitsRepo.getById(inventoryLineHU.getHuId());
			huDestination = HUListAllocationSourceDestination.of(hu);
		}

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContextFactory.createMutableHUContext())
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
			final I_M_InventoryLine inventoryLineRecord = inventoryRepository.getInventoryLineRecordFor(inventoryLine);

			final HuId singleHuId = inventoryLine.getSingleLineHU().getHuId();
			if (singleHuId == null)
			{
				throw new FillMandatoryException(I_M_InventoryLine.COLUMNNAME_M_HU_ID)
						.setParameter(I_M_InventoryLine.COLUMNNAME_Line, inventoryLineRecord.getLine())
						.appendParametersToMessage();
			}

			final Quantity qtyDiff = inventoryLine.getMovementQty().negate();

			final I_M_HU singleHU = handlingUnitsRepo.getById(singleHuId);
			final IAllocationSource source = HUListAllocationSourceDestination.of(singleHU);
			final IAllocationDestination destination = createInventoryLineAllocationSourceOrDestination(inventoryLineRecord);

			final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
					.setHUContext(huContextFactory.createMutableHUContext())
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

	private GenericAllocationSourceDestination createInventoryLineAllocationSourceOrDestination(final I_M_InventoryLine inventoryLine)
	{
		final ProductId productId = ProductId.ofRepoId(inventoryLine.getM_Product_ID());
		final Quantity qtyDiff = inventoryBL.getMovementQty(inventoryLine);
		final PlainProductStorage productStorage = new PlainProductStorage(productId, qtyDiff.getUOM(), qtyDiff.toBigDecimal());
		return new GenericAllocationSourceDestination(productStorage, inventoryLine);
	}

	private IAllocationDestination createHUAllocationDestination(final I_M_InventoryLine inventoryLine)
	{
		final HuId huId = HuId.ofRepoIdOrNull(inventoryLine.getM_HU_ID());
		if (huId != null)
		{
			final I_M_HU hu = handlingUnitsRepo.getById(huId);
			return HUListAllocationSourceDestination.of(hu);
		}
		// TODO handle: else if(inventoryLine.getM_HU_PI_Item_Product_ID() > 0)
		else
		{
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

	public static HUAggregationType computeHUAggregationType(
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
			throw new AdempiereException(MSG_EXISTING_LINES_WITH_DIFFERENT_HU_AGGREGATION_TYPE)
					.markAsUserValidationError();
		}
	}
}
