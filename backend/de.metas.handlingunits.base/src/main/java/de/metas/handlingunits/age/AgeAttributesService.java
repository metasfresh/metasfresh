package de.metas.handlingunits.age;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.cache.CCache;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

	@SuppressWarnings("OptionalIsPresent")
	public int computeDefaultAge()
	{
		final List<AttributeListValue> allAgeValues = getAllAgeValues();

		final Optional<AttributeListValue> nullFieldValueOpt = allAgeValues.stream()
				.filter(AttributeListValue::isNullFieldValue)
				.findFirst();

		final int defaultAge;
		if (nullFieldValueOpt.isPresent())
		{
			defaultAge = Integer.parseInt(nullFieldValueOpt.get().getValue());
		}
		else
		{
			defaultAge = Integer.parseInt(allAgeValues.get(0).getValue());
		}
		return defaultAge;
	}

	private List<AttributeListValue> getAllAgeValues()
	{
		final AttributeId ageId = attributesRepo.retrieveAttributeIdByValueOrNull(HUAttributeConstants.ATTR_Age);
		final I_M_Attribute age = attributesRepo.getAttributeById(ageId);

		return attributesRepo.retrieveAttributeValues(age);
	}

	public List<Object> getSuitableValues(final Set<BPartnerId> bPartnerIds, final Set<ProductId> productIds, final Object attributeValue)
	{
		int pickingAgeTolerance_BeforeMonths = 0;
		int pickingAgeTolerance_AfterMonths = 0;

		for (final ProductId productId : productIds)
		{
			final I_M_Product product = productDAO.getById(productId);

			final int productBefore = product.getPicking_AgeTolerance_BeforeMonths();

			pickingAgeTolerance_BeforeMonths = productBefore;

			final int productAfter = product.getPicking_AgeTolerance_AfterMonths();

			if (productAfter < pickingAgeTolerance_AfterMonths)
			{
				pickingAgeTolerance_AfterMonths = productAfter;
			}

			final OrgId orgId = OrgId.ofRepoId(product.getAD_Org_ID());

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
					final int bpartnerProductBefore = bPartnerProduct.getPicking_AgeTolerance_BeforeMonths();

					pickingAgeTolerance_BeforeMonths = bpartnerProductBefore;

					final int bpartnerProductAfter = bPartnerProduct.getPicking_AgeTolerance_AfterMonths();

					pickingAgeTolerance_AfterMonths = bpartnerProductAfter;

				}
			}
		}

		final int minimumValue = Integer.parseInt(attributeValue.toString()) - pickingAgeTolerance_BeforeMonths;
		final int maximumValue = Integer.parseInt(attributeValue.toString()) + pickingAgeTolerance_AfterMonths;

		final List<Object> suitableValues = new ArrayList<>();
		final List<AttributeListValue> allAgeValues = getAllAgeValues();

		for (
				final AttributeListValue ageValue : allAgeValues)

		{
			final int ageValueInt = ageValue.getValueAsInt();

			if (ageValueInt >= minimumValue && ageValueInt <= maximumValue)
			{
				suitableValues.add(ageValue.getValue());
			}
		}

		return suitableValues;
	}

}
