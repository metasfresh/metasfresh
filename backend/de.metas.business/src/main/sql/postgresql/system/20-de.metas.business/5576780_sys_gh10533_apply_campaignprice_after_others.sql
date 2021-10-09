
-- CampaignPricingRule
UPDATE c_pricingrule
SET seqno=1500,
    description='Needs to be applied after the "normal" pricing rules, so it can verify whether it actually improves on their price. Also see https://github.com/metasfresh/metasfresh/issues/4952',
    updatedby=99,
    updated='2021-01-19 20:49:49.572807 +01:00'
WHERE c_pricingrule_id = 540014;

-- ContractDiscount
UPDATE c_pricingrule
SET description='Sets the discount to 100% for a C_Flatrate_Conditions that has IsFreeOfCharge=Y',
    updatedby=99,
    updated='2021-01-19 20:49:49.572807 +01:00'
WHERE c_pricingrule_id = 540004;

-- CustomerTradeMarginPricingRule
UPDATE c_pricingrule
SET description='Allows a sales rep offer a lower price to a customer and in turn receive a lower commission',
    updatedby=99,
    updated='2021-01-19 20:49:49.572807 +01:00'
WHERE c_pricingrule_id = 540015;