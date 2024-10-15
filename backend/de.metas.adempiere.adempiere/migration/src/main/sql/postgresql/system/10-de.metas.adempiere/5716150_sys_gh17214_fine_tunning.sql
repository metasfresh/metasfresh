-- Column: PickingProfile_PickingJobConfig.SeqNo
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- 2024-01-30T16:56:00.281Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587880,566,0,11,542390,'SeqNo',TO_TIMESTAMP('2024-01-30 18:55:59','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','de.metas.picking',0,22,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2024-01-30 18:55:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-30T16:56:00.286Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587880 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-30T16:56:00.294Z
/* DDL */  select update_Column_Translation_From_AD_Element(566) 
;

-- 2024-01-30T16:56:03.494Z
/* DDL */ SELECT public.db_alter_table('PickingProfile_PickingJobConfig','ALTER TABLE public.PickingProfile_PickingJobConfig ADD COLUMN SeqNo NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- Tab: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking)
-- UI Section: main
-- 2024-01-30T18:36:43.337Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547359,545941,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-01-30T18:36:43.339Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545941 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> main
-- UI Column: 10
-- 2024-01-30T18:36:43.557Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547244,545941,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> main -> 10
-- UI Element Group: default
-- 2024-01-30T18:36:43.753Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547244,551430,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking)
-- UI Section: main
-- 2024-01-30T18:36:43.860Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547360,545942,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2024-01-30T18:36:43.861Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545942 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main
-- UI Column: 10
-- 2024-01-30T18:36:43.961Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547245,545942,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10
-- UI Element Group: default
-- 2024-01-30T18:36:44.055Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547245,551431,TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-01-30 20:36:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Filter.Filter Typ
-- Column: PickingProfile_Filter.FilterType
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> main -> 10 -> default.Filter Typ
-- Column: PickingProfile_Filter.FilterType
-- 2024-01-30T18:39:40.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723863,0,547359,622143,551430,'F',TO_TIMESTAMP('2024-01-30 20:39:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Filter Typ',10,0,0,TO_TIMESTAMP('2024-01-30 20:39:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Filter.Aktiv
-- Column: PickingProfile_Filter.IsActive
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Filter(547359,de.metas.picking) -> main -> 10 -> default.Aktiv
-- Column: PickingProfile_Filter.IsActive
-- 2024-01-30T18:39:53.424Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723860,0,547359,622144,551430,'F',TO_TIMESTAMP('2024-01-30 20:39:53','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2024-01-30 20:39:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Eintragsfeld
-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10 -> default.Eintragsfeld
-- Column: PickingProfile_PickingJobConfig.PickingJobField
-- 2024-01-30T18:41:03.674Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723855,0,547360,622145,551431,'F',TO_TIMESTAMP('2024-01-30 20:41:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Eintragsfeld',10,0,0,TO_TIMESTAMP('2024-01-30 20:41:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Aktiv
-- Column: PickingProfile_PickingJobConfig.IsActive
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10 -> default.Aktiv
-- Column: PickingProfile_PickingJobConfig.IsActive
-- 2024-01-30T18:41:22.064Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723852,0,547360,622146,551431,'F',TO_TIMESTAMP('2024-01-30 20:41:21','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',20,0,0,TO_TIMESTAMP('2024-01-30 20:41:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Display in summary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10 -> default.Display in summary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- 2024-01-30T18:41:34.057Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723856,0,547360,622147,551431,'F',TO_TIMESTAMP('2024-01-30 20:41:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Display in summary',30,0,0,TO_TIMESTAMP('2024-01-30 20:41:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Display in detailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10 -> default.Display in detailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- 2024-01-30T18:41:41.820Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723857,0,547360,622148,551431,'F',TO_TIMESTAMP('2024-01-30 20:41:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Display in detailed',40,0,0,TO_TIMESTAMP('2024-01-30 20:41:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Mobile UI Kommissionierprofil -> Picking Job Config -> Reihenfolge
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> Reihenfolge
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- 2024-01-30T18:42:29.031Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587880,723864,0,547360,TO_TIMESTAMP('2024-01-30 20:42:28','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'de.metas.picking','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2024-01-30 20:42:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-01-30T18:42:29.034Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=723864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-01-30T18:42:29.041Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2024-01-30T18:42:29.064Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723864
;

-- 2024-01-30T18:42:29.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723864)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Reihenfolge
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10 -> default.Reihenfolge
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- 2024-01-30T18:42:44.965Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,723864,0,547360,622149,551431,'F',TO_TIMESTAMP('2024-01-30 20:42:44','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',50,0,0,TO_TIMESTAMP('2024-01-30 20:42:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Reihenfolge
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 10 -> default.Reihenfolge
-- Column: PickingProfile_PickingJobConfig.SeqNo
-- 2024-01-30T18:43:03.199Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2024-01-30 20:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622149
;

-- UI Section: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main
-- UI Column: 20
-- 2024-01-30T18:47:01.327Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547246,545942,TO_TIMESTAMP('2024-01-30 20:47:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-01-30 20:47:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 20
-- UI Element Group: flags
-- 2024-01-30T18:47:17.172Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547246,551432,TO_TIMESTAMP('2024-01-30 20:47:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2024-01-30 20:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Aktiv
-- Column: PickingProfile_PickingJobConfig.IsActive
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 20 -> flags.Aktiv
-- Column: PickingProfile_PickingJobConfig.IsActive
-- 2024-01-30T18:47:50.759Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551432, SeqNo=10,Updated=TO_TIMESTAMP('2024-01-30 20:47:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622146
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Display in summary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 20 -> flags.Display in summary
-- Column: PickingProfile_PickingJobConfig.IsDisplayInSummary
-- 2024-01-30T18:47:59.317Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551432, SeqNo=20,Updated=TO_TIMESTAMP('2024-01-30 20:47:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622147
;

-- UI Element: Mobile UI Kommissionierprofil -> Picking Job Config.Display in detailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Picking Job Config(547360,de.metas.picking) -> main -> 20 -> flags.Display in detailed
-- Column: PickingProfile_PickingJobConfig.IsDisplayInDetailed
-- 2024-01-30T18:48:07.085Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551432, SeqNo=30,Updated=TO_TIMESTAMP('2024-01-30 20:48:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=622148
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:08.608Z
UPDATE AD_Ref_List_Trl SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:18.541Z
UPDATE AD_Ref_List_Trl SET Name='Handover address',Updated=TO_TIMESTAMP('2024-01-31 01:13:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:20.758Z
UPDATE AD_Ref_List_Trl SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543619
;

-- Reference Item: PickingFilter_Options -> HandoverLocation_HandoverLocation
-- 2024-01-30T23:13:23.070Z
UPDATE AD_Ref_List_Trl SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543619
;

-- Reference: PickingFilter_Options
-- Value: HandoverLocation
-- ValueName: HandoverLocation
-- 2024-01-30T23:13:35.869Z
UPDATE AD_Ref_List SET Name='Übergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:13:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543619
;

-- Reference: PickingFilter_Options
-- Value: DeliveryDate
-- ValueName: DeliveryDate
-- 2024-01-30T23:13:47.143Z
UPDATE AD_Ref_List SET Name='Lieferdatum',Updated=TO_TIMESTAMP('2024-01-31 01:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543618
;

-- Reference: PickingFilter_Options
-- Value: Customer
-- ValueName: Customer
-- 2024-01-30T23:13:52.546Z
UPDATE AD_Ref_List SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-31 01:13:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543617
;

-- 2024-01-30T23:14:54.482Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2024-01-31 01:14:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540786
;

-- 2024-01-30T23:15:08.315Z
DROP INDEX IF EXISTS unq_idx_profile_filter
;

-- 2024-01-30T23:15:08.317Z
CREATE UNIQUE INDEX unq_idx_profile_filter ON PickingProfile_Filter (MobileUI_UserProfile_Picking_ID,FilterType) WHERE isActive='Y'
;

-- 2024-01-30T23:16:13.131Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2024-01-31 01:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540787
;

-- 2024-01-30T23:16:17.150Z
DROP INDEX IF EXISTS unq_idx_profile_field
;

-- 2024-01-30T23:16:17.152Z
CREATE UNIQUE INDEX unq_idx_profile_field ON PickingProfile_PickingJobConfig (MobileUI_UserProfile_Picking_ID,PickingJobField) WHERE isActive='Y'
;

-- Element: IsDisplayInDetailed
-- 2024-01-30T23:16:59.200Z
UPDATE AD_Element_Trl SET Name='Display in detailed view', PrintName='Display in detailed view',Updated=TO_TIMESTAMP('2024-01-31 01:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582931 AND AD_Language='de_CH'
;

-- 2024-01-30T23:16:59.213Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582931,'de_CH') 
;

-- Element: IsDisplayInDetailed
-- 2024-01-30T23:17:04.955Z
UPDATE AD_Element_Trl SET Name='Display in detailed view', PrintName='Display in detailed view',Updated=TO_TIMESTAMP('2024-01-31 01:17:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582931 AND AD_Language='en_US'
;

-- 2024-01-30T23:17:04.956Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582931,'en_US') 
;

-- Element: IsDisplayInDetailed
-- 2024-01-30T23:17:30.577Z
UPDATE AD_Element_Trl SET Name='Anzeige in der Detailansicht', PrintName='Anzeige in der Detailansicht',Updated=TO_TIMESTAMP('2024-01-31 01:17:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582931 AND AD_Language='de_CH'
;

-- 2024-01-30T23:17:30.578Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582931,'de_CH') 
;

-- Element: IsDisplayInDetailed
-- 2024-01-30T23:17:33.889Z
UPDATE AD_Element_Trl SET Name='Anzeige in der Detailansicht', PrintName='Anzeige in der Detailansicht',Updated=TO_TIMESTAMP('2024-01-31 01:17:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582931 AND AD_Language='fr_CH'
;

-- 2024-01-30T23:17:33.890Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582931,'fr_CH') 
;

-- Element: IsDisplayInDetailed
-- 2024-01-30T23:17:37.138Z
UPDATE AD_Element_Trl SET Name='Anzeige in der Detailansicht', PrintName='Anzeige in der Detailansicht',Updated=TO_TIMESTAMP('2024-01-31 01:17:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582931 AND AD_Language='de_DE'
;

-- 2024-01-30T23:17:37.139Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582931,'de_DE') 
;

-- 2024-01-30T23:17:37.150Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582931,'de_DE') 
;

-- Element: IsDisplayInSummary
-- 2024-01-30T23:20:44.919Z
UPDATE AD_Element_Trl SET Name='Anzeige in der Übersicht', PrintName='Anzeige in der Übersicht',Updated=TO_TIMESTAMP('2024-01-31 01:20:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582930 AND AD_Language='de_CH'
;

-- 2024-01-30T23:20:44.920Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582930,'de_CH') 
;

-- Element: IsDisplayInSummary
-- 2024-01-30T23:20:49.210Z
UPDATE AD_Element_Trl SET Name='Anzeige in der Übersicht', PrintName='Anzeige in der Übersicht',Updated=TO_TIMESTAMP('2024-01-31 01:20:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582930 AND AD_Language='de_DE'
;

-- 2024-01-30T23:20:49.219Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582930,'de_DE') 
;

-- 2024-01-30T23:20:49.220Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582930,'de_DE') 
;

-- Element: IsDisplayInSummary
-- 2024-01-30T23:21:03.059Z
UPDATE AD_Element_Trl SET Name='Anzeige in der Übersicht', PrintName='Anzeige in der Übersicht',Updated=TO_TIMESTAMP('2024-01-31 01:21:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582930 AND AD_Language='fr_CH'
;

-- 2024-01-30T23:21:03.060Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582930,'fr_CH') 
;

-- Element: PickingProfile_PickingJobConfig_ID
-- 2024-01-30T23:21:45.256Z
UPDATE AD_Element_Trl SET Name='Picking Job UI Config', PrintName='Picking Job UI Config',Updated=TO_TIMESTAMP('2024-01-31 01:21:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582928 AND AD_Language='de_CH'
;

-- 2024-01-30T23:21:45.256Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582928,'de_CH') 
;

-- Element: PickingProfile_PickingJobConfig_ID
-- 2024-01-30T23:21:52.127Z
UPDATE AD_Element_Trl SET Name='Picking Job UI Config', PrintName='Picking Job UI Config',Updated=TO_TIMESTAMP('2024-01-31 01:21:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582928 AND AD_Language='en_US'
;

-- 2024-01-30T23:21:52.130Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582928,'en_US') 
;

-- Element: PickingProfile_PickingJobConfig_ID
-- 2024-01-30T23:23:05.817Z
UPDATE AD_Element_Trl SET Name='Kommissionier-Job UI Konfig', PrintName='Kommissionier-Job UI Konfig',Updated=TO_TIMESTAMP('2024-01-31 01:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582928 AND AD_Language='de_CH'
;

-- 2024-01-30T23:23:05.818Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582928,'de_CH') 
;

-- Element: PickingProfile_PickingJobConfig_ID
-- 2024-01-30T23:23:09.357Z
UPDATE AD_Element_Trl SET Name='Kommissionier-Job UI Konfig', PrintName='Kommissionier-Job UI Konfig',Updated=TO_TIMESTAMP('2024-01-31 01:23:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582928 AND AD_Language='de_DE'
;

-- 2024-01-30T23:23:09.358Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582928,'de_DE') 
;

-- 2024-01-30T23:23:09.363Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582928,'de_DE') 
;

-- Element: PickingProfile_PickingJobConfig_ID
-- 2024-01-30T23:23:26.875Z
UPDATE AD_Element_Trl SET Name='Kommissionier-Job UI Konfig', PrintName='Kommissionier-Job UI Konfig',Updated=TO_TIMESTAMP('2024-01-31 01:23:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582928 AND AD_Language='fr_CH'
;

-- 2024-01-30T23:23:26.877Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582928,'fr_CH') 
;

-- Element: PickingProfile_Filter_ID
-- 2024-01-30T23:23:58.484Z
UPDATE AD_Element_Trl SET Name='Filter', PrintName='Filter',Updated=TO_TIMESTAMP('2024-01-31 01:23:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582926 AND AD_Language='de_CH'
;

-- 2024-01-30T23:23:58.486Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582926,'de_CH') 
;

-- Element: PickingProfile_Filter_ID
-- 2024-01-30T23:24:01.085Z
UPDATE AD_Element_Trl SET Name='Filter', PrintName='Filter',Updated=TO_TIMESTAMP('2024-01-31 01:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582926 AND AD_Language='en_US'
;

-- 2024-01-30T23:24:01.086Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582926,'en_US') 
;

-- Element: PickingProfile_Filter_ID
-- 2024-01-30T23:24:04.207Z
UPDATE AD_Element_Trl SET Name='Filter', PrintName='Filter',Updated=TO_TIMESTAMP('2024-01-31 01:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582926 AND AD_Language='de_DE'
;

-- 2024-01-30T23:24:04.209Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582926,'de_DE') 
;

-- 2024-01-30T23:24:04.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582926,'de_DE') 
;

-- Element: PickingProfile_Filter_ID
-- 2024-01-30T23:24:16.808Z
UPDATE AD_Element_Trl SET Name='Filter', PrintName='Filter',Updated=TO_TIMESTAMP('2024-01-31 01:24:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582926 AND AD_Language='fr_CH'
;

-- 2024-01-30T23:24:16.809Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582926,'fr_CH') 
;

-- Reference Item: PickingJobField_Options -> Customer_Customer
-- 2024-01-30T23:26:01.885Z
UPDATE AD_Ref_List_Trl SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-31 01:26:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543621
;

-- Reference Item: PickingJobField_Options -> Customer_Customer
-- 2024-01-30T23:26:04.975Z
UPDATE AD_Ref_List_Trl SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-31 01:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543621
;

-- Reference Item: PickingJobField_Options -> Customer_Customer
-- 2024-01-30T23:26:08.841Z
UPDATE AD_Ref_List_Trl SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-31 01:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543621
;

-- Reference: PickingJobField_Options
-- Value: Customer
-- ValueName: Customer
-- 2024-01-30T23:26:15.696Z
UPDATE AD_Ref_List SET Name='Kunde',Updated=TO_TIMESTAMP('2024-01-31 01:26:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543621
;

-- Reference: PickingJobField_Options
-- Value: DocumentNo
-- ValueName: DocumentNo
-- 2024-01-30T23:26:41.180Z
UPDATE AD_Ref_List SET Name='Dokument Nr.',Updated=TO_TIMESTAMP('2024-01-31 01:26:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543620
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-30T23:26:44.473Z
UPDATE AD_Ref_List_Trl SET Name='Dokument Nr.',Updated=TO_TIMESTAMP('2024-01-31 01:26:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543620
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-30T23:26:47.582Z
UPDATE AD_Ref_List_Trl SET Name='Dokument Nr.',Updated=TO_TIMESTAMP('2024-01-31 01:26:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543620
;

-- Reference Item: PickingJobField_Options -> DocumentNo_DocumentNo
-- 2024-01-30T23:26:51.085Z
UPDATE AD_Ref_List_Trl SET Name='Dokument Nr.',Updated=TO_TIMESTAMP('2024-01-31 01:26:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543620
;

-- Reference: PickingJobField_Options
-- Value: HandoverLocation
-- ValueName: Handover Location
-- 2024-01-30T23:27:10.016Z
UPDATE AD_Ref_List SET Name='Handover location',Updated=TO_TIMESTAMP('2024-01-31 01:27:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:27:16.263Z
UPDATE AD_Ref_List_Trl SET Name='Handover location',Updated=TO_TIMESTAMP('2024-01-31 01:27:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:27:19.006Z
UPDATE AD_Ref_List_Trl SET Name='Handover location',Updated=TO_TIMESTAMP('2024-01-31 01:27:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:28:32.424Z
UPDATE AD_Ref_List_Trl SET Name='Ubergabeaddresse',Updated=TO_TIMESTAMP('2024-01-31 01:28:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:28:37.531Z
UPDATE AD_Ref_List_Trl SET Name='Ubergabeaddresse',Updated=TO_TIMESTAMP('2024-01-31 01:28:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:28:57.463Z
UPDATE AD_Ref_List_Trl SET Name='Ubergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:28:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:28:59.601Z
UPDATE AD_Ref_List_Trl SET Name='Ubergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:28:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> HandoverLocation_Handover Location
-- 2024-01-30T23:29:03.764Z
UPDATE AD_Ref_List_Trl SET Name='Ubergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:29:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543624
;

-- Reference Item: PickingJobField_Options -> PreparationDate_Date Ready
-- 2024-01-30T23:32:27.034Z
UPDATE AD_Ref_List_Trl SET Name='Bereitstellungsdatum',Updated=TO_TIMESTAMP('2024-01-31 01:32:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543623
;

-- Reference Item: PickingJobField_Options -> PreparationDate_Date Ready
-- 2024-01-30T23:32:29.870Z
UPDATE AD_Ref_List_Trl SET Name='Bereitstellungsdatum',Updated=TO_TIMESTAMP('2024-01-31 01:32:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543623
;

-- Reference Item: PickingJobField_Options -> PreparationDate_Date Ready
-- 2024-01-30T23:32:37.224Z
UPDATE AD_Ref_List_Trl SET Name='Bereitstellungsdatum',Updated=TO_TIMESTAMP('2024-01-31 01:32:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543623
;

-- Reference: PickingJobField_Options
-- Value: PreparationDate
-- ValueName: Date Ready
-- 2024-01-30T23:32:42.199Z
UPDATE AD_Ref_List SET Name='Bereitstellungsdatum',Updated=TO_TIMESTAMP('2024-01-31 01:32:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543623
;

-- Reference: PickingJobField_Options
-- Value: ShipToLocation
-- ValueName: Delivery Address
-- 2024-01-30T23:33:52.866Z
UPDATE AD_Ref_List SET Name='Lieferadresse',Updated=TO_TIMESTAMP('2024-01-31 01:33:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543622
;

-- Reference Item: PickingJobField_Options -> ShipToLocation_Delivery Address
-- 2024-01-30T23:33:57.507Z
UPDATE AD_Ref_List_Trl SET Name='Lieferadresse',Updated=TO_TIMESTAMP('2024-01-31 01:33:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543622
;

-- Reference Item: PickingJobField_Options -> ShipToLocation_Delivery Address
-- 2024-01-30T23:34:01.016Z
UPDATE AD_Ref_List_Trl SET Name='Lieferadresse',Updated=TO_TIMESTAMP('2024-01-31 01:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543622
;

-- Reference Item: PickingJobField_Options -> ShipToLocation_Delivery Address
-- 2024-01-30T23:34:04.387Z
UPDATE AD_Ref_List_Trl SET Name='Lieferadresse',Updated=TO_TIMESTAMP('2024-01-31 01:34:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543622
;

-- Reference: PickingJobField_Options
-- Value: HandoverLocation
-- ValueName: Handover Location
-- 2024-01-30T23:34:15.946Z
UPDATE AD_Ref_List SET Name='Ubergabeadresse',Updated=TO_TIMESTAMP('2024-01-31 01:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543624
;

-- Column: M_Picking_Job.HandOver_Partner_ID
-- Column: M_Picking_Job.HandOver_Partner_ID
-- 2024-01-31T06:37:55.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587881,542280,0,18,541252,541906,'XX','HandOver_Partner_ID',TO_TIMESTAMP('2024-01-31 08:37:55','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.handlingunits',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Übergabe-Partner',0,0,TO_TIMESTAMP('2024-01-31 08:37:55','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-31T06:37:56.013Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-31T06:37:56.030Z
/* DDL */  select update_Column_Translation_From_AD_Element(542280) 
;

-- 2024-01-31T06:37:59.390Z
/* DDL */ SELECT public.db_alter_table('M_Picking_Job','ALTER TABLE public.M_Picking_Job ADD COLUMN HandOver_Partner_ID NUMERIC(10)')
;

-- 2024-01-31T06:37:59.425Z
ALTER TABLE M_Picking_Job ADD CONSTRAINT HandOverPartner_MPickingJob FOREIGN KEY (HandOver_Partner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Packageable_V.HandOver_Partner_ID
-- Column: M_Packageable_V.HandOver_Partner_ID
-- 2024-01-31T06:39:03.769Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,587882,542280,0,18,541252,540823,'XX','HandOver_Partner_ID',TO_TIMESTAMP('2024-01-31 08:39:03','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.inoutcandidate',10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Übergabe-Partner',0,TO_TIMESTAMP('2024-01-31 08:39:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-01-31T06:39:03.773Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-01-31T06:39:03.780Z
/* DDL */  select update_Column_Translation_From_AD_Element(542280) 
;

-- Column: M_Packageable_V.HandOver_Location_ID
-- Column: M_Packageable_V.HandOver_Location_ID
-- 2024-01-31T06:39:11.661Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-01-31 08:39:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=587878
;

