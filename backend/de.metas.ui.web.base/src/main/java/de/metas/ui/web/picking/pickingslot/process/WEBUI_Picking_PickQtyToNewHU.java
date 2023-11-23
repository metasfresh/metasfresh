package de.metas.ui.web.picking.pickingslot.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReportWrapper;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.util.WEBUI_ProcessHelper;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKING_SLOT;

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

/**
 *
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_PickQtyToNewHU
		extends WEBUI_Picking_With_M_Source_HU_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	private static final String PARAM_M_HU_PI_Item_Product_ID = I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID;
	@Param(parameterName = PARAM_M_HU_PI_Item_Product_ID, mandatory = true)
	protected I_M_HU_PI_Item_Product huPIItemProduct;

	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU, mandatory = true)
	protected BigDecimal qtyCUsPerTU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<ProcessPreconditionsResolution> preconditionsResolution = checkValidSelection();

		if (preconditionsResolution.isPresent())
		{
			return preconditionsResolution.get();
		}

		if (isForceDelivery())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(" Use 'WEBUI_Picking_ForcePickToNewHU' in case of force shipping! ");
		}

		if (noSourceHUAvailable())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected Optional<ProcessPreconditionsResolution> checkValidSelection()
	{
		if (getParentViewRowIdsSelection() == null)
		{
			return Optional.of(ProcessPreconditionsResolution.rejectBecauseNoSelection());
		}

		if (getSelectedRowIds().isMoreThanOneDocumentId()
	        ||  getParentViewRowIdsSelection().getRowIds().isMoreThanOneDocumentId() )
		{
			return Optional.of(ProcessPreconditionsResolution.rejectBecauseNotSingleSelection());
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickingSlotRow())
		{
			return Optional.of(ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKING_SLOT)));
		}

		return Optional.empty();
	}

	protected Quantity getQtyToPack()
	{
		final I_C_UOM uom = getCurrentShipmentScheuduleUOM();
		return Quantity.of(qtyCUsPerTU, uom);
	}

	@Override
	protected String doIt()
	{
		final Quantity qtyToPack = getQtyToPack();
		if (qtyToPack.signum() <= 0)
		{
			throw new AdempiereException("@QtyCU@ > 0");
		}

		final HuId packToHuId = createNewHuId();
		final ImmutableList<HuId> sourceHUIds = getSourceHUIds();

		pickHUsAndPackTo(sourceHUIds, qtyToPack, packToHuId);

		printPickingLabel(packToHuId);

		invalidatePackablesView(); // left side view
		invalidatePickingSlotsView(); // right side view

		return MSG_OK;
	}

	protected void printPickingLabel(@NonNull final HuId huId)
	{
		final HUReportService huReportService = HUReportService.get();
		final IHandlingUnitsDAO handlingUnitsRepo = Services.get(IHandlingUnitsDAO.class);

		final I_M_HU hu = handlingUnitsRepo.getById(huId);
		final HUToReportWrapper huToReport = HUToReportWrapper.of(hu);
		final boolean onlyIfAutoPrintIsEnabled = true;
		huReportService.printPickingLabel(huToReport, onlyIfAutoPrintIsEnabled);
	}

	@NonNull
	protected HuId createNewHuId()
	{
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();

		final WarehouseId warehouseId = WarehouseId.ofRepoId(shipmentSchedule.getM_Warehouse_ID());
		final LocatorId defaultLocatorId = warehouseBL.getDefaultLocatorId(warehouseId);

		final I_M_HU hu = createTU(huPIItemProduct, defaultLocatorId);
		return HuId.ofRepoId(hu.getM_HU_ID());
	}

	/**
	 *
	 * @return a list of PI item products that match the selected shipment schedule's product and partner, sorted by name.
	 */
	@ProcessParamLookupValuesProvider(parameterName = PARAM_M_HU_PI_Item_Product_ID, dependsOn = {}, numericKey = true, lookupTableName = I_M_HU_PI_Item_Product.Table_Name)
	private LookupValuesList getM_HU_PI_Item_Products()
	{
		final Properties ctx = getCtx();
		final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();

		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		return WEBUI_ProcessHelper.retrieveHUPIItemProducts(ctx,
				productId,
				Services.get(IShipmentScheduleEffectiveBL.class).getBPartnerId(shipmentSchedule),
				true); // includeVirtualItem = true..similar case as with production
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_QtyCUsPerTU, parameter.getColumnName()))
		{
			return retrieveQtyToPick().toBigDecimal();
		}
		else if (Objects.equals(PARAM_M_HU_PI_Item_Product_ID, parameter.getColumnName()))
		{
			final I_M_ShipmentSchedule shipmentSchedule = getCurrentShipmentSchedule();
			final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoIdOrNull(shipmentSchedule.getM_HU_PI_Item_Product_ID());
			if (piItemProductId == null)
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}
			else
			{
				final IHUPIItemProductBL hupiItemProductBL = Services.get(IHUPIItemProductBL.class);

				final ITranslatableString displayName = hupiItemProductBL.getDisplayName(piItemProductId);
				return IntegerLookupValue.of(piItemProductId, displayName);
			}
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private final LocatorId getPickingSlotLocatorId(final PickingSlotRow pickingSlotRow)
	{
		if (pickingSlotRow.getPickingSlotLocatorId() != null)
		{
			return pickingSlotRow.getPickingSlotLocatorId();
		}

		final WarehouseId pickingSlotWarehouseId = pickingSlotRow.getPickingSlotWarehouseId();
		if (pickingSlotWarehouseId == null)
		{
			throw new AdempiereException("Picking slot with M_PickingSlot_ID=" + pickingSlotRow.getPickingSlotId() + " has no warehouse configured");
		}
		return Services.get(IWarehouseBL.class).getDefaultLocatorId(pickingSlotWarehouseId);
	}

	/**
	 * Creates a new M_HU within the processe's interited trx.
	 *
	 * @param itemProduct
	 * @param locatorId
	 * @return
	 */
	private static final I_M_HU createTU(
			@NonNull final I_M_HU_PI_Item_Product itemProduct,
			@NonNull final LocatorId locatorId)
	{
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		final I_M_HU_PI huPI = itemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI();

		return huTrxBL.createHUContextProcessorExecutor()
				.call(huContext -> handlingUnitsDAO.createHUBuilder(huContext)
						.setM_HU_Item_Parent(null) // no parent
						.setM_HU_PI_Item_Product(itemProduct)
						.setLocatorId(locatorId)

						// we are going to load from a "real" source HU onto this HU, so both shall be active. Otherwise it would look as if stuff was vanishing for the source HU
						.setHUStatus(X_M_HU.HUSTATUS_Active)

						.create(huPI));
	}
}
