/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.report;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CCache;
import de.metas.i18n.IModelTranslationMap;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Process_Para;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class DocumentPrintOptionDescriptorsRepository
{
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final CCache<AdProcessId, DocumentPrintOptionDescriptorsList> cache = CCache.<AdProcessId, DocumentPrintOptionDescriptorsList>builder()
			.tableName(I_AD_Process_Para.Table_Name)
			.build();

	public DocumentPrintOptionDescriptorsList getPrintingOptionDescriptors(@NonNull final AdProcessId reportProcessId)
	{
		return cache.getOrLoad(reportProcessId, this::retrievePrintingOptionDescriptors);
	}

	private DocumentPrintOptionDescriptorsList retrievePrintingOptionDescriptors(@NonNull final AdProcessId reportProcessId)
	{
		final ImmutableList<DocumentPrintOptionDescriptor> options = adProcessDAO.retrieveProcessParameters(reportProcessId)
				.stream()
				.map(DocumentPrintOptionDescriptorsRepository::extractDescriptorOrNull)
				.collect(ImmutableList.toImmutableList());

		return DocumentPrintOptionDescriptorsList.of(options);
	}

	@Nullable
	private static DocumentPrintOptionDescriptor extractDescriptorOrNull(final I_AD_Process_Para processPara)
	{
		final String parameterName = processPara.getColumnName();
		if (!parameterName.startsWith(DocumentPrintOptionDescriptor.PROCESS_PARAM_PRINTER_OPTIONS_PREFIX))
		{
			return null;
		}

		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(processPara);

		return DocumentPrintOptionDescriptor.builder()
				.internalName(parameterName)
				.name(trls.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Name, processPara.getName()))
				.description(trls.getColumnTrl(I_AD_Process_Para.COLUMNNAME_Description, processPara.getDescription()))
				.defaultValue(StringUtils.toBoolean(processPara.getDefaultValue()))
				.build();
	}

}
