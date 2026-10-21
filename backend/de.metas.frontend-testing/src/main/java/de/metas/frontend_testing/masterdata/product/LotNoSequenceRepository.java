package de.metas.frontend_testing.masterdata.product;

import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequenceno.DateSequenceProvider;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Sequence;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persists the lot-number sequence of a test BOM.
 */
class LotNoSequenceRepository
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	DocSequenceId create(@NonNull final JsonCreateProductRequest.LotNoSequence request, @NonNull final String productValue)
	{
		final int currentNext = request.getCurrentNext() != null ? request.getCurrentNext() : 1;

		final I_AD_Sequence sequence = newInstance(I_AD_Sequence.class);
		sequence.setAD_Org_ID(OrgId.ANY.getRepoId());
		sequence.setName("LotNo_" + productValue);
		sequence.setIsAutoSequence(true);
		sequence.setIsTableID(false);
		sequence.setIncrementNo(1);
		sequence.setStartNo(currentNext);
		sequence.setCurrentNext(currentNext);
		sequence.setCurrentNextSys(currentNext);
		sequence.setPrefix(request.getPrefix());
		sequence.setDecimalPattern(request.getDecimalPattern());
		if (request.isDateBased())
		{
			sequence.setDateColumn("MovementDate"); // LotNumberBL passes the receipt date under the sequence's DateColumn
			sequence.setCustomSequenceNoProvider_JavaClass_ID(getDateSequenceProviderJavaClassId());
		}
		saveRecord(sequence);
		return DocSequenceId.ofRepoId(sequence.getAD_Sequence_ID());
	}

	private int getDateSequenceProviderJavaClassId()
	{
		final int javaClassId = queryBL.createQueryBuilder(I_AD_JavaClass.class)
				.addEqualsFilter(I_AD_JavaClass.COLUMNNAME_Classname, DateSequenceProvider.class.getName())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstId();
		if (javaClassId <= 0)
		{
			throw new AdempiereException("No AD_JavaClass found for " + DateSequenceProvider.class.getName());
		}
		return javaClassId;
	}
}
