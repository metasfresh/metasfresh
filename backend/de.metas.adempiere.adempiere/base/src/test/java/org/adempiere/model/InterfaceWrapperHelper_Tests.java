package org.adempiere.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.GridTab;
import org.compiere.model.I_Test;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InterfaceWrapperHelper_Tests
{
	public interface I_TestModel
	{
		//@formatter:off
		String Table_Name = "TestModel";

		String COLUMNNAME_OverridableValue = "OverridableValue";
		int getOverridableValue();
		void setOverridableValue(int value);

		String COLUMNNAME_OverridableValue_Override = "OverridableValue_Override";
		int getOverridableValue_Override();
		void setOverridableValue_Override(int value);

		String COLUMNNAME_NotOverridableValue = "NotOverridableValue";
		int getNotOverridableValue();
		void setNotOverridableValue(int value);

		String COLUMNNAME_C_Tax_ID = "C_Tax_ID";
		int getC_Tax_ID();
		void setC_Tax_ID(final int taxId);
		//
		String COLUMNNAME_C_Tax_Override_ID = "C_Tax_Override_ID";
		int getC_Tax_Override_ID();
		void setC_Tax_Override_ID(final int taxId);
		//@formatter:on
	}

	public interface I_TestModel_Ext extends I_TestModel
	{
	}

	public interface ITaxAware
	{
		String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

		int getC_Tax_ID();

		void setC_Tax_ID(final int taxId);
	}

	private PlainContextAware contextProvider;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		contextProvider = PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx());
	}

	/**
	 * Tests {@link InterfaceWrapperHelper#getValueOverrideOrValue(Object, String)}.
	 */
	@Test
	public void test_getValueOverrideOrValue_OverridableColumn()
	{
		final I_TestModel testModel = InterfaceWrapperHelper.newInstance(I_TestModel.class, contextProvider);

		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue), is(nullValue()));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue_Override), is(nullValue()));

		testModel.setOverridableValue(10);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue), is(10));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue_Override), is(nullValue()));

		testModel.setOverridableValue_Override(20);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue), is(20));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue_Override), is(20));

		InterfaceWrapperHelper.setValue(testModel, I_TestModel.COLUMNNAME_OverridableValue_Override, null);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue), is(10));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_OverridableValue_Override), is(nullValue()));
	}

	/**
	 * Tests {@link InterfaceWrapperHelper#getValueOverrideOrValue(Object, String)}.
	 */
	@Test
	public void test_getValueOverrideOrValue_IDOverridableColumn()
	{
		final I_TestModel testModel = InterfaceWrapperHelper.newInstance(I_TestModel.class, contextProvider);

		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_ID), is(nullValue()));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_Override_ID), is(nullValue()));

		testModel.setC_Tax_ID(10);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_ID), is(10));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_Override_ID), is(nullValue()));

		testModel.setC_Tax_Override_ID(20);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_ID), is(20));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_Override_ID), is(20));

		InterfaceWrapperHelper.setValue(testModel, I_TestModel.COLUMNNAME_C_Tax_Override_ID, null);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_ID), is(10));
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_C_Tax_Override_ID), is(nullValue()));
	}

	/**
	 * Tests {@link InterfaceWrapperHelper#getValueOverrideOrValue(Object, String)}.
	 */
	@Test
	public void test_getValueOverrideOrValue_NotOverridableColumn()
	{
		final I_TestModel testModel = InterfaceWrapperHelper.newInstance(I_TestModel.class, contextProvider);

		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_NotOverridableValue), is(nullValue()));

		testModel.setNotOverridableValue(10);
		assertThat(InterfaceWrapperHelper.getValueOverrideOrValue(testModel, I_TestModel.COLUMNNAME_NotOverridableValue), is(10));
	}

	/**
	 * Makes sure {@link InterfaceWrapperHelper#create(Object, Class, boolean)} when using useOldValues flag is working correctly.
	 */
	@Test
	public void test_wrapToOldValues_POJOWrapper()
	{
		final I_TestModel baseModel = InterfaceWrapperHelper.newInstance(I_TestModel.class, contextProvider);
		InterfaceWrapperHelper.save(baseModel);
		test_wrapToOldValues(baseModel);
	}

	@Test
	public void test_wrapToOldValues_GridTabWrapper()
	{
		final GridTab gridTab = Mockito.mock(GridTab.class);
		Mockito.doReturn(I_TestModel.Table_Name).when(gridTab).get_TableName();
		Mockito.doReturn(I_TestModel.Table_Name).when(gridTab).getTableName();

		final I_TestModel baseModel = InterfaceWrapperHelper.create(gridTab, I_TestModel.class);
		test_wrapToOldValues(baseModel);
	}

	@Test
	@Disabled("test failing because final PO.get_TableName could not be mocked")
	public void test_wrapToOldValues_POWrapper()
	{
		final PO po = Mockito.mock(PO.class);
		Mockito.doReturn(I_TestModel.Table_Name).when(po).get_TableName();

		final I_TestModel baseModel = InterfaceWrapperHelper.create(po, I_TestModel.class);
		test_wrapToOldValues(baseModel);
	}

	private void test_wrapToOldValues(final I_TestModel baseModel)
	{
		final I_TestModel_Ext extModel = InterfaceWrapperHelper.create(baseModel, I_TestModel_Ext.class);
		assertThat("Old values flag shall not be set by default", InterfaceWrapperHelper.isOldValues(extModel), is(false));

		final I_TestModel extModelOld = InterfaceWrapperHelper.createOld(extModel, I_TestModel_Ext.class);
		assertThat("Old values flag shall be set", InterfaceWrapperHelper.isOldValues(extModelOld), is(true));

		final I_TestModel extModelOld_WrappedAgain = InterfaceWrapperHelper.create(extModelOld, I_TestModel.class);
		assertThat("Old values flag shall be preserved", InterfaceWrapperHelper.isOldValues(extModelOld_WrappedAgain), is(true));

		// Make sure that when calling InterfaceWrapperHelper.create() with oldValues=false
		// that option is interpreted as "PRESERVE" and not as "DON'T use old values"
		@SuppressWarnings("deprecation")
		final I_TestModel extModelOld_WrappedAgain2 = InterfaceWrapperHelper.create(extModelOld, I_TestModel.class, false);
		assertThat("Old values flag shall be preserved", InterfaceWrapperHelper.isOldValues(extModelOld_WrappedAgain2), is(true));
	}

	/**
	 * Make sure {@link InterfaceWrapperHelper#getModelTableNameOrNull(Object)} is working with {@link ITableRecordReference}s.
	 *
	 * This behavior is assumed by some BLs.
	 */
	@Test
	public void test_getModelTableNameOrNull_for_TableRecordReference()
	{
		final I_Test testModel = InterfaceWrapperHelper.newInstance(I_Test.class, contextProvider);
		InterfaceWrapperHelper.save(testModel);

		final ITableRecordReference testModelRef = TableRecordReference.of(testModel);
		assertThat(InterfaceWrapperHelper.getModelTableNameOrNull(testModelRef), is(I_Test.Table_Name));
	}

	/**
	 * Make sure {@link InterfaceWrapperHelper#getId(Object)} is working with {@link ITableRecordReference}s.
	 *
	 * This behavior is assumed by some BLs.
	 */
	@Test
	public void test_getId_for_TableRecordReference()
	{
		final I_Test testModel = InterfaceWrapperHelper.newInstance(I_Test.class, contextProvider);
		InterfaceWrapperHelper.save(testModel);

		final ITableRecordReference testModelRef = TableRecordReference.of(testModel);
		assertThat(InterfaceWrapperHelper.getId(testModelRef), is(testModelRef.getRecord_ID()));
	}

	@Test
	public void test_getTableName_withModelClassTableName_withExpectedTableName()
	{
		String tableName = InterfaceWrapperHelper.getTableName(I_TestModel.class, I_TestModel.Table_Name);
		Assert.assertEquals(I_TestModel.Table_Name, tableName);
	}

	@Test
	public void test_getTableName_withModelClassTableName_withNullExpectedTableName()
	{
		String tableName = InterfaceWrapperHelper.getTableName(I_TestModel.class, null);
		Assert.assertEquals(I_TestModel.Table_Name, tableName);
	}

	@Test
	public void test_getTableName_withModelClassTableName_withWrongExpectedTableName()
	{
		assertThatThrownBy(() -> InterfaceWrapperHelper.getTableName(I_TestModel.class, "WrongTableName"))
				.isInstanceOf(InterfaceWrapperHelper.MissingTableNameException.class);
	}

	@Test
	public void test_getTableName_withNonModelClass_withExpectedTableName()
	{
		String tableName = InterfaceWrapperHelper.getTableName(ITaxAware.class, "expectedTableName");
		Assert.assertEquals("expectedTableName", tableName);
	}

	@Test
	public void test_getTableName_withNonModelClass_withNullExpectedTableName()
	{
		assertThatThrownBy(() -> InterfaceWrapperHelper.getTableName(ITaxAware.class, null))
				.isInstanceOf(InterfaceWrapperHelper.MissingTableNameException.class);
	}
}
