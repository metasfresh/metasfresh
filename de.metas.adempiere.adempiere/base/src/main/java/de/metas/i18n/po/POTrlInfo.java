package de.metas.i18n.po;

import java.util.Optional;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * Persistent object's translation info.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
public final class POTrlInfo
{
	public static final POTrlInfo NOT_TRANSLATED = POTrlInfo.builder().translated(false).build();

	/**
	 * True if at least one column is translated
	 */
	private final boolean translated;

	private final String tableName;
	private final String keyColumnName;

	@Default
	@NonNull
	private final ImmutableList<String> translatedColumnNames = ImmutableList.of();

	/**
	 * SQL SELECT used to fetch the translation of a given record for a given language.
	 * 
	 * @return <code>SELECT ... FROM TableName_Trl WHERE KeyColumnName=? AND AD_Language=?</code> or <code>null</code>
	 */
	@Default
	@NonNull
	private final Optional<String> sqlSelectTrlByIdAndLanguage = Optional.empty();

	/**
	 * SQL SELECT used to fetch the translations of a given record for any language.
	 * 
	 * @return <code>SELECT ... FROM TableName_Trl WHERE KeyColumnName=?</code> or <code>null</code>
	 */
	@Default
	@NonNull
	private final Optional<String> sqlSelectTrlById = Optional.empty();

	public boolean isColumnTranslated(final String columnName)
	{
		return translatedColumnNames.contains(columnName);
	}
}
