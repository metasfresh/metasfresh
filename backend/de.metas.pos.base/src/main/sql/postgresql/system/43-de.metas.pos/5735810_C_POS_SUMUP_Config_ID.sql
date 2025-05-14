-- Reference: POSPaymentProcessor
-- Value: sumup
-- ValueName: SumUp
-- 2024-10-04T11:39:19.066Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541896,543735,TO_TIMESTAMP('2024-10-04 14:39:18','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.payment.sumup','Y','SumUp',TO_TIMESTAMP('2024-10-04 14:39:18','YYYY-MM-DD HH24:MI:SS'),100,'sumup','SumUp')
;

-- 2024-10-04T11:39:19.068Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543735 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_POS.SUMUP_Config_ID
-- Column: C_POS.SUMUP_Config_ID
-- 2024-10-04T11:39:37.831Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589239,583297,0,30,748,'XX','SUMUP_Config_ID',TO_TIMESTAMP('2024-10-04 14:39:37','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.payment.sumup',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SumUp Configuration',0,0,TO_TIMESTAMP('2024-10-04 14:39:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-04T11:39:37.833Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589239 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-04T11:39:37.837Z
/* DDL */  select update_Column_Translation_From_AD_Element(583297) 
;

-- 2024-10-04T11:39:56.313Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE public.C_POS ADD COLUMN SUMUP_Config_ID NUMERIC(10)')
;

-- 2024-10-04T11:39:56.321Z
ALTER TABLE C_POS ADD CONSTRAINT SUMUPConfig_CPOS FOREIGN KEY (SUMUP_Config_ID) REFERENCES public.SUMUP_Config DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_POS.SUMUP_Config_ID
-- Column: C_POS.SUMUP_Config_ID
-- 2024-10-04T11:40:03.448Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-10-04 14:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589239
;

-- Field: POS-Terminal -> POS-Terminal -> SumUp Configuration
-- Column: C_POS.SUMUP_Config_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> SumUp Configuration
-- Column: C_POS.SUMUP_Config_ID
-- 2024-10-04T11:40:10.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589239,731842,0,676,TO_TIMESTAMP('2024-10-04 14:40:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','SumUp Configuration',TO_TIMESTAMP('2024-10-04 14:40:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-04T11:40:10.840Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731842 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-04T11:40:10.842Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583297) 
;

-- 2024-10-04T11:40:10.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731842
;

-- 2024-10-04T11:40:10.849Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731842)
;

-- UI Element: POS-Terminal -> POS-Terminal.SumUp Configuration
-- Column: C_POS.SUMUP_Config_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> card payments.SumUp Configuration
-- Column: C_POS.SUMUP_Config_ID
-- 2024-10-04T11:40:30.694Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731842,0,676,552034,626144,'F',TO_TIMESTAMP('2024-10-04 14:40:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','SumUp Configuration',20,0,0,TO_TIMESTAMP('2024-10-04 14:40:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_POS.SUMUP_Config_ID
-- Column: C_POS.SUMUP_Config_ID
-- 2024-10-04T11:41:41.679Z
UPDATE AD_Column SET MandatoryLogic='@POSPaymentProcessor/X@=sumup',Updated=TO_TIMESTAMP('2024-10-04 14:41:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589239
;

-- Field: POS-Terminal -> POS-Terminal -> SumUp Configuration
-- Column: C_POS.SUMUP_Config_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> SumUp Configuration
-- Column: C_POS.SUMUP_Config_ID
-- 2024-10-04T11:41:44.335Z
UPDATE AD_Field SET DisplayLogic='@POSPaymentProcessor/X@=sumup',Updated=TO_TIMESTAMP('2024-10-04 14:41:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=731842
;

-- Reference: POSPaymentProcessor
-- Value: sumup
-- ValueName: SumUp
-- 2024-10-04T11:42:46.978Z
UPDATE AD_Ref_List SET EntityType='de.metas.pos',Updated=TO_TIMESTAMP('2024-10-04 14:42:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543735
;

-- Reference: POSPaymentProcessor
-- Value: sumup
-- ValueName: SumUp
-- 2024-10-04T11:43:23.934Z
UPDATE AD_Ref_List SET EntityType='de.metas.payment.sumup',Updated=TO_TIMESTAMP('2024-10-04 14:43:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543735
;

-- Column: C_POS.POSPaymentProcessor
-- Column: C_POS.POSPaymentProcessor
-- 2024-10-04T11:44:30.106Z
UPDATE AD_Column SET IsForceIncludeInGeneratedModel='Y',Updated=TO_TIMESTAMP('2024-10-04 14:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=589238
;

-- 2024-10-04T11:44:33.241Z
INSERT INTO t_alter_column values('c_pos','POSPaymentProcessor','VARCHAR(40)',null,null)
;

