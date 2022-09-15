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

package de.metas.cucumber.stepdefs;

import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
import static org.compiere.model.I_M_Attribute.COLUMNNAME_AttributeValueType;
import static org.compiere.model.X_M_Attribute.ATTRIBUTEVALUETYPE_Date;
import static org.compiere.model.X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40;

public class M_HU_Attribute_StepDef
{
	private final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);

	private final M_HU_StepDefData huTable;

	public M_HU_Attribute_StepDef(@NonNull final M_HU_StepDefData huTable)
	{
		this.huTable = huTable;
	}

	@And("update M_HU_Attribute:")
	public void update_M_HU_Attribute(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			updateHuAttribute(row);
		}
	}

	private void updateHuAttribute(@NonNull final Map<String, String> row)
	{
		final String huIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_HU_Attribute.COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_M_HU huRecord = huTable.get(huIdentifier);

		final AttributeId attributeId = AttributeId.ofRepoId(DataTableUtil.extractIntForColumnName(row, I_M_HU_Attribute.COLUMNNAME_M_Attribute_ID));

		final I_M_HU_Attribute huAttributeRecord = huAttributesDAO.retrieveAttribute(huRecord, attributeId);
		assertThat(huAttributeRecord).isNotNull();

		final String attributeValueType = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_AttributeValueType);

		switch (attributeValueType)
		{
			case ATTRIBUTEVALUETYPE_Date:
				final Timestamp attributeValueTimestamp = DataTableUtil.extractDateTimestampForColumnName(row, I_M_HU_Attribute.COLUMNNAME_Value);
				huAttributeRecord.setValueDate(attributeValueTimestamp);
				break;
			case ATTRIBUTEVALUETYPE_StringMax40:
				final String attributeValueString = DataTableUtil.extractStringForColumnName(row, I_M_HU_Attribute.COLUMNNAME_Value);
				huAttributeRecord.setValue(attributeValueString);
				break;
			default:
				throw new AdempiereException(COLUMNNAME_AttributeValueType + " is not supported: " + attributeValueType);
		}

		saveRecord(huAttributeRecord);
	}
}
