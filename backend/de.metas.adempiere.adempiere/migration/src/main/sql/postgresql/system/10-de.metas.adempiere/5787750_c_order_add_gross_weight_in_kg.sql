-- Run mode: SWING_CLIENT

-- 2026-02-11T14:30:49.941Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584512,0,'GrossWeightKg',TO_TIMESTAMP('2026-02-11 14:30:49.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Brutto Gewicht (Kg)','Brutto Gewicht (Kg)',TO_TIMESTAMP('2026-02-11 14:30:49.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-11T14:30:49.965Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584512 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: GrossWeightKg
-- 2026-02-11T14:31:20.701Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gross Weight (Kg)', PrintName='Gross Weight (Kg)',Updated=TO_TIMESTAMP('2026-02-11 14:31:20.701000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584512 AND AD_Language='en_US'
;

-- 2026-02-11T14:31:20.702Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-11T14:31:20.907Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584512,'en_US')
;

-- Element: GrossWeightKg
-- 2026-02-11T14:31:21.671Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-11 14:31:21.671000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584512 AND AD_Language='de_CH'
;

-- 2026-02-11T14:31:21.679Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584512,'de_CH')
;

-- Element: GrossWeightKg
-- 2026-02-11T14:35:19.006Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-11 14:35:19.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584512 AND AD_Language='de_DE'
;

-- 2026-02-11T14:35:19.008Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584512,'de_DE')
;

-- 2026-02-11T14:35:19.009Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584512,'de_DE')
;

-- 2026-02-11T14:35:56.767Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584513,0,'TotalGrossWeightKg',TO_TIMESTAMP('2026-02-11 14:35:56.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Brutto Gewicht Summe (Kg)','Brutto Gewicht Summe (Kg)',TO_TIMESTAMP('2026-02-11 14:35:56.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-11T14:35:56.767Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584513 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: TotalGrossWeightKg
-- 2026-02-11T14:37:36.684Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gross Weight Sum (Kg)', PrintName='Gross Weight Sum (Kg)',Updated=TO_TIMESTAMP('2026-02-11 14:37:36.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584513 AND AD_Language='en_US'
;

-- 2026-02-11T14:37:36.686Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-11T14:37:36.877Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584513,'en_US')
;

-- Element: TotalGrossWeightKg
-- 2026-02-11T14:37:37.666Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-11 14:37:37.666000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584513 AND AD_Language='de_CH'
;

-- 2026-02-11T14:37:37.668Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584513,'de_CH')
;

-- Element: TotalGrossWeightKg
-- 2026-02-11T14:39:44.303Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-11 14:39:44.303000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584513 AND AD_Language='de_DE'
;

-- 2026-02-11T14:39:44.309Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584513,'de_DE')
;

-- 2026-02-11T14:39:44.310Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584513,'de_DE')
;

-- Column: C_Order.TotalGrossWeightKg
-- 2026-02-11T17:00:20.740Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591990,584513,0,22,259,'XX','TotalGrossWeightKg',TO_TIMESTAMP('2026-02-11 17:00:20.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','0','D',0,20,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Brutto Gewicht Summe (Kg)',0,0,TO_TIMESTAMP('2026-02-11 17:00:20.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-11T17:00:20.748Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591990 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-11T17:00:20.755Z
/* DDL */  select update_Column_Translation_From_AD_Element(584513)
;

-- 2026-02-11T17:00:25.463Z
/* DDL */ SELECT public.db_alter_table('C_Order','ALTER TABLE public.C_Order ADD COLUMN TotalGrossWeightKg NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: C_OrderLine.GrossWeightKg
-- 2026-02-11T17:01:35.009Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591991,584512,0,22,260,'XX','GrossWeightKg',TO_TIMESTAMP('2026-02-11 17:01:34.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','0','D',0,20,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Brutto Gewicht (Kg)',0,0,TO_TIMESTAMP('2026-02-11 17:01:34.905000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-11T17:01:35.015Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591991 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-11T17:01:35.148Z
/* DDL */  select update_Column_Translation_From_AD_Element(584512)
;

-- 2026-02-11T17:01:36.955Z
/* DDL */ SELECT public.db_alter_table('C_OrderLine','ALTER TABLE public.C_OrderLine ADD COLUMN GrossWeightKg NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: Auftrag(143,D) -> Auftrag(186,D) -> Brutto Gewicht Summe (Kg)
-- Column: C_Order.TotalGrossWeightKg
-- 2026-02-11T17:23:25.869Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591990,773623,0,186,TO_TIMESTAMP('2026-02-11 17:23:25.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','Y','N','Brutto Gewicht Summe (Kg)',TO_TIMESTAMP('2026-02-11 17:23:25.735000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-11T17:23:25.874Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773623 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-11T17:23:25.883Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584513)
;

-- 2026-02-11T17:23:25.895Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773623
;

-- 2026-02-11T17:23:25.903Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773623)
;

-- UI Element: Auftrag(143,D) -> Auftrag(186,D) -> advanced edit -> 10 -> advanced edit.Brutto Gewicht Summe (Kg)
-- Column: C_Order.TotalGrossWeightKg
-- 2026-02-11T17:23:59.311Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773623,0,186,540499,647721,'F',TO_TIMESTAMP('2026-02-11 17:23:59.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Brutto Gewicht Summe (Kg)',490,0,0,TO_TIMESTAMP('2026-02-11 17:23:59.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Auftrag(143,D) -> Auftragsposition(187,D) -> Brutto Gewicht (Kg)
-- Column: C_OrderLine.GrossWeightKg
-- 2026-02-11T17:24:26.809Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591991,773624,0,187,TO_TIMESTAMP('2026-02-11 17:24:26.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','Y','N','Brutto Gewicht (Kg)',TO_TIMESTAMP('2026-02-11 17:24:26.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-11T17:24:26.817Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773624 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-11T17:24:26.818Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584512)
;

-- 2026-02-11T17:24:26.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773624
;

-- 2026-02-11T17:24:26.821Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773624)
;

-- UI Element: Auftrag(143,D) -> Auftragsposition(187,D) -> main -> 10 -> main.Brutto Gewicht (Kg)
-- Column: C_OrderLine.GrossWeightKg
-- 2026-02-11T17:24:51.651Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773624,0,187,1000005,647722,'F',TO_TIMESTAMP('2026-02-11 17:24:51.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Brutto Gewicht (Kg)',460,0,0,TO_TIMESTAMP('2026-02-11 17:24:51.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

