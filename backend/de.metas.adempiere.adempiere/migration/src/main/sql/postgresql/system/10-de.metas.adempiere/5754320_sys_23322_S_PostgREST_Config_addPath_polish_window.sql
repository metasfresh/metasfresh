-- Run mode: SWING_CLIENT

-- 2025-05-13T12:38:12.790Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583629,0,'PostgREST_ResultDirectory',TO_TIMESTAMP('2025-05-13 12:38:11.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn ein PostgREST-Resultat lokal gespeichert wird, gibt dieses Feld den Ordner aus Sicht des metasfresh-Servers an.','D','Y','Ausgabeverzeichnis','Ausgabeverzeichnis',TO_TIMESTAMP('2025-05-13 12:38:11.436000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-13T12:38:12.797Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583629 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PostgREST_ResultDirectory
-- 2025-05-13T12:38:16.874Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 12:38:16.874000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583629 AND AD_Language='de_CH'
;

-- 2025-05-13T12:38:16.899Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583629,'de_CH')
;

-- Element: PostgREST_ResultDirectory
-- 2025-05-13T12:38:23.003Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 12:38:23.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583629 AND AD_Language='de_DE'
;

-- 2025-05-13T12:38:23.005Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583629,'de_DE')
;

-- 2025-05-13T12:38:23.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583629,'de_DE')
;

-- Element: PostgREST_ResultDirectory
-- 2025-05-13T12:38:52.596Z
UPDATE AD_Element_Trl SET Description='If a PostgREST result is saved locally, this field specifies the folder from the perspective of the metasfresh server.', IsTranslated='Y', Name='Output folder', PrintName='Output folder',Updated=TO_TIMESTAMP('2025-05-13 12:38:52.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583629 AND AD_Language='en_US'
;

-- 2025-05-13T12:38:52.597Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-13T12:38:52.890Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583629,'en_US')
;

