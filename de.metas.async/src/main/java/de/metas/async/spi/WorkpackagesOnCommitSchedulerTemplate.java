package de.metas.async.spi;

import java.util.LinkedHashSet;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.spi.TrxOnCommitCollectorFactory;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Template class for implementing algorithms which are collecting items, group them in workpackages and submit the workpackages when the transaction is committed.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 * @param <ItemType> item type to be collected.
 */
public abstract class WorkpackagesOnCommitSchedulerTemplate<ItemType>
{
	private final Class<? extends IWorkpackageProcessor> workpackageProcessorClass;
	private String trxPropertyName;

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
		// Avoid collecting the item if is not eligible
		if (!isEligibleForScheduling(item))
		{
			return;
		}

		scheduleFactory.collect(item);
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
	 * Extracts and returns the context to be used from given item.
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
	 * @param item
	 * @return model to be enqueued or <code>null</code> if not model shall be enqueued.
	 */
	protected abstract Object extractModelToEnqueueFromItem(final ItemType item);

	/** @return true if the workpackage shall be enqueued even if there are no models collected to it. */
	protected boolean isEnqueueWorkpackageWhenNoModelsEnqueued()
	{
		return false;
	}

	/** Factory which creates Scheduler instances which are collecting the items on transaction level. */
	private final TrxOnCommitCollectorFactory<Scheduler, ItemType> scheduleFactory = new TrxOnCommitCollectorFactory<Scheduler, ItemType>()
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
		protected Scheduler newCollector(final ItemType firstItem)
		{
			final Properties ctx = WorkpackagesOnCommitSchedulerTemplate.this.extractCtxFromItem(firstItem);
			return new Scheduler(ctx);
		}

		@Override
		protected void collectItem(final Scheduler collector, final ItemType item)
		{
			collector.addItem(item);
		}

		@Override
		protected void processCollector(final Scheduler collector)
		{
			collector.createAndSubmitWorkpackage();
		}
	};

	/**
	 * Scheduler class responsible to collecting items and enqueuing a workpackage which will process the collected items.
	 * 
	 * @author metas-dev <dev@metas-fresh.com>
	 */
	private final class Scheduler
	{
		private final Properties ctx;
		private final LinkedHashSet<Object> models = new LinkedHashSet<>();

		private boolean processed = false;

		private Scheduler(final Properties ctx)
		{
			super();
			this.ctx = ctx;
		}

		private final void assertNotProcessed()
		{
			Check.assume(!processed, "Not processed: {0}", this);
		}

		private final void markAsProcessed()
		{
			assertNotProcessed();
			this.processed = true;
		}

		public Scheduler addItem(final ItemType item)
		{
			assertNotProcessed();

			final Object model = WorkpackagesOnCommitSchedulerTemplate.this.extractModelToEnqueueFromItem(item);
			if (model != null)
			{
				models.add(model);
			}
			return this;
		}

		public void createAndSubmitWorkpackage()
		{
			markAsProcessed();

			if (models.isEmpty() && !isEnqueueWorkpackageWhenNoModelsEnqueued())
			{
				return;
			}

			Services.get(IWorkPackageQueueFactory.class)
					.getQueueForEnqueuing(ctx, workpackageProcessorClass)
					.newBlock()
					.setContext(ctx)
					.newWorkpackage()
					.addElements(models)
					.build();
		}
	}
}
