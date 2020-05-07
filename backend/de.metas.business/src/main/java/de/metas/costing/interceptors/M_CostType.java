package de.metas.costing.interceptors;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostType;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostTypeId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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
@Interceptor(I_M_CostType.class)
public class M_CostType
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_M_CostType costType)
	{
		if (costType.getAD_Org_ID() != 0)
		{
			costType.setAD_Org_ID(0);
		}
	}	// beforeSave

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void beforeDelete(final I_M_CostType costType)
	{
		final CostTypeId costTypeId = CostTypeId.ofRepoId(costType.getM_CostType_ID());
		final ClientId clientId = ClientId.ofRepoId(costType.getAD_Client_ID());
		
		for (final AcctSchema as : Services.get(IAcctSchemaDAO.class).getAllByClient(clientId))
		{
			if (as.getCosting().getCostTypeId().equals(costTypeId))
			{
				throw new AdempiereException("@CannotDelete@ @C_AcctSchema_ID@");
			}
		}
	}	// beforeDelete

}
