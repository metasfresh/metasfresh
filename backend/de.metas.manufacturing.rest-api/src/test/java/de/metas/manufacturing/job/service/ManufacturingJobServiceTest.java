package de.metas.manufacturing.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.job.model.FinishedGoodsReceive;
import de.metas.manufacturing.job.model.FinishedGoodsReceiveLine;
import de.metas.manufacturing.job.model.ManufacturingJob;
import de.metas.manufacturing.job.model.ManufacturingJobActivity;
import de.metas.manufacturing.job.model.ManufacturingJobActivityId;
import de.metas.manufacturing.workflows_api.rest_api.json.JsonManufacturingOrderEvent;
import de.metas.material.planning.pporder.PPAlwaysAvailableToUser;
import de.metas.material.planning.pporder.PPRoutingActivityType;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_MobileUI_UserProfile_MFG;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ManufacturingJobServiceTest
{
	private ManufacturingJobService manufacturingJobService;
	private ISysConfigDAO sysConfigDAO;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		this.manufacturingJobService = ManufacturingJobService.newInstanceForUnitTesting();
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

	/**
	 * Catch weight with a mandatory scan is received one at a time: one receipt = one unit carrying the one scanned weight.
	 * Any other qty (e.g. from a stale cached mobile client or a direct API call) would put one unit's weight on an HU of N units.
	 */
	@Nested
	class catchWeightScanRequired_receiveOneAtATime
	{
		private I_C_UOM uomPCE;
		private UomId uomKgId;
		private ProductId productId;

		@BeforeEach
		void beforeEach()
		{
			uomPCE = BusinessTestHelper.createUomPCE();
			uomKgId = UomId.ofRepoId(BusinessTestHelper.createUomKg().getC_UOM_ID());
			productId = BusinessTestHelper.createProductId("catch weight product", uomPCE);
		}

		@Test
		void receiveGoods_rejectsMoreThanOneUnit()
		{
			final UserId responsibleId = UserId.ofRepoId(1234);
			final I_MobileUI_UserProfile_MFG userConfig = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class);
			userConfig.setAD_User_ID(responsibleId.getRepoId());
			userConfig.setIsCaptureCatchWeightAtReceipt("Y");
			userConfig.setIsFinishedGoodsCatchWeightScanRequired("Y");
			InterfaceWrapperHelper.saveRecord(userConfig);

			final FinishedGoodsReceiveLine line = receiveLine(uomKgId, null);
			final ManufacturingJob job = job(line, responsibleId);

			assertThatThrownBy(() -> manufacturingJobService.receiveGoods(receiveFrom(line, "2"), job, SystemTime.asZonedDateTime()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ManufacturingJobService.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		@Test
		void scanRequired_moreThanOneUnit_rejected()
		{
			assertThatThrownBy(() -> assertQty(config(true, true), receiveLine(uomKgId, null), "2"))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ManufacturingJobService.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		@Test
		void scanRequired_fractionOfAUnit_rejected()
		{
			assertThatThrownBy(() -> assertQty(config(true, true), receiveLine(uomKgId, null), "0.5"))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void scanRequired_oneUnit_accepted()
		{
			assertThatCode(() -> assertQty(config(true, true), receiveLine(uomKgId, null), "1.000")).doesNotThrowAnyException();
		}

		@Test
		void settingOff_anyQty_accepted()
		{
			assertThatCode(() -> assertQty(config(false, true), receiveLine(uomKgId, null), "5")).doesNotThrowAnyException();
		}

		@Test
		void coProduct_anyQty_accepted()
		{
			assertThatCode(() -> assertQty(config(true, true), receiveLine(uomKgId, PPOrderBOMLineId.ofRepoId(1)), "5")).doesNotThrowAnyException();
		}

		@Test
		void notACatchWeightProduct_anyQty_accepted()
		{
			assertThatCode(() -> assertQty(config(true, true), receiveLine(null, null), "5")).doesNotThrowAnyException();
		}

		@Test
		void catchWeightNotCapturedAtReceipt_anyQty_accepted()
		{
			// no weight is captured, so there is nothing to scan: the mobile client does not require the scan either
			assertThatCode(() -> assertQty(config(true, false), receiveLine(uomKgId, null), "5")).doesNotThrowAnyException();
		}

		private void assertQty(final MobileUIManufacturingConfig config, final FinishedGoodsReceiveLine line, final String qty)
		{
			ManufacturingJobService.assertOneUnitPerReceiptIfCatchWeightScanRequired(line, config, new BigDecimal(qty));
		}

		private MobileUIManufacturingConfig config(final boolean scanRequired, final boolean captureCatchWeight)
		{
			return MobileUIManufacturingConfig.builder()
					.isScanResourceRequired(OptionalBoolean.UNKNOWN)
					.isAllowIssuingAnyHU(OptionalBoolean.UNKNOWN)
					.isBestBeforeDateEditable(OptionalBoolean.UNKNOWN)
					.isLotNumberEditable(OptionalBoolean.UNKNOWN)
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.UNKNOWN)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.UNKNOWN)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.UNKNOWN)
					.isCaptureCatchWeightAtReceipt(OptionalBoolean.ofBoolean(captureCatchWeight))
					.isFinishedGoodsCatchWeightScanRequired(OptionalBoolean.ofBoolean(scanRequired))
					.build();
		}

		private FinishedGoodsReceiveLine receiveLine(@Nullable final UomId catchWeightUOMId, @Nullable final PPOrderBOMLineId coProductBOMLineId)
		{
			return FinishedGoodsReceiveLine.builder()
					.productId(productId)
					.productName(TranslatableStrings.anyLanguage("product"))
					.productValue("P")
					.attributes(ImmutableAttributeSet.EMPTY)
					.qtyToReceive(Quantity.of(BigDecimal.TEN, uomPCE))
					.qtyReceived(Quantity.zero(uomPCE))
					.coProductBOMLineId(coProductBOMLineId)
					.catchWeightUOMId(catchWeightUOMId)
					.build();
		}

		private ManufacturingJob job(final FinishedGoodsReceiveLine line, final UserId responsibleId)
		{
			final PPOrderId ppOrderId = PPOrderId.ofRepoId(1);
			final PPOrderRoutingActivityId routingActivityId = PPOrderRoutingActivityId.ofRepoId(ppOrderId, 1);
			return ManufacturingJob.builder()
					.ppOrderId(ppOrderId)
					.documentNo("PP1")
					.dateStartSchedule(SystemTime.asZonedDateTime())
					.responsibleId(responsibleId)
					.warehouseId(WarehouseId.ofRepoId(1))
					.activities(ImmutableList.of(ManufacturingJobActivity.builder()
							.id(ManufacturingJobActivityId.of(routingActivityId))
							.name("Receive")
							.type(PPRoutingActivityType.MaterialReceipt)
							.finishedGoodsReceive(FinishedGoodsReceive.builder().linesById(ImmutableMap.of(line.getId(), line)).build())
							.orderRoutingActivityId(routingActivityId)
							.routingActivityStatus(PPOrderRoutingActivityStatus.NOT_STARTED)
							.alwaysAvailableToUser(PPAlwaysAvailableToUser.DEFAULT)
							.build()))
					.build();
		}

		private JsonManufacturingOrderEvent.ReceiveFrom receiveFrom(final FinishedGoodsReceiveLine line, final String qty)
		{
			return JsonManufacturingOrderEvent.ReceiveFrom.builder()
					.lineId(line.getId().toJson())
					.qtyReceived(new BigDecimal(qty))
					.catchWeight(new BigDecimal("0.101"))
					.catchWeightUomSymbol("kg")
					.build();
		}
	}
}
