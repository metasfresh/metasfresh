package de.metas.ui.web.picking.husToPick.process;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.business.BusinessTestHelper;
import de.metas.document.IDocTypeDAO;
import de.metas.document.IDocTypeDAO.DocTypeCreateRequest;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HUTestHelper.TestHelperLoadRequest;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.PlainWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.inventory.InventoryRepository;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.inventory.draftlinescreator.InventoryLineAggregatorFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.inventory.InventoryDocSubType;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@ExtendWith(AdempiereTestWatcher.class)
public class WeightHUCommandTest
{
	private InventoryService inventoryService;
	private InventoryLineAggregatorFactory inventoryLineAggregatorFactory;
	private HUTestHelper helper;

	private I_C_UOM uomKg;
	private ProductId productId;
	private I_M_HU_PI piIFCO;
	private LocatorId locatorId;

	@BeforeEach
	public void beforeEach()
	{
		helper = new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None;
			}
		};

		final InventoryRepository inventoryRepo = new InventoryRepository();
		this.inventoryService = new InventoryService(inventoryRepo);
		this.inventoryLineAggregatorFactory = new InventoryLineAggregatorFactory();

		createMasterData();

		POJOLookupMap.get().addModelValidator(new de.metas.handlingunits.inventory.interceptor.M_Inventory(inventoryService));
	}

	private void createMasterData()
	{
		uomKg = helper.uomKg;
		productId = BusinessTestHelper.createProductId("product", uomKg);

		piIFCO = helper.createHUDefinition("IFCO", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		final I_AD_Org org = newInstance(I_AD_Org.class);
		saveRecord(org);
		// orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		I_M_Warehouse warehouse = BusinessTestHelper.createWarehouse("warehouse");
		I_M_Locator locator = BusinessTestHelper.createLocator("locator", warehouse);
		locatorId = LocatorId.ofRecord(locator);

		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		docTypeDAO.createDocType(DocTypeCreateRequest.builder()
				.ctx(Env.getCtx())
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory)
				.docSubType(InventoryDocSubType.SingleHUInventory.getCode())
				.name("inventory")
				.build());
	}

	@Test
	public void test()
	{
		final HuId huId = createHU("100");
		{
			final IHUProductStorage huProductStorage = getHUProductStorage(huId);
			assertThat(huProductStorage.getQty()).isEqualTo(Quantity.of("100", uomKg));
		}
		{
			final IWeightable huAttributes = getHUAttributes(huId);
			huAttributes.setWeightGross(new BigDecimal("100"));

			assertThat(huAttributes.getWeightGross()).isEqualByComparingTo("100");
			assertThat(huAttributes.getWeightNet()).isEqualByComparingTo("100");
			assertThat(huAttributes.getWeightNetUOM()).isEqualTo(uomKg);
			assertThat(huAttributes.getWeightTare()).isEqualByComparingTo("0");
			assertThat(huAttributes.getWeightTareAdjust()).isEqualByComparingTo("0");
		}

		WeightHUCommand.builder()
				.inventoryService(inventoryService)
				.inventoryLineAggregatorFactory(inventoryLineAggregatorFactory)
				.huId(huId)
				.targetWeight(PlainWeightable.builder()
						.uom(uomKg)
						.weightGross(new BigDecimal("99"))
						.weightNet(new BigDecimal("99"))
						.build())
				.build()
				.execute();

		{
			final IHUProductStorage huProductStorage = getHUProductStorage(huId);
			assertThat(huProductStorage.getQty()).isEqualTo(Quantity.of("99", uomKg));
		}
		{
			final IWeightable huAttributes = getHUAttributes(huId);
			assertThat(huAttributes.getWeightGross()).isEqualByComparingTo("99");
			assertThat(huAttributes.getWeightNet()).isEqualByComparingTo("99");
			assertThat(huAttributes.getWeightTare()).isEqualByComparingTo("0");
			assertThat(huAttributes.getWeightTareAdjust()).isEqualByComparingTo("0");
		}
	}

	private HuId createHU(@NonNull final String qtyInKg)
	{
		final IHUProducerAllocationDestination producer = HUProducerDestination.of(piIFCO)
				.setLocatorId(locatorId)
				.setHUStatus(X_M_HU.HUSTATUS_Active);
		helper.load(TestHelperLoadRequest.builder()
				.producer(producer)
				.cuProductId(productId)
				.loadCuQty(new BigDecimal(qtyInKg))
				.loadCuUOM(uomKg)
				.build());
		return producer.getSingleCreatedHuId().get();
	}

	private IWeightable getHUAttributes(@NonNull final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IAttributeStorage huAttributes = huContextFactory
				.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu);
		huAttributes.setSaveOnChange(true);

		return Weightables.wrap(huAttributes);
	}

	private IHUProductStorage getHUProductStorage(@NonNull final HuId huId)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final I_M_HU hu = handlingUnitsBL.getById(huId);

		return handlingUnitsBL.getStorageFactory()
				.getStorage(hu)
				.getProductStorage(productId);
	}
}
