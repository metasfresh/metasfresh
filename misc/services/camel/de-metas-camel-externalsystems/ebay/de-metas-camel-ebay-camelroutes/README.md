# Unit Tests

In order to run the unit test you need two things configured:

1. ebay-test-creds.yaml in src/test/resources. Format see: https://github.com/eBay/ebay-oauth-java-client/blob/master/src/test/resources/ebay-config-sample.yaml

2. ebay-user for sandbox (see: application.properties)



# Api to Metasfesh mappings



| Nr. | Field-Name | Occurence E | M                 | Metas OC-Field                    | Metas Payment Field | Notes                                             |
| --- | --- | --- |-------------------|-----------------------------------| --- |---------------------------------------------------|
|  | #main order information |  |                   |                                   |  |                                                   |
| 1 | orderId | Always | x                 | externalHeaderId                  | externalOrderId |                                                   |
| 2 | legacyOrderId | Always |                   |                                   |  |                                                   |
| 3 | creationDate | Always | x                 | dateOrdered                       |  |                                                   |
| 4 | lastModifiedDate | Always |                   |                                   |  |                                                   |
| m4 |  |  | x                 | dataDest                          |  |                                                   |
| 5 | ebay-<sellerId> | Always | x                 | dataSource                        |  | Prefix added to enable user source identification |
| 6 | salesRecordReference | Always |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | buyer | Always |                   |                                   |  |                                                   |
| 7 | buyer.username | Always | x                 | bpartner.bpartner.externalId      | bpartnerIdentifier | ext-Ebay-<C_Bartner.ExternalId>                   |
| 8 | buyer.CheckoutNotes |  |                   |                                   |  |                                                   |
|  | buyer.taxAddress | Always |                   |                                   |  |                                                   |
| 9 | buyer.taxAddress.city | abw. RE Empf. | (x)               | billBPartner.location.city        |  |                                                   |
| 10 | buyer.taxAddress.countryCode | abw. RE Empf. | (x)               | billBPartner.location.countryCode |  |                                                   |
| 11 | buyer.taxAddress.postalCode | abw. RE Empf. | (x)               | billBPartner.location.postal      |  |                                                   |
| 12 | buyer.taxAddress.stateOrProvince | abw. RE Empf. | (x)               | billBPartner.location.district    |  |                                                   |
|  | buyer.taxIdentifier |  |                   |                                   |  |                                                   |
| 13 | buyer.taxIdentifier.taxpayerId |  | (x)               | bPartner.vatId                    |  |                                                   |
| 14 | buyer.taxIdentifier.taxIdentifierType |  |                   |                                   |  |                                                   |
| 15 | buyer.taxIdentifier.issuingCountry |  |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | #status general |  |                   |                                   |  |                                                   |
| 16 | orderPaymentStatus |  |                   |                                   |  |                                                   |
| 17 | orderFulfillmentStatus |  |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # cancellation information if a cancel request has been made |  |                   |                                   |  |                                                   |
|  | cancelStatus | Always |                   |                                   |  |                                                   |
| 18 | cancelStatus.cancelledDate |  |                   |                                   |  |                                                   |
|  | cancelStatus.cancelRequests | Always |                   |                                   |  |                                                   |
| 19 | cancelStatus.cancelRequests.cancelCompletedDate |  |                   |                                   |  |                                                   |
| 20 | cancelStatus.cancelRequests.cancelInitiator |  |                   |                                   |  |                                                   |
| 21 | cancelStatus.cancelRequests.cancelReason |  |                   |                                   |  |                                                   |
| 22 | cancelStatus.cancelRequests.cancelRequestedDate | Always |                   |                                   |  |                                                   |
| 23 | cancelStatus.cancelRequests.cancelRequestId |  |                   |                                   |  |                                                   |
| 24 | cancelStatus.cancelRequests.cancelRequestState |  |                   |                                   |  |                                                   |
| 25 | cancelStatus.cancelState | Always |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # ebay collects tax? |  |                   |                                   |  |                                                   |
| 26 | ebayCollectAndRemitTax |  |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | #  set of specifications for fulfilling the order from customer profile |  |                   |                                   |  |                                                   |
|  | fulfillmentHrefs |  |                   |                                   |  |                                                   |
|  | fulfillmentStartInstructions | Always |                   |                                   |  |                                                   |
| 27 | fulfillmentStartInstructions.destinationTimeZone |  |                   |                                   |  |                                                   |
| 28 | fulfillmentStartInstructions.ebaySupportedFulfillment |  |                   |                                   |  |                                                   |
|  | fulfillmentStartInstructions.finalDestinationAddress |  |                   |                                   |  |                                                   |
| 29 | fulfillmentStartInstructions.finalDestinationAddress.addressLine1 | Always |                   |                                   |  |                                                   |
| 30 | fulfillmentStartInstructions.finalDestinationAddress.addressLine2 |  |                   |                                   |  |                                                   |
| 31 | fulfillmentStartInstructions.finalDestinationAddress.city | Always |                   |                                   |  |                                                   |
| 32 | fulfillmentStartInstructions.finalDestinationAddress.countryCode | Always |                   |                                   |  |                                                   |
| 33 | fulfillmentStartInstructions.finalDestinationAddress.county |  |                   |                                   |  |                                                   |
| 34 | fulfillmentStartInstructions.finalDestinationAddress.postalCode |  |                   |                                   |  |                                                   |
| 35 | fulfillmentStartInstructions.finalDestinationAddress.stateOrProvince |  |                   |                                   |  |                                                   |
| 36 | fulfillmentStartInstructions.fulfillmentInstructionsType | Always |                   |                                   |  |                                                   |
| 37 | fulfillmentStartInstructions.maxEstimatedDeliveryDate |  |                   |                                   |  |                                                   |
| 38 | fulfillmentStartInstructions.minEstimatedDeliveryDate |  |                   |                                   |  |                                                   |
|  | fulfillmentStartInstructions.pickupStep |  |                   |                                   |  |                                                   |
| 39 | fulfillmentStartInstructions.pickupStep.merchantLocationKey |  |                   |                                   |  |                                                   |
|  | fulfillmentStartInstructions.shippingStep |  |                   |                                   |  |                                                   |
| 40 | fulfillmentStartInstructions.shippingStep.shippingCarrierCode | Always |                   |                                   |  |                                                   |
| 41 | fulfillmentStartInstructions.shippingStep.shippingServiceCode | Always |                   |                                   |  |                                                   |
|  | fulfillmentStartInstructions.shippingStep.shipTo | Always |                   |                                   |  |                                                   |
| 42 | fulfillmentStartInstructions.shippingStep.shipTo.companyName |  | x                 | bpartner.bpartner.companyName     |  |                                                   |
| 43 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress | Always |                   |                                   |  |                                                   |
| 44 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.addressLine1 | Always | x                 | bpartner.location.address1        |  |                                                   |
| 45 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.addressLine2 |  | x                 | bpartner.location.address2        |  |                                                   |
| 46 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.city | Always | x                 | bpartner.location.city            |  |                                                   |
| 47 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.countryCode | Always | x                 | bpartner.location.countryCode     |  |                                                   |
| 48 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.county |  |                   |                                   |  |                                                   |
| 49 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.postalCode |  | x                 | bpartner.location.postal          |  |                                                   |
| 50 | fulfillmentStartInstructions.shippingStep.shipTo.contactAddress.stateOrProvince |  |                   |                                   |  |                                                   |
| 51 | fulfillmentStartInstructions.shippingStep.shipTo.email |  | x                 | bpartner.contact.email            |  | Masked by Ebay after 14 days (e.g. reimport)      |
| 52 | fulfillmentStartInstructions.shippingStep.shipTo.fullName | Always | x                 | bpartner.contact.name             |  |                                                   |
|  | fulfillmentStartInstructions.shippingStep.shipTo.primaryPhone |  |                   |                                   |  |                                                   |
| 53 | fulfillmentStartInstructions.shippingStep.shipTo.primaryPhone.phoneNumber | Always | x                 | bpartner.contact.phone            |  |                                                   |
| 54 | fulfillmentStartInstructions.shippingStep.shipToReferenceId |  |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # order items |  |                   |                                   |  |                                                   |
|  | lineItems | Always |                   | One OLC per line item             |  |                                                   |
| 55 | lineItems.title | Always | x                 | product.name                      |  |                                                   |
| 56 | lineItems.lineItemId | Always | x                 | olc.externalLineId                |  |                                                   |
| 57 | lineItems.sku |  | productIdentifier |                                   |  |                                                   |
| 58 | lineItems.soldFormat | Always |                   |                                   |  |                                                   |
| 59 | lineItems.legacyItemId | Always |                   |                                   |  |                                                   |
| 60 | lineItems.legacyVariationId |  |                   |                                   |  |                                                   |
| 61 | lineItems.listingMarketplaceId | Always |                   |                                   |  |                                                   |
| 62 | lineItems.quantity | Always | x                 | qty                               |  |                                                   |
|  | lineItems.properties | Always |                   |                                   |  |                                                   |
| 63 | lineItems.properties.buyerProtection | Always |                   |                                   |  |                                                   |
| 64 | lineItems.properties.fromBestOffer |  |                   |                                   |  |                                                   |
| 65 | lineItems.properties.soldViaAdCampaign |  |                   |                                   |  |                                                   |
| 66 | lineItems.purchaseMarketplaceId | Always |                   |                                   |  |                                                   |
|  | lineItems.total | Always |                   |                                   |  |                                                   |
| 67 | lineItems.total.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 68 | lineItems.total.convertedFromValue |  |                   |                                   |  |                                                   |
| 69 | lineItems.total.currency | Always | x                 | currencyCode                      |  |                                                   |
| 70 | lineItems.total.value | Always | x                 | price                             |  |                                                   |
|  | lineItems.appliedPromotions | Always |                   |                                   |  |                                                   |
| 71 | lineItems.appliedPromotions.description |  |                   |                                   |  |                                                   |
| 72 | lineItems.appliedPromotions.discountAmount |  |                   |                                   |  |                                                   |
| 73 | lineItems.appliedPromotions.discountAmount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 74 | lineItems.appliedPromotions.discountAmount.convertedFromValue |  |                   |                                   |  |                                                   |
| 75 | lineItems.appliedPromotions.discountAmount.currency | Always |                   |                                   |  |                                                   |
| 76 | lineItems.appliedPromotions.discountAmount.value | Always |                   |                                   |  |                                                   |
| 77 | lineItems.appliedPromotions.promotionId |  |                   |                                   |  |                                                   |
|  | lineItems.deliveryCost | Always |                   |                                   |  |                                                   |
| 78 | lineItems.deliveryCost.importCharges |  |                   |                                   |  |                                                   |
| 79 | lineItems.deliveryCost.importCharges.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 80 | lineItems.deliveryCost.importCharges.convertedFromValue |  |                   |                                   |  |                                                   |
| 81 | lineItems.deliveryCost.importCharges.currency | Always |                   |                                   |  |                                                   |
| 82 | lineItems.deliveryCost.importCharges.value | Always |                   |                                   |  |                                                   |
|  | lineItems.deliveryCost.shippingCost | Always |                   |                                   |  |                                                   |
| 83 | lineItems.deliveryCost.shippingCost.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 84 | lineItems.deliveryCost.shippingCost.convertedFromValue |  |                   |                                   |  |                                                   |
| 85 | lineItems.deliveryCost.shippingCost.currency | Always |                   |                                   |  |                                                   |
| 86 | lineItems.deliveryCost.shippingCost.value | Always |                   |                                   |  |                                                   |
|  | lineItems.deliveryCost.shippingIntermediationFee |  |                   |                                   |  |                                                   |
| 87 | lineItems.deliveryCost.shippingIntermediationFee.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 88 | lineItems.deliveryCost.shippingIntermediationFee.convertedFromValue |  |                   |                                   |  |                                                   |
| 89 | lineItems.deliveryCost.shippingIntermediationFee.currency |  |                   |                                   |  |                                                   |
| 90 | lineItems.deliveryCost.shippingIntermediationFee.value |  |                   |                                   |  |                                                   |
|  | lineItems.discountedLineItemCost |  |                   |                                   |  |                                                   |
| 91 | lineItems.discountedLineItemCost.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 92 | lineItems.discountedLineItemCost.convertedFromValue |  |                   |                                   |  |                                                   |
| 93 | lineItems.discountedLineItemCost.currency | Always |                   |                                   |  |                                                   |
| 94 | lineItems.discountedLineItemCost.value | Always |                   |                                   |  |                                                   |
|  | lineItems.ebayCollectAndRemitTaxes |  |                   |                                   |  |                                                   |
| 95 | lineItems.ebayCollectAndRemitTaxes.amount |  |                   |                                   |  |                                                   |
| 96 | lineItems.ebayCollectAndRemitTaxes.amount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 97 | lineItems.ebayCollectAndRemitTaxes.amount.convertedFromValue |  |                   |                                   |  |                                                   |
| 98 | lineItems.ebayCollectAndRemitTaxes.amount.currency | Always |                   |                                   |  |                                                   |
| 99 | lineItems.ebayCollectAndRemitTaxes.amount.value | Always |                   |                                   |  |                                                   |
| 100 | lineItems.ebayCollectAndRemitTaxes.taxType |  |                   |                                   |  |                                                   |
| 101 | lineItems.ebayCollectAndRemitTaxes.collectionMethod |  |                   |                                   |  |                                                   |
|  | lineItems.giftDetails |  |                   |                                   |  |                                                   |
| 102 | lineItems.giftDetails.message |  |                   |                                   |  |                                                   |
| 103 | lineItems.giftDetails.recipientEmail | Always |                   |                                   |  |                                                   |
| 104 | lineItems.giftDetails.senderName | Always |                   |                                   |  |                                                   |
|  | lineItems.lineItemCost | Always |                   |                                   |  |                                                   |
| 105 | lineItems.lineItemCost.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 106 | lineItems.lineItemCost.convertedFromValue |  |                   |                                   |  |                                                   |
| 107 | lineItems.lineItemCost.currency | Always |                   |                                   |  |                                                   |
| 108 | lineItems.lineItemCost.value | Always |                   |                                   |  |                                                   |
|  | lineItems.lineItemFulfillmentInstructions | Always |                   |                                   |  |                                                   |
| 109 | lineItems.lineItemFulfillmentInstructions.destinationTimeZone |  |                   |                                   |  |                                                   |
| 110 | lineItems.lineItemFulfillmentInstructions.guaranteedDelivery | Always |                   |                                   |  |                                                   |
| 111 | lineItems.lineItemFulfillmentInstructions.maxEstimatedDeliveryDate | Always |                   |                                   |  |                                                   |
| 112 | lineItems.lineItemFulfillmentInstructions.minEstimatedDeliveryDate | Always |                   |                                   |  |                                                   |
| 113 | lineItems.lineItemFulfillmentInstructions.shipByDate | Always |                   |                                   |  |                                                   |
| 114 | lineItems.lineItemFulfillmentInstructions.sourceTimeZone |  |                   |                                   |  |                                                   |
| 115 | lineItems.lineItemFulfillmentStatus | Always |                   |                                   |  |                                                   |
|  | lineItems.refunds | Always |                   |                                   |  |                                                   |
| 116 | lineItems.refunds.amount |  |                   |                                   |  |                                                   |
| 117 | lineItems.refunds.amount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 118 | lineItems.refunds.amount.convertedFromValue |  |                   |                                   |  |                                                   |
| 119 | lineItems.refunds.amount.currency | Always |                   |                                   |  |                                                   |
| 120 | lineItems.refunds.amount.value | Always |                   |                                   |  |                                                   |
| 121 | lineItems.refunds.refundDate |  |                   |                                   |  |                                                   |
| 122 | lineItems.refunds.refundId |  |                   |                                   |  |                                                   |
| 123 | lineItems.refunds.refundReferenceId |  |                   |                                   |  |                                                   |
|  | lineItems.taxes | Always |                   |                                   |  |                                                   |
| 124 | lineItems.taxes.amount |  |                   |                                   |  |                                                   |
| 125 | lineItems.taxes.amount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 126 | lineItems.taxes.amount.convertedFromValue |  |                   |                                   |  |                                                   |
| 127 | lineItems.taxes.amount.currency | Always |                   |                                   |  |                                                   |
| 128 | lineItems.taxes.amount.value | Always |                   |                                   |  |                                                   |
| 129 | lineItems.taxes.taxType |  |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # detailed payment information for the order |  |                   |                                   |  |                                                   |
|  | paymentSummary | Always |                   |                                   |  |                                                   |
|  | paymentSummary.payments | Always |                   |                                   |  |                                                   |
|  | paymentSummary.payments.amount | Always |                   |                                   |  |                                                   |
| 130 | paymentSummary.payments.amount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 131 | paymentSummary.payments.amount.convertedFromValue |  |                   |                                   |  |                                                   |
| 132 | paymentSummary.payments.amount.currency | Always |                   |                                   |  |                                                   |
| 133 | paymentSummary.payments.amount.value | Always |                   |                                   |  |                                                   |
| 134 | paymentSummary.payments.paymentDate |  |                   |                                   |  |                                                   |
|  | paymentSummary.payments.paymentHolds |  |                   |                                   |  |                                                   |
| 135 | paymentSummary.payments.paymentHolds.expectedReleaseDate |  |                   |                                   |  |                                                   |
|  | paymentSummary.payments.paymentHolds.holdAmount |  |                   |                                   |  |                                                   |
| 136 | paymentSummary.payments.paymentHolds.holdAmount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 137 | paymentSummary.payments.paymentHolds.holdAmount.convertedFromValue |  |                   |                                   |  |                                                   |
| 138 | paymentSummary.payments.paymentHolds.holdAmount.currency | Always |                   |                                   |  |                                                   |
| 139 | paymentSummary.payments.paymentHolds.holdAmount.value | Always |                   |                                   |  |                                                   |
| 140 | paymentSummary.payments.paymentHolds.holdReason |  |                   |                                   |  |                                                   |
| 141 | paymentSummary.payments.paymentHolds.holdState |  |                   |                                   |  |                                                   |
| 142 | paymentSummary.payments.paymentHolds.releaseDate |  |                   |                                   |  |                                                   |
| 143 | paymentSummary.payments.paymentHolds.sellerActionsToRelease |  |                   |                                   |  |                                                   |
| 144 | paymentSummary.payments.paymentHolds.sellerActionsToRelease.sellerActionToRelease |  |                   |                                   |  |                                                   |
| 145 | paymentSummary.payments.paymentMethod | Always |                   |                                   |  |                                                   |
| 146 | paymentSummary.payments.paymentReferenceId |  |                   |                                   |  |                                                   |
| 147 | paymentSummary.payments.paymentStatus | Always |                   |                                   |  |                                                   |
|  | paymentSummary.refunds | Always |                   |                                   |  |                                                   |
|  | paymentSummary.refunds.amount |  |                   |                                   |  |                                                   |
| 148 | paymentSummary.refunds.amount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 149 | paymentSummary.refunds.amount.convertedFromValue |  |                   |                                   |  |                                                   |
| 150 | paymentSummary.refunds.amount.currency | Always |                   |                                   |  |                                                   |
| 151 | paymentSummary.refunds.amount.value | Always |                   |                                   |  |                                                   |
| 152 | paymentSummary.refunds.refundDate |  |                   |                                   |  |                                                   |
| 153 | paymentSummary.refunds.refundId |  |                   |                                   |  |                                                   |
| 154 | paymentSummary.refunds.refundReferenceId |  |                   |                                   |  |                                                   |
| 155 | paymentSummary.refunds.refundStatus |  |                   |                                   |  |                                                   |
|  | paymentSummary.totalDueSeller | Always |                   |                                   |  |                                                   |
| 156 | paymentSummary.totalDueSeller.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 157 | paymentSummary.totalDueSeller.convertedFromValue |  |                   |                                   |  |                                                   |
| 158 | paymentSummary.totalDueSeller.currency | Always |                   |                                   |  |                                                   |
| 159 | paymentSummary.totalDueSeller.value | Always |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # summary of cumulative costs and charges for all line items of an order |  |                   |                                   |  |                                                   |
|  | pricingSummary | Always |                   |                                   |  |                                                   |
|  | pricingSummary.adjustment |  |                   |                                   |  |                                                   |
| 160 | pricingSummary.adjustment.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 161 | pricingSummary.adjustment.convertedFromValue |  |                   |                                   |  |                                                   |
| 162 | pricingSummary.adjustment.currency | Always |                   |                                   |  |                                                   |
| 163 | pricingSummary.adjustment.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.deliveryCost | Always |                   |                                   |  |                                                   |
| 164 | pricingSummary.deliveryCost.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 165 | pricingSummary.deliveryCost.convertedFromValue |  |                   |                                   |  |                                                   |
| 166 | pricingSummary.deliveryCost.currency | Always |                   |                                   |  |                                                   |
| 167 | pricingSummary.deliveryCost.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.deliveryDiscount |  |                   |                                   |  |                                                   |
| 168 | pricingSummary.deliveryDiscount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 169 | pricingSummary.deliveryDiscount.convertedFromValue |  |                   |                                   |  |                                                   |
| 170 | pricingSummary.deliveryDiscount.currency | Always |                   |                                   |  |                                                   |
| 171 | pricingSummary.deliveryDiscount.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.fee |  |                   |                                   | Should fees be stored somewhere? |                                                   |
| 172 | pricingSummary.fee.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 173 | pricingSummary.fee.convertedFromValue |  |                   |                                   |  |                                                   |
| 174 | pricingSummary.fee.currency | Always |                   |                                   |  |                                                   |
| 175 | pricingSummary.fee.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.priceDiscountSubtotal |  |                   |                                   |  |                                                   |
| 176 | pricingSummary.priceDiscountSubtotal.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 177 | pricingSummary.priceDiscountSubtotal.convertedFromValue |  |                   |                                   |  |                                                   |
| 178 | pricingSummary.priceDiscountSubtotal.currency | Always |                   |                                   |  |                                                   |
| 179 | pricingSummary.priceDiscountSubtotal.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.priceSubtotal |  |                   |                                   |  |                                                   |
| 180 | pricingSummary.priceSubtotal.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 181 | pricingSummary.priceSubtotal.convertedFromValue |  |                   |                                   |  |                                                   |
| 182 | pricingSummary.priceSubtotal.currency | Always |                   |                                   |  |                                                   |
| 183 | pricingSummary.priceSubtotal.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.tax |  |                   |                                   |  |                                                   |
| 184 | pricingSummary.tax.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 185 | pricingSummary.tax.convertedFromValue |  |                   |                                   |  |                                                   |
| 186 | pricingSummary.tax.currency | Always |                   |                                   |  |                                                   |
| 187 | pricingSummary.tax.value | Always |                   |                                   |  |                                                   |
|  | pricingSummary.total | Always |                   |                                   |  |                                                   |
| 188 | pricingSummary.total.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 189 | pricingSummary.total.convertedFromValue |  |                   |                                   |  |                                                   |
| 190 | pricingSummary.total.currency | Always |                   | currencyCode                      |  |                                                   |
| 191 | pricingSummary.total.value | Always |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # if in eBays Authenticity Guarantee service. |  |                   |                                   |  |                                                   |
|  | program |  |                   |                                   |  |                                                   |
|  | program.authenticityVerification |  |                   |                                   |  |                                                   |
| 192 | program.authenticityVerification.outcomeReason |  |                   |                                   |  |                                                   |
| 193 | program.authenticityVerification.status |  |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # cumulative base amount used to calculate the final value fees for each |  |                   |                                   |  |                                                   |
|  | totalFeeBasisAmount | Always |                   |                                   |  |                                                   |
| 194 | totalFeeBasisAmount.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 195 | totalFeeBasisAmount.convertedFromValue |  |                   |                                   |  |                                                   |
| 196 | totalFeeBasisAmount.currency | Always |                   |                                   |  |                                                   |
| 197 | totalFeeBasisAmount.value | Always |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # cumulative fees accrued for the order and deducted from the seller payout |  |                   |                                   |  |                                                   |
|  | totalMarketplaceFee |  |                   |                                   |  |                                                   |
| 198 | totalMarketplaceFee.convertedFromCurrency |  |                   |                                   |  |                                                   |
| 199 | totalMarketplaceFee.convertedFromValue |  |                   |                                   |  |                                                   |
| 200 | totalMarketplaceFee.currency | Always |                   |                                   |  |                                                   |
| 201 | totalMarketplaceFee.value | Always |                   |                                   |  |                                                   |
|  |  |  |                   |                                   |  |                                                   |
|  | # call related meta |  |                   |                                   |  |                                                   |
| 202 | prev |  |                   |                                   |  |                                                   |
| 203 | total | Always |                   |                                   |  |                                                   |
|  | warnings |  |                   |                                   |  |                                                   |
| 204 | warnings.category |  |                   |                                   |  |                                                   |
| 205 | warnings.domain |  |                   |                                   |  |                                                   |
| 206 | warnings.errorId |  |                   |                                   |  |                                                   |
| 207 | warnings.inputRefIds |  |                   |                                   |  |                                                   |
| 208 | warnings.longMessage |  |                   |                                   |  |                                                   |
| 209 | warnings.message |  |                   |                                   |  |                                                   |
| 210 | warnings.outputRefIds |  |                   |                                   |  |                                                   |
| 211 | warnings.parameters |  |                   |                                   |  |                                                   |
| 212 | warnings.parameters.name |  |                   |                                   |  |                                                   |
| 213 | warnings.parameters.value |  |                   |                                   |  |                                                   |
| 214 | warnings.subdomain |  |                   |                                   |  |                                                   |
