package de.metas.contracts.refund.interceptor;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import de.metas.contracts.ConditionsId;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.refund.RefundConfigRepository;
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
@Interceptor(I_C_Flatrate_Conditions.class)
public class C_Flatrate_Conditions
{
	private final RefundConfigRepository refundConfigRepository;

	public C_Flatrate_Conditions(@NonNull final RefundConfigRepository refundConfigRepository)
	{
		this.refundConfigRepository = refundConfigRepository;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteRefundconfigs(final I_C_Flatrate_Conditions cond)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_RefundConfig.class)
				.addEqualsFilter(I_C_Flatrate_RefundConfig.COLUMN_C_Flatrate_Conditions_ID, cond.getC_Flatrate_Conditions_ID())
				.create()
				.delete();
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void beforeComplete(@NonNull final I_C_Flatrate_Conditions cond)
	{
		final boolean refundConfigIsRequired = X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund.equals(cond.getType_Conditions());
		if (!refundConfigIsRequired)
		{
			return;
		}
		if (refundConfigRepository.hasRefundConfig(ConditionsId.ofRepoId(cond.getC_Flatrate_Conditions_ID())))
		{
			return;
		}

		throw new AdempiereException(Env.getAD_Language(Env.getCtx()), "Conditions_Error_RefundConfigMissing", new Object[0]);
	}
}
