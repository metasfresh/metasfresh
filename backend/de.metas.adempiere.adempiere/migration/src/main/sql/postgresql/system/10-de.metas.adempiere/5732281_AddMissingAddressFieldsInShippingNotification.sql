-- Column: M_Shipping_Notification.ShipFrom_User_ID
-- 2024-09-02T12:16:29.363Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588940,583240,0,30,540376,542365,'ShipFrom_User_ID',TO_TIMESTAMP('2024-09-02 15:16:29.232','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'ShipFrom User',0,0,TO_TIMESTAMP('2024-09-02 15:16:29.232','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-02T12:16:29.379Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588940 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-02T12:16:29.401Z
/* DDL */  select update_Column_Translation_From_AD_Element(583240)
;

-- Column: M_Shipping_Notification.ShipFrom_User_ID
-- 2024-09-02T12:17:16.051Z
UPDATE AD_Column SET AD_Reference_Value_ID=110,Updated=TO_TIMESTAMP('2024-09-02 15:17:16.051','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588940
;


-- Name: AD_User ShipFrom Partner
-- 2024-09-03T07:00:13.242Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540688,'AD_User.C_BPartner_ID=@ShipFrom_Partner_ID@',TO_TIMESTAMP('2024-09-03 10:00:13.051','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','AD_User ShipFrom Partner','S',TO_TIMESTAMP('2024-09-03 10:00:13.051','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: M_Shipping_Notification.ShipFrom_User_ID
-- 2024-09-03T07:00:34.187Z
UPDATE AD_Column SET AD_Val_Rule_ID=540688,Updated=TO_TIMESTAMP('2024-09-03 10:00:34.187','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588940
;

-- 2024-09-03T07:00:36.793Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN ShipFrom_User_ID NUMERIC(10)')
;

-- 2024-09-03T07:00:37.104Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT ShipFromUser_MShippingNotification FOREIGN KEY (ShipFrom_User_ID) REFERENCES public.AD_User DEFERRABLE INITIALLY DEFERRED
;

-- 2024-09-03T07:03:09.927Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,Updated,UpdatedBy) VALUES (0,583241,0,'ShipFrom_Location_ID',TO_TIMESTAMP('2024-09-03 10:03:09.806','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Lieferung von-) Adresse des Geschäftspartners','de.metas.shippingnotification','Identifiziert die Adresse des Geschäftspartners','Y','Standort','','','','','Standort',TO_TIMESTAMP('2024-09-03 10:03:09.806','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:03:09.932Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583241 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ShipFrom_Location_ID
-- 2024-09-03T07:03:15.176Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-03 10:03:15.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583241 AND AD_Language='de_CH'
;

-- 2024-09-03T07:03:15.181Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583241,'de_CH')
;

-- Element: ShipFrom_Location_ID
-- 2024-09-03T07:03:17.714Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-03 10:03:17.714','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583241 AND AD_Language='de_DE'
;

-- 2024-09-03T07:03:17.719Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583241,'de_DE')
;

-- 2024-09-03T07:03:17.721Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583241,'de_DE')
;

-- Element: ShipFrom_Location_ID
-- 2024-09-03T07:03:46.682Z
UPDATE AD_Element_Trl SET Description='Indentifies Ship From address', Help='Indentifies Ship From address', IsTranslated='Y', Name='Location', PrintName='Location',Updated=TO_TIMESTAMP('2024-09-03 10:03:46.682','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583241 AND AD_Language='en_US'
;

-- 2024-09-03T07:03:46.685Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583241,'en_US')
;

-- Column: M_Shipping_Notification.ShipFrom_Location_ID
-- 2024-09-03T07:04:08.069Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588941,583241,0,30,159,542365,'ShipFrom_Location_ID',TO_TIMESTAMP('2024-09-03 10:04:07.951','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Identifiziert die (Lieferung von-) Adresse des Geschäftspartners','de.metas.shippingnotification',0,10,'Identifiziert die Adresse des Geschäftspartners','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort',0,0,TO_TIMESTAMP('2024-09-03 10:04:07.951','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-03T07:04:08.072Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588941 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-03T07:04:08.075Z
/* DDL */  select update_Column_Translation_From_AD_Element(583241)
;

-- Name: C_BPartner_Location_ID_ShipFrom(ShipFrom_BPartner_ID)
-- 2024-09-03T07:05:49.625Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540689,'C_BPartner_Location.C_BPartner_ID=@ShipFrom_Partner_ID@AND C_BPartner_Location.IsActive=''Y'' AND C_BPartner_Location.IsEphemeral=''N''',TO_TIMESTAMP('2024-09-03 10:05:49.482','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.shippingnotification','Y','C_BPartner_Location_ID_ShipFrom(ShipFrom_BPartner_ID)','S',TO_TIMESTAMP('2024-09-03 10:05:49.482','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Name: C_BPartner_Location_ID_ShipFrom(ShipFrom_BPartner_ID)
-- 2024-09-03T07:05:51.744Z
UPDATE AD_Val_Rule SET Code='C_BPartner_Location.C_BPartner_ID=@ShipFrom_Partner_ID@ AND C_BPartner_Location.IsActive=''Y'' AND C_BPartner_Location.IsEphemeral=''N''',Updated=TO_TIMESTAMP('2024-09-03 10:05:51.742','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540689
;

-- Column: M_Shipping_Notification.ShipFrom_Location_ID
-- 2024-09-03T07:06:05.528Z
UPDATE AD_Column SET AD_Val_Rule_ID=540689,Updated=TO_TIMESTAMP('2024-09-03 10:06:05.528','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588941
;

-- 2024-09-03T07:06:07.779Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN ShipFrom_Location_ID NUMERIC(10)')
;

-- 2024-09-03T07:06:07.946Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT ShipFromLocation_MShippingNotification FOREIGN KEY (ShipFrom_Location_ID) REFERENCES public.C_BPartner_Location DEFERRABLE INITIALLY DEFERRED
;

-- 2024-09-03T07:06:56.390Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583242,0,'ShipFrom_Location_Value_ID',TO_TIMESTAMP('2024-09-03 10:06:56.261','YYYY-MM-DD HH24:MI:SS.US'),100,'','de.metas.shippingnotification','Y','Standort (Address)','Standort (Address)',TO_TIMESTAMP('2024-09-03 10:06:56.261','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:06:56.393Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583242 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: ShipFrom_Location_Value_ID
-- 2024-09-03T07:06:59.724Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-03 10:06:59.724','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583242 AND AD_Language='de_CH'
;

-- 2024-09-03T07:06:59.727Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583242,'de_CH')
;

-- Element: ShipFrom_Location_Value_ID
-- 2024-09-03T07:07:01.682Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-03 10:07:01.682','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583242 AND AD_Language='de_DE'
;

-- 2024-09-03T07:07:01.686Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583242,'de_DE')
;

-- 2024-09-03T07:07:01.688Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583242,'de_DE')
;

-- Element: ShipFrom_Location_Value_ID
-- 2024-09-03T07:07:22.226Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Location (Address)', PrintName='Location (Address)',Updated=TO_TIMESTAMP('2024-09-03 10:07:22.226','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583242 AND AD_Language='en_US'
;

-- 2024-09-03T07:07:22.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583242,'en_US')
;

-- Column: M_Shipping_Notification.ShipFrom_Location_Value_ID
-- 2024-09-03T07:07:50.234Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588942,583242,0,21,542365,'ShipFrom_Location_Value_ID',TO_TIMESTAMP('2024-09-03 10:07:50.095','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort (Address)',0,0,TO_TIMESTAMP('2024-09-03 10:07:50.095','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-03T07:07:50.237Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588942 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-03T07:07:50.248Z
/* DDL */  select update_Column_Translation_From_AD_Element(583242)
;

-- 2024-09-03T07:07:51.904Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN ShipFrom_Location_Value_ID NUMERIC(10)')
;

-- 2024-09-03T07:07:52.070Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT ShipFromLocationValue_MShippingNotification FOREIGN KEY (ShipFrom_Location_Value_ID) REFERENCES public.C_Location DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_Shipping_Notification.C_BPartner_Location_Value_ID
-- 2024-09-03T07:09:11.673Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588943,579023,0,21,542365,'C_BPartner_Location_Value_ID',TO_TIMESTAMP('2024-09-03 10:09:11.545','YYYY-MM-DD HH24:MI:SS.US'),100,'N','','de.metas.shippingnotification',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Standort (Address)',0,0,TO_TIMESTAMP('2024-09-03 10:09:11.545','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-03T07:09:11.676Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588943 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-03T07:09:11.680Z
/* DDL */  select update_Column_Translation_From_AD_Element(579023)
;

-- 2024-09-03T07:09:15.063Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN C_BPartner_Location_Value_ID NUMERIC(10)')
;

-- 2024-09-03T07:09:15.200Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT CBPartnerLocationValue_MShippingNotification FOREIGN KEY (C_BPartner_Location_Value_ID) REFERENCES public.C_Location DEFERRABLE INITIALLY DEFERRED
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> main -> 10 -> default.Lieferung von
-- Column: M_Shipping_Notification.ShipFrom_Partner_ID
-- 2024-09-03T07:12:33.937Z
UPDATE AD_UI_Element SET UIStyle='primary', WidgetSize='L',Updated=TO_TIMESTAMP('2024-09-03 10:12:33.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625300
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> Storno-Gegenbeleg
-- Column: M_Shipping_Notification.Reversal_ID
-- 2024-09-03T07:12:52.989Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587470,729846,0,547218,TO_TIMESTAMP('2024-09-03 10:12:52.874','YYYY-MM-DD HH24:MI:SS.US'),100,'ID of document reversal',10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Storno-Gegenbeleg',TO_TIMESTAMP('2024-09-03 10:12:52.874','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:12:52.997Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729846 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-03T07:12:53.004Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53457)
;

-- 2024-09-03T07:12:53.029Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729846
;

-- 2024-09-03T07:12:53.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729846)
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> ShipFrom User
-- Column: M_Shipping_Notification.ShipFrom_User_ID
-- 2024-09-03T07:12:53.122Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588940,729847,0,547218,TO_TIMESTAMP('2024-09-03 10:12:53.037','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','ShipFrom User',TO_TIMESTAMP('2024-09-03 10:12:53.037','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:12:53.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-03T07:12:53.127Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583240)
;

-- 2024-09-03T07:12:53.131Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729847
;

-- 2024-09-03T07:12:53.132Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729847)
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> Standort
-- Column: M_Shipping_Notification.ShipFrom_Location_ID
-- 2024-09-03T07:12:53.234Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588941,729848,0,547218,TO_TIMESTAMP('2024-09-03 10:12:53.136','YYYY-MM-DD HH24:MI:SS.US'),100,'Identifiziert die (Lieferung von-) Adresse des Geschäftspartners',10,'de.metas.shippingnotification','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2024-09-03 10:12:53.136','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:12:53.236Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-03T07:12:53.238Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583241)
;

-- 2024-09-03T07:12:53.240Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729848
;

-- 2024-09-03T07:12:53.241Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729848)
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> Standort (Address)
-- Column: M_Shipping_Notification.ShipFrom_Location_Value_ID
-- 2024-09-03T07:12:53.328Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588942,729849,0,547218,TO_TIMESTAMP('2024-09-03 10:12:53.243','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2024-09-03 10:12:53.243','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:12:53.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729849 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-03T07:12:53.331Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583242)
;

-- 2024-09-03T07:12:53.334Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729849
;

-- 2024-09-03T07:12:53.335Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729849)
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> Standort (Address)
-- Column: M_Shipping_Notification.C_BPartner_Location_Value_ID
-- 2024-09-03T07:12:53.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588943,729850,0,547218,TO_TIMESTAMP('2024-09-03 10:12:53.337','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.shippingnotification','','Y','N','N','N','N','N','N','N','Standort (Address)',TO_TIMESTAMP('2024-09-03 10:12:53.337','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:12:53.428Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729850 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-03T07:12:53.429Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579023)
;

-- 2024-09-03T07:12:53.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729850
;

-- 2024-09-03T07:12:53.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729850)
;

-- 2024-09-03T07:15:10.089Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,729848,0,541834,625300,TO_TIMESTAMP('2024-09-03 10:15:09.948','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,'widget',TO_TIMESTAMP('2024-09-03 10:15:09.948','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-03T07:15:18.957Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,729847,0,541835,625300,TO_TIMESTAMP('2024-09-03 10:15:18.848','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',20,'widget',TO_TIMESTAMP('2024-09-03 10:15:18.848','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Run mode: SWING_CLIENT

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> main -> 10 -> default.Lieferung von
-- Column: M_Shipping_Notification.ShipFrom_Partner_ID
-- 2024-09-16T13:41:26.439Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=50,Updated=TO_TIMESTAMP('2024-09-16 16:41:26.439','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625300
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Lieferavis(547218,de.metas.shippingnotification) -> main -> 10 -> default.Auktion
-- Column: M_Shipping_Notification.C_Auction_ID
-- 2024-09-16T13:41:26.455Z
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=60,Updated=TO_TIMESTAMP('2024-09-16 16:41:26.454','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620440
;

-- Column: M_Shipping_Notification.ShipFrom_Partner_ID
-- 2024-09-17T11:40:47.301Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Val_Rule_ID=540244,Updated=TO_TIMESTAMP('2024-09-17 14:40:47.301','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588928
;

-- Column: M_Shipping_Notification.ShipFrom_Partner_ID
-- 2024-09-17T11:41:07.720Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2024-09-17 14:41:07.72','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588928
;

