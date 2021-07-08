package de.metas.ui.web.window.model;

import de.metas.ui.web.window.WindowConstants;
import lombok.Builder;
import lombok.Value;

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

/** Decides on whether a field is readonly, based one the field's document. Example: i an M_Product is not active, then its fields are in general read-only. */
@Value
@Builder
public final class DocumentReadonly
{
	public static final DocumentReadonly NOT_READONLY = builder()
			.parentActive(true).active(true)
			.processed(false)
			.processing(false)
			.build();

	public static final DocumentReadonly ofParent(DocumentReadonly parentDocumentReadonly)
	{
		return builder()
				.parentActive(parentDocumentReadonly.active)
				.active(parentDocumentReadonly.active)
				.processed(parentDocumentReadonly.processed)
				.processed(parentDocumentReadonly.processing)
				.fieldsReadonly(null) // unknown (will fallback to not-readonly)
				.build();
	}

	private final boolean parentActive;
	private final boolean active;
	private final boolean processed;
	private final boolean processing;
	private final Boolean fieldsReadonly;

	public boolean computeFieldReadonly(final String fieldName, final boolean alwaysUpdateable)
	{
		// Case: parent document is not active => fields of this document shall be completely readonly (including the IsActive flag)
		if (!parentActive)
		{
			return true; // readonly
		}

		// Case: this or parent document is processed => fields of this document shall be completely readonly if they were not flagged with AlwaysUpdateable
		if (processed || processing)
		{
			return !alwaysUpdateable; // readonly if not always updateable
		}

		// Case: this document is not active => fields of this document shall be completely readonly, BUT NOT the IsActive flag.
		// We shall alow user to edit it
		if (!active)
		{
			if (WindowConstants.FIELDNAME_IsActive.equals(fieldName))
			{
				return false; // not readonly
			}
			else
			{
				return true; // readonly
			}
		}

		// If we reached this point, it means the document and parent document are active and not processed
		// => readonly if fields are readonly.
		return fieldsReadonly != null ? fieldsReadonly : false;
	}
}
