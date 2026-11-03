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

package de.metas.cucumber.stepdefs.productCategory;

import de.metas.acct.api.AcctSchemaId;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.CostingLevel;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.ValueAndName;
import de.metas.cucumber.stepdefs.attribute.M_AttributeSet_StepDefData;
import de.metas.cucumber.stepdefs.order.C_CompensationGroup_Schema_StepDefData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_Category_Acct;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_AttributeSet_ID;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_Product_Category_ID;
import static org.compiere.model.I_M_Product_Category.COLUMNNAME_M_Product_Category_Parent_ID;

@RequiredArgsConstructor
public class M_Product_Category_StepDef
{
	private static final String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final M_Product_Category_StepDefData productCategoryTable;
	@NonNull private final M_AttributeSet_StepDefData attributeSetTable;
	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final C_CompensationGroup_Schema_StepDefData compensationGroupSchemaTable;

	/**
	 * Updates the accounting settings ({@code M_Product_Category_Acct}) of a product category for an accounting schema,
	 * e.g. to cost the category's products at client level while the schema itself keeps its own costing level.
	 * Meant for a category the scenario created itself, so no other scenario's products are affected.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_Category_ID</b> — (required, identifier-ref) the product category<br>
	 *   <b>C_AcctSchema_ID</b> — (required, identifier-ref) the accounting schema<br>
	 *   <b>CostingLevel</b> — (optional) costing level code, e.g. {@code C} (client) or {@code O} (organization)<br>
	 * @cucumber.depends StepDefData: M_Product_Category_StepDefData, C_AcctSchema_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update M_Product_Category_Acct:
	 *   | M_Product_Category_ID | C_AcctSchema_ID | CostingLevel |
	 *   | productCategory       | acctSchema      | C            |
	 * </pre>
	 */
	@And("update M_Product_Category_Acct:")
	public void update_M_Product_Category_Acct(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product_Category productCategory = row.getAsIdentifier(COLUMNNAME_M_Product_Category_ID).lookupNotNullIn(productCategoryTable);
			final AcctSchemaId acctSchemaId = row.getAsIdentifier(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID).lookupIdIn(acctSchemaTable);

			final I_M_Product_Category_Acct productCategoryAcct = queryBL.createQueryBuilder(I_M_Product_Category_Acct.class)
					.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_M_Product_Category_ID, productCategory.getM_Product_Category_ID())
					.addEqualsFilter(I_M_Product_Category_Acct.COLUMNNAME_C_AcctSchema_ID, acctSchemaId.getRepoId())
					.create()
					.firstOnlyNotNull(I_M_Product_Category_Acct.class);

			row.getAsOptionalString(I_M_Product_Category_Acct.COLUMNNAME_CostingLevel)
					.map(CostingLevel::ofCode)
					.ifPresent(costingLevel -> productCategoryAcct.setCostingLevel(costingLevel.getCode()));

