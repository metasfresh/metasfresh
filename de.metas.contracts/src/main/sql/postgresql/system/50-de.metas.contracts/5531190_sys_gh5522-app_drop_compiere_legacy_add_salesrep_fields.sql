DELETE FROM AD_Menu WHERE (ad_window_id)=(207);
DELETE FROM ad_wf_node WHERE (ad_window_id)=(207);

-- 2019-09-18T15:48:51.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=207
;

-- 2019-09-18T15:48:51.493Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=207
;

-- 2019-09-18T15:48:51.496Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=207
;

-- 2019-09-18T15:50:02.002Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=429
;

-- 2019-09-18T15:50:02.009Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=429
;


-- 2019-09-18T15:50:47.591Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=431
;

-- 2019-09-18T15:50:47.599Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=431
;

-----------
DELETE FROM AD_Menu WHERE (ad_window_id)=(210);
DELETE FROM ad_wf_node WHERE (ad_window_id)=(210);


--C_CommissionRun-- 2019-09-18T15:53:13.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Window_ID=210
;

-- 2019-09-18T15:53:13.434Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Window_Trl WHERE AD_Window_ID=210
;

-- 2019-09-18T15:53:13.438Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Window WHERE AD_Window_ID=210
;

-- 2019-09-18T15:53:30.948Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=436
;

-- 2019-09-18T15:53:30.956Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=436
;

-- 2019-09-18T15:54:05.006Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=430
;

-- 2019-09-18T15:54:05.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=430
;

-- 2019-09-18T15:54:30.928Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Table_Trl WHERE AD_Table_ID=437
;

-- 2019-09-18T15:54:30.931Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Table WHERE AD_Table_ID=437
;

