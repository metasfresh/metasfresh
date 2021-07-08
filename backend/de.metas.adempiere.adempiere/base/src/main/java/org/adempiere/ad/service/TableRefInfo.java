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

package org.adempiere.ad.service;

import com.google.common.annotations.VisibleForTesting;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

@Value
@VisibleForTesting
public class TableRefInfo
{
	@NonNull String identifier; // used only for debugging
	@NonNull String tableName;
	@NonNull String keyColumn;
	@Nullable String displayColumn;
	boolean valueDisplayed;
	boolean translated;
	@Nullable String whereClause;
	@Nullable String orderByClause;
	@Nullable AdWindowId zoomSO_Window_ID;
	@Nullable AdWindowId zoomPO_Window_ID;
	@Nullable AdWindowId zoomAD_Window_ID_Override;
	@Nullable String displayColumnSQL;
	boolean autoComplete;
	boolean showInactiveValues;
	@NonNull TooltipType tooltipType;

	@Builder(toBuilder = true)
	public TableRefInfo(
			@NonNull final String identifier,
			@NonNull final String tableName,
			@NonNull final String keyColumn,
			@Nullable final String displayColumn,
			final boolean valueDisplayed,
			final boolean translated,
			@Nullable final String whereClause,
			@Nullable final String orderByClause,
			@Nullable final AdWindowId zoomSO_Window_ID,
			@Nullable final AdWindowId zoomPO_Window_ID,
			@Nullable final AdWindowId zoomAD_Window_ID_Override,
			@Nullable final String displayColumnSQL,
			final boolean autoComplete,
			final boolean showInactiveValues,
			@Nullable final TooltipType tooltipType)
	{
		this.identifier = identifier;
		this.tableName = Check.assumeNotEmpty(tableName, "tableName not empty");
		this.keyColumn = Check.assumeNotEmpty(keyColumn, "keyColumn not empty");

		if (!Check.isEmpty(displayColumn, true))
		{
			this.displayColumn = displayColumn;
		}
		else
		{
			this.displayColumn = null;
		}

		if (!Check.isEmpty(displayColumnSQL, true))
		{
			this.displayColumnSQL = displayColumnSQL;
		}
		else
		{
			this.displayColumnSQL = null;
		}

		this.valueDisplayed = valueDisplayed;
		this.translated = translated;

		if (!Check.isEmpty(whereClause, true))
		{
			this.whereClause = whereClause;
		}
		else
		{
			this.whereClause = null;
		}

		if (!Check.isEmpty(orderByClause, true))
		{
			this.orderByClause = orderByClause;
		}
		else
		{
			this.orderByClause = null;
		}

		this.zoomSO_Window_ID = zoomSO_Window_ID;
		this.zoomPO_Window_ID = zoomPO_Window_ID;
		this.zoomAD_Window_ID_Override = zoomAD_Window_ID_Override;

		this.autoComplete = autoComplete;

		this.showInactiveValues = showInactiveValues;
		this.tooltipType = tooltipType != null ? tooltipType : TooltipType.DEFAULT;
	}

	public boolean isNumericKey()
	{
		return keyColumn.endsWith("_ID");
	}

	public TableRefInfo mapWindowIds(@NonNull final UnaryOperator<AdWindowId> adWindowIdMapper)
	{
		final AdWindowId new_zoomSO_window_id = zoomSO_Window_ID != null ? adWindowIdMapper.apply(zoomSO_Window_ID) : null;
		final AdWindowId new_zoomPO_window_id = zoomPO_Window_ID != null ? adWindowIdMapper.apply(zoomPO_Window_ID) : null;
		final AdWindowId new_zoomAD_window_id_override = zoomAD_Window_ID_Override != null ? adWindowIdMapper.apply(zoomAD_Window_ID_Override) : null;

		if (!AdWindowId.equals(zoomSO_Window_ID, new_zoomSO_window_id)
				|| !AdWindowId.equals(zoomPO_Window_ID, new_zoomPO_window_id)
				|| !AdWindowId.equals(zoomAD_Window_ID_Override, new_zoomAD_window_id_override))
		{
			return toBuilder()
					.zoomSO_Window_ID(new_zoomSO_window_id)
					.zoomPO_Window_ID(new_zoomPO_window_id)
					.zoomAD_Window_ID_Override(new_zoomAD_window_id_override)
					.build();
		}
		else
		{
			return this;
		}
	}
}
