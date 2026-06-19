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

package de.metas.cucumber.stepdefs.attribute;

import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_M_AttributeSetInstance;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.DB;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Attribute.COLUMNNAME_M_Attribute_ID;
import static org.compiere.model.I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;

/**
 * Exercises the generic core SQL helpers de_metas_attributes.upsert_attributeinstance and
 * .get_attributeinstance_value. These functions are the unit under test.
 * <p>
 * In production upsert_attributeinstance is called by a column&rarr;ASI DB trigger on a host
 * table (e.g. C_OrderLine) when a user edits an attribute value in a grid cell. That trigger
 * lives in the customer repository; the core function is host-agnostic. The function is
 * therefore tested by invoking it directly here &mdash; there is no core user/UI path to drive.
 */
@RequiredArgsConstructor
public class de_metas_attributes_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final M_Attribute_StepDefData attributeTable;
	@NonNull private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	/**
	 * @cucumber.stepdef Calls de_metas_attributes.upsert_attributeinstance once per row.
	 * @cucumber.columns
	 *   <b>M_AttributeSetInstance_ID</b> &mdash; (required, identifier-ref) the ASI to write to.
	 *     If the identifier is not yet known, the function creates a new ASI on demand and the
	 *     returned ASI is stored under this identifier for later steps.<br>
	 *   <b>M_Attribute_ID</b> &mdash; (required, identifier-ref) the attribute to set.<br>
	 *   <b>Value</b> &mdash; (required) the value as text; the function casts it per AttributeValueType.<br>
	 * @cucumber.depends StepDefData: M_AttributeSetInstance_StepDefData, M_Attribute_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When invoke de_metas_attributes.upsert_attributeinstance:
	 *   | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
	 *   | asi_1                     | attr_str       | M     |
	 * </pre>
	 */
	@When("invoke de_metas_attributes.upsert_attributeinstance:")
	public void invoke_upsert_attributeinstance(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier asiIdentifier = row.getAsIdentifier(COLUMNNAME_M_AttributeSetInstance_ID);
			final I_M_AttributeSetInstance existingAsi = attributeSetInstanceTable.getOptional(asiIdentifier).orElse(null);
			final int asiIdInput = existingAsi != null ? existingAsi.getM_AttributeSetInstance_ID() : 0;

			final AttributeId attributeId = attributeTable.getId(row.getAsIdentifier(COLUMNNAME_M_Attribute_ID));
			final String value = row.getAsString("Value");

			final int returnedAsiId = DB.getSQLValueEx(
					ITrx.TRXNAME_ThreadInherited,
					"SELECT de_metas_attributes.upsert_attributeinstance(?::numeric, ?::numeric, ?::text)",
					asiIdInput, attributeId.getRepoId(), value);

			assertThat(returnedAsiId).as("returned M_AttributeSetInstance_ID").isGreaterThan(0);

			if (existingAsi == null)
			{
				final I_M_AttributeSetInstance newAsi = queryBL.createQueryBuilder(I_M_AttributeSetInstance.class)
						.addEqualsFilter(COLUMNNAME_M_AttributeSetInstance_ID, returnedAsiId)
						.create()
						.firstOnlyNotNull(I_M_AttributeSetInstance.class);
				attributeSetInstanceTable.putOrReplace(asiIdentifier, newAsi);
			}
		});
	}

	/**
	 * @cucumber.stepdef Asserts de_metas_attributes.get_attributeinstance_value returns the expected text.
	 * @cucumber.columns
	 *   <b>M_AttributeSetInstance_ID</b> &mdash; (required, identifier-ref) the ASI to read from.<br>
	 *   <b>M_Attribute_ID</b> &mdash; (required, identifier-ref) the attribute to read.<br>
	 *   <b>Value</b> &mdash; (optional) expected value as text; empty asserts null.<br>
	 * @cucumber.depends StepDefData: M_AttributeSetInstance_StepDefData, M_Attribute_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate de_metas_attributes.get_attributeinstance_value:
	 *   | M_AttributeSetInstance_ID | M_Attribute_ID | Value |
	 *   | asi_1                     | attr_str       | M     |
	 * </pre>
	 */
	@Then("validate de_metas_attributes.get_attributeinstance_value:")
	public void validate_get_attributeinstance_value(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final int asiId = attributeSetInstanceTable.getId(row.getAsIdentifier(COLUMNNAME_M_AttributeSetInstance_ID)).getRepoId();
			final AttributeId attributeId = attributeTable.getId(row.getAsIdentifier(COLUMNNAME_M_Attribute_ID));
			final String expected = row.getAsOptionalString("Value").orElse(null);

			final String actual = DB.getSQLValueStringEx(
					ITrx.TRXNAME_ThreadInherited,
					"SELECT de_metas_attributes.get_attributeinstance_value(?::numeric, ?::numeric)",
					asiId, attributeId.getRepoId());

			assertThat(actual).as("get_attributeinstance_value for M_Attribute_ID=%s", attributeId.getRepoId()).isEqualTo(expected);
		});
	}
}
