package de.metas.document.archive.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
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

import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.async.spi.impl.DocOutboundWorkpackageProcessor;
import de.metas.document.archive.async.spi.impl.ProcessPrintingQueueWorkpackageProcessor;
import de.metas.document.archive.config.DocOutboundConfigService;
import de.metas.document.archive.spi.IDocOutboundProducer;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Properties;

/**
 * {@link IDocOutboundProducer} base implementation.
 * <p>
 * Mainly all business logic is implemented but the life-cycle management methods are left unimplemented (see {@link #init(IDocOutboundProducerService)}, {@link #destroy(IDocOutboundProducerService)}
 * ).
 *
 * @author tsa
 *
 */
public abstract class AbstractDocOutboundProducer implements IDocOutboundProducer
{
	protected final transient Logger logger = LogManager.getLogger(getClass());

	private final transient IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final transient IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final transient IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final transient IWorkPackageQueueFactory workPackageQueueFactory = Services.get(IWorkPackageQueueFactory.class);
	private final transient DocOutboundConfigService docOutboundConfigService = SpringContextHolder.instance.getBean(DocOutboundConfigService.class);

	@Getter private final AdTableId tableId;
	@Getter private final String tableName;
	@Getter private final boolean isDocument;

	@Override
	public abstract void init(IDocOutboundProducerService producerService);

	@Override
	public abstract void destroy(IDocOutboundProducerService producerService);

	public AbstractDocOutboundProducer(@NonNull final AdTableId tableId)
	{
		this.tableId = tableId;
		this.tableName = adTableDAO.retrieveTableName(tableId);
		this.isDocument = documentBL.isDocumentTable(tableName);
	}

	@Override
	public String toString()
	{
		return "DocOutboundProducer ["
				+ "tableName=" + tableName
				+ ", isDocument=" + isDocument
				+ "]";
	}



	@Override
	public boolean accept(@Nullable final Object model)
	{
		if (model == null)
		{
			return false;
		}

		// Check tableName match
		final String modelTableName = InterfaceWrapperHelper.getModelTableName(model);
		if (!tableName.equals(modelTableName))
		{
			return false;
		}

		if (isDocument)
		{
			return acceptDocument(model);
		}

		return true;
	}

	protected boolean acceptDocument(final Object model)
	{
		return docOutboundConfigService.retrieveConfigForModel(model) != null;
	}

	@Override
	public void createDocOutbound(@NonNull final Object model)
	{
		enqueueModelForWorkpackageProcessor(model, DocOutboundWorkpackageProcessor.class);
	}

	@Override
	public void voidDocOutbound(@NonNull final Object model)
	{
		enqueueModelForWorkpackageProcessor(model, ProcessPrintingQueueWorkpackageProcessor.class);
	}

	private void enqueueModelForWorkpackageProcessor(
			@NonNull final Object model,
			@NonNull final Class<? extends IWorkpackageProcessor> packageProcessorClass)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(model);

		final I_C_Async_Batch asyncBatch = asyncBatchBL.getAsyncBatchId(model)
				.map(asyncBatchBL::getAsyncBatchById)
				.orElse(null);

			workPackageQueueFactory
					.getQueueForEnqueuing(ctx, packageProcessorClass)
					.newWorkPackage()
					.bindToThreadInheritedTrx()
					.addElement(model)
					.setC_Async_Batch(asyncBatch)
					.setUserInChargeId(Env.getLoggedUserIdIfExists().orElse(null))
					.buildAndEnqueue();
	}
}
