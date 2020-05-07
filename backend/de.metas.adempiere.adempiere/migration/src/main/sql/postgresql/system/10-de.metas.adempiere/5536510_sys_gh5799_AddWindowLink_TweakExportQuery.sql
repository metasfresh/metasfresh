-- 2019-11-21T08:03:06.840Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           l.m_product_category_id                                                                               m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           l.c_bpartner_id                                                                                       c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
--     WHERE l.m_discountschema_id = 1000000
)

SELECT --
       -- fields which appear in the UI
       l.seqno::text              seqno,
       l.product_value            ProductValue,
       l.m_product_id             m_product_id,
       l.m_product_category_value ProductCategory_Value,
       l.m_product_category_id    m_product_category_id,
       l.c_bpartner_value         BPartnerValue,
       l.c_bpartner_id            c_bpartner_id,
       l.c_taxcategory_id         c_taxcategory_id,
       l.std_addamt               std_addamt,
       l.std_rounding             std_rounding,
       l.c_taxcategory_target_id  c_taxcategory_target_id

FROM exportedLines l

UNION ALL

SELECT --
       -- fields which appear in the UI
       ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10)::text seqno,        -- this select is needed since seqno must be unique
       p.value                                                                                    ProductValue,
       p.m_product_id                                                                             m_product_id,
       NULL                                                                                       ProductCategory_Value,
       NULL                                                                                       m_product_category_id,
       NULL                                                                                       BPartnerValue,
       NULL                                                                                       c_bpartner_id,
       NULL                                                                                       c_taxcategory_id,
       0                                                                                          std_addamt,
       ''C''                                                                                        std_rounding, -- this is the default value
       NULL                                                                                       c_taxcategory_target_id

FROM M_Product p
WHERE 1 = 1
  AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0) FROM exportedLines)
  AND p.IsActive = ''Y''
  AND p.IsSold = ''Y''
  AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
  AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)
--   AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = 1000000))
--   AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = 1000000)

ORDER BY seqno
',Updated=TO_TIMESTAMP('2019-11-21 10:03:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T08:12:16.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           l.m_product_category_id                                                                               m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           l.c_bpartner_id                                                                                       c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
--     WHERE l.m_discountschema_id = 1000000
)

SELECT --
       -- fields which appear in the UI
       l.seqno::text              seqno,
       l.product_value            ProductValue,
       l.m_product_id             m_product_id,
       l.m_product_category_value ProductValue,
       l.m_product_category_id    m_product_category_id,
       l.c_bpartner_value         BPartnerValue,
       l.c_bpartner_id            c_bpartner_id,
       l.c_taxcategory_id         c_taxcategory_id,
       l.std_addamt               std_addamt,
       l.std_rounding             std_rounding,
       l.c_taxcategory_target_id  c_taxcategory_target_id

FROM exportedLines l

UNION ALL

SELECT --
       -- fields which appear in the UI
       ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10)::text seqno,        -- this select is needed since seqno must be unique
       p.value                                                                                    ProductValue,
       p.m_product_id                                                                             m_product_id,
       NULL                                                                                       ProductValue,
       NULL                                                                                       m_product_category_id,
       NULL                                                                                       BPartnerValue,
       NULL                                                                                       c_bpartner_id,
       NULL                                                                                       c_taxcategory_id,
       0                                                                                          std_addamt,
       ''C''                                                                                        std_rounding, -- this is the default value
       NULL                                                                                       c_taxcategory_target_id

FROM M_Product p
WHERE 1 = 1
  AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0) FROM exportedLines)
  AND p.IsActive = ''Y''
  AND p.IsSold = ''Y''
  AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
  AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)
--   AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = 1000000))
--   AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = 1000000)

ORDER BY seqno
',Updated=TO_TIMESTAMP('2019-11-21 10:12:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T08:13:48.522Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           l.m_product_category_id                                                                               m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           l.c_bpartner_id                                                                                       c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
--     WHERE l.m_discountschema_id = 1000000
)

