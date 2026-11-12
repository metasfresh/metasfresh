-- One business partner group per Navision "Customer Group" and "Vendor Group" (key: Value = the Navision "Code").
-- An active group with the same Value is skipped. Customer groups are created first, so a vendor group with the same
-- code as a customer group is skipped and the customer group is used.
-- The staging tables "Customer Group" / "Vendor Group" are not part of ../migration_data; dump them like the other tables.
-- 04_partners.sql does not assign these groups (GroupValue is NULL, so the import uses the default group); ADAPT it if needed.
--
-- The groups are inserted by SQL, so C_BP_Group's model interceptor does not run. Its accounting records (C_BP_Group_Acct)
-- are created at the end of this script; without them, partners in these groups get no customer / vendor accounts.

DROP TABLE IF EXISTS migration_data.c_bp_group_Customer
;

CREATE TABLE migration_data.c_bp_group_Customer AS
SELECT 1000000                                  AS ad_client_id,
       1000000 AS ad_org_id, -- adapt per project
       'Y'::bpchar                              AS isactive,
       NOW()                                    AS created,
       99                                       AS createdby,
       NOW()                                    AS updated,
       99                                       AS updatedby,
       TRIM(LEFT(T."Code", 40))     AS value,
       TRIM(LEFT(T."Description", 60))          AS name,
       NULL                                     AS description,
       'N'::bpchar                              AS isdefault,
       NULL::numeric                            AS ad_printcolor_id,
       'N'::bpchar                              AS isconfidentialinfo,
       NULL                                     AS prioritybase,
       NULL::numeric                            AS m_pricelist_id,
       NULL::numeric                            AS po_pricelist_id,
       NULL::numeric                            AS m_discountschema_id,
       NULL::numeric                            AS po_discountschema_id,
       NULL::numeric                            AS creditwatchpercent,
       NULL::numeric                            AS pricematchtolerance,
       NULL::numeric                            AS c_dunning_id,
       NULL::numeric                            AS m_pricingsystem_id,
       NULL::numeric                            AS po_pricingsystem_id,
       NULL::numeric                            AS m_freightcost_id,
       NULL                                     AS paymentrule,
       'Y'::bpchar                              AS isprinttax,
       NULL                                     AS mrp_exclude,
       'Y'::bpchar                              AS isprinttaxsales,
       'Y'::bpchar                              AS iscustomer,
       'X'::bpchar                              AS socreditstatus,
       'XX'::varchar                            AS bpnameandgreetingstrategy,
       'N'::bpchar                              AS isdeviatingbillbpartner,
       NULL::numeric                            AS c_paymentterm_id,
       NULL::numeric                            AS po_paymentterm_id,
       NULL                                     AS freightcostrule,
       NULL::numeric                            AS parent_bp_group_id,
       NULL                                     AS paymentrulepo,
       NULL::numeric                            AS bill_bpartner_id,
       NULL::numeric                            AS bill_location_id,
       NULL::numeric                            AS bill_user_id,
       NULL::numeric                            AS c_incoterms_id,
       NULL                                     AS incotermlocation,
       NULL::numeric                            AS po_incoterms_id,
       NULL                                     AS po_incotermlocation,
       NULL                                     AS isautoinvoice,
       NULL                                     AS invoicerule,
       TRIM(LEFT(T."Code", 255))                AS migration_key
FROM migration_data."Navision$Customer Group" T
WHERE NOT EXISTS (SELECT 1
                  FROM c_bp_group
                  WHERE isactive = 'Y'
                    AND value = TRIM(LEFT(T."Code", 40)))
AND T."Description" != ''
;

INSERT INTO c_bp_group (c_bp_group_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, name, description, isdefault, ad_printcolor_id, isconfidentialinfo, prioritybase,
                        m_pricelist_id, po_pricelist_id, m_discountschema_id, po_discountschema_id,
                        creditwatchpercent, pricematchtolerance, c_dunning_id, m_pricingsystem_id,
                        po_pricingsystem_id, m_freightcost_id, paymentrule, isprinttax, mrp_exclude,
                        isprinttaxsales, iscustomer, socreditstatus, bpnameandgreetingstrategy, isdeviatingbillbpartner,
                        c_paymentterm_id, po_paymentterm_id, freightcostrule, parent_bp_group_id, paymentrulepo,
                        bill_bpartner_id, bill_location_id, bill_user_id, c_incoterms_id, incotermlocation,
                        po_incoterms_id, po_incotermlocation, isautoinvoice, invoicerule)
