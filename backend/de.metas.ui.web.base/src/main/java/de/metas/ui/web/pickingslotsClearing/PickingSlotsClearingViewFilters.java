package de.metas.ui.web.pickingslotsClearing;

import de.metas.bpartner.BPartnerId;
import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.IMsgBL;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFilters;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.util.Services;
import lombok.experimental.UtilityClass;
import org.compiere.util.DisplayType;

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
public class PickingSlotsClearingViewFilters
{
	private static final String FILTER_ID_BPartner = "BPartner";
	private static final String PARAM_C_BPartner_ID = I_C_BPartner.COLUMNNAME_C_BPartner_ID;

	public static DocumentFilterDescriptorsProvider createFilterDescriptorsProvider()
	{
		return ImmutableDocumentFilterDescriptorsProvider.of(
				PickingSlotViewFilters.createPickingSlotBarcodeFilters(),
				createBPartnerFilter());
	}

	private static DocumentFilterDescriptor createBPartnerFilter()
	{
		final LookupDescriptor bpartnerLookupDescriptor = LookupDescriptorProviders.sharedInstance().sql()
				.setCtxColumnName(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.setDisplayType(DisplayType.Search)
				.buildForDefaultScope();
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_BPartner)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.fieldName(PARAM_C_BPartner_ID)
						.displayName(Services.get(IMsgBL.class).translatable(PARAM_C_BPartner_ID))
						.mandatory(true)
						.widgetType(DocumentFieldWidgetType.Lookup)
						.lookupDescriptor(bpartnerLookupDescriptor))
				.build();

	}

	public static PickingSlotQRCode getPickingSlotQRCode(final DocumentFilterList filters)
	{
		return PickingSlotViewFilters.getPickingSlotQRCode(filters);
	}

	public static BPartnerId getBPartnerId(final DocumentFilterList filters)
	{
		return BPartnerId.ofRepoIdOrNull(filters.getParamValueAsInt(FILTER_ID_BPartner, PARAM_C_BPartner_ID, -1));
	}
}
