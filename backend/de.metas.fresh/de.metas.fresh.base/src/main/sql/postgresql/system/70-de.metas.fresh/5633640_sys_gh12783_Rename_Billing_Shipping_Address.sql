-- 2022-04-06T11:14:52Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580755,0,'Lieferadresse',TO_TIMESTAMP('2022-04-06 12:14:50','YYYY-MM-DD HH24:MI:SS'),100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','D','Identifiziert die Adresse des Geschäftspartners','Y','Lieferadresse','Lieferadresse',TO_TIMESTAMP('2022-04-06 12:14:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-06T11:14:52.159Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580755 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Geschäftspartner -> Adresse -> Lieferadresse
-- Column: C_BPartner_Location.IsShipTo
-- 2022-04-06T11:16:24.771Z
UPDATE AD_Field SET AD_Name_ID=580755, Description='Identifiziert die (Liefer-) Adresse des Geschäftspartners', Help='Identifiziert die Adresse des Geschäftspartners', Name='Lieferadresse',Updated=TO_TIMESTAMP('2022-04-06 12:16:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2196
;

-- 2022-04-06T11:16:24.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580755) 
;

-- 2022-04-06T11:16:24.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2196
;

-- 2022-04-06T11:16:25.042Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(2196)
;

-- 2022-04-06T11:56:47.410Z
UPDATE AD_Element_Trl SET Description='Business Partner Location for shipping to', Help='', Name='Drop Shipment Location', PrintName='Drop Shipment Location',Updated=TO_TIMESTAMP('2022-04-06 12:56:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580755 AND AD_Language='en_US'
;

-- 2022-04-06T11:56:47.534Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580755,'en_US') 
;

-- 2022-04-06T14:58:03.115Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580756,0,'Lieferstandard',TO_TIMESTAMP('2022-04-06 15:58:02','YYYY-MM-DD HH24:MI:SS'),100,'Liefer-Adresse für den Geschäftspartner','D','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','Lieferstandard','Liefer-Adresse',TO_TIMESTAMP('2022-04-06 15:58:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-06T14:58:03.197Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580756 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-06T15:01:03.066Z
UPDATE AD_Element_Trl SET Description='Business Partner Shipment Address', Help='If the Ship Address is selected, the location is used to ship goods to a customer or receive goods from a vendor.', Name='Standard Ship Address', PrintName='Standard Ship Address',Updated=TO_TIMESTAMP('2022-04-06 16:01:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580756 AND AD_Language='en_US'
;

-- 2022-04-06T15:01:03.144Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580756,'en_US') 
;

-- 2022-04-06T15:01:27.865Z
UPDATE AD_Element_Trl SET Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.', PrintName='Lieferstandard',Updated=TO_TIMESTAMP('2022-04-06 16:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580756 AND AD_Language='de_DE'
;

-- 2022-04-06T15:01:27.938Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580756,'de_DE') 
;

-- 2022-04-06T15:01:28.100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(580756,'de_DE') 
;

-- 2022-04-06T15:01:28.173Z
UPDATE AD_Column SET ColumnName='Lieferstandard', Name='Lieferstandard', Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.' WHERE AD_Element_ID=580756
;

-- 2022-04-06T15:01:28.245Z
UPDATE AD_Process_Para SET ColumnName='Lieferstandard', Name='Lieferstandard', Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.', AD_Element_ID=580756 WHERE UPPER(ColumnName)='LIEFERSTANDARD' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-04-06T15:01:28.331Z
UPDATE AD_Process_Para SET ColumnName='Lieferstandard', Name='Lieferstandard', Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.' WHERE AD_Element_ID=580756 AND IsCentrallyMaintained='Y'
;

-- 2022-04-06T15:01:28.401Z
UPDATE AD_Field SET Name='Lieferstandard', Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=580756) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 580756)
;

-- 2022-04-06T15:01:28.592Z
UPDATE AD_PrintFormatItem pi SET PrintName='Lieferstandard', Name='Lieferstandard' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=580756)
;

