-- Run mode: SWING_CLIENT

-- 2025-09-24T08:45:43.793Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584002,0,'IsInvoiceEmailCcToMember',TO_TIMESTAMP('2025-09-24 08:45:43.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Cc Email Rechnung an Mitglied','Cc Email Rechnung an Mitglied',TO_TIMESTAMP('2025-09-24 08:45:43.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T08:45:43.806Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584002 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:46:20.211Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-24 08:46:20.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='de_DE'
;

-- 2025-09-24T08:46:20.216Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584002,'de_DE')
;

-- 2025-09-24T08:46:20.224Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'de_DE')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:47:11.803Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoice Email CC to Member', PrintName='Invoice Email CC to Member',Updated=TO_TIMESTAMP('2025-09-24 08:47:11.803000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='en_US'
;

-- 2025-09-24T08:47:11.805Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:47:12.093Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'en_US')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:47:39.376Z
UPDATE AD_Element_Trl SET Description='If active, invoice emails are also sent as CC to the member.', Help='Enable this flag to automatically CC invoice emails to the member of the business partner group.',Updated=TO_TIMESTAMP('2025-09-24 08:47:39.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='en_US'
;

-- 2025-09-24T08:47:39.378Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:47:39.628Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'en_US')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:47:53.488Z
UPDATE AD_Element_Trl SET Name='Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.',Updated=TO_TIMESTAMP('2025-09-24 08:47:53.488000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='de_DE'
;

-- 2025-09-24T08:47:53.490Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:47:54.637Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584002,'de_DE')
;

-- 2025-09-24T08:47:54.639Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'de_DE')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:48:08.389Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.',Updated=TO_TIMESTAMP('2025-09-24 08:48:08.389000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='de_CH'
;

-- 2025-09-24T08:48:08.391Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:48:08.663Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'de_CH')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:48:26.584Z
UPDATE AD_Element_Trl SET Description='Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.', Help='Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.', PrintName='Cc Email Rechnung an Mitglied',Updated=TO_TIMESTAMP('2025-09-24 08:48:26.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='de_CH'
;

-- 2025-09-24T08:48:26.585Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:48:26.838Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'de_CH')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:48:38.903Z
UPDATE AD_Element_Trl SET Description='Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.', Name='Cc Email Rechnung an Mitglied',Updated=TO_TIMESTAMP('2025-09-24 08:48:38.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='de_DE'
;

-- 2025-09-24T08:48:38.906Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:48:39.379Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584002,'de_DE')
;

-- 2025-09-24T08:48:39.380Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'de_DE')
;

-- Element: IsInvoiceEmailCcToMember
-- 2025-09-24T08:48:48.952Z
UPDATE AD_Element_Trl SET Help='Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.',Updated=TO_TIMESTAMP('2025-09-24 08:48:48.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584002 AND AD_Language='de_DE'
;

-- 2025-09-24T08:48:48.953Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:48:49.390Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584002,'de_DE')
;

-- 2025-09-24T08:48:49.392Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584002,'de_DE')
;

-- Column: C_BP_Group.IsInvoiceEmailCcToMember
-- 2025-09-24T08:49:03.908Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591096,584002,0,20,394,'XX','IsInvoiceEmailCcToMember',TO_TIMESTAMP('2025-09-24 08:49:03.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.','D',0,1,'Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cc Email Rechnung an Mitglied',0,0,TO_TIMESTAMP('2025-09-24 08:49:03.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-24T08:49:03.911Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591096 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-24T08:49:03.916Z
/* DDL */  select update_Column_Translation_From_AD_Element(584002)
;

-- 2025-09-24T08:49:05.322Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN IsInvoiceEmailCcToMember CHAR(1) DEFAULT ''N'' CHECK (IsInvoiceEmailCcToMember IN (''Y'',''N'')) NOT NULL')
;

-- 2025-09-24T08:50:29.312Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584004,0,'CurrentEMailAddressCC',TO_TIMESTAMP('2025-09-24 08:50:29.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','D','Y','Cc Mailadresse','Cc Mailadresse',TO_TIMESTAMP('2025-09-24 08:50:29.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T08:50:29.314Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584004 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CurrentEMailAddressCC
-- 2025-09-24T08:50:39.034Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-24 08:50:39.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584004 AND AD_Language='de_CH'
;

-- 2025-09-24T08:50:39.037Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584004,'de_CH')
;

-- Element: CurrentEMailAddressCC
-- 2025-09-24T08:50:41.161Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-24 08:50:41.161000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584004 AND AD_Language='de_DE'
;

-- 2025-09-24T08:50:41.164Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584004,'de_DE')
;

-- 2025-09-24T08:50:41.167Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584004,'de_DE')
;

-- Element: CurrentEMailAddressCC
-- 2025-09-24T08:51:00.273Z
UPDATE AD_Element_Trl SET Name='Cc EMail', PrintName='Cc EMail',Updated=TO_TIMESTAMP('2025-09-24 08:51:00.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584004 AND AD_Language='en_US'
;

-- 2025-09-24T08:51:00.274Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:51:00.540Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584004,'en_US')
;

-- Element: CurrentEMailAddressCC
-- 2025-09-24T08:51:02.847Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-24 08:51:02.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584004 AND AD_Language='en_US'
;

-- 2025-09-24T08:51:02.850Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584004,'en_US')
;

-- Element: CurrentEMailAddressCC
-- 2025-09-24T08:52:23.098Z
UPDATE AD_Element_Trl SET Name='EMail Address CC', PrintName='EMail Address CC',Updated=TO_TIMESTAMP('2025-09-24 08:52:23.098000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584004 AND AD_Language='en_US'
;

-- 2025-09-24T08:52:23.100Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:52:23.376Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584004,'en_US')
;

-- Column: C_Doc_Outbound_Log.CurrentEMailAddressCC
-- 2025-09-24T08:52:42.017Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591097,584004,0,10,540453,'XX','CurrentEMailAddressCC',TO_TIMESTAMP('2025-09-24 08:52:41.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.document.archive',0,600,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cc Mailadresse','NP',0,0,TO_TIMESTAMP('2025-09-24 08:52:41.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-24T08:52:42.019Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591097 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-24T08:52:42.147Z
/* DDL */  select update_Column_Translation_From_AD_Element(584004)
;

-- 2025-09-24T08:52:44.807Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN CurrentEMailAddressCC VARCHAR(600)')
;

-- 2025-09-24T08:53:40.370Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584007,0,'CurrentEMailCCRecipient_ID',TO_TIMESTAMP('2025-09-24 08:53:40.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'','de.metas.document.archive','Y','Cc Mailempfänger','Cc Mailempfänger',TO_TIMESTAMP('2025-09-24 08:53:40.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T08:53:40.372Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584007 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CurrentEMailCCRecipient_ID
-- 2025-09-24T08:55:35.232Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-24 08:55:35.232000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584007 AND AD_Language='de_CH'
;

-- 2025-09-24T08:55:35.236Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584007,'de_CH')
;

-- Element: CurrentEMailCCRecipient_ID
-- 2025-09-24T08:55:38.655Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-24 08:55:38.655000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584007 AND AD_Language='de_DE'
;

-- 2025-09-24T08:55:38.658Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584007,'de_DE')
;

-- 2025-09-24T08:55:38.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584007,'de_DE')
;

-- Element: CurrentEMailCCRecipient_ID
-- 2025-09-24T08:55:52.197Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Cc Email Recipient', PrintName='Cc Email Recipient',Updated=TO_TIMESTAMP('2025-09-24 08:55:52.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584007 AND AD_Language='en_US'
;

-- 2025-09-24T08:55:52.200Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-24T08:55:52.476Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584007,'en_US')
;

