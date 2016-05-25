package de.metas.ui.web.vaadin.window;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

import de.metas.logging.LogManager;
import de.metas.ui.web.Application;
import de.metas.ui.web.vaadin.window.view.ActionsView;
import de.metas.ui.web.vaadin.window.view.WindowView;
import de.metas.ui.web.vaadin.window.view.WindowViewListener;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.WindowConstants.OnChangesFound;
import de.metas.ui.web.window.descriptor.PropertyDescriptor;
import de.metas.ui.web.window.model.JSONProxyWindowModel;
import de.metas.ui.web.window.model.WindowModel;
import de.metas.ui.web.window.model.WindowModelImpl;
import de.metas.ui.web.window.model.action.ActionsList;
import de.metas.ui.web.window.model.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.window.model.event.ConfirmDiscardChangesModelEvent;
import de.metas.ui.web.window.model.event.GridPropertyChangedModelEvent;
import de.metas.ui.web.window.model.event.GridRowAddedModelEvent;
import de.metas.ui.web.window.model.event.PropertyChangedModelEvent;
import de.metas.ui.web.window.model.event.ZoomToWindowEvent;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;

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

//	@Autowired(required = true)
//	// @Lazy
//	private WindowModel _model;
	private WindowModel _model = new JSONProxyWindowModel(new WindowModelImpl());
	private boolean _registeredToModelEventBus = false;

	@Autowired(required = true)
	// @Lazy
	private WindowView _view;
	private final Set<ActionsView> _actionsViews = new LinkedHashSet<>();

	/** {@link PropertyName}s which are interesting for view and which shall be propagated to the view */
	private Set<PropertyName> viewPropertyNames = ImmutableSet.of();

	private Multimap<PropertyName, PropertyValueChangedListener> propertyValueChangedListeners = LinkedHashMultimap.create();

	public WindowPresenter()
	{
		super();
		Application.autowire(this);

		setView(this._view, null);
	}

	public void setRootPropertyDescriptor(final PropertyDescriptor rootPropertyDescriptor)
	{
		final WindowModel model = getModel();
		final WindowView view = getView();

		//
		// Unregister listeners
		unbindFromModel();
		unbindFromView();

		//
		// Set root property descriptor to model and view
		if (view != null)
		{
			view.setRootPropertyDescriptor(rootPropertyDescriptor);
		}
		model.setRootPropertyDescriptor(rootPropertyDescriptor);

		//
		// Register back all listeners
		bindToModel();
		bindToView();

		//
		updateViewFromModel(view);
	}

	public void dispose()
	{
		unbindFromModel();
		setView(null);
		_actionsViews.clear();
		viewPropertyNames = ImmutableSet.of();
	}

	public WindowModel getModel()
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
		model.subscribe(this);
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
		model.unsubscribe(this);
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

		setView(view, viewOld);
	}

	private final void setView(final WindowView view, final WindowView viewOld)
	{
		unbindFromView();
		this._view = view;
		bindToView();

		//
		// Add remove for actions view
		if (viewOld instanceof ActionsView)
		{
			removeActionsView((ActionsView)viewOld);
		}
		if (view instanceof ActionsView)
		{
			addActionsView((ActionsView)view);
		}
	}

	private WindowView getView()
	{
		return _view;
	}

	private final void bindToView()
	{
		final WindowView view = getView();
		if (view == null)
		{
			return;
		}

		view.setListener(this);
	}

	private final void unbindFromView()
	{
		final WindowView view = getView();
		if (view == null)
		{
			return;
		}

		view.setListener(null);

	}

	public Component getViewComponent()
	{
		final WindowView view = getView();
		return view == null ? null : view.getComponent();
	}

	private void updateViewFromModel(final WindowView view)
	{
		if (view == null)
		{
			return;
		}

		final WindowModel model = getModel();
		logger.debug("Updating {} from {}", view, model);

		// view.setTitle(model.getTitle()); // not needed, will come with all properties

		//
		// Actions
		view.setPreviousRecordEnabled(model.hasPreviousRecord());
		view.setNextRecordEnabled(model.hasNextRecord());
		//
		final Set<ActionsView> actionsViews = getActionsView();
		if (!actionsViews.isEmpty())
		{
			final ActionsList actions = model.getActions();
			for (final ActionsView actionsView : actionsViews)
			{
				actionsView.setActions(actions);
			}
		}

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
		//
		fireAllPropertyValueChangedListeners();

		// TODO Auto-generated method stub

	}

	public void addActionsView(final ActionsView actionsView)
	{
		Preconditions.checkNotNull(actionsView);

		if (!_actionsViews.add(actionsView))
		{
			// already added
			return;
		}

		final WindowModel model = getModel();
		if (model != null)
		{
			actionsView.setActions(model.getActions());
		}
	}

	public void removeActionsView(final ActionsView actionsView)
	{
		if (!_actionsViews.remove(actionsView))
		{
			// does not exist
			return;
		}

		// also reset the actions
		actionsView.setActions(ActionsList.EMPTY);
	}

	public Set<ActionsView> getActionsView()
	{
		return ImmutableSet.copyOf(_actionsViews);
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

		updateViewFromModel(getView());
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
			updateModel((model) -> model.setProperty(propertyName, value));
		}
		finally
		{
			viewSettingPropertyNames.remove(propertyName);
		}
	}

	@Override
	public void viewGridNewRow(final PropertyName gridPropertyName)
	{
		updateModel(model -> model.gridNewRow(gridPropertyName));
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
			updateModel((model) -> model.setGridProperty(gridPropertyName, rowId, propertyName, value));
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

	private final void updateView(final Consumer<WindowView> consumer)
	{
		final WindowView view = getView();
		if (view == null)
		{
			return;
		}

		final UI viewUI = getUI();
		final UI currentUI = UI.getCurrent();
		if (viewUI != null && viewUI != currentUI)
		{
			logger.trace("Updating view on UI: {}", viewUI);
			viewUI.access(() -> consumer.accept(view));
		}
		else
		{
			logger.trace("Updating view directly (viewUI={}, currentUI={})", viewUI, currentUI);
			consumer.accept(view);
		}
	}

	private final void updateModel(final Consumer<WindowModel> consumer)
	{
		final WindowModel model = getModel();
		logger.trace("Updating the model {} using {}", model, consumer);
		try
		{
			consumer.accept(model);
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
		updateView((view) -> view.showError(modelException.getLocalizedMessage()));
	}

	@Subscribe
	public void modelAllPropertiesChanged(final AllPropertiesChangedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView((view) -> updateViewFromModel(view));

		fireAllPropertyValueChangedListeners();
	}

	@Subscribe
	public void modelPropertyChanged(final PropertyChangedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView((view) -> modelPropertyChanged0(view, event));

		firePropertyValueChangedListeners(event.getPropertyName(), event.getValue());
	}

	private void modelPropertyChanged0(final WindowView view, final PropertyChangedModelEvent event)
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

		final Object value = event.getValue();
		view.setProperty(propertyName, value);
	}

	@Subscribe
	public void modelGridPropertyChanged(final GridPropertyChangedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView((view) -> modelGridPropertyChanged0(view, event));
	}

	private void modelGridPropertyChanged0(final WindowView view, final GridPropertyChangedModelEvent event)
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

		final Object value = event.getValue();
		view.setGridProperty(gridPropertyName, rowId, propertyName, value);
	}

	@Subscribe
	public void modelGridRowAdded(final GridRowAddedModelEvent event)
	{
		logger.trace("Got {}", event);
		updateView((view) -> {
			final PropertyName gridPropertyName = event.getGridPropertyName();
			final Object rowId = event.getRowId();
			final PropertyValuesDTO rowValues = event.getRowValues();

			view.gridNewRow(gridPropertyName, rowId, rowValues);
		});
	}

	@Subscribe
	public void modelConfirmDiscardChanges(final ConfirmDiscardChangesModelEvent event)
	{
		updateView((view) -> view.confirmDiscardChanges());
	}

	@Override
	public void viewNextRecord(final OnChangesFound onChangesFound)
	{
		final WindowView view = getView();
		Preconditions.checkNotNull(view, "view");
		view.commitChanges();

		updateModel((model) -> model.nextRecord(onChangesFound));
	}

	@Override
	public void viewPreviousRecord(final OnChangesFound onChangesFound)
	{
		final WindowView view = getView();
		Preconditions.checkNotNull(view, "view");
		view.commitChanges();

		updateModel((model) -> model.previousRecord(onChangesFound));
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

	public void onError(final Throwable ex)
	{
		logger.warn("Got error", ex);

		final String errorMessage = Throwables.getRootCause(ex).getLocalizedMessage();
		updateView(view -> view.showError(errorMessage));
	}

	public static interface PropertyValueChangedListener
	{
		void valueChanged(PropertyName propertyName, Object value);
	}

	public void addPropertyValueChangedListener(final PropertyName propertyName, PropertyValueChangedListener listener)
	{
		Preconditions.checkNotNull(propertyName, "propertyName not null");
		Preconditions.checkNotNull(listener, "listener not null");
		propertyValueChangedListeners.put(propertyName, listener);

		final WindowModel model = getModel();
		if (model.hasProperty(propertyName))
		{
			final Object value = model.getProperty(propertyName);
			listener.valueChanged(propertyName, value);
		}
	}

	private void firePropertyValueChangedListeners(final PropertyName propertyName, final Object value)
	{
		for (final PropertyValueChangedListener listener : propertyValueChangedListeners.get(propertyName))
		{
			listener.valueChanged(propertyName, value);
		}
	}

	private void fireAllPropertyValueChangedListeners()
	{
		final Set<PropertyName> propertyNames = propertyValueChangedListeners.keySet();
		if (propertyNames.isEmpty())
		{
			return;
		}

		final WindowModel model = getModel();
		for (final PropertyName propertyName : propertyNames)
		{
			if (!model.hasProperty(propertyName))
			{
				continue;
			}
			final Object value = model.getProperty(propertyName);
			for (final PropertyValueChangedListener listener : propertyValueChangedListeners.get(propertyName))
			{
				listener.valueChanged(propertyName, value);
			}
		}
	}

	@Override
	public void onActionClicked(final String actionId)
	{
		final WindowModel model = getModel();
		model.executeAction(actionId);
	}
	
	@Override
	public ActionsList viewRequestChildActions(String actionId)
	{
		final WindowModel model = getModel();
		return model.getChildActions(actionId);
	}

	@Subscribe
	public void modelZoomToWindowEvent(final ZoomToWindowEvent event)
	{
		final int windowId = event.getWindowId();
		final String viewNameAndParameters = WindowViewProvider.createViewNameAndParameters(windowId);
		UI.getCurrent().getNavigator().navigateTo(viewNameAndParameters);
	}

}
