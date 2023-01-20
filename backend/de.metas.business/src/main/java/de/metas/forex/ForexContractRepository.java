package de.metas.forex;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

@Repository
public class ForexContractRepository
{
	public ForexContract getById(@NonNull ForexContractId id)
	{
		final I_C_ForeignExchangeContract record = getRecordById(id);
		return fromRecord(record);
	}

	private I_C_ForeignExchangeContract getRecordById(final @NonNull ForexContractId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_ForeignExchangeContract.class);
	}

	public static ForexContract fromRecord(final I_C_ForeignExchangeContract record)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());

		return ForexContract.builder()
				.id(extractId(record))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.docStatus(DocStatus.ofCode(record.getDocStatus()))
				.currencyId(currencyId)
				.toCurrencyId(CurrencyId.ofRepoId(record.getTo_Currency_ID()))
				.currencyRate(record.getCurrencyRate())
				.amount(Money.of(record.getFEC_Amount(), currencyId))
				.allocatedAmount(Money.of(record.getFEC_Amount_Open(), currencyId))
				.openAmount(Money.of(record.getFEC_Amount_Open(), currencyId))
				.build();
	}

	@NonNull
	private static ForexContractId extractId(final I_C_ForeignExchangeContract record)
	{
		return ForexContractId.ofRepoId(record.getC_ForeignExchangeContract_ID());
	}

	private static void updateRecord(@NonNull final I_C_ForeignExchangeContract record, @NonNull final ForexContract from)
	{
		record.setFEC_Amount_Alloc(from.getAllocatedAmount().toBigDecimal());
		record.setFEC_Amount_Open(from.getOpenAmount().toBigDecimal());
	}

	public void updateById(@NonNull final ForexContractId contractId, @NonNull final Consumer<ForexContract> updater)
	{
		final I_C_ForeignExchangeContract record = getRecordById(contractId);
		updateRecordAndSave(record, updater);
	}

	private static void updateRecordAndSave(final I_C_ForeignExchangeContract record, final @NonNull Consumer<ForexContract> updater)
	{
		final ForexContract contract = fromRecord(record);
		updater.accept(contract);

		updateRecord(record, contract);
		InterfaceWrapperHelper.save(record);
	}

	public void updateByIds(@NonNull final Collection<ForexContractId> contractIds, @NonNull final Consumer<ForexContract> updater)
	{
		if (contractIds.isEmpty())
		{
			return;
		}

		final List<I_C_ForeignExchangeContract> records = InterfaceWrapperHelper.loadByRepoIdAwares(ImmutableSet.copyOf(contractIds), I_C_ForeignExchangeContract.class);
		for (I_C_ForeignExchangeContract record : records)
		{
			updateRecordAndSave(record, updater);
		}
	}

}
