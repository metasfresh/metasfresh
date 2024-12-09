package de.metas.distribution.ddordercandidate;

import com.google.common.collect.ImmutableMap;
import de.metas.distribution.ddorder.DDOrderAndLineId;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.eevolution.model.I_DD_Order_Candidate_DDOrder;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

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

		return getByQuery(
				queryBL.createQueryBuilder(I_DD_Order_Candidate_DDOrder.class)
						.addOnlyActiveRecordsFilter()
						.addInArrayFilter(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_Order_Candidate_ID, ddOrderCandidateIds)
		);
	}

	public DDOrderCandidateAllocList getByDDOrderId(@NonNull final DDOrderId ddOrderId)
	{
		return getByQuery(
				queryBL.createQueryBuilder(I_DD_Order_Candidate_DDOrder.class)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_Order_ID, ddOrderId)
		);
	}

	private DDOrderCandidateAllocList getByQuery(final IQueryBuilder<I_DD_Order_Candidate_DDOrder> queryBuilder)
	{
		return queryBuilder
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
				.id(record.getDD_Order_Candidate_DDOrder_ID())
				.ddOrderCandidateId(extractDDOrderCandidateId(record))
				.ddOrderAndLineId(extractDDOrderAndLineId(record))
<<<<<<< HEAD
				.qty(Quantitys.create(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
=======
				.qty(Quantitys.of(record.getQty(), UomId.ofRepoId(record.getC_UOM_ID())))
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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

	public void delete(@NonNull final DDOrderCandidateAllocList list)
	{
		final Set<Integer> ids = list.getIds();
		if (ids.isEmpty())
		{
			return;
		}

		queryBL.createQueryBuilder(I_DD_Order_Candidate_DDOrder.class)
				.addInArrayFilter(I_DD_Order_Candidate_DDOrder.COLUMN_DD_Order_Candidate_DDOrder_ID, ids)
				.create()
				.delete();
	}

	public void deleteByQuery(@NonNull final DeleteDDOrderCandidateAllocQuery deleteAllocQuery)
	{
		final IQueryBuilder<I_DD_Order_Candidate_DDOrder> queryBuilder = queryBL.createQueryBuilder(I_DD_Order_Candidate_DDOrder.class);

		if (deleteAllocQuery.getDdOrderCandidateId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_Order_Candidate_ID, deleteAllocQuery.getDdOrderCandidateId());
		}

		if (deleteAllocQuery.getDdOrderLineId() != null)
		{
			queryBuilder.addEqualsFilter(I_DD_Order_Candidate_DDOrder.COLUMNNAME_DD_OrderLine_ID, deleteAllocQuery.getDdOrderLineId());
		}

		if (queryBuilder.getCompositeFilter().isEmpty())
		{
			throw new AdempiereException("Deleting all DD_Order_Candidate_DDOrder records is not allowed!");
		}

		queryBuilder.create().delete();
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
