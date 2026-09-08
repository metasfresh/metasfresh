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

package de.metas.cucumber.stepdefs.mobileui.manufacturing;

import com.google.common.collect.ImmutableList;
import de.metas.cache.CacheMgt;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.attribute.M_Attribute_StepDefData;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.service.ClientId;
import org.compiere.SpringContextHolder;

import java.util.List;

/**
 * Step definitions for the global mobile-UI manufacturing config's editable-attribute list
 * ({@code MobileUI_MFG_Config} + {@code MobileUI_MFG_Config_Attribute}).
 */
@RequiredArgsConstructor
public class MobileUIManufacturingConfig_StepDef
{
	@NonNull private final M_Attribute_StepDefData attributeTable;

	/**
	 * Sets the global {@code MobileUI_MFG_Config} editable-attribute list to exactly the given attributes, in row
	 * order (upserting the config row for {@link ClientId#METASFRESH}). Any attribute not listed is deactivated.
	 * This is what the manufacturing-receipt UI offers as editable and what the receive-time guard validates against.
	 *
	 * <p>Required columns: {@code M_Attribute_ID} — an attribute identifier previously loaded/created via
	 * {@code load M_Attribute} / {@code metasfresh contains M_Attributes}.
	 *
	 * <p><b>Gherkin usage example</b>:
	 * <pre>{@code
	 * And set MobileUI_MFG_Config editable attributes:
	 *   | M_Attribute_ID     |
	 *   | lotNumberAttr      |
	 *   | bestBeforeDateAttr |
	 *   | genericAttr        |
	 * }</pre>
	 */
	@And("set MobileUI_MFG_Config editable attributes:")
	public void setEditableAttributes(@NonNull final DataTable dataTable)
	{
		final List<AttributeCode> attributeCodesInOrder = DataTableRows.of(dataTable)
				.stream()
				.map(row -> attributeTable.get(row.getAsIdentifier("M_Attribute_ID")).getAttributeCode())
				.collect(ImmutableList.toImmutableList());

		final MobileUIManufacturingConfigRepository repository = SpringContextHolder.instance.getBean(MobileUIManufacturingConfigRepository.class);
		repository.saveGlobalEditableAttributeCodesInOrder(ClientId.METASFRESH, attributeCodesInOrder);

		// The repository's global config cache is keyed on the wrong table name (a known quirk, see the
		// IsAllowIssuingAnyHU step def), so the MobileUI_MFG_Config write does not auto-invalidate it. Force a reset.
		CacheMgt.get().reset();
	}
}
