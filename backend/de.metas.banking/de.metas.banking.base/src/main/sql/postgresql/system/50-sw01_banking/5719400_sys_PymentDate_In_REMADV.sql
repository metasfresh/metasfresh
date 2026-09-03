-- Column: C_RemittanceAdvice.PaymentDate
-- Column: C_RemittanceAdvice.PaymentDate
-- 2024-03-08T12:01:43.802Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587990,541830,0,15,541573,'XX','PaymentDate',TO_TIMESTAMP('2024-03-08 14:01:43','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,7,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Zahldatum',0,0,TO_TIMESTAMP('2024-03-08 14:01:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-03-08T12:01:43.817Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587990 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-03-08T12:01:43.871Z
/* DDL */  select update_Column_Translation_From_AD_Element(541830) 
;

-- 2024-03-08T12:01:46.816Z
/* DDL */ SELECT public.db_alter_table('C_RemittanceAdvice','ALTER TABLE public.C_RemittanceAdvice ADD COLUMN PaymentDate TIMESTAMP WITHOUT TIME ZONE')
;

-- Field: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV) -> Zahldatum
-- Column: C_RemittanceAdvice.PaymentDate
-- Field: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> Zahldatum
-- Column: C_RemittanceAdvice.PaymentDate
-- 2024-03-08T12:02:03.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587990,726579,0,543319,TO_TIMESTAMP('2024-03-08 14:02:03','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Zahldatum',TO_TIMESTAMP('2024-03-08 14:02:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-08T12:02:03.842Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=726579 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-03-08T12:02:03.846Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541830) 
;

-- 2024-03-08T12:02:03.868Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=726579
;

-- 2024-03-08T12:02:03.877Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(726579)
;

-- UI Element: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV).Zahldatum
-- Column: C_RemittanceAdvice.PaymentDate
-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> main -> 20 -> dates.Zahldatum
-- Column: C_RemittanceAdvice.PaymentDate
-- 2024-03-08T12:02:36.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,726579,0,543319,623754,544782,'F',TO_TIMESTAMP('2024-03-08 14:02:36','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Zahldatum',25,0,0,TO_TIMESTAMP('2024-03-08 14:02:36','YYYY-MM-DD HH24:MI:SS'),100)
;













-- UI Element: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV).Zahldatum
-- Column: C_RemittanceAdvice.PaymentDate
-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> main -> 20 -> dates.Zahldatum
-- Column: C_RemittanceAdvice.PaymentDate
-- 2024-03-08T12:20:28.496Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-03-08 14:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=623754
;

-- UI Element: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV).Remittance amount
-- Column: C_RemittanceAdvice.RemittanceAmt
-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> main -> 10 -> amounts.Remittance amount
-- Column: C_RemittanceAdvice.RemittanceAmt
-- 2024-03-08T12:20:28.505Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-03-08 14:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576406
;

-- UI Element: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV).Currency
-- Column: C_RemittanceAdvice.RemittanceAmt_Currency_ID
-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> main -> 10 -> amounts.Currency
-- Column: C_RemittanceAdvice.RemittanceAmt_Currency_ID
-- 2024-03-08T12:20:28.513Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-03-08 14:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576407
;

-- UI Element: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV).Service fee amount
-- Column: C_RemittanceAdvice.ServiceFeeAmount
-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> main -> 10 -> amounts.Service fee amount
-- Column: C_RemittanceAdvice.ServiceFeeAmount
-- 2024-03-08T12:20:28.521Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-03-08 14:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576408
;

-- UI Element: Zahlungsavis (REMADV) -> Zahlungsavis (REMADV).Service fee currency
-- Column: C_RemittanceAdvice.ServiceFeeAmount_Currency_ID
-- UI Element: Zahlungsavis (REMADV)(541023,D) -> Zahlungsavis (REMADV)(543319,D) -> main -> 10 -> amounts.Service fee currency
-- Column: C_RemittanceAdvice.ServiceFeeAmount_Currency_ID
-- 2024-03-08T12:20:28.528Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-03-08 14:20:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=576409
;




