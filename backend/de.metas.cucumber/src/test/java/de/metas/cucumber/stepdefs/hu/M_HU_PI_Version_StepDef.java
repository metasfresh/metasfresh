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

package de.metas.cucumber.stepdefs.hu;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;

import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_HU_UnitType;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_IsCurrent;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_Name;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class M_HU_PI_Version_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_PI_StepDefData huPiTable;
	private final M_HU_PI_Version_StepDefData huPiVersionTable;
	private final M_HU_PackagingCode_StepDefData huPackagingCodeTable;

	public M_HU_PI_Version_StepDef(
			@NonNull final M_HU_PI_StepDefData huPiTable,
			@NonNull final M_HU_PI_Version_StepDefData huPiVersionTable,
			@NonNull final M_HU_PackagingCode_StepDefData huPackagingCodeTable)
	{
		this.huPiTable = huPiTable;
		this.huPiVersionTable = huPiVersionTable;
		this.huPackagingCodeTable = huPackagingCodeTable;
	}

	@And("metasfresh contains M_HU_PI_Version:")
	public void add_M_HU_PI_Version(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_HU_PI_Version_ID)
				.forEach(row -> {
					final I_M_HU_PI huPi = row.getAsIdentifier(COLUMNNAME_M_HU_PI_ID).lookupNotNullIn(huPiTable);
					final String name = row.suggestValueAndName(null, huPi::getName).getName();
					final String huUnitType = row.getAsString(COLUMNNAME_HU_UnitType); //dev-note: HU_UNITTYPE_AD_Reference_ID=540472;
					final boolean isCurrent = row.getAsOptionalBoolean(COLUMNNAME_IsCurrent).orElseTrue();
					final boolean active = row.getAsOptionalBoolean(COLUMNNAME_IsActive).orElseTrue();

					final I_M_HU_PI_Version existingPiVersion = queryBL.createQueryBuilder(I_M_HU_PI_Version.class)
							.addEqualsFilter(COLUMNNAME_M_HU_PI_ID, huPi.getM_HU_PI_ID())
							.addStringLikeFilter(COLUMNNAME_Name, name, true)
							.addEqualsFilter(COLUMNNAME_HU_UnitType, huUnitType)
							.addEqualsFilter(COLUMNNAME_IsActive, active)
							.create()
							.firstOnly(I_M_HU_PI_Version.class);

					final I_M_HU_PI_Version piVersion = CoalesceUtil.coalesceSuppliers(
							() -> existingPiVersion,
							() -> InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI_Version.class)
					);
					assertThat(piVersion).isNotNull();

					piVersion.setM_HU_PI_ID(huPi.getM_HU_PI_ID());
					piVersion.setName(name);
					piVersion.setHU_UnitType(huUnitType);
					piVersion.setIsCurrent(isCurrent);
					piVersion.setIsActive(active);

					row.getAsOptionalIdentifier(I_M_HU_PI_Version.COLUMNNAME_M_HU_PackagingCode_ID)
							.ifPresent(huPackagingCodeIdentifier -> {
								final int huPackagingCodeId = huPackagingCodeIdentifier.isNullPlaceholder()
										? -1
										: huPackagingCodeTable.get(huPackagingCodeIdentifier).getM_HU_PackagingCode_ID();

								piVersion.setM_HU_PackagingCode_ID(huPackagingCodeId);
							});

					saveRecord(piVersion);
					huPiVersionTable.put(row.getAsIdentifier(), piVersion);
				});
	}

	@And("load M_HU_PI_Version:")
	public void load_M_HU_PI_Version(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_HU_PI_Version_ID)
				.forEach(row -> {
					final StepDefDataIdentifier huPIVersionIdentifier = row.getAsIdentifier();
					final int huPIVersionId = row.getAsInt(COLUMNNAME_M_HU_PI_Version_ID);

					final I_M_HU_PI_Version huPiVersionRecord = InterfaceWrapperHelper.load(huPIVersionId, I_M_HU_PI_Version.class);

					assertThat(huPiVersionRecord).isNotNull();

					huPiVersionTable.put(huPIVersionIdentifier, huPiVersionRecord);
				});
	}
}
