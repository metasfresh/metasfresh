package de.metas.util.hash;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.util.hash.HashableString;

/*
 * #%L
 * de.metas.util
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

public class HashableStringTest
{
	@Test
	public void test_isMatching()
	{
		final HashableString value = HashableString.ofPlainValue("plain value");
		final HashableString valueHashed = value.hash();

		assertThat(valueHashed).isSameAs(valueHashed.hash());
		assertThat(value).matches(value::isMatching);
		assertThat(value).matches(valueHashed::isMatching);
		assertThat(valueHashed).matches(value::isMatching);
	}

	@Test
	public void test_null_and_empty()
	{
		HashableString nullValue = HashableString.ofPlainValue(null);
		final HashableString emptyValue = HashableString.ofPlainValue("");

		assertThat(nullValue).isSameAs(emptyValue);
		assertThat(nullValue.hash()).isSameAs(emptyValue.hash());
	}

	@Test
	public void test_fromString_hashed()
	{
		final HashableString valuePlain = HashableString.ofPlainValue("test");
		final HashableString valueHashed = valuePlain.hash();
		final String valueHashedStr = valueHashed.getValue();

		final HashableString valueHashed2 = HashableString.fromString(valueHashedStr);
		assertThat(valueHashed2).isEqualTo(valueHashed);
		assertThat(valueHashed2).matches(valuePlain::isMatching);
	}

	@Test
	public void test_with_specific_string()
	{
		final HashableString valueHashed = HashableString.fromString("sha512:76f8f2efdcfd7306592a2ab2907d200c3de8dd6bffcbdfe75864c23099440355ec40cb72854cf6063036027e8dc317c1f2c693d16b66deb67968fed19fa568e9:4ed70bd7-4f4c-4a41-b992-59a75ac9dd6f");
		assertThat(valueHashed.getSalt()).isEqualTo("4ed70bd7-4f4c-4a41-b992-59a75ac9dd6f");
		assertThat(HashableString.ofPlainValue("test")).matches(valueHashed::isMatching);
	}

}
