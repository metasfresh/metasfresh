
-- 2025-05-06T12:16:35.563Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583608,0,'RefundStatus',TO_TIMESTAMP('2025-05-06 15:16:35.32','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Refund Status','Refund Status',TO_TIMESTAMP('2025-05-06 15:16:35.32','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-06T12:16:35.566Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583608 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: RefundStatus
-- 2025-05-06T13:04:48.407Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückerstattungsstatus', PrintName='Rückerstattungsstatus',Updated=TO_TIMESTAMP('2025-05-06 16:04:48.407','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583608 AND AD_Language='de_CH'
;

-- 2025-05-06T13:04:48.451Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583608,'de_CH')
;

-- Element: RefundStatus
-- 2025-05-06T13:04:52.354Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Rückerstattungsstatus', PrintName='Rückerstattungsstatus',Updated=TO_TIMESTAMP('2025-05-06 16:04:52.354','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583608 AND AD_Language='de_DE'
;

-- 2025-05-06T13:04:52.355Z
UPDATE AD_Element SET Name='Rückerstattungsstatus', PrintName='Rückerstattungsstatus' WHERE AD_Element_ID=583608
;

-- 2025-05-06T13:04:52.778Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583608,'de_DE')
;

-- 2025-05-06T13:04:52.782Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583608,'de_DE')
;

