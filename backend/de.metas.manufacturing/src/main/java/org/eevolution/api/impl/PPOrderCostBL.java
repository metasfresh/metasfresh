package org.eevolution.api.impl;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import org.eevolution.api.IPPOrderCostBL;
import org.eevolution.api.IPPOrderCostDAO;
import org.eevolution.api.PPOrderCosts;
import org.eevolution.model.I_PP_Order;

import org.eevolution.api.PPOrderId;
import de.metas.util.Services;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

public class PPOrderCostBL implements IPPOrderCostBL
{
	private final IPPOrderCostDAO orderCostsRepo = Services.get(IPPOrderCostDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Override
	public void createOrderCosts(@NonNull final I_PP_Order ppOrder)
	{
		new CreatePPOrderCostsCommand(ppOrder)
				.execute();
	}

	@Override
	public boolean hasPPOrderCosts(@NonNull final PPOrderId orderId)
	{
		return orderCostsRepo.hasPPOrderCosts(orderId);
	}

	@Override
	public PPOrderCosts getByOrderId(@NonNull final PPOrderId orderId)
	{
		return orderCostsRepo.getByOrderId(orderId);
	}

	@Override
	public void deleteByOrderId(@NonNull final PPOrderId orderId)
	{
		orderCostsRepo.deleteByOrderId(orderId);
	}

	/**
	 * {@code M_Product.CoProductFixedCostPrice} was a branch-only scaffold that was discarded before it was ever
	 * applied to a real database — it was rewritten in place into {@code M_Product.CoProductCostDistributionPercent}
	 * (see {@code 5824760_sys_M_Product_CoProductCostDistributionPercent.sql}). There is no column left to read, so
	 * no product can ever be opted into fixed-price valuation; this always reports "not opted in". Kept as a stub so
	 * the {@link org.eevolution.api.FixedCostPriceProvider} seam — still consulted by the Average-PO / Moving-
	 * Average-Invoice costing-method handlers — keeps compiling.
	 */
	@Override
	public Optional<BigDecimal> getFixedCostPrice(@NonNull final ProductId productId)
	{
		return Optional.empty();
	}

	@Override
	public String getProductName(@NonNull final ProductId productId)
	{
		return productDAO.getById(productId).getName();
	}

	@Override
	public void save(@NonNull final PPOrderCosts orderCosts)
	{
		orderCostsRepo.save(orderCosts);
	}
}
