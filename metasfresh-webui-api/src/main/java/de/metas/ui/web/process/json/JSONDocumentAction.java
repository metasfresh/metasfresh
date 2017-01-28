package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.RelatedProcessDescriptorWrapper;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public final class JSONDocumentAction implements Serializable
{
	public static final Comparator<JSONDocumentAction> ORDERBY_QuickActionFirst_Caption = Comparator
			.<JSONDocumentAction, Boolean> comparing(action -> !action.isDefaultQuickAction()) // Default QuickAction first
			.thenComparing(action -> !action.isQuickAction()) // QuickAction
			.thenComparing(JSONDocumentAction::getCaption) // Caption
			;

	@JsonProperty("processId")
	private final int processId;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("description")
	private final String description;
	@JsonProperty("quickAction")
	private final boolean quickAction;
	@JsonProperty("defaultQuickAction")
	private final boolean defaultQuickAction;

	private final Map<String, Object> debugProperties;

	JSONDocumentAction(final RelatedProcessDescriptorWrapper relatedProcessDescriptorWrapper, final JSONOptions jsonOpts)
	{
		super();

		final String adLanguage = jsonOpts.getAD_Language();

		final ProcessDescriptor processDescriptor = relatedProcessDescriptorWrapper.getProcessDescriptor();
		processId = processDescriptor.getAD_Process_ID();
		caption = processDescriptor.getCaption(adLanguage);
		description = processDescriptor.getDescription(adLanguage);

		quickAction = relatedProcessDescriptorWrapper.isQuickAction();
		defaultQuickAction = relatedProcessDescriptorWrapper.isDefaultQuickAction();

		//
		// Debug properties
		if (jsonOpts.isProtocolDebugging())
		{
			final ImmutableMap.Builder<String, Object> debugProperties = ImmutableMap.<String, Object> builder();

			final String processClassname = processDescriptor.getProcessClassname();
			if (processClassname != null)
			{
				debugProperties.put("debug-classname", processClassname);
			}

			this.debugProperties = debugProperties.build();
		}
		else
		{
			debugProperties = ImmutableMap.of();
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("processId", processId)
				.add("quickAction", quickAction)
				.add("defaultQuickAction", defaultQuickAction)
				.toString();
	}

	public int getProcessId()
	{
		return processId;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}

	public boolean isQuickAction()
	{
		return quickAction;
	}

	public boolean isDefaultQuickAction()
	{
		return defaultQuickAction;
	}

	@JsonAnyGetter
	public Map<String, Object> getDebugProperties()
	{
		return debugProperties;
	}
}
