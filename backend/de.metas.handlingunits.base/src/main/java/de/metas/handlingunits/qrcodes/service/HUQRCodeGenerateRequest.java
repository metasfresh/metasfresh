package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.AttributeConstants;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

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
			@Nullable final HuPackingInstructionsId huPackingInstructionsId,
			@NonNull final ProductId productId,
			@NonNull @Singular("_attribute") final List<Attribute> attributes)
	{
		Check.assumeGreaterThanZero(count, "count");

		this.count = count;
		this.huPackingInstructionsId = CoalesceUtil.coalesceNotNull(huPackingInstructionsId, HuPackingInstructionsId.VIRTUAL);
		this.productId = productId;
		this.attributes = ImmutableList.copyOf(attributes);
	}

	//
	//
	//

	@SuppressWarnings("unused")
	public static class HUQRCodeGenerateRequestBuilder
	{
		public HUQRCodeGenerateRequestBuilder attribute(@NonNull AttributeId attributeId, @Nullable String valueString)
		{
			return _attribute(Attribute.builder().attributeId(attributeId).valueString(valueString).build());
		}

		public HUQRCodeGenerateRequestBuilder attribute(@NonNull AttributeId attributeId, @Nullable BigDecimal valueNumber)
		{
			return _attribute(Attribute.builder().attributeId(attributeId).valueNumber(valueNumber).build());
		}

		public HUQRCodeGenerateRequestBuilder attribute(@NonNull AttributeId attributeId, @Nullable LocalDate valueDate)
		{
			return _attribute(Attribute.builder().attributeId(attributeId).valueDate(valueDate).build());
		}

		public HUQRCodeGenerateRequestBuilder attribute(@NonNull AttributeId attributeId, @Nullable AttributeValueId valueListId)
		{
			return _attribute(Attribute.builder().attributeId(attributeId).valueListId(valueListId).build());
		}

		public HUQRCodeGenerateRequestBuilder lotNo(@Nullable final String lotNo, @NonNull final Function<AttributeCode, AttributeId> getAttributeIdByCode)
		{
			final AttributeId attributeId = getAttributeIdByCode.apply(AttributeConstants.ATTR_LotNumber);
			return attribute(attributeId, StringUtils.trimBlankToNull(lotNo));
		}

	}

	//
	//
	//

	@Value
	@Builder
	public static class Attribute
	{
		@Nullable AttributeId attributeId;
		@Nullable AttributeCode code;

		@Nullable String valueString;
		@Nullable BigDecimal valueNumber;
		@Nullable LocalDate valueDate;
		@Nullable AttributeValueId valueListId;
	}
}
