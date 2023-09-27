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
				.setAccount(getAccount(ProductAcctType.P_ExternallyOwnedStock_Acct, as, line))
				.setAmt(roundToStdPrecision(costs), null)
				.setQty(line.getQty())
				.buildAndAdd();
		if (dr == null)
		{
			throw newPostingException().setDetailMessage("FactLine DR not created: " + line);
		}
		dr.setLine_ID(line.getIdNotNull().getRepoId());
		dr.setFromDimension(dimension);
		dr.setM_Locator_ID(shippingNotification.getLocatorId().getRepoId());
		dr.setLocationFromLocator(shippingNotification.getLocatorId().getRepoId(), true);    // from Loc
		dr.setLocationFromBPartner(shippingNotification.getBpartnerAndLocationId(), false);  // to Loc

		//
		// Inventory CR
		final FactLine cr = fact.createLine()
				//.setDocLine(line)
				.setAccount(getAccount(ProductAcctType.P_Asset_Acct, as, line))
				.setAmt(null, roundToStdPrecision(costs))
				.setQty(line.getQty().negate())
				.buildAndAdd();
		if (cr == null)
		{
			throw newPostingException().setDetailMessage("FactLine CR not created: " + line);
		}
		cr.setLine_ID(line.getIdNotNull().getRepoId());
		cr.setFromDimension(dimension);
		cr.setM_Locator_ID(shippingNotification.getLocatorId().getRepoId());
		cr.setLocationFromLocator(shippingNotification.getLocatorId().getRepoId(), true);    // from Loc
		cr.setLocationFromBPartner(shippingNotification.getBpartnerAndLocationId(), false);  // to Loc
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
