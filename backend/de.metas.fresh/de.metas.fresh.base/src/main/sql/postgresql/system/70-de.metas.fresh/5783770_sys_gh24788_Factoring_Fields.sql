-- Run mode: SWING_CLIENT

-- 2026-01-14T08:36:31.599Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584394,0,'IsFactorer',TO_TIMESTAMP('2026-01-14 08:36:30.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.','D','Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.','Y','Factor','Factor',TO_TIMESTAMP('2026-01-14 08:36:30.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T08:36:31.686Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584394 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsFactorer
-- 2026-01-14T08:37:18.740Z
UPDATE AD_Element_Trl SET Description='Factoring is a financial transaction and a type of debtor finance in which a business sells its accounts receivable (i.e., invoices) to a third party (called a factor).', Help='Factoring is a financial transaction and a type of debtor finance in which a business sells its accounts receivable (i.e., invoices) to a third party (called a factor).', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-14 08:37:18.740000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584394 AND AD_Language='en_US'
;

-- 2026-01-14T08:37:18.820Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-14T08:37:36.866Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584394,'en_US')
;

-- 2026-01-14T08:41:39.877Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584395,0,'IsFactoring',TO_TIMESTAMP('2026-01-14 08:41:39.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Factoring','Factoring',TO_TIMESTAMP('2026-01-14 08:41:39.338000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T08:41:39.969Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584395 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsFactoring
-- 2026-01-14T08:42:34.879Z
UPDATE AD_Element_Trl SET Description='The business partner sells its receivables (invoices) to a factor to obtain immediate liquidity.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-14 08:42:34.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584395 AND AD_Language='en_US'
;

-- 2026-01-14T08:42:34.963Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-14T08:42:53.132Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584395,'en_US')
;

-- Element: IsFactoring
-- 2026-01-14T08:43:05.950Z
UPDATE AD_Element_Trl SET Help='The business partner sells its receivables (invoices) to a factor to obtain immediate liquidity.',Updated=TO_TIMESTAMP('2026-01-14 08:43:05.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584395 AND AD_Language='en_US'
;

-- 2026-01-14T08:43:06.060Z
UPDATE AD_Element base SET Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-14T08:43:25.299Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584395,'en_US')
;

-- Element: IsFactoring
-- 2026-01-14T08:44:36.046Z
UPDATE AD_Element_Trl SET Description='Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.', Help='Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-14 08:44:36.046000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584395 AND AD_Language='de_DE'
;

-- 2026-01-14T08:44:36.126Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-14T08:44:52.699Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584395,'de_DE')
;

-- 2026-01-14T08:44:52.779Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584395,'de_DE')
;

