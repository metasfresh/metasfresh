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
import de.metas.rest_api.v2.attributes.JsonAttributeService;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeSetInstance;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;

@RequiredArgsConstructor
public class M_AttributeSetInstance_StepDef
{
	private final JsonAttributeService jsonAttributeService = SpringContextHolder.instance.getBean(JsonAttributeService.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

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
		final ImmutableAttributeSet asi = attributeDAO.getImmutableAttributeSetById(asiId);
		SharedTestContext.put("asi", asi);

		final String expectedValue = row.getAsOptionalString("Value").orElse(null);
		final String actualValue = asi.hasAttribute(attributeCode) ? asi.getValueAsString(attributeCode) : null;

		assertThat(actualValue).as("attribute value").isEqualTo(expectedValue);
	}

	private void validate_M_AttributeInstance_CommaSeparatedValues(final DataTableRow row)
	{
		final AttributeSetInstanceId asiId = getAttributeSetInstanceId(row);
		SharedTestContext.put("asiId", asiId);
		final ImmutableAttributeSet asi = attributeDAO.getImmutableAttributeSetById(asiId);
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
}