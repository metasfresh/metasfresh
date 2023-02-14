package de.metas.acct.accounts;

import com.google.common.collect.ImmutableMap;
import de.metas.acct.api.AccountId;
import de.metas.acct.api.AcctSchemaId;
import de.metas.cache.CCache;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.Account;
import org.compiere.model.I_C_Project;
import org.compiere.model.I_C_Project_Acct;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectAccountsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProjectId, ImmutableMap<AcctSchemaId, ProjectAccounts>> cache = CCache.<ProjectId, ImmutableMap<AcctSchemaId, ProjectAccounts>>builder()
			.tableName(I_C_Project.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(50)
			.build();

	public ProjectAccounts getAccounts(@NonNull final ProjectId projectId, @NonNull final AcctSchemaId acctSchemaId)
	{
		final ImmutableMap<AcctSchemaId, ProjectAccounts> map = cache.getOrLoad(projectId, this::retrieveAccounts);
		final ProjectAccounts accounts = map.get(acctSchemaId);
		if (accounts == null)
		{
			throw new AdempiereException("No Project accounts defined for " + projectId + " and " + acctSchemaId);
		}
		return accounts;
	}

	private ImmutableMap<AcctSchemaId, ProjectAccounts> retrieveAccounts(@NonNull final ProjectId projectId)
	{
		return queryBL.createQueryBuilder(I_C_Project_Acct.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Project_Acct.COLUMNNAME_C_Project_ID, projectId)
				.create()
				.stream()
				.map(ProjectAccountsRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(ProjectAccounts::getAcctSchemaId, accounts -> accounts));
	}

	private static ProjectAccounts fromRecord(@NonNull final I_C_Project_Acct record)
	{
		return ProjectAccounts.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(record.getC_AcctSchema_ID()))
				.PJ_Asset_Acct(Account.of(AccountId.ofRepoId(record.getPJ_Asset_Acct()), I_C_Project_Acct.COLUMNNAME_PJ_Asset_Acct))
				.PJ_WIP_Acct(Account.of(AccountId.ofRepoId(record.getPJ_WIP_Acct()), I_C_Project_Acct.COLUMNNAME_PJ_WIP_Acct))
				.build();
	}
}
