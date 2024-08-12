/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.resource;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.material.planning.IResourceDAO;
import de.metas.product.ResourceId;
import de.metas.resource.ManufacturingResourceType;
import de.metas.resource.ResourceTypeId;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_S_Resource;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_S_Resource.COLUMNNAME_S_Resource_ID;

@RequiredArgsConstructor
public class S_Resource_StepDef
{
	@NonNull private final IUOMDAO uomDao = Services.get(IUOMDAO.class);
	@NonNull private final IResourceDAO resourceDAO = Services.get(IResourceDAO.class);
	@NonNull private final S_Resource_StepDefData resourceTable;

	@And("load S_Resource:")
	public void load_S_Resource(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName("S_Resource_ID.Identifier")
				.forEach(row -> {
					final ResourceId resourceId = ResourceId.ofRepoId(row.getAsInt(COLUMNNAME_S_Resource_ID));
					final I_S_Resource resource = resourceDAO.getById(resourceId);
					assertThat(resource).isNotNull();

					resourceTable.put(row.getAsIdentifier(), resource);
				});
	}

	@And("update S_Resource:")
	public void update_S_Resource(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_S_Resource_ID)
				.forEach(row -> {
					final StepDefDataIdentifier identifier = row.getAsIdentifier();
					final I_S_Resource resource = resourceTable.get(identifier);
					assertThat(resource).isNotNull();

					row.getAsOptionalQuantity(I_S_Resource.COLUMNNAME_CapacityPerProductionCycle, I_S_Resource.COLUMNNAME_CapacityPerProductionCycle + "UOMCode", uomDao::getByX12DE355)
							.ifPresent(capacityPerProductionCycle -> {
								resource.setCapacityPerProductionCycle(capacityPerProductionCycle.toBigDecimal());
								resource.setCapacityPerProductionCycle_UOM_ID(capacityPerProductionCycle.getUomId().getRepoId());
							});

					saveRecord(resource);

					resourceTable.putOrReplace(identifier, resource);
				});
	}

	@And("create S_Resource:")
	public void createResources(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createResource);
	}

	private void createResource(@NonNull final DataTableRow row)
	{
		final ResourceTypeId resourceTypeId = row.getAsIdentifier(I_S_Resource.COLUMNNAME_S_ResourceType_ID).getAsId(ResourceTypeId.class);
		final ValueAndName valueAndName = row.suggestValueAndName();

		final I_S_Resource record = InterfaceWrapperHelper.newInstance(I_S_Resource.class);
		record.setValue(valueAndName.getValue());
		record.setName(valueAndName.getName());
		record.setS_ResourceType_ID(resourceTypeId.getRepoId());
		record.setIsAvailable(true);
		record.setPercentUtilization(BigDecimal.valueOf(100));

		row.getAsOptionalBoolean(I_S_Resource.COLUMNNAME_IsManufacturingResource).ifPresent(record::setIsManufacturingResource);
		row.getAsOptionalEnum(I_S_Resource.COLUMNNAME_ManufacturingResourceType, ManufacturingResourceType.class)
				.map(ManufacturingResourceType::getCode)
				.ifPresent(record::setManufacturingResourceType);
		row.getAsOptionalInt(I_S_Resource.COLUMNNAME_PlanningHorizon).ifPresent(record::setPlanningHorizon);

		row.getAsOptionalQuantity(
						I_S_Resource.COLUMNNAME_CapacityPerProductionCycle,
						I_S_Resource.COLUMNNAME_CapacityPerProductionCycle_UOM_ID,
						uomDao::getByX12DE355
				)
				.ifPresent(capacityPerProductionCycle -> {
					record.setCapacityPerProductionCycle(capacityPerProductionCycle.toBigDecimal());
					record.setCapacityPerProductionCycle_UOM_ID(capacityPerProductionCycle.getUomId().getRepoId());
				});

		InterfaceWrapperHelper.save(record);

		resourceTable.put(row.getAsIdentifier(), record);
	}

}