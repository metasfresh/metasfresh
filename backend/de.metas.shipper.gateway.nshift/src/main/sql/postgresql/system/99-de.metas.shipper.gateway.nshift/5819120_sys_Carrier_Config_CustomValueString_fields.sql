-- nShift shipper config: generic custom value fields.
-- New Carrier_Config.CustomValueString1..3 columns, surfaced as advanced (non-grid) fields on the nShift
-- configuration tab (AD_Tab 548455, window 142). ShipperConfigRepository maps every non-excluded Carrier_Config
-- column to a JsonShipperConfig additional property by column name, so each value is reachable as
-- additionalProperty("CustomValueStringN") and can be routed into the nShift address CustNo via a SenderCustNo /
-- ReceiverCustNo mapping rule (e.g. for DHL Freight the operator stores the consignee id here). Carrier-agnostic.

-- 1) physical columns (new columns -> ALTER TABLE ADD COLUMN, nullable)
ALTER TABLE Carrier_Config ADD COLUMN IF NOT EXISTS CustomValueString1 VARCHAR(60);
ALTER TABLE Carrier_Config ADD COLUMN IF NOT EXISTS CustomValueString2 VARCHAR(60);
ALTER TABLE Carrier_Config ADD COLUMN IF NOT EXISTS CustomValueString3 VARCHAR(60);

