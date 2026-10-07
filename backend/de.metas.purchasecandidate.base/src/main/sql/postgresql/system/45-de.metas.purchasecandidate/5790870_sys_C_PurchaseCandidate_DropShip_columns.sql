-- Run mode: SWING_CLIENT

-- Column: C_PurchaseCandidate.IsDropShip
-- 2026-03-04T13:43:54.693Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, DefaultValue, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated,
                       IsUpdateable, IsUseDocSequence, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592164, 2466, 0, 20, 540861, 'XX', 'IsDropShip', TO_TIMESTAMP('2026-03-04 13:43:54.419000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'N', 'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', 'de.metas.purchasecandidate', 0, 1,
        'Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Abweichende Lieferadresse', 0, 0,
        TO_TIMESTAMP('2026-03-04 13:43:54.419000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-04T13:43:54.701Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592164
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-04T13:43:54.727Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(2466)
;

-- 2026-03-04T13:43:56.817Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN IsDropShip CHAR(1) DEFAULT ''N'' CHECK (IsDropShip IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_PurchaseCandidate.DropShip_BPartner_ID
-- 2026-03-04T13:45:17.555Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, Help, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated,
                       IsUpdateable, IsUseDocSequence, MandatoryLogic, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592165, 53458, 0, 30, 541252, 540861, 'XX', 'DropShip_BPartner_ID', TO_TIMESTAMP('2026-03-04 13:45:17.319000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'Business Partner to ship to', 'de.metas.purchasecandidate', 0, 10, 'If empty the business partner will be shipped to.', 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', '@IsDropShip/''N''@=''Y''', 0, 'Lieferempfänger', 0, 0, TO_TIMESTAMP('2026-03-04 13:45:17.319000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-04T13:45:17.558Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592165
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-04T13:45:17.561Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(53458)
;

-- 2026-03-04T13:45:22.936Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN DropShip_BPartner_ID NUMERIC(10)')
;

-- 2026-03-04T13:45:23.069Z
ALTER TABLE C_PurchaseCandidate
    ADD CONSTRAINT DropShipBPartner_CPurchaseCandidate FOREIGN KEY (DropShip_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PurchaseCandidate.DropShip_Location_ID
-- 2026-03-04T13:46:03.509Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated,
                       IsUpdateable, IsUseDocSequence, MandatoryLogic, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592166, 53459, 0, 30, 159, 540861, 'XX', 'DropShip_Location_ID', TO_TIMESTAMP('2026-03-04 13:46:03.285000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', 'Business Partner Location for shipping to', 'de.metas.purchasecandidate', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N',
        'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', '@IsDropShip/''N''@=''Y''', 0, 'Lieferadresse', 0, 0, TO_TIMESTAMP('2026-03-04 13:46:03.285000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-04T13:46:03.512Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592166
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-04T13:46:03.516Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(53459)
;

-- 2026-03-04T13:46:09.078Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN DropShip_Location_ID NUMERIC(10)')
;

-- 2026-03-04T13:46:09.222Z
ALTER TABLE C_PurchaseCandidate
    ADD CONSTRAINT DropShipLocation_CPurchaseCandidate FOREIGN KEY (DropShip_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PurchaseCandidate.DropShip_User_ID
-- 2026-03-04T13:46:28.900Z
INSERT INTO AD_Column (AD_Client_ID, AD_Column_ID, AD_Element_ID, AD_Org_ID, AD_Reference_ID, AD_Reference_Value_ID, AD_Table_ID, CloningStrategy, ColumnName, Created, CreatedBy, DDL_NoForeignKey, Description, EntityType, FacetFilterSeqNo, FieldLength, IsActive, IsAdvancedText, IsAllowLogging, IsAlwaysUpdateable, IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter, IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin, IsIdentifier, IsKey, IsLazyLoading, IsMandatory, IsParent, IsRestAPICustomColumn, IsSelectionColumn, IsShowFilterInactiveValues, IsShowFilterIncrementButtons, IsShowFilterInline, IsStaleable, IsSyncDatabase, IsTranslated,
                       IsUpdateable, IsUseDocSequence, MandatoryLogic, MaxFacetsToFetch, Name, SelectionColumnSeqNo, SeqNo, Updated, UpdatedBy, Version)
VALUES (0, 592167, 53460, 0, 30, 110, 540861, 'XX', 'DropShip_User_ID', TO_TIMESTAMP('2026-03-04 13:46:28.763000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'N', '', 'de.metas.purchasecandidate', 0, 10, 'Y', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'Y', 'N',
        '@IsDropShip/''N''@=''Y''', 0, 'Lieferkontakt', 0, 0, TO_TIMESTAMP('2026-03-04 13:46:28.763000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 0)
;

-- 2026-03-04T13:46:28.903Z
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Column_ID,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Column t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 592167
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- 2026-03-04T13:46:28.992Z
/* DDL */

SELECT update_Column_Translation_From_AD_Element(53460)
;

-- 2026-03-04T13:46:30.897Z
/* DDL */

SELECT public.db_alter_table('C_PurchaseCandidate', 'ALTER TABLE public.C_PurchaseCandidate ADD COLUMN DropShip_User_ID NUMERIC(10)')
;

-- 2026-03-04T13:46:31.004Z
ALTER TABLE C_PurchaseCandidate
    ADD CONSTRAINT DropShipUser_CPurchaseCandidate FOREIGN KEY (DropShip_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Abweichende Lieferadresse
-- Column: C_PurchaseCandidate.IsDropShip
-- 2026-03-04T13:52:10.643Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, EntityType, FacetFilterSeqNo, Help, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch, Name,
                      SelectionColumnSeqNo, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592164, 774822, 0, 540894, 0, TO_TIMESTAMP('2026-03-04 13:52:10.495000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert', 0, 'D', 0,
        'Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Abweichende Lieferadresse', 0, 0, 340, 0, 1, 1,
        TO_TIMESTAMP('2026-03-04 13:52:10.495000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-04T13:52:10.647Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774822
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-03-04T13:52:10.649Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(2466)
;

-- 2026-03-04T13:52:10.668Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774822
;

-- 2026-03-04T13:52:10.672Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774822)
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Lieferempfänger
-- Column: C_PurchaseCandidate.DropShip_BPartner_ID
-- 2026-03-04T13:52:49.281Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, DisplayLogic, EntityType, FacetFilterSeqNo, Help, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch,
                      Name, SelectionColumnSeqNo, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592165, 774823, 0, 540894, 0, TO_TIMESTAMP('2026-03-04 13:52:49.160000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Business Partner to ship to', 0, '@IsDropShip/''N''@=''Y''', 'D', 0, 'If empty the business partner will be shipped to.', 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Lieferempfänger', 0, 0, 350, 0, 1, 1,
        TO_TIMESTAMP('2026-03-04 13:52:49.160000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-04T13:52:49.284Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774823
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-03-04T13:52:49.286Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(53458)
;

-- 2026-03-04T13:52:49.294Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774823
;

-- 2026-03-04T13:52:49.295Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774823)
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Lieferadresse
-- Column: C_PurchaseCandidate.DropShip_Location_ID
-- 2026-03-04T13:53:02.417Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, DisplayLogic, EntityType, FacetFilterSeqNo, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch, Name,
                      SelectionColumnSeqNo, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592166, 774824, 0, 540894, 0, TO_TIMESTAMP('2026-03-04 13:53:02.300000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Business Partner Location for shipping to', 0, '@IsDropShip/''N''@=''Y''', 'D', 0, 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Lieferadresse', 0, 0, 360, 0, 1, 1,
        TO_TIMESTAMP('2026-03-04 13:53:02.300000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-04T13:53:02.419Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774824
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-03-04T13:53:02.420Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(53459)
;

-- 2026-03-04T13:53:02.427Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774824
;

-- 2026-03-04T13:53:02.429Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774824)
;

-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Lieferkontakt
-- Column: C_PurchaseCandidate.DropShip_User_ID
-- 2026-03-04T13:53:12.413Z
INSERT INTO AD_Field (AD_Client_ID, AD_Column_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, ColumnDisplayLength, Created, CreatedBy, Description, DisplayLength, DisplayLogic, EntityType, FacetFilterSeqNo, IncludedTabHeight, IsActive, IsDisplayed, IsDisplayedGrid, IsEncrypted, IsFieldOnly, IsHeading, IsHideGridColumnIfEmpty, IsOverrideFilterDefaultValue, IsReadOnly, IsSameLine, MaxFacetsToFetch, Name,
                      SelectionColumnSeqNo, SeqNo, SeqNoGrid, SortNo, SpanX, SpanY, Updated, UpdatedBy)
VALUES (0, 592167, 774825, 0, 540894, 0, TO_TIMESTAMP('2026-03-04 13:53:12.283000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, '', 0, '@IsDropShip/''N''@=''Y''', 'D', 0, 0, 'Y', 'Y', 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'N', 0, 'Lieferkontakt', 0, 0, 370, 0, 1, 1,
        TO_TIMESTAMP('2026-03-04 13:53:12.283000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-04T13:53:12.415Z
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated, AD_Client_ID, AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language,
       t.AD_Field_ID,
       t.Description,
       t.Help,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy,
       'Y'
FROM AD_Language l,
     AD_Field t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Field_ID = 774825
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- 2026-03-04T13:53:12.417Z
/* DDL */

SELECT update_FieldTranslation_From_AD_Name_Element(53460)
;

-- 2026-03-04T13:53:12.421Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 774825
;

-- 2026-03-04T13:53:12.422Z
/* DDL */

SELECT AD_Element_Link_Create_Missing_Field(774825)
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Abweichende Lieferadresse
-- Column: C_PurchaseCandidate.IsDropShip
-- 2026-03-04T13:58:54.676Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, Help, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774822, 0, 540894, 541247, 648472, 'F', TO_TIMESTAMP('2026-03-04 13:58:54.496000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Beim Streckengeschäft wird die Ware direkt vom Lieferanten zum Kunden geliefert',
        'Drop Shipments do not cause any Inventory reservations or movements as the Shipment is from the Vendor''s inventory. The Shipment of the Vendor to the Customer must be confirmed.', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Abweichende Lieferadresse', 50, 0, 0, TO_TIMESTAMP('2026-03-04 13:58:54.496000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Lieferempfänger
-- Column: C_PurchaseCandidate.DropShip_BPartner_ID
-- 2026-03-04T13:59:09.644Z
INSERT INTO AD_UI_Element (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_Tab_ID, AD_UI_ElementGroup_ID, AD_UI_Element_ID, AD_UI_ElementType, Created, CreatedBy, Description, Help, IsActive, IsAdvancedField, IsAllowFiltering, IsDisplayed, IsDisplayedGrid, IsDisplayed_SideList, IsMultiLine, MultiLine_LinesCount, Name, SeqNo, SeqNoGrid, SeqNo_SideList, Updated, UpdatedBy)
VALUES (0, 774823, 0, 540894, 541247, 648473, 'F', TO_TIMESTAMP('2026-03-04 13:59:09.515000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Business Partner to ship to', 'If empty the business partner will be shipped to.', 'Y', 'N', 'N', 'Y', 'N', 'N', 'N', 0, 'Lieferempfänger', 60, 0, 0,
        TO_TIMESTAMP('2026-03-04 13:59:09.515000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-04T13:59:33.250Z
INSERT INTO AD_UI_ElementField (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_UI_ElementField_ID, AD_UI_Element_ID, Created, CreatedBy, IsActive, SeqNo, Type, Updated, UpdatedBy)
VALUES (0, 774824, 0, 542475, 648473, TO_TIMESTAMP('2026-03-04 13:59:33.131000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 10, 'widget', TO_TIMESTAMP('2026-03-04 13:59:33.131000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- 2026-03-04T13:59:43.066Z
INSERT INTO AD_UI_ElementField (AD_Client_ID, AD_Field_ID, AD_Org_ID, AD_UI_ElementField_ID, AD_UI_Element_ID, Created, CreatedBy, IsActive, SeqNo, Type, Updated, UpdatedBy)
VALUES (0, 774825, 0, 542476, 648473, TO_TIMESTAMP('2026-03-04 13:59:42.933000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100, 'Y', 20, 'widget', TO_TIMESTAMP('2026-03-04 13:59:42.933000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', 100)
;

-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> default.Lieferempfänger
-- Column: C_PurchaseCandidate.DropShip_BPartner_ID
-- 2026-03-04T14:03:35.812Z
UPDATE AD_UI_Element
SET UIStyle='primary', WidgetSize='L', Updated=TO_TIMESTAMP('2026-03-04 14:03:35.812000', 'YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_UI_Element_ID = 648473
;