-- Column: C_Doc_Outbound_Log.CurrentEMailCCRecipient_ID
-- 2025-09-24T08:56:24.856Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591098,584007,0,30,110,540453,123,'XX','CurrentEMailCCRecipient_ID',TO_TIMESTAMP('2025-09-24 08:56:24.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','de.metas.document.archive',10,'Y','N','Y','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cc Mailempfänger','NP',0,0,TO_TIMESTAMP('2025-09-24 08:56:24.660000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-24T08:56:24.859Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591098 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-24T08:56:24.963Z
/* DDL */  select update_Column_Translation_From_AD_Element(584007)
;

-- 2025-09-24T08:56:26.013Z
/* DDL */ SELECT public.db_alter_table('C_Doc_Outbound_Log','ALTER TABLE public.C_Doc_Outbound_Log ADD COLUMN CurrentEMailCCRecipient_ID NUMERIC(10)')
;

-- 2025-09-24T08:56:26.021Z
ALTER TABLE C_Doc_Outbound_Log ADD CONSTRAINT CurrentEMailCCRecipient_CDocOutboundLog FOREIGN KEY (CurrentEMailCCRecipient_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> Cc Mailadresse
-- Column: C_Doc_Outbound_Log.CurrentEMailAddressCC
-- 2025-09-24T09:06:07.484Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591097,754026,0,540474,TO_TIMESTAMP('2025-09-24 09:06:07.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',600,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Cc Mailadresse',TO_TIMESTAMP('2025-09-24 09:06:07.269000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T09:06:07.487Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T09:06:07.492Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584004)
;

-- 2025-09-24T09:06:07.504Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754026
;

-- 2025-09-24T09:06:07.512Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754026)
;

-- Field: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> Cc Mailempfänger
-- Column: C_Doc_Outbound_Log.CurrentEMailCCRecipient_ID
-- 2025-09-24T09:06:07.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591098,754027,0,540474,TO_TIMESTAMP('2025-09-24 09:06:07.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'de.metas.document.archive','Y','N','N','N','N','N','N','N','Cc Mailempfänger',TO_TIMESTAMP('2025-09-24 09:06:07.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T09:06:07.649Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T09:06:07.651Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584007)
;

-- 2025-09-24T09:06:07.654Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754027
;

-- 2025-09-24T09:06:07.656Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754027)
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Cc Mailempfänger
-- Column: C_Doc_Outbound_Log.CurrentEMailCCRecipient_ID
-- 2025-09-24T09:07:07.637Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754027,0,540474,540352,637268,'F',TO_TIMESTAMP('2025-09-24 09:07:07.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Cc Mailempfänger',112,140,0,TO_TIMESTAMP('2025-09-24 09:07:07.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Cc Mailadresse
-- Column: C_Doc_Outbound_Log.CurrentEMailAddressCC
-- 2025-09-24T09:07:23.637Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,754026,0,540474,540352,637269,'F',TO_TIMESTAMP('2025-09-24 09:07:23.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','Y','N','N','Cc Mailadresse',115,140,0,TO_TIMESTAMP('2025-09-24 09:07:23.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M')
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Cc Mailempfänger
-- Column: C_Doc_Outbound_Log.CurrentEMailCCRecipient_ID
-- 2025-09-24T09:07:47.079Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637268
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Cc Mailadresse
-- Column: C_Doc_Outbound_Log.CurrentEMailAddressCC
-- 2025-09-24T09:07:47.089Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.089000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=637269
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Anz. PDF
-- Column: C_Doc_Outbound_Log.PDFCount
-- 2025-09-24T09:07:47.099Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.099000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543693
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Anz. gespeichert
-- Column: C_Doc_Outbound_Log.StoreCount
-- 2025-09-24T09:07:47.108Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553992
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> stats.Zuletzt gespeichert
-- Column: C_Doc_Outbound_Log.DateLastStore
-- 2025-09-24T09:07:47.116Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.115000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=553993
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 10 -> default.Async Batch
-- Column: C_Doc_Outbound_Log.C_Async_Batch_ID
-- 2025-09-24T09:07:47.124Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.124000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=587063
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.per EDI übermitteln
-- Column: C_Doc_Outbound_Log.IsEdiEnabled
-- 2025-09-24T09:07:47.131Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=200,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543690
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> flags.EDI Status exportieren
-- Column: C_Doc_Outbound_Log.EDI_ExportStatus
-- 2025-09-24T09:07:47.139Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=210,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.139000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543705
;

-- UI Element: Ausgehende Belege(540170,de.metas.document.archive) -> Ausgehende Belege(540474,de.metas.document.archive) -> main -> 20 -> org.Sektion
-- Column: C_Doc_Outbound_Log.AD_Org_ID
-- 2025-09-24T09:07:47.147Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-09-24 09:07:47.147000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=543700
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Zahlungsweise
-- Column: C_BP_Group.PaymentRulePO
-- 2025-09-24T09:08:20.149Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590809,754028,0,322,TO_TIMESTAMP('2025-09-24 09:08:20.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Möglichkeiten der Bezahlung einer Bestellung',1,'D','"Zahlungsweise" zeigt die Arten der Zahlungen für Einkäufe an.','Y','N','N','N','N','N','N','N','Zahlungsweise',TO_TIMESTAMP('2025-09-24 09:08:20.009000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T09:08:20.152Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T09:08:20.154Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(950)
;

-- 2025-09-24T09:08:20.165Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754028
;

-- 2025-09-24T09:08:20.167Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754028)
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Cc Email Rechnung an Mitglied
-- Column: C_BP_Group.IsInvoiceEmailCcToMember
-- 2025-09-24T09:08:20.271Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591096,754029,0,322,TO_TIMESTAMP('2025-09-24 09:08:20.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.',1,'D','Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.','Y','N','N','N','N','N','N','N','Cc Email Rechnung an Mitglied',TO_TIMESTAMP('2025-09-24 09:08:20.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T09:08:20.273Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T09:08:20.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584002)
;

-- 2025-09-24T09:08:20.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754029
;

-- 2025-09-24T09:08:20.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754029)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Cc Email Rechnung an Mitglied
-- Column: C_BP_Group.IsInvoiceEmailCcToMember
-- 2025-09-24T09:08:57.692Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754029,0,322,540480,637270,'F',TO_TIMESTAMP('2025-09-24 09:08:57.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.','Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.','Y','N','N','Y','N','N','N',0,'Cc Email Rechnung an Mitglied',50,0,0,TO_TIMESTAMP('2025-09-24 09:08:57.463000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> main -> 10 -> default.Cc Email Rechnung an Mitglied
-- Column: C_BP_Group.IsInvoiceEmailCcToMember
-- 2025-09-24T12:57:56.643Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637270
;

-- 2025-09-24T12:58:11.869Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754029
;

-- Field: Geschäftspartnergruppe(192,D) -> Geschäftspartnergruppe(322,D) -> Cc Email Rechnung an Mitglied
-- Column: C_BP_Group.IsInvoiceEmailCcToMember
-- 2025-09-24T12:58:11.879Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=754029
;

-- 2025-09-24T12:58:11.888Z
DELETE FROM AD_Field WHERE AD_Field_ID=754029
;

-- 2025-09-24T12:58:42.032Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE C_BP_Group DROP COLUMN IF EXISTS IsInvoiceEmailCcToMember')
;

-- Column: C_BP_Group.IsInvoiceEmailCcToMember
-- 2025-09-24T12:58:42.582Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=591096
;

-- 2025-09-24T12:58:42.590Z
DELETE FROM AD_Column WHERE AD_Column_ID=591096
;

-- Column: C_BPartner.IsInvoiceEmailCcToMember
-- 2025-09-24T12:59:21.197Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591099,584002,0,20,291,'XX','IsInvoiceEmailCcToMember',TO_TIMESTAMP('2025-09-24 12:59:20.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.','D',0,1,'Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Cc Email Rechnung an Mitglied',0,0,TO_TIMESTAMP('2025-09-24 12:59:20.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-09-24T12:59:21.203Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591099 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-09-24T12:59:21.333Z
/* DDL */  select update_Column_Translation_From_AD_Element(584002)
;

-- 2025-09-24T12:59:22.625Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsInvoiceEmailCcToMember CHAR(1) DEFAULT ''N'' CHECK (IsInvoiceEmailCcToMember IN (''Y'',''N'')) NOT NULL')
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ESR Drucken
-- Column: C_BPartner.Fresh_IsPrintESR
-- 2025-09-24T13:00:50.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65577,754030,0,220,TO_TIMESTAMP('2025-09-24 13:00:50.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','ESR Drucken',TO_TIMESTAMP('2025-09-24 13:00:50.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:50.836Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:50.840Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55919)
;

-- 2025-09-24T13:00:50.846Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754030
;

-- 2025-09-24T13:00:50.852Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754030)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Gebinde Saldo an drucken auf LS
-- Column: C_BPartner.Fresh_ContainersBalanceToPrintOnLS
-- 2025-09-24T13:00:50.974Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65579,754031,0,220,TO_TIMESTAMP('2025-09-24 13:00:50.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Gebinde Saldo an drucken auf LS',TO_TIMESTAMP('2025-09-24 13:00:50.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:50.976Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:50.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542153)
;

-- 2025-09-24T13:00:50.984Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754031
;

-- 2025-09-24T13:00:50.985Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754031)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Preis Liste Senden
-- Column: C_BPartner.Fresh_SendPriceList
-- 2025-09-24T13:00:51.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65580,754032,0,220,TO_TIMESTAMP('2025-09-24 13:00:50.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Preis Liste Senden',TO_TIMESTAMP('2025-09-24 13:00:50.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.105Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542154)
;

-- 2025-09-24T13:00:51.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754032
;

-- 2025-09-24T13:00:51.111Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754032)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> VK Preis auf LS
-- Column: C_BPartner.Fresh_RetailPriceOnLS
-- 2025-09-24T13:00:51.226Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65581,754033,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','VK Preis auf LS',TO_TIMESTAMP('2025-09-24 13:00:51.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.229Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.230Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542155)
;

-- 2025-09-24T13:00:51.233Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754033
;

-- 2025-09-24T13:00:51.234Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754033)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Auszeichnungspreis auf LS
-- Column: C_BPartner.Fresh_AwardPriceOnLS
-- 2025-09-24T13:00:51.362Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,65582,754034,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Auszeichnungspreis auf LS',TO_TIMESTAMP('2025-09-24 13:00:51.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.366Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(55918)
;

-- 2025-09-24T13:00:51.368Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754034
;

-- 2025-09-24T13:00:51.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754034)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Default contact
-- Column: C_BPartner.Default_User_ID
-- 2025-09-24T13:00:51.503Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546704,754035,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Default contact',TO_TIMESTAMP('2025-09-24 13:00:51.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.504Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.507Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541717)
;

-- 2025-09-24T13:00:51.514Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754035
;

-- 2025-09-24T13:00:51.515Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754035)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Rechnungsstandort
-- Column: C_BPartner.Default_Bill_Location_ID
-- 2025-09-24T13:00:51.651Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546705,754036,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Standort des Geschäftspartners für die Rechnungsstellung',10,'D','Y','N','N','N','N','N','N','N','Rechnungsstandort',TO_TIMESTAMP('2025-09-24 13:00:51.519000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.655Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541718)
;

-- 2025-09-24T13:00:51.658Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754036
;

-- 2025-09-24T13:00:51.659Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754036)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Default ship location
-- Column: C_BPartner.Default_Ship_Location_ID
-- 2025-09-24T13:00:51.781Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,546706,754037,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Default ship location',TO_TIMESTAMP('2025-09-24 13:00:51.663000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.783Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.785Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541719)
;

-- 2025-09-24T13:00:51.787Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754037
;

-- 2025-09-24T13:00:51.789Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754037)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Migration_Key
-- Column: C_BPartner.Migration_Key
-- 2025-09-24T13:00:51.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549131,754038,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'When data is imported from a an external datasource, this element can be used to identify the data record',255,'D','Y','N','N','N','N','N','N','N','Migration_Key',TO_TIMESTAMP('2025-09-24 13:00:51.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:51.911Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:51.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542124)
;

-- 2025-09-24T13:00:51.922Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754038
;

-- 2025-09-24T13:00:51.923Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754038)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Allow Line Discount
-- Column: C_BPartner.Fresh_AllowLineDiscount
-- 2025-09-24T13:00:52.051Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549327,754039,0,220,TO_TIMESTAMP('2025-09-24 13:00:51.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Allow Line Discount',TO_TIMESTAMP('2025-09-24 13:00:51.926000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.052Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754039 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542151)
;

-- 2025-09-24T13:00:52.057Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754039
;

-- 2025-09-24T13:00:52.059Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754039)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Anbauplanung
-- Column: C_BPartner.IsPlanning
-- 2025-09-24T13:00:52.189Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549800,754040,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Anbauplanung',TO_TIMESTAMP('2025-09-24 13:00:52.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.191Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.192Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542243)
;

-- 2025-09-24T13:00:52.201Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754040
;

-- 2025-09-24T13:00:52.203Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754040)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ADR-Zertifizierung
-- Column: C_BPartner.Fresh_Certification
-- 2025-09-24T13:00:52.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549801,754041,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,16,'D','Y','N','N','N','N','N','N','N','ADR-Zertifizierung',TO_TIMESTAMP('2025-09-24 13:00:52.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.331Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542244)
;

-- 2025-09-24T13:00:52.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754041
;

-- 2025-09-24T13:00:52.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754041)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Flag Urproduzent
-- Column: C_BPartner.Fresh_Urproduzent
-- 2025-09-24T13:00:52.469Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549845,754042,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Flag Urproduzent',TO_TIMESTAMP('2025-09-24 13:00:52.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.471Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.473Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542256)
;

-- 2025-09-24T13:00:52.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754042
;

-- 2025-09-24T13:00:52.479Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754042)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Flag Produzentenabrechnung
-- Column: C_BPartner.Fresh_Produzentenabrechnung
-- 2025-09-24T13:00:52.599Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549846,754043,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Flag Produzentenabrechnung',TO_TIMESTAMP('2025-09-24 13:00:52.483000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.601Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.603Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542257)
;

