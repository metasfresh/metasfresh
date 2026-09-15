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

package de.metas.cucumber.stepdefs.attribute;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.rest_api.v2.JsonAttributeSetInstance;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.context.SharedTestContext;
import de.metas.cucumber.stepdefs.order.C_OrderLine_StepDefData;
import de.metas.rest_api.v2.attributes.JsonAttributeService;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_AttributeSetInstance;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;

@RequiredArgsConstructor
public class M_AttributeSetInstance_StepDef
{
	private final JsonAttributeService jsonAttributeService = SpringContextHolder.instance.getBean(JsonAttributeService.class);
	private final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;
	private final C_OrderLine_StepDefData orderLineTable;

	@And("metasfresh contains M_AttributeSetInstance with identifier {string}:")
	public void contains_M_AttributeSetInstance(
			@NonNull final String attributeSetInstanceIdentifier,
			@NonNull final String jsonAttributeSetInstanceIS) throws JsonProcessingException
	{
		final JsonAttributeSetInstance jsonAttributeSetInstance = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(jsonAttributeSetInstanceIS, JsonAttributeSetInstance.class);

		final AttributeSetInstanceId attributeSetInstanceId = jsonAttributeService.computeAttributeSetInstanceFromJson(jsonAttributeSetInstance)
				.orElse(null);

		assertThat(attributeSetInstanceId).isNotNull();

		final I_M_AttributeSetInstance attributeSetInstance = queryBL.createQueryBuilder(I_M_AttributeSetInstance.class)
				.addEqualsFilter(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstanceId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_AttributeSetInstance.COLUMN_M_AttributeSetInstance_ID) // ..to shut up the warning
				.create()
				.firstNotNull(I_M_AttributeSetInstance.class);

		assertThat(attributeSetInstance).isNotNull();

		attributeSetInstanceTable.put(attributeSetInstanceIdentifier, attributeSetInstance);
	}

	@And("validate M_AttributeInstance:")
	public void validate_M_AttributeInstance(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final AttributeCode attributeCode = row.getAsOptionalString("AttributeCode").map(AttributeCode::ofString).orElse(null);
			if (attributeCode == null)
			{
				validate_M_AttributeInstance_CommaSeparatedValues(row);
			}
			else
			{
				validate_M_AttributeInstance_SingleValue(row, attributeCode);
			}
		});
	}

	private void validate_M_AttributeInstance_SingleValue(final DataTableRow row, final AttributeCode attributeCode)
	{
		SharedTestContext.put("attributeCode", attributeCode);

		final AttributeSetInstanceId asiId = getAttributeSetInstanceId(row);
		SharedTestContext.put("asiId", asiId);
		final ImmutableAttributeSet asi = asiBL.getImmutableAttributeSetById(asiId);
		SharedTestContext.put("asi", asi);

		final String expectedValue = row.getAsOptionalString("Value").orElse(null);
		final String actualValue = asi.hasAttribute(attributeCode) ? asi.getValueAsString(attributeCode) : null;

		assertThat(actualValue).as("attribute value").isEqualTo(expectedValue);
	}

	/**
	 * @cucumber.stepdef Asserts that no {@code M_AttributeInstance} row exists for the given attribute on the
	 *                   {@code C_OrderLine}'s CURRENT ASI (as opposed to a row that exists but carries a null
	 *                   value). The ASI is resolved fresh from the order-line record rather than from an ASI
	 *                   identifier captured at setup time, because a project-clearing update clones the ASI onto
	 *                   a new id and re-points the line at the clone — checking a stale identifier would silently
	 *                   miss that.
	 * @cucumber.columns
	 *   <b>C_OrderLine_ID</b> &mdash; (required, identifier-ref) the order line whose current ASI is checked.<br>
	 *   <b>AttributeCode</b> &mdash; (required) the {@code M_Attribute.Value} that must be absent.<br>
	 * @cucumber.depends StepDefData: C_OrderLine_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate M_AttributeInstance is absent:
	 *   | C_OrderLine_ID | AttributeCode |
	 *   | orderLine_1    | ProjectValue  |
	 * </pre>
	 */
	@And("validate M_AttributeInstance is absent:")
	public void validate_M_AttributeInstance_absent(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final AttributeCode attributeCode = AttributeCode.ofString(row.getAsString("AttributeCode"));

			final I_C_OrderLine orderLine = row.getAsIdentifier(I_C_OrderLine.COLUMNNAME_C_OrderLine_ID).lookupNotNullIn(orderLineTable);
			InterfaceWrapperHelper.refresh(orderLine);
			final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(orderLine.getM_AttributeSetInstance_ID());
			SharedTestContext.put("asiId", asiId);

			final ImmutableAttributeSet asi = asiBL.getImmutableAttributeSetById(asiId);
			SharedTestContext.put("asi", asi);

			assertThat(asi.hasAttribute(attributeCode))
					.as("M_AttributeInstance for %s must NOT exist on C_OrderLine %s's current ASI %s", attributeCode, orderLine.getC_OrderLine_ID(), asiId)
					.isFalse();
		});
	}

	private void validate_M_AttributeInstance_CommaSeparatedValues(final DataTableRow row)
	{
		final AttributeSetInstanceId asiId = getAttributeSetInstanceId(row);
		SharedTestContext.put("asiId", asiId);
		final ImmutableAttributeSet asi = asiBL.getImmutableAttributeSetById(asiId);
		SharedTestContext.put("asi", asi);

		final List<String> expectedValues = row.getAsCommaSeparatedString("Value");
		assertThat(expectedValues).hasSameSizeAs(asi.getAttributeCodes());

		for (final String expectedValue : expectedValues)
		{
			assertAttributeSetContainsValue(asi, expectedValue);
		}
	}

	private static void assertAttributeSetContainsValue(final ImmutableAttributeSet asi, final String expectedValue)
	{
		final boolean matches = asi.getAttributeCodes().stream().anyMatch(attributeCode -> Objects.equals(asi.getValueAsString(attributeCode), expectedValue));
		assertThat(matches).as("AttributeSet contains value: `" + expectedValue + "`").isTrue();
	}

	private AttributeSetInstanceId getAttributeSetInstanceId(final DataTableRow row)
	{
		return attributeSetInstanceTable.getId(row.getAsIdentifier(COLUMNNAME_M_AttributeSetInstance_ID));
	}

	/**
	 * @cucumber.stepdef Asserts column values of an M_AttributeSetInstance (currently its Description).
	 * @cucumber.columns
	 *   <b>M_AttributeSetInstance_ID</b> &mdash; (required, identifier-ref) the ASI to validate.<br>
	 *   <b>Description</b> &mdash; (optional) expected M_AttributeSetInstance.Description.<br>
	 * @cucumber.depends StepDefData: M_AttributeSetInstance_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate M_AttributeSetInstance:
	 *   | M_AttributeSetInstance_ID | Description |
	 *   | asi_1                     | M_5         |
	 * </pre>
	 */
	@And("validate M_AttributeSetInstance:")
	public void validate_M_AttributeSetInstance(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach((row) -> {
			final I_M_AttributeSetInstance asi = attributeSetInstanceTable.get(row.getAsIdentifier(COLUMNNAME_M_AttributeSetInstance_ID));
			InterfaceWrapperHelper.refresh(asi);

			row.getAsOptionalString(I_M_AttributeSetInstance.COLUMNNAME_Description)
					.ifPresent(expectedDescription -> assertThat(asi.getDescription()).as("M_AttributeSetInstance Description").isEqualTo(expectedDescription));
		});
	}
}
