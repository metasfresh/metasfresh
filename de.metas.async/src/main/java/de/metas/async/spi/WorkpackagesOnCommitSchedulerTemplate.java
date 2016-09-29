package de.metas.async.spi;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.spi.TrxOnCommitCollectorFactory;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.async.api.IWorkPackageBlockBuilder;
import de.metas.async.api.IWorkPackageBuilder;
import de.metas.async.processor.IWorkPackageQueueFactory;

/*
 * #%L
 * de.metas.async
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

/**
 * Template class for implementing algorithms which are collecting items, group them in workpackages and submit the workpackages when the transaction is committed.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ItemType> item type to be collected.
 */
public abstract class WorkpackagesOnCommitSchedulerTemplate<ItemType>
{
	/** Convenient method to create an instance which is scheduling a workpackage on transaction commit, but which is NOT collecting the enqueued models */
	public static final <ModelType> WorkpackagesOnCommitSchedulerTemplate<ModelType> newModelSchedulerNoCollect(
			final Class<? extends IWorkpackageProcessor> workpackageProcessorClass,
			final Class<ModelType> modelType)
	{
		final boolean collectModels = false;
		return new ModelsScheduler<ModelType>(workpackageProcessorClass, modelType, collectModels);
	}

	/** Convenient method to create an instance which is scheduling a workpackage on transaction commit based on a given {@link IContextAware} */
	public static final WorkpackagesOnCommitSchedulerTemplate<IContextAware> newContextAwareSchedulerNoCollect(final Class<? extends IWorkpackageProcessor> workpackageProcessorClass)
	{
		final boolean collectModels = false;
		return new ModelsScheduler<>(workpackageProcessorClass, IContextAware.class, collectModels);
	}

	/** Convenient method to create an instance which IS COLLECTING models and schedules a workpackage on transaction commit. */
	public static final <ModelType> WorkpackagesOnCommitSchedulerTemplate<ModelType> newModelScheduler(
			final Class<? extends IWorkpackageProcessor> workpackageProcessorClass,
			final Class<ModelType> modelType)
	{
		final boolean collectModels = true;
		return new ModelsScheduler<ModelType>(workpackageProcessorClass, modelType, collectModels);
	}

	private final Class<? extends IWorkpackageProcessor> workpackageProcessorClass;
	private final String trxPropertyName;
	private final AtomicBoolean createOneWorkpackagePerModel = new AtomicBoolean(false);

	/**
	 *
	 * @param workpackageProcessorClass workpackage processor class to be used when workpackages are enqueued.
	 */
	public WorkpackagesOnCommitSchedulerTemplate(final Class<? extends IWorkpackageProcessor> workpackageProcessorClass)
	{
		super();

		Check.assumeNotNull(workpackageProcessorClass, "workpackageProcessorClass not null");
		this.workpackageProcessorClass = workpackageProcessorClass;

		this.trxPropertyName = getClass().getSimpleName() + "-" + workpackageProcessorClass.getName();
	}

	/**
	 * Schedule given item to be enqueued in a transaction level workpackage which will be submitted when the transaction is committed.
	 *
	 * The transaction is extracted from item, using {@link #extractTrxNameFromItem(Object)}.
	 *
	 * If item has no transaction, a workpackage with given item will be automatically created and enqueued to be processed.
	 *
	 * @param item
	 */
	public final void schedule(final ItemType item)
	{
		Check.assumeNotNull(item, "Param 'item' is not null");
		// Avoid collecting the item if is not eligible
		if (!isEligibleForScheduling(item))
		{
			return;
		}
		scheduleFactory.collect(item);
	}

	public WorkpackagesOnCommitSchedulerTemplate<ItemType> setCreateOneWorkpackagePerModel(final boolean createOneWorkpackagePerModel)
	{
		this.createOneWorkpackagePerModel.set(createOneWorkpackagePerModel);
		return this;
	}

	protected final Class<? extends IWorkpackageProcessor> getWorkpackageProcessorClass()
	{
		return workpackageProcessorClass;
	}

