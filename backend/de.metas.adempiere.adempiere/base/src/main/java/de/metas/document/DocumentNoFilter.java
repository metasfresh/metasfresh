/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.document;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

@EqualsAndHashCode
public class DocumentNoFilter
{
	private final String string;

	private DocumentNoFilter(@NonNull final String string)
	{
		this.string = StringUtils.trimBlankToNull(string);
		if (this.string == null)
		{
			throw new AdempiereException("DocumentNo string cannot be null/empty");
		}
	}

	@Nullable
	public static DocumentNoFilter ofNullableString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		return stringNorm != null ? new DocumentNoFilter(stringNorm) : null;
	}

	@Override
	@Deprecated
	public String toString() {return string;}

	public <T> IQueryFilter<T> toSqlFilter(@NonNull final String documentNoColumnName) {return new StringLikeFilter<>(documentNoColumnName, string, true);}

	public <T> IQueryFilter<T> toSqlFilter(@NonNull final ModelColumn<T, ?> documentNoColumn) {return toSqlFilter(documentNoColumn.getColumnName());}
}
