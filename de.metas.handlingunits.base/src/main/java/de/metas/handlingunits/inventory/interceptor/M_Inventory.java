package de.metas.handlingunits.inventory.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Inventory;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inventory.IHUInventoryBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.inventory.IInventoryBL;
import de.metas.inventory.IInventoryDAO;
import de.metas.quantity.Quantity;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@Interceptor(I_M_Inventory.class)
public class M_Inventory
{
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void beforeComplete(final I_M_Inventory inventory)
	{
		final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);
		final IInventoryBL inventoryBL = Services.get(IInventoryBL.class);

		for (final I_M_InventoryLine inventoryLine : inventoryDAO.retrieveLinesForInventoryId(inventory.getM_Inventory_ID(), I_M_InventoryLine.class))
		{
			final Quantity qtyDiff = inventoryBL.getMovementQty(inventoryLine);
			if (qtyDiff.signum() == 0)
			{
				continue;
			}
			else if (qtyDiff.signum() > 0)
			{
				addQtyDiffToHU(inventoryLine);
			}
			else // qtyDiff < 0
			{
				subtractQtyDiffFromHU(inventoryLine);
			}

			final I_M_HU hu = InterfaceWrapperHelper.load(inventoryLine.getM_HU_ID(), I_M_HU.class);
			setAttributes(hu, inventoryLine.getM_AttributeSetInstance());
			InterfaceWrapperHelper.save(hu);
		}
	}

	private void addQtyDiffToHU(final I_M_InventoryLine inventoryLine)
	{
		final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQty(inventoryLine);

		final IAllocationSource source = createInventoryLineAllocationSourceOrDestination(inventoryLine);
		final IAllocationDestination huDestination = createHUAllocationDestination(inventoryLine);

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
				.setDateAsToday()
				.setProduct(inventoryLine.getM_Product())
				.setQuantity(qtyDiff)
				.setFromReferencedModel(inventoryLine)
				.setForceQtyAllocation(true)
				.create();

		HULoader.of(source, huDestination)
				.load(request);

		if (inventoryLine.getM_HU_ID() <= 0)
		{
			inventoryLine.setM_HU_ID(extractSingleCreatedHUId(huDestination));
			InterfaceWrapperHelper.save(inventoryLine);
		}
	}

	private void subtractQtyDiffFromHU(final I_M_InventoryLine inventoryLine)
	{
		final int huId = inventoryLine.getM_HU_ID();
		if (huId <= 0)
		{
			throw new FillMandatoryException(I_M_InventoryLine.COLUMNNAME_M_HU_ID)
					.setParameter(I_M_InventoryLine.COLUMNNAME_Line, inventoryLine.getLine())
					.appendParametersToMessage();
		}

		final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQty(inventoryLine).negate();

		final IAllocationSource source = HUListAllocationSourceDestination.ofHUId(huId);
		final IAllocationDestination destination = createInventoryLineAllocationSourceOrDestination(inventoryLine);

		final IAllocationRequest request = AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(Services.get(IHUContextFactory.class).createMutableHUContext())
				.setDateAsToday()
				.setProduct(inventoryLine.getM_Product())
				.setQuantity(qtyDiff)
				.setFromReferencedModel(inventoryLine)
				.setForceQtyAllocation(true)
				.create();

		HULoader.of(source, destination)
				.load(request);
	}

	private GenericAllocationSourceDestination createInventoryLineAllocationSourceOrDestination(final I_M_InventoryLine inventoryLine)
	{
		final I_M_Product product = loadOutOfTrx(inventoryLine.getM_Product_ID(), I_M_Product.class);
		final Quantity qtyDiff = Services.get(IInventoryBL.class).getMovementQty(inventoryLine);
		final PlainProductStorage productStorage = new PlainProductStorage(product, qtyDiff.getUOM(), qtyDiff.getQty());
		return new GenericAllocationSourceDestination(productStorage, inventoryLine);
	}

	private IAllocationDestination createHUAllocationDestination(final I_M_InventoryLine inventoryLine)
	{
		if (inventoryLine.getM_HU_ID() > 0)
		{
			return HUListAllocationSourceDestination.ofHUId(inventoryLine.getM_HU_ID());
		}
		// TODO handle: else if(inventoryLine.getM_HU_PI_Item_Product_ID() > 0)
		else
		{
			return HUProducerDestination.ofVirtualPI()
					.setHUStatus(X_M_HU.HUSTATUS_Active)
					.setM_Locator(inventoryLine.getM_Locator());
		}
	}

	private int extractSingleCreatedHUId(final IAllocationDestination huDestination)
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
				return createdHUs.get(0).getM_HU_ID();
			}
		}
		else
		{
			throw new HUException("No HU was created by " + huDestination);
		}
	}

	private void setAttributes(final I_M_HU hu, final I_M_AttributeSetInstance attributeSetInstance)
	{
		final List<I_M_AttributeInstance> instances = Services.get(IAttributeDAO.class).retrieveAttributeInstances(attributeSetInstance);
		final IHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		final IAttributeStorage huAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);

		for (final I_M_AttributeInstance instance : instances)
		{
			final I_M_Attribute attribute = instance.getM_Attribute();
			final I_M_AttributeValue attrValue = instance.getM_AttributeValue();
			if (attribute != null)
			{
				huAttributeStorage.setValue(attribute, attrValue.getValue());
			}
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_REVERSECORRECT)
	public void reverseDisposal(final I_M_Inventory inventory)
	{
		final IHUInventoryBL huInventoryBL = Services.get(IHUInventoryBL.class);
		final IInventoryDAO inventoryDAO = Services.get(IInventoryDAO.class);

		if (!(huInventoryBL.isMaterialDisposal(inventory)))
		{
			return; // nothing to do
		}

		final String snapshotId = inventory.getSnapshot_UUID();
		if (Check.isEmpty(snapshotId, true))
		{
			throw new HUException("@NotFound@ @Snapshot_UUID@ (" + inventory + ")");
		}

		//
		// restore HUs from snapshots
		{
			final List<Integer> topLevelHUIds = inventoryDAO.retrieveLinesForInventoryId(inventory.getM_Inventory_ID(), I_M_InventoryLine.class)
					.stream()
					.map(I_M_InventoryLine::getM_HU_ID)
					.collect(ImmutableList.toImmutableList());

			Services.get(IHUSnapshotDAO.class).restoreHUs()
					.setContext(PlainContextAware.newWithThreadInheritedTrx())
					.setSnapshotId(snapshotId)
					.setDateTrx(inventory.getMovementDate())
					.addModelIds(topLevelHUIds)
					.setReferencedModel(inventory)
					.restoreFromSnapshot();
		}

		//
		// Reverse empties movements
		{
			final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
			Services.get(IMovementDAO.class)
					.retrieveMovementsForInventoryQuery(inventory.getM_Inventory_ID())
					.addEqualsFilter(I_M_Inventory.COLUMNNAME_DocStatus, X_M_Inventory.DOCSTATUS_Completed)
					.create()
					.stream()
					.forEach(emptiesMovement -> docActionBL.processEx(emptiesMovement, X_M_Inventory.DOCACTION_Reverse_Correct, X_M_Inventory.DOCSTATUS_Reversed));
		}
	}

}
