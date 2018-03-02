package de.metas.ui.web.handlingunits.report;

import de.metas.handlingunits.process.api.HUProcessDescriptor;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * Unifies different descriptors.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
class WebuiHUProcessDescriptor
{
	@NonNull
	private ProcessDescriptor processDescriptor;

	@NonNull
	@Getter(AccessLevel.NONE)
	private HUProcessDescriptor huProcessDescriptor;

	public ProcessId getProcessId()
	{
		return processDescriptor.getProcessId();
	}

	public int getReportADProcessId()
	{
		return huProcessDescriptor.getProcessId();
	}

	public WebuiRelatedProcessDescriptor toWebuiRelatedProcessDescriptor()
	{
		return WebuiRelatedProcessDescriptor.builder()
				.processId(processDescriptor.getProcessId())
				.processCaption(processDescriptor.getCaption())
				.processDescription(processDescriptor.getDescription())
				.quickAction(true)
				.preconditionsResolutionSupplier(() -> ProcessPreconditionsResolution.accept())
				.build();
	}

	public boolean appliesToHUUnitType(final String huUnitType)
	{
		return huProcessDescriptor.appliesToHUUnitType(huUnitType);
	}
}
