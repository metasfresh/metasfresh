-- 2025-05-07T09:01:04.871Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583613,0,'IsRefund',TO_TIMESTAMP('2025-05-07 12:01:04.739','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Refund','Refund',TO_TIMESTAMP('2025-05-07 12:01:04.739','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-07T09:01:04.872Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583613 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsRefund
-- 2025-05-07T09:01:12.276Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückerstattung', PrintName='Rückerstattung',Updated=TO_TIMESTAMP('2025-05-07 12:01:12.276','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583613 AND AD_Language='de_DE'
;

-- 2025-05-07T09:01:12.278Z
UPDATE AD_Element SET Name='Rückerstattung', PrintName='Rückerstattung' WHERE AD_Element_ID=583613
;

-- 2025-05-07T09:01:12.651Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583613,'de_DE')
;

-- 2025-05-07T09:01:12.654Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583613,'de_DE')
;

-- Element: IsRefund
-- 2025-05-07T09:01:39.423Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückerstattung', PrintName='Rückerstattung',Updated=TO_TIMESTAMP('2025-05-07 12:01:39.423','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583613 AND AD_Language='de_CH'
;

-- 2025-05-07T09:01:39.435Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583613,'de_CH')
;

-- Column: C_Payment.IsRefund
-- 2025-05-07T09:03:17.076Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589956,583613,0,20,335,'IsRefund',TO_TIMESTAMP('2025-05-07 12:03:16.934','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rückerstattung',0,0,TO_TIMESTAMP('2025-05-07 12:03:16.934','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-07T09:03:17.078Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589956 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-07T09:03:17.080Z
/* DDL */  select update_Column_Translation_From_AD_Element(583613)
;

-- 2025-05-07T09:03:17.977Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN IsRefund CHAR(1) DEFAULT ''N'' CHECK (IsRefund IN (''Y'',''N'')) NOT NULL')
;

-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Rückerstattung
-- Column: C_Payment.IsRefund
-- 2025-05-07T09:03:37.302Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589956,742006,0,330,TO_TIMESTAMP('2025-05-07 12:03:37.146','YYYY-MM-DD HH24:MI:SS.US'),100,1,'D','Y','N','N','N','N','N','N','N','Rückerstattung',TO_TIMESTAMP('2025-05-07 12:03:37.146','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-07T09:03:37.303Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742006 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T09:03:37.304Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583613)
;

-- 2025-05-07T09:03:37.307Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742006
;

-- 2025-05-07T09:03:37.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742006)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> dates.Rückerstattung
-- Column: C_Payment.IsRefund
-- 2025-05-07T09:04:50.004Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742006,0,330,540065,631419,'F',TO_TIMESTAMP('2025-05-07 12:04:49.786','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Rückerstattung',70,0,0,TO_TIMESTAMP('2025-05-07 12:04:49.786','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> dates.Rückerstattung
-- Column: C_Payment.IsRefund
-- 2025-05-07T09:05:16.308Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-05-07 12:05:16.308','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631419
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Rechnung
-- Column: C_Payment.C_Invoice_ID
-- 2025-05-07T09:05:16.315Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-05-07 12:05:16.315','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547163
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Payment.DocStatus
-- 2025-05-07T09:05:16.323Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-05-07 12:05:16.323','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541210
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> org.Section Code
-- Column: C_Payment.M_SectionCode_ID
-- 2025-05-07T09:05:16.329Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-05-07 12:05:16.329','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611340
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Payment.Posted
-- 2025-05-07T09:05:16.335Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-05-07 12:05:16.335','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541152
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> org.Sektion
-- Column: C_Payment.AD_Org_ID
-- 2025-05-07T09:05:16.341Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-05-07 12:05:16.341','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547168
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Ursprungszahlung
-- Column: C_Payment.Original_Payment_ID
-- 2025-05-07T09:06:01.341Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.341','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631418
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Rückerstattungsstatus
-- Column: C_Payment.RefundStatus
-- 2025-05-07T09:06:01.347Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631414
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> dates.Rückerstattung
-- Column: C_Payment.IsRefund
-- 2025-05-07T09:06:01.353Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.353','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631419
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Rechnung
-- Column: C_Payment.C_Invoice_ID
-- 2025-05-07T09:06:01.359Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.359','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547163
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Payment.DocStatus
-- 2025-05-07T09:06:01.365Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.365','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541210
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> org.Section Code
-- Column: C_Payment.M_SectionCode_ID
-- 2025-05-07T09:06:01.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.371','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611340
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Payment.Posted
-- 2025-05-07T09:06:01.377Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.377','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541152
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> org.Sektion
-- Column: C_Payment.AD_Org_ID
-- 2025-05-07T09:06:01.382Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-05-07 12:06:01.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547168
;

