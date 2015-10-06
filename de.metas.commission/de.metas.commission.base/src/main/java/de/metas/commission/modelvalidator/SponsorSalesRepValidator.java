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


import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.I_C_VAT_SmallBusiness;
import de.metas.commission.model.MCVATSmallBusiness;
import de.metas.commission.service.ISponsorDAO;

public class SponsorSalesRepValidator implements ModelValidator
{

	private int ad_Client_ID = -1;

	private final CLogger logger = CLogger.getCLogger(SponsorSalesRepValidator.class);

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addModelChange(I_C_Sponsor_SalesRep.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		final I_C_Sponsor_SalesRep ssr = InterfaceWrapperHelper.create(po, I_C_Sponsor_SalesRep.class);

		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			//
			// 02224: make sure that either C_BPartner or a parent sponsor needs to be selected,
			// but not both
			if (po.is_ValueChanged(I_C_Sponsor_SalesRep.COLUMNNAME_C_BPartner_ID)
					|| po.is_ValueChanged(I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID))
			{
				if (ssr.getC_BPartner_ID() <= 0 && ssr.getC_Sponsor_Parent_ID() <= 0)
				{
					throw new AdempiereException("@OneFieldMandatory@" + " " + I_C_Sponsor_SalesRep.COLUMNNAME_C_BPartner_ID + " @OR@ " + I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID);
				}
				if (ssr.getC_BPartner_ID() > 0 && ssr.getC_Sponsor_Parent_ID() > 0)
				{
					// no need for a user friendly message, because this should basically be prohibited by the GUI
					throw new AdempiereException(I_C_Sponsor_SalesRep.COLUMNNAME_C_BPartner_ID + " and " + I_C_Sponsor_SalesRep.COLUMNNAME_C_Sponsor_Parent_ID + " may not be set at the same time");
				}
			}

			//
			// 02224: make sure that if a C_BPartner is selected,
			// there should also be a contract
			if (type == ModelValidator.TYPE_BEFORE_NEW
					|| po.is_ValueChanged(I_C_Sponsor_SalesRep.COLUMNNAME_C_BPartner_ID)
					|| po.is_ValueChanged(I_C_Sponsor_SalesRep.COLUMNNAME_C_AdvCommissionCondition_ID))
			{
				if (ssr.getC_BPartner_ID() > 0)
				{
					if (ssr.getC_AdvCommissionCondition_ID() <= 0)
					{
						throw new FillMandatoryException(I_C_Sponsor_SalesRep.COLUMNNAME_C_AdvCommissionCondition_ID);
					}
				}
				else
				{
					ssr.setC_AdvCommissionCondition_ID(0);
				}
			}
		}

		if (type == ModelValidator.TYPE_AFTER_NEW || type == ModelValidator.TYPE_AFTER_CHANGE || type == ModelValidator.TYPE_AFTER_DELETE)
		{
			updateBPartnerIsSalesRep(ssr);
		}
		if (type == ModelValidator.TYPE_AFTER_NEW || type == ModelValidator.TYPE_AFTER_CHANGE)
		{
			checkSSRs(ssr, ISponsorDAO.WHERE_SPONSORSALESREP_SALESREP);
		}
		return null;
	}

	private void updateBPartnerIsSalesRep(final I_C_Sponsor_SalesRep ssr)
	{
		if (ssr.getC_BPartner_ID() <= 0)
		{
			return; // nothing to do
		}

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ssr.getC_BPartner(), I_C_BPartner.class);

		final List<I_C_Sponsor> sponsors = Services.get(ISponsorDAO.class).retrieveForSalesRepAt(bPartner, SystemTime.asTimestamp());

		final boolean isSalesrepOld = bPartner.isSalesRep();

		final boolean isSalesRepNew = !sponsors.isEmpty();
		bPartner.setIsSalesRep(isSalesRepNew);

		InterfaceWrapperHelper.save(bPartner);

		if (isSalesRepNew && !isSalesrepOld)
		{
			logger.info("BP " + bPartner.getValue()
					+ " just became sales rep; Making sure there is a record in "
					+ I_C_VAT_SmallBusiness.Table_Name);

			final Properties ctx = InterfaceWrapperHelper.getCtx(ssr);
			final String trxName = InterfaceWrapperHelper.getTrxName(ssr);

			final boolean isTaxExempt = MCVATSmallBusiness.retrieveIsTaxExempt(bPartner, Env.getContextAsDate(ctx, "#Date"));
			if (!isTaxExempt)
			{
				final MCVATSmallBusiness newTaxExempt = new MCVATSmallBusiness(ctx, 0, trxName);
				newTaxExempt.setC_BPartner_ID(bPartner.getC_BPartner_ID());
				newTaxExempt.setVATaxID(bPartner.getVATaxID());
				newTaxExempt.saveEx();
			}
		}
	}

	private void checkSSRs(final I_C_Sponsor_SalesRep ssr, final String whereClause)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(ssr);
		final String trxName = InterfaceWrapperHelper.getTrxName(ssr);

		final List<I_C_Sponsor_SalesRep> seList = Services.get(ISponsorDAO.class).retrieveSSRs(ctx, whereClause, ssr.getC_Sponsor_ID(), trxName);
		// TODO: retrieveSSR nur wenn equals
		if (ISponsorDAO.WHERE_SPONSORSALESREP_PARENT_SPONSOR.equals(whereClause) && seList.size() > 1)
		{
			final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, ssr.getC_Sponsor().getC_BPartner_ID(), I_C_BPartner.class, trxName);

			logger.info("Making C_BPartner.C_Sponsor_Parent_ID read-only for " + bPartner);
			bPartner.setIsParentSponsorReadWrite(false);
			InterfaceWrapperHelper.save(bPartner);
		}
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
	}
}
