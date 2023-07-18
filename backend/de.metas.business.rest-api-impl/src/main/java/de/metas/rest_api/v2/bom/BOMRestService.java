/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.rest_api.v2.bom;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateRequest;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateResponse;
import de.metas.common.rest_api.v2.bom.JsonCreateBOMLine;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.resource.ResourceService;
import de.metas.rest_api.v2.attributes.JsonAttributeService;
import de.metas.rest_api.v2.product.ExternalIdentifierResolver;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import de.metas.util.web.exception.InvalidIdentifierException;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMCreateRequest;
import org.eevolution.api.impl.ProductBOMService;
import org.eevolution.model.I_PP_Product_BOM;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static de.metas.RestUtils.retrieveOrgIdOrDefault;

@Service
public class BOMRestService
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	private final ProductBOMService bomService;
	private final ExternalIdentifierResolver externalIdentifierResolver;
	private final JsonAttributeService jsonAttributeService;
	private final ResourceService resourceService;

	public BOMRestService(
			@NonNull final ExternalIdentifierResolver externalIdentifierResolver,
			@NonNull final ProductBOMService bomService,
			@NonNull final JsonAttributeService jsonAttributeService,
			@NonNull final ResourceService resourceService)
	{
		this.externalIdentifierResolver = externalIdentifierResolver;
		this.bomService = bomService;
		this.jsonAttributeService = jsonAttributeService;
		this.resourceService = resourceService;
	}

	@NonNull
	public JsonBOMCreateResponse createBOMs(
			@Nullable final String orgCode,
			@NonNull final JsonBOMCreateRequest request)
	{
		final I_AD_Org org = orgDAO.getById(retrieveOrgIdOrDefault(orgCode));
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final ExternalIdentifier productExternalIdentifier = ExternalIdentifier.of(request.getProductIdentifier());

		final ProductId finishedProductId = externalIdentifierResolver.resolveProductExternalIdentifier(productExternalIdentifier, orgId)
				.orElseThrow(() -> new InvalidIdentifierException(request.getProductIdentifier()));

		final I_M_Product finishedProduct = productDAO.getById(finishedProductId);

		final UomId bomUOMId = uomDAO.getUomIdByX12DE355(X12DE355.ofCode(request.getUomCode()));

		final ImmutableList<BOMCreateRequest.BOMLine> bomLines = request.getBomLines()
				.stream()
				.map(line -> buildBOMLine(line, orgId))
				.collect(ImmutableList.toImmutableList());

		final AttributeSetInstanceId attributeSetInstanceId = jsonAttributeService.computeAttributeSetInstanceFromJson(request.getAttributeSetInstance())
				.orElse(null);

		final ResourceId resourceId = request.getResourceCode() == null
				? null
				: resourceService.getResourceIdByValue(request.getResourceCode(), orgId)
				.orElseThrow(() -> new AdempiereException("No S_Resource found for org & value!")
						.appendParametersToMessage()
						.setParameter("OrgId", orgId.getRepoId())
						.setParameter("ResourceCode", request.getResourceCode()));

		final BOMCreateRequest bomRequest = BOMCreateRequest.builder()
				.orgId(orgId)
				.productId(finishedProductId)
				.productValue(finishedProduct.getValue())
				.productName(request.getName())
				.uomId(bomUOMId)
				.isActive(request.getIsActive())
				.validFrom(request.getValidFrom())
				.attributeSetInstanceId(attributeSetInstanceId)
				.lines(bomLines)
				.resourceId(resourceId)
				.build();

		final I_PP_Product_BOM createdBOM = bomService.createBOM(bomRequest);

		return JsonBOMCreateResponse.of(JsonMetasfreshId.of(createdBOM.getPP_Product_BOM_ID()));
	}

	@NonNull
	public BOMCreateRequest.BOMLine buildBOMLine(
			@NonNull final JsonCreateBOMLine lineRequest,
			@NonNull final OrgId orgId)
	{
		final ExternalIdentifier productExternalIdentifier = ExternalIdentifier.of(lineRequest.getProductIdentifier());

		final ProductId productId = externalIdentifierResolver.resolveProductExternalIdentifier(productExternalIdentifier, orgId)
				.orElseThrow(() -> new InvalidIdentifierException(lineRequest.getProductIdentifier()));

		final X12DE355 uomCode = X12DE355.ofCode(lineRequest.getQtyBom().getUomCode());
		final I_C_UOM uom = uomDAO.getByX12DE355(uomCode);

		final AttributeSetInstanceId attributeSetInstanceId = jsonAttributeService.computeAttributeSetInstanceFromJson(lineRequest.getAttributeSetInstance())
				.orElse(null);

		return BOMCreateRequest.BOMLine.builder()
				.productId(productId)
				.line(lineRequest.getLine())
				.scrap(lineRequest.getScrap())
				.isQtyPercentage(lineRequest.getIsQtyPercentage())
				.qty(Quantity.of(lineRequest.getQtyBom().getQty(), uom))
				.attributeSetInstanceId(attributeSetInstanceId)
				.help(lineRequest.getHelp())
				.build();
	}

	public void verifyDefaultBOM(@NonNull final String productExternalIdentifier, @NonNull final String orgCode)
	{
		final ExternalIdentifier externalIdentifier = ExternalIdentifier.of(productExternalIdentifier);
		final OrgId orgId = retrieveOrgIdOrDefault(orgCode);

		final ProductId productId = productRestService.resolveProductExternalIdentifier(externalIdentifier, orgId)
				.orElseThrow(() -> new InvalidIdentifierException(productExternalIdentifier));

		bomService.verifyDefaultBOMFor(productId);
	}
}
