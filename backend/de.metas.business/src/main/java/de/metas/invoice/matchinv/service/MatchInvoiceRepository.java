package de.metas.invoice.matchinv.service;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_MatchInv;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
class MatchInvoiceRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_M_MatchInv getById(@NonNull final MatchInvId matchInvId)
	{
		return InterfaceWrapperHelper.load(matchInvId, I_M_MatchInv.class);
	}

	/**
	 * Retrieves the (active) records that reference the given invoice line.
	 */
	public List<I_M_MatchInv> getByInvoiceLineId(@NonNull final InvoiceLineId invoiceLineId)
	{
		return queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_C_InvoiceLine_ID, invoiceLineId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.list();
	}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInOutLineIds(@NonNull final Set<InOutLineId> inoutLineIds)
	{
		if (inoutLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_MatchInv.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineIds)
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.listIds(MatchInvId::ofRepoId);
	}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInvoiceLineIds(@NonNull final Set<InvoiceLineId> invoiceLineIds)
	{
		if (invoiceLineIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return queryBL
				.createQueryBuilder(I_M_MatchInv.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceLineIds)
				.addEqualsFilter(I_M_MatchInv.COLUMN_Processed, true)
				.addNotEqualsFilter(I_M_MatchInv.COLUMN_Posted, true)
				.create()
				.listIds(MatchInvId::ofRepoId);
	}

	public void deleteByInOutId(@NonNull final InOutId inoutId)
	{
		queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOut_ID, inoutId)
				//.addOnlyActiveRecordsFilter()  // all of them because maybe we want to delete them
				//.orderBy(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.create()
				.delete(false);
	}

	public void deleteByInOutLineId(@NonNull final InOutLineId inoutLineId)
	{
		queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, inoutLineId)
				//.addOnlyActiveRecordsFilter()  // all of them because maybe we want to delete them
				//.orderBy(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.create()
				.delete(false);
	}

	public List<I_M_MatchInv> getByInOutLineId(@NonNull final InOutLineId inoutLineId)
	{
		return queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMNNAME_M_InOutLine_ID, inoutLineId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_M_MatchInv.COLUMN_M_MatchInv_ID)
				.create()
				.list();
	}

	public boolean hasMatchInvs(@NonNull final InvoiceLineId invoiceLineId, @NonNull final InOutLineId inoutLineId)
	{
		return queryBL.createQueryBuilder(I_M_MatchInv.class)
				.addEqualsFilter(I_M_MatchInv.COLUMN_C_InvoiceLine_ID, invoiceLineId)
				.addEqualsFilter(I_M_MatchInv.COLUMN_M_InOutLine_ID, inoutLineId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
