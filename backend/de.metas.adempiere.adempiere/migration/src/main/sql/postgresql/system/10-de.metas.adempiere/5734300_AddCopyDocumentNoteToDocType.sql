-- 2024-09-23T15:35:08.993Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583282,0,'CopyDescriptionNote',TO_TIMESTAMP('2024-09-23 18:35:08.576','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Copy description ','Copy description ',TO_TIMESTAMP('2024-09-23 18:35:08.576','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T15:35:09.020Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583282 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: CopyDescriptionNote
-- 2024-09-23T15:35:29.290Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Copy description Note', PrintName='Copy description Note',Updated=TO_TIMESTAMP('2024-09-23 18:35:29.29','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='en_US'
;

-- 2024-09-23T15:35:29.356Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'en_US') 
;

-- Element: CopyDescriptionNote
-- 2024-09-23T15:39:34.931Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Beschreibung kopieren Hinweis', PrintName='Beschreibung kopieren Hinweis',Updated=TO_TIMESTAMP('2024-09-23 18:39:34.931','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='de_CH'
;

-- 2024-09-23T15:39:34.981Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_CH') 
;

-- Element: CopyDescriptionNote
-- 2024-09-23T15:39:47.812Z
UPDATE AD_Element_Trl SET Name='Copy document note ', PrintName='Copy document note ',Updated=TO_TIMESTAMP('2024-09-23 18:39:47.811','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='en_US'
;

-- 2024-09-23T15:39:47.857Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'en_US') 
;

-- Element: CopyDescriptionNote
-- 2024-09-23T15:40:28.638Z
UPDATE AD_Element_Trl SET Name='Dokumentnotiz kopieren', PrintName='Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:40:28.638','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='de_CH'
;

-- 2024-09-23T15:40:28.683Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_CH') 
;

-- Element: CopyDescriptionNote
-- 2024-09-23T15:40:34.979Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Dokumentnotiz kopieren', PrintName='Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:40:34.978','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282 AND AD_Language='de_DE'
;

-- 2024-09-23T15:40:35.022Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583282,'de_DE') 
;

-- 2024-09-23T15:40:35.047Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_DE') 
;

-- 2024-09-23T15:41:05.662Z
UPDATE AD_Element SET ColumnName='CopyDocumentNote',Updated=TO_TIMESTAMP('2024-09-23 18:41:05.661','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583282
;

-- 2024-09-23T15:41:05.685Z
UPDATE AD_Column SET ColumnName='CopyDocumentNote' WHERE AD_Element_ID=583282
;

-- 2024-09-23T15:41:05.709Z
UPDATE AD_Process_Para SET ColumnName='CopyDocumentNote' WHERE AD_Element_ID=583282
;

-- 2024-09-23T15:41:05.800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583282,'de_DE') 
;

