package de.metas.ui.web.dashboard;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZoneId;

import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import de.metas.ui.web.window.datatypes.json.JSONOptions;

/*
 * #%L
 * metasfresh-webui-api
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

public class KPIFieldTest
{
	@Test
	public void test_convertValueToJsonUserFriendly_ToDate()
	{
		final KPIField kpiField = newKPIField(KPIFieldValueType.Date);
		final JSONOptions jsonOpts = newJSONOptions();

		assertConvertValueToJsonUserFriendly(kpiField, jsonOpts, "2019-08-23T00:00:00.000Z", "23.08.2019");

		final java.util.Date date_2019_03_04 = TimeUtil.getDay(2019, 03, 04);
		assertConvertValueToJsonUserFriendly(kpiField, jsonOpts, date_2019_03_04, "04.03.2019");
		assertConvertValueToJsonUserFriendly(kpiField, jsonOpts, date_2019_03_04.getTime(), "04.03.2019");
	}

	private void assertConvertValueToJsonUserFriendly(
			final KPIField kpiField,
			final JSONOptions jsonOpts,
			final Object valueToConvert,
			final String expectedValue)
	{
		final Object result = kpiField.convertValueToJsonUserFriendly(valueToConvert, jsonOpts);
		assertThat(result).isInstanceOf(String.class);
		assertThat(result.toString()).isEqualTo(expectedValue);
	}

	private JSONOptions newJSONOptions()
	{
		final JSONOptions jsonOpts = JSONOptions.builder()
				.adLanguage("de_DE")
				.zoneId(ZoneId.of("Europe/Berlin"))
				.build();
		return jsonOpts;
	}

	private KPIField newKPIField(final KPIFieldValueType valueType)
	{
		final KPIField kpiField = KPIField.builder()
				.setFieldName("test")
				.setValueType(valueType)
				.setESPath("test")
				.build();
		return kpiField;
	}
}
