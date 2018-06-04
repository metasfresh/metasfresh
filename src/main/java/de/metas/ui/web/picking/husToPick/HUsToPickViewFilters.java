package de.metas.ui.web.picking.husToPick;

import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.DB;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.i18n.IMsgBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.sql.SqlOptions;
import lombok.experimental.UtilityClass;

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

@UtilityClass
/* package */ class HUsToPickViewFilters
{
	private static final String LocatorBarcode_FilterId = "LocatorBarcodeFilter";
	private static final String PARAM_Barcode = "Barcode";

	private static final String HU_IDS_FilterId = "HU_IDS_Filter";
	private static final String PARAM_ConsiderAttributes = "ConsiderAttributes";

	public static ImmutableList<DocumentFilterDescriptor> createFilterDescriptors()
	{
		return ImmutableList.of(
				createLocatorBarcodeFilters(),
				createHUIdsFilter());
	}

	public static Map<String, SqlDocumentFilterConverter> createFilterConvertersIndexedByFilterId()
	{
		return ImmutableMap.<String, SqlDocumentFilterConverter> builder()

				.put(HU_IDS_FilterId, HUsToPickViewFilters::getHUIdsFilterSql)
				.put(LocatorBarcode_FilterId, HUsToPickViewFilters::getLocatorBarcodeFilterSql)
				.build();
	}

	private static final DocumentFilterDescriptor createLocatorBarcodeFilters()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(LocatorBarcode_FilterId)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_Barcode)
						.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText("webui.view.husToPick.filters.locatorBarcodeFilter"))
						.setMandatory(true)
						.setWidgetType(DocumentFieldWidgetType.Text))
				.build();
	}

	public static String getLocatorBarcode(final DocumentFiltersList filters)
	{
		return filters.getParamValueAsString(LocatorBarcode_FilterId, PARAM_Barcode);
	}

	public static String getLocatorBarcodeFilterSql(final SqlParamsCollector sqlParamsOut, final DocumentFilter filter, final SqlOptions sqlOpts, final IView view)
	{
		if (!LocatorBarcode_FilterId.equals(filter.getFilterId()))
		{
			throw new AdempiereException("Invalid filterId " + filter.getFilterId() + ". Expected: " + LocatorBarcode_FilterId);
		}

		final String barcode = filter.getParameterValueAsString(PARAM_Barcode);
		final int locatorId = Services.get(IWarehouseDAO.class).retrieveLocatorIdByBarcode(barcode);

		final String sql = sqlOpts.getTableNameOrAlias() + "." + I_M_HU.COLUMNNAME_M_Locator_ID + "=" + sqlParamsOut.placeholder(locatorId);
		return sql;
	}

	private static final DocumentFilterDescriptor createHUIdsFilter()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(HU_IDS_FilterId)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_ConsiderAttributes)
						.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText(PARAM_ConsiderAttributes))
						.setMandatory(true)
						.setWidgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	public static String getHUIdsFilterSql(final SqlParamsCollector sqlParamsOut, final DocumentFilter filter, final SqlOptions sqlOpts, final IView view)
	{
		if (!HU_IDS_FilterId.equals(filter.getFilterId()))
		{
			throw new AdempiereException("Invalid filterId " + filter.getFilterId() + ". Expected: " + HU_IDS_FilterId);
		}

		final Boolean considerAttributes = filter.getParameterValueAsBoolean(PARAM_ConsiderAttributes, true);
		
		if(! (view instanceof PickingSlotView))
		{
			return "/* no M_HU_ShipmentSchedule_ID */ 1=0";
		}
		
		final PickingSlotView pickingSlotView = (PickingSlotView) view;
		final int shipmentScheduleId = pickingSlotView.getCurrentShipmentScheduleId();//-1; // FIXME getView().getCurrentShipmentScheduleId();
		final List<Integer> huIds = retrieveAvailableHuIdsForCurrentShipmentScheduleId(shipmentScheduleId, considerAttributes);
		if (huIds.isEmpty())
		{
			return "/* no M_HU_IDs */ 1=0";
		}

		final String sql = sqlOpts.getTableNameOrAlias() + "." + I_M_HU.COLUMNNAME_M_HU_ID + " IN " + DB.buildSqlList(huIds);
		return sql;
	}

	private static List<Integer> retrieveAvailableHuIdsForCurrentShipmentScheduleId(final int shipmentScheduleId, final boolean considerAttributes)
	{
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

		final PickingHUsQuery query = PickingHUsQuery.builder()
				.shipmentSchedule(loadOutOfTrx(shipmentScheduleId, I_M_ShipmentSchedule.class))
				.onlyTopLevelHUs(false)
				.onlyIfAttributesMatchWithShipmentSchedules(considerAttributes)
				.build();
		final List<Integer> availableHUIdsToPick = huPickingSlotBL.retrieveAvailableHUIdsToPick(query);
		return availableHUIdsToPick;
	}
}
