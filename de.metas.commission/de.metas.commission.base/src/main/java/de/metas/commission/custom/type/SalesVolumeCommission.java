package de.metas.commission.custom.type;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.PO;

import de.metas.adempiere.model.IProductAware;
import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionFactCand;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.ICommissionTermDAO;
import de.metas.commission.service.ICommissionTypeDAO;
import de.metas.commission.service.IContractBL;
import de.metas.commission.service.IFieldAccessBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;

/**
 * Assigns a commission based on the overall number of sales. I.e. this type is independent from the sponsor hierarchy!
 * 
 * @author rc, ts
 * 
 */
public class SalesVolumeCommission extends BaseCommission
{

	@Override
	public BigDecimal getCommissionPointsSum(final IAdvComInstance inst, final String status, final Timestamp date, final Object po)
	{
		final ICommissionContext commissionCtx = mkCommissionCtx(inst, date);

		final String paramName = ConfigParams.PARAM_COMMISSION_POINTS;
		final IContractBL contractBL = Services.get(IContractBL.class);

		if (!contractBL.hasSponsorParam(commissionCtx, paramName))
		{
			// use super implementation instead
			return super.getCommissionPointsSum(inst, status, date, po);
		}
		final BigDecimal pointsAbs = (BigDecimal)contractBL.retrieveSponsorParam(commissionCtx, paramName);
		if (pointsAbs.signum() <= 0)
		{
			// use super implementation instead
			return super.getCommissionPointsSum(inst, status, date, po);
		}
		
		final IFieldAccessBL fieldAccessBL = Services.get(IFieldAccessBL.class);
		final BigDecimal qty = fieldAccessBL.getQty(po);
		
		return pointsAbs.multiply(qty);
	}

	@Override
	public BigDecimal getFactor()
	{
		return BigDecimal.ONE;
	}

	@Override
	public BigDecimal getPercent(final IAdvComInstance inst, final String status, final Timestamp date)
	{
		final ICommissionContext commissionCtx = mkCommissionCtx(inst, date);

		final String paramName = ConfigParams.PARAM_COMMISSION_PERCENT;

		final IContractBL contractBL = Services.get(IContractBL.class);

		if (!contractBL.hasSponsorParam(commissionCtx, paramName))
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal percent = (BigDecimal)contractBL.retrieveSponsorParam(commissionCtx, paramName);

		return percent;
	}

	public ICommissionContext mkCommissionCtx(final IAdvComInstance inst, final Timestamp date)
	{
		final Object poLine = Services.get(ICommissionInstanceDAO.class).retrievePO(inst, Object.class);
		Check.assumeNotNull(poLine, "poLine with AD_Table_ID=" + inst.getAD_Table_ID() + " and Record_ID=" + inst.getRecord_ID() + " not null");

		final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
		final I_M_Product product = productAware.getM_Product();

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(inst.getC_Sponsor_SalesRep(), date, this, product);
		return commissionCtx;
	}

	@Override
	public IParameterizable getInstanceParams(final Properties ctx, final I_C_AdvComSystem system, final String trxName)
	{
		return new BaseConfig();
	}

