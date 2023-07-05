-- Column: C_ElementValue.IsOpenItem
-- 2023-07-05T10:12:05.110Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587038,582534,0,20,188,'XX','IsOpenItem',TO_TIMESTAMP('2023-07-05 11:11:59','YYYY-MM-DD HH24:MI:SS'),100,'N','N','This indicator shows that the account selected is an open item managed account.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'OI',0,0,TO_TIMESTAMP('2023-07-05 11:11:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-05T10:12:05.447Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587038 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-05T10:12:30.427Z
/* DDL */  select update_Column_Translation_From_AD_Element(582534) 
;

-- 2023-07-05T10:14:00.624Z
/* DDL */ SELECT public.db_alter_table('C_ElementValue','ALTER TABLE public.C_ElementValue ADD COLUMN IsOpenItem CHAR(1) DEFAULT ''N'' CHECK (IsOpenItem IN (''Y'',''N'')) NOT NULL')
;

-- Field: Element values(540761,D) -> Account Element(542127,D) -> OI
-- Column: C_ElementValue.IsOpenItem
-- 2023-07-05T10:22:04.789Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,587038,716633,0,542127,0,TO_TIMESTAMP('2023-07-05 11:22:02','YYYY-MM-DD HH24:MI:SS'),100,'This indicator shows that the account selected is an open item managed account.',0,'D',0,'Y','Y','Y','N','N','N','N','N','OI',0,30,0,1,1,TO_TIMESTAMP('2023-07-05 11:22:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T10:22:04.882Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716633 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-05T10:22:04.968Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582534) 
;

-- 2023-07-05T10:22:05.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716633
;

-- 2023-07-05T10:22:05.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716633)
;

-- Element: IsOpenItem
-- 2023-07-05T10:25:02.537Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Open Item Managed', PrintName='Open Item Managed',Updated=TO_TIMESTAMP('2023-07-05 11:25:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582534 AND AD_Language='en_US'
;

-- 2023-07-05T10:25:02.628Z
UPDATE AD_Element SET Name='Open Item Managed', PrintName='Open Item Managed', Updated=TO_TIMESTAMP('2023-07-05 11:25:02','YYYY-MM-DD HH24:MI:SS') WHERE AD_Element_ID=582534
;

-- 2023-07-05T10:25:13.112Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582534,'en_US') 
;

-- 2023-07-05T10:25:13.198Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582534,'en_US') 
;

-- Element: IsOpenItem
-- 2023-07-05T10:26:04.391Z
UPDATE AD_Element_Trl SET Name='Offener Posten Verwaltet', PrintName='Offener Posten Verwaltet',Updated=TO_TIMESTAMP('2023-07-05 11:26:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582534 AND AD_Language='de_DE'
;

-- 2023-07-05T10:26:04.566Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582534,'de_DE') 
;

-- UI Element: Element values(540761,D) -> Account Element(542127,D) -> main -> 20 -> flags.Open Item Managed
-- Column: C_ElementValue.IsOpenItem
-- 2023-07-05T10:27:47.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716633,0,542127,543188,618219,'F',TO_TIMESTAMP('2023-07-05 11:27:45','YYYY-MM-DD HH24:MI:SS'),100,'This indicator shows that the account selected is an open item managed account.','Y','N','N','Y','N','N','N',0,'Open Item Managed',70,0,0,TO_TIMESTAMP('2023-07-05 11:27:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T10:28:36.227Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582535,0,TO_TIMESTAMP('2023-07-05 11:28:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','OI','OI',TO_TIMESTAMP('2023-07-05 11:28:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-05T10:28:36.307Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582535 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> Open Item Managed
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T10:33:07.435Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-05 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716632
;

-- Field: GL Journal (SAP)(541693,de.metas.ab182) -> GL Journal Line (SAP)(546899,de.metas.ab182) -> OI
-- Column: SAP_GLJournalLine.IsOpenItem
-- 2023-07-05T10:34:06.696Z
UPDATE AD_Field SET AD_Name_ID=582535, Description=NULL, Help=NULL, Name='OI',Updated=TO_TIMESTAMP('2023-07-05 11:34:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716632
;

-- 2023-07-05T10:34:06.786Z
UPDATE AD_Field_Trl trl SET Description=NULL,Name='OI' WHERE AD_Field_ID=716632 AND AD_Language='en_US'
;

-- 2023-07-05T10:34:10.962Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582535) 
;

-- 2023-07-05T10:34:11.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716632
;

-- 2023-07-05T10:34:11.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716632)
;

