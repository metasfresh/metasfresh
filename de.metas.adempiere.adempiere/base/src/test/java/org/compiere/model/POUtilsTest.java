package org.compiere.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import mockit.Mocked;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class POUtilsTest
{
	@Mocked
	private PO po;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void stripZerosAndLogIssueIfBigDecimalScaleTooBig_too_many_zeroes_strip_zeros()
	{
		final Object result = POUtils.stripZerosAndLogIssueIfBigDecimalScaleTooBig(new BigDecimal("1.123000000000000000000000000000000000"), po);
		assertThat(result).isEqualTo(new BigDecimal("1.123"));
		assertThat(POJOLookupMap.get().getRecords(I_AD_Issue.class)).hasSize(1);
	}

	@Test
	public void stripZerosAndLogIssueIfBigDecimalScaleTooBig_too_many_zeroes_dont_strip_nonzero()
	{
		final Object result = POUtils.stripZerosAndLogIssueIfBigDecimalScaleTooBig(new BigDecimal("1.123456789012345678901234567800000000"), po);
		assertThat(result).isEqualTo(new BigDecimal("1.1234567890123456789012345678"));
		assertThat(POJOLookupMap.get().getRecords(I_AD_Issue.class)).hasSize(1);
	}

	@Test
	public void stripZerosAndLogIssueIfBigDecimalScaleTooBig_dont_change_value()
	{
		final BigDecimal value = new BigDecimal("1.12345");
		final Object result = POUtils.stripZerosAndLogIssueIfBigDecimalScaleTooBig(value, po);
		assertThat(result).isSameAs(value);
		assertThat(POJOLookupMap.get().getRecords(I_AD_Issue.class)).isEmpty();
	}

	@Test
	public void stripZerosAndLogIssueIfBigDecimalScaleTooBig_null()
	{
		final Object result = POUtils.stripZerosAndLogIssueIfBigDecimalScaleTooBig(null, po);
		assertThat(result).isNull();
		assertThat(POJOLookupMap.get().getRecords(I_AD_Issue.class)).isEmpty();
	}

	@Test
	public void stripZerosAndLogIssueIfBigDecimalScaleTooBig_other_object()
	{
		final String someOtherValue = "not a BigDecimal instace";
		final Object result = POUtils.stripZerosAndLogIssueIfBigDecimalScaleTooBig(someOtherValue, po);
		assertThat(result).isSameAs(someOtherValue);
		assertThat(POJOLookupMap.get().getRecords(I_AD_Issue.class)).isEmpty();
	}
}
