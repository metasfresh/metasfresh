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

package de.metas.ui.web.kpi.data;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.exceptions.WebuiError;
import de.metas.ui.web.kpi.TimeRange;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Objects;

@Value
public class KPIDataResult
{
	public static Builder builder()
	{
		return new Builder();
	}

	@Nullable TimeRange range;
	@NonNull ImmutableList<KPIDataSet> datasets;
	@NonNull Instant datasetsComputedTime;
	@Nullable Duration datasetsComputeDuration;

	@Nullable WebuiError error;

	private KPIDataResult(final Builder builder)
	{
		final Instant now = SystemTime.asInstant();

		range = builder.range;
		datasets = builder.getBuiltDatasets();
		datasetsComputedTime = builder.datasetsComputedTime != null ? builder.datasetsComputedTime : now;
		datasetsComputeDuration = builder.datasetsComputeDuration;

		error = builder.error;

	}

	public static boolean equals(@Nullable final KPIDataResult o1, @Nullable final KPIDataResult o2)
	{
		return Objects.equals(o1, o2);
	}

	//
	//
	//
	//
	//

	public static final class Builder
	{
		private ImmutableList<KPIDataSet> builtDatasets = null;
		private LinkedHashMap<String, KPIDataSet.KPIDataSetBuilder> datasets = null;
		@Nullable private TimeRange range;
		@Nullable private Instant datasetsComputedTime;
		@Nullable private Duration datasetsComputeDuration;

		public static final AdMessageKey MSG_FailedLoadingKPI = AdMessageKey.of("webui.dashboard.KPILoadError");
		@Nullable WebuiError error;

		private Builder() { }

		public KPIDataResult build()
		{
			return new KPIDataResult(this);
		}

		private ImmutableList<KPIDataSet> getBuiltDatasets()
		{
			if (builtDatasets == null)
			{
				if (datasets == null)
				{
					builtDatasets = ImmutableList.of();
				}
				else
				{
					builtDatasets = datasets
							.values()
							.stream()
							.map(KPIDataSet.KPIDataSetBuilder::build)
							.collect(ImmutableList.toImmutableList());
				}
			}
			return builtDatasets;
		}

		public Builder setFromPreviousResult(@Nullable final KPIDataResult previousResult)
		{
			if (previousResult == null)
			{
				builtDatasets(ImmutableList.of());
				range(null);
			}
			else
			{
				builtDatasets(previousResult.getDatasets());
				range(previousResult.getRange());
				datasetsComputedTime(previousResult.getDatasetsComputedTime());
				datasetsComputeDuration(previousResult.getDatasetsComputeDuration());
			}

			return this;
		}

		public KPIDataSet.KPIDataSetBuilder dataSet(final String name)
		{
			if (builtDatasets != null)
			{
				throw new AdempiereException("datasets were already built");
			}

			if (datasets == null)
			{
				datasets = new LinkedHashMap<>();
			}

			return datasets.computeIfAbsent(name, KPIDataSet::builder);
		}

		private void datasetsComputedTime(@Nullable final Instant datasetsComputedTime)
		{
			this.datasetsComputedTime = datasetsComputedTime;
		}

		private void builtDatasets(final ImmutableList<KPIDataSet> builtDatasets)
		{
			if (datasets != null && !datasets.isEmpty())
			{
				throw new AdempiereException("Setting builtDatasets not allowed when some datasets builder were previously set");
			}

			this.builtDatasets = builtDatasets;
		}

		public Builder range(@Nullable final TimeRange range)
		{
			this.range = range;
			return this;
		}

		@Nullable
		public TimeRange getRange()
		{
			return range;
		}

		public Builder datasetsComputeDuration(@Nullable final Duration datasetsComputeDuration)
		{
			this.datasetsComputeDuration = datasetsComputeDuration;
			return this;
		}

		public void putValue(
				@NonNull final String dataSetName,
				@NonNull final KPIDataSetValuesAggregationKey dataSetValueKey,
				@NonNull final String fieldName,
				@NonNull final KPIDataValue value)
		{
			dataSet(dataSetName).putValue(dataSetValueKey, fieldName, value);
		}

		public void putValueIfAbsent(
				@NonNull final String dataSetName,
				@NonNull final KPIDataSetValuesAggregationKey dataSetValueKey,
				@NonNull final String fieldName,
				@NonNull final KPIDataValue value)
		{
			dataSet(dataSetName).putValueIfAbsent(dataSetValueKey, fieldName, value);
		}

		public Builder error(@NonNull final Exception exception)
		{
			final ITranslatableString errorMessage = AdempiereException.isUserValidationError(exception)
					? AdempiereException.extractMessageTrl(exception)
					: TranslatableStrings.adMessage(MSG_FailedLoadingKPI);

			this.error = WebuiError.of(exception, errorMessage);
			return this;
		}

		public Builder error(@NonNull final ITranslatableString errorMessage)
		{
			this.error = WebuiError.of(errorMessage);
			return this;
		}

	}
}
