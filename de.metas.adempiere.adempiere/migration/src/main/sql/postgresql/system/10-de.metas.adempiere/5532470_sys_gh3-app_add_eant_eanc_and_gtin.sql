-- 2019-09-30T08:58:46.929Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577133,0,'EAN_TU',TO_TIMESTAMP('2019-09-30 10:58:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','TU-EAN','TU-EAN',TO_TIMESTAMP('2019-09-30 10:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T08:58:46.932Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577133 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-30T08:59:11.235Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577134,0,'EAN_CU',TO_TIMESTAMP('2019-09-30 10:59:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CU-EAN','CU-EAN',TO_TIMESTAMP('2019-09-30 10:59:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T08:59:11.240Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577134 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-30T08:59:58.046Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577135,0,'GTIN',TO_TIMESTAMP('2019-09-30 10:59:57','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','GTIN','GTIN',TO_TIMESTAMP('2019-09-30 10:59:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T08:59:58.048Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577135 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-30T09:00:25.861Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577134, ColumnName='EAN_CU', Description=NULL, Help=NULL, Name='CU-EAN',Updated=TO_TIMESTAMP('2019-09-30 11:00:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551751
;

-- 2019-09-30T09:00:25.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CU-EAN', Description=NULL, Help=NULL WHERE AD_Column_ID=551751
;

-- 2019-09-30T09:00:25.910Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577134) 
;

-- 2019-09-30T09:02:12.364Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568980,577133,0,10,540645,'EAN_TU',TO_TIMESTAMP('2019-09-30 11:02:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',1,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','TU-EAN',0,0,TO_TIMESTAMP('2019-09-30 11:02:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:02:12.366Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568980 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:02:12.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577133) 
;

-- 2019-09-30T09:02:17.293Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FieldLength=50,Updated=TO_TIMESTAMP('2019-09-30 11:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568980
;

-- 2019-09-30T09:02:22.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN EAN_TU VARCHAR(50)')
;

-- 2019-09-30T09:02:40.503Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568981,577135,0,10,540645,'GTIN',TO_TIMESTAMP('2019-09-30 11:02:40','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','GTIN',0,0,TO_TIMESTAMP('2019-09-30 11:02:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:02:40.505Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568981 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:02:40.507Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577135) 
;

-- 2019-09-30T09:02:40.823Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN GTIN VARCHAR(50)')
;

-- 2019-09-30T09:06:17.657Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568982,577134,0,10,632,'EAN_CU',TO_TIMESTAMP('2019-09-30 11:06:17','YYYY-MM-DD HH24:MI:SS'),100,'N','D',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','CU-EAN',0,0,TO_TIMESTAMP('2019-09-30 11:06:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:06:17.658Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568982 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:06:17.660Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577134) 
;

-- 2019-09-30T09:06:37.432Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN EAN_CU VARCHAR(50)')
;

-- 2019-09-30T09:09:20.368Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=568982
;

-- 2019-09-30T09:09:20.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=568982
;

-- 2019-09-30T09:09:46.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568983,577134,0,10,632,'EAN_CU',TO_TIMESTAMP('2019-09-30 11:09:46','YYYY-MM-DD HH24:MI:SS'),100,'N','D',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','CU-EAN',0,0,TO_TIMESTAMP('2019-09-30 11:09:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:09:46.336Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568983 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:09:46.338Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577134) 
;

-- 2019-09-30T09:09:47.512Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner_product','EAN_CU','VARCHAR(50)',null,null)
;


select db_Alter_Table('EDI_DesadvLine', 'ALTER TABLE EDI_DesadvLine RENAME COLUMN UPC TO UPC_CU');


-- 2019-09-30T09:12:57.591Z-- 2019-09-30T09:26:58.788Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=603, ColumnName='UPC_UPC/EAN', Description='', Help='', Name='CU-UPC',Updated=TO_TIMESTAMP('2019-09-30 11:26:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551751
;

-- 2019-09-30T09:26:58.789Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CU-UPC', Description='', Help='' WHERE AD_Column_ID=551751
;

-- 2019-09-30T09:26:58.790Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(603) 
;

-- 2019-09-30T09:27:14.828Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568984,577134,0,10,540645,'EAN_CU',TO_TIMESTAMP('2019-09-30 11:27:14','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','CU-EAN',0,0,TO_TIMESTAMP('2019-09-30 11:27:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:27:14.829Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568984 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:27:14.830Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577134) 
;

-- 2019-09-30T09:27:23.636Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','EAN_CU','VARCHAR(50)',null,null)
;

-- 2019-09-30T09:28:13.334Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('edi_desadvline','EAN_TU','VARCHAR(50)',null,null)
;

-- 2019-09-30T09:29:53.258Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577136,0,'UPC_CU',TO_TIMESTAMP('2019-09-30 11:29:53','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','CU-UPC','CU-UPC',TO_TIMESTAMP('2019-09-30 11:29:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T09:29:53.259Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577136 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-30T09:30:13.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577137,0,'UPC-TU',TO_TIMESTAMP('2019-09-30 11:30:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','TU-UPC','TU-UPC',TO_TIMESTAMP('2019-09-30 11:30:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-30T09:30:13.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577137 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-30T09:30:38.722Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577136, ColumnName='UPC_CU', Description=NULL, Help=NULL, Name='CU-UPC',Updated=TO_TIMESTAMP('2019-09-30 11:30:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551751
;

-- 2019-09-30T09:30:38.723Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='CU-UPC', Description=NULL, Help=NULL WHERE AD_Column_ID=551751
;

-- 2019-09-30T09:30:38.724Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577136) 
;

-- 2019-09-30T09:30:51.673Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568985,577137,0,10,540645,'UPC-TU',TO_TIMESTAMP('2019-09-30 11:30:51','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','TU-UPC',0,0,TO_TIMESTAMP('2019-09-30 11:30:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:30:51.674Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568985 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:30:51.676Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577137) 
;

-- 2019-09-30T09:31:20.675Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='UPC_TU',Updated=TO_TIMESTAMP('2019-09-30 11:31:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577137
;

-- 2019-09-30T09:31:20.677Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UPC_TU', Name='TU-UPC', Description=NULL, Help=NULL WHERE AD_Element_ID=577137
;

-- 2019-09-30T09:31:20.679Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC_TU', Name='TU-UPC', Description=NULL, Help=NULL, AD_Element_ID=577137 WHERE UPPER(ColumnName)='UPC_TU' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-30T09:31:20.680Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC_TU', Name='TU-UPC', Description=NULL, Help=NULL WHERE AD_Element_ID=577137 AND IsCentrallyMaintained='Y'
;

-- 2019-09-30T09:31:25.598Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('EDI_DesadvLine','ALTER TABLE public.EDI_DesadvLine ADD COLUMN UPC_TU VARCHAR(50)')
;

-- 2019-09-30T09:36:37.919Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='UPC',Updated=TO_TIMESTAMP('2019-09-30 11:36:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603
;

-- 2019-09-30T09:36:37.922Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UPC', Name='CU-UPC', Description='', Help='' WHERE AD_Element_ID=603
;

-- 2019-09-30T09:36:37.925Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC', Name='CU-UPC', Description='', Help='', AD_Element_ID=603 WHERE UPPER(ColumnName)='UPC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-30T09:36:37.926Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC', Name='CU-UPC', Description='', Help='' WHERE AD_Element_ID=603 AND IsCentrallyMaintained='Y'
;

-- 2019-09-30T09:36:51.872Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UPC/EAN', PrintName='UPC/EAN',Updated=TO_TIMESTAMP('2019-09-30 11:36:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603 AND AD_Language='de_CH'
;

-- 2019-09-30T09:36:51.875Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(603,'de_CH') 
;

-- 2019-09-30T09:36:59.178Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UPC/EAN', PrintName='UPC/EAN',Updated=TO_TIMESTAMP('2019-09-30 11:36:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603 AND AD_Language='en_US'
;

-- 2019-09-30T09:36:59.179Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(603,'en_US') 
;

-- 2019-09-30T09:37:06.104Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='UPC/EAN', PrintName='UPC/EAN',Updated=TO_TIMESTAMP('2019-09-30 11:37:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=603 AND AD_Language='de_DE'
;

-- 2019-09-30T09:37:06.106Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(603,'de_DE') 
;

-- 2019-09-30T09:37:06.122Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(603,'de_DE') 
;

-- 2019-09-30T09:37:06.123Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='UPC', Name='UPC/EAN', Description='', Help='' WHERE AD_Element_ID=603
;

-- 2019-09-30T09:37:06.125Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC', Name='UPC/EAN', Description='', Help='', AD_Element_ID=603 WHERE UPPER(ColumnName)='UPC' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-30T09:37:06.126Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='UPC', Name='UPC/EAN', Description='', Help='' WHERE AD_Element_ID=603 AND IsCentrallyMaintained='Y'
;

-- 2019-09-30T09:37:06.127Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='UPC/EAN', Description='', Help='' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=603) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 603)
;

-- 2019-09-30T09:37:06.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='UPC/EAN', Name='UPC/EAN' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=603)
;

-- 2019-09-30T09:37:06.139Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='UPC/EAN', Description='', Help='', CommitWarning = NULL WHERE AD_Element_ID = 603
;

-- 2019-09-30T09:37:06.141Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='UPC/EAN', Description='', Help='' WHERE AD_Element_ID = 603
;

-- 2019-09-30T09:37:06.142Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'UPC/EAN', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 603
;

-- 2019-09-30T09:51:09.449Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568986,577133,0,10,540508,'EAN_TU',TO_TIMESTAMP('2019-09-30 11:51:09','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','TU-EAN',0,0,TO_TIMESTAMP('2019-09-30 11:51:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:51:09.450Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568986 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:51:09.452Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577133) 
;

-- 2019-09-30T09:51:09.781Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_PI_Item_Product','ALTER TABLE public.M_HU_PI_Item_Product ADD COLUMN EAN_TU VARCHAR(50)')
;

-- 2019-09-30T09:51:34.877Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Element_ID=577137, ColumnName='UPC_TU', Description=NULL, Help=NULL, Name='TU-UPC',Updated=TO_TIMESTAMP('2019-09-30 11:51:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549977
;

-- 2019-09-30T09:51:34.879Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='TU-UPC', Description=NULL, Help=NULL WHERE AD_Column_ID=549977
;

-- 2019-09-30T09:51:34.880Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577137) 
;

-- 2019-09-30T09:52:03.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsShowFilterIncrementButtons,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,568987,577135,0,10,540508,'GTIN',TO_TIMESTAMP('2019-09-30 11:52:03','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.handlingunits',50,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','GTIN',0,0,TO_TIMESTAMP('2019-09-30 11:52:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2019-09-30T09:52:03.233Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Column_ID=568987 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2019-09-30T09:52:03.238Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_Column_Translation_From_AD_Element(577135) 
;

-- 2019-09-30T09:52:05.114Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('M_HU_PI_Item_Product','ALTER TABLE public.M_HU_PI_Item_Product ADD COLUMN GTIN VARCHAR(50)')
;

-- 2019-09-30T09:52:10.137Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('m_hu_pi_item_product','EAN_TU','VARCHAR(50)',null,null)
;

-- 2019-09-30T10:09:54.889Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568984,0,TO_TIMESTAMP('2019-09-30 12:09:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550235,'N','N','CU-EAN',250,'E',TO_TIMESTAMP('2019-09-30 12:09:54','YYYY-MM-DD HH24:MI:SS'),100,'EAN_CU')
;

-- 2019-09-30T10:09:54.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568980,0,TO_TIMESTAMP('2019-09-30 12:09:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550236,'N','N','TU-EAN',260,'E',TO_TIMESTAMP('2019-09-30 12:09:54','YYYY-MM-DD HH24:MI:SS'),100,'EAN_TU')
;

-- 2019-09-30T10:09:55.077Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568981,0,TO_TIMESTAMP('2019-09-30 12:09:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550237,'N','N','GTIN',270,'E',TO_TIMESTAMP('2019-09-30 12:09:54','YYYY-MM-DD HH24:MI:SS'),100,'GTIN')
;

-- 2019-09-30T10:09:55.173Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,568985,0,TO_TIMESTAMP('2019-09-30 12:09:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540406,550238,'N','N','TU-UPC',280,'E',TO_TIMESTAMP('2019-09-30 12:09:55','YYYY-MM-DD HH24:MI:SS'),100,'UPC_TU')
;

-- 2019-09-30T10:10:07.762Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-09-30 12:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550235
;

-- 2019-09-30T10:10:08.382Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-09-30 12:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550236
;

-- 2019-09-30T10:10:09.031Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-09-30 12:10:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550237
;

-- 2019-09-30T10:10:16.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE EXP_FormatLine SET IsActive='Y',Updated=TO_TIMESTAMP('2019-09-30 12:10:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550238
;

