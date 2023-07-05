-- 2023-07-05T08:25:15.307Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582534,0,'IsOpenItem',TO_TIMESTAMP('2023-07-05 09:25:14','YYYY-MM-DD HH24:MI:SS'),100,'This indicator shows that the account selected is an open item managed account.','D','Y','OI','OI',TO_TIMESTAMP('2023-07-05 09:25:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T08:25:15.391Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582534 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsOpenItem
-- 2023-07-05T08:25:47.978Z
UPDATE AD_Element_Trl SET Description='Dieses Kennzeichen zeigt an, dass es sich bei dem ausgewählten Konto um ein Konto mit Offener-Posten-Verwaltung handelt.', IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-05 09:25:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582534 AND AD_Language='de_DE'
;

-- 2023-07-05T08:25:48.191Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582534,'de_DE') 
;

-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T08:27:23.704Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587035,582534,0,20,542276,'XX','IsOpenItem',TO_TIMESTAMP('2023-07-05 09:27:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N','This indicator shows that the account selected is an open item managed account.','de.metas.acct',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'OI',0,0,TO_TIMESTAMP('2023-07-05 09:27:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-05T08:27:23.802Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587035 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-05T08:27:28.792Z
/* DDL */  select update_Column_Translation_From_AD_Element(582534) 
;

-- 2023-07-05T08:30:35.584Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN IsOpenItem CHAR(1) DEFAULT ''N'' CHECK (IsOpenItem IN (''Y'',''N'')) NOT NULL')
;

-- Field: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T08:36:32.541Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587035,716632,0,546899,0,TO_TIMESTAMP('2023-07-05 09:36:30','YYYY-MM-DD HH24:MI:SS'),100,'This indicator shows that the account selected is an open item managed account.',0,'D',0,'Y','Y','Y','N','N','N','N','N','OI',0,120,0,1,1,TO_TIMESTAMP('2023-07-05 09:36:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T08:36:32.644Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716632 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-05T08:36:32.742Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582534) 
;

-- 2023-07-05T08:36:32.845Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716632
;

-- 2023-07-05T08:36:32.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716632)
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> seqno&description.OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T08:37:28.486Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716632,0,546899,550557,618218,'F',TO_TIMESTAMP('2023-07-05 09:37:27','YYYY-MM-DD HH24:MI:SS'),100,'This indicator shows that the account selected is an open item managed account.','Y','N','N','Y','N','N','N',0,'OI',15,0,0,TO_TIMESTAMP('2023-07-05 09:37:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> seqno&description.OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T08:37:55.112Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-07-05 09:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618218
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> account&amounts.DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2023-07-05T08:37:55.679Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-07-05 09:37:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616609
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> account&amounts.Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2023-07-05T08:37:56.238Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-07-05 09:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616608
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> account&amounts.Amount
-- Column: SAP_GLJournalLine.Amount
-- 2023-07-05T08:37:56.840Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-07-05 09:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616610
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> account&amounts.Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2023-07-05T08:37:57.407Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-07-05 09:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616611
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> tax.Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2023-07-05T08:37:57.982Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-07-05 09:37:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616612
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 10 -> seqno&description.Description
-- Column: SAP_GLJournalLine.Description
-- 2023-07-05T08:37:58.568Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-07-05 09:37:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616607
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 20 -> dimension.Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2023-07-05T08:37:59.141Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-07-05 09:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616615
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 20 -> dimension.Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2023-07-05T08:37:59.708Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-07-05 09:37:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616616
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-07-05T08:38:00.298Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-07-05 09:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616617
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_OrderSO_ID
-- 2023-07-05T08:38:00.862Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-07-05 09:38:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616618
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2023-07-05T08:38:01.408Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-07-05 09:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616619
;

-- UI Element: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2023-07-05T08:38:01.988Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-07-05 09:38:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616620
;

