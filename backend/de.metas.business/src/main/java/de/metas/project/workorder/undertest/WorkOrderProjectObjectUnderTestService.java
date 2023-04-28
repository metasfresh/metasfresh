/*
 * #%L
 * de.metas.business
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

package de.metas.project.workorder.undertest;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderFactory;
import de.metas.order.OrderLineBuilder;
import de.metas.order.OrderLineId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.IPricingBL;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class WorkOrderProjectObjectUnderTestService
{
	private final WorkOrderProjectObjectUnderTestRepository woProjectObjectUnderTestRepository;

	final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	final IOrderBL orderBL = Services.get(IOrderBL.class);
	final IPricingBL pricingBL = Services.get(IPricingBL.class);
	final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	final IProductDAO productDAO = Services.get(IProductDAO.class);

	public WorkOrderProjectObjectUnderTestService(@NonNull final WorkOrderProjectObjectUnderTestRepository woProjectObjectUnderTestRepository)
	{
		this.woProjectObjectUnderTestRepository = woProjectObjectUnderTestRepository;
	}

	@NonNull
	public List<WOProjectObjectUnderTest> getByProjectId(@NonNull final ProjectId projectId)
	{
		return woProjectObjectUnderTestRepository.getByProjectId(projectId);
	}

	@NonNull
	public List<WOProjectObjectUnderTest> updateAll(@NonNull final Collection<WOProjectObjectUnderTest> objectUnderTestList)
	{
		return woProjectObjectUnderTestRepository.updateAll(objectUnderTestList);
	}

	@NonNull
	public void createOrder(
			@NonNull final BPartnerId bPartnerId,
			@NonNull final ZonedDateTime datePromised,
			@NonNull final List<WOProjectObjectUnderTest> woProjectObjectUnderTestList)
	{
		final OrderFactory orderFactory = OrderFactory.newPurchaseOrder();

		final DocBaseAndSubType docBaseAndSubType = DocBaseAndSubType.of(DocBaseType.PurchaseOrder, X_C_DocType.DOCSUBTYPE_Provision);

		final DocTypeQuery docTypeQuery = DocTypeQuery.builder()
				.docBaseType(docBaseAndSubType.getDocBaseType())
				// .docSubType(docBaseAndSubType.getDocSubType())
				.adClientId(Env.getAD_Client_ID())
				.build();
		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(docTypeQuery);
		orderFactory.docType(docTypeId);

		final BPartnerQuery bPartnerQuery = BPartnerQuery.builder()
				.bPartnerId(bPartnerId)
				.onlyOrgId(OrgId.ANY)
				.onlyOrgId(Env.getOrgId())
				.failIfNotExists(true)
				.build();

		final BPartnerId shipBPartnerId = bPartnerDAO
				.retrieveBPartnerIdBy(bPartnerQuery)
				.get()/* bc failIfNotExists(true) */;

		orderFactory.shipBPartner(shipBPartnerId);

		orderFactory.datePromised(datePromised);

		orderFactory.createDraftOrderHeader();

		woProjectObjectUnderTestList.forEach(woProjectObjectUnderTest -> createOrderLineAndSetOnObjectUnderTest(orderFactory, woProjectObjectUnderTest));

		orderFactory.complete();
	}

	public void createOrderLineAndSetOnObjectUnderTest(
			@NonNull final OrderFactory orderFactory,
			@NonNull final WOProjectObjectUnderTest woProjectObjectUnderTest)
	{
		final ProductId productId = woProjectObjectUnderTest.getProductId();

		if (productId == null)
		{
			throw new AdempiereException("ProductId cannot be null at this point!");
		}

		final I_C_UOM uom = uomDAO.getById(productDAO.getById(productId.getRepoId()).getC_UOM_ID());

		final Quantity qty = Quantity.of(woProjectObjectUnderTest.getNumberOfObjectsUnderTest(), uom);

		final OrderLineBuilder orderLineBuilder = orderFactory.newOrderLine()
				.productId(productId)
				.addQty(qty);

		orderLineBuilder.build();

		final OrderAndLineId orderAndLineId = orderLineBuilder.getCreatedOrderAndLineId();

		woProjectObjectUnderTest.setOrderLineProvisionId(orderAndLineId.getOrderLineId());

		calculatePriceForOrderLine(orderAndLineId);
	}

	@Nullable
	public WOProjectObjectUnderTest getByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return woProjectObjectUnderTestRepository.getByOrderLineId(orderLineId);
	}

	public void updateObjectDeliveredDate(@NonNull final WOProjectObjectUnderTest woProjectObjectUnderTest, @NonNull final I_C_OrderLine orderLine)
	{
		woProjectObjectUnderTestRepository.updateObjectDeliveredDate(woProjectObjectUnderTest, orderLine);
	}

	// public List<WOProjectObjectUnderTest> getWOProjectObjectUnderTestListByOrderLine(@NonNull final OrderLineId orderLineId)
	// {
	// 	inOutDAO.retrieveLinesForOrderLine(orderLineBL.getOrderLineById(orderLineId))
	// 			.stream()
	// 			.map(iMInOutLine -> )
	// }

	private void calculatePriceForOrderLine(
			@NonNull final OrderAndLineId orderAndLineId)
	{
		final I_C_Order order = orderBL.getById(orderAndLineId.getOrderId());
		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(orderAndLineId.getOrderLineId());

		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(
				OrgId.ofRepoIdOrAny(order.getAD_Org_ID()),
				ProductId.ofRepoId(orderLine.getM_Product_ID()),
				BPartnerId.ofRepoId(order.getC_BPartner_ID()),
				Quantity.of(orderLine.getQtyEntered(), uomDAO.getById(orderLine.getC_UOM_ID())),
				soTrx);

		final I_M_PricingSystem pricingSystem = getPricingSystemOrNull(order, soTrx);

		if (pricingSystem == null)
		{
			throw new AdempiereException("@NotFound@ @M_PricingSystem_ID@"
												 + "\n @C_Order_ID@: " + order.getC_Order_ID()
												 + "\n @C_BPartner_ID@: " + order.getC_BPartner_ID());
		}

		final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(pricingSystem.getM_PricingSystem_ID());
		Check.assumeNotNull(pricingSystemId, "No pricing system found for M_Order_ID={}", order);

		final PriceListId priceListId = priceListDAO.retrievePriceListIdByPricingSyst(
				pricingSystemId,
				BPartnerLocationAndCaptureId.ofRepoId(order.getC_BPartner_ID(), order.getC_BPartner_Location_ID()),
				soTrx);
		Check.errorIf(priceListId == null,
					  "No price list found for C_OrderLine_ID {}; C_Order.M_PricingSystem_ID={}, C_Order.C_BPartner_Location_ID={}, C_Order.SOTrx={}",
					  orderLine.getC_OrderLine_ID(), pricingSystemId, order.getC_BPartner_Location_ID(), soTrx);

		pricingCtx.setPricingSystemId(pricingSystemId);
		pricingCtx.setPriceListId(priceListId);

		final LocalDate orderedLocalDate = TimeUtil.asLocalDate(orderLine.getDateOrdered(), orgDAO.getTimeZone(OrgId.ofRepoId(order.getAD_Org_ID())));
		if (orderedLocalDate != null)
		{
			pricingCtx.setPriceDate(orderedLocalDate);
		}

		pricingCtx.setFailIfNotCalculated();

		pricingBL.calculatePrice(pricingCtx);
	}

	@Nullable
	private I_M_PricingSystem getPricingSystemOrNull(@NonNull final I_C_Order order, @NonNull final SOTrx soTrx)
	{
		if (order.getM_PricingSystem_ID() > 0)
		{
			final PricingSystemId pricingSystemId = PricingSystemId.ofRepoId(order.getM_PricingSystem_ID());
			return priceListDAO.getPricingSystemById(pricingSystemId);
		}

		final PricingSystemId pricingSystemId = bPartnerDAO.retrievePricingSystemIdOrNull(BPartnerId.ofRepoId(order.getC_BPartner_ID()), soTrx);
		if (pricingSystemId == null)
		{
			return null;
		}

		return priceListDAO.getPricingSystemById(pricingSystemId);
	}

	// public void updateObjectDeliveredDate(
	// 		@NonNull final OrderAndLineId orderAndLineId,
	// 		@NonNull final WOProjectObjectUnderTest woProjectObjectUnderTest)
	// {
	// 	if (woProjectObjectUnderTest.getOrderLineProvisionId() == null)
	// 	{
	// 		return;
	// 	}
	//
	// 	final Quantity qtyToDeliver = orderLineBL.getQtyToDeliver(orderAndLineId);
	//
	// 	if (qtyToDeliver.isZero())
	// 	{
	// 		final I_C_OrderLine orderLine = orderLineBL.getOrderLineById(OrderLineId.ofRepoId(orderAndLineId.getOrderLineRepoId()));
	//
	//
	// 	}
	// }
}