-- 2025-09-24T13:00:52.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754043
;

-- 2025-09-24T13:00:52.608Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754043)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> AdRRegion
-- Column: C_BPartner.Fresh_AdRRegion
-- 2025-09-24T13:00:52.738Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549865,754044,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','AdRRegion',TO_TIMESTAMP('2025-09-24 13:00:52.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.741Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.742Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542261)
;

-- 2025-09-24T13:00:52.746Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754044
;

-- 2025-09-24T13:00:52.747Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754044)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ADR Kunde
-- Column: C_BPartner.IsADRCustomer
-- 2025-09-24T13:00:52.870Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550255,754045,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','ADR Kunde',TO_TIMESTAMP('2025-09-24 13:00:52.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:52.873Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:52.875Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542343)
;

-- 2025-09-24T13:00:52.880Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754045
;

-- 2025-09-24T13:00:52.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754045)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> ADR Lieferant
-- Column: C_BPartner.IsADRVendor
-- 2025-09-24T13:00:53Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550445,754046,0,220,TO_TIMESTAMP('2025-09-24 13:00:52.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','ADR Lieferant',TO_TIMESTAMP('2025-09-24 13:00:52.885000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.003Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.005Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542398)
;

-- 2025-09-24T13:00:53.009Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754046
;

-- 2025-09-24T13:00:53.011Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754046)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> AdRRegionVendor
-- Column: C_BPartner.Fresh_AdRVendorRegion
-- 2025-09-24T13:00:53.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550447,754047,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','AdRRegionVendor',TO_TIMESTAMP('2025-09-24 13:00:53.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.155Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542399)
;

-- 2025-09-24T13:00:53.159Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754047
;

-- 2025-09-24T13:00:53.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754047)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lager
-- Column: C_BPartner.M_Warehouse_ID
-- 2025-09-24T13:00:53.281Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550682,754048,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2025-09-24 13:00:53.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.283Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.285Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(459)
;

-- 2025-09-24T13:00:53.417Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754048
;

-- 2025-09-24T13:00:53.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754048)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferung
-- Column: C_BPartner.PO_DeliveryViaRule
-- 2025-09-24T13:00:53.540Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550687,754049,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wie der Auftrag geliefert wird',2,'D','"Lieferung durch" gibt an, auf welche Weise die Produkte geliefert werden sollen. Beispiel: wird abgeholt oder geliefert?','Y','N','N','N','N','N','N','N','Lieferung',TO_TIMESTAMP('2025-09-24 13:00:53.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.543Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.546Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542451)
;

-- 2025-09-24T13:00:53.552Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754049
;

-- 2025-09-24T13:00:53.553Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754049)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Eingangsrechungen
-- Column: C_BPartner.PO_Invoice_Aggregation_ID
-- 2025-09-24T13:00:53.690Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551863,754050,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Optionale Konfigurationsregel für das Kopf-Aggregationsmerkmal bei Rechnungskandidaten mit "Verkaufsrechnung = Nein". Wenn nicht gesetzt wird ein Standardwert benutzt.',10,'D','Y','N','N','N','N','N','N','N','Aggregationsregel für Eingangsrechungen',TO_TIMESTAMP('2025-09-24 13:00:53.557000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.694Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.698Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542718)
;

-- 2025-09-24T13:00:53.704Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754050
;

-- 2025-09-24T13:00:53.706Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754050)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Ausgangsrechungen
-- Column: C_BPartner.SO_Invoice_Aggregation_ID
-- 2025-09-24T13:00:53.820Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551864,754051,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Optionale Konfigurationsregel für das Kopf-Aggregationsmerkmal bei Rechnungskandidaten mit "Verkaufsrechnung = Ja". Wenn nicht gesetzt wird ein Standardwert benutzt.',10,'D','Y','N','N','N','N','N','N','N','Aggregationsregel für Ausgangsrechungen',TO_TIMESTAMP('2025-09-24 13:00:53.709000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.822Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.825Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542719)
;

