package de.metas.util.stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.adempiere.util.collections.IteratorUtils;

import lombok.Builder;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.util
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

@UtilityClass
public final class StreamUtils
{
	private static final int DEFAULT_BUFFERSIZE = 100;

	/**
	 * Transform a given input stream (the upstream) to an output stream (the downstream) by performing following operations:
	 * <ul>
	 * <li>buffering: the input stream elements are fetched in chunks
	 * <li>mapping: each fetched chunk is sent to <code>mapper</code> to be transformed to a stream of output elements
	 * </ul>
	 * 
	 * Usually this method is used when performing slow operations like database queries for a huge amount of input elements.
	 * e.g. you have a stream of IDs (imagine they could be A LOT), you want to stream the loaded models for those IDs but you don't want to load all the IDs in memory,
	 * because they can be a lot and also because the code which is streaming those IDs might be also slow.
	 * You want to load them just in time, when they are needed.
	 * So, using this operation basically you are able to load the IDs in chunks (let's say chunks of 100 IDs), load all those 100 records from database and stream them forward.
	 * 
	 * @param upstream input stream
	 * @param mapper mapper used to convert the input elements to output elements
	 * @param bufferSize internal buffer size (the chunk size)
	 * @return
	 */
	@Builder(builderMethodName = "bufferAndMap")
	private static <IN, OUT> Stream<OUT> bufferAndMapBuilder(
			@NonNull final Stream<IN> upstream,
			@NonNull final Function<List<IN>, Stream<OUT>> mapper,
			final int bufferSize)
	{
		final Iterator<IN> upstreamIterator = upstream.iterator();
		if (!upstreamIterator.hasNext())
		{
			return Stream.empty();
		}
		
		final int bufferSizeEffective = bufferSize > 0 ? bufferSize : DEFAULT_BUFFERSIZE;

		final Stream<List<IN>> downStream = IteratorUtils.stream(() -> {
			if (!upstreamIterator.hasNext())
			{
				return null;
			}

			final List<IN> list = new ArrayList<>(bufferSizeEffective);
			while (upstreamIterator.hasNext() && list.size() < bufferSizeEffective)
			{
				list.add(upstreamIterator.next());
			}

			return list;
		});

		return downStream.flatMap(mapper);
	}
}
