package de.metas.ui.web.window.descriptor.factory;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.NewRecordDescriptor;
import de.metas.util.Services;

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
 * Provides {@link NewRecordDescriptor}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @task https://github.com/metasfresh/metasfresh/issues/1090
 */
@Component
public class NewRecordDescriptorsProvider
{
	// services
	private static final Logger logger = LogManager.getLogger(NewRecordDescriptorsProvider.class);
	@Autowired
	private DocumentDescriptorFactory documentDescriptors;

	private final ConcurrentHashMap<String, NewRecordDescriptor> newRecordDescriptorsByTableName = new ConcurrentHashMap<>();

	NewRecordDescriptorsProvider()
	{
		// FIXME: hardcoded NewRecordDescriptor for C_BPartner_QuickInput
		addNewRecordDescriptor(NewRecordDescriptor.of(
				I_C_BPartner.Table_Name //
				, 540327 // AD_Window_ID
				, document -> {
					final I_C_BPartner_QuickInput template = InterfaceWrapperHelper.getPO(document);
					Services.get(IBPartnerBL.class).createFromTemplate(template);
					return template.getC_BPartner_ID();
				}));
	}

	public void addNewRecordDescriptor(final NewRecordDescriptor newRecordDescriptor)
	{
		newRecordDescriptorsByTableName.put(newRecordDescriptor.getTableName(), newRecordDescriptor);
	}

	public NewRecordDescriptor getNewRecordDescriptorOrNull(final String tableName)
	{
		return newRecordDescriptorsByTableName.get(tableName);
	}

	/**
	 * @param entityDescriptor the entity descriptor of the quick input window (e.g. for BPartner that is C_BPartner_QuickInput)
	 * @return new record descriptor
	 */
	public NewRecordDescriptor getNewRecordDescriptor(final DocumentEntityDescriptor entityDescriptor)
	{
		final int newRecordWindowId = entityDescriptor.getWindowId().toInt();

		return newRecordDescriptorsByTableName.values()
				.stream()
				.filter(descriptor -> newRecordWindowId == descriptor.getNewRecordWindowId())
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No new record quick input defined windowId=" + newRecordWindowId));
	}

	public DocumentEntityDescriptor getNewRecordEntityDescriptorIfAvailable(final String tableName)
	{
		final NewRecordDescriptor newRecordDescriptor = getNewRecordDescriptorOrNull(tableName);
		if (newRecordDescriptor == null)
		{
			return null;
		}

		try
		{
			return documentDescriptors.getDocumentEntityDescriptor(newRecordDescriptor.getNewRecordWindowId());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching document entity descriptor for {}. Ignored", newRecordDescriptor, ex);
			return null;
		}
	}
}
