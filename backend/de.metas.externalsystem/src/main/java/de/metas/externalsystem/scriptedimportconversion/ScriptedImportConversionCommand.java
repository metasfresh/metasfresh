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
	DisableSftpPolling("disableSftpPolling");

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
				return ScriptedImportConversionIntent.Start;
			case DisableRestAPI:
			case DisableSftpPolling:
				return ScriptedImportConversionIntent.Stop;
			default:
				throw new AdempiereException("Unhandled ScriptedImportConversionCommand")
						.appendParametersToMessage()
						.setParameter("command", this);
		}
	}

	/**
	 * Derive the concrete command from the user's Start/Stop intent and the child's endpoint
	 * transport. A parent config may have both REST and SFTP children, so this is resolved per child.
	 */
	@NonNull
	public static ScriptedImportConversionCommand ofIntentAndTransport(
			@NonNull final ScriptedImportConversionIntent intent,
			@NonNull final TransportType transportType)
	{
		final boolean sftp = transportType == TransportType.SFTP;
		if (intent == ScriptedImportConversionIntent.Start)
		{
			return sftp ? EnableSftpPolling : EnableRestAPI;
		}
		if (intent == ScriptedImportConversionIntent.Stop)
		{
			return sftp ? DisableSftpPolling : DisableRestAPI;
		}
		throw new AdempiereException("Unhandled ScriptedImportConversionIntent")
				.appendParametersToMessage()
				.setParameter("intent", intent)
				.setParameter("transportType", transportType);
	}
}
