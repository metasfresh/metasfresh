-- Name: Create Shipment Policy
-- 2023-10-18T08:59:30.890Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541839,TO_TIMESTAMP('2023-10-18 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'Create Shipment Policy - Don''t Create, Create draft, Create and complete','D','Y','N','Create Shipment Policy',TO_TIMESTAMP('2023-10-18 10:59:30','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-10-18T08:59:30.894Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541839 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Create Shipment Policy
-- Value: NO
-- ValueName: DO_NOT_CREATE
-- 2023-10-18T09:01:46.897Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541839,543588,TO_TIMESTAMP('2023-10-18 11:01:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Don''t create',TO_TIMESTAMP('2023-10-18 11:01:46','YYYY-MM-DD HH24:MI:SS'),100,'NO','DO_NOT_CREATE')
;

-- 2023-10-18T09:01:46.899Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543588 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Create Shipment Policy
-- Value: DR
-- ValueName: CREATE_DRAFT
-- 2023-10-18T09:06:11.986Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541839,543589,TO_TIMESTAMP('2023-10-18 11:06:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Create draft',TO_TIMESTAMP('2023-10-18 11:06:11','YYYY-MM-DD HH24:MI:SS'),100,'DR','CREATE_DRAFT')
;

-- 2023-10-18T09:06:11.987Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543589 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Create Shipment Policy
-- Value: CO
-- ValueName: CREATE_AND_COMPLETE
-- 2023-10-18T09:07:07.532Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541839,543590,TO_TIMESTAMP('2023-10-18 11:07:07','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Create and complete',TO_TIMESTAMP('2023-10-18 11:07:07','YYYY-MM-DD HH24:MI:SS'),100,'CO','CREATE_AND_COMPLETE')
;

-- 2023-10-18T09:07:07.533Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543590 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-10-18T09:12:24.480Z
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-18 11:12:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541839
;

-- 2023-10-18T09:15:05.707Z
UPDATE AD_Reference_Trl SET Description='Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen', IsTranslated='Y', Name='Erstellen von Lieferung Richtlinie',Updated=TO_TIMESTAMP('2023-10-18 11:15:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541839
;

-- 2023-10-18T09:15:24.195Z
UPDATE AD_Reference_Trl SET Description='Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen', IsTranslated='Y', Name='Erstellen von Lieferung Richtlinie',Updated=TO_TIMESTAMP('2023-10-18 11:15:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541839
;

-- Reference Item: Create Shipment Policy -> CO_CREATE_AND_COMPLETE
-- 2023-10-18T09:15:39.510Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-18 11:15:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543590
;

-- Reference Item: Create Shipment Policy -> CO_CREATE_AND_COMPLETE
-- 2023-10-18T09:15:54.311Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erstellen und fertigstellen',Updated=TO_TIMESTAMP('2023-10-18 11:15:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543590
;

-- Reference Item: Create Shipment Policy -> CO_CREATE_AND_COMPLETE
-- 2023-10-18T09:16:01.539Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erstellen und fertigstellen',Updated=TO_TIMESTAMP('2023-10-18 11:16:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543590
;

-- Reference Item: Create Shipment Policy -> DR_CREATE_DRAFT
-- 2023-10-18T09:16:13.751Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-18 11:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543589
;

-- Reference Item: Create Shipment Policy -> DR_CREATE_DRAFT
-- 2023-10-18T09:16:31.664Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Entwurf erstellen',Updated=TO_TIMESTAMP('2023-10-18 11:16:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543589
;

-- Reference Item: Create Shipment Policy -> DR_CREATE_DRAFT
-- 2023-10-18T09:16:38.747Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Entwurf erstellen',Updated=TO_TIMESTAMP('2023-10-18 11:16:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543589
;

-- Reference Item: Create Shipment Policy -> NO_DO_NOT_CREATE
-- 2023-10-18T09:17:18.955Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-18 11:17:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543588
;

-- Reference Item: Create Shipment Policy -> NO_DO_NOT_CREATE
-- 2023-10-18T09:17:40.231Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nicht erstellen',Updated=TO_TIMESTAMP('2023-10-18 11:17:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543588
;

-- Reference Item: Create Shipment Policy -> NO_DO_NOT_CREATE
-- 2023-10-18T09:17:48.658Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Nicht erstellen',Updated=TO_TIMESTAMP('2023-10-18 11:17:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543588
;

-- 2023-10-18T09:21:19.328Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582769,0,'CreateShipmentPolicy',TO_TIMESTAMP('2023-10-18 11:21:19','YYYY-MM-DD HH24:MI:SS'),100,'Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen','D','Y','Erstellen von Lieferung Richtlinie','Erstellen von Lieferung Richtlinie',TO_TIMESTAMP('2023-10-18 11:21:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-18T09:21:19.340Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582769 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CreateShipmentPolicy
-- 2023-10-18T09:21:34.278Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-18 11:21:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582769 AND AD_Language='de_CH'
;

-- 2023-10-18T09:21:34.303Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582769,'de_CH') 
;

-- Element: CreateShipmentPolicy
-- 2023-10-18T09:21:35.999Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-10-18 11:21:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582769 AND AD_Language='de_DE'
;

-- 2023-10-18T09:21:36.002Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582769,'de_DE') 
;

-- 2023-10-18T09:21:36.007Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582769,'de_DE') 
;

-- Element: CreateShipmentPolicy
-- 2023-10-18T09:22:57.851Z
UPDATE AD_Element_Trl SET Description='Create Shipment Policy - Don''t Create, Create Draft, Create and Complete', IsTranslated='Y', Name='Create Shipment Policy', PrintName='Create Shipment Policy',Updated=TO_TIMESTAMP('2023-10-18 11:22:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582769 AND AD_Language='en_US'
;

-- 2023-10-18T09:22:57.854Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582769,'en_US') 
;

-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- 2023-10-18T09:31:27.936Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587574,582769,0,17,541839,542373,'CreateShipmentPolicy',TO_TIMESTAMP('2023-10-18 11:31:27','YYYY-MM-DD HH24:MI:SS'),100,'N','NO','Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Erstellen von Lieferung Richtlinie',0,0,TO_TIMESTAMP('2023-10-18 11:31:27','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-10-18T09:31:27.943Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-10-18T09:31:27.952Z
/* DDL */  select update_Column_Translation_From_AD_Element(582769) 
;

-- 2023-10-18T09:45:33.828Z
/* DDL */ SELECT public.db_alter_table('MobileUI_UserProfile_Picking','ALTER TABLE public.MobileUI_UserProfile_Picking ADD COLUMN CreateShipmentPolicy VARCHAR(2) DEFAULT ''NO'' NOT NULL')
;

-- Field: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil -> Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- Field: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- 2023-10-18T09:39:46.880Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587574,721596,0,547258,0,TO_TIMESTAMP('2023-10-18 11:39:46','YYYY-MM-DD HH24:MI:SS'),100,'Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen',0,'D',0,'Y','Y','Y','N','N','N','N','N','Erstellen von Lieferung Richtlinie',0,10,0,1,1,TO_TIMESTAMP('2023-10-18 11:39:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-10-18T09:39:46.884Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721596 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-10-18T09:39:46.892Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582769) 
;

-- 2023-10-18T09:39:46.903Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721596
;

-- 2023-10-18T09:39:46.921Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721596)
;

-- UI Column: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10
-- UI Element Group: settings
-- 2023-10-18T09:41:42.260Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547136,551255,TO_TIMESTAMP('2023-10-18 11:41:42','YYYY-MM-DD HH24:MI:SS'),100,'Y','settings',20,'primary',TO_TIMESTAMP('2023-10-18 11:41:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Mobile UI Kommissionierprofil -> Mobile UI Kommissionierprofil.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- UI Element: Mobile UI Kommissionierprofil(541743,D) -> Mobile UI Kommissionierprofil(547258,D) -> main -> 10 -> settings.Erstellen von Lieferung Richtlinie
-- Column: MobileUI_UserProfile_Picking.CreateShipmentPolicy
-- 2023-10-18T09:42:31.280Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721596,0,547258,551255,621123,'F',TO_TIMESTAMP('2023-10-18 11:42:31','YYYY-MM-DD HH24:MI:SS'),100,'Erstellen von Lieferung Richtlinie - Nicht erstellen, Entwurf erstellen, Erstellen und fertigstellen','Y','N','N','Y','N','N','N',0,'Erstellen von Lieferung Richtlinie',10,0,0,TO_TIMESTAMP('2023-10-18 11:42:31','YYYY-MM-DD HH24:MI:SS'),100)
;

