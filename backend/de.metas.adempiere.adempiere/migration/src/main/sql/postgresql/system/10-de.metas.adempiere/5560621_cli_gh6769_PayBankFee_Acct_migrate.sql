INSERT INTO c_elementvalue (c_elementvalue_id,
                            ad_client_id, ad_org_id,
                            isactive, created, createdby, updated, updatedby,
                            VALUE, NAME,
                            accounttype, accountsign,
                            isdoccontrolled,
                            c_element_id,
                            issummary,
                            postactual,
                            parent_id,
                            seqno)
VALUES (540032, -- c_elementvalue_id
        1000000, 0, -- client/org
        'Y', '2020-06-05', 99, '2020-06-05', 99,
        '69100', 'Bank Fee', -- Value, Name
        'E', 'N', -- acct type, sign
        'Y', -- is doc controlled,
        1000000, -- c_element_id
        'N', -- isSummary
        'Y', -- post actual
        NULL, -- parent_id
        1 -- seqno
       );



INSERT INTO c_validcombination (c_validcombination_id,
                                ad_client_id, ad_org_id,
                                isactive,
                                created, createdby, updated, updatedby,
                                combination,
                                description,
                                isfullyqualified,
                                c_acctschema_id,
                                account_id)
SELECT nextval('c_validcombination_seq'),
       ev.ad_client_id,
       0                    AS ad_org_id,
       'Y'                  AS isactive,
       now()                AS created,
       99                   AS createdby,
       now()                AS updated,
       99                      updatedby,
       ev.value             AS combination,
       ev.name              AS description,
       'Y'                  AS isfullyqualified,
       cas.c_acctschema_id,
       ev.c_elementvalue_id AS account_id
FROM c_elementvalue ev,
     c_acctschema cas
WHERE ev.c_elementvalue_id = 540032
  AND NOT EXISTS(SELECT 1
                 FROM c_validcombination vc
                 WHERE vc.account_id = ev.c_elementvalue_id
                   AND vc.c_acctschema_id = cas.c_acctschema_id);


UPDATE c_acctschema_default t
SET paybankfee_acct=(
    SELECT vc.c_validcombination_id
    FROM c_validcombination vc
    WHERE vc.c_acctschema_id = t.c_acctschema_id
      AND vc.account_id = 540032)
WHERE paybankfee_acct IS NULL;



UPDATE c_bp_bankaccount_acct ba
SET paybankfee_acct=d.paybankfee_acct
FROM c_acctschema_default d
WHERE d.c_acctschema_id = ba.c_acctschema_id
  AND ba.paybankfee_acct IS NULL;

