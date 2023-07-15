package de.metas.acct.open_items;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.acct.AccountConceptualName;
import de.metas.acct.gljournal_sap.SAPGLJournal;
import de.metas.acct.gljournal_sap.SAPGLJournalLine;
import de.metas.acct.open_items.handlers.Generic_OIHandler;
import de.metas.elementvalue.ElementValueService;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.acct.FactLine;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class FAOpenItemsService
{
	private static final Logger logger = LogManager.getLogger(FAOpenItemsService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_ProcessingBatchSize = "de.metas.acct.fact_acct_openItems_to_update.processingBatchSize";
	private static final int DEFAULT_ProcessingBatchSize = 1000;

	private final ImmutableMap<AccountConceptualName, FAOpenItemsHandler> handlersByAccount;
	private final Generic_OIHandler genericOIHandler;

	public FAOpenItemsService(
			@NonNull final ElementValueService elementValueService,
			@NonNull Optional<List<FAOpenItemsHandler>> handlers)
	{
		this.handlersByAccount = indexByAccount(handlers);
		this.genericOIHandler = new Generic_OIHandler(elementValueService);

		logger.info("Handlers: {}, {}", this.handlersByAccount, genericOIHandler);
	}

	private static ImmutableMap<AccountConceptualName, FAOpenItemsHandler> indexByAccount(Optional<List<FAOpenItemsHandler>> optionalHandlers)
	{
		final List<FAOpenItemsHandler> handlers = optionalHandlers.orElse(ImmutableList.of());
		if (handlers.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap.Builder<AccountConceptualName, FAOpenItemsHandler> result = ImmutableMap.builder();
		for (final FAOpenItemsHandler handler : handlers)
		{
			final Set<AccountConceptualName> handledAccountConceptualNames = handler.getHandledAccountConceptualNames();
			if (handledAccountConceptualNames.isEmpty())
			{
				logger.warn("Skip handler because it declares no handled accounts: {}", handler);
				continue;
			}

			for (final AccountConceptualName accountConceptualName : handledAccountConceptualNames)
			{
				result.put(accountConceptualName, handler);
			}
		}

		return result.build();
	}

	public Optional<FAOpenItemTrxInfo> computeTrxInfo(final FactLine factLine)
	{
		return computeTrxInfo(FAOpenItemTrxInfoComputeRequest.builder()
				.accountConceptualName(factLine.getAccountConceptualNameVO().orElse(null))
				.elementValueId(factLine.getElementValueId())
				.tableName(TableIdsCache.instance.getTableName(AdTableId.ofRepoId(factLine.getAD_Table_ID())))
				.recordId(factLine.getRecord_ID())
				.lineId(factLine.getLine_ID())
				.subLineId(factLine.getSubLine_ID())
				.build());
	}

	public Optional<FAOpenItemTrxInfo> computeTrxInfo(@NonNull final FAOpenItemTrxInfoComputeRequest request)
	{
		final FAOpenItemsHandler handler = getHandler(request.getAccountConceptualName());
		return handler.computeTrxInfo(request);
	}

	private FAOpenItemsHandler getHandler(@Nullable final AccountConceptualName accountConceptualName)
	{
		final FAOpenItemsHandler handler = accountConceptualName != null ? handlersByAccount.get(accountConceptualName) : null;
		return handler != null ? handler : genericOIHandler;
	}

	public int processScheduled()
	{
		final int batchSize = getProcessingBatchSize();
		final Stopwatch stopwatch = Stopwatch.createStarted();

		final int count = DB.getSQLValueEx(ITrx.TRXNAME_ThreadInherited,
				"SELECT de_metas_acct.fact_acct_openItems_to_update_process(p_BatchSize:=?)",
				batchSize);
		stopwatch.stop();

		logger.debug("Processed {} records in {} (batchSize={})", count, stopwatch, batchSize);
		return count;
	}

	private int getProcessingBatchSize()
	{
		final int batchSize = sysConfigBL.getIntValue(SYSCONFIG_ProcessingBatchSize, -1);
		return batchSize > 0 ? batchSize : DEFAULT_ProcessingBatchSize;
	}

	public void fireGLJournalCompleted(final SAPGLJournal glJournal)
	{
		for (SAPGLJournalLine line : glJournal.getLines())
		{
			final FAOpenItemTrxInfo openItemTrxInfo = line.getOpenItemTrxInfo();
			if (openItemTrxInfo == null)
			{
				continue;
			}

			final FAOpenItemsHandler handler = getHandler(openItemTrxInfo.getAccountConceptualName());
			handler.onGLJournalLineCompleted(line);
		}
	}

	public void fireGLJournalReactivated(final SAPGLJournal glJournal)
	{
		for (SAPGLJournalLine line : glJournal.getLines())
		{
			final FAOpenItemTrxInfo openItemTrxInfo = line.getOpenItemTrxInfo();
			if (openItemTrxInfo == null)
			{
				continue;
			}

			final FAOpenItemsHandler handler = getHandler(openItemTrxInfo.getAccountConceptualName());
			handler.onGLJournalLineReactivated(line);
		}
	}

}