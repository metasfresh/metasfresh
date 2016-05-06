package de.metas.ui.web.vaadin.window.prototype.order;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.Application;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants.OnChangesFound;
import de.metas.ui.web.vaadin.window.prototype.order.model.WindowModel;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.ConfirmDiscardChangesModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.GridPropertyChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.GridRowAddedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.PropertyChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.view.WindowView;
import de.metas.ui.web.vaadin.window.prototype.order.view.WindowViewListener;

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

public class WindowPresenter implements WindowViewListener
{
	private static final Logger logger = LogManager.getLogger(WindowPresenter.class);

	@Autowired(required = true)
	private WindowModel _model;
	private boolean _registeredToModelEventBus = false;

	@Autowired(required = true)
	private WindowView _view;

	/** {@link PropertyName}s which are interesting for view and which shall be propagated to the view */
	private Set<PropertyName> viewPropertyNames = ImmutableSet.of();

	public WindowPresenter()
	{
		super();
		Application.autowire(this);

		// this._model = new WindowModel();
		// this._view = new WindowViewImpl();
	}

	public void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
	{
		final WindowModel model = getModel();
		final WindowView view = getView();

		//
		// Unregister listeners
		unbindFromModel();
		view.setListener(null);

		//
		// Set root property descriptor to model and view
		view.setRootPropertyDescriptor(rootPropertyDescriptor);
		model.setRootPropertyDescriptor(rootPropertyDescriptor);

		//
		// Register back all listeners
		bindToModel();
		view.setListener(this);
		
		updateViewFromModel();
	}

	public void dispose()
	{
		unbindFromModel();

		final WindowView view = getView();
		if (view != null)
		{
			view.setListener(null);
		}
	}

	private WindowModel getModel()
	{
		return _model;
	}

	private final void bindToModel()
	{
		if (_registeredToModelEventBus)
		{
			logger.trace("Skip binding presenter to model because it was already bound");
			return;
		}

		final WindowModel model = getModel();
		model.getEventBus().register(this);
		_registeredToModelEventBus = true;
		logger.trace("Bound presenter {} to model {}", this, model);
	}

	private final void unbindFromModel()
	{
		if (!_registeredToModelEventBus)
		{
			logger.trace("Skip unbinding presenter from model because it was not bound");
			return;
		}

		final WindowModel model = getModel();
		model.getEventBus().unregister(this);
		_registeredToModelEventBus = false;
		logger.trace("Unbound presenter {} from model {}", this, model);
	}
	
	public void setView(final WindowView view)
	{
		final WindowView viewOld = this._view;
		if (viewOld == view)
		{
			return;
		}
		
		if (viewOld != null)
		{
			viewOld.setListener(null);
		}
		
		this._view = view;
		if(this._view != null)
		{
			this._view.setListener(this);
		}
	}

	private WindowView getView()
	{
		return _view;
	}

	public Component getViewComponent()
	{
		final WindowView view = getView();
		return view == null ? null : view.getComponent();
	}

	private void updateViewFromModel()
	{
		final WindowModel model = getModel();
		final WindowView view = getView();
		logger.debug("Updating {} from {}", view, model);

		// view.setTitle(model.getTitle()); // not needed, will come with all properties
		view.setPreviousRecordEnabled(model.hasPreviousRecord());
		view.setNextRecordEnabled(model.hasNextRecord());

		//
		// Properties
		try
		{
			final PropertyValuesDTO values = model.getPropertyValuesDTO(viewPropertyNames);
			view.setProperties(values);
		}
		catch (Exception e)
		{
			logger.warn("Failed updating the view from model", e);
			view.showError(e.getLocalizedMessage());
		}

		// TODO Auto-generated method stub

	}

	@Override
	public void viewSubscribeToValueChanges(final Set<PropertyName> propertyNames)
	{
		Preconditions.checkNotNull(propertyNames, "propertyNames");
		final Set<PropertyName> viewPropertyNamesNew = ImmutableSet.copyOf(propertyNames);
		if (Objects.equals(this.viewPropertyNames, propertyNames))
		{
			return;
		}

		this.viewPropertyNames = viewPropertyNamesNew;
		logger.trace("View subscribed to following property names: {}", propertyNames);

		updateViewFromModel();
	}

	private final Set<PropertyName> viewSettingPropertyNames = new HashSet<>();

	@Override
	public void viewPropertyChanged(final PropertyName propertyName, final Object value)
	{
		logger.trace("Got view property changed: {}={} ({})", propertyName, value, (value == null ? "-" : value.getClass()));
		logger.trace("UI: {}", UI.getCurrent());

		viewSettingPropertyNames.add(propertyName);
		try
		{
			logger.trace("Updating model's property");
			updateModel(new Runnable()
			{
				@Override
				public void run()
				{
					final WindowModel model = getModel();
					model.setProperty(propertyName, value);
				}
			});
		}
		finally
		{
			viewSettingPropertyNames.remove(propertyName);
		}
	}

	@Override
	public void viewGridPropertyChanged(final PropertyName gridPropertyName, final Object rowId, final PropertyName propertyName, final Object value)
	{
		logger.trace("Got view grid property changed - {}, {}: {}={} ({})", gridPropertyName, rowId, propertyName, value, (value == null ? "-" : value.getClass()));

		final PropertyName cellPropertyName = buildGridCellPropertyName(gridPropertyName, rowId, propertyName);

		viewSettingPropertyNames.add(cellPropertyName);
		try
		{
			logger.trace("Updating model's property");
			updateModel(new Runnable()
			{
				@Override
				public void run()
				{
					final WindowModel model = getModel();
					model.setGridProperty(gridPropertyName, rowId, propertyName, value);
				};
			});
		}
		finally
		{
			viewSettingPropertyNames.remove(cellPropertyName);
		}
	}

