-- Mobile_Application_Action.IsIncludedInAllowAllActions: 'Y' (default, today's behaviour for every existing action) = roles with
-- Mobile_Application_Access.IsAllowAllActions='Y' get the action; 'N' = only roles with an explicit Mobile_Application_Action_Access row.
-- 'createHU' (HU Manager, Mobile_Application_Action_ID 540008, see 5832630) becomes opt-in ('N').
-- Field placement: window "Mobile Application" (541826), tab "Mobile Application Action" (548438), group 553542 (next to Description), after Description.
-- IDs from the central ID server (2026-11-03): AD_Element 585580; AD_Column 593767; AD_Field 787203; AD_UI_Element 656038.

-- ============================================================================
-- AD_Element IsIncludedInAllowAllActions: 585580
-- ============================================================================

-- 2026-11-03 16:50:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585580 /*From ID Server*/,0,'IsIncludedInAllowAllActions',TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rollen mit „Alle Aktionen erlaubt“ erhalten diese Aktion. N = nur Rollen mit ausdrücklicher Berechtigung für die Aktion.','D','Ja (Standard): Rollen, denen alle Aktionen der mobilen Anwendung erlaubt sind, dürfen diese Aktion ausführen. Nein: Die Aktion steht nur Rollen zur Verfügung, die dafür eine ausdrückliche Berechtigung (Aktion der mobilen Anwendung - Zugriff) haben. So lassen sich neue Aktionen einführen, ohne dass sie automatisch bei allen Rollen mit „Alle Aktionen erlaubt“ erscheinen.','Y','In „Alle Aktionen erlaubt“ enthalten','In „Alle Aktionen erlaubt“ enthalten',TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-11-03 16:50:00 (seed _Trl rows for all system/base languages)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585580 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-11-03 16:50:00 (en_US)
UPDATE AD_Element_Trl SET Name='Included in Allow all Actions', PrintName='Included in Allow all Actions', Description='Roles allowed all actions of the app get this action. N = only roles with an explicit action grant.', Help='Yes (default): roles that are allowed all actions of the mobile application may run this action. No: the action is only available to roles that have an explicit grant for it (mobile application action access). This allows introducing new actions without them appearing automatically for every role that is allowed all actions.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585580 AND AD_Language='en_US'
;

-- 2026-11-03 16:50:00
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585580,'en_US')
;

-- 2026-11-03 16:50:00 (de_DE: same text as the base)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585580 AND AD_Language='de_DE'
;

-- 2026-11-03 16:50:00 (de_CH: same text as the base)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585580 AND AD_Language='de_CH'
;

-- 2026-11-03 16:50:00
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585580,'de_CH')
;

-- ============================================================================
-- AD_Column Mobile_Application_Action.IsIncludedInAllowAllActions (593767), YesNo (20), mandatory, default 'Y'
-- ============================================================================

-- 2026-11-03 16:50:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) SELECT 0,593767 /*From ID Server*/,585580,0,20,t.AD_Table_ID,'XX','IsIncludedInAllowAllActions',TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y',e.Description,'D',0,1,e.Help,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','Y','N',0,e.Name,'NP',0,0,TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0 FROM AD_Table t, AD_Element e WHERE t.TableName='Mobile_Application_Action' AND e.AD_Element_ID=585580
;

-- 2026-11-03 16:50:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593767 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-11-03 16:50:00
/* DDL */ select update_Column_Translation_From_AD_Element(585580)
;

-- ============================================================================
-- Physical DB column (every existing action row gets 'Y' = unchanged behaviour), then createHU -> 'N'
-- ============================================================================

-- 2026-11-03 16:50:00
/* DDL */ SELECT public.db_alter_table('Mobile_Application_Action','ALTER TABLE public.Mobile_Application_Action ADD COLUMN IsIncludedInAllowAllActions CHAR(1) DEFAULT ''Y'' CHECK (IsIncludedInAllowAllActions IN (''Y'',''N'')) NOT NULL')
;

-- 2026-11-03 16:50:00 createHU (5832630, id 540008) is opt-in: only roles with an explicit Mobile_Application_Action_Access row get it
UPDATE Mobile_Application_Action SET IsIncludedInAllowAllActions='N', Updated=TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE Mobile_Application_Action_ID=540008 AND InternalName='createHU'
;

-- ============================================================================
-- AD_Field on Mobile Application (541826) -> Mobile Application Action (548438)
-- ============================================================================

-- 2026-11-03 16:50:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) SELECT 0,593767,787203 /*From ID Server*/,0,548438,TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,e.Description,1,'D',e.Help,'Y','Y','Y','N','N','N','N','N',e.Name,25,25,TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100 FROM AD_Element e WHERE e.AD_Element_ID=585580
;

-- 2026-11-03 16:50:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=787203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-03 16:50:00
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585580)
;

-- 2026-11-03 16:50:00
DELETE FROM AD_Element_Link WHERE AD_Field_ID=787203
;

-- 2026-11-03 16:50:00
/* DDL */ select AD_Element_Link_Create_Missing_Field(787203)
;

-- ============================================================================
-- AD_UI_Element: group 553542 (Internal name, Description); Description is SeqNo 20 -> new field SeqNo 30 (grid 25 = between Description and Active)
-- ============================================================================

-- 2026-11-03 16:50:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,787203,0,548438,553542,656038 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','In „Alle Aktionen erlaubt“ enthalten',30,25,0,TO_TIMESTAMP('2026-11-03 16:50:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
