/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.dailydeliveryrevenue;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Thin wrapper around {@code report.Docs_Daily_Delivery_Revenue_Report(...)} — a
 * PostgreSQL {@code RETURNS TABLE} function. Raw SQL is unavoidable here (the function cannot be called via
 * {@code IQueryBL} or any DAO), which is why this repository uses {@link DB#retrieveRowsOutOfTrx}.
 * A narrow exception to the "avoid direct SQL in step defs" rule.
 */
public class DailyDeliveryRevenueReportRepository
{
	public ImmutableList<DailyDeliveryRevenueReportRow> list(
			@Nullable final Integer bpartnerId,
			@Nullable final Integer bpGroupId,
			@NonNull final LocalDate dateFrom,
			@NonNull final LocalDate dateTo)
	{
		final String sql = "SELECT day, bpartner, bpartner_group, delivery_revenue_net, invoiced_net, open_net, delivery_count, cursymbol"
				+ " FROM report.Docs_Daily_Delivery_Revenue_Report("
				+ "      p_bpartner_id   => ?,"
				+ "      p_bp_group_id   => ?,"
				+ "      p_date_from     => ?::date,"
				+ "      p_date_to       => ?::date"
				+ " )";

		return DB.retrieveRowsOutOfTrx(
				sql,
				Arrays.asList(bpartnerId, bpGroupId, dateFrom, dateTo),
				DailyDeliveryRevenueReportRepository::toRow);
	}

	private static DailyDeliveryRevenueReportRow toRow(@NonNull final ResultSet rs) throws SQLException
	{
		return DailyDeliveryRevenueReportRow.builder()
				.day(rs.getDate("day").toLocalDate())
				.bpartner(rs.getString("bpartner"))
				.bpartnerGroup(rs.getString("bpartner_group"))
				.deliveryRevenueNet(rs.getBigDecimal("delivery_revenue_net"))
				.invoicedNet(rs.getBigDecimal("invoiced_net"))
				.openNet(rs.getBigDecimal("open_net"))
				.deliveryCount(rs.getObject("delivery_count", Integer.class))
				.currencySymbol(rs.getString("cursymbol"))
				.build();
	}
}
