package de.metas.acct.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.ModelValidator;

/*
 * #%L
 * de.metas.acct.base
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

@Interceptor(I_GL_Distribution.class)
public class GL_Distribution
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_GL_Distribution glDistribution)
	{
		//
		// Update segment matcher values
		if (glDistribution.isAnyAcct() && glDistribution.getAccount_ID() > 0)
		{
			glDistribution.setAccount(null);
		}
		if (glDistribution.isAnyActivity() && glDistribution.getC_Activity_ID() > 0)
		{
			glDistribution.setC_Activity(null);
		}
		if (glDistribution.isAnyBPartner() && glDistribution.getC_BPartner_ID() > 0)
		{
			glDistribution.setC_BPartner(null);
		}
		if (glDistribution.isAnyCampaign() && glDistribution.getC_Campaign_ID() > 0)
		{
			glDistribution.setC_Campaign(null);
		}
		if (glDistribution.isAnyLocFrom() && glDistribution.getC_LocFrom_ID() > 0)
		{
			glDistribution.setC_LocFrom(null);
		}
		if (glDistribution.isAnyLocTo() && glDistribution.getC_LocTo_ID() > 0)
		{
			glDistribution.setC_LocTo(null);
		}
		if (glDistribution.isAnyOrg() && glDistribution.getOrg_ID() > 0)
		{
			glDistribution.setOrg(null);
		}
		if (glDistribution.isAnyOrgTrx() && glDistribution.getAD_OrgTrx_ID() > 0)
		{
			glDistribution.setAD_OrgTrx(null);
		}
		if (glDistribution.isAnyProduct() && glDistribution.getM_Product_ID() > 0)
		{
			glDistribution.setM_Product(null);
		}
		if (glDistribution.isAnyProject() && glDistribution.getC_Project_ID() > 0)
		{
			glDistribution.setC_Project(null);
		}
		if (glDistribution.isAnySalesRegion() && glDistribution.getC_SalesRegion_ID() > 0)
		{
			glDistribution.setC_SalesRegion(null);
		}
		if (glDistribution.isAnyUser1() && glDistribution.getUser1_ID() > 0)
		{
			glDistribution.setUser1(null);
		}
		if (glDistribution.isAnyUser2() && glDistribution.getUser2_ID() > 0)
		{
			glDistribution.setUser2(null);
		}
	}
}
