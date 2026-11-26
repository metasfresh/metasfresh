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
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.product.PackageDimensionCalcMethod;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import javax.annotation.Nullable;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_HU_UnitType;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_IsActive;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_IsCurrent;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_M_HU_PI_Version_ID;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_Name;
import static de.metas.handlingunits.model.I_M_HU_PI_Version.COLUMNNAME_PackageDimensionCalcMethod;
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

	/**
	 * Creates or upserts {@code M_HU_PI_Version} records.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_HU_PI_Version_ID</b> — (required) alias for cross-step reference<br>
	 *   <b>M_HU_PI_ID</b> — (required, identifier-ref) parent packing instruction<br>
	 *   <b>HU_UnitType</b> — (required) TU, LU, or V<br>
	 *   <b>IsCurrent</b> — (optional) default true<br>
	 *   <b>IsActive</b> — (optional) default true<br>
	 *   <b>M_HU_PackagingCode_ID</b> — (optional, identifier-ref) packaging code<br>
	 *   <b>PackageDimensionCalcMethod</b> — (optional) S (Strapping), R (Repacking), N (Nesting); only valid on TU versions<br>
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_HU_PI_Version:
	 *   | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | IsCurrent | PackageDimensionCalcMethod |
	 *   | tuVersion          | tuPi       | TU          | Y         | S                          |
	 * </pre>
	 */
	@And("metasfresh contains M_HU_PI_Version:")
	public void add_M_HU_PI_Version(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_HU_PI_Version_ID)
				.forEach(row -> buildAndSavePiVersion(row, huPiVersionTable));
	}

	/**
	 * Variant of {@link #add_M_HU_PI_Version} that expects the save to FAIL with a specific error:
	 * it saves the given rows, catches the {@link AdempiereException} (e.g. when {@code PackageDimensionCalcMethod}
	 * is set on a non-TU version), and asserts its {@code errorCode} equals the given code. The errorCode is the
	 * language-independent AD_Message key, so the assertion is stable regardless of the runtime language (cucumber
	 * runs in de_DE, so the message TEXT is German — the key/errorCode is not).
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns Same columns as {@link #add_M_HU_PI_Version}.
	 * @cucumber.example
	 * <pre>
	 * When metasfresh contains M_HU_PI_Version expecting error "M_HU_PI_Version_CalcMethodOnlyOnTU":
	 *   | M_HU_PI_Version_ID | M_HU_PI_ID | HU_UnitType | PackageDimensionCalcMethod |
	 *   | luVersion          | luPi       | LU          | S                          |
	 * </pre>
	 */
	@And("metasfresh contains M_HU_PI_Version expecting error {string}:")
	public void add_M_HU_PI_Version_expectingError(@NonNull final String expectedErrorCode, @NonNull final DataTable dataTable)
	{
		AdempiereException caughtException = null;
		try
		{
			DataTableRows.of(dataTable)
					.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_HU_PI_Version_ID)
					.forEach(row -> buildAndSavePiVersion(row, null));
		}
		catch (final AdempiereException e)
		{
			caughtException = e;
		}

		assertThat(caughtException)
				.as("Expected the save to fail with errorCode %s, but no AdempiereException was thrown", expectedErrorCode)
				.isNotNull();
		assertThat(caughtException.getErrorCode())
				.as("Expected the guard error code")
				.isEqualTo(expectedErrorCode);
	}

	private void buildAndSavePiVersion(
			@NonNull final DataTableRow row,
			@Nullable final M_HU_PI_Version_StepDefData versionTableToStore)
	{
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

		row.getAsOptionalEnum(COLUMNNAME_PackageDimensionCalcMethod, PackageDimensionCalcMethod.class)
				.ifPresent(calcMethod -> piVersion.setPackageDimensionCalcMethod(calcMethod.getCode()));

		saveRecord(piVersion);

		if (versionTableToStore != null)
		{
			versionTableToStore.putOrReplace(row.getAsIdentifier(), piVersion);
		}
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