	/**
	 * Checks if given item is eligible for scheduling.
	 *
	 * To be implemented by extending classes in order to avoid some items to be scheduled. By default this method accepts any item.
	 *
	 * @return true if given item shall be scheduled
	 */
	protected boolean isEligibleForScheduling(final ItemType item)
	{
		return true;
	}

	/**
	 * Returns a map of parameters to be added to the workpackage for the given item. This implementation returns an empty map.
	 * <p>
	 * <b>Important:</b> this method is called for each item and the parameter names for different items need have different names! Otherwise, there will be an exception.
	 *
	 * @param item
	 * @return an empty map. Overwrite as required
	 * @task https://github.com/metasfresh/metasfresh/issues/409
	 */
	protected Map<String, Object> extractParametersFromItem(final ItemType item)
	{
		return Collections.emptyMap();
	}

	/**
	 * Extract and return the context to be used from given item.
	 *
	 * The context is used to create the internal {@link IWorkPackageBuilder}.
	 *
	 * @param item
	 * @return ctx; shall never be <code>null</code>
	 */
	protected abstract Properties extractCtxFromItem(final ItemType item);

	/**
	 * Extracts and returns the trxName to be used from given item
	 *
	 * @param item
	 * @return transaction name or {@link ITrx#TRXNAME_None}
	 */
	protected abstract String extractTrxNameFromItem(final ItemType item);

	/**
	 * Extracts the model to be enqueued to internal {@link IWorkPackageBuilder}.
	 *
	 * @param collector
	 * @param item
	 * @return model to be enqueued or <code>null</code> if no model shall be enqueued.
	 */
	protected abstract Object extractModelToEnqueueFromItem(final Collector collector, final ItemType item);

	/**
	 * @return true if the workpackage shall be enqueued even if there are no models collected to it.
	 */
	protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
	{
		return false;
	}

	/** Factory which creates Scheduler instances which are collecting the items on transaction level. */
	private final TrxOnCommitCollectorFactory<Collector, ItemType> scheduleFactory = new TrxOnCommitCollectorFactory<Collector, ItemType>()
	{
		@Override
		protected String getTrxProperyName()
		{
			return WorkpackagesOnCommitSchedulerTemplate.this.trxPropertyName;
		};

		@Override
		protected String extractTrxNameFromItem(final ItemType item)
		{
			return WorkpackagesOnCommitSchedulerTemplate.this.extractTrxNameFromItem(item);
		}

		@Override
		protected Collector newCollector(final ItemType firstItem)
		{
			final Properties ctx = WorkpackagesOnCommitSchedulerTemplate.this.extractCtxFromItem(firstItem);
			final Collector collector = new Collector(ctx);
			collector.setCreateOneWorkpackagePerModel(WorkpackagesOnCommitSchedulerTemplate.this.createOneWorkpackagePerModel.get());
			return collector;
		}

		@Override
		protected void collectItem(final Collector collector, final ItemType item)
		{
			collector.addItem(item);
			final Map<String, Object> params = extractParametersFromItem(item);
			if (params == null)
			{
				return; // guard against NPE
			}

			for (final Entry<String, Object> param : params.entrySet())
			{
				collector.setParameter(param.getKey(), param.getValue());
			}
		}

		@Override
		protected void processCollector(final Collector collector)
		{
			collector.createAndSubmitWorkpackage();
		}
	};

	/**
	 * Collector class responsible to collecting items and enqueuing a workpackage which will process the collected items.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 */
	protected final class Collector
	{
		private final Properties ctx;
		private final LinkedHashSet<Object> models = new LinkedHashSet<>();
		private final Map<String, Object> parameters = new LinkedHashMap<>();
		private boolean createOneWorkpackagePerModel = false;

		private boolean processed = false;

		private Collector(final Properties ctx)
		{
			super();
			this.ctx = ctx;
		}

		private final void assertNotProcessed()
		{
			Check.assume(!processed, "Not processed: {}", this);
		}

