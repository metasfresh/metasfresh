-- 2022-03-04T11:16:19.982Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580648,0,TO_TIMESTAMP('2022-03-04 12:16:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Mobilnummer','Mobilnummer',TO_TIMESTAMP('2022-03-04 12:16:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-04T11:16:19.985Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580648 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;


-- 2022-03-04T11:21:25.806Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Mobile', PrintName='Mobile',Updated=TO_TIMESTAMP('2022-03-04 12:21:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=580648 AND AD_Language='en_US'
;

-- 2022-03-04T11:21:25.837Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(580648,'en_US')
;


-- 2022-03-03T11:21:37.020Z
-- URL zum Konzept
UPDATE AD_Field SET AD_Name_ID=580648, Description=NULL, Help=NULL, IsDisplayed='Y', Name='Mobilnummer',Updated=TO_TIMESTAMP('2022-03-03 12:21:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=565535
;

-- 2022-03-03T11:21:37.050Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580648)
;

-- 2022-03-03T11:21:37.056Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=565535
;

-- 2022-03-03T11:21:37.059Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(565535)
;

-- 2022-03-03T11:25:38.025Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,565535,0,118,540592,604659,'F',TO_TIMESTAMP('2022-03-03 12:25:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Mobilnummer',45,0,0,TO_TIMESTAMP('2022-03-03 12:25:37','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-03-03T11:27:38.308Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=41,Updated=TO_TIMESTAMP('2022-03-03 12:27:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=604659
;

-- 2022-03-03T11:28:00.908Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,6518,0,118,540592,604660,'F',TO_TIMESTAMP('2022-03-03 12:28:00','YYYY-MM-DD HH24:MI:SS'),100,'Faxnummer','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','Y','N','N','N',0,'Fax',42,0,0,TO_TIMESTAMP('2022-03-03 12:28:00','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- 2022-03-03T11:30:50.669Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=542399
;

-- 2022-03-03T11:31:20.122Z
-- URL zum Konzept
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=540592, SeqNo=60,Updated=TO_TIMESTAMP('2022-03-03 12:31:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542398
;

-- 2022-03-03T11:31:57.865Z
-- URL zum Konzept
UPDATE AD_UI_Element SET IsAdvancedField='N', SeqNo=43,Updated=TO_TIMESTAMP('2022-03-03 12:31:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542398
;

-- 2022-03-03T11:32:56.767Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=41,Updated=TO_TIMESTAMP('2022-03-03 12:32:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542398
;

-- 2022-03-03T11:33:04.854Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=42,Updated=TO_TIMESTAMP('2022-03-03 12:33:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=604659
;

-- 2022-03-03T11:33:12.315Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=43,Updated=TO_TIMESTAMP('2022-03-03 12:33:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=604660
;
