/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.manufacturing.rest_api.v2;

import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.JsonQuantity;
import de.metas.common.shipping.v2.JsonProduct;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.time.ZoneId;

@UtilityClass
public class JsonConverter
{
	@NonNull
	public JsonQuantity toJsonQuantity(@NonNull final Quantity qty)
	{
		return JsonQuantity.builder()
				.qty(qty.toBigDecimal())
				.uomCode(qty.getX12DE355().getCode())
				.build();
	}

	@NonNull
	public JsonProduct toJsonProduct(@NonNull final Product product, @NonNull final String adLanguage)
	{
		return JsonProduct.builder()
				.productNo(product.getProductNo())
				.name(product.getName().translate(adLanguage))
				.description(product.getDescription().translate(adLanguage))
				.documentNote(product.getDocumentNote().translate(adLanguage))
				.packageSize(product.getPackageSize())
				.weight(product.getWeight())
				.stocked(product.isStocked())
				.build();
	}

	@NonNull
	public JsonResponseManufacturingOrderBOMLine toJson(
			@NonNull final I_PP_Order_BOMLine bomLine,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final ProductRepository productRepository)
	{
		final ProductId productId = ProductId.ofRepoId(bomLine.getM_Product_ID());
		final Product product = productRepository.getById(productId);

		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final Quantity qtyRequiredToIssue = ppOrderBOMBL.getQtyRequiredToIssue(bomLine);
		return JsonResponseManufacturingOrderBOMLine.builder()
				.componentType(bomLine.getComponentType())
				.product(toJsonProduct(product, adLanguage))
				.qty(JsonConverter.toJsonQuantity(qtyRequiredToIssue))
				.build();
	}

	@NonNull
	public JsonResponseManufacturingOrder toJson(@NonNull final MapToJsonResponseManufacturingOrderRequest request)
	{
		final I_PP_Order ppOrder = request.getOrder();
		final PPOrderId orderId = PPOrderId.ofRepoId(ppOrder.getPP_Order_ID());
		final Quantity qtyToProduce = request.getPpOrderBOMBL().getQuantities(ppOrder).getQtyRequiredToProduce();

		final OrgId orgId = OrgId.ofRepoId(ppOrder.getAD_Org_ID());
		final IOrgDAO orgDAO = request.getOrgDAO();

		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		final String orgCode = orgDAO.retrieveOrgValue(orgId);

		final ProductId productId = ProductId.ofRepoId(ppOrder.getM_Product_ID());
		final Product product = request.getProductRepository().getById(productId);

		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		return JsonResponseManufacturingOrder.builder()
				.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
				.orgCode(orgCode)
				.documentNo(ppOrder.getDocumentNo())
				.description(StringUtils.trimBlankToNull(ppOrder.getDescription()))
				.finishGoodProduct(toJsonProduct(product, adLanguage))
				.qtyToProduce(JsonConverter.toJsonQuantity(qtyToProduce))
				.dateOrdered(TimeUtil.asZonedDateTime(ppOrder.getDateOrdered(), timeZone))
				.datePromised(TimeUtil.asZonedDateTime(ppOrder.getDatePromised(), timeZone))
				.dateStartSchedule(TimeUtil.asZonedDateTime(ppOrder.getDateStartSchedule(), timeZone))
				.productId(JsonMetasfreshId.of(ppOrder.getM_Product_ID()))
				.bpartnerId(JsonMetasfreshId.ofOrNull(ppOrder.getC_BPartner_ID()))
				.components(request.getComponents())
				.build();
	}

}
