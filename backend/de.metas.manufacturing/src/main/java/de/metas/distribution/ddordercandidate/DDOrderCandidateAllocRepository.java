package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableMap;
import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_Order_Candidate_DDOrder;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public class DDOrderCandidateAllocRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public DDOrderCandidateAllocList getByCandidateIds(final Collection<DDOrderCandidateId> ddOrderCandidateIds)
	{
		if (ddOrderCandidateIds.isEmpty())
		{
			return DDOrderCandidateAllocList.EMPTY;
		}

		return queryBL.createQueryBuilder(I_DD_Order_Candidate_DDOrder.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_DD_Order_Candidate_DDOrder.COLUMN_DD_Order_Candidate_ID, ddOrderCandidateIds)
				.orderBy(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_Order_Candidate_ID)
				.orderBy(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_Order_Candidate_DDOrder_ID)
				.create()
				.stream()
				.map(DDOrderCandidateAllocRepository::fromRecord)
				.collect(DDOrderCandidateAllocList.collect())
				;
	}

	private static DDOrderCandidateAlloc fromRecord(final I_DD_Order_Candidate_DDOrder record)
	{
		return DDOrderCandidateAlloc.builder()
				.ddOrderCandidateId(extractDDOrderCandidateId(record))
				.ddOrderAndLineId(extractDDOrderAndLineId(record))
				.qty(Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
				.build();
	}

	public void save(@NonNull final DDOrderCandidateAllocList list)
	{
		if (list.isEmpty())
		{
			return;
		}

		final ImmutableMap<DDOrderCandidateAllocKey, I_DD_Order_Candidate_DDOrder> records = queryBL.createQueryBuilder(I_DD_Order_Candidate_DDOrder.class)
				.addInArrayFilter(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_Order_Candidate_ID, list.getDDOrderCandidateIds())
				.addInArrayFilter(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_OrderLine_ID, list.getDDOrderLineIds())
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						DDOrderCandidateAllocRepository::extractKey,
						record -> record
				));

		for (final DDOrderCandidateAlloc alloc : list)
		{
			final DDOrderCandidateAllocKey key = extractKey(alloc);
			I_DD_Order_Candidate_DDOrder record = records.get(key);
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_DD_Order_Candidate_DDOrder.class);
			}

			updateRecord(record, alloc);
			InterfaceWrapperHelper.save(record);
		}

	}

	@Value(staticConstructor = "of")
	private static class DDOrderCandidateAllocKey
	{
		@NonNull DDOrderCandidateId ddOrderCandidateId;
		@NonNull DDOrderAndLineId ddOrderAndLineId;
	}

	private static DDOrderCandidateAllocKey extractKey(final DDOrderCandidateAlloc alloc)
	{
		return DDOrderCandidateAllocKey.of(alloc.getDdOrderCandidateId(), alloc.getDdOrderAndLineId());
	}

	private static DDOrderCandidateAllocKey extractKey(final I_DD_Order_Candidate_DDOrder record)
	{
		return DDOrderCandidateAllocKey.of(extractDDOrderCandidateId(record), extractDDOrderAndLineId(record));
	}

	private static DDOrderCandidateId extractDDOrderCandidateId(final I_DD_Order_Candidate_DDOrder record)
	{
		return DDOrderCandidateId.ofRepoId(record.getDD_Order_Candidate_ID());
	}

	private static DDOrderAndLineId extractDDOrderAndLineId(final I_DD_Order_Candidate_DDOrder record)
	{
		return DDOrderAndLineId.ofRepoIds(record.getDD_Order_ID(), record.getDD_OrderLine_ID());
	}

	private static void updateRecord(final I_DD_Order_Candidate_DDOrder record, final DDOrderCandidateAlloc from)
	{
		//record.setAD_Org_ID(??);
		record.setIsActive(true);
		record.setDD_Order_Candidate_ID(from.getDdOrderCandidateId().getRepoId());
		record.setDD_Order_ID(from.getDdOrderAndLineId().getDdOrderId().getRepoId());
		record.setDD_OrderLine_ID(from.getDdOrderAndLineId().getDdOrderLineId().getRepoId());
		record.setC_UOM_ID(from.getQty().getUomId().getRepoId());
		record.setQty(from.getQty().toBigDecimal());
	}
}