	@Override
	public IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName)
	{
		final BaseConfig config = new BaseConfig();

		config.addNewParam(ConfigParams.PARAM_COMMISSION_PERCENT, "Provision %", "", 10, BigDecimal.ZERO);
		config.addNewParam(ConfigParams.PARAM_COMMISSION_POINTS, "Provisionspkte Abs.", "", 20, BigDecimal.ONE.negate());

		return config;
	}

	@Override
	public boolean isCommissionCalculated()
	{
		return true;
	}

	@Override
	void createInstanceAndFact(
			final MCAdvCommissionFactCand cand,
			final PO poLine,
			final String status,
			final int adPInstanceId)
	{
		final IProductAware productAware = InterfaceWrapperHelper.create(poLine, IProductAware.class);
		final I_M_Product product = productAware.getM_Product();

		final I_C_BPartner customer = cand.retrieveBPartner();
		Check.assumeNotNull(customer, "customer C_BPartner of " + cand + " is not null");
		final I_C_Sponsor customerSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(customer, true);

		final Properties ctx = InterfaceWrapperHelper.getCtx(cand);
		final String trxName = InterfaceWrapperHelper.getTrxName(cand);

		final I_C_AdvCommissionType commissionType = Services.get(ICommissionTypeDAO.class).retrieveForClass(ctx, getClass(), cand.getAD_Org_ID(), trxName);

		final List<I_C_AdvComSystem_Type> types = Services.get(ICommissionTypeDAO.class).retrieveSystemTypesForCommissionType(commissionType);

		for (final I_C_AdvComSystem_Type type : types)
		{
			final List<I_C_AdvCommissionTerm> terms = Services.get(ICommissionTermDAO.class).retrieveTermsForProductAndSystemType(type, product);

			for (final I_C_AdvCommissionTerm term : terms)
			{
				if (term.getM_Product_Category_ID() == 0 && term.getM_Product_ID() == 0)
				{
					continue; // for now, we don't give this kind of commission "for everything"
				}

				Check.assume(term.getM_Product_ID() == product.getM_Product_ID() || term.getM_Product_Category_ID() == product.getM_Product_Category_ID(), "{0} matches {1}", term, product);

				final List<I_C_Sponsor_SalesRep> salesRepSSRs = Services.get(ISponsorDAO.class).retrieveSponsorSalesRepsForCondition(ctx, term.getC_AdvCommissionCondition(), trxName);

				for (final I_C_Sponsor_SalesRep salesRepSSR : salesRepSSRs)
				{
					final I_C_Sponsor salesRepSponsor = salesRepSSR.getC_Sponsor();

					// check if there is an existing instance
					createOrExtendInstance(cand, salesRepSponsor, customerSponsor, term, poLine, adPInstanceId);
				}
			}
		}
	}

	private void createOrExtendInstance(
			final I_C_AdvCommissionFactCand cand,
			final I_C_Sponsor salesRepSponsor,
			final I_C_Sponsor customerSponsor,
			final I_C_AdvCommissionTerm term,
			final Object poLine,
			final int adPInstanceId)
	{
		if (InterfaceWrapperHelper.isInstanceOf(poLine, I_C_InvoiceLine.class))
		{
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(poLine, I_C_InvoiceLine.class);
			final I_C_OrderLine ol = il.getC_OrderLine();

			final IFieldAccessBL fieldAccessBL = Services.get(IFieldAccessBL.class);

			if (ol != null && ol.getC_OrderLine_ID() > 0 && !fieldAccessBL.isCreditMemo(il))
			{
				// il has an ol and is thus not an instance trigger.
				// Retrieve the ol's instances
				final List<IAdvComInstance> instancesOfOl = Services.get(ICommissionInstanceDAO.class).retrieveAllFor(ol, getComSystemType());

				// Now update the ol's instance with the invoice line
				for (final IAdvComInstance instance : instancesOfOl)
				{
					final Timestamp dateToUse = Services.get(ISponsorBL.class).retrieveDateTo(salesRepSponsor, getComSystemType(), instance.getDateDoc());

					// also update the instance with the ol. This is required if the invoice is created during the
					// order's completeIt process
					BaseCommission.commissionFactBL.recordInstanceTrigger(this, cand, dateToUse, ol, instance, adPInstanceId);
					BaseCommission.commissionFactBL.recordInstanceTrigger(this, cand, dateToUse, il, instance, adPInstanceId);
				}

				return;
			}
		}

		final IAdvComInstance instance =
				Services.get(ICommissionInstanceDAO.class).retrieveNonClosedFor(poLine, customerSponsor, salesRepSponsor, term.getC_AdvCommissionTerm_ID());
		if (instance == null)
		{
			BaseCommission.commissionFactBL.createInstanceAndFact(
					term,
					cand,
					poLine,
					customerSponsor,
					salesRepSponsor,
					-1,
					-1,
					-1,
					false,
					adPInstanceId);
		}
		else
		{
			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
			final Timestamp dateToUse = sponsorBL.retrieveDateTo(salesRepSponsor, getComSystemType(), cand.getDateAcct());
			BaseCommission.commissionFactBL.recordInstanceTrigger(this, cand, dateToUse, poLine, instance, adPInstanceId);
		}
	}
}
