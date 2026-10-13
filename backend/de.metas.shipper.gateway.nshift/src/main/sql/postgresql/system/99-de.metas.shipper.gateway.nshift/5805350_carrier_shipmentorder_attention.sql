-- Run mode: SWING_CLIENT

-- IDs allocated from idserver.metas.de on 2026-05-29:
--   AD_Element 584923 /*From ID Server*/ (Receiver_Attention)
--   AD_Element 584924 /*From ID Server*/ (Shipper_Attention)
--   AD_Column  592664 /*From ID Server*/ (Carrier_ShipmentOrder.Receiver_Attention)
--   AD_Column  592665 /*From ID Server*/ (Carrier_ShipmentOrder.Shipper_Attention)

-- Element: Receiver_Attention
-- 2026-05-29T10:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584923 /*From ID Server*/,0,'Receiver_Attention',TO_TIMESTAMP('2026-05-29 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','z. Hd. (Empfänger)','z. Hd. (Empfänger)',TO_TIMESTAMP('2026-05-29 10:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-29T10:00:00.001Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584923 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Receiver_Attention — en_US translation
-- 2026-05-29T10:00:12.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attn. (Receiver)', PrintName='Attn. (Receiver)',Updated=TO_TIMESTAMP('2026-05-29 10:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584923 AND AD_Language='en_US'
;

-- 2026-05-29T10:00:12.001Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-29T10:00:12.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584923,'en_US')
;

-- Element: Receiver_Attention — de_CH
-- 2026-05-29T10:00:13.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-29 10:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584923 AND AD_Language='de_CH'
;

-- 2026-05-29T10:00:13.001Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584923,'de_CH')
;

-- Element: Receiver_Attention — de_DE
-- 2026-05-29T10:00:14.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-29 10:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584923 AND AD_Language='de_DE'
;

-- 2026-05-29T10:00:14.001Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584923,'de_DE')
;

-- 2026-05-29T10:00:14.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584923,'de_DE')
;

-- Column: Carrier_ShipmentOrder.Receiver_Attention
-- 2026-05-29T10:01:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592664 /*From ID Server*/,584923,0,10,542532,'XX','Receiver_Attention',TO_TIMESTAMP('2026-05-29 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'z. Hd. (Empfänger)','P',0,0,TO_TIMESTAMP('2026-05-29 10:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-29T10:01:00.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592664 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-29T10:01:00.002Z
/* DDL */  select update_Column_Translation_From_AD_Element(584923)
;

-- 2026-05-29T10:01:01.000Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE public.Carrier_ShipmentOrder ADD COLUMN Receiver_Attention VARCHAR(30)')
;

-- Element: Shipper_Attention
-- 2026-05-29T10:02:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584924 /*From ID Server*/,0,'Shipper_Attention',TO_TIMESTAMP('2026-05-29 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','z. Hd. (Lieferant)','z. Hd. (Lieferant)',TO_TIMESTAMP('2026-05-29 10:02:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-05-29T10:02:00.001Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584924 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Shipper_Attention — en_US translation
-- 2026-05-29T10:02:12.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Attn. (Shipper)', PrintName='Attn. (Shipper)',Updated=TO_TIMESTAMP('2026-05-29 10:02:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584924 AND AD_Language='en_US'
;

-- 2026-05-29T10:02:12.001Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-05-29T10:02:12.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584924,'en_US')
;

-- Element: Shipper_Attention — de_CH
-- 2026-05-29T10:02:13.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-29 10:02:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584924 AND AD_Language='de_CH'
;

-- 2026-05-29T10:02:13.001Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584924,'de_CH')
;

-- Element: Shipper_Attention — de_DE
-- 2026-05-29T10:02:14.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-05-29 10:02:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584924 AND AD_Language='de_DE'
;

-- 2026-05-29T10:02:14.001Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584924,'de_DE')
;

-- 2026-05-29T10:02:14.002Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584924,'de_DE')
;

-- Column: Carrier_ShipmentOrder.Shipper_Attention
-- 2026-05-29T10:03:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592665 /*From ID Server*/,584924,0,10,542532,'XX','Shipper_Attention',TO_TIMESTAMP('2026-05-29 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,30,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'z. Hd. (Lieferant)','P',0,0,TO_TIMESTAMP('2026-05-29 10:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-05-29T10:03:00.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592665 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-05-29T10:03:00.002Z
/* DDL */  select update_Column_Translation_From_AD_Element(584924)
;

-- 2026-05-29T10:03:01.000Z
/* DDL */ SELECT public.db_alter_table('Carrier_ShipmentOrder','ALTER TABLE public.Carrier_ShipmentOrder ADD COLUMN Shipper_Attention VARCHAR(30)')
;

-- Window fields for Receiver_Attention and Shipper_Attention

-- AD_Field: Receiver_Attention
-- 2026-05-29T10:04:00.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,Description,DisplayLength,EntityType,
                      IsActive,IsDisplayed,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,780642 /*From ID Server*/,592664,548456,
        TO_TIMESTAMP('2026-05-29 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,30,'D','Y','Y','z. Hd. (Empfänger)',NULL,
        TO_TIMESTAMP('2026-05-29 10:04:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100);

-- 2026-05-29T10:04:00.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780642 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-29T10:04:00.002Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584923)
;

-- 2026-05-29T10:04:00.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780642
;

-- 2026-05-29T10:04:00.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780642)
;

-- AD_UI_Element: Receiver_Attention in receiver group (553601), SeqNo=150
-- 2026-05-29T10:04:01.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,
                           Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize)
VALUES (0,0,651942 /*From ID Server*/,780642 /*From ID Server*/,553601,548456,
        TO_TIMESTAMP('2026-05-29 10:04:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',
        'N','Y','N','N',
        'z. Hd. (Empfänger)',150,0,0,
        TO_TIMESTAMP('2026-05-29 10:04:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M');

-- AD_Field: Shipper_Attention
-- 2026-05-29T10:04:02.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Org_ID,AD_Field_ID,AD_Column_ID,AD_Tab_ID,
                      Created,CreatedBy,Description,DisplayLength,EntityType,
                      IsActive,IsDisplayed,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,780643 /*From ID Server*/,592665,548456,
        TO_TIMESTAMP('2026-05-29 10:04:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,
        NULL,30,'D','Y','Y','z. Hd. (Lieferant)',NULL,
        TO_TIMESTAMP('2026-05-29 10:04:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100);

-- 2026-05-29T10:04:02.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=780643 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-05-29T10:04:02.002Z
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584924)
;

-- 2026-05-29T10:04:02.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780643
;

-- 2026-05-29T10:04:02.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(780643)
;

-- AD_UI_Element: Shipper_Attention in shipper group (553599), SeqNo=140
-- 2026-05-29T10:04:03.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_UI_Element_ID,AD_Field_ID,AD_UI_ElementGroup_ID,AD_Tab_ID,
                           Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,
                           Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize)
VALUES (0,0,651943 /*From ID Server*/,780643 /*From ID Server*/,553599,548456,
        TO_TIMESTAMP('2026-05-29 10:04:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',
        'N','Y','N','N',
        'z. Hd. (Lieferant)',140,0,0,
        TO_TIMESTAMP('2026-05-29 10:04:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M');
