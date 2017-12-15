package de.metas.ui.web.picking.husToPick;

import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlParamsCollector;
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

	public static ImmutableList<DocumentFilterDescriptor> createFilterDescriptors()
	{
		return ImmutableList.of(createLocatorBarcodeFilters());
	}

	public static Map<String, SqlDocumentFilterConverter> createFilterConvertersIndexedByFilterId()
	{
		return ImmutableMap.<String, SqlDocumentFilterConverter> builder()
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

	public static String getLocatorBarcodeFilterSql(SqlParamsCollector sqlParamsOut, DocumentFilter filter, final SqlOptions sqlOpts)
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

}
