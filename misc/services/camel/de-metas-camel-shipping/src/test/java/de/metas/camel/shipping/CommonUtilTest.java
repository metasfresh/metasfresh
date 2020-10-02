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

package de.metas.camel.shipping;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class CommonUtilTest
{

	@ParameterizedTest
	@CsvSource(
			value = {
					"test,test",
					"-test,test",
					"-test-test,test-test",
					"t-test-test,test-test",
					"tt-test-test,test-test",
					"tK-test,testmhd",
					"ttK-test,testmhd",
					"K-test,test" })
	void convertProductValue(final String input, final String expectedOutput)
	{
		assertThat(CommonUtil.convertProductValue(input)).isEqualTo(expectedOutput);
	}
}