-- Column: C_BPartner.IsFactorer
-- 2026-01-14T08:47:32.307Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591857,584394,0,20,291,'XX','IsFactorer',TO_TIMESTAMP('2026-01-14 08:47:31.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.','D',0,1,'Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Factor',0,0,TO_TIMESTAMP('2026-01-14 08:47:31.667000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-14T08:47:32.386Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591857 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-14T08:47:32.548Z
/* DDL */  select update_Column_Translation_From_AD_Element(584394)
;

-- 2026-01-14T08:52:22.580Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540860,0,291,TO_TIMESTAMP('2026-01-14 08:52:22.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Nur ein Faktor innerhalb einer Organisation','Y','Y','C_BPartner_IsFactorer_Uniqe','N',TO_TIMESTAMP('2026-01-14 08:52:22.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'')
;

-- 2026-01-14T08:52:22.659Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540860 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2026-01-14T08:53:05.487Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Only one Factor within an organisation', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-14 08:53:05.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540860 AND AD_Language='en_US'
;

-- 2026-01-14T08:53:05.573Z
UPDATE AD_Index_Table base SET ErrorMsg=trl.ErrorMsg, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Index_Table_Trl trl  WHERE trl.AD_Index_Table_ID=base.AD_Index_Table_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-14T08:53:49.132Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,591857,541512,540860,0,TO_TIMESTAMP('2026-01-14 08:53:48.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',10,TO_TIMESTAMP('2026-01-14 08:53:48.700000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T08:54:19.413Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,2895,541513,540860,0,TO_TIMESTAMP('2026-01-14 08:54:18.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y',20,TO_TIMESTAMP('2026-01-14 08:54:18.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T08:56:22.733Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsFactorer CHAR(1) DEFAULT ''N'' CHECK (IsFactorer IN (''Y'',''N'')) NOT NULL')
;

-- 2026-01-14T08:59:59.987Z
UPDATE AD_Index_Table SET WhereClause='IsFactorer=''Y''',Updated=TO_TIMESTAMP('2026-01-14 08:59:59.750000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540860
;

-- 2026-01-14T09:02:07.664Z
UPDATE AD_Index_Table SET WhereClause='IsFactorer=''Y'' AND IsActive=''Y''',Updated=TO_TIMESTAMP('2026-01-14 09:02:07.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Index_Table_ID=540860
;

-- 2026-01-14T09:03:20.354Z
CREATE UNIQUE INDEX C_BPartner_IsFactorer_Uniqe ON C_BPartner (IsFactorer,AD_Org_ID) WHERE IsFactorer='Y' AND IsActive='Y'
;

-- Column: C_BPartner.IsFactoring
-- 2026-01-14T12:45:47.059Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591859,584395,0,20,291,'XX','IsFactoring',TO_TIMESTAMP('2026-01-14 12:45:46.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.','D',0,1,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Factoring','',0,0,TO_TIMESTAMP('2026-01-14 12:45:46.465000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-14T12:45:47.150Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591859 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-14T12:45:47.311Z
/* DDL */  select update_Column_Translation_From_AD_Element(584395)
;

-- 2026-01-14T12:47:12.488Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsFactoring CHAR(1) DEFAULT ''N'' CHECK (IsFactoring IN (''Y'',''N'')) NOT NULL')
;

-- Field: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> Factor
-- Column: C_BPartner.IsFactorer
-- 2026-01-14T12:50:42.899Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591857,761012,0,220,0,TO_TIMESTAMP('2026-01-14 12:50:41.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.',0,'D',0,'Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Factor',0,0,440,0,1,1,TO_TIMESTAMP('2026-01-14 12:50:41.844000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T12:50:42.977Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-14T12:50:43.056Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584394)
;

-- 2026-01-14T12:50:43.137Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761012
;

-- 2026-01-14T12:50:43.216Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761012)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Factor
-- Column: C_BPartner.IsFactorer
-- 2026-01-14T12:52:13.648Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761012,0,220,1000011,641335,'F',TO_TIMESTAMP('2026-01-14 12:52:13.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.','Ein Drittunternehmen, das Forderungen (Rechnungen) eines Unternehmens ankauft und sofortige Liquidität bereitstellt, häufig inklusive Forderungsmanagement und Übernahme des Ausfallrisikos.','Y','N','N','Y','N','N','N',0,'Factor',115,0,0,TO_TIMESTAMP('2026-01-14 12:52:13.034000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> Factoring
-- Column: C_BPartner.IsFactoring
-- 2026-01-14T12:54:39.418Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591859,761013,0,223,0,TO_TIMESTAMP('2026-01-14 12:54:38.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.',0,'D',0,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Factoring',0,0,390,0,1,1,TO_TIMESTAMP('2026-01-14 12:54:38.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-14T12:54:39.500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=761013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-14T12:54:39.580Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584395)
;

-- 2026-01-14T12:54:39.664Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=761013
;

-- 2026-01-14T12:54:39.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(761013)
;

-- UI Element: Geschäftspartner_OLD(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Factoring
-- Column: C_BPartner.IsFactoring
-- 2026-01-14T12:55:16.712Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,761013,0,223,540672,641336,'F',TO_TIMESTAMP('2026-01-14 12:55:16.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.','Der Geschäftspartner verkauft seine Forderungen (Rechnungen) an einen Factor, um sofortige Liquidität zu erhalten.','Y','N','N','Y','N','N','N',0,'Factoring',77,0,0,TO_TIMESTAMP('2026-01-14 12:55:16.091000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_BPartner.IsFactoring
-- 2026-01-14T14:36:06.859Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2026-01-14 14:36:06.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591859
;

-- Element: IsFactorer
-- 2026-01-15T10:41:38.632Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Factorer', PrintName='Factorer',Updated=TO_TIMESTAMP('2026-01-15 10:41:38.632000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584394 AND AD_Language='de_DE'
;

-- 2026-01-15T10:41:38.713Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-15T10:41:47.367Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584394,'de_DE')
;

-- 2026-01-15T10:41:47.531Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584394,'de_DE')
;

-- Element: IsFactorer
-- 2026-01-15T10:41:59.276Z
UPDATE AD_Element_Trl SET Name='Factorer', PrintName='Factorer',Updated=TO_TIMESTAMP('2026-01-15 10:41:59.276000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584394 AND AD_Language='en_US'
;

-- 2026-01-15T10:41:59.361Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-15T10:42:08.841Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584394,'en_US')
;

-- Element: IsFactorer
-- 2026-01-15T10:42:23.334Z
UPDATE AD_Element_Trl SET Description='Factoring is a financial transaction and a type of debtor finance in which a business sells its accounts receivable (i.e., invoices) to a third party (called a Factorer).', Help='Factoring is a financial transaction and a type of debtor finance in which a business sells its accounts receivable (i.e., invoices) to a third party (called a Factorer).',Updated=TO_TIMESTAMP('2026-01-15 10:42:23.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584394 AND AD_Language='en_US'
;

-- 2026-01-15T10:42:23.406Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-15T10:42:35.301Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584394,'en_US')
;
