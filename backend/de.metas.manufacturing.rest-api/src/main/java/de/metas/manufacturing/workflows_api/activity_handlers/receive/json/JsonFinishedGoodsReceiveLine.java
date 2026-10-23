package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonAllergen;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonHazardSymbol;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonFinishedGoodsReceiveLine
{
	@NonNull String id;

	boolean coproduct;
	boolean skipReceiveTargetStep;
	/**
	 * {@code true} if a catch-weight line can only be received by scanning a code that the operator then confirms (no manual input)
	 */
	boolean catchWeightScanRequired;
	/**
	 * {@code true} if each receipt of this line is exactly one piece (scan required AND weight captured at receipt);
	 * otherwise the operator enters any qty. The client uses it as is.
	 */
	boolean receiveOnePiecePerScan;
	@NonNull String productName;
	@NonNull String uom;
	@Nullable String catchWeightUomSymbol;
	@NonNull List<JsonHazardSymbol> hazardSymbols;
	@NonNull List<JsonAllergen> allergens;

	@NonNull BigDecimal qtyToReceive;
	@NonNull BigDecimal qtyReceived;

	@Nullable JsonHUQRCodeTarget currentReceivingHU;

	@NonNull JsonNewLUTargetsList availableReceivingTargets;
	@NonNull JsonNewTUTargetList availableReceivingTUTargets;

	/**
	 * Pre-filled best-before date (MHD); {@code null} if there is no default
	 */
	@Nullable @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate defaultBestBeforeDate;

	/**
	 * Pre-filled lot number; {@code null} if there is no default
	 */
	@Nullable String defaultLotNo;
}
