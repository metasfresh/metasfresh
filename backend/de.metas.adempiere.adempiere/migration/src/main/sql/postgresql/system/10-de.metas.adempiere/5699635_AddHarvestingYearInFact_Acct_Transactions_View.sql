-- Column: Fact_Acct_Transactions_View.C_Harvesting_Calendar_ID
-- 2023-08-24T06:17:19.588307200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587326,581157,0,30,541485,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-24 09:17:18.869','YYYY-MM-DD HH24:MI:SS.US'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Erntekalender',TO_TIMESTAMP('2023-08-24 09:17:18.869','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-24T06:17:19.623277900Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-24T06:17:21.458779100Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- Column: Fact_Acct_Transactions_View.Harvesting_Year_ID
-- 2023-08-24T06:17:23.327690100Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,587327,582471,0,30,541485,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-24 09:17:22.79','YYYY-MM-DD HH24:MI:SS.US'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Erntejahr',TO_TIMESTAMP('2023-08-24 09:17:22.79','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-24T06:17:23.363119800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587327 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-24T06:17:24.909294400Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;


-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Erntekalender
-- Column: Fact_Acct_Transactions_View.C_Harvesting_Calendar_ID
-- 2023-08-24T06:19:31.049838Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587326,720210,0,242,TO_TIMESTAMP('2023-08-24 09:19:30.539','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntekalender',TO_TIMESTAMP('2023-08-24 09:19:30.539','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-24T06:19:31.084798500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720210 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-24T06:19:31.118290Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-24T06:19:31.156135600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720210
;

-- 2023-08-24T06:19:31.191129500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720210)
;

-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Erntejahr
-- Column: Fact_Acct_Transactions_View.Harvesting_Year_ID
-- 2023-08-24T06:19:31.741128200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587327,720211,0,242,TO_TIMESTAMP('2023-08-24 09:19:31.257','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2023-08-24 09:19:31.257','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-24T06:19:31.775033600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720211 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-24T06:19:31.808393500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-24T06:19:31.843858600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720211
;

-- 2023-08-24T06:19:31.875319300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720211)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 10 -> advanced edit.Erntekalender
-- Column: Fact_Acct_Transactions_View.C_Harvesting_Calendar_ID
-- 2023-08-24T06:20:20.964978600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720210,0,242,540303,620362,'F',TO_TIMESTAMP('2023-08-24 09:20:20.5','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntekalender',290,0,0,TO_TIMESTAMP('2023-08-24 09:20:20.5','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 10 -> advanced edit.Erntejahr
-- Column: Fact_Acct_Transactions_View.Harvesting_Year_ID
-- 2023-08-24T06:20:34.282524700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720211,0,242,540303,620363,'F',TO_TIMESTAMP('2023-08-24 09:20:33.83','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntejahr',300,0,0,TO_TIMESTAMP('2023-08-24 09:20:33.83','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: Fact_Acct_Transactions_View.C_Harvesting_Calendar_ID
-- 2023-08-24T06:23:23.013613Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540260,Updated=TO_TIMESTAMP('2023-08-24 09:23:23.012','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587326
;

-- Column: Fact_Acct_Transactions_View.Harvesting_Year_ID
-- 2023-08-24T06:23:48.910928900Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540133, AD_Val_Rule_ID=540655,Updated=TO_TIMESTAMP('2023-08-24 09:23:48.91','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587327
;

