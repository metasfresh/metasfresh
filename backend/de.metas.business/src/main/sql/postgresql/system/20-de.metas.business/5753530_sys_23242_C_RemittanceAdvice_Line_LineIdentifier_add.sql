-- Run mode: SWING_CLIENT

-- 2025-05-05T14:51:56.139Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583607,0,'LineIdentifier',TO_TIMESTAMP('2025-05-05 14:51:55.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zeile','Zeile',TO_TIMESTAMP('2025-05-05 14:51:55.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-05T14:51:56.148Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583607 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_RemittanceAdvice_Line.LineIdentifier
-- 2025-05-05T14:53:00.853Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589928,583607,0,10,541574,'XX','LineIdentifier',TO_TIMESTAMP('2025-05-05 14:53:00.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.esb.edi',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zeile',0,0,TO_TIMESTAMP('2025-05-05 14:53:00.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-05-05T14:53:00.855Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589928 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-05T14:53:00.897Z
/* DDL */  select update_Column_Translation_From_AD_Element(583607)
;

-- 2025-05-05T14:54:29.443Z
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice_Line','ALTER TABLE public.C_RemittanceAdvice_Line ADD COLUMN LineIdentifier VARCHAR(255)')
;

-- Field: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis Position(543320,D) -> Verarbeitet
-- Column: C_RemittanceAdvice_Line.Processed
-- 2025-05-05T14:55:04.607Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572804,741997,0,543320,TO_TIMESTAMP('2025-05-05 14:55:04.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2025-05-05 14:55:04.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-05T14:55:04.611Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-05T14:55:04.614Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2025-05-05T14:55:04.710Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741997
;

-- 2025-05-05T14:55:04.712Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741997)
;

-- Field: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis Position(543320,D) -> Zeile
-- Column: C_RemittanceAdvice_Line.LineIdentifier
-- 2025-05-05T14:55:04.819Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589928,741998,0,543320,TO_TIMESTAMP('2025-05-05 14:55:04.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Zeile',TO_TIMESTAMP('2025-05-05 14:55:04.718000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-05T14:55:04.821Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741998 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-05T14:55:04.823Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583607)
;

-- 2025-05-05T14:55:04.825Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741998
;

-- 2025-05-05T14:55:04.826Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741998)
;

-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis Position(543320,D) -> main -> 10 -> default.Remittance Advice Line
-- Column: C_RemittanceAdvice_Line.C_RemittanceAdvice_Line_ID
-- 2025-05-05T14:55:25.993Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2025-05-05 14:55:25.992000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=576418
;

-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis Position(543320,D) -> main -> 10 -> default.LineIdentifier
-- Column: C_RemittanceAdvice_Line.LineIdentifier
-- 2025-05-05T14:55:58.020Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741998,0,543320,544779,631409,'F',TO_TIMESTAMP('2025-05-05 14:55:57.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'LineIdentifier',9,0,0,TO_TIMESTAMP('2025-05-05 14:55:57.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis Position(543320,D) -> main -> 10 -> default.Remittance Advice Line
-- Column: C_RemittanceAdvice_Line.C_RemittanceAdvice_Line_ID
-- 2025-05-05T14:56:14.370Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-05-05 14:56:14.370000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=576418
;

-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis Position(543320,D) -> main -> 10 -> default.LineIdentifier
-- Column: C_RemittanceAdvice_Line.LineIdentifier
-- 2025-05-05T14:56:14.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-05-05 14:56:14.381000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631409
;

