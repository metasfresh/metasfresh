package de.metas.payment.sepa.sepamarshaller.impl;

/*
 * #%L
 * de.metas.payment.sepa
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import de.metas.payment.sepa.sepamarshaller.impl.SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02;

public class SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02Tests
{

	@Test
	public void testSplitAddress1_1()
	{
		final Pattern pattern = Pattern.compile(SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02.REGEXP_STREET_AND_NUMER_SPLIT);
		final Matcher matcher = pattern.matcher("Carretera Nueva Jarilla");

		assertThat(matcher.matches(), is(true));
		assertThat(matcher.group(1), is("Carretera Nueva Jarilla"));
		assertThat(matcher.group(2), isEmptyOrNullString());
	}
	
	@Test
	public void testSplitAddress1_2()
	{
		final Pattern pattern = Pattern.compile(SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02.REGEXP_STREET_AND_NUMER_SPLIT);
		final Matcher matcher = pattern.matcher("Laternenstrasse 14");

		assertThat(matcher.matches(), is(true));
		assertThat(matcher.group(1).trim(), is("Laternenstrasse"));
		assertThat(matcher.group(2), is("14"));
	}

	@Test
	public void testSplitAddress1_3()
	{
		final Pattern pattern = Pattern.compile(SEPACustomerCTIMarshaler_Pain_001_001_03_CH_02.REGEXP_STREET_AND_NUMER_SPLIT);
		final Matcher matcher = pattern.matcher("Laternenstrasse 14-26c");

		assertThat(matcher.matches(), is(true));
		assertThat(matcher.group(1).trim(), is("Laternenstrasse"));
		assertThat(matcher.group(2), is("14-26c"));
	}
	
}
