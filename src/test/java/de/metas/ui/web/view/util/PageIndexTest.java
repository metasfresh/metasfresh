package de.metas.ui.web.view.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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

public class PageIndexTest
{
	@Test
	public void test_getPageContainingRow()
	{
		final int pageLength = 10;
		assertThat(PageIndex.getPageContainingRow(0, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(1, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(2, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(3, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(4, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(5, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(6, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(7, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(8, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		assertThat(PageIndex.getPageContainingRow(9, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(0, pageLength));
		//
		assertThat(PageIndex.getPageContainingRow(10, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(11, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(12, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(13, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(14, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(15, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(16, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(17, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(18, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));
		assertThat(PageIndex.getPageContainingRow(19, pageLength)).isEqualTo(PageIndex.ofFirstRowAndPageLength(10, pageLength));

		//
		// for (int row = 0; row < 30; row++)
		// {
		// PageIndex page = PageIndex.getPageContainingRow(row, pageLength);
		// System.out.println("row=" + row + " => " + page);
		// }
	}
}
