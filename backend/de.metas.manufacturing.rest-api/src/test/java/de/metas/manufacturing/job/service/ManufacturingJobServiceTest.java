package de.metas.manufacturing.job.service;

import de.metas.device.accessor.DeviceAccessorsHubFactory;
import de.metas.device.config.DeviceConfigPoolFactory;
import de.metas.device.websocket.DeviceWebsocketNamingStrategy;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleRepository;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleService;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHURepository;
import de.metas.handlingunits.pporder.source_hu.PPOrderSourceHUService;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.manufacturing.job.model.HUInfo;
import de.metas.manufacturing.job.model.LocatorInfo;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ManufacturingJobServiceTest
{
	private ManufacturingJobService manufacturingJobService;
	private ISysConfigDAO sysConfigDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final PPOrderIssueScheduleService ppOrderIssueScheduleService = new PPOrderIssueScheduleService(
				new PPOrderIssueScheduleRepository(),
				new HUQtyService(InventoryService.newInstanceForUnitTesting())
		);

		this.manufacturingJobService = new ManufacturingJobService(
				ppOrderIssueScheduleService,
				new HUReservationService(new HUReservationRepository()),
				new PPOrderSourceHUService(new PPOrderSourceHURepository(), ppOrderIssueScheduleService),
				new DeviceAccessorsHubFactory(new DeviceConfigPoolFactory()),
				new DeviceWebsocketNamingStrategy("/test/"),
				HUQRCodesService.newInstanceForUnitTesting(),
				new MobileUIManufacturingConfigRepository()
		);

		this.sysConfigDAO = Services.get(ISysConfigDAO.class);
	}

	@Nested
	class getDefaultFilters
	{
		@Test
		void empty()
		{
			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet()).isEmpty();
		}

		@Test
		void userPlant()
		{
			// IMPORTANT: set the value as plain string to also enforce the name of the enums are not changed on refactoring
			sysConfigDAO.setValue(ManufacturingJobService.SYSCONFIG_defaultFilters, "UserPlant", ClientAndOrgId.SYSTEM);

			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet())
					.contains(ManufacturingJobDefaultFilter.UserPlant);
		}

		@Test
		void allEnumValues()
		{
			// IMPORTANT: set the value as plain string to also enforce the name of the enums are not changed on refactoring
			sysConfigDAO.setValue(ManufacturingJobService.SYSCONFIG_defaultFilters, "UserPlant, TodayDateStartSchedule", ClientAndOrgId.SYSTEM);

			Assertions.assertThat(manufacturingJobService.getDefaultFilters().toSet())
					.contains(ManufacturingJobDefaultFilter.UserPlant, ManufacturingJobDefaultFilter.TodayDateStartSchedule);
		}
	}

	@Nested
	class assertEmptyingAllowed
	{
		private RawMaterialsIssueStep stepWithAllowEmptying(final boolean isAllowEmptying)
		{
			final I_C_UOM uom = newInstance(I_C_UOM.class);
			uom.setUOMSymbol("Ea");
			final Quantity qty = Quantity.of(BigDecimal.TEN, uom);

			return RawMaterialsIssueStep.builder()
					.id(PPOrderIssueScheduleId.ofRepoId(1))
					.productId(ProductId.ofRepoId(1))
					.productName(TranslatableStrings.constant("Test product"))
					.qtyToIssue(qty)
					.issueFromLocator(LocatorInfo.builder()
							.id(LocatorId.ofRepoId(1, 1))
							.caption("Loc")
							.qrCode(LocatorQRCode.builder().locatorId(LocatorId.ofRepoId(1, 1)).caption("Loc").build())
							.build())
					.issueFromHU(HUInfo.builder()
							.id(HuId.ofRepoId(1))
							.huCapacity(qty)
							.build())
					.isAllowEmptying(isAllowEmptying)
					.build();
		}

		private MobileUIManufacturingConfig configWithOffer(final boolean offerEmptyingHUs)
		{
			return MobileUIManufacturingConfig.builder()
					.isScanResourceRequired(OptionalBoolean.FALSE)
					.isAllowIssuingAnyHU(OptionalBoolean.FALSE)
					.isAllowEmptyingHUs(offerEmptyingHUs ? OptionalBoolean.TRUE : OptionalBoolean.FALSE)
					.build();
		}

		@Test
		void reasonNotEmptied_doesNotThrow_evenWhenStepAndConfigWouldRefuse()
		{
			ManufacturingJobService.assertEmptyingAllowed(stepWithAllowEmptying(false), configWithOffer(false), null);
			// no exception
		}

		@Test
		void stepEligible_configOffers_doesNotThrow()
		{
			ManufacturingJobService.assertEmptyingAllowed(stepWithAllowEmptying(true), configWithOffer(true), QtyRejectedReasonCode.EMPTIED);
			// no exception
		}

		@Test
		void stepNotEligible_configOffers_throws()
		{
			assertThatThrownBy(() -> ManufacturingJobService.assertEmptyingAllowed(stepWithAllowEmptying(false), configWithOffer(true), QtyRejectedReasonCode.EMPTIED))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
		}

		@Test
		void stepEligible_configDoesNotOffer_throws()
		{
			assertThatThrownBy(() -> ManufacturingJobService.assertEmptyingAllowed(stepWithAllowEmptying(true), configWithOffer(false), QtyRejectedReasonCode.EMPTIED))
					.isInstanceOf(org.adempiere.exceptions.AdempiereException.class);
		}
	}
}