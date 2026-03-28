/**
 *
 */
package org.adempiere.exceptions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import org.compiere.model.IQuery;

import javax.annotation.Nullable;

/**
 * Exception thrown by {@link IQuery} class when there were more records and only one was expected
 *
 * @author Teo Sarca
 */
public class DBMoreThanOneRecordsFoundException extends DBException
{
	public static final AdMessageKey MSG_QueryMoreThanOneRecordsFound = AdMessageKey.of("QueryMoreThanOneRecordsFound");

	public DBMoreThanOneRecordsFoundException(String detailMessage)
	{
		this(TranslatableStrings.parse(detailMessage));
	}

	public DBMoreThanOneRecordsFoundException(@Nullable ITranslatableString detailMessage)
	{
		super(buildMsg(detailMessage));
	}

	private static ITranslatableString buildMsg(@Nullable final ITranslatableString detailMessage)
	{
		final TranslatableStringBuilder builder = TranslatableStrings.builder().appendADMessage(MSG_QueryMoreThanOneRecordsFound);

		if (detailMessage != null && !TranslatableStrings.isBlank(detailMessage))
		{
			builder.append(" (").append(detailMessage).append(")");
		}

		return builder.build();
	}

}