-- 2022-04-06T15:01:28.667Z
UPDATE AD_Tab SET Name='Lieferstandard', Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.', CommitWarning = NULL WHERE AD_Element_ID = 580756
;

-- 2022-04-06T15:01:28.743Z
UPDATE AD_WINDOW SET Name='Lieferstandard', Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.' WHERE AD_Element_ID = 580756
;

-- 2022-04-06T15:01:28.818Z
UPDATE AD_Menu SET   Name = 'Lieferstandard', Description = 'Lieferstandard für den Geschäftspartner', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 580756
;

-- Field: Geschäftspartner -> Adresse -> Lieferstandard
-- Column: C_BPartner_Location.IsShipToDefault
-- 2022-04-06T15:02:08.498Z
UPDATE AD_Field SET AD_Name_ID=580756, Description='Lieferstandard für den Geschäftspartner', Help='Wenn "Lieferstandard" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.', Name='Lieferstandard',Updated=TO_TIMESTAMP('2022-04-06 16:02:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548907
;

-- 2022-04-06T15:02:08.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580756) 
;

-- 2022-04-06T15:02:08.676Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=548907
;

-- 2022-04-06T15:02:08.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(548907)
;

-- 2022-04-06T15:05:19.314Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580757,0,'Rechnungsadresse',TO_TIMESTAMP('2022-04-06 16:05:18','YYYY-MM-DD HH24:MI:SS'),100,'Standort des Geschäftspartners für die Rechnungsstellung','D','Y','Rechnungsadresse','Rechnungsadresse',TO_TIMESTAMP('2022-04-06 16:05:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-06T15:05:19.390Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580757 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2022-04-06T15:05:59.497Z
UPDATE AD_Element_Trl SET Description='Business Partner Location for invoicing', Name='Invoice Location', PrintName='Invoice Location',Updated=TO_TIMESTAMP('2022-04-06 16:05:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580757 AND AD_Language='en_US'
;

-- 2022-04-06T15:05:59.568Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580757,'en_US') 
;

-- Field: Geschäftspartner -> Adresse -> Rechnungsadresse
-- Column: C_BPartner_Location.IsBillTo
-- 2022-04-06T15:06:24.619Z
UPDATE AD_Field SET AD_Name_ID=580757, Description='Standort des Geschäftspartners für die Rechnungsstellung', Help=NULL, Name='Rechnungsadresse',Updated=TO_TIMESTAMP('2022-04-06 16:06:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=2193
;

-- 2022-04-06T15:06:24.692Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580757) 
;

-- 2022-04-06T15:06:24.772Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=2193
;

-- 2022-04-06T15:06:24.842Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(2193)
;

-- 2022-04-06T15:10:31.553Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580758,0,'Rechnungsstandard',TO_TIMESTAMP('2022-04-06 16:10:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Rechnungsstandard','Rechnungsstandard',TO_TIMESTAMP('2022-04-06 16:10:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-06T15:10:31.637Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580758 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Geschäftspartner -> Adresse -> Rechnungsstandard
-- Column: C_BPartner_Location.IsBillToDefault
-- 2022-04-06T15:10:59.459Z
UPDATE AD_Field SET AD_Name_ID=580758, Description=NULL, Help=NULL, Name='Rechnungsstandard',Updated=TO_TIMESTAMP('2022-04-06 16:10:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=548910
;

-- 2022-04-06T15:10:59.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580758) 
;

-- 2022-04-06T15:10:59.606Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=548910
;

-- 2022-04-06T15:10:59.681Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(548910)
;

-- 2022-04-06T15:12:08.548Z
UPDATE AD_Element_Trl SET Name='Standard Invoicing address', PrintName='Standard Invoicing address',Updated=TO_TIMESTAMP('2022-04-06 16:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580758 AND AD_Language='en_US'
;

-- 2022-04-06T15:12:08.620Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580758,'en_US') 
;

