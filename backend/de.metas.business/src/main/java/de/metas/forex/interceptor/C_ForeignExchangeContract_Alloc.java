package de.metas.forex.interceptor;

import de.metas.forex.ForexContractId;
import de.metas.forex.ForexContractService;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_ForeignExchangeContract_Alloc;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_ForeignExchangeContract_Alloc.class)
@Component
public class C_ForeignExchangeContract_Alloc
{
	private final ForexContractService forexContractService;

	public C_ForeignExchangeContract_Alloc(final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE })
	public void afterDelete(final I_C_ForeignExchangeContract_Alloc record)
	{
		if (InterfaceWrapperHelper.isUIAction(record))
		{
			final ForexContractId forexContractId = ForexContractId.ofRepoId(record.getC_ForeignExchangeContract_ID());
			forexContractService.updateAmountsAfterCommit(forexContractId);
		}
	}
}
