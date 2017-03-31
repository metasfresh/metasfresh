package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
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
			.<JSONDocumentAction> comparingInt(action -> action.isEnabled() ? 0 : 1) // enabled actions first
			.thenComparingInt(action -> action.isDefaultQuickAction() ? 0 : 1) // Default QuickAction
			.thenComparingInt(action -> action.isQuickAction() ? 0 : 1) // QuickAction
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

	@JsonProperty("disabled")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean disabled;
	@JsonProperty("disabledReason")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String disabledReason;

	private final Map<String, Object> debugProperties;

	JSONDocumentAction(final WebuiRelatedProcessDescriptor relatedProcessDescriptor, final JSONOptions jsonOpts)
	{
		super();

		final String adLanguage = jsonOpts.getAD_Language();

		processId = relatedProcessDescriptor.getAD_Process_ID();
		caption = relatedProcessDescriptor.getCaption(adLanguage);
		description = relatedProcessDescriptor.getDescription(adLanguage);

		quickAction = relatedProcessDescriptor.isQuickAction();
		defaultQuickAction = relatedProcessDescriptor.isDefaultQuickAction();
		
		disabled = relatedProcessDescriptor.isDisabled() ? Boolean.TRUE : null;
		disabledReason = relatedProcessDescriptor.getDisabledReason(adLanguage);

		//
		// Debug properties
		if (jsonOpts.isProtocolDebugging())
		{
			debugProperties = relatedProcessDescriptor.getDebugProperties();
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
	
	@JsonIgnore
	public boolean isDisabled()
	{
		return disabled != null && disabled.booleanValue();
	}
	
	@JsonIgnore
	public boolean isEnabled()
	{
		return !isDisabled();
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
