-- 2020-10-30T15:52:33.079Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-30 16:52:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573171 AND AD_Language='de_CH'
;

-- 2020-10-30T15:52:33.094Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573171,'de_CH') 
;

-- 2020-10-30T15:52:52.470Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', Name='Buyer', PrintName='Buyer',Updated=TO_TIMESTAMP('2020-10-30 16:52:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573171 AND AD_Language='en_US'
;

-- 2020-10-30T15:52:52.492Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573171,'en_US') 
;

-- 2020-10-30T15:53:00.560Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET Description='', IsTranslated='Y',Updated=TO_TIMESTAMP('2020-10-30 16:53:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=573171 AND AD_Language='de_DE'
;

-- 2020-10-30T15:53:00.588Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(573171,'de_DE') 
;

-- 2020-10-30T15:53:00.655Z
-- URL zum Konzept
/* DDL */  select update_ad_element_on_ad_element_trl_update(573171,'de_DE') 
;

-- 2020-10-30T15:53:00.670Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName=NULL, Name='Einkäufer', Description='', Help=NULL WHERE AD_Element_ID=573171
;

-- 2020-10-30T15:53:00.702Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName=NULL, Name='Einkäufer', Description='', Help=NULL WHERE AD_Element_ID=573171 AND IsCentrallyMaintained='Y'
;

-- 2020-10-30T15:53:00.717Z
-- URL zum Konzept
UPDATE AD_Field SET Name='Einkäufer', Description='', Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=573171) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 573171)
;

-- 2020-10-30T15:53:00.771Z
-- URL zum Konzept
UPDATE AD_Tab SET Name='Einkäufer', Description='', Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 573171
;

-- 2020-10-30T15:53:00.786Z
-- URL zum Konzept
UPDATE AD_WINDOW SET Name='Einkäufer', Description='', Help=NULL WHERE AD_Element_ID = 573171
;

-- 2020-10-30T15:53:00.818Z
-- URL zum Konzept
UPDATE AD_Menu SET   Name = 'Einkäufer', Description = '', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 573171
;

-- 2020-10-30T15:53:14.016Z
-- URL zum Konzept
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,572014,625331,573171,0,542946,0,TO_TIMESTAMP('2020-10-30 16:53:13','YYYY-MM-DD HH24:MI:SS'),100,'',0,'D',0,'Y','Y','Y','N','N','N','N','N','Einkäufer',1280,220,0,1,1,TO_TIMESTAMP('2020-10-30 16:53:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-30T15:53:14.048Z
-- URL zum Konzept
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=625331 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2020-10-30T15:54:22.012Z
-- URL zum Konzept
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,625331,0,542946,544235,574849,'F',TO_TIMESTAMP('2020-10-30 16:54:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SalesRepIntern_ID',20,0,0,TO_TIMESTAMP('2020-10-30 16:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

