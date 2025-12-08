
-- This deletes AD_ColumnCallout_IDs 540784 and 540788 from 2010
-- updating the pricelist is now done in de.metas.order.callout.C_Order (**after** C_BPartner_Location_Value_ID was updated)
DELETE
FROM ad_columncallout
WHERE classname IN (
                    'de.metas.adempiere.callout.OrderPricingSystem.cBPartnerId',
                    'de.metas.adempiere.callout.OrderPricingSystem.cBPartnerLocationId')
;