SELECT NEXTVAL('c_bp_group_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       value,
       name,
       description,
       isdefault,
       ad_printcolor_id,
       isconfidentialinfo,
       prioritybase,
       m_pricelist_id,
       po_pricelist_id,
       m_discountschema_id,
       po_discountschema_id,
       creditwatchpercent,
       pricematchtolerance,
       c_dunning_id,
       m_pricingsystem_id,
       po_pricingsystem_id,
       m_freightcost_id,
       paymentrule,
       isprinttax,
       mrp_exclude,
       isprinttaxsales,
       iscustomer,
       socreditstatus,
       bpnameandgreetingstrategy,
       isdeviatingbillbpartner,
       c_paymentterm_id,
       po_paymentterm_id,
       freightcostrule,
       parent_bp_group_id,
       paymentrulepo,
       bill_bpartner_id,
       bill_location_id,
       bill_user_id,
       c_incoterms_id,
       incotermlocation,
       po_incoterms_id,
       po_incotermlocation,
       isautoinvoice,
       invoicerule
FROM migration_data.c_bp_group_Customer
;



DROP TABLE IF EXISTS migration_data.c_bp_group_Vendor
;

CREATE TABLE migration_data.c_bp_group_Vendor AS
SELECT 1000000                                  AS ad_client_id,
       1000000 AS ad_org_id, -- adapt per project
       'Y'::bpchar                              AS isactive,
       NOW()                                    AS created,
       99                                       AS createdby,
       NOW()                                    AS updated,
       99                                       AS updatedby,
       TRIM(LEFT(T."Code", 40))     AS value,
       TRIM(LEFT(T."Description", 60))          AS name,
       NULL                                     AS description,
       'N'::bpchar                              AS isdefault,
       NULL::numeric                            AS ad_printcolor_id,
       'N'::bpchar                              AS isconfidentialinfo,
       NULL                                     AS prioritybase,
       NULL::numeric                            AS m_pricelist_id,
       NULL::numeric                            AS po_pricelist_id,
       NULL::numeric                            AS m_discountschema_id,
       NULL::numeric                            AS po_discountschema_id,
       NULL::numeric                            AS creditwatchpercent,
       NULL::numeric                            AS pricematchtolerance,
       NULL::numeric                            AS c_dunning_id,
       NULL::numeric                            AS m_pricingsystem_id,
       NULL::numeric                            AS po_pricingsystem_id,
       NULL::numeric                            AS m_freightcost_id,
       NULL                                     AS paymentrule,
       'Y'::bpchar                              AS isprinttax,
       NULL                                     AS mrp_exclude,
       'Y'::bpchar                              AS isprinttaxsales,
       'N'::bpchar                              AS iscustomer,
       'X'::bpchar                              AS socreditstatus,
       'XX'::varchar                            AS bpnameandgreetingstrategy,
       'N'::bpchar                              AS isdeviatingbillbpartner,
       NULL::numeric                            AS c_paymentterm_id,
       NULL::numeric                            AS po_paymentterm_id,
       NULL                                     AS freightcostrule,
       NULL::numeric                            AS parent_bp_group_id,
       NULL                                     AS paymentrulepo,
       NULL::numeric                            AS bill_bpartner_id,
       NULL::numeric                            AS bill_location_id,
       NULL::numeric                            AS bill_user_id,
       NULL::numeric                            AS c_incoterms_id,
       NULL                                     AS incotermlocation,
       NULL::numeric                            AS po_incoterms_id,
       NULL                                     AS po_incotermlocation,
       NULL                                     AS isautoinvoice,
       NULL                                     AS invoicerule,
       TRIM(LEFT(T."Code", 255))                AS migration_key
FROM migration_data."Navision$Vendor Group" T
WHERE NOT EXISTS (SELECT 1
                  FROM c_bp_group
                  WHERE isactive = 'Y'
                    AND value = TRIM(LEFT(T."Code", 40)))
;

INSERT INTO c_bp_group (c_bp_group_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        value, name, description, isdefault, ad_printcolor_id, isconfidentialinfo, prioritybase,
                        m_pricelist_id, po_pricelist_id, m_discountschema_id, po_discountschema_id,
                        creditwatchpercent, pricematchtolerance, c_dunning_id, m_pricingsystem_id,
                        po_pricingsystem_id, m_freightcost_id, paymentrule, isprinttax, mrp_exclude,
                        isprinttaxsales, iscustomer, socreditstatus, bpnameandgreetingstrategy, isdeviatingbillbpartner,
                        c_paymentterm_id, po_paymentterm_id, freightcostrule, parent_bp_group_id, paymentrulepo,
                        bill_bpartner_id, bill_location_id, bill_user_id, c_incoterms_id, incotermlocation,
                        po_incoterms_id, po_incotermlocation, isautoinvoice, invoicerule)
SELECT NEXTVAL('c_bp_group_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       value,
       name,
       description,
       isdefault,
       ad_printcolor_id,
       isconfidentialinfo,
       prioritybase,
       m_pricelist_id,
       po_pricelist_id,
       m_discountschema_id,
       po_discountschema_id,
       creditwatchpercent,
       pricematchtolerance,
       c_dunning_id,
       m_pricingsystem_id,
       po_pricingsystem_id,
       m_freightcost_id,
       paymentrule,
       isprinttax,
       mrp_exclude,
       isprinttaxsales,
       iscustomer,
       socreditstatus,
       bpnameandgreetingstrategy,
       isdeviatingbillbpartner,
       c_paymentterm_id,
       po_paymentterm_id,
       freightcostrule,
       parent_bp_group_id,
       paymentrulepo,
       bill_bpartner_id,
       bill_location_id,
       bill_user_id,
       c_incoterms_id,
       incotermlocation,
       po_incoterms_id,
       po_incotermlocation,
       isautoinvoice,
       invoicerule
FROM migration_data.c_bp_group_Vendor
;


-- Accounting of the new groups, as C_BP_Group's model interceptor would create it: copied from the accounting schema defaults
INSERT INTO c_bp_group_acct (c_bp_group_id, c_acctschema_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                             c_receivable_acct, c_receivable_services_acct, c_prepayment_acct, v_liability_acct, v_liability_services_acct,
                             v_prepayment_acct, paydiscount_exp_acct, paydiscount_rev_acct, writeoff_acct, notinvoicedreceipts_acct,
                             unearnedrevenue_acct, notinvoicedrevenue_acct, notinvoicedreceivables_acct)
SELECT g.c_bp_group_id,
       d.c_acctschema_id,
       d.ad_client_id,
       0,
       'Y',
       NOW(),
       99,
       NOW(),
       99,
       d.c_receivable_acct,
       d.c_receivable_services_acct,
       d.c_prepayment_acct,
       d.v_liability_acct,
       d.v_liability_services_acct,
       d.v_prepayment_acct,
       d.paydiscount_exp_acct,
       d.paydiscount_rev_acct,
       d.writeoff_acct,
       d.notinvoicedreceipts_acct,
       d.unearnedrevenue_acct,
       d.notinvoicedrevenue_acct,
       d.notinvoicedreceivables_acct
FROM c_bp_group g
         JOIN c_acctschema_default d ON d.ad_client_id = g.ad_client_id
WHERE g.value IN (SELECT value FROM migration_data.c_bp_group_Customer
                  UNION
                  SELECT value FROM migration_data.c_bp_group_Vendor)
  AND NOT EXISTS (SELECT 1
                  FROM c_bp_group_acct a
                  WHERE a.c_bp_group_id = g.c_bp_group_id
                    AND a.c_acctschema_id = d.c_acctschema_id)
;
