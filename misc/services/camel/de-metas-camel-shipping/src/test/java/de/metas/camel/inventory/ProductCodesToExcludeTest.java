package de.metas.camel.inventory;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/*
 * #%L
 * de-metas-camel-shipping
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class ProductCodesToExcludeTest
{
	@Test
	public void ofNullString()
	{
		final ProductCodesToExclude productCodesToExclude = ProductCodesToExclude.ofCommaSeparatedString(null);
		assertThat(productCodesToExclude.isProductCodeExcluded("P1")).isFalse();
		assertThat(productCodesToExclude.isProductCodeExcluded("P2")).isFalse();
	}

	@Test
	public void ofBlankString()
	{
		final ProductCodesToExclude productCodesToExclude = ProductCodesToExclude.ofCommaSeparatedString("     ");
		assertThat(productCodesToExclude.isProductCodeExcluded("P1")).isFalse();
		assertThat(productCodesToExclude.isProductCodeExcluded("P2")).isFalse();
	}

	@Test
	public void exclude_P1()
	{
		final ProductCodesToExclude productCodesToExclude = ProductCodesToExclude.ofCommaSeparatedString("P1");
		assertThat(productCodesToExclude.isProductCodeExcluded("P1")).isTrue();
		assertThat(productCodesToExclude.isProductCodeExcluded("P2")).isFalse();
	}

	@Test
	public void exclude_P1_and_P2()
	{
		final ProductCodesToExclude productCodesToExclude = ProductCodesToExclude.ofCommaSeparatedString("P1,P2");
		assertThat(productCodesToExclude.isProductCodeExcluded("P1")).isTrue();
		assertThat(productCodesToExclude.isProductCodeExcluded("P2")).isTrue();
		assertThat(productCodesToExclude.isProductCodeExcluded("P3")).isFalse();
	}
}
