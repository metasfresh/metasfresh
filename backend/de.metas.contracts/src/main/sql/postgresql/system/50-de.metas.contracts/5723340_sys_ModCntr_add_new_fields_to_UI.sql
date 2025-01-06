-- Run mode: SWING_CLIENT

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Total Zins
-- Column: ModCntr_InvoicingGroup.TotalInterest
-- 2024-05-09T03:09:44.397Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588197,728697,0,547205,0,TO_TIMESTAMP('2024-05-09 06:09:44.107','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Total Zins',0,10,0,1,1,TO_TIMESTAMP('2024-05-09 06:09:44.107','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-09T03:09:44.406Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728697 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-09T03:09:44.436Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583091)
;

-- 2024-05-09T03:09:44.447Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728697
;

-- 2024-05-09T03:09:44.450Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728697)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Erntekalender
-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-05-09T03:10:04.900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588195,728698,0,547205,0,TO_TIMESTAMP('2024-05-09 06:10:04.759','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Erntekalender',0,20,0,1,1,TO_TIMESTAMP('2024-05-09 06:10:04.759','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-09T03:10:04.901Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728698 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-09T03:10:04.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157)
;

-- 2024-05-09T03:10:04.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728698
;

-- 2024-05-09T03:10:04.917Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728698)
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Erntejahr
-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-05-09T03:10:25.130Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588196,728699,0,547205,0,TO_TIMESTAMP('2024-05-09 06:10:25.002','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Erntejahr',0,30,0,1,1,TO_TIMESTAMP('2024-05-09 06:10:25.002','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-09T03:10:25.131Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728699 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-09T03:10:25.133Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471)
;

-- 2024-05-09T03:10:25.138Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728699
;

-- 2024-05-09T03:10:25.139Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728699)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Erntekalender
-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-05-09T05:09:18.138Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728698,0,547205,551112,624727,'F',TO_TIMESTAMP('2024-05-09 08:09:17.878','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntekalender',50,0,0,TO_TIMESTAMP('2024-05-09 08:09:17.878','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Erntejahr
-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-05-09T05:09:39.507Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728699,0,547205,551112,624728,'F',TO_TIMESTAMP('2024-05-09 08:09:39.348','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntejahr',60,0,0,TO_TIMESTAMP('2024-05-09 08:09:39.348','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20
-- UI Element Group: amts
-- 2024-05-09T05:10:07.670Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547063,551831,TO_TIMESTAMP('2024-05-09 08:10:07.55','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','amts',30,TO_TIMESTAMP('2024-05-09 08:10:07.55','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20
-- UI Element Group: amts
-- 2024-05-09T05:10:13.541Z
UPDATE AD_UI_ElementGroup SET SeqNo=15,Updated=TO_TIMESTAMP('2024-05-09 08:10:13.541','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551831
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> amts.Total Zins
-- Column: ModCntr_InvoicingGroup.TotalInterest
-- 2024-05-09T05:10:23.430Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728697,0,547205,551831,624729,'F',TO_TIMESTAMP('2024-05-09 08:10:23.308','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Total Zins',10,0,0,TO_TIMESTAMP('2024-05-09 08:10:23.308','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Gültig ab
-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2024-05-09T05:22:55.901Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620341
;

-- 2024-05-09T05:22:55.902Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720158
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Gültig ab
-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2024-05-09T05:22:55.905Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=720158
;

-- 2024-05-09T05:22:55.908Z
DELETE FROM AD_Field WHERE AD_Field_ID=720158
;

-- 2024-05-09T05:22:55.910Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE ModCntr_InvoicingGroup DROP COLUMN IF EXISTS ValidFrom')
;

-- Column: ModCntr_InvoicingGroup.ValidFrom
-- 2024-05-09T05:22:56.066Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587288
;

-- 2024-05-09T05:22:56.070Z
DELETE FROM AD_Column WHERE AD_Column_ID=587288
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Gültig bis
-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2024-05-09T05:23:04.521Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=620342
;

-- 2024-05-09T05:23:04.522Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720159
;

-- Field: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> Gültig bis
-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2024-05-09T05:23:04.525Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=720159
;

-- 2024-05-09T05:23:04.527Z
DELETE FROM AD_Field WHERE AD_Field_ID=720159
;

-- 2024-05-09T05:23:04.529Z
/* DDL */ SELECT public.db_alter_table('ModCntr_InvoicingGroup','ALTER TABLE ModCntr_InvoicingGroup DROP COLUMN IF EXISTS ValidTo')
;

-- Column: ModCntr_InvoicingGroup.ValidTo
-- 2024-05-09T05:23:04.610Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=587289
;

-- 2024-05-09T05:23:04.613Z
DELETE FROM AD_Column WHERE AD_Column_ID=587289
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Zinssatz
-- Column: ModCntr_Settings.InterestRate
-- 2024-05-09T05:49:10.980Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588193,728700,0,547013,0,TO_TIMESTAMP('2024-05-09 08:49:10.825','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Zinssatz',0,50,0,1,1,TO_TIMESTAMP('2024-05-09 08:49:10.825','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-09T05:49:10.983Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728700 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-09T05:49:10.984Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583090)
;

-- 2024-05-09T05:49:10.987Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728700
;

-- 2024-05-09T05:49:10.988Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728700)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Zusätzlicher Zinstage
-- Column: ModCntr_Settings.AddInterestDays
-- 2024-05-09T05:49:34.468Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588194,728701,0,547013,0,TO_TIMESTAMP('2024-05-09 08:49:34.321','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Zusätzlicher Zinstage',0,60,0,1,1,TO_TIMESTAMP('2024-05-09 08:49:34.321','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-09T05:49:34.469Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728701 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-05-09T05:49:34.470Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583089)
;

-- 2024-05-09T05:49:34.473Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728701
;

-- 2024-05-09T05:49:34.475Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728701)
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Erntejahr
-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-05-09T05:50:23.801Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-05-09 08:50:23.801','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624728
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 10 -> main.Erntekalender
-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-05-09T05:50:23.806Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-05-09 08:50:23.806','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624727
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> amts.Total Zins
-- Column: ModCntr_InvoicingGroup.TotalInterest
-- 2024-05-09T05:50:23.810Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-05-09 08:50:23.81','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=624729
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> active.Aktiv
-- Column: ModCntr_InvoicingGroup.IsActive
-- 2024-05-09T05:50:23.815Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-05-09 08:50:23.815','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620343
;

-- UI Element: Rechnungsgruppe(541728,D) -> Rechnungsgruppe(547205,D) -> main -> 20 -> org.Organisation
-- Column: ModCntr_InvoicingGroup.AD_Org_ID
-- 2024-05-09T05:50:23.819Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-05-09 08:50:23.819','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620344
;

-- Column: ModCntr_InvoicingGroup.C_Harvesting_Calendar_ID
-- 2024-05-09T05:51:26.890Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-09 08:51:26.89','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588195
;

-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-05-09T05:51:39.540Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-09 08:51:39.54','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588196
;

-- Column: ModCntr_InvoicingGroup.Group_Product_ID
-- 2024-05-09T05:51:46.805Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-05-09 08:51:46.805','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587290
;

-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-05-09T05:53:06.353Z
UPDATE AD_Column SET AD_Val_Rule_ID=198,Updated=TO_TIMESTAMP('2024-05-09 08:53:06.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588196
;

-- Column: ModCntr_InvoicingGroup.Harvesting_Year_ID
-- 2024-05-09T05:53:58.948Z
UPDATE AD_Column SET AD_Val_Rule_ID=540655,Updated=TO_TIMESTAMP('2024-05-09 08:53:58.948','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588196
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Zusätzlicher Zinstage
-- Column: ModCntr_Settings.AddInterestDays
-- 2024-05-09T05:55:58.853Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728701,0,547013,551809,624730,'F',TO_TIMESTAMP('2024-05-09 08:55:58.713','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zusätzlicher Zinstage',20,0,0,TO_TIMESTAMP('2024-05-09 08:55:58.713','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 20 -> additional.Zinssatz
-- Column: ModCntr_Settings.InterestRate
-- 2024-05-09T05:56:11.567Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728700,0,547013,551809,624731,'F',TO_TIMESTAMP('2024-05-09 08:56:11.426','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Zinssatz',30,0,0,TO_TIMESTAMP('2024-05-09 08:56:11.426','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: ModCntr_Settings.AddInterestDays
-- 2024-05-09T06:02:39.533Z
UPDATE AD_Column SET AD_Reference_ID=11, DefaultValue='0',Updated=TO_TIMESTAMP('2024-05-09 09:02:39.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588194
;

-- 2024-05-09T06:02:42.093Z
INSERT INTO t_alter_column values('modcntr_settings','AddInterestDays','NUMERIC(10)',null,'0')
;

-- 2024-05-09T06:02:42.109Z
UPDATE ModCntr_Settings SET AddInterestDays=0 WHERE AddInterestDays IS NULL
;
