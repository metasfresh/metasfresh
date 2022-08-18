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

package de.metas.po;

import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.PO;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Map;

@Service
public class CustomColumnService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final CustomColumnDAO customColumnDAO;

	public CustomColumnService(@NonNull final CustomColumnDAO customColumnDAO)
	{
		this.customColumnDAO = customColumnDAO;
	}

	public void saveCustomColumns(@NonNull final TableRecordReference tableRecordReference, @NonNull final Map<String, Object> columnName2Value)
	{
		if (columnName2Value.isEmpty())
		{
			return;
		}

		final PO po = InterfaceWrapperHelper.getPO(tableRecordReference.getModel());
		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(po.getAD_Org_ID()));

		final SaveCustomColumnsRequest saveCustomColumnsRequest = SaveCustomColumnsRequest.builder()
				.po(po)
				.zoneId(zoneId)
				.columnName2Value(columnName2Value)
				.build();

		this.customColumnDAO.save(saveCustomColumnsRequest);
	}

	@NonNull
	public Map<String, Object> getCustomColumnsAsMap(@NonNull final TableRecordReference tableRecordReference)
	{
		final PO po = InterfaceWrapperHelper.getPO(tableRecordReference.getModel());
		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(po.getAD_Org_ID()));

		return this.customColumnDAO.getCustomColumns(po, zoneId);
	}
}
