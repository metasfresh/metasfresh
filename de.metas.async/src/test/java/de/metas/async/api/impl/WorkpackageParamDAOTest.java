package de.metas.async.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.async.api.IWorkpackageParamDAO;
import de.metas.async.model.I_C_Queue_WorkPackage_Param;
import de.metas.bpartner.BPartnerId;
import de.metas.util.Services;
import de.metas.util.StringUtils;

/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class WorkpackageParamDAOTest
{
	private WorkpackageParamDAO workpackageParamDAO;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		workpackageParamDAO = (WorkpackageParamDAO)Services.get(IWorkpackageParamDAO.class);
	}

	@Nested
	public class SetParameterValue
	{
		private I_C_Queue_WorkPackage_Param setParameterValue(final Object parameterValue)
		{
			I_C_Queue_WorkPackage_Param record = newInstance(I_C_Queue_WorkPackage_Param.class);
			workpackageParamDAO.setParameterValue(record, parameterValue);
			return record;
		}

		private BigDecimal extractPNumber(final I_C_Queue_WorkPackage_Param param)
		{
			return InterfaceWrapperHelper.getValueOrNull(param, I_C_Queue_WorkPackage_Param.COLUMNNAME_P_Number);
		}

		private void setParameterValueAndExpectString(final String parameterValue)
		{
			I_C_Queue_WorkPackage_Param param = setParameterValue(parameterValue);
			assertThat(param.getAD_Reference_ID()).isEqualTo(DisplayType.String);
			assertThat(param.getP_String()).isEqualTo(parameterValue);
			assertThat(extractPNumber(param)).isNull();
			assertThat(param.getP_Date()).isNull();
		}

		private void setParameterValueAndExpectNumber(final Object parameterValue, final int expectedDisplayType, BigDecimal expectedNumber)
		{
			I_C_Queue_WorkPackage_Param param = setParameterValue(parameterValue);
			assertThat(param.getAD_Reference_ID()).isEqualTo(expectedDisplayType);
			assertThat(param.getP_String()).isNull();
			assertThat(param.getP_Number()).isEqualTo(expectedNumber);
			assertThat(param.getP_Date()).isNull();
		}

		private void setParameterValueAndExpectDate(final Object parameterValue, Timestamp expectedDate)
		{
			I_C_Queue_WorkPackage_Param param = setParameterValue(parameterValue);
			assertThat(param.getAD_Reference_ID()).isEqualTo(DisplayType.DateTime);
			assertThat(param.getP_String()).isNull();
			assertThat(extractPNumber(param)).isNull();
			assertThat(param.getP_Date()).isEqualTo(expectedDate);
		}

		private void setParameterValueAndBoolean(final Object parameterValue, boolean expectedValue)
		{
			I_C_Queue_WorkPackage_Param param = setParameterValue(parameterValue);
			assertThat(param.getAD_Reference_ID()).isEqualTo(DisplayType.YesNo);
			assertThat(param.getP_String()).isEqualTo(StringUtils.ofBoolean(expectedValue));
			assertThat(extractPNumber(param)).isNull();
			assertThat(param.getP_Date()).isNull();
		}

		@Test
		public void string()
		{
			setParameterValueAndExpectString("");
			setParameterValueAndExpectString("string");
		}

		@Test
		public void integer()
		{
			setParameterValueAndExpectNumber(123, DisplayType.Integer, new BigDecimal("123"));
		}

		@Test
		public void repoId()
		{
			setParameterValueAndExpectNumber(BPartnerId.ofRepoId(123), DisplayType.Integer, new BigDecimal("123"));
		}

		@Test
		public void bigDecimal()
		{
			setParameterValueAndExpectNumber(new BigDecimal("123.45"), DisplayType.Number, new BigDecimal("123.45"));
		}

		@Test
		public void julDate()
		{
			final Date date = TimeUtil.asDate(ZonedDateTime.now());
			setParameterValueAndExpectDate(date, TimeUtil.asTimestamp(date));
		}

		@Test
		public void sqlTimestamp()
		{
			final Timestamp ts = TimeUtil.asTimestamp(ZonedDateTime.now());
			setParameterValueAndExpectDate(ts, ts);
		}

		@Test
		public void zonedDateTime()
		{
			final ZonedDateTime zdt = ZonedDateTime.now();
			setParameterValueAndExpectDate(zdt, TimeUtil.asTimestamp(zdt));
		}

		@Test
		public void localDate()
		{
			final LocalDate date = LocalDate.now();
			setParameterValueAndExpectDate(date, TimeUtil.asTimestamp(date));
		}

	}
}
