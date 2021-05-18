/*
 * #%L
 * de.metas.elasticsearch.server
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

package de.metas.elasticsearch.indexer.engine;

import com.google.common.base.MoreObjects;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;

final class ESIndexerResult implements IESIndexerResult
{
	public static IESIndexerResult of(final BulkResponse response)
	{
		return new ESIndexerResult(response);
	}

	private final BulkResponse esResponse;
	private Integer _countOK; // lazy
	private Integer _countFailures; // lazy

	private ESIndexerResult(final BulkResponse esResponse)
	{
		this.esResponse = esResponse;
	}

	@Override
	public String getSummary()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("TotalCount", getTotalCount())
				.add("FailuresCount", getFailuresCount())
				.add("ErrorMsg", hasFailures() ? getFailureMessage() : null)
				.add("Duration", getDurationAsString())
				.toString();
	}

	@Override
	public long getDurationInMillis()
	{
		return esResponse.getTook().millis();
	}

	private String getDurationAsString()
	{
		return esResponse.getTook().toString();
	}

	@Override
	public boolean hasFailures()
	{
		return esResponse.hasFailures();
	}

	@Override
	public String getFailureMessage()
	{
		return esResponse.buildFailureMessage();
	}

	@Override
	public int getTotalCount()
	{
		return esResponse.getItems().length;
	}

	@Override
	public int getOKCount()
	{
		if (_countOK == null)
		{
			int count = 0;
			for (final BulkItemResponse response : esResponse.getItems())
			{
				if (!response.isFailed())
				{
					count++;
				}
			}

			_countOK = count;
		}
		return _countOK;
	}

	@Override
	public int getFailuresCount()
	{
		if (_countFailures == null)
		{
			int count = 0;
			for (final BulkItemResponse response : esResponse.getItems())
			{
				if (response.isFailed())
				{
					count++;
				}
			}

			_countFailures = count;
		}
		return _countFailures;
	}

	@Override
	public void throwExceptionIfAnyFailure()
	{
		if (hasFailures())
		{
			throw new ElasticsearchException(getFailureMessage());
		}
	}
}
