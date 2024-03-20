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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.annotations.VisibleForTesting;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Phase;
import org.compiere.model.I_C_Task;
import org.compiere.model.MAccount;
import org.compiere.model.MKCategory;
import org.compiere.model.MRequestCategory;
import org.compiere.model.MTree_Base;
import org.compiere.model.M_Element;
import org.compiere.model.PO;
import org.slf4j.Logger;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

/**
 * Class responsible for loading model classes by given Table Name.
 *
 * @author tsa
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

	private final ConcurrentMap<String, String> specialTableName2className = new ConcurrentHashMap<>();

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

	/**
	 * Packages for Model Classes
	 */
	private static final String[] s_packages = new String[] {
			"org.compiere.model", "org.compiere.wf",
			"org.compiere.report", // teo_sarca BF[3133032]
			"org.compiere.print", "org.compiere.impexp",
			"compiere.model",            // globalqss allow compatibility with other plugins
			"adempiere.model",            // Extensions
			"org.adempiere.model"
	};
	private static final String[] import_packages = new String[] {
			"org.compiere.model.",
			"de.metas.contracts.model"
	};

	@VisibleForTesting
	TableModelClassLoader()
	{
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

		specialTableName2className.put("AD_Element", M_Element.class.getName());
		specialTableName2className.put("AD_Tree", MTree_Base.class.getName());
		specialTableName2className.put("R_Category", MRequestCategory.class.getName());
		specialTableName2className.put("K_Category", MKCategory.class.getName());
		specialTableName2className.put("C_ValidCombination", MAccount.class.getName());
		specialTableName2className.put("C_Phase", I_C_Phase.class.getName());
		specialTableName2className.put("C_Task", I_C_Task.class.getName());
	}

	public void registerSpecialClassName(@NonNull final String tableName, @NonNull final String className)
	{
		specialTableName2className.put(tableName, className);
		tableName2class.invalidate(tableName);
	}

	@VisibleForTesting
	void setEntityTypesCache(@NonNull final IEntityTypesCache entityTypesCache)
	{
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
	}    // getClass

	private final Class<?> findModelClassByTableName(final String tableName)
	{
		if (Check.isEmpty(tableName, true) || tableName.endsWith("_Trl"))
		{
			return NO_CLASS;
		}

		// Import Tables (Name conflict)
		if (tableName.startsWith("I_"))
		{
			for (final String pkg : import_packages)
			{
				final Class<?> clazz = loadModelClassForClassname(pkg + ".X_" + tableName);
				if (clazz != null)
				{
					return clazz;
				}
			}

			log.warn("No class for import table: {}", tableName);
			return NO_CLASS;
		}

		// Special Naming
		{
			final String specialClassName = specialTableName2className.get(tableName);
			if (specialClassName != null)
			{
				final Class<?> clazz = loadModelClassForClassname(specialClassName);
				if (clazz != null)
				{
					return clazz;
				}
			}
		}

		//
		// Use ModelPackage if exists
		// Initially introduced by [ 1784588 ], vpj-cd
		final String modelpackage = getModelPackageForTableName(tableName);
		if (modelpackage != null)
		{
			Class<?> clazz = loadModelClassForClassname(modelpackage + ".M" + StringUtils.replace(tableName, "_", ""));
			if (clazz != null)
			{
				return clazz;
			}
			clazz = loadModelClassForClassname(modelpackage + ".X_" + tableName);
			if (clazz != null)
			{
				return clazz;
			}
			log.warn("No class for table with it entity: {}", tableName);
		}

		// Strip table name prefix (e.g. AD_) Customizations are 3/4
		String className = tableName;
		int index = className.indexOf('_');
		if (index > 0)
		{
			if (index < 3)
			{
				className = className.substring(index + 1);
				/*
				 * DELETEME: this part is useless - teo_sarca, [ 1648850 ] else { String prefix = className.substring(0,index); if (prefix.equals("Fact")) // keep custom prefix className =
				 * className.substring(index+1); }
				 */
			}
		}
		// Remove underlines
		className = StringUtils.replace(className, "_", "");

		// Search packages
		for (String s_package : s_packages)
		{
			StringBuilder name = new StringBuilder(s_package).append(".M").append(className);
			Class<?> clazz = loadModelClassForClassname(name.toString());
			if (clazz != null)
			{
				return clazz;
			}
		}

		// Adempiere Extension
		Class<?> clazz = loadModelClassForClassname("adempiere.model.X_" + tableName);
		if (clazz != null)
		{
			return clazz;
		}

		// hengsin - allow compatibility with compiere plugins
		// Compiere Extension
		clazz = loadModelClassForClassname("compiere.model.X_" + tableName);
		if (clazz != null)
		{
			return clazz;
		}

		// Default
		clazz = loadModelClassForClassname("org.compiere.model.X_" + tableName);
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
		return TableIdsCache.instance.getEntityType(tableName);
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
