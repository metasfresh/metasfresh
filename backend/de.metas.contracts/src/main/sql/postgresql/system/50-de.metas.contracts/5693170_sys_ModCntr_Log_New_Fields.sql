-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Document Type
-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-06-23T16:31:04.213484900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586851,716453,0,547012,TO_TIMESTAMP('2023-06-23 19:31:03.573','YYYY-MM-DD HH24:MI:SS.US'),100,250,'D','Y','N','N','N','N','N','N','N','Document Type',TO_TIMESTAMP('2023-06-23 19:31:03.573','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:04.251510500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716453 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:04.297263900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582470) 
;

-- 2023-06-23T16:31:04.391403900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716453
;

-- 2023-06-23T16:31:04.429382500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716453)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Menge
-- Column: ModCntr_Log.Qty
-- 2023-06-23T16:31:05.076372400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586852,716454,0,547012,TO_TIMESTAMP('2023-06-23 19:31:04.515','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge',10,'D','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2023-06-23 19:31:04.515','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:05.114340600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716454 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:05.153525400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526) 
;

-- 2023-06-23T16:31:05.216108200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716454
;

-- 2023-06-23T16:31:05.254067600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716454)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Erntejahr
-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-06-23T16:31:05.922864100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586853,716455,0,547012,TO_TIMESTAMP('2023-06-23 19:31:05.356','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2023-06-23 19:31:05.356','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:05.961259Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716455 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:06.000342900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-06-23T16:31:06.041581Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716455
;

-- 2023-06-23T16:31:06.079040100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716455)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Maßeinheit
-- Column: ModCntr_Log.C_UOM_ID
-- 2023-06-23T16:31:06.773738500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586854,716456,0,547012,TO_TIMESTAMP('2023-06-23 19:31:06.156','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2023-06-23 19:31:06.156','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:06.812088100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716456 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:06.851129800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2023-06-23T16:31:06.900255500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716456
;

-- 2023-06-23T16:31:06.937633700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716456)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Betrag
-- Column: ModCntr_Log.Amount
-- 2023-06-23T16:31:07.628930Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586855,716457,0,547012,TO_TIMESTAMP('2023-06-23 19:31:07.016','YYYY-MM-DD HH24:MI:SS.US'),100,'Betrag in einer definierten Währung',10,'D','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','N','N','N','N','N','Betrag',TO_TIMESTAMP('2023-06-23 19:31:07.016','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:07.666534400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716457 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:07.704514600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2023-06-23T16:31:07.756833600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716457
;

-- 2023-06-23T16:31:07.795138300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716457)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Währung
-- Column: ModCntr_Log.C_Currency_ID
-- 2023-06-23T16:31:08.477592900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586856,716458,0,547012,TO_TIMESTAMP('2023-06-23 19:31:07.871','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-06-23 19:31:07.871','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:08.515593300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716458 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:08.553592800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-06-23T16:31:08.620123400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716458
;

-- 2023-06-23T16:31:08.660639100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716458)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Rechnungspartner
-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2023-06-23T16:31:09.309721300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586857,716459,0,547012,TO_TIMESTAMP('2023-06-23 19:31:08.737','YYYY-MM-DD HH24:MI:SS.US'),100,'Geschäftspartner für die Rechnungsstellung',10,'D','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','N','N','N','N','N','Rechnungspartner',TO_TIMESTAMP('2023-06-23 19:31:08.737','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-23T16:31:09.387656800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716459 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-06-23T16:31:09.426753800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2039) 
;

-- 2023-06-23T16:31:09.468338600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716459
;

-- 2023-06-23T16:31:09.506532400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716459)
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Contract Module Log
-- Column: ModCntr_Log.ModCntr_Log_ID
-- 2023-06-23T16:31:30.043122Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:30.043','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716315
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Menge
-- Column: ModCntr_Log.Qty
-- 2023-06-23T16:31:31.979621600Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:31.979','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716454
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Währung
-- Column: ModCntr_Log.C_Currency_ID
-- 2023-06-23T16:31:34.111623300Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:34.111','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716458
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Rechnungspartner
-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2023-06-23T16:31:36.061170900Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:36.061','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716459
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Erntejahr
-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-06-23T16:31:38.008406900Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:38.008','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716455
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Maßeinheit
-- Column: ModCntr_Log.C_UOM_ID
-- 2023-06-23T16:31:39.913040100Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:39.913','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716456
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Betrag
-- Column: ModCntr_Log.Amount
-- 2023-06-23T16:31:41.790911500Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:41.79','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716457
;

