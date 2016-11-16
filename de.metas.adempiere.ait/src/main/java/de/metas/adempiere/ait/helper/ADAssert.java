package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.adempiere.ad.service.IADMessageDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.Callout;
import org.compiere.model.I_AD_Color;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_AD_Scheduler;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MDocType;
import org.compiere.model.MField;
import org.compiere.model.MOrg;
import org.compiere.model.MReference;
import org.compiere.model.MSysConfig;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidator;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.model.X_AD_ModelValidator;
import org.compiere.util.CPreparedStatement;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import de.metas.document.IDocumentPA;
import de.metas.process.IADProcessDAO;
import de.metas.process.SvrProcess;

/**
 * Contains assertions relating to the ADempeire application dictionary
 * 
 * @author ts
 * 
 */
public class ADAssert
{

	public static final String SQL_SELECT_MODELVALIDATOR = "SELECT * FROM "
			+ I_AD_ModelValidator.Table_Name + " WHERE "
			+ I_AD_ModelValidator.COLUMNNAME_ModelValidationClass + "=?";

	public static final String SQL_SELECT_PROCESS = "SELECT * FROM "
			+ I_AD_Process.Table_Name + " WHERE "
			+ I_AD_Process.COLUMNNAME_Classname + "=?";

	private ADAssert()
	{
	}

