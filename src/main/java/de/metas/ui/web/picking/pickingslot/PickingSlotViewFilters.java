package de.metas.ui.web.picking.pickingslot;

import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParam;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.view.CreateViewRequest.DocumentFiltersList;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
public class PickingSlotViewFilters
{
	private static final String PickingSlotBarcodeFilter_FilterId = "PickingSlotBarcodeFilter";
	private static final String PARAM_Barcode = "Barcode";

	public static final DocumentFilterDescriptor createPickingSlotBarcodeFilters()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(PickingSlotBarcodeFilter_FilterId)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_Barcode)
						.setDisplayName(Services.get(IMsgBL.class).getTranslatableMsgText("webui.view.pickingSlot.filters.pickingSlotBarcodeFilter"))
						.setMandatory(true)
						.setWidgetType(DocumentFieldWidgetType.Text))
				.build();
	}

	public static String getPickingSlotBarcode(final DocumentFiltersList filters)
	{
		return getFilterParamValueAsString(filters, PickingSlotBarcodeFilter_FilterId, PARAM_Barcode);
	}

	private static String getFilterParamValueAsString(final DocumentFiltersList filters, final String filterId, final String parameterName)
	{
		final DocumentFilter filter = filters.getFilterByIdOrNull(filterId);
		if (filter == null)
		{
			return null;
		}

		DocumentFilterParam barcodeParam = filter.getParameter(parameterName);
		if (barcodeParam == null)
		{
			return null;
		}

		return barcodeParam.getValueAsString();
	}

}
