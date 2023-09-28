package de.metas.shippingnotification.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.document.DocBaseType;
import de.metas.document.dimension.Dimension;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationLine;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;

import java.math.BigDecimal;
import java.util.List;

class Doc_ShippingNotification extends Doc<DocLine<?>>
{
	private final ShippingNotificationAcctService shippingNotificationAcctService;
	private ShippingNotification shippingNotification;

	Doc_ShippingNotification(
			@NonNull ShippingNotificationAcctService shippingNotificationAcctService,
			@NonNull final AcctDocContext ctx)
	{
		super(ctx, DocBaseType.ShippingNotification);
		this.shippingNotificationAcctService = shippingNotificationAcctService;
	}

	@Override
	protected void loadDocumentDetails()
	{
		this.shippingNotification = shippingNotificationAcctService.getByRecord(getModel(I_M_Shipping_Notification.class));
	}

	@Override
	protected BigDecimal getBalance() {return BigDecimal.ZERO;}

	@Override
	protected List<Fact> createFacts(final AcctSchema as)
	{
		final Fact fact = new Fact(this, as, PostingType.Actual);
		shippingNotification.getLines().forEach(line -> createFactsForLine(fact, line));
		return ImmutableList.of(fact);
	}

	private void createFactsForLine(final Fact fact, final ShippingNotificationLine line)
	{
		// Skip not stockable (e.g. service products) because they have no cost
		if (!getServices().isProductStocked(line.getProductId()))
		{
			return;
		}

		final AcctSchema as = fact.getAcctSchema();
		final CostAmount costs = shippingNotificationAcctService.getCreateCosts(shippingNotification, line, as);

		final Dimension dimension = line.getDimension().fallbackTo(shippingNotification.getDimension());

		//
		// ExternallyOwnedStock DR
		final FactLine dr = fact.createLine()
				//.setDocLine(line)
				.lineId(line.getIdNotNull())
				.setAccount(getAccount(ProductAcctType.P_ExternallyOwnedStock_Acct, as, line))
				.setAmt(roundToStdPrecision(costs), null)
				.setQty(line.getQty())
				.locatorId(shippingNotification.getLocatorId())
				.fromLocationOfLocator(shippingNotification.getLocatorId())
				.toLocationOfBPartner(shippingNotification.getBpartnerAndLocationId())
				.buildAndAdd();
		if (dr == null)
		{
			throw newPostingException().setDetailMessage("FactLine DR not created: " + line);
		}
		dr.setFromDimension(dimension);

		//
		// Inventory CR
		final FactLine cr = fact.createLine()
				//.setDocLine(line)
				.lineId(line.getIdNotNull())
				.setAccount(getAccount(ProductAcctType.P_Asset_Acct, as, line))
				.setAmt(null, roundToStdPrecision(costs))
				.setQty(line.getQty().negate())
				.locatorId(shippingNotification.getLocatorId())
				.fromLocationOfLocator(shippingNotification.getLocatorId())
				.toLocationOfBPartner(shippingNotification.getBpartnerAndLocationId())
				.buildAndAdd();
		if (cr == null)
		{
			throw newPostingException().setDetailMessage("FactLine CR not created: " + line);
		}
		cr.setFromDimension(dimension);
	}

	public Account getAccount(@NonNull final ProductAcctType acctType, @NonNull final AcctSchema as, @NonNull ShippingNotificationLine line)
	{
		return getAccountProvider().getProductAccount(as.getId(), line.getProductId(), null, acctType);
	}

	@NonNull
	private CostAmount roundToStdPrecision(@NonNull final CostAmount costs)
	{
		return costs.round(services::getCurrencyStandardPrecision);
	}
}