-- 2019-09-18T15:54:45.360Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET AD_Window_ID=540702,Updated=TO_TIMESTAMP('2019-09-18 17:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541407
;

DROP VIEW rv_commissionrundetail;
DROP TABLE C_CommissionDetail;
DROP TABLE C_CommissionAmt;
DROP TABLE C_CommissionRun;
DROP TABLE C_CommissionLine;
DROP TABLE C_Commission;

-- 2019-09-18T16:00:18.007Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=123
;

-- 2019-09-18T16:00:18.014Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=123
;

-- 2019-09-18T16:00:40.838Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=165
;

-- 2019-09-18T16:00:40.843Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=165
;

-- 2019-09-18T16:01:07.313Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Process_Trl WHERE AD_Process_ID=166
;

-- 2019-09-18T16:01:07.319Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Process WHERE AD_Process_ID=166
;

-- 2019-09-19T12:51:23.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=540924
;

-- 2019-09-19T12:52:06.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET Name='DocType',Updated=TO_TIMESTAMP('2019-09-19 14:52:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=1000001
;

-- 2019-09-19T12:52:43.228Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568785,586847,0,186,0,TO_TIMESTAMP('2019-09-19 14:52:43','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Vertriebspartner',660,640,0,1,1,TO_TIMESTAMP('2019-09-19 14:52:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T12:52:43.232Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-19T12:52:43.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357) 
;

-- 2019-09-19T12:52:43.255Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586847
;

-- 2019-09-19T12:52:43.256Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(586847)
;

-- 2019-09-19T12:53:07.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586847,0,186,1000001,562100,'F',TO_TIMESTAMP('2019-09-19 14:53:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_SalesRep_ID',50,0,0,TO_TIMESTAMP('2019-09-19 14:53:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T12:56:43.782Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,568741,586848,0,540279,0,TO_TIMESTAMP('2019-09-19 14:56:43','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.invoicecandidate',0,'Y','Y','Y','N','N','N','N','N','Vertriebspartner',1130,490,0,1,1,TO_TIMESTAMP('2019-09-19 14:56:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T12:56:43.787Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Field_ID=586848 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2019-09-19T12:56:43.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541357) 
;

-- 2019-09-19T12:56:43.805Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Element_Link WHERE AD_Field_ID=586848
;

-- 2019-09-19T12:56:43.806Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ select AD_Element_Link_Create_Missing_Field(586848)
;

-- 2019-09-19T12:56:48.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2019-09-19 14:56:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586848
;

-- 2019-09-19T12:59:30.874Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2019-09-19 14:59:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541083
;

-- 2019-09-19T12:59:33.538Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2019-09-19 14:59:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541082
;

-- 2019-09-19T12:59:35.833Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2019-09-19 14:59:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541081
;

-- 2019-09-19T12:59:38.306Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2019-09-19 14:59:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553823
;

-- 2019-09-19T12:59:45.367Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2019-09-19 14:59:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=553822
;

-- 2019-09-19T13:00:02.079Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,586848,0,540279,540057,562101,'F',TO_TIMESTAMP('2019-09-19 15:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'C_BPartner_SalesRep_ID',30,0,0,TO_TIMESTAMP('2019-09-19 15:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T13:00:53.699Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET DisplayLogic='@C_BPartner_SalesRep_ID/0@!0',Updated=TO_TIMESTAMP('2019-09-19 15:00:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=586848
;

-- 2019-09-19T13:09:19.601Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,9626,0,220,1000011,562102,'F',TO_TIMESTAMP('2019-09-19 15:09:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'IsSalesRep',90,0,0,TO_TIMESTAMP('2019-09-19 15:09:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T13:09:57.159Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sales partner', PrintName='Sales partner',Updated=TO_TIMESTAMP('2019-09-19 15:09:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='en_GB'
;

-- 2019-09-19T13:09:57.166Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'en_GB') 
;

-- 2019-09-19T13:10:08.993Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2019-09-19 15:10:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='de_CH'
;

-- 2019-09-19T13:10:08.994Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'de_CH') 
;

-- 2019-09-19T13:10:15.771Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET Name='Sales partner', PrintName='Sales partner',Updated=TO_TIMESTAMP('2019-09-19 15:10:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='en_US'
;

-- 2019-09-19T13:10:15.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'en_US') 
;

-- 2019-09-19T13:10:26.375Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Vertriebspartner', PrintName='Vertriebspartner',Updated=TO_TIMESTAMP('2019-09-19 15:10:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=409 AND AD_Language='de_DE'
;

-- 2019-09-19T13:10:26.376Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(409,'de_DE') 
;

-- 2019-09-19T13:10:26.386Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */  select update_ad_element_on_ad_element_trl_update(409,'de_DE') 
;

-- 2019-09-19T13:10:26.388Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='IsSalesRep', Name='Vertriebspartner', Description='Indicates if  the business partner is a sales representative or company agent', Help='The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.' WHERE AD_Element_ID=409
;

-- 2019-09-19T13:10:26.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesRep', Name='Vertriebspartner', Description='Indicates if  the business partner is a sales representative or company agent', Help='The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.', AD_Element_ID=409 WHERE UPPER(ColumnName)='ISSALESREP' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2019-09-19T13:10:26.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='IsSalesRep', Name='Vertriebspartner', Description='Indicates if  the business partner is a sales representative or company agent', Help='The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.' WHERE AD_Element_ID=409 AND IsCentrallyMaintained='Y'
;

-- 2019-09-19T13:10:26.393Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Vertriebspartner', Description='Indicates if  the business partner is a sales representative or company agent', Help='The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.' WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=409) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 409)
;

-- 2019-09-19T13:10:26.411Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Vertriebspartner', Name='Vertriebspartner' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=409)
;

-- 2019-09-19T13:10:26.413Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Tab SET Name='Vertriebspartner', Description='Indicates if  the business partner is a sales representative or company agent', Help='The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.', CommitWarning = NULL WHERE AD_Element_ID = 409
;

-- 2019-09-19T13:10:26.415Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_WINDOW SET Name='Vertriebspartner', Description='Indicates if  the business partner is a sales representative or company agent', Help='The Sales Rep checkbox indicates if this business partner is a sales representative. A sales representative may also be an emplyee, but does not need to be.' WHERE AD_Element_ID = 409
;

-- 2019-09-19T13:10:26.416Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET   Name = 'Vertriebspartner', Description = 'Indicates if  the business partner is a sales representative or company agent', WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 409
;

-- 2019-09-19T13:10:38.427Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DefaultValue='N',Updated=TO_TIMESTAMP('2019-09-19 15:10:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2929
;

-- 2019-09-19T13:10:41.628Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_bpartner','IsSalesRep','CHAR(1)',null,'N')
;

-- 2019-09-19T13:10:42.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_BPartner SET IsSalesRep='N' WHERE IsSalesRep IS NULL
;

-- 2019-09-19T13:12:47.409Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2019-09-19 15:12:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=353
;

-- 2019-09-19T13:13:49.208Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540451,'C_BPartner.IsSalesRep=''Y''',TO_TIMESTAMP('2019-09-19 15:13:49','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_BPartner SalesRep','S',TO_TIMESTAMP('2019-09-19 15:13:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T13:14:40.442Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138, AD_Val_Rule_ID=540451,Updated=TO_TIMESTAMP('2019-09-19 15:14:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568785
;

-- 2019-09-19T13:15:19.971Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138, AD_Val_Rule_ID=540451,Updated=TO_TIMESTAMP('2019-09-19 15:15:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=14095
;

-- 2019-09-19T13:16:14.186Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138, AD_Val_Rule_ID=540451,Updated=TO_TIMESTAMP('2019-09-19 15:16:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568741
;

-- 2019-09-19T13:16:40.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138, AD_Val_Rule_ID=540451,Updated=TO_TIMESTAMP('2019-09-19 15:16:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568764
;

-- 2019-09-19T13:17:29.453Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=353
;

-- 2019-09-19T13:17:29.459Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=353
;

-- 2019-09-19T13:47:02.907Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540452,'C_BPartner.IsSalesRep=''Y'' AND C_BPartner.C_BPartner_ID!=@Bill_BPartner_ID/-1@',TO_TIMESTAMP('2019-09-19 15:47:02','YYYY-MM-DD HH24:MI:SS'),100,'','D','Y','C_BPartner SalesRep and NOT BiIl_Partner','S',TO_TIMESTAMP('2019-09-19 15:47:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-09-19T13:47:24.756Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Val_Rule_ID=540452,Updated=TO_TIMESTAMP('2019-09-19 15:47:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568785
;

-- 2019-09-19T13:54:15.976Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO t_alter_column values('c_commission_instance','C_Order_ID','NUMERIC(10)',null,null)
;

-- 2019-09-19T13:54:45.151Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=138,Updated=TO_TIMESTAMP('2019-09-19 15:54:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568789
;

-- 2019-09-19T13:54:46.801Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('C_Commission_Instance','ALTER TABLE public.C_Commission_Instance ADD COLUMN Bill_BPartner_ID NUMERIC(10)')
;

-- 2019-09-19T13:54:46.808Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE C_Commission_Instance ADD CONSTRAINT BillBPartner_CCommissionInstance FOREIGN KEY (Bill_BPartner_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;


CREATE INDEX c_commission_instance_c_order_id
    ON public.c_commission_instance (c_order_id);
	
CREATE INDEX c_commission_instance_bill_bpartner_id
    ON public.c_commission_instance (bill_bpartner_id);
	
CREATE INDEX c_commission_share_c_bpartner_salesrep_id
    ON public.c_commission_share (c_bpartner_salesrep_id);
	
DROP INDEX IF EXISTS public.c_commission_share_c_commission_instance_id;
CREATE INDEX c_commission_share_c_commission_instance_id
    ON public.c_commission_share (c_commission_instance_id);




-- 2019-09-19T14:20:59.525Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET AD_Display=NULL,Updated=TO_TIMESTAMP('2019-09-19 16:20:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540100
;

-- 2019-09-19T14:21:10.578Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET EntityType='D',Updated=TO_TIMESTAMP('2019-09-19 16:21:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540100
;

-- 2019-09-19T14:21:14.625Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=540100,Updated=TO_TIMESTAMP('2019-09-19 16:21:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=568790
;

