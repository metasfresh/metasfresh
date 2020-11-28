package de.metas.ui.web.dashboard;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;

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
public class KPI
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int id;
	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final KPIChartType chartType;
	private final ImmutableSet<DashboardWidgetType> supportedWidgetTypes;
	private final Duration compareOffset;

	private final List<KPIField> fields;
	private final KPIField groupByField;

	private final KPITimeRangeDefaults timeRangeDefaults;

	private final String esSearchIndex;
	private final String esSearchTypes;
	private final IStringExpression esQuery;

	private final int pollIntervalSec;

	private KPI(final Builder builder)
	{
		super();

		Check.assume(builder.id > 0, "id > 0");
		Check.assumeNotNull(builder.caption, "Parameter builder.caption is not null");
		Check.assumeNotNull(builder.description, "Parameter builder.description is not null");
		Check.assumeNotNull(builder.chartType, "Parameter builder.chartType is not null");
		Check.assumeNotEmpty(builder.fields, "builder.fields is not empty");
		Check.assumeNotEmpty(builder.esSearchIndex, "builder.esSearchIndex is not empty");
		Check.assumeNotEmpty(builder.esSearchTypes, "builder.esSearchTypes is not empty");
		Check.assumeNotEmpty(builder.esQuery, "builder.esQuery is not empty");

		id = builder.id;

		caption = builder.caption;
		description = builder.description;
		chartType = builder.chartType;
		supportedWidgetTypes = ImmutableSet.copyOf(builder.getSupportedWidgetTypes());
		compareOffset = builder.compareOffset;

		timeRangeDefaults = builder.timeRangeDefaults;

		fields = ImmutableList.copyOf(builder.fields);
		final List<KPIField> groupByFieldsList = fields.stream()
				.filter(KPIField::isGroupBy)
				.collect(GuavaCollectors.toImmutableList());
		if (groupByFieldsList.isEmpty())
		{
			groupByField = null;
		}
		else if (groupByFieldsList.size() == 1)
		{
			groupByField = groupByFieldsList.get(0);
		}
		else
		{
			throw new AdempiereException("Only one group by field allowed but we found: " + groupByFieldsList);
		}

		esSearchIndex = builder.esSearchIndex;
		esSearchTypes = builder.esSearchTypes;
		esQuery = StringExpressionCompiler.instance.compile(builder.esQuery);

		pollIntervalSec = builder.pollIntervalSec;
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

	public int getId()
	{
		return id;
	}

	public String getCaption(final String adLanguage)
	{
		return caption.translate(adLanguage);
	}

	public String getDescription(final String adLanguage)
	{
		return description.translate(adLanguage);
	}

	public KPIChartType getChartType()
	{
		return chartType;
	}
	
	public Set<DashboardWidgetType> getSupportedWidgetTypes()
	{
		return supportedWidgetTypes;
	}

	public List<KPIField> getFields()
	{
		return fields;
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

	public KPIField getGroupByFieldOrNull()
	{
		return groupByField;
	}

	public KPITimeRangeDefaults getTimeRangeDefaults()
	{
		return timeRangeDefaults;
	}

	public boolean hasCompareOffset()
	{
		return compareOffset != null;
	}

	public Duration getCompareOffset()
	{
		return compareOffset;
	}

	public int getPollIntervalSec()
	{
		return pollIntervalSec;
	}

	public IStringExpression getESQuery()
	{
		return esQuery;
	}

	public String getESSearchIndex()
	{
		return esSearchIndex;
	}

	public String getESSearchTypes()
	{
		return esSearchTypes;
	}

	public static final class Builder
	{
		private int id;
		private ITranslatableString caption = TranslatableStrings.empty();
		private ITranslatableString description = TranslatableStrings.empty();
		private KPIChartType chartType;
		private Duration compareOffset;
		private List<KPIField> fields;

		private KPITimeRangeDefaults timeRangeDefaults = KPITimeRangeDefaults.DEFAULT;

		private String esSearchTypes;
		private String esSearchIndex;
		private String esQuery;
		private int pollIntervalSec;

		private Builder()
		{
			super();
		}

		public KPI build()
		{
			return new KPI(this);
		}

		public Builder setId(final int id)
		{
			this.id = id;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			this.caption = caption;
			return this;
		}

		public Builder setDescription(final ITranslatableString description)
		{
			this.description = description;
			return this;
		}

		public Builder setChartType(final KPIChartType chartType)
		{
			this.chartType = chartType;
			return this;
		}
		
		private Set<DashboardWidgetType> getSupportedWidgetTypes()
		{
			if(chartType == KPIChartType.Metric)
			{
				return ImmutableSet.of(DashboardWidgetType.TargetIndicator);
			}
			else
			{
				return ImmutableSet.of(DashboardWidgetType.KPI);
			}
		}

		public Builder setFields(final List<KPIField> fields)
		{
			this.fields = fields;
			return this;
		}

		public Builder setCompareOffset(final Duration compareOffset)
		{
			this.compareOffset = compareOffset;
			return this;
		}

		public Builder setESSearchIndex(final String esSearchIndex)
		{
			this.esSearchIndex = esSearchIndex;
			return this;
		}

		public Builder setESSearchTypes(final String esSearchTypes)
		{
			this.esSearchTypes = esSearchTypes;
			return this;
		}

		public Builder setESQuery(final String query)
		{
			esQuery = query;
			return this;
		}

		public Builder setTimeRangeDefaults(final KPITimeRangeDefaults timeRangeDefaults)
		{
			this.timeRangeDefaults = timeRangeDefaults != null ? timeRangeDefaults : KPITimeRangeDefaults.DEFAULT;
			return this;
		}

		public Builder setPollIntervalSec(final int pollIntervalSec)
		{
			this.pollIntervalSec = pollIntervalSec;
			return this;
		}

	}
}
