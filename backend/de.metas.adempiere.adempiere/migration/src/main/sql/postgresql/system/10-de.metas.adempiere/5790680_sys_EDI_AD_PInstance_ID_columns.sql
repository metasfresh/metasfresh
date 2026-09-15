-- Run mode: SWING_CLIENT

-- 2026-02-26T19:07:04.587Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584593,0,'EDI_AD_PInstance_ID',TO_TIMESTAMP('2026-02-26 19:07:04.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.esb.edi','Y','EDI Prozess Instanz','EDI Prozess Instanz',TO_TIMESTAMP('2026-02-26 19:07:04.449000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-26T19:07:04.599Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584593 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: EDI_AD_PInstance_ID
-- 2026-02-26T19:07:24.404Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='EDI Process Instance', PrintName='EDI Process Instance',Updated=TO_TIMESTAMP('2026-02-26 19:07:24.402000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584593 AND AD_Language='en_US'
;

-- 2026-02-26T19:07:24.411Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-26T19:07:25.524Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584593,'en_US')
;

-- Element: EDI_AD_PInstance_ID
-- 2026-02-26T19:07:26.922Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-26 19:07:26.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584593 AND AD_Language='de_CH'
;

-- 2026-02-26T19:07:26.930Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584593,'de_CH')
;

-- Element: EDI_AD_PInstance_ID
-- 2026-02-26T19:07:30.921Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-26 19:07:30.921000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584593 AND AD_Language='de_DE'
;

-- 2026-02-26T19:07:30.925Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584593,'de_DE')
;

-- 2026-02-26T19:07:30.928Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584593,'de_DE')
;

-- Column: M_InOut.EDI_AD_PInstance_ID
-- 2026-02-26T19:09:20.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592092,584593,0,18,540169,319,'XX','EDI_AD_PInstance_ID',TO_TIMESTAMP('2026-02-26 19:09:20.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'EDI Prozess Instanz',0,0,TO_TIMESTAMP('2026-02-26 19:09:20.310000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-26T19:09:20.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592092 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-26T19:09:20.461Z
/* DDL */  select update_Column_Translation_From_AD_Element(584593)
;

-- 2026-02-26T19:09:22.375Z
/* DDL */ SELECT public.db_alter_table('M_InOut','ALTER TABLE public.M_InOut ADD COLUMN EDI_AD_PInstance_ID NUMERIC(10)')
;

-- 2026-02-26T19:09:23.157Z
ALTER TABLE M_InOut ADD CONSTRAINT EDIADPInstance_MInOut FOREIGN KEY (EDI_AD_PInstance_ID) REFERENCES public.AD_PInstance DEFERRABLE INITIALLY DEFERRED
;

CREATE INDEX IF NOT EXISTS m_inout_ediadpinstance_id
    ON m_inout (EDI_AD_PInstance_ID)
    WHERE isActive = 'Y'
;

-- Column: C_Invoice.EDI_AD_PInstance_ID
-- 2026-02-26T19:18:47.515Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592093,584593,0,18,540169,318,'XX','EDI_AD_PInstance_ID',TO_TIMESTAMP('2026-02-26 19:18:47.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'EDI Prozess Instanz',0,0,TO_TIMESTAMP('2026-02-26 19:18:47.374000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-26T19:18:47.518Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592093 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-26T19:18:47.703Z
/* DDL */  select update_Column_Translation_From_AD_Element(584593)
;

-- 2026-02-26T19:18:49.509Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN EDI_AD_PInstance_ID NUMERIC(10)')
;

-- 2026-02-26T19:18:50.509Z
ALTER TABLE C_Invoice ADD CONSTRAINT EDIADPInstance_CInvoice FOREIGN KEY (EDI_AD_PInstance_ID) REFERENCES public.AD_PInstance DEFERRABLE INITIALLY DEFERRED
;

CREATE INDEX IF NOT EXISTS c_invoice_ediadpinstance_id
    ON c_invoice (EDI_AD_PInstance_ID)
WHERE isActive = 'Y'
;

-- Column: C_Invoice.EDI_AD_PInstance_ID
-- 2026-02-27T12:51:29.644Z
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2026-02-27 12:51:29.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592093
;

-- Column: M_InOut.EDI_AD_PInstance_ID
-- 2026-02-27T12:52:08.706Z
UPDATE AD_Column SET PersonalDataCategory='NP',Updated=TO_TIMESTAMP('2026-02-27 12:52:08.706000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592092
;
