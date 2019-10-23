package de.metas.adempiere.tools;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper tool which introspects a given "X_" class and creates SQL code to fix AD_Ref_List.ValueNames.
 * 
 * NOTE: this tool DOES NOT need database access.
 * 
 * e.g. for "org.compiere.model.X_C_AcctSchema" it will generate
 * 
 * <pre>
 * ....
 * -- COMMITMENTTYPE_
 * UPDATE AD_Ref_List SET ValueName='POCommitmentOnly' WHERE AD_Reference_ID=359 AND Value='C';
 * UPDATE AD_Ref_List SET ValueName='POCommitmentReservation' WHERE AD_Reference_ID=359 AND Value='B';
 * UPDATE AD_Ref_List SET ValueName='None' WHERE AD_Reference_ID=359 AND Value='N';
 * UPDATE AD_Ref_List SET ValueName='POSOCommitmentReservation' WHERE AD_Reference_ID=359 AND Value='A';
 * UPDATE AD_Ref_List SET ValueName='SOCommitmentOnly' WHERE AD_Reference_ID=359 AND Value='S';
 * UPDATE AD_Ref_List SET ValueName='POSOCommitment' WHERE AD_Reference_ID=359 AND Value='O';
 * ....
 * </pre>
 * 
 * @author tsa
 *
 */
public class AD_Ref_List_ValueName_UpdateFromClass
{
	private static final String SUFFIX_AD_Reference_ID = "AD_Reference_ID";

	public static void main(final String[] args)
	{
		final String classname = args[0];
		// final String classname = "org.compiere.model.X_C_AcctSchema";

		final AD_Ref_List_ValueName_UpdateFromClass instance = new AD_Ref_List_ValueName_UpdateFromClass();
		instance.dumpUpdateSqls(classname);
	}

	private void dumpUpdateSqls(final String classname)
	{
		System.out.println("-- Generated with " + getClass() + " by " + System.getProperty("user.name"));
		final List<String> updateSqls = createUpdateSqls(classname);

		for (final String updateSql : updateSqls)
		{
			System.out.println(updateSql);
		}
	}

	private List<String> createUpdateSqls(String classname)
	{
		final List<String> updateSqls = new ArrayList<>();
		final Class<?> clazz = loadClass(classname);
		final Field[] classFields = clazz.getFields();
		for (final Field field : classFields)
		{
			final String fieldName = field.getName();
			if (!fieldName.endsWith(SUFFIX_AD_Reference_ID))
			{
				continue;
			}

			final int adReferenceId = getFieldValueAsInt(field);
			final String prefix = fieldName.substring(0, fieldName.length() - SUFFIX_AD_Reference_ID.length());
			final Map<String, String> name2value = extractNameAndValueForPrefix(prefix, classFields);

			updateSqls.add("\n\n-- " + prefix);
			updateSqls.addAll(createUpdateSqls(adReferenceId, name2value));
		}

		return updateSqls;
	}

	private List<String> createUpdateSqls(int adReferenceId, Map<String, String> name2valuesMap)
	{
		final List<String> updateSqls = new ArrayList<>(name2valuesMap.size());
		for (final Map.Entry<String, String> name2value : name2valuesMap.entrySet())
		{
			final String name = name2value.getKey();
			final String value = name2value.getValue();

			final String sql = "UPDATE AD_Ref_List SET ValueName='" + name + "'"
					+ " WHERE AD_Reference_ID=" + adReferenceId
					+ " AND Value='" + value + "'"
					+ ";";
			updateSqls.add(sql);

		}
		return updateSqls;
	}

	private Map<String, String> extractNameAndValueForPrefix(String prefix, Field[] classFields)
	{
		final Map<String, String> name2value = new LinkedHashMap<>();
		for (final Field field : classFields)
		{
			final String fieldName = field.getName();
			if (!fieldName.startsWith(prefix))
			{
				continue;
			}
			if (fieldName.endsWith(SUFFIX_AD_Reference_ID))
			{
				continue;
			}

			final String name = fieldName.substring(prefix.length());
			final String value = getFieldValueAsString(field);
			name2value.put(name, value);
		}

		return name2value;
	}

	private final String getFieldValueAsString(final Field field)
	{
		try
		{
			return (String)field.get(null);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot get value of " + field);
		}
	}

	private final int getFieldValueAsInt(final Field field)
	{
		try
		{
			return (int)field.get(null);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Cannot get value of " + field);
		}
	}

	private Class<?> loadClass(String classname)
	{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			return classLoader.loadClass(classname);
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException("Cannot load class: " + classname, e);
		}
	}

}
