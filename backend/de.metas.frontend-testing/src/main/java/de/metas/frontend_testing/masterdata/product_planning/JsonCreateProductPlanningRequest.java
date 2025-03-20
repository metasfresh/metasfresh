package de.metas.frontend_testing.masterdata.product_planning;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateProductPlanningRequest
{
	@NonNull Identifier product;
	@Nullable Identifier plant;
	@Nullable Identifier warehouse;
	@Nullable Identifier bom;
	boolean pickingOrder;
	int seqNo;
}
