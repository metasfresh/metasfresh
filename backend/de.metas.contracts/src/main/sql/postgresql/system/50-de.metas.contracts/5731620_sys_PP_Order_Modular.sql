-- Run mode: SWING_CLIENT

-- Field: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> Modularer Vertrag
-- Column: PP_Order.Modular_Flatrate_Term_ID
-- 2024-08-22T12:51:28.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,
FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsOverrideFilterDefaultValue,IsReadOnly,
IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587163,729832,0,53054,0,
TO_TIMESTAMP('2024-08-22 12:51:27.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,
'Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.',0,'@C_DocTypeTarget_ID@=541107','de.metas.contracts',0,0,
'Y','Y','Y','N','N','N','N','N','N','N',0,'Modularer Vertrag',0,0,610,0,1,1,TO_TIMESTAMP('2024-08-22 12:51:27.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-22T12:51:28.135Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729832 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-22T12:51:28.168Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582523)
;

-- 2024-08-22T12:51:28.184Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729832
;

-- 2024-08-22T12:51:28.186Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729832)
;

-- UI Element: Produktionsauftrag(53009,EE01) -> Produktionsauftrag(53054,EE01) -> main -> 10 -> menge.Modularer Vertrag
-- Column: PP_Order.Modular_Flatrate_Term_ID
-- 2024-08-22T13:06:30.036Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729832,0,53054,540186,625290,'F',TO_TIMESTAMP('2024-08-22 13:06:29.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegzeilen, die mit einem modularen Vertrag verknüpft sind, erzeugen Vertragsbausteinprotokolle.','Y','N','Y','N','N','Modularer Vertrag',80,0,0,TO_TIMESTAMP('2024-08-22 13:06:29.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;




