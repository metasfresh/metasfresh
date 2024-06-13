-- Run mode: SWING_CLIENT

-- 2024-06-11T10:48:18.761Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583135,0,'InterimPricePercent',TO_TIMESTAMP('2024-06-11 13:48:17.656','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Interim price %','Interim price %',TO_TIMESTAMP('2024-06-11 13:48:17.656','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-11T10:48:18.778Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583135 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InterimPricePercent
-- 2024-06-11T10:48:59.937Z
UPDATE AD_Element_Trl SET Description='Set the interim contract specific price to given percentage of modular price for the raw product.', Help='Set the interim contract specific price to given percentage of modular price for the raw product.',Updated=TO_TIMESTAMP('2024-06-11 13:48:59.937','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583135 AND AD_Language='en_US'
;

-- 2024-06-11T10:48:59.996Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583135,'en_US')
;

-- Element: InterimPricePercent
-- 2024-06-11T10:49:17.649Z
UPDATE AD_Element_Trl SET Description='Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.', Help='Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.',Updated=TO_TIMESTAMP('2024-06-11 13:49:17.649','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583135 AND AD_Language='de_DE'
;

-- 2024-06-11T10:49:17.652Z
UPDATE AD_Element SET Description='Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.', Help='Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.' WHERE AD_Element_ID=583135
;

-- 2024-06-11T10:49:18.148Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583135,'de_DE')
;

-- 2024-06-11T10:49:18.151Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583135,'de_DE')
;

-- Element: InterimPricePercent
-- 2024-06-11T10:49:23.400Z
UPDATE AD_Element_Trl SET Description='Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.', Help='Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.',Updated=TO_TIMESTAMP('2024-06-11 13:49:23.4','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583135 AND AD_Language='de_CH'
;

-- 2024-06-11T10:49:23.403Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583135,'de_CH')
;

-- Element: InterimPricePercent
-- 2024-06-11T10:49:54.822Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Akonto Preis %', PrintName='Akonto Preis %',Updated=TO_TIMESTAMP('2024-06-11 13:49:54.822','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583135 AND AD_Language='de_DE'
;

-- 2024-06-11T10:49:54.823Z
UPDATE AD_Element SET Name='Akonto Preis %', PrintName='Akonto Preis %' WHERE AD_Element_ID=583135
;

-- 2024-06-11T10:49:55.173Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583135,'de_DE')
;

-- 2024-06-11T10:49:55.174Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583135,'de_DE')
;

-- Element: InterimPricePercent
-- 2024-06-11T10:49:59.174Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Akonto Preis %', PrintName='Akonto Preis %',Updated=TO_TIMESTAMP('2024-06-11 13:49:59.174','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583135 AND AD_Language='de_CH'
;

-- 2024-06-11T10:49:59.176Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583135,'de_CH')
;

-- Column: ModCntr_Settings.InterimPricePercent
-- 2024-06-11T12:45:55.607Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,ValueMin,Version) VALUES (0,588337,583135,0,22,542339,'InterimPricePercent',TO_TIMESTAMP('2024-06-11 15:45:54.38','YYYY-MM-DD HH24:MI:SS.US'),100,'N','75','Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.','de.metas.contracts',0,14,'Setze den Akonto vertragspezifischen Preis auf gegebenen Prozentsatz des Vertragsbaustein Preises des Rohprodukts.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Akonto Preis %',0,0,TO_TIMESTAMP('2024-06-11 15:45:54.38','YYYY-MM-DD HH24:MI:SS.US'),100,'0',0)
;

-- 2024-06-11T12:45:55.611Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588337 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-11T12:45:55.620Z
/* DDL */  select update_Column_Translation_From_AD_Element(583135)
;

-- 2024-06-11T12:46:16.416Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Settings','ALTER TABLE public.ModCntr_Settings ADD COLUMN InterimPricePercent NUMERIC DEFAULT 75 NOT NULL')
;

-- Run mode: SWING_CLIENT

-- Value: ModCntr_Settings_InterimPricePercent_Positive
-- 2024-06-12T14:25:52.900Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545421,0,TO_TIMESTAMP('2024-06-12 17:25:52.241','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','The interim price percent must be positive.','E',TO_TIMESTAMP('2024-06-12 17:25:52.241','YYYY-MM-DD HH24:MI:SS.US'),100,'ModCntr_Settings_InterimPricePercent_Positive')
;

-- 2024-06-12T14:25:52.908Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545421 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: ModCntr_Settings_InterimPricePercent_Positive
-- 2024-06-12T14:26:03.488Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Akonto Preis Prozentwert muss positiv sein',Updated=TO_TIMESTAMP('2024-06-12 17:26:03.488','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545421
;

-- Value: ModCntr_Settings_InterimPricePercent_Positive
-- 2024-06-12T14:26:06.199Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-06-12 17:26:06.199','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545421
;

-- Value: ModCntr_Settings_InterimPricePercent_Positive
-- 2024-06-12T14:26:11.251Z
UPDATE AD_Message_Trl SET IsTranslated='Y', MsgText='Der Akonto Preis Prozentwert muss positiv sein',Updated=TO_TIMESTAMP('2024-06-12 17:26:11.251','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545421
;

-- 2024-06-12T14:26:11.253Z
UPDATE AD_Message SET MsgText='Der Akonto Preis Prozentwert muss positiv sein' WHERE AD_Message_ID=545421
;

-- Value: de.metas.contracts.modular.settings.interceptor.InterimPricePercent_Positive
-- 2024-06-12T14:27:11.814Z
UPDATE AD_Message SET Value='de.metas.contracts.modular.settings.interceptor.InterimPricePercent_Positive',Updated=TO_TIMESTAMP('2024-06-12 17:27:11.812','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545421
;




-- Column: ModCntr_Settings.InterimPricePercent
-- 2024-06-12T14:49:19.918Z
UPDATE AD_Column SET ValueMin='',Updated=TO_TIMESTAMP('2024-06-12 17:49:19.918','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588337
;

-- 2024-06-12T14:49:22.485Z
INSERT INTO t_alter_column values('modcntr_settings','InterimPricePercent','NUMERIC',null,'75')
;

-- 2024-06-12T14:49:22.493Z
UPDATE ModCntr_Settings SET InterimPricePercent=75 WHERE InterimPricePercent IS NULL
;