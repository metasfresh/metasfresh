package de.metas.contracts.refund.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
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
import de.metas.contracts.refund.RefundInvoiceCandidateInvalidator;
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
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final RefundConfigRepository refundConfigRepository;
	private final RefundContractRepository refundContractRepository;
	private final RefundInvoiceCandidateInvalidator refundInvoiceCandidateInvalidator;

	public C_Flatrate_RefundConfig(
			@NonNull final RefundConfigRepository refundConfigRepository,
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final RefundInvoiceCandidateInvalidator refundInvoiceCandidateInvalidator)
	{
		this.refundConfigRepository = refundConfigRepository;
		this.refundContractRepository = refundContractRepository;
		this.refundInvoiceCandidateInvalidator = refundInvoiceCandidateInvalidator;
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
		RefundConfigs.assertInvoiceDistanceDividesTheYear(newRefundConfig);

		if (!configRecord.isActive())
		{
			// the engine ignores inactive lines, and the lines it compares with are the active ones
			return;
		}

		// only active lines are used for the bonus at payment, so an invalid line can still be deactivated
		RefundConfigs.assertDeductedAtPaymentIsComputable(newRefundConfig);

		// the stored state of the record itself is replaced by its new state
		final List<RefundConfig> existingRefundConfigs = refundConfigRepository.getAllActiveByConditions(ConditionsId.ofRepoId(configRecord.getC_Flatrate_Conditions_ID())).stream()
				.filter(existingConfig -> !Objects.equals(existingConfig.getId(), newRefundConfig.getId()))
				.collect(ImmutableList.toImmutableList());

		final ArrayList<RefundConfig> allRefundConfigs = new ArrayList<>(existingRefundConfigs);
		allRefundConfigs.add(newRefundConfig);

		// first: a condition with a line deducted at payment gets no second line, whatever the new line's own flag
		RefundConfigs.assertDeductedAtPaymentIsSingleLine(allRefundConfigs);
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

	/**
	 * The refund of the completed contracts with these conditions is computed again with the changed amount, e.g. after its currency was corrected
	 * (until then, the refund candidates are in error and the sales are not assigned to them).
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = {
			I_C_Flatrate_RefundConfig.COLUMNNAME_C_Currency_ID,
			I_C_Flatrate_RefundConfig.COLUMNNAME_RefundAmt })
	public void invalidateInvoiceCandidatesAfterCommit(@NonNull final I_C_Flatrate_RefundConfig configRecord)
	{
		final ConditionsId conditionsId = ConditionsId.ofRepoIdOrNull(configRecord.getC_Flatrate_Conditions_ID());
		if (conditionsId == null)
		{
			return;
		}
		refundInvoiceCandidateInvalidator.invalidateCandidatesOfConditionsAfterCommit(conditionsId);
	}

	/**
	 * Like when a term is completed: the cache invalidation of the table change is sent before the transaction is committed, so a read in between (e.g. of whether there
	 * is any contract deducted at payment, by a payment allocation view that is loaded meanwhile) could cache the old state; it is reset again once the change is committed.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
			I_C_Flatrate_RefundConfig.COLUMNNAME_IsActive,
			I_C_Flatrate_RefundConfig.COLUMNNAME_IsDeductedAtPayment,
			I_C_Flatrate_RefundConfig.COLUMNNAME_C_Flatrate_Conditions_ID })
	public void resetCachesAfterCommit(@NonNull final I_C_Flatrate_RefundConfig configRecord)
	{
		trxManager
				.getCurrentTrxListenerManagerOrAutoCommit()
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.registerHandlingMethod(trx -> refundContractRepository.resetCaches());
	}
}
