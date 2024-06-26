-- Field: Auftragsdisposition OLD -> Kandidat -> Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- Field: Auftragsdisposition OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- 2024-06-25T15:14:16.536Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588627,729006,0,540282,TO_TIMESTAMP('2024-06-25 18:14:16','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Menge Abw.',TO_TIMESTAMP('2024-06-25 18:14:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-25T15:14:16.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-25T15:14:16.539Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583153) 
;

-- 2024-06-25T15:14:16.541Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729006
;

-- 2024-06-25T15:14:16.542Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729006)
;

-- UI Element: Auftragsdisposition OLD -> Kandidat.Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- UI Element: Auftragsdisposition OLD(540095,de.metas.ordercandidate) -> Kandidat(540282,de.metas.ordercandidate) -> main -> 10 -> default.Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- 2024-06-25T15:14:38.801Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,729006,0,540282,624955,540964,'F',TO_TIMESTAMP('2024-06-25 18:14:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Menge Abw.',85,0,0,TO_TIMESTAMP('2024-06-25 18:14:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Auftragsdisposition - Fehler -> Kandidat -> Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- Field: Auftragsdisposition - Fehler(540127,de.metas.ordercandidate) -> Kandidat(540368,de.metas.ordercandidate) -> Menge Abw.
-- Column: C_OLCand.QtyEntered_Override
-- 2024-06-25T15:15:51.531Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588627,729007,0,540368,TO_TIMESTAMP('2024-06-25 18:15:51','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.ordercandidate','Y','N','N','N','N','N','N','N','Menge Abw.',TO_TIMESTAMP('2024-06-25 18:15:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-06-25T15:15:51.533Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729007 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-25T15:15:51.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583153) 
;

-- 2024-06-25T15:15:51.540Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729007
;

-- 2024-06-25T15:15:51.544Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729007)
;

-- Value: de.metas.ordercandidate.modelvalidator.C_OLCand.InvalidQuantity
-- 2024-06-25T17:27:43.257Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545427,0,TO_TIMESTAMP('2024-06-25 20:27:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Die Menge muss größer als 0 sein.','E',TO_TIMESTAMP('2024-06-25 20:27:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.ordercandidate.modelvalidator.C_OLCand.InvalidQuantity')
;

-- 2024-06-25T17:27:43.264Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545427 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: de.metas.ordercandidate.modelvalidator.C_OLCand.InvalidQuantity
-- 2024-06-25T17:28:05.913Z
UPDATE AD_Message_Trl SET MsgText='Quantity has to be greater than 0.',Updated=TO_TIMESTAMP('2024-06-25 20:28:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545427
;

