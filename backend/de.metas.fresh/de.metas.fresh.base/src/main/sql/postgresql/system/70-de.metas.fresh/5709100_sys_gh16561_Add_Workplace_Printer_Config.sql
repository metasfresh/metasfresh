-- Column: AD_Printer_Config.C_Workplace_ID
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T17:11:21.217Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587610,582772,0,30,540637,'C_Workplace_ID',TO_TIMESTAMP('2023-10-29 18:11:20','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.printing',0,10,'E','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','Y','N',0,'Arbeitsplatz',0,0,TO_TIMESTAMP('2023-10-29 18:11:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-29T17:11:21.220Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587610 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-29T17:11:21.240Z
/* DDL */  select update_Column_Translation_From_AD_Element(582772) 
;

-- 2023-10-29T17:11:38.550Z
/* DDL */ SELECT public.db_alter_table('AD_Printer_Config','ALTER TABLE public.AD_Printer_Config ADD COLUMN C_Workplace_ID NUMERIC(10)')
;

-- 2023-10-29T17:11:38.558Z
ALTER TABLE AD_Printer_Config ADD CONSTRAINT CWorkplace_ADPrinterConfig FOREIGN KEY (C_Workplace_ID) REFERENCES public.C_Workplace DEFERRABLE INITIALLY DEFERRED
;

-- Field: Drucker Zuordnung -> Konfiguration -> Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- Field: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T17:16:24.005Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587610,721725,0,540652,0,TO_TIMESTAMP('2023-10-29 18:16:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Arbeitsplatz',0,100,0,1,1,TO_TIMESTAMP('2023-10-29 18:16:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-29T17:16:24.007Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721725 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-29T17:16:24.009Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582772) 
;

-- 2023-10-29T17:16:24.018Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721725
;

-- 2023-10-29T17:16:24.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721725)
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T17:16:56.043Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721725,0,540652,541046,621188,'F',TO_TIMESTAMP('2023-10-29 18:16:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Arbeitsplatz',15,0,0,TO_TIMESTAMP('2023-10-29 18:16:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T17:17:04.883Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621188
;

-- UI Element: Drucker Zuordnung -> Konfiguration.AD_User_PrinterMatchingConfig_ID
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.AD_User_PrinterMatchingConfig_ID
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2023-10-29T17:17:04.892Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547845
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Host Key
-- Column: AD_Printer_Config.ConfigHostKey
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> default.Host Key
-- Column: AD_Printer_Config.ConfigHostKey
-- 2023-10-29T17:17:04.900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547846
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Geteilt
-- Column: AD_Printer_Config.IsSharedPrinterConfig
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> config.Geteilt
-- Column: AD_Printer_Config.IsSharedPrinterConfig
-- 2023-10-29T17:17:04.908Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547847
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Konfiguration
-- Column: AD_Printer_Config.AD_Printer_Config_Shared_ID
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 10 -> config.Konfiguration
-- Column: AD_Printer_Config.AD_Printer_Config_Shared_ID
-- 2023-10-29T17:17:04.917Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547848
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Aktiv
-- Column: AD_Printer_Config.IsActive
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 20 -> flags.Aktiv
-- Column: AD_Printer_Config.IsActive
-- 2023-10-29T17:17:04.925Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547849
;

-- UI Element: Drucker Zuordnung -> Konfiguration.Sektion
-- Column: AD_Printer_Config.AD_Org_ID
-- UI Element: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> main -> 20 -> org.Sektion
-- Column: AD_Printer_Config.AD_Org_ID
-- 2023-10-29T17:17:04.929Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-10-29 18:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547850
;

-- Column: AD_Printer_Config.IsSharedPrinterConfig
-- Column: AD_Printer_Config.IsSharedPrinterConfig
-- 2023-10-29T17:22:54.345Z
UPDATE AD_Column SET SeqNo=30,Updated=TO_TIMESTAMP('2023-10-29 18:22:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551597
;

-- Column: AD_Printer_Config.ConfigHostKey
-- Column: AD_Printer_Config.ConfigHostKey
-- 2023-10-29T17:23:02.666Z
UPDATE AD_Column SET SeqNo=20,Updated=TO_TIMESTAMP('2023-10-29 18:23:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551593
;

-- Column: AD_Printer_Config.C_Workplace_ID
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T17:23:28.653Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-10-29 18:23:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587610
;

-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2023-10-29T17:47:59.399Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-10-29 18:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569796
;

-- 2023-10-29T17:50:44.923Z
INSERT INTO t_alter_column values('ad_printer_config','AD_User_PrinterMatchingConfig_ID','NUMERIC(10)',null,null)
;

-- 2023-10-29T17:50:44.927Z
INSERT INTO t_alter_column values('ad_printer_config','AD_User_PrinterMatchingConfig_ID',null,'NULL',null)
;

-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2023-10-29T17:58:00.494Z
UPDATE AD_Column SET MandatoryLogic='@C_Workplace_ID/0@=0',Updated=TO_TIMESTAMP('2023-10-29 18:58:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569796
;

-- Field: Drucker Zuordnung -> Konfiguration -> Nutzer
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- Field: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> Nutzer
-- Column: AD_Printer_Config.AD_User_PrinterMatchingConfig_ID
-- 2023-10-29T17:59:53.729Z
UPDATE AD_Field SET DisplayLogic='@C_Workplace_ID@=0',Updated=TO_TIMESTAMP('2023-10-29 18:59:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=593827
;

-- Field: Drucker Zuordnung -> Konfiguration -> Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- Field: Drucker Zuordnung(540169,de.metas.printing) -> Konfiguration(540652,de.metas.printing) -> Arbeitsplatz
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T18:01:43.939Z
UPDATE AD_Field SET DisplayLogic='@AD_User_PrinterMatchingConfig_ID@=0',Updated=TO_TIMESTAMP('2023-10-29 19:01:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=721725
;


-- Column: AD_Printer_Config.C_Workplace_ID
-- Column: AD_Printer_Config.C_Workplace_ID
-- 2023-10-29T18:04:07.609Z
UPDATE AD_Column SET MandatoryLogic='@AD_User_PrinterMatchingConfig_ID/0@=0', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2023-10-29 19:04:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587610
;

