package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.util.List;

@Value
public class JsonQRCodesGenerateRequest
{
	int count;
	@NonNull HuPackingInstructionsId huPackingInstructionsId;
	@NonNull ProductId productId;
	@NonNull List<Attribute> attributes;

	boolean onlyPrint;

	@Builder
	@Jacksonized
	private JsonQRCodesGenerateRequest(
			final int count,
			@NonNull final HuPackingInstructionsId huPackingInstructionsId,
			@NonNull final ProductId productId,
			@Nullable final List<Attribute> attributes,
			final boolean onlyPrint)
	{
		Check.assumeGreaterThanZero(count, "count");

		this.count = count;
		this.huPackingInstructionsId = huPackingInstructionsId;
		this.productId = productId;
		this.attributes = attributes != null ? ImmutableList.copyOf(attributes) : ImmutableList.of();
		this.onlyPrint = onlyPrint;
	}

	@Value
	@Builder
	@Jacksonized
	public static class Attribute
	{
		@NonNull AttributeCode attributeCode;
		@Nullable String value;
	}
}
