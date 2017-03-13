package de.metas.ui.web.process.descriptor;

import java.util.Map;
import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;

import com.google.common.collect.ImmutableMap;

import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Wraps {@link RelatedProcessDescriptor} (from metasfresh) and {@link ProcessDescriptor} (webui) in one java object so we would be able to easily process, filter and process it in streams.
 *
 * NOTE: this is a short living object and it shall not be cached
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class WebuiRelatedProcessDescriptor
{
	public static final WebuiRelatedProcessDescriptor of( //
			final RelatedProcessDescriptor relatedProcessDescriptor //
			, final ProcessDescriptor processDescriptor //
			, final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier //
	)
	{
		return new WebuiRelatedProcessDescriptor(relatedProcessDescriptor, processDescriptor, preconditionsResolutionSupplier);
	}

	private final RelatedProcessDescriptor relatedProcessDescriptor;
	private final ProcessDescriptor processDescriptor;
	private final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier;

	private WebuiRelatedProcessDescriptor( //
			final RelatedProcessDescriptor relatedProcessDescriptor //
			, final ProcessDescriptor processDescriptor //
			, final Supplier<ProcessPreconditionsResolution> preconditionsResolutionSupplier //
	)
	{
		super();

		Check.assumeNotNull(relatedProcessDescriptor, "Parameter relatedProcessDescriptor is not null");
		Check.assumeNotNull(processDescriptor, "Parameter processDescriptor is not null");
		Check.assume(relatedProcessDescriptor.getAD_Process_ID() == processDescriptor.getAD_Process_ID(), "AD_Process_ID matching for {} and {}", relatedProcessDescriptor, processDescriptor);

		this.relatedProcessDescriptor = relatedProcessDescriptor;
		this.processDescriptor = processDescriptor;
		this.preconditionsResolutionSupplier = ExtendedMemorizingSupplier.ofJUFSupplier(preconditionsResolutionSupplier);
	}

	public int getAD_Process_ID()
	{
		return processDescriptor.getAD_Process_ID();
	}

	public String getCaption(final String adLanguage)
	{
		final String captionOverride = getPreconditionsResolution().getCaptionOverrideOrNull(adLanguage);
		if (captionOverride != null)
		{
			return captionOverride;
		}

		return processDescriptor.getCaption(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return processDescriptor.getDescription(adLanguage);
	}

	public boolean isQuickAction()
	{
		return relatedProcessDescriptor.isWebuiQuickAction();
	}

	public boolean isDefaultQuickAction()
	{
		return relatedProcessDescriptor.isWebuiDefaultQuickAction();
	}

	private ProcessPreconditionsResolution getPreconditionsResolution()
	{
		return preconditionsResolutionSupplier.get();
	}

	public boolean isDisabled()
	{
		return getPreconditionsResolution().isRejected();
	}
	
	public boolean isEnabled()
	{
		final ProcessPreconditionsResolution preconditionsResolution = getPreconditionsResolution();
		return preconditionsResolution.isAccepted();
	}
	
	public boolean isEnabledOrNotSilent()
	{
		final ProcessPreconditionsResolution preconditionsResolution = getPreconditionsResolution();
		return preconditionsResolution.isAccepted() || !preconditionsResolution.isInternal();
	}


	public String getDisabledReason(final String adLanguage)
	{
		return getPreconditionsResolution().getRejectReason();
	}

	public Map<String, Object> getDebugProperties()
	{
		final ImmutableMap.Builder<String, Object> debugProperties = ImmutableMap.<String, Object> builder();

		final String processClassname = processDescriptor.getProcessClassname();
		if (processClassname != null)
		{
			debugProperties.put("debug-classname", processClassname);
		}

		return debugProperties.build();
	}
}
