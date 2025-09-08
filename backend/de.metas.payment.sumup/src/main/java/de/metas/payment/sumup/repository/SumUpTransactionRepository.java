package de.metas.payment.sumup.repository;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.error.AdIssueId;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpCardReaderExternalId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpEventsDispatcher;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpPOSRef;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransaction.LastSync;
import de.metas.payment.sumup.SumUpTransaction.LastSyncStatus;
import de.metas.payment.sumup.SumUpTransactionExternalId;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.IQuery;
import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

@Repository
public class SumUpTransactionRepository
{
	@NonNull private static final Logger logger = LogManager.getLogger(SumUpTransactionRepository.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final SumUpEventsDispatcher eventsDispatcher;

	public SumUpTransactionRepository(
			@NonNull @Lazy final SumUpEventsDispatcher eventsDispatcher)
	{
		this.eventsDispatcher = eventsDispatcher;
	}

	public SumUpTransaction getById(@NonNull final SumUpTransactionExternalId id)
	{
		final I_SUMUP_Transaction record = retrieveSingleRecordNotNull(SumUpTransactionQuery.ofExternalId(id));
		return fromRecord(record);
	}

	public List<SumUpTransaction> list(@NonNull final SumUpTransactionQuery query)
	{
		return stream(query).collect(ImmutableList.toImmutableList());
	}

	public Stream<SumUpTransaction> stream(@NonNull final SumUpTransactionQuery query)
	{
		return toSqlQuery(query)
				.create()
				.stream()
				.map(SumUpTransactionRepository::fromRecord);
	}

	public void saveNew(@NonNull final SumUpTransaction trx)
	{
		final I_SUMUP_Transaction record = InterfaceWrapperHelper.newInstance(I_SUMUP_Transaction.class);
		updateRecordAndSave(record, trx);
	}

	public void save(@NonNull final SumUpTransaction trx)
	{
		I_SUMUP_Transaction record = retrieveSingleRecordIfExists(SumUpTransactionQuery.ofClientTransactionId(trx.getClientTransactionId())).orElse(null);
		if (record == null)
		{
			saveNew(trx);
		}
		else
		{
			updateRecordAndSave(record, trx);
		}
	}

	private void updateRecordAndSave(final I_SUMUP_Transaction record, final SumUpTransaction trx)
	{
		final boolean isNew;
		final SumUpTransaction trxPrev;
		if (InterfaceWrapperHelper.isNew(record))
		{
			isNew = true;
			trxPrev = null;
		}
		else
		{
			isNew = false;
			trxPrev = fromRecord(record);
		}

		updateRecord(record, trx);
		InterfaceWrapperHelper.save(record);

		if (isNew)
		{
			eventsDispatcher.fireNewTransaction(trx);
		}
		else
		{
			eventsDispatcher.fireStatusChangedIfNeeded(trx, trxPrev);
		}

	}

	private static void updateRecord(final I_SUMUP_Transaction record, final @NonNull SumUpTransaction from)
	{
		Check.assumeEquals(record.getAD_Client_ID(), from.getClientAndOrgId().getClientId().getRepoId(), "AD_Client_ID");
		record.setAD_Org_ID(from.getClientAndOrgId().getOrgId().getRepoId());
		record.setSUMUP_Config_ID(from.getConfigId().getRepoId());
		record.setExternalId(from.getExternalId().getAsString());
		record.setSUMUP_ClientTransactionId(from.getClientTransactionId().getAsString());
		record.setSUMUP_merchant_code(from.getMerchantCode().getAsString());
		updateRecord(record, from.getCardReader());
		record.setTimestamp(Timestamp.from(from.getTimestamp()));
		record.setStatus(from.getStatus().getCode());
		record.setCurrencyCode(from.getAmount().getCurrencyCode().toThreeLetterCode());
		record.setAmount(from.getAmount().toBigDecimal());
		record.setRefundAmt(from.getAmountRefunded().toBigDecimal());
		updateRecord(record, from.getCard());
		record.setJsonResponse(from.getJson());
		record.setC_POS_Order_ID(from.getPosRef() != null ? from.getPosRef().getPosOrderId() : -1);
		record.setC_POS_Payment_ID(from.getPosRef() != null ? from.getPosRef().getPosPaymentId() : -1);
		updateRecord(record, from.getLastSync());
	}

	private static void updateRecord(final I_SUMUP_Transaction record, final @Nullable SumUpTransaction.CardReader from)
	{
		record.setSUMUP_CardReader_ExternalId(from != null ? from.getExternalId().getAsString() : null);
		record.setSUMUP_CardReader_Name(from != null ? from.getName() : null);
	}

	private static void updateRecord(final I_SUMUP_Transaction record, final @Nullable SumUpTransaction.Card from)
	{
		record.setCardType(from != null ? from.getType() : null);
		record.setCardLast4Digits(from != null ? from.getLast4Digits() : null);
	}

	private static void updateRecord(final I_SUMUP_Transaction record, final @Nullable LastSync from)
	{
		record.setSUMUP_LastSync_Status(from != null ? from.getStatus().getCode() : null);
		record.setSUMUP_LastSync_Timestamp(from != null && from.getTimestamp() != null ? Timestamp.from(from.getTimestamp()) : null);
		record.setSUMUP_LastSync_Error_ID(from != null ? AdIssueId.toRepoId(from.getErrorId()) : -1);
	}

	private static SumUpTransaction fromRecord(final I_SUMUP_Transaction record)
	{
		final CurrencyCode currencyCode = CurrencyCode.ofThreeLetterCode(record.getCurrencyCode());

		return SumUpTransaction.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.configId(SumUpConfigId.ofRepoId(record.getSUMUP_Config_ID()))
				.externalId(SumUpTransactionExternalId.ofString(record.getExternalId()))
				.clientTransactionId(SumUpClientTransactionId.ofString(record.getSUMUP_ClientTransactionId()))
				.merchantCode(SumUpMerchantCode.ofString(record.getSUMUP_merchant_code()))
				.cardReader(extractCardReader(record))
				.timestamp(record.getTimestamp().toInstant())
				.status(SumUpTransactionStatus.ofString(record.getStatus()))
				.amount(Amount.of(record.getAmount(), currencyCode))
				.amountRefunded(Amount.of(record.getRefundAmt(), currencyCode))
				.card(extractCard(record))
				.json(record.getJsonResponse())
				.posRef(extractPOSRef(record))
				.lastSync(extractLastSync(record))
				.build();
	}

	@Nullable
	private static SumUpTransaction.CardReader extractCardReader(final I_SUMUP_Transaction record)
	{
		final SumUpCardReaderExternalId externalId = SumUpCardReaderExternalId.ofNullableString(record.getSUMUP_CardReader_ExternalId());
		if (externalId == null)
		{
			return null;
		}

		return SumUpTransaction.CardReader.builder()
				.externalId(externalId)
				.name(CoalesceUtil.coalesceNotNull(record.getSUMUP_CardReader_Name(), "?"))
				.build();
	}

	private static SumUpTransaction.Card extractCard(final I_SUMUP_Transaction record)
	{
		final String cardType = StringUtils.trimBlankToNull(record.getCardType());
		if (cardType == null)
		{
			return null;
		}

		final String cardLast4Digits = StringUtils.trimBlankToNull(record.getCardLast4Digits());
		if (cardLast4Digits == null)
		{
			return null;
		}

		return SumUpTransaction.Card.builder()
				.type(cardType)
				.last4Digits(cardLast4Digits)
				.build();
	}

	@Nullable
	private static SumUpPOSRef extractPOSRef(final I_SUMUP_Transaction record)
	{
		final int posOrderId = record.getC_POS_Order_ID();
		final int posPaymentId = record.getC_POS_Payment_ID();
		if (posOrderId <= 0 && posPaymentId <= 0)
		{
			return null;
		}

		return SumUpPOSRef.builder()
				.posOrderId(posOrderId)
				.posPaymentId(posPaymentId)
				.build();
	}

	@Nullable
	private static LastSync extractLastSync(final I_SUMUP_Transaction record)
	{
		final LastSyncStatus status = LastSyncStatus.ofNullableCode(record.getSUMUP_LastSync_Status());
		if (status == null)
		{
			return null;
		}

		final Timestamp timestamp = record.getSUMUP_LastSync_Timestamp();

		return LastSync.builder()
				.status(status)
				.timestamp(timestamp != null ? timestamp.toInstant() : null)
				.errorId(AdIssueId.ofRepoIdOrNull(record.getSUMUP_LastSync_Error_ID()))
				.build();
	}

	private Optional<I_SUMUP_Transaction> retrieveSingleRecordIfExists(@NonNull final SumUpTransactionQuery query)
	{
		return toSqlQuery(query).create().firstOnlyOptional(I_SUMUP_Transaction.class);
	}

	@NonNull
	private I_SUMUP_Transaction retrieveSingleRecordNotNull(@NonNull final SumUpTransactionQuery query)
	{
		return retrieveSingleRecordIfExists(query)
				.orElseThrow(() -> new AdempiereException("No transaction found for " + query));
	}

	private IQueryBuilder<I_SUMUP_Transaction> toSqlQuery(final SumUpTransactionQuery query)
	{
		final IQueryBuilder<I_SUMUP_Transaction> sqlQueryBuilder = queryBL.createQueryBuilder(I_SUMUP_Transaction.class)
				.setLimit(query.getLimit())
				.orderBy(I_SUMUP_Transaction.COLUMNNAME_SUMUP_Transaction_ID);

		if (query.getExternalId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_ExternalId, query.getExternalId().getAsString());
		}
		if (query.getClientTransactionId() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_SUMUP_ClientTransactionId, query.getClientTransactionId().getAsString());
		}
		if (query.getLocalIds() != null && !query.getLocalIds().isEmpty())
		{
			sqlQueryBuilder.addInArrayFilter(I_SUMUP_Transaction.COLUMNNAME_SUMUP_Transaction_ID, query.getLocalIds());
		}
		if (query.getStatus() != null)
		{
			sqlQueryBuilder.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_Status, query.getStatus().getCode());
		}
		if (query.getPosRef() != null)
		{
			final SumUpPOSRef posRef = query.getPosRef();
			if (posRef.getPosOrderId() > 0)
			{
				sqlQueryBuilder.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_C_POS_Order_ID, posRef.getPosOrderId());
			}
			if (posRef.getPosPaymentId() > 0)
			{
				sqlQueryBuilder.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_C_POS_Payment_ID, posRef.getPosPaymentId());
			}
		}

		return sqlQueryBuilder;
	}

	public void updateById(@NonNull final SumUpClientTransactionId id, @NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		final I_SUMUP_Transaction record = retrieveSingleRecordNotNull(SumUpTransactionQuery.ofClientTransactionId(id));
		updateRecord(record, updater);
	}

	public SumUpTransaction updateById(@NonNull final SumUpTransactionExternalId id, @NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		final I_SUMUP_Transaction record = retrieveSingleRecordNotNull(SumUpTransactionQuery.ofExternalId(id));
		return updateRecord(record, updater);
	}

	private SumUpTransaction updateRecord(@NonNull final I_SUMUP_Transaction record, @NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		final SumUpTransaction trxBeforeChange = fromRecord(record);
		final SumUpTransaction trx = updater.apply(trxBeforeChange);
		if (Objects.equals(trxBeforeChange, trx))
		{
			return trxBeforeChange;
		}

		updateRecordAndSave(record, trx);
		return trx;
	}

	public BulkUpdateByQueryResult bulkUpdateByQuery(
			@NonNull final SumUpTransactionQuery query,
			boolean isForceSendingChangeEvents,
			@NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final AtomicInteger countOK = new AtomicInteger(0);
		final AtomicInteger countError = new AtomicInteger(0);

		try (IAutoCloseable ignored = SumUpEventsDispatcher.temporaryForceSendingChangeEventsIf(isForceSendingChangeEvents))
		{
			toSqlQuery(query)
					.clearOrderBys()
					.orderBy(I_SUMUP_Transaction.COLUMNNAME_SUMUP_Transaction_ID)
					.create()
					.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
					.iterateAndStream()
					.forEach(record -> {
						try
						{
							trxManager.runInThreadInheritedTrx(() -> updateRecord(record, updater));
							countOK.incrementAndGet();
						}
						catch (final Exception ex)
						{
							countError.incrementAndGet();
							logger.warn("Failed updating transaction {}", record.getSUMUP_ClientTransactionId(), ex);
						}
					});

			return BulkUpdateByQueryResult.builder()
					.countOK(countOK.get())
					.countError(countError.get())
					.build();
		}
	}
}
