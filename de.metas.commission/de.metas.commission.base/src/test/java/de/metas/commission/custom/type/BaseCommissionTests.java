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


import static org.hamcrest.Matchers.comparesEqualTo;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.adempiere.service.IParameterBL;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComSystemType;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvComSystem_Type;
import de.metas.commission.service.ICommissionTermDAO;

/**
 * @author cg
 *
 */
public class BaseCommissionTests extends AbstractComissionTest
{
	private I_C_Sponsor spSalesRep11;
	private I_C_Sponsor spSalesRep12;
	private I_C_Sponsor spSalesRep13;
	
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
		//
		// Create test sponsors
		spSalesRep11 = createSponsor("SalesRep11", null, true);
		spSalesRep12 = createSponsor("SalesRep12", spSalesRep11, true);
		spSalesRep13 = createSponsor("SalesRep13", spSalesRep12, true);
	}

	
	@Test
	public void test_CommissionPoints()
	{
		comSystemTypeParams.addNewParam(ConfigParams.NAME_LEVEL_MAX, "", "", 10, 10);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "", "", 20, false);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT, "", "", 30, false);

		comSystemTypeParams.addNewParam(ConfigParams.PARAM_MAX_COMMISSION, "", "", 50, new BigDecimal("1000"));
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, "", "", 60, false);
		Services.get(IParameterBL.class).createParameters(comSystemType, comSystemTypeParams, MCAdvComSystemType.PARAM_TABLE);

		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rankDefault.getValue(), "", "", 40, new BigDecimal("10"));
		Services.get(IParameterBL.class).createParameters(defaultTerm, commissionTermParams, ICommissionTermDAO.PARAM_TABLE);

		final I_C_Sponsor spCustomer = createSponsor("Customer_11", spSalesRep13, false);

		assertCommissionPointsSumBrutto(spSalesRep13, spCustomer);
		assertCommissionPointsSumBrutto(spSalesRep12, spCustomer);
		assertCommissionPointsSumBrutto(spSalesRep11, spCustomer);
		//
		assertCommissionPointsSumNetto(spSalesRep13, spCustomer);
		assertCommissionPointsSumNetto(spSalesRep12, spCustomer);
		assertCommissionPointsSumNetto(spSalesRep11, spCustomer);
		//
		assertCommissionPointsSumNettoDiscount(spSalesRep13, spCustomer);
		assertCommissionPointsSumNettoDiscount(spSalesRep12, spCustomer);
		assertCommissionPointsSumNettoDiscount(spSalesRep11, spCustomer);
	}
	
	
	@Test
	public void test_CommissionPointsManual()
	{
		comSystemTypeParams.addNewParam(ConfigParams.NAME_LEVEL_MAX, "", "", 10, 10);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_HIERARCHY_SKIP_CUSTOMERS, "", "", 20, false);
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_SUBTRACT_POLINE_DISCOUNT, "", "", 30, false);

		comSystemTypeParams.addNewParam(ConfigParams.PARAM_MAX_COMMISSION, "", "", 50, new BigDecimal("1000"));
		comSystemTypeParams.addNewParam(ConfigParams.PARAM_STOP_AFTER_FIRST_SALES_REP, "", "", 60, false);
		Services.get(IParameterBL.class).createParameters(comSystemType, comSystemTypeParams, MCAdvComSystemType.PARAM_TABLE);

		commissionTermParams.addNewParam(ConfigParams.PARAM_COMMISSION_ + rankDefault.getValue(), "", "", 40, new BigDecimal("10"));
		Services.get(IParameterBL.class).createParameters(defaultTerm, commissionTermParams, ICommissionTermDAO.PARAM_TABLE);

		final I_C_Sponsor spCustomer = createSponsor("Customer_11", spSalesRep13, false);

		assertCommissionPointsSumBruttoManual(spSalesRep13, spCustomer);
		assertCommissionPointsSumBruttoManual(spSalesRep12, spCustomer);
		assertCommissionPointsSumBruttoManual(spSalesRep11, spCustomer);
		//
		assertCommissionPointsSumNettoManual(spSalesRep13, spCustomer);
		assertCommissionPointsSumNetto(spSalesRep12, spCustomer);
		assertCommissionPointsSumNettoManual(spSalesRep11, spCustomer);
		//
		assertCommissionPointsSumNettoDiscountManual(spSalesRep13, spCustomer);
		assertCommissionPointsSumNettoDiscountManual(spSalesRep12, spCustomer);
		assertCommissionPointsSumNettoDiscountManual(spSalesRep11, spCustomer);
	}


	
	public void assertCommissionPointsSumBrutto(final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		comSystemType.setUseGrossOrNetPoints(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Brutto);
		InterfaceWrapperHelper.save(comSystemType);
		
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final Timestamp dateToUse = TimeUtil.getDay(2013, 7, 29);
		
		final I_C_OrderLine ol = createOrderline(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(2));
		
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final IAdvComInstance inst = createAdvComInstance(ol, spSalesRep, spCustomer);
		
		final BigDecimal commissionPointsSum =  commission.getCommissionPointsSum(inst, salesRepFactStatus, dateToUse, ol);

		Assert.assertThat("Invalid commission Points calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				commissionPointsSum, comparesEqualTo(BigDecimal.valueOf(200)));
	}
	
	public void assertCommissionPointsSumBruttoManual(final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		comSystemType.setUseGrossOrNetPoints(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Brutto);
		InterfaceWrapperHelper.save(comSystemType);
		
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final Timestamp dateToUse = TimeUtil.getDay(2013, 7, 29);
		
		final I_C_OrderLine ol = createOrderline(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(2));
		ol.setIsManualCommissionPoints(true);
		ol.setCommissionPoints(BigDecimal.valueOf(50));
		InterfaceWrapperHelper.save(ol);
		
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final IAdvComInstance inst = createAdvComInstance(ol, spSalesRep, spCustomer);
		
		final BigDecimal commissionPointsSum =  commission.getCommissionPointsSum(inst, salesRepFactStatus, dateToUse, ol);

		Assert.assertThat("Invalid commission Points calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				commissionPointsSum, comparesEqualTo(BigDecimal.valueOf(100)));

	}
	
	public void assertCommissionPointsSumNetto(final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		comSystemType.setUseGrossOrNetPoints(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Netto);
		InterfaceWrapperHelper.save(comSystemType);
		
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final Timestamp dateToUse = TimeUtil.getDay(2013, 7, 29);
		
		final I_C_OrderLine ol = createOrderline(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(2));
		
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final IAdvComInstance inst = createAdvComInstance(ol, spSalesRep, spCustomer);
		
		final BigDecimal commissionPointsSum =  commission.getCommissionPointsSum(inst, salesRepFactStatus, dateToUse, ol);

		Assert.assertThat("Invalid commission Points calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				commissionPointsSum, comparesEqualTo(BigDecimal.valueOf(200)));
	}
	
	public void assertCommissionPointsSumNettoManual(final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		comSystemType.setUseGrossOrNetPoints(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Netto);
		InterfaceWrapperHelper.save(comSystemType);
		
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final Timestamp dateToUse = TimeUtil.getDay(2013, 7, 29);
		
		final I_C_OrderLine ol = createOrderline(BigDecimal.valueOf(10), BigDecimal.valueOf(10), BigDecimal.valueOf(100), BigDecimal.valueOf(2));
		ol.setIsManualCommissionPoints(true);
		ol.setCommissionPoints(BigDecimal.valueOf(50));
		InterfaceWrapperHelper.save(ol);
		
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final IAdvComInstance inst = createAdvComInstance(ol, spSalesRep, spCustomer);
		
		final BigDecimal commissionPointsSum =  commission.getCommissionPointsSum(inst, salesRepFactStatus, dateToUse, ol);

		Assert.assertThat("Invalid commission Points calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				commissionPointsSum, comparesEqualTo(BigDecimal.valueOf(100)));
	}
	
	public void assertCommissionPointsSumNettoDiscount(final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		comSystemType.setUseGrossOrNetPoints(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Netto);
		InterfaceWrapperHelper.save(comSystemType);
		
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final Timestamp dateToUse = TimeUtil.getDay(2013, 7, 29);
		
		final I_C_OrderLine ol = createOrderline(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(100), BigDecimal.valueOf(2));
		
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final IAdvComInstance inst = createAdvComInstance(ol, spSalesRep, spCustomer);
		
		final BigDecimal commissionPointsSum =  commission.getCommissionPointsSum(inst, salesRepFactStatus, dateToUse, ol);

		Assert.assertThat("Invalid commission Points calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				commissionPointsSum, comparesEqualTo(BigDecimal.valueOf(100)));
	}
	
	public void assertCommissionPointsSumNettoDiscountManual(final I_C_Sponsor spSalesRep, final I_C_Sponsor spCustomer)
	{
		comSystemType.setUseGrossOrNetPoints(X_C_AdvComSystem_Type.USEGROSSORNETPOINTS_Netto);
		InterfaceWrapperHelper.save(comSystemType);
		
		final ICommissionType commission = new HierarchyDiffCommission();
		commission.setComSystemType(comSystemType);
		final Timestamp dateToUse = TimeUtil.getDay(2013, 7, 29);
		
		final I_C_OrderLine ol = createOrderline(BigDecimal.valueOf(10), BigDecimal.valueOf(20), BigDecimal.valueOf(100), BigDecimal.valueOf(2));
		ol.setIsManualCommissionPoints(true);
		ol.setCommissionPoints(BigDecimal.valueOf(50));
		InterfaceWrapperHelper.save(ol);
		
		final String salesRepFactStatus = X_C_AdvComSalesRepFact.STATUS_Prognose;

		final IAdvComInstance inst = createAdvComInstance(ol, spSalesRep, spCustomer);
		
		final BigDecimal commissionPointsSum =  commission.getCommissionPointsSum(inst, salesRepFactStatus, dateToUse, ol);

		Assert.assertThat("Invalid commission Points calculated for SalesRep=" + POJOWrapper.getInstanceName(spSalesRep) + ", Customer=" + POJOWrapper.getInstanceName(spCustomer),
				commissionPointsSum, comparesEqualTo(BigDecimal.valueOf(50)));
	}
	
}
