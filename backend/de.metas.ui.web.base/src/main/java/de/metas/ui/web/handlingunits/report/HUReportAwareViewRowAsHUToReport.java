package de.metas.ui.web.handlingunits.report;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.report.HUToReport;
import de.metas.ui.web.view.IViewRow;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import javax.annotation.Nullable;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@EqualsAndHashCode(of = "huId")
@ToString(exclude = "row")
public final class HUReportAwareViewRowAsHUToReport implements HUToReport
{
	public static HUReportAwareViewRowAsHUToReport of(final HUReportAwareViewRow row)
	{
		return new HUReportAwareViewRowAsHUToReport(row);
	}

	@Nullable
	public static HUReportAwareViewRowAsHUToReport ofOrNull(final IViewRow row)
	{
		return row instanceof HUReportAwareViewRow ? new HUReportAwareViewRowAsHUToReport((HUReportAwareViewRow)row) : null;
	}

	private final HUReportAwareViewRow row;

	private final HuId huId;
	private final BPartnerId partnerId;
	private final String huUnitType;
	private final boolean topLevel;

	private HUReportAwareViewRowAsHUToReport(@NonNull final HUReportAwareViewRow row)
	{
		this.row = row;

		huId = row.getHuId();
		partnerId = row.getBpartnerId();
		huUnitType = row.getHUUnitType();
		topLevel = row.isTopLevel();
	}

	@Override
	public HuId getHUId()
	{
		return huId;
	}

	@Override
	public BPartnerId getBPartnerId()
	{
		return partnerId;
	}

	@Override
	public String getHUUnitType()
	{
		return huUnitType;
	}

	@Override
	public boolean isTopLevel()
	{
		return topLevel;
	}

	@Override
	public List<HUToReport> getIncludedHUs()
	{
		return row.streamIncludedHUReportAwareRows()
				.map(HUReportAwareViewRowAsHUToReport::of)
				.collect(ImmutableList.toImmutableList());
	}

}
