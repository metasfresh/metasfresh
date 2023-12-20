-- 2023-07-18T13:56:46.128Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582563,0,TO_TIMESTAMP('2023-07-18 15:56:45','YYYY-MM-DD HH24:MI:SS'),100,'If a printing job is made available for this printer, then the selected Externalsystem(-config) is notified.','D','Y','External System','External System',TO_TIMESTAMP('2023-07-18 15:56:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-18T13:56:46.137Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582563 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-07-18T13:57:12.358Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-18 15:57:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582563 AND AD_Language='en_US'
;

-- 2023-07-18T13:57:12.386Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582563,'en_US') 
;

-- Element: null
-- 2023-07-18T14:15:03.093Z
UPDATE AD_Element_Trl SET Description='Wenn ein Druckauftrag für diesen Drucker bereitgestellt wird, wird das ausgewählte Externe System (Konfiguration) benachrichtigt.', IsTranslated='Y', Name='Externes System', PrintName='Externes System',Updated=TO_TIMESTAMP('2023-07-18 16:15:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582563 AND AD_Language='de_CH'
;

-- 2023-07-18T14:15:03.096Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582563,'de_CH') 
;

-- Element: null
-- 2023-07-18T14:15:28.471Z
UPDATE AD_Element_Trl SET Description='Wenn ein Druckauftrag für diesen Drucker bereitgestellt wird, wird das ausgewählte Externe System (Konfiguration) benachrichtigt.', IsTranslated='Y', Name='Externes System', PrintName='Externes System',Updated=TO_TIMESTAMP('2023-07-18 16:15:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582563 AND AD_Language='de_DE'
;

-- 2023-07-18T14:15:28.473Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582563,'de_DE') 
;

-- 2023-07-18T14:15:28.476Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582563,'de_DE') 
;

-- Field: Hardware-Drucker -> Drucker -> Externes System
-- Column: AD_PrinterHW.ExternalSystem_Config_ID
-- Field: Hardware-Drucker(540167,de.metas.printing) -> Drucker(540463,de.metas.printing) -> Externes System
-- Column: AD_PrinterHW.ExternalSystem_Config_ID
-- 2023-07-19T09:24:25.278Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587125,716712,582563,0,540463,0,TO_TIMESTAMP('2023-07-19 11:24:24','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Druckauftrag für diesen Drucker bereitgestellt wird, wird das ausgewählte Externe System (Konfiguration) benachrichtigt.',0,'@OutputType@=Queue','de.metas.printing',0,'Y','N','N','N','N','N','N','N','Externes System',0,70,0,1,1,TO_TIMESTAMP('2023-07-19 11:24:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T09:24:25.282Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716712 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T09:24:25.326Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582563) 
;

-- 2023-07-19T09:24:25.343Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716712
;

-- 2023-07-19T09:24:25.347Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716712)
;

-- UI Element: Hardware-Drucker -> Drucker.Externes System
-- Column: AD_PrinterHW.ExternalSystem_Config_ID
-- UI Element: Hardware-Drucker(540167,de.metas.printing) -> Drucker(540463,de.metas.printing) -> main -> 10 -> default.Externes System
-- Column: AD_PrinterHW.ExternalSystem_Config_ID
-- 2023-07-19T09:26:40.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716712,0,540463,541040,618293,'F',TO_TIMESTAMP('2023-07-19 11:26:40','YYYY-MM-DD HH24:MI:SS'),100,'Wenn ein Druckauftrag für diesen Drucker bereitgestellt wird, wird das ausgewählte Externe System (Konfiguration) benachrichtigt.','Y','N','N','Y','N','N','N',0,'Externes System',40,0,0,TO_TIMESTAMP('2023-07-19 11:26:40','YYYY-MM-DD HH24:MI:SS'),100)
;

