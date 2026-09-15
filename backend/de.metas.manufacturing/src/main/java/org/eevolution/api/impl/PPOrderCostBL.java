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
	 * Reads the co-product's manual {@code M_Product.CoProductFixedCostPrice} live. A blank field (null /
	 * not-positive) means the product has NOT opted into fixed-price valuation. The product master is read
	 * fail-loud via {@link IProductDAO#getById(ProductId)}: an orphaned co-product FK surfaces as an exception
	 * rather than being silently swallowed into the qty-distribution path.
	 */
	@Override
	public Optional<BigDecimal> getFixedCostPrice(@NonNull final ProductId productId)
	{
		return Optional.ofNullable(productDAO.getById(productId).getCoProductFixedCostPrice())
				.filter(fixedCostPrice -> fixedCostPrice.signum() > 0);
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
