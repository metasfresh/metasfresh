package de.metas.ui.web.dataentry.interceptor;

import org.springframework.stereotype.Component;

import de.metas.dataentry.model.I_DataEntry_Field;
import de.metas.dataentry.model.I_DataEntry_Group;
import de.metas.dataentry.model.I_DataEntry_Line;
import de.metas.dataentry.model.I_DataEntry_ListValue;
import de.metas.dataentry.model.I_DataEntry_Section;
import de.metas.dataentry.model.I_DataEntry_SubGroup;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class DataEntryInterceptorUtil
{
	private final DocumentDescriptorFactory documentDescriptorFactory;

	private final DocumentCollection documentCollection;

	public DataEntryInterceptorUtil(
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final DocumentCollection documentCollection)
	{
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.documentCollection = documentCollection;
	}

	public void resetCacheFor(@NonNull final I_DataEntry_ListValue dataEntryListValueRecord)
	{
		if (dataEntryListValueRecord.getDataEntry_Field_ID() > 0)
		{
			resetCacheFor(dataEntryListValueRecord.getDataEntry_Field());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Field dataEntryFieldRecord)
	{
		if (dataEntryFieldRecord.getDataEntry_Line_ID() > 0)
		{
			resetCacheFor(dataEntryFieldRecord.getDataEntry_Line());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Line dataEntryLineRecord)
	{
		if (dataEntryLineRecord.getDataEntry_Section_ID() > 0)
		{
			resetCacheFor(dataEntryLineRecord.getDataEntry_Section());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Section dataEntrySectionRecord)
	{
		if (dataEntrySectionRecord.getDataEntry_SubGroup_ID() > 0)
		{
			resetCacheFor(dataEntrySectionRecord.getDataEntry_SubGroup());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_SubGroup dataEntrySubGroupRecord)
	{
		if (dataEntrySubGroupRecord.getDataEntry_Group_ID() > 0)
		{
			resetCacheFor(dataEntrySubGroupRecord.getDataEntry_Group());
		}
	}

	public void resetCacheFor(@NonNull final I_DataEntry_Group dataEntryGroupRecord)
	{
		final int windowId = dataEntryGroupRecord.getDataEntry_TargetWindow_ID();
		if (windowId > 0)
		{
			documentDescriptorFactory.invalidateForWindow(WindowId.of(windowId));
			documentCollection.cacheReset();
		}
	}

}
