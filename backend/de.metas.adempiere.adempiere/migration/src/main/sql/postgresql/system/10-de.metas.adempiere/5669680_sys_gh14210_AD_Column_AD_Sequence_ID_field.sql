-- Field: Tabelle und Spalte -> Spalte -> Reihenfolge
-- Column: AD_Column.AD_Sequence_ID
-- 2022-12-22T13:54:40.532Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585427,710060,0,101,0,TO_TIMESTAMP('2022-12-22 15:54:40','YYYY-MM-DD HH24:MI:SS'),100,'Nummernfolgen für Belege',0,'D','Der Nummernkreis bestimmt, welche Nummernfolge für eine Belegart verwendet wird.',0,'Y','Y','Y','N','N','N','N','N','Reihenfolge',0,540,0,1,1,TO_TIMESTAMP('2022-12-22 15:54:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-22T13:54:40.536Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=710060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-22T13:54:40.563Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(124) 
;

-- 2022-12-22T13:54:40.574Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710060
;

-- 2022-12-22T13:54:40.579Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710060)
;

-- Field: Tabelle und Spalte -> Spalte -> Reihenfolge
-- Column: AD_Column.AD_Sequence_ID
-- 2022-12-22T13:56:34.010Z
UPDATE AD_Field SET SeqNo=435,Updated=TO_TIMESTAMP('2022-12-22 15:56:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710060
;

