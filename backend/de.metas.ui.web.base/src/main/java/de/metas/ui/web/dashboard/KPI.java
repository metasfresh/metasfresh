package de.metas.ui.web.dashboard;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;
import org.elasticsearch.action.search.SearchType;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
@EqualsAndHashCode(doNotUseGetters = true)
public class KPI
{
	@NonNull KPIId id;
	@NonNull ITranslatableString caption;
	@NonNull ITranslatableString description;
	@NonNull KPIChartType chartType;
	@NonNull ImmutableSet<DashboardWidgetType> supportedWidgetTypes;
	@Nullable Duration compareOffset;

	@NonNull List<KPIField> fields;
	@Nullable KPIField groupByField;

	@NonNull KPITimeRangeDefaults timeRangeDefaults;

	@NonNull String esSearchIndex;
	@NonNull SearchType esSearchTypes;
	@NonNull IStringExpression esQuery;

	int pollIntervalSec;

	@Builder
	private KPI(
			@NonNull final KPIId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final KPIChartType chartType,
			@Nullable final Duration compareOffset,
			@NonNull final List<KPIField> fields,
			@NonNull final KPITimeRangeDefaults timeRangeDefaults,
			@NonNull final String esSearchIndex,
			@NonNull final SearchType esSearchTypes,
			@NonNull final String esQuery,
			final int pollIntervalSec)
	{
		Check.assumeNotEmpty(fields, "fields is not empty");
		Check.assumeNotEmpty(esSearchIndex, "esSearchIndex is not empty");
		Check.assumeNotEmpty(esQuery, "esQuery is not empty");

		this.id = id;

		this.caption = caption;
		this.description = description;
		this.chartType = chartType;
		this.supportedWidgetTypes = this.chartType == KPIChartType.Metric
				? ImmutableSet.of(DashboardWidgetType.TargetIndicator)
				: ImmutableSet.of(DashboardWidgetType.KPI);
		this.compareOffset = compareOffset;

		this.timeRangeDefaults = timeRangeDefaults;

		this.fields = ImmutableList.copyOf(fields);
		final List<KPIField> groupByFieldsList = fields.stream()
				.filter(KPIField::isGroupBy)
				.collect(GuavaCollectors.toImmutableList());
		if (groupByFieldsList.isEmpty())
		{
			this.groupByField = null;
		}
		else if (groupByFieldsList.size() == 1)
		{
			this.groupByField = groupByFieldsList.get(0);
		}
		else
		{
			throw new AdempiereException("Only one group by field allowed but we found: " + groupByFieldsList);
		}

		this.esSearchIndex = esSearchIndex;
		this.esSearchTypes = esSearchTypes;
		this.esQuery = StringExpressionCompiler.instance.compile(esQuery);

		this.pollIntervalSec = pollIntervalSec;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("id", id)
				.add("caption", caption.getDefaultValue())
				.toString();
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public KPIField getGroupByField()
	{
		final KPIField groupByField = getGroupByFieldOrNull();
		if (groupByField == null)
		{
			throw new IllegalStateException("KPI has no group by field defined");
		}
		return groupByField;
	}

	@Nullable
	public KPIField getGroupByFieldOrNull()
	{
		return groupByField;
	}

	public boolean hasCompareOffset()
	{
		return compareOffset != null;
	}
}
