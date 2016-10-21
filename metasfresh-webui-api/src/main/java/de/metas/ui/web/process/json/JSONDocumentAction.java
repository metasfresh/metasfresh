package de.metas.ui.web.process.json;

import java.util.List;
import java.util.Map;

import org.adempiere.ad.process.ISvrProcessPrecondition;
import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.window.datatypes.json.JSONFilteringOptions;

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

public class JSONDocumentAction
{
	static final List<JSONDocumentAction> ofList(final List<ProcessDescriptor> processDescriptors, final JSONFilteringOptions jsonOpts)
	{
		return processDescriptors.stream()
				.map(processDescriptor -> new JSONDocumentAction(processDescriptor, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	@JsonProperty("actionId")
	private final int actionId;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("description")
	private final String description;

	private final Map<String, Object> debugProperties;

	private JSONDocumentAction(final ProcessDescriptor processDescriptor, final JSONFilteringOptions jsonOpts)
	{
		super();
		
		final String adLanguage = jsonOpts.getAD_Language();
		
		actionId = processDescriptor.getActionId();
		caption = processDescriptor.getCaption(adLanguage);
		description = processDescriptor.getDescription(adLanguage);

		if (jsonOpts.isProtocolDebugging())
		{
			final ImmutableMap.Builder<String, Object> debugProperties = ImmutableMap.<String, Object> builder();

			final Class<? extends ISvrProcessPrecondition> preconditionsClass = processDescriptor.getPreconditionsClass();
			if (preconditionsClass != null)
			{
				debugProperties.put("debug-classname", preconditionsClass.getName());
			}

			this.debugProperties = debugProperties.build();
		}
		else
		{
			debugProperties = ImmutableMap.of();
		}
	}

	public int getActionId()
	{
		return actionId;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}

	@JsonAnyGetter
	public Map<String, Object> getDebugProperties()
	{
		return debugProperties;
	}
}
