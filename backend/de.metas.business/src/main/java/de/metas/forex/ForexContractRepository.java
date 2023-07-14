package de.metas.forex;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.DisplayNameQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

@Repository
public class ForexContractRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ForexContract getById(@NonNull ForexContractId id)
	{
		final I_C_ForeignExchangeContract record = getRecordById(id);
		return fromRecord(record);
	}

	private I_C_ForeignExchangeContract getRecordById(final @NonNull ForexContractId id)
	{
		return InterfaceWrapperHelper.load(id, I_C_ForeignExchangeContract.class);
	}

	public ImmutableList<ForexContract> getByIds(@NonNull Set<ForexContractId> ids)
	{
		if (ids.isEmpty())
		{
			return ImmutableList.of();
		}
		final List<I_C_ForeignExchangeContract> records = InterfaceWrapperHelper.loadByRepoIdAwares(ids, I_C_ForeignExchangeContract.class);
		return records.stream()
				.map(ForexContractRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public static ForexContract fromRecord(final I_C_ForeignExchangeContract record)
	{
		try
		{
			final CurrencyId currencyId = CurrencyId.ofRepoId(record.getC_Currency_ID());
			return ForexContract.builder()
					.id(extractId(record))
					.sectionCodeId(SectionCodeId.ofRepoIdOrNull(record.getM_SectionCode_ID()))
					.documentNo(record.getDocumentNo())
					.created(record.getCreated().toInstant())
					.createdBy(UserId.ofRepoId(record.getCreatedBy()))
					.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
					.docStatus(DocStatus.ofCode(record.getDocStatus()))
					.validityDate(record.getFEC_ValidityDate().toInstant())
					.maturityDate(record.getFEC_MaturityDate().toInstant())
					.currencyId(currencyId)
					.toCurrencyId(CurrencyId.ofRepoId(record.getTo_Currency_ID()))
					.currencyRate(record.getFEC_CurrencyRate())
					.amount(Money.of(record.getFEC_Amount(), currencyId))
					.allocatedAmount(Money.of(record.getFEC_Amount_Alloc(), currencyId))
					.openAmount(Money.of(record.getFEC_Amount_Open(), currencyId))
					.build();
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("ID", record.getC_ForeignExchangeContract_ID())
					.appendParametersToMessage();
		}
	}

	@NonNull
	private static ForexContractId extractId(final I_C_ForeignExchangeContract record)
	{
		return ForexContractId.ofRepoId(record.getC_ForeignExchangeContract_ID());
	}

	public ImmutableList<ForexContract> query(@NonNull final ForexContractQuery query)
	{
		return toSqlQuery(query)
				.stream()
				.map(ForexContractRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public ImmutableSet<ForexContractId> queryIds(@NonNull final ForexContractQuery query)
	{
		return toSqlQuery(query).listIds(ForexContractId::ofRepoId);
	}

	private IQuery<I_C_ForeignExchangeContract> toSqlQuery(@NonNull final ForexContractQuery query)
	{
		final IQueryBuilder<I_C_ForeignExchangeContract> queryBuilder = queryBL.createQueryBuilder(I_C_ForeignExchangeContract.class)
				.addOnlyActiveRecordsFilter()
				.setLimit(query.getLimit());

		if (query.getDocStatus() != null)
		{
			queryBuilder.addEqualsFilter(I_C_ForeignExchangeContract.COLUMNNAME_DocStatus, query.getDocStatus());
		}
		if (query.getCurrencyId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_ForeignExchangeContract.COLUMNNAME_C_Currency_ID, query.getCurrencyId());
		}
		if (query.getCurrencyToId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_ForeignExchangeContract.COLUMNNAME_To_Currency_ID, query.getCurrencyToId());
		}
		if (query.isOnlyWithOpenAmount())
		{
			queryBuilder.addCompareFilter(I_C_ForeignExchangeContract.COLUMN_FEC_Amount_Open, CompareQueryFilter.Operator.GREATER, BigDecimal.ZERO);
		}

		if (Check.isNotBlank(query.getDisplayNameSearchTerm()))
		{
			queryBuilder.addFilter(DisplayNameQueryFilter.of(I_C_ForeignExchangeContract.class, query.getDisplayNameSearchTerm()));
		}

		if (query.getSectionCodeId() != null)
		{
			queryBuilder.addCompositeQueryFilter()
					.setJoinOr()
					.addEqualsFilter(I_C_ForeignExchangeContract.COLUMNNAME_M_SectionCode_ID, query.getSectionCodeId())
					.addIsNull(I_C_ForeignExchangeContract.COLUMNNAME_M_SectionCode_ID);
		}

		return queryBuilder.create();
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
		updateRecordNoSave(record, updater);
		InterfaceWrapperHelper.save(record);
	}

	static void updateRecordNoSave(final I_C_ForeignExchangeContract record, final @NonNull Consumer<ForexContract> updater)
	{
		final ForexContract contract = fromRecord(record);
		updater.accept(contract);
		updateRecord(record, contract);
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
