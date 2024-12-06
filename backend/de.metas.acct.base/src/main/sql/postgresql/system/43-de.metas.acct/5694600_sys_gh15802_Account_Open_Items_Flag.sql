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

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Open Item Managed
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T13:24:25.597Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587035,716634,0,546731,0,TO_TIMESTAMP('2023-07-05 14:24:23','YYYY-MM-DD HH24:MI:SS'),100,'This indicator shows that the account selected is an open item managed account.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Open Item Managed',0,120,0,1,1,TO_TIMESTAMP('2023-07-05 14:24:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T13:24:25.693Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716634 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-05T13:24:25.804Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582534)
;

-- 2023-07-05T13:24:25.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716634
;

-- 2023-07-05T13:24:25.992Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716634)
;

-- 2023-07-05T10:28:36.227Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582535,0,TO_TIMESTAMP('2023-07-05 11:28:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','OI','OI',TO_TIMESTAMP('2023-07-05 11:28:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T10:28:36.307Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582535 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T13:24:41.583Z
UPDATE AD_Field SET AD_Name_ID=582535, Description=NULL, Help=NULL, Name='OI',Updated=TO_TIMESTAMP('2023-07-05 14:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716634
;

-- 2023-07-05T13:24:41.668Z
UPDATE AD_Field_Trl trl SET Description=NULL,Name='OI' WHERE AD_Field_ID=716634 AND AD_Language='en_US'
;

-- 2023-07-05T13:24:45.843Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582535)
;

-- 2023-07-05T13:24:45.940Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716634
;

-- 2023-07-05T13:24:46.032Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716634)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T13:26:02.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716634,0,546731,550191,618220,'F',TO_TIMESTAMP('2023-07-05 14:26:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'OI',15,0,0,TO_TIMESTAMP('2023-07-05 14:26:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T13:26:34.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-07-05 14:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618220
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.DR/CR
-- Column: SAP_GLJournalLine.PostingSign
-- 2023-07-05T13:26:34.661Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-07-05 14:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614556
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Combination
-- Column: SAP_GLJournalLine.C_ValidCombination_ID
-- 2023-07-05T13:26:35.207Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-07-05 14:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614555
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Amount
-- Column: SAP_GLJournalLine.Amount
-- 2023-07-05T13:26:35.744Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-07-05 14:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614557
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> account&amounts.Accounted Amount
-- Column: SAP_GLJournalLine.AmtAcct
-- 2023-07-05T13:26:36.253Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-07-05 14:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614558
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Tax
-- Column: SAP_GLJournalLine.C_Tax_ID
-- 2023-07-05T13:26:36.753Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-07-05 14:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614559
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.Description
-- Column: SAP_GLJournalLine.Description
-- 2023-07-05T13:26:37.263Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-07-05 14:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614554
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2023-07-05T13:26:37.760Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-07-05 14:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614560
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2023-07-05T13:26:38.309Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-07-05 14:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614561
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-07-05T13:26:38.901Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-07-05 14:26:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616498
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_OrderSO_ID
-- 2023-07-05T13:26:39.474Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-07-05 14:26:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614562
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2023-07-05T13:26:40.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-07-05 14:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614563
;

-- UI Element: GL Journal (SAP) (original)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2023-07-05T13:26:40.610Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-07-05 14:26:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614564
;

