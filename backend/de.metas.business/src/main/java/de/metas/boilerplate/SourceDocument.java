/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.boilerplate;

import org.compiere.model.PO;
import org.compiere.util.Env;

import static org.adempiere.model.InterfaceWrapperHelper.getPO;

public interface SourceDocument
{
	String NAME = "__SourceDocument";

	default int getWindowNo()
	{
		return Env.WINDOW_None;
	}

	boolean hasFieldValue(String fieldName);

	Object getFieldValue(String fieldName);

	default int getFieldValueAsInt(final String fieldName, final int defaultValue)
	{
		final Object value = getFieldValue(fieldName);
		return value != null ? (int)value : defaultValue;
	}

	static SourceDocument toSourceDocumentOrNull(final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		if (obj instanceof SourceDocument)
		{
			return (SourceDocument)obj;
		}

		final PO po = getPO(obj);
		return new POSourceDocument(po);
	}
}