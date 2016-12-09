package de.metas.dlm.partitioner;

import java.util.Date;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ITableRecordReference;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest.OnNotDLMTable;
import de.metas.dlm.partitioner.async.DLMPartitionerWorkpackageProcessor;
import de.metas.dlm.partitioner.config.PartitionConfig;

/*
 * #%L
 * metasfresh-dlm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Factory for requests that can be passed as parameters to the {@link IPartitionerService} and {@link DLMPartitionerWorkpackageProcessor}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("rawtypes")
public class PartitionRequestFactory
{
	public static PartitionerRequestBuilder builder()
	{
		return new PartitionerRequestBuilder(null);
	}

	/**
	 * Create a new builder, using the given <code>template</code> to initialize its values.
	 *
	 * @param template may be {@link CreatePartitionAsyncRequest}, but the "async" properties are discarded.
	 * @return
	 */
	public static PartitionerRequestBuilder builder(final CreatePartitionRequest template)
	{
		return new PartitionerRequestBuilder(template);
	}

	public static AsyncPartitionerRequestBuilder asyncBuilder()
	{
		return new AsyncPartitionerRequestBuilder(null);
	}

	/**
	 * Create a new async-builder, using the given <code>template</code> to initialize its values.
	 *
	 * @param template can be either {@link CreatePartitionRequest} or {@link CreatePartitionAsyncRequest}.
	 * @return
	 */
	public static AsyncPartitionerRequestBuilder asyncBuilder(final CreatePartitionRequest template)
	{
		return new AsyncPartitionerRequestBuilder(template);
	}

	/**
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 * @param <T> actual type of this builder. Used such that if this builder is an AsyncPartitionerRequestBuilder, then e.g.
	 *            {@link #setConfig(PartitionConfig)} shall return not {@link PartitionerRequestBuilder}, but {@link AsyncPartitionerRequestBuilder}
	 */
	@SuppressWarnings("unchecked")
	public static class PartitionerRequestBuilder<T extends PartitionerRequestBuilder>
	{
		private PartitionConfig config;
		private boolean oldestFirst = true;
		private OnNotDLMTable onNotDLMTable = OnNotDLMTable.FAIL;
		private ITableRecordReference recordToAttach;
		private I_DLM_Partition partitionToComplete;

		private PartitionerRequestBuilder(final CreatePartitionRequest template)
		{
			if (template != null)
			{
				onNotDLMTable = template.onNotDLMTable;
				config = template.getConfig();
				oldestFirst = template.isOldestFirst();
				recordToAttach = template.getRecordToAttach();
			}
		}

		public CreatePartitionRequest build()
		{
			return new CreatePartitionRequest(config, oldestFirst, recordToAttach, partitionToComplete, onNotDLMTable);
		}

		public T setConfig(final PartitionConfig config)
		{
			this.config = config;
			return (T)this;
		}

		/**
		 * May be <code>null</code>. If set, then {@link IPartitionerService#createPartition(CreatePartitionRequest)} does not iterate all lines of the given config,
		 * but explicitly explores the references of the given {@link ITableRecordReference}'s record.
		 *
		 * @return
		 */
		public T setRecordToAttach(final ITableRecordReference recordToAttach)
		{
			this.recordToAttach = recordToAttach;
			return (T)this;
		}

		public T setPartitionToComplete(final I_DLM_Partition partitionToComplete)
		{
			this.partitionToComplete = partitionToComplete;
			return (T)this;
		}

		/**
		 * When {@link IPartitionerService#createPartition(CreatePartitionRequest)} iterates all lines and loads one record per line,
		 * then this flag decides whether that record shall have a low or a high ID.
		 * If omitted, then the default is <code>true</code>.
		 * <p>
		 * Note that this setting is ignored in the request instance, if {@link #setRecordToAttach(ITableRecordReference)} was called with a not <code>null</code> value.
		 *
		 * @param oldest
		 * @return
		 */
		public T setOldestFirst(final boolean oldest)
		{
			oldestFirst = oldest;
			return (T)this;
		}

		/**
		 * The default if omitted is {@link OnNotDLMTable#FAIL}
		 *
		 * @param onNotDLMTable
		 * @return
		 */
		public T setOnNotDLMTable(final OnNotDLMTable onNotDLMTable)
		{
			this.onNotDLMTable = onNotDLMTable;
			return (T)this;
		}
	}

	public static class AsyncPartitionerRequestBuilder extends PartitionerRequestBuilder<AsyncPartitionerRequestBuilder>
	{
		private int count;

		private Date dontReEnqueueAfter;

		private AsyncPartitionerRequestBuilder(final CreatePartitionRequest template)
		{
			super(template);

			if (template != null && template instanceof CreatePartitionAsyncRequest)
			{
				final CreatePartitionAsyncRequest asyncTemplate = (CreatePartitionAsyncRequest)template;
				setDontReEnqueueAfter(asyncTemplate.getDontReEnqueueAfter());
				setCount(asyncTemplate.getCount());
			}
		}

		/**
		 * See {@link CreatePartitionAsyncRequest#getCount()}.
		 *
		 * @param count
		 * @return
		 */
		public AsyncPartitionerRequestBuilder setCount(final int count)
		{
			this.count = count;
			return this;
		}

		/**
		 * See {@link CreatePartitionAsyncRequest#getDontReEnqueueAfter()}.
		 *
		 * @param dontReEnqueueAfter
		 * @return
		 */
		public AsyncPartitionerRequestBuilder setDontReEnqueueAfter(final Date dontReEnqueueAfter)
		{
			this.dontReEnqueueAfter = dontReEnqueueAfter;
			return this;
		}

		@Override
		public CreatePartitionAsyncRequest build()
		{
			return new CreatePartitionAsyncRequest(super.build(), count, dontReEnqueueAfter);
		}
	}

	/**
	 * Passed to {@link IPartitionerService#createPartition(CreatePartitionRequest)} to create another partition.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@Immutable
	public static class CreatePartitionRequest
	{
		/**
		 * What to do if the partitioner encounters a record from a table that was not yet added to DLM.
		 * <p>
		 * Note: <code>IGNORE</code> is not an option, because if we don't handle the record, there could be FK-references between records that have different DLM levels
		 *
		 * @author metas-dev <dev@metasfresh.com>
		 *
		 */
		public enum OnNotDLMTable
		{
			/**
			 * Invoke {@link IDLMService#addTableToDLM(de.metas.dlm.model.I_AD_Table)}.
			 * <p>
			 * <b>IMPORTANT:</b> never to this unattended, because it includes DDL changes.
			 * It also creates indices which might take some time (complete scan of the table) and during that time, even SELECTs are blocked.
			 */
			ADD_TO_DLM,

			/**
			 * Abort by throwing a {@link de.metas.dlm.exception.TableNotAddedToDLMException} and let a user handle the case first.
			 */
			FAIL
		}

		private final PartitionConfig config;

		private final boolean oldestFirst;

		private final OnNotDLMTable onNotDLMTable;

		private final ITableRecordReference recordToAttach;

		private final I_DLM_Partition partitionToComplete;

		private CreatePartitionRequest(
				final PartitionConfig config,
				final boolean oldestFirst,
				final ITableRecordReference recordToAttach,
				final I_DLM_Partition partitionToComplete,
				final OnNotDLMTable onNotDLMTable)
		{
			Check.assumeNotNull(config, "Param 'config' is not null");

			this.config = config;
			this.oldestFirst = oldestFirst;
			this.recordToAttach = recordToAttach;
			this.partitionToComplete = partitionToComplete;
			this.onNotDLMTable = onNotDLMTable;
		}

		public PartitionConfig getConfig()
		{
			return config;
		}

		public boolean isOldestFirst()
		{
			return oldestFirst;
		}

		public OnNotDLMTable getOnNotDLMTable()
		{
			return onNotDLMTable;
		}

		/**
		 * See {@link PartitionerRequestBuilder#setRecordToAttach(ITableRecordReference)}
		 *
		 * @return
		 */
		public ITableRecordReference getRecordToAttach()
		{
			return recordToAttach;
		}

		public I_DLM_Partition getPartitionToComplete()
		{
			return partitionToComplete;
		}

		@Override
		public String toString()
		{
			return "CreatePartitionRequest [onNotDLMTable=" + onNotDLMTable + ", oldestFirst=" + oldestFirst + ", partitionToComplete=" + partitionToComplete + ", recordToAttach=" + recordToAttach + ", config=" + config + "]";
		}
	}

	/**
	 * Passed to {@link DLMPartitionerWorkpackageProcessor#schedule(CreatePartitionAsyncRequest, int)} to create a workpackage to invoke {@link IPartitionerService#createPartition(CreatePartitionRequest)} asynchronously.
	 * The additional members of this class tell the work package processor if it shall enqueue another workpackage after it did its job.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@Immutable
	public static class CreatePartitionAsyncRequest extends CreatePartitionRequest
	{
		private final int count;

		private final Date dontReEnqueueAfter;

		private final CreatePartitionRequest partitionRequest;

		private CreatePartitionAsyncRequest(
				final CreatePartitionRequest partitionRequest,
				final int count,
				final Date dontReEnqueueAfter)
		{
			super(partitionRequest.getConfig(),
					partitionRequest.isOldestFirst(),
					partitionRequest.getRecordToAttach(),
					partitionRequest.getPartitionToComplete(),
					partitionRequest.getOnNotDLMTable());

			this.partitionRequest = partitionRequest; // we use it for the toString() method
			this.count = count;
			this.dontReEnqueueAfter = dontReEnqueueAfter;
		}

		/**
		 * Only enqueue the given number of work packages (one after each other).
		 *
		 * @return
		 */
		public int getCount()
		{
			return count;
		}

		/**
		 * Don't enqueue another workpackage after the given time has passed. One intended usage scenario is to start partitioning in the evening and stop in the morning.
		 *
		 * @return
		 */
		public Date getDontReEnqueueAfter()
		{
			return dontReEnqueueAfter;
		}

		@Override
		public String toString()
		{
			return "CreatePartitionAsyncRequest [count=" + count + ", dontReEnqueueAfter=" + dontReEnqueueAfter + ", partitionRequest=" + partitionRequest + "]";
		}
	}
}
