package de.metas.contracts.interceptor;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;

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

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.i18n.IMsgBL;

@Interceptor(I_C_Flatrate_Matching.class)
public class C_Flatrate_Matching
{

	public static final transient C_Flatrate_Matching instance = new C_Flatrate_Matching();

	private C_Flatrate_Matching()
	{
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateDataEntry(final I_C_Flatrate_Matching matching)
	{
		final I_C_Flatrate_Conditions fc = matching.getC_Flatrate_Conditions();

		if (matching.getM_Product_Category_Matching_ID() <= 0
				&& X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee.equals(fc.getType_Conditions()))
		{
			throw new FillMandatoryException(I_C_Flatrate_Matching.COLUMNNAME_M_Product_Category_Matching_ID);
		}

	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_VOID, ModelValidator.TIMING_BEFORE_CLOSE })
	public void disallowNotSupportedDocActions(final I_C_Flatrate_Matching matching)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(matching);
		final String msg = Services.get(IMsgBL.class).getMsg(ctx, MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P);
		throw new AdempiereException(msg);
	}
	

}
