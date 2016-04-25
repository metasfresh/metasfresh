package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.model.util.ModelByIdComparator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

public class HUPIItemProductDAOTest
{
	private HUPIItemProductDAO dao;
	private I_M_Product product1;
	private I_M_Product product2;
	// private I_M_Product product3;
	private I_C_BPartner bpartner1;
	private I_C_BPartner bpartner2;
	private final I_C_BPartner bpartner_NULL = null;
	// private I_C_BPartner bpartner3;
	// private I_M_HU_PI_Item piItem1;
	private Timestamp date1;
	private Timestamp date2;
	private Timestamp date3;
	private Timestamp date4;

	/** Watches current test and dumps the database to console in case of failure */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		dao = (HUPIItemProductDAO)Services.get(IHUPIItemProductDAO.class);
		product1 = createProduct("p1");
		product2 = createProduct("p2");
		// product3 = createProduct("p3");
		bpartner1 = createBPartner("bp1");
		bpartner2 = createBPartner("bp2");
		// bpartner3 = createBPartner("bp3");
		// piItem1 = createM_HU_PI_Item();
		date1 = TimeUtil.getDay(2011, 10, 01);
		date2 = TimeUtil.getDay(2012, 10, 01);
		date3 = TimeUtil.getDay(2013, 10, 01);
		date4 = TimeUtil.getDay(2014, 10, 01);
	}

	@Test
	public void test_createQueryOrderByBuilder()
	{
		final Comparator<Object> orderBy = dao.createQueryOrderByBuilder(null)
				.createQueryOrderBy()
				.getComparator();

		final List<I_M_HU_PI_Item_Product> itemProducts = Arrays.asList(
				createM_HU_PI_Item_Product(product1, null, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit)
				, createM_HU_PI_Item_Product(product1, bpartner1, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit)
				);

		final List<I_M_HU_PI_Item_Product> itemProductsOrdered = new ArrayList<I_M_HU_PI_Item_Product>(itemProducts);
		Collections.sort(itemProductsOrdered, orderBy);

		assertEqualsByDescription("Invalid item at index=1", itemProducts.get(1), itemProductsOrdered.get(0));
		assertEqualsByDescription("Invalid item at index=2", itemProducts.get(0), itemProductsOrdered.get(1));
	}

	@Test
	public void test_retrieveMaterialItemProduct_product_anyBPartner_date()
	{
		final I_M_HU_PI_Item_Product[] itemProducts = new I_M_HU_PI_Item_Product[] {
				/* 0 */createM_HU_PI_Item_Product(product1, null, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
				/* 1 */createM_HU_PI_Item_Product(product1, bpartner1, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
		};

		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date2, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[1], product1, bpartner1, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner2, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner2, date2, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner2, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
	}

	@Test
	public void test_retrieveMaterialItemProduct_anyProduct_bpartner_date()
	{
		final I_M_HU_PI_Item_Product[] itemProducts = new I_M_HU_PI_Item_Product[] {
				/* 0 */createM_HU_PI_Item_Product(product1, null, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
				/* 1 */createM_HU_PI_Item_Product(null, null, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit),
		};

		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date2, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(itemProducts[0], product1, bpartner1, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date2, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date3, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit); // 06566: don't pull the "anyProduct"
																																				// I_M_HU_PI_Item_Product, because product2 has no
																																				// assignment
		test_retrieveMaterialItemProduct_product_bpartner_date(null, product2, bpartner1, date4, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit); // 06566: don't pull the "anyProduct"
	}

	private void test_retrieveMaterialItemProduct_product_bpartner_date(final I_M_HU_PI_Item_Product expected,
			final I_M_Product product, final I_C_BPartner bpartner, final Date date, final String huUnitType)
	{
		final I_M_HU_PI_Item_Product actual = dao.retrieveMaterialItemProduct(product, bpartner, date, huUnitType, true);
		final String message = "Invalid for product=" + product.getValue() + ", bpartner=" + bpartner.getValue() + ", date=" + date;
		assertEqualsByDescription(message, expected, actual);
	}

	private void assertEqualsByDescription(final String message, final I_M_HU_PI_Item_Product expected, final I_M_HU_PI_Item_Product actual)
	{
		final String expectedDescription = expected == null ? null : expected.getDescription();
		final String actualDescription = actual == null ? null : actual.getDescription();
		Assert.assertEquals(message, expectedDescription, actualDescription);
	}

	private I_M_Product createProduct(final String value)
	{
		final I_M_Product p = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Product.class, ITrx.TRXNAME_None);
		p.setValue(value);
		p.setName(value);
		InterfaceWrapperHelper.save(p);

		return p;
	}

	private I_C_BPartner createBPartner(final String value)
	{
		final I_C_BPartner bp = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_None);
		bp.setValue(value);
		bp.setName(value);
		InterfaceWrapperHelper.save(bp);

		return bp;
	}

	private I_M_HU_PI_Version createM_HU_PI_Version(final String huUnitType)
	{
		final I_M_HU_PI pi = InterfaceWrapperHelper.create(Env.getCtx(), I_M_HU_PI.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(pi);

		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.create(Env.getCtx(), I_M_HU_PI_Version.class, ITrx.TRXNAME_None);
		piVersion.setM_HU_PI(pi);
		piVersion.setHU_UnitType(huUnitType);
		piVersion.setIsCurrent(true);
		piVersion.setName("M_HU_PI_ID=" + pi.getM_HU_PI_ID() + "_Current");
		InterfaceWrapperHelper.save(piVersion);
		return piVersion;
	}

	private I_M_HU_PI_Item createM_HU_PI_Item(final String huUnitType)
	{
		final I_M_HU_PI_Version piVersion = createM_HU_PI_Version(huUnitType);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.create(Env.getCtx(), I_M_HU_PI_Item.class, ITrx.TRXNAME_None);
		piItem.setM_HU_PI_Version(piVersion);
		InterfaceWrapperHelper.save(piItem);
		return piItem;
	}

	private I_M_HU_PI_Item_Product createM_HU_PI_Item_Product(final I_M_Product product, final I_C_BPartner bpartner, final Timestamp validFrom, final String huUnitType)
	{
		final I_M_HU_PI_Item_Product piItemProduct = InterfaceWrapperHelper.create(Env.getCtx(), I_M_HU_PI_Item_Product.class, ITrx.TRXNAME_None);

		piItemProduct.setM_Product(product);
		if (product == null)
		{
			piItemProduct.setIsAllowAnyProduct(true);
		}

		piItemProduct.setIsInfiniteCapacity(false);

		final I_M_HU_PI_Item huPiItem = createM_HU_PI_Item(huUnitType);
		piItemProduct.setM_HU_PI_Item(huPiItem);

		piItemProduct.setC_BPartner(bpartner);
		piItemProduct.setValidFrom(validFrom);

		InterfaceWrapperHelper.save(piItemProduct);
		piItemProduct.setDescription(toString(piItemProduct));

		InterfaceWrapperHelper.save(piItemProduct);

		return piItemProduct;
	}

	private String toString(final I_M_HU_PI_Item_Product piItemProduct)
	{
		final StringBuilder sb = new StringBuilder();
		if (piItemProduct.getC_BPartner_ID() > 0)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("BP=").append(piItemProduct.getC_BPartner().getValue());
		}
		if (piItemProduct.getM_Product_ID() > 0)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("Product=").append(piItemProduct.getM_Product().getValue());
		}
		if (piItemProduct.isAllowAnyProduct())
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("IsAllowAnyProduct");
		}
		if (piItemProduct.getValidFrom() != null)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("ValidFrom=").append(piItemProduct.getValidFrom());
		}

		final I_M_HU_PI_Item piItem = piItemProduct.getM_HU_PI_Item();
		final I_M_HU_PI_Version piVersion = piItem == null ? null : piItem.getM_HU_PI_Version();
		if (piVersion != null && piVersion.getHU_UnitType() != null)
		{
			if (sb.length() > 0)
			{
				sb.append(", ");
			}
			sb.append("huUnitType=").append(piVersion.getHU_UnitType());
		}

		final String instanceName = sb.toString();
		POJOWrapper.setInstanceName(piItemProduct, instanceName);

		return instanceName;
	}

	/**
	 * @see http://dewiki908/mediawiki/index.php/08868_Wareneingang_POS_does_not_suggest_customer_defined_TU_%28109729999780%29
	 */
	@Test
	public void test_retrieveTUs_case08868()
	{
		final I_M_HU_PI_Item_Product pip1 = createM_HU_PI_Item_Product(product1, bpartner_NULL, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item_Product pip2 = createM_HU_PI_Item_Product(product1, bpartner1, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		@SuppressWarnings("unused")
		final I_M_HU_PI_Item_Product pip3 = createM_HU_PI_Item_Product(product1, bpartner2, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item_Product pip4 = createM_HU_PI_Item_Product(product1, bpartner1, date1, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final List<I_M_HU_PI_Item_Product> pipsExpected = Arrays.asList(pip1, pip2, pip4);
		final List<I_M_HU_PI_Item_Product> pipsActual = dao.retrieveTUs(Env.getCtx(), product1, bpartner1);
		Collections.sort(pipsActual, ModelByIdComparator.getInstance());
		Assert.assertEquals(
				pipsExpected,
				pipsActual);
	}
}
