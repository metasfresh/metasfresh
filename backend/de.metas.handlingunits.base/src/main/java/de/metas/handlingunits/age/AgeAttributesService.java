package de.metas.handlingunits.age;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.cache.CCache;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class AgeAttributesService
{
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);

	private final CCache<Integer, AgeValues> cache = CCache.<Integer, AgeValues>builder()
			.tableName(IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE)
			.build();

	public AgeValues getAgeValues()
	{
		return cache.getOrLoad(0, this::retrieveAgeValues);
	}

	@SuppressWarnings("UnstableApiUsage")
	@NonNull
	private AgeValues retrieveAgeValues()
	{
		final List<AttributeListValue> allAgeValues = getAllAgeValues();
		final ImmutableSet<Integer> agesInMonths = allAgeValues
				.stream()
				.map(AttributeListValue::getValueAsInt)
				.sorted()
				.collect(ImmutableSet.toImmutableSet());

		return AgeValues.ofAgeInMonths(agesInMonths);
	}

	@Nullable
	public Integer computeDefaultAgeOrNull()
	{
		final List<AttributeListValue> allAgeValues = getAllAgeValues();

		return allAgeValues.stream()
				.filter(AttributeListValue::isNullFieldValue)
				.findFirst()
				.map(AttributeListValue::getValue)
				.filter(Check::isNotBlank)
				.map(Integer::parseInt)
				.orElse(null);
	}

	private List<AttributeListValue> getAllAgeValues()
	{
		final AttributeId ageId = attributesRepo.retrieveAttributeIdByValueOrNull(HUAttributeConstants.ATTR_Age);
		final I_M_Attribute age = attributesRepo.getAttributeById(ageId);

		return attributesRepo.retrieveAttributeValues(age);
	}

	public List<Object> extractMatchingValues(
			@NonNull final Set<BPartnerId> bPartnerIds, 
			@NonNull final Set<ProductId> productIds, 
			@Nullable final Object attributeValue)
	{
		if (attributeValue == null)
		{
			return Collections.emptyList();
		}

		final AgeRange ageRange = computeAgeRangeForProducts(productIds, bPartnerIds);

		final List<Object> matchingValues = new ArrayList<>();

		try
		{
			final int minimumValue = Integer.parseInt(attributeValue.toString()) - ageRange.getPickingAgeTolerance_BeforeMonths();
			final int maximumValue = Integer.parseInt(attributeValue.toString()) + ageRange.getPickingAgeTolerance_AfterMonths();

			final List<AttributeListValue> allAgeValues = getAllAgeValues();

			for (final AttributeListValue ageValue : allAgeValues)
			{
				final int ageValueInt = ageValue.getValueAsInt();

				if (ageValueInt >= minimumValue && ageValueInt <= maximumValue)
				{
					matchingValues.add(ageValue.getValue());
				}

			}
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Age attribute Value " + attributeValue + " could not be parsed to Int", e);
		}

		return matchingValues;
	}

	@VisibleForTesting
	protected AgeRange computeAgeRangeForProducts(
			@NonNull final Set<ProductId> productIds,
			@NonNull final Set<BPartnerId> bPartnerIds)
	{
		if (Check.isEmpty(productIds))
		{
			return AgeRange.builder()
					.pickingAgeTolerance_BeforeMonths(0)
					.pickingAgeTolerance_AfterMonths(0)
					.build();
		}

		int pickingAgeTolerance_BeforeMonths = Integer.MAX_VALUE;
		int pickingAgeTolerance_AfterMonths = Integer.MAX_VALUE;

		for (final ProductId productId : productIds)
		{
			final I_M_Product product = productDAO.getById(productId);

			final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

			final AgeRange ageRange = computeAgeRangeForProduct(product, bPartnerIds, orgId);

			if (ageRange.getPickingAgeTolerance_BeforeMonths() < pickingAgeTolerance_BeforeMonths)
			{
				pickingAgeTolerance_BeforeMonths = ageRange.getPickingAgeTolerance_BeforeMonths();
			}

			if (ageRange.getPickingAgeTolerance_AfterMonths() < pickingAgeTolerance_AfterMonths)
			{
				pickingAgeTolerance_AfterMonths = ageRange.getPickingAgeTolerance_AfterMonths();
			}
		}

		return AgeRange.builder()
				.pickingAgeTolerance_BeforeMonths(pickingAgeTolerance_BeforeMonths)
				.pickingAgeTolerance_AfterMonths(pickingAgeTolerance_AfterMonths)
				.build();
	}

	private AgeRange computeAgeRangeForProduct(
			@NonNull final I_M_Product product, 
			@NonNull final Set<BPartnerId> bPartnerIds,
			@NonNull final OrgId orgId)
	{
		int pickingAgeTolerance_BP_Product_BeforeMonths = Integer.MAX_VALUE;
		int pickingAgeTolerance_BP_Product_AfterMonths = Integer.MAX_VALUE;

		boolean bPartnerProductWasFound = false;

		for (final BPartnerId bpartnerId : bPartnerIds)
		{
			if (bpartnerId == null)
			{
				continue;
			}

			final I_C_BPartner partner = partnerDAO.getById(bpartnerId);
			final I_C_BPartner_Product bPartnerProduct = bpartnerProductDAO.retrieveBPProductForCustomer(partner, product, orgId);

			if (bPartnerProduct != null)
			{
				bPartnerProductWasFound = true;
				if (bPartnerProduct.getPicking_AgeTolerance_BeforeMonths() < pickingAgeTolerance_BP_Product_BeforeMonths)
				{
					pickingAgeTolerance_BP_Product_BeforeMonths = bPartnerProduct.getPicking_AgeTolerance_BeforeMonths();
				}

				if (bPartnerProduct.getPicking_AgeTolerance_AfterMonths() < pickingAgeTolerance_BP_Product_AfterMonths)
				{
					pickingAgeTolerance_BP_Product_AfterMonths = bPartnerProduct.getPicking_AgeTolerance_AfterMonths();
				}
			}
		}

		if (bPartnerProductWasFound)
		{
			return AgeRange.builder()
					.pickingAgeTolerance_BeforeMonths(pickingAgeTolerance_BP_Product_BeforeMonths)
					.pickingAgeTolerance_AfterMonths(pickingAgeTolerance_BP_Product_AfterMonths)
					.build();
		}

		return AgeRange.builder()
				.pickingAgeTolerance_BeforeMonths(product.getPicking_AgeTolerance_BeforeMonths())
				.pickingAgeTolerance_AfterMonths(product.getPicking_AgeTolerance_AfterMonths())
				.build();

	}


}
