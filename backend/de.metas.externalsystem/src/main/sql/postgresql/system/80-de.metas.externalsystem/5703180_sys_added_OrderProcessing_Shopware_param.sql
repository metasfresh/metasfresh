-- 2023-09-18T10:48:51.248538600Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582721,0,'OrderProcessing',TO_TIMESTAMP('2023-09-18 13:48:51.007','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Auftragsbearbeitung','Order Processing',TO_TIMESTAMP('2023-09-18 13:48:51.007','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-18T10:48:51.256436700Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582721 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: AD_UI_ElementTypeo
-- 2023-09-18T10:50:36.542150900Z
UPDATE AD_Reference SET Name='AD_UI_ElementTypeo',Updated=TO_TIMESTAMP('2023-09-18 13:50:36.54','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=540736
;

-- 2023-09-18T10:50:36.544271200Z
UPDATE AD_Reference_Trl trl SET Name='AD_UI_ElementTypeo' WHERE AD_Reference_ID=540736 AND AD_Language='de_DE'
;

-- Name: Order Processing
-- 2023-09-18T10:51:09.378275800Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541831,TO_TIMESTAMP('2023-09-18 13:51:09.246','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Order Processing',TO_TIMESTAMP('2023-09-18 13:51:09.246','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2023-09-18T10:51:09.379307300Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541831 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Order Processing
-- Value: O
-- ValueName: Keine
-- 2023-09-18T10:53:48.167361400Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541831,543551,TO_TIMESTAMP('2023-09-18 13:53:48.035','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Keine',TO_TIMESTAMP('2023-09-18 13:53:48.035','YYYY-MM-DD HH24:MI:SS.US'),100,'O','Keine')
;

-- 2023-09-18T10:53:48.168428600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543551 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Order Processing
-- Value: N
-- ValueName: Keine
-- 2023-09-18T10:55:46.084838500Z
UPDATE AD_Ref_List SET Value='N',Updated=TO_TIMESTAMP('2023-09-18 13:55:46.084','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543551
;

-- Reference: Order Processing
-- Value: O
-- ValueName: Auftrag
-- 2023-09-18T10:56:04.875918200Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541831,543557,TO_TIMESTAMP('2023-09-18 13:56:04.744','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Auftrag',TO_TIMESTAMP('2023-09-18 13:56:04.744','YYYY-MM-DD HH24:MI:SS.US'),100,'O','Auftrag')
;

-- 2023-09-18T10:56:04.876968900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543557 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Order Processing
-- Value: S
-- ValueName: Auftrag und Lieferung
-- 2023-09-18T10:56:24.030205900Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541831,543558,TO_TIMESTAMP('2023-09-18 13:56:23.906','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Auftrag und Lieferung',TO_TIMESTAMP('2023-09-18 13:56:23.906','YYYY-MM-DD HH24:MI:SS.US'),100,'S','Auftrag und Lieferung')
;

-- 2023-09-18T10:56:24.031250600Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543558 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Order Processing
-- Value: I
-- ValueName: Auftrag, Lieferung und Rechnung
-- 2023-09-18T10:56:40.554252500Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541831,543559,TO_TIMESTAMP('2023-09-18 13:56:40.433','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Auftrag, Lieferung und Rechnung',TO_TIMESTAMP('2023-09-18 13:56:40.433','YYYY-MM-DD HH24:MI:SS.US'),100,'I','Auftrag, Lieferung und Rechnung')
;

-- 2023-09-18T10:56:40.555309900Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543559 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Order Processing -> N_Keine
-- 2023-09-18T10:56:55.276626100Z
UPDATE AD_Ref_List_Trl SET Name='None',Updated=TO_TIMESTAMP('2023-09-18 13:56:55.276','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543551
;

-- Reference Item: Order Processing -> O_Auftrag
-- 2023-09-18T10:57:06.147212500Z
UPDATE AD_Ref_List_Trl SET Name='Sales Order',Updated=TO_TIMESTAMP('2023-09-18 13:57:06.147','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543557
;

-- Reference Item: Order Processing -> S_Auftrag und Lieferung
-- 2023-09-18T10:57:17.588968500Z
UPDATE AD_Ref_List_Trl SET Name='Sales Order and Shipment',Updated=TO_TIMESTAMP('2023-09-18 13:57:17.588','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543558
;

-- Reference Item: Order Processing -> I_Auftrag, Lieferung und Rechnung
-- 2023-09-18T10:57:33.015961500Z
UPDATE AD_Ref_List_Trl SET Name='Sales Order, Shipment and Invoice',Updated=TO_TIMESTAMP('2023-09-18 13:57:33.015','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543559
;

-- Reference Item: Order Processing -> I_Auftrag, Lieferung und Rechnung
-- 2023-09-18T10:57:40.435795200Z
UPDATE AD_Ref_List_Trl SET Name='Auftrag, Lieferung und Rechnung',Updated=TO_TIMESTAMP('2023-09-18 13:57:40.435','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543559
;

-- Reference Item: Order Processing -> I_Auftrag, Lieferung und Rechnung
-- 2023-09-18T10:57:41.384933700Z
UPDATE AD_Ref_List_Trl SET Name='Auftrag, Lieferung und Rechnung',Updated=TO_TIMESTAMP('2023-09-18 13:57:41.384','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543559
;

-- Reference Item: Order Processing -> I_Auftrag, Lieferung und Rechnung
-- 2023-09-18T10:57:42.083854Z
UPDATE AD_Ref_List_Trl SET Name='Auftrag, Lieferung und Rechnung',Updated=TO_TIMESTAMP('2023-09-18 13:57:42.083','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543559
;

-- 2023-09-18T10:57:42.086462200Z
UPDATE AD_Ref_List SET Name='Auftrag, Lieferung und Rechnung', Updated=TO_TIMESTAMP('2023-09-18 13:57:42.084','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Ref_List_ID=543559
;

-- Reference Item: Order Processing -> I_Auftrag, Lieferung und Rechnung
-- 2023-09-18T10:57:43.406122600Z
UPDATE AD_Ref_List_Trl SET Name='Auftrag, Lieferung und Rechnung',Updated=TO_TIMESTAMP('2023-09-18 13:57:43.406','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543559
;

-- Column: ExternalSystem_Config_Shopware6.OrderProcessing
-- 2023-09-18T10:58:31.117818400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587478,582721,0,17,541831,541585,'XX','OrderProcessing',TO_TIMESTAMP('2023-09-18 13:58:30.977','YYYY-MM-DD HH24:MI:SS.US'),100,'N','O','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Auftragsbearbeitung',0,0,TO_TIMESTAMP('2023-09-18 13:58:30.977','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-18T10:58:31.119369200Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587478 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-18T10:58:31.509624700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582721) 
;

-- 2023-09-18T10:58:36.431781400Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_Shopware6','ALTER TABLE public.ExternalSystem_Config_Shopware6 ADD COLUMN OrderProcessing CHAR(1) DEFAULT ''O'' NOT NULL')
;

-- Field: Externe System Konfiguration Shopware 6(541116,de.metas.externalsystem) -> External system config Shopware6(543838,de.metas.externalsystem) -> Auftragsbearbeitung
-- Column: ExternalSystem_Config_Shopware6.OrderProcessing
-- 2023-09-18T19:39:48.755964100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587478,720496,0,543838,0,TO_TIMESTAMP('2023-09-18 22:39:48.461','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Auftragsbearbeitung',0,40,0,1,1,TO_TIMESTAMP('2023-09-18 22:39:48.461','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-18T19:39:48.760120900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720496 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-18T19:39:48.788636800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582721) 
;

-- 2023-09-18T19:39:48.800578500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720496
;

-- 2023-09-18T19:39:48.802146600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720496)
;

-- UI Element: Externe System Konfiguration Shopware 6(541116,de.metas.externalsystem) -> External system config Shopware6(543838,de.metas.externalsystem) -> main -> 20 -> lookup.Auftragsbearbeitung
-- Column: ExternalSystem_Config_Shopware6.OrderProcessing
-- 2023-09-18T19:41:17.739755800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720496,0,543838,547717,620487,'F',TO_TIMESTAMP('2023-09-18 22:41:17.594','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Auftragsbearbeitung',5,0,0,TO_TIMESTAMP('2023-09-18 22:41:17.594','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Element: OrderProcessing
-- 2023-09-19T06:14:04.231548400Z
UPDATE AD_Element_Trl SET Name='Order Processing',Updated=TO_TIMESTAMP('2023-09-19 09:14:04.231','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582721 AND AD_Language='en_US'
;

-- 2023-09-19T06:14:04.259518400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582721,'en_US') 
;

-- Element: OrderProcessing
-- 2023-09-19T06:14:07.167518700Z
UPDATE AD_Element_Trl SET PrintName='Auftragsbearbeitung',Updated=TO_TIMESTAMP('2023-09-19 09:14:07.167','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582721 AND AD_Language='de_CH'
;

-- 2023-09-19T06:14:07.169619200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582721,'de_CH') 
;

-- Element: OrderProcessing
-- 2023-09-19T06:14:08.502144200Z
UPDATE AD_Element_Trl SET PrintName='Auftragsbearbeitung',Updated=TO_TIMESTAMP('2023-09-19 09:14:08.501','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582721 AND AD_Language='de_DE'
;

-- 2023-09-19T06:14:08.503182600Z
UPDATE AD_Element SET PrintName='Auftragsbearbeitung', Updated=TO_TIMESTAMP('2023-09-19 09:14:08.503','YYYY-MM-DD HH24:MI:SS.US') WHERE AD_Element_ID=582721
;

-- 2023-09-19T06:14:08.755738400Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582721,'de_DE') 
;

-- 2023-09-19T06:14:08.756852400Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582721,'de_DE') 
;

-- Element: OrderProcessing
-- 2023-09-19T06:14:09.847112100Z
UPDATE AD_Element_Trl SET PrintName='Auftragsbearbeitung',Updated=TO_TIMESTAMP('2023-09-19 09:14:09.847','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582721 AND AD_Language='fr_CH'
;

-- 2023-09-19T06:14:09.848678Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582721,'fr_CH') 
;

-- Element: OrderProcessing
-- 2023-09-19T06:14:32.731350300Z
UPDATE AD_Element_Trl SET PrintName='Auftragsbearbeitung',Updated=TO_TIMESTAMP('2023-09-19 09:14:32.731','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582721 AND AD_Language='it_IT'
;

-- 2023-09-19T06:14:32.732926100Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582721,'it_IT') 
;

-- Field: Externes System(541024,de.metas.externalsystem) -> Shopware6(543435,de.metas.externalsystem) -> Auftragsbearbeitung
-- Column: ExternalSystem_Config_Shopware6.OrderProcessing
-- 2023-09-19T16:07:27.372915700Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587478,720540,0,543435,0,TO_TIMESTAMP('2023-09-19 19:07:27.064','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Auftragsbearbeitung',0,50,0,1,1,TO_TIMESTAMP('2023-09-19 19:07:27.064','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-19T16:07:27.377646300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-19T16:07:27.403193Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582721) 
;

-- 2023-09-19T16:07:27.414171700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720540
;

-- 2023-09-19T16:07:27.415238900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720540)
;

-- UI Element: Externes System(541024,de.metas.externalsystem) -> Shopware6(543435,de.metas.externalsystem) -> main -> 20 -> product lookup.Auftragsbearbeitung
-- Column: ExternalSystem_Config_Shopware6.OrderProcessing
-- 2023-09-19T16:07:47.186737Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720540,0,543435,547241,620490,'F',TO_TIMESTAMP('2023-09-19 19:07:47.004','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Auftragsbearbeitung',5,0,0,TO_TIMESTAMP('2023-09-19 19:07:47.004','YYYY-MM-DD HH24:MI:SS.US'),100)
;

