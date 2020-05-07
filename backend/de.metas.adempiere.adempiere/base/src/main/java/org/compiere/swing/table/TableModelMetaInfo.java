package org.compiere.swing.table;

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


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IMsgBL;

/**
 * Defines the meta informations of an {@link AnnotatedTableModel}.
 * 
 * @author tsa
 *
 * @param <ModelType> model type
 */
public class TableModelMetaInfo<ModelType>
{
	/**
	 * @param modelClass
	 * @return meta-info of annotated model class
	 */
	public static final <ModelType> TableModelMetaInfo<ModelType> ofModelClass(final Class<ModelType> modelClass)
	{
		return new Builder<>(modelClass)
				.build();
	}

	private final Class<ModelType> modelClass;

	private List<String> columnNames;
	private Map<String, TableColumnInfo> columnInfos = new HashMap<String, TableColumnInfo>();

	private TableModelMetaInfo(final Builder<ModelType> builder)
	{
		super();

		this.modelClass = builder.modelClass;
		this.columnNames = ImmutableList.copyOf(builder.columnNames);
		this.columnInfos = ImmutableMap.copyOf(builder.columnInfos);
	}

	public Class<ModelType> getModelClass()
	{
		return modelClass;
	}

	public List<String> getColumnNames()
	{
		return this.columnNames;
	}

	public void setColumnNames(final List<String> columnNames)
	{
		this.columnNames = ImmutableList.copyOf(columnNames);
	}

	public int getColumnCount()
	{
		return columnNames.size();
	}

	public final int getColumnIndexByColumnName(final String columnName)
	{
		final int column = columnNames.indexOf(columnName);
		if (column < 0)
		{
			throw new IllegalArgumentException("ColumnName '" + columnName + "' was not found");
		}
		return column;
	}

	public final String getColumnNameByColumnIndex(final int column)
	{
		return columnNames.get(column);
	}

	public final TableColumnInfo getTableColumnInfo(final String columnName)
	{
		Check.assumeNotNull(columnName, "columnName not null");
		final TableColumnInfo columnInfo = columnInfos.get(columnName);
		if (columnInfo == null)
		{
			throw new IllegalStateException("No column info found for column name '" + columnName + "'");
		}

		return columnInfo;
	}

	public final TableColumnInfo getTableColumnInfo(final int columnModelIndex)
	{
		final String columnName = getColumnNameByColumnIndex(columnModelIndex);
		return getTableColumnInfo(columnName);
	}

	/**
	 * @return the {@link TableColumnInfo} which is about Selected flag
	 * @see ColumnInfo#selectionColumn()
	 */
	public final TableColumnInfo getSelectionTableColumnInfoOrNull()
	{
		for (final TableColumnInfo columnInfo : columnInfos.values())
		{
			if (columnInfo.isSelectionColumn())
			{
				return columnInfo;
			}
		}
		return null;
	}

	//
	//
	// -----------------------------------------------------------------------------------------------------------------------------------------------
	//
	//
	public static class Builder<ModelType>
	{
		// services
		private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

		private final Class<ModelType> modelClass;
		private boolean defaultHideAllColumns = false;

		private List<String> columnNames;
		private Map<String, TableColumnInfo> columnInfos = new HashMap<String, TableColumnInfo>();

		private Builder(final Class<ModelType> modelClass)
		{
			super();
			Check.assumeNotNull(modelClass, "modelClass not null");
			this.modelClass = modelClass;
		}

		public TableModelMetaInfo<ModelType> build()
		{
			try
			{
				setupColumnInfos();
			}
			catch (final IntrospectionException e)
			{
				throw new AdempiereException("Error while loading info from " + modelClass, e);
			}

			return new TableModelMetaInfo<ModelType>(this);
		}

		public boolean isEditable()
		{
			return true;
		}

