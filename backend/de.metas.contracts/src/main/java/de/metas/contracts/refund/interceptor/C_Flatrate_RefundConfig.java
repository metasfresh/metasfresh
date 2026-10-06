package de.metas.contracts.refund.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
@Interceptor(I_C_Flatrate_RefundConfig.class)
@Callout(I_C_Flatrate_RefundConfig.class)
public class C_Flatrate_RefundConfig
{
	private final RefundConfigRepository refundConfigRepository;
	private final RefundContractRepository refundContractRepository;

	public C_Flatrate_RefundConfig(
			@NonNull final RefundConfigRepository refundConfigRepository,
			@NonNull final RefundContractRepository refundContractRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
		this.refundContractRepository = refundContractRepository;
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_Flatrate_RefundConfig.COLUMNNAME_RefundBase)
	public void resetRefundValue(@NonNull final I_C_Flatrate_RefundConfig configRecord)
	{
		final String refundBase = configRecord.getRefundBase();
		if (X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage.equals(refundBase))
		{
			configRecord.setRefundAmt(null);
		}
		else if (X_C_Flatrate_RefundConfig.REFUNDBASE_Amount.equals(refundBase))
		{
			configRecord.setRefundPercent(null);
		}
		else
		{
			Check.fail("Unsupported C_Flatrate_RefundConfig.RefundBase value={}; configRecord={}", refundBase, configRecord);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void assertValid(@NonNull final I_C_Flatrate_RefundConfig configRecord)
	{
		if (configRecord.getC_Flatrate_Conditions_ID() <= 0)
		{
			return;
		}

		final RefundConfig newRefundConfig = refundConfigRepository.ofRecord(configRecord);
		RefundConfigs.assertRefundProductIsKnown(newRefundConfig);
		RefundConfigs.assertDeductedAtPaymentIsComputable(newRefundConfig);

		if (!configRecord.isActive())
		{
			// the engine ignores inactive lines, and the lines it compares with are the active ones
			return;
		}

		// the stored state of the record itself is replaced by its new state
		final List<RefundConfig> existingRefundConfigs = refundConfigRepository.getAllActiveByConditions(ConditionsId.ofRepoId(configRecord.getC_Flatrate_Conditions_ID())).stream()
				.filter(existingConfig -> !Objects.equals(existingConfig.getId(), newRefundConfig.getId()))
				.collect(ImmutableList.toImmutableList());

		final ArrayList<RefundConfig> allRefundConfigs = new ArrayList<>(existingRefundConfigs);
		allRefundConfigs.add(newRefundConfig);

		RefundConfigs.assertValid(allRefundConfigs);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Flatrate_RefundConfig.COLUMNNAME_IsDeductedAtPayment)
	public void assertDeductedAtPaymentNotChanged(@NonNull final I_C_Flatrate_RefundConfig configRecord)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(configRecord.getC_Flatrate_Conditions_ID());
		if (conditionsId == null)
		{
			return;
		}

		// the completed contracts already have refund candidates, or bonuses deducted at payment, under the current setting
		if (refundContractRepository.hasCompletedContracts(conditionsId))
		{
			throw new AdempiereException(RefundConfigs.MSG_REFUND_CONFIG_DEDUCTED_AT_PAYMENT_NOT_CHANGEABLE).markAsUserValidationError();
		}
	}
}