SELECT --
       -- fields which appear in the UI
       l.seqno::text              seqno,
       l.product_value            ProductValue,
       l.m_product_id             m_product_id,
       l.m_product_category_value ProductValue,
       l.m_product_category_id    m_product_category_id,
       l.c_bpartner_value         BPartnerValue,
       l.c_bpartner_id            c_bpartner_id,
       l.c_taxcategory_id         c_taxcategory_id,
       l.std_addamt               std_addamt,
       l.std_rounding             std_rounding,
       l.c_taxcategory_target_id  c_taxcategory_target_id

FROM exportedLines l

UNION ALL

SELECT --
       -- fields which appear in the UI
       ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10)::text seqno,        -- this select is needed since seqno must be unique
       p.value                                                                                    ProductValue,
       p.m_product_id                                                                             m_product_id,
       NULL                                                                                       ProductValue,
       NULL                                                                                       m_product_category_id,
       NULL                                                                                       BPartnerValue,
       NULL                                                                                       c_bpartner_id,
       NULL                                                                                       c_taxcategory_id,
       0                                                                                          std_addamt,
       ''C''                                                                                        std_rounding, -- this is the default value
       NULL                                                                                       c_taxcategory_target_id

FROM M_Product p
WHERE 1 = 1
  AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0) FROM exportedLines)
  AND p.IsActive = ''Y''
  AND p.IsSold = ''Y''
  AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
  AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)
--   AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = 1000000))
--   AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = 1000000)

ORDER BY seqno::numeric
',Updated=TO_TIMESTAMP('2019-11-21 10:13:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T08:24:45.978Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,IsActive,Created,CreatedBy,AD_Org_ID,Updated,UpdatedBy,AD_Element_ID,ColumnName,PrintName,Name,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-21 10:24:45','YYYY-MM-DD HH24:MI:SS'),100,0,TO_TIMESTAMP('2019-11-21 10:24:45','YYYY-MM-DD HH24:MI:SS'),100,577367,'M_Product_Category_Value','Product Category Value','Product Category Value','D')
;

-- 2019-11-21T08:24:45.981Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, Description,PO_PrintName,CommitWarning,WEBUI_NameBrowse,WEBUI_NameNew,Help,PrintName,PO_Description,PO_Help,PO_Name,WEBUI_NameNewBreadcrumb,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.Description,t.PO_PrintName,t.CommitWarning,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.Help,t.PrintName,t.PO_Description,t.PO_Help,t.PO_Name,t.WEBUI_NameNewBreadcrumb,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577367 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-11-21T08:28:55.152Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno                                                                                               seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           l.m_product_category_id                                                                               m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           l.c_bpartner_id                                                                                       c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
--     WHERE l.m_discountschema_id = 1000000
)
SELECT selectedRecords.seqno::text              seqno,
       selectedRecords.ProductValue             ProductValue,
       selectedRecords.m_product_id             m_product_id,
       selectedRecords.M_Product_Category_Value M_Product_Category_Value,
       selectedRecords.m_product_category_id    m_product_category_id,
       selectedRecords.BPartnerValue            BPartnerValue,
       selectedRecords.c_bpartner_id            c_bpartner_id,
       selectedRecords.c_taxcategory_id         c_taxcategory_id,
       selectedRecords.std_addamt               std_addamt,
       selectedRecords.std_rounding             std_rounding,
       selectedRecords.c_taxcategory_target_id  c_taxcategory_target_id
