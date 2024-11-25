package de.metas.order.stats.purchase_max_price;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.CurrencyRepository;
import de.metas.money.MoneyService;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PurchaseLastMaxPriceService
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	@NonNull private final MoneyService moneyService;

	private static final String TABLENAME_Recompute = "purchase_order_highestprice_per_day_mv$recompute";

	@VisibleForTesting
	public static PurchaseLastMaxPriceService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new PurchaseLastMaxPriceService(new MoneyService(new CurrencyRepository()));
	}

	public PurchaseLastMaxPriceProvider newProvider()
	{
		return PurchaseLastMaxPriceProvider.builder()
				.sysConfigBL(sysConfigBL)
				.moneyService(moneyService)
				.build();
	}

	public void invalidateByOrder(final I_C_Order order)
	{
		// Consider only purchase orders
		if (order.isSOTrx())
		{
			return;
		}

		final LocalDate date = extractDate(order);
		final ImmutableSet<ProductId> productIds = getProductIds(order);
		invalidateByProducts(productIds, date);
	}

	private static LocalDate extractDate(final I_C_Order order)
	{
		return order.getDateOrdered().toLocalDateTime().toLocalDate();
	}

	private ImmutableSet<ProductId> getProductIds(final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		return orderDAO.retrieveOrderLines(orderId)
				.stream()
				.map(orderLine -> ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

	public void invalidateByProducts(@NonNull Set<ProductId> productIds, @NonNull LocalDate date)
	{
		if (productIds.isEmpty())
		{
			return;
		}

		final ArrayList<Object> sqlValuesParams = new ArrayList<>();
		final StringBuilder sqlValues = new StringBuilder();
		for (final ProductId productId : productIds)
		{
			if (sqlValues.length() > 0)
			{
				sqlValues.append(",");
			}

			sqlValues.append("(?,?)");
			sqlValuesParams.add(productId);
			sqlValuesParams.add(date);
		}

		final String sql = "INSERT INTO " + TABLENAME_Recompute + " (m_product_id, date)" + "VALUES " + sqlValues;
		DB.executeUpdateAndThrowExceptionOnFail(sql, sqlValuesParams.toArray(), ITrx.TRXNAME_ThreadInherited);
	}
}
