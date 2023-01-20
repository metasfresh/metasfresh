package de.metas.forex;

import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.springframework.stereotype.Repository;

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
				.id(ForexContractId.ofRepoId(record.getC_ForeignExchangeContract_ID()))
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

	private static void updateRecord(@NonNull final I_C_ForeignExchangeContract record, @NonNull final ForexContract from)
	{
		record.setFEC_Amount_Alloc(from.getAllocatedAmount().toBigDecimal());
		record.setFEC_Amount_Open(from.getOpenAmount().toBigDecimal());
	}

	public void updateById(@NonNull final ForexContractId contractId, @NonNull final Consumer<ForexContract> consumer)
	{
		final I_C_ForeignExchangeContract record = getRecordById(contractId);
		final ForexContract contract = fromRecord(record);
		consumer.accept(contract);

		updateRecord(record, contract);
		InterfaceWrapperHelper.save(record);
	}

}
