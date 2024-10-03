-- 2024-10-02T12:58:45.646Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583295,0,'IsShipmentNotificationRequired',TO_TIMESTAMP('2024-10-02 14:58:45.493','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.inoutcandidate','Y','Lieferavis erforderlich','Lieferavis erforderlich',TO_TIMESTAMP('2024-10-02 14:58:45.493','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-02T12:58:45.650Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583295 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShipmentNotificationRequired
-- 2024-10-02T13:12:55.752Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 15:12:55.752','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583295 AND AD_Language='de_CH'
;

-- 2024-10-02T13:12:55.754Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583295,'de_CH')
;

-- Element: IsShipmentNotificationRequired
-- 2024-10-02T13:12:57.247Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-10-02 15:12:57.247','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583295 AND AD_Language='de_DE'
;

-- 2024-10-02T13:12:57.249Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583295,'de_DE')
;

-- 2024-10-02T13:12:57.251Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583295,'de_DE')
;

-- Element: IsShipmentNotificationRequired
-- 2024-10-02T13:13:48.254Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Shipment Notification required', PrintName='Shipment Notification required',Updated=TO_TIMESTAMP('2024-10-02 15:13:48.254','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583295 AND AD_Language='en_US'
;

-- 2024-10-02T13:13:48.257Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583295,'en_US')
;

-- Column: M_ShipmentSchedule.IsShipmentNotificationRequired
-- 2024-10-02T13:23:05.576Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589178,583295,0,20,500221,'IsShipmentNotificationRequired',TO_TIMESTAMP('2024-10-02 15:23:05.369','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','de.metas.inoutcandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Lieferavis erforderlich',0,0,TO_TIMESTAMP('2024-10-02 15:23:05.369','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-02T13:23:05.582Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589178 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-02T13:23:05.587Z
/* DDL */  select update_Column_Translation_From_AD_Element(583295)
;

-- 2024-10-02T13:23:08.889Z
/* DDL */ SELECT public.db_alter_table('M_ShipmentSchedule','ALTER TABLE public.M_ShipmentSchedule ADD COLUMN IsShipmentNotificationRequired CHAR(1) DEFAULT ''N'' CHECK (IsShipmentNotificationRequired IN (''Y'',''N'')) NOT NULL')
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Lieferavis erforderlich
-- Column: M_ShipmentSchedule.IsShipmentNotificationRequired
-- 2024-10-02T15:51:03.331Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589178,731797,0,500221,TO_TIMESTAMP('2024-10-02 17:51:03.179','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Lieferavis erforderlich',TO_TIMESTAMP('2024-10-02 17:51:03.179','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-02T15:51:03.334Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-02T15:51:03.336Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583295)
;

-- 2024-10-02T15:51:03.340Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731797
;

-- 2024-10-02T15:51:03.341Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731797)
;

-- UI Element: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Lieferavis erforderlich
-- Column: M_ShipmentSchedule.IsShipmentNotificationRequired
-- 2024-10-02T15:52:15.725Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731797,0,500221,540052,626112,'F',TO_TIMESTAMP('2024-10-02 17:52:15.599','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Lieferavis erforderlich',350,0,0,TO_TIMESTAMP('2024-10-02 17:52:15.599','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Lieferdisposition(500221,de.metas.inoutcandidate) -> Auslieferplan(500221,de.metas.inoutcandidate) -> Lieferavis erforderlich
-- Column: M_ShipmentSchedule.IsShipmentNotificationRequired
-- 2024-10-03T11:43:58.122Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2024-10-03 13:43:58.122','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731797
;
