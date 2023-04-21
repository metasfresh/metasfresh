-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:49:10.947Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586207,192,0,19,540774,'C_Country_ID',TO_TIMESTAMP('2023-02-21 11:49:10','YYYY-MM-DD HH24:MI:SS'),100,'N','Land','D',0,10,'"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Land',0,0,TO_TIMESTAMP('2023-02-21 11:49:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-21T10:49:10.951Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586207 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-21T10:49:10.995Z
/* DDL */  select update_Column_Translation_From_AD_Element(192)
;

-- 2023-02-21T10:49:13.095Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Sequence','ALTER TABLE public.C_DocType_Sequence ADD COLUMN C_Country_ID NUMERIC(10)')
;

-- 2023-02-21T10:49:13.109Z
ALTER TABLE C_DocType_Sequence ADD CONSTRAINT CCountry_CDocTypeSequence FOREIGN KEY (C_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:52:46.448Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586207,712689,0,540744,TO_TIMESTAMP('2023-02-21 11:52:46','YYYY-MM-DD HH24:MI:SS'),100,'Land',10,'D','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','N','N','N','N','N','Land',TO_TIMESTAMP('2023-02-21 11:52:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T10:52:46.451Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T10:52:46.454Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(192)
;

-- 2023-02-21T10:52:46.481Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712689
;

-- 2023-02-21T10:52:46.485Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712689)
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:54:03.441Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y', SeqNo=60, SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-21 11:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712689
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T10:56:20.710Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712689,0,540744,540404,615896,'F',TO_TIMESTAMP('2023-02-21 11:56:20','YYYY-MM-DD HH24:MI:SS'),100,'Land','"Land" bezeichnet ein Land. Jedes Land muss angelegt sein, bevor es in einem Beleg verwendet werden kann.','Y','N','N','Y','N','N','N',0,'Land',0,0,0,TO_TIMESTAMP('2023-02-21 11:56:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T11:02:15.976Z
/* DDL */ select AD_Element_Link_Create_Missing()
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T11:04:37.721Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-21 12:04:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615896
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T11:05:20.885Z
UPDATE AD_Field SET IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-02-21 12:05:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712689
;

-- Column: C_DocType_Sequence.SeqNo
-- 2023-02-21T13:53:44.714Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586208,566,0,11,540774,'SeqNo',TO_TIMESTAMP('2023-02-21 14:53:42','YYYY-MM-DD HH24:MI:SS'),100,'N','0','Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','D',0,10,'"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reihenfolge',0,0,TO_TIMESTAMP('2023-02-21 14:53:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-21T13:53:44.717Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586208 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-21T13:53:44.725Z
/* DDL */  select update_Column_Translation_From_AD_Element(566)
;

-- 2023-02-21T13:53:47.843Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Sequence','ALTER TABLE public.C_DocType_Sequence ADD COLUMN SeqNo NUMERIC(10) DEFAULT 0 NOT NULL')
;

DROP INDEX c_doctype_sequence_uq
;

CREATE UNIQUE INDEX c_doctype_sequence_uq ON c_doctype_sequence (seqno, c_doctype_id, ad_client_id, ad_org_id, coalesce(c_country_id,0))
;

-- Field: Belegart(135,D) -> Sequence(540744,D) -> Reihenfolge
-- Column: C_DocType_Sequence.SeqNo
-- 2023-02-21T13:54:28.117Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586208,712690,0,540744,TO_TIMESTAMP('2023-02-21 14:54:28','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2023-02-21 14:54:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-21T13:54:28.120Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712690 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-21T13:54:28.122Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2023-02-21T13:54:28.177Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712690
;

-- 2023-02-21T13:54:28.181Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712690)
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Reihenfolge
-- Column: C_DocType_Sequence.SeqNo
-- 2023-02-21T13:55:31.348Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712690,0,540744,540404,615897,'F',TO_TIMESTAMP('2023-02-21 14:55:31','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',0,0,0,TO_TIMESTAMP('2023-02-21 14:55:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Reihenfolge
-- Column: C_DocType_Sequence.SeqNo
-- 2023-02-21T13:55:47.254Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-21 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615897
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Belegart
-- Column: C_DocType_Sequence.C_DocType_ID
-- 2023-02-21T13:55:47.261Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-21 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544090
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Nummernfolgen für Belege
-- Column: C_DocType_Sequence.DocNoSequence_ID
-- 2023-02-21T13:55:47.267Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-21 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544092
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Aktiv
-- Column: C_DocType_Sequence.IsActive
-- 2023-02-21T13:55:47.273Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-21 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544091
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Sektion
-- Column: C_DocType_Sequence.AD_Org_ID
-- 2023-02-21T13:55:47.278Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-21 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544089
;

-- UI Element: Belegart(135,D) -> Sequence(540744,D) -> main -> 10 -> default.Land
-- Column: C_DocType_Sequence.C_Country_ID
-- 2023-02-21T13:55:47.284Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-21 14:55:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615896
;

-- Element: null
-- 2023-02-21T14:16:49.994Z
UPDATE AD_Element_Trl SET Description='Tab can be used to define Sequences. SeqNo can be used for Setting priorities. Country can be used to define sequences for each billto-country of invoices.',Updated=TO_TIMESTAMP('2023-02-21 15:16:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573405 AND AD_Language='de_CH'
;

-- 2023-02-21T14:16:49.997Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573405,'de_CH')
;

-- Element: null
-- 2023-02-21T14:16:51.072Z
UPDATE AD_Element_Trl SET Description='Tab can be used to define Sequences. SeqNo can be used for Setting priorities. Country can be used to define sequences for each billto-country of invoices.',Updated=TO_TIMESTAMP('2023-02-21 15:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573405 AND AD_Language='en_US'
;

-- 2023-02-21T14:16:51.075Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573405,'en_US')
;

-- Element: null
-- 2023-02-21T14:16:51.935Z
UPDATE AD_Element_Trl SET Description='Tab can be used to define Sequences. SeqNo can be used for Setting priorities. Country can be used to define sequences for each billto-country of invoices.',Updated=TO_TIMESTAMP('2023-02-21 15:16:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573405 AND AD_Language='nl_NL'
;

-- 2023-02-21T14:16:51.937Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573405,'nl_NL')
;

-- Element: null
-- 2023-02-21T14:16:52.763Z
UPDATE AD_Element_Trl SET Description='Tab can be used to define Sequences. SeqNo can be used for Setting priorities. Country can be used to define sequences for each billto-country of invoices.',Updated=TO_TIMESTAMP('2023-02-21 15:16:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573405 AND AD_Language='de_DE'
;

-- 2023-02-21T14:16:52.764Z
UPDATE AD_Element SET Description='Tab can be used to define Sequences. SeqNo can be used for Setting priorities. Country can be used to define sequences for each billto-country of invoices.' WHERE AD_Element_ID=573405
;

-- 2023-02-21T14:16:53.088Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(573405,'de_DE')
;

-- 2023-02-21T14:16:53.089Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573405,'de_DE')
;

-- Element: null
-- 2023-02-21T14:16:59.198Z
UPDATE AD_Element_Trl SET Description='Tab can be used to define Sequences. SeqNo can be used for Setting priorities. Country can be used to define sequences for each billto-country of invoices.',Updated=TO_TIMESTAMP('2023-02-21 15:16:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573405 AND AD_Language='fr_CH'
;

-- 2023-02-21T14:16:59.200Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573405,'fr_CH')
;

