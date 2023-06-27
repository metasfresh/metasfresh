/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.shipmentschedule;

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Item;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit.COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Item.COLUMNNAME_M_ShipmentSchedule_ExportAudit_Item_ID;
import static de.metas.inoutcandidate.model.I_M_ShipmentSchedule_ExportAudit_Item.COLUMNNAME_M_ShipmentSchedule_ID;
import static org.assertj.core.api.Assertions.*;

public class M_ShipmentSchedule_ExportAudit_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_ShipmentSchedule_ExportAudit_StepDefData shipmentScheduleExportAuditTable;
	private final M_ShipmentSchedule_ExportAudit_Item_StepDefData shipmentScheduleExportAuditItemTable;
	private final M_ShipmentSchedule_StepDefData shipmentScheduleTable;

	public M_ShipmentSchedule_ExportAudit_StepDef(
			@NonNull final M_ShipmentSchedule_ExportAudit_StepDefData shipmentScheduleExportAuditTable,
			@NonNull final M_ShipmentSchedule_ExportAudit_Item_StepDefData shipmentScheduleExportAuditItemTable,
			@NonNull final M_ShipmentSchedule_StepDefData shipmentScheduleTable)
	{
		this.shipmentScheduleExportAuditTable = shipmentScheduleExportAuditTable;
		this.shipmentScheduleExportAuditItemTable = shipmentScheduleExportAuditItemTable;
		this.shipmentScheduleTable = shipmentScheduleTable;
	}

	@And("validate M_ShipmentSchedule_ExportAudit:")
	public void validate_M_ShipmentSchedule_ExportAudit(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String scheduleExportAuditIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipmentSchedule_ExportAudit scheduleExportAudit = shipmentScheduleExportAuditTable.get(scheduleExportAuditIdentifier);
			assertThat(scheduleExportAudit).isNotNull();

			final String exportStatus = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule_ExportAudit.COLUMNNAME_ExportStatus);
			assertThat(scheduleExportAudit.getExportStatus()).isEqualTo(exportStatus);
		}
	}

	@And("validate M_ShipmentSchedule_ExportAudit_Item:")
	public void validate_M_ShipmentSchedule_ExportAudit_Item(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String scheduleExportAuditIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ExportAudit_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipmentSchedule_ExportAudit scheduleExportAudit = shipmentScheduleExportAuditTable.get(scheduleExportAuditIdentifier);
			assertThat(scheduleExportAudit).isNotNull();

			final String scheduleIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_ShipmentSchedule schedule = shipmentScheduleTable.get(scheduleIdentifier);
			assertThat(schedule).isNotNull();

			final I_M_ShipmentSchedule_ExportAudit_Item scheduleExportAuditItem = queryBL.createQueryBuilder(I_M_ShipmentSchedule_ExportAudit_Item.class)
					.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit_Item.COLUMN_M_ShipmentSchedule_ExportAudit_ID, scheduleExportAudit.getM_ShipmentSchedule_ExportAudit_ID())
					.addEqualsFilter(I_M_ShipmentSchedule_ExportAudit_Item.COLUMN_M_ShipmentSchedule_ID, schedule.getM_ShipmentSchedule_ID())
					.create()
					.firstOnlyNotNull(I_M_ShipmentSchedule_ExportAudit_Item.class);

			assertThat(scheduleExportAuditItem).isNotNull();

			final String exportStatus = DataTableUtil.extractStringForColumnName(row, I_M_ShipmentSchedule_ExportAudit_Item.COLUMNNAME_ExportStatus);
			assertThat(scheduleExportAuditItem.getExportStatus()).isEqualTo(exportStatus);

			final String scheduleExportAuditItemIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_ShipmentSchedule_ExportAudit_Item_ID + "." + TABLECOLUMN_IDENTIFIER);
			shipmentScheduleExportAuditItemTable.put(scheduleExportAuditItemIdentifier, scheduleExportAuditItem);
		}
	}
}
