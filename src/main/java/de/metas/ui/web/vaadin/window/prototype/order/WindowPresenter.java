package de.metas.ui.web.vaadin.window.prototype.order;

import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.Subscribe;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.ui.UI;

import de.metas.logging.LogManager;
import de.metas.ui.web.vaadin.window.prototype.order.WindowConstants.OnChangesFound;
import de.metas.ui.web.vaadin.window.prototype.order.model.WindowModel;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.ConfirmDiscardChangesModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.GridPropertyChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.GridRowAddedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.model.event.PropertyChangedModelEvent;
import de.metas.ui.web.vaadin.window.prototype.order.view.WindowView;
import de.metas.ui.web.vaadin.window.prototype.order.view.WindowViewImpl;
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

	private final WindowModel model;
	private final WindowView view;

	/** {@link PropertyName}s which are interesting for view and which shall be propagated to the view */
	private Set<PropertyName> viewPropertyNames = ImmutableSet.of();

	public WindowPresenter()
	{
		super();

		// FIXME: setting up the context
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_User_ID, 100);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, 1000000);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_DE");

		this.model = HARDCODED_Order.getSingletonWindowModel();
		final PropertyDescriptor rootPropertyDescriptor = model.getRootPropertyDescriptor();
		model.getEventBus().register(this);

		this.view = new WindowViewImpl(rootPropertyDescriptor);
		this.view.setListener(this);

		updateViewFromModel();
	}

	public void dispose()
	{
		model.getEventBus().unregister(this);
	}

	public WindowView getView()
	{
		return view;
	}

	private void updateViewFromModel()
	{
		logger.debug("Updating {} from {}", view, model);

		// view.setTitle(model.getTitle()); // not needed, will come with all properties
		view.setPreviousRecordEnabled(model.hasPreviousRecord());
		view.setNextRecordEnabled(model.hasNextRecord());

		//
		// Properties
		try
		{
			view.setProperties(model.getPropertiesAsMap(viewPropertyNames));
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
		this.viewPropertyNames = ImmutableSet.copyOf(propertyNames);
		
		logger.trace("View subscribed to following property names: {}", propertyNames);
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

	private final void updateView(final Runnable runnable)
	{
		final UI viewUI = view.getUI();
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
				view.confirmDiscardChanges();
			}
		});
	}

	@Override
	public void viewNextRecord(final OnChangesFound onChangesFound)
	{
		view.commitChanges();

		updateModel(new Runnable()
		{
			@Override
			public void run()
			{
				model.nextRecord(onChangesFound);
			}
		});
	}

	@Override
	public void viewPreviousRecord(final OnChangesFound onChangesFound)
	{
		view.commitChanges();

		updateModel(new Runnable()
		{
			@Override
			public void run()
			{
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
				model.reloadRecord();
			}
		});
	}

	@Override
	public ListenableFuture<Object> viewRequestValue(final PropertyName propertyName)
	{
		final Object value = model.getProperty(propertyName);
		return Futures.immediateFuture(value);
	}

	@Override
	public ListenableFuture<Object> viewRequestGridValue(PropertyName gridPropertyName, Object rowId, PropertyName propertyName)
	{
		final Object value = model.getGridProperty(gridPropertyName, rowId, propertyName);
		return Futures.immediateFuture(value);
	}
}
