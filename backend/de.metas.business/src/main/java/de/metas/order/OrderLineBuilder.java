package de.metas.order;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.money.Money;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
 * Order line builder. Used exclusively by {@link OrderFactory}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class OrderLineBuilder
{
	private static final Logger logger = LogManager.getLogger(OrderLineBuilder.class);
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final OrderLineDetailRepository orderLineDetailRepository = SpringContextHolder.instance.getBean(OrderLineDetailRepository.class);

	private final OrderFactory parent;
	private boolean built = false;

	private ProductId productId;
	private AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;
	private Quantity qty;

	@Nullable private BigDecimal manualPrice;
	private BigDecimal manualDiscount;

	private final ArrayList<OrderLineDetailCreateRequest> detailCreateRequests = new ArrayList<>();

	private I_C_OrderLine createdOrderLine;

	/* package */ OrderLineBuilder(@NonNull final OrderFactory parent)
	{
		this.parent = parent;
	}

	/* package */void build()
	{
		assertNotBuilt();
		built = true;

		Check.assumeNotNull(qty, "qty is not null");

		// assume returned order is already saved
		final I_C_OrderLine orderLine = orderLineBL.createOrderLine(getParent().getC_Order());

		orderLine.setM_Product_ID(productId.getRepoId());
		orderLine.setM_AttributeSetInstance_ID(asiId.getRepoId());

		orderLine.setQtyEntered(qty.toBigDecimal());
		orderLine.setC_UOM_ID(qty.getUomId().getRepoId());

		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qty, productId);
		orderLine.setQtyOrdered(qtyOrdered.toBigDecimal());

		if (manualPrice != null)
		{
			orderLine.setIsManualPrice(true);
			orderLine.setPriceEntered(manualPrice);
		}

		if (manualDiscount != null)
		{
			orderLine.setIsManualDiscount(true);
			orderLine.setDiscount(manualDiscount);
		}

		orderLineBL.updatePrices(orderLine);
		saveRecord(orderLine);

		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(orderLine))
		{
			// would be great to also have the pricing engine run in here..if there is a way for this without the need to save twice
			logger.debug("Set C_OrderLine.QtyOrdered={} as converted from qty={} and productId={}", qtyOrdered.toBigDecimal(), qty, productId);

			// Create Details if any
			if (!detailCreateRequests.isEmpty())
			{
				final OrderAndLineId orderAndLineId = OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID());
				final OrgId orgId = OrgId.ofRepoId(orderLine.getAD_Org_ID());
				detailCreateRequests.forEach(detailCreateRequest -> orderLineDetailRepository.createNew(
						orderAndLineId,
						orgId,
						detailCreateRequest));
			}
		}

		this.createdOrderLine = orderLine;
	}

	private void assertNotBuilt()
	{
		if (built)
		{
			throw new AdempiereException("Already built: " + this);
		}
	}

	public OrderFactory getParent() { return parent; }

	public OrderFactory endOrderLine() { return getParent(); }

	public OrderAndLineId getCreatedOrderAndLineId() { return OrderAndLineId.ofRepoIds(createdOrderLine.getC_Order_ID(), createdOrderLine.getC_OrderLine_ID()); }

	public OrderLineBuilder productId(final ProductId productId)
	{
		assertNotBuilt();
		this.productId = productId;
		return this;
	}

	private ProductId getProductId()
	{
		return productId;
	}

	public OrderLineBuilder asiId(@NonNull final AttributeSetInstanceId asiId)
	{
		assertNotBuilt();
		this.asiId = asiId;
		return this;
	}

	public OrderLineBuilder qty(@NonNull final Quantity qty)
	{
		assertNotBuilt();

		this.qty = qty;
		return this;
	}

	public OrderLineBuilder addQty(@NonNull final Quantity qtyToAdd)
	{
		assertNotBuilt();

		this.qty = Quantity.addNullables(this.qty, qtyToAdd);
		return this;
	}

	@Nullable
	private UomId getUomId()
	{
		return qty != null ? qty.getUomId() : null;
	}

	public OrderLineBuilder manualPrice(@Nullable final BigDecimal manualPrice)
	{
		assertNotBuilt();
		this.manualPrice = manualPrice;
		return this;
	}

	public OrderLineBuilder manualPrice(@Nullable final Money manualPrice)
	{
		return manualPrice(manualPrice != null ? manualPrice.toBigDecimal() : null);
	}

	public OrderLineBuilder manualDiscount(final BigDecimal manualDiscount)
	{
		assertNotBuilt();
		this.manualDiscount = manualDiscount;
		return this;
	}

	public boolean isProductAndUomMatching(@Nullable final ProductId productId, @Nullable final UomId uomId)
	{
		return Objects.equals(getProductId(), productId)
				&& Objects.equals(getUomId(), uomId);
	}

	public OrderLineBuilder details(@NonNull final Collection<OrderLineDetailCreateRequest> details)
	{
		assertNotBuilt();
		detailCreateRequests.addAll(details);
		return this;
	}

	public OrderLineBuilder detail(@NonNull final OrderLineDetailCreateRequest detail)
	{
		assertNotBuilt();
		detailCreateRequests.add(detail);
		return this;
	}
}
