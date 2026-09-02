/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2026 metas GmbH
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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

/**
 * User intent for the scripted-import "call" process ({@code AD_Process 585512}, param
 * {@code External_Request} = {@code AD_Reference 541998}): whether to START or STOP the import
 * processor(s) of a parent config.
 * <p>
 * The concrete camel command (REST vs SFTP) is NOT chosen by the user: a parent config can have
 * several import children with different transports, so the command is derived per child from that
 * child's endpoint transport — see
 * {@link ScriptedImportConversionCommand#ofIntentAndTransport(ScriptedImportConversionIntent, de.metas.externalsystem.endpoint.TransportType)}.
 */
@AllArgsConstructor
public enum ScriptedImportConversionIntent
{
	Start("start"),
	Stop("stop");

	@Getter
	private final String code;

	@NonNull
	public static ScriptedImportConversionIntent ofCode(@NonNull final String code)
	{
		final ScriptedImportConversionIntent intent = ofCodeOrNull(code);
		if (intent == null)
		{
			throw new AdempiereException("No ScriptedImportConversionIntent for code")
					.appendParametersToMessage()
					.setParameter("code", code);
		}
		return intent;
	}

	@Nullable
	public static ScriptedImportConversionIntent ofCodeOrNull(@Nullable final String code)
	{
		for (final ScriptedImportConversionIntent intent : values())
		{
			if (intent.code.equals(code))
			{
				return intent;
			}
		}
		return null;
	}
}
