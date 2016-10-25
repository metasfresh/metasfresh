package org.adempiere.ad.persistence;

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


import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.logging.LogManager;

/**
 * Class responsible for loading model classes by given Table Name.
 *
 * @author tsa
 *
 */
public class TableModelClassLoader
{
	public static final TableModelClassLoader instance = new TableModelClassLoader();

	private static final transient Class<?> NO_CLASS = Object.class;

	private final Logger log = LogManager.getLogger(getClass());

	/**
	 * EntityTypes cache
	 */
	private IEntityTypesCache entityTypesCache = EntityTypesCache.instance;

	/**
	 * Cache: TableName to Model Class
	 */
	private final LoadingCache<String, Class<?>> tableName2class;
	/**
	 * Cache: Model Class to ID Constructor
	 */
	private final LoadingCache<Class<?>, Constructor<?>> class2idConstructor;
	/**
	 * Cache: Model Class to ResultSet Constructor
	 */
	private final LoadingCache<Class<?>, Constructor<?>> class2resultSetConstructor;

	/** Packages for Model Classes */
	private static final String[] s_packages = new String[] {

			"org.compiere.model", "org.compiere.wf",
			"org.compiere.report", // teo_sarca BF[3133032]
			"org.compiere.print", "org.compiere.impexp",
			"compiere.model",			// globalqss allow compatibility with other plugins
			"adempiere.model",			// Extensions
			"org.adempiere.model"
	};

	/** Special Classes */
	private static final String[] s_special = new String[] {
			"AD_Element", "org.compiere.model.M_Element",
			"AD_Registration", "org.compiere.model.M_Registration",
			"AD_Tree", "org.compiere.model.MTree_Base",
			"R_Category", "org.compiere.model.MRequestCategory",
			"GL_Category", "org.compiere.model.MGLCategory",
			"K_Category", "org.compiere.model.MKCategory",
			"C_ValidCombination", "org.compiere.model.MAccount",
			"C_Phase", "org.compiere.model.MProjectTypePhase",
			"C_Task", "org.compiere.model.MProjectTypeTask"
			// AD_Attribute_Value, AD_TreeNode
	};

	@VisibleForTesting
	TableModelClassLoader()
	{
		super();

		this.tableName2class = CacheBuilder.newBuilder()
				.build(new CacheLoader<String, Class<?>>()
				{

					@Override
					public Class<?> load(final String tableName) throws Exception
					{
						return findModelClassByTableName(tableName);
					}
				});

		this.class2idConstructor = CacheBuilder.newBuilder()
				.build(new CacheLoader<Class<?>, Constructor<?>>()
				{

					@Override
					public Constructor<?> load(Class<?> modelClass) throws Exception
					{
						return findIDConstructor(modelClass);
					}
				});

		this.class2resultSetConstructor = CacheBuilder.newBuilder()
				.build(new CacheLoader<Class<?>, Constructor<?>>()
				{

					@Override
					public Constructor<?> load(Class<?> modelClass) throws Exception
					{
						return findResultSetConstructor(modelClass);
					}
				});
	}

	@VisibleForTesting
	void setEntityTypesCache(final IEntityTypesCache entityTypesCache)
	{
		Check.assumeNotNull(entityTypesCache, "entityTypesCache not null");
		this.entityTypesCache = entityTypesCache;
	}

	/**
	 * Get Persistency Class for Table
	 *
	 * @param tableName table name
	 * @return class or null
	 */
	public Class<?> getClass(final String tableName)
	{
		final Class<?> modelClass;
		try
		{
			modelClass = tableName2class.get(tableName);
		}
		catch (ExecutionException e)
		{
			throw new AdempiereException("Error while getting model class for table: " + tableName);
		}

		if (modelClass == null || modelClass == NO_CLASS)
		{
			return null;
		}

		return modelClass;
	}	// getClass


