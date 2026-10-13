-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/views/Fact_Acct_Transactions_View.sql
-- AC9: add IsAccountOverridden derived marker to Fact_Acct_Transactions_View.
-- A fact line is 'Y' when a C_Invoice_Acct override row caused the posted account,
-- i.e. a matching C_Invoice_Acct row (same acct-schema, invoice, optional line, same element value, IsActive='Y') exists.
-- No posting-logic change; derived at read time via EXISTS sub-query.

-- ============================================================
-- 1. Recreate view with new IsAccountOverridden column
-- ============================================================
DROP VIEW IF EXISTS Fact_Acct_Transactions_View$new;

CREATE OR REPLACE VIEW Fact_Acct_Transactions_View$new AS
SELECT fact.fact_acct_id,
       fact.ad_client_id,
       fact.ad_org_id,
       fact.isactive,
       fact.created,
       fact.createdby,
       fact.updated,
       fact.updatedby,
       fact.c_acctschema_id,
       fact.account_id,
       fact.accountconceptualname,
       fact.C_CostClassification_ID,
       fact.C_CostClassification_Category_ID,
       fact.datetrx,
       fact.dateacct,
       fact.c_period_id,
       fact.ad_table_id,
       fact.record_id,
       fact.line_id,
       fact.gl_category_id,
       fact.gl_budget_id,
       fact.c_tax_id,
       fact.m_locator_id,
       fact.postingtype,
       fact.c_currency_id,
       fact.amtsourcedr,
       fact.amtsourcecr,
       fact.amtacctdr,
       fact.amtacctcr,
       fact.c_uom_id,
       fact.qty,
       fact.m_product_id,
       fact.c_bpartner_id,
       fact.ad_orgtrx_id,
       fact.c_locfrom_id,
       fact.c_locto_id,
       fact.c_salesregion_id,
       fact.c_project_id,
       fact.c_campaign_id,
       fact.c_activity_id,
       fact.user1_id,
       fact.user2_id,
       fact.description,
       fact.a_asset_id,
       fact.c_subacct_id,
       fact.userelement1_id,
       fact.userelement2_id,
       fact.c_projectphase_id,
       fact.c_projecttask_id,
       fact.currencyrate,
       fact.docstatus,
       fact.poreference,
       fact.subline_id,
       fact.documentno,
       fact.c_doctype_id,
       fact.docbasetype,
       fact.vatcode,
       fact.counterpart_fact_acct_id,
       --NEW_DAWN_MERGE_ARTIFACT
       -- fact.userelementnumber1,
       -- fact.userelementnumber2,
       fact.userelementstring1,
       fact.userelementstring2,
       fact.userelementstring3,
       fact.userelementstring4,
       fact.userelementstring5,
       fact.userelementstring6,
       fact.userelementstring7,
       --NEW_DAWN_MERGE_ARTIFACT
       -- fact.userElementDate1,
       -- fact.userElementDate2,
       fact.C_OrderSO_ID,
       acctbalance(fact.account_id, fact.amtacctdr, fact.amtacctcr) AS balance,
       fact.m_costelement_id,
       fact.c_bpartner2_id,
       fact.c_bpartner_location_id,
       fact.OpenItemKey,
       fact.OI_TrxType,
       fact.IsOpenItemsReconciled,
       fact.OI_OpenAmount,
       fact.OI_OpenAmountSource,
       CASE WHEN EXISTS (
           SELECT 1
           FROM C_Invoice_Acct ia
           WHERE ia.IsActive = 'Y'
             AND ia.C_AcctSchema_ID = fact.C_AcctSchema_ID
             AND ia.C_Invoice_ID = fact.Record_ID
             AND fact.AD_Table_ID = 318
             AND (ia.C_InvoiceLine_ID = fact.Line_ID OR ia.C_InvoiceLine_ID IS NULL)
             AND ia.C_ElementValue_ID = fact.Account_ID
       ) THEN 'Y' ELSE 'N' END AS IsAccountOverridden
FROM fact_acct fact
;

SELECT db_alter_view(
    'Fact_Acct_Transactions_View',
    (SELECT view_definition
     FROM information_schema.views
     WHERE lower(table_name) = lower('Fact_Acct_Transactions_View$new'))
);

DROP VIEW IF EXISTS Fact_Acct_Transactions_View$new;

