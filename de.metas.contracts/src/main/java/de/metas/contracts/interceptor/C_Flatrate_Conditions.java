package de.metas.contracts.interceptor;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.i18n.IMsgBL;

@Validator(I_C_Flatrate_Conditions.class)
public class C_Flatrate_Conditions
{

	public static final String MSG_CONDITIONS_ERROR_INVALID_TRANSITION_2P = "Conditions_Error_Invalid_Transition";
	public static final String MSG_CONDITIONS_ERROR_ALREADY_IN_USE_0P = "Conditions_Error_AlreadyInUse";
	public static final String MSG_CONDITIONS_ERROR_MATCHING_MISSING_0P = "Conditions_Error_MatchingMissing";
	public static final String MSG_CONDITIONS_ERROR_TRANSITION_NOT_CO_0P = "Conditions_Error_Transition_Not_Completed";
	public static final String MSG_CONDITIONS_ERROR_ORDERLESS_SUBSCRIPTION_NOT_SUPPORTED_0P = "Conditions_Error_Subscription_Not_Supported"; // 03204

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void onTransitionChange(final I_C_Flatrate_Conditions cond)
	{
		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription.equals(cond.getType_Conditions()))
		{
			final I_C_Flatrate_Transition trans = cond.getC_Flatrate_Transition();
			if (trans != null)
			{
				if (Check.isEmpty(trans.getDeliveryIntervalUnit())
						|| trans.getDeliveryInterval() <= 0)
				{
					final Properties ctx = InterfaceWrapperHelper.getCtx(cond);
					final IMsgBL msgBL = Services.get(IMsgBL.class);

					throw new AdempiereException(Env.getAD_Language(ctx), MSG_CONDITIONS_ERROR_INVALID_TRANSITION_2P,
							new Object[] {
									msgBL.translate(ctx, I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Transition_ID),
									msgBL.translate(ctx, I_C_Flatrate_Transition.COLUMNNAME_DeliveryIntervalUnit) + ", " +
											msgBL.translate(ctx, I_C_Flatrate_Transition.COLUMNNAME_DeliveryInterval)
							});
				}
			}
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_CLOSE })
	public void prohibitVoidAndClose(final I_C_Flatrate_Conditions cond)
	{
		throw new AdempiereException("@" + MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P + "@");
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void beforeComplete(final I_C_Flatrate_Conditions cond)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final boolean matchingsAreRequired =
				X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee.equals(cond.getType_Conditions())
						|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable.equals(cond.getType_Conditions())
						|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee.equals(cond.getType_Conditions())
						|| X_C_Flatrate_Conditions.TYPE_CONDITIONS_QualityBasedInvoicing.equals(cond.getType_Conditions());
		if (matchingsAreRequired)
		{
			// 03660 for subscriptions, we don't strictly require mappings anymore,
			// because it's simply impractical for multi-org-setups
			final List<I_C_Flatrate_Matching> matchings = flatrateDB.retrieveFlatrateMatchings(cond);
			if (matchings.isEmpty())
			{
				throw new AdempiereException("@" + MSG_CONDITIONS_ERROR_MATCHING_MISSING_0P + "@");
			}

		}
		final boolean hasHoCompletedTransition = cond.getC_Flatrate_Transition_ID() <= 0 || !X_C_Flatrate_Transition.DOCSTATUS_Completed.equals(cond.getC_Flatrate_Transition().getDocStatus());
		if (hasHoCompletedTransition)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(cond);
			// note that the message itself contains the string '@C_Flatrate_Transition_ID@'
			throw new AdempiereException(Services.get(IMsgBL.class).getMsg(ctx, MSG_CONDITIONS_ERROR_TRANSITION_NOT_CO_0P));
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void beforeReactivate(final I_C_Flatrate_Conditions cond)
	{
		// check if the conditions are already used somewhere
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final List<I_C_Flatrate_Term> terms = flatrateDB.retrieveTerms(cond);
		if (!terms.isEmpty())
		{
			throw new AdempiereException("@" + MSG_CONDITIONS_ERROR_ALREADY_IN_USE_0P + "@");
		}
	}
}