-- ============================ slot 1 ============================
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                        ColumnName,EntityType,Name,PrintName)
VALUES (585301 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:00:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CustomValueString1','D','Benutzerdef. Text 1','Benutzerdef. Text 1');

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                            Name,PrintName,IsTranslated)
SELECT l.AD_Language,585301 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-08-14 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       TO_TIMESTAMP('2026-08-14 11:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       'Benutzerdef. Text 1','Benutzerdef. Text 1','N'
FROM AD_Language l WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Element_ID=585301 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Element_Trl SET Name='Custom Text 1', PrintName='Custom Text 1', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 11:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585301 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 11:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585301 AND AD_Language IN ('de_DE','de_CH');

INSERT INTO AD_Column (AD_Column_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,Updated,CreatedBy,UpdatedBy,
                       Name,Version,EntityType,ColumnName,AD_Table_ID,AD_Reference_ID,FieldLength,
                       IsKey,IsParent,IsMandatory,IsUpdateable,IsIdentifier,SeqNo,IsTranslated,IsEncrypted,
                       IsSelectionColumn,AD_Element_ID,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,
                       IsAdvancedText,IsLazyLoading,IsCalculated,IsGenericZoomOrigin,IsGenericZoomKeyColumn,
                       IsUseDocSequence,IsStaleable,DDL_NoForeignKey,IsDimension,IsDLMPartitionBoundary,
                       CacheInvalidateParent,SelectionColumnSeqNo,IsRangeFilter,IsShowFilterIncrementButtons,
                       IsForceIncludeInGeneratedModel,PersonalDataCategory,IsAutoApplyValidationRule,IsFacetFilter,
                       MaxFacetsToFetch,FacetFilterSeqNo,IsShowFilterInline,IsExcludeFromZoomTargets,
                       IsRestApiCustomColumn,CloningStrategy,IsShowFilterInactiveValues)
VALUES (593311 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        TO_TIMESTAMP('2026-08-14 11:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,100,
        'Benutzerdef. Text 1',0,'D','CustomValueString1',542540,10,60,
        'N','N','N','Y','N',0,'N','N',
        'N',585301,'N','N','N','Y',
        'N','N','N','N','N','N','N','N','N','N',
        'Y',0,'N','N',
        'N','NP','N','N',
        0,0,'N','Y',
        'N','XX','N');

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                      Name,AD_Tab_ID,AD_Column_ID,IsDisplayed,DisplayLength,IsReadOnly,IsSameLine,IsHeading,
                      IsFieldOnly,IsEncrypted,EntityType,ColumnDisplayLength,IncludedTabHeight,IsDisplayedGrid,
                      SpanX,SpanY,IsAlwaysUpdateable,IsOverrideFilterDefaultValue,IsHideGridColumnIfEmpty)
VALUES (782295 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Benutzerdef. Text 1',548455,593311,'Y',60,'N','N','N',
        'N','N','D',0,0,'N',
        1,1,'N','N','N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,SeqNo,IsAdvancedField,IsDisplayed,
                           IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           IsAllowFiltering,IsMultiline,Multiline_LinesCount)
VALUES (653145 /*From ID Server*/,0,0,782295,553597,548455,
        TO_TIMESTAMP('2026-08-14 11:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',
        'Benutzerdef. Text 1',100,'Y','Y',
        'N',0,'N',0,'F',
        'N','N',0);

-- ============================ slot 2 ============================
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                        ColumnName,EntityType,Name,PrintName)
VALUES (585302 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:03:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CustomValueString2','D','Benutzerdef. Text 2','Benutzerdef. Text 2');

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                            Name,PrintName,IsTranslated)
SELECT l.AD_Language,585302 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-08-14 11:03:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       TO_TIMESTAMP('2026-08-14 11:03:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       'Benutzerdef. Text 2','Benutzerdef. Text 2','N'
FROM AD_Language l WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Element_ID=585302 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Element_Trl SET Name='Custom Text 2', PrintName='Custom Text 2', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 11:03:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585302 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 11:03:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585302 AND AD_Language IN ('de_DE','de_CH');

INSERT INTO AD_Column (AD_Column_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,Updated,CreatedBy,UpdatedBy,
                       Name,Version,EntityType,ColumnName,AD_Table_ID,AD_Reference_ID,FieldLength,
                       IsKey,IsParent,IsMandatory,IsUpdateable,IsIdentifier,SeqNo,IsTranslated,IsEncrypted,
                       IsSelectionColumn,AD_Element_ID,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,
                       IsAdvancedText,IsLazyLoading,IsCalculated,IsGenericZoomOrigin,IsGenericZoomKeyColumn,
                       IsUseDocSequence,IsStaleable,DDL_NoForeignKey,IsDimension,IsDLMPartitionBoundary,
                       CacheInvalidateParent,SelectionColumnSeqNo,IsRangeFilter,IsShowFilterIncrementButtons,
                       IsForceIncludeInGeneratedModel,PersonalDataCategory,IsAutoApplyValidationRule,IsFacetFilter,
                       MaxFacetsToFetch,FacetFilterSeqNo,IsShowFilterInline,IsExcludeFromZoomTargets,
                       IsRestApiCustomColumn,CloningStrategy,IsShowFilterInactiveValues)
VALUES (593312 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:04:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        TO_TIMESTAMP('2026-08-14 11:04:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,100,
        'Benutzerdef. Text 2',0,'D','CustomValueString2',542540,10,60,
        'N','N','N','Y','N',0,'N','N',
        'N',585302,'N','N','N','Y',
        'N','N','N','N','N','N','N','N','N','N',
        'Y',0,'N','N',
        'N','NP','N','N',
        0,0,'N','Y',
        'N','XX','N');

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                      Name,AD_Tab_ID,AD_Column_ID,IsDisplayed,DisplayLength,IsReadOnly,IsSameLine,IsHeading,
                      IsFieldOnly,IsEncrypted,EntityType,ColumnDisplayLength,IncludedTabHeight,IsDisplayedGrid,
                      SpanX,SpanY,IsAlwaysUpdateable,IsOverrideFilterDefaultValue,IsHideGridColumnIfEmpty)
VALUES (782296 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:05:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:05:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Benutzerdef. Text 2',548455,593312,'Y',60,'N','N','N',
        'N','N','D',0,0,'N',
        1,1,'N','N','N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,SeqNo,IsAdvancedField,IsDisplayed,
                           IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           IsAllowFiltering,IsMultiline,Multiline_LinesCount)
VALUES (653146 /*From ID Server*/,0,0,782296,553597,548455,
        TO_TIMESTAMP('2026-08-14 11:05:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:05:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',
        'Benutzerdef. Text 2',110,'Y','Y',
        'N',0,'N',0,'F',
        'N','N',0);

-- ============================ slot 3 ============================
INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                        ColumnName,EntityType,Name,PrintName)
VALUES (585303 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:06:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:06:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'CustomValueString3','D','Benutzerdef. Text 3','Benutzerdef. Text 3');

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                            Name,PrintName,IsTranslated)
SELECT l.AD_Language,585303 /*From ID Server*/,0,0,'Y',
       TO_TIMESTAMP('2026-08-14 11:06:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       TO_TIMESTAMP('2026-08-14 11:06:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
       'Benutzerdef. Text 3','Benutzerdef. Text 3','N'
FROM AD_Language l WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Element_ID=585303 AND tt.AD_Language=l.AD_Language);

UPDATE AD_Element_Trl SET Name='Custom Text 3', PrintName='Custom Text 3', IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 11:06:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585303 AND AD_Language='en_US';

UPDATE AD_Element_Trl SET IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-08-14 11:06:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100
WHERE AD_Element_ID=585303 AND AD_Language IN ('de_DE','de_CH');

INSERT INTO AD_Column (AD_Column_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,Updated,CreatedBy,UpdatedBy,
                       Name,Version,EntityType,ColumnName,AD_Table_ID,AD_Reference_ID,FieldLength,
                       IsKey,IsParent,IsMandatory,IsUpdateable,IsIdentifier,SeqNo,IsTranslated,IsEncrypted,
                       IsSelectionColumn,AD_Element_ID,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,
                       IsAdvancedText,IsLazyLoading,IsCalculated,IsGenericZoomOrigin,IsGenericZoomKeyColumn,
                       IsUseDocSequence,IsStaleable,DDL_NoForeignKey,IsDimension,IsDLMPartitionBoundary,
                       CacheInvalidateParent,SelectionColumnSeqNo,IsRangeFilter,IsShowFilterIncrementButtons,
                       IsForceIncludeInGeneratedModel,PersonalDataCategory,IsAutoApplyValidationRule,IsFacetFilter,
                       MaxFacetsToFetch,FacetFilterSeqNo,IsShowFilterInline,IsExcludeFromZoomTargets,
                       IsRestApiCustomColumn,CloningStrategy,IsShowFilterInactiveValues)
VALUES (593313 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:07:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',
        TO_TIMESTAMP('2026-08-14 11:07:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,100,
        'Benutzerdef. Text 3',0,'D','CustomValueString3',542540,10,60,
        'N','N','N','Y','N',0,'N','N',
        'N',585303,'N','N','N','Y',
        'N','N','N','N','N','N','N','N','N','N',
        'Y',0,'N','N',
        'N','NP','N','N',
        0,0,'N','Y',
        'N','XX','N');

INSERT INTO AD_Field (AD_Field_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,
                      Name,AD_Tab_ID,AD_Column_ID,IsDisplayed,DisplayLength,IsReadOnly,IsSameLine,IsHeading,
                      IsFieldOnly,IsEncrypted,EntityType,ColumnDisplayLength,IncludedTabHeight,IsDisplayedGrid,
                      SpanX,SpanY,IsAlwaysUpdateable,IsOverrideFilterDefaultValue,IsHideGridColumnIfEmpty)
VALUES (782297 /*From ID Server*/,0,0,'Y',
        TO_TIMESTAMP('2026-08-14 11:08:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:08:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        'Benutzerdef. Text 3',548455,593313,'Y',60,'N','N','N',
        'N','N','D',0,0,'N',
        1,1,'N','N','N');

INSERT INTO AD_UI_Element (AD_UI_Element_ID,AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,Updated,UpdatedBy,IsActive,Name,SeqNo,IsAdvancedField,IsDisplayed,
                           IsDisplayedGrid,SeqNoGrid,IsDisplayed_SideList,SeqNo_SideList,AD_UI_ElementType,
                           IsAllowFiltering,IsMultiline,Multiline_LinesCount)
VALUES (653147 /*From ID Server*/,0,0,782297,553597,548455,
        TO_TIMESTAMP('2026-08-14 11:08:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,
        TO_TIMESTAMP('2026-08-14 11:08:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',
        'Benutzerdef. Text 3',120,'Y','Y',
        'N',0,'N',0,'F',
        'N','N',0);

-- backfill AD_Column_Trl / AD_Field_Trl for all languages, then cascade each element's translations onto them
SELECT add_missing_translations();
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585301, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585301, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585301, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585302, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585302, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585302, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585303, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585303, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585303, 'de_CH');
