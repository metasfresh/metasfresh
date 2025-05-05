package de.metas.ui.web.picking.husToPick;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.requests.RetrieveAvailableHUIdsToPickRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.ShipmentScheduleId;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.sql.FilterSql;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterContext;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.DB;

import java.util.List;
import java.util.Objects;

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
class HUsToPickViewFilters
{
	private static final String LocatorBarcode_FilterId = "LocatorBarcodeFilter";
	private static final String PARAM_Barcode = "Barcode";
	private static final AdMessageKey MSG_LocatorBarcodeFilter = AdMessageKey.of("webui.view.husToPick.filters.locatorBarcodeFilter");

	private static final String HU_IDS_FilterId = "HU_IDS_Filter";
	private static final String PARAM_ConsiderAttributes = "IsConsiderAttributes";

	static final String PARAM_CurrentShipmentScheduleId = "CurrentShipmentScheduleId";
	static final String PARAM_BestBeforePolicy = "BestBeforePolicy";

	public static ImmutableList<DocumentFilterDescriptor> createFilterDescriptors()
	{
		return ImmutableList.of(
				createLocatorBarcodeFilterDescriptor(),
				createHUIdsFilterDescriptor());
	}

	public static List<SqlDocumentFilterConverter> createFilterConverters()
	{
		return ImmutableList.of(
				LocatorBarcodeFilterConverter.instance,
				HUIdsFilterConverter.instance);
	}

	private static DocumentFilterDescriptor createLocatorBarcodeFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(LocatorBarcode_FilterId)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.fieldName(PARAM_Barcode)
						.displayName(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_LocatorBarcodeFilter))
						.mandatory(true)
						.widgetType(DocumentFieldWidgetType.Text)
						.barcodeScannerType(BarcodeScannerType.QRCode))
				.build();
	}

	private static class LocatorBarcodeFilterConverter implements SqlDocumentFilterConverter
	{
		public static final transient LocatorBarcodeFilterConverter instance = new LocatorBarcodeFilterConverter();

		@Override
		public boolean canConvert(final String filterId)
		{
			return Objects.equals(filterId, LocatorBarcode_FilterId);
		}

		@Override
		public FilterSql getSql(
				final DocumentFilter filter,
				final SqlOptions sqlOpts,
				final SqlDocumentFilterConverterContext context)
		{
			if (!LocatorBarcode_FilterId.equals(filter.getFilterId()))
			{
				throw new AdempiereException("Invalid filterId " + filter.getFilterId() + ". Expected: " + LocatorBarcode_FilterId);
			}

			final String barcode = filter.getParameterValueAsString(PARAM_Barcode);
			final int locatorId = Services.get(IWarehouseDAO.class).retrieveLocatorIdByBarcode(barcode);

			return FilterSql.ofWhereClause(sqlOpts.getTableNameOrAlias() + "." + I_M_HU.COLUMNNAME_M_Locator_ID + "=?", locatorId);
		}
	}

	private static DocumentFilterDescriptor createHUIdsFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(HU_IDS_FilterId)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.fieldName(PARAM_ConsiderAttributes)
						.displayName(Services.get(IMsgBL.class).translatable(PARAM_ConsiderAttributes))
						.mandatory(false)
						.defaultValue(true)
						.widgetType(DocumentFieldWidgetType.YesNo))
				.build();
	}

	public static DocumentFilter createHUIdsFilter(final boolean considerAttributes)
	{
		return DocumentFilter.singleParameterFilter(HU_IDS_FilterId, PARAM_ConsiderAttributes, Operator.EQUAL, considerAttributes);
	}

	private static class HUIdsFilterConverter implements SqlDocumentFilterConverter
	{
		public static final transient HUIdsFilterConverter instance = new HUIdsFilterConverter();

		@Override
		public boolean canConvert(final String filterId)
		{
			return Objects.equals(filterId, HU_IDS_FilterId);
		}

		@Override
		public FilterSql getSql(
				final DocumentFilter filter,
				final SqlOptions sqlOpts,
				final SqlDocumentFilterConverterContext context)
		{
			if (!HU_IDS_FilterId.equals(filter.getFilterId()))
			{
				throw new AdempiereException("Invalid filterId " + filter.getFilterId() + ". Expected: " + HU_IDS_FilterId);
			}

			final int shipmentScheduleId = context.getPropertyAsInt(PARAM_CurrentShipmentScheduleId, -1);
			if (shipmentScheduleId <= 0)
			{
				return FilterSql.allowNoneWithComment("no shipment schedule");
			}

			final boolean considerAttributes = filter.getParameterValueAsBoolean(PARAM_ConsiderAttributes, false);
			final List<Integer> huIds = retrieveAvailableHuIdsForCurrentShipmentScheduleId(shipmentScheduleId, considerAttributes);
			if (huIds.isEmpty())
			{
				return FilterSql.allowNoneWithComment("no M_HU_IDs");
			}

			return FilterSql.ofWhereClause(sqlOpts.getTableNameOrAlias() + "." + I_M_HU.COLUMNNAME_M_HU_ID + " IN " + DB.buildSqlList(huIds));
		}
	}

	private static List<Integer> retrieveAvailableHuIdsForCurrentShipmentScheduleId(
			final int shipmentScheduleId,
			final boolean considerAttributes)
	{
		final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

		final ShipmentScheduleId sScheduleID = ShipmentScheduleId.ofRepoId(shipmentScheduleId);

		final RetrieveAvailableHUIdsToPickRequest request = RetrieveAvailableHUIdsToPickRequest
				.builder()
				.scheduleId(sScheduleID)
				.considerAttributes(considerAttributes)
				.onlyTopLevel(false)
				.build();

		return huPickingSlotBL.retrieveAvailableHUIdsToPickForShipmentSchedule(request)
				.stream()
				.map(HuId::getRepoId)
				.collect(ImmutableList.toImmutableList());
	}
}
