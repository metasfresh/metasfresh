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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
import static de.metas.business.BusinessTestHelper.createLocator;
import static de.metas.business.BusinessTestHelper.createWarehouse;

import java.math.BigDecimal;
import java.util.Date;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnableAdapter;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.expectations.HUExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item_Storage;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.snapshot.IHUSnapshotDAO;
import de.metas.handlingunits.snapshot.impl.HUSnapshotDAO;
import de.metas.handlingunits.util.TraceUtils;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import org.junit.jupiter.api.Test;

public class HUSnapshotDAOTest extends AbstractHUTest
{
	private HUSnapshotDAO huSnapshotDAO;
	private ITrxManager trxManager;

	private I_M_HU_PI piTU;
	private I_M_HU_PI_Item piLU_item;
	private I_M_HU_PI piLU;
	private I_M_HU_PI_Item piTU_item;
	private I_M_Locator warehouse1_locator1;

	@Override
	protected HUTestHelper createHUTestHelper()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None; // no transaction by default
			}

			@Override
			protected void setupModuleInterceptors_HU()
			{
				setupModuleInterceptors_HU_Full();
			}
		};
	}

	@Override
	protected void initialize()
	{
		huSnapshotDAO = (HUSnapshotDAO)Services.get(IHUSnapshotDAO.class); // service under test

		trxManager = Services.get(ITrxManager.class);
		Check.assumeNotNull(trxManager, "trxManager not null");

		setupMasterData_HU_PI();

		final I_M_Warehouse warehouse1 = createWarehouse("Warehouse1");
		warehouse1_locator1 = createLocator("Warehouse1_Locator1", warehouse1);
	}

	protected void setupMasterData_HU_PI()
	{
		pTomato.setC_UOM_ID(uomKg.getC_UOM_ID());
		InterfaceWrapperHelper.save(pTomato);

		//
		// IFCO
		piTU = helper.createHUDefinition(HUTestHelper.NAME_IFCO_Product, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		{
			piTU_item = helper.createHU_PI_Item_Material(piTU);
			// huDefIFCO_pip_Tomato =
			helper.assignProduct(piTU_item, pTomatoId, BigDecimal.TEN, uomKg);
		}

		piLU = helper.createHUDefinition(HUTestHelper.NAME_Palet_Product, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		{
			piLU_item = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("2"));
		}

	}

	@Test
	public void test()
	{
		final PlainContextAware context = PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx());
		final Date dateTrx = SystemTime.asDate();
		final Object referencedModel = helper.createDummyReferenceModel();

		final IMutable<I_M_HU> luRef = new Mutable<>();
		final IMutable<I_M_HU> tuRef = new Mutable<>();
		final IMutable<I_M_HU> vhu1Ref = new Mutable<>();
		final IMutable<I_M_HU> vhu2Ref = new Mutable<>();
		final IMutable<I_M_HU_Item_Storage> vhu1_itemStorageRef = new Mutable<>();
		final IMutable<I_M_HU_Item_Storage> vhu2_itemStorageRef = new Mutable<>();

		//
		// Define the LU on which we want to test
		//@formatter:off
		final HUExpectation<Object> luExpectation = HUExpectation.newExpectation()
				.capture(luRef)
				.instanceName("LU")
				.huPI(piLU)
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locator(warehouse1_locator1)
				.newHUItemExpectation(piLU_item)
					//
					// TU
					.newIncludedHUExpectation()
						.capture(tuRef)
						.instanceName("TU")
						.huPI(piTU)
						.huStatus(X_M_HU.HUSTATUS_Active)
						.newHUItemExpectation(piTU_item)
							//
							// VHU 1
							.newIncludedVirtualHU()
								.instanceName("VHU1")
								.capture(vhu1Ref)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pTomato).qty("10").uom(uomKg)
										.capture(vhu1_itemStorageRef)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							//
							// VHU 2
							.newIncludedVirtualHU()
								.instanceName("VHU2")
								.capture(vhu2Ref)
								.newVirtualHUItemExpectation()
									.newItemStorageExpectation()
										.product(pTomato).qty("5").uom(uomKg)
										.capture(vhu2_itemStorageRef)
										.endExpectation()
									.endExpectation()
								.endExpectation()
							.endExpectation()
					.endExpectation()
				.endExpectation();
		//@formatter:on

		//
		// Create LU
		luExpectation.createHU();
		TraceUtils.dumpAllHUs("After create");
		// Make sure HUStatus was correctly propagated to all children
		Assert.assertEquals("Invalid VHU1's HUStatus", X_M_HU.HUSTATUS_Active, vhu1Ref.getValue().getHUStatus());
		Assert.assertEquals("Invalid VHU2's HUStatus", X_M_HU.HUSTATUS_Active, vhu2Ref.getValue().getHUStatus());

		//
		// Create snapshot
		final String snapshotId = huSnapshotDAO.createSnapshot()
				.setContext(context)
				.addModel(luRef.getValue())
				.createSnapshots()
				.getSnapshotId();

		//
		// Change our LU/TU/VHUs
		trxManager.runInNewTrx(new TrxRunnableAdapter()
		{

			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_M_HU lu = luRef.getValue();
				lu.setHUStatus(X_M_HU.HUSTATUS_Destroyed);
				lu.setIsActive(false);
				InterfaceWrapperHelper.save(lu, localTrxName);

				final I_M_HU_Item_Storage vhu1_itemStorage = vhu1_itemStorageRef.getValue();
				vhu1_itemStorage.setQty(new BigDecimal("1")); // from 10
				InterfaceWrapperHelper.save(vhu1_itemStorage, localTrxName);
			}
		});
		TraceUtils.dumpAllHUs("After changed");
		// Make sure it's changed
		InterfaceWrapperHelper.refresh(vhu1_itemStorageRef.getValue(), ITrx.TRXNAME_None);
		Assert.assertThat(vhu1_itemStorageRef.getValue().getQty(), Matchers.comparesEqualTo(new BigDecimal("1")));

		//
		// Restore the HU
		TraceUtils.dumpAllHUs("Before restore");
		POJOLookupMap.get().dumpStatus("Trx Line before restore", I_M_HU_Trx_Line.Table_Name);
		huSnapshotDAO.restoreHUs()
				.setContext(context)
				.setDateTrx(dateTrx)
				.setReferencedModel(referencedModel)
				.setSnapshotId(snapshotId)
				.addModel(luRef.getValue())
				.restoreFromSnapshot();
		//
		InterfaceWrapperHelper.refresh(luRef.getValue(), ITrx.TRXNAME_None);
		TraceUtils.dumpAllHUs("After restore");
		POJOLookupMap.get().dumpStatus("Trx Line after restore", I_M_HU_Trx_Line.Table_Name);

		//
		// Make sure it was correctly restored
		luExpectation.assertExpected("LU was correctly restored", luRef.getValue());

	}
}
