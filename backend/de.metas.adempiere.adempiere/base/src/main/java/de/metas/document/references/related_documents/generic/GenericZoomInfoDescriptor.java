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

package de.metas.document.references.related_documents.generic;

import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
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
	@NonNull ImmutableTranslatableString name;
	@NonNull AdWindowId targetWindowId;

	@NonNull String targetWindowInternalName;

	@NonNull String targetTableName;
	@NonNull String targetColumnName;
	boolean dynamicTargetColumnName;
	@Nullable String virtualTargetColumnSql;

	@Nullable Boolean isSOTrx;
	boolean targetHasIsSOTrxColumn;

	@Nullable String tabSqlWhereClause;

	@Builder(toBuilder = true)
	private GenericZoomInfoDescriptor(
			@NonNull final ImmutableTranslatableString name,
			@NonNull final String targetTableName,
			@Nullable final String targetColumnName,
			@Nullable final String virtualTargetColumnSql,
			final boolean dynamicTargetColumnName,
			@NonNull final AdWindowId targetWindowId,
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

		this.targetWindowId = targetWindowId;

		this.targetWindowInternalName = targetWindowInternalName;

		this.isSOTrx = isSOTrx; // null is also accepted
		this.targetHasIsSOTrxColumn = targetHasIsSOTrxColumn;

		this.tabSqlWhereClause = StringUtils.trimBlankToNull(tabSqlWhereClause);
	}

	public boolean isVirtualTargetColumnName()
	{
		return getVirtualTargetColumnSql() != null;
	}

	public GenericZoomInfoDescriptor withCustomizedWindows(@NonNull final CustomizedWindowInfoMap customizedWindowInfoMap)
	{
		final CustomizedWindowInfo customizedWindow = customizedWindowInfoMap.getCustomizedWindowInfo(this.targetWindowId).orElse(null);
		if (customizedWindow == null)
		{
			return this;
		}

		return toBuilder()
				.targetWindowId(customizedWindow.getCustomizationWindowId())
				.name(customizedWindow.getCustomizationWindowCaption())
				.build();
	}
}