FROM (
         SELECT --
                -- fields which appear in the UI
                l.seqno                    seqno,
                l.product_value            ProductValue,
                l.m_product_id             m_product_id,
                l.m_product_category_value M_Product_Category_Value,
                l.m_product_category_id    m_product_category_id,
                l.c_bpartner_value         BPartnerValue,
                l.c_bpartner_id            c_bpartner_id,
                l.c_taxcategory_id         c_taxcategory_id,
                l.std_addamt               std_addamt,
                l.std_rounding             std_rounding,
                l.c_taxcategory_target_id  c_taxcategory_target_id

         FROM exportedLines l

         UNION ALL

         SELECT --
                -- fields which appear in the UI
                ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10) seqno,        -- this select is needed since seqno must be unique
                p.value                                                                              ProductValue,
                p.m_product_id                                                                       m_product_id,
                NULL                                                                                 M_Product_Category_Value,
                NULL                                                                                 m_product_category_id,
                NULL                                                                                 BPartnerValue,
                NULL                                                                                 c_bpartner_id,
                NULL                                                                                 c_taxcategory_id,
                0                                                                                    std_addamt,
                ''C''                                                                                  std_rounding, -- this is the default value
                NULL                                                                                 c_taxcategory_target_id

         FROM M_Product p
         WHERE 1 = 1
           AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0) FROM exportedLines)
           AND p.IsActive = ''Y''
           AND p.IsSold = ''Y''
  AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
  AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)
--            AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = 1000000))
--            AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = 1000000)
     ) selectedRecords
ORDER BY cast(seqno AS numeric)',Updated=TO_TIMESTAMP('2019-11-21 10:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T08:31:34.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno                                                                                               seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           (SELECT p.name FROM m_product p WHERE p.m_product_id = l.m_product_id)                                m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           (SELECT pc.name FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id)  m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           (SELECT bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                          c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
--     WHERE l.m_discountschema_id = 1000000
)
SELECT selectedRecords.seqno::text              seqno,
       selectedRecords.ProductValue             ProductValue,
       selectedRecords.m_product_id             m_product_id,
       selectedRecords.M_Product_Category_Value M_Product_Category_Value,
       selectedRecords.m_product_category_id    m_product_category_id,
       selectedRecords.BPartnerValue            BPartnerValue,
       selectedRecords.c_bpartner_id            c_bpartner_id,
       selectedRecords.c_taxcategory_id         c_taxcategory_id,
       selectedRecords.std_addamt               std_addamt,
       selectedRecords.std_rounding             std_rounding,
       selectedRecords.c_taxcategory_target_id  c_taxcategory_target_id
FROM (
         SELECT --
                -- fields which appear in the UI
                l.seqno                    seqno,
                l.product_value            ProductValue,
                l.m_product_id             m_product_id,
                l.m_product_category_value M_Product_Category_Value,
                l.m_product_category_id    m_product_category_id,
                l.c_bpartner_value         BPartnerValue,
                l.c_bpartner_id            c_bpartner_id,
                l.c_taxcategory_id         c_taxcategory_id,
                l.std_addamt               std_addamt,
                l.std_rounding             std_rounding,
                l.c_taxcategory_target_id  c_taxcategory_target_id

         FROM exportedLines l

         UNION ALL

         SELECT --
                -- fields which appear in the UI
                ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10) seqno,        -- this select is needed since seqno must be unique
                p.value                                                                              ProductValue,
                p.name                                                                               m_product_id,
                NULL                                                                                 M_Product_Category_Value,
                NULL                                                                                 m_product_category_id,
                NULL                                                                                 BPartnerValue,
                NULL                                                                                 c_bpartner_id,
                NULL                                                                                 c_taxcategory_id,
                0                                                                                    std_addamt,
                ''C''                                                                                  std_rounding, -- this is the default value
                NULL                                                                                 c_taxcategory_target_id

         FROM M_Product p
         WHERE 1 = 1
           AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0) FROM exportedLines)
           AND p.IsActive = ''Y''
           AND p.IsSold = ''Y''
           AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
           AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)
--            AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = 1000000))
--            AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = 1000000)
     ) selectedRecords
