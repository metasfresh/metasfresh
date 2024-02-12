package de.metas.ui.web.process;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;
import de.metas.process.AdProcessId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

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

	public static ProcessId of(final ProcessHandlerType processHandlerType, final int processId)
	{
		return new ProcessId(processHandlerType, processId);
	}

	public static ProcessId of(final ProcessHandlerType processHandlerType, final String processId)
	{
		return new ProcessId(processHandlerType, processId);
	}

	public static ProcessId ofAD_Process_ID(final int adProcessId)
	{
		return new ProcessId(PROCESSHANDLERTYPE_AD_Process, adProcessId);
	}

	public static ProcessId ofAD_Process_ID(@NonNull final AdProcessId adProcessId)
	{
		return new ProcessId(PROCESSHANDLERTYPE_AD_Process, adProcessId.getRepoId());
	}

	private final String json;
	//
	public static final ProcessHandlerType PROCESSHANDLERTYPE_AD_Process = ProcessHandlerType.ofCode("ADP");
	@NonNull @Getter private final ProcessHandlerType processHandlerType;
	//
	@NonNull @Getter private final String processId;
	private transient int processIdAsInt = 0;

	@JsonCreator
	private ProcessId(final String json)
	{
		Preconditions.checkArgument(json != null && !json.isEmpty(), "invalid ProcessId json: %s", json);

		this.json = json;

		final int idx = json.indexOf("_");
		Preconditions.checkArgument(idx > 0, "invalid ProcessId format: %s", json);

		processHandlerType = ProcessHandlerType.ofCode(json.substring(0, idx));
		processId = json.substring(idx + 1);
	}

	private ProcessId(@NonNull final ProcessHandlerType processHandlerType, final int processIdAsInt)
	{
		Preconditions.checkArgument(processIdAsInt > 0, "invalid processAsIdInt: %s", processIdAsInt);

		json = processHandlerType.getAsString() + "_" + processIdAsInt;
		this.processHandlerType = processHandlerType;
		processId = String.valueOf(processIdAsInt);
		this.processIdAsInt = processIdAsInt;
	}

	private ProcessId(@NonNull final ProcessHandlerType processHandlerType, @NonNull final String processId)
	{
		Check.assumeNotEmpty(processId, "processId shall not be blank");

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

	public AdProcessId toAdProcessId()
	{
		final AdProcessId adProcessId = toAdProcessIdOrNull();
		if (adProcessId == null)
		{
			throw new AdempiereException("Cannot convert " + this + " to " + AdProcessId.class.getSimpleName() + " because the processHanderType=" + processHandlerType + " is not supported");
		}
		return adProcessId;
	}

	/**
	 * Convenience method to get the {@link AdProcessId} for this instance if its {@code processIdAsInt} member is set and/or if {@link #getProcessHandlerType()} is {@link  #PROCESSHANDLERTYPE_AD_Process}.
	 * {@code null} otherwise.
	 */
	public AdProcessId toAdProcessIdOrNull()
	{
		if (this.processIdAsInt > 0) // was set by the creator of this instance
		{
			return AdProcessId.ofRepoId(this.processIdAsInt);
		}
		else if (this.processHandlerType.isADProcess())
		{
			return AdProcessId.ofRepoId(getProcessIdAsInt());
		}
		else // nothing we can do here
		{
			return null;
		}
	}

	public DocumentId toDocumentId()
	{
		return DocumentId.ofString(toJson());
	}
}
