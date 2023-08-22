-- Column: C_AcctSchema_Element.C_Harvesting_Calendar_ID
-- 2023-08-21T15:38:23.268240200Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,587303,581157,0,19,279,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-21 18:38:22.635','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',10,'Y','N','Y','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender','NP',0,TO_TIMESTAMP('2023-08-21 18:38:22.635','YYYY-MM-DD HH24:MI:SS.US'),100,1)
;

-- 2023-08-21T15:38:23.301719700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587303 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-21T15:38:24.806339Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- Column: C_AcctSchema_Element.C_Harvesting_Calendar_ID
-- 2023-08-21T15:39:03.412537500Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=540260, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-08-21 18:39:03.412','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587303
;

-- 2023-08-21T15:39:09.371523500Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_Element','ALTER TABLE public.C_AcctSchema_Element ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-21T15:39:09.405686Z
ALTER TABLE C_AcctSchema_Element ADD CONSTRAINT CHarvestingCalendar_CAcctSchemaElement FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-21T15:40:07.065319500Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587304,582471,0,18,540133,279,198,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-21 18:40:06.602','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-21 18:40:06.602','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-21T15:40:07.094820300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587304 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-21T15:40:08.501046600Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- Name: C_Year of Calendar
-- 2023-08-21T15:40:37.534050300Z
UPDATE AD_Val_Rule SET Code='C_Year.C_Calendar_ID=@C_Harvesting_Calendar_ID@',Updated=TO_TIMESTAMP('2023-08-21 18:40:37.461','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=198
;

-- Name: C_Year of Calendar
-- 2023-08-21T15:40:54.038252600Z
UPDATE AD_Val_Rule SET Code='C_Year.C_Calendar_ID=@C_Calendar_ID@',Updated=TO_TIMESTAMP('2023-08-21 18:40:53.965','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=198
;

-- Name: C_Year of Harvesting Calendar
-- 2023-08-21T15:51:43.225455600Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540655,'C_Year.C_Calendar_ID=@C_Harvesting_Calendar_ID@',TO_TIMESTAMP('2023-08-21 18:51:42.889','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','C_Year of Harvesting Calendar','S',TO_TIMESTAMP('2023-08-21 18:51:42.889','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-21T15:52:08.256851700Z
UPDATE AD_Column SET AD_Val_Rule_ID=540655,Updated=TO_TIMESTAMP('2023-08-21 18:52:08.255','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587304
;

-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-21T15:52:20.780261100Z
UPDATE AD_Column SET IsAutoApplyValidationRule='Y',Updated=TO_TIMESTAMP('2023-08-21 18:52:20.78','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587304
;

-- 2023-08-21T15:52:27.234397100Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_Element','ALTER TABLE public.C_AcctSchema_Element ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-21T15:52:27.271740200Z
ALTER TABLE C_AcctSchema_Element ADD CONSTRAINT HarvestingYear_CAcctSchemaElement FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Field: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> Auftrag
-- Column: C_AcctSchema_Element.C_OrderSO_ID
-- 2023-08-22T07:37:16.989654Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585405,720169,0,217,TO_TIMESTAMP('2023-08-22 10:37:16.43','YYYY-MM-DD HH24:MI:SS.US'),100,'Auftrag',10,'D','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2023-08-22 10:37:16.43','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T07:37:17.021735100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720169 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T07:37:17.055608600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479) 
;

-- 2023-08-22T07:37:17.107290Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720169
;

-- 2023-08-22T07:37:17.137449800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720169)
;

-- Field: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> Sektionskennung
-- Column: C_AcctSchema_Element.M_SectionCode_ID
-- 2023-08-22T07:37:17.582062100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585406,720170,0,217,TO_TIMESTAMP('2023-08-22 10:37:17.191','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-08-22 10:37:17.191','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T07:37:17.608957200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720170 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T07:37:17.636017600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-08-22T07:37:17.670947900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720170
;

-- 2023-08-22T07:37:17.696762300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720170)
;

-- Field: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> Erntekalender
-- Column: C_AcctSchema_Element.C_Harvesting_Calendar_ID
-- 2023-08-22T07:37:18.192861100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587303,720171,0,217,TO_TIMESTAMP('2023-08-22 10:37:17.753','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntekalender',TO_TIMESTAMP('2023-08-22 10:37:17.753','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T07:37:18.222295700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720171 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T07:37:18.256816500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-22T07:37:18.285998200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720171
;

-- 2023-08-22T07:37:18.311485300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720171)
;

-- Field: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> Erntejahr
-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-22T07:37:18.753758100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587304,720172,0,217,TO_TIMESTAMP('2023-08-22 10:37:18.364','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2023-08-22 10:37:18.364','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T07:37:18.781365200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720172 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T07:37:18.807022200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-22T07:37:18.837121500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720172
;

-- 2023-08-22T07:37:18.861674800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720172)
;

-- Reference: C_AcctSchema ElementType
-- Value: HC
-- ValueName: Harversting Calendar
-- 2023-08-22T11:05:39.748457900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,181,543538,TO_TIMESTAMP('2023-08-22 14:05:39.338','YYYY-MM-DD HH24:MI:SS.US'),100,'Harversting Calendar','D','Y','Harversting Calendar',TO_TIMESTAMP('2023-08-22 14:05:39.338','YYYY-MM-DD HH24:MI:SS.US'),100,'HC','Harversting Calendar')
;

-- 2023-08-22T11:05:39.776355600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543538 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: C_AcctSchema ElementType
-- Value: HC
-- ValueName: Erntekalender
-- 2023-08-22T11:06:33.043134100Z
UPDATE AD_Ref_List SET Description='Erntekalender', ValueName='Erntekalender',Updated=TO_TIMESTAMP('2023-08-22 14:06:33.043','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543538
;

-- 2023-08-22T11:06:33.071160800Z
UPDATE AD_Ref_List_Trl trl SET Description='Erntekalender' WHERE AD_Ref_List_ID=543538 AND AD_Language='de_DE'
;

-- Reference: C_AcctSchema ElementType
-- Value: HC
-- ValueName: Erntekalender
-- 2023-08-22T11:06:39.975114500Z
UPDATE AD_Ref_List SET Name='Erntekalender',Updated=TO_TIMESTAMP('2023-08-22 14:06:39.974','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543538
;

-- 2023-08-22T11:06:40.002200600Z
UPDATE AD_Ref_List_Trl trl SET Name='Erntekalender' WHERE AD_Ref_List_ID=543538 AND AD_Language='de_DE'
;

-- Reference: C_AcctSchema ElementType
-- Value: HC
-- ValueName: Harvesting Calendar
-- 2023-08-22T11:06:58.379198700Z
UPDATE AD_Ref_List SET ValueName='Harvesting Calendar',Updated=TO_TIMESTAMP('2023-08-22 14:06:58.379','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543538
;

-- Reference Item: C_AcctSchema ElementType -> HC_Harvesting Calendar
-- 2023-08-22T11:07:09.097116800Z
UPDATE AD_Ref_List_Trl SET Description='Erntekalender', IsTranslated='Y', Name='Erntekalender',Updated=TO_TIMESTAMP('2023-08-22 14:07:09.096','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543538
;

-- Reference Item: C_AcctSchema ElementType -> HC_Harvesting Calendar
-- 2023-08-22T11:07:11.719372300Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-22 14:07:11.718','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543538
;

-- Reference Item: C_AcctSchema ElementType -> HC_Harvesting Calendar
-- 2023-08-22T11:07:19.170262100Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-22 14:07:19.17','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543538
;

-- Reference: C_AcctSchema ElementType
-- Value: HY
-- ValueName: Harvesting Year
-- 2023-08-22T11:08:11.054237600Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,181,543539,TO_TIMESTAMP('2023-08-22 14:08:10.719','YYYY-MM-DD HH24:MI:SS.US'),100,'Erntejahr','D','Y','Erntejahr',TO_TIMESTAMP('2023-08-22 14:08:10.719','YYYY-MM-DD HH24:MI:SS.US'),100,'HY','Harvesting Year')
;

-- 2023-08-22T11:08:11.080048100Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543539 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_AcctSchema ElementType -> HY_Harvesting Year
-- 2023-08-22T11:08:19.754109Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-22 14:08:19.754','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543539
;

-- Reference Item: C_AcctSchema ElementType -> HY_Harvesting Year
-- 2023-08-22T11:08:33.050180Z
UPDATE AD_Ref_List_Trl SET Description='Harvesting Year', IsTranslated='Y', Name='Harvesting Year',Updated=TO_TIMESTAMP('2023-08-22 14:08:33.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543539
;

-- Reference Item: C_AcctSchema ElementType -> HY_Harvesting Year
-- 2023-08-22T11:08:36.600206200Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-08-22 14:08:36.6','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543539
;

-- Field: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> Erntekalender
-- Column: C_AcctSchema_Element.C_Harvesting_Calendar_ID
-- 2023-08-22T11:09:58.514193900Z
UPDATE AD_Field SET DisplayLogic='@ElementType@=''HC''',Updated=TO_TIMESTAMP('2023-08-22 14:09:58.514','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720171
;

-- Field: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> Erntejahr
-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-22T11:10:26.852304600Z
UPDATE AD_Field SET DisplayLogic='@ElementType@=''HY''',Updated=TO_TIMESTAMP('2023-08-22 14:10:26.852','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720172
;

-- UI Element: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> main -> 10 -> default.Erntekalender
-- Column: C_AcctSchema_Element.C_Harvesting_Calendar_ID
-- 2023-08-22T11:12:02.053978700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720171,0,217,541332,620352,'F',TO_TIMESTAMP('2023-08-22 14:12:01.655','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N','Erntekalender',100,0,0,TO_TIMESTAMP('2023-08-22 14:12:01.655','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> main -> 10 -> default.Erntejahr
-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-22T11:12:22.650075300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720172,0,217,541332,620353,'F',TO_TIMESTAMP('2023-08-22 14:12:22.277','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N','Erntejahr',100,0,0,TO_TIMESTAMP('2023-08-22 14:12:22.277','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> main -> 10 -> default.Erntekalender
-- Column: C_AcctSchema_Element.C_Harvesting_Calendar_ID
-- 2023-08-22T11:13:01.592376800Z
UPDATE AD_UI_Element SET SeqNo=280,Updated=TO_TIMESTAMP('2023-08-22 14:13:01.592','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620352
;

-- UI Element: Buchführungs-Schema(125,D) -> Kontenschema-Element(217,D) -> main -> 10 -> default.Erntejahr
-- Column: C_AcctSchema_Element.Harvesting_Year_ID
-- 2023-08-22T11:13:10.712111500Z
UPDATE AD_UI_Element SET SeqNo=290,Updated=TO_TIMESTAMP('2023-08-22 14:13:10.712','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620353
;

-- Column: C_ValidCombination.C_Harvesting_Calendar_ID
-- 2023-08-22T11:14:14.051287Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587305,581157,0,18,540260,176,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-22 14:14:13.57','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-08-22 14:14:13.57','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-22T11:14:14.079913400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587305 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-22T11:14:15.392891900Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- 2023-08-22T11:14:21.270948300Z
/* DDL */ SELECT public.db_alter_table('C_ValidCombination','ALTER TABLE public.C_ValidCombination ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-22T11:14:21.351960300Z
ALTER TABLE C_ValidCombination ADD CONSTRAINT CHarvestingCalendar_CValidCombination FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_ValidCombination.Harvesting_Year_ID
-- 2023-08-22T11:15:00.776876300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587306,582471,0,18,540133,176,540655,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-22 14:15:00.136','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-22 14:15:00.136','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-22T11:15:00.806544100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587306 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-22T11:15:02.125829500Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-08-22T11:15:08.399190Z
/* DDL */ SELECT public.db_alter_table('C_ValidCombination','ALTER TABLE public.C_ValidCombination ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-22T11:15:08.486198200Z
ALTER TABLE C_ValidCombination ADD CONSTRAINT HarvestingYear_CValidCombination FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString1
-- Column: C_ValidCombination.UserElementString1
-- 2023-08-22T11:15:37.614629800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572775,720173,0,207,TO_TIMESTAMP('2023-08-22 14:15:37.189','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString1',TO_TIMESTAMP('2023-08-22 14:15:37.189','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:37.643843900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720173 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:37.670931600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578653) 
;

-- 2023-08-22T11:15:37.701958400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720173
;

-- 2023-08-22T11:15:37.727901600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720173)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString2
-- Column: C_ValidCombination.UserElementString2
-- 2023-08-22T11:15:38.172903600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572776,720174,0,207,TO_TIMESTAMP('2023-08-22 14:15:37.78','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString2',TO_TIMESTAMP('2023-08-22 14:15:37.78','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:38.200145800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:38.227260400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578654) 
;

-- 2023-08-22T11:15:38.256255500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720174
;

-- 2023-08-22T11:15:38.280193400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720174)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString3
-- Column: C_ValidCombination.UserElementString3
-- 2023-08-22T11:15:38.729250400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572777,720175,0,207,TO_TIMESTAMP('2023-08-22 14:15:38.332','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString3',TO_TIMESTAMP('2023-08-22 14:15:38.332','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:38.755135600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:38.782192600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578655) 
;

-- 2023-08-22T11:15:38.810428100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720175
;

-- 2023-08-22T11:15:38.835025300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720175)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString4
-- Column: C_ValidCombination.UserElementString4
-- 2023-08-22T11:15:39.284808200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572778,720176,0,207,TO_TIMESTAMP('2023-08-22 14:15:38.886','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString4',TO_TIMESTAMP('2023-08-22 14:15:38.886','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:39.311178500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:39.338023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578656) 
;

-- 2023-08-22T11:15:39.365793200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720176
;

-- 2023-08-22T11:15:39.390938500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720176)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString5
-- Column: C_ValidCombination.UserElementString5
-- 2023-08-22T11:15:39.841940700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572779,720177,0,207,TO_TIMESTAMP('2023-08-22 14:15:39.441','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString5',TO_TIMESTAMP('2023-08-22 14:15:39.441','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:39.868978500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:39.894614400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578657) 
;

-- 2023-08-22T11:15:39.923244900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720177
;

-- 2023-08-22T11:15:39.948178200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720177)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString6
-- Column: C_ValidCombination.UserElementString6
-- 2023-08-22T11:15:40.414695Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572780,720178,0,207,TO_TIMESTAMP('2023-08-22 14:15:40.004','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString6',TO_TIMESTAMP('2023-08-22 14:15:40.004','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:40.442206400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:40.468713500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578658) 
;

-- 2023-08-22T11:15:40.498070Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720178
;

-- 2023-08-22T11:15:40.522321900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720178)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> UserElementString7
-- Column: C_ValidCombination.UserElementString7
-- 2023-08-22T11:15:40.965025600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572781,720179,0,207,TO_TIMESTAMP('2023-08-22 14:15:40.575','YYYY-MM-DD HH24:MI:SS.US'),100,1000,'D','Y','N','N','N','N','N','N','N','UserElementString7',TO_TIMESTAMP('2023-08-22 14:15:40.575','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:40.992532Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:41.018511Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578659) 
;

-- 2023-08-22T11:15:41.048273Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720179
;

-- 2023-08-22T11:15:41.073258200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720179)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> Auftrag
-- Column: C_ValidCombination.C_OrderSO_ID
-- 2023-08-22T11:15:41.532696500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585403,720180,0,207,TO_TIMESTAMP('2023-08-22 14:15:41.125','YYYY-MM-DD HH24:MI:SS.US'),100,'Auftrag',10,'D','Y','N','N','N','N','N','N','N','Auftrag',TO_TIMESTAMP('2023-08-22 14:15:41.125','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:41.560364700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:41.586214500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543479) 
;

-- 2023-08-22T11:15:41.615854100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720180
;

-- 2023-08-22T11:15:41.640258Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720180)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> Sektionskennung
-- Column: C_ValidCombination.M_SectionCode_ID
-- 2023-08-22T11:15:42.076467500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585404,720181,0,207,TO_TIMESTAMP('2023-08-22 14:15:41.69','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-08-22 14:15:41.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:42.103414900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:42.128868700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-08-22T11:15:42.159853Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720181
;

-- 2023-08-22T11:15:42.185116600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720181)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> Erntekalender
-- Column: C_ValidCombination.C_Harvesting_Calendar_ID
-- 2023-08-22T11:15:42.611505600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587305,720182,0,207,TO_TIMESTAMP('2023-08-22 14:15:42.236','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntekalender',TO_TIMESTAMP('2023-08-22 14:15:42.236','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:42.636908200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:42.663527600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-22T11:15:42.692940400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720182
;

-- 2023-08-22T11:15:42.717875800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720182)
;

-- Field: Kontenkombination(153,D) -> Kombination(207,D) -> Erntejahr
-- Column: C_ValidCombination.Harvesting_Year_ID
-- 2023-08-22T11:15:43.152878100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587306,720183,0,207,TO_TIMESTAMP('2023-08-22 14:15:42.769','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Erntejahr',TO_TIMESTAMP('2023-08-22 14:15:42.769','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:15:43.180032500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:15:43.206112100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-22T11:15:43.234789500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720183
;

-- 2023-08-22T11:15:43.260020400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720183)
;

-- UI Element: Kontenkombination(153,D) -> Kombination(207,D) -> advanced edit -> 10 -> advanced edit.Erntekalender
-- Column: C_ValidCombination.C_Harvesting_Calendar_ID
-- 2023-08-22T11:16:47.140216200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720182,0,207,540872,620354,'F',TO_TIMESTAMP('2023-08-22 14:16:46.767','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N','Erntekalender',230,0,0,TO_TIMESTAMP('2023-08-22 14:16:46.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Kontenkombination(153,D) -> Kombination(207,D) -> advanced edit -> 10 -> advanced edit.Erntejahr
-- Column: C_ValidCombination.Harvesting_Year_ID
-- 2023-08-22T11:17:29.372200300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720183,0,207,540872,620355,'F',TO_TIMESTAMP('2023-08-22 14:17:28.986','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N','Erntejahr',230,0,0,TO_TIMESTAMP('2023-08-22 14:17:28.986','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Kontenkombination(153,D) -> Kombination(207,D) -> advanced edit -> 10 -> advanced edit.Erntejahr
-- Column: C_ValidCombination.Harvesting_Year_ID
-- 2023-08-22T11:17:46.645898300Z
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2023-08-22 14:17:46.645','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620355
;

-- UI Element: Kontenkombination(153,D) -> Kombination(207,D) -> advanced edit -> 10 -> advanced edit.Erntekalender
-- Column: C_ValidCombination.C_Harvesting_Calendar_ID
-- 2023-08-22T11:17:54.332316600Z
UPDATE AD_UI_Element SET SeqNo=240,Updated=TO_TIMESTAMP('2023-08-22 14:17:54.331','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620354
;

-- Column: C_InvoiceLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:19:33.903166800Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587308,581157,0,18,540260,333,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-22 14:19:33.531','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-08-22 14:19:33.531','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-22T11:19:33.930011400Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587308 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-22T11:19:34.896514100Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- Column: C_InvoiceLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:19:44.165188700Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-08-22 14:19:44.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587308
;

-- 2023-08-22T11:19:48.909872700Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-22T11:19:49.619057800Z
ALTER TABLE C_InvoiceLine ADD CONSTRAINT CHarvestingCalendar_CInvoiceLine FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_InvoiceLine.Harvesting_Year_ID
-- 2023-08-22T11:20:48.085026Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587309,582471,0,18,540133,333,540655,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-22 14:20:47.561','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-22 14:20:47.561','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-22T11:20:48.112660600Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587309 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-22T11:20:49.159712600Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- 2023-08-22T11:20:55.877914300Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceLine','ALTER TABLE public.C_InvoiceLine ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-22T11:20:56.540053200Z
ALTER TABLE C_InvoiceLine ADD CONSTRAINT HarvestingYear_CInvoiceLine FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Field: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> Erntekalender
-- Column: C_InvoiceLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:23:15.322572200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587308,720202,0,270,0,TO_TIMESTAMP('2023-08-22 14:23:14.814','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Erntekalender',0,220,0,1,1,TO_TIMESTAMP('2023-08-22 14:23:14.814','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:23:15.351099700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720202 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:23:15.376933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-22T11:23:15.405958100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720202
;

-- 2023-08-22T11:23:15.431076400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720202)
;

-- Field: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> Erntekalender
-- Column: C_InvoiceLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:23:23.469818700Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-08-22 14:23:23.468','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720202
;

-- Field: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> Erntejahr
-- Column: C_InvoiceLine.Harvesting_Year_ID
-- 2023-08-22T11:23:40.491104800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587309,720203,0,270,0,TO_TIMESTAMP('2023-08-22 14:23:39.981','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntejahr',0,230,0,1,1,TO_TIMESTAMP('2023-08-22 14:23:39.981','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:23:40.518651900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720203 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:23:40.544733200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-22T11:23:40.572905100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720203
;

-- 2023-08-22T11:23:40.597362900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720203)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> main -> 10 -> default.Erntekalender
-- Column: C_InvoiceLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:24:38.646035100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720202,0,270,540023,620356,'F',TO_TIMESTAMP('2023-08-22 14:24:38.239','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntekalender',240,0,0,TO_TIMESTAMP('2023-08-22 14:24:38.239','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnungsposition(270,D) -> main -> 10 -> default.Erntejahr
-- Column: C_InvoiceLine.Harvesting_Year_ID
-- 2023-08-22T11:24:57.663063800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720203,0,270,540023,620357,'F',TO_TIMESTAMP('2023-08-22 14:24:57.275','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntejahr',250,0,0,TO_TIMESTAMP('2023-08-22 14:24:57.275','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Erntekalender
-- Column: C_InvoiceLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:27:43.498237Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587308,720204,0,291,0,TO_TIMESTAMP('2023-08-22 14:27:43.029','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntekalender',0,120,0,1,1,TO_TIMESTAMP('2023-08-22 14:27:43.029','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:27:43.526804400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720204 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:27:43.553626100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-22T11:27:43.581754200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720204
;

-- 2023-08-22T11:27:43.607706Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720204)
;

-- Field: Eingangsrechnung_OLD(183,D) -> Rechnungsposition(291,D) -> Erntejahr
-- Column: C_InvoiceLine.Harvesting_Year_ID
-- 2023-08-22T11:28:03.984282500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587309,720205,0,291,0,TO_TIMESTAMP('2023-08-22 14:28:03.517','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntejahr',0,130,0,1,1,TO_TIMESTAMP('2023-08-22 14:28:03.517','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:28:04.012270800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720205 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:28:04.038616200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-22T11:28:04.067910800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720205
;

-- 2023-08-22T11:28:04.095215600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720205)
;

-- Column: M_InOutLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:30:54.422761300Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587310,581157,0,18,540260,320,'C_Harvesting_Calendar_ID',TO_TIMESTAMP('2023-08-22 14:30:54.057','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntekalender',0,0,TO_TIMESTAMP('2023-08-22 14:30:54.057','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-22T11:30:54.451908800Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587310 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-22T11:30:55.517265Z
/* DDL */  select update_Column_Translation_From_AD_Element(581157) 
;

-- 2023-08-22T11:31:00.713971800Z
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN C_Harvesting_Calendar_ID NUMERIC(10)')
;

-- 2023-08-22T11:31:03.309508500Z
ALTER TABLE M_InOutLine ADD CONSTRAINT CHarvestingCalendar_MInOutLine FOREIGN KEY (C_Harvesting_Calendar_ID) REFERENCES public.C_Calendar DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOutLine.Harvesting_Year_ID
-- 2023-08-22T11:32:13.084900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587311,582471,0,18,540133,320,540655,'Harvesting_Year_ID',TO_TIMESTAMP('2023-08-22 14:32:12.518','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Erntejahr',0,0,TO_TIMESTAMP('2023-08-22 14:32:12.518','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-08-22T11:32:13.113468500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587311 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-08-22T11:32:13.943751100Z
/* DDL */  select update_Column_Translation_From_AD_Element(582471) 
;

-- Column: M_InOutLine.Harvesting_Year_ID
-- 2023-08-22T11:32:23.220194500Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-08-22 14:32:23.22','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587311
;

-- 2023-08-22T11:32:28.886675800Z
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN Harvesting_Year_ID NUMERIC(10)')
;

-- 2023-08-22T11:32:29.449743200Z
ALTER TABLE M_InOutLine ADD CONSTRAINT HarvestingYear_MInOutLine FOREIGN KEY (Harvesting_Year_ID) REFERENCES public.C_Year DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_InOutLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:32:52.188445500Z
UPDATE AD_Column SET IsUpdateable='N',Updated=TO_TIMESTAMP('2023-08-22 14:32:52.187','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587310
;

-- 2023-08-22T11:32:57.091483300Z
INSERT INTO t_alter_column values('m_inoutline','C_Harvesting_Calendar_ID','NUMERIC(10)',null,null)
;

-- Field: Lieferung_OLD(169,D) -> Lieferposition(258,D) -> Erntekalender
-- Column: M_InOutLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:34:13.534071500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587310,720206,0,258,0,TO_TIMESTAMP('2023-08-22 14:34:13.004','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntekalender',0,150,0,1,1,TO_TIMESTAMP('2023-08-22 14:34:13.004','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:34:13.562422500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720206 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:34:13.588717100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-22T11:34:13.617888500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720206
;

-- 2023-08-22T11:34:13.644961900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720206)
;

-- Field: Lieferung_OLD(169,D) -> Lieferposition(258,D) -> Erntejahr
-- Column: M_InOutLine.Harvesting_Year_ID
-- 2023-08-22T11:34:32.969713800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587311,720207,0,258,0,TO_TIMESTAMP('2023-08-22 14:34:32.458','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntejahr',0,160,0,1,1,TO_TIMESTAMP('2023-08-22 14:34:32.458','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:34:32.995928500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720207 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:34:33.022761Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-22T11:34:33.050756900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720207
;

-- 2023-08-22T11:34:33.075593900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720207)
;

-- UI Element: Lieferung_OLD(169,D) -> Lieferposition(258,D) -> main -> 10 -> default.Erntekalender
-- Column: M_InOutLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:35:55.575543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720206,0,258,540976,620358,'F',TO_TIMESTAMP('2023-08-22 14:35:55.201','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntekalender',190,0,0,TO_TIMESTAMP('2023-08-22 14:35:55.201','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferung_OLD(169,D) -> Lieferposition(258,D) -> main -> 10 -> default.Erntejahr
-- Column: M_InOutLine.Harvesting_Year_ID
-- 2023-08-22T11:36:12.072922800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720207,0,258,540976,620359,'F',TO_TIMESTAMP('2023-08-22 14:36:11.69','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntejahr',200,0,0,TO_TIMESTAMP('2023-08-22 14:36:11.69','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Wareneingang_OLD(184,D) -> Wareneingangsposition(297,D) -> Erntekalender
-- Column: M_InOutLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:37:48.214422200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587310,720208,0,297,0,TO_TIMESTAMP('2023-08-22 14:37:47.727','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntekalender',0,160,0,1,1,TO_TIMESTAMP('2023-08-22 14:37:47.727','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:37:48.242529700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720208 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:37:48.268221100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581157) 
;

-- 2023-08-22T11:37:48.296903500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720208
;

-- 2023-08-22T11:37:48.321199400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720208)
;

-- Field: Wareneingang_OLD(184,D) -> Wareneingangsposition(297,D) -> Erntejahr
-- Column: M_InOutLine.Harvesting_Year_ID
-- 2023-08-22T11:38:15.336446800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587311,720209,0,297,0,TO_TIMESTAMP('2023-08-22 14:38:14.909','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','Y','N','Erntejahr',0,170,0,1,1,TO_TIMESTAMP('2023-08-22 14:38:14.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-08-22T11:38:15.363430800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720209 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-08-22T11:38:15.389667200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582471) 
;

-- 2023-08-22T11:38:15.419492400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720209
;

-- 2023-08-22T11:38:15.445027800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720209)
;

-- UI Element: Wareneingang_OLD(184,D) -> Wareneingangsposition(297,D) -> main -> 10 -> default.Erntekalender
-- Column: M_InOutLine.C_Harvesting_Calendar_ID
-- 2023-08-22T11:38:56.795564600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720208,0,297,540078,620360,'F',TO_TIMESTAMP('2023-08-22 14:38:56.443','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntekalender',170,0,0,TO_TIMESTAMP('2023-08-22 14:38:56.443','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Wareneingang_OLD(184,D) -> Wareneingangsposition(297,D) -> main -> 10 -> default.Erntejahr
-- Column: M_InOutLine.Harvesting_Year_ID
-- 2023-08-22T11:39:08.815597600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720209,0,297,540078,620361,'F',TO_TIMESTAMP('2023-08-22 14:39:08.45','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Erntejahr',180,0,0,TO_TIMESTAMP('2023-08-22 14:39:08.45','YYYY-MM-DD HH24:MI:SS.US'),100)
;

