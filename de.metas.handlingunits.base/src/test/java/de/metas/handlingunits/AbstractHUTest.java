package de.metas.handlingunits;

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
// NOPMD by al on 7/25/13 11:56 AM

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.test.ErrorMessage;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import de.metas.handlingunits.model.I_M_HU_PackingMaterial;

public abstract class AbstractHUTest
{
	protected I_C_UOM uomEach;
	protected I_C_UOM uomKg;

	/**
	 * Value: Pallete
	 */
	protected I_M_Product pPallets; // Pallets are our packing material
	protected I_M_HU_PackingMaterial pmPallets; // Pallets are our packing material

	/**
	 * Value: IFCO
	 */
	protected I_M_Product pIFCO; // IFCOs are another kind of packing material
	protected I_M_HU_PackingMaterial pmIFCO; // IFCO Packing Material

	/**
	 * Value: Bag
	 */
	protected I_M_Product pBag; // Bags are another kind of packing material
	protected I_M_HU_PackingMaterial pmBag; // Bag Packing Material

	/**
	 * Value: Paloxe
	 */
	protected I_M_Product pPaloxe; // Paloxes are another kind of packing material
	protected I_M_HU_PackingMaterial pmPaloxe; // Paloxe Packing Material

	/**
	 * Value: Tomato
	 */
	protected I_M_Product pTomato;
	protected I_M_Product pSalad;

	protected I_M_Attribute attr_CountryMadeIn;
	protected I_M_Attribute attr_Volume;
	protected I_M_Attribute attr_FragileSticker;

	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightGross}
	 */
	protected I_M_Attribute attr_WeightGross;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightNet}
	 */
	protected I_M_Attribute attr_WeightNet;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightTare}
	 */
	protected I_M_Attribute attr_WeightTare;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_WeightTareAdjust}
	 */
	protected I_M_Attribute attr_WeightTareAdjust;

	protected I_M_Attribute attr_AttributeNotFound;

	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_QualityDiscountPercent}
	 */
	protected I_M_Attribute attr_QualityDiscountPercent;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_QualityNotice}
	 */
	protected I_M_Attribute attr_QualityNotice;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_SubProducerBPartner}
	 */
	protected I_M_Attribute attr_SubProducerBPartner;
	/**
	 * See {@link de.metas.handlingunits.HUTestHelper#attr_LotNumberDate}
	 */
	protected I_M_Attribute attr_LotNumberDate;

	@BeforeClass
	public final static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(false);
	}

	/** HU Test helper */
	public HUTestHelper helper;

	/**
	 * Watches current test and dumps the database to console in case of failure
	 */
	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher()
	{
		@Override
		protected void failed(final Throwable e, final Description description)
		{
			super.failed(e, description);
			afterTestFailed();
		}
	};

	@Before
	public final void init()
	{
		setupMasterData();

		initialize();
	}

	abstract protected void initialize();

	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper();
	}

	protected void setupMasterData()
	{
		helper = createHUTestHelper();

		attr_CountryMadeIn = helper.attr_CountryMadeIn;
		attr_Volume = helper.attr_Volume;
		attr_FragileSticker = helper.attr_FragileSticker;

		attr_WeightGross = helper.attr_WeightGross;
		attr_WeightNet = helper.attr_WeightNet;
		attr_WeightTare = helper.attr_WeightTare;
		attr_WeightTareAdjust = helper.attr_WeightTareAdjust;

		attr_QualityDiscountPercent = helper.attr_QualityDiscountPercent;
		attr_QualityNotice = helper.attr_QualityNotice;
		attr_SubProducerBPartner = helper.attr_SubProducerBPartner;

		attr_LotNumberDate = helper.attr_LotNumberDate;

		uomEach = helper.uomEach;
		uomKg = helper.uomKg;

		pPallets = helper.pPalet;
		pmPallets = helper.pmPalet;

		pIFCO = helper.pIFCO;
		pmIFCO = helper.pmIFCO;

		pPaloxe = helper.pPaloxe;
		pmPaloxe = helper.pmPaloxe;

		pBag = helper.pBag;
		pmBag = helper.pmBag;

		pTomato = helper.pTomato;
		pSalad = helper.pSalad;
	}

	/**
	 * Method called after a test failed.
	 *
	 * To be overridden by implementors.
	 */
	protected void afterTestFailed()
	{
		// nothing at this level
	}

	protected ErrorMessage newErrorMessage()
	{
		return ErrorMessage.newInstance();
	}
}
