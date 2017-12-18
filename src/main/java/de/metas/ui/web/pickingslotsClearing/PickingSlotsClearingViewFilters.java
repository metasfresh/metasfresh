package de.metas.ui.web.pickingslotsClearing;

import org.adempiere.util.Services;
import org.compiere.util.DisplayType;

import de.metas.document.archive.model.I_C_BPartner;
import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.DocumentFiltersList;
import de.metas.ui.web.document.filter.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFilters;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
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
		final LookupDescriptor bpartnerLookupDescriptor = SqlLookupDescriptor.builder()
				.setCtxColumnName(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.setDisplayType(DisplayType.Search)
				.buildForDefaultScope();
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_BPartner)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_C_BPartner_ID)
						.setDisplayName(Services.get(IMsgBL.class).translatable(PARAM_C_BPartner_ID))
						.setMandatory(true)
						.setWidgetType(DocumentFieldWidgetType.Lookup)
						.setLookupDescriptor(bpartnerLookupDescriptor))
				.build();

	}

	public static String getPickingSlotBarcode(final DocumentFiltersList filters)
	{
		return PickingSlotViewFilters.getPickingSlotBarcode(filters);
	}

	public static int getBPartnerId(final DocumentFiltersList filters)
	{
		return filters.getParamValueAsInt(FILTER_ID_BPartner, PARAM_C_BPartner_ID, -1);
	}
}
