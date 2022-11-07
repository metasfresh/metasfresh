/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.monitoring.adapter;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
public class PerformanceMonitoringData
{
	@Setter
	private int depth = 0;
	private String initiator = "";
	private String initiatorWindow = "";
	@Setter
	private ArrayList<String> calledBy = new ArrayList<>();
	@Setter
	private boolean initiatorLabelActive = false;

	public void setRestControllerData(PerformanceMonitoringService.Metadata metadata)
	{
		initiatorLabelActive = true;
		initiator = metadata.getClassName() + " - " + metadata.getFunctionName();
		calledBy.add(0, "HTTP Request");

		final String windowNameAndId = metadata.getWindowNameAndId();
		if(windowNameAndId == null)
		{
			initiatorWindow = "NONE";
		}
		else
		{
			initiatorWindow = windowNameAndId;
		}
	}

	public boolean isCalledByMonitoredFunction()
	{
		return depth != 0;
	}
}
