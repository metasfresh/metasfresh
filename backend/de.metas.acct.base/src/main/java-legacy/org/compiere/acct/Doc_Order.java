package org.compiere.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.doc.AcctDocContext;
<<<<<<< HEAD

=======
import de.metas.order.OrderId;
import org.compiere.model.I_C_Order;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.math.BigDecimal;
import java.util.List;

public class Doc_Order extends Doc<DocLine_Order>
{
	public Doc_Order(final AcctDocContext ctx)
	{
		super(ctx);
	}

	@Override
	protected void loadDocumentDetails()
	{
	}

	@Override
	public BigDecimal getBalance()
	{
		return BigDecimal.ZERO;
	}

	@Override
	protected void checkConvertible(final AcctSchema acctSchema)
	{
		// since we are creating no acct facts there is no point to check
		// if the document's currency can be converted to accounting currency
	}

	@Override
	public List<Fact> createFacts(final AcctSchema as)
	{
		return ImmutableList.of();
	}
<<<<<<< HEAD
=======

	private I_C_Order getOrder()
	{
		return getModel(I_C_Order.class);
	}

	@Nullable
	@Override
	protected OrderId getSalesOrderId()
	{
		final I_C_Order order = getOrder();
		return order.isSOTrx() ? OrderId.ofRepoId(order.getC_Order_ID()) : null;
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
