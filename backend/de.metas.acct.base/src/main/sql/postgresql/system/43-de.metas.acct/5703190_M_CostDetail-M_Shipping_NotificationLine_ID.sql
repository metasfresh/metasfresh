-- Run mode: SWING_CLIENT

-- Column: M_CostDetail.M_Shipping_NotificationLine_ID
-- 2023-09-18T11:10:05.912Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587479,582701,0,30,808,'M_Shipping_NotificationLine_ID',TO_TIMESTAMP('2023-09-18 14:10:05.731','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.shippingnotification',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Shipping Notification Line',0,0,TO_TIMESTAMP('2023-09-18 14:10:05.731','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-18T11:10:05.920Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-18T11:10:05.955Z
/* DDL */  select update_Column_Translation_From_AD_Element(582701)
;

-- 2023-09-18T11:10:16.198Z
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN M_Shipping_NotificationLine_ID NUMERIC(10)')
;

-- 2023-09-18T11:10:16.394Z
ALTER TABLE M_CostDetail ADD CONSTRAINT MShippingNotificationLine_MCostDetail FOREIGN KEY (M_Shipping_NotificationLine_ID) REFERENCES public.M_Shipping_NotificationLine DEFERRABLE INITIALLY DEFERRED
;

-- Column: M_CostDetail.M_Shipping_NotificationLine_ID
-- 2023-09-18T11:14:10.605Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2023-09-18 14:14:10.605','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587479
;

-- Field: Produktkosten(344,D) -> Kostendetails(748,D) -> Shipping Notification Line
-- Column: M_CostDetail.M_Shipping_NotificationLine_ID
-- 2023-09-18T11:22:55.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587479,720492,0,748,TO_TIMESTAMP('2023-09-18 14:22:55.422','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Shipping Notification Line',TO_TIMESTAMP('2023-09-18 14:22:55.422','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-18T11:22:55.651Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720492 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-18T11:22:55.653Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582701)
;

-- 2023-09-18T11:22:55.666Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720492
;

-- 2023-09-18T11:22:55.669Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720492)
;

-- UI Element: Produktkosten(344,D) -> Kostendetails(748,D) -> main -> 20 -> Reference.Shipping Notification Line
-- Column: M_CostDetail.M_Shipping_NotificationLine_ID
-- 2023-09-18T11:27:52.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720492,0,748,544599,620482,'F',TO_TIMESTAMP('2023-09-18 14:27:52.483','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Shipping Notification Line',100,0,0,TO_TIMESTAMP('2023-09-18 14:27:52.483','YYYY-MM-DD HH24:MI:SS.US'),100)
;

