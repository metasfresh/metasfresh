package de.metas.acct.model.validator;

import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.acct.api.GLDistributionNotValidException;
import org.adempiere.acct.api.IGLDistributionBL;
import org.adempiere.acct.api.IGLDistributionDAO;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.Services;
import org.compiere.model.I_GL_Distribution;
import org.compiere.model.I_GL_DistributionLine;
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

@Interceptor(I_GL_DistributionLine.class)
public class GL_DistributionLine
{
	private static final transient Logger logger = LogManager.getLogger(GL_DistributionLine.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validateLine(final I_GL_DistributionLine line)
	{
		if (line.getLine() == 0)
		{
			final I_GL_Distribution glDistribution = line.getGL_Distribution();
			final int lastLineNo = Services.get(IGLDistributionDAO.class).retrieveLastLineNo(glDistribution);
			final int lineNo = lastLineNo + 10;
			line.setLine(lineNo);
		}

		// Reset not selected Overwrite
		if (!line.isOverwriteAcct() && line.getAccount_ID() > 0)
		{
			line.setAccount_ID(0);
		}
		if (!line.isOverwriteActivity() && line.getC_Activity_ID() > 0)
		{
			line.setC_Activity(null);
		}
		if (!line.isOverwriteBPartner() && line.getC_BPartner_ID() > 0)
		{
			line.setC_BPartner(null);
		}
		if (!line.isOverwriteCampaign() && line.getC_Campaign_ID() > 0)
		{
			line.setC_Campaign(null);
		}
		if (!line.isOverwriteLocFrom() && line.getC_LocFrom_ID() > 0)
		{
			line.setC_LocFrom(null);
		}
		if (!line.isOverwriteLocTo() && line.getC_LocTo_ID() > 0)
		{
			line.setC_LocTo(null);
		}
		if (!line.isOverwriteOrg() && line.getOrg_ID() > 0)
		{
			line.setOrg(null);
		}
		if (!line.isOverwriteOrgTrx() && line.getAD_OrgTrx_ID() > 0)
		{
			line.setAD_OrgTrx(null);
		}
		if (!line.isOverwriteProduct() && line.getM_Product_ID() > 0)
		{
			line.setM_Product(null);
		}
		if (!line.isOverwriteProject() && line.getC_Project_ID() > 0)
		{
			line.setC_Project(null);
		}
		if (!line.isOverwriteSalesRegion() && line.getC_SalesRegion_ID() > 0)
		{
			line.setC_SalesRegion(null);
		}
		if (!line.isOverwriteUser1() && line.getUser1_ID() > 0)
		{
			line.setUser1(null);
		}
		if (!line.isOverwriteUser2() && line.getUser2_ID() > 0)
		{
			line.setUser2(null);
		}

		// Account Overwrite cannot be 0
		if (line.isOverwriteAcct() && line.getAccount_ID() <= 0)
		{
			throw new FillMandatoryException(I_GL_DistributionLine.COLUMNNAME_Account_ID);
		}
		// Org Overwrite cannot be 0
		if (line.isOverwriteOrg() && line.getOrg_ID() <= 0)
		{
			throw new FillMandatoryException(I_GL_DistributionLine.COLUMNNAME_Org_ID);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void validateGLDistribution(final I_GL_DistributionLine line)
	{
		final I_GL_Distribution glDistribution = line.getGL_Distribution();
		try
		{
			final IGLDistributionBL glDistributionBL = Services.get(IGLDistributionBL.class);
			glDistributionBL.validate(glDistribution);
		}
		catch (final GLDistributionNotValidException e)
		{
			// NOTE: don't propagate the exception because we flagged the glDistribution as not valid, and we want to persist that.
			logger.info("", e);
		}
	}

}
