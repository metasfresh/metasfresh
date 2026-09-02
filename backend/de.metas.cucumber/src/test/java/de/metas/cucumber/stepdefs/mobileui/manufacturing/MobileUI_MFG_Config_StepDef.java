/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.mobileui.manufacturing;

import de.metas.cache.CacheMgt;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_MobileUI_MFG_Config;
import org.compiere.model.I_MobileUI_MFG_Config_Attribute;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions to configure/assert the global {@code MobileUI_MFG_Config_Attribute} editable-attribute list
 * (F31771 - see {@code de.metas.manufacturing.config.MobileUIManufacturingConfig#getEditableAttributeCodesInOrder()}).
 * <p>
 * {@code de.metas.cucumber} does not depend on {@code de.metas.manufacturing.rest-api}, so this step def writes
 * the {@code MobileUI_MFG_Config} / {@code MobileUI_MFG_Config_Attribute} records directly - the same pattern
 * already used by {@code ManufacturingIssueScheduleOnTheFly_StepDef#setIsAllowIssuingAnyHU} for the sibling
 * boolean flags on the same table - rather than calling the production repository (which the Playwright-facing
 * {@code MobileConfigManufacturingCommand} masterdata path uses instead, see
 * {@code de.metas.manufacturing.config.MobileUIManufacturingConfigRepository#saveGlobalEditableAttributeCodesInOrder}).
 * Both paths write the same table, which is what this step proves from the cucumber side.
 */
@RequiredArgsConstructor
public class MobileUI_MFG_Config_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Attribute_StepDefData attributeTable;

	/**
	 * Replaces the global {@code MobileUI_MFG_Config}'s editable-attribute list with the given, ordered rows -
	 * an attribute already present (active or not) is reactivated with the given {@code SeqNo}, a new one is
	 * created, and any active row whose attribute is no longer listed is deactivated.
	 * <p>
	 * Required columns:
	 * <ul>
	 *     <li>{@code SeqNo}</li>
	 *     <li>{@code M_Attribute_ID.Identifier} - identifier of an {@code M_Attribute} already loaded/created
	 *     (e.g. via {@code metasfresh contains M_Attributes:})</li>
	 * </ul>
	 * <pre>
	 * And metasfresh has mobileUI manufacturing editable attributes:
	 *   | SeqNo | M_Attribute_ID.Identifier |
	 *   | 10    | sizeAttr                  |
	 * </pre>
	 */
	@And("metasfresh has mobileUI manufacturing editable attributes:")
	public void setEditableAttributes(@NonNull final DataTable dataTable)
	{
		final I_MobileUI_MFG_Config config = queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_MobileUI_MFG_Config.class)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config.class));
		InterfaceWrapperHelper.save(config);

		final List<I_MobileUI_MFG_Config_Attribute> existingRows = queryBL.createQueryBuilder(I_MobileUI_MFG_Config_Attribute.class)
				.addEqualsFilter(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_MobileUI_MFG_Config_ID, config.getMobileUI_MFG_Config_ID())
				.create()
				.list();

		final Set<Integer> keptAttributeIds = new HashSet<>();

		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier attributeIdentifier = row.getAsIdentifier(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_M_Attribute_ID);
			final Attribute attribute = attributeTable.get(attributeIdentifier);
			final int attributeId = attribute.getAttributeId().getRepoId();
			keptAttributeIds.add(attributeId);

			final I_MobileUI_MFG_Config_Attribute record = existingRows.stream()
					.filter(existingRow -> existingRow.getM_Attribute_ID() == attributeId)
					.findFirst()
					.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_MobileUI_MFG_Config_Attribute.class));

			record.setMobileUI_MFG_Config_ID(config.getMobileUI_MFG_Config_ID());
			record.setM_Attribute_ID(attributeId);
			record.setSeqNo(row.getAsInt(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_SeqNo));
			record.setIsActive(true);
			InterfaceWrapperHelper.save(record);
		});

		existingRows.stream()
				.filter(existingRow -> !keptAttributeIds.contains(existingRow.getM_Attribute_ID()) && existingRow.isActive())
				.forEach(existingRow -> {
					existingRow.setIsActive(false);
					InterfaceWrapperHelper.save(existingRow);
				});

		// The production repository's globalConfigsCache is wired to auto-invalidate on this table (see
		// MobileUIManufacturingConfigRepository), but that repository class is not on this module's classpath -
		// force a full reset defensively, mirroring the sibling step def's same workaround.
		CacheMgt.get().reset();
	}

	/**
	 * Asserts the global {@code MobileUI_MFG_Config}'s CURRENT active editable-attribute list, in {@code SeqNo}
	 * order, matches exactly the given rows.
	 * <p>
	 * Required columns: same as {@link #setEditableAttributes}.
	 * <pre>
	 * Then mobileUI manufacturing editable attributes are:
	 *   | SeqNo | M_Attribute_ID.Identifier |
	 *   | 10    | sizeAttr                  |
	 * </pre>
	 */
	@Then("mobileUI manufacturing editable attributes are:")
	public void assertEditableAttributes(@NonNull final DataTable dataTable)
	{
		final I_MobileUI_MFG_Config config = queryBL.createQueryBuilder(I_MobileUI_MFG_Config.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_MobileUI_MFG_Config.class);
		assertThat(config).as("active MobileUI_MFG_Config row").isNotNull();

		final List<I_MobileUI_MFG_Config_Attribute> actualRows = queryBL.createQueryBuilder(I_MobileUI_MFG_Config_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_MobileUI_MFG_Config_ID, config.getMobileUI_MFG_Config_ID())
				.orderBy(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_SeqNo)
				.create()
				.list();

		final int[] expectedRowCount = {0};
		DataTableRows.of(dataTable).forEach((row, index) -> {
			expectedRowCount[0]++;
			assertThat(actualRows).as("row " + index).hasSizeGreaterThan(index);

			final I_MobileUI_MFG_Config_Attribute actualRow = actualRows.get(index);
			assertThat(actualRow.getSeqNo()).as("SeqNo of row " + index).isEqualTo(row.getAsInt(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_SeqNo));

			final StepDefDataIdentifier attributeIdentifier = row.getAsIdentifier(I_MobileUI_MFG_Config_Attribute.COLUMNNAME_M_Attribute_ID);
			final Attribute expectedAttribute = attributeTable.get(attributeIdentifier);
			assertThat(actualRow.getM_Attribute_ID()).as("M_Attribute_ID of row " + index).isEqualTo(expectedAttribute.getAttributeId().getRepoId());
		});

		assertThat(actualRows).as("active MobileUI_MFG_Config_Attribute rows").hasSize(expectedRowCount[0]);
	}
}
