package de.metas.payment.sumup.repository;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.logging.LogManager;
import de.metas.organization.ClientAndOrgId;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpEventsDispatcher;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.slf4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Objects;
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

	public void saveNew(@NonNull final SumUpTransaction trx)
	{
		final I_SUMUP_Transaction record = InterfaceWrapperHelper.newInstance(I_SUMUP_Transaction.class);
		updateRecordAndSave(record, trx);
	}

	public void save(@NonNull final SumUpTransaction trx)
	{
		I_SUMUP_Transaction record = retrieveRecordById(trx.getClientTransactionId());
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
		SumUpTransactionStatus statusPrev;
		if (InterfaceWrapperHelper.isNew(record))
		{
			isNew = true;
			statusPrev = null;
		}
		else
		{
			isNew = false;
			statusPrev = SumUpTransactionStatus.ofString(record.getStatus());
		}

		updateRecord(record, trx);
		InterfaceWrapperHelper.save(record);

		if (isNew)
		{
			eventsDispatcher.fireNewTransaction(trx);
		}
		else
		{
			eventsDispatcher.fireStatusChangedIfNeeded(trx, statusPrev);
		}

	}

	private static void updateRecord(final I_SUMUP_Transaction record, final @NonNull SumUpTransaction from)
	{
		Check.assumeEquals(record.getAD_Client_ID(), from.getClientAndOrgId().getClientId().getRepoId(), "AD_Client_ID");
		record.setAD_Org_ID(from.getClientAndOrgId().getOrgId().getRepoId());
		record.setSUMUP_Config_ID(from.getConfigId().getRepoId());
		record.setExternalId(from.getExternalId());
		record.setSUMUP_ClientTransactionId(from.getClientTransactionId().getAsString());
		record.setSUMUP_merchant_code(from.getMerchantCode().getAsString());
		record.setTimestamp(Timestamp.from(from.getTimestamp()));
		record.setStatus(from.getStatus().getCode());
		record.setCurrencyCode(from.getAmount().getCurrencyCode().toThreeLetterCode());
		record.setAmount(from.getAmount().toBigDecimal());
		record.setJsonResponse(from.getJson());
		record.setC_POS_Order_ID(from.getPosOrderId());
		record.setC_POS_Payment_ID(from.getPosPaymentId());
	}

	private static SumUpTransaction fromRecord(final I_SUMUP_Transaction record)
	{
		return SumUpTransaction.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.configId(SumUpConfigId.ofRepoId(record.getSUMUP_Config_ID()))
				.externalId(record.getExternalId())
				.clientTransactionId(SumUpClientTransactionId.ofString(record.getSUMUP_ClientTransactionId()))
				.merchantCode(SumUpMerchantCode.ofString(record.getSUMUP_merchant_code()))
				.timestamp(record.getTimestamp().toInstant())
				.status(SumUpTransactionStatus.ofString(record.getStatus()))
				.amount(Amount.of(record.getAmount(), CurrencyCode.ofThreeLetterCode(record.getCurrencyCode())))
				.json(record.getJsonResponse())
				.posOrderId(record.getC_POS_Order_ID())
				.posPaymentId(record.getC_POS_Payment_ID())
				.build();
	}

	@Nullable
	private I_SUMUP_Transaction retrieveRecordById(@NonNull final SumUpClientTransactionId id)
	{
		return queryBL.createQueryBuilder(I_SUMUP_Transaction.class)
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_SUMUP_ClientTransactionId, id.getAsString())
				.create()
				.firstOnly(I_SUMUP_Transaction.class);
	}

	public void updateById(@NonNull final SumUpClientTransactionId id, @NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		final I_SUMUP_Transaction record = retrieveRecordById(id);
		if (record == null)
		{
			throw new AdempiereException("No transaction found for " + id);
		}

		updateRecord(record, updater);
	}

	private void updateRecord(@NonNull final I_SUMUP_Transaction record, @NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		final SumUpTransaction trxBeforeChange = fromRecord(record);
		final SumUpTransaction trx = updater.apply(trxBeforeChange);
		if (Objects.equals(trxBeforeChange, trx))
		{
			return;
		}

		updateRecordAndSave(record, trx);
	}

	public UpdateByPendingStatusResult updateByPendingStatus(@NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final AtomicInteger countOK = new AtomicInteger(0);
		final AtomicInteger countError = new AtomicInteger(0);
		streamByPendingStatus().forEach(record -> {
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

		return UpdateByPendingStatusResult.builder()
				.countOK(countOK.get())
				.countError(countError.get())
				.build();
	}

	private Stream<I_SUMUP_Transaction> streamByPendingStatus()
	{
		return queryBL.createQueryBuilder(I_SUMUP_Transaction.class)
				.addEqualsFilter(I_SUMUP_Transaction.COLUMNNAME_Status, SumUpTransactionStatus.PENDING.getCode())
				.orderBy(I_SUMUP_Transaction.COLUMNNAME_SUMUP_Transaction_ID)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.iterateAndStream();
	}
}
