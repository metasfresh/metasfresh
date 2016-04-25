/**
 * 
 */
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
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.MTable;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.service.IParameterBL;
import de.metas.commission.custom.config.BaseConfig;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.I_C_AdvComRankCollection;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComSystemType;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ICommissionTermDAO;

/**
 * @author cg
 *
 */
public abstract class AbstractComissionTest
{
	protected Properties ctx;
	
	private I_C_Calendar calendar;

	protected I_C_AdvComRankCollection rankCollection;
	protected I_C_AdvCommissionSalaryGroup rank1;
	protected I_C_AdvCommissionSalaryGroup rankDefault;

	protected I_C_AdvComSystem comSystem;
	protected I_C_AdvComSystem_Type comSystemType;
	protected BaseConfig comSystemTypeParams;
	
	protected I_C_AdvCommissionCondition defaultConditions;
	protected I_C_AdvCommissionTerm defaultTerm;
	protected BaseConfig commissionTermParams;
	
	protected void createMasterData()
	{
		ctx = Env.getCtx();
		//
		// Setup default calendar
		{
			final PeriodsHelper periodHelper = new PeriodsHelper();
			this.calendar = periodHelper.createCalendar(ctx, "Test");
			periodHelper.createYear(calendar, 2013);
		}

		//
		// Default Rank Collection
		{
			this.rankCollection = InterfaceWrapperHelper.create(ctx, I_C_AdvComRankCollection.class, ITrx.TRXNAME_None);
			rankCollection.setName("Test");
			InterfaceWrapperHelper.save(rankCollection);
		}
		// Ranks
		{
			this.rank1 = createRank("Rank1", 10);
			this.rankDefault = rank1;
		}

		//
		// Default Commission System
		{
			this.comSystem = InterfaceWrapperHelper.create(ctx, I_C_AdvComSystem.class, ITrx.TRXNAME_None);
			comSystem.setName("Test");
			comSystem.setC_AdvComRankCollection_ID(rankCollection.getC_AdvComRankCollection_ID());
			InterfaceWrapperHelper.save(comSystem);
		}

		//
		// Commission System Type
		{
			this.comSystemType = InterfaceWrapperHelper.create(ctx, I_C_AdvComSystem_Type.class, ITrx.TRXNAME_None);
			comSystemType.setName("Test");
			comSystemType.setC_AdvComSystem_ID(comSystem.getC_AdvComSystem_ID());
			InterfaceWrapperHelper.save(comSystemType);

			// Params
			this.comSystemTypeParams = new BaseConfig();
			Services.get(IParameterBL.class).createParameters(comSystemType, comSystemTypeParams, MCAdvComSystemType.PARAM_TABLE);
		}

		//
		// Setup default Contract (Conditions)
		{
			this.defaultConditions = InterfaceWrapperHelper.create(ctx, I_C_AdvCommissionCondition.class, ITrx.TRXNAME_None);
			defaultConditions.setC_AdvComSystem_ID(comSystem.getC_AdvComSystem_ID());
			defaultConditions.setIsDefault(true);
			defaultConditions.setIsDefaultForOrphandedSponsors(true);
			defaultConditions.setC_Calendar_ID(calendar.getC_Calendar_ID());
			InterfaceWrapperHelper.save(defaultConditions);

			this.defaultTerm = InterfaceWrapperHelper.create(ctx, I_C_AdvCommissionTerm.class, ITrx.TRXNAME_None);
			defaultTerm.setC_AdvCommissionCondition_ID(defaultConditions.getC_AdvCommissionCondition_ID());
			defaultTerm.setC_AdvComSystem_Type_ID(comSystemType.getC_AdvComSystem_Type_ID());
			InterfaceWrapperHelper.save(defaultTerm);
			
			this.commissionTermParams = new BaseConfig();
			Services.get(IParameterBL.class).createParameters(defaultTerm, commissionTermParams, ICommissionTermDAO.PARAM_TABLE);
		}
	}

	/**
	 * create instance
	 * @param ol
	 * @param spSalesRep
	 * @param spCustomer
	 * @return
	 */
	protected IAdvComInstance createAdvComInstance(final I_C_OrderLine ol, final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		final IAdvComInstance inst = InterfaceWrapperHelper.create(ctx, IAdvComInstance.class, ITrx.TRXNAME_None);
		inst.setRecord_ID(ol.getC_OrderLine_ID());
		inst.setAD_Table_ID(MTable.getTable_ID(I_C_OrderLine.Table_Name));
		inst.setC_Sponsor_Customer_ID(spCustomer.getC_Sponsor_ID());
		inst.setC_Sponsor_SalesRep_ID(spSalesRep.getC_Sponsor_ID());
		InterfaceWrapperHelper.save(inst);
		return inst;
	}
	