	@VisibleForTesting
	/* package */ final Class<?> findModelClassByTableName(final String tableName)
	{
		if (Check.isEmpty(tableName, true) || tableName.endsWith("_Trl"))
		{
			return NO_CLASS;
		}

		// given tableName may be in lower-case..we get the table and use its tableName
		final I_AD_Table table = MTable.get(Env.getCtx(), tableName);
		final String tableNameToUse = table.getTableName();

		// Import Tables (Name conflict)
		if (tableNameToUse.startsWith("I_"))
		{
			final Class<?> clazz = loadModelClassForClassname("org.compiere.model.X_" + tableNameToUse);
			if (clazz != null)
			{
				return clazz;
			}

			log.warn("No class for import table: {}", tableNameToUse);
			return NO_CLASS;
		}

		// Special Naming
		for (int i = 0; i < s_special.length; i++)
		{
			if (s_special[i++].equals(tableNameToUse))
			{
				final Class<?> clazz = loadModelClassForClassname(s_special[i]);
				if (clazz != null)
				{
					return clazz;
				}
				break;
			}
		}

		//
		// Use ModelPackage if exists
		// Initially introduced by [ 1784588 ], vpj-cd
		final String modelpackage = getModelPackageForTableName(tableNameToUse);
		if (modelpackage != null)
		{
			Class<?> clazz = loadModelClassForClassname(modelpackage + ".M" + Util.replace(tableNameToUse, "_", ""));
			if (clazz != null)
			{
				return clazz;
			}
			clazz = loadModelClassForClassname(modelpackage + ".X_" + tableNameToUse);
			if (clazz != null)
			{
				return clazz;
			}
			log.warn("No class for table with it entity: {}", tableNameToUse);
		}

		// Strip table name prefix (e.g. AD_) Customizations are 3/4
		String className = tableNameToUse;
		int index = className.indexOf('_');
		if (index > 0)
		{
			if (index < 3)		// AD_, A_
				className = className.substring(index + 1);
			/*
			 * DELETEME: this part is useless - teo_sarca, [ 1648850 ] else { String prefix = className.substring(0,index); if (prefix.equals("Fact")) // keep custom prefix className =
			 * className.substring(index+1); }
			 */
		}
		// Remove underlines
		className = Util.replace(className, "_", "");

		// Search packages
		for (int i = 0; i < s_packages.length; i++)
		{
			StringBuffer name = new StringBuffer(s_packages[i]).append(".M").append(className);
			Class<?> clazz = loadModelClassForClassname(name.toString());
			if (clazz != null)
			{
				return clazz;
			}
		}

		// Adempiere Extension
		Class<?> clazz = loadModelClassForClassname("adempiere.model.X_" + tableNameToUse);
		if (clazz != null)
		{
			return clazz;
		}

		// hengsin - allow compatibility with compiere plugins
		// Compiere Extension
		clazz = loadModelClassForClassname("compiere.model.X_" + tableNameToUse);
		if (clazz != null)
		{
			return clazz;
		}

		// Default
		clazz = loadModelClassForClassname("org.compiere.model.X_" + tableNameToUse);
		if (clazz != null)
		{
			return clazz;
		}

		return NO_CLASS;
	}

	private final String getModelPackageForTableName(final String tableName)
	{
		final String entityType = getEntityTypeForTableName(tableName);
		final String modelpackage = entityTypesCache.getModelPackage(entityType);
		return modelpackage;
	}

	@VisibleForTesting
	String getEntityTypeForTableName(final String tableName)
	{
		final I_AD_Table table = MTable.get(Env.getCtx(), tableName);
		if (table == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Table_ID@: " + tableName);
		}
		final String entityType = table.getEntityType();
		return entityType;
	}

	/**
	 * Get PO class.
	 *
	 * @param className fully qualified class name
	 * @return class or <code>null</code> if model class was not found or it's not valid
	 */
	private Class<?> loadModelClassForClassname(final String className)
	{
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try
		{
			final Class<?> clazz = classLoader.loadClass(className);

			// Make sure that it is a PO class
			if (!PO.class.isAssignableFrom(clazz))
			{
				log.debug("Skip {} because it does not have PO class as supertype", clazz);
				return null;
			}

			return clazz;
		}
		catch (ClassNotFoundException e)
		{
			if (log.isDebugEnabled())
			{
				log.debug("No class found for " + className + " (classloader: " + classLoader + ")", e);
			}
		}

		return null;
	}

	private final Constructor<?> findIDConstructor(final Class<?> modelClass) throws NoSuchMethodException, SecurityException
	{
		return modelClass.getDeclaredConstructor(new Class[] { Properties.class, int.class, String.class });
	}

	public Constructor<?> getIDConstructor(final Class<?> modelClass)
	{
		try
		{
			return class2idConstructor.get(modelClass);
		}
		catch (ExecutionException e)
		{
			throw new AdempiereException("Cannot get ID constructor for " + modelClass, e);
		}
	}

	private final Constructor<?> findResultSetConstructor(final Class<?> modelClass) throws NoSuchMethodException, SecurityException
	{
		return modelClass.getDeclaredConstructor(new Class[] { Properties.class, ResultSet.class, String.class });
	}

	public Constructor<?> getResultSetConstructor(final Class<?> modelClass)
	{
		try
		{
			return class2resultSetConstructor.get(modelClass);
		}
		catch (ExecutionException e)
		{
			throw new AdempiereException("Cannot get ResultSet constructor for " + modelClass, e);
		}
	}

}
