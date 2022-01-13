/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.window.datatypes.json;

import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.util.Check;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

@Getter
public class JSONTriggerAction
{
	public static JSONTriggerAction startProcess(
			@NonNull final ProcessId processId,
			@Nullable final DocumentPath selectedDocumentPath)
	{
		return JSONTriggerProcessAction.builder()
				.processId(processId.toJson())
				.selectedDocumentPath(selectedDocumentPath != null
						? JSONDocumentPath.ofWindowDocumentPath(selectedDocumentPath)
						: null)
				.build();
	}

	private final String type;

	protected JSONTriggerAction(@NonNull final String type)
	{
		Check.assumeNotEmpty(type, "type");
		this.type = type;
	}
}
