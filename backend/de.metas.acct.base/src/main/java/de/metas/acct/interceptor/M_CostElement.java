package de.metas.acct.interceptor;

import de.metas.acct.accounts.CostElementAccountsRepository;
import de.metas.costing.CostElementId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema_Default;
import org.compiere.model.I_M_CostElement;
import org.compiere.model.I_M_CostElement_Acct;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_CostElement.class)
@Component
public class M_CostElement
{
	private final CostElementAccountsRepository costElementAccountsRepository;

	public M_CostElement(@NonNull final CostElementAccountsRepository costElementAccountsRepository)
	{
		this.costElementAccountsRepository = costElementAccountsRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterSave(final I_M_CostElement record, ModelChangeType changeType)
	{
		if (changeType.isNew())
		{
			InterfaceWrapperHelper.getPO(record).insert_Accounting(I_M_CostElement_Acct.Table_Name, I_C_AcctSchema_Default.Table_Name, null);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_M_CostElement record)
	{
		costElementAccountsRepository.deleteByCostElementId(CostElementId.ofRepoId(record.getM_CostElement_ID()));
	}
}
