package de.metas.contracts.interceptor;

import de.metas.contracts.impl.FlatrateTransitionService;
import de.metas.contracts.impl.FlatrateTransitionRepository;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.modular.settings.ModularContractSettingsRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class C_Flatrate_ConditionsTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void prohibitWrongDeliveryInterval()
	{
		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setDeliveryInterval(0);
		save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription);
		conditions.setC_Flatrate_Transition(transition);
		save(conditions);

		final C_Flatrate_Conditions flatrateConditions = new C_Flatrate_Conditions(new FlatrateTransitionService(new FlatrateTransitionRepository()), new ModularContractSettingsRepository());

		assertThatExceptionOfType(AdempiereException.class)
				.isThrownBy(() -> flatrateConditions.onTransitionChange(conditions))
				.withMessageContaining(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_INVALID_TRANSITION_2P.toAD_Message())
				.withNoCause();
	}

	@Test
	public void prohibitVoidAndClose()
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		final C_Flatrate_Conditions flatrateConditions = new C_Flatrate_Conditions(new FlatrateTransitionService(new FlatrateTransitionRepository()), new ModularContractSettingsRepository());

		assertThatThrownBy(() -> flatrateConditions.prohibitVoidAndClose(conditions)).hasMessage(MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P.toAD_Message());
	}

	@Test
	public void prohibitCompletingConditionsWithNoTransition()
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription);
		save(conditions);

		final C_Flatrate_Conditions flatrateConditions = new C_Flatrate_Conditions(new FlatrateTransitionService(new FlatrateTransitionRepository()), new ModularContractSettingsRepository());

		assertThatThrownBy(() -> flatrateConditions.beforeComplete(conditions)).hasMessage(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_TRANSITION_NOT_CO_0P.toAD_Message());
	}

	@Test
	public void prohibitReactivatingConditionsWithTermsCompleted()
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription);
		save(conditions);

		final I_C_Flatrate_Term subscriptionTerm = newInstance(I_C_Flatrate_Term.class);
		subscriptionTerm.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		subscriptionTerm.setC_Flatrate_Conditions(conditions);
		save(subscriptionTerm);

		final C_Flatrate_Conditions flatrateConditions = new C_Flatrate_Conditions(new FlatrateTransitionService(new FlatrateTransitionRepository()), new ModularContractSettingsRepository());

		assertThatThrownBy(() -> flatrateConditions.beforeReactivate(conditions)).hasMessage(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_ALREADY_IN_USE_0P.toAD_Message());
	}

}