-- ============================================================
-- 2. AD_Element 585021 — IsAccountOverridden
-- ============================================================
INSERT INTO AD_Element
    (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES
    (585021 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-06-18 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     'IsAccountOverridden',
     'Konto überschrieben',
     'Konto überschrieben',
     'Das Konto dieser Buchungszeile stammt aus einer Rechnungs-Konto-Überschreibung.',
     'Zeigt an, ob das Sachkonto dieser Buchungszeile aus einer manuellen Konto-Überschreibung auf der Rechnung (C_Invoice_Acct) resultiert, anstatt aus der Standard-Produktkontierung abgeleitet zu sein.',
     'D')
;

-- Seed _Trl rows for all active system languages (copies base DE text)
INSERT INTO AD_Element_Trl
    (AD_Language, AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, PrintName, Description, Help, IsTranslated, WebUI_NameBrowse, WebUI_NameNew, WebUI_NameNewBreadcrumb)
SELECT
    l.AD_Language,
    585021,
    0,
    0,
    'Y',
    TO_TIMESTAMP('2026-06-18 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    TO_TIMESTAMP('2026-06-18 10:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    e.Name,
    e.PrintName,
    e.Description,
    e.Help,
    'N',
    NULL, NULL, NULL
FROM AD_Language l, AD_Element e
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND e.AD_Element_ID = 585021
  AND NOT EXISTS (
      SELECT 1 FROM AD_Element_Trl tt
      WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = 585021
  )
;

-- Override en_US translation
UPDATE AD_Element_Trl
SET Name            = 'Account Overridden',
    PrintName       = 'Account Overridden',
    Description     = 'The account on this accounting fact line originated from an invoice-account override.',
    Help            = 'Indicates whether the GL account on this accounting fact line resulted from a manual account override on the invoice (C_Invoice_Acct), rather than being derived from the standard product accounting.',
    IsTranslated    = 'Y',
    Updated         = TO_TIMESTAMP('2026-06-18 10:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy       = 100
WHERE AD_Language = 'en_US'
  AND AD_Element_ID = 585021
;

-- Mark de_DE and de_CH as translated (same text as base)
UPDATE AD_Element_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-06-18 10:00:18', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language IN ('de_DE', 'de_CH')
  AND AD_Element_ID = 585021
;

-- ============================================================
-- 3. AD_Column 592835 — IsAccountOverridden on Fact_Acct_Transactions_View
-- ============================================================
INSERT INTO AD_Column
    (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Table_ID, AD_Element_ID, ColumnName, Name, Description, Help,
     AD_Reference_ID, FieldLength, IsMandatory, IsUpdateable, IsKey, IsParent,
     IsTranslated, IsEncrypted, IsAllowLogging,
     EntityType, Version, PersonalDataCategory)
VALUES
    (592835 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-06-18 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     541485,    -- AD_Table_ID for Fact_Acct_Transactions_View
     585021,    -- AD_Element_ID
     'IsAccountOverridden',
     'Konto überschrieben',
     'Das Konto dieser Buchungszeile stammt aus einer Rechnungs-Konto-Überschreibung.',
     'Zeigt an, ob das Sachkonto dieser Buchungszeile aus einer manuellen Konto-Überschreibung auf der Rechnung (C_Invoice_Acct) resultiert, anstatt aus der Standard-Produktkontierung abgeleitet zu sein.',
     20,  -- AD_Reference_ID: Yes-No
     1,   -- FieldLength
     'N', -- IsMandatory
     'N', -- IsUpdateable (view column, read-only)
     'N', -- IsKey
     'N', -- IsParent
     'N', -- IsTranslated
     'N', -- IsEncrypted
     'Y', -- IsAllowLogging
     'D', -- EntityType
     0,   -- Version
     'NP' -- PersonalDataCategory: Not Personal
    )
;

-- Seed AD_Column_Trl rows
INSERT INTO AD_Column_Trl
    (AD_Language, AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, IsTranslated)
SELECT
    l.AD_Language,
    592835,
    0,
    0,
    'Y',
    TO_TIMESTAMP('2026-06-18 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    TO_TIMESTAMP('2026-06-18 10:01:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    c.Name,
    'N'
FROM AD_Language l, AD_Column c
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND c.AD_Column_ID = 592835
  AND NOT EXISTS (
      SELECT 1 FROM AD_Column_Trl tt
      WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = 592835
  )
;

-- ============================================================
-- 4. AD_Field 781213 — on CORE accounting tab 242 (window 162)
-- ============================================================
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, AD_Column_ID, Name, Description, Help,
     IsDisplayed, IsMandatory, IsReadOnly, IsEncrypted,
     SeqNo, EntityType)
VALUES
    (781213 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:02:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-06-18 10:02:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     242,   -- AD_Tab_ID: Buchführung tab (CORE accounting window 162)
     592835,   -- AD_Column_ID: IsAccountOverridden
     'Konto überschrieben',
     'Das Konto dieser Buchungszeile stammt aus einer Rechnungs-Konto-Überschreibung.',
     'Zeigt an, ob das Sachkonto dieser Buchungszeile aus einer manuellen Konto-Überschreibung auf der Rechnung (C_Invoice_Acct) resultiert, anstatt aus der Standard-Produktkontierung abgeleitet zu sein.',
     'Y',  -- IsDisplayed
     'N',  -- IsMandatory
     'Y',  -- IsReadOnly (view-derived marker)
     'N',  -- IsEncrypted
     630,  -- SeqNo (after last field at 620)
     'D'   -- EntityType
    )
;

-- Seed AD_Field_Trl rows
INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, Name, Description, Help, IsTranslated)
SELECT
    l.AD_Language,
    781213,
    0,
    0,
    'Y',
    TO_TIMESTAMP('2026-06-18 10:02:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    TO_TIMESTAMP('2026-06-18 10:02:00', 'YYYY-MM-DD HH24:MI:SS'),
    100,
    f.Name,
    f.Description,
    f.Help,
    'N'
FROM AD_Language l, AD_Field f
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND f.AD_Field_ID = 781213
  AND NOT EXISTS (
      SELECT 1 FROM AD_Field_Trl tt
      WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781213
  )
;

-- ============================================================
-- 5. AD_UI_Element 652325 — type 'F', paired with AD_Field 781213
-- Place in 'default' group (540304) on CORE tab 242, SeqNo=80 (group max was 70)
-- Grid: not shown (override marker, informational)
-- ============================================================
INSERT INTO AD_UI_Element
    (AD_UI_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     AD_Tab_ID, AD_Field_ID, AD_UI_ElementGroup_ID, Name,
     SeqNo, IsDisplayed, IsDisplayedGrid, SeqNoGrid,
     UIStyle, AD_UI_ElementType)
VALUES
    (652325 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:02:30', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-06-18 10:02:30', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     242,              -- AD_Tab_ID (CORE accounting window 162, "Buchführung")
     781213,              -- AD_Field_ID
     540304,              -- AD_UI_ElementGroup_ID: 'default' group (CORE tab 242)
     'IsAccountOverridden', -- Name
     80,                  -- SeqNo (group 540304 max was 70)
     'Y',                 -- IsDisplayed
     'Y',                 -- IsDisplayedGrid (AC9: scannable in the accounting-facts grid)
     75,                  -- SeqNoGrid (after PostingType grid 70, before Periode 80)
     NULL,                -- UIStyle
     'F'                  -- AD_UI_ElementType: Field
    )
;

-- ============================================================
-- 6. Translation propagation
-- ============================================================
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585021, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585021, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585021, 'en_US');
SELECT update_FieldTranslation_From_AD_Name_Element(585021);

-- Durable en_US/de propagation: update_FieldTranslation_From_AD_Name_Element has a
-- timestamp guard (f_trl.updated <> e_trl.updated) that no-ops when the field _Trl and
-- element _Trl share a timestamp (element 585021 + field 781213 are created in this same
-- script). Copy the translated element texts into the field _Trl directly to be safe.
UPDATE AD_Field_Trl ft
SET    Name=et.Name, Description=et.Description, Help=et.Help, IsTranslated=et.IsTranslated,
       Updated=TO_TIMESTAMP('2026-06-19 10:05:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM   AD_Element_Trl et
WHERE  et.AD_Element_ID=585021 /*From ID Server*/
  AND  ft.AD_Field_ID=781213 /*From ID Server*/
  AND  ft.AD_Language=et.AD_Language
  AND  et.IsTranslated='Y';

-- Rebuild AD_Element_Link for field 781213 (CORE accounting window 162)
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781213;
INSERT INTO AD_Element_Link (AD_Element_Link_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, AD_Element_ID, AD_Field_ID, AD_Window_ID)
VALUES
    (628441 /*From ID Server*/, 0, 0, 'Y',
     TO_TIMESTAMP('2026-06-18 10:03:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     TO_TIMESTAMP('2026-06-18 10:03:00', 'YYYY-MM-DD HH24:MI:SS'),
     100,
     585021,
     781213,
     162)
;

