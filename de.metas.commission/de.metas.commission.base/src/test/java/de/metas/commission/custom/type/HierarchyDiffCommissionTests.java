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


import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.MTable;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.service.IParameterBL;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvComSystemType;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ICommissionTermDAO;

/**
 * Test {@link HierarchyCommission#getPercent(IAdvComInstance, String, Timestamp)}
 * 
 * @author tsa
 * 
 */
public class HierarchyDiffCommissionTests extends AbstractComissionTest
{
	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}


	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		createMasterData();

		// the other ranks
		this.rank2 = createRank("Rank2", 20);
		this.rank3 = createRank("Rank3", 30);
		this.rank4 = createRank("Rank4", 40);
		
		//
		// Create test sponsors
		spSalesRep11 = createSponsor("SalesRep11", null, true);
		spSalesRep12 = createSponsor("SalesRep12", spSalesRep11, true);
		spSalesRep13 = createSponsor("SalesRep13", spSalesRep12, true);
		spSalesRep14 = createSponsor("SalesRep14", spSalesRep13, true);
		spSalesRep15 = createSponsor("SalesRep15", spSalesRep14, true);
		spSalesRep16 = createSponsor("SalesRep16", spSalesRep15, true);

	}

	private I_C_AdvCommissionSalaryGroup rank2;
	private I_C_AdvCommissionSalaryGroup rank3;
	private I_C_AdvCommissionSalaryGroup rank4;
	
	private I_C_Sponsor spSalesRep11;
	private I_C_Sponsor spSalesRep12;
	private I_C_Sponsor spSalesRep13;
	private I_C_Sponsor spSalesRep14;
	private I_C_Sponsor spSalesRep15;
	private I_C_Sponsor spSalesRep16;



	@Test
	public void test_sameRankForAll()
	{
		comSystemTypeParams.addNewParam(ConfigParams.NAME_LEVEL_MAX, "", "", 10, 10);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "", "", 20, false);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT, "", "", 30, false);

		comSystemTypeParams.addNewParam(ConfigParams.PARAM_MAX_COMMISSION, "", "", 50, new BigDecimal("1000"));
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, "", "", 60, false);
		Services.get(IParameterBL.class).createParameters(comSystemType, comSystemTypeParams, MCAdvComSystemType.PARAM_TABLE);

		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rankDefault.getValue(), "", "", 40, new BigDecimal("10"));
		Services.get(IParameterBL.class).createParameters(defaultTerm, commissionTermParams, ICommissionTermDAO.PARAM_TABLE);

		final I_C_Sponsor spCustomer = createSponsor("Customer_11", spSalesRep16, false);

		assertCommissionPercent(new BigDecimal("10"), spSalesRep16, spCustomer);
		assertCommissionPercent(new BigDecimal("0"), spSalesRep15, spCustomer);
		assertCommissionPercent(new BigDecimal("0"), spSalesRep14, spCustomer);
		assertCommissionPercent(new BigDecimal("0"), spSalesRep13, spCustomer);
		assertCommissionPercent(new BigDecimal("0"), spSalesRep12, spCustomer);
		assertCommissionPercent(new BigDecimal("0"), spSalesRep11, spCustomer);
	}

	@Test
	public void test_4ranks()
	{
		comSystemTypeParams.addNewParam(ConfigParams.NAME_LEVEL_MAX, "", "", 10, 10);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "", "", 20, false);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT, "", "", 30, false);

		comSystemTypeParams.addNewParam(ConfigParams.PARAM_MAX_COMMISSION, "", "", 50, new BigDecimal("1000"));
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, "", "", 60, false);
		Services.get(IParameterBL.class).createParameters(comSystemType, comSystemTypeParams, MCAdvComSystemType.PARAM_TABLE);

		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank1.getValue(), "", "", 41, new BigDecimal("7"));
		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank2.getValue(), "", "", 42, new BigDecimal("10"));
		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank3.getValue(), "", "", 43, new BigDecimal("25"));
		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank4.getValue(), "", "", 44, new BigDecimal("51"));
		Services.get(IParameterBL.class).createParameters(defaultTerm, commissionTermParams, ICommissionTermDAO.PARAM_TABLE);

		
		setManualRank(spSalesRep16, rank1);
		setManualRank(spSalesRep15, rank2);
		setManualRank(spSalesRep14, rank3);
		setManualRank(spSalesRep13, rank4);

		final I_C_Sponsor spCustomer = createSponsor("Customer_11", spSalesRep16, false);

		// NOTE: this assumption are failing because we are always calculating the percent as "current level percent" - "prev level percent"
		// agreed with Tobi to leave it as is
		// assertCommissionPercent(new BigDecimal(7), spSalesRep16, spCustomer);
		// assertCommissionPercent(new BigDecimal(10 - 7), spSalesRep15, spCustomer);
		// assertCommissionPercent(new BigDecimal(25 - 10 - 7), spSalesRep14, spCustomer);
		// assertCommissionPercent(new BigDecimal(51 - 25 - 10 - 7), spSalesRep13, spCustomer);
		// assertCommissionPercent(new BigDecimal(0), spSalesRep12, spCustomer);
		// assertCommissionPercent(new BigDecimal(0), spSalesRep11, spCustomer);

		assertCommissionPercent(new BigDecimal(7), spSalesRep16, spCustomer);
		assertCommissionPercent(new BigDecimal(10 - 7), spSalesRep15, spCustomer);
		assertCommissionPercent(new BigDecimal(25 - 10), spSalesRep14, spCustomer);
		assertCommissionPercent(new BigDecimal(51 - 25), spSalesRep13, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep12, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep11, spCustomer);

	}

	@Test
	public void test_StopAfterFirstSalesRep()
	{
		comSystemTypeParams.addNewParam(ConfigParams.NAME_LEVEL_MAX, "", "", 10, 10);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "", "", 20, false);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT, "", "", 30, false);

		comSystemTypeParams.addNewParam(ConfigParams.PARAM_MAX_COMMISSION, "", "", 50, new BigDecimal("1000"));

		comSystemTypeParams.addNewParam(ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, "", "", 60, true);

		Services.get(IParameterBL.class).createParameters(comSystemType, comSystemTypeParams, MCAdvComSystemType.PARAM_TABLE);

		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank1.getValue(), "", "", 41, new BigDecimal("5"));
		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank2.getValue(), "", "", 42, new BigDecimal("10"));
		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank3.getValue(), "", "", 43, new BigDecimal("20"));
		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rank4.getValue(), "", "", 44, new BigDecimal("30"));
		Services.get(IParameterBL.class).createParameters(defaultTerm, commissionTermParams, ICommissionTermDAO.PARAM_TABLE);
		
		setManualRank(spSalesRep16, rank1);
		setManualRank(spSalesRep15, rank2);
		setManualRank(spSalesRep14, rank3);
		setManualRank(spSalesRep13, rank4);

		final I_C_Sponsor spCustomer = createSponsor("Customer_11", spSalesRep16, false);

		assertCommissionPercent(new BigDecimal(5), spSalesRep16, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep15, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep14, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep13, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep12, spCustomer);
		assertCommissionPercent(new BigDecimal(0), spSalesRep11, spCustomer);
	}

	public void assertCommissionPercent(final BigDecimal percentExpected, final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final String status = X_C_AdvComSalesRepFact.STATUS_Prov_Relevant;
		final Timestamp date = TimeUtil.getDay(2013, 7, 29);

		I_M_Product product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(product);

		I_C_OrderLine ol = InterfaceWrapperHelper.create(ctx, I_C_OrderLine.class, Trx.TRXNAME_None);
		ol.setM_Product_ID(product.getM_Product_ID());
		InterfaceWrapperHelper.save(ol);

		final IAdvComInstance inst = InterfaceWrapperHelper.create(ctx, IAdvComInstance.class, Trx.TRXNAME_None);
		inst.setRecord_ID(ol.getC_OrderLine_ID());
		inst.setAD_Table_ID(MTable.getTable_ID(I_C_OrderLine.Table_Name));

		inst.setC_Sponsor_Customer_ID(spCustomer.getC_Sponsor_ID());
		inst.setC_Sponsor_SalesRep_ID(spSalesRep.getC_Sponsor_ID());

		final BigDecimal percentActual = commission.getPercent(inst, status, date);

		Assert.assertThat("Invalid percent calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				percentActual, comparesEqualTo(percentExpected));
	}

	protected I_C_Sponsor createSponsor(final String value, final I_C_Sponsor parent, final boolean assignContract)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, I_C_BPartner.class, Trx.TRXNAME_None);
		bpartner.setValue("BP_" + value);
		bpartner.setName("BP_" + value);
		InterfaceWrapperHelper.save(bpartner);

		final I_C_Sponsor sponsor = InterfaceWrapperHelper.create(ctx, I_C_Sponsor.class, Trx.TRXNAME_None);
		sponsor.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		POJOWrapper.setInstanceName(sponsor, "Sponsor_" + bpartner.getValue());
		InterfaceWrapperHelper.save(sponsor);

		if (parent != null)
		{
			final I_C_Sponsor_SalesRep sponsorSalesRepHierachy = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, Trx.TRXNAME_None);
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
			final I_C_Sponsor_SalesRep sponsorSalesRepContract = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, Trx.TRXNAME_None);
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

	@After
	public void dumpDatabase()
	{
		// POJOLookupMap.get().dumpStatus();
	}
}
