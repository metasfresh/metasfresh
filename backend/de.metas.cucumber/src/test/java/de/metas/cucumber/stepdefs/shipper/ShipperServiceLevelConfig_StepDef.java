/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.cucumber.stepdefs.shipper;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.externalsystem.ExternalSystemRepository;
import de.metas.externalsystem.ExternalSystemType;
import de.metas.externalsystem.model.I_ExternalSystem;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Shipper_ServiceLevel_Config;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ShipperServiceLevelConfig_StepDef
{
	@NonNull private final ExternalSystemRepository externalSystemRepository = SpringContextHolder.instance.getBean(ExternalSystemRepository.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Shipper_StepDefData shipperTable;

	private final List<Integer> createdConfigIds = new ArrayList<>();

	@After
	public void cleanUp()
	{
		if (!createdConfigIds.isEmpty())
		{
			queryBL.createQueryBuilder(I_M_Shipper_ServiceLevel_Config.class)
					.addInArrayFilter(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_M_Shipper_ServiceLevel_Config_ID, createdConfigIds)
					.create()
					.delete();
			createdConfigIds.clear();
		}
	}

	@And("metasfresh contains M_Shipper_ServiceLevel_Configs:")
	public void addServiceLevelConfigs(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::upsertServiceLevelConfig);
	}

	private void upsertServiceLevelConfig(@NonNull final DataTableRow row)
	{
		final int shipperId = row.getAsIdentifier(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_M_Shipper_ID)
				.lookupNotNullIdIn(shipperTable).getRepoId();
		final int seqNo = row.getAsInt(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_SeqNo);
		final String serviceLevel = row.getAsString(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_ServiceLevel);

		I_M_Shipper_ServiceLevel_Config record = queryBL.createQueryBuilder(I_M_Shipper_ServiceLevel_Config.class)
				.addEqualsFilter(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_M_Shipper_ID, shipperId)
				.addEqualsFilter(I_M_Shipper_ServiceLevel_Config.COLUMNNAME_SeqNo, seqNo)
				.create()
				.firstOnly();
		if (record == null)
		{
			record = InterfaceWrapperHelper.newInstance(I_M_Shipper_ServiceLevel_Config.class);
			record.setM_Shipper_ID(shipperId);
			record.setSeqNo(seqNo);
		}
		record.setServiceLevel(serviceLevel);

		@Nullable final String externalSystemValue = row.getAsOptionalString(
				I_ExternalSystem.Table_Name + "." + I_ExternalSystem.COLUMNNAME_Value).orElse(null);
		if (externalSystemValue != null)
		{
			final int externalSystemId = externalSystemRepository
					.getIdByType(ExternalSystemType.ofValue(externalSystemValue)).getRepoId();
			record.setExternal_System_ID(externalSystemId);
		}

		InterfaceWrapperHelper.save(record);
		createdConfigIds.add(record.getM_Shipper_ServiceLevel_Config_ID());
	}
}