-- Name: CopyDocumentNote
-- 2024-09-23T15:41:59.071Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541893,TO_TIMESTAMP('2024-09-23 18:41:58.773','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','N','CopyDocumentNote',TO_TIMESTAMP('2024-09-23 18:41:58.773','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2024-09-23T15:41:59.095Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541893 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Name: CopyDocumentNote
-- 2024-09-23T15:42:04.109Z
UPDATE AD_Reference SET IsOrderByValue='Y',Updated=TO_TIMESTAMP('2024-09-23 18:42:04.046','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541893
;

-- Column: C_DocType.CopyDocumentNote
-- Column: C_DocType.CopyDocumentNote
-- 2024-09-23T15:42:21.597Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589158,583282,0,17,541893,217,'CopyDocumentNote',TO_TIMESTAMP('2024-09-23 18:42:21.147','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,2,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Dokumentnotiz kopieren',0,0,TO_TIMESTAMP('2024-09-23 18:42:21.147','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-23T15:42:21.620Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589158 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-23T15:42:21.668Z
/* DDL */  select update_Column_Translation_From_AD_Element(583282) 
;

-- 2024-09-23T15:42:41.240Z
UPDATE AD_Reference_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-23 18:42:41.174','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Reference_ID=541893
;

-- 2024-09-23T15:42:46.140Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:42:46.075','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Reference_ID=541893
;

-- 2024-09-23T15:42:48.727Z
UPDATE AD_Reference_Trl SET IsTranslated='Y', Name='Dokumentnotiz kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:42:48.66','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Reference_ID=541893
;

-- Reference: CopyDocumentNote
-- Value: CD
-- ValueName: Copy document note to document
-- 2024-09-23T15:43:32.861Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541893,543725,TO_TIMESTAMP('2024-09-23 18:43:32.542','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Copy document note to document',TO_TIMESTAMP('2024-09-23 18:43:32.542','YYYY-MM-DD HH24:MI:SS.US'),100,'CD','Copy document note to document')
;

-- 2024-09-23T15:43:32.884Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543725 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: CopyDocumentNote -> CD_Copy document note to document
-- 2024-09-23T15:44:01.068Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Dokumentnotiz in Dokument kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:44:01.068','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543725
;

-- Reference Item: CopyDocumentNote -> CD_Copy document note to document
-- 2024-09-23T15:44:04.658Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Dokumentnotiz in Dokument kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:44:04.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543725
;

-- Reference Item: CopyDocumentNote -> CD_Copy document note to document
-- 2024-09-23T15:44:07.018Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-23 18:44:07.018','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543725
;

-- Reference: CopyDocumentNote
-- Value: CO
-- ValueName: Copy document note from Order
-- 2024-09-23T15:44:26.851Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541893,543726,TO_TIMESTAMP('2024-09-23 18:44:26.547','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Copy document note from Order',TO_TIMESTAMP('2024-09-23 18:44:26.547','YYYY-MM-DD HH24:MI:SS.US'),100,'CO','Copy document note from Order')
;

-- 2024-09-23T15:44:26.874Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543726 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: CopyDocumentNote -> CO_Copy document note from Order
-- 2024-09-23T15:44:34.957Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-09-23 18:44:34.957','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543726
;

-- Reference Item: CopyDocumentNote -> CO_Copy document note from Order
-- 2024-09-23T15:44:42.091Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Dokument aus Bestellung kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:44:42.091','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543726
;

-- Reference Item: CopyDocumentNote -> CO_Copy document note from Order
-- 2024-09-23T15:44:45.787Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Dokument aus Bestellung kopieren',Updated=TO_TIMESTAMP('2024-09-23 18:44:45.787','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543726
;

-- Column: C_DocType.CopyDocumentNote
-- Column: C_DocType.CopyDocumentNote
-- 2024-09-23T15:45:15.539Z
UPDATE AD_Column SET DefaultValue='''CD''',Updated=TO_TIMESTAMP('2024-09-23 18:45:15.539','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589158
;

-- 2024-09-23T15:45:19.929Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN CopyDocumentNote VARCHAR(2) DEFAULT ''CD''')
;

-- Field: Belegart -> Belegart -> Dokumentnotiz kopieren
-- Column: C_DocType.CopyDocumentNote
-- Field: Belegart(135,D) -> Belegart(167,D) -> Dokumentnotiz kopieren
-- Column: C_DocType.CopyDocumentNote
-- 2024-09-23T15:49:44.208Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,589158,731501,0,167,0,TO_TIMESTAMP('2024-09-23 18:49:43.63','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Dokumentnotiz kopieren',0,340,0,1,1,TO_TIMESTAMP('2024-09-23 18:49:43.63','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-23T15:49:44.232Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731501 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-23T15:49:44.259Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583282) 
;

-- 2024-09-23T15:49:44.296Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731501
;

-- 2024-09-23T15:49:44.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731501)
;

-- UI Element: Belegart -> Belegart.Dokumentnotiz kopieren
-- Column: C_DocType.CopyDocumentNote
-- UI Element: Belegart(135,D) -> Belegart(167,D) -> main -> 10 -> description.Dokumentnotiz kopieren
-- Column: C_DocType.CopyDocumentNote
-- 2024-09-23T15:50:37.751Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731501,0,167,540406,625986,'F',TO_TIMESTAMP('2024-09-23 18:50:37.255','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Dokumentnotiz kopieren',30,0,0,TO_TIMESTAMP('2024-09-23 18:50:37.255','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Belegart -> Belegart -> Dokumentnotiz kopieren
-- Column: C_DocType.CopyDocumentNote
-- Field: Belegart(135,D) -> Belegart(167,D) -> Dokumentnotiz kopieren
-- Column: C_DocType.CopyDocumentNote
-- 2024-09-23T15:50:55.383Z
UPDATE AD_Field SET DisplayLogic='@IsCopyDescriptionToDocument@=''Y''',Updated=TO_TIMESTAMP('2024-09-23 18:50:55.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=731501
;


-- Column: C_DocType.CopyDocumentNote
-- Column: C_DocType.CopyDocumentNote
-- 2024-09-24T07:32:28.525Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-09-24 10:32:28.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589158
;

-- 2024-09-24T07:32:32.944Z
INSERT INTO t_alter_column values('c_doctype','CopyDocumentNote','VARCHAR(2)',null,'CD')
;

-- 2024-09-24T07:32:32.983Z
UPDATE C_DocType SET CopyDocumentNote='CD' WHERE CopyDocumentNote IS NULL
;

-- 2024-09-24T07:32:33.010Z
INSERT INTO t_alter_column values('c_doctype','CopyDocumentNote',null,'NOT NULL',null)
;

-- Field: Belegart -> Belegart -> Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- Field: Belegart(135,D) -> Belegart(167,D) -> Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- 2024-09-24T07:37:15.293Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=SOO | @DocBaseType@=POO | @DocBaseType@=ARI | @DocBaseType@=ARC | @DocBaseType@=API | @DocBaseType@=ARC | @DocBaseType@=MMS',Updated=TO_TIMESTAMP('2024-09-24 10:37:15.293','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=561403
;

-- Field: Belegart -> Belegart -> Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- Field: Belegart(135,D) -> Belegart(167,D) -> Copy description to document
-- Column: C_DocType.IsCopyDescriptionToDocument
-- 2024-09-24T07:37:52.340Z
UPDATE AD_Field SET DisplayLogic='@DocBaseType@=SOO | @DocBaseType@=POO | @DocBaseType@=ARI | @DocBaseType@=ARC | @DocBaseType@=API | @DocBaseType@=ARC | @DocBaseType@=MMS | @DocBaseType@=MMR',Updated=TO_TIMESTAMP('2024-09-24 10:37:52.34','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=561403
;

