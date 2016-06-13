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
import de.metas.ui.web.vaadin.VaadinClientApplication;
import de.metas.ui.web.vaadin.model.RestProxyWindowModel;
import de.metas.ui.web.vaadin.window.editor.EditorListener.ViewCommandCallback;
import de.metas.ui.web.vaadin.window.view.ActionsView;
import de.metas.ui.web.vaadin.window.view.WindowView;
import de.metas.ui.web.vaadin.window.view.WindowViewListener;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.PropertyNameSet;
import de.metas.ui.web.window.model.WindowModel;
import de.metas.ui.web.window.shared.action.ActionsList;
import de.metas.ui.web.window.shared.command.ViewCommand;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.datatype.PropertyPath;
import de.metas.ui.web.window.shared.datatype.PropertyValuesDTO;
import de.metas.ui.web.window.shared.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.window.shared.event.ConfirmDiscardChangesModelEvent;
import de.metas.ui.web.window.shared.event.GridRowAddedModelEvent;
import de.metas.ui.web.window.shared.event.PropertyChangedModelEvent;
import de.metas.ui.web.window.shared.event.ZoomToWindowEvent;

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

	private WindowModel _model = null;
	private boolean _registeredToModelEventBus = false;
	private boolean _modelInitialized = false;

	@Autowired(required = true)
	// @Lazy
	private WindowView _view;
	private final Set<ActionsView> _actionsViews = new LinkedHashSet<>();

	/** {@link PropertyName}s which are interesting for view and which shall be propagated to the view */
	private PropertyNameSet viewPropertyNames = PropertyNameSet.EMPTY;

	private final Multimap<PropertyPath, PropertyValueChangedListener> propertyValueChangedListeners = LinkedHashMultimap.create();

	public WindowPresenter()
	{
		super();
		VaadinClientApplication.autowire(this);

		// _model = new JSONProxyWindowModel(new WindowModelImpl());
		_model = new RestProxyWindowModel();

		setView(_view, null);
	}

	public void setRootPropertyDescriptorFromWindow(final int windowId)
	{
		final WindowModel model = getModel();
		final WindowView view = getView();

		//
		// Unregister listeners
		unbindFromModel();
		unbindFromView();

		//
		// Set root property descriptor to model and view
		model.setRootPropertyDescriptorFromWindow(windowId);
		_modelInitialized = true;
		if (view != null)
		{
			view.setRootPropertyDescriptor(model.getViewRootPropertyDescriptor());
		}

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
		viewPropertyNames = PropertyNameSet.EMPTY;
	}

	public WindowModel getModel()
	{
		return _model;
	}

	private WindowModel getModelIfInitialized()
	{
		return _modelInitialized ? _model : null;
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
		final WindowView viewOld = _view;
		if (viewOld == view)
		{
			return;
		}

		setView(view, viewOld);
	}

	private final void setView(final WindowView view, final WindowView viewOld)
	{
		unbindFromView();
		_view = view;
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

		final WindowModel model = getModelIfInitialized();
		if (model == null)
		{
			logger.debug("Skip updating {} because model is not initialized", view);
			return;
		}

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
		catch (final Exception e)
		{
			logger.warn("Failed updating the view from model", e);
			view.showError(e.getLocalizedMessage());
		}
		//
		fireAllPropertyValueChangedListeners();
	}

	public void addActionsView(final ActionsView actionsView)
	{
		Preconditions.checkNotNull(actionsView);

		if (!_actionsViews.add(actionsView))
		{
			// already added
			return;
		}

		final WindowModel model = getModelIfInitialized();
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
		final PropertyNameSet viewPropertyNamesNew = PropertyNameSet.of(propertyNames);
		if (Objects.equals(viewPropertyNames, viewPropertyNamesNew))
		{
			return;
		}

		viewPropertyNames = viewPropertyNamesNew;
		logger.trace("View subscribed to following property names: {}", propertyNames);

		updateViewFromModel(getView());
	}

	private final Set<PropertyPath> viewSettingPropertyNames = new HashSet<>();

	@Override
	public void viewPropertyChanged(final PropertyPath propertyPath, final Object value)
	{
		logger.trace("Got view property changed: {}={} ({})", propertyPath, value, value == null ? "-" : value.getClass());
		logger.trace("UI: {}", UI.getCurrent());

		viewSettingPropertyNames.add(propertyPath);
		try
		{
			logger.trace("Updating model's property");
			updateModel((model) -> model.setProperty(propertyPath, value));
		}
		finally
		{
			viewSettingPropertyNames.remove(propertyPath);
		}
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
		catch (final Exception modelException)
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

		firePropertyValueChangedListeners(event.getPropertyPath(), event.getValue());
	}

	private void modelPropertyChanged0(final WindowView view, final PropertyChangedModelEvent event)
	{
		final PropertyPath propertyPath = event.getPropertyPath();

		if (viewSettingPropertyNames.contains(propertyPath))
		{
			logger.trace("Skip updating the view because this property is currently updating from view: {}", propertyPath);
			return;
		}

		if (!propertyPath.isGridProperty() && !viewPropertyNames.contains(propertyPath.getPropertyName()))
		{
			logger.trace("Skip updating the view because this property is not interesting for view: {}", propertyPath);
		}

		final Object value = event.getValue();
		view.setProperty(propertyPath, value);
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
	public ListenableFuture<Object> viewRequestValue(final PropertyPath propertyPath)
	{
		final WindowModel model = getModel();
		final Object value = model.getPropertyOrNull(propertyPath);
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
		void valueChanged(PropertyPath propertyPath, Object value);
	}

	public void addPropertyValueChangedListener(final PropertyPath propertyPath, final PropertyValueChangedListener listener)
	{
		Preconditions.checkNotNull(propertyPath, "propertyPath not null");
		Preconditions.checkNotNull(listener, "listener not null");
		propertyValueChangedListeners.put(propertyPath, listener);

		final WindowModel model = getModelIfInitialized();
		if (model != null && model.hasProperty(propertyPath))
		{
			final Object value = model.getProperty(propertyPath);
			listener.valueChanged(propertyPath, value);
		}
	}

	private void firePropertyValueChangedListeners(final PropertyPath propertyPath, final Object value)
	{
		for (final PropertyValueChangedListener listener : propertyValueChangedListeners.get(propertyPath))
		{
			listener.valueChanged(propertyPath, value);
		}
	}

	private void fireAllPropertyValueChangedListeners()
	{
		final Set<PropertyPath> propertyPaths = propertyValueChangedListeners.keySet();
		if (propertyPaths.isEmpty())
		{
			return;
		}

		final WindowModel model = getModel();
		for (final PropertyPath propertyPath : propertyPaths)
		{
			if (!model.hasProperty(propertyPath))
			{
				continue;
			}
			final Object value = model.getProperty(propertyPath);
			for (final PropertyValueChangedListener listener : propertyValueChangedListeners.get(propertyPath))
			{
				listener.valueChanged(propertyPath, value);
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
	public ActionsList viewRequestChildActions(final String actionId)
	{
		final WindowModel model = getModel();
		return model.getChildActions(actionId);
	}

	@Subscribe
	public void modelZoomToWindowEvent(final ZoomToWindowEvent event)
	{
		final int windowId = event.getAD_Window_ID();
		final String viewNameAndParameters = WindowViewProvider.createViewNameAndParameters(windowId);
		final UI ui = UI.getCurrent();
		ui.access(() -> ui.getNavigator().navigateTo(viewNameAndParameters));
	}

	@Override
	public void viewCommandExecute(final ViewCommand command, final ViewCommandCallback callback)
	{
		updateModel(model -> {
			final ViewCommandResult result;
			try
			{
				result = model.executeCommand(command);
			}
			catch (final Exception ex)
			{
				if (callback == null)
				{
					throw Throwables.propagate(ex);
				}

				callback.onError(ex);
				return;
			}

			if (callback != null)
			{
				try
				{
					callback.onResult(command, result);
				}
				catch (final Exception e)
				{
					logger.error("Failed while setting the result {} to {} for {}", result, callback, command);
					throw Throwables.propagate(e);
				}
			}
		});

	}

}
