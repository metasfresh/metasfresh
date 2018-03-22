package de.metas.ui.web.pickingslotsClearing.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IAllocationRequestBuilder;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_Locator;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowId;
import de.metas.ui.web.pickingslotsClearing.PickingSlotsClearingView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

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

	public final PickingSlotsClearingView getPickingSlotsClearingView()
	{
		return getView(PickingSlotsClearingView.class);
	}

	public final void invalidatePickingSlotsClearingView()
	{
		invalidateView(getPickingSlotsClearingView().getViewId());
	}

	protected final boolean isSingleSelectedPickingSlotRow()
	{
		return getSelectedRowIds().isSingleDocumentId();
	}

	/** @return single selected picking slot row (left side) */
	protected final PickingSlotRow getSingleSelectedPickingSlotRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}

	protected final I_M_HU getSingleSelectedPickingSlotTopLevelHU()
	{
		final PickingSlotRow huRow = getSingleSelectedPickingSlotRow();
		Check.assume(huRow.isTopLevelHU(), "row {} shall be a top level HU", huRow);
		final I_M_HU fromHU = InterfaceWrapperHelper.load(huRow.getHuId(), I_M_HU.class);
		return fromHU;
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
				.map(huRow -> huRow.getHuId())
				.distinct()
				.map(huId -> InterfaceWrapperHelper.load(huId, I_M_HU.class))
				.collect(ImmutableList.toImmutableList());
	}

	protected final BigDecimal retrieveQtyCU(@NonNull final I_M_HU hu)
	{
		final IHUContext huContext = huContextFactory.createMutableHUContext();
		final IHUStorage fromHUStorage = huContext.getHUStorageFactory()
				.getStorage(hu);
		final I_M_Product product = fromHUStorage.getSingleProductOrNull();
		if (product == null)
		{
			return BigDecimal.ZERO;
		}

		final IHUProductStorage productStorage = fromHUStorage.getProductStorage(product);
		return productStorage.getQty();

	}

	/** @return the actual picking slow row (the top level row) */
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
		final I_M_Product product = fromHUStorage.getSingleProductOrNull();
		if (product == null)
		{
			throw new AdempiereException("Cannot determine the product to transfer from " + fromHU);
		}
		final IHUProductStorage productStorage = fromHUStorage.getProductStorage(product);

		return AllocationUtils.createAllocationRequestBuilder()
				.setHUContext(huContext)
				.setProduct(product)
				.setQuantity(qtyCU, productStorage.getC_UOM())
				.setDateAsToday()
				.setFromReferencedModel(null)
				.setForceQtyAllocation(false);
	}

	protected final LUTUProducerDestination createNewHUProducer(
			@NonNull final PickingSlotRow pickingRow,
			@NonNull final I_M_HU_PI targetHUPI)
	{
		final int bpartnerId = pickingRow.getBPartnerId();
		final I_C_BPartner bpartner = bpartnerId > 0 ? load(bpartnerId, I_C_BPartner.class) : null;
		final int bpartnerLocationId = pickingRow.getBPartnerLocationId();

		final int locatorId = pickingRow.getPickingSlotLocatorId();
		final I_M_Locator locator = load(locatorId, I_M_Locator.class);
		if (!locator.isAfterPickingLocator())
		{
			throw new AdempiereException("Picking slot's locator is not an after picking locator: " + locator.getValue());
		}

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setC_BPartner(bpartner)
				.setC_BPartner_Location_ID(bpartnerLocationId)
				.setM_Locator(locator)
				.setHUStatus(X_M_HU.HUSTATUS_Picked);

		final String targetHuType = Services.get(IHandlingUnitsBL.class).getHU_UnitType(targetHUPI);
		if (X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(targetHuType))
		{
			lutuProducer.setLUPI(targetHUPI);
		}
		else if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(targetHuType))
		{
			pickingRow.getHuProductId();

			lutuProducer.setNoLU();
			lutuProducer.setTUPI(targetHUPI);
		}
		return lutuProducer;
	}

}
