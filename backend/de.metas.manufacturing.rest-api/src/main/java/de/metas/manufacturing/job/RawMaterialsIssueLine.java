package de.metas.manufacturing.job;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class RawMaterialsIssueLine
{
	@NonNull ProductId productId;
	@NonNull ITranslatableString productName;
	@NonNull Quantity qtyToIssue;
	@NonNull Quantity qtyIssued;

	@NonNull ImmutableList<RawMaterialsIssueStep> steps;
}
