package de.metas.ui.web.handlingunits.report;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.report.HUToReport;
import de.metas.ui.web.handlingunits.HUEditorRow;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
public final class HUEditorRowAsHUToReport implements HUToReport
{
	public static HUEditorRowAsHUToReport of(final HUEditorRow row)
	{
		return new HUEditorRowAsHUToReport(row);
	}

	private final HUEditorRow row;

	private final HuId huId;
	private final BPartnerId partnerId;
	private final String huUnitType;
	private final boolean topLevel;

	private HUEditorRowAsHUToReport(@NonNull final HUEditorRow row)
	{
		this.row = row;

		huId = row.getHuId();
		partnerId = row.getBpartnerId();
		huUnitType = row.getType().toHUUnitType();
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
		return row.getIncludedRows()
				.stream()
				.map(HUEditorRow::getAsHUToReportOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

}
