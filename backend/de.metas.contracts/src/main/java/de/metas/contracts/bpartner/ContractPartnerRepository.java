package de.metas.contracts.bpartner;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Services;

@Repository
public class ContractPartnerRepository
{
	public ImmutableSet<BPartnerId> retrieveAllPartnersWithContracts()
	{
		IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_Flatrate_Term> contractsFilter = queryBL.createQueryBuilder(I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				.create();

		return queryBL
				.createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_Flatrate_Term.COLUMNNAME_Bill_BPartner_ID, contractsFilter)
				.create()
				.listIds(BPartnerId::ofRepoId);
	}

}
