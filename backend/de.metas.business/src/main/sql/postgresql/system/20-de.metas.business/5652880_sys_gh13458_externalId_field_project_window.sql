-- Field: Budget Project -> Projekt -> Externe ID
-- Column: C_Project.ExternalId
-- 2022-08-24T16:05:39.699Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,584192,705530,0,546269,0,TO_TIMESTAMP('2022-08-24 18:05:39','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Externe ID',0,380,0,1,1,TO_TIMESTAMP('2022-08-24 18:05:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-24T16:05:39.702Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-24T16:05:39.729Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939) 
;

-- 2022-08-24T16:05:39.752Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705530
;

-- 2022-08-24T16:05:39.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705530)
;

-- UI Element: Budget Project -> Projekt.Externe ID_ExternalId_Externe ID
-- Column: C_Project.ExternalId
-- 2022-08-24T16:06:06.980Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,705530,0,546269,549125,612205,'F',TO_TIMESTAMP('2022-08-24 18:06:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Externe ID_ExternalId_Externe ID',55,0,0,TO_TIMESTAMP('2022-08-24 18:06:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Budget Project -> Projekt.Externe ID_ExternalId_Externe ID
-- Column: C_Project.ExternalId
-- 2022-08-24T16:06:16.948Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-08-24 18:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=612205
;

-- UI Element: Budget Project -> Projekt.Aktiv
-- Column: C_Project.IsActive
-- 2022-08-24T16:06:16.960Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-08-24 18:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607800
;

-- UI Element: Budget Project -> Projekt.Aussendienst
-- Column: C_Project.SalesRep_ID
-- 2022-08-24T16:06:16.971Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-08-24 18:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607802
;

-- UI Element: Budget Project -> Projekt.Beschreibung
-- Column: C_Project.Description
-- 2022-08-24T16:06:16.981Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-08-24 18:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607799
;

-- UI Element: Budget Project -> Projekt.Kunde
-- Column: C_Project.C_BPartner_ID
-- 2022-08-24T16:06:16.991Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-08-24 18:06:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607801
;

-- UI Element: Budget Project -> Projekt.Name
-- Column: C_Project.Name
-- 2022-08-24T16:06:17Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-08-24 18:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607795
;

-- UI Element: Budget Project -> Projekt.Projektnummer
-- Column: C_Project.Value
-- 2022-08-24T16:06:17.009Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-08-24 18:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607794
;

-- UI Element: Budget Project -> Projekt.Externe Projektreferenz
-- Column: C_Project.C_Project_Reference_Ext
-- 2022-08-24T16:06:17.019Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-08-24 18:06:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=607797
;

