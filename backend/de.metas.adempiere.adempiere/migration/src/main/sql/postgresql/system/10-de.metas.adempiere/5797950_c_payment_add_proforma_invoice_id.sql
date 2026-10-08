-- Run mode: SWING_CLIENT

-- 2026-04-14T06:28:41.235Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584758,0,'Proforma_Invoice_ID',TO_TIMESTAMP('2026-04-14 06:28:41.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Proforma Rechnung','Proforma Rechnung',TO_TIMESTAMP('2026-04-14 06:28:41.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-14T06:28:41.249Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584758 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Proforma_Invoice_ID
-- 2026-04-14T06:29:01.449Z
UPDATE AD_Element_Trl SET IsTranslated='Y', PrintName='Proforma Invoice',Updated=TO_TIMESTAMP('2026-04-14 06:29:01.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584758 AND AD_Language='en_US'
;

-- 2026-04-14T06:29:01.450Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-14T06:29:01.784Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584758,'en_US')
;

-- Element: Proforma_Invoice_ID
-- 2026-04-14T06:29:02.834Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-14 06:29:02.834000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584758 AND AD_Language='de_CH'
;

-- 2026-04-14T06:29:02.837Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584758,'de_CH')
;

-- Element: Proforma_Invoice_ID
-- 2026-04-14T06:29:05.210Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-04-14 06:29:05.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584758 AND AD_Language='de_DE'
;

-- 2026-04-14T06:29:05.213Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584758,'de_DE')
;

-- 2026-04-14T06:29:05.214Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584758,'de_DE')
;

-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-14T06:37:32.678Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592361,584758,0,18,336,335,'XX','Proforma_Invoice_ID',TO_TIMESTAMP('2026-04-14 06:37:32.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Proforma Rechnung','1=1',0,0,TO_TIMESTAMP('2026-04-14 06:37:32.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-04-14T06:37:32.680Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592361 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-14T06:37:32.685Z
/* DDL */  select update_Column_Translation_From_AD_Element(584758)
;

-- 2026-04-14T06:37:35.082Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN Proforma_Invoice_ID NUMERIC(10)')
;

-- 2026-04-14T06:37:36.484Z
ALTER TABLE C_Payment ADD CONSTRAINT ProformaInvoice_CPayment FOREIGN KEY (Proforma_Invoice_ID) REFERENCES public.C_Invoice DEFERRABLE INITIALLY DEFERRED
;

-- NOTE (iter2 cherry-pick): AD_Field, AD_Field_Trl, and AD_UI_Element blocks were pruned.
-- Iter 2 scope is payment-tagging only (column + FK); window-decoration changes are deferred to Phase 3.
-- AD_UI_ElementGroup 540955 was created by a different migration in PR 23407 that is not cherry-picked here.

-- Column: C_Payment.Proforma_Invoice_ID
-- 2026-04-14T06:40:39.998Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2026-04-14 06:40:39.998000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592361
;

