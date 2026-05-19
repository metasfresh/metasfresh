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
}
