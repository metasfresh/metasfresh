package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.MCAdvComSponsorParam;
import de.metas.commission.service.ICommissionTermBL;
import de.metas.commission.service.ICommissionTermDAO;

@Validator(I_C_AdvCommissionTerm.class)
public class C_AdvCommissionTerm
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void createParameters(final I_C_AdvCommissionTerm term)
	{
		final PO termPO = InterfaceWrapperHelper.getPO(term);
		if (termPO.is_ValueChanged(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvComSystem_Type_ID)
				|| termPO.is_ValueChanged(I_C_AdvCommissionTerm.COLUMNNAME_C_AdvCommissionCondition_ID)
				|| MCAdvComSponsorParam.retrieve(term).isEmpty())
		{
			Services.get(ICommissionTermDAO.class).createParameters(term);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteParameters(final I_C_AdvCommissionTerm term)
	{
		Services.get(ICommissionTermDAO.class).deleteParameters(term);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_AdvCommissionTerm.COLUMNNAME_M_Product_ID })
	public void onChangeProduct(final I_C_AdvCommissionTerm term)
	{
		Services.get(ICommissionTermBL.class).setProductCategory(term);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void onAdvComSystem(final I_C_AdvCommissionTerm term)
	{
		final I_C_AdvCommissionCondition conditions = term.getC_AdvCommissionCondition();

		if (conditions != null)
		{
			term.setC_AdvComSystem(conditions.getC_AdvComSystem());
		}
	}

}
