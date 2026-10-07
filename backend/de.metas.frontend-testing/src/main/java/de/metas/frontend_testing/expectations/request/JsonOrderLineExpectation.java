package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * Expectation for one order line (C_OrderLine) of a sales order, matched by its product: exactly one line of the
 * order must carry that product. Order lines of other products are not looked at. All other fields are optional;
 * when null, they are not asserted. Decimals are compared by value (scale-insensitive).
 */
@Value
@Builder
@Jacksonized
public class JsonOrderLineExpectation
{
	/**
	 * Masterdata map key of the line's product (resolved through the carried context).
	 */
	@NonNull Identifier product;

	/**
	 * Expected C_OrderLine.QtyEntered.
	 */
	@Nullable BigDecimal qtyEntered;

	/**
	 * Expected C_OrderLine.GroupCompensationCalibrationFactor.
	 */
	@Nullable BigDecimal calibrationFactor;

	/**
	 * Masterdata map key of the calibration rule expected in C_OrderLine.C_CompensationGroup_CalibrationRule_ID.
	 */
	@Nullable Identifier calibrationRule;

	/**
	 * Expected C_OrderLine.GroupCompensationQtyEnteredUncalibrated.
	 */
	@Nullable BigDecimal qtyEnteredUncalibrated;

	/**
	 * {@code false}: the line carries no calibration (factor, rule and uncalibrated qty are all empty);
	 * {@code true}: the line is calibrated (factor and uncalibrated qty are set, the rule may be empty).
	 * {@code false} cannot be combined with an expected factor, rule or uncalibrated qty.
	 */
	@Nullable Boolean calibrated;

	/**
	 * {@code false}: C_OrderLine.C_CompensationGroup_CalibrationRule_ID is empty (e.g. calibrated with factor 1 because no rule matched);
	 * {@code true}: some rule is stored. Cannot be combined with an expected {@link #calibrationRule}.
	 */
	@Nullable Boolean hasCalibrationRule;
}
