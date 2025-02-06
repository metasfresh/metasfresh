package org.adempiere.ad.validationRule.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Services;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Reference;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.X_AD_Val_Rule;
import org.compiere.util.NamePair;
import org.compiere.util.ValueNamePair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class RegisteredValidationRuleTest
{
	private final static String param1 = "Param1";
	private final static String param2 = "Param2";

	private final static String tableNameForValRule = "TableNameForValRule";
	private final static String tableNameForTestValRule = "TableNameForTestTable";

	private final static String columnNameForRegisteredValRule = "ColumnNameForRegisteredValRule";

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void registerValidationRule_ParametersTest()
	{

		final I_AD_Table tableForValRule = createTable(tableNameForValRule);

		final I_AD_Table tableToTestValrule = createTable(tableNameForTestValRule);

		final I_AD_Column columnForValRule = createColumn(tableToTestValrule, columnNameForRegisteredValRule);

		final I_AD_Reference referenceValue = createTableReferenceValue(tableForValRule);

		columnForValRule.setAD_Reference_Value(referenceValue);

		InterfaceWrapperHelper.save(columnForValRule);

		Services.get(IValidationRuleFactory.class).registerTableValidationRule(tableNameForValRule, ValRuleTest.instance);

		final IValidationRule validationRule = Services.get(IValidationRuleFactory.class).create(tableNameForValRule, null, tableNameForValRule, columnNameForRegisteredValRule);

		assertThat(validationRule.getAllParameters()).containsOnly(param1, param2);
	}

	@Test
	public void registerValidationRuleException_NoRegisteredValRuleTest()
	{
		final I_AD_Table tableForValRule = createTable(tableNameForValRule);

		final I_AD_Table tableToTestValrule = createTable(tableNameForTestValRule);

		final I_AD_Column columnForValRule = createColumn(tableToTestValrule, columnNameForRegisteredValRule);

		final I_AD_Reference referenceValue = createTableReferenceValue(tableForValRule);

		columnForValRule.setAD_Reference_Value(referenceValue);

		InterfaceWrapperHelper.save(columnForValRule);

		final IValidationRule validationRule = Services.get(IValidationRuleFactory.class).create(tableNameForValRule, null, tableNameForValRule, columnNameForRegisteredValRule);

		assertThat(validationRule).isInstanceOf(NullValidationRule.class);
		assertThat(validationRule.getExceptionTableAndColumns()).isEmpty();

	}

	/**
	 * This test is mocking the general validation rule for the table M_Warehouse, which applies to all the columns that point to the table M_Warehouse.
	 * This validation rule has an exception for the M_ReceiptSchedule.M_Warehouse_Dest_ID.
	 * <p>
	 * In this test, the validation Rule is build built for M_ReceiptSchedule.M_Warehouse_Dest_ID, which fits the exception, so the validation rule should be not applied
	 */
	@Test
	public void registerValidationRuleException_WithExceptions_ExceptionTableAndColumnTest()
	{

		final I_AD_Table M_Warehouse = createTable("M_Warehouse");

		final I_AD_Table M_InOut = createTable("M_InOut");
		final I_AD_Table M_ReceiptSchedule = createTable("M_ReceiptSchedule");

		final I_AD_Reference referenceValue = createTableReferenceValue(M_Warehouse);

		final I_AD_Column inout_M_Warehouse_ID = createColumn(M_InOut, "inout_M_Warehouse_ID");
		final I_AD_Column rs_Warehouse_Dest_ID = createColumn(M_ReceiptSchedule, "rs_Warehouse_Dest_ID");

		inout_M_Warehouse_ID.setAD_Reference_Value(referenceValue);
		rs_Warehouse_Dest_ID.setAD_Reference_Value(referenceValue);

		InterfaceWrapperHelper.save(inout_M_Warehouse_ID);
		InterfaceWrapperHelper.save(rs_Warehouse_Dest_ID);

		Services.get(IValidationRuleFactory.class).registerTableValidationRule(M_Warehouse.getTableName(), ValRuleTest.instance);
		Services.get(IValidationRuleFactory.class).registerValidationRuleException(ValRuleTest.instance, M_ReceiptSchedule.getTableName(), rs_Warehouse_Dest_ID.getColumnName(), "test");

		final IValidationRule validationRule = Services.get(IValidationRuleFactory.class).create(M_Warehouse.getTableName(), null, M_ReceiptSchedule.getTableName(), rs_Warehouse_Dest_ID.getColumnName());

		// If a validation rule has no registered rules it is considered NullValidationRule
		assertThat(validationRule).isInstanceOf(NullValidationRule.class);
		assertThat(validationRule.getExceptionTableAndColumns()).isEmpty();
	}

	/**
	 * This test is mocking the general validation rule for the table M_Warehouse, which applies to all the columns that point to the table M_Warehouse.
	 * This validation rule has an exception for the M_ReceiptSchedule.M_Warehouse_Dest_ID.
	 * <p>
	 * In this test, the validation Rule is build built for M_ReceiptSchedule.M_Warehouse_Dest_ID, which fits the exception, so the validation rule should be not applied
	 * The column M_ReceiptSchedule.M_Warehouse_Dest_ID has an SQL validation rule. This one will be applied so the final ValidationRule will be of type SQL
	 */
	@Test
	public void registerValidationRuleException_WithExceptions_ExceptionTableAndColumn_ExistingValRuleInDatabase()
	{

		final I_AD_Table M_Warehouse = createTable("M_Warehouse");

		final I_AD_Table M_InOut = createTable("M_InOut");
		final I_AD_Table M_ReceiptSchedule = createTable("M_ReceiptSchedule");

		final I_AD_Reference referenceValue = createTableReferenceValue(M_Warehouse);

		final I_AD_Column inout_M_Warehouse_ID = createColumn(M_InOut, "inout_M_Warehouse_ID");
		final I_AD_Column rs_Warehouse_Dest_ID = createColumn(M_ReceiptSchedule, "rs_Warehouse_Dest_ID");

		final I_AD_Val_Rule databaseValRule = createSQLValRule("Database ValRule Code");
		rs_Warehouse_Dest_ID.setAD_Val_Rule(databaseValRule);

		inout_M_Warehouse_ID.setAD_Reference_Value(referenceValue);
		rs_Warehouse_Dest_ID.setAD_Reference_Value(referenceValue);

		InterfaceWrapperHelper.save(inout_M_Warehouse_ID);
		InterfaceWrapperHelper.save(rs_Warehouse_Dest_ID);

		Services.get(IValidationRuleFactory.class).registerTableValidationRule(M_Warehouse.getTableName(), ValRuleTest.instance);
		Services.get(IValidationRuleFactory.class).registerValidationRuleException(ValRuleTest.instance, M_ReceiptSchedule.getTableName(), rs_Warehouse_Dest_ID.getColumnName(), "test");

		final IValidationRule validationRule = Services.get(IValidationRuleFactory.class).create(
				M_Warehouse.getTableName(),
				AdValRuleId.ofRepoId(databaseValRule.getAD_Val_Rule_ID()),
				M_ReceiptSchedule.getTableName(),
				rs_Warehouse_Dest_ID.getColumnName());

		// If a validation rule has no registered rules it is considered NullValidationRule
		assertThat(validationRule).isInstanceOf(SQLValidationRule.class);
		assertThat(validationRule.getExceptionTableAndColumns()).isEmpty();
	}

	/**
	 * This test is mocking the general validation rule for the table M_Warehouse, which applies to all the columns that point to the table M_Warehouse.
	 * This validation rule has an exception for the M_ReceiptSchedule.M_Warehouse_Dest_ID.
	 * <p>
	 * In this test, the validation Rule is build built for M_InOut.M_Warehouse_ID, which doesn't fit the exception, so the validation rule should be applied
	 */
	@Test
	public void registerValidationRuleException_WithExceptions_GeneralCaseTest()
	{

		final I_AD_Table M_Warehouse = createTable("M_Warehouse");

		final I_AD_Table M_InOut = createTable("M_InOut");
		final I_AD_Table M_ReceiptSchedule = createTable("M_ReceiptSchedule");

		final I_AD_Reference referenceValue = createTableReferenceValue(M_Warehouse);

		final I_AD_Column inout_M_Warehouse_ID = createColumn(M_InOut, "inout_M_Warehouse_ID");
		final I_AD_Column rs_Warehouse_Dest_ID = createColumn(M_ReceiptSchedule, "rs_Warehouse_Dest_ID");

		inout_M_Warehouse_ID.setAD_Reference_Value(referenceValue);
		rs_Warehouse_Dest_ID.setAD_Reference_Value(referenceValue);

		InterfaceWrapperHelper.save(inout_M_Warehouse_ID);
		InterfaceWrapperHelper.save(rs_Warehouse_Dest_ID);

		Services.get(IValidationRuleFactory.class).registerTableValidationRule(M_Warehouse.getTableName(), ValRuleTest.instance);
		Services.get(IValidationRuleFactory.class).registerValidationRuleException(ValRuleTest.instance, M_ReceiptSchedule.getTableName(), rs_Warehouse_Dest_ID.getColumnName(), "test");

		final IValidationRule validationRule = Services.get(IValidationRuleFactory.class).create(M_Warehouse.getTableName(), null, M_InOut.getTableName(), inout_M_Warehouse_ID.getColumnName());

		final ValueNamePair tableAndColumnException = ValueNamePair.of(M_ReceiptSchedule.getTableName(), rs_Warehouse_Dest_ID.getColumnName(), "test");

		assertThat(validationRule).isInstanceOf(ValRuleTest.class);
		assertThat(validationRule.getExceptionTableAndColumns()).containsOnly(tableAndColumnException);

	}

	private static final class ValRuleTest extends AbstractJavaValidationRule
	{
		private static final ValRuleTest instance = new ValRuleTest();

		private ValRuleTest() {}

		@Override
		public Set<String> getParameters(@Nullable final String contextTableName)
		{
			return ImmutableSet.of(param1, param2);
		}

		@Override
		public boolean accept(final IValidationContext evalCtx, final NamePair item)
		{
			return false; // always reject
		}

	}

	private I_AD_Reference createTableReferenceValue(final I_AD_Table tableForReference)
	{
		final I_AD_Reference reference = InterfaceWrapperHelper.newInstance(I_AD_Reference.class);
		InterfaceWrapperHelper.save(reference);

		final I_AD_Ref_Table refTable = InterfaceWrapperHelper.newInstance(I_AD_Ref_Table.class);
		refTable.setAD_Table_ID(tableForReference.getAD_Table_ID());
		InterfaceWrapperHelper.save(refTable);

		refTable.setAD_Reference_ID(reference.getAD_Reference_ID());

		return reference;
	}

	private I_AD_Table createTable(final String tableName)
	{
		final I_AD_Table table = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		table.setTableName(tableName);
		InterfaceWrapperHelper.save(table);

		return table;
	}

	private I_AD_Column createColumn(final I_AD_Table table, final String columnName)
	{
		final I_AD_Column column = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		column.setColumnName(columnName);
		column.setAD_Table_ID(table.getAD_Table_ID());
		InterfaceWrapperHelper.save(column);

		return column;

	}

	private I_AD_Val_Rule createSQLValRule(final String validationCode)
	{
		final I_AD_Val_Rule registeredValRule = InterfaceWrapperHelper.newInstance(I_AD_Val_Rule.class);
		registeredValRule.setCode(validationCode);
		registeredValRule.setName(validationCode); // name is mandatory
		registeredValRule.setType(X_AD_Val_Rule.TYPE_SQL);
		InterfaceWrapperHelper.save(registeredValRule);

		return registeredValRule;
	}

}
