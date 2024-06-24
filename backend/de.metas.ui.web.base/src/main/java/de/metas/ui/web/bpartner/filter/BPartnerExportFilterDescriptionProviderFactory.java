/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.bpartner.filter;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner_Export;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;

@Component
public class BPartnerExportFilterDescriptionProviderFactory implements DocumentFilterDescriptorsProviderFactory
{

	private static final String C_BPARTNER_EXPORT_TABLE = I_C_BPartner_Export.Table_Name;
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	public BPartnerExportFilterDescriptionProviderFactory()
	{
	}

	@Override
	@NonNull
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final CreateFiltersProviderContext context,
			final @NonNull Collection<DocumentFieldDescriptor> fields)
	{
		if (!isValidTable(context.getTableName()))
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		return ImmutableDocumentFilterDescriptorsProvider.of(
				DocumentFilterDescriptor.builder()
						.setFilterId(BPartnerExportFilterConverter.FILTER_ID)
						.setFrequentUsed(true) // TODO ???
						.setDisplayName(msgBL.translatable("Postal"))
						//
						.addParameter(DocumentFilterParamDescriptor.builder()
											  .setMandatory(true)
											  .setFieldName(BPartnerExportFilterConverter.PARAM_POSTAL_FROM)
											  .setDisplayName(msgBL.translatable(BPartnerExportFilterConverter.PARAM_POSTAL_FROM))
											  .setWidgetType(DocumentFieldWidgetType.Text)
						)
						.addParameter(DocumentFilterParamDescriptor.builder()
											  .setMandatory(true)
											  .setFieldName(BPartnerExportFilterConverter.PARAM_POSTAL_TO)
											  .setDisplayName(msgBL.translatable(BPartnerExportFilterConverter.PARAM_POSTAL_TO))
											  .setWidgetType(DocumentFieldWidgetType.Text)
						)
						//
						.build()
		);
	}

	private boolean isValidTable(@Nullable final String tableName)
	{
		return C_BPARTNER_EXPORT_TABLE.equals(tableName);
	}
}
