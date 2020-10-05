/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package org.adempiere.process.rpl.exp;

import de.metas.util.time.SystemTime;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.util.DisplayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class ExportHelperTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		AdempiereTestHelper.createOrgWithTimeZone();

		SystemTime.setTimeSource(() -> 1597126895000L/*Tue, 11 Aug 2020 06:21:35 GMT*/);
	}

	@Test
	void encodeDate()
	{
		// given
		final I_EXP_FormatLine formatLine = setupFormatLine();

		// when
		final String result = ExportHelper.encodeDate(SystemTime.asTimestamp(), formatLine, DisplayType.DateTime);

		// then
		assertThat(result).isEqualTo("2020-08-11T08:21:35+02:00");
	}

	@NonNull
	private I_EXP_FormatLine setupFormatLine()
	{
		final I_EXP_FormatLine formatLine = newInstance(I_EXP_FormatLine.class);
		saveRecord(formatLine);
		return formatLine;
	}
}