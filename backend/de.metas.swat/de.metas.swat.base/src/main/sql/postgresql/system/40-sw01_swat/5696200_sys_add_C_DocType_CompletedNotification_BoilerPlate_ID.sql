-- 2023-07-19T10:39:56.283Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582567,0,'CompletedNotification_BoilerPlate_ID',TO_TIMESTAMP('2023-07-19 13:39:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Fertigmeldung Textbaustein','Fertigmeldung Textbaustein',TO_TIMESTAMP('2023-07-19 13:39:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T10:39:56.291Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582567 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-07-19T10:41:00.270Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582567,'en_US') 
;

-- Column: C_DocType.CompletedNotification_BoilerPlate_ID
-- 2023-07-19T10:47:51.625Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587149,582567,0,30,540008,217,'CompletedNotification_BoilerPlate_ID',TO_TIMESTAMP('2023-07-19 13:47:51','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Completed Notification Text Snippet',0,0,TO_TIMESTAMP('2023-07-19 13:47:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T10:47:51.627Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587149 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T10:47:51.630Z
/* DDL */  select update_Column_Translation_From_AD_Element(582567) 
;

-- 2023-07-19T10:47:59.971Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN CompletedNotification_BoilerPlate_ID NUMERIC(10)')
;

-- 2023-07-19T10:48:00.732Z
ALTER TABLE C_DocType ADD CONSTRAINT CompletedNotificationBoilerPlate_CDocType FOREIGN KEY (CompletedNotification_BoilerPlate_ID) REFERENCES public.AD_BoilerPlate DEFERRABLE INITIALLY DEFERRED
;

-- 2023-07-19T11:40:00.736Z
UPDATE AD_Element_Trl SET Name='Benachrichtigungstext für Belegstatus "Fertiggestellt"', PrintName='Benachrichtigungstext für Belegstatus "Fertiggestellt"',Updated=TO_TIMESTAMP('2023-07-19 14:40:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582567 AND AD_Language='de_CH'
;

-- 2023-07-19T11:40:00.738Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582567,'de_CH') 
;

-- 2023-07-19T11:40:02.958Z
UPDATE AD_Element_Trl SET Name='Benachrichtigungstext für Belegstatus "Fertiggestellt"', PrintName='Benachrichtigungstext für Belegstatus "Fertiggestellt"',Updated=TO_TIMESTAMP('2023-07-19 14:40:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582567 AND AD_Language='de_DE'
;

-- 2023-07-19T11:40:02.960Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582567,'de_DE') 
;

-- 2023-07-19T11:40:02.961Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582567,'de_DE') 
;

-- 2023-07-19T11:40:04.889Z
UPDATE AD_Element_Trl SET Name='Benachrichtigungstext für Belegstatus "Fertiggestellt"', PrintName='Benachrichtigungstext für Belegstatus "Fertiggestellt"',Updated=TO_TIMESTAMP('2023-07-19 14:40:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582567 AND AD_Language='fr_CH'
;

-- 2023-07-19T11:40:04.891Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582567,'fr_CH') 
;

-- 2023-07-19T11:40:17.978Z
UPDATE AD_Element_Trl SET Name='Benachrichtigungstext für Belegstatus "Fertiggestellt"', PrintName='Benachrichtigungstext für Belegstatus "Fertiggestellt"',Updated=TO_TIMESTAMP('2023-07-19 14:40:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582567 AND AD_Language='nl_NL'
;

-- 2023-07-19T11:40:17.980Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582567,'nl_NL') 
;

-- 2023-07-19T12:02:17.414Z
UPDATE AD_Element_Trl SET Name='Notification text for doc status "Completed"', PrintName='Notification text for doc status "Completed"',Updated=TO_TIMESTAMP('2023-07-19 15:02:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582567 AND AD_Language='en_US'
;

-- 2023-07-19T12:02:17.416Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582567,'en_US') 
;

-- Field: Belegart -> Belegart -> Benachrichtigungstext für Belegstatus "Fertiggestellt"
-- Column: C_DocType.CompletedNotification_BoilerPlate_ID
-- 2023-07-19T19:58:17.642Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587149,716719,0,167,0,TO_TIMESTAMP('2023-07-19 22:58:17','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Benachrichtigungstext für Belegstatus "Fertiggestellt"',0,340,0,1,1,TO_TIMESTAMP('2023-07-19 22:58:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T19:58:17.644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=716719 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T19:58:17.672Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582567) 
;

-- 2023-07-19T19:58:17.684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716719
;

-- 2023-07-19T19:58:17.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716719)
;

-- UI Element: Belegart -> Belegart.Benachrichtigungstext für Belegstatus "Fertiggestellt"
-- Column: C_DocType.CompletedNotification_BoilerPlate_ID
-- 2023-07-19T19:59:27.826Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716719,0,167,540408,618295,'F',TO_TIMESTAMP('2023-07-19 22:59:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Benachrichtigungstext für Belegstatus "Fertiggestellt"',30,0,0,TO_TIMESTAMP('2023-07-19 22:59:27','YYYY-MM-DD HH24:MI:SS'),100)
;

