-- Run mode: SWING_CLIENT

-- 2025-06-02T07:50:03.308Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583678,0,'Severity',TO_TIMESTAMP('2025-06-02 07:50:02.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Severity','Severity',TO_TIMESTAMP('2025-06-02 07:50:02.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T07:50:03.322Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583678 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Severity
-- 2025-06-02T07:51:57.237Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Schweregrad', PrintName='Schweregrad',Updated=TO_TIMESTAMP('2025-06-02 07:51:57.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583678 AND AD_Language='de_CH'
;

-- 2025-06-02T07:51:57.238Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T07:51:57.932Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583678,'de_CH')
;

-- Element: Severity
-- 2025-06-02T07:52:06.895Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Schweregrad', PrintName='Schweregrad',Updated=TO_TIMESTAMP('2025-06-02 07:52:06.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583678 AND AD_Language='de_DE'
;

-- 2025-06-02T07:52:06.896Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-06-02T07:52:07.948Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583678,'de_DE')
;

-- 2025-06-02T07:52:07.953Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583678,'de_DE')
;

-- Name: Severity
-- 2025-06-02T07:53:06.206Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541949,TO_TIMESTAMP('2025-06-02 07:53:06.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','Severity',TO_TIMESTAMP('2025-06-02 07:53:06.007000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'L')
;

-- 2025-06-02T07:53:06.214Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541949 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Severity
-- Value: Notice
-- ValueName: Notice
-- 2025-06-02T07:53:19.002Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541949,543921,TO_TIMESTAMP('2025-06-02 07:53:18.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Notice',TO_TIMESTAMP('2025-06-02 07:53:18.883000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Notice','Notice')
;

-- 2025-06-02T07:53:19.004Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543921 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Severity
-- Value: N
-- ValueName: Notice
-- 2025-06-02T07:53:38.279Z
UPDATE AD_Ref_List SET Value='N',Updated=TO_TIMESTAMP('2025-06-02 07:53:38.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=543921
;

-- Reference: Severity
-- Value: E
-- ValueName: Error
-- 2025-06-02T07:53:47.524Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541949,543922,TO_TIMESTAMP('2025-06-02 07:53:47.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Error',TO_TIMESTAMP('2025-06-02 07:53:47.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'E','Error')
;

-- 2025-06-02T07:53:47.526Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543922 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Severity -> E_Error
-- 2025-06-02T07:54:26.978Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Fehler',Updated=TO_TIMESTAMP('2025-06-02 07:54:26.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543922
;

-- 2025-06-02T07:54:26.985Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Severity -> E_Error
-- 2025-06-02T07:54:38.559Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Fehler',Updated=TO_TIMESTAMP('2025-06-02 07:54:38.559000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543922
;

-- 2025-06-02T07:54:38.560Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Severity -> N_Notice
-- 2025-06-02T07:55:30.163Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nachricht',Updated=TO_TIMESTAMP('2025-06-02 07:55:30.163000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543921
;

-- 2025-06-02T07:55:30.164Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Reference Item: Severity -> N_Notice
-- 2025-06-02T07:55:47.567Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nachricht',Updated=TO_TIMESTAMP('2025-06-02 07:55:47.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543921
;

-- 2025-06-02T07:55:47.568Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Column: AD_BusinessRule.Severity
-- 2025-06-02T07:56:44.488Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590135,583678,0,17,541949,542456,'XX','Severity',TO_TIMESTAMP('2025-06-02 07:56:44.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Schweregrad',0,0,TO_TIMESTAMP('2025-06-02 07:56:44.292000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-06-02T07:56:44.490Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590135 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-06-02T07:56:44.494Z
/* DDL */  select update_Column_Translation_From_AD_Element(583678)
;

-- 2025-06-02T07:56:46.460Z
/* DDL */ SELECT public.db_alter_table('AD_BusinessRule','ALTER TABLE public.AD_BusinessRule ADD COLUMN Severity VARCHAR(250) DEFAULT ''N''')
;




UPDATE AD_BusinessRule set Severity = 'N' WHERE Severity IS NULL;
