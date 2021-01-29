package de.metas.order;

import de.metas.adempiere.model.I_C_Order;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.freighcost.FreightCostRule;
import de.metas.lang.SOTrx;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.project.ProjectId;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Factory class used to create(and complete) sales or purchase orders.
 * <p>
 * This is a general purpose class to be used by other more specific factories.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderFactory
{
	public static OrderFactory newPurchaseOrder()
	{
		return new OrderFactory()
				.soTrx(SOTrx.PURCHASE);
	}

	public static OrderFactory newSalesOrder()
	{
		return new OrderFactory()
				.soTrx(SOTrx.SALES);
	}

	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);

	private final I_C_Order order;
	private boolean built = false;

	private final List<OrderLineBuilder> orderLineBuilders = new ArrayList<>();

	private OrderFactory()
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		trxManager.assertThreadInheritedTrxExists();
		order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setDocStatus(DocStatus.Drafted.getCode());
		order.setDocAction(IDocument.ACTION_Complete);
	}

	public org.compiere.model.I_C_Order createAndComplete()
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			createDraft();

			documentBL.processEx(order, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			return order;
		}
	}

	public I_C_Order createDraft()
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			if (orderLineBuilders.isEmpty())
			{
				throw new AdempiereException("Cannot create an order without lines");
			}

			createDraftOrderHeader();

			orderLineBuilders.forEach(OrderLineBuilder::build);

			return order;
		}
	}

	public I_C_Order createDraftOrderHeader()
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			assertNotBuilt();
			built = true;

			if (order.getC_DocTypeTarget_ID() <= 0)
			{
				orderBL.setDocTypeTargetId(order);
			}

			if (order.getBill_BPartner_ID() <= 0)
			{
				orderBL.setBillLocation(order);
			}

			saveRecord(order);

			return order;
		}
	}

	private void assertNotBuilt()
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			if (built)
			{
				throw new AdempiereException("Already built: " + this);
			}
		}
	}

	/* package */I_C_Order getC_Order()
	{
		return order;
	}

	public OrderLineBuilder newOrderLine()
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			final OrderLineBuilder orderLineBuilder = new OrderLineBuilder(this);
			orderLineBuilders.add(orderLineBuilder);
			return orderLineBuilder;
		}
	}

	public Optional<OrderLineBuilder> orderLineByProductAndUom(final ProductId productId, final UomId uomId)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			return orderLineBuilders.stream()
					.filter(orderLineBuilder -> orderLineBuilder.isProductAndUomMatching(productId, uomId))
					.findFirst();
		}
	}

	private OrderFactory soTrx(@NonNull final SOTrx soTrx)
	{
		order.setIsSOTrx(soTrx.toBoolean());
		return this;
	}

	public OrderFactory shipperId(@Nullable final ShipperId shipperId)
	{
		assertNotBuilt();
		order.setM_Shipper_ID(ShipperId.toRepoId(shipperId));
		return this;
	}

	public OrderFactory freightCostRule(@NonNull final FreightCostRule freightCostRule)
	{
		assertNotBuilt();
		order.setFreightCostRule(freightCostRule.getCode());
		return this;
	}

	public OrderFactory paymentRule(@NonNull final PaymentRule paymentRule)
	{
		assertNotBuilt();
		order.setPaymentRule(paymentRule.getCode());
		return this;
	}

	public OrderFactory paymentTermId(@Nullable final PaymentTermId paymentTermId)
	{
		assertNotBuilt();
		order.setC_PaymentTerm_ID(PaymentTermId.toRepoId(paymentTermId));
		return this;
	}

	public OrderFactory invoiceRule(@NonNull final InvoiceRule invoiceRule)
	{
		assertNotBuilt();
		order.setInvoiceRule(invoiceRule.getCode());
		return this;
	}

	public OrderFactory docType(final DocTypeId docTypeTargetId)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(order))
		{
			assertNotBuilt();

			orderBL.setDocTypeTargetIdAndUpdateDescription(order, docTypeTargetId);

			return this;
		}
	}

	public OrderFactory warehouseId(@Nullable final WarehouseId warehouseId)
	{
		assertNotBuilt();
		order.setM_Warehouse_ID(WarehouseId.toRepoId(warehouseId));
		return this;
	}

	public OrderFactory orgId(@NonNull final OrgId orgId)
	{
		assertNotBuilt();
		order.setAD_Org_ID(orgId.getRepoId());
		return this;
	}

	public OrgId getOrgId()
	{
		return OrgId.ofRepoId(order.getAD_Org_ID());
	}


	public OrderFactory dateOrdered(final LocalDate dateOrdered)
	{
		assertNotBuilt();
		order.setDateOrdered(TimeUtil.asTimestamp(dateOrdered));
		return this;
	}

	public OrderFactory datePromised(final ZonedDateTime datePromised)
	{
		assertNotBuilt();
		order.setDatePromised(TimeUtil.asTimestamp(datePromised));
		return this;
	}

	public ZonedDateTime getDatePromised()
	{
		return TimeUtil.asZonedDateTime(order.getDatePromised());
	}

	public OrderFactory shipBPartner(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@Nullable final BPartnerContactId contactId)
	{
		assertNotBuilt();

		if (bpartnerLocationId != null && !BPartnerId.equals(bpartnerId, bpartnerLocationId.getBpartnerId()))
		{
			throw new AdempiereException("BPartner not matching: " + bpartnerLocationId + ", " + bpartnerId);
		}
		if (contactId != null && !BPartnerId.equals(bpartnerId, contactId.getBpartnerId()))
		{
			throw new AdempiereException("BPartner not matching: " + contactId + ", " + bpartnerId);
		}

		order.setC_BPartner_ID(bpartnerId.getRepoId());
		order.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));
		order.setAD_User_ID(BPartnerContactId.toRepoId(contactId));
		return this;
	}

	public OrderFactory shipBPartner(final BPartnerId bpartnerId)
	{
		shipBPartner(bpartnerId, null, null);
		return this;
	}

	public BPartnerId getShipBPartnerId()
	{
		return BPartnerId.ofRepoId(order.getC_BPartner_ID());
	}

	public OrderFactory pricingSystemId(@NonNull final PricingSystemId pricingSystemId)
	{
		assertNotBuilt();
		order.setM_PricingSystem_ID(pricingSystemId.getRepoId());
		return this;
	}

	public OrderFactory poReference(final String poReference)
	{
		assertNotBuilt();
		order.setPOReference(poReference);
		return this;
	}

	public OrderFactory salesRepId(@Nullable final UserId salesRepId)
	{
		assertNotBuilt();
		order.setSalesRep_ID(UserId.toRepoId(salesRepId));
		return this;
	}

	public OrderFactory projectId(@Nullable final ProjectId projectId)
	{
		assertNotBuilt();
		order.setC_Project_ID(ProjectId.toRepoId(projectId));
		return this;
	}

	public OrderFactory campaignId(final int campaignId)
	{
		assertNotBuilt();
		order.setC_Campaign_ID(campaignId);
		return this;
	}
}
