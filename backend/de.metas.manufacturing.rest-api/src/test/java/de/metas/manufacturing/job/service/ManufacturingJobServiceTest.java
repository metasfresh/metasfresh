package de.metas.manufacturing.job.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.business.BusinessTestHelper;
import org.eevolution.model.I_PP_Order;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonTUReceivingTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.manufacturing.job.service.commands.receive.ReceiveGoodsCommand;
import de.metas.manufacturing.config.ReceiveUnitType;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.common.util.time.SystemTime;
import de.metas.i18n.TranslatableStrings;
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
import org.eevolution.api.PPOrderId;
import org.eevolution.api.PPOrderRoutingActivityId;
import org.eevolution.api.PPOrderRoutingActivityStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
	 * Catch weight with a mandatory scan is received one at a time: one receipt = one piece carrying the one scanned weight.
	 * Any other qty (e.g. from a stale cached mobile client or a direct API call) would put one piece's weight on N pieces.
	 * The guard itself (counting pieces in every receive mode) is unit-tested in ReceiveGoodsCommandTest;
	 * the effective "scan required" condition in MobileUIManufacturingConfigTest.
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
		void receiveGoods_rejectsMoreThanOnePiece()
		{
			final UserId responsibleId = UserId.ofRepoId(1234);
			userConfig(responsibleId, null);
			final FinishedGoodsReceiveLine line = receiveLine();
			final ManufacturingJob job = job(line, responsibleId);

			assertThatThrownBy(() -> manufacturingJobService.receiveGoods(receiveFrom(line, "2"), job, SystemTime.asZonedDateTime()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		/**
		 * In TU receive mode the qty is NOT a number of TUs when the scan is required: 2 means 2 pieces and is rejected.
		 */
		@Test
		void receiveGoods_TUMode_rejectsMoreThanOnePiece()
		{
			final UserId responsibleId = UserId.ofRepoId(1235);
			userConfig(responsibleId, ReceiveUnitType.TU);
			final FinishedGoodsReceiveLine line = receiveLine();
			final ManufacturingJob job = job(line, responsibleId);

			assertThatThrownBy(() -> manufacturingJobService.receiveGoods(receiveFrom(line, "2"), job, SystemTime.asZonedDateTime()))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining(ReceiveGoodsCommand.MSG_CatchWeightReceiveOneAtATime.toAD_Message());
		}

		private void userConfig(final UserId responsibleId, @Nullable final ReceiveUnitType receiveUnitType)
		{
			final I_MobileUI_UserProfile_MFG userConfig = InterfaceWrapperHelper.newInstance(I_MobileUI_UserProfile_MFG.class);
			userConfig.setAD_User_ID(responsibleId.getRepoId());
			userConfig.setIsCaptureCatchWeightAtReceipt("Y");
			userConfig.setIsFinishedGoodsCatchWeightScanRequired("Y");
			if (receiveUnitType != null)
			{
				userConfig.setReceiveUnitType(receiveUnitType.getCode());
			}
			InterfaceWrapperHelper.saveRecord(userConfig);
		}

		private FinishedGoodsReceiveLine receiveLine()
		{
			return FinishedGoodsReceiveLine.builder()
					.productId(productId)
					.productName(TranslatableStrings.anyLanguage("product"))
					.productValue("P")
					.attributes(ImmutableAttributeSet.EMPTY)
					.qtyToReceive(Quantity.of(BigDecimal.TEN, uomPCE))
					.qtyReceived(Quantity.zero(uomPCE))
					.catchWeightUOMId(uomKgId)
					.build();
		}

		private ManufacturingJob job(final FinishedGoodsReceiveLine line, final UserId responsibleId)
		{
			final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
			ppOrder.setM_Product_ID(productId.getRepoId());
			ppOrder.setC_UOM_ID(uomPCE.getC_UOM_ID());
			InterfaceWrapperHelper.saveRecord(ppOrder);

			final PPOrderId ppOrderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
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
					.aggregateToTU(JsonTUReceivingTarget.builder()
							.newTU(JsonNewTUTarget.builder().tuPIItemProductId(HUPIItemProductId.ofRepoId(1000)).build())
							.build())
					.catchWeight(new BigDecimal("0.101"))
					.catchWeightUomSymbol("kg")
					.build();
		}
	}
}
