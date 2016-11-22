package de.metas.elasticsearch;

/*
 * #%L
 * de.metas.elasticsearch
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Indexer result.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IESIndexerResult
{
	/**
	 * Null/no result. Usually this object is returned by methods which decided to not even try contacting the indexing server because there is no point (e.g. there were no requests to perform).
	 */
	IESIndexerResult NULL = NullESIndexerResult.instance;

	/**
	 * @return user friendly summary of what has been done
	 */
	String getSummary();

	/**
	 * @return duration in milliseconds
	 */
	long getDurationInMillis();

	/**
	 * @return how many documents were considered for processing
	 */
	int getTotalCount();

	/**
	 * @return how many documents were successfully processed (i.e. added to index, removed from index)
	 */
	int getOKCount();

	/**
	 * @return how many documents failed processing (i.e. added to index, removed from index)
	 */
	int getFailuresCount();

	/**
	 * @return true if at least one document failed processing
	 */
	boolean hasFailures();

	/**
	 * @return if {@link #hasFailures()} this method will return a concatenated string of each document failure message
	 */
	String getFailureMessage();

	/**
	 * If {@link #hasFailures()} this method will throw an exception with {@link #getFailureMessage()} as message.
	 */
	void throwExceceptionIfAnyFailure();
}
