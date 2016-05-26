package de.metas.ui.web.vaadin.window.editor;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.adempiere.util.concurrent.FutureValue;
import org.slf4j.Logger;
import org.vaadin.viritin.fields.LazyComboBox;
import org.vaadin.viritin.fields.LazyComboBox.FilterableCountProvider;
import org.vaadin.viritin.fields.LazyComboBox.FilterablePagingProvider;

import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.editor.EditorListener.ViewCommandCallback;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.shared.command.LookupDataSourceQueryCommand;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.datatype.LookupValue;
import de.metas.ui.web.window.shared.datatype.NullValue;
import de.metas.ui.web.window.shared.datatype.PropertyPath;

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

@SuppressWarnings("serial")
public class SearchLookupValueEditor extends FieldEditor<LookupValue>
{
	@SuppressWarnings("unused")
	private static final Logger logger = LogManager.getLogger(SearchLookupValueEditor.class);

	private PropertyName valuesPropertyName;

	private final int pageLength = LookupDataSourceQueryCommand.DEFAULT_PageLength;
	private ComboDataSource _comboDataSource;

	public SearchLookupValueEditor(final PropertyDescriptor descriptor)
	{
		super(descriptor);
	}

	@Override
	protected void collectWatchedPropertyNamesOnInit(final ImmutableSet.Builder<PropertyName> watchedPropertyNames)
	{
		super.collectWatchedPropertyNamesOnInit(watchedPropertyNames);

		valuesPropertyName = WindowConstants.lookupValuesName(getPropertyName());
		watchedPropertyNames.add(valuesPropertyName);
	}

	@Override
	protected LazyComboBox<LookupValue> createValueField()
	{
		final ComboDataSource comboDataSource = getComboDataSource();
		final int pageLength = comboDataSource.getPageLength();

		@SuppressWarnings("unchecked")
		final LazyComboBox<LookupValue> valueField = new LazyComboBox<LookupValue>(LookupValue.class, comboDataSource, comboDataSource, pageLength)
		{
			@Override
			protected void setInternalValue(final Object newValue)
			{
				final LookupValue lookupValue = toLookupValue(newValue);
				getComboDataSource().setCurrentValue(lookupValue);
				super.setInternalValue(lookupValue);
			}
		};

		return valueField;
	}

