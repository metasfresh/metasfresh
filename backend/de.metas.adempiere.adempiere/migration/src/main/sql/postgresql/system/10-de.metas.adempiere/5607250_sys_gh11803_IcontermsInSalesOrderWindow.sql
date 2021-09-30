-- 2021-09-30T13:36:51.793Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569792,661052,0,186,TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100,360,'D','Y','N','N','N','N','N','N','N','Übergabe adresse',TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-30T13:36:51.795Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=661052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-30T13:36:51.815Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577444) 
;

-- 2021-09-30T13:36:51.822Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661052
;

-- 2021-09-30T13:36:51.823Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(661052)
;

-- 2021-09-30T13:36:51.885Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570876,661053,0,186,TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Verbuchungsfehler',TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-30T13:36:51.886Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=661053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-30T13:36:51.887Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755) 
;

-- 2021-09-30T13:36:51.889Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661053
;

-- 2021-09-30T13:36:51.890Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(661053)
;

-- 2021-09-30T13:36:51.949Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572014,661054,0,186,TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100,'Sales Responsible Internal',10,'D','','Y','N','N','N','N','N','N','N','Sales Responsible',TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-30T13:36:51.950Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=661054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-30T13:36:51.952Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543385) 
;

-- 2021-09-30T13:36:51.954Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661054
;

-- 2021-09-30T13:36:51.954Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(661054)
;

-- 2021-09-30T13:36:52.006Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574447,661055,0,186,TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100,'Wenn eine Bestellung aus einem externen System synchronisiert wurde, kann dieses Feld die betreffende URL enthalten',1000,'D','Y','N','N','N','N','N','N','N','Bestell-URL im externen system',TO_TIMESTAMP('2021-09-30 15:36:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-30T13:36:52.008Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=661055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-30T13:36:52.010Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579351) 
;

-- 2021-09-30T13:36:52.015Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661055
;

-- 2021-09-30T13:36:52.015Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(661055)
;

-- 2021-09-30T13:36:52.091Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,577195,661056,0,186,TO_TIMESTAMP('2021-09-30 15:36:52','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2021-09-30 15:36:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-09-30T13:36:52.092Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=661056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-09-30T13:36:52.093Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927) 
;

-- 2021-09-30T13:36:52.095Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=661056
;

-- 2021-09-30T13:36:52.095Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(661056)
;

-- 2021-09-30T13:38:32.240Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2021-09-30 15:38:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544770
;

-- 2021-09-30T13:38:59.449Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,661056,0,186,540499,591844,'F',TO_TIMESTAMP('2021-09-30 15:38:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Incoterms',131,0,0,TO_TIMESTAMP('2021-09-30 15:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

