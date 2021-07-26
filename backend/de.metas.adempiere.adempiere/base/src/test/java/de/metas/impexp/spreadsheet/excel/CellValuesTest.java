/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.impexp.spreadsheet.excel;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.impexp.spreadsheet.excel.CellValue;
import de.metas.impexp.spreadsheet.excel.CellValues;
import org.assertj.core.api.Assertions;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

class CellValuesTest
{

	@Nested
	class toCellValue
	{
		@Test
		public void intValue()
		{
			Assertions.assertThat(CellValues.toCellValue(12))
					.isEqualTo(CellValue.ofNumber(12));
		}
		@Test
		public void repoIdAware()
		{
			Assertions.assertThat(CellValues.toCellValue(BPartnerId.ofRepoId(12)))
					.isEqualTo(CellValue.ofNumber(12));
		}

		@Test
		public void bigDecimal()
		{
			Assertions.assertThat(CellValues.toCellValue(new BigDecimal("12.33")))
					.isEqualTo(CellValue.ofNumber(new BigDecimal("12.33")));
		}

		@Test
		public void booleanValue()
		{
			Assertions.assertThat(CellValues.toCellValue(true))
					.isEqualTo(CellValue.ofBoolean(true));
		}

		@Test
		public void stringValue()
		{
			Assertions.assertThat(CellValues.toCellValue("string value"))
					.isEqualTo(CellValue.ofString("string value"));
		}

		@Test
		public void timestamp()
		{
			final Timestamp timestamp = SystemTime.asTimestamp();
			Assertions.assertThat(CellValues.toCellValue(timestamp))
					.isEqualTo(CellValue.ofDate(TimeUtil.asDate(timestamp)));
		}
	}
}