	private final ComboDataSource getComboDataSource()
	{
		if (_comboDataSource == null)
		{
			final PropertyPath propertyPath = getPropertyPath();
			final PropertyPath valuesPropertyPath = PropertyPath.of(propertyPath.getGridPropertyName(), propertyPath.getRowId(), WindowConstants.lookupValuesName(propertyPath.getPropertyName()));
			_comboDataSource = new ComboDataSource(valuesPropertyPath, getEditorListener(), pageLength);
		}
		return _comboDataSource;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected LazyComboBox<LookupValue> getValueField()
	{
		return (LazyComboBox<LookupValue>)super.getValueField();
	}

	@Override
	public void setEditorListener(final EditorListener listener)
	{
		super.setEditorListener(listener);
		getComboDataSource().setHandler(listener);
	}

	@Override
	public void setValue(final PropertyName propertyName, final Object value)
	{
		if (Objects.equals(getPropertyName(), propertyName))
		{
			final LookupValue lookupValue = toLookupValue(value);
			getComboDataSource().setCurrentValue(lookupValue);
			super.setValue(propertyName, lookupValue);
		}
		// else if (Objects.equals(valuesPropertyName, propertyName))
		// {
		// final LookupDataSourceServiceDTO lookupDataSourceService = LookupDataSourceServiceDTO.cast(value);
		// lookupDataSourceServiceSupplier.set(lookupDataSourceService);
		// }
		else
		{
			super.setValue(propertyName, value);
		}
	}

	@Override
	protected LookupValue convertToView(final Object valueObj)
	{
		return toLookupValue(valueObj);
	}

	private static final LookupValue toLookupValue(final Object valueObj)
	{
		if (NullValue.isNull(valueObj))
		{
			return null;
		}
		else if (valueObj instanceof LookupValue)
		{
			return LookupValue.cast(valueObj);
		}
		else if (valueObj instanceof Integer)
		{
			final int id = (int)valueObj;
			return LookupValue.unknownId(id);
		}
		else
		{
			throw new IllegalArgumentException("Cannot cast '" + valueObj + "' (" + valueObj.getClass() + ") to " + LookupValue.class);
		}

	}

	private static final class ComboDataSource implements FilterablePagingProvider<LookupValue>, FilterableCountProvider
	{
		private final PropertyPath valuesPropertyPath;
		private EditorListener handler = NullEditorListener.instance;

		private final int pageLength;
		private LookupValue _currentValue;

		public ComboDataSource(final PropertyPath valuesPropertyPath, final EditorListener handler, final int pageLength)
		{
			super();

			this.valuesPropertyPath = valuesPropertyPath;

			if (pageLength <= 0)
			{
				throw new IllegalArgumentException("pageLength <= 0");
			}
			this.pageLength = pageLength;

			setHandler(handler);
		}

		public void setHandler(final EditorListener handler)
		{
			this.handler = handler == null ? NullEditorListener.instance : handler;
		}

		public int getPageLength()
		{
			return pageLength;
		}

		public void setCurrentValue(final LookupValue newValue)
		{
			_currentValue = newValue;
		}

		private LookupValue getCurrentValue()
		{
			return _currentValue;
		}

		private ImmutableList<LookupValue> returnInvalidResult = null;

		@Override
		public int size(final String filter)
		{
			returnInvalidResult = null;

			final int size = executeCommand(ViewCommand.builder()
					.setPropertyPath(valuesPropertyPath)
					.setCommandId(LookupDataSourceQueryCommand.COMMAND_Size)
					.setParameter(LookupDataSourceQueryCommand.PARAMETER_Filter, filter)
					.build());

			if (size != LookupDataSourceQueryCommand.SIZE_InvalidFilter)
			{
				return size;
			}

			//
			// Fallback (when filter is not valid or there is no datasource service)
			final LookupValue currentValue = getCurrentValue();
			final ImmutableList<LookupValue> returnInvalidResult = currentValue == null ? ImmutableList.of() : ImmutableList.of(currentValue);
			this.returnInvalidResult = returnInvalidResult;
			return returnInvalidResult.size();
		}

		@Override
		public List<LookupValue> findEntities(final int firstRow, final String filter)
		{
			final ImmutableList<LookupValue> returnInvalidResult = this.returnInvalidResult;
			if (returnInvalidResult != null)
			{
				return returnInvalidResult;
			}

			final List<LookupValue> values = executeCommand(ViewCommand.builder()
					.setPropertyPath(valuesPropertyPath)
					.setCommandId(LookupDataSourceQueryCommand.COMMAND_Find)
					.setParameter(LookupDataSourceQueryCommand.PARAMETER_Filter, filter)
					.setParameter(LookupDataSourceQueryCommand.PARAMETER_FirstRow, firstRow)
					.setParameter(LookupDataSourceQueryCommand.PARAMETER_PageLength, pageLength)
					.build());
			if (values != null)
			{
				return ImmutableList.copyOf(values);
			}

			//
			// Fallback (when filter is not valid or there is no datasource service)
			final LookupValue currentValue = getCurrentValue();
			final List<LookupValue> fallbackValues = currentValue == null ? ImmutableList.of() : ImmutableList.of(currentValue);
			return fallbackValues;
		}

		private <ResultType> ResultType executeCommand(final ViewCommand command)
		{
			try
			{
				final FutureValue<ResultType> futureValue = new FutureValue<>();
				handler.executeCommand(command, new ViewCommandCallback()
				{

					@Override
					public void onResult(final ViewCommand command, final ViewCommandResult result)
					{
						try
						{
							final ResultType resultCasted = result.getValue();
							futureValue.set(resultCasted);
						}
						catch (final Exception ex)
						{
							futureValue.setError(ex);
						}
					}

					@Override
					public void onError(final Exception error)
					{
						futureValue.setError(error);
					}
				});

				return futureValue.get(10, TimeUnit.SECONDS);
			}
			catch (final Exception e)
			{
				throw Throwables.propagate(Throwables.getRootCause(e));
			}
		}
	}
}
