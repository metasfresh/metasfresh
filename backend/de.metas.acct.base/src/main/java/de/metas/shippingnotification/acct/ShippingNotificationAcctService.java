package de.metas.shippingnotification.acct;

import de.metas.acct.api.AcctSchema;
import de.metas.costing.CostAmount;
import de.metas.costing.CostDetailCreateRequest;
import de.metas.costing.CostDetailReverseRequest;
import de.metas.costing.CostingDocumentRef;
import de.metas.costing.ICostingService;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationCollection;
import de.metas.shippingnotification.ShippingNotificationId;
import de.metas.shippingnotification.ShippingNotificationLine;
import de.metas.shippingnotification.ShippingNotificationQuery;
import de.metas.shippingnotification.ShippingNotificationService;
import de.metas.shippingnotification.model.I_M_Shipping_Notification;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingNotificationAcctService
{
	@NonNull private final ShippingNotificationService shippingNotificationService;
	@NonNull private final ICostingService costingService;

	public I_M_Shipping_Notification getRecordById(@NonNull final ShippingNotificationId id)
	{
		return shippingNotificationService.getRecordById(id);
	}

	public ShippingNotification getByRecord(@NonNull final I_M_Shipping_Notification record)
	{
		return shippingNotificationService.getByRecord(record);
	}

	public ShippingNotificationCollection getByQuery(@NonNull final ShippingNotificationQuery query)
	{
		return shippingNotificationService.getByQuery(query);
	}

	public CostAmount getCreateCosts(
			final ShippingNotification shippingNotification,
			final ShippingNotificationLine line,
			final AcctSchema as)
	{
		if (shippingNotification.isReversal())
		{
			return costingService.createReversalCostDetails(CostDetailReverseRequest.builder()
							.acctSchemaId(as.getId())
							.reversalDocumentRef(CostingDocumentRef.ofShippingNotificationLineId(line.getIdNotNull()))
							.initialDocumentRef(CostingDocumentRef.ofShippingNotificationLineId(line.getReversalLineId()))
							.date(shippingNotification.getDateAcct())
							.build())
					.getMainAmountToPost(as)
					// Negate the amount coming from the costs because it must be negative in the accounting.
					.negate();
		}
		else
		{
			return costingService.createCostDetail(
							CostDetailCreateRequest.builder()
									.acctSchemaId(as.getId())
									.clientId(shippingNotification.getClientId())
									.orgId(shippingNotification.getOrgId())
									.productId(line.getProductId())
									.attributeSetInstanceId(line.getAsiId())
									.documentRef(CostingDocumentRef.ofShippingNotificationLineId(line.getIdNotNull()))
									.qty(line.getQty().negate())
									.amt(CostAmount.zero(as.getCurrencyId())) // expect to be calculated
									.date(shippingNotification.getDateAcct())
									.build())
					.getMainAmountToPost(as)
					// The shipment notification is an outgoing document, so the costing amounts will be negative values.
					// In the accounting they must be positive values. This is the reason why the amount
					// coming from the product costs must be negated.
					.negate();
		}
	}
}