ORDER BY cast(seqno AS numeric)
',Updated=TO_TIMESTAMP('2019-11-21 10:31:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T08:47:48.878Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno                                                                                               seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           (SELECT p.name FROM m_product p WHERE p.m_product_id = l.m_product_id)                                product_name,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           (SELECT pc.name FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id)  m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           (SELECT bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                          c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id

    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = @M_DiscountSchema_ID@
--     WHERE l.m_discountschema_id = 1000000
)
SELECT selectedRecords.seqno::text              seqno,
       selectedRecords.ProductValue             ProductValue,
       selectedRecords.product_name             m_product_id,
       selectedRecords.M_Product_Category_Value M_Product_Category_Value,
       selectedRecords.m_product_category_id    m_product_category_id,
       selectedRecords.BPartnerValue            BPartnerValue,
       selectedRecords.c_bpartner_id            c_bpartner_id,
       selectedRecords.c_taxcategory_id         c_taxcategory_id,
       selectedRecords.std_addamt               std_addamt,
       selectedRecords.std_rounding             std_rounding,
       selectedRecords.c_taxcategory_target_id  c_taxcategory_target_id
FROM (
         SELECT --
                -- fields which appear in the UI
                l.seqno                    seqno,
                l.product_value            ProductValue,
                l.product_name             product_name,
                l.m_product_category_value M_Product_Category_Value,
                l.m_product_category_id    m_product_category_id,
                l.c_bpartner_value         BPartnerValue,
                l.c_bpartner_id            c_bpartner_id,
                l.c_taxcategory_id         c_taxcategory_id,
                l.std_addamt               std_addamt,
                l.std_rounding             std_rounding,
                l.c_taxcategory_target_id  c_taxcategory_target_id

         FROM exportedLines l

         UNION ALL

         SELECT --
                -- fields which appear in the UI
                ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10) seqno,        -- this select is needed since seqno must be unique
                p.value                                                                              ProductValue,
                p.name                                                                               product_name,
                NULL                                                                                 M_Product_Category_Value,
                NULL                                                                                 m_product_category_id,
                NULL                                                                                 BPartnerValue,
                NULL                                                                                 c_bpartner_id,
                NULL                                                                                 c_taxcategory_id,
                0                                                                                    std_addamt,
                ''C''                                                                                  std_rounding, -- this is the default value
                NULL                                                                                 c_taxcategory_target_id

         FROM M_Product p
         WHERE 1 = 1
           AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0) FROM exportedLines)
           AND p.IsActive = ''Y''
           AND p.IsSold = ''Y''
           AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@))
           AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = @M_DiscountSchema_ID@)
--            AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = 1000000))
--            AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = 1000000)
     ) selectedRecords
ORDER BY cast(seqno AS numeric)',Updated=TO_TIMESTAMP('2019-11-21 10:47:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541210
;

