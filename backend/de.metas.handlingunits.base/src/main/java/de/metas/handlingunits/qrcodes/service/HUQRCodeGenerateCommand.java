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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

class HUQRCodeGenerateCommand
{
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IProductBL productBL;
	private final IAttributeDAO attributeDAO;
	private final Supplier<UUID> randomUUIDGenerator;

	private final HUQRCodeGenerateRequest request;

	private final Cache cache;

	@Builder
	private HUQRCodeGenerateCommand(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IProductBL productBL,
			@NonNull final IAttributeDAO attributeDAO,
			@Nullable final Supplier<UUID> randomUUIDGenerator,
			@NonNull final HUQRCodeGenerateRequest request,
			@Nullable final Cache sharedCache)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.productBL = productBL;
		this.attributeDAO = attributeDAO;
		this.randomUUIDGenerator = randomUUIDGenerator != null ? randomUUIDGenerator : UUID::randomUUID;
		this.request = request;
		this.cache = sharedCache != null ? sharedCache : newSharedCache();
	}

	public static Cache newSharedCache() {return new Cache();}

	public List<HUQRCode> execute()
	{
		final int count = request.getCount();
		if (count <= 0)
		{
			throw new AdempiereException("Invalid count: " + count);
		}

		final HUQRCode.HUQRCodeBuilder template = HUQRCode.builder()
				//.id(...) // will be set later
				.packingInfo(getPackingInfo(request.getHuPackingInstructionsId()))
				.product(getProductInfo(request.getProductId()))
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

	private HUQRCodePackingInfo getPackingInfo(final HuPackingInstructionsId huPackingInstructionsId)
	{
		return cache.packingInfos.computeIfAbsent(huPackingInstructionsId, this::computePackingInfo);
	}

	private HUQRCodePackingInfo computePackingInfo(final HuPackingInstructionsId huPackingInstructionsId)
	{
		return HUQRCodePackingInfo.builder()
				.packingInstructionsId(huPackingInstructionsId)
				.huUnitType(HUQRCodeUnitType.ofCode(handlingUnitsBL.getHU_UnitType(huPackingInstructionsId)))
				.caption(handlingUnitsBL.getPIName(huPackingInstructionsId))
				.build();
	}

	private HUQRCodeProductInfo getProductInfo(final ProductId productId)
	{
		return cache.productInfos.computeIfAbsent(productId, this::computeProductInfo);
	}

	private HUQRCodeProductInfo computeProductInfo(final ProductId productId)
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

	//
	//
	//

	public static class Cache
	{
		private final HashMap<ProductId, HUQRCodeProductInfo> productInfos = new HashMap<>();
		private final HashMap<HuPackingInstructionsId, HUQRCodePackingInfo> packingInfos = new HashMap<>();
	}
}
