package de.metas.commission.service.impl;

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
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvCommissionFactCand;
import de.metas.commission.model.X_C_Sponsor_SalesRep;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ISponsorBL;

public class SponsorBLTest extends SponsorBLTestBase
{
	public static class MockedCommissionType implements ICommissionType
	{
		@Override
		public void evaluateCandidate(MCAdvCommissionFactCand candidate, String status, int adPinstanceId)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void setComSystemType(I_C_AdvComSystem_Type comSystemType)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public I_C_AdvComSystem_Type getComSystemType()
		{
			return systemType;
		}

		@Override
		public BigDecimal getFactor()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public BigDecimal getPercent(IAdvComInstance inst, String status, Timestamp date)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public BigDecimal getCommissionPointsSum(IAdvComInstance inst, String status, Timestamp date, Object po)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public IParameterizable getInstanceParams(Properties ctx, I_C_AdvComSystem system, String trxName)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public IParameterizable getSponsorParams(Properties ctx, I_C_AdvCommissionCondition contract, String trxName)
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isCommissionCalculated()
		{
			throw new UnsupportedOperationException();
		}
	}

	private I_C_AdvCommissionCondition defaultConditions;

	@Before()
	public void setupSponsorAndConditions()
	{
		date = SystemTime.asTimestamp();

		commissionType = new MockedCommissionType();

		system = InterfaceWrapperHelper.create(ctx, I_C_AdvComSystem.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(system);

		product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(product);

		systemType = InterfaceWrapperHelper.create(ctx, I_C_AdvComSystem_Type.class, Trx.TRXNAME_None);
		systemType.setC_AdvComSystem_ID(system.getC_AdvComSystem_ID());
		InterfaceWrapperHelper.save(systemType);

		defaultConditions = InterfaceWrapperHelper.create(ctx, I_C_AdvCommissionCondition.class, Trx.TRXNAME_None);
		defaultConditions.setC_AdvComSystem(system);
		InterfaceWrapperHelper.save(defaultConditions);

		sponsor = InterfaceWrapperHelper.create(ctx, I_C_Sponsor.class, Trx.TRXNAME_None);
		POJOWrapper.setInstanceName(sponsor, "Sponsor_" + "testRetrieveTerm_Product");
		InterfaceWrapperHelper.save(sponsor);

		final I_C_Sponsor_SalesRep sponsorSalesRepContract = InterfaceWrapperHelper.create(ctx, I_C_Sponsor_SalesRep.class, Trx.TRXNAME_None);
		sponsorSalesRepContract.setC_Sponsor_ID(sponsor.getC_Sponsor_ID());
		sponsorSalesRepContract.setSponsorSalesRepType(X_C_Sponsor_SalesRep.SPONSORSALESREPTYPE_VP);
		sponsorSalesRepContract.setC_AdvCommissionCondition_ID(defaultConditions.getC_AdvCommissionCondition_ID());
		sponsorSalesRepContract.setValidFrom(TimeUtil.getDay(1970, 1, 1));
		sponsorSalesRepContract.setValidTo(TimeUtil.getDay(2050, 1, 1));

		InterfaceWrapperHelper.save(sponsorSalesRepContract);
	}

	@Test
	public void testRetrieveTerm_Product()
	{
		final I_C_AdvCommissionTerm term = prepareTerm();
		term.setM_Product(product);
		InterfaceWrapperHelper.save(term);

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		final I_C_AdvCommissionTerm term2 = Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, true);

		Assert.assertTrue("Wrong product in term", product.equals(term2.getM_Product()));
	}

	@Test
	public void testRetrieveTerm_ProductCategory_NoProduct()
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(productCategory);

		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());

		final I_C_AdvCommissionTerm term = prepareTerm();
		term.setM_Product_Category(productCategory);
		InterfaceWrapperHelper.save(term);

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		final I_C_AdvCommissionTerm term2 = Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, true);

		Assert.assertTrue("Wrong product category in term", productCategory.equals(term2.getM_Product_Category()));
	}
	
	@Test(expected = AdempiereException.class)
	public void testRetrieveTerm_ProductCategory_AnotherProduct_AssertTrue()
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(productCategory);

		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		
		final I_M_Product product2 = InterfaceWrapperHelper.create(ctx,  I_M_Product.class, Trx.TRXNAME_None);
		product2.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		InterfaceWrapperHelper.save(product2);
		
		final I_C_AdvCommissionTerm term = prepareTerm();
		term.setM_Product_Category(productCategory);
		term.setM_Product(product2);
		InterfaceWrapperHelper.save(term);

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, true);
	}
	
	
	@Test
	public void testRetrieveTerm_ProductCategory_AnotherProduct_AssertFalse()
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(productCategory);

		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		
		final I_M_Product product2 = InterfaceWrapperHelper.create(ctx,  I_M_Product.class, Trx.TRXNAME_None);
		product2.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		InterfaceWrapperHelper.save(product2);
		
		final I_C_AdvCommissionTerm term = prepareTerm();
		term.setM_Product_Category(productCategory);
		term.setM_Product(product2);
		InterfaceWrapperHelper.save(term);

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		final I_C_AdvCommissionTerm term2 = Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, false);

		Assert.assertNull(term2);
	}
	
	

	@Test
	public void testRetrieveTerm_NoProductNoCategory()
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, Trx.TRXNAME_None);
		InterfaceWrapperHelper.save(productCategory);

		product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
		InterfaceWrapperHelper.save(product);

		final I_C_AdvCommissionTerm term = prepareTerm();
		InterfaceWrapperHelper.save(term);

		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		final I_C_AdvCommissionTerm term2 = Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, true);

		Assert.assertTrue("Wrong system Type in term", systemType.getC_AdvComSystem_Type_ID() == term2.getC_AdvComSystem_Type_ID());
		Assert.assertTrue("Wrong condition in term", defaultConditions.getC_AdvCommissionCondition_ID() == term2.getC_AdvCommissionCondition_ID());

		Assert.assertNull("Term should not have a product set", term2.getM_Product());
		Assert.assertNull("Term should not have a product category set", term2.getM_Product_Category());
	}
	
	@Test(expected = AdempiereException.class)
	public void testRetrieveTerm_NoTerm_AssertTrue()
	{
		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, true);
	}
	
	@Test
	public void testRetrieveTerm_NoTerm_AssertFalse()
	{
		final ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, date, commissionType, system, product);

		final I_C_AdvCommissionTerm term2 = Services.get(ISponsorBL.class).retrieveTerm(commissionCtx, false);
		Assert.assertNull(term2);
	}
	

	/**
	 * Creates a term and assingns it to {@link #defaultConditions}. The term is not saved.
	 * 
	 * @return
	 */
	private I_C_AdvCommissionTerm prepareTerm()
	{
		final I_C_AdvCommissionTerm term = InterfaceWrapperHelper.create(ctx, I_C_AdvCommissionTerm.class, Trx.TRXNAME_None);
		term.setC_AdvComSystem_Type(systemType);
		term.setC_AdvCommissionCondition(defaultConditions);
		return term;
	}
}
