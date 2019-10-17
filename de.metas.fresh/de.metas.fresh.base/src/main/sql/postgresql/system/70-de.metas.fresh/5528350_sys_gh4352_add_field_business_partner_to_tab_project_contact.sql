

-- 2019-07-29T13:48:57.870Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DefaultValue,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568516,582484,0,541832,0,TO_TIMESTAMP('2019-07-29 15:48:57','YYYY-MM-DD HH24:MI:SS'),100,'@C_BPartner_ID@','Bezeichnet einen GeschÃ¤ftspartner',0,'U','Ein GeschÃ¤ftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.',0,'Y','Y','Y','N','N','N','N','N','GeschÃ¤ftspartner',120,50,0,1,1,TO_TIMESTAMP('2019-07-29 15:48:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:48:57.871Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=582484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-07-29T13:48:57.875Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2019-07-29T13:48:57.903Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=582484
;

-- 2019-07-29T13:48:57.904Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(582484)
;

-- 2019-07-29T13:49:19.533Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,582484,0,541832,542698,560313,'F',TO_TIMESTAMP('2019-07-29 15:49:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'GeschÃ¤ftspartner',5,0,0,TO_TIMESTAMP('2019-07-29 15:49:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-07-29T13:49:46.168Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2019-07-29 15:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560313
;

-- 2019-07-29T13:49:46.171Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2019-07-29 15:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560262
;

-- 2019-07-29T13:49:46.174Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2019-07-29 15:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560263
;

-- 2019-07-29T13:49:46.176Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2019-07-29 15:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=560299
;


-- 2019-07-29T13:54:36.069Z
-- URL zum Konzept
UPDATE AD_Field SET DefaultValue='-1',Updated=TO_TIMESTAMP('2019-07-29 15:54:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=582376
;