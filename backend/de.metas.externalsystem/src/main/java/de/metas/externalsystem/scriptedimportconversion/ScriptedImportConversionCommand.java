/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.externalsystem.scriptedimportconversion;

import de.metas.externalsystem.endpoint.TransportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@AllArgsConstructor
public enum ScriptedImportConversionCommand
{
	// concrete wire commands sent to the camel scripted-adapter (matched by ExternalSystem_Service
	// EnableCommand/DisableCommand). NOTE: these are NOT user-facing choices — see
	// ScriptedImportConversionIntent + ofIntentAndTransport. AD_Reference 541998 offers Start/Stop.
	EnableRestAPI("enableRestAPI"),
	DisableRestAPI("disableRestAPI"),
	EnableSftpPolling("enableSftpPolling"),
	DisableSftpPolling("disableSftpPolling"),
	EnableLocalFilePolling("enableLocalFilePolling"),
	DisableLocalFilePolling("disableLocalFilePolling");

	@Getter
	private final String value;

	@NonNull
	public static ScriptedImportConversionCommand ofCode(@NonNull final String value)
	{
		final ScriptedImportConversionCommand command = ofCodeOrNull(value);
		if (command == null)
		{
			throw new AdempiereException("No ScriptedImportConversionCommand for code")
					.appendParametersToMessage()
					.setParameter("code", value);
		}
		return command;
	}

	@Nullable
	public static ScriptedImportConversionCommand ofCodeOrNull(@Nullable final String value)
	{
		for (final ScriptedImportConversionCommand command : values())
		{
			if (command.value.equals(value))
			{
				return command;
			}
		}
		return null;
	}

	@NonNull
	public ScriptedImportConversionIntent getIntent()
	{
		switch (this)
		{
			case EnableRestAPI:
			case EnableSftpPolling:
			case EnableLocalFilePolling:
				return ScriptedImportConversionIntent.Start;
			case DisableRestAPI:
			case DisableSftpPolling:
			case DisableLocalFilePolling:
				return ScriptedImportConversionIntent.Stop;
			default:
				throw new AdempiereException("Unhandled ScriptedImportConversionCommand")
						.appendParametersToMessage()
						.setParameter("command", this);
		}
	}

	/**
	 * Derive the concrete command from the user's Start/Stop intent and the child's endpoint
	 * transport. A parent config may have children on different transports, so this is resolved per
	 * child.
	 * <p>
	 * Both switches are exhaustive over their enum and end in a throwing {@code default}: a transport
	 * (or intent) added later must fail loudly here rather than fall through to the REST command and
	 * silently run the wrong route for it.
	 */
	@NonNull
	public static ScriptedImportConversionCommand ofIntentAndTransport(
			@NonNull final ScriptedImportConversionIntent intent,
			@NonNull final TransportType transportType)
	{
		switch (intent)
		{
			case Start:
				switch (transportType)
				{
					case HTTP:
						return EnableRestAPI;
					case SFTP:
						return EnableSftpPolling;
					case LOCAL_FILE:
						return EnableLocalFilePolling;
					default:
						throw unhandled(intent, transportType);
				}
			case Stop:
				switch (transportType)
				{
					case HTTP:
						return DisableRestAPI;
					case SFTP:
						return DisableSftpPolling;
					case LOCAL_FILE:
						return DisableLocalFilePolling;
					default:
						throw unhandled(intent, transportType);
				}
			default:
				throw unhandled(intent, transportType);
		}
	}

	@NonNull
	private static AdempiereException unhandled(
			@NonNull final ScriptedImportConversionIntent intent,
			@NonNull final TransportType transportType)
	{
		return new AdempiereException("No ScriptedImportConversionCommand for intent and transport")
				.appendParametersToMessage()
				.setParameter("intent", intent)
				.setParameter("transportType", transportType);
	}
}
