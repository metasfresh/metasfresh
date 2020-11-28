package de.metas.dataentry;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.util.JSONObjectMapper;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class DataEntryFieldIdTest
{

	@Test
	public void serializeToJSON()
	{
		final JSONObjectMapper<DataEntryFieldId> objectMapper = JSONObjectMapper.forClass(DataEntryFieldId.class);
		final DataEntryFieldId dataEntryFieldId = DataEntryFieldId.ofRepoId(30);

		final String result = objectMapper.writeValueAsString(dataEntryFieldId);
		assertThat(result).isEqualTo("30");
	}

	@Test
	public void deserializeFromJSON()
	{
		final JSONObjectMapper<DataEntryFieldId> objectMapper = JSONObjectMapper.forClass(DataEntryFieldId.class);

		final DataEntryFieldId result = objectMapper.readValue("30");
		assertThat(result).isEqualTo(DataEntryFieldId.ofRepoId(30));
	}

}
