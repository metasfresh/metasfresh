package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Value
public class HUQRCodeGenerateRequest
{
	int count;
	@NonNull HuPackingInstructionsId huPackingInstructionsId;
	@NonNull ProductId productId;
	@NonNull ImmutableList<Attribute> attributes;

	@Builder
	private HUQRCodeGenerateRequest(
			final int count,
			@NonNull final HuPackingInstructionsId huPackingInstructionsId,
			@NonNull final ProductId productId,
			@NonNull final List<Attribute> attributes)
	{
		Check.assumeGreaterThanZero(count, "count");

		this.count = count;
		this.huPackingInstructionsId = huPackingInstructionsId;
		this.productId = productId;
		this.attributes = ImmutableList.copyOf(attributes);
	}

	//
	//
	//

	@Value
	@Builder
	public static class Attribute
	{
		@NonNull AttributeId attributeId;

		@Nullable String valueString;
		@Nullable BigDecimal valueNumber;
		@Nullable LocalDate valueDate;
		@Nullable AttributeValueId valueListId;
	}
}
