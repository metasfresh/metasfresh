package de.metas.contracts.refund.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfigQuery;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundConfigs;
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

	public C_Flatrate_RefundConfig(@NonNull final RefundConfigRepository refundConfigRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
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

		final RefundConfigQuery query = RefundConfigQuery
				.builder()
				.conditionsId(ConditionsId.ofRepoId(configRecord.getC_Flatrate_Conditions_ID()))
				.build();

		final List<RefundConfig> existingRefundConfigs = refundConfigRepository.getByQuery(query);
		final RefundConfig newRefundConfig = refundConfigRepository.ofRecord(configRecord);

		final ArrayList<RefundConfig> allRefundConfigs = new ArrayList<>(existingRefundConfigs);
		allRefundConfigs.add(newRefundConfig);

		RefundConfigs.assertValid(allRefundConfigs);
	}
}