		private void setupColumnInfos() throws IntrospectionException
		{
			columnNames = new ArrayList<String>();
			columnInfos = new HashMap<String, TableColumnInfo>();

			//
			// Load defaults defined on class/interface level
			final TableInfo tableInfo = modelClass.getAnnotation(TableInfo.class);
			if (tableInfo != null)
			{
				defaultHideAllColumns = tableInfo.defaultHideAll();
			}

			final BeanInfo beanInfo = Introspector.getBeanInfo(modelClass);
			for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors())
			{
				final Method readMethod = pd.getReadMethod();
				if (readMethod == null)
				{
					// we cannot read this property, skip it
					continue;
				}

				// skip methods declared on Object class level
				if (Object.class.equals(readMethod.getDeclaringClass()))
				{
					continue;
				}

				loadInfo(pd);

				if (pd.isHidden())
				{
					continue;
				}

				final String columnName = pd.getName();
				final String displayName = pd.getDisplayName();
				final Class<?> columnClass = pd.getPropertyType();
				final Method writeMethod = pd.getWriteMethod();
				final int seqNo = getSeqNo(pd);
				final String lookupTableName = getLookupTableName(pd);
				final String lookupColumnName = getLookupColumnName(pd);
				final String prototypeValue = getPrototypeValue(pd);
				final int displayType = getDisplayType(pd);
				final boolean selectionColumn = isSelectionColumn(pd);

				final TableColumnInfo columnInfo = new TableColumnInfo(columnName, columnClass, readMethod);
				columnInfo.setSeqNo(seqNo);
				columnInfo.setDisplayName(displayName);
				columnInfo.setWriteMethod(writeMethod);
				columnInfo.setEditable(isEditable());
				columnInfo.setLookupTableName(lookupTableName);
				columnInfo.setLookupColumnName(lookupColumnName);
				columnInfo.setPrototypeValue(prototypeValue);
				columnInfo.setDisplayType(displayType);
				columnInfo.setSelectionColumn(selectionColumn);

				//
				columnNames.add(columnName);
				columnInfos.put(columnName, columnInfo);
			}

			//
			// Sort columnNames by TableColumnInfo's SeqNo
			Collections.sort(columnNames, new Comparator<String>()
			{

				@Override
				public int compare(final String o1, final String o2)
				{
					if (Check.equals(o1, o2))
					{
						return 0;
					}

					final ITableColumnInfo columnInfo1 = columnInfos.get(o1);
					final int seqNo1 = columnInfo1 == null ? Integer.MAX_VALUE : columnInfo1.getSeqNo();

					final ITableColumnInfo columnInfo2 = columnInfos.get(o2);
					final int seqNo2 = columnInfo2 == null ? Integer.MAX_VALUE : columnInfo2.getSeqNo();

					return seqNo1 - seqNo2;
				}
			});
		}

		private void loadInfo(final PropertyDescriptor pd)
		{
			final Method readMethod = pd.getReadMethod();
			final ColumnInfo readMethodInfo = readMethod == null ? null : readMethod.getAnnotation(ColumnInfo.class);
			if (readMethodInfo != null)
			{
				loadInfo(pd, readMethodInfo);
			}

			final Method writeMethod = pd.getWriteMethod();
			final ColumnInfo writeMethodInfo = writeMethod == null ? null : writeMethod.getAnnotation(ColumnInfo.class);
			if (writeMethodInfo != null)
			{
				loadInfo(pd, writeMethodInfo);
			}

			//
			// If no infos defined, apply defaults
			if (readMethodInfo == null && writeMethodInfo == null)
			{
				if (defaultHideAllColumns)
				{
					pd.setHidden(true);
				}
			}
		}

		private void loadInfo(final PropertyDescriptor pd, final ColumnInfo info)
		{
			//
			// ColumnName
			final String columnName = info.columnName();
			if (!Check.isEmpty(columnName, true))
			{
				pd.setName(columnName);
			}

			//
			// DisplayName
			String displayName;
			boolean translate;
			if (Check.isEmpty(info.displayName(), false))
			{
				displayName = columnName;
				translate = true;
			}
			else
			{
				displayName = info.displayName();
				translate = info.translate();
			}
			// Translate if needed
			if (translate && !Check.isEmpty(displayName, false) && !Services.get(IDeveloperModeBL.class).isEnabled())
			{
				displayName = msgBL.translate(getCtx(), displayName);
			}
			// Set it
			pd.setDisplayName(displayName);

			//
			// Hidden
			if (info.hidden())
			{
				pd.setHidden(true);
			}
		}

		private Properties getCtx()
		{
			return Env.getCtx(); // FIXME
		}

