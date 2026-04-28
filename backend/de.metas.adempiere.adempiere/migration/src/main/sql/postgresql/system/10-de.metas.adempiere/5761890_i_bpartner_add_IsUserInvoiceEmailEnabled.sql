-- Run mode: SWING_CLIENT

-- 2025-08-01T07:04:42.823Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583843,0,'IsUserInvoiceEmailEnabled',TO_TIMESTAMP('2025-08-01 07:04:42.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Rechnung per eMail (Nutzer)','Rechnung per eMail (Nutzer)',TO_TIMESTAMP('2025-08-01 07:04:42.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-01T07:04:42.828Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583843 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsUserInvoiceEmailEnabled
-- 2025-08-01T07:05:19.510Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoice via eMail (User)', PrintName='Invoice via eMail (User)',Updated=TO_TIMESTAMP('2025-08-01 07:05:19.510000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583843 AND AD_Language='en_US'
;

-- 2025-08-01T07:05:19.511Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-08-01T07:05:19.705Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583843,'en_US')
;

-- Element: IsUserInvoiceEmailEnabled
-- 2025-08-01T07:05:20.870Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-01 07:05:20.870000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583843 AND AD_Language='de_CH'
;

-- 2025-08-01T07:05:20.881Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583843,'de_CH')
;

-- Element: IsUserInvoiceEmailEnabled
-- 2025-08-01T07:05:21.913Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-08-01 07:05:21.913000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583843 AND AD_Language='de_DE'
;

-- 2025-08-01T07:05:21.922Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583843,'de_DE')
;

-- 2025-08-01T07:05:21.926Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583843,'de_DE')
;
-- Column: I_BPartner.IsUserInvoiceEmailEnabled
-- 2025-08-01T07:08:34.739Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590589,583843,0,17,319,533,'XX','IsUserInvoiceEmailEnabled',TO_TIMESTAMP('2025-08-01 07:08:34.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnung per eMail (Nutzer)',0,0,TO_TIMESTAMP('2025-08-01 07:08:34.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-08-01T07:08:34.741Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590589 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-08-01T07:08:34.744Z
/* DDL */  select update_Column_Translation_From_AD_Element(583843)
;

-- 2025-08-01T07:18:35.929Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN IsUserInvoiceEmailEnabled CHAR(1)')
;

-- Field: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> Rechnung per eMail (Nutzer)
-- Column: I_BPartner.IsUserInvoiceEmailEnabled
-- 2025-08-01T08:16:59.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590589,751751,0,441,TO_TIMESTAMP('2025-08-01 08:16:59.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Rechnung per eMail (Nutzer)',TO_TIMESTAMP('2025-08-01 08:16:59.385000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-08-01T08:16:59.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=751751 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-08-01T08:16:59.576Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583843)
;

-- 2025-08-01T08:16:59.586Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=751751
;

-- 2025-08-01T08:16:59.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(751751)
;

-- UI Element: Import Geschäftspartner(172,D) -> Import - Geschäftspartner(441,D) -> advanced edit -> 10 -> advanced edit.Rechnung per eMail (Nutzer)
-- Column: I_BPartner.IsUserInvoiceEmailEnabled
-- 2025-08-01T08:18:52.580Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,751751,0,441,541262,636088,'F',TO_TIMESTAMP('2025-08-01 08:18:52.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Y','N','Y','N','N','N',0,'Rechnung per eMail (Nutzer)',590,0,0,TO_TIMESTAMP('2025-08-01 08:18:52.439000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Nutzer / Kontakt(496,D) -> main -> 10 -> main.eMail per Rechnung
-- Column: AD_User.IsInvoiceEmailEnabled
-- 2025-08-01T08:29:20.896Z
UPDATE AD_UI_Element SET IsAdvancedField='Y', IsDisplayed='Y',Updated=TO_TIMESTAMP('2025-08-01 08:29:20.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544832
;