	private static final PropertyName buildGridCellPropertyName(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
	{
		return PropertyName.of(gridPropertyName + "." + rowId + "." + propertyName);
	}

	private final UI getUI()
	{
		final WindowView view = getView();
		if (view == null)
		{
			return null;
		}
		final Component viewComp = view.getComponent();
		if (viewComp == null)
		{
			return null;
		}

		final UI ui = viewComp.getUI();
		return ui;
	}

	private final void updateView(final Runnable runnable)
	{
		final UI viewUI = getUI();
		if (viewUI != null && viewUI != UI.getCurrent())
		{
			logger.trace("Updating view on UI: {}", viewUI);
			viewUI.access(runnable);
		}
		else
		{
			logger.trace("Updating view directly");
			runnable.run();
		}
	}

	private final void updateModel(final Runnable runnable)
	{
		final WindowModel model = getModel();
		logger.trace("Updating the model {} using {}", model, runnable);
		try
		{
			runnable.run();
			return;
		}
		catch (Exception modelException)
		{
			handleModelException(modelException);
			return;
		}
	}

	private void handleModelException(final Exception modelException)
	{
		logger.debug("Got model exception", modelException);
		updateView(new Runnable()
		{
			@Override
			public void run()
			{
				final WindowView view = getView();
				view.showError(modelException.getLocalizedMessage());
			}
		});
	}

	@Subscribe
	public void modelAllPropertiesChanged(final AllPropertiesChangedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView(new Runnable()
		{
			@Override
			public void run()
			{
				updateViewFromModel();
			}
		});
	}

	@Subscribe
	public void modelPropertyChanged(final PropertyChangedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView(new Runnable()
		{
			@Override
			public void run()
			{
				modelPropertyChanged0(event);
			}
		});
	}

	private void modelPropertyChanged0(final PropertyChangedModelEvent event)
	{
		final PropertyName propertyName = event.getPropertyName();

		if (viewSettingPropertyNames.contains(propertyName))
		{
			logger.trace("Skip updating the view because this property is currently updating from view: {}", propertyName);
			return;
		}

		if (!viewPropertyNames.contains(propertyName))
		{
			logger.trace("Skip updating the view because this property is not interesting for view: {}", propertyName);
		}

		final WindowView view = getView();
		final Object value = event.getValue();
		view.setProperty(propertyName, value);
	}

	@Subscribe
	public void modelGridPropertyChanged(final GridPropertyChangedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView(new Runnable()
		{
			@Override
			public void run()
			{
				modelGridPropertyChanged0(event);
			}
		});
	}

	private void modelGridPropertyChanged0(final GridPropertyChangedModelEvent event)
	{
		final PropertyName gridPropertyName = event.getGridPropertyName();
		final Object rowId = event.getRowId();
		final PropertyName propertyName = event.getPropertyName();
		final PropertyName cellPropertyName = buildGridCellPropertyName(gridPropertyName, rowId, propertyName);

		if (viewSettingPropertyNames.contains(cellPropertyName))
		{
			logger.trace("Skip updating the view because this property is currently updating from view: {}", cellPropertyName);
			return;
		}

		final WindowView view = getView();
		final Object value = event.getValue();
		view.setGridProperty(gridPropertyName, rowId, propertyName, value);
	}

	@Subscribe
	public void modelGridRowAdded(final GridRowAddedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView(new Runnable()
		{
			@Override
			public void run()
			{
				final PropertyName gridPropertyName = event.getGridPropertyName();
				final Object rowId = event.getRowId();
				final Map<PropertyName, Object> rowValues = event.getRowValues();

				final WindowView view = getView();
				view.gridNewRow(gridPropertyName, rowId, rowValues);
			}
		});
	}

	@Subscribe
	public void modelConfirmDiscardChanges(final ConfirmDiscardChangesModelEvent event)
	{
		updateView(new Runnable()
		{
			@Override
			public void run()
			{
				final WindowView view = getView();
				view.confirmDiscardChanges();
			}
		});
	}

	@Override
	public void viewNextRecord(final OnChangesFound onChangesFound)
	{
		final WindowView view = getView();
		view.commitChanges();

		updateModel(new Runnable()
		{
			@Override
			public void run()
			{
				final WindowModel model = getModel();
				model.nextRecord(onChangesFound);
			}
		});
	}

	@Override
	public void viewPreviousRecord(final OnChangesFound onChangesFound)
	{
		final WindowView view = getView();
		view.commitChanges();

		updateModel(new Runnable()
		{
			@Override
			public void run()
			{
				final WindowModel model = getModel();
				model.previousRecord(onChangesFound);
			}
		});
	}

	@Override
	public void viewSaveEditing()
	{
		updateModel(new Runnable()
		{
			@Override
			public void run()
			{
				final WindowModel model = getModel();
				model.saveRecord();
			}
		});
	}

	@Override
	public void viewCancelEditing()
	{
		updateModel(new Runnable()
		{
			@Override
			public void run()
			{
				final WindowModel model = getModel();
				model.reloadRecord();
			}
		});
	}

	@Override
	public ListenableFuture<Object> viewRequestValue(final PropertyName propertyName)
	{
		final WindowModel model = getModel();
		final Object value = model.getPropertyOrNull(propertyName);
		return Futures.immediateFuture(value);
	}

	@Override
	public ListenableFuture<Object> viewRequestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
	{
		final WindowModel model = getModel();
		final Object value = model.getGridProperty(gridPropertyName, rowId, propertyName);
		return Futures.immediateFuture(value);
	}
}
