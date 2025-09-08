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

package de.metas.cucumber.stepdefs.shipper;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.organization.OrgId;
import de.metas.shipping.IShipperDAO;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Shipper;
import org.compiere.util.Env;

@RequiredArgsConstructor
public class M_Shipper_StepDef
{
	private final IShipperDAO shipperDAO = Services.get(IShipperDAO.class);

	@NonNull private final M_Shipper_StepDefData shipperTable;

	@And("load M_Shipper:")
	public void load_M_Shipper(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::loadM_Shipper);
	}

	private void loadM_Shipper(@NonNull final DataTableRow row)
	{

		final String name = row.getAsString(I_M_Shipper.COLUMNNAME_Name);
		final I_M_Shipper shipperRecord = shipperDAO.getByName(name).orElseThrow(() -> new AdempiereException("No shipper found for " + name));
		row.getAsOptionalString(I_M_Shipper.COLUMNNAME_InternalName).ifPresent(shipperRecord::setInternalName);
		InterfaceWrapperHelper.saveRecord(shipperRecord);

		shipperTable.put(row.getAsIdentifier(), shipperRecord);
	}

	@And("contains M_Shippers")
	public void createOrUpdateShippers(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Shipper.COLUMNNAME_M_Shipper_ID)
				.forEach(this::createOrUpdateShipper);
	}

	public void createOrUpdateShipper(@NonNull final DataTableRow row)
	{
		final ValueAndName valueAndName = row.suggestValueAndName();
		final OrgId orgId = Env.getOrgId();

		final I_M_Shipper record = CoalesceUtil.coalesceSuppliersNotNull(
				() -> shipperDAO.getShipperIdByValue(valueAndName.getValue(), orgId).map(shipperDAO::getById).orElse(null),
				() -> shipperDAO.getByName(valueAndName.getName()).orElse(null),
				() -> InterfaceWrapperHelper.newInstance(I_M_Shipper.class)
		);

		record.setAD_Org_ID(orgId.getRepoId());
		record.setValue(valueAndName.getValue());
		record.setName(valueAndName.getName());

		row.getAsOptionalString(I_M_Shipper.COLUMNNAME_InternalName)
				.map(StringUtils::trimBlankToNull)
				.ifPresent(record::setInternalName);

		InterfaceWrapperHelper.save(record);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> shipperTable.put(identifier, record));
	}

}
