package de.metas.order;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.money.Money;
import de.metas.order.impl.OrderLineDetailRepository;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

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
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final OrderLineDetailRepository orderLineDetailRepository = SpringContextHolder.instance.getBean(OrderLineDetailRepository.class);
	private final DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);

	private final OrderFactory parent;
	private boolean built = false;

	private ProductId productId;

	private AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;

	private Quantity qty;

	@Nullable
	private BigDecimal manualPrice;

	@Nullable
	private UomId priceUomId;
	private BigDecimal manualDiscount;

	@Nullable
	private String description;

	private boolean hideWhenPrinting;

	private final ArrayList<OrderLineDetailCreateRequest> detailCreateRequests = new ArrayList<>();

	private I_C_OrderLine createdOrderLine;

	private Dimension dimension;

	@Nullable
	private String productDescription;

	@Nullable
	private ActivityId activityId;

	@Nullable
	private ImmutableList<Consumer<I_C_OrderLine>> afterSaveHooks;

	@Nullable
	private PricingSystemId overridingPricingSystemId;

	/* package */ OrderLineBuilder(@NonNull final OrderFactory parent)
	{
		this.parent = parent;
	}

	/* package */ void build()
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
			orderLine.setPrice_UOM_ID(UomId.toRepoId(priceUomId));
		}

		if (manualDiscount != null)
		{
			orderLine.setIsManualDiscount(true);
			orderLine.setDiscount(manualDiscount);
		}

		if (dimension != null)
		{
			dimensionService.updateRecord(orderLine, dimension);
		}

		orderLineBL.updatePrices(getUpdatePriceRequest(orderLine));

		if (!Check.isBlank(description))
		{
			orderLine.setDescription(description);
		}

		orderLine.setIsHideWhenPrinting(hideWhenPrinting);
		orderLine.setProductDescription(productDescription);
		orderLine.setC_Activity_ID(ActivityId.toRepoId(activityId));

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

		getAfterSaveHooks().forEach(hook -> hook.accept(createdOrderLine));
	}

	private void assertNotBuilt()
	{
		if (built)
		{
			throw new AdempiereException("Already built: " + this);
		}
	}

	private OrderFactory getParent()
	{
		return parent;
	}

	public OrderFactory endOrderLine()
	{
		return getParent();
	}

	public OrderAndLineId getCreatedOrderAndLineId()
	{
		return OrderAndLineId.ofRepoIds(createdOrderLine.getC_Order_ID(), createdOrderLine.getC_OrderLine_ID());
	}

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
		return qty(Quantity.addNullables(this.qty, qtyToAdd));
	}

	public OrderLineBuilder qty(@NonNull final BigDecimal qty)
	{
		if (productId == null)
		{
			throw new AdempiereException("Setting BigDecimal Qty not allowed if the product was not already set");
		}

		final I_C_UOM uom = productBL.getStockUOM(productId);
		return qty(Quantity.of(qty, uom));
	}

	@Nullable
	private UomId getUomId()
	{
		return qty != null ? qty.getUomId() : null;
	}

	public OrderLineBuilder priceUomId(@Nullable final UomId priceUomId)
	{
		assertNotBuilt();
		this.priceUomId = priceUomId;
		return this;
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

	public OrderLineBuilder setDimension(final Dimension dimension)
	{
		assertNotBuilt();

		this.dimension = dimension;

		return this;
	}

	public boolean isProductAndUomMatching(
			@Nullable final ProductId productId,
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@Nullable final UomId uomId)
	{
		return ProductId.equals(this.productId, productId)
				&& AttributeSetInstanceId.equals(asiId, attributeSetInstanceId)
				&& UomId.equals(getUomId(), uomId);
	}

	public OrderLineBuilder description(@Nullable final String description)
	{
		this.description = description;
		return this;
	}

	public OrderLineBuilder hideWhenPrinting(final boolean hideWhenPrinting)
	{
		this.hideWhenPrinting = hideWhenPrinting;
		return this;
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

	public OrderLineBuilder productDescription(@Nullable final String productDescription)
	{
		this.productDescription = productDescription;
		return this;
	}

	public OrderLineBuilder activityId(@Nullable final ActivityId activityId)
	{
		this.activityId = activityId;
		return this;
	}

	@NonNull
	public OrderLineBuilder afterSaveHook(@NonNull final Consumer<I_C_OrderLine> afterSaveHook)
	{
		assertNotBuilt();

		final ImmutableList.Builder<Consumer<I_C_OrderLine>> hooksBuilder = ImmutableList.builder();

		this.afterSaveHooks = hooksBuilder
				.addAll(getAfterSaveHooks())
				.add(afterSaveHook)
				.build();

		return this;
	}

	@NonNull
	public OrderLineBuilder overridingPricingSystemId(@Nullable final PricingSystemId overridingPricingSystemId)
	{
		assertNotBuilt();

		this.overridingPricingSystemId = overridingPricingSystemId;

		return this;
	}

	@NonNull
	private ImmutableList<Consumer<I_C_OrderLine>> getAfterSaveHooks()
	{
		return CoalesceUtil.coalesceNotNull(afterSaveHooks, ImmutableList.of());
	}

	@NonNull
	private OrderLinePriceUpdateRequest getUpdatePriceRequest(@NonNull final I_C_OrderLine orderLine)
	{
		OrderLinePriceUpdateRequest request = OrderLinePriceUpdateRequest.ofOrderLine(orderLine);

		if (overridingPricingSystemId != null)
		{
			request = request.toBuilder()
					.pricingSystemIdOverride(overridingPricingSystemId)
					.build();
		}

		return request;
	}
}
