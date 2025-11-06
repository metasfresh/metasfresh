package de.metas.payment.paymentterm.repository.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.payment.paymentterm.PaymentTerm;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.util.collections.CollectionUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_PaySchedule;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_PaymentTerm_Break;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder(access = AccessLevel.PACKAGE)
public class PaymentTermLoaderAndSaver
{
	@NonNull private final ITrxManager trxManager;
	@NonNull private final IQueryBL queryBL;

	@NonNull private final LinkedHashMap<PaymentTermId, I_C_PaymentTerm> paymentTermRecordsById = new LinkedHashMap<>();
	@NonNull private final HashMap<PaymentTermId, List<I_C_PaymentTerm_Break>> breakRecordsById = new HashMap<>();
	@NonNull private final HashMap<PaymentTermId, List<I_C_PaySchedule>> scheduleRecordsById = new HashMap<>();

	public List<PaymentTerm> loadAll()
	{
		final ImmutableList<I_C_PaymentTerm> paymentTermRecords = retrieveAllPaymentTermRecords();
		if (paymentTermRecords.isEmpty()) {return ImmutableList.of();}

		addToCacheAndWarmUpDependencies(paymentTermRecords);

		return paymentTermRecords.stream()
				.map(this::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public PaymentTerm loadById(@NonNull final PaymentTermId paymentTermId)
	{
		return fromRecord(getRecordById(paymentTermId));
	}

	private ImmutableList<I_C_PaymentTerm> retrieveAllPaymentTermRecords()
	{
		return queryBL.createQueryBuilder(I_C_PaymentTerm.class).list();
	}

	I_C_PaymentTerm getRecordById(final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoad(this.paymentTermRecordsById, paymentTermId, this::retrieveRecordByIds);
	}

	private Map<PaymentTermId, I_C_PaymentTerm> retrieveRecordByIds(final Set<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty()) {return ImmutableMap.of();}

		return queryBL.createQueryBuilder(I_C_PaymentTerm.class)
				.addInArrayFilter(I_C_PaymentTerm.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(PaymentTermConverter::extractId, record -> record));
	}

	private void addToCacheAndWarmUpDependencies(Collection<I_C_PaymentTerm> paymentTermRecords)
	{
		if (paymentTermRecords.isEmpty()) {return;}

		paymentTermRecords.forEach(record -> paymentTermRecordsById.put(PaymentTermConverter.extractId(record), record));
		CollectionUtils.getAllOrLoad(this.breakRecordsById, paymentTermRecordsById.keySet(), this::retrievePaymentTermBreakRecords);
		CollectionUtils.getAllOrLoad(this.scheduleRecordsById, paymentTermRecordsById.keySet(), this::retrievePayScheduleRecords);
	}

	private void warmUpByIds(final Set<PaymentTermId> paymentTermIds)
	{
		final Collection<I_C_PaymentTerm> paymentTerms = retrieveRecordByIds(paymentTermIds).values();
		addToCacheAndWarmUpDependencies(paymentTerms);
	}

	@NonNull
	private List<I_C_PaymentTerm_Break> getPaymentTermBreakRecords(@NonNull final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoad(this.breakRecordsById, paymentTermId, this::retrievePaymentTermBreakRecords);
	}

	@NonNull
	private Map<PaymentTermId, List<I_C_PaymentTerm_Break>> retrievePaymentTermBreakRecords(@NonNull final Set<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<PaymentTermId, List<I_C_PaymentTerm_Break>> result = queryBL.createQueryBuilder(I_C_PaymentTerm_Break.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaymentTerm_Break.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.stream()
				.collect(Collectors.groupingBy(PaymentTermBreakConverter::extractPaymentTermId, Collectors.toList()));

		return CollectionUtils.fillMissingKeys(result, paymentTermIds, ImmutableList.of());
	}

	@NonNull
	private List<I_C_PaySchedule> getPayScheduleRecords(@NonNull final PaymentTermId paymentTermId)
	{
		return CollectionUtils.getOrLoad(this.scheduleRecordsById, paymentTermId, this::retrievePayScheduleRecords);
	}

	@NonNull
	private Map<PaymentTermId, List<I_C_PaySchedule>> retrievePayScheduleRecords(@NonNull final Set<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final Map<PaymentTermId, List<I_C_PaySchedule>> result = queryBL.createQueryBuilder(I_C_PaySchedule.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_PaySchedule.COLUMNNAME_C_PaymentTerm_ID, paymentTermIds)
				.stream()
				.collect(Collectors.groupingBy(PayScheduleConverter::extractPaymentTermId, Collectors.toList()));

		return CollectionUtils.fillMissingKeys(result, paymentTermIds, ImmutableList.of());
	}

	private PaymentTerm fromRecord(final I_C_PaymentTerm record)
	{
		@NonNull final PaymentTermId paymentTermId = PaymentTermConverter.extractId(record);
		@NonNull final List<I_C_PaymentTerm_Break> breakRecords = getPaymentTermBreakRecords(paymentTermId);
		@NonNull final List<I_C_PaySchedule> payScheduleRecords = getPayScheduleRecords(paymentTermId);
		return PaymentTermConverter.fromRecord(record, breakRecords, payScheduleRecords);
	}

	private void saveToDB(final I_C_PaymentTerm record)
	{
		InterfaceWrapperHelper.saveRecord(record);
	}

	public void syncStateToDatabase(final Set<PaymentTermId> paymentTermIds)
	{
		if (paymentTermIds.isEmpty()) {return;}

		trxManager.runInThreadInheritedTrx(() -> {
			warmUpByIds(paymentTermIds);

			for (final PaymentTermId paymentTermId : paymentTermIds)
			{
				final PaymentTerm paymentTerm = loadById(paymentTermId);
				saveToDB(paymentTerm);
			}
		});
	}

	private void saveToDB(@NonNull final PaymentTerm paymentTerm)
	{
		final PaymentTermId paymentTermId = paymentTerm.getId();
		final I_C_PaymentTerm record = getRecordById(paymentTermId);
		PaymentTermConverter.updateRecord(record, paymentTerm);
		saveToDB(record);
	}
}
