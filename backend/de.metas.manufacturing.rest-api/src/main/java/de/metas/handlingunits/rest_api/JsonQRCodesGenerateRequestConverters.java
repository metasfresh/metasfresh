package de.metas.handlingunits.rest_api;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateRequest;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;

import java.math.BigDecimal;
import java.time.LocalDate;

@UtilityClass
public class JsonQRCodesGenerateRequestConverters
{
	public static HUQRCodeGenerateRequest toHUQRCodeGenerateRequest(
			@NonNull final JsonQRCodesGenerateRequest json,
			@NonNull final IAttributeDAO attributeDAO)
	{
		return HUQRCodeGenerateRequest.builder()
				.count(json.getCount())
				.huPackingInstructionsId(json.getHuPackingInstructionsId())
				.productId(json.getProductId())
				.attributes(json.getAttributes()
						.stream()
						.map(json1 -> toHUQRCodeGenerateRequestAttribute(json1, attributeDAO))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static HUQRCodeGenerateRequest.Attribute toHUQRCodeGenerateRequestAttribute(
			final JsonQRCodesGenerateRequest.Attribute json,
			final IAttributeDAO attributeDAO)
	{
		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(json.getAttributeCode());
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());

		final HUQRCodeGenerateRequest.Attribute.AttributeBuilder resultBuilder = HUQRCodeGenerateRequest.Attribute.builder()
				.attributeId(attributeId);

		return AttributeValueType.ofCode(attribute.getAttributeValueType())
				.map(new AttributeValueType.CaseMapper<HUQRCodeGenerateRequest.Attribute>()
				{
					@Override
					public HUQRCodeGenerateRequest.Attribute string()
					{
						return resultBuilder.valueString(json.getValue()).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute number()
					{
						final BigDecimal valueNumber = NumberUtils.asBigDecimal(json.getValue());
						return resultBuilder.valueNumber(valueNumber).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute date()
					{
						final LocalDate valueDate = StringUtils.trimBlankToOptional(json.getValue()).map(LocalDate::parse).orElse(null);
						return resultBuilder.valueDate(valueDate).build();
					}

					@Override
					public HUQRCodeGenerateRequest.Attribute list()
					{
						final String listItemCode = json.getValue();
						if (listItemCode != null)
						{
							final AttributeListValue listItem = attributeDAO.retrieveAttributeValueOrNull(attributeId, listItemCode);
							if (listItem == null)
							{
								throw new AdempiereException("No M_AttributeValue_ID found for " + attributeId + " and `" + listItemCode + "`");
							}
							return resultBuilder.valueListId(listItem.getId()).build();
						}
						else
						{
							return resultBuilder.valueListId(null).build();
						}
					}
				});
	}
}
