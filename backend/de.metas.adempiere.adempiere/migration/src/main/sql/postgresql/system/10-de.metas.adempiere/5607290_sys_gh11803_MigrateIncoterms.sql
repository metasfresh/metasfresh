INSERT INTO c_incoterms (ad_client_id, ad_org_id, created, createdby, c_incoterms_id, isactive, description, name, updated, updatedby, value)
    (
        SELECT ad_client_id, ad_org_id, created, '99', nextval('C_INCOTERMS_SEQ'), isactive, null, name, now(), '99', value

        FROM ad_ref_list where ad_reference_id = 501599 and value in ('DAF', 'DES', 'DEQ', 'DDU')
    )
;

INSERT INTO c_incoterms_trl (ad_client_id, ad_org_id, created, createdby, c_incoterms_trl_id, isactive, description, name, updated, updatedby, c_incoterms_id, ad_language)
    (
        SELECT ad_client_id, ad_org_id, created, '99', nextval('C_INCOTERMS_TRL_SEQ'), isactive, description, name, now(), '99',  (select c_incoterms_id from c_incoterms where value ILIKE substring(ad_ref_list_trl.name, 0, position(' - ' in  ad_ref_list_trl.name))), ad_language

        FROM ad_ref_list_trl where ad_ref_list_id in  (select ad_ref_list_id from ad_ref_list where ad_reference_id = 501599 and value in ('DAF', 'DES', 'DEQ', 'DDU'))
    )
;