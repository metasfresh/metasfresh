-- 2022-11-03T15:04:31.972Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581634,0,'OrderCandidateSeqNo',TO_TIMESTAMP('2022-11-03 16:04:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kandidat Reihenfolge','Kandidat Reihenfolge',TO_TIMESTAMP('2022-11-03 16:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-03T15:04:32.070Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581634 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-11-03T15:05:32.778Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet',Updated=TO_TIMESTAMP('2022-11-03 16:05:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581634 AND AD_Language='de_DE'
;

-- 2022-11-03T15:05:32.894Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581634,'de_DE') 
;

-- 2022-11-03T15:05:33.112Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(581634,'de_DE') 
;

-- 2022-11-03T15:05:33.209Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='OrderCandidateSeqNo', Name='Kandidat Reihenfolge', Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet' WHERE AD_Element_ID=581634
;

-- 2022-11-03T15:05:33.302Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='OrderCandidateSeqNo', Name='Kandidat Reihenfolge', Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', AD_Element_ID=581634 WHERE UPPER(ColumnName)='ORDERCANDIDATESEQNO' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-11-03T15:05:33.401Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='OrderCandidateSeqNo', Name='Kandidat Reihenfolge', Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet' WHERE AD_Element_ID=581634 AND IsCentrallyMaintained='Y'
;

-- 2022-11-03T15:05:33.495Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Kandidat Reihenfolge', Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=581634) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 581634)
;

-- 2022-11-03T15:05:33.627Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Kandidat Reihenfolge', Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', CommitWarning = NULL WHERE AD_Element_ID = 581634
;

-- 2022-11-03T15:05:33.727Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Kandidat Reihenfolge', Description='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', Help='Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet' WHERE AD_Element_ID = 581634
;

-- 2022-11-03T15:05:33.822Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Kandidat Reihenfolge', Description = 'Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 581634
;

-- 2022-11-03T15:05:58.902Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='Record with lowest number will be processed first', Help='Record with lowest number will be processed first', IsTranslated='Y',Updated=TO_TIMESTAMP('2022-11-03 16:05:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581634 AND AD_Language='en_US'
;

-- 2022-11-03T15:05:59.044Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581634,'en_US') 
;

-- 2022-11-03T15:09:35.661Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584863,581634,0,11,541913,'OrderCandidateSeqNo',TO_TIMESTAMP('2022-11-03 16:09:34','YYYY-MM-DD HH24:MI:SS'),100,'N','@SQL=SELECT COALESCE(MAX(seqno),0)+10 AS DefaultValue FROM PP_Order_Candidate WHERE M_Product_ID=@M_Product_ID/0@','Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet','D',0,22,'Der Datensatz mit der niedrigsten Nummer wird zuerst bearbeitet','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Kandidat Reihenfolge',0,0,TO_TIMESTAMP('2022-11-03 16:09:34','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-03T15:09:35.781Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-03T15:09:35.993Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(581634) 
;

-- 2022-11-03T15:11:15.874Z
-- URL zum Konzept
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-11-03 16:11:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584863
;

-- 2022-11-03T15:11:40.940Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN OrderCandidateSeqNo NUMERIC(10)')
;

