package de.metas.ui.web.window.descriptor.factory;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.AdvancedSearchDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.util.Check;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.concurrent.ConcurrentHashMap;

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

@Component
public class AdvancedSearchDescriptorsProvider
{
	// services
	private static final Logger logger = LogManager.getLogger(AdvancedSearchDescriptorsProvider.class);
	@Autowired
	private DocumentDescriptorFactory documentDescriptors;

	private final ConcurrentHashMap<String, AdvancedSearchDescriptor> newRecordDescriptorsByTableName = new ConcurrentHashMap<>();

	AdvancedSearchDescriptorsProvider()
	{
		// FIXME: hardcoded AdvancedSearchDescriptor for C_BPartner_Adv_Search_v
		addNewRecordDescriptor(AdvancedSearchDescriptor.of(
				I_C_BPartner.Table_Name //
				, 541045 // AD_Window_ID
				, (document, idStr) -> {
					final int locId = Integer.parseInt(idStr);
					final I_C_BPartner_Location loc = InterfaceWrapperHelper.load(locId, I_C_BPartner_Location.class);
					Check.assumeNotNull(loc, "Cannot find C_BPartner_Location for ID: " + idStr);
					document.processValueChange(I_C_Order.COLUMNNAME_C_BPartner_Location_ID, loc.getC_BPartner_Location_ID(), null, false);
					document.processValueChange(I_C_Order.COLUMNNAME_C_BPartner_ID, loc.getC_BPartner_ID(), null, false);
				}));
	}

	public void addNewRecordDescriptor(final AdvancedSearchDescriptor searchDescriptor)
	{
		newRecordDescriptorsByTableName.put(searchDescriptor.getTableName(), searchDescriptor);
	}

	public AdvancedSearchDescriptor getAdvancedSearchDescriptorOrNull(final String tableName)
	{
		return newRecordDescriptorsByTableName.get(tableName);
	}

	/**
	 * @param windowId the window ID of the Advanced Search window
	 * @return corresponding record descriptor from static map
	 */
	public AdvancedSearchDescriptor getAdvancedSearchDescriptor(final WindowId windowId)
	{
		final int windowIdValue = windowId.toInt();

		return newRecordDescriptorsByTableName.values()
				.stream()
				.filter(descriptor -> windowIdValue == descriptor.getWindowId())
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No advanced search quick input defined windowId=" + windowId));
	}

	@Nullable
	public DocumentEntityDescriptor getAdvancedSearchDescriptorIfAvailable(final String tableName)
	{
		final AdvancedSearchDescriptor newRecordDescriptor = getAdvancedSearchDescriptorOrNull(tableName);
		if (newRecordDescriptor == null)
		{
			return null;
		}

		try
		{
			return documentDescriptors.getDocumentEntityDescriptor(newRecordDescriptor.getWindowId());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed fetching document entity descriptor for {}. Ignored", newRecordDescriptor, ex);
			return null;
		}
	}
}
