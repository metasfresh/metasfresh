-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:03:56.764809938Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587473,1065,0,20,542276,'IsTaxIncluded',TO_TIMESTAMP('2023-09-14 22:03:56.361','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Tax is included in the price ','de.metas.acct',0,1,'The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preis inklusive Steuern',0,0,TO_TIMESTAMP('2023-09-14 22:03:56.361','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-09-14T21:03:56.770906425Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-09-14T21:03:56.791489399Z
/* DDL */  select update_Column_Translation_From_AD_Element(1065) 
;

-- 2023-09-14T21:05:20.014380136Z
/* DDL */ SELECT public.db_alter_table('SAP_GLJournalLine','ALTER TABLE public.SAP_GLJournalLine ADD COLUMN IsTaxIncluded CHAR(1) DEFAULT ''N'' CHECK (IsTaxIncluded IN (''Y'',''N''))')
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Preis inklusive Steuern
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:06:43.619570604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587473,720484,0,546731,TO_TIMESTAMP('2023-09-14 22:06:43.16','YYYY-MM-DD HH24:MI:SS.US'),100,'Tax is included in the price ',1,'de.metas.acct','The Tax Included checkbox indicates if the prices include tax.  This is also known as the gross price.','Y','N','N','N','N','N','N','N','Preis inklusive Steuern',TO_TIMESTAMP('2023-09-14 22:06:43.16','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-14T21:06:43.624455763Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=720484 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-09-14T21:06:43.628890426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1065) 
;

-- 2023-09-14T21:06:43.652941555Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720484
;

-- 2023-09-14T21:06:43.656090559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720484)
;

-- 2023-09-14T21:07:23.544388755Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582719,0,TO_TIMESTAMP('2023-09-14 22:07:23.172','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct','Y','Tax Included','Tax Included',TO_TIMESTAMP('2023-09-14 22:07:23.172','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-09-14T21:07:23.549962569Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582719 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-09-14T21:07:54.096463058Z
UPDATE AD_Element_Trl SET Name='Inklusive MwSt.', PrintName='Inklusive MwSt.',Updated=TO_TIMESTAMP('2023-09-14 22:07:54.095','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582719 AND AD_Language='de_CH'
;

-- 2023-09-14T21:07:54.102627830Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582719,'de_CH') 
;

-- Element: null
-- 2023-09-14T21:08:14.023080110Z
UPDATE AD_Element_Trl SET Name='Inklusive MwSt.', PrintName='Inklusive MwSt.',Updated=TO_TIMESTAMP('2023-09-14 22:08:14.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582719 AND AD_Language='de_DE'
;

-- 2023-09-14T21:08:14.025834648Z
UPDATE AD_Element SET Name='Inklusive MwSt.', PrintName='Inklusive MwSt.' WHERE AD_Element_ID=582719
;

-- 2023-09-14T21:08:14.434631097Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582719,'de_DE') 
;

-- 2023-09-14T21:08:14.435489744Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582719,'de_DE') 
;

-- Element: null
-- 2023-09-14T21:08:34.599910209Z
UPDATE AD_Element_Trl SET Name='Taxe Incluse', PrintName='Taxe Incluse',Updated=TO_TIMESTAMP('2023-09-14 22:08:34.599','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582719 AND AD_Language='fr_CH'
;

-- 2023-09-14T21:08:34.604793214Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582719,'fr_CH') 
;

-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Inklusive MwSt.
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:08:51.100892540Z
UPDATE AD_Field SET AD_Name_ID=582719, Description=NULL, Help=NULL, Name='Inklusive MwSt.',Updated=TO_TIMESTAMP('2023-09-14 22:08:51.1','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720484
;

-- 2023-09-14T21:08:51.105326034Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Inklusive MwSt.' WHERE AD_Field_ID=720484 AND AD_Language='de_DE'
;

-- 2023-09-14T21:08:51.106673783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582719) 
;

-- 2023-09-14T21:08:51.109636245Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=720484
;

-- 2023-09-14T21:08:51.110054737Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(720484)
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Inklusive MwSt.
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:13:36.583743203Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,720484,0,546731,550195,620474,'F',TO_TIMESTAMP('2023-09-14 22:13:36.168','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Inklusive MwSt.',40,0,0,TO_TIMESTAMP('2023-09-14 22:13:36.168','YYYY-MM-DD HH24:MI:SS.US'),100)
;


-- Field: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> Inklusive MwSt.
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:18:03.710876109Z
UPDATE AD_Field SET IsDisplayed='Y', IsDisplayedGrid='Y',Updated=TO_TIMESTAMP('2023-09-14 22:18:03.71','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=720484
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Inklusive MwSt.
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:18:57.146267256Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.146','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620474
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> seqno&description.Description
-- Column: SAP_GLJournalLine.Description
-- 2023-09-14T21:18:57.151924539Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.151','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614554
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Section Code
-- Column: SAP_GLJournalLine.M_SectionCode_ID
-- 2023-09-14T21:18:57.156576638Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.156','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614560
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Product
-- Column: SAP_GLJournalLine.M_Product_ID
-- 2023-09-14T21:18:57.160679841Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.16','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614561
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.UserElementString1
-- Column: SAP_GLJournalLine.UserElementString1
-- 2023-09-14T21:18:57.164880882Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.164','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=616498
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Sales order
-- Column: SAP_GLJournalLine.C_OrderSO_ID
-- 2023-09-14T21:18:57.168781203Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.168','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614562
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> dimension.Activity
-- Column: SAP_GLJournalLine.C_Activity_ID
-- 2023-09-14T21:18:57.172618869Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.172','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614563
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 20 -> org&client.Organisation
-- Column: SAP_GLJournalLine.AD_Org_ID
-- 2023-09-14T21:18:57.177017755Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-09-14 22:18:57.176','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=614564
;

-- UI Element: GL Journal (SAP)(541656,de.metas.acct) -> GL Journal Line (SAP)(546731,de.metas.acct) -> main -> 10 -> tax.Inklusive MwSt.
-- Column: SAP_GLJournalLine.IsTaxIncluded
-- 2023-09-14T21:20:26.344985188Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-09-14 22:20:26.344','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=620474
;
