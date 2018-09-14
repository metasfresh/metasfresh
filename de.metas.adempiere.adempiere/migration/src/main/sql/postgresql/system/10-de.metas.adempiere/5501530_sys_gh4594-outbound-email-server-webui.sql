-- 2018-09-14T17:33:33.798
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,541140,0,540080,TO_TIMESTAMP('2018-09-14 17:33:33','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','_Outbound_EMail_Server_(SMTP)','Y','N','N','N','N','Outbound EMail Server (SMTP)',TO_TIMESTAMP('2018-09-14 17:33:33','YYYY-MM-DD HH24:MI:SS'),100,'Outbound EMail Server (SMTP)')
;

-- 2018-09-14T17:33:33.804
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=541140 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2018-09-14T17:33:33.806
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541140, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541140)
;

-- 2018-09-14T17:33:34.449
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541139 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:34.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541132 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:34.451
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541133 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:34.452
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541140 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:41.073
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541140 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:41.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541139 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:41.074
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541132 AND AD_Tree_ID=10
;

-- 2018-09-14T17:33:41.075
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=541134, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541133 AND AD_Tree_ID=10
;

-- 2018-09-14T17:35:59.121
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,540236,540897,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2018-09-14T17:35:59.122
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540897 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2018-09-14T17:35:59.164
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541170,540897,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.194
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,541171,540897,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.264
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,541170,541876,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.326
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545480,0,540236,541876,553810,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','Y','N','Mandant',10,10,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.382
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545481,0,540236,541876,553811,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.433
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545475,0,540236,541876,553812,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Hostname oder IP-Adresse des Servers für SMTP und IMAP','Der Name oder die IP-Adresse des Servers für diesen Mandanten mit SMTP-Service, um EMail zu versenden und  IMAP, um einkommende EMail zu verarbeiten.','Y','N','Y','Y','N','EMail-Server',30,30,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.484
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545474,0,540236,541876,553813,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','Y','N','eMail',40,40,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.526
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,547486,0,540236,541876,553814,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Ihr EMail-Server verlangt eine Anmeldung','Some email servers require authentication before sending emails.  If yes, users are required to define their email user name and password.  If authentication is required and no user name and password is required, delivery will fail.','Y','N','Y','Y','N','SMTP Anmeldung',50,50,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.564
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545482,0,540236,541876,553815,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','SMTP Login',60,60,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.610
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,545478,0,540236,541876,553816,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','SMTP Kennwort',70,70,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.662
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563534,0,540236,541876,553817,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Start TLS',80,80,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-09-14T17:35:59.707
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,563533,0,540236,541876,553818,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','SMTP Port',90,90,0,TO_TIMESTAMP('2018-09-14 17:35:59','YYYY-MM-DD HH24:MI:SS'),100)
;

