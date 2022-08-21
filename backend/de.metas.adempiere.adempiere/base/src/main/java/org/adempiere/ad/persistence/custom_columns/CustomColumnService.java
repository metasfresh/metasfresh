/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package org.adempiere.ad.persistence.custom_columns;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.Null;
import org.compiere.model.PO;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Map;

@Service
public class CustomColumnService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull
	public CustomColumnsJsonValues getCustomColumnsJsonValues(@NonNull final PO record)
	{
		return CustomColumnsConverters.convertToJsonValues(record, extractZoneId(record));
	}

	public void setCustomColumns(@NonNull final PO record, @NonNull final Map<String, Object> valuesByColumnName)
	{
		if (valuesByColumnName.isEmpty())
		{
			return;
		}

		final CustomColumnsPOValues poValues = CustomColumnsConverters.convertToPOValues(valuesByColumnName, record.getPOInfo(), extractZoneId(record));
		setCustomColumns(record, poValues);
	}

	private static void setCustomColumns(final @NonNull PO record, final CustomColumnsPOValues poValues)
	{
		poValues.forEach((columnName, value) -> record.set_ValueOfColumn(columnName, Null.unbox(value)));
	}

	private ZoneId extractZoneId(final @NonNull PO record) {return orgDAO.getTimeZone(OrgId.ofRepoId(record.getAD_Org_ID()));}}
