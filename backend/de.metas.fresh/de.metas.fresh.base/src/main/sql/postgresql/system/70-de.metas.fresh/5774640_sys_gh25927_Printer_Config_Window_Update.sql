-- Run mode: SWING_CLIENT 

-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:21:02.872Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591439,469,0,10,540637,'XX','Name',TO_TIMESTAMP('2025-10-26 09:21:02.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.printing',0,2000,'E','','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Name',0,1,TO_TIMESTAMP('2025-10-26 09:21:02.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-26T09:21:02.949Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591439 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-26T09:21:03.122Z
/* DDL */  select update_Column_Translation_From_AD_Element(469)
;

-- 2025-10-26T09:21:20.370Z
/* DDL */ SELECT public.db_alter_table('AD_Printer_Config','ALTER TABLE public.AD_Printer_Config ADD COLUMN Name VARCHAR(2000)')
;

-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:21:57.188Z
UPDATE AD_Column SET MandatoryLogic='IsSharedPrinterConfig=''Y''',Updated=TO_TIMESTAMP('2025-10-26 09:21:57.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591439
;

-- Column: AD_Printer_Config.Description
-- 2025-10-26T09:22:57.172Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591440,275,0,14,540637,'XX','Description',TO_TIMESTAMP('2025-10-26 09:22:56.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.printing',0,2000,'E','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2025-10-26 09:22:56.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-26T09:22:57.246Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591440 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-26T09:23:00.130Z
/* DDL */  select update_Column_Translation_From_AD_Element(275)
;

-- Column: AD_Printer_Config.IsSharedPrinterConfig
-- 2025-10-26T09:24:58.907Z
UPDATE AD_Column SET IsIdentifier='N', SeqNo=NULL,Updated=TO_TIMESTAMP('2025-10-26 09:24:58.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551597
;

-- Column: AD_Printer_Config.C_Workplace_ID
-- 2025-10-26T09:27:25.336Z
UPDATE AD_Column SET SeqNo=40,Updated=TO_TIMESTAMP('2025-10-26 09:27:25.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=587610
;

-- Column: AD_Printer_Config.ConfigHostKey
-- 2025-10-26T09:27:59.374Z
UPDATE AD_Column SET SeqNo=30,Updated=TO_TIMESTAMP('2025-10-26 09:27:59.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=551593
;

-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2025-10-26T09:28:24.971Z
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2025-10-26 09:28:24.971000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569796
;

-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:28:51.887Z
UPDATE AD_Column SET SeqNo=10,Updated=TO_TIMESTAMP('2025-10-26 09:28:51.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591439
;

-- 2025-10-26T09:34:54.138Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540832,0,540637,TO_TIMESTAMP('2025-10-26 09:34:53.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Der Name muss eindeutig sein','Y','Y','AD_Printer_Config_UC Name','N',TO_TIMESTAMP('2025-10-26 09:34:53.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-26T09:34:54.221Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540832 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2025-10-26T09:35:23.622Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591439,541478,540832,0,TO_TIMESTAMP('2025-10-26 09:35:23.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2025-10-26 09:35:23.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-26T09:36:14.321Z
UPDATE AD_Index_Table SET Name='AD_Printer_Config_UC_Name',Updated=TO_TIMESTAMP('2025-10-26 09:36:13.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540832
;

-- 2025-10-26T09:36:38.303Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='The name must be unique', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-10-26 09:36:38.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540832 AND AD_Language='en_US'
;

-- 2025-10-26T09:36:38.376Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-26T09:36:47.692Z
CREATE UNIQUE INDEX AD_Printer_Config_UC_Name ON AD_Printer_Config (Name)
;

-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:40:02.934Z
UPDATE AD_Column SET MandatoryLogic='@IsSharedPrinterConfig@=''Y''',Updated=TO_TIMESTAMP('2025-10-26 09:40:02.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591439
;

-- Column: AD_Printer_Config.C_Workplace_ID
-- 2025-10-26T09:40:43.330Z
UPDATE AD_Column SET MandatoryLogic='@AD_User_PrinterMatchingConfig_ID/0@=0 & @IsSharedPrinterConfig@=''N''',Updated=TO_TIMESTAMP('2025-10-26 09:40:43.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=587610
;

-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2025-10-26T09:41:09.477Z
UPDATE AD_Column SET MandatoryLogic='@C_Workplace_ID/0@=0 & @IsSharedPrinterConfig@=''N''',Updated=TO_TIMESTAMP('2025-10-26 09:41:09.477000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=569796
;

-- Field: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> Name
-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:42:12.276Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591439,755087,0,540652,0,TO_TIMESTAMP('2025-10-26 09:42:11.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',0,'D',0,'',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Name',0,0,110,0,1,1,TO_TIMESTAMP('2025-10-26 09:42:11.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-26T09:42:12.352Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-26T09:42:12.431Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-10-26T09:42:12.587Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755087
;

-- 2025-10-26T09:42:12.662Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755087)
;

-- Field: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> Beschreibung
-- Column: AD_Printer_Config.Description
-- 2025-10-26T09:42:39.133Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591440,755088,0,540652,0,TO_TIMESTAMP('2025-10-26 09:42:38.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Beschreibung',0,0,120,0,1,1,TO_TIMESTAMP('2025-10-26 09:42:38.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-26T09:42:39.209Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-26T09:42:39.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-10-26T09:42:39.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755088
;

-- 2025-10-26T09:42:39.492Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755088)
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Name
-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:43:31.204Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755087,0,540652,541046,637952,'F',TO_TIMESTAMP('2025-10-26 09:43:30.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',50,0,0,TO_TIMESTAMP('2025-10-26 09:43:30.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Beschreibung
-- Column: AD_Printer_Config.Description
-- 2025-10-26T09:43:54.291Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755088,0,540652,541046,637953,'F',TO_TIMESTAMP('2025-10-26 09:43:53.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Beschreibung',60,0,0,TO_TIMESTAMP('2025-10-26 09:43:53.742000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Name
-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:44:07.956Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-10-26 09:44:07.956000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637952
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2025-10-26T09:44:08.397Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-10-26 09:44:08.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=621188
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.AD_User_PrinterMatchingConfig_ID
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2025-10-26T09:44:08.839Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-10-26 09:44:08.839000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547845
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Host Key
-- Column: AD_Printer_Config.ConfigHostKey
-- 2025-10-26T09:44:09.289Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-10-26 09:44:09.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547846
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> config.Geteilt
-- Column: AD_Printer_Config.IsSharedPrinterConfig
-- 2025-10-26T09:44:09.731Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-10-26 09:44:09.731000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547847
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> config.Konfiguration
-- Column: AD_Printer_Config.AD_Printer_Config_Shared_ID
-- 2025-10-26T09:44:10.174Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-10-26 09:44:10.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547848
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 20 -> flags.Aktiv
-- Column: AD_Printer_Config.IsActive
-- 2025-10-26T09:44:10.620Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-10-26 09:44:10.620000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547849
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 20 -> org.Sektion
-- Column: AD_Printer_Config.AD_Org_ID
-- 2025-10-26T09:44:11.066Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-10-26 09:44:11.066000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=547850
;

-- 2025-10-26T09:44:43.140Z
/* DDL */ SELECT public.db_alter_table('AD_Printer_Config','ALTER TABLE public.AD_Printer_Config ADD COLUMN Description VARCHAR(2000)')
;

-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Name
-- Column: AD_Printer_Config.Name
-- 2025-10-26T09:46:21.171Z
UPDATE AD_UI_Element SET SeqNo=5,Updated=TO_TIMESTAMP('2025-10-26 09:46:21.171000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637952
;

-- Column: AD_Printer_Config.Description
-- 2025-10-26T09:50:21.065Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2025-10-26 09:50:21.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591440
;