-- Field: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> Document Type
-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-06-23T16:31:46.314393800Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-06-23 19:31:46.314','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=716453
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Document Type
-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-06-23T16:36:00.875133600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716453,0,547012,550777,618088,'F',TO_TIMESTAMP('2023-06-23 19:36:00.33','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Document Type',30,0,0,TO_TIMESTAMP('2023-06-23 19:36:00.33','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Erntejahr
-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-06-23T16:36:57.898753200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716455,0,547012,550777,618089,'F',TO_TIMESTAMP('2023-06-23 19:36:57.384','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Erntejahr',40,0,0,TO_TIMESTAMP('2023-06-23 19:36:57.384','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Rechnungspartner
-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2023-06-23T16:37:15.952993400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716459,0,547012,550777,618090,'F',TO_TIMESTAMP('2023-06-23 19:37:15.409','YYYY-MM-DD HH24:MI:SS.US'),100,'Geschäftspartner für die Rechnungsstellung','Wenn leer, wird die Rechnung an den Geschäftspartner der Lieferung gestellt','Y','N','N','Y','N','N','N',0,'Rechnungspartner',50,0,0,TO_TIMESTAMP('2023-06-23 19:37:15.409','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: org
-- 2023-06-23T16:37:31.064050200Z
UPDATE AD_UI_ElementGroup SET SeqNo=50,Updated=TO_TIMESTAMP('2023-06-23 19:37:31.064','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550780
;

-- UI Column: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20
-- UI Element Group: numbers
-- 2023-06-23T16:37:45.528325Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546864,550801,TO_TIMESTAMP('2023-06-23 19:37:44.998','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','numbers',40,TO_TIMESTAMP('2023-06-23 19:37:44.998','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Menge
-- Column: ModCntr_Log.Qty
-- 2023-06-23T16:38:00.845936700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716454,0,547012,550801,618091,'F',TO_TIMESTAMP('2023-06-23 19:38:00.271','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',10,0,0,TO_TIMESTAMP('2023-06-23 19:38:00.271','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Maßeinheit
-- Column: ModCntr_Log.C_UOM_ID
-- 2023-06-23T16:38:15.258340100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716456,0,547012,550801,618092,'F',TO_TIMESTAMP('2023-06-23 19:38:14.703','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',20,0,0,TO_TIMESTAMP('2023-06-23 19:38:14.703','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Betrag
-- Column: ModCntr_Log.Amount
-- 2023-06-23T16:38:36.075484700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716457,0,547012,550801,618093,'F',TO_TIMESTAMP('2023-06-23 19:38:35.553','YYYY-MM-DD HH24:MI:SS.US'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','Y','N','N','N',0,'Betrag',30,0,0,TO_TIMESTAMP('2023-06-23 19:38:35.553','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Währung
-- Column: ModCntr_Log.C_Currency_ID
-- 2023-06-23T16:38:52.405792900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716458,0,547012,550801,618094,'F',TO_TIMESTAMP('2023-06-23 19:38:51.851','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',40,0,0,TO_TIMESTAMP('2023-06-23 19:38:51.851','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Document Type
-- Column: ModCntr_Log.ModCntr_Log_DocumentType
-- 2023-06-23T16:43:24.899739Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-06-23 19:43:24.899','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618088
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Erntejahr
-- Column: ModCntr_Log.Harvesting_Year_ID
-- 2023-06-23T16:43:25.131034300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-06-23 19:43:25.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618089
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 10 -> details.Rechnungspartner
-- Column: ModCntr_Log.Bill_BPartner_ID
-- 2023-06-23T16:43:25.402786700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2023-06-23 19:43:25.402','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618090
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Menge
-- Column: ModCntr_Log.Qty
-- 2023-06-23T16:43:25.629987800Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2023-06-23 19:43:25.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618091
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Maßeinheit
-- Column: ModCntr_Log.C_UOM_ID
-- 2023-06-23T16:43:25.858547900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2023-06-23 19:43:25.858','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618092
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Betrag
-- Column: ModCntr_Log.Amount
-- 2023-06-23T16:43:26.086961300Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2023-06-23 19:43:26.086','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618093
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> numbers.Währung
-- Column: ModCntr_Log.C_Currency_ID
-- 2023-06-23T16:43:26.314560900Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2023-06-23 19:43:26.314','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=618094
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.DB-Tabelle
-- Column: ModCntr_Log.AD_Table_ID
-- 2023-06-23T16:43:26.593085400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2023-06-23 19:43:26.593','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617977
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> records.Datensatz-ID
-- Column: ModCntr_Log.Record_ID
-- 2023-06-23T16:43:26.821531700Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2023-06-23 19:43:26.821','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617978
;

-- UI Element: Vertragsbaustein Log(541711,D) -> Contract Module Log(547012,D) -> main -> 20 -> org.Organisation
-- Column: ModCntr_Log.AD_Org_ID
-- 2023-06-23T16:43:27.053449400Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2023-06-23 19:43:27.053','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617973
;

