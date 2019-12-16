package de.metas.util.collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.util.collections.PagedIterator.Page;
import lombok.Builder;
import lombok.Value;

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

public class PagedIteratorTests
{
	@Test
	public void fullTest_FirstRowZero()
	{
		final PagedIterator<String> it = PagedIterator.<String> builder()
				.firstRow(0)
				.maxRows(55)
				.pageSize(10)
				.pageFetcher((firstRow, pageSize) -> generatePage(firstRow, pageSize))
				.build();

		final List<String> listActual = IteratorUtils.asList(it);
		// System.out.println(listActual);

		assertThat(listActual).hasSize(55);
		assertThat(listActual).isEqualTo(generatePage(0, 55).getRows());
	}

	@Test
	public void fullTest_FirstRowNonZero()
	{
		final PagedIterator<String> it = PagedIterator.<String> builder()
				.firstRow(13)
				.maxRows(55)
				.pageSize(10)
				.pageFetcher((firstRow, pageSize) -> generatePage(firstRow, pageSize))
				.build();

		final List<String> listActual = IteratorUtils.asList(it);
		// System.out.println(listActual);

		assertThat(listActual).hasSize(55);
		assertThat(listActual).isEqualTo(generatePage(13, 55).getRows());
	}

	@Test
	public void testFetcherCalls_FirstRowZero()
	{
		final List<PageFetcherCall> pageFetcherCalls = new ArrayList<>();
		final PagedIterator<String> it = PagedIterator.<String> builder()
				.firstRow(0)
				.maxRows(57)
				.pageSize(10)
				.pageFetcher((firstRow, pageSize) -> {
					final PageFetcherCall call = generatePageFetcherCall(firstRow, pageSize);
					pageFetcherCalls.add(call);
					return call.getReturnValue();
				})
				.build();

		final List<String> listActual = IteratorUtils.asList(it); // iterate all
		// System.out.println(listActual);
		// System.out.println("size=" + listActual.size());
		// pageFetcherCalls.forEach(System.out::println);

		assertThat(listActual).isEqualTo(generatePage(0, 57).getRows());
		assertThat(pageFetcherCalls).isEqualTo(ImmutableList.of(
				generatePageFetcherCall(0, 10),
				generatePageFetcherCall(10, 10),
				generatePageFetcherCall(20, 10),
				generatePageFetcherCall(30, 10),
				generatePageFetcherCall(40, 10),
				generatePageFetcherCall(50, 7)));
	}

	@Test
	public void testFetcherCalls_FirstRowNonZero()
	{
		final List<PageFetcherCall> pageFetcherCalls = new ArrayList<>();
		final PagedIterator<String> it = PagedIterator.<String> builder()
				.firstRow(13)
				.maxRows(55)
				.pageSize(10)
				.pageFetcher((firstRow, pageSize) -> {
					final PageFetcherCall call = generatePageFetcherCall(firstRow, pageSize);
					pageFetcherCalls.add(call);
					return call.getReturnValue();
				})
				.build();

		final List<String> listActual = IteratorUtils.asList(it); // iterate all
		// System.out.println(listActual);
		// System.out.println("size=" + listActual.size());
		// pageFetcherCalls.forEach(System.out::println);

		assertThat(listActual).isEqualTo(generatePage(13, 55).getRows());
		assertThat(pageFetcherCalls).isEqualTo(ImmutableList.of(
				generatePageFetcherCall(13, 10),
				generatePageFetcherCall(23, 10),
				generatePageFetcherCall(33, 10),
				generatePageFetcherCall(43, 10),
				generatePageFetcherCall(53, 10),
				generatePageFetcherCall(63, 5)));
	}

	@Test
	public void pageFetcherReturnsNull()
	{
		final PagedIterator<String> it = PagedIterator.<String> builder()
				.pageSize(10)
				.pageFetcher((firstRow, pageSize) -> null)
				.build();
		assertThat(it).isEmpty();
	}

	private final Page<String> generatePage(final int firstRow, final int pageSize)
	{
		final List<String> rows = new ArrayList<>();
		int row = firstRow;
		while (rows.size() < pageSize)
		{
			rows.add("row-" + row);
			row++;
		}

		return Page.ofRows(rows);
	}

	private final PageFetcherCall generatePageFetcherCall(final int firstRow, final int pageSize)
	{
		return PageFetcherCall.builder()
				.firstRow(firstRow)
				.pageSize(pageSize)
				.returnValue(generatePage(firstRow, pageSize))
				.build();
	}

	@Builder
	@Value
	private static class PageFetcherCall
	{
		int firstRow;
		int pageSize;
		Page<String> returnValue;
	}
}
