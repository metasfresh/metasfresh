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

package de.metas.ui.web.kpi.descriptor;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.dashboard.DashboardWidgetType;
import de.metas.ui.web.kpi.KPITimeRangeDefaults;
import de.metas.ui.web.kpi.descriptor.elasticsearch.ElasticsearchDatasourceDescriptor;
import de.metas.ui.web.kpi.descriptor.sql.SQLDatasourceDescriptor;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CtxName;

import javax.annotation.Nullable;
import java.time.Duration;
import java.util.List;
import java.util.Set;

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

	@NonNull ImmutableList<KPIField> fields;
	@Nullable KPIField groupByField;

	@NonNull KPITimeRangeDefaults timeRangeDefaults;

	@NonNull KPIDatasourceType datasourceType;
	@Nullable ElasticsearchDatasourceDescriptor elasticsearchDatasource;
	@Nullable SQLDatasourceDescriptor sqlDatasource;

	// Performance tuning
	@NonNull Duration allowedStaleDuration;

	@Builder
	private KPI(
			@NonNull final KPIId id,
			@NonNull final ITranslatableString caption,
			@NonNull final ITranslatableString description,
			@NonNull final KPIChartType chartType,
			@Nullable final Duration compareOffset,
			@NonNull final List<KPIField> fields,
			@NonNull final KPITimeRangeDefaults timeRangeDefaults,
			@NonNull final KPIDatasourceType datasourceType,
			@Nullable final ElasticsearchDatasourceDescriptor elasticsearchDatasource,
			@Nullable final SQLDatasourceDescriptor sqlDatasource,
			@NonNull final Duration allowedStaleDuration)
	{
		this.allowedStaleDuration = allowedStaleDuration;
		Check.assumeNotEmpty(fields, "fields is not empty");

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
		groupByField = extractGroupByField(fields);

		this.datasourceType = datasourceType;
		if (datasourceType == KPIDatasourceType.ELASTICSEARCH)
		{
			Check.assumeNotNull(elasticsearchDatasource, "elasticsearchDatasource shall be set");
			this.elasticsearchDatasource = elasticsearchDatasource;
			this.sqlDatasource = null;
		}
		else if (datasourceType == KPIDatasourceType.SQL)
		{
			Check.assumeNotNull(sqlDatasource, "sqlDatasource shall be set");
			this.elasticsearchDatasource = null;
			this.sqlDatasource = sqlDatasource;
		}
		else
		{
			throw new AdempiereException("Unknown datasource type: " + datasourceType);
		}
	}

	@Nullable
	private static KPIField extractGroupByField(final @NonNull List<KPIField> fields)
	{
		final List<KPIField> groupByFieldsList = fields.stream()
				.filter(KPIField::isGroupBy)
				.collect(GuavaCollectors.toImmutableList());
		if (groupByFieldsList.isEmpty())
		{
			return null;
		}
		else if (groupByFieldsList.size() == 1)
		{
			return groupByFieldsList.get(0);
		}
		else
		{
			throw new AdempiereException("Only one group by field allowed but we found: " + groupByFieldsList);
		}
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

	public Set<CtxName> getRequiredContextParameters()
	{
		if (elasticsearchDatasource != null)
		{
			return elasticsearchDatasource.getRequiredContextParameters();
		}
		else if (sqlDatasource != null)
		{
			return sqlDatasource.getRequiredContextParameters();
		}
		else
		{
			throw new AdempiereException("Unknown datasource type: " + datasourceType);
		}
	}

	public boolean isZoomToDetailsAvailable()
	{
		return sqlDatasource != null;
	}

	@NonNull
	public SQLDatasourceDescriptor getSqlDatasourceNotNull()
	{
		return Check.assumeNotNull(getSqlDatasource(), "Not an SQL data source: {}", this);
	}
}
