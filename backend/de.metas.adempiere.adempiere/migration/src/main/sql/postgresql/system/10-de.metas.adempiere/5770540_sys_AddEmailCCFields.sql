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

