-- Run mode: SWING_CLIENT

-- Column: C_ElementValue.C_CostClassification_ID
-- 2025-12-22T14:09:43.026Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591816,584379,0,19,188,'XX','C_CostClassification_ID',TO_TIMESTAMP('2025-12-22 14:09:42.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenklassifizierung',0,0,TO_TIMESTAMP('2025-12-22 14:09:42.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-22T14:09:43.042Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591816 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-22T14:09:43.267Z
/* DDL */  select update_Column_Translation_From_AD_Element(584379)
;

-- 2025-12-22T14:09:45.304Z
/* DDL */ SELECT public.db_alter_table('C_ElementValue','ALTER TABLE public.C_ElementValue ADD COLUMN C_CostClassification_ID NUMERIC(10)')
;

-- 2025-12-22T14:09:45.653Z
ALTER TABLE C_ElementValue ADD CONSTRAINT CCostClassification_CElementValue FOREIGN KEY (C_CostClassification_ID) REFERENCES public.C_CostClassification DEFERRABLE INITIALLY DEFERRED
;

-- Column: Fact_Acct.C_CostClassification_ID
-- 2025-12-22T14:11:58.060Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591817,584379,0,19,270,'XX','C_CostClassification_ID',TO_TIMESTAMP('2025-12-22 14:11:57.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenklassifizierung',0,0,TO_TIMESTAMP('2025-12-22 14:11:57.887000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-22T14:11:58.061Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-22T14:11:58.243Z
/* DDL */  select update_Column_Translation_From_AD_Element(584379)
;

-- 2025-12-22T14:11:59.437Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN C_CostClassification_ID NUMERIC(10)')
;

-- 2025-12-22T14:11:59.761Z
ALTER TABLE Fact_Acct ADD CONSTRAINT CCostClassification_FactAcct FOREIGN KEY (C_CostClassification_ID) REFERENCES public.C_CostClassification DEFERRABLE INITIALLY DEFERRED
;

-- Column: Fact_Acct.C_CostClassification_Category_ID
-- 2025-12-22T14:24:01.817Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591818,584374,0,19,270,'XX','C_CostClassification_Category_ID',TO_TIMESTAMP('2025-12-22 14:24:01.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenartengruppe',0,0,TO_TIMESTAMP('2025-12-22 14:24:01.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-22T14:24:01.819Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-22T14:24:01.821Z
/* DDL */  select update_Column_Translation_From_AD_Element(584374)
;

-- 2025-12-22T14:24:03.105Z
/* DDL */ SELECT public.db_alter_table('Fact_Acct','ALTER TABLE public.Fact_Acct ADD COLUMN C_CostClassification_Category_ID NUMERIC(10)')
;

-- 2025-12-22T14:24:03.446Z
ALTER TABLE Fact_Acct ADD CONSTRAINT CCostClassificationCategory_FactAcct FOREIGN KEY (C_CostClassification_Category_ID) REFERENCES public.C_CostClassification_Category DEFERRABLE INITIALLY DEFERRED
;

-- Run mode: SWING_CLIENT

-- Column: Fact_Acct_Transactions_View.C_CostClassification_ID
-- 2025-12-22T15:32:21.572Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591819,584379,0,19,541485,'XX','C_CostClassification_ID',TO_TIMESTAMP('2025-12-22 15:32:21.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenklassifizierung',0,0,TO_TIMESTAMP('2025-12-22 15:32:21.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-22T15:32:21.584Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591819 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-22T15:32:21.834Z
/* DDL */  select update_Column_Translation_From_AD_Element(584379)
;

-- 2025-12-22T15:32:36.182Z
INSERT INTO t_alter_column values('fact_acct_transactions_view','C_CostClassification_ID','NUMERIC(10)',null,null)
;

-- Column: Fact_Acct_Transactions_View.C_CostClassification_Category_ID
-- 2025-12-22T15:33:30.125Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591820,584374,0,19,541485,'XX','C_CostClassification_Category_ID',TO_TIMESTAMP('2025-12-22 15:33:29.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Kostenartengruppe',0,0,TO_TIMESTAMP('2025-12-22 15:33:29.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-12-22T15:33:30.127Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591820 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-12-22T15:33:30.130Z
/* DDL */  select update_Column_Translation_From_AD_Element(584374)
;

-- 2025-12-22T15:33:31.296Z
INSERT INTO t_alter_column values('fact_acct_transactions_view','C_CostClassification_Category_ID','NUMERIC(10)',null,null)
;

-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Kostenklassifizierung
-- Column: Fact_Acct_Transactions_View.C_CostClassification_ID
-- 2025-12-22T15:33:47.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591819,760962,0,242,TO_TIMESTAMP('2025-12-22 15:33:46.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.',10,'D','Y','N','N','N','N','N','N','N','Kostenklassifizierung',TO_TIMESTAMP('2025-12-22 15:33:46.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-22T15:33:47.118Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760962 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-22T15:33:47.121Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584379)
;

-- 2025-12-22T15:33:47.145Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760962
;

-- 2025-12-22T15:33:47.150Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760962)
;

-- Field: Buchführungs-Details(162,D) -> Buchführung(242,D) -> Kostenartengruppe
-- Column: Fact_Acct_Transactions_View.C_CostClassification_Category_ID
-- 2025-12-22T15:33:56.249Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591820,760963,0,242,TO_TIMESTAMP('2025-12-22 15:33:56.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.',10,'D','Y','N','N','N','N','N','N','N','Kostenartengruppe',TO_TIMESTAMP('2025-12-22 15:33:56.093000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-22T15:33:56.250Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760963 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-22T15:33:56.251Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584374)
;

-- 2025-12-22T15:33:56.256Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760963
;

-- 2025-12-22T15:33:56.257Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760963)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Kostenklassifizierung
-- Column: Fact_Acct_Transactions_View.C_CostClassification_ID
-- 2025-12-22T15:34:36.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760962,0,242,641301,540306,'F',TO_TIMESTAMP('2025-12-22 15:34:35.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Definiert die wirtschaftliche Art eines Aufwands oder Ertrags (Kostenart) als eigenständige Controlling-Dimension für Finanzbuchhaltung und Reporting.','Y','N','N','Y','N','N','N',0,'Kostenklassifizierung',31,0,0,TO_TIMESTAMP('2025-12-22 15:34:35.993000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Buchführungs-Details(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Kostenartengruppe
-- Column: Fact_Acct_Transactions_View.C_CostClassification_Category_ID
-- 2025-12-22T15:34:53.923Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,760963,0,242,641302,540306,'F',TO_TIMESTAMP('2025-12-22 15:34:53.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gruppiert Kostenarten zu übergeordneten Analyse-Kategorien für ein strukturiertes und aggregiertes Controlling-Reporting.','Y','N','N','Y','N','N','N',0,'Kostenartengruppe',32,0,0,TO_TIMESTAMP('2025-12-22 15:34:53.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: Fact_Acct_Transactions_View.C_CostClassification_Category_ID
-- 2025-12-22T15:35:50.173Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-12-22 15:35:50.173000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591820
;

-- 2025-12-22T15:35:51.245Z
INSERT INTO t_alter_column values('fact_acct_transactions_view','C_CostClassification_Category_ID','NUMERIC(10)',null,null)
;

-- Column: Fact_Acct_Transactions_View.C_CostClassification_ID
-- 2025-12-22T15:35:57.299Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-12-22 15:35:57.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591819
;

-- 2025-12-22T15:35:58.257Z
INSERT INTO t_alter_column values('fact_acct_transactions_view','C_CostClassification_ID','NUMERIC(10)',null,null)
;

-- Run mode: SWING_CLIENT

-- Column: Fact_Acct_Transactions_View.C_CostClassification_ID
-- 2025-12-22T15:53:52.188Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-12-22 15:53:52.188000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591819
;

-- Column: Fact_Acct_Transactions_View.C_CostClassification_Category_ID
-- 2025-12-22T15:54:00.118Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-12-22 15:54:00.118000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591820
;

-- UI Element: Buchführungs-Details_OLD(162,D) -> Buchführung(242,D) -> main -> 20 -> section.Sales order
-- Column: Fact_Acct_Transactions_View.C_OrderSO_ID
-- 2025-12-22T15:54:16.991Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=220,Updated=TO_TIMESTAMP('2025-12-22 15:54:16.990000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=614551
;

-- UI Element: Buchführungs-Details_OLD(162,D) -> Buchführung(242,D) -> main -> 20 -> quantity.Menge
-- Column: Fact_Acct_Transactions_View.Qty
-- 2025-12-22T15:54:16.997Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=230,Updated=TO_TIMESTAMP('2025-12-22 15:54:16.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570661
;

-- UI Element: Buchführungs-Details_OLD(162,D) -> Buchführung(242,D) -> main -> 20 -> quantity.Maßeinheit
-- Column: Fact_Acct_Transactions_View.C_UOM_ID
-- 2025-12-22T15:54:17.002Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=240,Updated=TO_TIMESTAMP('2025-12-22 15:54:17.002000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=570662
;

-- UI Element: Buchführungs-Details_OLD(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Cost Element
-- Column: Fact_Acct_Transactions_View.M_CostElement_ID
-- 2025-12-22T15:54:17.008Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=250,Updated=TO_TIMESTAMP('2025-12-22 15:54:17.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=615852
;

-- UI Element: Buchführungs-Details_OLD(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Kostenklassifizierung
-- Column: Fact_Acct_Transactions_View.C_CostClassification_ID
-- 2025-12-22T15:54:17.011Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=260,Updated=TO_TIMESTAMP('2025-12-22 15:54:17.011000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641301
;

-- UI Element: Buchführungs-Details_OLD(162,D) -> Buchführung(242,D) -> main -> 20 -> references.Kostenartengruppe
-- Column: Fact_Acct_Transactions_View.C_CostClassification_Category_ID
-- 2025-12-22T15:54:17.014Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=270,Updated=TO_TIMESTAMP('2025-12-22 15:54:17.014000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641302
;
