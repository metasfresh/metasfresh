package de.metas.dlm.model.interceptor;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Column;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IColumnBL;
import de.metas.dlm.IDLMService;
import de.metas.dlm.model.I_DLM_Partition_Workqueue;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionAsyncRequest;
import de.metas.dlm.partitioner.async.DLMPartitionerWorkpackageProcessor;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;
import de.metas.dlm.partitioner.config.TableReferenceDescriptor;

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
 * This interceptor is responsible for reacting on DLM-related events by identifying the related records and scheduling them with {@link DLMPartitionerWorkpackageProcessor}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PartitionerInterceptor extends AbstractModelInterceptor
{
	public static final PartitionerInterceptor INSTANCE = new PartitionerInterceptor();

	private PartitionerInterceptor()
	{
	}

	/**
	 * Registers {@link AddToPartitionInterceptor#INSTANCE} as listener for every table that has a {@link PartitionerConfigLine} in the default configuration.
	 * Also registers {@link CheckTableRecordReferenceInterceptor#INSTANCE} as listener for every table that has an <code>AD_Table-ID</code> and <code>Record_ID</code> column.
	 *
	 */
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final IDLMService dlmService = Services.get(IDLMService.class);

		final PartitionConfig config = dlmService.loadDefaultPartitionConfig();

		config.getLines().forEach(line -> {
			engine.addModelChange(line.getTableName(), AddToPartitionInterceptor.INSTANCE);
		});

		// get the tables that have a "Record_ID" and AD_table_ID column and register CheckTableRecordReferenceInterceptor for it
		// note that AD_Issue has just Record_ID and shall therefore not be registered
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IColumnBL columnBL = Services.get(IColumnBL.class);

		queryBL.createQueryBuilder(I_AD_Column.class, client)
				.addOnlyActiveRecordsFilter()
				.addEndsWithQueryFilter(I_AD_Column.COLUMNNAME_ColumnName, ITableRecordReference.COLUMNNAME_Record_ID)
				.orderBy().addColumn(I_AD_Column.COLUMN_AD_Column_ID).endOrderBy()
				.create()
				.list(I_AD_Column.class)
				.forEach(column -> {

					final String tableName = column.getAD_Table().getTableName();
					if (!I_DLM_Partition_Workqueue.Table_Name.equals(tableName)) // this table is part of the DLM engine
					{
						final Optional<String> tableColumnName = columnBL.getTableColumnName(tableName, column.getColumnName());
						if (tableColumnName.isPresent()) // not the case for e.g. AD_Issue
						{
							engine.addModelChange(tableName, CheckTableRecordReferenceInterceptor.INSTANCE);
						}
					}
				});
	}

	public static class AddToPartitionInterceptor extends AbstractModelInterceptor
	{
		public static AddToPartitionInterceptor INSTANCE = new AddToPartitionInterceptor();

		private AddToPartitionInterceptor()
		{
		}

		@Override
		protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
		{
			// nothing to do
		}

		@Override
		public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
		{
			final boolean afterNewOrChange = changeType.isNewOrChange() && changeType.isAfter();
			if (!afterNewOrChange)
			{
				// Note that currently we are not interested in splitting a partition if the only linking record gets deleted.
				return; // Nothing to do.
			}

			final IDLMService dlmService = Services.get(IDLMService.class);

			final PartitionConfig config = dlmService.loadDefaultPartitionConfig();

			final String tableName = InterfaceWrapperHelper.getModelTableName(model);
			final Optional<PartitionerConfigLine> configLine = config.getLine(tableName);
			if (!configLine.isPresent())
			{
				// The line was deleted since we were registered for "tableName".
				// We can expect the DLM_Partition_Config_Line interceptor to unregister us shortly
				return;
			}

			final Set<String> columnNames = configLine.get()
					.getReferences().stream()
					.map(ref -> ref.getReferencingColumnName())
					.collect(Collectors.toSet());
			if (!InterfaceWrapperHelper.isValueChanged(model, columnNames))
			{
				// "model" was changed, but not any of the columns we are interested in
				return;
			}

			final CreatePartitionAsyncRequest request = PartitionRequestFactory.asyncBuilder()
					.setConfig(config)
					.setRecordToAttach(ITableRecordReference.FromModelConverter.convert(model))
					.build();
			DLMPartitionerWorkpackageProcessor.schedule(request, -1);
		}
	}

	public static class CheckTableRecordReferenceInterceptor extends AbstractModelInterceptor
	{
		public static CheckTableRecordReferenceInterceptor INSTANCE = new CheckTableRecordReferenceInterceptor();

		private CheckTableRecordReferenceInterceptor()
		{
		}

		@Override
		protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
		{
			// nothing to do
		}

		@Override
		public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
		{
			final boolean afterNewOrChange = changeType.isNewOrChange() && changeType.isAfter();
			if (!afterNewOrChange)
			{
				// Note that currently we are not interested in splitting a partition if the only linking record gets deleted.
				return; // Nothing to do.
			}

			final ImmutableSet<String> columnNames = ImmutableSet.of(ITableRecordReference.COLUMNNAME_AD_Table_ID, ITableRecordReference.COLUMNNAME_Record_ID);
			if (!InterfaceWrapperHelper.isValueChanged(model, columnNames))
			{
				return;
			}

			final IPartitionerService partitionerService = Services.get(IPartitionerService.class);
			final IDLMService dlmService = Services.get(IDLMService.class);

			final PartitionConfig config = dlmService.loadDefaultPartitionConfig();

			final ITableRecordReference recordReference = ITableRecordReference.FromReferencedModelConverter.convert(model);
			final Optional<PartitionerConfigLine> referencedLine = config.getLine(recordReference.getTableName());
			if (!referencedLine.isPresent())
			{
				return; // the table we are referencing is not part of DLM; nothing to do
			}

			final String modelTableName = InterfaceWrapperHelper.getModelTableName(model);

			final TableReferenceDescriptor descriptor = TableReferenceDescriptor.of(modelTableName, ITableRecordReference.COLUMNNAME_Record_ID, recordReference.getTableName());

			final PartitionConfig augmentedConfig = partitionerService.augmentPartitionerConfig(config, Collections.singletonList(descriptor));
			if (!augmentedConfig.isChanged())
			{
				return; // we are done
			}

			// now that the augmented config is stored, further changes will be handeled by AddToPartitionInterceptor.
			dlmService.storePartitionConfig(augmentedConfig);

			// however, for the current 'model', we need to enqueue it ourselves
			final CreatePartitionAsyncRequest request = PartitionRequestFactory.asyncBuilder()
					.setConfig(config)
					.setRecordToAttach(ITableRecordReference.FromModelConverter.convert(model))
					.build();
			DLMPartitionerWorkpackageProcessor.schedule(request, -1);
		}
	}
}
