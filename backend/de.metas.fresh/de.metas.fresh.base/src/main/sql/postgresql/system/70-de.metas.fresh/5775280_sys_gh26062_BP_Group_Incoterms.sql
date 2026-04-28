-- Run mode: SWING_CLIENT

-- Column: C_BP_Group.C_Incoterms_ID
-- 2025-10-31T09:38:47.774Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591447,579927,0,19,394,'XX','C_Incoterms_ID',TO_TIMESTAMP('2025-10-31 09:38:46.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterms',0,0,TO_TIMESTAMP('2025-10-31 09:38:46.679000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T09:38:47.852Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591447 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T09:38:48.008Z
/* DDL */  select update_Column_Translation_From_AD_Element(579927)
;

-- 2025-10-31T09:39:18.065Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN C_Incoterms_ID NUMERIC(10)')
;

-- 2025-10-31T09:39:18.275Z
ALTER TABLE C_BP_Group ADD CONSTRAINT CIncoterms_CBPGroup FOREIGN KEY (C_Incoterms_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.IncotermLocation
-- 2025-10-31T09:40:08.654Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591448,501608,0,10,394,'XX','IncotermLocation',TO_TIMESTAMP('2025-10-31 09:40:08.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Anzugebender Ort für Handelsklausel','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Incoterm Ort',0,0,TO_TIMESTAMP('2025-10-31 09:40:08.067000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T09:40:08.727Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591448 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T09:40:13.386Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608)
;

-- 2025-10-31T09:40:42.438Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN IncotermLocation VARCHAR(255)')
;

-- 2025-10-31T09:44:05.208Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584187,0,'PO_Incoterms_ID',TO_TIMESTAMP('2025-10-31 09:44:04.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Incoterms','Incoterms',TO_TIMESTAMP('2025-10-31 09:44:04.714000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T09:44:05.281Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584187 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-10-31T09:46:31.955Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584188,0,'PO_IncotermLocation',TO_TIMESTAMP('2025-10-31 09:46:31.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','D','Y','PO Incoterm Ort','PO Incoterm Ort',TO_TIMESTAMP('2025-10-31 09:46:31.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T09:46:32.031Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584188 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PO_IncotermLocation
-- 2025-10-31T09:47:26.985Z
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y', Name='PO Incoterm Location', PrintName='PO Incoterm Location',Updated=TO_TIMESTAMP('2025-10-31 09:47:26.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584188 AND AD_Language='en_US'
;

-- 2025-10-31T09:47:27.057Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-31T09:47:41.606Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584188,'en_US')
;

-- Element: PO_Incoterms_ID
-- 2025-10-31T09:48:48.813Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PO Incoterms', PrintName='PO Incoterms',Updated=TO_TIMESTAMP('2025-10-31 09:48:48.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584187 AND AD_Language='de_DE'
;

-- 2025-10-31T09:48:48.887Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-31T09:49:04.457Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584187,'de_DE')
;

-- 2025-10-31T09:49:04.532Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584187,'de_DE')
;

-- Element: PO_Incoterms_ID
-- 2025-10-31T09:49:27.873Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PO Incoterms', PrintName='PO Incoterms',Updated=TO_TIMESTAMP('2025-10-31 09:49:27.873000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584187 AND AD_Language='de_CH'
;

-- 2025-10-31T09:49:27.945Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-31T09:49:41.717Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584187,'de_CH')
;

-- Element: PO_Incoterms_ID
-- 2025-10-31T09:50:00.583Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PO Incoterms', PrintName='PO Incoterms',Updated=TO_TIMESTAMP('2025-10-31 09:50:00.583000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584187 AND AD_Language='en_US'
;

-- 2025-10-31T09:50:00.656Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-31T09:50:13.100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584187,'en_US')
;

-- Element: PO_Incoterms_ID
-- 2025-10-31T09:50:19.969Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PO Incoterms', PrintName='PO Incoterms',Updated=TO_TIMESTAMP('2025-10-31 09:50:19.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584187 AND AD_Language='fr_CH'
;

-- 2025-10-31T09:50:20.044Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-10-31T09:50:31.279Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584187,'fr_CH')
;

-- Column: C_BP_Group.PO_Incoterms_ID
-- 2025-10-31T09:51:04.892Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591449,584187,0,19,394,'XX','PO_Incoterms_ID',TO_TIMESTAMP('2025-10-31 09:51:03.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'PO Incoterms',0,0,TO_TIMESTAMP('2025-10-31 09:51:03.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T09:51:04.966Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591449 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T09:51:05.111Z
/* DDL */  select update_Column_Translation_From_AD_Element(584187)
;

-- Column: C_BP_Group.PO_Incoterms_ID
-- 2025-10-31T09:52:34.593Z
UPDATE AD_Column SET AD_Reference_ID=18, AD_Reference_Value_ID=541534, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-10-31 09:52:34.593000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591449
;

-- 2025-10-31T09:53:00.743Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN PO_Incoterms_ID NUMERIC(10)')
;

-- 2025-10-31T09:53:00.934Z
ALTER TABLE C_BP_Group ADD CONSTRAINT POIncoterms_CBPGroup FOREIGN KEY (PO_Incoterms_ID) REFERENCES public.C_Incoterms DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BP_Group.PO_IncotermLocation
-- 2025-10-31T09:53:33.883Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591450,584188,0,10,394,'XX','PO_IncotermLocation',TO_TIMESTAMP('2025-10-31 09:53:33.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Anzugebender Ort für Handelsklausel','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'PO Incoterm Ort',0,0,TO_TIMESTAMP('2025-10-31 09:53:33.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-10-31T09:53:33.959Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591450 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-31T09:53:39.053Z
/* DDL */  select update_Column_Translation_From_AD_Element(584188)
;

-- 2025-10-31T09:54:10.210Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN PO_IncotermLocation VARCHAR(255)')
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> Incoterms
-- Column: C_BP_Group.C_Incoterms_ID
-- 2025-10-31T09:56:59.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591447,755916,0,322,0,TO_TIMESTAMP('2025-10-31 09:56:58.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Incoterms',0,0,330,0,1,1,TO_TIMESTAMP('2025-10-31 09:56:58.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T09:56:59.936Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T09:57:00.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927)
;

-- 2025-10-31T09:57:00.090Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755916
;

-- 2025-10-31T09:57:00.163Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755916)
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> Incoterm Ort
-- Column: C_BP_Group.IncotermLocation
-- 2025-10-31T09:57:30.808Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591448,755917,0,322,0,TO_TIMESTAMP('2025-10-31 09:57:29.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel',0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Incoterm Ort',0,0,340,0,1,1,TO_TIMESTAMP('2025-10-31 09:57:29.720000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T09:57:30.886Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T09:57:30.961Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608)
;

-- 2025-10-31T09:57:31.041Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755917
;

-- 2025-10-31T09:57:31.114Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755917)
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> PO Incoterms
-- Column: C_BP_Group.PO_Incoterms_ID
-- 2025-10-31T09:58:03.993Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591449,755918,0,322,0,TO_TIMESTAMP('2025-10-31 09:58:02.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'PO Incoterms',0,0,350,0,1,1,TO_TIMESTAMP('2025-10-31 09:58:02.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T09:58:04.068Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T09:58:04.141Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584187)
;

-- 2025-10-31T09:58:04.223Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755918
;

-- 2025-10-31T09:58:04.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755918)
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> PO Incoterm Ort
-- Column: C_BP_Group.PO_IncotermLocation
-- 2025-10-31T09:58:21.478Z
UPDATE AD_Field SET AD_Column_ID=591450, Description='Anzugebender Ort für Handelsklausel', Help=NULL, Name='PO Incoterm Ort',Updated=TO_TIMESTAMP('2025-10-31 09:58:21.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755918
;

-- 2025-10-31T09:58:21.552Z
UPDATE AD_Field_Trl trl SET Description='Anzugebender Ort für Handelsklausel',Name='PO Incoterm Ort' WHERE AD_Field_ID=755918 AND AD_Language='de_DE'
;

-- 2025-10-31T09:58:21.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584188)
;

-- 2025-10-31T09:58:21.723Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755918
;

-- 2025-10-31T09:58:21.871Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755918)
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> PO Incoterms
-- Column: C_BP_Group.PO_Incoterms_ID
-- 2025-10-31T09:59:28.325Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591449,755919,0,322,0,TO_TIMESTAMP('2025-10-31 09:59:27.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'PO Incoterms',0,0,360,0,1,1,TO_TIMESTAMP('2025-10-31 09:59:27.529000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-31T09:59:28.398Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=755919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-10-31T09:59:28.474Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584187)
;

-- 2025-10-31T09:59:28.550Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=755919
;

-- 2025-10-31T09:59:28.625Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(755919)
;

-- UI Column: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10
-- UI Element Group: Incoterms
-- 2025-10-31T10:00:05.509Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540290,553736,TO_TIMESTAMP('2025-10-31 10:00:05.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Incoterms',30,TO_TIMESTAMP('2025-10-31 10:00:05.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> Incoterms.Incoterms
-- Column: C_BP_Group.C_Incoterms_ID
-- 2025-10-31T10:00:52.321Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755916,0,322,553736,638525,'F',TO_TIMESTAMP('2025-10-31 10:00:51.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Incoterms',10,0,0,TO_TIMESTAMP('2025-10-31 10:00:51.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> Incoterms.Incoterm Ort
-- Column: C_BP_Group.IncotermLocation
-- 2025-10-31T10:01:21.231Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755917,0,322,553736,638526,'F',TO_TIMESTAMP('2025-10-31 10:01:20.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','Y','N','N','Y','N','N','N',0,'Incoterm Ort',20,0,0,TO_TIMESTAMP('2025-10-31 10:01:20.631000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> Incoterms.PO Incoterms
-- Column: C_BP_Group.PO_Incoterms_ID
-- 2025-10-31T10:01:47.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755919,0,322,553736,638527,'F',TO_TIMESTAMP('2025-10-31 10:01:46.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'PO Incoterms',30,0,0,TO_TIMESTAMP('2025-10-31 10:01:46.963000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> Incoterms.PO Incoterm Ort
-- Column: C_BP_Group.PO_IncotermLocation
-- 2025-10-31T10:02:13.403Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,755918,0,322,553736,638528,'F',TO_TIMESTAMP('2025-10-31 10:02:12.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anzugebender Ort für Handelsklausel','Y','N','N','Y','N','N','N',0,'PO Incoterm Ort',40,0,0,TO_TIMESTAMP('2025-10-31 10:02:12.838000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> Incoterm Ort
-- Column: C_BP_Group.IncotermLocation
-- 2025-10-31T11:51:20.306Z
UPDATE AD_Field SET DisplayLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-10-31 11:51:20.306000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755917
;

-- Field: Geschäftspartnergruppe_OLD(192,D) -> Geschäftspartnergruppe(322,D) -> PO Incoterm Ort
-- Column: C_BP_Group.PO_IncotermLocation
-- 2025-10-31T11:52:14.747Z
UPDATE AD_Field SET DisplayLogic='@PO_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-10-31 11:52:14.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=755918
;

-- Column: C_BP_Group.IncotermLocation
-- 2025-11-03T09:53:28.472Z
UPDATE AD_Column SET MandatoryLogic='@C_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-11-03 09:53:28.472000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591448
;

-- Column: C_BP_Group.PO_IncotermLocation
-- 2025-11-03T09:53:53.002Z
UPDATE AD_Column SET MandatoryLogic='@PO_Incoterms_ID/-1@>0',Updated=TO_TIMESTAMP('2025-11-03 09:53:53.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591450
;
