-- 2020-11-26T14:52:53.211Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578581,0,'LeadTime',TO_TIMESTAMP('2020-11-26 11:52:50','YYYY-MM-DD HH24:MI:SS'),100,'This column indicates the replenish time in days for a product','D','Y','LeadTime','LeadTime',TO_TIMESTAMP('2020-11-26 11:52:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T14:52:53.438Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578581 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-11-26T14:56:45.225Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Wiederbeschaffung', PrintName='Wiederbeschaffung',Updated=TO_TIMESTAMP('2020-11-26 11:56:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578581 AND AD_Language='de_DE'
;

-- 2020-11-26T14:56:45.440Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578581,'de_DE') 
;

-- 2020-11-26T14:56:46.521Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578581,'de_DE') 
;

-- 2020-11-26T14:56:46.730Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='This column indicates the replenish time in days for a product', Help=NULL WHERE AD_Element_ID=578581
;

-- 2020-11-26T14:56:46.936Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='This column indicates the replenish time in days for a product', Help=NULL, AD_Element_ID=578581 WHERE UPPER(ColumnName)='LEADTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-26T14:56:47.151Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='This column indicates the replenish time in days for a product', Help=NULL WHERE AD_Element_ID=578581 AND IsCentrallyMaintained='Y'
;

-- 2020-11-26T14:56:47.363Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Wiederbeschaffung', Description='This column indicates the replenish time in days for a product', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578581) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578581)
;

-- 2020-11-26T14:56:47.590Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Wiederbeschaffung', Name='Wiederbeschaffung' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578581)
;

-- 2020-11-26T14:56:47.817Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Wiederbeschaffung', Description='This column indicates the replenish time in days for a product', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:56:48.030Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Wiederbeschaffung', Description='This column indicates the replenish time in days for a product', Help=NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:56:48.241Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Wiederbeschaffung', Description = 'This column indicates the replenish time in days for a product', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:59:34.585Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.',Updated=TO_TIMESTAMP('2020-11-26 11:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578581 AND AD_Language='de_DE'
;

-- 2020-11-26T14:59:34.790Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578581,'de_DE') 
;

-- 2020-11-26T14:59:35.204Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578581,'de_DE') 
;

-- 2020-11-26T14:59:35.409Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help=NULL WHERE AD_Element_ID=578581
;

-- 2020-11-26T14:59:35.613Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help=NULL, AD_Element_ID=578581 WHERE UPPER(ColumnName)='LEADTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-26T14:59:35.818Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help=NULL WHERE AD_Element_ID=578581 AND IsCentrallyMaintained='Y'
;

-- 2020-11-26T14:59:36.021Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578581) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578581)
;

-- 2020-11-26T14:59:36.247Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:59:36.465Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help=NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:59:36.672Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Wiederbeschaffung', Description = 'In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:59:48.200Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.',Updated=TO_TIMESTAMP('2020-11-26 11:59:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578581 AND AD_Language='de_DE'
;

-- 2020-11-26T14:59:48.416Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578581,'de_DE') 
;

-- 2020-11-26T14:59:48.851Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578581,'de_DE') 
;

-- 2020-11-26T14:59:49.064Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.' WHERE AD_Element_ID=578581
;

-- 2020-11-26T14:59:49.272Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', AD_Element_ID=578581 WHERE UPPER(ColumnName)='LEADTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-26T14:59:49.478Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.' WHERE AD_Element_ID=578581 AND IsCentrallyMaintained='Y'
;

-- 2020-11-26T14:59:49.681Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578581) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578581)
;

-- 2020-11-26T14:59:49.889Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', CommitWarning = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:59:50.094Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', Help='In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.' WHERE AD_Element_ID = 578581
;

-- 2020-11-26T14:59:50.302Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Wiederbeschaffung', Description = 'In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T15:00:13.051Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='This column indicates the replenish time in days for a product',Updated=TO_TIMESTAMP('2020-11-26 12:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578581 AND AD_Language='en_GB'
;

-- 2020-11-26T15:00:13.254Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578581,'en_GB') 
;

-- 2020-11-26T15:00:36.691Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Help='This column indicates the replenish time in days for a product',Updated=TO_TIMESTAMP('2020-11-26 12:00:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578581 AND AD_Language='en_US'
;

-- 2020-11-26T15:00:36.904Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578581,'en_US') 
;

