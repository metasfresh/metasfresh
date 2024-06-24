-- 2022-04-04T14:20:47.445Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT p.value as "Produkt", bp.value as "Geschäftspartner", bpg.Value as "Geschäftspartnergruppe", ps.value as "Preissystem", c.countryCode as "Land", cur.iso_code as "Währung", cp.pricestd as "Standardpreis", TO_CHAR(cp.validfrom, ''dd.mm.yyyy'') AS "Gültig ab", TO_CHAR(cp.validTo, ''dd.mm.yyyy'')   AS "Gültig bis", uom.name as "Maßeinheit", tc.Name as "Steuerkategorie", cp.invoicableqtybasedon as "Abr. Menge basiert auf" FROM c_campaign_price cp LEFT JOIN m_product p ON cp.m_product_id = p.m_product_id LEFT JOIN c_bpartner bp ON cp.c_bpartner_id = bp.c_bpartner_id LEFT JOIN c_bp_group bpg ON cp.c_bp_group_id = bpg.c_bp_group_id LEFT JOIN m_pricingsystem ps ON cp.m_pricingsystem_id = ps.m_pricingsystem_id LEFT JOIN c_country c ON cp.c_country_id = c.c_country_id LEFT JOIN c_currency cur ON cp.c_currency_id = cur.c_currency_id LEFT JOIN c_uom uom ON cp.c_uom_id = uom.c_uom_id LEFT JOIN c_taxCategory tc ON cp.c_taxcategory_id = tc.c_taxcategory_id WHERE cp.validTo >= ''@Date@''::DATE AND cp.isactive = ''Y'' ORDER BY p.value, cp.validTo;',Updated=TO_TIMESTAMP('2022-04-04 17:20:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585034
;

UPDATE AD_ImpFormat_Row
SET dataformat='dd.MM.yyyy',
    updated=TO_TIMESTAMP('2022-04-29 12:20:47', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby=100
WHERE AD_ImpFormat_Row_ID = 541720
;

UPDATE AD_ImpFormat_Row
SET dataformat='dd.MM.yyyy',
    updated=TO_TIMESTAMP('2022-04-29 12:20:47', 'YYYY-MM-DD HH24:MI:SS'),
    updatedby=100
WHERE AD_ImpFormat_Row_ID = 541721
;

-- 2022-04-29T10:24:00.348Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-04-29 13:24:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582661
;

-- 2022-04-29T10:24:36.572Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='N', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-04-29 13:24:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582660
;
