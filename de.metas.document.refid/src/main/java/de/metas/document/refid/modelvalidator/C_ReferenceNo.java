package de.metas.document.refid.modelvalidator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.document.refid.model.I_C_ReferenceNo;
import de.metas.document.refid.model.I_C_ReferenceNo_Doc;
import de.metas.util.Services;

@Validator(I_C_ReferenceNo.class)
public class C_ReferenceNo
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteReferenceNoDocs(final I_C_ReferenceNo referenceNo)
	{
		Services
				.get(IQueryBL.class)
				.createQueryBuilder(I_C_ReferenceNo_Doc.class)
				.addEqualsFilter(I_C_ReferenceNo_Doc.COLUMNNAME_C_ReferenceNo_ID, referenceNo.getC_ReferenceNo_ID())
				.create()
				.delete();
	}
}