-- Column: S_PostgREST_Config.PostgREST_ResultDirectory
-- 2025-05-13T12:39:18.646Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589982,583629,0,10,541503,'XX','PostgREST_ResultDirectory',TO_TIMESTAMP('2025-05-13 12:39:17.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Wenn ein PostgREST-Resultat lokal gespeichert wird, gibt dieses Feld den Ordner aus Sicht des metasfresh-Servers an.','D',0,1024,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ausgabeverzeichnis',0,0,TO_TIMESTAMP('2025-05-13 12:39:17.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-05-13T12:39:18.649Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-13T12:39:18.653Z
/* DDL */  select update_Column_Translation_From_AD_Element(583629)
;

-- Column: S_PostgREST_Config.PostgREST_ResultDirectory
-- 2025-05-13T12:39:30.995Z
UPDATE AD_Column SET DefaultValue='/tmp', IsMandatory='Y',Updated=TO_TIMESTAMP('2025-05-13 12:39:30.995000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589982
;

-- 2025-05-13T12:41:26.774Z
/* DDL */ SELECT public.db_alter_table('S_PostgREST_Config','ALTER TABLE public.S_PostgREST_Config ADD COLUMN PostgREST_ResultDirectory VARCHAR(1024) DEFAULT ''/tmp'' NOT NULL')
;

-- Table: S_PostgREST_Config
-- 2025-05-13T12:41:46.208Z
UPDATE AD_Table SET AD_Window_ID=540933,Updated=TO_TIMESTAMP('2025-05-13 12:41:46.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=541503
;

-- Field: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> Ausgabeverzeichnis
-- Column: S_PostgREST_Config.PostgREST_ResultDirectory
-- 2025-05-13T12:42:15.351Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589982,743163,0,542805,TO_TIMESTAMP('2025-05-13 12:42:13.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn ein PostgREST-Resultat lokal gespeichert wird, gibt dieses Feld den Ordner aus Sicht des metasfresh-Servers an.',1024,'D','Y','N','N','N','N','N','N','N','Ausgabeverzeichnis',TO_TIMESTAMP('2025-05-13 12:42:13.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-13T12:42:15.354Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=743163 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-13T12:42:15.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583629)
;

-- 2025-05-13T12:42:15.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=743163
;

-- 2025-05-13T12:42:15.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(743163)
;

-- UI Section: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main
-- UI Column: 20
-- 2025-05-13T12:43:07.334Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548032,542070,TO_TIMESTAMP('2025-05-13 12:43:07.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2025-05-13 12:43:07.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10
-- UI Element Group: timeouts
-- 2025-05-13T12:44:44.067Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,542600,552825,TO_TIMESTAMP('2025-05-13 12:44:43.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','timeouts',20,TO_TIMESTAMP('2025-05-13 12:44:43.840000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> default.PostgREST Configs
-- Column: S_PostgREST_Config.S_PostgREST_Config_ID
-- 2025-05-13T12:44:59.083Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=570164
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> timeouts.Read timeout
-- Column: S_PostgREST_Config.Read_timeout
-- 2025-05-13T12:45:06.127Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552825, SeqNo=10,Updated=TO_TIMESTAMP('2025-05-13 12:45:06.127000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570165
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> timeouts.Connection timeout
-- Column: S_PostgREST_Config.Connection_timeout
-- 2025-05-13T12:45:22.892Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552825, SeqNo=20,Updated=TO_TIMESTAMP('2025-05-13 12:45:22.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570166
;

-- UI Column: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 20
-- UI Element Group: path
-- 2025-05-13T12:45:39.733Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548032,552826,TO_TIMESTAMP('2025-05-13 12:45:39.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','path',10,TO_TIMESTAMP('2025-05-13 12:45:39.527000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 20 -> path.PostgREST_ResultDirectory
-- Column: S_PostgREST_Config.PostgREST_ResultDirectory
-- 2025-05-13T12:45:56.902Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,743163,0,542805,552826,631892,'F',TO_TIMESTAMP('2025-05-13 12:45:56.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn ein PostgREST-Resultat lokal gespeichert wird, gibt dieses Feld den Ordner aus Sicht des metasfresh-Servers an.','Y','N','N','Y','N','N','N',0,'PostgREST_ResultDirectory',10,0,0,TO_TIMESTAMP('2025-05-13 12:45:56.530000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 20
-- UI Element Group: org
-- 2025-05-13T12:46:04.329Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548032,552827,TO_TIMESTAMP('2025-05-13 12:46:03.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2025-05-13 12:46:03.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 20 -> org.Sektion
-- Column: S_PostgREST_Config.AD_Org_ID
-- 2025-05-13T12:46:27.300Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552827, SeqNo=10,Updated=TO_TIMESTAMP('2025-05-13 12:46:27.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570168
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 20 -> org.Mandant
-- Column: S_PostgREST_Config.AD_Client_ID
-- 2025-05-13T12:46:37.702Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552827, SeqNo=20,Updated=TO_TIMESTAMP('2025-05-13 12:46:37.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570169
;

-- UI Column: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 20
-- UI Element Group: client
-- 2025-05-13T12:47:26.915Z
UPDATE AD_UI_ElementGroup SET Name='client',Updated=TO_TIMESTAMP('2025-05-13 12:47:26.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552827
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> default.Sektion
-- Column: S_PostgREST_Config.AD_Org_ID
-- 2025-05-13T12:47:39.489Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=543938, SeqNo=50,Updated=TO_TIMESTAMP('2025-05-13 12:47:39.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570168
;

-- UI Column: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10
-- UI Element Group: connectionParams
-- 2025-05-13T12:48:11.337Z
UPDATE AD_UI_ElementGroup SET Name='connectionParams',Updated=TO_TIMESTAMP('2025-05-13 12:48:11.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=552825
;

-- UI Column: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10
-- UI Element Group: main
-- 2025-05-13T12:48:14.570Z
UPDATE AD_UI_ElementGroup SET Name='main',Updated=TO_TIMESTAMP('2025-05-13 12:48:14.570000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=543938
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> connectionParams.URL
-- Column: S_PostgREST_Config.Base_url
-- 2025-05-13T12:48:25.052Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=552825, SeqNo=30,Updated=TO_TIMESTAMP('2025-05-13 12:48:25.052000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570167
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> connectionParams.Read timeout
-- Column: S_PostgREST_Config.Read_timeout
-- 2025-05-13T12:48:45.257Z
UPDATE AD_UI_Element SET SeqNo=31,Updated=TO_TIMESTAMP('2025-05-13 12:48:45.257000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570165
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> connectionParams.URL
-- Column: S_PostgREST_Config.Base_url
-- 2025-05-13T12:48:48.063Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2025-05-13 12:48:48.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570167
;

-- UI Element: PostgREST Konfiguration(540933,D) -> PostgREST Konfiguration(542805,D) -> main -> 10 -> connectionParams.Read timeout
-- Column: S_PostgREST_Config.Read_timeout
-- 2025-05-13T12:48:53.996Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2025-05-13 12:48:53.996000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570165
;

-- Table: S_PostgREST_Config_Process
-- 2025-05-13T12:49:23.178Z
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=542478
;

-- 2025-05-13T12:49:23.183Z
DELETE FROM AD_Table WHERE AD_Table_ID=542478
;

-- 2025-05-13T12:51:54.008Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583630,0,'AD_Org_ID_S_PostgREST_Config',TO_TIMESTAMP('2025-05-13 12:51:53.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sektion, für die diese Konfiguration gilt','D','Y','Sektion (Matching)','Sektion (Matching)',TO_TIMESTAMP('2025-05-13 12:51:53.845000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-05-13T12:51:54.011Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583630 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: AD_Org_ID_S_PostgREST_Config
-- 2025-05-13T12:52:21.017Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 12:52:21.017000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583630 AND AD_Language='de_CH'
;

-- 2025-05-13T12:52:21.020Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583630,'de_CH')
;

-- Element: AD_Org_ID_S_PostgREST_Config
-- 2025-05-13T12:52:23.540Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-13 12:52:23.540000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583630 AND AD_Language='de_DE'
;

-- 2025-05-13T12:52:23.543Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583630,'de_DE')
;

-- 2025-05-13T12:52:23.544Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583630,'de_DE')
;

-- Element: AD_Org_ID_S_PostgREST_Config
-- 2025-05-13T12:52:57.625Z
UPDATE AD_Element_Trl SET Description='Organisation for which this config is applicable.', IsTranslated='Y', Name='Org (Matching)', PrintName='Org (Matching)',Updated=TO_TIMESTAMP('2025-05-13 12:52:57.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583630 AND AD_Language='en_US'
;

-- 2025-05-13T12:52:57.626Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-05-13T12:52:57.889Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583630,'en_US')
;

