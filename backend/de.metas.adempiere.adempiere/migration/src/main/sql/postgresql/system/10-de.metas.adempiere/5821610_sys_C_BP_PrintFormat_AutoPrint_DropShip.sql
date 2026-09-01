-- Add C_BP_PrintFormat.IsDropShip + C_BP_PrintFormat.IsAutoPrint (nullable Yes/No, reusing the
-- existing shared AD_Elements of the same name); also make C_BP_PrintFormat.DocumentCopies_Override
-- nullable and drop its DB default, so per-partner print-format rows can leave these unset (meaning
-- "not configured") instead of forcing an explicit value.

-- Column: C_BP_PrintFormat.IsDropShip
-- 2026-09-01T10:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593458 /*From ID Server*/,2466,0,17,319,540638,'XX','IsDropShip',TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Abweichende Lieferadresse',0,0,TO_TIMESTAMP('2026-09-01 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-09-01T10:00:01.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593458 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-01T10:00:02.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(2466)
;

-- 2026-09-01T10:00:03.000Z
/* DDL */ SELECT public.db_alter_table('C_BP_PrintFormat','ALTER TABLE public.C_BP_PrintFormat ADD COLUMN IsDropShip CHAR(1)')
;

-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T10:00:04.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593459 /*From ID Server*/,581849,0,17,319,540638,'XX','IsAutoPrint',TO_TIMESTAMP('2026-09-01 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sofort drucken',0,0,TO_TIMESTAMP('2026-09-01 10:00:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-09-01T10:00:05.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593459 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-01T10:00:06.000Z
/* DDL */ select update_Column_Translation_From_AD_Element(581849)
;

-- 2026-09-01T10:00:07.000Z
/* DDL */ SELECT public.db_alter_table('C_BP_PrintFormat','ALTER TABLE public.C_BP_PrintFormat ADD COLUMN IsAutoPrint CHAR(1)')
;

-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Abweichende Lieferadresse
-- Column: C_BP_PrintFormat.IsDropShip
-- 2026-09-01T10:00:08.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593458,783059 /*From ID Server*/,0,540653,TO_TIMESTAMP('2026-09-01 10:00:08','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Abweichende Lieferadresse',TO_TIMESTAMP('2026-09-01 10:00:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01T10:00:09.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-01T10:00:10.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(2466)
;

-- 2026-09-01T10:00:11.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783059
;

-- 2026-09-01T10:00:12.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783059)
;

-- Field: Geschäftspartner(123,D) -> Druck Format(540653,D) -> Sofort drucken
-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T10:00:13.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593459,783060 /*From ID Server*/,0,540653,TO_TIMESTAMP('2026-09-01 10:00:13','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Sofort drucken',TO_TIMESTAMP('2026-09-01 10:00:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-09-01T10:00:14.000Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Field_ID=783060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-01T10:00:15.000Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(581849)
;

-- 2026-09-01T10:00:16.000Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=783060
;

-- 2026-09-01T10:00:17.000Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(783060)
;

-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Abweichende Lieferadresse
-- Column: C_BP_PrintFormat.IsDropShip
-- 2026-09-01T10:00:18.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783059,0,540653,1000037,653699 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 10:00:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Abweichende Lieferadresse',46,71,0,TO_TIMESTAMP('2026-09-01 10:00:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Geschäftspartner(123,D) -> Druck Format(540653,D) -> main -> 10 -> default.Sofort drucken
-- Column: C_BP_PrintFormat.IsAutoPrint
-- 2026-09-01T10:00:19.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783060,0,540653,1000037,653700 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-01 10:00:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Sofort drucken',47,72,0,TO_TIMESTAMP('2026-09-01 10:00:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Make C_BP_PrintFormat.DocumentCopies_Override nullable and drop its '0' DB default: an override
-- row should be able to leave the copy count unset (meaning "no override"), not forced to 0.
-- Column: C_BP_PrintFormat.DocumentCopies_Override
-- 2026-09-01T10:00:20.000Z
UPDATE AD_Column SET IsMandatory='N', DefaultValue=NULL, Updated=TO_TIMESTAMP('2026-09-01 10:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=587623
;

-- 2026-09-01T10:00:21.000Z
INSERT INTO t_alter_column VALUES ('C_BP_PrintFormat','DocumentCopies_Override','NUMERIC(10)','NULL','NULL')
;
