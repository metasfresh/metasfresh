-- Run mode: SWING_CLIENT

-- Reference: C_ScannableCode_Format_Part_DataType
-- Value: CONSTANT
-- ValueName: Constant
-- 2025-06-11T11:14:41.561Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541944,543925,TO_TIMESTAMP('2025-06-11 11:14:41.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Constant',TO_TIMESTAMP('2025-06-11 11:14:41.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CONSTANT','Constant')
;

-- 2025-06-11T11:14:41.573Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543925 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_ScannableCode_Format_Part.ConstantValue
-- 2025-06-11T11:15:05.907Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590394,1322,0,22,542476,'XX','ConstantValue',TO_TIMESTAMP('2025-06-11 11:15:05.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Konstanter Wert','D',0,100,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Konstante',0,0,TO_TIMESTAMP('2025-06-11 11:15:05.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-11T11:15:05.910Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590394 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-11T11:15:06.102Z
/* DDL */  select update_Column_Translation_From_AD_Element(1322)
;

-- 2025-06-11T11:15:08.193Z
/* DDL */ SELECT public.db_alter_table('C_ScannableCode_Format_Part','ALTER TABLE public.C_ScannableCode_Format_Part ADD COLUMN ConstantValue NUMERIC')
;

-- 2025-06-11T11:17:33.425Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583720,0,'DecimalPointPosition',TO_TIMESTAMP('2025-06-11 11:17:33.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','Y','Dezimal-Punkt','Dezimal-Punkt',TO_TIMESTAMP('2025-06-11 11:17:33.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-11T11:17:33.430Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583720 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: DecimalPointPosition
-- 2025-06-11T11:17:37.826Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-11 11:17:37.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583720 AND AD_Language='de_CH'
;

-- 2025-06-11T11:17:37.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583720,'de_CH')
;

-- Element: DecimalPointPosition
-- 2025-06-11T11:17:48.837Z
UPDATE AD_Element_Trl SET Name='Decimal Point Position', PrintName='Decimal Point Position',Updated=TO_TIMESTAMP('2025-06-11 11:17:48.837000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583720 AND AD_Language='en_US'
;

-- 2025-06-11T11:17:48.840Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-11T11:17:49.209Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583720,'en_US')
;

-- Element: DecimalPointPosition
-- 2025-06-11T11:17:51.267Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-11 11:17:51.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583720 AND AD_Language='en_US'
;

-- 2025-06-11T11:17:51.270Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583720,'en_US')
;

-- Element: DecimalPointPosition
-- 2025-06-11T11:17:53.447Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-11 11:17:53.446000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583720 AND AD_Language='de_DE'
;

-- 2025-06-11T11:17:53.450Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583720,'de_DE')
;

-- 2025-06-11T11:17:53.452Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583720,'de_DE')
;

-- Column: C_ScannableCode_Format_Part.DecimalPointPosition
-- 2025-06-11T11:18:27.203Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590395,583720,0,11,542476,'XX','DecimalPointPosition',TO_TIMESTAMP('2025-06-11 11:18:27.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Dezimal-Punkt',0,0,TO_TIMESTAMP('2025-06-11 11:18:27.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-11T11:18:27.206Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590395 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-11T11:18:27.209Z
/* DDL */  select update_Column_Translation_From_AD_Element(583720)
;

-- 2025-06-11T11:18:27.880Z
/* DDL */ SELECT public.db_alter_table('C_ScannableCode_Format_Part','ALTER TABLE public.C_ScannableCode_Format_Part ADD COLUMN DecimalPointPosition NUMERIC(10)')
;

-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Konstante
-- Column: C_ScannableCode_Format_Part.ConstantValue
-- 2025-06-11T11:18:56.634Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590394,747596,0,547958,TO_TIMESTAMP('2025-06-11 11:18:56.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konstanter Wert',100,'D','Y','N','N','N','N','N','N','N','Konstante',TO_TIMESTAMP('2025-06-11 11:18:56.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-11T11:18:56.638Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-11T11:18:56.642Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1322)
;

-- 2025-06-11T11:18:56.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747596
;

-- 2025-06-11T11:18:56.671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747596)
;

-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Dezimal-Punkt
-- Column: C_ScannableCode_Format_Part.DecimalPointPosition
-- 2025-06-11T11:18:56.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590395,747597,0,547958,TO_TIMESTAMP('2025-06-11 11:18:56.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'D','Y','N','N','N','N','N','N','N','Dezimal-Punkt',TO_TIMESTAMP('2025-06-11 11:18:56.681000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-11T11:18:56.808Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=747597 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-06-11T11:18:56.810Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583720)
;

-- 2025-06-11T11:18:56.812Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=747597
;

-- 2025-06-11T11:18:56.813Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(747597)
;

-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Konstante
-- Column: C_ScannableCode_Format_Part.ConstantValue
-- 2025-06-11T11:19:28.201Z
UPDATE AD_Field SET DisplayLogic='@DataType/X@=CONSTANT',Updated=TO_TIMESTAMP('2025-06-11 11:19:28.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=747596
;

-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Dezimal-Punkt
-- Column: C_ScannableCode_Format_Part.DecimalPointPosition
-- 2025-06-11T11:20:36.281Z
UPDATE AD_Field SET DisplayLogic='@DataType/X@=WEIGHT_KG',Updated=TO_TIMESTAMP('2025-06-11 11:20:36.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=747597
;

-- Field: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> Data Format
-- Column: C_ScannableCode_Format_Part.DataFormat
-- 2025-06-11T11:21:31.143Z
UPDATE AD_Field SET DisplayLogic='@DataType/X@=BEST_BEFORE_DATE',Updated=TO_TIMESTAMP('2025-06-11 11:21:31.143000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=742022
;

-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Konstante
-- Column: C_ScannableCode_Format_Part.ConstantValue
-- 2025-06-11T11:22:30.780Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747596,0,547958,552749,633965,'F',TO_TIMESTAMP('2025-06-11 11:22:30.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Konstanter Wert','Y','N','Y','N','N','Konstante',60,0,0,TO_TIMESTAMP('2025-06-11 11:22:30.545000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Dezimal-Punkt
-- Column: C_ScannableCode_Format_Part.DecimalPointPosition
-- 2025-06-11T11:22:40.282Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,747597,0,547958,552749,633966,'F',TO_TIMESTAMP('2025-06-11 11:22:40.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','Y','N','Y','N','N','Dezimal-Punkt',70,0,0,TO_TIMESTAMP('2025-06-11 11:22:40.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Scannable Code Format(541880,D) -> Scannable Code Format Part(547958,D) -> main -> 10 -> default.Beschreibung
-- Column: C_ScannableCode_Format_Part.Description
-- 2025-06-11T11:22:51.910Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2025-06-11 11:22:51.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=631430
;

-- Column: C_ScannableCode_Format_Part.ConstantValue
-- 2025-06-11T11:24:49.235Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2025-06-11 11:24:49.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=590394
;

-- 2025-06-11T11:24:49.904Z
INSERT INTO t_alter_column values('c_scannablecode_format_part','ConstantValue','VARCHAR(100)',null,null)
;

