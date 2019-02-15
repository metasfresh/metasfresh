package de.metas.dataentry.data.json;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

import org.adempiere.user.CreatedUpdatedInfo;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import lombok.Value;

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

@Value
public class JSONDataEntryRecord
{
	Map<DataEntryFieldId, CreatedUpdatedInfo> createdUpdated;

	Map<DataEntryFieldId, ZonedDateTime> dates;

	Map<DataEntryFieldId, DataEntryListValueId> listValues;

	Map<DataEntryFieldId, BigDecimal> numbers;

	Map<DataEntryFieldId, String> strings;

	Map<DataEntryFieldId, Boolean> booleans;
}
