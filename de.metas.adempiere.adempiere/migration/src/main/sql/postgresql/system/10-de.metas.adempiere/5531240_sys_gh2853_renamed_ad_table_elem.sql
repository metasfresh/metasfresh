-- 2019-09-19T06:12:45.272Z
-- URL zum Konzept
UPDATE AD_Field SET SortNo=-1.000000000000,Updated=TO_TIMESTAMP('2019-09-19 09:12:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=10452
;

-- 2019-09-19T06:32:35.192Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577099,0,TO_TIMESTAMP('2019-09-19 09:32:35','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Belegart','Belegart',TO_TIMESTAMP('2019-09-19 09:32:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T06:32:35.208Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577099 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-19T06:54:35.228Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=577099
;

-- 2019-09-19T06:54:35.231Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=577099
;

-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577100,0,'UnPostedDocuments_Belegart',TO_TIMESTAMP('2019-09-19 09:59:04','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Belegart','Beleart',TO_TIMESTAMP('2019-09-19 09:59:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T06:59:04.626Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577100 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2019-09-19T07:17:14.842Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=561836
;

-- 2019-09-19T07:17:14.851Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=10451
;

-- 2019-09-19T07:17:14.858Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=10451
;

-- 2019-09-19T07:17:14.860Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=10451
;

-- 2019-09-19T07:18:25.057Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12343,586148,0,662,0,TO_TIMESTAMP('2019-09-19 10:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Database Table information',0,'D','The Database Table provides the information of the table definition',0,'Y','Y','Y','N','N','N','N','N','DB-Tabelle',100,120,0,1,1,TO_TIMESTAMP('2019-09-19 10:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T07:18:25.059Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586148 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-19T07:18:25.116Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2019-09-19T07:18:25.213Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586148
;

-- 2019-09-19T07:18:25.214Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586148)
;

-- 2019-09-19T08:03:58.784Z
-- URL zum Konzept
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=577100
;

-- 2019-09-19T08:03:58.791Z
-- URL zum Konzept
DELETE FROM AD_Element WHERE AD_Element_ID=577100
;

-- 2019-09-19T08:05:45.463Z
-- URL zum Konzept
UPDATE AD_Field SET Help='The Document Definition Tab defines the processing parameters and controls for the document.  Note that shipments for automatic documents like POS/Warehouse Orders cannot have confirmations!', AD_Name_ID=572896, Name='Belegart', Description='Define a Document Type',Updated=TO_TIMESTAMP('2019-09-19 11:05:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586148
;

-- 2019-09-19T08:05:45.465Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(572896) 
;

-- 2019-09-19T08:05:45.481Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586148
;

-- 2019-09-19T08:05:45.482Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(586148)
;

-- 2019-09-19T08:11:20.644Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,586148,0,662,561863,542895,'F',TO_TIMESTAMP('2019-09-19 11:11:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Belegart',20,0,0,TO_TIMESTAMP('2019-09-19 11:11:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T08:11:27.891Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2019-09-19 11:11:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=561835
;

