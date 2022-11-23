/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.common.externalsystem.JsonExternalMapping;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.externalreference.ExternalReference;
import de.metas.externalreference.ExternalReferenceMappingQuery;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.IExternalReferenceType;
import de.metas.externalreference.productcategory.ProductCategoryExternalReferenceType;
import de.metas.externalreference.uom.UOMExternalReferenceType;
import de.metas.externalsystem.ExternalSystemConfigService;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.producttype.ProductTypeExternalMapping;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_CATEGORY_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_TYPE_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_UOM_MAPPINGS;

@Service
public class InvokeSAPService
{
	private final ExternalReferenceRepository externalReferenceRepository;
	private final ExternalSystemConfigService externalSystemConfigService;

	public InvokeSAPService(
			@NonNull final ExternalReferenceRepository externalReferenceRepository,
			@NonNull final ExternalSystemConfigService externalSystemConfigService)
	{
		this.externalReferenceRepository = externalReferenceRepository;
		this.externalSystemConfigService = externalSystemConfigService;
	}

	@NonNull
	public Map<String, String> getMappingParameters(@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		final ExternalReferenceMappingQuery externalReferenceQuery = ExternalReferenceMappingQuery.builder()
				.externalSystemParentConfigId(parentConfigId.getRepoId())
				.externalReferenceTypeSet(ImmutableSet.of(UOMExternalReferenceType.UOM, ProductCategoryExternalReferenceType.PRODUCT_CATEGORY))
				.build();

		final Map<IExternalReferenceType, List<ExternalReference>> type2ExternalRefList = externalReferenceRepository.getExternalMappingsByConfigIdAndType(externalReferenceQuery);

		final ImmutableList<JsonExternalMapping> productCategoryMappings = type2ExternalRefList.get(ProductCategoryExternalReferenceType.PRODUCT_CATEGORY)
				.stream()
				.map(InvokeSAPService::mapFromProductCategoryExtRef)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<JsonExternalMapping> uomMappings = type2ExternalRefList.get(UOMExternalReferenceType.UOM)
				.stream()
				.map(InvokeSAPService::mapFromUOMExtRef)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<ProductTypeExternalMapping> productTypeExternalMapping = externalSystemConfigService.retrieveProductTypeExternalMappings(parentConfigId);
		final ImmutableList<JsonExternalMapping> productTypeMappingsAsString = productTypeExternalMapping.stream()
				.map(InvokeSAPService::mapFromProductTypeExternalMapping)
				.collect(ImmutableList.toImmutableList());

		final Map<String, String> paramName2JsonExtMappingsAsString = new HashMap<>();

		paramName2JsonExtMappingsAsString.put(PARAM_PRODUCT_CATEGORY_MAPPINGS, getJsonExternalMappingAsString(productCategoryMappings));
		paramName2JsonExtMappingsAsString.put(PARAM_UOM_MAPPINGS, getJsonExternalMappingAsString(uomMappings));
		paramName2JsonExtMappingsAsString.put(PARAM_PRODUCT_TYPE_MAPPINGS, getJsonExternalMappingAsString(productTypeMappingsAsString));

		return paramName2JsonExtMappingsAsString;
	}

	@NonNull
	private static JsonExternalMapping mapFromProductCategoryExtRef(@NonNull final ExternalReference externalReference)
	{
		final JsonMetasfreshId metasfreshId = JsonMetasfreshId.of(externalReference.getRecordId());

		return JsonExternalMapping.of(externalReference.getExternalReference(), metasfreshId);
	}

	@NonNull
	private static JsonExternalMapping mapFromUOMExtRef(@NonNull final ExternalReference externalReference)
	{
		final I_C_UOM uomRecord = InterfaceWrapperHelper.load(externalReference.getRecordId(), I_C_UOM.class);

		return JsonExternalMapping.of(externalReference.getExternalReference(), uomRecord.getX12DE355());
	}

	@NonNull
	private static JsonExternalMapping mapFromProductTypeExternalMapping(@NonNull final ProductTypeExternalMapping productTypeExternalMapping)
	{
		return JsonExternalMapping.of(productTypeExternalMapping.getExternalValue(), productTypeExternalMapping.getValue());
	}

	@NonNull
	public static String getJsonExternalMappingAsString(@NonNull final List<JsonExternalMapping> jsonExternalMappings)
	{
		try
		{
			return new ObjectMapper().writeValueAsString(jsonExternalMappings);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
