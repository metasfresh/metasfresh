SELECT backup_table('ExternalSystem_Config_LeichMehl_ProductMapping')
;

DELETE FROM ExternalSystem_Config_LeichMehl_ProductMapping WHERE TRUE
;

-- 2023-11-14T13:38:11.037Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl_ProductMapping','ALTER TABLE ExternalSystem_Config_LeichMehl_ProductMapping DROP COLUMN IF EXISTS ExternalSystem_Config_LeichMehl_ID')
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.ExternalSystem_Config_LeichMehl_ID
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.ExternalSystem_Config_LeichMehl_ID
-- 2023-11-14T13:38:11.194Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583477
;

-- 2023-11-14T13:38:11.211Z
DELETE FROM AD_Column WHERE AD_Column_ID=583477
;

-- 2023-11-14T13:41:00.213Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl_ProductMapping','ALTER TABLE ExternalSystem_Config_LeichMehl_ProductMapping DROP COLUMN IF EXISTS M_Product_Category_ID')
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.M_Product_Category_ID
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.M_Product_Category_ID
-- 2023-11-14T13:41:00.232Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583479
;

-- 2023-11-14T13:41:00.243Z
DELETE FROM AD_Column WHERE AD_Column_ID=583479
;

-- 2023-11-14T13:42:34.542Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl_ProductMapping','ALTER TABLE ExternalSystem_Config_LeichMehl_ProductMapping DROP COLUMN IF EXISTS SeqNo')
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.SeqNo
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.SeqNo
-- 2023-11-14T13:42:34.582Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=583487
;

-- 2023-11-14T13:42:34.590Z
DELETE FROM AD_Column WHERE AD_Column_ID=583487
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.M_Product_ID
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.M_Product_ID
-- 2023-11-14T14:53:32.968Z
UPDATE AD_Column SET IsMandatory='Y', IsParent='Y', IsUpdateable='N',Updated=TO_TIMESTAMP('2023-11-14 15:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583480
;

-- 2023-11-14T14:56:05.197Z
INSERT INTO t_alter_column values('externalsystem_config_leichmehl_productmapping','M_Product_ID','NUMERIC(10)',null,null)
;

-- 2023-11-14T14:56:05.208Z
INSERT INTO t_alter_column values('externalsystem_config_leichmehl_productmapping','M_Product_ID',null,'NOT NULL',null)
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T14:55:19.509Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587645,582801,0,19,542172,'LeichMehl_PluFile_ConfigGroup_ID',TO_TIMESTAMP('2023-11-14 15:55:19','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.externalsystem',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'PLU File Configuration',0,0,TO_TIMESTAMP('2023-11-14 15:55:19','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-11-14T14:55:19.516Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587645 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-11-14T14:55:19.594Z
/* DDL */  select update_Column_Translation_From_AD_Element(582801)
;

-- 2023-11-14T14:55:40.395Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_LeichMehl_ProductMapping','ALTER TABLE public.ExternalSystem_Config_LeichMehl_ProductMapping ADD COLUMN LeichMehl_PluFile_ConfigGroup_ID NUMERIC(10) NOT NULL')
;

-- 2023-11-14T14:55:40.435Z
ALTER TABLE ExternalSystem_Config_LeichMehl_ProductMapping ADD CONSTRAINT LeichMehlPluFileConfigGroup_ExternalSystemConfigLeichMehlProductMappin FOREIGN KEY (LeichMehl_PluFile_ConfigGroup_ID) REFERENCES public.LeichMehl_PluFile_ConfigGroup DEFERRABLE INITIALLY DEFERRED
;

CREATE UNIQUE INDEX IF NOT EXISTS M_Product_ID_C_BPartner_ID_UX
    ON ExternalSystem_Config_LeichMehl_ProductMapping (M_Product_ID, C_BPartner_ID)
    WHERE isActive = 'Y' AND C_BPartner_ID IS NOT NULL
;

CREATE UNIQUE INDEX IF NOT EXISTS M_Product_ID_C_BPartner_ID_NULL_UX
    ON ExternalSystem_Config_LeichMehl_ProductMapping (M_Product_ID)
    WHERE isActive = 'Y' AND C_BPartner_ID IS NULL
;

-- Element: ExternalSystem_Config_LeichMehl_ProductMapping_ID
-- 2023-11-15T15:23:34.213Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU Konfiguration', PrintName='PLU Konfiguration',Updated=TO_TIMESTAMP('2023-11-15 16:23:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581055 AND AD_Language='de_DE'
;

-- 2023-11-15T15:23:34.220Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581055,'de_DE')
;

-- 2023-11-15T15:23:34.304Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581055,'de_DE')
;

-- Element: ExternalSystem_Config_LeichMehl_ProductMapping_ID
-- 2023-11-15T15:25:25.491Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU Konfiguration', PrintName='PLU Konfiguration',Updated=TO_TIMESTAMP('2023-11-15 16:25:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581055 AND AD_Language='de_CH'
;

-- 2023-11-15T15:25:25.501Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581055,'de_CH')
;

-- Element: ExternalSystem_Config_LeichMehl_ProductMapping_ID
-- 2023-11-15T15:25:51.100Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='PLU Configuration', PrintName='PLU Configuration',Updated=TO_TIMESTAMP('2023-11-15 16:25:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581055 AND AD_Language='en_US'
;

-- 2023-11-15T15:25:51.103Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581055,'en_US')
;

-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- 2023-11-16T10:23:55.931Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=541252, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2023-11-16 11:23:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=583481
;

