/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms ofValueAndField the GNU General Public License as
 * published by the Free Software Foundation, either version 2 ofValueAndField the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty ofValueAndField
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy ofValueAndField the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.ui.web.dashboard;

import de.metas.ui.web.dashboard.json.KPIJsonOptions;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.assertj.core.api.Assertions.assertThat;

class KPIDataValueTest
{
	@Test
	public void toJsonValue_prettyValues()
	{
		final KPIJsonOptions jsonOpts = KPIJsonOptions.builder()
				.adLanguage("de_DE")
				.zoneId(ZoneId.of("Europe/Berlin"))
				.prettyValues(true)
				.build();

		assertThat(KPIDataValue.ofValueAndType("2019-08-23T00:00:00.000Z", KPIFieldValueType.Date).toJsonValue(jsonOpts))
				.isEqualTo("23.08.2019");

		assertThat(KPIDataValue.ofValueAndType(TimeUtil.asDate(LocalDate.parse("2019-03-04")), KPIFieldValueType.Date).toJsonValue(jsonOpts))
				.isEqualTo("04.03.2019");

		assertThat(KPIDataValue.ofValueAndType(LocalDate.parse("2019-03-04").atStartOfDay(jsonOpts.getZoneId()).toInstant().toEpochMilli(), KPIFieldValueType.Date).toJsonValue(jsonOpts))
				.isEqualTo("04.03.2019");
	}
}