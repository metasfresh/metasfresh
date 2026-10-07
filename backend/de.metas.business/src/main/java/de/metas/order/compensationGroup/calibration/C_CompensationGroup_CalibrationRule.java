package de.metas.order.compensationGroup.calibration;

import de.metas.i18n.AdMessageKey;
import de.metas.order.IOrderDAO;
import de.metas.order.model.I_C_CompensationGroup_CalibrationRule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_CompensationGroup_CalibrationRule.class)
public class C_CompensationGroup_CalibrationRule
{
	private static final AdMessageKey MSG_UsedDeactivateInstead = AdMessageKey.of("C_CompensationGroup_CalibrationRule_UsedDeactivateInstead");

	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {
					I_C_CompensationGroup_CalibrationRule.COLUMNNAME_C_BPartner_ID,
					I_C_CompensationGroup_CalibrationRule.COLUMNNAME_C_BP_Group_ID,
					I_C_CompensationGroup_CalibrationRule.COLUMNNAME_GroupCompensationCalibrationFactor })
	public void validate(@NonNull final I_C_CompensationGroup_CalibrationRule rule)
	{
		CompensationGroupCalibrationRuleRepository.fromRecord(rule);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void assertNotUsed(@NonNull final I_C_CompensationGroup_CalibrationRule rule)
	{
		final CalibrationRuleId ruleId = CalibrationRuleId.ofRepoId(rule.getC_CompensationGroup_CalibrationRule_ID());
		if (orderDAO.isCalibrationRuleUsed(ruleId))
		{
			throw new AdempiereException(MSG_UsedDeactivateInstead).markAsUserValidationError();
		}
	}
}
