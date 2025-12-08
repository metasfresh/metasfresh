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

 import de.metas.common.util.CoalesceUtil;
 import de.metas.cucumber.stepdefs.DataTableUtil;
 import de.metas.cucumber.stepdefs.StepDefConstants;
 import de.metas.util.Services;
 import io.cucumber.datatable.DataTable;
 import io.cucumber.java.en.And;
 import lombok.NonNull;
 import org.adempiere.ad.dao.IQueryBL;
 import org.adempiere.model.InterfaceWrapperHelper;
 import org.compiere.model.I_M_Attribute;

 import java.util.List;
 import java.util.Map;

 import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
 import static org.assertj.core.api.Assertions.*;

 public class M_Attribute_StepDef
 {
	 private final IQueryBL queryBL = Services.get(IQueryBL.class);

	 private final M_Attribute_StepDefData attributeTable;

	 public M_Attribute_StepDef(@NonNull final M_Attribute_StepDefData attributeTable)
	 {
		 this.attributeTable = attributeTable;
	 }

	 @And("load M_Attribute:")
	 public void load_M_Attribute(@NonNull final DataTable dataTable)
	 {
		 final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		 for (final Map<String, String> row : tableRows)
		 {
			 final String value = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_Value);

			 final I_M_Attribute attributeRecord = queryBL.createQueryBuilder(I_M_Attribute.class)
					 .addOnlyActiveRecordsFilter()
					 .addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, value)
					 .create()
					 .firstOnly(I_M_Attribute.class);

			 assertThat(attributeRecord).isNotNull();

			 final String attributeIdentifier = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_M_Attribute_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			 attributeTable.putOrReplace(attributeIdentifier, attributeRecord);
		 }
	 }

	 @And("metasfresh contains M_Attributes:")
	 public void metasfresh_contains_M_Attributes(@NonNull final DataTable dataTable)
	 {
		 for (final Map<String, String> row : dataTable.asMaps())
		 {
			 final String attrIdentifier = DataTableUtil.extractStringForColumnName(row, TABLECOLUMN_IDENTIFIER);

			 final String value = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_Value);
			 final String name = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_Name);
			 final String type = DataTableUtil.extractStringForColumnName(row, I_M_Attribute.COLUMNNAME_AttributeValueType);

			 
			 final I_M_Attribute attributeRecord = CoalesceUtil.coalesceSuppliersNotNull(
					 () -> queryBL.createQueryBuilder(I_M_Attribute.class)
							 .addOnlyActiveRecordsFilter()
							 .addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, value)
							 .create()
							 .firstOnly(I_M_Attribute.class),
					 () -> InterfaceWrapperHelper.newInstance(I_M_Attribute.class));

			 attributeRecord.setValue(value);
			 attributeRecord.setName(name);
			 attributeRecord.setAttributeValueType(type);
			 InterfaceWrapperHelper.saveRecord(attributeRecord);

			 attributeTable.putOrReplace(attrIdentifier, attributeRecord);
		 }
	 }
 }
