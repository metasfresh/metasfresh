package de.metas.payment.sumup.repository;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.payment.sumup.SumUpClientTransactionId;
import de.metas.payment.sumup.SumUpConfigId;
import de.metas.payment.sumup.SumUpMerchantCode;
import de.metas.payment.sumup.SumUpTransaction;
import de.metas.payment.sumup.SumUpTransactionStatus;
import de.metas.payment.sumup.repository.model.I_SUMUP_Transaction;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.function.UnaryOperator;

@Repository
public class SumUpTransactionRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void saveNew(@NonNull final SumUpTransaction trx)
	{
		final I_SUMUP_Transaction record = InterfaceWrapperHelper.newInstance(I_SUMUP_Transaction.class);
		updateRecord(record, trx);
		InterfaceWrapperHelper.save(record);
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
			updateRecord(record, trx);
			InterfaceWrapperHelper.save(record);
		}
	}

	private static void updateRecord(final I_SUMUP_Transaction record, final @NonNull SumUpTransaction from)
	{
		record.setSUMUP_Config_ID(from.getConfigId().getRepoId());
		record.setExternalId(from.getExternalId());
		record.setSUMUP_ClientTransactionId(from.getClientTransactionId().getAsString());
		record.setSUMUP_merchant_code(from.getMerchantCode().getAsString());
		record.setTimestamp(Timestamp.from(from.getTimestamp()));
		record.setStatus(from.getStatus().getAsString());
		record.setCurrencyCode(from.getAmount().getCurrencyCode().toThreeLetterCode());
		record.setAmount(from.getAmount().toBigDecimal());
		record.setJsonResponse(from.getJson());
		record.setC_POS_Order_ID(from.getPosOrderId());
		record.setC_POS_Payment_ID(from.getPosPaymentId());
	}

	private static SumUpTransaction fromRecord(final I_SUMUP_Transaction record)
	{
		return SumUpTransaction.builder()
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

	public SumUpTransaction updateById(@NonNull final SumUpClientTransactionId id, @NonNull final UnaryOperator<SumUpTransaction> updater)
	{
		final I_SUMUP_Transaction record = retrieveRecordById(id);
		if (record == null)
		{
			throw new AdempiereException("No transaction found for " + id);
		}

		final SumUpTransaction trxBeforeChange = fromRecord(record);
		final SumUpTransaction trx = updater.apply(trxBeforeChange);
		if (Objects.equals(trxBeforeChange, trx))
		{
			return trxBeforeChange;
		}

		updateRecord(record, trx);
		InterfaceWrapperHelper.save(record);

		return trx;
	}
}