			saveRecord(productCategoryAcct);
		});
	}

	@And("load M_Product_Category:")
	public void load_M_Product_Category(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Product_Category_ID)
				.forEach(row -> {
					final String name = row.getAsString(I_M_Product_Category.COLUMNNAME_Name);
					final String value = row.getAsString(I_M_Product_Category.COLUMNNAME_Value);

					final I_M_Product_Category productCategory = queryBL.createQueryBuilder(I_M_Product_Category.class)
							.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Name, name)
							.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, value)
							.addOnlyActiveRecordsFilter()
							.create()
							.firstOnlyNotNull(I_M_Product_Category.class);
					assertThat(productCategory).as("Unable to load active M_ProductCategory with name=%s and value=%s", name, value).isNotNull();
					productCategoryTable.putOrReplace(row.getAsIdentifier(), productCategory);
				});
	}

	/**
	 * Updates a product category.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_Category_ID</b> — (required, identifier-ref) the category<br>
	 *   <b>OPT.M_AttributeSet_ID</b> — (optional, identifier-ref) the attribute set<br>
	 *   <b>OPT.C_CompensationGroup_Schema_ID</b> — (optional, identifier-ref) the compensation group schema (menu) of the category<br>
	 * @cucumber.depends StepDefData: M_Product_Category_StepDefData, M_AttributeSet_StepDefData, C_CompensationGroup_Schema_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And update M_Product_Category:
	 *   | M_Product_Category_ID | OPT.C_CompensationGroup_Schema_ID |
	 *   | category_1            | schema_1                          |
	 * </pre>
	 */
	@And("update M_Product_Category:")
	public void update_M_Product_Category(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Product_Category_ID)
				.forEach(row -> {
					final StepDefDataIdentifier identifier = row.getAsIdentifier();
					final I_M_Product_Category productCategory = identifier.lookupIn(productCategoryTable);
					assertThat(productCategory).as("Unable to load active M_ProductCategory with identifier=%s", identifier).isNotNull();

					row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSet_ID)
							.map(attributeSetTable::getId)
							.ifPresent(attributeSetId -> productCategory.setM_AttributeSet_ID(attributeSetId.getRepoId()));

					row.getAsOptionalIdentifier(COLUMNNAME_C_CompensationGroup_Schema_ID)
							.map(schemaIdentifier -> schemaIdentifier.lookupNotNullIn(compensationGroupSchemaTable))
							.ifPresent(schema -> InterfaceWrapperHelper.setValue(productCategory, COLUMNNAME_C_CompensationGroup_Schema_ID, schema.getC_CompensationGroup_Schema_ID()));

					saveRecord(productCategory);
					productCategoryTable.putOrReplace(identifier, productCategory);
				});
	}

	@Given("metasfresh contains M_Product_Categories:")
	public void create_M_Product_Categories(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
				.forEach(row -> {
					final ValueAndName valueAndName = row.suggestValueAndName();
					final String value = valueAndName.getValue();

					final I_M_Product_Category productCategoryRecord =
							CoalesceUtil.coalesceSuppliersNotNull(
									() -> queryBL.createQueryBuilder(I_M_Product_Category.class)
											.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, value)
											.create()
											.firstOnly(I_M_Product_Category.class),
									() -> InterfaceWrapperHelper.newInstance(I_M_Product_Category.class)
							);

					productCategoryRecord.setIsActive(true);
					productCategoryRecord.setValue(value);
					productCategoryRecord.setName(valueAndName.getName());

					row.getAsOptionalIdentifier(COLUMNNAME_M_AttributeSet_ID)
							.map(attributeSetTable::getId)
							.ifPresent(attributeSetId -> productCategoryRecord.setM_AttributeSet_ID(attributeSetId.getRepoId()));

					InterfaceWrapperHelper.saveRecord(productCategoryRecord);

					row.getAsOptionalIdentifier().ifPresent(identifier -> productCategoryTable.putOrReplace(identifier, productCategoryRecord));
				});
	}

	/**
	 * Creates a single {@link I_M_Product_Category} record with an explicit {@code Name}/{@code Value} — unlike
	 * {@code metasfresh contains M_Product_Categories:}, which auto-generates them — so a scenario building a
	 * category hierarchy (e.g. for {@code C_CompensationGroup_SchemaLine.M_Product_Category_ID} matching) can
	 * reference categories by a readable name.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>Name</b> — (required) category name<br>
	 *   <b>Value</b> — (required) category value (upsert key)<br>
	 *   <b>OPT.M_Product_Category_Parent_ID</b> — (optional, identifier-ref) parent category, for a hierarchy<br>
	 * @cucumber.depends StepDefData: M_Product_Category_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains M_Product_Category:
	 *   | Identifier         | Name       | Value      |
	 *   | productCategoryTop | Beverages  | BEVERAGES  |
	 * </pre>
	 */
	@Given("metasfresh contains M_Product_Category:")
	public void create_M_Product_Category(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(COLUMNNAME_M_Product_Category_ID)
				.forEach(row -> {
					final String value = row.getAsString(I_M_Product_Category.COLUMNNAME_Value);

					// upsert by Value: a Background step re-runs once per Scenario in the same feature,
					// so a fixed Value must not blow up on the second Scenario's re-creation attempt.
					final I_M_Product_Category record = CoalesceUtil.coalesceSuppliersNotNull(
							() -> queryBL.createQueryBuilder(I_M_Product_Category.class)
									.addEqualsFilter(I_M_Product_Category.COLUMNNAME_Value, value)
									.create()
									.firstOnly(I_M_Product_Category.class),
							() -> InterfaceWrapperHelper.newInstance(I_M_Product_Category.class));

					record.setIsActive(true);
					record.setName(row.getAsString(I_M_Product_Category.COLUMNNAME_Name));
					record.setValue(value);

					row.getAsOptionalIdentifier(COLUMNNAME_M_Product_Category_Parent_ID)
							.map(identifier -> identifier.lookupNotNullIn(productCategoryTable))
							.ifPresent(parent -> record.setM_Product_Category_Parent_ID(parent.getM_Product_Category_ID()));

					saveRecord(record);

					row.getAsOptionalIdentifier().ifPresent(identifier -> productCategoryTable.putOrReplace(identifier, record));
				});
	}
}
