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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.cache.CacheMgt;
import de.metas.contracts.FlatrateTransitionId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Year;
import org.compiere.model.ModelValidator;

import java.util.List;
import java.util.Properties;

@Validator(I_C_Flatrate_Conditions.class)
public class C_Flatrate_Conditions
{
	public static C_Flatrate_Conditions INSTANCE = new C_Flatrate_Conditions();

	public static final AdMessageKey MSG_CONDITIONS_ERROR_INVALID_TRANSITION_2P = AdMessageKey.of("Conditions_Error_Invalid_Transition");
	public static final AdMessageKey MSG_CONDITIONS_ERROR_ALREADY_IN_USE_0P = AdMessageKey.of("Conditions_Error_AlreadyInUse");
	public static final AdMessageKey MSG_CONDITIONS_ERROR_MATCHING_MISSING_0P = AdMessageKey.of("Conditions_Error_MatchingMissing");
	public static final AdMessageKey MSG_CONDITIONS_ERROR_TRANSITION_NOT_CO_0P = AdMessageKey.of("Conditions_Error_Transition_Not_Completed");
	public static final AdMessageKey MSG_CONDITIONS_ERROR_ORDERLESS_SUBSCRIPTION_NOT_SUPPORTED_0P = AdMessageKey.of("Conditions_Error_Subscription_Not_Supported"); // 03204

	private static final String SYSCONFIG_MODULAR_CONTRACT_TRANSITION_DEFAULT_VALUE = "C_Flatrate_Conditions.MODULAR_CONTRACT_TRANSITION_DEFAULT_VALUE";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private C_Flatrate_Conditions()
	{
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE)
	public void onTransitionChange(final I_C_Flatrate_Conditions cond)
	{
		if (X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription.equals(cond.getType_Conditions()))
		{
			final I_C_Flatrate_Transition trans = cond.getC_Flatrate_Transition();
			if (trans == null)
			{
				return;
			}

			final boolean reansRecordHasValidDeliverySetting = !Check.isEmpty(trans.getDeliveryIntervalUnit()) && trans.getDeliveryInterval() > 0;
			if (reansRecordHasValidDeliverySetting)
			{
				return;
			}

			final Properties ctx = InterfaceWrapperHelper.getCtx(cond);
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			throw new AdempiereException(MSG_CONDITIONS_ERROR_INVALID_TRANSITION_2P,
					new Object[] {
							msgBL.translate(ctx, I_C_Flatrate_Transition.COLUMNNAME_C_Flatrate_Transition_ID),
							msgBL.translate(ctx, I_C_Flatrate_Transition.COLUMNNAME_DeliveryIntervalUnit) + ", " +
									msgBL.translate(ctx, I_C_Flatrate_Transition.COLUMNNAME_DeliveryInterval)
					});
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteMatchings(final I_C_Flatrate_Conditions cond)
	{
		Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Flatrate_Matching.class)
				.addEqualsFilter(I_C_Flatrate_Matching.COLUMN_C_Flatrate_Conditions_ID, cond.getC_Flatrate_Conditions_ID())
				.create()
				.delete();
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_CLOSE })
	public void prohibitVoidAndClose(final I_C_Flatrate_Conditions cond)
	{
		throw new AdempiereException(MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void beforeComplete(final I_C_Flatrate_Conditions cond)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final boolean matchingsAreRequired = X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee.equals(cond.getType_Conditions())
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
				throw new AdempiereException(MSG_CONDITIONS_ERROR_MATCHING_MISSING_0P);
			}

		}

		setFlatrateTransitionForModularContract(cond);

		final boolean hasHoCompletedTransition = cond.getC_Flatrate_Transition_ID() <= 0 || !X_C_Flatrate_Transition.DOCSTATUS_Completed.equals(cond.getC_Flatrate_Transition().getDocStatus());
		if (hasHoCompletedTransition)
		{
			throw new AdempiereException(MSG_CONDITIONS_ERROR_TRANSITION_NOT_CO_0P);
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
			throw new AdempiereException(MSG_CONDITIONS_ERROR_ALREADY_IN_USE_0P);
		}
	}

	private void setFlatrateTransitionForModularContract(@NonNull final I_C_Flatrate_Conditions conditions)
	{
		final TypeConditions typeConditions = TypeConditions.ofCode(conditions.getType_Conditions());
		if (!typeConditions.isModularOrInterim())
		{
			return;
		}

		final FlatrateTransitionId flatrateTransitionId = FlatrateTransitionId.ofRepoIdOrNull(sysConfigBL.getIntValue(SYSCONFIG_MODULAR_CONTRACT_TRANSITION_DEFAULT_VALUE, -1));

		if (flatrateTransitionId == null)
		{
			throw new AdempiereException("Missing default value for flatrate condition configured for MODULAR CONTRACTS! Please set value for system configuration \"C_Flatrate_Conditions.MODULAR_CONTRACT_TRANSITION_DEFAULT_VALUE\"")
					.markAsUserValidationError();
		}

		conditions.setC_Flatrate_Transition_ID(flatrateTransitionId.getRepoId());
	}


	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE },  ifColumnsChanged =I_C_Flatrate_Conditions.COLUMNNAME_ModCntr_Settings_ID )
	void test(@NonNull final I_C_Flatrate_Conditions conditions)
	{
		CacheMgt.get().reset(I_C_Year.Table_Name);
	}
}
