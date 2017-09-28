package de.metas.procurement.base.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.procurement.base.IWebuiPush;
import de.metas.procurement.base.model.I_C_Flatrate_Conditions;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Interceptor(I_C_Flatrate_Term.class)
public class C_Flatrate_Term
{
	public static final transient C_Flatrate_Term instance = new C_Flatrate_Term();

	private C_Flatrate_Term()
	{
		super();
	}

	// TODO: when activating a flatrate term, make sure the product is present in PMM_Product table

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE, afterCommit = true)
	public void onAfterComplete(final I_C_Flatrate_Term term)
	{
		pushToWebUI(term);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE, ModelValidator.TIMING_AFTER_VOID }, afterCommit = true)
	public void onAfterVoid(final I_C_Flatrate_Term term)
	{
		pushToWebUI(term);
	}

	private final void pushToWebUI(final I_C_Flatrate_Term term)
	{
		final I_C_Flatrate_Conditions condidations = InterfaceWrapperHelper.create(term.getC_Flatrate_Conditions(), I_C_Flatrate_Conditions.class);

		//
		// Do nothing if this is not a procurement contract
		if (!I_C_Flatrate_Conditions.TYPE_CONDITIONS_Procuremnt.equals(condidations.getType_Conditions()))
		{
			return;
		}

		Services.get(IWebuiPush.class).pushBPartnerForContract(term);
	}

}