	/**
	 * create orderLine
	 * @param PriceActual
	 * @param PriceList
	 * @param CommissionPoints
	 * @param qty
	 * @return
	 */
	protected I_C_OrderLine createOrderline (
			final BigDecimal PriceActual, 
			final BigDecimal PriceList, 
			final BigDecimal CommissionPoints, 
			final BigDecimal qty)
	{
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(product);
		
		final I_C_OrderLine ol = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, ITrx.TRXNAME_None);
		ol.setM_Product_ID(product.getM_Product_ID());
		ol.setQtyOrdered(qty);
		ol.setCommissionPoints(CommissionPoints);
		ol.setPriceList(PriceList);
		ol.setPriceActual(PriceActual);
		// 07090: not setting the priceUOM, because i think it's really note required here
		InterfaceWrapperHelper.save(ol);
		return ol;
	}
	
	/**
	 * create sponsor
	 * @param value
	 * @param parent
	 * @param assignContract
	 * @return
	 */
	protected I_C_Sponsor createSponsor(final String value, final I_C_Sponsor parent, final boolean assignContract)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, ITrx.TRXNAME_None);
		bpartner.setValue("BP_" + value);
		bpartner.setName("BP_" + value);
		InterfaceWrapperHelper.save(bpartner);

		final I_C_Sponsor sponsor = InterfaceWrapperHelper.create(ctx, I_C_Sponsor.class, ITrx.TRXNAME_None);
		sponsor.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		POJOWrapper.setInstanceName(sponsor, "Sponsor_" + bpartner.getValue());
		InterfaceWrapperHelper.save(sponsor);

		if (parent != null)
		{
			final I_C_Sponsor_SalesRep sponsorSalesRepHierachy = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, ITrx.TRXNAME_None);
			sponsorSalesRepHierachy.setC_Sponsor_ID(sponsor.getC_Sponsor_ID());
			sponsorSalesRepHierachy.setC_Sponsor_Parent_ID(parent.getC_Sponsor_ID());
			sponsorSalesRepHierachy.setSponsorSalesRepType(X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_Hierarchie);

			// In some places ValidFrom/ValidTo are required, so we cannot let them null
			sponsorSalesRepHierachy.setValidFrom(TimeUtil.getDay(1970, 1, 1));
			sponsorSalesRepHierachy.setValidTo(TimeUtil.getDay(2050, 1, 1));

			POJOWrapper.setInstanceName(sponsorSalesRepHierachy, "SSR_Hierarchy_" + POJOWrapper.getInstanceName(parent) + "-" + POJOWrapper.getInstanceName(sponsor));
			InterfaceWrapperHelper.save(sponsorSalesRepHierachy);
		}
		if (assignContract)
		{
			final I_C_Sponsor_SalesRep sponsorSalesRepContract = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, ITrx.TRXNAME_None);
			sponsorSalesRepContract.setC_Sponsor_ID(sponsor.getC_Sponsor_ID());

			sponsorSalesRepContract.setSponsorSalesRepType(X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP);
			sponsorSalesRepContract.setC_AdvCommissionCondition_ID(defaultConditions.getC_AdvCommissionCondition_ID());
			sponsorSalesRepContract.setC_BPartner_ID(bpartner.getC_BPartner_ID());

			// In some places ValidFrom/ValidTo are required, so we cannot let them null
			sponsorSalesRepContract.setValidFrom(TimeUtil.getDay(1970, 1, 1));
			sponsorSalesRepContract.setValidTo(TimeUtil.getDay(2050, 1, 1));

			POJOWrapper.setInstanceName(sponsorSalesRepContract, "SSR_Contract_" + POJOWrapper.getInstanceName(parent) + "-" + POJOWrapper.getInstanceName(sponsor));
			InterfaceWrapperHelper.save(sponsorSalesRepContract);
		}
		return sponsor;
	}

	protected void setManualRank(I_C_Sponsor sponsor, I_C_AdvCommissionSalaryGroup rank)
	{
		sponsor.setIsManualRank(true);
		sponsor.setC_AdvCommissionSalaryGroup_ID(rank.getC_AdvCommissionSalaryGroup_ID());
		InterfaceWrapperHelper.save(sponsor);
	}

	public I_C_AdvCommissionSalaryGroup createRank(final String value, final int seqNo)
	{
		I_C_AdvCommissionSalaryGroup rank = InterfaceWrapperHelper.newInstance(I_C_AdvCommissionSalaryGroup.class, rankCollection);
		rank.setC_AdvComRankCollection_ID(rankCollection.getC_AdvComRankCollection_ID());
		rank.setValue(value);
		rank.setName(value);
		rank.setSeqNo(seqNo);
		InterfaceWrapperHelper.save(rank);
		return rank;
	}

}