-- 2020-11-26T15:00:59.118Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Reference_ID,DefaultValue,FieldLength,Version,IsKey,IsParent,IsTranslated,IsIdentifier,SeqNo,AD_Client_ID,IsActive,Created,CreatedBy,IsUpdateable,DDL_NoForeignKey,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAutocomplete,IsAllowLogging,IsEncrypted,Updated,UpdatedBy,IsAdvancedText,IsLazyLoading,AD_Table_ID,IsCalculated,Help,AD_Column_ID,IsDimension,IsMandatory,IsStaleable,IsUseDocSequence,IsRangeFilter,IsShowFilterIncrementButtons,IsDLMPartitionBoundary,IsGenericZoomKeyColumn,SelectionColumnSeqNo,EntityType,IsForceIncludeInGeneratedModel,IsGenericZoomOrigin,ColumnName,IsAutoApplyValidationRule,Name,AD_Org_ID,Description,IsFacetFilter,MaxFacetsToFetch,FacetFilterSeqNo,AD_Element_ID) VALUES (11,'0',10,0,'N','N','N','N',0,0,'Y',TO_TIMESTAMP('2020-11-26 12:00:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','Y','N',TO_TIMESTAMP('2020-11-26 12:00:56','YYYY-MM-DD HH24:MI:SS'),100,'N','N',632,'N','In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.',572241,'N','Y','N','N','N','N','N','N',0,'D','N','N','LeadTime','N','Wiederbeschaffung',0,'In dieser Spalte wird die Wiederbestellzeit in Tagen für ein Produkt angezeigt.','N',0,0,578581)
;

-- 2020-11-26T15:00:59.333Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=572241 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2020-11-26T15:00:59.546Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578581) 
;

-- 2020-11-26T15:03:09.628Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BPartner_Product','ALTER TABLE public.C_BPartner_Product ADD COLUMN LeadTime NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- 2020-11-26T16:54:30.927Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.',Updated=TO_TIMESTAMP('2020-11-26 13:54:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578581 AND AD_Language='de_DE'
;

-- 2020-11-26T16:54:31.163Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578581,'de_DE') 
;

-- 2020-11-26T16:54:31.668Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(578581,'de_DE') 
;

-- 2020-11-26T16:54:31.916Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.' WHERE AD_Element_ID=578581
;

-- 2020-11-26T16:54:32.147Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', AD_Element_ID=578581 WHERE UPPER(ColumnName)='LEADTIME' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2020-11-26T16:54:32.394Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='LeadTime', Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.' WHERE AD_Element_ID=578581 AND IsCentrallyMaintained='Y'
;

-- 2020-11-26T16:54:32.657Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578581) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578581)
;

-- 2020-11-26T16:54:32.960Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', CommitWarning = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T16:54:33.203Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Wiederbeschaffung', Description='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', Help='In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.' WHERE AD_Element_ID = 578581
;

-- 2020-11-26T16:54:33.442Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Wiederbeschaffung', Description = 'In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578581
;

-- 2020-11-26T17:05:39.557Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563892,626770,0,180,TO_TIMESTAMP('2020-11-26 14:05:33','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Ursprungsland',TO_TIMESTAMP('2020-11-26 14:05:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T17:05:39.770Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626770 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T17:05:39.989Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576016) 
;

-- 2020-11-26T17:05:40.211Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626770
;

-- 2020-11-26T17:05:40.424Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626770)
;

-- 2020-11-26T17:05:43.164Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,563992,626771,0,180,TO_TIMESTAMP('2020-11-26 14:05:40','YYYY-MM-DD HH24:MI:SS'),100,25,'D','Y','N','N','N','N','N','N','N','Nettogewicht',TO_TIMESTAMP('2020-11-26 14:05:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T17:05:43.371Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626771 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T17:05:43.578Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540043) 
;

-- 2020-11-26T17:05:43.792Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626771
;

-- 2020-11-26T17:05:43.996Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626771)
;

-- 2020-11-26T17:05:46.732Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,567933,626772,0,180,TO_TIMESTAMP('2020-11-26 14:05:44','YYYY-MM-DD HH24:MI:SS'),100,1,'D','Y','N','N','N','N','N','N','N','Pharma Product',TO_TIMESTAMP('2020-11-26 14:05:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T17:05:46.949Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626772 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T17:05:47.156Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576727) 
;

-- 2020-11-26T17:05:47.377Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626772
;

-- 2020-11-26T17:05:47.584Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626772)
;


-- 2020-11-26T17:05:50.727Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577615) 
;

