-- Follow-up refinements for the M_HU_PI_GRAI window based on code-review findings.
-- Resolving IDs (queried before authoring):
--   AD_Field 780648 = GRAI_CompanyPrefix, 780649 = GRAI_AssetType
--   AD_UI_Element 651948 = GRAI_CompanyPrefix, 651949 = GRAI_AssetType
--   AD_Tab 549285, AD_UI_ElementGroup 555414

-- ============================================================
-- 1. GRAI_CompanyPrefix (592691): IsIdentifier='Y', SeqNo=10
-- ============================================================
UPDATE AD_Column
SET    IsIdentifier = 'Y',
       SeqNo        = 10,
       Updated      = TO_TIMESTAMP('2026-06-02 12:00:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Column_ID = 592691;

-- ============================================================
-- 2. GRAI_AssetType (592692): IsIdentifier='Y', SeqNo=20
-- ============================================================
UPDATE AD_Column
SET    IsIdentifier = 'Y',
       SeqNo        = 20,
       Updated      = TO_TIMESTAMP('2026-06-02 12:00:10', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy    = 100
WHERE  AD_Column_ID = 592692;

-- ============================================================
-- 3. All three columns: IsSelectionColumn='Y'
-- ============================================================
UPDATE AD_Column
SET    IsSelectionColumn = 'Y',
       Updated           = TO_TIMESTAMP('2026-06-02 12:00:20', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy         = 100
WHERE  AD_Column_ID IN (592690, 592691, 592692);

-- ============================================================
-- 4. AD_Field SortNo: GRAI_CompanyPrefix=1, GRAI_AssetType=2
-- ============================================================
UPDATE AD_Field
SET    SortNo    = 1,
       Updated   = TO_TIMESTAMP('2026-06-02 12:00:30', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 780648;  -- GRAI_CompanyPrefix

UPDATE AD_Field
SET    SortNo    = 2,
       Updated   = TO_TIMESTAMP('2026-06-02 12:00:31', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_Field_ID = 780649;  -- GRAI_AssetType

-- ============================================================
-- 5. AD_Tab: IsSingleRow='N' — open grid-first
-- ============================================================
UPDATE AD_Tab
SET    IsSingleRow = 'N',
       Updated     = TO_TIMESTAMP('2026-06-02 12:00:40', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy   = 100
WHERE  AD_Tab_ID = 549285;

-- ============================================================
-- 6. AD_UI_Element WidgetSize='S' for GRAI_CompanyPrefix and GRAI_AssetType
-- ============================================================
UPDATE AD_UI_Element
SET    WidgetSize = 'S',
       Updated    = TO_TIMESTAMP('2026-06-02 12:00:50', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy  = 100
WHERE  AD_UI_Element_ID IN (651948, 651949);  -- GRAI_CompanyPrefix, GRAI_AssetType

-- ============================================================
-- 7. AD_UI_ElementGroup: rename 'default' → 'org'
-- ============================================================
UPDATE AD_UI_ElementGroup
SET    Name      = 'org',
       Updated   = TO_TIMESTAMP('2026-06-02 12:01:00', 'YYYY-MM-DD HH24:MI:SS'),
       UpdatedBy = 100
WHERE  AD_UI_ElementGroup_ID = 555414;
