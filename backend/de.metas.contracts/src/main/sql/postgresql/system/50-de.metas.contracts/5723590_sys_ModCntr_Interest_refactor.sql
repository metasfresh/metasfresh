-- Run mode: SWING_CLIENT

-- 2024-05-13T14:31:15.563Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE ModCntr_Interest DROP COLUMN IF EXISTS Balance')
;

-- Column: ModCntr_Interest.Balance
-- 2024-05-13T14:31:15.635Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588233
;

-- 2024-05-13T14:31:15.640Z
DELETE FROM AD_Column WHERE AD_Column_ID=588233
;

-- Column: ModCntr_Interest.C_Currency_ID
-- 2024-05-13T14:31:51.445Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588262,193,0,19,542410,'C_Currency_ID',TO_TIMESTAMP('2024-05-13 17:31:44.089','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Die Währung für diesen Eintrag','de.metas.contracts',0,10,'Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2024-05-13 17:31:44.089','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-13T14:31:51.450Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588262 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-13T14:31:51.453Z
/* DDL */  select update_Column_Translation_From_AD_Element(193)
;

-- 2024-05-13T14:31:57.727Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE public.ModCntr_Interest ADD COLUMN C_Currency_ID NUMERIC(10) NOT NULL')
;

-- 2024-05-13T14:31:57.731Z
ALTER TABLE ModCntr_Interest ADD CONSTRAINT CCurrency_ModCntrInterest FOREIGN KEY (C_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2024-05-13T14:34:12.192Z
INSERT INTO t_alter_column values('modcntr_interest','Interest','NUMERIC',null,null)
;

-- 2024-05-13T14:34:22.469Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE ModCntr_Interest DROP COLUMN IF EXISTS Interest')
;

-- Column: ModCntr_Interest.Interest
-- 2024-05-13T14:34:22.478Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588234
;

-- 2024-05-13T14:34:22.482Z
DELETE FROM AD_Column WHERE AD_Column_ID=588234
;

-- 2024-05-13T14:35:36.934Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583107,0,'InterestScore',TO_TIMESTAMP('2024-05-13 17:35:36.78','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Zins Nummer','Zins Nummer',TO_TIMESTAMP('2024-05-13 17:35:36.78','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-05-13T14:35:36.935Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583107 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InterestScore
-- 2024-05-13T14:36:54.639Z
UPDATE AD_Element_Trl SET Name='Interest Score', PrintName='Interest Score',Updated=TO_TIMESTAMP('2024-05-13 17:36:54.639','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583107 AND AD_Language='en_US'
;

-- 2024-05-13T14:36:54.641Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583107,'en_US')
;

-- Element: InterestScore
-- 2024-05-13T14:36:59.414Z
UPDATE AD_Element_Trl SET Name='Zinsnummer', PrintName='Zinsnummer',Updated=TO_TIMESTAMP('2024-05-13 17:36:59.414','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583107 AND AD_Language='de_CH'
;

-- 2024-05-13T14:36:59.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583107,'de_CH')
;

-- Element: InterestScore
-- 2024-05-13T14:37:01.378Z
UPDATE AD_Element_Trl SET Name='Zinsnummer', PrintName='Zinsnummer',Updated=TO_TIMESTAMP('2024-05-13 17:37:01.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583107 AND AD_Language='de_DE'
;

-- 2024-05-13T14:37:01.380Z
UPDATE AD_Element SET Name='Zinsnummer', PrintName='Zinsnummer' WHERE AD_Element_ID=583107
;

-- 2024-05-13T14:37:01.629Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583107,'de_DE')
;

-- 2024-05-13T14:37:01.631Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583107,'de_DE')
;

-- Element: InterestScore
-- 2024-05-13T14:37:03.664Z
UPDATE AD_Element_Trl SET Name='Zinsnummer', PrintName='Zinsnummer',Updated=TO_TIMESTAMP('2024-05-13 17:37:03.664','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583107 AND AD_Language='fr_CH'
;

-- 2024-05-13T14:37:03.666Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583107,'fr_CH')
;

-- Element: InterestScore
-- 2024-05-13T14:37:08.748Z
UPDATE AD_Element_Trl SET Name='Zinsnummer', PrintName='Zinsnummer',Updated=TO_TIMESTAMP('2024-05-13 17:37:08.748','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583107 AND AD_Language='it_IT'
;

-- 2024-05-13T14:37:08.749Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583107,'it_IT')
;

-- Column: ModCntr_Interest.InterestScore
-- 2024-05-13T14:48:17.217Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588263,583107,0,22,542410,'InterestScore',TO_TIMESTAMP('2024-05-13 17:48:17.078','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zinsnummer',0,0,TO_TIMESTAMP('2024-05-13 17:48:17.078','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-05-13T14:48:17.219Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588263 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-05-13T14:48:17.221Z
/* DDL */  select update_Column_Translation_From_AD_Element(583107)
;

-- 2024-05-13T14:48:19.487Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE public.ModCntr_Interest ADD COLUMN InterestScore NUMERIC')
;

