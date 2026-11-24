package de.metas.ui.web.pickingslotsClearing.process;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHUWarehouseDAO;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowId;
import de.metas.ui.web.pickingslotsClearing.PickingSlotsClearingView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * metasfresh-webui-api
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

public abstract class PickingSlotsClearingViewBasedProcess extends ViewBasedProcessTemplate
{
	// services
	protected final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IHUWarehouseDAO huWarehouseDAO = Services.get(IHUWarehouseDAO.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	/**
	 * Same message as {@code LUTUProducerDestination}'s (private there). The LU item is resolved here, product-aware, instead of in
	 * {@code LUTUProducerDestination.getLUItemPI()}, whose lazy lookup knows no product: making that lookup product-aware would change
	 * every producer caller at once, which is out of this change's scope (product-specific LU items, picking-slot clearing only).
	 */
	private static final AdMessageKey ERR_LU_HAS_NO_TU_SUB_PACK_INSTR = AdMessageKey.of("ERR_LU_HAS_NO_TU_SUB_PACK_INSTR");

	public final PickingSlotsClearingView getPickingSlotsClearingView()
	{
		return getView(PickingSlotsClearingView.class);
	}

	protected final boolean isSingleSelectedPickingSlotRow()
	{
		return getSelectedRowIds().isSingleDocumentId();
	}

	/**
	 * @return single selected picking slot row (left side)
	 */
	protected final PickingSlotRow getSingleSelectedPickingSlotRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

	protected final I_M_HU getSingleSelectedPickingSlotTopLevelHU()
	{
		final PickingSlotRow huRow = getSingleSelectedPickingSlotRow();
		Check.assume(huRow.isTopLevelHU(), "row {} shall be a top level HU", huRow);
		return InterfaceWrapperHelper.load(huRow.getHuId(), I_M_HU.class);
	}

	protected final List<PickingSlotRow> getSelectedPickingSlotRows()
	{
		return streamSelectedRows()
				.map(PickingSlotRow::cast)
				.collect(ImmutableList.toImmutableList());
	}

	protected final List<I_M_HU> getSelectedPickingSlotTopLevelHUs()
	{
		return getSelectedPickingSlotRows()
				.stream()
				.peek(huRow -> Check.assume(huRow.isTopLevelHU(), "row {} shall be a top level HU", huRow))
				.map(PickingSlotRow::getHuId)
				.distinct()
				.map(huId -> InterfaceWrapperHelper.load(huId, I_M_HU.class))
				.collect(ImmutableList.toImmutableList());
	}

	protected final BigDecimal retrieveQtyCU(@NonNull final I_M_HU hu)
	{
		final IHUContext huContext = huContextFactory.createMutableHUContext();
		final IHUStorage fromHUStorage = huContext.getHUStorageFactory()
				.getStorage(hu);
		final ProductId productId = fromHUStorage.getSingleProductIdOrNull();
		if (productId == null)
		{
			return BigDecimal.ZERO;
		}

		final IHUProductStorage productStorage = fromHUStorage.getProductStorage(productId);
		return productStorage.getQty().toBigDecimal();

	}

	/**
	 * @return the actual picking slow row (the top level row)
	 */
	protected final PickingSlotRow getRootRowForSelectedPickingSlotRows()
	{
		final Set<PickingSlotRowId> rootRowIds = getRootRowIdsForSelectedPickingSlotRows();
		Check.assumeNotEmpty(rootRowIds, "rootRowIds is not empty");
		if (rootRowIds.size() > 1)
		{
			throw new AdempiereException("Select rows from one picking slot");
		}
		final PickingSlotRowId rootRowId = rootRowIds.iterator().next();

		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView();
		return pickingSlotsClearingView.getById(rootRowId);
	}

	protected final Set<PickingSlotRowId> getRootRowIdsForSelectedPickingSlotRows()
	{
		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView();
		return getSelectedPickingSlotRows()
				.stream()
				.map(row -> pickingSlotsClearingView.getRootRowIdWhichIncludesRowId(row.getPickingSlotRowId()))
				.collect(ImmutableSet.toImmutableSet());
	}

	protected final HUEditorView getPackingHUsView()
	{
		return getChildView(HUEditorView.class);
	}

	protected final DocumentIdsSelection getSelectedPackingHUsRowIds()
	{
		return getChildViewSelectedRowIds();
	}

	protected final boolean isSingleSelectedPackingHUsRow()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedPackingHUsRowIds();
		return selectedRowIds.isSingleDocumentId();
	}

