package de.metas.acct.tax;

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.I_C_TaxDeclarationAcct;
import org.compiere.model.I_C_TaxDeclarationLine;
import org.springframework.stereotype.Repository;

@Repository
public class TaxDeclarationRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public I_C_TaxDeclaration getById(@NonNull final TaxDeclarationId id)
	{
		final I_C_TaxDeclaration record = InterfaceWrapperHelper.load(id, I_C_TaxDeclaration.class);
		if (record == null)
		{
			throw new AdempiereException("No C_TaxDeclaration found for id=" + id.getRepoId());
		}
		return record;
	}

	public void deleteChildRows(@NonNull final TaxDeclarationId id)
	{
		queryBL.createQueryBuilder(I_C_TaxDeclarationAcct.class)
				.addEqualsFilter(I_C_TaxDeclarationAcct.COLUMNNAME_C_TaxDeclaration_ID, id)
				.create()
				.deleteDirectly();
		queryBL.createQueryBuilder(I_C_TaxDeclarationLine.class)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, id)
				.create()
				.deleteDirectly();
	}

	public boolean hasAnyLines(@NonNull final TaxDeclarationId id)
	{
		return queryBL.createQueryBuilder(I_C_TaxDeclarationLine.class)
				.addEqualsFilter(I_C_TaxDeclarationLine.COLUMNNAME_C_TaxDeclaration_ID, id)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

	public boolean existsCompletedOverlappingPeriod(
			@NonNull final TaxDeclarationId selfId,
			final int acctSchemaId,
			final int periodId)
	{
		return queryBL.createQueryBuilder(I_C_TaxDeclaration.class)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_Processed, true)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_AcctSchema_ID, acctSchemaId)
				.addEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_Period_ID, periodId)
				.addNotEqualsFilter(I_C_TaxDeclaration.COLUMNNAME_C_TaxDeclaration_ID, selfId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
