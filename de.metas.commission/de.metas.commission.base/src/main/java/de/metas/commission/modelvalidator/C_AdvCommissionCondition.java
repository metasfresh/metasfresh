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

import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.service.ICommissionTermDAO;

@Validator(I_C_AdvCommissionCondition.class)
public class C_AdvCommissionCondition
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteTerms(final I_C_AdvCommissionCondition condition)
	{
		for (final I_C_AdvCommissionTerm term : Services.get(ICommissionTermDAO.class).retrieveFor(condition))
		{
			InterfaceWrapperHelper.delete(term);
		}
	}
}