	protected final HUEditorRow getSingleSelectedPackingHUsRow()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedPackingHUsRowIds();
		final DocumentId rowId = selectedRowIds.getSingleDocumentId();
		return getPackingHUsView().getById(rowId);
	}

	protected IAllocationRequestBuilder prepareUnloadRequest(@NonNull final I_M_HU fromHU, @NonNull final BigDecimal qtyCU)
	{
		Check.assume(qtyCU.signum() > 0, "qtyCU > 0");

		final IHUContext huContext = huContextFactory.createMutableHUContext();

		final IHUStorage fromHUStorage = huContext.getHUStorageFactory()
				.getStorage(fromHU);
		final ProductId productId = fromHUStorage.getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("Cannot determine the product to transfer from " + fromHU);
		}
		final IHUProductStorage productStorage = fromHUStorage.getProductStorage(productId);

		return AllocationUtils.builder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(qtyCU, productStorage.getC_UOM())
				.setDateAsToday()
				.setFromReferencedModel(null)
				.setForceQtyAllocation(false);
	}

	/**
	 * @param productId  the single product of the HUs to be moved, or {@code null} if they hold several products; used to pick the LU item when {@code targetHUPI} is an LU
	 * @param sourceHUs the HUs to be moved; when {@code targetHUPI} is an LU, their TU packing instruction decides which LU item is used
	 */
	protected final LUTUProducerDestination createNewHUProducer(
			@NonNull final PickingSlotRow pickingRow,
			@NonNull final I_M_HU_PI targetHUPI,
			@Nullable final ProductId productId,
			@NonNull final List<I_M_HU> sourceHUs)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(pickingRow.getBPartnerId());
		final int bpartnerLocationId = pickingRow.getBPartnerLocationId();

		final LocatorId locatorId = pickingRow.getPickingSlotLocatorId();
		final I_M_Locator locator = Services.get(IWarehouseDAO.class).getLocatorById(locatorId, I_M_Locator.class);
		if (!locator.isAfterPickingLocator())
		{
			throw new AdempiereException("Picking slot's locator is not an after picking locator: " + locator.getValue());
		}

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setBPartnerId(bpartnerId)
				.setC_BPartner_Location_ID(bpartnerLocationId)
				.setLocatorId(locatorId)
				.setHUStatus(X_M_HU.HUSTATUS_Picked);

		final String targetHuType = handlingUnitsBL.getHU_UnitType(targetHUPI);
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(targetHuType))
		{
			// the LU item (how many TUs of which TU packing go on the LU) is not implied by the LU packing instruction alone:
			// resolve it for the moved TUs' packing instruction and product, else the producer has no LU item to build on
			final I_M_HU_PI tuPI = getSourceTUPI(sourceHUs);
			final I_M_HU_PI_Item luItem = handlingUnitsDAO.retrieveFirstPIItem(
							HuPackingInstructionsId.ofRepoId(targetHUPI.getM_HU_PI_ID()),
							HuPackingInstructionsId.ofRepoId(tuPI.getM_HU_PI_ID()),
							bpartnerId,
							productId)
					.orElseThrow(() -> new HUException(ERR_LU_HAS_NO_TU_SUB_PACK_INSTR, targetHUPI.getName(), tuPI.getName()).markAsUserValidationError());

			lutuProducer.setLUPI(targetHUPI);
			lutuProducer.setLUItemPI(luItem);
			lutuProducer.setTUPI(luItem.getIncluded_HU_PI());
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(targetHuType))
		{
			lutuProducer.setNoLU();
			lutuProducer.setTUPI(targetHUPI);
		}
		return lutuProducer;
	}

	/**
	 * @return the packing instruction of the first source TU (for a source LU: of its first included TU).
	 * The pallet item is resolved for this one TU packing only; source HUs of another TU packing are not checked against it.
	 * A source without a TU (a CU, an LU without included TUs) yields its own packing instruction, for which no pallet item exists:
	 * the user then gets {@code ERR_LU_HAS_NO_TU_SUB_PACK_INSTR} naming that packing instruction.
	 */
	private I_M_HU_PI getSourceTUPI(@NonNull final List<I_M_HU> sourceHUs)
	{
		Check.assumeNotEmpty(sourceHUs, "sourceHUs is not empty");
		final I_M_HU sourceHU = sourceHUs.get(0);
		final I_M_HU tu = handlingUnitsBL.isLoadingUnit(sourceHU)
				? handlingUnitsDAO.retrieveIncludedHUs(sourceHU).stream().findFirst().orElse(sourceHU)
				: sourceHU;
		return handlingUnitsBL.getEffectivePI(tu);
	}

	/**
	 * @return the product that all the given HUs hold, or {@code null} if they hold several products (or none)
	 */
	@Nullable
	protected final ProductId getSingleProductIdOrNull(@NonNull final List<I_M_HU> hus)
	{
		final Set<ProductId> productIds = new HashSet<>();
		for (final I_M_HU hu : hus)
		{
			final ProductId productId = handlingUnitsBL.getStorageFactory().getStorage(hu).getSingleProductIdOrNull();
			if (productId == null)
			{
				return null;
			}
			productIds.add(productId);
		}
		return productIds.size() == 1 ? productIds.iterator().next() : null;
	}

	protected void moveToAfterPickingLocator(@NonNull final I_M_HU hu)
	{
		final String huStatus = hu.getHUStatus();

		// Move the HU to an after picking locator
		final LocatorId afterPickingLocatorId = huWarehouseDAO.suggestAfterPickingLocatorId(hu.getM_Locator_ID())
				.orElseThrow(() -> new AdempiereException("No after picking locator found for locatorId=" + hu.getM_Locator_ID()));

		if (afterPickingLocatorId.getRepoId() != hu.getM_Locator_ID())
		{
			huMovementBL.moveHUsToLocator(ImmutableList.of(hu), afterPickingLocatorId);

			//
			// FIXME: workaround to restore HU's HUStatus (i.e. which was changed from Picked to Active by the moveHUsToLocator() method, indirectly).
			// See https://github.com/metasfresh/metasfresh-webui-api/issues/678#issuecomment-344876035, that's the stacktrace where the HU status was set to Active.
			InterfaceWrapperHelper.refresh(hu, ITrx.TRXNAME_ThreadInherited);
			if (!Objects.equal(huStatus, hu.getHUStatus()))
			{
				final IHUContext huContext = huContextFactory.createMutableHUContext();
				huStatusBL.setHUStatus(huContext, hu, huStatus);
				save(hu);
			}
		}
	}
}