-- Name: Refund Status
-- 2025-05-06T13:07:58.762Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541943,TO_TIMESTAMP('2025-05-06 16:07:58.636','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','Refund Status',TO_TIMESTAMP('2025-05-06 16:07:58.636','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2025-05-06T13:07:58.764Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541943 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Refund Status
-- Value: Scheduled for Refund
-- ValueName: Scheduled for Refund
-- 2025-05-06T13:08:57.746Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541943,543903,TO_TIMESTAMP('2025-05-06 16:08:57.622','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Scheduled for Refund',TO_TIMESTAMP('2025-05-06 16:08:57.622','YYYY-MM-DD HH24:MI:SS.US'),100,'Scheduled for Refund','Scheduled for Refund')
;

-- 2025-05-06T13:08:57.748Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543903 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Refund Status
-- Value: Refunded
-- ValueName: Refunded
-- 2025-05-06T13:10:36.170Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541943,543904,TO_TIMESTAMP('2025-05-06 16:10:35.94','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Refunded',TO_TIMESTAMP('2025-05-06 16:10:35.94','YYYY-MM-DD HH24:MI:SS.US'),100,'Refunded','Refunded')
;

-- 2025-05-06T13:10:36.176Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543904 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Refund Status -> Scheduled for Refund_Scheduled for Refund
-- 2025-05-06T13:10:40.518Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Zur Rückerstattung vorgemerkt',Updated=TO_TIMESTAMP('2025-05-06 16:10:40.518','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543903
;

-- Reference Item: Refund Status -> Scheduled for Refund_Scheduled for Refund
-- 2025-05-06T13:10:47.995Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Zur Rückerstattung vorgemerkt',Updated=TO_TIMESTAMP('2025-05-06 16:10:47.995','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543903
;

-- 2025-05-06T13:10:47.996Z
UPDATE AD_Ref_List SET Name='Zur Rückerstattung vorgemerkt' WHERE AD_Ref_List_ID=543903
;

-- Reference Item: Refund Status -> Refunded_Refunded
-- 2025-05-06T13:11:37.543Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erstattet',Updated=TO_TIMESTAMP('2025-05-06 16:11:37.543','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543904
;

-- Reference Item: Refund Status -> Refunded_Refunded
-- 2025-05-06T13:11:42.784Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Erstattet',Updated=TO_TIMESTAMP('2025-05-06 16:11:42.784','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543904
;

-- 2025-05-06T13:11:42.785Z
UPDATE AD_Ref_List SET Name='Erstattet' WHERE AD_Ref_List_ID=543904
;

-- Name: Refund Status
-- 2025-05-06T13:12:10.351Z
UPDATE AD_Reference SET Description='Indicates the refund processing status of the payment.',Updated=TO_TIMESTAMP('2025-05-06 16:12:10.349','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541943
;

-- 2025-05-06T13:12:10.355Z
UPDATE AD_Reference_Trl trl SET Description='Indicates the refund processing status of the payment.' WHERE AD_Reference_ID=541943 AND AD_Language='de_DE'
;

-- 2025-05-06T13:12:19.015Z
UPDATE AD_Reference_Trl SET Description='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', Help='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-06 16:12:19.006','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541943
;

-- Name: Refund Status
-- 2025-05-06T13:12:32.067Z
UPDATE AD_Reference SET Help='Indicates the refund processing status of the payment.',Updated=TO_TIMESTAMP('2025-05-06 16:12:32.065','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541943
;

-- 2025-05-06T13:12:32.068Z
UPDATE AD_Reference_Trl trl SET Help='Indicates the refund processing status of the payment.' WHERE AD_Reference_ID=541943 AND AD_Language='de_DE'
;

-- 2025-05-06T13:12:36.630Z
UPDATE AD_Reference_Trl SET Description='Indicates the refund processing status of the payment.', Help='Indicates the refund processing status of the payment.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-06 16:12:36.629','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541943
;

-- 2025-05-06T13:12:52.782Z
UPDATE AD_Reference_Trl SET Description='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', Help='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-06 16:12:52.78','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541943
;

-- 2025-05-06T13:12:52.783Z
UPDATE AD_Reference SET Description='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', Help='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.' WHERE AD_Reference_ID=541943
;

-- Column: C_Payment.RefundStatus
-- 2025-05-06T13:13:22.577Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589929,583608,0,17,541943,335,'RefundStatus',TO_TIMESTAMP('2025-05-06 16:13:22.348','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,250,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rückerstattungsstatus',0,0,TO_TIMESTAMP('2025-05-06 16:13:22.348','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-06T13:13:22.579Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589929 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-06T13:13:22.583Z
/* DDL */  select update_Column_Translation_From_AD_Element(583608)
;

-- 2025-05-06T13:13:23.696Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN RefundStatus VARCHAR(250)')
;

-- Element: RefundStatus
-- 2025-05-06T13:15:17.521Z
UPDATE AD_Element_Trl SET Description='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', Help='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.',Updated=TO_TIMESTAMP('2025-05-06 16:15:17.521','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583608 AND AD_Language='de_DE'
;

-- 2025-05-06T13:15:17.522Z
UPDATE AD_Element SET Description='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', Help='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.' WHERE AD_Element_ID=583608
;

-- 2025-05-06T13:15:17.946Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583608,'de_DE')
;

-- 2025-05-06T13:15:17.947Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583608,'de_DE')
;

-- Element: RefundStatus
-- 2025-05-06T13:15:33.004Z
UPDATE AD_Element_Trl SET Description='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.', Help='Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.',Updated=TO_TIMESTAMP('2025-05-06 16:15:33.004','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583608 AND AD_Language='de_CH'
;

-- 2025-05-06T13:15:33.017Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583608,'de_CH')
;

-- Element: RefundStatus
-- 2025-05-06T13:15:45.105Z
UPDATE AD_Element_Trl SET Description='Indicates the refund processing status of the payment.', Help='Indicates the refund processing status of the payment.', IsTranslated='Y',Updated=TO_TIMESTAMP('2025-05-06 16:15:45.105','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583608 AND AD_Language='en_US'
;

-- 2025-05-06T13:15:45.107Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583608,'en_US')
;


-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Rückerstattungsstatus
-- Column: C_Payment.RefundStatus
-- 2025-05-06T16:17:34.656Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589929,742002,0,330,TO_TIMESTAMP('2025-05-06 19:17:34.182','YYYY-MM-DD HH24:MI:SS.US'),100,'Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.',250,'D','Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.','Y','N','N','N','N','N','N','N','Rückerstattungsstatus',TO_TIMESTAMP('2025-05-06 19:17:34.182','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-06T16:17:34.665Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742002 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-06T16:17:34.676Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583608)
;

-- 2025-05-06T16:17:34.748Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742002
;

-- 2025-05-06T16:17:34.757Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742002)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Rückerstattungsstatus
-- Column: C_Payment.RefundStatus
-- 2025-05-06T16:18:56.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742002,0,330,540955,631414,'F',TO_TIMESTAMP('2025-05-06 19:18:56.277','YYYY-MM-DD HH24:MI:SS.US'),100,'Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.','Gibt den Status der Rückerstattungsbearbeitung der Zahlung an.','Y','N','Y','N','N','Rückerstattungsstatus',50,0,0,TO_TIMESTAMP('2025-05-06 19:18:56.277','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Rückerstattungsstatus
-- Column: C_Payment.RefundStatus
-- 2025-05-06T16:19:42.871Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-05-06 19:19:42.871','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631414
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Rechnung
-- Column: C_Payment.C_Invoice_ID
-- 2025-05-06T16:19:42.879Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-05-06 19:19:42.878','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547163
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> dates.Belegstatus
-- Column: C_Payment.DocStatus
-- 2025-05-06T16:19:42.886Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-05-06 19:19:42.886','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541210
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> org.Section Code
-- Column: C_Payment.M_SectionCode_ID
-- 2025-05-06T16:19:42.894Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-05-06 19:19:42.893','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=611340
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> posted.Verbucht
-- Column: C_Payment.Posted
-- 2025-05-06T16:19:42.903Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-05-06 19:19:42.903','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=541152
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 20 -> org.Sektion
-- Column: C_Payment.AD_Org_ID
-- 2025-05-06T16:19:42.910Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-05-06 19:19:42.91','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547168
;





-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Rückerstattungsstatus
-- Column: C_Payment.RefundStatus
-- 2025-05-07T07:35:25.288Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-05-07 10:35:25.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=742002
;



