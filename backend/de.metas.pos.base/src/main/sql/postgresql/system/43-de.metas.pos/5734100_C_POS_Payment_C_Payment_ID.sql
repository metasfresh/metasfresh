-- Column: C_POS_Payment.C_Payment_ID
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-09-20T19:00:44.078Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589121,1384,0,30,542437,'XX','C_Payment_ID',TO_TIMESTAMP('2024-09-20 22:00:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Zahlung','de.metas.pos',0,10,'Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahlung',0,0,TO_TIMESTAMP('2024-09-20 22:00:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-09-20T19:00:44.086Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589121 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-20T19:00:44.091Z
/* DDL */  select update_Column_Translation_From_AD_Element(1384) 
;

-- 2024-09-20T19:00:44.853Z
/* DDL */ SELECT public.db_alter_table('C_POS_Payment','ALTER TABLE public.C_POS_Payment ADD COLUMN C_Payment_ID NUMERIC(10)')
;

-- 2024-09-20T19:00:44.871Z
ALTER TABLE C_POS_Payment ADD CONSTRAINT CPayment_CPOSPayment FOREIGN KEY (C_Payment_ID) REFERENCES public.C_Payment DEFERRABLE INITIALLY DEFERRED
;

-- Field: POS Order -> POS Payment -> Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- Field: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-09-20T19:13:52.386Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589121,730914,0,547593,TO_TIMESTAMP('2024-09-20 22:13:52','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung',10,'de.metas.pos','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).','Y','N','N','N','N','N','N','N','Zahlung',TO_TIMESTAMP('2024-09-20 22:13:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-09-20T19:13:52.391Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=730914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-20T19:13:52.395Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1384) 
;

-- 2024-09-20T19:13:52.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=730914
;

-- 2024-09-20T19:13:52.444Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(730914)
;

-- UI Section: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main
-- UI Column: 20
-- 2024-09-20T19:14:39.678Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547554,546176,TO_TIMESTAMP('2024-09-20 22:14:39','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2024-09-20 22:14:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20
-- UI Element Group: receipt
-- 2024-09-20T19:14:54.199Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547554,551964,TO_TIMESTAMP('2024-09-20 22:14:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','receipt',10,TO_TIMESTAMP('2024-09-20 22:14:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> receipt.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-09-20T19:15:10.415Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,730914,0,547593,551964,625769,'F',TO_TIMESTAMP('2024-09-20 22:15:10','YYYY-MM-DD HH24:MI:SS'),100,'Zahlung','Bei einer Zahlung handelt es sich um einen Zahlungseingang oder Zahlungsausgang (Bar, Bank, Kreditkarte).','Y','N','Y','N','N','Zahlung',10,0,0,TO_TIMESTAMP('2024-09-20 22:15:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS Order -> POS Payment.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- UI Element: POS Order(541818,de.metas.pos) -> POS Payment(547593,de.metas.pos) -> main -> 20 -> receipt.Zahlung
-- Column: C_POS_Payment.C_Payment_ID
-- 2024-09-20T19:15:19.159Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-09-20 22:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=625769
;

