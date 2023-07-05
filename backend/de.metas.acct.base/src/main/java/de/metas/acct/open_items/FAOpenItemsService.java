package de.metas.acct.open_items;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.acct.AccountConceptualName;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.acct.FactLine;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Service
public class FAOpenItemsService
{
	private static final Logger logger = LogManager.getLogger(FAOpenItemsService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String SYSCONFIG_ProcessingBatchSize = "de.metas.acct.fact_acct_openItems_to_update.processingBatchSize";
	private static final int DEFAULT_ProcessingBatchSize = 1000;

	private final ImmutableMap<AccountConceptualName, FAOpenItemsHandler> handlersByAccount;

	public FAOpenItemsService(
			@NonNull Optional<List<FAOpenItemsHandler>> handlers)
	{
		this.handlersByAccount = handlers
				.map(list -> Maps.uniqueIndex(list, FAOpenItemsHandler::getHandledAccountConceptualName))
				.orElseGet(ImmutableMap::of);
		logger.info("Handlers: {}", this.handlersByAccount);
	}

	public Optional<FAOpenItemKey> getOrComputeOpenItemKey(final FactLine factLine)
	{
		final FAOpenItemKey openItemKey = FAOpenItemKey.ofNullableString(factLine.getOpenItemKey());
		if (openItemKey != null)
		{
			return Optional.of(openItemKey);
		}

		return factLine.getAccountConceptualNameVO()
				.map(this::getHandlerOrNull)
				.flatMap(handler -> handler.extractMatchingKey(factLine));
	}

	@Nullable
	private FAOpenItemsHandler getHandlerOrNull(final AccountConceptualName accountConceptualName)
	{
		return handlersByAccount.get(accountConceptualName);
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

}