-- Element: IsGenerated
-- 2024-12-03T11:46:35.378Z
UPDATE AD_Element_Trl SET Name='Generiert', PrintName='Generiert',Updated=TO_TIMESTAMP('2024-12-03 12:46:35.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=380 AND AD_Language='de_DE'
;

-- 2024-12-03T11:46:35.380Z
UPDATE AD_Element SET Name='Generiert', PrintName='Generiert' WHERE AD_Element_ID=380
;

-- 2024-12-03T11:46:35.681Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(380,'de_DE')
;

-- 2024-12-03T11:46:35.683Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(380,'de_DE')
;

-- Element: IsGenerated
-- 2024-12-03T11:46:42.508Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-12-03 12:46:42.508','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=380 AND AD_Language='de_DE'
;

-- 2024-12-03T11:46:42.510Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(380,'de_DE')
;

-- 2024-12-03T11:46:42.511Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(380,'de_DE')
;

-- Element: IsGenerated
-- 2024-12-03T11:47:02.842Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Generiert', PrintName='Generiert',Updated=TO_TIMESTAMP('2024-12-03 12:47:02.842','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=380 AND AD_Language='de_CH'
;

-- 2024-12-03T11:47:02.845Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(380,'de_CH')
;

-- Column: ModCntr_Module.IsGenerated
-- 2024-12-03T11:48:20.835Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589470,380,0,20,542340,'IsGenerated',TO_TIMESTAMP('2024-12-03 12:48:20.645','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','This Line is generated','de.metas.contracts',0,1,'The Generated checkbox identifies a journal line that was generated from a source document.  Lines could also be entered manually or imported.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Generiert',0,0,TO_TIMESTAMP('2024-12-03 12:48:20.645','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-12-03T11:48:20.843Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589470 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-12-03T11:48:20.846Z
/* DDL */  select update_Column_Translation_From_AD_Element(380)
;

-- 2024-12-03T11:48:23.724Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Module','ALTER TABLE public.ModCntr_Module ADD COLUMN IsGenerated CHAR(1) DEFAULT ''N'' CHECK (IsGenerated IN (''Y'',''N'')) NOT NULL')
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-12-03T11:53:46.323Z
UPDATE AD_Tab SET WhereClause='IsGenerated=''N''',Updated=TO_TIMESTAMP('2024-12-03 12:53:46.323','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Automatische Bausteine
-- Table: ModCntr_Module
-- 2024-12-03T11:53:58.130Z
UPDATE AD_Tab SET WhereClause='IsGenerated=''Y''',Updated=TO_TIMESTAMP('2024-12-03 12:53:58.13','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547697
;

