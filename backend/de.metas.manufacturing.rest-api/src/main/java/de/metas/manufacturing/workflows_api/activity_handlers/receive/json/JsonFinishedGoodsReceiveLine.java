package de.metas.manufacturing.workflows_api.activity_handlers.receive.json;

import de.metas.handlingunits.attribute.json.JsonAttribute;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonAllergen;
import de.metas.manufacturing.workflows_api.activity_handlers.issue.json.JsonHazardSymbol;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonFinishedGoodsReceiveLine
{
	@NonNull String id;

	boolean coproduct;
	boolean skipReceiveTargetStep;
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
	 * The generic, per-line editable-attribute list (issue #31771 Task 6): the mobile-UI Manufacturing
	 * Configuration's editable-attribute list, restricted to this line's product {@code M_AttributeSet} and to
	 * instance-level attributes, in the config's {@code SeqNo} order.
	 */
	@NonNull List<JsonAttribute> editableAttributes;
}
