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

package de.metas.ui.web.dashboard;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.WebuiError;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class UserDashboardItemDataResponse
{
	@NonNull UserDashboardItemId itemId;
	@Nullable KPIDataResult kpiData;
	@Nullable WebuiError error;

	public static UserDashboardItemDataResponse ok(
			@NonNull final UserDashboardItemId itemId,
			@NonNull final KPIDataResult kpiData)
	{
		return new UserDashboardItemDataResponse(itemId, kpiData, null);
	}

	public static UserDashboardItemDataResponse error(
			@NonNull final UserDashboardItemId itemId,
			@NonNull final WebuiError error)
	{
		return new UserDashboardItemDataResponse(itemId, null, error);
	}

	public boolean isSameDataAs(@NonNull final UserDashboardItemDataResponse other)
	{
		return Objects.equals(getDatasetsOrNull(), other.getDatasetsOrNull())
				&& Objects.equals(error, other.error);
	}

	@Nullable
	private ImmutableList<KPIDataSet> getDatasetsOrNull()
	{
		return kpiData != null ? kpiData.getDatasets() : null;
	}
}
