-- Run mode: SWING_CLIENT

-- -- Column: ModCntr_Interest.ShippingNotification_ModCntr_Log_ID
-- -- 2024-06-27T12:54:50.123Z
-- UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-06-27 15:54:50.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588268
-- ;

-- 2024-06-27T12:55:51.975Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583155,0,'InterimContract_ModCntr_Log_ID',TO_TIMESTAMP('2024-06-27 15:55:51.853','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Interim Contract Log','Interim Contract Log',TO_TIMESTAMP('2024-06-27 15:55:51.853','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-27T12:55:51.980Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583155 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: ModCntr_Interest.InterimContract_ModCntr_Log_ID
-- 2024-06-27T13:12:42.136Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588629,583155,0,30,541775,542410,'InterimContract_ModCntr_Log_ID',TO_TIMESTAMP('2024-06-27 16:12:41.941','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Interim Contract Log',0,0,TO_TIMESTAMP('2024-06-27 16:12:41.941','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-06-27T13:12:42.138Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-27T13:12:42.173Z
/* DDL */  select update_Column_Translation_From_AD_Element(583155)
;

-- 2024-06-27T13:12:43.357Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Interest','ALTER TABLE public.ModCntr_Interest ADD COLUMN InterimContract_ModCntr_Log_ID NUMERIC(10)')
;

-- 2024-06-27T13:12:43.565Z
ALTER TABLE ModCntr_Interest ADD CONSTRAINT InterimContractModCntrLog_ModCntrInterest FOREIGN KEY (InterimContract_ModCntr_Log_ID) REFERENCES public.ModCntr_Log DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT


-- -- 2024-07-02T08:07:57.240Z
-- INSERT INTO t_alter_column values('modcntr_interest','ShippingNotification_ModCntr_Log_ID','NUMERIC(10)',null,null)
-- ;

-- -- 2024-07-02T08:07:57.246Z
-- INSERT INTO t_alter_column values('modcntr_interest','ShippingNotification_ModCntr_Log_ID',null,'NULL',null)
-- ;




-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Akontorechnung
-- Column: ModCntr_Interest_V.C_InterimInvoice_ID
-- 2024-07-02T12:51:13.540Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624769
;

-- 2024-07-02T12:51:13.553Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728758
;

-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Akontorechnung
-- Column: ModCntr_Interest_V.C_InterimInvoice_ID
-- 2024-07-02T12:51:13.559Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728758
;

-- 2024-07-02T12:51:13.563Z
DELETE FROM AD_Field WHERE AD_Field_ID=728758
;

-- -- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Vorfinanzierungsbetrag
-- -- Column: ModCntr_Interest_V.InterimAmt
-- -- 2024-07-02T12:51:34.882Z
-- DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624762
-- ;

-- -- 2024-07-02T12:51:34.884Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=728742
-- ;

-- -- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Vorfinanzierungsbetrag
-- -- Column: ModCntr_Interest_V.InterimAmt
-- -- 2024-07-02T12:51:34.887Z
-- DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728742
-- ;

-- -- 2024-07-02T12:51:34.891Z
-- DELETE FROM AD_Field WHERE AD_Field_ID=728742
-- ;



-- -- Column: ModCntr_Interest_V.InterimAmt
-- -- 2024-07-02T14:55:24.991Z
-- DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588301
-- ;

-- -- 2024-07-02T14:55:24.997Z
-- DELETE FROM AD_Column WHERE AD_Column_ID=588301
-- ;

-- -- 2024-07-02T14:55:54.098Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=728736
-- ;

-- -- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Datum Vorfinanzierung
-- -- Column: ModCntr_Interest_V.InterimDate
-- -- 2024-07-02T14:55:54.102Z
-- DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728736
-- ;

-- -- 2024-07-02T14:55:54.106Z
-- DELETE FROM AD_Field WHERE AD_Field_ID=728736
-- ;

-- -- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Datum Vorfinanzierung
-- -- Column: ModCntr_Interest_V.InterimDate
-- -- 2024-07-02T14:56:29.686Z
-- INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588295,729022,0,547546,0,TO_TIMESTAMP('2024-07-02 17:56:29.392','YYYY-MM-DD HH24:MI:SS.US'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','N','Datum Vorfinanzierung',0,10,0,1,1,TO_TIMESTAMP('2024-07-02 17:56:29.392','YYYY-MM-DD HH24:MI:SS.US'),100)
-- ;

-- -- 2024-07-02T14:56:29.693Z
-- INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
-- ;

-- -- 2024-07-02T14:56:29.730Z
-- /* DDL */  select update_FieldTranslation_From_AD_Name_Element(583086)
-- ;

-- -- 2024-07-02T14:56:29.737Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=729022
-- ;

-- -- 2024-07-02T14:56:29.738Z
-- /* DDL */ select AD_Element_Link_Create_Missing_Field(729022)
-- ;

-- -- Column: ModCntr_Interest_V.InterimInvoice_documentNo
-- -- 2024-07-02T15:01:45.868Z
-- DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588294
-- ;

-- -- 2024-07-02T15:01:45.873Z
-- DELETE FROM AD_Column WHERE AD_Column_ID=588294
-- ;

-- -- Column: ModCntr_Interest_V.C_InterimInvoice_ID
-- -- 2024-07-02T15:01:59.255Z
-- DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588317
-- ;

-- -- 2024-07-02T15:01:59.259Z
-- DELETE FROM AD_Column WHERE AD_Column_ID=588317
-- ;

-- -- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000028 -> 10 -> main.Gesamtbetrag
-- -- Column: ModCntr_Interest_V.TotalAmt
-- -- 2024-07-02T15:06:18.116Z
-- DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624764
-- ;

-- -- 2024-07-02T15:06:18.128Z
-- DELETE FROM AD_Element_Link WHERE AD_Field_ID=728744
-- ;

-- -- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Gesamtbetrag
-- -- Column: ModCntr_Interest_V.TotalAmt
-- -- 2024-07-02T15:06:18.149Z
-- DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=728744
-- ;

-- -- 2024-07-02T15:06:18.178Z
-- DELETE FROM AD_Field WHERE AD_Field_ID=728744
-- ;

-- -- Column: ModCntr_Interest_V.TotalAmt
-- -- 2024-07-02T15:06:25.798Z
-- DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588303
-- ;

-- -- 2024-07-02T15:06:25.802Z
-- DELETE FROM AD_Column WHERE AD_Column_ID=588303
-- ;

-- Run mode: SWING_CLIENT



-- 2024-07-05T09:59:30.600Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583171,0,'C_Interim_Flatrate_Term_ID',TO_TIMESTAMP('2024-07-05 12:59:30.345','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Interim Contract','Interim Contract',TO_TIMESTAMP('2024-07-05 12:59:30.345','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-05T09:59:30.605Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583171 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: C_Interim_Flatrate_Term_ID
-- 2024-07-05T10:07:07.923Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Akonto-vertrag', PrintName='Akonto-vertrag',Updated=TO_TIMESTAMP('2024-07-05 13:07:07.923','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583171 AND AD_Language='de_CH'
;

-- 2024-07-05T10:07:07.929Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583171,'de_CH')
;

-- Element: C_Interim_Flatrate_Term_ID
-- 2024-07-05T10:07:14.635Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Akonto-vertrag', PrintName='Akonto-vertrag',Updated=TO_TIMESTAMP('2024-07-05 13:07:14.635','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583171 AND AD_Language='de_DE'
;

-- 2024-07-05T10:07:14.639Z
UPDATE AD_Element SET Name='Akonto-vertrag', PrintName='Akonto-vertrag' WHERE AD_Element_ID=583171
;

-- 2024-07-05T10:07:15.035Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583171,'de_DE')
;

-- 2024-07-05T10:07:15.037Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583171,'de_DE')
;

-- Column: ModCntr_Interest_V.M_Shipping_Notification_ID
-- 2024-07-05T10:08:34.420Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,588676,582695,0,30,542412,'M_Shipping_Notification_ID',TO_TIMESTAMP('2024-07-05 13:08:34.253','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts',10,'Y','Y','N','N','N','N','N','N','N','N','N','Shipping Notification',TO_TIMESTAMP('2024-07-05 13:08:34.253','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-05T10:08:34.422Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588676 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T10:08:34.426Z
/* DDL */  select update_Column_Translation_From_AD_Element(582695)
;

-- 2024-07-05T13:21:51.127Z
UPDATE AD_Element SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-07-05 16:21:51.119','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583171
;

-- 2024-07-05T13:21:51.148Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583171,'de_DE')
;

-- Column: ModCntr_Interest_V.C_Interim_Flatrate_Term_ID
-- 2024-07-05T13:24:45.120Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588710,583171,0,30,540279,542412,'C_Interim_Flatrate_Term_ID',TO_TIMESTAMP('2024-07-05 16:24:44.88','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.contracts',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Akonto-vertrag',0,0,TO_TIMESTAMP('2024-07-05 16:24:44.88','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-05T13:24:45.121Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588710 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-05T13:24:45.124Z
/* DDL */  select update_Column_Translation_From_AD_Element(583171)
;

;


-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Shipping Notification
-- Column: ModCntr_Interest_V.M_Shipping_Notification_ID
-- 2024-07-05T13:25:04.852Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588676,729046,0,547546,TO_TIMESTAMP('2024-07-05 16:25:04.735','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Shipping Notification',TO_TIMESTAMP('2024-07-05 16:25:04.735','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-05T13:25:04.854Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:25:04.855Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582695)
;

-- 2024-07-05T13:25:04.858Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729046
;

-- 2024-07-05T13:25:04.859Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729046)
;


-- Field: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> Akonto-vertrag
-- Column: ModCntr_Interest_V.C_Interim_Flatrate_Term_ID
-- 2024-07-05T13:25:05.354Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588710,729050,0,547546,TO_TIMESTAMP('2024-07-05 16:25:05.251','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Akonto-vertrag',TO_TIMESTAMP('2024-07-05 16:25:05.251','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-05T13:25:05.355Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-05T13:25:05.357Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583171)
;

-- Run mode: SWING_CLIENT

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000029 -> 10 -> main.Akonto-vertrag
-- Column: ModCntr_Interest_V.C_Interim_Flatrate_Term_ID
-- 2024-07-05T13:58:25.028Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729050,0,547546,551836,624977,'F',TO_TIMESTAMP('2024-07-05 16:58:24.359','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Akonto-vertrag',180,0,0,TO_TIMESTAMP('2024-07-05 16:58:24.359','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zinsberechnungslauf(541801,de.metas.contracts) -> Zins(547546,de.metas.contracts) -> 1000029 -> 10 -> main.Shipping Notification
-- Column: ModCntr_Interest_V.M_Shipping_Notification_ID
-- 2024-07-05T13:58:46.898Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729046,0,547546,551836,624978,'F',TO_TIMESTAMP('2024-07-05 16:58:46.714','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Shipping Notification',190,0,0,TO_TIMESTAMP('2024-07-05 16:58:46.714','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Element: M_Shipping_Notification_ID
-- 2024-07-05T13:59:19.066Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferavis', PrintName='Lieferavis',Updated=TO_TIMESTAMP('2024-07-05 16:59:19.066','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582695 AND AD_Language='de_CH'
;

-- 2024-07-05T13:59:19.099Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582695,'de_CH')
;

-- Element: M_Shipping_Notification_ID
-- 2024-07-05T13:59:22.914Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Lieferavis', PrintName='Lieferavis',Updated=TO_TIMESTAMP('2024-07-05 16:59:22.914','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582695 AND AD_Language='de_DE'
;

-- 2024-07-05T13:59:22.915Z
UPDATE AD_Element SET Name='Lieferavis', PrintName='Lieferavis' WHERE AD_Element_ID=582695
;

-- 2024-07-05T13:59:23.542Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582695,'de_DE')
;

-- 2024-07-05T13:59:23.552Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582695,'de_DE')
;

