package de.metas.handlingunits.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.expectations.HUsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.model.validator.M_HU;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class HUBuilderTests
{
	private HUTestHelper helper;

	private IHandlingUnitsDAO handlingUnitsDAO;

	private IMutableHUContext huContext;
	private I_M_HU_PI_Version piLU_Version;
	private I_M_HU_PI piLU;

	private I_M_HU_PI_Item piLU_Item_TU;

	private I_M_HU_PI_Version piTU_Version;
	private I_M_HU_PI piTU;

	@BeforeEach
	public void init()
	{
		helper = new HUTestHelper();
		helper.init();

		handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		huContext = helper.createMutableHUContext();
		// POJOLookupMap.get().dumpStatus();

		// create a TU related packing instruction
		piTU = helper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		I_M_HU_PI_Item piTU_Item = helper.createHU_PI_Item_Material(piTU);
		helper.createHU_PI_Item_PackingMaterial(piTU, helper.pmIFCO);

		piTU_Version = piTU_Item.getM_HU_PI_Version();
		assertThat(piTU_Version.getM_HU_PI()).isEqualTo(piTU); // guard

		// create a LU related packing instruction and link to it the TU related PI we created above
		piLU = helper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		helper.createHU_PI_Item_PackingMaterial(piLU, helper.pmPalet);

		piLU_Item_TU = helper.createHU_PI_Item_IncludedHU(piLU, piTU, new BigDecimal("5"));

		piLU_Version = piLU_Item_TU.getM_HU_PI_Version();
		assertThat(piLU_Version.getM_HU_PI()).isEqualTo(piLU); // guard
	}

	@Test
	public void simpleTest()
	{
		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Version.class);
		InterfaceWrapperHelper.save(piVersion);

		final HUBuilder testee = new HUBuilder(huContext);
		final I_M_HU result = testee.create(piVersion);

		assertThat(result).isNotNull();
		assertThat(Services.get(IHandlingUnitsBL.class).getPIVersion(result)).isEqualTo(piVersion);
	}

	/**
	 * Invokes the {@link HUBuilder} once to create an LU handling unit according to {@link #piLU_Version}.
	 */
	@Test
	public void testOnlyLULevel()
	{
		final HUBuilder testee = new HUBuilder(huContext);

		final I_M_HU result = testee.create(piLU_Version);

		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
				.newHUExpectation()
					.huPI(piLU)
					.item() // the virtual item that shall hold the "bag" VHU
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.huPIItem(null)
						.noIncludedHUs() // note that the HU builder does not really recurse, but only creates the HU for the given piVersion, plus the HU's items. not no included HUs
					.endExpectation() // huItemExpectation
					.item() // the packing material item for this LU
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.packingMaterial(helper.pmPalet)
					.endExpectation() // HUItemExpectation()
				.endExpectation() // huExpectation
		;
		//@formatter:on
		compressedHUExpectation.assertExpected(Collections.singletonList(result));
	}

	/**
	 * Invokes the {@link HUBuilder} two times to first create a LU handling unit.
	 * It then explicitly  creates a "HU" type hu-item and invokes the builder with that item as parent item to create a TU handling unit.  
	 */
	@Test
	public void testLUandTULevelExplicit()
	{
		// create the LU hu with its items etc.
		final HUBuilder testeeLU = new HUBuilder(huContext);

		final I_M_HU luHU = testeeLU.create(piLU_Version);
		assertThat(luHU).isNotNull();
		assertThat(luHU.getM_HU_Item_Parent()).isNull();

		// to create the TU and link it to the LU, we need to give it a parent-item
		final I_M_HU_Item parentItemAggregate = handlingUnitsDAO.retrieveItemIfExists(luHU, piLU_Item_TU).orElse(null);

		// these asserts are just guards. the real verification happens further down.
		// it's ok that the DAI returned the aggregate item, but..
		assertThat(parentItemAggregate).isNotNull();
		assertThat(parentItemAggregate.getItemType()).isEqualTo(X_M_HU_Item.ITEMTYPE_HUAggregate);

		// .. now we explicitly create the HU item..
		handlingUnitsDAO.createHUItem(luHU, piLU_Item_TU);
		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveItemIfExists(luHU, piLU_Item_TU).orElse(null);

		// ..and now we don't want the aggregate item to be returned anymore
		assertThat(parentItem).isNotNull();
		assertThat(parentItem.getItemType()).isEqualTo(X_M_HU_Item.ITEMTYPE_HandlingUnit);

		final HUBuilder testeeTU = new HUBuilder(huContext);
		testeeTU.setM_HU_Item_Parent(parentItem);
		final I_M_HU luTU = testeeTU.create(piTU_Version);

		assertThat(luTU).isNotNull();
		assertThat(luTU.getM_HU_Item_Parent()).isNotNull();
		assertThat(luTU.getM_HU_Item_Parent().getM_HU()).isEqualTo(luHU);

		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
				.newHUExpectation()
					.huPI(piLU)

					.item()
						.itemType(X_M_HU_Item.ITEMTYPE_HandlingUnit)
						// the HU builder does not really recurse, but only creates the HU for the given piVersion, plus the HU's items
						.includedHU()
						.huPI(piTU)
							.item()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.endExpectation() // HUItemExpectation()
							
							.item()
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
								.packingMaterial(helper.pmIFCO)
							.endExpectation() // HUItemExpectation()
						.endExpectation() // includedHUExpectation
					.endExpectation() // HU - huItemExpectation

					.item()
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)	
						.huPIItem(null)
						.noIncludedHUs()
					.endExpectation() // HA - HUItemExpectation()

					.item()
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.packingMaterial(helper.pmPalet)
					.endExpectation() // PM - HUItemExpectation()
				.endExpectation(); // huExpectation
		//@formatter:on
		compressedHUExpectation.assertExpected(Collections.singletonList(luHU));
	}

	/**
	 * Invokes the {@link HUBuilder} two times to first create a LU handling unit.
	 * It then invokes the builder a second time to create a an "aggregate" handling unit below the LU. There shall be no "explicit" TU, but the TU's packaging shall be added to the LU's aggregate item.  
	 */
	@Test
	public void testLUandTULevel()
	{
		final HUBuilder testeeLU = new HUBuilder(huContext);
		final I_M_HU luHU = testeeLU.create(piLU_Version);

		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveItemIfExists(luHU, piLU_Item_TU).orElse(null);
		assertThat(parentItem).isNotNull();
		assertThat(parentItem.getItemType()).isEqualTo(X_M_HU_Item.ITEMTYPE_HUAggregate);

		final HUBuilder testeeCompressedVHU = new HUBuilder(huContext);
		testeeCompressedVHU.setM_HU_Item_Parent(parentItem);
		/* final I_M_HU aggregateVHU = */ testeeCompressedVHU.create(piTU_Version);

		//@formatter:off
		final HUsExpectation compressedHUExpectation = new HUsExpectation()
				.newHUExpectation()
					.huPI(piLU)
					.item() // the virtual item that shall hold the "bag" VHU
						.itemType(X_M_HU_Item.ITEMTYPE_HUAggregate)
						.huPIItem(null)
						.includedHU() // the "bag" VHU itself
							.huPI(helper.huDefVirtual)
							.virtualPIItem() // the product qty that is still "bagged"
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_Material)
							.endExpectation()
							.item() // the remaining packaging (IFCO) that is still "bagged"
								.noIncludedHUs()
								.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
								.packingMaterial(helper.pmIFCO)
							.endExpectation()
						.endExpectation() // includedHUExpectation
					.endExpectation() // huItemExpectation with itemType=HA
					.item() // the packing material item for this LU
						.noIncludedHUs()
						.itemType(X_M_HU_Item.ITEMTYPE_PackingMaterial)
						.packingMaterial(helper.pmPalet)
					.endExpectation()
				.endExpectation() // huExpectation
		;
		//@formatter:on
		compressedHUExpectation.assertExpected(Collections.singletonList(luHU));
	}

	/**
	 * A physical HU (top-level, real locator) shall get its {@code AD_Org_ID} from the locator's warehouse
	 * at creation time, regardless of the creating context's org. If the context org already matches
	 * the warehouse org, nothing changes (no-regression).
	 */
	@Test
	public void huCreatedUnderOrgZeroContextGetsWarehouseOrg()
	{
		// Production wiring registers M_HU.INSTANCE as a model validator (see Main#registerInterceptors).
		// HUTestHelper's "minimal" interceptor setup intentionally skips it (to avoid NPEs in unrelated tests),
		// so register it here to exercise the real TYPE_BEFORE_NEW creation path.
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(M_HU.INSTANCE);

		// Fixture: a warehouse+locator that belongs to a real org R (the harness' helper.defaultWarehouse is org 0)
		final OrgId orgR = AdempiereTestHelper.createOrgWithTimeZone("HuOrgFromWarehouseOrg");
		assertThat(orgR.getRepoId()).isGreaterThan(0); // fixture sanity check

		final I_M_Warehouse warehouseR = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_Warehouse.class);
		warehouseR.setValue("WhOrgR");
		warehouseR.setName("WhOrgR");
		warehouseR.setIsIssueWarehouse(false);
		warehouseR.setAD_Org_ID(orgR.getRepoId());
		InterfaceWrapperHelper.save(warehouseR);
		assertThat(warehouseR.getAD_Org_ID()).isEqualTo(orgR.getRepoId()); // fixture sanity check: R > 0

		final I_M_Locator locatorR = BusinessTestHelper.createLocator("WhOrgR-default", warehouseR);
		final LocatorId locatorIdR = LocatorId.ofRepoId(warehouseR.getM_Warehouse_ID(), locatorR.getM_Locator_ID());

		// Set the creation context org to 0 and create a top-level HU at the real-org locator.
		Env.setContext(helper.getCtx(), Env.CTXNAME_AD_Org_ID, 0);

		final HUBuilder testeeOrgZero = new HUBuilder(huContext);
		testeeOrgZero.setLocatorId(locatorIdR);
		final I_M_HU resultOrgZero = testeeOrgZero.create(piLU_Version);

		final I_M_HU reloadedOrgZero = InterfaceWrapperHelper.load(resultOrgZero.getM_HU_ID(), I_M_HU.class);
		assertThat(reloadedOrgZero.getM_Locator_ID()).isEqualTo(locatorR.getM_Locator_ID()); // guard
		assertThat(reloadedOrgZero.getAD_Org_ID()).isEqualTo(orgR.getRepoId());

		// No-regression: context org already == R => persisted org stays R
		Env.setContext(helper.getCtx(), Env.CTXNAME_AD_Org_ID, orgR.getRepoId());

		final HUBuilder testeeOrgR = new HUBuilder(huContext);
		testeeOrgR.setLocatorId(locatorIdR);
		final I_M_HU resultOrgR = testeeOrgR.create(piLU_Version);

		final I_M_HU reloadedOrgR = InterfaceWrapperHelper.load(resultOrgR.getM_HU_ID(), I_M_HU.class);
		assertThat(reloadedOrgR.getAD_Org_ID()).isEqualTo(orgR.getRepoId());
	}

	/**
	 * An org-0 warehouse ("*" / ANY) carries no real org. The interceptor shall then keep the creating
	 * context's org rather than re-stamping the physical HU with org 0 (symmetric with the data-repair
	 * migration, which likewise only repairs HUs whose warehouse has a real org).
	 */
	@Test
	public void huCreatedAtOrgZeroWarehouseKeepsContextOrg()
	{
		// Production wiring registers M_HU.INSTANCE as a model validator (see Main#registerInterceptors).
		// HUTestHelper's "minimal" interceptor setup intentionally skips it (to avoid NPEs in unrelated tests),
		// so register it here to exercise the real TYPE_BEFORE_NEW creation path.
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(M_HU.INSTANCE);

		// Fixture: a warehouse+locator whose own AD_Org_ID is 0 ("*" / ANY)
		final I_M_Warehouse warehouseOrgZero = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_Warehouse.class);
		warehouseOrgZero.setValue("WhOrgZero");
		warehouseOrgZero.setName("WhOrgZero");
		warehouseOrgZero.setIsIssueWarehouse(false);
		warehouseOrgZero.setAD_Org_ID(0);
		InterfaceWrapperHelper.save(warehouseOrgZero);
		assertThat(warehouseOrgZero.getAD_Org_ID()).isEqualTo(0); // fixture sanity check

		final I_M_Locator locatorOrgZero = BusinessTestHelper.createLocator("WhOrgZero-default", warehouseOrgZero);
		final LocatorId locatorIdOrgZero = LocatorId.ofRepoId(warehouseOrgZero.getM_Warehouse_ID(), locatorOrgZero.getM_Locator_ID());

		// Set the creation context org to a real org R and create a top-level HU at the org-0-warehouse locator.
		final OrgId orgR = AdempiereTestHelper.createOrgWithTimeZone("HuOrgZeroWarehouseContextOrg");
		assertThat(orgR.getRepoId()).isGreaterThan(0); // fixture sanity check
		Env.setContext(helper.getCtx(), Env.CTXNAME_AD_Org_ID, orgR.getRepoId());

		final HUBuilder testee = new HUBuilder(huContext);
		testee.setLocatorId(locatorIdOrgZero);
		final I_M_HU result = testee.create(piLU_Version);

		final I_M_HU reloaded = InterfaceWrapperHelper.load(result.getM_HU_ID(), I_M_HU.class);
		assertThat(reloaded.getM_Locator_ID()).isEqualTo(locatorOrgZero.getM_Locator_ID()); // guard
		assertThat(reloaded.getAD_Org_ID()).isEqualTo(orgR.getRepoId());
	}

	/**
	 * An HU with no locator (no physical location) has no warehouse to derive an org from
	 * ({@link IHandlingUnitsBL#extractWarehouseOrNull} returns null). The interceptor shall then keep the
	 * creating context's org.
	 */
	@Test
	public void huCreatedWithoutLocatorKeepsContextOrg()
	{
		// Production wiring registers M_HU.INSTANCE as a model validator (see Main#registerInterceptors).
		// HUTestHelper's "minimal" interceptor setup intentionally skips it (to avoid NPEs in unrelated tests),
		// so register it here to exercise the real TYPE_BEFORE_NEW creation path.
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(M_HU.INSTANCE);

		final OrgId orgR = AdempiereTestHelper.createOrgWithTimeZone("HuNoLocatorContextOrg");
		assertThat(orgR.getRepoId()).isGreaterThan(0); // fixture sanity check
		Env.setContext(helper.getCtx(), Env.CTXNAME_AD_Org_ID, orgR.getRepoId());

		// no setLocatorId(..) call => M_Locator_ID stays 0 => extractWarehouseOrNull(hu) returns null
		final HUBuilder testee = new HUBuilder(huContext);
		final I_M_HU result = testee.create(piLU_Version);

		final I_M_HU reloaded = InterfaceWrapperHelper.load(result.getM_HU_ID(), I_M_HU.class);
		assertThat(reloaded.getM_Locator_ID()).isLessThanOrEqualTo(0); // guard: no physical location (LocatorId.toRepoId(null) == -1)
		assertThat(reloaded.getAD_Org_ID()).isEqualTo(orgR.getRepoId());
	}
}
