package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import de.metas.business.BusinessTestHelper;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.IDocTypeDAO;
import de.metas.acct.GLCategoryId;
import de.metas.handlingunits.CreateHUFromInventoryRequest;
import de.metas.handlingunits.CreatedHU;
import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_InventoryLine;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.qrcodes.service.HUQRCodesRepository;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * {@link HUQtyService#createHUFromInventory(CreateHUFromInventoryRequest)} sets the given weight on the new CU.
 * Needs the real HU stack (weight attributes on a real CU), which {@link HUQtyServiceTest} does not set up; completing the inventory
 * is stubbed to a no-op, the CU is created up front by {@link HUTestHelper}.
 */
@ExtendWith(AdempiereTestWatcher.class)
class HUQtyServiceWeightTest
{
	private static final ZonedDateTime MOVEMENT_DATE = ZonedDateTime.of(2026, 1, 15, 10, 30, 0, 0, ZoneId.of("Europe/Berlin"));

	private HUTestHelper helper;
	private HUQtyService huQtyService;
	private ProductId productId;
	private LocatorId locatorId;
	private HuId cuId;
	private BigDecimal weightNetBefore;

	@BeforeEach
	void beforeEach()
	{
		helper = HUTestHelper.newInstanceOutOfTrx();

		final InventoryService inventoryService = Mockito.spy(InventoryService.newInstanceForUnitTesting());
		huQtyService = new HUQtyService(inventoryService, HUQRCodesService.newInstanceForUnitTesting());

		Services.get(IDocTypeDAO.class).createDocType(IDocTypeDAO.DocTypeCreateRequest.builder()
				.ctx(Env.getCtx())
				.docBaseType(DocBaseType.MaterialPhysicalInventory)
				.docSubType(DocSubType.SingleHUInventory)
				.name("inventory")
				.glCategoryId(GLCategoryId.ofRepoId(123))
				.build());

		final I_C_UOM uomKg = helper.uomKg;
		productId = BusinessTestHelper.createProductId("product", uomKg);
		final I_M_Warehouse warehouse = BusinessTestHelper.createWarehouse("warehouse");
		final I_M_Locator locator = BusinessTestHelper.createLocator("locator", warehouse);
		locatorId = LocatorId.ofRecord(locator);

		final I_M_HU cu = helper.newVHU()
				.productId(productId)
				.qty(Quantity.of("12.345", uomKg))
				.huStatus(X_M_HU.HUSTATUS_Active)
				.locatorId(locatorId)
				.build();
		cuId = HuId.ofRepoId(cu.getM_HU_ID());

		// the new CU's QR code
		new HUQRCodesRepository().createNew(HUQRCode.builder()
				.id(HUQRCodeUniqueId.random())
				.packingInfo(HUQRCodePackingInfo.builder().huUnitType(HUQRCodeUnitType.VHU).packingInstructionsId(HuPackingInstructionsId.VIRTUAL).caption("CU").build())
				.attributes(ImmutableList.of())
				.build(), cuId);

		weightNetBefore = weightNetOf(cuId);

		// completing the inventory would create the HU; here only its effect is simulated: the completed SINGLE_HU line points to the CU
		doAnswer(invocation -> {
			final I_M_InventoryLine line = Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine.class).create().firstOnlyNotNull(I_M_InventoryLine.class);
			line.setM_HU_ID(cuId.getRepoId());
			saveRecord(line);
			return null;
		}).when(inventoryService).completeDocument(any());
	}

	@Test
	void createHUFromInventory_withWeight_setsWeightNetOnTheNewCU()
	{
		final CreatedHU created = inCallersTrx(request(new BigDecimal("12.345")));

		assertThat(created.getHuId()).isEqualTo(cuId);
		assertThat(weightNetBefore).isNotEqualByComparingTo("12.345"); // else the test proves nothing
		assertThat(weightNetOf(cuId)).isEqualByComparingTo("12.345");
	}

	@Test
	void createHUFromInventory_withoutWeight_leavesWeightNetUntouched()
	{
		final CreatedHU created = inCallersTrx(request(null));

		assertThat(created.getHuId()).isEqualTo(cuId);
		assertThat(weightNetOf(cuId)).isEqualTo(weightNetBefore);
	}

	/** the method runs in the caller's transaction (HU attributes are written in it), see its Javadoc */
	private CreatedHU inCallersTrx(final CreateHUFromInventoryRequest request)
	{
		return Services.get(ITrxManager.class).callInThreadInheritedTrx(() -> huQtyService.createHUFromInventory(request));
	}

	private CreateHUFromInventoryRequest request(final BigDecimal weightInKg)
	{
		return CreateHUFromInventoryRequest.builder()
				.date(MOVEMENT_DATE)
				.productId(productId)
				.qty(Quantity.of("12.345", helper.uomKg))
				.locatorId(locatorId)
				.attributes(ImmutableAttributeSet.EMPTY)
				.weightInKg(weightInKg != null ? Quantity.of(weightInKg, helper.uomKg) : null)
				.build();
	}

	private BigDecimal weightNetOf(final HuId huId)
	{
		final I_M_HU hu = Services.get(IHandlingUnitsBL.class).getById(huId);
		final IWeightable weightable = Weightables.wrap(Services.get(IHUContextFactory.class)
				.createMutableHUContext()
				.getHUAttributeStorageFactory()
				.getAttributeStorage(hu));
		return weightable.getWeightNetOrNull();
	}
}
