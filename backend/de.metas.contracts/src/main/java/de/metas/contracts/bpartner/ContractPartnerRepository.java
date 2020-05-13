package de.metas.contracts.bpartner;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.util.Services;

@Repository
public class ContractPartnerRepository
{
	public Iterator<BPartnerId> retrieveAllPartnersWithContracts()
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_Flatrate_Term> contractsFilter = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocStatus.completedOrClosedStatuses())
				.create();

		return queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, contractsFilter)
				.create()
				.iterateIds(BPartnerId::ofRepoId);
	}

}
