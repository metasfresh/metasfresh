-- Run mode: SWING_CLIENT

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Co-Produkt
-- Column: ModCntr_Settings.M_Co_Product_ID
-- 2024-04-04T07:53:39.607Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588087,727314,0,547013,0,TO_TIMESTAMP('2024-04-04 10:53:39.442','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Co-Produkt',0,10,0,1,1,TO_TIMESTAMP('2024-04-04 10:53:39.442','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T07:53:39.609Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T07:53:39.641Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583066)
;

-- 2024-04-04T07:53:39.652Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727314
;

-- 2024-04-04T07:53:39.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727314)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Verarbeitetes Produkt
-- Column: ModCntr_Settings.M_Processed_Product_ID
-- 2024-04-04T07:53:50.271Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588086,727315,0,547013,0,TO_TIMESTAMP('2024-04-04 10:53:50.119','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Verarbeitetes Produkt',0,20,0,1,1,TO_TIMESTAMP('2024-04-04 10:53:50.119','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T07:53:50.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T07:53:50.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583069)
;

-- 2024-04-04T07:53:50.276Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727315
;

-- 2024-04-04T07:53:50.277Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727315)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> Rohprodukt
-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-04T07:54:47.590Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588085,727316,0,547013,0,TO_TIMESTAMP('2024-04-04 10:54:47.458','YYYY-MM-DD HH24:MI:SS.US'),100,0,'de.metas.contracts',0,'Y','Y','Y','N','N','N','N','N','N','Rohprodukt',0,30,0,1,1,TO_TIMESTAMP('2024-04-04 10:54:47.458','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-04T07:54:47.591Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-04T07:54:47.592Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583068)
;

-- 2024-04-04T07:54:47.594Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727316
;

-- 2024-04-04T07:54:47.595Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727316)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Rohprodukt
-- Column: ModCntr_Settings.M_Raw_Product_ID
-- 2024-04-04T07:55:22.512Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727316,0,547013,550782,624022,'F',TO_TIMESTAMP('2024-04-04 10:55:22.354','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rohprodukt',20,0,0,TO_TIMESTAMP('2024-04-04 10:55:22.354','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Kalender
-- Column: ModCntr_Settings.C_Calendar_ID
-- 2024-04-04T07:55:29.999Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-04-04 10:55:29.999','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617986
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Jahr
-- Column: ModCntr_Settings.C_Year_ID
-- 2024-04-04T07:55:34.177Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2024-04-04 10:55:34.177','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617987
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Verarbeitetes Produkt
-- Column: ModCntr_Settings.M_Processed_Product_ID
-- 2024-04-04T07:55:59.318Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727315,0,547013,550782,624023,'F',TO_TIMESTAMP('2024-04-04 10:55:59.186','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Verarbeitetes Produkt',30,0,0,TO_TIMESTAMP('2024-04-04 10:55:59.186','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Einstellungen für modulare Verträge(547013,de.metas.contracts) -> main -> 10 -> default.Co-Produkt
-- Column: ModCntr_Settings.M_Co_Product_ID
-- 2024-04-04T07:56:08.400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727314,0,547013,550782,624024,'F',TO_TIMESTAMP('2024-04-04 10:56:08.271','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Co-Produkt',40,0,0,TO_TIMESTAMP('2024-04-04 10:56:08.271','YYYY-MM-DD HH24:MI:SS.US'),100)
;

