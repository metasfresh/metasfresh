package de.metas.shippingnotification.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.Account;
import de.metas.acct.accounts.ProductAcctType;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.PostingType;
import de.metas.acct.doc.AcctDocContext;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.document.DocBaseType;
import de.metas.document.dimension.Dimension;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationLine;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.acct.Doc;
import org.compiere.acct.DocLine;
import org.compiere.acct.Fact;
import org.compiere.acct.FactLine;

import java.math.BigDecimal;
import java.util.List;

public class Doc_ShippingNotification extends Doc<DocLine<?>>
{
	private final ShippingNotificationService shippingNotificationService = SpringContextHolder.instance.getBean(ShippingNotificationService.class);
	private ShippingNotification shippingNotification;

	protected Doc_ShippingNotification(final @NonNull AcctDocContext ctx)
	{
		super(ctx, DocBaseType.ShippingNotification);
	}

	@Override
	protected void loadDocumentDetails()
	{
		this.shippingNotification = shippingNotificationService.getByRecord(getModel(I_M_Shipping_Notification.class));
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
		final CostAmount costs = getCreateShipmentCosts(line, as);

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

	public CostAmount getCreateShipmentCosts(final ShippingNotificationLine line, final AcctSchema as)
	{
		if (shippingNotification.isReversal())
		{
			return services.createReversalCostDetails(CostDetailReverseRequest.builder()
							.acctSchemaId(as.getId())
							.reversalDocumentRef(CostingDocumentRef.ofShippingNotificationLineId(line.getIdNotNull()))
							.initialDocumentRef(CostingDocumentRef.ofShippingNotificationLineId(line.getReversalLineId()))
							.date(getDateAcctAsInstant())
							.build())
					.getTotalAmountToPost(as)
					.getMainAmt()
					// Negate the amount coming from the costs because it must be negative in the accounting.
					.negate();
		}
		else
		{
			return services.createCostDetail(
							CostDetailCreateRequest.builder()
									.acctSchemaId(as.getId())
									.clientId(getClientId())
									.orgId(shippingNotification.getOrgId())
									.productId(line.getProductId())
									.attributeSetInstanceId(line.getAsiId())
									.documentRef(CostingDocumentRef.ofShippingNotificationLineId(line.getIdNotNull()))
									.qty(line.getQty())
									.amt(CostAmount.zero(as.getCurrencyId())) // expect to be calculated
									.currencyConversionContext(getCurrencyConversionContext(as))
									.date(getDateAcctAsInstant())
									.build())
					.getTotalAmountToPost(as)
					.getMainAmt()
					// The shipment notification is an outgoing document, so the costing amounts will be negative values.
					// In the accounting they must be positive values. This is the reason why the amount
					// coming from the product costs must be negated.
					.negate();
		}
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
