SELECT backup_table('LeichMehl_PluFile_Config')
;

DELETE FROM LeichMehl_PluFile_Config WHERE TRUE
;

-- 2023-11-14T08:30:13.081Z
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_Config','ALTER TABLE LeichMehl_PluFile_Config DROP COLUMN IF EXISTS ExternalSystem_Config_LeichMehl_ID')
;

-- Column: LeichMehl_PluFile_Config.ExternalSystem_Config_LeichMehl_ID
-- Column: LeichMehl_PluFile_Config.ExternalSystem_Config_LeichMehl_ID
-- 2023-11-14T08:30:13.160Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583578
;

-- 2023-11-14T08:30:13.164Z
DELETE FROM AD_Column WHERE AD_Column_ID=583578
;

-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_ConfigGroup_ID
-- Column: LeichMehl_PluFile_Config.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T08:36:14.072Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587644,582801,0,19,542182,'LeichMehl_PluFile_ConfigGroup_ID',TO_TIMESTAMP('2023-11-14 09:36:13','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y','N','N','N','N','N','N','N','N','N',0,'PLU File Configuration',0,0,TO_TIMESTAMP('2023-11-14 09:36:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-14T08:36:14.075Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-14T08:36:14.080Z
/* DDL */  select update_Column_Translation_From_AD_Element(582801)
;

-- 2023-11-14T08:36:33.992Z
/* DDL */ SELECT public.db_alter_table('LeichMehl_PluFile_Config','ALTER TABLE public.LeichMehl_PluFile_Config ADD COLUMN LeichMehl_PluFile_ConfigGroup_ID NUMERIC(10) NOT NULL')
;

-- 2023-11-14T08:36:34.003Z
ALTER TABLE LeichMehl_PluFile_Config ADD CONSTRAINT LeichMehlPluFileConfigGroup_LeichMehlPluFileConfig FOREIGN KEY (LeichMehl_PluFile_ConfigGroup_ID) REFERENCES public.LeichMehl_PluFile_ConfigGroup DEFERRABLE INITIALLY DEFERRED
;

CREATE UNIQUE INDEX IF NOT EXISTS LeichMehl_PluFile_ConfigGroup_ID_TargetFieldName_UX
    ON LeichMehl_PLUFile_Config (leichmehl_plufile_configgroup_id, targetfieldname)
    WHERE isActive = 'Y'
;
