package de.metas.ui.web.process;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.EqualsAndHashCode;

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

@EqualsAndHashCode(exclude = { "json" })
public final class ProcessId
{
	public static ProcessId fromJson(final String json)
	{
		return new ProcessId(json);
	}

	public static ProcessId of(final String processHandlerType, final int processId)
	{
		return new ProcessId(processHandlerType, processId);
	}
	
	public static ProcessId of(final String processHandlerType, final String processId)
	{
		return new ProcessId(processHandlerType, processId);
	}


	public static final ProcessId ofAD_Process_ID(final int adProcessId)
	{
		return new ProcessId(PROCESSHANDLERTYPE_AD_Process, adProcessId);
	}

	private final String json;
	//
	public static final String PROCESSHANDLERTYPE_AD_Process = "ADP";
	private final String processHandlerType;
	//
	private final String processId;
	private transient int processIdAsInt = 0;

	@JsonCreator
	private ProcessId(final String json)
	{
		super();

		Preconditions.checkArgument(json != null && !json.isEmpty(), "invalid ProcessId json: %s", json);

		this.json = json;

		final int idx = json.indexOf("_");
		Preconditions.checkArgument(idx > 0, "invalid ProcessId format: %s", json);

		processHandlerType = json.substring(0, idx);
		processId = json.substring(idx + 1);
	}

	private ProcessId(final String processHandlerType, final int processIdAsInt)
	{
		Preconditions.checkArgument(processHandlerType != null && !processHandlerType.isEmpty(), "invalid processHandlerType: %s", processHandlerType);
		Preconditions.checkArgument(processIdAsInt > 0, "invalid processAsIdInt: %s", processIdAsInt);

		json = processHandlerType + "_" + processIdAsInt;
		this.processHandlerType = processHandlerType;
		processId = String.valueOf(processIdAsInt);
		this.processIdAsInt = processIdAsInt;
	}

	private ProcessId(final String processHandlerType, final String processId)
	{
		Preconditions.checkArgument(processHandlerType != null && !processHandlerType.isEmpty(), "invalid processHandlerType: %s", processHandlerType);
		Preconditions.checkArgument(processId != null && !processId.isEmpty(), "invalid processId: %s", processId);

		json = processHandlerType + "_" + processId;
		this.processHandlerType = processHandlerType;
		this.processId = processId;
	}

	@Override
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		return json;
	}

	public String getProcessHandlerType()
	{
		return processHandlerType;
	}

	public String getProcessId()
	{
		return processId;
	}

	public int getProcessIdAsInt()
	{
		int processIdAsInt = this.processIdAsInt;
		if (processIdAsInt <= 0)
		{
			final String processIdStr = getProcessId();
			processIdAsInt = Integer.parseInt(processIdStr);
			if (processIdAsInt <= 0)
			{
				throw new IllegalStateException("processId cannot be converted to int: " + processIdStr);
			}

			this.processIdAsInt = processIdAsInt;
		}
		return processIdAsInt;
	}

	public DocumentId toDocumentId()
	{
		return DocumentId.ofString(toJson());
	}
}
