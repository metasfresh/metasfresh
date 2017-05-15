package de.metas.ui.web.window.descriptor;

import de.metas.ui.web.process.ProcessId;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
public final class ButtonFieldActionDescriptor
{
	public static final ButtonFieldActionDescriptor processCall(@NonNull final ProcessId processId)
	{
		final String zoomIntoTableIdFieldName = null; // N/A
		return new ButtonFieldActionDescriptor(ButtonFieldActionType.processCall, processId, zoomIntoTableIdFieldName);
	}

	public static final ButtonFieldActionDescriptor genericZoomInto(@NonNull final String zoomIntoTableIdFieldName)
	{
		final ProcessId processId = null; // N/A
		return new ButtonFieldActionDescriptor(ButtonFieldActionType.genericZoomInto, processId, zoomIntoTableIdFieldName);
	}

	public static enum ButtonFieldActionType
	{
		processCall, genericZoomInto,
	}

	private @NonNull final ButtonFieldActionType actionType;
	private final ProcessId processId;
	private final String zoomIntoTableIdFieldName;

	private ButtonFieldActionDescriptor(final ButtonFieldActionType actionType, final ProcessId processId, final String zoomIntoTableIdFieldName)
	{
		super();
		this.actionType = actionType;
		this.processId = processId;
		this.zoomIntoTableIdFieldName = zoomIntoTableIdFieldName;
	}
	
	public ButtonFieldActionType getActionType()
	{
		return actionType;
	}
	
	public ProcessId getProcessId()
	{
		return processId;
	}
	
	public String getZoomIntoTableIdFieldName()
	{
		return zoomIntoTableIdFieldName;
	}
}
