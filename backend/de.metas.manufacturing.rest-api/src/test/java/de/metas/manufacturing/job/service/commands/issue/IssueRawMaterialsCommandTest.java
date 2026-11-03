package de.metas.manufacturing.job.service.commands.issue;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.QtyRejectedReasonCode;
import de.metas.handlingunits.pporder.api.issue_schedule.PPOrderIssueScheduleId;
import de.metas.i18n.TranslatableStrings;
import de.metas.manufacturing.config.MobileUIManufacturingConfig;
import de.metas.manufacturing.job.model.HUInfo;
import de.metas.manufacturing.job.model.LocatorInfo;
import de.metas.manufacturing.job.model.RawMaterialsIssueStep;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.OptionalBoolean;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class IssueRawMaterialsCommandTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	class assertEmptyingAllowed
	{
		// IMPORTANT: plain string, not a reference to the private IssueRawMaterialsCommand.MSG_EmptyingNotAllowedForHU
		// field, so a refactoring that silently changes the message key also breaks this test.
		private static final String MSG_EMPTYING_NOT_ALLOWED_FOR_HU = "de.metas.manufacturing.job.service.EmptyingNotAllowedForHU";

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
					.editableAttributeCodesInOrder(ImmutableList.of())
					.isAllowFinishedGoodsReceiveToLU(OptionalBoolean.UNKNOWN)
					.isAllowFinishedGoodsReceiveToTU(OptionalBoolean.UNKNOWN)
					.isSkipFinishedGoodsReceiveTargetStep(OptionalBoolean.UNKNOWN)
					.isCaptureCatchWeightAtReceipt(OptionalBoolean.UNKNOWN)
					.isAllowReceiveWithoutPackingItem(OptionalBoolean.UNKNOWN)
					.isAllowEmptyingHUs(offerEmptyingHUs ? OptionalBoolean.TRUE : OptionalBoolean.FALSE)
					.build();
		}

		@Test
		void reasonNotEmptied_doesNotThrow_evenWhenStepAndConfigWouldRefuse()
		{
			IssueRawMaterialsCommand.assertEmptyingAllowed(stepWithAllowEmptying(false), configWithOffer(false), null);
			// no exception
		}

		@Test
		void stepEligible_configOffers_doesNotThrow()
		{
			IssueRawMaterialsCommand.assertEmptyingAllowed(stepWithAllowEmptying(true), configWithOffer(true), QtyRejectedReasonCode.EMPTIED);
			// no exception
		}

		@Test
		void stepNotEligible_configOffers_throws()
		{
			assertThatThrownBy(() -> IssueRawMaterialsCommand.assertEmptyingAllowed(stepWithAllowEmptying(false), configWithOffer(true), QtyRejectedReasonCode.EMPTIED))
					.isInstanceOf(AdempiereException.class)
					.extracting(e -> ((AdempiereException)e).getErrorCode())
					.isEqualTo(MSG_EMPTYING_NOT_ALLOWED_FOR_HU);
		}

		@Test
		void stepEligible_configDoesNotOffer_throws()
		{
			assertThatThrownBy(() -> IssueRawMaterialsCommand.assertEmptyingAllowed(stepWithAllowEmptying(true), configWithOffer(false), QtyRejectedReasonCode.EMPTIED))
					.isInstanceOf(AdempiereException.class)
					.extracting(e -> ((AdempiereException)e).getErrorCode())
					.isEqualTo(MSG_EMPTYING_NOT_ALLOWED_FOR_HU);
		}
	}
}