		private final void markAsProcessed()
		{
			assertNotProcessed();
			this.processed = true;
		}

		public final Collector addItem(final ItemType item)
		{
			assertNotProcessed();

			final Object model = WorkpackagesOnCommitSchedulerTemplate.this.extractModelToEnqueueFromItem(this, item);
			if (model != null)
			{
				models.add(model);
			}
			return this;
		}

		public final Collector setParameter(final String parameterName, final Object parameterValue)
		{
			assertNotProcessed();
			Check.assumeNotEmpty(parameterName, "parameterName not empty");

			final Object oldValue = parameters.put(parameterName, parameterValue);
			Check.errorIf(oldValue != null
					// gh #409: it's ok if an equal value is added more than once. This for example happens if an inout is reversed, because there the counter doc's model interceptor is fired twice within the same transaction.
					&& !oldValue.equals(parameterValue),
					"Illegal attempt to overwrite parameter name={} with newValue={}; it was already set to oldValue={}",
					parameterName, parameterValue, oldValue);

			return this;
		}

		public final <ParameterType> ParameterType getParameter(final String parameterName)
		{
			@SuppressWarnings("unchecked")
			final ParameterType parameterValue = (ParameterType)parameters.get(parameterName);
			return parameterValue;
		}

		public final Collector setCreateOneWorkpackagePerModel(final boolean createOneWorkpackagePerModel)
		{
			assertNotProcessed();
			this.createOneWorkpackagePerModel = createOneWorkpackagePerModel;
			return this;
		}

		private final boolean isCreateOneWorkpackagePerModel()
		{
			return createOneWorkpackagePerModel;
		}

		public final void createAndSubmitWorkpackage()
		{
			markAsProcessed();

			if (models.isEmpty() && !isEnqueueWorkpackageWhenNoModelsEnqueued())
			{
				return;
			}

			final IWorkPackageBlockBuilder blockBuilder = Services.get(IWorkPackageQueueFactory.class)
					.getQueueForEnqueuing(ctx, workpackageProcessorClass)
					.newBlock()
					.setContext(ctx);

			if (isCreateOneWorkpackagePerModel())
			{
				for (final Object model : models)
				{
					createAndSubmitWorkpackage(blockBuilder, ImmutableList.of(model));
				}
			}
			else
			{
				createAndSubmitWorkpackage(blockBuilder, models);
			}
		}

		private final void createAndSubmitWorkpackage(final IWorkPackageBlockBuilder blockBuilder, final Collection<Object> modelsToEnqueue)
		{
			blockBuilder
					.newWorkpackage()
					//
					// Workpackage Parameters
					.parameters()
					.setParameters(parameters)
					.end()
					//
					// Workpackage elements
					.addElements(modelsToEnqueue)
					//
					// Build & enqueue
					.build();
		}
	}

	private static final class ModelsScheduler<ModelType> extends WorkpackagesOnCommitSchedulerTemplate<ModelType>
	{
		private final Class<ModelType> modelType;
		private final boolean collectModels;

		public ModelsScheduler(final Class<? extends IWorkpackageProcessor> workpackageProcessorClass, final Class<ModelType> modelType, final boolean collectModels)
		{
			super(workpackageProcessorClass);
			this.modelType = modelType;
			this.collectModels = collectModels;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("collectModels", collectModels)
					.add("workpackageProcessorClass", getWorkpackageProcessorClass())
					.add("modelType", modelType)
					.toString();
		}

		@Override
		protected Properties extractCtxFromItem(final ModelType item)
		{
			return InterfaceWrapperHelper.getCtx(item);
		}

		@Override
		protected String extractTrxNameFromItem(final ModelType item)
		{
			return InterfaceWrapperHelper.getTrxName(item);
		}

		@Override
		protected Object extractModelToEnqueueFromItem(final Collector collector, final ModelType item)
		{
			return collectModels ? item : null;
		}

		@Override
		protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
		{
			return !collectModels;
		}
	}
}