		private int getSeqNo(final PropertyDescriptor pd)
		{
			final Method method = pd.getReadMethod();
			if (method == null)
			{
				return Integer.MAX_VALUE;
			}
			final ColumnInfo info = method.getAnnotation(ColumnInfo.class);
			if (info == null)
			{
				return Integer.MAX_VALUE;
			}

			return info.seqNo();
		}

		private String getLookupTableName(final PropertyDescriptor pd)
		{
			String lookupTableName = getLookupTableName(pd.getReadMethod());
			if (Check.isEmpty(lookupTableName))
			{
				lookupTableName = getLookupTableName(pd.getWriteMethod());
			}

			return lookupTableName;
		}

		private String getLookupTableName(final Method method)
		{
			if (method == null)
			{
				return null;
			}
			final ColumnInfo info = method.getAnnotation(ColumnInfo.class);
			if (info == null)
			{
				return null;
			}
			final String lookupTableName = info.lookupTableName();
			if (Check.isEmpty(lookupTableName))
			{
				return null;
			}
			return lookupTableName;
		}

		private String getLookupColumnName(final PropertyDescriptor pd)
		{
			String lookupColumnName = getLookupColumnName(pd.getReadMethod());
			if (Check.isEmpty(lookupColumnName))
			{
				lookupColumnName = getLookupColumnName(pd.getWriteMethod());
			}

			return lookupColumnName;
		}

		private String getLookupColumnName(final Method method)
		{
			if (method == null)
			{
				return null;
			}
			final ColumnInfo info = method.getAnnotation(ColumnInfo.class);
			if (info == null)
			{
				return null;
			}
			final String lookupColumnName = info.lookupColumnName();
			if (Check.isEmpty(lookupColumnName))
			{
				return null;
			}
			return lookupColumnName;
		}

		private String getPrototypeValue(final PropertyDescriptor pd)
		{
			String prototypeValue = getPrototypeValue(pd.getReadMethod());
			if (Check.isEmpty(prototypeValue))
			{
				prototypeValue = getPrototypeValue(pd.getWriteMethod());
			}

			return prototypeValue;
		}

		private String getPrototypeValue(final Method method)
		{
			if (method == null)
			{
				return null;
			}
			final ColumnInfo info = method.getAnnotation(ColumnInfo.class);
			if (info == null)
			{
				return null;
			}
			final String prototypeValue = info.prototypeValue();
			if (Check.isEmpty(prototypeValue, false))
			{
				return null;
			}
			return prototypeValue;
		}

		private int getDisplayType(final PropertyDescriptor pd)
		{
			int displayType = getDisplayType(pd.getReadMethod());
			if (displayType <= 0)
			{
				displayType = getDisplayType(pd.getWriteMethod());
			}
			if (displayType <= 0)
			{
				displayType = suggestDisplayType(pd.getReadMethod().getReturnType());
			}

			return displayType;
		}

		private static final int suggestDisplayType(final Class<?> returnType)
		{
			if (returnType == String.class)
			{
				return DisplayType.String;
			}
			else if (returnType == Boolean.class || returnType == boolean.class)
			{
				return DisplayType.YesNo;
			}
			else if (returnType == BigDecimal.class)
			{
				return DisplayType.Amount;
			}
			else if (returnType == Integer.class)
			{
				return DisplayType.Integer;
			}
			else if (Date.class.isAssignableFrom(returnType))
			{
				return DisplayType.Date;
			}
			else
			{
				return -1;
			}
		}

		private int getDisplayType(final Method method)
		{
			if (method == null)
			{
				return -1;
			}
			final ColumnInfo info = method.getAnnotation(ColumnInfo.class);
			if (info == null)
			{
				return -1;
			}
			final int displayType = info.displayType();
			return displayType > 0 ? displayType : -1;
		}

		private boolean isSelectionColumn(final PropertyDescriptor pd)
		{
			Boolean selectionColumn = getSelectionColumn(pd.getReadMethod());
			if (selectionColumn == null)
			{
				selectionColumn = getSelectionColumn(pd.getWriteMethod());
			}

			return selectionColumn != null ? selectionColumn : false;
		}

		private Boolean getSelectionColumn(final Method method)
		{
			if (method == null)
			{
				return null;
			}
			final ColumnInfo info = method.getAnnotation(ColumnInfo.class);
			if (info == null)
			{
				return null;
			}
			return info.selectionColumn();
		}
	}
}
