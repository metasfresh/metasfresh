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

import com.google.common.collect.ImmutableList;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.shipping.v2.JsonProduct;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.Product;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import java.time.ZoneId;

public class ManufacturingOrdersExportById
{
	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final ProductRepository productRepository;

	private final PPOrderId ppOrderId;

	@Builder
	public ManufacturingOrdersExportById(
			@NonNull final ProductRepository productRepository,
			@NonNull final PPOrderId ppOrderId)
	{
		this.productRepository = productRepository;
		this.ppOrderId = ppOrderId;
	}

	public JsonResponseManufacturingOrder execute()
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		return toJson(ppOrder);
	}

	private JsonResponseManufacturingOrder toJson(@NonNull final I_PP_Order order)
	{
		final PPOrderId orderId = PPOrderId.ofRepoId(order.getPP_Order_ID());
		final Quantity qtyToProduce = ppOrderBOMBL.getQuantities(order).getQtyRequiredToProduce();

		final OrgId orgId = OrgId.ofRepoId(order.getAD_Org_ID());
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		final String orgCode = orgDAO.retrieveOrgValue(orgId);

		return JsonResponseManufacturingOrder.builder()
				.orderId(JsonMetasfreshId.of(orderId.getRepoId()))
				.orgCode(orgCode)
				.documentNo(order.getDocumentNo())
				.description(StringUtils.trimBlankToNull(order.getDescription()))
				.finishGoodProduct(toJsonProduct(ProductId.ofRepoId(order.getM_Product_ID())))
				.qtyToProduce(JsonConverter.toJsonQuantity(qtyToProduce))
				.dateOrdered(TimeUtil.asZonedDateTime(order.getDateOrdered(), timeZone))
				.datePromised(TimeUtil.asZonedDateTime(order.getDatePromised(), timeZone))
				.dateStartSchedule(TimeUtil.asZonedDateTime(order.getDateStartSchedule(), timeZone))
				.productId(JsonMetasfreshId.of(order.getM_Product_ID()))
				.bpartnerId(JsonMetasfreshId.ofOrNull(order.getC_BPartner_ID()))
				.components(ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId)
									.stream()
									.map(this::toJson)
									.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private JsonResponseManufacturingOrderBOMLine toJson(@NonNull final I_PP_Order_BOMLine bomLine)
	{
		final Quantity qtyRequiredToIssue = ppOrderBOMBL.getQtyRequiredToIssue(bomLine);
		return JsonResponseManufacturingOrderBOMLine.builder()
				.componentType(bomLine.getComponentType())
				.product(toJsonProduct(ProductId.ofRepoId(bomLine.getM_Product_ID())))
				.qty(JsonConverter.toJsonQuantity(qtyRequiredToIssue))
				.build();
	}

	private JsonProduct toJsonProduct(@NonNull final ProductId productId)
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();

		final Product product = productRepository.getById(productId);

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
}
