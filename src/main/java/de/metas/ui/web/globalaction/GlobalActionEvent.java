package de.metas.ui.web.globalaction;

import com.google.common.base.Strings;

import de.metas.process.ProcessExecutionResult;
import lombok.Builder;
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

@Value
@Builder
public class GlobalActionEvent
{
	@NonNull
	GlobalActionType type;
	String payload;

	private static final String SEPARATOR = "#";

	public static GlobalActionEvent parseQRCode(@NonNull final String eventStr)
	{
		final int idx = eventStr.indexOf(SEPARATOR);

		if (idx > 0)
		{
			final String typeStr = eventStr.substring(0, idx);
			final GlobalActionType type = GlobalActionType.forCode(typeStr);
			final String payload = Strings.emptyToNull(eventStr.substring(idx + SEPARATOR.length()));
			return builder()
					.type(type)
					.payload(payload)
					.build();
		}
		else
		{
			final GlobalActionType type = GlobalActionType.forCode(eventStr);
			return builder()
					.type(type)
					.build();
		}
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toQRCodeString();
	}

	public String toQRCodeString()
	{
		if (payload == null || payload.isEmpty())
		{
			return type.getCode();
		}
		else
		{
			return type.getCode() + SEPARATOR + payload;
		}
	}

	public ProcessExecutionResult.DisplayQRCode toDisplayQRCodeProcessResult()
	{
		return ProcessExecutionResult.DisplayQRCode.builder()
				.code(toQRCodeString())
				.build();
	}
}