-- 2025-09-24T13:00:53.829Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754051
;

-- 2025-09-24T13:00:53.830Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754051)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Gebinde auf Lsch. ausblenden
-- Column: C_BPartner.IsHidePackingMaterialInShipmentPrint
-- 2025-09-24T13:00:53.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552285,754052,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuert ob Gebindezeilen beim Lieferscheindruck verborgen werden',1,'D','Wenn gesetzt, werden Gebindezeilen im ausgedruckten Lieferschein ausgeblendet','Y','N','N','N','N','N','N','N','Gebinde auf Lsch. ausblenden',TO_TIMESTAMP('2025-09-24 13:00:53.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:53.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754052 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:53.954Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542803)
;

-- 2025-09-24T13:00:53.962Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754052
;

-- 2025-09-24T13:00:53.963Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754052)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Ausgangsrechungen (Rechnungsposition)
-- Column: C_BPartner.SO_InvoiceLine_Aggregation_ID
-- 2025-09-24T13:00:54.090Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552547,754053,0,220,TO_TIMESTAMP('2025-09-24 13:00:53.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Aggregationsregel für Ausgangsrechungen (Rechnungsposition)',TO_TIMESTAMP('2025-09-24 13:00:53.966000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.092Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754053 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.095Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542849)
;

-- 2025-09-24T13:00:54.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754053
;

-- 2025-09-24T13:00:54.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754053)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregationsregel für Eingangsrechungen (Rechnungsposition)
-- Column: C_BPartner.PO_InvoiceLine_Aggregation_ID
-- 2025-09-24T13:00:54.230Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552548,754054,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Aggregationsregel für Eingangsrechungen (Rechnungsposition)',TO_TIMESTAMP('2025-09-24 13:00:54.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.231Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754054 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542848)
;

-- 2025-09-24T13:00:54.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754054
;

-- 2025-09-24T13:00:54.240Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754054)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Kundengruppe
-- Column: C_BPartner.Customer_Group_ID
-- 2025-09-24T13:00:54.360Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552717,754055,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Kundengruppe',TO_TIMESTAMP('2025-09-24 13:00:54.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.361Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754055 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.364Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542888)
;

-- 2025-09-24T13:00:54.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754055
;

-- 2025-09-24T13:00:54.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754055)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> keine Bestellkontrolle
-- Column: C_BPartner.IsDisableOrderCheckup
-- 2025-09-24T13:00:54.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552778,754056,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Legt fest, dass beim Fertigstellen von Aufträgen zu diesem Kunden keine automatischen Bestellkontroll-Druckstücke erzeugt werden',1,'D','Y','N','N','N','N','N','N','N','keine Bestellkontrolle',TO_TIMESTAMP('2025-09-24 13:00:54.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754056 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.495Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542907)
;

-- 2025-09-24T13:00:54.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754056
;

-- 2025-09-24T13:00:54.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754056)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Statistik Gruppe
-- Column: C_BPartner.Salesgroup
-- 2025-09-24T13:00:54.628Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,553014,754057,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,250,'D','Y','N','N','N','N','N','N','N','Statistik Gruppe',TO_TIMESTAMP('2025-09-24 13:00:54.508000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.632Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754057 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.633Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542949)
;

-- 2025-09-24T13:00:54.643Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754057
;

-- 2025-09-24T13:00:54.644Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754057)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Sales Responsible
-- Column: C_BPartner.SalesRepIntern_ID
-- 2025-09-24T13:00:54.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557022,754058,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Sales Responsible Internal',10,'D','','Y','N','N','N','N','N','N','N','Sales Responsible',TO_TIMESTAMP('2025-09-24 13:00:54.648000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.791Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.793Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543385)
;

-- 2025-09-24T13:00:54.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754058
;

-- 2025-09-24T13:00:54.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754058)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Wiedervorlage Datum Innen
-- Column: C_BPartner.ReminderDateIntern
-- 2025-09-24T13:00:54.921Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557023,754059,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Wiedervorlage Datum Innen',TO_TIMESTAMP('2025-09-24 13:00:54.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:54.923Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:54.925Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543386)
;

-- 2025-09-24T13:00:54.930Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754059
;

-- 2025-09-24T13:00:54.931Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754059)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Wiedervorlage Datum Aussen
-- Column: C_BPartner.ReminderDateExtern
-- 2025-09-24T13:00:55.048Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557024,754060,0,220,TO_TIMESTAMP('2025-09-24 13:00:54.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Wiedervorlage Datum Aussen',TO_TIMESTAMP('2025-09-24 13:00:54.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.050Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543387)
;

-- 2025-09-24T13:00:55.055Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754060
;

-- 2025-09-24T13:00:55.057Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754060)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Ort
-- Column: C_BPartner.City
-- 2025-09-24T13:00:55.180Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557178,754061,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Name des Ortes',60,'D','Bezeichnet einen einzelnen Ort in diesem Land oder dieser Region.','Y','N','N','N','N','N','N','N','Ort',TO_TIMESTAMP('2025-09-24 13:00:55.060000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.185Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.189Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(225)
;

-- 2025-09-24T13:00:55.210Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754061
;

-- 2025-09-24T13:00:55.212Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754061)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> PLZ
-- Column: C_BPartner.Postal
-- 2025-09-24T13:00:55.339Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557179,754062,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Postleitzahl',10,'D','"PLZ" bezeichnet die Postleitzahl für diese Adresse oder dieses Postfach.','Y','N','N','N','N','N','N','N','PLZ',TO_TIMESTAMP('2025-09-24 13:00:55.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.341Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.343Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(512)
;

-- 2025-09-24T13:00:55.363Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754062
;

-- 2025-09-24T13:00:55.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754062)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Straße und Nr.
-- Column: C_BPartner.Address1
-- 2025-09-24T13:00:55.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557180,754063,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Adresszeile 1 für diesen Standort',100,'D','"Adresszeile 1" gibt die Anschrift für diesen Standort an.','Y','N','N','N','N','N','N','N','Straße und Nr.',TO_TIMESTAMP('2025-09-24 13:00:55.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.496Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(156)
;

-- 2025-09-24T13:00:55.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754063
;

-- 2025-09-24T13:00:55.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754063)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> eMail
-- Column: C_BPartner.EMail
-- 2025-09-24T13:00:55.631Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,557181,754064,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2025-09-24 13:00:55.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881)
;

-- 2025-09-24T13:00:55.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754064
;

-- 2025-09-24T13:00:55.671Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754064)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Global ID
-- Column: C_BPartner.GlobalId
-- 2025-09-24T13:00:55.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,558551,754065,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Global ID',TO_TIMESTAMP('2025-09-24 13:00:55.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.793Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.794Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543753)
;

-- 2025-09-24T13:00:55.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754065
;

-- 2025-09-24T13:00:55.805Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754065)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Großhandelserlaubnis §52a AMG
-- Column: C_BPartner.IsPharmaWholesalePermission
-- 2025-09-24T13:00:55.929Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559720,754066,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Großhandelserlaubnis §52a AMG',TO_TIMESTAMP('2025-09-24 13:00:55.809000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:55.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:55.934Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543965)
;

-- 2025-09-24T13:00:55.938Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754066
;

-- 2025-09-24T13:00:55.939Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754066)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Arzneivermittler §52c Abs.1-3 AMG
-- Column: C_BPartner.IsPharmaAgentPermission
-- 2025-09-24T13:00:56.060Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559721,754067,0,220,TO_TIMESTAMP('2025-09-24 13:00:55.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Arzneivermittler §52c Abs.1-3 AMG',TO_TIMESTAMP('2025-09-24 13:00:55.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543967)
;

-- 2025-09-24T13:00:56.070Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754067
;

-- 2025-09-24T13:00:56.072Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754067)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Tierärtzliche Hausapotheke §67 ApoG
-- Column: C_BPartner.IsVeterinaryPharmacyPermission
-- 2025-09-24T13:00:56.190Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559722,754068,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Tierärtzliche Hausapotheke §67 ApoG',TO_TIMESTAMP('2025-09-24 13:00:56.075000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.193Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543968)
;

-- 2025-09-24T13:00:56.197Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754068
;

-- 2025-09-24T13:00:56.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754068)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Herstellererlaubnis §13 AMG
-- Column: C_BPartner.IsPharmaManufacturerPermission
-- 2025-09-24T13:00:56.320Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559723,754069,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Herstellererlaubnis §13 AMG',TO_TIMESTAMP('2025-09-24 13:00:56.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.323Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.324Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543966)
;

-- 2025-09-24T13:00:56.327Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754069
;

