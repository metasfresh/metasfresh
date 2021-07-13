-- 2020-06-23T11:23:05.069Z
-- URL zum Konzept
UPDATE AD_Window SET AD_Element_ID=575146, Description='Bericht zu Produktkosten', Help=NULL, Name='Produktkosten',Updated=TO_TIMESTAMP('2020-06-23 14:23:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=344
;

-- 2020-06-23T11:23:05.529Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Bericht zu Produktkosten', IsActive='Y', Name='Produktkosten',Updated=TO_TIMESTAMP('2020-06-23 14:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=540906
;

-- 2020-06-23T11:23:05.653Z
-- URL zum Konzept
UPDATE AD_Menu SET Description='Bericht zu Produktkosten', IsActive='Y', Name='Produktkosten',Updated=TO_TIMESTAMP('2020-06-23 14:23:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=520
;

-- 2020-06-23T11:23:05.853Z
-- URL zum Konzept
/* DDL */  select update_window_translation_from_ad_element(575146) 
;

-- 2020-06-23T11:23:05.936Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Window_ID=344
;

-- 2020-06-23T11:23:05.977Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Window(344)
;

-- 2020-06-23T11:24:04.254Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,56574,614942,0,748,TO_TIMESTAMP('2020-06-23 14:24:03','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Manufacturing Cost Collector',TO_TIMESTAMP('2020-06-23 14:24:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:04.463Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:04.506Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53310) 
;

-- 2020-06-23T11:24:04.625Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614942
;

-- 2020-06-23T11:24:04.663Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614942)
;

-- 2020-06-23T11:24:05.157Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558621,614943,0,748,TO_TIMESTAMP('2020-06-23 14:24:04','YYYY-MM-DD HH24:MI:SS'),100,'Match Purchase Order to Shipment/Receipt and Invoice',10,'D','The matching record is usually created automatically.  If price matching is enabled on business partner group level, the matching might have to be approved.','Y','N','N','N','N','N','N','N','Abgleich Bestellung',TO_TIMESTAMP('2020-06-23 14:24:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:05.229Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:05.261Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1690) 
;

-- 2020-06-23T11:24:05.323Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614943
;

-- 2020-06-23T11:24:05.355Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614943)
;

-- 2020-06-23T11:24:05.828Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558650,614944,0,748,TO_TIMESTAMP('2020-06-23 14:24:05','YYYY-MM-DD HH24:MI:SS'),100,'Match Shipment/Receipt to Invoice',10,'D','Y','N','N','N','N','N','N','N','Abgleich Rechnung',TO_TIMESTAMP('2020-06-23 14:24:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:05.899Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:05.935Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1689) 
;

-- 2020-06-23T11:24:06.003Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614944
;

-- 2020-06-23T11:24:06.036Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614944)
;

-- 2020-06-23T11:24:06.534Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559013,614945,0,748,TO_TIMESTAMP('2020-06-23 14:24:06','YYYY-MM-DD HH24:MI:SS'),100,'Set if this record is changing the costs.',1,'D','Y','N','N','N','N','N','N','N','Changing costs',TO_TIMESTAMP('2020-06-23 14:24:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:06.605Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614945 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:06.636Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543853) 
;

-- 2020-06-23T11:24:06.673Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614945
;

-- 2020-06-23T11:24:06.706Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614945)
;

-- 2020-06-23T11:24:07.166Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559014,614946,0,748,TO_TIMESTAMP('2020-06-23 14:24:06','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Previous Current Cost Price',TO_TIMESTAMP('2020-06-23 14:24:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:07.233Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614946 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:07.266Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543854) 
;

-- 2020-06-23T11:24:07.300Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614946
;

-- 2020-06-23T11:24:07.332Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614946)
;

-- 2020-06-23T11:24:07.804Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559015,614947,0,748,TO_TIMESTAMP('2020-06-23 14:24:07','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Previous Current Cost Price LL',TO_TIMESTAMP('2020-06-23 14:24:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:07.873Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614947 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:07.906Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543855) 
;

-- 2020-06-23T11:24:07.940Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614947
;

-- 2020-06-23T11:24:07.972Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614947)
;

-- 2020-06-23T11:24:08.444Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559016,614948,0,748,TO_TIMESTAMP('2020-06-23 14:24:08','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Previous Current Qty',TO_TIMESTAMP('2020-06-23 14:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:08.516Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614948 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:08.551Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543856) 
;

-- 2020-06-23T11:24:08.585Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614948
;

-- 2020-06-23T11:24:08.620Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614948)
;

-- 2020-06-23T11:24:09.087Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563713,614949,0,748,TO_TIMESTAMP('2020-06-23 14:24:08','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2020-06-23 14:24:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:09.160Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614949 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:09.194Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2020-06-23T11:24:09.548Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614949
;

-- 2020-06-23T11:24:09.579Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614949)
;

-- 2020-06-23T11:24:10.066Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563714,614950,0,748,TO_TIMESTAMP('2020-06-23 14:24:09','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2020-06-23 14:24:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:10.134Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614950 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:10.168Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215) 
;

-- 2020-06-23T11:24:10.353Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614950
;

-- 2020-06-23T11:24:10.387Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614950)
;

-- 2020-06-23T11:24:11.376Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563752,614951,0,748,TO_TIMESTAMP('2020-06-23 14:24:10','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Previous Cumulated Amount',TO_TIMESTAMP('2020-06-23 14:24:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:11.445Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614951 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:11.479Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575934) 
;

-- 2020-06-23T11:24:11.513Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614951
;

-- 2020-06-23T11:24:11.545Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614951)
;

-- 2020-06-23T11:24:12.015Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563753,614952,0,748,TO_TIMESTAMP('2020-06-23 14:24:11','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Previous Cumulated Quantity',TO_TIMESTAMP('2020-06-23 14:24:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:24:12.086Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=614952 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-06-23T11:24:12.122Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575935) 
;

-- 2020-06-23T11:24:12.157Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=614952
;

-- 2020-06-23T11:24:12.190Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(614952)
;

-- 2020-06-23T11:28:33.193Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,614944,0,748,541004,570141,'F',TO_TIMESTAMP('2020-06-23 14:28:32','YYYY-MM-DD HH24:MI:SS'),100,'Match Shipment/Receipt to Invoice','Y','N','N','Y','N','N','N',0,'Abgleich Rechnung',200,0,0,TO_TIMESTAMP('2020-06-23 14:28:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:28:58.766Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,614943,0,748,541004,570142,'F',TO_TIMESTAMP('2020-06-23 14:28:58','YYYY-MM-DD HH24:MI:SS'),100,'Match Purchase Order to Shipment/Receipt and Invoice','The matching record is usually created automatically.  If price matching is enabled on business partner group level, the matching might have to be approved.','Y','N','N','Y','N','N','N',0,'Abgleich Bestellung',210,0,0,TO_TIMESTAMP('2020-06-23 14:28:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:29:48.752Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=143,Updated=TO_TIMESTAMP('2020-06-23 14:29:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570141
;

-- 2020-06-23T11:29:54.246Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=144,Updated=TO_TIMESTAMP('2020-06-23 14:29:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570142
;

-- 2020-06-23T11:30:16.857Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2020-06-23 14:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547588
;

-- 2020-06-23T11:30:17.021Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2020-06-23 14:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570142
;

-- 2020-06-23T11:30:17.189Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2020-06-23 14:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570141
;

-- 2020-06-23T11:30:17.355Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2020-06-23 14:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547589
;

-- 2020-06-23T11:30:17.520Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2020-06-23 14:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547583
;

-- 2020-06-23T11:30:17.687Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2020-06-23 14:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547573
;

-- 2020-06-23T11:30:17.851Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2020-06-23 14:30:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=547572
;

-- 2020-06-23T11:30:29.927Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-06-23 14:30:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=614945
;

-- 2020-06-23T11:31:04.042Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,614945,0,748,541004,570143,'F',TO_TIMESTAMP('2020-06-23 14:31:03','YYYY-MM-DD HH24:MI:SS'),100,'Set if this record is changing the costs.','Y','N','N','Y','N','N','N',0,'Changing costs',200,0,0,TO_TIMESTAMP('2020-06-23 14:31:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-06-23T11:31:42.718Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=52,Updated=TO_TIMESTAMP('2020-06-23 14:31:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=570143
;

