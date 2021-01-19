
-- CampaignPricingRule
UPDATE c_pricingrule
SET seqno=3000,
    description='Needs to be applied last, so it can verify whether it actually improves on the normal rules'' price. Also see https://github.com/metasfresh/metasfresh/issues/4952',
    updatedby=99,
    updated='2021-01-19 20:49:49.572807 +01:00'
WHERE c_pricingrule_id = 540014;
