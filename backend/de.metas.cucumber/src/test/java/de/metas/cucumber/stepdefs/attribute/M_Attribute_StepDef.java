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
 import de.metas.cucumber.stepdefs.DataTableRows;
 import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
 import de.metas.cucumber.stepdefs.ValueAndName;
 import de.metas.util.Services;
 import io.cucumber.datatable.DataTable;
 import io.cucumber.java.en.And;
 import lombok.NonNull;
 import lombok.RequiredArgsConstructor;
 import org.adempiere.ad.dao.IQueryBL;
 import org.adempiere.mm.attributes.AttributeValueType;
 import org.adempiere.mm.attributes.api.Attribute;
 import org.adempiere.mm.attributes.api.impl.AttributeDAO;
 import org.adempiere.model.InterfaceWrapperHelper;
 import org.compiere.model.I_M_Attribute;

 import static org.assertj.core.api.Assertions.assertThat;

 @RequiredArgsConstructor
 public class M_Attribute_StepDef
 {
	 private final IQueryBL queryBL = Services.get(IQueryBL.class);

	 @NonNull private final M_Attribute_StepDefData attributeTable;

	 @And("load M_Attribute:")
	 public void load_M_Attribute(@NonNull final DataTable dataTable)
	 {
		 DataTableRows.of(dataTable)
				 .setAdditionalRowIdentifierColumnName(I_M_Attribute.COLUMNNAME_M_Attribute_ID)
				 .forEach(row -> {
					 final String value = row.getAsString(I_M_Attribute.COLUMNNAME_Value);

					 final I_M_Attribute attributeRecord = queryBL.createQueryBuilder(I_M_Attribute.class)
							 .addOnlyActiveRecordsFilter()
							 .addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, value)
							 .create()
							 .firstOnly(I_M_Attribute.class);
					 assertThat(attributeRecord).isNotNull();
					 final Attribute attribute = AttributeDAO.fromRecord(attributeRecord);

					 final StepDefDataIdentifier attributeIdentifier = row.getAsIdentifier();
					 attributeTable.putOrReplace(attributeIdentifier, attribute);
				 });
	 }

	 @And("metasfresh contains M_Attributes:")
	 public void metasfresh_contains_M_Attributes(@NonNull final DataTable dataTable)
	 {
		 DataTableRows.of(dataTable)
				 .setAdditionalRowIdentifierColumnName(I_M_Attribute.COLUMNNAME_M_Attribute_ID)
				 .forEach(row -> {
					 final ValueAndName valueAndName = row.suggestValueAndName();
					 final String value = valueAndName.getValue();

					 final I_M_Attribute attributeRecord = CoalesceUtil.coalesceSuppliersNotNull(
							 () -> queryBL.createQueryBuilder(I_M_Attribute.class)
									 .addOnlyActiveRecordsFilter()
									 .addEqualsFilter(I_M_Attribute.COLUMNNAME_Value, value)
									 .create()
									 .firstOnly(I_M_Attribute.class),
							 () -> InterfaceWrapperHelper.newInstance(I_M_Attribute.class));

					 attributeRecord.setValue(value);
					 attributeRecord.setName(valueAndName.getName());

					 row.getAsOptionalEnum(I_M_Attribute.COLUMNNAME_AttributeValueType, AttributeValueType.class)
							 .ifPresent(type -> attributeRecord.setAttributeValueType(type.getCode()));

					 row.getAsOptionalString(I_M_Attribute.COLUMNNAME_DefaultValueSQL)
							 .ifPresent(attributeRecord::setDefaultValueSQL);

					 InterfaceWrapperHelper.saveRecord(attributeRecord);
					 final Attribute attribute = AttributeDAO.fromRecord(attributeRecord);

					 row.getAsOptionalIdentifier().ifPresent(identifier -> attributeTable.putOrReplace(identifier, attribute));
				 });
	 }
 }
