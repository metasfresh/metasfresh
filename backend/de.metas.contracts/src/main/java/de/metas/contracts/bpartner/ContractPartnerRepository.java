package de.metas.contracts.bpartner;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Customer_Retention;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;

@Repository
public class ContractPartnerRepository
{
	/**
	 * Partners whose customer retention the process {@code C_Customer_Retention_CreateUpdate} recomputes: those with a completed or closed subscription contract,
	 * plus those that still have a customer retention value, so that a value is cleared when the partner has no subscription contract (any more).
	 * <p>
	 * Iterated in buffered chunks, because the number of partners grows with the business data. The iterator is guaranteed: it walks a snapshot of the matching IDs,
	 * so partners whose customer retention the caller clears while iterating are neither skipped nor repeated. The caller closes it.
	 */
	public Iterator<BPartnerId> iteratePartnersForCustomerRetention()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_Flatrate_Term> subscriptionContracts = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_Type_Conditions, TypeConditions.SUBSCRIPTION)
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.completedOrClosedStatuses())
				.create();

		final IQuery<I_C_Customer_Retention> customerRetentionValues = queryBL.createQueryBuilder(I_C_Customer_Retention.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_C_Customer_Retention.COLUMNNAME_CustomerRetention)
				.create();

		return queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.filter(queryBL.createCompositeQueryFilter(I_C_BPartner.class)
						.setJoinOr()
						.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, subscriptionContracts)
						.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_Customer_Retention.COLUMNNAME_C_BPartner_ID, customerRetentionValues))
				.orderBy(I_C_BPartner.COLUMNNAME_C_BPartner_ID)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.iterateIds(BPartnerId::ofRepoId);
	}
}
