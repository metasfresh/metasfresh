package de.metas.forex.interceptor;

import de.metas.forex.ForexContract;
import de.metas.forex.ForexContractRepository;
import de.metas.forex.ForexContractService;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_ForeignExchangeContract.class)
@Component
public class C_ForeignExchangeContract
{
	private final ForexContractService forexContractService;

	public C_ForeignExchangeContract(final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_ForeignExchangeContract record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			ForexContractRepository.fromRecord(record); // validate

			if (InterfaceWrapperHelper.isValueChanged(record, I_C_ForeignExchangeContract.COLUMNNAME_FEC_Amount))
			{
				forexContractService.updateWhileSaving(record, ForexContract::updateOpenAmount);
			}
		}
	}
}
