package de.metas.logging;

import java.util.Objects;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.MDC;
import org.slf4j.MDC.MDCCloseable;

import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@UtilityClass
public class TableRecordMDC
{
	public MDCCloseable putTableRecordReference(@NonNull final String tableName, @NonNull final RepoIdAware id)
	{
		return putTableRecordReference(TableRecordReference.of(tableName, id));
	}

	public MDCCloseable putTableRecordReference(@NonNull final Object recordModel)
	{
		return putTableRecordReference(TableRecordReference.of(recordModel));
	}

	/**
	 * @return {@code null} if the given {@code tableRecordReference} was put already.
	 *         Thx to https://stackoverflow.com/a/35372185/1012103
	 */
	public MDCCloseable putTableRecordReference(@NonNull final TableRecordReference tableRecordReference)
	{
		final String key = tableRecordReference.getTableName() + "_ID";
		final String value = Integer.toString(tableRecordReference.getRecord_ID());

		if (Objects.equals(MDC.get(key), value))
		{
			return null;
		}
		return MDC.putCloseable(key, value);
	}
}
