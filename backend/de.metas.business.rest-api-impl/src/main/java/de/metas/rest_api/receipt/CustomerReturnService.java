/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.receipt;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.customerreturns.JsonCreateCustomerReturnInfo;
import de.metas.common.rest_api.JsonMetasfreshId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.inout.impl.CustomerReturnLineCandidate;
import de.metas.handlingunits.inout.impl.CustomerReturnsWithoutHUsProducer;
import de.metas.handlingunits.inout.impl.ReturnedGoodsWarehouseType;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.order.OrderId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgQuery;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.rest_api.shipping.AttributeSetHelper;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class CustomerReturnService
{
	private final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer;
	private final AttributeSetHelper attributeSetHelper;

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	private final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IOrgDAO orgRepo = Services.get(IOrgDAO.class);

	private static final String SYS_CONFIG_UNKNOWN_BPARTNER = "sysconfig.customerReturn.unknownBpartner";

	public CustomerReturnService(final CustomerReturnsWithoutHUsProducer customerReturnsWithoutHUsProducer, final AttributeSetHelper attributeSetHelper)
	{
		this.customerReturnsWithoutHUsProducer = customerReturnsWithoutHUsProducer;
		this.attributeSetHelper = attributeSetHelper;
	}

	@NonNull
	public List<InOutId> handleReturns(@NonNull final List<JsonCreateCustomerReturnInfo> createCustomerReturnInfoList)
	{
		try
		{
			return handleReturns_0(createCustomerReturnInfoList);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("requestBody", createCustomerReturnInfoList);
		}
	}

	private List<InOutId> handleReturns_0(@NonNull final List<JsonCreateCustomerReturnInfo> createCustomerReturnInfoList)
	{
		final ReturnProductInfoProvider returnProductInfoProvider = ReturnProductInfoProvider.newInstance();

		//1. validate request
		createCustomerReturnInfoList.forEach(customerReturnInfo -> validateRequestItem(customerReturnInfo, returnProductInfoProvider));

		//2. create CustomerReturnLineCandidate
		final ImmutableList<CustomerReturnLineCandidate> customerReturnLineCandidates = createCustomerReturnInfoList
				.stream()
				.map(createReturnInfo -> buildCustomerReturnLineCandidate(createReturnInfo, returnProductInfoProvider))
				.collect(ImmutableList.toImmutableList());

		//3. generate returns
		return customerReturnsWithoutHUsProducer.create(customerReturnLineCandidates);
	}

	private void validateRequestItem(@NonNull final JsonCreateCustomerReturnInfo customerReturnInfo,
			@NonNull final CustomerReturnService.ReturnProductInfoProvider returnProductInfoProvider)
	{
		//will throw error if there is no product
		returnProductInfoProvider.getOrgId(customerReturnInfo.getOrgCode());

		//will throw error if there is no product
		returnProductInfoProvider.getProductId(customerReturnInfo.getProductSearchKey());
	}

	private CustomerReturnLineCandidate buildCustomerReturnLineCandidate(@NonNull final JsonCreateCustomerReturnInfo customerReturnInfo,
			@NonNull final CustomerReturnService.ReturnProductInfoProvider returnProductInfoProvider)
	{
		final OrgId orgId = returnProductInfoProvider.getOrgId(customerReturnInfo.getOrgCode());

		//1. try to get the corresponding originShipmentLine
		final I_M_InOutLine originShipmentLine = getShipmentLine(customerReturnInfo.getShipmentScheduleId());

		//2. try to get the sales order
		final I_C_Order salesOrder = getSalesOrder(orgId, originShipmentLine, customerReturnInfo.getShipmentDocumentNumber());

		//3. determine bPartner and bPartner location
		final BPartnerLocationId bPartnerLocationId = getBPartnerLocation(orgId, originShipmentLine, salesOrder);

		//4. build the create attribute instance requests
		final List<CreateAttributeInstanceReq> createAttributeInstanceReqList = customerReturnInfo.getAttributes() != null
				? attributeSetHelper.toCreateAttributeInstanceReqList(customerReturnInfo.getAttributes())
				: null;

		//5. build the actual candidate
		final OrderId orderId = salesOrder != null ? OrderId.ofRepoId(salesOrder.getC_Order_ID()) : null;
		final InOutLineId originShipmentLineId = originShipmentLine != null ? InOutLineId.ofRepoId(originShipmentLine.getM_InOutLine_ID()) : null;

		final ProductId productId = returnProductInfoProvider.getProductId(customerReturnInfo.getProductSearchKey());
		final I_C_UOM stockingUOM = returnProductInfoProvider.getStockingUOM(customerReturnInfo.getProductSearchKey());

		final ReturnedGoodsWarehouseType returnedGoodsWarehouseType = Check.isNotBlank(customerReturnInfo.getReturnedGoodsWarehouseType())
				? ReturnedGoodsWarehouseType.ofCode(customerReturnInfo.getReturnedGoodsWarehouseType())
				: ReturnedGoodsWarehouseType.getDefault();

		final CustomerReturnLineCandidate candidate = CustomerReturnLineCandidate.builder()
				.orgId(orgId)
				.bPartnerLocationId(bPartnerLocationId)
				.orderId(orderId)
				.originalShipmentInOutLineId(originShipmentLineId)
				.productId(productId)
				.returnedQty(Quantity.of(customerReturnInfo.getMovementQuantity(), stockingUOM))
				.movementDate(customerReturnInfo.getMovementDate())
				.dateReceived(toZonedDateTime(orgId, customerReturnInfo.getDateReceived()))
				.createAttributeInstanceReqs(createAttributeInstanceReqList)
				.externalResourceURL(customerReturnInfo.getExternalResourceURL())
				.externalId(customerReturnInfo.getExternalId())
				.returnedGoodsWarehouseType(returnedGoodsWarehouseType)
				.build();

		return candidate;
	}

	@Nullable
	private I_C_Order getSalesOrder(@NonNull final OrgId orgId,
			@Nullable final I_M_InOutLine originShipmentLine,
			@Nullable final String shipmentDocumentNumber)
	{
		Optional<I_C_Order> order = Optional.empty();

		if (originShipmentLine != null)
		{
			order = Optional.ofNullable(inOutBL.getOrderByInOutLine(originShipmentLine));
		}

		return order.orElseGet(() -> this.getOrderByShipmentDocNumber(orgId, shipmentDocumentNumber));
	}

	@Nullable
	private I_C_Order getOrderByShipmentDocNumber(@NonNull final OrgId orgId, @Nullable final String documentNo)
	{
		if (Check.isEmpty(documentNo))
		{
			return null;
		}

		final DocTypeQuery query = DocTypeQuery.builder()
				.docBaseType(X_C_DocType.DOCBASETYPE_MaterialDelivery)
				.adClientId(Env.getAD_Client_ID())
				.adOrgId(orgId.getRepoId())
				.build();

		final DocTypeId shipmentDocTypeId = docTypeDAO.getDocTypeId(query);

		final I_M_InOut shipment = inOutDAO.getInOutByDocumentNumber(documentNo, shipmentDocTypeId, orgId);

		return shipment != null ? shipment.getC_Order() : null;
	}

	@NonNull
	private BPartnerLocationId getBPartnerLocation(@NonNull final OrgId orgId, @Nullable final I_M_InOutLine originShipmentLine, @Nullable final I_C_Order order)
	{
		final List<Supplier<BPartnerLocationId>> getBPartnerLocationSuppliers = ImmutableList.of(
				() -> getBPartnerLocationFromShipment(originShipmentLine),
				() -> getBPartnerLocationFromSalesOrder(order),
				() -> getBPartnerLocationForUnknownCustomer(orgId)
		);

		BPartnerLocationId bPartnerLocationId = null;

		for (final Supplier<BPartnerLocationId> bpLocationSupplier : getBPartnerLocationSuppliers)
		{
			bPartnerLocationId = bpLocationSupplier.get();

			if (bPartnerLocationId != null)
			{
				break;
			}
		}

		if (bPartnerLocationId == null)
		{
			throw new AdempiereException("No Business partner location was found for the target return!");
		}

		return bPartnerLocationId;
	}

	@Nullable
	private BPartnerLocationId getBPartnerLocationFromShipment(@Nullable final I_M_InOutLine originShipmentLine)
	{
		if (originShipmentLine == null)
		{
			return null;
		}

		final I_M_InOut shipment = originShipmentLine.getM_InOut();

		return BPartnerLocationId.ofRepoId(shipment.getC_BPartner_ID(), shipment.getC_BPartner_Location_ID());
	}

	@Nullable
	private BPartnerLocationId getBPartnerLocationFromSalesOrder(@Nullable final I_C_Order order)
	{
		if (order == null)
		{
			return null;
		}

		return BPartnerLocationId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID());
	}

	@NonNull
	private BPartnerLocationId getBPartnerLocationForUnknownCustomer(@NonNull final OrgId orgId)
	{
		final int unknownPartnerId = sysConfigBL.getIntValue(SYS_CONFIG_UNKNOWN_BPARTNER, -1, Env.getAD_Client_ID(), orgId.getRepoId());

		if (unknownPartnerId <= 0)
		{
			throw new AdempiereException("No Business partner was found for the target return!");
		}

		final IBPartnerDAO.BPartnerLocationQuery bPartnerLocationQuery = IBPartnerDAO.BPartnerLocationQuery.builder()
				.bpartnerId(BPartnerId.ofRepoId(unknownPartnerId))
				.type(IBPartnerDAO.BPartnerLocationQuery.Type.SHIP_TO)
				.applyTypeStrictly(false)
				.build();

		final BPartnerLocationId partnerLocationId = bPartnerDAO.retrieveBPartnerLocationId(bPartnerLocationQuery);

		if (partnerLocationId == null)
		{
			throw new AdempiereException("No Business partner location was found for the target return!");
		}

		return partnerLocationId;
	}

	@Nullable
	private ZonedDateTime toZonedDateTime(@NonNull final OrgId orgId, @Nullable final LocalDateTime dateTime)
	{
		if (dateTime == null)
		{
			return null;
		}

		final ZoneId timeZoneId = orgRepo.getTimeZone(orgId);
		return TimeUtil.asZonedDateTime(dateTime, timeZoneId);
	}

	@Nullable
	private I_M_InOutLine getShipmentLine(@Nullable final JsonMetasfreshId shipmentScheduleId)
	{
		if (shipmentScheduleId == null)
		{
			return null;
		}

		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(shipmentScheduleId.getValue());

		final List<I_M_ShipmentSchedule_QtyPicked> pickedLines = shipmentScheduleAllocDAO.retrieveOnShipmentLineRecords(scheduleId);

		if (Check.isEmpty(pickedLines))
		{
			return null;
		}

		//at this point we are sure there is a shipment line id present on all the retrieved `pickedLines`
		final InOutLineId shipmentLineId = InOutLineId.ofRepoId(pickedLines.get(0).getM_InOutLine_ID());

		return inOutDAO.getLineById(shipmentLineId);
	}

	@Value
	private static class ReturnProductInfoProvider
	{
		Map<String, I_M_Product> searchKey2Product = new HashMap<>();
		Map<String, OrgId> searchKey2Org = new HashMap<>();

		IProductDAO productRepo = Services.get(IProductDAO.class);
		IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		private static ReturnProductInfoProvider newInstance()
		{
			return new ReturnProductInfoProvider();
		}

		private OrgId getOrgId(@NonNull final String orgCode)
		{
			return searchKey2Org.computeIfAbsent(orgCode, this::retrieveOrgId);
		}

		@NonNull
		private ProductId getProductId(@NonNull final String productSearchKey)
		{
			final I_M_Product product = getProductNotNull(productSearchKey);

			return ProductId.ofRepoId(product.getM_Product_ID());
		}

		@NonNull
		private I_C_UOM getStockingUOM(@NonNull final String productSearchKey)
		{
			final I_M_Product product = getProductNotNull(productSearchKey);
			return uomDAO.getById(UomId.ofRepoId(product.getC_UOM_ID()));
		}

		@NonNull
		private I_M_Product getProductNotNull(@NonNull final String productSearchKey)
		{
			final I_M_Product product = searchKey2Product.computeIfAbsent(productSearchKey, this::loadProduct);

			if (product == null)
			{
				throw new AdempiereException("No product could be found for the target search key!")
						.appendParametersToMessage()
						.setParameter("ProductSearchKey", productSearchKey);
			}

			return product;
		}

		private I_M_Product loadProduct(@NonNull final String productSearchKey)
		{
			return productRepo.retrieveProductByValue(productSearchKey);
		}

		private OrgId retrieveOrgId(@NonNull final String orgCode)
		{
			final OrgQuery query = OrgQuery.builder()
					.orgValue(orgCode)
					.failIfNotExists(true)
					.build();

			return orgDAO.retrieveOrgIdBy(query).orElseThrow(() -> new AdempiereException("No AD_Org was found for the given search key!")
					.appendParametersToMessage()
					.setParameter("OrgSearchKey", orgCode));
		}
	}

}
