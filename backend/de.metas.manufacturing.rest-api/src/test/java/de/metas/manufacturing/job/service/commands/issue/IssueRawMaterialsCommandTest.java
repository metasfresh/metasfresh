package de.metas.manufacturing.job.service.commands.issue;

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
					.isBestBeforeDateEditable(OptionalBoolean.TRUE)
					.isLotNumberEditable(OptionalBoolean.TRUE)
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

		// An ALTERNATIVE (non-FEFO) step that is a pallet-nested TU (not top-level) must stay locked: a
		// replayed/crafted EMPTIED on it is refused even though the TU itself is emptying-eligible.
		private RawMaterialsIssueStep alternativeStep(final boolean isAllowEmptying, final boolean isTopLevelHU)
		{
			final I_C_UOM uom = newInstance(I_C_UOM.class);
			uom.setUOMSymbol("Ea");
			final Quantity qty = Quantity.of(BigDecimal.TEN, uom);

			return RawMaterialsIssueStep.builder()
					.id(PPOrderIssueScheduleId.ofRepoId(1))
					.isAlternativeIssue(true)
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
					.isIssueFromTopLevelHU(isTopLevelHU)
					.build();
		}

		@Test
		void alternativeStep_notTopLevelHU_throws()
		{
			assertThatThrownBy(() -> IssueRawMaterialsCommand.assertEmptyingAllowed(alternativeStep(true, false), configWithOffer(true), QtyRejectedReasonCode.EMPTIED))
					.isInstanceOf(AdempiereException.class)
					.extracting(e -> ((AdempiereException)e).getErrorCode())
					.isEqualTo(MSG_EMPTYING_NOT_ALLOWED_FOR_HU);
		}

		@Test
		void alternativeStep_topLevelHU_doesNotThrow()
		{
			IssueRawMaterialsCommand.assertEmptyingAllowed(alternativeStep(true, true), configWithOffer(true), QtyRejectedReasonCode.EMPTIED);
			// no exception: a free-standing top-level alternative HU is the feature's intended empty target
		}
	}

	@Nested
	class assertWithinRemainingDemand
	{
		// IMPORTANT: plain string, not a reference to the private
		// IssueRawMaterialsCommand.MSG_CannotIssueMoreThanRemainingDemand field, so a refactoring that
		// silently changes the message key also breaks this test.
		private static final String MSG_CANNOT_ISSUE_MORE_THAN_REMAINING = "de.metas.manufacturing.job.service.CannotIssueMoreThanRemainingDemand";

		private RawMaterialsIssueStep step()
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
					.build();
		}

		private Quantity qty(final String value)
		{
			final I_C_UOM uom = newInstance(I_C_UOM.class);
			uom.setUOMSymbol("kg");
			return Quantity.of(new BigDecimal(value), uom);
		}

		@Test
		void qtyIssuedBelowRemaining_doesNotThrow()
		{
			IssueRawMaterialsCommand.assertWithinRemainingDemand(step(), qty("10"), new BigDecimal("9"));
			// no exception
		}

		@Test
		void qtyIssuedEqualsRemaining_doesNotThrow()
		{
			IssueRawMaterialsCommand.assertWithinRemainingDemand(step(), qty("10"), new BigDecimal("10"));
			// no exception: issuing exactly the remaining demand is allowed
		}

		@Test
		void qtyIssuedExceedsRemaining_throws()
		{
			assertThatThrownBy(() -> IssueRawMaterialsCommand.assertWithinRemainingDemand(step(), qty("10"), new BigDecimal("11")))
					.isInstanceOf(AdempiereException.class)
					.extracting(e -> ((AdempiereException)e).getErrorCode())
					.isEqualTo(MSG_CANNOT_ISSUE_MORE_THAN_REMAINING);
		}

		@Test
		void qtyIssuedWithFloatNoiseAtBoundary_doesNotThrow()
		{
			// an on-scale weightable issue can arrive as 5.200000000000001 (unrounded JS subtraction); the
			// float-noise tolerance absorbs it, so it is not refused as an over-issue.
			IssueRawMaterialsCommand.assertWithinRemainingDemand(step(), qty("5.2"), new BigDecimal("5.200000000000001"));
			// no exception
		}

		@Test
		void qtyIssuedFractionallyOver_throws()
		{
			// a real sub-unit over-issue (0.4 over a demand of 10) must be refused regardless of UOM
			// precision -- a UOM-precision HALF_UP round would have admitted it at precision 0.
			assertThatThrownBy(() -> IssueRawMaterialsCommand.assertWithinRemainingDemand(step(), qty("10"), new BigDecimal("10.4")))
					.isInstanceOf(AdempiereException.class);
		}

		@Test
		void qtyIssuedEqualsFinerScaledRemaining_doesNotThrow()
		{
			// issuing exactly a finer-scaled remaining demand (5.25) must be allowed -- a UOM-precision round
			// of the request could have pushed it above the ceiling and falsely refused it.
			IssueRawMaterialsCommand.assertWithinRemainingDemand(step(), qty("5.25"), new BigDecimal("5.25"));
			// no exception
		}
	}
}
