package de.metas.fresh.ordercheckup;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

@Value
public class OrderCheckupReportId implements RepoIdAware
{
	int repoId;

	@JsonCreator
	public static OrderCheckupReportId ofRepoId(final int repoId)
	{
		return new OrderCheckupReportId(repoId);
	}

	@Nullable
	public static OrderCheckupReportId ofRepoIdOrNull(final int repoId)
	{
		return repoId > 0 ? new OrderCheckupReportId(repoId) : null;
	}

	public static OrderCheckupReportId ofReport(@NonNull final I_C_Order_MFGWarehouse_Report report)
	{
		return ofRepoId(report.getC_Order_MFGWarehouse_Report_ID());
	}

	public static boolean equals(@Nullable final OrderCheckupReportId o1, @Nullable final OrderCheckupReportId o2)
	{
		return Objects.equals(o1, o2);
	}

	private OrderCheckupReportId(final int repoId)
	{
		this.repoId = Check.assumeGreaterThanZero(repoId, I_C_Order_MFGWarehouse_Report.COLUMNNAME_C_Order_MFGWarehouse_Report_ID);
	}

	@Override
	@JsonValue
	public int getRepoId()
	{
		return repoId;
	}
}
