/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.document.references.generic;

import de.metas.i18n.ImmutableTranslatableString;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;

@Value
public class GenericZoomInfoDescriptor
{
	ImmutableTranslatableString name;
	AdWindowId targetAD_Window_ID;

	String targetWindowInternalName;

	String targetTableName;
	String targetColumnName;
	boolean dynamicTargetColumnName;
	String virtualTargetColumnSql;

	Boolean isSOTrx;
	boolean targetHasIsSOTrxColumn;

	String tabSqlWhereClause;

	@Builder
	private GenericZoomInfoDescriptor(
			@NonNull final ImmutableTranslatableString name,
			@NonNull final String targetTableName,
			@Nullable final String targetColumnName,
			@Nullable final String virtualTargetColumnSql,
			final boolean dynamicTargetColumnName,
			@NonNull final AdWindowId targetAD_Window_ID,
			@NonNull final String targetWindowInternalName,
			@Nullable final Boolean isSOTrx,
			final boolean targetHasIsSOTrxColumn,
			@Nullable final String tabSqlWhereClause)
	{
		Check.assumeNotEmpty(targetTableName, "targetTableName is not empty");
		if (!dynamicTargetColumnName && Check.isEmpty(targetColumnName, true))
		{
			throw new IllegalArgumentException("targetColumnName must be set when it's not dynamic");
		}

		this.name = name;

		this.targetTableName = targetTableName;
		this.targetColumnName = targetColumnName;
		this.virtualTargetColumnSql = StringUtils.trimBlankToNull(virtualTargetColumnSql);
		this.dynamicTargetColumnName = dynamicTargetColumnName;

		this.targetAD_Window_ID = targetAD_Window_ID;

		this.targetWindowInternalName = targetWindowInternalName;

		this.isSOTrx = isSOTrx; // null is also accepted
		this.targetHasIsSOTrxColumn = targetHasIsSOTrxColumn;

		this.tabSqlWhereClause = StringUtils.trimBlankToNull(tabSqlWhereClause);
	}

	public boolean isVirtualTargetColumnName()
	{
		return getVirtualTargetColumnSql() != null;
	}
}