-- 2025-09-24T13:00:56.328Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754069)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Herstellererlaubnis §13 AMG
-- Column: C_BPartner.IsPharmaVendorManufacturerPermission
-- 2025-09-24T13:00:56.454Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559725,754070,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Herstellererlaubnis §13 AMG',TO_TIMESTAMP('2025-09-24 13:00:56.331000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.456Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.458Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543971)
;

-- 2025-09-24T13:00:56.462Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754070
;

-- 2025-09-24T13:00:56.463Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754070)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Arzneimittelvermittler §52c Abs.1-3 AMG
-- Column: C_BPartner.IsPharmaVendorAgentPermission
-- 2025-09-24T13:00:56.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559726,754071,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Arzneimittelvermittler §52c Abs.1-3 AMG',TO_TIMESTAMP('2025-09-24 13:00:56.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543970)
;

-- 2025-09-24T13:00:56.579Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754071
;

-- 2025-09-24T13:00:56.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754071)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Großhandelserlaubnis §52a AMG
-- Column: C_BPartner.IsPharmaVendorWholesalePermission
-- 2025-09-24T13:00:56.701Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559727,754072,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Großhandelserlaubnis §52a AMG',TO_TIMESTAMP('2025-09-24 13:00:56.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.704Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543969)
;

-- 2025-09-24T13:00:56.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754072
;

-- 2025-09-24T13:00:56.710Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754072)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferberechtigung
-- Column: C_BPartner.ShipmentPermissionPharma
-- 2025-09-24T13:00:56.810Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559728,754073,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Lieferberechtigung',TO_TIMESTAMP('2025-09-24 13:00:56.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.813Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.815Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543972)
;

-- 2025-09-24T13:00:56.819Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754073
;

-- 2025-09-24T13:00:56.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754073)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Bezugsberechtigung
-- Column: C_BPartner.ReceiptPermissionPharma
-- 2025-09-24T13:00:56.941Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559729,754074,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Bezugsberechtigung',TO_TIMESTAMP('2025-09-24 13:00:56.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:56.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:56.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543973)
;

-- 2025-09-24T13:00:56.948Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754074
;

-- 2025-09-24T13:00:56.949Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754074)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Datum Typ A Erlaubnis
-- Column: C_BPartner.ShipmentPermissionChangeDate
-- 2025-09-24T13:00:57.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559730,754075,0,220,TO_TIMESTAMP('2025-09-24 13:00:56.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Datum Typ A Erlaubnis',TO_TIMESTAMP('2025-09-24 13:00:56.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.071Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.072Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543974)
;

-- 2025-09-24T13:00:57.076Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754075
;

-- 2025-09-24T13:00:57.077Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754075)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Datum Typ A Erlaubnis
-- Column: C_BPartner.ReceiptPermissionChangeDate
-- 2025-09-24T13:00:57.199Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,559731,754076,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Datum Typ A Erlaubnis',TO_TIMESTAMP('2025-09-24 13:00:57.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.201Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.203Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543975)
;

-- 2025-09-24T13:00:57.205Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754076
;

-- 2025-09-24T13:00:57.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754076)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aggregate Purchase Orders
-- Column: C_BPartner.IsAggregatePO
-- 2025-09-24T13:00:57.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560060,754077,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Aggregate Purchase Orders',TO_TIMESTAMP('2025-09-24 13:00:57.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.331Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544052)
;

-- 2025-09-24T13:00:57.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754077
;

-- 2025-09-24T13:00:57.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754077)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betreuer Vertrieb
-- Column: C_BPartner.SalesResponsible
-- 2025-09-24T13:00:57.461Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560062,754078,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Betreuer Vertrieb',TO_TIMESTAMP('2025-09-24 13:00:57.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.465Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544053)
;

-- 2025-09-24T13:00:57.469Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754078
;

-- 2025-09-24T13:00:57.471Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754078)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Einkaufsgr./Haupt-Apo.
-- Column: C_BPartner.PurchaseGroup
-- 2025-09-24T13:00:57.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560064,754079,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Einkaufsgr./Haupt-Apo.',TO_TIMESTAMP('2025-09-24 13:00:57.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544060)
;

-- 2025-09-24T13:00:57.581Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754079
;

-- 2025-09-24T13:00:57.582Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754079)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Verbandszugehörigkeit
-- Column: C_BPartner.AssociationMembership
-- 2025-09-24T13:00:57.699Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560065,754080,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Verbandszugehörigkeit',TO_TIMESTAMP('2025-09-24 13:00:57.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.701Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.704Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544055)
;

-- 2025-09-24T13:00:57.708Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754080
;

-- 2025-09-24T13:00:57.709Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754080)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferberechtigung(Old)
-- Column: C_BPartner.ShipmentPermissionPharma_Old
-- 2025-09-24T13:00:57.824Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560066,754081,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezugsberechtigung',60,'D','Bezugsberechtigung
','Y','N','N','N','N','N','N','N','Lieferberechtigung(Old)',TO_TIMESTAMP('2025-09-24 13:00:57.712000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544056)
;

-- 2025-09-24T13:00:57.831Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754081
;

-- 2025-09-24T13:00:57.832Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754081)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Hersteller/GH/Apotheke
-- Column: C_BPartner.PermissionPharmaType
-- 2025-09-24T13:00:57.961Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560067,754082,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Hersteller/GH/Apotheke ',TO_TIMESTAMP('2025-09-24 13:00:57.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:57.964Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:57.966Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544057)
;

-- 2025-09-24T13:00:57.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754082
;

-- 2025-09-24T13:00:57.970Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754082)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Mindesthaltbarkeit Tage
-- Column: C_BPartner.ShelfLifeMinDays
-- 2025-09-24T13:00:58.092Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560068,754083,0,220,TO_TIMESTAMP('2025-09-24 13:00:57.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mindesthaltbarkeit in Tagen, bezogen auf das Mindesthaltbarkeitsdatum einer Produktinstanz',3,'D','Miminum Shelf Life of products with Guarantee Date instance. If > 0 you cannot select products with a shelf life less than the minum shelf life, unless you select "Show All"','Y','N','N','N','N','N','N','N','Mindesthaltbarkeit Tage',TO_TIMESTAMP('2025-09-24 13:00:57.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.095Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.098Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2264)
;

-- 2025-09-24T13:00:58.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754083
;

-- 2025-09-24T13:00:58.110Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754083)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> KV-Gebiet (Info)
-- Column: C_BPartner.Region
-- 2025-09-24T13:00:58.227Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560069,754084,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',60,'D','','Y','N','N','N','N','N','N','N','KV-Gebiet (Info) ',TO_TIMESTAMP('2025-09-24 13:00:58.114000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.229Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.231Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544061)
;

-- 2025-09-24T13:00:58.234Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754084
;

-- 2025-09-24T13:00:58.235Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754084)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Samstag Öffnungszeiten
-- Column: C_BPartner.WeekendOpeningTimes
-- 2025-09-24T13:00:58.358Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560070,754085,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Samstag Öffnungszeiten ',TO_TIMESTAMP('2025-09-24 13:00:58.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.360Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.362Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544058)
;

-- 2025-09-24T13:00:58.366Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754085
;

-- 2025-09-24T13:00:58.368Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754085)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> URL3
-- Column: C_BPartner.URL3
-- 2025-09-24T13:00:58.487Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560188,754086,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Full URL address - e.g. http://www.adempiere.org',400,'D','The URL defines an fully qualified web address like http://www.adempiere.org','Y','N','N','N','N','N','N','N','URL3',TO_TIMESTAMP('2025-09-24 13:00:58.372000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.489Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.492Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544091)
;

-- 2025-09-24T13:00:58.495Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754086
;

-- 2025-09-24T13:00:58.496Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754086)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betreuer Einkauf
-- Column: C_BPartner.VendorResponsible
-- 2025-09-24T13:00:58.616Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560212,754087,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Betreuer Einkauf ',TO_TIMESTAMP('2025-09-24 13:00:58.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.619Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.621Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544099)
;

-- 2025-09-24T13:00:58.625Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754087
;

-- 2025-09-24T13:00:58.626Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754087)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Mindestbestellwertinfo
-- Column: C_BPartner.MinimumOrderValue
-- 2025-09-24T13:00:58.740Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560213,754088,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Mindestbestellwertinfo',TO_TIMESTAMP('2025-09-24 13:00:58.630000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.742Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.745Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544100)
;

-- 2025-09-24T13:00:58.749Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754088
;

-- 2025-09-24T13:00:58.750Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754088)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Retouren-Telefax
-- Column: C_BPartner.RetourFax
-- 2025-09-24T13:00:58.869Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560214,754089,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Retouren-Telefax ',TO_TIMESTAMP('2025-09-24 13:00:58.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.872Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.875Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544101)
;

