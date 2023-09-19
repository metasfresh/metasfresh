package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.archive.api.IDocOutboundProducerService;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.printing.HardwarePrinterId;
import de.metas.printing.HardwareTrayId;
import de.metas.printing.PrintOutputFacade;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.model.I_C_Doc_Outbound_Config;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.I_C_Printing_Queue_Recipient;
import de.metas.printing.spi.IPrintingQueueHandler;
import de.metas.printing.spi.impl.C_Printing_Queue_RecipientHandler;
import de.metas.printing.spi.impl.CompositePrintingQueueHandler;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.report.PrintCopies;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.save;

public class PrintingQueueBL implements IPrintingQueueBL
{
	private final static transient Logger logger = LogManager.getLogger(PrintingQueueBL.class);

	/**
	 * gh #1081: set up our composite handler to always apply {@link C_Printing_Queue_RecipientHandler} after the other handlers
	 */
	private final CompositePrintingQueueHandler printingQueueHandler = new CompositePrintingQueueHandler(C_Printing_Queue_RecipientHandler.INSTANCE);
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

	@Nullable
	@Override
	public I_C_Printing_Queue enqueue(final org.compiere.model.I_AD_Archive archiveRecord)
	{
		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(archiveRecord))
		{
			final Properties localCtx = Env.deriveCtx(InterfaceWrapperHelper.getCtx(archiveRecord));
			Env.setContext(localCtx, Env.CTXNAME_AD_Client_ID, archiveRecord.getAD_Client_ID());
			Env.setContext(localCtx, Env.CTXNAME_AD_Org_ID, archiveRecord.getAD_Org_ID());

			final String trxName = InterfaceWrapperHelper.getTrxName(archiveRecord);

			final I_C_Printing_Queue item = InterfaceWrapperHelper.create(localCtx, I_C_Printing_Queue.class, trxName);
			item.setAD_Archive(archiveRecord);
			item.setIsActive(true);
			item.setProcessed(false);

			// 03870
			item.setAD_Org_ID(archiveRecord.getAD_Org_ID());
			item.setC_Async_Batch_ID(archiveRecord.getC_Async_Batch_ID());

			// 03829: set the values for new columns
			item.setAD_User_ID(Env.getAD_User_ID(localCtx)); // printingQueueHandler might/should override this
			item.setAD_Role_ID(Env.getAD_Role_ID(localCtx));
			item.setAD_Process_ID(archiveRecord.getAD_Process_ID());

			item.setIsPrintoutForOtherUser(false); // task 09028: this the default, but the printingQueueHandler can override it.

			item.setCopies(1); // can be changed by printingQueueHandlers and a value stored in "COPIES_PER_ARCHIVE"

			printingQueueHandler.afterEnqueueBeforeSave(item, InterfaceWrapperHelper.create(archiveRecord, I_AD_Archive.class));
			// If queue item was deactivated by listeners, there is no point to save it or go forward
			if (!item.isActive())
			{
				return null;
			}

			InterfaceWrapperHelper.save(item);

			printingQueueHandler.afterEnqueueAfterSave(item, InterfaceWrapperHelper.create(archiveRecord, I_AD_Archive.class));

			// https://github.com/metasfresh/metasfresh/issues/1240
			// see if a copies-per-archiveRecord value was specified. If yes, then use it as a multiplier.
			// Some printing queue handlers might also have set a value. We don't want to override it in a "hard" way.
			// Instead we assume that if a printingQueueHandler wants "two" in general, and now some user wants "three" in particular, then that user wants the "general" behavior times three, i.e. six.
			// Also note that right now I don't know any case where a user can set copies-per-archiveRecord in a case that is also handled by a printingQueueHandler.
			final Optional<PrintCopies> copiesIfExists = IArchiveBL.COPIES_PER_ARCHIVE.getValueIfExists(archiveRecord);
			if (copiesIfExists.isPresent())
			{
				final int oldItemCopies = Math.max(item.getCopies(), 1); // note about Math.max(): it should not happen that a printingQueueHandler sets copies:=0, but if it happens and COPIES_PER_ARCHIVE contained a value, then go with COPIES_PER_ARCHIVE
				final PrintCopies copiesMultipliers = copiesIfExists.get();
				final int newItemCopies = oldItemCopies * copiesMultipliers.toInt();

				if (oldItemCopies != newItemCopies)
				{
					Loggables.withLogger(logger, Level.DEBUG).addLog(
							"An explicit number of copies={} was specified for the given archive. Overwriting previous value={} with new value {}x{}={}; item={}",
							copiesMultipliers, item.getCopies(), oldItemCopies, copiesMultipliers, newItemCopies, item);
					item.setCopies(newItemCopies);
				}
			}

			InterfaceWrapperHelper.save(item); // make sure the changes made in after enqueue, are also saved
			return item;
		}
	}

	@Override
	public void printArchive(@NonNull final PrintArchiveParameters printArchiveParameters)
	{
		final PrintOutputFacade printOutputFacade = printArchiveParameters.getPrintOutputFacade();
		final de.metas.printing.model.I_AD_Archive archive = printArchiveParameters.getArchive();
		final HardwarePrinterId hwPrinterId = printArchiveParameters.getHwPrinterId();
		final HardwareTrayId hwTrayId = printArchiveParameters.getHwTrayId();

		if (!archive.isActive())
		{
			return;
		}

		I_C_Printing_Queue item = null;

		final boolean enqueueToPrintQueue = isIsCreatePrintingQueueItem(printArchiveParameters);
		if (enqueueToPrintQueue)
		{
			item = enqueue(archive);
		}

		if (item == null)
		{
			// nothing to do
			return;
		}
		if (hwPrinterId != null)
		{
			item.setAD_PrinterHW_ID(hwPrinterId.getRepoId());

			if (hwTrayId != null)
			{
				item.setAD_PrinterHW_MediaTray_ID(hwTrayId.getRepoId());
			}

			save(item);
		}
		final boolean createPrintJob = isProcessQueueItem(printArchiveParameters);
		if (createPrintJob)
		{
			forwardToJob(item, printOutputFacade);
		}
	}

	/**
	 * Directly create the print job. That means it will be printed now.
	 */
	private void forwardToJob(@NonNull final I_C_Printing_Queue printingQueue,
			@NonNull final PrintOutputFacade printOutputFacade)
	{
		final UserId adUserPrintJobId = UserId.ofRepoId(CoalesceUtil.firstGreaterThanZero(printingQueue.getAD_User_ID(), printingQueue.getCreatedBy()));
		final IPrintingQueueSource source = new SingletonPrintingQueueSource(printingQueue, adUserPrintJobId);

		printOutputFacade.print(source);
	}

	private boolean isIsCreatePrintingQueueItem(final PrintArchiveParameters printArchiveParameters)
	{
		final de.metas.printing.model.I_AD_Archive archive = printArchiveParameters.getArchive();

		try (final MDC.MDCCloseable ignore = TableRecordMDC.putTableRecordReference(archive))
		{
			// If we need to create a print job, then we shall enqueue to printing queue first
			if (isProcessQueueItem(printArchiveParameters))
			{
				logger.debug("IsCreatePrintingQueueItem - IsProcessQueueItem returned true; -> return true");
				return true;
			}

			if (archive.isDirectEnqueue())
			{
				logger.debug("IsCreatePrintingQueueItem - AD_Archive.IsDirectEnqueue=true; -> return true");
				return true;
			}

			// task 09417: also check if the archive references a docOutBoundConfig, and if yes, use its settings.
			if (archive.getC_Doc_Outbound_Config_ID() > 0)
			{
				final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
																					 I_C_Doc_Outbound_Config.class);
				if (config.isDirectEnqueue() || config.isDirectProcessQueueItem())
				{
					logger.debug("IsCreatePrintingQueueItem - AD_Archive has C_Doc_Outbound_Config_ID={} "
										 + "which has IsDirectEnqueue={} and DirectProcessQueueItem={}; -> return true",
								 archive.getC_Doc_Outbound_Config_ID(), config.isDirectEnqueue(), config.isDirectProcessQueueItem());
					return true;
				}
			}
			return false;
		}
	}

	private boolean isGenericArchive(final de.metas.printing.model.I_AD_Archive archive)
	{
		return (archive.getAD_Table_ID() <= 0 && archive.getRecord_ID() <= 0);
	}

	private boolean isProcessQueueItem(final PrintArchiveParameters printArchiveParameters)
	{
		if (printArchiveParameters.isEnforceEnqueueToPrintQueue())
		{
			return true;
		}

		final de.metas.printing.model.I_AD_Archive archive = printArchiveParameters.getArchive();

		// If we are explicitly asked to create a print job, then do it
		if (archive.isDirectProcessQueueItem())
		{
			logger.debug("IsProcessQueueItem - AD_Archive.IsDirectProcessQueueItem=true; -> return true");
			return true;
		}

		if (archive.getC_Doc_Outbound_Config_ID() > 0)
		{
			final I_C_Doc_Outbound_Config config = InterfaceWrapperHelper.create(archive.getC_Doc_Outbound_Config(),
																				 I_C_Doc_Outbound_Config.class);
			if (config.isDirectProcessQueueItem())
			{
				logger.debug("IsProcessQueueItem - AD_Archive has C_Doc_Outbound_Config_ID={} which has IsDirectProcessQueueItem=true; -> return true", archive.getC_Doc_Outbound_Config_ID());
				return true;
			}
		}

		// Backward compatibility: If this is a generic archive we are always creating a print job directly.
		// This is because old code rely on this logic (at that time there was no IsCreatePrintJob flag).
		if (isGenericArchive(archive))
		{
			logger.debug("IsProcessQueueItem - AD_Archive is a generic archive without record reference; -> return true");
			return true;
		}

		logger.debug("IsProcessQueueItem - none of the conditions applied -> return false");
		return false;
	}

	@Override
	public void registerHandler(final IPrintingQueueHandler handler)
	{
		printingQueueHandler.addHandler(handler);
	}

	@Override
	public void cancel(final I_C_Printing_Queue item)
	{
		if (item == null)
		{
			return;
		}

		if (item.isProcessed())
		{
			// Item was already printed, nothing do to
			return;
		}

		item.setIsActive(false);
		item.setProcessed(true);
		InterfaceWrapperHelper.save(item);
	}

	@Override
	public void renqueue(@NonNull final I_C_Printing_Queue item, final boolean recreateArchive)
	{
		cancel(item);

		final org.compiere.model.I_AD_Archive archive = item.getAD_Archive();

		if (recreateArchive)
		{
			final Object model = Services.get(IArchiveDAO.class).retrieveReferencedModel(archive, Object.class);
			if (model == null)
			{
				throw new AdempiereException("Cannot regenerate archive for " + item + " because there is no model for underlying archive " + archive);
			}

			//
			// We are just asking the document outbound producer to recreate the archive for the model.
			// As a consequence, following events are expected to happen:
			// * document archive is regenerated (async or sync)
			// * archive is automatically enqueued again to printing queue
			Services.get(IDocOutboundProducerService.class).createDocOutbound(model);
		}
		else
		{
			// Just re-enqueue then underlying archive
			enqueue(archive);
		}
	}

	@Override
	public IPrintingQueueQuery createPrintingQueueQuery()
	{
		return new PrintingQueueQuery();
	}

	@Override
	public PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(@NonNull final I_C_Printing_Queue printingQueueRecord)
	{
		final UserId printJobADUserId = getPrintToUser(printingQueueRecord);
		return createPrintingQueueProcessingInfo(printingQueueRecord, printJobADUserId);
	}

	@Override
	public PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(final Properties ctx, @NonNull final IPrintingQueueQuery query)
	{
		Check.assumeNotNull(query.getAggregationKey(), "IPrintingQueueQuery {} shall have an aggregation key", query);

		final I_C_Printing_Queue firstItem = printingDAO
				.createQuery(ctx, query, ITrx.TRXNAME_ThreadInherited)
				.firstNotNull(I_C_Printing_Queue.class);

		final UserId printJobAD_User_ID = getPrintJobAD_User_ID(ctx, query);

		return createPrintingQueueProcessingInfo(firstItem, printJobAD_User_ID);
	}

	@NonNull
	private PrintingQueueProcessingInfo createPrintingQueueProcessingInfo(
			@NonNull final I_C_Printing_Queue printingQueueRecord,
			final UserId printJobAD_User_ID)
	{
		final ImmutableList<UserId> printToUserIDs;
		final boolean createWithSpecificHostKey;
		if (printingQueueRecord.isPrintoutForOtherUser())
		{
			printToUserIDs = ImmutableList.copyOf(printingDAO.retrievePrintingQueueRecipientIDs(printingQueueRecord));

			// we don't know which hostKeys the users will be using next time they poll for print jobs
			createWithSpecificHostKey = false;
		}
		else
		{
			printToUserIDs = ImmutableList.of(printJobAD_User_ID);

			// If the item is for ourselves, then also our hostKey shall be added to the printing instructions.
			// Otherwise, the job might be printed by someone else who is logged in with our user-id (e.g SuperUser).
			createWithSpecificHostKey = true;
		}
		return new PrintingQueueProcessingInfo(printJobAD_User_ID, printToUserIDs, createWithSpecificHostKey);
	}

	private UserId getPrintJobAD_User_ID(final Properties ctx, final IPrintingQueueQuery printingQueueQuery)
	{
		if (printingQueueQuery.getAD_User_ID() >= 0)
		{
			logger.debug("Using AD_User_ToPrint from from query");
			return UserId.ofRepoId(printingQueueQuery.getAD_User_ID());
		}

		// 03870 R2
		// if possible, use the AD_User_ID of the user that started the process which is calling this method
		if (printingQueueQuery.getOnlyAD_PInstance_ID() != null)
		{
			final I_AD_PInstance pinstance = Services.get(IADPInstanceDAO.class).getById(printingQueueQuery.getOnlyAD_PInstance_ID());
			final int printJobUserId = pinstance.getAD_User_ID();
			if (printJobUserId > 0)
			{
				logger.debug("Using AD_User_ToPrint from AD_PInstance");
				return UserId.ofRepoId(printJobUserId);
			}
		}

		if (Env.getAD_User_ID(ctx) > 0)
		{
			final int printJobUserId = Env.getAD_User_ID(ctx);
			logger.debug("Using AD_User_ToPrint from context (logged user)");
			return UserId.ofRepoId(printJobUserId);
		}

		throw new AdempiereException("Unable to find a printJobUserId")
				.appendParametersToMessage()
				.setParameter("printingQueueQuery", printingQueueQuery)
				.setParameter("ctx", ctx);
	}

	@Override
	public List<IPrintingQueueSource> createPrintingQueueSources(final Properties ctx, @NonNull final IPrintingQueueQuery printingQueueQuery)
	{
		final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

		// get the distinct aggregation keys
		final IQuery<I_C_Printing_Queue> query = printingDAO.createQuery(ctx, printingQueueQuery, ITrx.TRXNAME_None);
		final List<Map<String, Object>> aggregationKeys = query.listDistinct(I_C_Printing_Queue.COLUMNNAME_PrintingQueueAggregationKey);

		final List<IPrintingQueueSource> sources = new ArrayList<>(aggregationKeys.size());

		// create one source per aggregation key
		for (final Map<String, Object> value2key : aggregationKeys)
		{
			final String aggregationKey = (String)value2key.get(I_C_Printing_Queue.COLUMNNAME_PrintingQueueAggregationKey);

			final IPrintingQueueQuery copy = printingQueueQuery.copy();
			copy.setAggregationKey(aggregationKey == null ? "" : aggregationKey); // if there is no key, whe assume ""..this might be the case for old items or for unit tests.

			final PrintingQueueProcessingInfo printingQueueProcessingInfo = printingQueueBL.createPrintingQueueProcessingInfo(ctx, copy);
			sources.add(new DefaultPrintingQueueSource(ctx, copy, printingQueueProcessingInfo));
		}
		return sources;
	}

	@Override
	public void setItemAggregationKey(final I_C_Printing_Queue item)
	{
		final List<Object> keyItems = new ArrayList<>();
		keyItems.add(item.getCopies());
		keyItems.add(item.isPrintoutForOtherUser());
		if (item.isPrintoutForOtherUser())
		{
			final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
			keyItems.addAll(printingDAO.retrievePrintingQueueRecipientIDs(item));
		}

		final String key = Util.mkKey(keyItems.toArray()).toString();
		item.setPrintingQueueAggregationKey(key);
	}

	@Override
	public void setPrintoutForOtherUsers(@NonNull final I_C_Printing_Queue item,
			final Set<Integer> userToPrintIds)
	{
		//
		// Make sure the item is not null and it was not already printed (i.e. processed)
		Check.assume(!item.isProcessed(), "item not already printed: {}", item);

		//
		// Build a list of valid user to print IDs
		Check.assumeNotEmpty(userToPrintIds, "userToPrintIds not empty");
		final Set<Integer> userToPrintIdsActual = new HashSet<>();
		for (final Integer userToPrintId : userToPrintIds)
		{
			// skip not valid user IDs
			if (userToPrintId == null || userToPrintId <= 0)
			{
				continue;
			}
			userToPrintIdsActual.add(userToPrintId);
		}
		Check.assumeNotEmpty(userToPrintIdsActual, "userToPrintIdsActual not empty");

		//
		// Delete existing queue item recipients (if any),
		// but don't update the aggregation key. We will update it later in this method.
		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
		printingDAO.deletePrintingQueueRecipients(item);

		//
		// Create new recipients
		// Make sure the item was saved.
		// We are not saving it here, because that shall be the responsibility of the caller.
		Check.errorIf(item.getC_Printing_Queue_ID() < 0, "Item shall be saved: {}", item);

		item.setIsPrintoutForOtherUser(true);
		for (final int userToPrintId : userToPrintIdsActual)
		{
			final I_C_Printing_Queue_Recipient recipient = InterfaceWrapperHelper.newInstance(I_C_Printing_Queue_Recipient.class, item);
			recipient.setC_Printing_Queue(item);
			recipient.setAD_User_ToPrint_ID(userToPrintId);
			printingDAO.setDisableAggregationKeyUpdate(recipient); // don't update the aggregation key
			InterfaceWrapperHelper.save(recipient);
		}

		// Make sure the aggregation key is up2date.
		setItemAggregationKey(item);
	}

	@Override
	public PrinterRoutingsQuery createPrinterRoutingsQueryForItem(@NonNull final I_C_Printing_Queue item)
	{
		return PrinterRoutingsQuery.builder()
				.clientId(ClientId.ofRepoIdOrSystem(item.getAD_Client_ID()))
				.orgId(OrgId.ofRepoIdOrAny(item.getAD_Org_ID()))
				.roleId(RoleId.ofRepoIdOrNull(item.getAD_Role_ID()))
				.userId(getPrintToUser(item))
				.tableId(AdTableId.ofRepoIdOrNull(item.getAD_Table_ID()))
				.processId(AdProcessId.ofRepoIdOrNull(item.getAD_Process_ID()))
				.docTypeId(DocTypeId.ofRepoIdOrNull(item.getC_DocType_ID()))
				.build();
	}

	private UserId getPrintToUser(@NonNull final I_C_Printing_Queue printingQueueRecord)
	{
		return UserId.ofRepoId(printingQueueRecord.getCreatedBy());
	}

	@Override
	public void setProcessedAndSave(@NonNull final I_C_Printing_Queue item)
	{
		item.setProcessed(true);
		InterfaceWrapperHelper.save(item);
	}
}
