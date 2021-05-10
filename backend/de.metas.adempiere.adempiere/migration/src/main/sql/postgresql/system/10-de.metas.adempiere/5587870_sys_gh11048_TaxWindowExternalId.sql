-- 2021-05-10T08:04:55.370Z
-- URL zum Konzept
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=584678
;

-- 2021-05-10T08:05:15.307Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=598475
;

-- 2021-05-10T08:05:15.317Z
-- URL zum Konzept
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=598475
;

-- 2021-05-10T08:05:15.319Z
-- URL zum Konzept
DELETE FROM AD_Field WHERE AD_Field_ID=598475
;

-- 2021-05-10T08:06:50.763Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,570108,645445,543939,0,174,0,TO_TIMESTAMP('2021-05-10 10:06:50','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','External ID',280,210,0,1,1,TO_TIMESTAMP('2021-05-10 10:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-10T08:06:50.767Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=645445 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2021-05-10T08:06:50.782Z
-- URL zum Konzept
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2021-05-10T08:06:50.794Z
-- URL zum Konzept
DELETE FROM AD_Element_Link WHERE AD_Field_ID=645445
;

-- 2021-05-10T08:06:50.795Z
-- URL zum Konzept
/* DDL */ select AD_Element_Link_Create_Missing_Field(645445)
;

-- 2021-05-10T08:07:18.787Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,645445,0,174,545777,584714,'F',TO_TIMESTAMP('2021-05-10 10:07:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External ID',30,0,0,TO_TIMESTAMP('2021-05-10 10:07:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-05-10T08:07:31.734Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2021-05-10 10:07:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584459
;

-- 2021-05-10T08:07:41.551Z
-- URL zum Konzept
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2021-05-10 10:07:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584714
;

-- 2021-05-10T08:11:41.885Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-05-10 10:11:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='de_CH'
;

-- 2021-05-10T08:11:41.902Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'de_CH')
;

-- 2021-05-10T08:11:46.010Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2021-05-10 10:11:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='en_US'
;

-- 2021-05-10T08:11:46.010Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'en_US')
;

-- 2021-05-10T08:11:52.707Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-05-10 10:11:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='nl_NL'
;

-- 2021-05-10T08:11:52.708Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'nl_NL')
;

-- 2021-05-10T08:11:58.420Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Externe ID', PrintName='Externe ID',Updated=TO_TIMESTAMP('2021-05-10 10:11:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543939 AND AD_Language='de_DE'
;

-- 2021-05-10T08:11:58.429Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543939,'de_DE')
;

-- 2021-05-10T08:11:58.460Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(543939,'de_DE')
;

-- 2021-05-10T08:11:58.464Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='ExternalId', Name='Externe ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543939
;

-- 2021-05-10T08:11:58.467Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ExternalId', Name='Externe ID', Description=NULL, Help=NULL, AD_Element_ID=543939 WHERE UPPER(ColumnName)='EXTERNALID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-05-10T08:11:58.469Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='ExternalId', Name='Externe ID', Description=NULL, Help=NULL WHERE AD_Element_ID=543939 AND IsCentrallyMaintained='Y'
;

-- 2021-05-10T08:11:58.470Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Externe ID', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543939) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 543939)
;

-- 2021-05-10T08:11:58.491Z
-- URL zum Konzept
UPDATE AD_PrintFormatItem pi SET PrintName='Externe ID', Name='Externe ID' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543939)
;

-- 2021-05-10T08:11:58.492Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Externe ID', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 543939
;

-- 2021-05-10T08:11:58.493Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Externe ID', Description=NULL, Help=NULL WHERE AD_Element_ID = 543939
;

-- 2021-05-10T08:11:58.493Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Externe ID', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 543939
;