-- 2020-11-26T17:05:50.943Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626773
;

-- 2020-11-26T17:05:51.148Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626773)
;

-- 2020-11-26T17:05:54.294Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577616) 
;

-- 2020-11-26T17:05:54.507Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626774
;

-- 2020-11-26T17:05:54.720Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626774)
;


-- 2020-11-26T17:05:57.909Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577617) 
;

-- 2020-11-26T17:05:58.133Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626775
;

-- 2020-11-26T17:05:58.346Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626775)
;

-- 2020-11-26T17:06:01.493Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577619) 
;

-- 2020-11-26T17:06:01.705Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626776
;

-- 2020-11-26T17:06:01.917Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626776)
;


-- 2020-11-26T17:06:05.596Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577641) 
;

-- 2020-11-26T17:06:05.817Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626777
;

-- 2020-11-26T17:06:06.080Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626777)
;

-- 2020-11-26T17:06:09.321Z
-- URL zum Konzept


-- 2020-11-26T17:06:09.754Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53243) 
;

-- 2020-11-26T17:06:09.982Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626778
;

-- 2020-11-26T17:06:10.190Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626778)
;

-- 2020-11-26T17:06:12.960Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570772,626779,0,180,TO_TIMESTAMP('2020-11-26 14:06:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Warennummer',TO_TIMESTAMP('2020-11-26 14:06:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T17:06:13.166Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626779 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T17:06:13.373Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577721) 
;

-- 2020-11-26T17:06:13.589Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626779
;

-- 2020-11-26T17:06:13.792Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626779)
;


-- 2020-11-26T17:06:17.250Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577773) 
;

-- 2020-11-26T17:06:17.583Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626780
;

-- 2020-11-26T17:06:17.789Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626780)
;


-- 2020-11-26T17:06:21.404Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577774) 
;

-- 2020-11-26T17:06:21.613Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626781
;

-- 2020-11-26T17:06:21.819Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626781)
;


-- 2020-11-26T17:06:24.973Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578011) 
;

-- 2020-11-26T17:06:25.185Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626782
;

-- 2020-11-26T17:06:25.390Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626782)
;


-- 2020-11-26T17:06:28.997Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578022) 
;

-- 2020-11-26T17:06:29.209Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626783
;

-- 2020-11-26T17:06:29.412Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626783)
;


-- 2020-11-26T17:06:32.644Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578023) 
;

-- 2020-11-26T17:06:32.858Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626784
;

-- 2020-11-26T17:06:33.066Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626784)
;

-- 2020-11-26T17:06:36.238Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578049) 
;

-- 2020-11-26T17:06:36.452Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626785
;

-- 2020-11-26T17:06:36.655Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626785)
;

-- 2020-11-26T17:10:40.537Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572241,626786,0,562,TO_TIMESTAMP('2020-11-26 14:10:37','YYYY-MM-DD HH24:MI:SS'),100,'In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.',10,'D','In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.','Y','N','N','N','N','N','N','N','Wiederbeschaffung',TO_TIMESTAMP('2020-11-26 14:10:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T17:10:40.743Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=626786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-11-26T17:10:40.954Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578581) 
;

-- 2020-11-26T17:10:41.164Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=626786
;

-- 2020-11-26T17:10:41.368Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(626786)
;

-- 2020-11-26T17:12:31.401Z
-- URL zum Konzept
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2020-11-26 14:12:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=626786
;

-- 2020-11-26T17:22:26.300Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,626786,0,562,1000021,575574,'F',TO_TIMESTAMP('2020-11-26 14:22:24','YYYY-MM-DD HH24:MI:SS'),100,'In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.','In dieser Spalte wird die Wiederbeschaffungszeit für ein Produkt in Tagen angezeigt.','Y','N','N','Y','N','N','N',0,'Wiederbeschaffung',185,0,0,TO_TIMESTAMP('2020-11-26 14:22:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-11-26T17:24:38.444Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=175,Updated=TO_TIMESTAMP('2020-11-26 14:24:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=575574
;