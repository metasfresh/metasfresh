package de.metas.ui.web.dashboard;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.LinkedHashMap;

@Value
public class KPIDataResult
{
	public static Builder builder()
	{
		return new Builder();
	}

	Instant createdTime = SystemTime.asInstant();
	String took;
	TimeRange range;
	ImmutableList<KPIDataSet> datasets;

	private KPIDataResult(final Builder builder)
	{
		took = builder.took;
		range = builder.range;
		datasets = builder.datasets
				.values()
				.stream()
				.map(KPIDataSet.KPIDataSetBuilder::build)
				.collect(ImmutableList.toImmutableList());
	}

	public static final class Builder
	{
		private final LinkedHashMap<String, KPIDataSet.KPIDataSetBuilder> datasets = new LinkedHashMap<>();
		private TimeRange range;
		private String took;

		private Builder() { }

		public KPIDataResult build()
		{
			return new KPIDataResult(this);
		}

		public KPIDataSet.KPIDataSetBuilder dataSet(final String name)
		{
			return datasets.computeIfAbsent(name, KPIDataSet::builder);
		}

		public Builder setRange(final TimeRange timeRange)
		{
			range = timeRange;
			return this;
		}

		public TimeRange getRange()
		{
			return range;
		}

		public Builder setTook(final Stopwatch took)
		{
			this.took = took.toString();
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

	}
}