-- 2025-09-24T13:00:58.879Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754089
;

-- 2025-09-24T13:00:58.881Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754089)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Pharma_Phone
-- Column: C_BPartner.Pharma_Phone
-- 2025-09-24T13:00:58.993Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560215,754090,0,220,TO_TIMESTAMP('2025-09-24 13:00:58.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Pharma_Phone',TO_TIMESTAMP('2025-09-24 13:00:58.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:58.996Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754090 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:58.998Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544102)
;

-- 2025-09-24T13:00:59Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754090
;

-- 2025-09-24T13:00:59.002Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754090)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Contacts
-- Column: C_BPartner.Contacts
-- 2025-09-24T13:00:59.101Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560216,754091,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Contacts',TO_TIMESTAMP('2025-09-24 13:00:59.004000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.102Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754091 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.104Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544103)
;

-- 2025-09-24T13:00:59.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754091
;

-- 2025-09-24T13:00:59.109Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754091)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Lieferanten Kategorie
-- Column: C_BPartner.VendorCategory
-- 2025-09-24T13:00:59.221Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560219,754092,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lieferanten Kategorie',255,'D','','Y','N','N','N','N','N','N','N','Lieferanten Kategorie',TO_TIMESTAMP('2025-09-24 13:00:59.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.223Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754092 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.225Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(622)
;

-- 2025-09-24T13:00:59.237Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754092
;

-- 2025-09-24T13:00:59.238Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754092)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Qualification
-- Column: C_BPartner.Qualification
-- 2025-09-24T13:00:59.361Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560220,754093,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Qualification ',TO_TIMESTAMP('2025-09-24 13:00:59.242000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.362Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754093 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.364Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544098)
;

-- 2025-09-24T13:00:59.369Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754093
;

-- 2025-09-24T13:00:59.370Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754093)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Eigene-Kd. Nr.
-- Column: C_BPartner.CustomerNoAtVendor
-- 2025-09-24T13:00:59.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560221,754094,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Eigene-Kd. Nr. ',TO_TIMESTAMP('2025-09-24 13:00:59.373000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754094 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.495Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544104)
;

-- 2025-09-24T13:00:59.498Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754094
;

-- 2025-09-24T13:00:59.500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754094)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Pharma_Fax
-- Column: C_BPartner.Pharma_Fax
-- 2025-09-24T13:00:59.621Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560227,754095,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Pharma_Fax',TO_TIMESTAMP('2025-09-24 13:00:59.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.623Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754095 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.625Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544105)
;

-- 2025-09-24T13:00:59.628Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754095
;

-- 2025-09-24T13:00:59.629Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754095)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Status-Info
-- Column: C_BPartner.StatusInfo
-- 2025-09-24T13:00:59.749Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560228,754096,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Status-Info',TO_TIMESTAMP('2025-09-24 13:00:59.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.751Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754096 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.752Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544106)
;

-- 2025-09-24T13:00:59.755Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754096
;

-- 2025-09-24T13:00:59.756Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754096)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Manufacturer
-- Column: C_BPartner.IsManufacturer
-- 2025-09-24T13:00:59.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560428,754097,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Manufacturer',TO_TIMESTAMP('2025-09-24 13:00:59.761000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:00:59.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754097 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:00:59.885Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544129)
;

-- 2025-09-24T13:00:59.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754097
;

-- 2025-09-24T13:00:59.889Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754097)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> GDP Certificate
-- Column: C_BPartner.GDPCertificateCustomer
-- 2025-09-24T13:01:00.010Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560500,754098,0,220,TO_TIMESTAMP('2025-09-24 13:00:59.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','GDP Certificate',TO_TIMESTAMP('2025-09-24 13:00:59.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.013Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754098 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.017Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544138)
;

-- 2025-09-24T13:01:00.021Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754098
;

-- 2025-09-24T13:01:00.023Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754098)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> GDP Certificate
-- Column: C_BPartner.GDPCertificateVendor
-- 2025-09-24T13:01:00.149Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560501,754099,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','GDP Certificate',TO_TIMESTAMP('2025-09-24 13:01:00.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.151Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754099 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.153Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544139)
;

-- 2025-09-24T13:01:00.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754099
;

-- 2025-09-24T13:01:00.157Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754099)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> QMS Certificate
-- Column: C_BPartner.QMSCertificateCustomer
-- 2025-09-24T13:01:00.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560502,754100,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','QMS Certificate',TO_TIMESTAMP('2025-09-24 13:01:00.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754100 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.276Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544140)
;

-- 2025-09-24T13:01:00.279Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754100
;

-- 2025-09-24T13:01:00.281Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754100)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> QMS Certificate
-- Column: C_BPartner.QMSCertificateVendor
-- 2025-09-24T13:01:00.400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560503,754101,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','QMS Certificate',TO_TIMESTAMP('2025-09-24 13:01:00.284000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.401Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754101 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.404Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544141)
;

-- 2025-09-24T13:01:00.407Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754101
;

-- 2025-09-24T13:01:00.408Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754101)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Self Disclosure
-- Column: C_BPartner.SelfDisclosureCustomer
-- 2025-09-24T13:01:00.530Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560504,754102,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Self Disclosure',TO_TIMESTAMP('2025-09-24 13:01:00.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.533Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754102 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.535Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544145)
;

-- 2025-09-24T13:01:00.538Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754102
;

-- 2025-09-24T13:01:00.539Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754102)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Self Disclosure
-- Column: C_BPartner.SelfDisclosureVendor
-- 2025-09-24T13:01:00.651Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560505,754103,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Self Disclosure',TO_TIMESTAMP('2025-09-24 13:01:00.543000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.653Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754103 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.655Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544144)
;

-- 2025-09-24T13:01:00.659Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754103
;

-- 2025-09-24T13:01:00.660Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754103)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Certificate of Registration
-- Column: C_BPartner.CertificateOfRegistrationCustomer
-- 2025-09-24T13:01:00.783Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560506,754104,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Certificate of Registration',TO_TIMESTAMP('2025-09-24 13:01:00.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.785Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754104 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.786Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544142)
;

-- 2025-09-24T13:01:00.789Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754104
;

-- 2025-09-24T13:01:00.791Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754104)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Certificate of Registration
-- Column: C_BPartner.CertificateOfRegistrationVendor
-- 2025-09-24T13:01:00.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560507,754105,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Certificate of Registration',TO_TIMESTAMP('2025-09-24 13:01:00.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:00.911Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754105 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:00.913Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544143)
;

-- 2025-09-24T13:01:00.916Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754105
;

-- 2025-09-24T13:01:00.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754105)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Contact Status Information
-- Column: C_BPartner.ContactStatusInfoCustomer
-- 2025-09-24T13:01:01.051Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560508,754106,0,220,TO_TIMESTAMP('2025-09-24 13:01:00.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Contact Status Information',TO_TIMESTAMP('2025-09-24 13:01:00.920000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754106 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.055Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544146)
;

-- 2025-09-24T13:01:01.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754106
;

-- 2025-09-24T13:01:01.060Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754106)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Contact Status Information
-- Column: C_BPartner.ContactStatusInfoVendor
-- 2025-09-24T13:01:01.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560509,754107,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Contact Status Information',TO_TIMESTAMP('2025-09-24 13:01:01.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754107 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.176Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544147)
;

-- 2025-09-24T13:01:01.179Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754107
;

-- 2025-09-24T13:01:01.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754107)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Externe ID
-- Column: C_BPartner.ExternalId
-- 2025-09-24T13:01:01.300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563299,754108,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2025-09-24 13:01:01.184000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.303Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754108 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.305Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2025-09-24T13:01:01.324Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754108
;

-- 2025-09-24T13:01:01.325Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754108)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Anbieter-Nr.
-- Column: C_BPartner.IFA_Manufacturer
-- 2025-09-24T13:01:01.441Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564554,754109,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,5,'D','Y','N','N','N','N','N','N','N','Anbieter-Nr.',TO_TIMESTAMP('2025-09-24 13:01:01.329000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.442Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754109 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.444Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576251)
;

-- 2025-09-24T13:01:01.450Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754109
;

-- 2025-09-24T13:01:01.451Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754109)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betäubungsmittel
-- Column: C_BPartner.IsPharmaCustomerNarcoticsPermission
-- 2025-09-24T13:01:01.570Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567353,754110,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Betäubungsmittel',TO_TIMESTAMP('2025-09-24 13:01:01.454000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754110 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.574Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576507)
;

-- 2025-09-24T13:01:01.579Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754110
;

