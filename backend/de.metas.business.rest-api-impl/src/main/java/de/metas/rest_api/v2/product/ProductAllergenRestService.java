/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.rest_api.v2.product;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.product.v2.request.JsonRequestAllergenItem;
import de.metas.common.product.v2.request.JsonRequestUpsertProductAllergen;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.SyncAdvise;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.allergen.AllergenExternalReferenceType;
import de.metas.externalreference.rest.v2.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.allergen.AllergenId;
import de.metas.product.allergen.AllergenService;
import de.metas.product.allergen.ProductAllergens;
import de.metas.product.allergen.ProductAllergensService;
import de.metas.product.allergen.SaveAllergenRequest;
import de.metas.product.allergen.SaveProductAllergenRequest;
import de.metas.rest_api.v2.product.helper.ExternalReferenceRequestHelper;
import de.metas.util.Check;
import de.metas.util.web.exception.InvalidIdentifierException;
import de.metas.util.web.exception.MissingResourceException;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Allergen;
import org.compiere.model.I_M_Product_Allergen;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductAllergenRestService
{
	@NonNull
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;
	@NonNull
	private final AllergenService allergenService;
	@NonNull
	private final ProductAllergensService productAllergensService;

	public ProductAllergenRestService(
			final @NonNull ExternalReferenceRestControllerService externalReferenceRestControllerService,
			final @NonNull AllergenService allergenService,
			final @NonNull ProductAllergensService productAllergensService)
	{
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
		this.allergenService = allergenService;
		this.productAllergensService = productAllergensService;
	}

	public void upsertProductAllergens(
			@NonNull final I_AD_Org org,
			@NonNull final ProductId productId,
			@NonNull final JsonRequestUpsertProductAllergen allergenRequest)
	{
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final ProductAllergens currentProductAllergens = productAllergensService.getProductAllergens(productId);
		final Map<ExternalIdentifier, AllergenId> identifier2Id = upsertAllergens(org, allergenRequest);
		final ImmutableSet<AllergenId> incomingAllergenIds = ImmutableSet.copyOf(identifier2Id.values());

		final ImmutableSet<AllergenId> missingAllergenIds = getMissingAllergensForProduct(incomingAllergenIds, currentProductAllergens);

		if (allergenRequest.getSyncAdvise().isFailIfNotExists() && !missingAllergenIds.isEmpty())
		{
			throw MissingResourceException.builder()
					.resourceName(I_M_Product_Allergen.Table_Name)
					.resourceIdentifier("Allergen_IDs=" + StringUtils.join(missingAllergenIds, ","))
					.parentResource(productId)
					.build()
					.setParameter("syncAdvise", allergenRequest.getSyncAdvise());
		}
		else if (allergenRequest.getSyncAdvise().getIfExists().isReplace())
		{
			productAllergensService.save(SaveProductAllergenRequest.of(orgId, currentProductAllergens.withAllergenIds(incomingAllergenIds)));
		}
		else
		{
			productAllergensService.save(SaveProductAllergenRequest.of(orgId, currentProductAllergens.addAllergens(incomingAllergenIds)));
		}
	}

	@NonNull
	private ImmutableMap<ExternalIdentifier, AllergenId> upsertAllergens(
			@NonNull final I_AD_Org org,
			@NonNull final JsonRequestUpsertProductAllergen allergenRequest)
	{
		if (Check.isEmpty(allergenRequest.getAllergenList()))
		{
			return ImmutableMap.of();
		}

		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final ImmutableMap<ExternalIdentifier, AllergenId> identifier2Id = resolveAllergenIdentifiers(orgId, allergenRequest);

		final ImmutableMap.Builder<ExternalIdentifier, AllergenId> resultBuilder = ImmutableMap.builder();
		resultBuilder.putAll(identifier2Id);

		allergenRequest.getAllergenList()
				.stream()
				.filter(allergenItem -> isUpsertAllowed(allergenRequest.getSyncAdvise(), allergenItem, identifier2Id))
				.forEach(allergenItem -> {
					final AllergenId allergenId = saveAllergen(orgId, allergenItem, identifier2Id);

					final ExternalIdentifier identifier = ExternalIdentifier.of(allergenItem.getIdentifier());
					if (!identifier2Id.containsKey(identifier))
					{
						resultBuilder.put(identifier, allergenId);
					}
				});

		final ImmutableMap<ExternalIdentifier, AllergenId> result = resultBuilder.build();

		handleAllergenExternalReference(org.getValue(), result);

		return result;
	}

	private void handleAllergenExternalReference(
			@NonNull final String orgCode,
			@NonNull final Map<ExternalIdentifier, AllergenId> identifier2AllergenId)
	{
		identifier2AllergenId.entrySet()
				.stream()
				.map(identifierAndAllergenId -> ExternalReferenceRequestHelper
						.getRequestExternalRefUpsert(identifierAndAllergenId.getValue(), identifierAndAllergenId.getKey()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.forEach(upsertAllergenExternalRefReq -> externalReferenceRestControllerService.performUpsert(upsertAllergenExternalRefReq, orgCode));
	}

	@NonNull
	private AllergenId saveAllergen(
			@NonNull final OrgId orgId,
			@NonNull final JsonRequestAllergenItem allergenItem,
			@NonNull final ImmutableMap<ExternalIdentifier, AllergenId> externalIdentifier2Id)
	{
		final SaveAllergenRequest saveAllergenRequest = SaveAllergenRequest.builder()
				.id(externalIdentifier2Id.get(ExternalIdentifier.of(allergenItem.getIdentifier())))
				.name(allergenItem.getName())
				.orgId(orgId)
				.build();

		return allergenService.saveAllergen(saveAllergenRequest).getId();
	}

	@NonNull
	private ImmutableMap<ExternalIdentifier, AllergenId> resolveAllergenIdentifiers(
			@NonNull final OrgId orgId,
			@NonNull final JsonRequestUpsertProductAllergen allergenRequest)
	{
		final ImmutableMap.Builder<ExternalIdentifier, AllergenId> existingAllergensCollector = ImmutableMap.builder();

		allergenRequest.getAllergenList().stream()
				.map(JsonRequestAllergenItem::getIdentifier)
				.map(ExternalIdentifier::of)
				.forEach(allergenIdentifier -> {
					final AllergenId allergenId = resolveAllergenId(allergenIdentifier, orgId).orElse(null);

					if (allergenId != null)
					{
						existingAllergensCollector.put(allergenIdentifier, allergenId);
					}
					else if (allergenRequest.getSyncAdvise().isFailIfNotExists())
					{
						throw MissingResourceException.builder()
								.resourceName(I_M_Allergen.Table_Name)
								.resourceIdentifier(allergenIdentifier.getRawValue())
								.build()
								.setParameter("syncAdvise", allergenRequest.getSyncAdvise());
					}
				});

		return existingAllergensCollector.build();
	}

	@NonNull
	private Optional<AllergenId> resolveAllergenId(
			@NonNull final ExternalIdentifier allergenExternalIdentifier,
			@NonNull final OrgId orgId)
	{
		switch (allergenExternalIdentifier.getType())
		{
			case METASFRESH_ID:
				return Optional.of(AllergenId.ofRepoId(allergenExternalIdentifier.asMetasfreshId().getValue()));

			case EXTERNAL_REFERENCE:
				return externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(orgId, allergenExternalIdentifier, AllergenExternalReferenceType.ALLERGEN)
						.map(JsonMetasfreshId::getValue)
						.map(AllergenId::ofRepoId);
			default:
				throw new InvalidIdentifierException(allergenExternalIdentifier.getRawValue());
		}
	}

	@NonNull
	private static ImmutableSet<AllergenId> getMissingAllergensForProduct(
			@NonNull final Set<AllergenId> newAllergenIds,
			@NonNull final ProductAllergens existingAllergens)
	{
		return newAllergenIds.stream()
				.filter(allergenId -> !existingAllergens.hasAllergen(allergenId))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static boolean isUpsertAllowed(
			@NonNull final SyncAdvise syncAdvise,
			@NonNull final JsonRequestAllergenItem allergenItem,
			@NonNull final Map<ExternalIdentifier, AllergenId> identifier2Id)
	{
		final boolean isUpdateAllowed = syncAdvise.getIfExists().isUpdate();
		final boolean isNew = !identifier2Id.containsKey(ExternalIdentifier.of(allergenItem.getIdentifier()));
		return isNew || isUpdateAllowed;
	}
}
