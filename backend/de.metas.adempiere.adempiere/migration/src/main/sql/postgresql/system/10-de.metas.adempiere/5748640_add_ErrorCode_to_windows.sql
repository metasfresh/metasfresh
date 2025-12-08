-- Field: Probleme -> Probleme -> Fehlercode
-- Column: AD_Issue.ErrorCode
-- 2025-03-06T07:40:04.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589751,740363,0,777,0,TO_TIMESTAMP('2025-03-06 09:40:04','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fehlercode',0,460,0,1,1,TO_TIMESTAMP('2025-03-06 09:40:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-06T07:40:04.377Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=740363 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-06T07:40:04.522Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583518) 
;

-- 2025-03-06T07:40:04.540Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740363
;

-- 2025-03-06T07:40:04.544Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740363)
;

-- UI Element: Probleme -> Probleme.Fehlercode
-- Column: AD_Issue.ErrorCode
-- 2025-03-06T07:41:04.735Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740363,0,777,541419,630669,'F',TO_TIMESTAMP('2025-03-06 09:41:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Fehlercode',460,0,0,TO_TIMESTAMP('2025-03-06 09:41:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Probleme -> Probleme.Fehlercode
-- Column: AD_Issue.ErrorCode
-- 2025-03-06T07:41:59.817Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-03-06 09:41:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=630669
;

-- Field: Meldung -> Meldung -> Fehlercode
-- Column: AD_Message.ErrorCode
-- 2025-03-06T07:46:18.336Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589750,740364,0,109,0,TO_TIMESTAMP('2025-03-06 09:46:18','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Fehlercode',0,100,0,1,1,TO_TIMESTAMP('2025-03-06 09:46:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2025-03-06T07:46:18.338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=740364 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-06T07:46:18.340Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583518) 
;

-- 2025-03-06T07:46:18.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=740364
;

-- 2025-03-06T07:46:18.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(740364)
;

-- UI Element: Meldung -> Meldung.Fehlercode
-- Column: AD_Message.ErrorCode
-- 2025-03-06T07:46:57.790Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,740364,0,109,541364,630670,'F',TO_TIMESTAMP('2025-03-06 09:46:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','Y','N','Y','N','N','N',0,'Fehlercode',20,0,0,TO_TIMESTAMP('2025-03-06 09:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