-- 2025-09-24T13:01:01.580Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754110)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Betäubungsmittel
-- Column: C_BPartner.IsPharmaVendorNarcoticsPermission
-- 2025-09-24T13:01:01.702Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567354,754111,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Betäubungsmittel',TO_TIMESTAMP('2025-09-24 13:01:01.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.704Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754111 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.706Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576506)
;

-- 2025-09-24T13:01:01.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754111
;

-- 2025-09-24T13:01:01.710Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754111)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> BTM
-- Column: C_BPartner.BTM
-- 2025-09-24T13:01:01.840Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567934,754112,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,25,'D','Y','N','N','N','N','N','N','N','BTM',TO_TIMESTAMP('2025-09-24 13:01:01.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.843Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754112 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.845Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576728)
;

-- 2025-09-24T13:01:01.849Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754112
;

-- 2025-09-24T13:01:01.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754112)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Telefon (alternativ)
-- Column: C_BPartner.Phone2
-- 2025-09-24T13:01:01.961Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568274,754113,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Alternative Telefonnummer',255,'D','','Y','N','N','N','N','N','N','N','Telefon (alternativ)',TO_TIMESTAMP('2025-09-24 13:01:01.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:01.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754113 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:01.966Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(506)
;

-- 2025-09-24T13:01:01.978Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754113
;

-- 2025-09-24T13:01:01.979Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754113)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> URL2
-- Column: C_BPartner.URL2
-- 2025-09-24T13:01:02.102Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568428,754114,0,220,TO_TIMESTAMP('2025-09-24 13:01:01.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Vollständige Web-Addresse, z.B. https://metasfresh.com/',512,'D','','Y','N','N','N','N','N','N','N','URL2',TO_TIMESTAMP('2025-09-24 13:01:01.983000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.105Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754114 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576916)
;

-- 2025-09-24T13:01:02.112Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754114
;

-- 2025-09-24T13:01:02.113Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754114)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Aktionsbezug
-- Column: C_BPartner.IsAllowActionPrice
-- 2025-09-24T13:01:02.230Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568477,754115,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn auf "ja" gesetzt, dann werden bei der Preisberechnung auch Aktionspreise berücksichtigt.',1,'D','Y','N','N','N','N','N','N','N','Aktionsbezug',TO_TIMESTAMP('2025-09-24 13:01:02.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.233Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754115 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.235Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576946)
;

-- 2025-09-24T13:01:02.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754115
;

-- 2025-09-24T13:01:02.241Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754115)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> PLV von Basis
-- Column: C_BPartner.IsAllowPriceMutation
-- 2025-09-24T13:01:02.371Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568595,754116,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Von Basis derivierte PLV erlauben ',1,'D','Y','N','N','N','N','N','N','N','PLV von Basis',TO_TIMESTAMP('2025-09-24 13:01:02.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754116 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.375Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577030)
;

-- 2025-09-24T13:01:02.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754116
;

-- 2025-09-24T13:01:02.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754116)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Standard Lieferstadt
-- Column: C_BPartner.DefaultShipTo_City
-- 2025-09-24T13:01:02.502Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569793,754117,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Standard Lieferstadt',TO_TIMESTAMP('2025-09-24 13:01:02.384000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.503Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754117 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.506Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577445)
;

-- 2025-09-24T13:01:02.511Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754117
;

-- 2025-09-24T13:01:02.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754117)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Standard Liefer PLZ
-- Column: C_BPartner.DefaultShipTo_Postal
-- 2025-09-24T13:01:02.630Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569794,754118,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','Standard Liefer PLZ',TO_TIMESTAMP('2025-09-24 13:01:02.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.631Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754118 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.634Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577446)
;

-- 2025-09-24T13:01:02.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754118
;

-- 2025-09-24T13:01:02.638Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754118)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Datum Haddex Prüfung
-- Column: C_BPartner.DateHaddexCheck
-- 2025-09-24T13:01:02.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572467,754119,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Datum Haddex Prüfung',TO_TIMESTAMP('2025-09-24 13:01:02.641000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.753Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754119 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.756Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578608)
;

-- 2025-09-24T13:01:02.761Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754119
;

-- 2025-09-24T13:01:02.762Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754119)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Haddex Kontroll Nr.
-- Column: C_BPartner.HaddexControlNr
-- 2025-09-24T13:01:02.881Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572468,754120,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1000,'D','Y','N','N','N','N','N','N','N','Haddex Kontroll Nr.',TO_TIMESTAMP('2025-09-24 13:01:02.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:02.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:02.886Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578609)
;

-- 2025-09-24T13:01:02.890Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754120
;

-- 2025-09-24T13:01:02.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754120)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Haddex Prüfung erforderlich
-- Column: C_BPartner.IsHaddexCheck
-- 2025-09-24T13:01:03.022Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572470,754121,0,220,TO_TIMESTAMP('2025-09-24 13:01:02.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Haddex Prüfung erforderlich',TO_TIMESTAMP('2025-09-24 13:01:02.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.023Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754121 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.025Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578664)
;

-- 2025-09-24T13:01:03.030Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754121
;

-- 2025-09-24T13:01:03.031Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754121)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Org Mapping
-- Column: C_BPartner.AD_Org_Mapping_ID
-- 2025-09-24T13:01:03.159Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573097,754122,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Org Mapping',TO_TIMESTAMP('2025-09-24 13:01:03.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.161Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754122 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578815)
;

-- 2025-09-24T13:01:03.168Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754122
;

-- 2025-09-24T13:01:03.169Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754122)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> PO_Incoterm
-- Column: C_BPartner.PO_Incoterm
-- 2025-09-24T13:01:03.301Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573903,754123,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Internationale Handelsklauseln (engl. International Commercial Terms)',3,'D','Incoterms (International Commercial Terms, deutsch: Internationale Handelsklauseln) sind eine Reihe internationaler Regeln zur Interpretation spezifizierter Handelsbedingungen im Außenhandelsgeschäft.

Die Incoterms wurden von der Internationalen Handelskammer (International Chamber of Commerce, ICC) erstmals 1936 aufgestellt, um eine gemeinsame Basis für den internationalen Handel zu schaffen. Sie regeln vor allem die Art und Weise der Lieferung von Gütern. Die Bestimmungen legen fest, welche Transportkosten der Verkäufer, welche der Käufer zu tragen hat und wer im Falle eines Verlustes der Ware das finanzielle Risiko trägt. Die Incoterms geben jedoch keine Auskunft darüber, wann und wo das Eigentum an der Ware von dem Verkäufer auf den Käufer übergeht. Der Stand der Incoterms wird durch Angabe der Jahreszahl gekennzeichnet. Aktuell gelten die Incoterms 2000 (6. Revision).','Y','N','N','N','N','N','N','N','PO_Incoterm',TO_TIMESTAMP('2025-09-24 13:01:03.172000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.304Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754123 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.306Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579178)
;

-- 2025-09-24T13:01:03.309Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754123
;

-- 2025-09-24T13:01:03.310Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754123)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Archiviert
-- Column: C_BPartner.IsArchived
-- 2025-09-24T13:01:03.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573948,754124,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Archiviert',TO_TIMESTAMP('2025-09-24 13:01:03.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.435Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754124 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.437Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578977)
;

-- 2025-09-24T13:01:03.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754124
;

-- 2025-09-24T13:01:03.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754124)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Is Alberta doctor
-- Column: C_BPartner.IsAlbertaDoctor
-- 2025-09-24T13:01:03.561Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573949,754125,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Is Alberta doctor',TO_TIMESTAMP('2025-09-24 13:01:03.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.562Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754125 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.564Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579162)
;

-- 2025-09-24T13:01:03.567Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754125
;

-- 2025-09-24T13:01:03.568Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754125)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Titel
-- Column: C_BPartner.AlbertaTitle
-- 2025-09-24T13:01:03.683Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573950,754126,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Titel',TO_TIMESTAMP('2025-09-24 13:01:03.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.685Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754126 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.687Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579194)
;

-- 2025-09-24T13:01:03.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754126
;

-- 2025-09-24T13:01:03.691Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754126)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Kurztitel
-- Column: C_BPartner.TitleShort
-- 2025-09-24T13:01:03.821Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573951,754127,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Kurztitel',TO_TIMESTAMP('2025-09-24 13:01:03.694000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.824Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754127 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.825Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579163)
;

-- 2025-09-24T13:01:03.828Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754127
;

-- 2025-09-24T13:01:03.830Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754127)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Role
-- Column: C_BPartner.AlbertaRole
-- 2025-09-24T13:01:03.940Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573952,754128,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','','Y','N','N','N','N','N','N','N','Role',TO_TIMESTAMP('2025-09-24 13:01:03.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:03.942Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:03.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579189)
;

