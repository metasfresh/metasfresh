package de.metas.forex.interceptor;

import de.metas.forex.ForexContractRepository;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_ForeignExchangeContract.class)
public class C_ForeignExchangeContract
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_ForeignExchangeContract record)
	{
		ForexContractRepository.fromRecord(record); // validate
	}
}