	public static void assertColumnMandatory(final int adTableId,
			final String columnName, final String trxName)
	{

		assertColumnExists(adTableId, columnName, trxName);

		POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), adTableId, trxName);

		assertTrue(getFailMsgPrefix(columnName, poInfo) + " be mandatory",
				poInfo.isColumnMandatory(poInfo.getColumnIndex(columnName)));
	}

	/**
	 * Asserts that the standard columns are present in a given table and are as expected. Standard columns are
	 * considered to be
	 * 
	 * <li>AD_Client_ID</li> <li>AD_Org_ID</li> <li>Created</li> <li>CreatedBy</li> <li>IsActive</li> <li>Updated</li>
	 * <li>UpdatedBy</li>
	 * 
	 * @param adTableId
	 * @param trxName
	 */
	public static void assertStandardCols(final int adTableId,
			final String trxName)
	{

		assertColumnMandatory(adTableId, Constants.COL_CLIENT_ID, trxName);
		// assertColumnDefaultLogic(adTableId, Constants.COL_CLIENT_ID,
		// "@#AD_Client_ID@", trxName);
		assertColumnReferenceType(adTableId, Constants.COL_CLIENT_ID,
				DisplayType.TableDir, trxName);

		assertColumnMandatory(adTableId, Constants.COL_ORG_ID, trxName);
		assertColumnDefaultLogic(adTableId, Constants.COL_ORG_ID,
				"@AD_Org_ID@", trxName);
		assertColumnReferenceType(adTableId, Constants.COL_ORG_ID,
				DisplayType.TableDir, trxName);

		assertColumnMandatory(adTableId, Constants.COL_CREATED, trxName);
		assertColumnDefaultLogic(adTableId, Constants.COL_CREATED, null,
				trxName);
		assertColumnReferenceType(adTableId, Constants.COL_CREATED,
				DisplayType.DateTime, trxName);

		assertColumnMandatory(adTableId, Constants.COL_CREATED_BY, trxName);
		assertColumnDefaultLogic(adTableId, Constants.COL_CREATED_BY, null,
				trxName);
		assertColumnReferenceType(adTableId, Constants.COL_CREATED_BY,
				DisplayType.Table, trxName);

		assertColumnMandatory(adTableId, Constants.COL_ISACTIVE, trxName);
		assertColumnDefaultLogic(adTableId, Constants.COL_ISACTIVE, "Y",
				trxName);
		assertColumnReferenceType(adTableId, Constants.COL_ISACTIVE,
				DisplayType.YesNo, trxName);

		assertColumnMandatory(adTableId, Constants.COL_UPDATED, trxName);
		assertColumnDefaultLogic(adTableId, Constants.COL_UPDATED, null,
				trxName);
		assertColumnReferenceType(adTableId, Constants.COL_UPDATED,
				DisplayType.DateTime, trxName);

		assertColumnMandatory(adTableId, Constants.COL_UPDATED_BY, trxName);
		assertColumnDefaultLogic(adTableId, Constants.COL_UPDATED_BY, null,
				trxName);
		assertColumnReferenceType(adTableId, Constants.COL_UPDATED_BY,
				DisplayType.Table, trxName);
	}

	public static void assertKeyColumn(final int adTableId,
			final String columnName, final String trxName)
	{

		POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), adTableId, trxName);

		assertTrue(getFailMsgPrefix(columnName, poInfo) + " be mandatory",
				poInfo.isKey(poInfo.getColumnIndex(columnName)));
	}

	public static void assertColumnNotMandatory(final int adTableId,
			final String columnName, final String trxName)
	{

		assertColumnExists(adTableId, columnName, trxName);

		POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), adTableId, trxName);

		assertFalse(
				getFailMsgPrefix(columnName, poInfo) + " be not mandatory",
				poInfo.isColumnMandatory(poInfo.getColumnIndex(columnName)));
	}

	public static void assertColumnMinValue(final int adTableId,
			final String columnName, final String expectedValue,
			final String trxName)
	{

		assertColumnExists(adTableId, columnName, trxName);

		MTable table = MTable.get(Env.getCtx(), adTableId);
		MColumn column = table.getColumn(columnName);

		assertEquals(column.getValueMin(), expectedValue, getFailMsgPrefix(
				columnName, table.get_TableName())
				+ " have '" + expectedValue + "' as minimum");
	}

	public static void assertColumnMaxValue(final int adTableId,
			final String columnName, final String expectedValue,
			final String trxName)
	{

		assertColumnExists(adTableId, columnName, trxName);

		MTable table = MTable.get(Env.getCtx(), adTableId);
		MColumn column = table.getColumn(columnName);

		assertEquals(column.getValueMax(), expectedValue, getFailMsgPrefix(
				columnName, table.get_TableName())
				+ " have '" + expectedValue + "' as maximum");
	}

	public static void assertColumnUpdatable(
			final int adTableId,
			final String columnName, final String trxName)
	{

		assertColumnExists(adTableId, columnName, trxName);

		POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), adTableId, trxName);

		assertTrue(getFailMsgPrefix(columnName, poInfo) + "be updatable",
				poInfo.isColumnUpdateable(getColIdx(adTableId, columnName, trxName)));

	}

	public static void assertColumnNotUpdatable(
			final int adTableId,
			final String columnName, final String trxName)
	{
		assertColumnExists(adTableId, columnName, trxName);

		final POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), adTableId, trxName);

		assertFalse(getFailMsgPrefix(columnName, poInfo) + "be not updatable",
				poInfo.isColumnUpdateable(getColIdx(adTableId, columnName, trxName)));
	}

	public static void assertColumnDefaultLogic(final int adTableId,
			final String columnName, final String defaultLogic,
			final String trxName)
	{
		assertColumnExists(adTableId, columnName, trxName);

		final POInfo poInfo = POInfo.getPOInfo(Env.getCtx(), adTableId, trxName);

		assertEquals(getFailMsgPrefix(columnName, poInfo) + "have default logic '" + defaultLogic + "'",
				poInfo.getDefaultLogic(getColIdx(adTableId, columnName, trxName)), defaultLogic);
	}

	public static void assertColumnReadOnlyLogic(final int adTableId,
			final String columnName, final String expectedReadOnlyLogic,
			final String trxName)
	{

		assertColumnExists(adTableId, columnName, trxName);

		MTable table = MTable.get(Env.getCtx(), adTableId);
		MColumn column = table.getColumn(columnName);

		assertEquals(
				getFailMsgPrefix(columnName, table.get_TableName()) + "have read only logic '" + expectedReadOnlyLogic + "'",
				column.getReadOnlyLogic(), expectedReadOnlyLogic);
	}

	/**
	 * Check if the callout class and method actually exist.
	 * 
	 * @param calloutString
	 */
	public static void assertCalloutExists(final String calloutString)
	{

		final int methodStart = calloutString.lastIndexOf('.');
		assertTrue("Illegal callout string '" + calloutString + "'", methodStart != -1);

		final String calloutClassName = calloutString.substring(0, methodStart);

		Callout calloutInstance = null;

		try
		{
			Class<?> cClass = Class.forName(calloutClassName);
			calloutInstance = (Callout)cClass.newInstance();
		}
		catch (ClassNotFoundException e)
		{
			fail("Callout class '" + calloutClassName + "' not found ");
		}
		catch (InstantiationException e)
		{
			fail("InstantiationException for callout class '" + calloutClassName + "'");
		}
		catch (IllegalAccessException e)
		{
			fail("IllegalAccessException for callout class '" + calloutClassName + "'");
		}

		final String methodName = calloutString.substring(methodStart + 1);

		boolean methodFound = false;
		Method[] allMethods = calloutInstance.getClass().getMethods();
		for (int i = 0; i < allMethods.length; i++)
		{
			if (methodName.equals(allMethods[i].getName()))
			{
				methodFound = true;
				break;
			}

		}
		if (!methodFound)
		{
			fail("Missing method '" + methodName + "' for callout class '"
					+ calloutClassName + "'");
		}
	}

	public static void assertColumnExists(final int adTableId,
			final String columnName, final String trxName)
	{
		int colIdx = getColIdx(adTableId, columnName, trxName);

		if (colIdx == -1)
		{
			fail("Missing column '" + columnName + "' in Table '"
					+ getTableName(adTableId, columnName, trxName) + "'.");
		}
	}

	public static void assertColumnLength(final int adTableId,
			final String columnName, final int length, final String trxName)
	{
		assertColumnExists(adTableId, columnName, trxName);

		final POInfo poInfo = POInfo
				.getPOInfo(Env.getCtx(), adTableId, trxName);
		final int fieldLength = poInfo.getFieldLength(getColIdx(adTableId,
				columnName, trxName));
		assertEquals(
				getFailMsgPrefix(columnName, poInfo) + " have a field length of " + length,
				fieldLength, length);

		// TODO instantiate a PO and use the column setter to make sure, the
		// value is
		// not truncated.
	}

	public static void assertColumnReferenceType(final int adTableId,
			final String columnName, final int referenceId, final String trxName)
	{
		assertColumnExists(adTableId, columnName, trxName);

		MTable table = MTable.get(Env.getCtx(), adTableId);
		MColumn column = table.getColumn(columnName);

		assertEquals(getFailMsgPrefix(columnName, table.get_TableName()) + "have reference type " + referenceId,
				column.getAD_Reference_ID(), referenceId);
	}

	public static void assertTableHasId(final String tableName)
	{

		if (MTable.getTable_ID(tableName) == 0)
		{
			fail("Table '" + tableName + "' is expected to have an AD_Table_ID");
		}
	}

	public static void assertMsgExists(final String searchValue)
	{
		if (searchValue == null)
		{
			throw new NullPointerException(searchValue);
		}

		final I_AD_Message msg = Services.get(IADMessageDAO.class).retrieveByValue(Env.getCtx(), searchValue);
		if (msg == null)
		{
			fail("Missing system message for search value '" + searchValue + "'");
		}
	}

	/**
	 * Checks if the given model validator class is configured in the application dictionary.F
	 * 
	 * @param validatorClass
	 * @param trxName
	 */
	public static void assertModelValidatorExists(
			final Class<? extends ModelValidator> validatorClass,
			final String trxName)
	{
		CPreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(SQL_SELECT_MODELVALIDATOR, trxName);
			pstmt.setString(1, validatorClass.getName());
			rs = pstmt.executeQuery();
			if (rs.next())
			{

				X_AD_ModelValidator modelValidator = new X_AD_ModelValidator(
						Env.getCtx(), rs, trxName);

				assertTrue("Model validator with class '" + validatorClass + "' exists, but it is not active",
						modelValidator.isActive());
				return;
			}

			fail("Found no model validator with class '" + validatorClass.getName() + "'");

		}
		catch (SQLException e)
		{
			fail("Unable to query the database for the model validator class '" + validatorClass.getName() + "'");
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	public static void assertProcessExists(
			final Properties ctx,
			final String value,
			final Class<? extends SvrProcess> processClass,
			final String trxName)
	{
		final I_AD_Process process = Services.get(IADProcessDAO.class).retriveProcessByValue(ctx, value);
		if (process == null)
		{
			fail("Found no process with class '" + processClass.getName() + "'");
		}

		assertFalse(process + " has empty Classname", Check.isEmpty(process.getClassname()));
		assertThat(process + " has wrong Classname", process.getClassname(), equalTo(processClass.getName()));
	}

	/**
	 * Asserts that a process with the given class exists in the AD and is active.
	 * 
	 * @param processClass
	 * @param trxName
	 */
	public static void assertProcessExists(
			final Class<? extends SvrProcess> processClass, final String trxName)
	{
		if (Services.get(IADProcessDAO.class).retriveProcessIdByClassIfUnique(Env.getCtx(), processClass) <= 0)
		{
			fail("Found no process with class '" + processClass.getName() + "'");
		}
	}

	public static void assertFieldIsReadOnly(final int adFieldId,
			final String trxName)
	{

		final MField field = new MField(Env.getCtx(), adFieldId, trxName);

		assertTrue(
				"Field '" + field.getName() + "' (id '" + adFieldId + "') is expected to be readonly",
				field.isReadOnly());
	}

	public static void assertFieldHasName(final int adFieldId,
			final String expectedName, final String trxName)
	{

		final MField field = new MField(Env.getCtx(), adFieldId, trxName);

		assertEquals(field.getName(), expectedName, "Field '" + field.getName()
				+ "' (id '" + adFieldId + "') is expected to have name '"
				+ expectedName + "'");
	}

	/**
	 * Checks if the given table has the columns that are implicitly expected for a table to be displayed in a tree tab.
	 * 
	 * @param adTableId
	 * @param trxName
	 */
	public static void assertTableHasTreeColumns(final int adTableId,
			final String trxName)
	{

		assertColumnExists(adTableId, "Name", trxName);
		assertColumnExists(adTableId, "Description", trxName);
		assertColumnExists(adTableId, "IsSummary", trxName);
	}

	public static final void assertRefListValueExists(final int AD_Reference_ID, final String value)
	{
		final String wc = I_AD_Ref_List.COLUMNNAME_AD_Reference_ID + "=? AND " + I_AD_Ref_List.COLUMNNAME_Value + "=?";
		final int count =
				new Query(Env.getCtx(), I_AD_Ref_List.Table_Name, wc, null)
						.setParameters(AD_Reference_ID, value)
						.setApplyAccessFilter(true)
						.count();

		final MReference reference = new MReference(Env.getCtx(), AD_Reference_ID, null);
		assertThat(
				"Expected AD_Ref_List record with AD_Reference " + reference.getName() + " (AD_Reference_ID=" + reference.get_ID() + ") and value '" + value + "' to exist",
				count, equalTo(1));
	}

	private static int getColIdx(final int adTableId, final String columnName,
			final String trxName)
	{

		final POInfo poInfo = POInfo
				.getPOInfo(Env.getCtx(), adTableId, trxName);
		return poInfo.getColumnIndex(columnName);
	}

	private static String getTableName(final int adTableId,
			final String columnName, final String trxName)
	{

		final POInfo poInfo = POInfo
				.getPOInfo(Env.getCtx(), adTableId, trxName);
		return poInfo.getTableName();
	}

	private static String getFailMsgPrefix(final String columnName,
			final String tableName)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("Column '");
		sb.append(columnName);
		sb.append("' of table '");
		sb.append(tableName);
		sb.append("\' is expected to ");

		return sb.toString();
	}

	private static String getFailMsgPrefix(final String columnName, final POInfo poInfo)
	{

		return getFailMsgPrefix(columnName, poInfo.getTableName());
	}

	public static void assertSysConfig(final String name, final String expectedValue)
	{
		final Properties ctx = Env.getCtx();

		assertSysConfig(Env.getAD_Client_ID(ctx), Env.getAD_Org_ID(ctx),
				name, expectedValue);
	}

	public static void assertSysConfig(
			final int clientId, final int orgId,
			final String name, final String expectedValue)
	{
		final Properties ctx = Env.getCtx();

		final String value = MSysConfig.getValue(name, "", clientId, orgId);
		if (orgId > 0)
		{
			// make sure that clientId and orgId are consistent
			final MOrg org = MOrg.get(ctx, orgId);
			assertEquals("AD_Client_ID of given Org '" + org.getName() + "' is different from given client ID", clientId, org.getAD_Client_ID());
			
			// now make the actual check
			assertEquals("Expected " + name + " to be set correctly for Org '" + org.getName() + "'", expectedValue, value);
		}
		else
		{
			// basically the same check, but a different error message if ordIg=0 
			assertEquals("Expected " + name + " to be set correctly for Client '" + MClient.get(ctx, clientId).getName() + "'", expectedValue, value);
		}
	}

	public static void assertProcessSchedulerExists(
			final Properties ctx,
			final String processValue,
			final String trxName)
	{
		final I_AD_Process process = Services.get(IADProcessDAO.class).retriveProcessByValue(ctx, processValue);

		if (process == null)
		{
			fail("Found no process with value '" + processValue + "'");
		}

		final I_AD_Scheduler scheduler = new Query(ctx, I_AD_Scheduler.Table_Name, I_AD_Scheduler.COLUMNNAME_AD_Process_ID + "=?", trxName)
				.setParameters(process.getAD_Process_ID())
				.setOnlyActiveRecords(true)
				.setApplyAccessFilter(true)
				.firstOnly(I_AD_Scheduler.class);

		if (scheduler == null)
		{
			fail("Found no scheduler with process " + process);
		}
	}

	public static final void assertDocTypeAvail(
			final int clientId,
			final int orgId,
			final String docBaseType,
			final String docSubType,
			final boolean isAvailable)
	{

		final I_C_DocType type = Services.get(IDocumentPA.class).retrieve(Env.getCtx(), orgId, docBaseType, docSubType, false, null);
		final int result = (type == null ? -1 : type.getC_DocType_ID());

		if (isAvailable)
		{
			assertThat("Expected a C_DocType with docSubType=" + docSubType + "' to exist for AD_Org_ID=" + orgId,
					result, greaterThan(-1));
		}
		else
		{
			assertThat("Expected *no* C_DocType with docSubType=" + docSubType + "' to exist for AD_Org_ID=" + orgId,
					result, equalTo(-1));
		}
	}

	/**
	 * Asserts that all C_DocTypes with the given docBaseType (as returned by
	 * {@link MDocType#getOfDocBaseType(Properties, String)}) have the given value in their IsCreateCounter column.
	 * 
	 * @param ctx
	 * @param docBaseType
	 * @param expCreateCounter
	 */
	public static void assertDocBaseTypeIsCreateCounter(final Properties ctx, final String docBaseType, final boolean expCreateCounter)
	{
		for (final MDocType docType : MDocType.getOfDocBaseType(ctx, docBaseType))
		{
			assertEquals("Expecting " + docType + " to have correct 'IsCreateCounter' setting", expCreateCounter, docType.isCreateCounter());
		}
	}

	/**
	 * Asserts that a color with the given name is active and accessible using the given ctx.
	 */
	public static void assertColorExists(final Properties ctx, String name)
	{
		final int count =
				new Query(ctx, I_AD_Color.Table_Name, I_AD_Color.COLUMNNAME_Name + "=?", null)
						.setParameters(name)
						.setOnlyActiveRecords(true)
						.setApplyAccessFilter(true)
						.count();

		assertEquals("Expecting a colur with name '"+name+"' to exist", count, 1);
	}
}
