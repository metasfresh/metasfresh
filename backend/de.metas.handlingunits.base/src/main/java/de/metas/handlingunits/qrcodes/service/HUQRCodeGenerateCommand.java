package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

class HUQRCodeGenerateCommand
{
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final Supplier<UUID> randomUUIDGenerator;

	private final HUQRCodeGenerateRequest request;

	@Builder
	private HUQRCodeGenerateCommand(
			@NonNull final HUQRCodeGenerateRequest request,
			@Nullable final Supplier<UUID> randomUUIDGenerator)
	{
		this.request = request;
		this.randomUUIDGenerator = randomUUIDGenerator != null ? randomUUIDGenerator : UUID::randomUUID;
	}

	public List<HUQRCode> execute()
	{
		final int count = request.getCount();
		if (count <= 0)
		{
			throw new AdempiereException("Invalid count: " + count);
		}

		final HUQRCode.HUQRCodeBuilder template = HUQRCode.builder()
				//.id(...) // will be set later
				.packingInfo(getHUQRCodePackingInfo(request.getHuPackingInstructionsId()))
				.product(getHUQRCodeProductInfo(request.getProductId()))
				.attributes(request.getAttributes()
						.stream()
						.map(this::toHUQRCodeAttribute)
						.collect(ImmutableList.toImmutableList()));

		final ArrayList<HUQRCode> result = new ArrayList<>();
		for (int i = 1; i <= count; i++)
		{
			final HUQRCodeUniqueId id = HUQRCodeUniqueId.ofUUID(randomUUIDGenerator.get());
			result.add(template.id(id).build());
		}

		return result;
	}

	private HUQRCodePackingInfo getHUQRCodePackingInfo(final HuPackingInstructionsId huPackingInstructionsId)
	{
		return HUQRCodePackingInfo.builder()
				.huUnitType(HUQRCodeUnitType.ofCode(handlingUnitsBL.getHU_UnitType(huPackingInstructionsId)))
				.packingInstructionsId(huPackingInstructionsId)
				.caption(handlingUnitsBL.getPIName(huPackingInstructionsId))
				.build();
	}

	private HUQRCodeProductInfo getHUQRCodeProductInfo(final ProductId productId)
	{
		final I_M_Product product = productBL.getById(productId);
		return HUQRCodeProductInfo.builder()
				.id(productId)
				.code(product.getValue())
				.name(product.getName())
				.build();
	}

	private HUQRCodeAttribute toHUQRCodeAttribute(final HUQRCodeGenerateRequest.Attribute request)
	{
		final AttributeId attributeId = request.getAttributeId();
		final I_M_Attribute attribute = attributeDAO.getAttributeById(attributeId);

		final String value;
		final String valueRendered;
		final AttributeValueType valueType = AttributeValueType.ofCode(attribute.getAttributeValueType());
		switch (valueType)
		{
			case STRING:
				value = StringUtils.trimBlankToNull(request.getValueString());
				valueRendered = null;
				break;
			case NUMBER:
				value = request.getValueNumber() != null ? request.getValueNumber().toString() : null;
				valueRendered = null;
				break;
			case DATE:
				value = request.getValueDate() != null ? request.getValueDate().toString() : null;
				valueRendered = null;
				break;
			case LIST:
				if (request.getValueListId() != null)
				{
					AttributeListValue listItem = attributeDAO.retrieveAttributeValueOrNull(attributeId, request.getValueListId());
					if (listItem == null)
					{
						throw new AdempiereException("No list item found for attribute " + attributeId + " and list value " + request.getValueListId());
					}

					value = String.valueOf(listItem.getId().getRepoId());
					valueRendered = listItem.getName();
				}
				else
				{
					value = null;
					valueRendered = null;
				}
				break;
			default:
				throw new AdempiereException("Unsupported attribute value type: " + valueType);
		}

		return HUQRCodeAttribute.builder()
				.code(AttributeCode.ofString(attribute.getValue()))
				.displayName(attribute.getName())
				.value(value)
				.valueRendered(valueRendered)
				.build();
	}
}
