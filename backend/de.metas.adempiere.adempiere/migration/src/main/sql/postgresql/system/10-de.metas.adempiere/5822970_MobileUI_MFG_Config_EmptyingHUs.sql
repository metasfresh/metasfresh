-- Run mode: SWING_CLIENT

-- IsAllowEmptyingHUs

-- 2026-09-07T08:00:01.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585428,0,'IsAllowEmptyingHUs',TO_TIMESTAMP('2026-09-07 08:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Gebinde leeren erlauben','Gebinde leeren erlauben',TO_TIMESTAMP('2026-09-07 08:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-07T08:00:01.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585428 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-09-07T08:00:01.200Z
UPDATE AD_Element_Trl SET Name='Allow emptying HUs', Description='Offers the reason "empty (auto. inventory)" on the raw-materials issue step. Choosing that reason books the HU''s remaining quantity off stock automatically, through a completed inventory document.', Help='Applies to single HUs only: issue steps sourced from a loading unit (pallet) or from an aggregate HU do not offer the reason. HUs holding more than one product are likewise excluded. Use the confirmation setting to control whether the operator is asked before the booking happens.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 08:00:01.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585428
;

-- 2026-09-07T08:00:01.300Z
-- The German text MUST be written to the de_DE _Trl row, not only to the base AD_Element row.
-- After the batch the migration tool runs after_migration_sync_translations, which calls
-- update_TRL_Tables_On_AD_Element_TRL_Update -> update_ad_element_on_ad_element_trl_update; that
-- syncs AD_Element from the base-language AD_Element_Trl row (de_DE here). Setting AD_Element
-- alone is therefore synced straight back to NULL from the still-empty de_DE row.
UPDATE AD_Element SET Description='Bietet im Schritt der Materialzuteilung den Grund "leer (autom. Inventur)" an. Mit diesem Grund wird die Restmenge des Gebindes automatisch über eine abgeschlossene Inventur vom Bestand ausgebucht.', Help='Gilt nur für einzelne Gebinde: Zuteilungsschritte aus einer LU (Palette) oder aus einem aggregierten Gebinde bieten den Grund nicht an. Gebinde mit mehr als einem Produkt sind ebenfalls ausgeschlossen. Über die Einstellung "Vor Buchung bestätigen" wird gesteuert, ob der Nutzer vor der Buchung gefragt wird.', Updated=TO_TIMESTAMP('2026-09-07 08:00:01.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585428
;

UPDATE AD_Element_Trl SET Description='Bietet im Schritt der Materialzuteilung den Grund "leer (autom. Inventur)" an. Mit diesem Grund wird die Restmenge des Gebindes automatisch über eine abgeschlossene Inventur vom Bestand ausgebucht.', Help='Gilt nur für einzelne Gebinde: Zuteilungsschritte aus einer LU (Palette) oder aus einem aggregierten Gebinde bieten den Grund nicht an. Gebinde mit mehr als einem Produkt sind ebenfalls ausgeschlossen. Über die Einstellung "Vor Buchung bestätigen" wird gesteuert, ob der Nutzer vor der Buchung gefragt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 08:00:01.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585428
;

UPDATE AD_Element_Trl SET Description='Bietet im Schritt der Materialzuteilung den Grund "leer (autom. Inventur)" an. Mit diesem Grund wird die Restmenge des Gebindes automatisch über eine abgeschlossene Inventur vom Bestand ausgebucht.', Help='Gilt nur für einzelne Gebinde: Zuteilungsschritte aus einer LU (Palette) oder aus einem aggregierten Gebinde bieten den Grund nicht an. Gebinde mit mehr als einem Produkt sind ebenfalls ausgeschlossen. Über die Einstellung "Vor Buchung bestätigen" wird gesteuert, ob der Nutzer vor der Buchung gefragt wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 08:00:01.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585428
;

-- Column: MobileUI_MFG_Config.IsAllowEmptyingHUs
-- 2026-09-07T08:00:01.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593508,585428,0,20,542397,'XX','IsAllowEmptyingHUs',TO_TIMESTAMP('2026-09-07 08:00:01.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gebinde leeren erlauben','NP',0,0,TO_TIMESTAMP('2026-09-07 08:00:01.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-09-07T08:00:01.500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593508 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-07T08:00:01.600Z
/* DDL */  select update_Column_Translation_From_AD_Element(585428)
;

-- 2026-09-07T08:00:01.700Z
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsAllowEmptyingHUs CHAR(1) DEFAULT ''Y'' CHECK (IsAllowEmptyingHUs IN (''Y'',''N'')) NOT NULL')
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Gebinde leeren erlauben
-- Column: MobileUI_MFG_Config.IsAllowEmptyingHUs
-- 2026-09-07T08:00:01.800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593508,784957,0,547483,TO_TIMESTAMP('2026-09-07 08:00:01.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Gebinde leeren erlauben',TO_TIMESTAMP('2026-09-07 08:00:01.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-07T08:00:01.900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784957 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-07T08:00:02.000Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585428)
;

-- 2026-09-07T08:00:02.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784957
;

-- 2026-09-07T08:00:02.200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(784957)
;

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 30 -> flags.Gebinde leeren erlauben
-- Column: MobileUI_MFG_Config.IsAllowEmptyingHUs
-- 2026-09-07T08:00:02.300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784957,0,547483,551690,654723,'F',TO_TIMESTAMP('2026-09-07 08:00:02.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Gebinde leeren erlauben',30,30,0,TO_TIMESTAMP('2026-09-07 08:00:02.300000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- IsConfirmEmptyingHU

-- 2026-09-07T08:00:02.400Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585429,0,'IsConfirmEmptyingHU',TO_TIMESTAMP('2026-09-07 08:00:02.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Vor Buchung bestätigen','Vor Buchung bestätigen',TO_TIMESTAMP('2026-09-07 08:00:02.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-07T08:00:02.500Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585429 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-09-07T08:00:02.600Z
UPDATE AD_Element_Trl SET Name='Confirm before booking', Description='Asks the operator to confirm, showing the quantity about to be written off and its unit of measure, before the HU''s remaining quantity is booked off stock.', Help='Applies to the "empty (auto. inventory)" reason only. Unticked, the write-off is booked immediately with no prompt.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 08:00:02.600000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Element_ID=585429
;

-- 2026-09-07T08:00:02.700Z
-- The German text MUST be written to the de_DE _Trl row, not only to the base AD_Element row.
-- After the batch the migration tool runs after_migration_sync_translations, which calls
-- update_TRL_Tables_On_AD_Element_TRL_Update -> update_ad_element_on_ad_element_trl_update; that
-- syncs AD_Element from the base-language AD_Element_Trl row (de_DE here). Setting AD_Element
-- alone is therefore synced straight back to NULL from the still-empty de_DE row.
UPDATE AD_Element SET Description='Fragt den Nutzer vor dem Ausbuchen der Restmenge des Gebindes zur Bestätigung und zeigt dabei die auszubuchende Menge und ihre Maßeinheit an.', Help='Gilt nur für den Grund "leer (autom. Inventur)". Ohne Häkchen wird die Ausbuchung sofort und ohne Rückfrage gebucht.', Updated=TO_TIMESTAMP('2026-09-07 08:00:02.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585429
;

UPDATE AD_Element_Trl SET Description='Fragt den Nutzer vor dem Ausbuchen der Restmenge des Gebindes zur Bestätigung und zeigt dabei die auszubuchende Menge und ihre Maßeinheit an.', Help='Gilt nur für den Grund "leer (autom. Inventur)". Ohne Häkchen wird die Ausbuchung sofort und ohne Rückfrage gebucht.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 08:00:02.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Element_ID=585429
;

UPDATE AD_Element_Trl SET Description='Fragt den Nutzer vor dem Ausbuchen der Restmenge des Gebindes zur Bestätigung und zeigt dabei die auszubuchende Menge und ihre Maßeinheit an.', Help='Gilt nur für den Grund "leer (autom. Inventur)". Ohne Häkchen wird die Ausbuchung sofort und ohne Rückfrage gebucht.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-07 08:00:02.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Element_ID=585429
;

-- Column: MobileUI_MFG_Config.IsConfirmEmptyingHU
-- 2026-09-07T08:00:02.800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593509,585429,0,20,542397,'XX','IsConfirmEmptyingHU',TO_TIMESTAMP('2026-09-07 08:00:02.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Vor Buchung bestätigen','NP',0,0,TO_TIMESTAMP('2026-09-07 08:00:02.800000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-09-07T08:00:02.900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593509 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-07T08:00:03.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(585429)
;

-- 2026-09-07T08:00:03.100Z
/* DDL */ SELECT public.db_alter_table('MobileUI_MFG_Config','ALTER TABLE public.MobileUI_MFG_Config ADD COLUMN IsConfirmEmptyingHU CHAR(1) DEFAULT ''Y'' CHECK (IsConfirmEmptyingHU IN (''Y'',''N'')) NOT NULL')
;

-- Field: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> Vor Buchung bestätigen
-- Column: MobileUI_MFG_Config.IsConfirmEmptyingHU
-- 2026-09-07T08:00:03.200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593509,784958,0,547483,TO_TIMESTAMP('2026-09-07 08:00:03.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Vor Buchung bestätigen',TO_TIMESTAMP('2026-09-07 08:00:03.200000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-07T08:00:03.300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784958 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-07T08:00:03.400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585429)
;

-- 2026-09-07T08:00:03.500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=784958
;

-- 2026-09-07T08:00:03.600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(784958)
;

-- UI Element: MobileUI Manufacturing Configuration(541788,D) -> MobileUI Manufacturing Configuration(547483,D) -> main -> 40 -> flags.Vor Buchung bestätigen
-- Column: MobileUI_MFG_Config.IsConfirmEmptyingHU
-- 2026-09-07T08:00:03.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,784958,0,547483,551690,654724,'F',TO_TIMESTAMP('2026-09-07 08:00:03.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Vor Buchung bestätigen',40,40,0,TO_TIMESTAMP('2026-09-07 08:00:03.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