-- 2025-09-24T13:01:03.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754128
;

-- 2025-09-24T13:01:03.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754128)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Keine Werbung
-- Column: C_BPartner.ExcludeFromPromotions
-- 2025-09-24T13:01:04.070Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574474,754129,0,220,TO_TIMESTAMP('2025-09-24 13:01:03.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Keine Werbung',TO_TIMESTAMP('2025-09-24 13:01:03.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.073Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754129 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.074Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579361)
;

-- 2025-09-24T13:01:04.079Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754129
;

-- 2025-09-24T13:01:04.080Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754129)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Referrer
-- Column: C_BPartner.Referrer
-- 2025-09-24T13:01:04.230Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574475,754130,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referring web address',255,'D','Y','N','N','N','N','N','N','N','Referrer',TO_TIMESTAMP('2025-09-24 13:01:04.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.232Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754130 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.233Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1429)
;

-- 2025-09-24T13:01:04.241Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754130
;

-- 2025-09-24T13:01:04.242Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754130)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> MKTG_Campaign
-- Column: C_BPartner.MKTG_Campaign_ID
-- 2025-09-24T13:01:04.340Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574563,754131,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','MKTG_Campaign',TO_TIMESTAMP('2025-09-24 13:01:04.246000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754131 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.344Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544034)
;

-- 2025-09-24T13:01:04.348Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754131
;

-- 2025-09-24T13:01:04.349Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754131)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Rechnungsstellung (Kreditoren)
-- Column: C_BPartner.PO_InvoiceRule
-- 2025-09-24T13:01:04.460Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578381,754132,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,250,'D','Y','N','N','N','N','N','N','N','Rechnungsstellung (Kreditoren)',TO_TIMESTAMP('2025-09-24 13:01:04.353000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.462Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754132 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580209)
;

-- 2025-09-24T13:01:04.469Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754132
;

-- 2025-09-24T13:01:04.470Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754132)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Alter Wert
-- Column: C_BPartner.OldValue
-- 2025-09-24T13:01:04.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579084,754133,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The old file data',250,'D','Old data overwritten in the field','Y','N','N','N','N','N','N','N','Alter Wert',TO_TIMESTAMP('2025-09-24 13:01:04.473000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.583Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754133 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.585Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2066)
;

-- 2025-09-24T13:01:04.592Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754133
;

-- 2025-09-24T13:01:04.594Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754133)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Suchschlüssel Alt (Kunde)
-- Column: C_BPartner.Old_Value_Customer
-- 2025-09-24T13:01:04.712Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579093,754134,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,250,'D','Y','N','N','N','N','N','N','N','Suchschlüssel Alt (Kunde)',TO_TIMESTAMP('2025-09-24 13:01:04.598000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.713Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.715Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580484)
;

-- 2025-09-24T13:01:04.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754134
;

-- 2025-09-24T13:01:04.719Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754134)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Suchschlüssel Alt (Lieferant)
-- Column: C_BPartner.Old_Value_Vendor
-- 2025-09-24T13:01:04.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579094,754135,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,250,'D','Y','N','N','N','N','N','N','N','Suchschlüssel Alt (Lieferant)',TO_TIMESTAMP('2025-09-24 13:01:04.723000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.834Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.836Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580485)
;

-- 2025-09-24T13:01:04.838Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754135
;

-- 2025-09-24T13:01:04.839Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754135)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Incoterms (Lieferant)
-- Column: C_BPartner.C_Incoterms_Vendor_ID
-- 2025-09-24T13:01:04.940Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579095,754136,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Incoterms (Lieferant)',TO_TIMESTAMP('2025-09-24 13:01:04.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:04.941Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:04.943Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580487)
;

-- 2025-09-24T13:01:04.946Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754136
;

-- 2025-09-24T13:01:04.948Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754136)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Incoterms (Kunde)
-- Column: C_BPartner.C_Incoterms_Customer_ID
-- 2025-09-24T13:01:05.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,579096,754137,0,220,TO_TIMESTAMP('2025-09-24 13:01:04.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Incoterms (Kunde)',TO_TIMESTAMP('2025-09-24 13:01:04.951000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754137 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580486)
;

-- 2025-09-24T13:01:05.068Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754137
;

-- 2025-09-24T13:01:05.069Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754137)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Wareneingangsinfo
-- Column: C_BPartner.MaterialReceiptInfo
-- 2025-09-24T13:01:05.182Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589627,754138,0,220,TO_TIMESTAMP('2025-09-24 13:01:05.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,2000,'D','Y','N','N','N','N','N','N','N','Wareneingangsinfo',TO_TIMESTAMP('2025-09-24 13:01:05.072000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754138 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583461)
;

-- 2025-09-24T13:01:05.187Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754138
;

-- 2025-09-24T13:01:05.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754138)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Vollständige LU Erforderlich
-- Column: C_BPartner.IsFullLURequired
-- 2025-09-24T13:01:05.300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589919,754139,0,220,TO_TIMESTAMP('2025-09-24 13:01:05.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.',1,'D','Dieses Flag gibt an, ob der Partner erfordert, dass die Ladeeinheit (LU) vollständig ist, wenn Produktmengen zu einer Auftragsposition hinzugefügt werden. Wenn das Flag auf ''Y'' gesetzt ist, muss die Auftragsposition eine vollständige Ladeeinheit enthalten. Wenn es auf ''N'' gesetzt ist, sind teilweise Ladeeinheiten für die Auftragsposition erlaubt.','Y','N','N','N','N','N','N','N','Vollständige LU Erforderlich',TO_TIMESTAMP('2025-09-24 13:01:05.193000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.302Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754139 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583599)
;

-- 2025-09-24T13:01:05.308Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754139
;

-- 2025-09-24T13:01:05.309Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754139)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Preis auf Lieferschein
-- Column: C_BPartner.IsShipmentPricePrinted
-- 2025-09-24T13:01:05.411Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590131,754140,0,220,TO_TIMESTAMP('2025-09-24 13:01:05.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','','Y','N','N','N','N','N','N','N','Preis auf Lieferschein',TO_TIMESTAMP('2025-09-24 13:01:05.312000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.413Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754140 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.415Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583670)
;

-- 2025-09-24T13:01:05.418Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754140
;

-- 2025-09-24T13:01:05.419Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754140)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Zahlungsweise Info
-- Column: C_BPartner.PaymentRuleInfo
-- 2025-09-24T13:01:05.521Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590590,754141,0,220,TO_TIMESTAMP('2025-09-24 13:01:05.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Zahlungsweise Info',TO_TIMESTAMP('2025-09-24 13:01:05.423000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.523Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754141 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.525Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583844)
;

-- 2025-09-24T13:01:05.530Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754141
;

-- 2025-09-24T13:01:05.531Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754141)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Zahlungsweise Info
-- Column: C_BPartner.PaymentRulePOInfo
-- 2025-09-24T13:01:05.641Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590591,754142,0,220,TO_TIMESTAMP('2025-09-24 13:01:05.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Zahlungsweise Info',TO_TIMESTAMP('2025-09-24 13:01:05.535000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.643Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754142 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.644Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583845)
;

-- 2025-09-24T13:01:05.648Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754142
;

-- 2025-09-24T13:01:05.650Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754142)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Cc Email Rechnung an Mitglied
-- Column: C_BPartner.IsInvoiceEmailCcToMember
-- 2025-09-24T13:01:05.750Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591099,754143,0,220,TO_TIMESTAMP('2025-09-24 13:01:05.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.',1,'D','Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.','Y','N','N','N','N','N','N','N','Cc Email Rechnung an Mitglied',TO_TIMESTAMP('2025-09-24 13:01:05.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-24T13:01:05.752Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=754143 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-24T13:01:05.754Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584002)
;

-- 2025-09-24T13:01:05.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=754143
;

-- 2025-09-24T13:01:05.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(754143)
;

-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Cc Email Rechnung an Mitglied
-- Column: C_BPartner.IsInvoiceEmailCcToMember
-- 2025-09-24T13:04:24.191Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,754143,0,220,1000011,637271,'F',TO_TIMESTAMP('2025-09-24 13:04:24.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Wenn aktiv, werden Rechnungs-E-Mails auch als CC an das Mitglied gesendet.','Aktivieren Sie dieses Feld, um Rechnungs-E-Mails automatisch als CC an das Mitglied der Geschäftspartnergruppe zu senden.','Y','N','N','Y','N','N','N',0,'Cc Email Rechnung an Mitglied',75,0,0,TO_TIMESTAMP('2025-09-24 13:04:24.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

