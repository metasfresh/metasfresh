package de.metas.edi.esb.commons;

/*
 * #%L
 * de.metas.edi.esb
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest
{
	@Test
	void normalize()
	{
		final String input = "~!@#$%^&*()_+{}:\"|<>?|`-=[];'\\,./ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ";
		final String actualResult = Util.normalize(input);

		final String expectedResult = "~!@#$%^&*()_+{}:\"|<>?|`-=[];'\\,./AAAAAAAECEEEEIIIIDNOOOOOxOUUUUYthssaaaaaaaceeeeiiiionooooo÷ouuuuythy";

		assertThat(actualResult).as(input + " must be converted to " + actualResult).isEqualTo(expectedResult);
	}

	@Test
	void trimAndTruncate()
	{
		assertThat(Util.trimAndTruncate("   12345678", 7)).isEqualTo("1234567");
	}

	@Test
	public void removePrecedingZeros()
	{
		final String input = "000000000000000040";
		assertThat(Util.removePrecedingZeros(input)).isEqualTo("40");
	}
}
