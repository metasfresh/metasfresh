/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2024 metas GmbH
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

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.handlingunits.HUPIAttributeId;
import de.metas.handlingunits.model.I_M_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.handlingunits.model.I_M_HU_PI_Item.COLUMNNAME_M_HU_PI_Version_ID;

public class M_HU_PI_Attribute_StepDef
{
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final M_HU_PI_Attribute_StepDefData huPiAttributeTable;
	private final M_HU_PI_Version_StepDefData huPiVersionTable;

	/**
	 * UseInASI overrides made by {@link #update_M_HU_PI_Attribute_UseInASI(DataTable)} during this scenario,
	 * captured so {@link #resetM_HU_PI_AttributeUseInASIOverrides()} can restore the value the row had before
	 * the update — without assuming the platform default. Per-scenario lifetime via PicoContainer's
	 * fresh-instance-per-scenario semantics.
	 */
	private final List<UseInASIOverride> useInASIOverrides = new ArrayList<>();

	@Value
	private static class UseInASIOverride
	{
		HUPIAttributeId piAttributeId;
		boolean originalUseInASI;
	}

	public M_HU_PI_Attribute_StepDef(
			@NonNull final M_HU_PI_Attribute_StepDefData huPiAttributeTable,
			@NonNull final M_HU_PI_Version_StepDefData huPiVersionTable)
	{
		this.huPiAttributeTable = huPiAttributeTable;
		this.huPiVersionTable = huPiVersionTable;
	}

	@Given("metasfresh contains M_HU_PI_Attribute:")
	public void metasfresh_contains_m_hu_pi_item_product(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> tableRow : rows)
		{
			createHUPIAttribute(tableRow);
		}
	}

	private void createHUPIAttribute(@NonNull final Map<String, String> tableRow)
	{
		final String huPiVersionIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_M_HU_PI_Version_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU_PI_Version huPiVersion = huPiVersionTable.get(huPiVersionIdentifier);

		final String attributeValue = DataTableUtil.extractStringForColumnName(tableRow, I_M_Attribute.Table_Name + "." + I_M_Attribute.COLUMNNAME_Value);

		final AttributeId attributeId = attributeDAO.getAttributeIdByCode(AttributeCode.ofString(attributeValue));

		final I_M_HU_PI_Attribute piAttributeRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Attribute.class);
		piAttributeRecord.setM_HU_PI_Version_ID(huPiVersion.getM_HU_PI_Version_ID());
		piAttributeRecord.setM_Attribute_ID(attributeId.getRepoId());
		piAttributeRecord.setHU_TansferStrategy_JavaClass_ID(540027);
		piAttributeRecord.setIsActive(true);
		piAttributeRecord.setIsDisplayed(true);
		piAttributeRecord.setIsOnlyIfInProductAttributeSet(false);
		piAttributeRecord.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
		piAttributeRecord.setUseInASI(true);
		InterfaceWrapperHelper.saveRecord(piAttributeRecord);

		final String piAttributeIdentifier = DataTableUtil.extractRecordIdentifier(tableRow, I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Attribute_ID);

		huPiAttributeTable.put(piAttributeIdentifier, piAttributeRecord);
	}

	@And("update M_HU_PI_Attribute UseInASI:")
	public void update_M_HU_PI_Attribute_UseInASI(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final int huPiVersionId = row.getAsInt(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID);
			final String attributeValue = row.getAsString(I_M_Attribute.Table_Name + "." + I_M_Attribute.COLUMNNAME_Value);
			final boolean useInASI = row.getAsBoolean(I_M_HU_PI_Attribute.COLUMNNAME_UseInASI);

			final AttributeId attributeId = attributeDAO.getAttributeIdByCode(AttributeCode.ofString(attributeValue));

			final I_M_HU_PI_Attribute piAttribute = queryBL.createQueryBuilder(I_M_HU_PI_Attribute.class)
					.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, huPiVersionId)
					.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, attributeId.getRepoId())
					.addOnlyActiveRecordsFilter()
					.create()
					.firstOnlyNotNull(I_M_HU_PI_Attribute.class);

			useInASIOverrides.add(new UseInASIOverride(
					HUPIAttributeId.ofRepoId(piAttribute.getM_HU_PI_Attribute_ID()),
					piAttribute.isUseInASI()));

			piAttribute.setUseInASI(useInASI);
			InterfaceWrapperHelper.saveRecord(piAttribute);
		});
	}

	/**
	 * Restores any {@code UseInASI} overrides made by {@link #update_M_HU_PI_Attribute_UseInASI(DataTable)}
	 * back to the value the row carried before the override. Fires for every scenario regardless of
	 * pass/fail; no-op when nothing was overridden in this scenario.
	 */
	@After
	public void resetM_HU_PI_AttributeUseInASIOverrides()
	{
		for (final UseInASIOverride override : useInASIOverrides)
		{
			final I_M_HU_PI_Attribute piAttribute = InterfaceWrapperHelper.load(override.getPiAttributeId(), I_M_HU_PI_Attribute.class);
			piAttribute.setUseInASI(override.isOriginalUseInASI());
			InterfaceWrapperHelper.saveRecord(piAttribute);
		}
		useInASIOverrides.clear();
	}
}
