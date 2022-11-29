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
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.rest_api.v2.attributes.JsonAttributeService;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID;

public class M_AttributeSetInstance_StepDef
{
	private final M_AttributeSetInstance_StepDefData attributeSetInstanceTable;

	private final JsonAttributeService jsonAttributeService;

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public M_AttributeSetInstance_StepDef(@NonNull final M_AttributeSetInstance_StepDefData attributeSetInstanceTable)
	{
		this.attributeSetInstanceTable = attributeSetInstanceTable;

		this.jsonAttributeService = SpringContextHolder.instance.getBean(JsonAttributeService.class);
	}

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
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> row : tableRows)
		{
			final String attributeSetInstanceIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_M_AttributeSetInstance_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_M_AttributeSetInstance attributeSetInstance = attributeSetInstanceTable.get(attributeSetInstanceIdentifier);
			assertThat(attributeSetInstance).isNotNull();

			final List<I_M_AttributeInstance> attributeInstances = queryBL.createQueryBuilder(I_M_AttributeInstance.class)
					.addEqualsFilter(I_M_AttributeInstance.COLUMN_M_AttributeSetInstance_ID, attributeSetInstance.getM_AttributeSetInstance_ID())
					.addOnlyActiveRecordsFilter()
					.create()
					.list();

			assertThat(attributeInstances).isNotEmpty();

			final String value = DataTableUtil.extractStringForColumnName(row, I_M_AttributeInstance.COLUMNNAME_Value);
			final List<String> attributeValues = StepDefUtil.splitIdentifiers(value);

			assertThat(attributeValues.size()).isEqualTo(attributeInstances.size());

			for (final String attributeValue : attributeValues)
			{
				final I_M_AttributeInstance attributeInstance = attributeInstances.stream()
						.filter(instance -> instance.getValue().equals(attributeValue))
						.findFirst()
						.orElse(null);

				assertThat(attributeInstance).isNotNull();
			}
		}
	}
}