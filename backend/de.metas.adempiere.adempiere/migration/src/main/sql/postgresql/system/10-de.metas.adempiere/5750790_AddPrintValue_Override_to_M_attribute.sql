-- Run mode: SWING_CLIENT

-- 2025-04-03T07:04:32.490Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583566,0,'PrintValue_Override',TO_TIMESTAMP('2025-04-03 07:04:32.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The value that we print on documents if is filled out','D','','Y','Print Value Override','Print Value Override',TO_TIMESTAMP('2025-04-03 07:04:32.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T07:04:32.498Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583566 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PrintValue_Override
-- 2025-04-03T07:04:42.709Z
UPDATE AD_Element_Trl SET Description='Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.', IsTranslated='Y', Name='Druckwert-Überschreibung', PrintName='Druckwert-Überschreibung',Updated=TO_TIMESTAMP('2025-04-03 07:04:42.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583566 AND AD_Language='de_CH'
;

-- 2025-04-03T07:04:42.711Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-03T07:04:43.048Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583566,'de_CH')
;

-- Element: PrintValue_Override
-- 2025-04-03T07:04:52.762Z
UPDATE AD_Element_Trl SET Description='Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.', IsTranslated='Y', Name='Druckwert-Überschreibung', PrintName='Druckwert-Überschreibung',Updated=TO_TIMESTAMP('2025-04-03 07:04:52.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583566 AND AD_Language='de_DE'
;

-- 2025-04-03T07:04:52.763Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-03T07:04:53.621Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583566,'de_DE')
;

-- 2025-04-03T07:04:53.624Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583566,'de_DE')
;

-- Column: M_Attribute.PrintValue_Override
-- 2025-04-03T07:05:17.511Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589831,583566,0,10,562,'XX','PrintValue_Override',TO_TIMESTAMP('2025-04-03 07:05:17.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.','D',0,255,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Druckwert-Überschreibung',0,0,TO_TIMESTAMP('2025-04-03 07:05:17.382000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-03T07:05:17.514Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589831 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-03T07:05:17.517Z
/* DDL */  select update_Column_Translation_From_AD_Element(583566)
;

-- 2025-04-03T07:05:22.919Z
/* DDL */ SELECT public.db_alter_table('M_Attribute','ALTER TABLE public.M_Attribute ADD COLUMN PrintValue_Override VARCHAR(255)')
;

-- Field: Merkmal(260,D) -> Merkmal(462,D) -> Druckwert-Überschreibung
-- Column: M_Attribute.PrintValue_Override
-- 2025-04-03T07:05:48.194Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589831,741860,0,462,0,TO_TIMESTAMP('2025-04-03 07:05:48.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.',0,'D',0,'',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Druckwert-Überschreibung',0,0,230,0,1,1,TO_TIMESTAMP('2025-04-03 07:05:48.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-03T07:05:48.197Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-04-03T07:05:48.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583566)
;

-- 2025-04-03T07:05:48.208Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741860
;

-- 2025-04-03T07:05:48.213Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741860)
;

-- UI Element: Merkmal(260,D) -> Merkmal(462,D) -> main -> 10 -> value.Druckwert-Überschreibung
-- Column: M_Attribute.PrintValue_Override
-- 2025-04-03T07:06:50.642Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,741860,0,462,540607,631307,'F',TO_TIMESTAMP('2025-04-03 07:06:50.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wert, der auf Dokumenten anstelle des Standardwerts gedruckt wird, falls ausgefüllt.','Y','N','N','Y','N','N','N',0,'Druckwert-Überschreibung',20,0,0,TO_TIMESTAMP('2025-04-03 07:06:50.490000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