-- 2019-11-21T08:54:52.408Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET IsActive='N',Updated=TO_TIMESTAMP('2019-11-21 10:54:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=405
;

-- 2019-11-21T08:55:01.940Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=540152,Updated=TO_TIMESTAMP('2019-11-21 10:55:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=591817
;


-- 2019-11-21T08:59:58.094Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Produktkategorie Nummer', IsTranslated='Y', Name='Produktkategorie Nummer',Updated=TO_TIMESTAMP('2019-11-21 10:59:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=577367
;

-- 2019-11-21T08:59:58.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577367,'de_CH')
;

-- 2019-11-21T09:00:03Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET PrintName='Produktkategorie Nummer', IsTranslated='Y', Name='Produktkategorie Nummer',Updated=TO_TIMESTAMP('2019-11-21 11:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=577367
;

-- 2019-11-21T09:00:03.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577367,'de_DE')
;

-- 2019-11-21T09:00:03.010Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(577367,'de_DE')
;

-- 2019-11-21T09:00:03.020Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='M_Product_Category_Value', Name='Produktkategorie Nummer', Description=NULL, Help=NULL WHERE AD_Element_ID=577367
;

-- 2019-11-21T09:00:03.022Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Category_Value', Name='Produktkategorie Nummer', Description=NULL, Help=NULL, AD_Element_ID=577367 WHERE UPPER(ColumnName)='M_PRODUCT_CATEGORY_VALUE' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-11-21T09:00:03.024Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='M_Product_Category_Value', Name='Produktkategorie Nummer', Description=NULL, Help=NULL WHERE AD_Element_ID=577367 AND IsCentrallyMaintained='Y'
;

-- 2019-11-21T09:00:03.025Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Produktkategorie Nummer', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=577367) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 577367)
;

-- 2019-11-21T09:00:03.036Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Produktkategorie Nummer', Name='Produktkategorie Nummer' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=577367)
;

-- 2019-11-21T09:00:03.039Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Produktkategorie Nummer', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 577367
;

-- 2019-11-21T09:00:03.042Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Produktkategorie Nummer', Description=NULL, Help=NULL WHERE AD_Element_ID = 577367
;

-- 2019-11-21T09:00:03.044Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Produktkategorie Nummer', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 577367
;

-- 2019-11-21T09:00:05.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2019-11-21 11:00:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=577367
;

-- 2019-11-21T09:00:05.211Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577367,'en_US')
;


-- 2019-11-21T09:02:03.263Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-21 11:02:03','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-21 11:02:03','YYYY-MM-DD HH24:MI:SS'),100,541076,'T','M_PriceListSchema',0,'D')
;

-- 2019-11-21T09:02:03.265Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541076 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-21T09:02:43.869Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541076,6581,0,'Y',TO_TIMESTAMP('2019-11-21 11:02:43','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-21 11:02:43','YYYY-MM-DD HH24:MI:SS'),'N',337,100,475,0,'D')
;

-- 2019-11-21T09:07:33.951Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,IsActive,Created,CreatedBy,IsOrderByValue,Updated,UpdatedBy,AD_Reference_ID,ValidationType,Name,AD_Org_ID,EntityType) VALUES (0,'Y',TO_TIMESTAMP('2019-11-21 11:07:33','YYYY-MM-DD HH24:MI:SS'),100,'N',TO_TIMESTAMP('2019-11-21 11:07:33','YYYY-MM-DD HH24:MI:SS'),100,541077,'T','M_PriceListSchemaLine -> M_PriceListSchema',0,'D')
;

-- 2019-11-21T09:07:33.953Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Help,Name,Description, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Help,t.Name,t.Description, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541077 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2019-11-21T09:09:02.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Reference_ID,AD_Key,WhereClause,AD_Client_ID,IsActive,Created,CreatedBy,Updated,IsValueDisplayed,AD_Window_ID,UpdatedBy,AD_Table_ID,AD_Org_ID,EntityType) VALUES (541077,6620,'M_DiscountSchema_ID=@M_DiscountSchema_ID/-1@',0,'Y',TO_TIMESTAMP('2019-11-21 11:09:02','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2019-11-21 11:09:02','YYYY-MM-DD HH24:MI:SS'),'N',540756,100,477,0,'D')
;

-- 2019-11-21T09:09:25.958Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (Created,CreatedBy,IsActive,AD_Client_ID,Updated,UpdatedBy,AD_RelationType_ID,IsTableRecordIdTarget,IsDirected,AD_Reference_Source_ID,Name,AD_Org_ID,EntityType) VALUES (TO_TIMESTAMP('2019-11-21 11:09:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',0,TO_TIMESTAMP('2019-11-21 11:09:25','YYYY-MM-DD HH24:MI:SS'),100,540232,'N','N',541076,'PriceListSchema -> PriceListSchemaLine',0,'D')
;

-- 2019-11-21T09:09:48.732Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=541077, Name='M_PriceListSchema -> M_PriceListSchemaLine',Updated=TO_TIMESTAMP('2019-11-21 11:09:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540232
;

