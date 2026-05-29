package de.metas.frontend_testing.expectations.request;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.handlingunits.QtyTU;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonShipmentScheduleQtyPickedExpectation
{
	@Nullable QtyAndUOMString qtyPicked;
	@Nullable QtyAndUOMString catchWeight;
	@Nullable QtyTU qtyTUs;
	@Nullable Integer qtyLUs;
	@Nullable Boolean processed;

	@Nullable Identifier vhu;
	@Nullable Identifier tu;
	@Nullable Identifier lu;

	@Nullable Identifier shipmentLineId;
}
