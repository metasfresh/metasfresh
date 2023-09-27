-- Run mode: SWING_CLIENT

-- Column: M_Shipping_Notification.PostingError_Issue_ID
-- 2023-09-18T14:01:49.901Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587481,577755,0,30,540991,542365,'PostingError_Issue_ID',TO_TIMESTAMP('2023-09-18 17:01:49.597','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Verbuchungsfehler',0,0,TO_TIMESTAMP('2023-09-18 17:01:49.597','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-18T14:01:49.912Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-18T14:01:49.950Z
/* DDL */  select update_Column_Translation_From_AD_Element(577755)
;

-- 2023-09-18T14:01:53.208Z
/* DDL */ SELECT public.db_alter_table('M_Shipping_Notification','ALTER TABLE public.M_Shipping_Notification ADD COLUMN PostingError_Issue_ID NUMERIC(10)')
;

-- 2023-09-18T14:01:53.286Z
ALTER TABLE M_Shipping_Notification ADD CONSTRAINT PostingErrorIssue_MShippingNotification FOREIGN KEY (PostingError_Issue_ID) REFERENCES public.AD_Issue DEFERRABLE INITIALLY DEFERRED
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> Verbuchungsfehler
-- Column: M_Shipping_Notification.PostingError_Issue_ID
-- 2023-09-18T14:02:32.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587481,720495,0,547218,TO_TIMESTAMP('2023-09-18 17:02:32.505','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.shippingnotification','Y','N','N','N','N','N','N','N','Verbuchungsfehler',TO_TIMESTAMP('2023-09-18 17:02:32.505','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-18T14:02:32.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720495 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-18T14:02:32.708Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577755)
;

-- 2023-09-18T14:02:32.722Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720495
;

-- 2023-09-18T14:02:32.724Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720495)
;

-- UI Column: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20
-- UI Element Group: accounting
-- 2023-09-18T14:03:06.639Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547077,551137,TO_TIMESTAMP('2023-09-18 17:03:06.451','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','accounting',40,TO_TIMESTAMP('2023-09-18 17:03:06.451','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20 -> accounting.Buchungsdatum
-- Column: M_Shipping_Notification.DateAcct
-- 2023-09-18T14:03:33.731Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551137, IsDisplayed='Y', SeqNo=10,Updated=TO_TIMESTAMP('2023-09-18 17:03:33.731','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620447
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20 -> accounting.Buchungsstatus
-- Column: M_Shipping_Notification.Posted
-- 2023-09-18T14:04:05.989Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720426,0,547218,551137,620485,'F',TO_TIMESTAMP('2023-09-18 17:04:05.802','YYYY-MM-DD HH24:MI:SS.US'),100,'Buchungsstatus','Zeigt den Verbuchungsstatus der Hauptbuchpositionen an.','Y','N','Y','N','N','Buchungsstatus',20,0,0,TO_TIMESTAMP('2023-09-18 17:04:05.802','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20 -> accounting.Verbuchungsfehler
-- Column: M_Shipping_Notification.PostingError_Issue_ID
-- 2023-09-18T14:04:25.483Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720495,0,547218,551137,620486,'F',TO_TIMESTAMP('2023-09-18 17:04:25.306','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Verbuchungsfehler',30,0,0,TO_TIMESTAMP('2023-09-18 17:04:25.306','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> Verbuchungsfehler
-- Column: M_Shipping_Notification.PostingError_Issue_ID
-- 2023-09-18T14:04:54.296Z
UPDATE AD_Field SET DisplayLogic='@PostingError_Issue_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-09-18 17:04:54.296','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720495
;

-- Field: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> Buchungsstatus
-- Column: M_Shipping_Notification.Posted
-- 2023-09-18T14:05:28.899Z
UPDATE AD_Field SET DisplayLogic='@Posted/N@!N', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-09-18 17:05:28.899','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720426
;

-- UI Column: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20
-- UI Element Group: org
-- 2023-09-18T14:06:10.712Z
UPDATE AD_UI_ElementGroup SET SeqNo=40,Updated=TO_TIMESTAMP('2023-09-18 17:06:10.712','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551135
;

-- UI Column: Lieferavis(541734,de.metas.shippingnotification) -> Shipping Notification(547218,de.metas.shippingnotification) -> main -> 20
-- UI Element Group: accounting
-- 2023-09-18T14:06:14.221Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2023-09-18 17:06:14.221','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=551137
;

