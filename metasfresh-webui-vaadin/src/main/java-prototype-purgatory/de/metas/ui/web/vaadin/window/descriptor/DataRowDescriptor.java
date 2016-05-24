package de.metas.ui.web.vaadin.window.descriptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.compiere.model.GridFieldVO;
import org.compiere.model.GridTabVO;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DataRowDescriptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int AD_Tab_ID;
	private final String name;
	private final int tabNo;
	private final int tabLevel;
	private final Map<String, DataFieldPropertyDescriptor> fieldPropertyDescriptors;
	private final List<DataFieldPropertyDescriptor> idFieldsDescriptors;
	private final List<DataFieldPropertyDescriptor> parentFieldDescriptors;

	// SQL
	private final String tableName;
	private List<String> _columnNamesInGridOrder;
	private List<String> _columnNamesInFormOrder;

	private DataRowDescriptor(final Builder builder)
	{
		super();

		this.AD_Tab_ID = builder.AD_Tab_ID;
		this.name = builder.name;
		this.tabNo = builder.tabNo;
		this.tabLevel = builder.tabLevel;
		this.fieldPropertyDescriptors = ImmutableMap.copyOf(builder.fieldPropertyDescriptors);
		this.idFieldsDescriptors = ImmutableList.copyOf(builder.idFieldDescriptors);
		this.parentFieldDescriptors = ImmutableList.copyOf(builder.parentFieldDescriptors);

		//
		// Table based row descriptor
		this.tableName = builder.tableName;
	}

	public Collection<DataFieldPropertyDescriptor> getFieldDescriptors()
	{
		return fieldPropertyDescriptors.values();
	}

	public DataFieldPropertyDescriptor getFieldDescriptorByColumnName(final Object columnName)
	{
		return fieldPropertyDescriptors.get(columnName);
	}

	public List<DataFieldPropertyDescriptor> getIdFieldsDescriptors()
	{
		return idFieldsDescriptors;
	}

	public List<DataFieldPropertyDescriptor> getParentFieldDescriptors()
	{
		return parentFieldDescriptors;
	}

	public Collection<String> getColumnNames()
	{
		return fieldPropertyDescriptors.keySet();
	}

	public List<String> getColumnNamesInGridOrder()
	{
		if (_columnNamesInGridOrder == null)
		{
			final List<DataFieldPropertyDescriptor> fieldDescriptors = new ArrayList<>(fieldPropertyDescriptors.values());
			Collections.sort(fieldDescriptors, new Comparator<DataFieldPropertyDescriptor>()
			{

				@Override
				public int compare(DataFieldPropertyDescriptor o1, DataFieldPropertyDescriptor o2)
				{
					return o1.getSeqNoGrid() - o2.getSeqNoGrid();
				}

			});
			_columnNamesInGridOrder = FluentIterable.from(fieldDescriptors)
					.filter(new Predicate<DataFieldPropertyDescriptor>()
					{
						@Override
						public boolean apply(DataFieldPropertyDescriptor field)
						{
							return field.isDisplayedInGrid();
						}
					})
					.transform(new Function<DataFieldPropertyDescriptor, String>()
					{
						@Override
						public String apply(DataFieldPropertyDescriptor field)
						{
							return field.getColumnName();
						}
					})
					.toList();
		}
		return _columnNamesInGridOrder;
	}
	
	public List<String> getColumnNamesInFormOrder()
	{
		if (_columnNamesInFormOrder == null)
		{
			final List<DataFieldPropertyDescriptor> fieldDescriptors = new ArrayList<>(fieldPropertyDescriptors.values());
			Collections.sort(fieldDescriptors, new Comparator<DataFieldPropertyDescriptor>()
			{

				@Override
				public int compare(DataFieldPropertyDescriptor o1, DataFieldPropertyDescriptor o2)
				{
					return o1.getSeqNoForm() - o2.getSeqNoForm();
				}

			});
			_columnNamesInFormOrder = FluentIterable.from(fieldDescriptors)
					.filter(new Predicate<DataFieldPropertyDescriptor>()
					{
						@Override
						public boolean apply(DataFieldPropertyDescriptor field)
						{
							return field.isDisplayedInForm();
						}
					})
					.transform(new Function<DataFieldPropertyDescriptor, String>()
					{
						@Override
						public String apply(DataFieldPropertyDescriptor field)
						{
							return field.getColumnName();
						}
					})
					.toList();
		}
		return _columnNamesInFormOrder;
	}


	public Class<?> getValueClassByColumnName(Object columnName)
	{
		final DataFieldPropertyDescriptor fieldDescriptor = fieldPropertyDescriptors.get(columnName);
		Preconditions.checkNotNull(fieldDescriptor, "no field descriptor found for %s", columnName);

		return fieldDescriptor.getValueClass();
	}

	public String getName()
	{
		return name;
	}

	public int getTabNo()
	{
		return tabNo;
	}

	public int getTabLevel()
	{
		return tabLevel;
	}

	public int getAD_Tab_ID()
	{
		return AD_Tab_ID;
	}

	public String getTableName()
	{
		return tableName;
	}

	public Set<DataFieldPropertyDescriptor> getLinkingFieldsFromParent(final DataRowDescriptor parentTabDescriptor)
	{
		final Set<DataFieldPropertyDescriptor> parentFields = new HashSet<>();
		for (final DataFieldPropertyDescriptor childFieldDescriptor : getParentFieldDescriptors())
		{
			final String columnName = childFieldDescriptor.getColumnName();
			final DataFieldPropertyDescriptor parentFieldDescriptor = parentTabDescriptor.getFieldDescriptorByColumnName(columnName);
			if (parentFieldDescriptor == null)
			{
				continue;
			}

			parentFields.add(parentFieldDescriptor);
		}

		//
		// Bind by parent's primary key
		if (parentFields.isEmpty())
		{
			for (final DataFieldPropertyDescriptor parentIdDescriptor : parentTabDescriptor.getIdFieldsDescriptors())
			{
				final String columnName = parentIdDescriptor.getColumnName();
				final DataFieldPropertyDescriptor childFieldDescriptor = getFieldDescriptorByColumnName(columnName);
				if (childFieldDescriptor == null)
				{
					continue;
				}

				parentFields.add(parentIdDescriptor);
			}
		}

		return parentFields;
	}

	public static final class Builder
	{
		private int AD_Tab_ID;
		private String name;
		public int tabNo;
		private int tabLevel;
		//
		private Map<String, DataFieldPropertyDescriptor.Builder> fieldBuilders = new HashMap<>();
		private Map<String, DataFieldPropertyDescriptor> fieldPropertyDescriptors;
		private List<DataFieldPropertyDescriptor> idFieldDescriptors;
		private List<DataFieldPropertyDescriptor> parentFieldDescriptors;
		//
		public String tableName;

		private Builder()
		{
			super();
		}

		public DataRowDescriptor build()
		{
			normalizeIDFields();

			final ImmutableMap.Builder<String, DataFieldPropertyDescriptor> fieldPropertyDescriptors = ImmutableMap.builder();
			final ImmutableList.Builder<DataFieldPropertyDescriptor> idFieldsDescriptors = ImmutableList.builder();
			final ImmutableList.Builder<DataFieldPropertyDescriptor> parentFieldDescriptors = ImmutableList.builder();
			for (final DataFieldPropertyDescriptor.Builder fieldBuilder : fieldBuilders.values())
			{
				final DataFieldPropertyDescriptor fieldDescriptor = fieldBuilder.build();
				final String columnName = fieldDescriptor.getColumnName();
				fieldPropertyDescriptors.put(columnName, fieldDescriptor);

				if (fieldDescriptor.isKey())
				{
					idFieldsDescriptors.add(fieldDescriptor);
				}

				if (fieldDescriptor.isParentLink())
				{
					parentFieldDescriptors.add(fieldDescriptor);
				}
			}

			//
			//
			this.fieldPropertyDescriptors = fieldPropertyDescriptors.build();
			this.idFieldDescriptors = idFieldsDescriptors.build();
			this.parentFieldDescriptors = parentFieldDescriptors.build();

			//
			//
			return new DataRowDescriptor(this);
		}

		private final void normalizeIDFields()
		{
			final List<DataFieldPropertyDescriptor.Builder> primaryKeys = new ArrayList<>();
			final List<DataFieldPropertyDescriptor.Builder> parentKeys = new ArrayList<>();
			for (final DataFieldPropertyDescriptor.Builder fieldBuilder : fieldBuilders.values())
			{
				if (fieldBuilder.isKey())
				{
					primaryKeys.add(fieldBuilder);
				}
				if (fieldBuilder.isParentLink())
				{
					parentKeys.add(fieldBuilder);
				}
				fieldBuilder.setKey(false);
			}

			if (!primaryKeys.isEmpty())
			{
				if (primaryKeys.size() == 1)
				{
					final DataFieldPropertyDescriptor.Builder primaryKey = primaryKeys.get(0);
					primaryKey.setKey(true);
				}
				else
				{
					// TODO
				}
			}
			else if (!parentKeys.isEmpty())
			{
				for (final DataFieldPropertyDescriptor.Builder parentKey : parentKeys)
				{
					parentKey.setKey(true);
				}
			}
			else
			{
				// TODO
			}
		}

		public Builder initFrom(GridTabVO gridTabVO)
		{
			this.AD_Tab_ID = gridTabVO.AD_Tab_ID;
			this.name = gridTabVO.Name;
			this.tabNo = gridTabVO.TabNo;
			this.tabLevel = gridTabVO.TabLevel;

			this.fieldBuilders.clear();
			for (final GridFieldVO gridFieldVO : gridTabVO.getFields())
			{
				final DataFieldPropertyDescriptor.Builder fieldBuilder = DataFieldPropertyDescriptor.builder()
						.initFrom(gridFieldVO);
				fieldBuilders.put(fieldBuilder.getColumnName(), fieldBuilder);
			}

			this.tableName = gridTabVO.TableName;

			return this;
		}
	}
}
