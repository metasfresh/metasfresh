
DROP INDEX IF EXISTS c_validcombination_alt
;

CREATE UNIQUE INDEX c_validcombination_alt
    ON c_validcombination (ad_client_id, c_acctschema_id, ad_org_id, account_id,
                           (COALESCE(c_subacct_id, 0::numeric)),
                           (COALESCE(m_product_id, 0::numeric)),
                           (COALESCE(c_bpartner_id, 0::numeric)),
                           (COALESCE(ad_orgtrx_id, 0::numeric)),
                           (COALESCE(c_locfrom_id, 0::numeric)),
                           (COALESCE(c_locto_id, 0::numeric)),
                           (COALESCE(c_salesregion_id, 0::numeric)),
                           (COALESCE(c_project_id, 0::numeric)),
                           (COALESCE(c_campaign_id, 0::numeric)),
                           (COALESCE(c_activity_id, 0::numeric)),
                           (COALESCE(user1_id, 0::numeric)),
                           (COALESCE(user2_id, 0::numeric)),
                           (COALESCE(userelement1_id, 0::numeric)),
                           (COALESCE(userelement2_id, 0::numeric)))
    WHERE (isactive = 'Y'::bpchar)
;
