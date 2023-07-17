package de.metas.ui.web.picking.pickingslot;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.process.BarcodeScannerType;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

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
	private static final AdMessageKey MSG_BarcodeFilter = AdMessageKey.of("webui.view.pickingSlot.filters.pickingSlotBarcodeFilter");

	public static DocumentFilterDescriptorsProvider createFilterDescriptorsProvider()
	{
		return ImmutableDocumentFilterDescriptorsProvider.of(createPickingSlotBarcodeFilters());
	}

	public static DocumentFilterDescriptor createPickingSlotBarcodeFilters()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(PickingSlotBarcodeFilter_FilterId)
				.setFrequentUsed(true)
				.setParametersLayoutType(PanelLayoutType.SingleOverlayField)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.fieldName(PARAM_Barcode)
						.displayName(TranslatableStrings.adMessage(MSG_BarcodeFilter))
						.mandatory(true)
						.widgetType(DocumentFieldWidgetType.Text)
						.barcodeScannerType(BarcodeScannerType.QRCode))
				.build();
	}

	@Nullable
	public static PickingSlotQRCode getPickingSlotQRCode(final DocumentFilterList filters)
	{
		final String barcodeString = StringUtils.trimBlankToNull(filters.getParamValueAsString(PickingSlotBarcodeFilter_FilterId, PARAM_Barcode));
		if (barcodeString == null)
		{
			return null;
		}

		//
		// Try parsing the Global QR Code, if possible
		final GlobalQRCode globalQRCode = GlobalQRCode.parse(barcodeString).orNullIfError();

		//
		// Case: valid Global QR Code
		if (globalQRCode != null)
		{
			return PickingSlotQRCode.ofGlobalQRCode(globalQRCode);
		}
		//
		// Case: Legacy barcode support
		else
		{
			final IPickingSlotDAO pickingSlotDAO = Services.get(IPickingSlotDAO.class);
			return pickingSlotDAO.getPickingSlotIdAndCaptionByCode(barcodeString)
					.map(PickingSlotQRCode::ofPickingSlotIdAndCaption)
					.orElseThrow(() -> new AdempiereException("Invalid barcode: " + barcodeString));
		}
	}

}
