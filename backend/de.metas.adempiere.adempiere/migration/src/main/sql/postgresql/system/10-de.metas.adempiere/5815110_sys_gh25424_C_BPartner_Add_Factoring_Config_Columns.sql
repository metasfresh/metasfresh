-- IDs allocated from idserver.metas.de on 2026-07-21:
--   AD_MigrationScript 5815110
--   AD_Element         585119 (FactoringContractNo)
--   AD_Element         585120 (FactoringClientAccountId)
--   AD_Column          592972 (C_BPartner.FactoringContractNo)
--   AD_Column          592973 (C_BPartner.FactoringClientAccountId)

-- Add factoring configuration columns to C_BPartner table

ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS FactoringContractNo VARCHAR(20);
ALTER TABLE C_BPartner ADD COLUMN IF NOT EXISTS FactoringClientAccountId VARCHAR(20);

INSERT INTO ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, name, printname, description, entitytype)
VALUES (585119 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'FactoringContractNo', 'Vertragsnummer', 'Vertragsnummer', 'Vertragsnummer des Factoring-Vertrags', 'D')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, name, printname, description, entitytype)
VALUES (585120 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'FactoringClientAccountId', 'Kundenkontonummer', 'Kundenkontonummer', 'Kundenkontonummer bei dem Factor', 'D')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, istranslated)
VALUES (585119 /*From ID Server*/, 'en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Factoring Contract No.', 'Factoring Contract No.', 'Factoring contract number', 'Y')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, istranslated)
VALUES (585119 /*From ID Server*/, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Vertragsnummer', 'Vertragsnummer', 'Vertragsnummer des Factoring-Vertrags', 'Y')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, istranslated)
VALUES (585119 /*From ID Server*/, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Vertragsnummer', 'Vertragsnummer', 'Vertragsnummer des Factoring-Vertrags', 'Y')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, istranslated)
VALUES (585120 /*From ID Server*/, 'en_US', 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Factoring Client Account ID', 'Factoring Client Account ID', 'Factoring client account ID at the factor', 'Y')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, istranslated)
VALUES (585120 /*From ID Server*/, 'de_DE', 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Kundenkontonummer', 'Kundenkontonummer', 'Kundenkontonummer bei dem Factor', 'Y')
ON CONFLICT DO NOTHING;

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, istranslated)
VALUES (585120 /*From ID Server*/, 'de_CH', 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, TO_TIMESTAMP('2026-07-21 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100, 'Kundenkontonummer', 'Kundenkontonummer', 'Kundenkontonummer bei dem Factor', 'Y')
ON CONFLICT DO NOTHING;

INSERT INTO ad_column (ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, ad_element_id, ad_table_id, ad_reference_id, columnname, fieldlength, version, entitytype, iskey, ismandatory, isupdateable, isidentifier, isselectioncolumn, istranslated, issyncdatabase, isalwaysupdateable, seqno, personaldatacategory)
VALUES (592972 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2026-07-21 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 100, 585119 /*From ID Server*/, 291, 10, 'FactoringContractNo', 20, 0, 'D', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 0, 'NP')
ON CONFLICT DO NOTHING;

INSERT INTO ad_column (ad_column_id, ad_client_id, ad_org_id, isactive, created, updated, createdby, updatedby, ad_element_id, ad_table_id, ad_reference_id, columnname, fieldlength, version, entitytype, iskey, ismandatory, isupdateable, isidentifier, isselectioncolumn, istranslated, issyncdatabase, isalwaysupdateable, seqno, personaldatacategory)
VALUES (592973 /*From ID Server*/, 0, 0, 'Y', TO_TIMESTAMP('2026-07-21 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), TO_TIMESTAMP('2026-07-21 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100, 100, 585120 /*From ID Server*/, 291, 10, 'FactoringClientAccountId', 20, 0, 'D', 'N', 'N', 'Y', 'N', 'N', 'N', 'Y', 'N', 0, 'NP')
ON CONFLICT DO NOTHING;
