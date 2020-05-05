-- 2017-06-03T15:05:12.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window SET Description='Mein Benutzer Profil', Name='Mein Profil',Updated=TO_TIMESTAMP('2017-06-03 15:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=53100
;

-- 2017-06-03T15:05:12.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Window_Trl SET IsTranslated='N' WHERE AD_Window_ID=53100
;

-- 2017-06-03T15:05:12.754
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu SET Description='Mein Benutzer Profil', IsActive='Y', Name='Mein Profil',Updated=TO_TIMESTAMP('2017-06-03 15:05:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=53246
;

-- 2017-06-03T15:05:12.755
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Menu_Trl SET IsTranslated='N' WHERE AD_Menu_ID=53246
;

-- 2017-06-03T15:08:34.087
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy,WEBUI_NameBrowse) VALUES ('W',0,540849,0,53100,TO_TIMESTAMP('2017-06-03 15:08:34','YYYY-MM-DD HH24:MI:SS'),100,'Mein Benutzer Profil','D','_Mein_Profil','Y','N','N','N','N','Mein Profil',TO_TIMESTAMP('2017-06-03 15:08:34','YYYY-MM-DD HH24:MI:SS'),100,'Mein Profil')
;

-- 2017-06-03T15:08:34.091
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Menu_ID=540849 AND NOT EXISTS (SELECT * FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2017-06-03T15:08:34.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 540849, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=540849)
;

-- 2017-06-03T15:08:34.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=334 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.185
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=498 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.186
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=224 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=514 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.187
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=336 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=341 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=170 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=465 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=101 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=294 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.189
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=395 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=296 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=221 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.190
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=233 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=290 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=109 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.191
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540375 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.192
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=50008 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:34.193
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=161, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.060
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.062
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.063
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.064
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.065
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- 2017-06-03T15:08:39.066
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- 2017-06-03T15:17:06.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53282,540251,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-03T15:17:06.771
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540251 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-03T15:17:06.803
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540341,540251,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:06.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540342,540251,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:06.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540341,540584,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:06.910
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57984,0,53282,540584,545304,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','Y','Y','N','Client',10,10,0,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:06.943
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57985,0,53282,540584,545305,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','Y','N','Sektion',20,20,0,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:06.975
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57986,0,53282,540584,545306,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Alphanumeric identifier of the entity','The name of an entity (record) is used as an default search option in addition to the search key. The name is up to 60 characters in length.','Y','N','Y','Y','N','Name',30,30,0,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.005
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57987,0,53282,540584,545307,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required','7 bit lower case alpha numeric - max length 8 - can be used for operating system names.','Y','N','Y','Y','N','Search Key',40,40,0,TO_TIMESTAMP('2017-06-03 15:17:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.034
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57988,0,53282,540584,545308,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Beschreibung',50,50,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.061
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57989,0,53282,540584,545309,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Comments or additional information','The Comments field allows for free form entry of additional information.','Y','N','Y','Y','N','Comments',60,60,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57990,0,53282,540584,545310,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','Y','Y','N','Active',70,70,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.130
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57991,0,53282,540584,545311,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a Business Partner','A Business Partner is anyone with whom you transact.  This can include Vendor, Customer, Employee or Salesperson','Y','N','Y','Y','N','Business Partner ',80,80,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.181
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,57992,0,540137,545311,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57993,0,53282,540584,545312,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Electronic Mail Address','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','Y','Y','N','EMail Address',90,90,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.257
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57994,0,53282,540584,545313,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Password of any length (case sensitive)','The Password for this User.  Passwords are required to identify authorized users.  For Adempiere Users, you can change the password via the Process "Reset Password".','Y','N','Y','Y','N','Password',100,100,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.289
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57995,0,53282,540584,545314,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','UserPIN',110,110,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.325
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57996,0,53282,540584,545315,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Name this entity is referred to as','The Title indicates the name that an entity is referred to as.','Y','N','Y','Y','N','Title',120,120,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.365
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57997,0,53282,540584,545316,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Birthday or Anniversary day','Birthday or Anniversary day','Y','N','Y','Y','N','Birthday',130,130,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.395
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57998,0,53282,540584,545317,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Identifies a telephone number','The Phone field identifies a telephone number','Y','N','Y','Y','N','Phone',140,140,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.430
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57999,0,53282,540584,545318,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Identifies an alternate telephone number.','The 2nd Phone field identifies an alternate telephone number.','Y','N','Y','Y','N','2nd Phone',150,150,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.461
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58000,0,53282,540584,545319,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Facsimile number','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','Y','Y','N','Fax',160,160,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.488
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58001,0,53282,540584,545320,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Type of Notifications','Emails or Notification sent out for Request Updates, etc.','Y','N','Y','Y','N','Notification Type',170,170,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.515
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58002,0,53282,540584,545321,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Job Position','Y','N','Y','Y','N','Position',180,180,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.540
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58003,0,53282,540584,545322,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'The user/contact has full access to Business Partner information and resources','If selected, the user has full access to the Business Partner (BP) information (Business Documents like Orders, Invoices - Requests) or resources (Assets, Downloads). If you deselet it, the user has no access rights unless, you explicitly grant it in tab "BP Access"','Y','N','Y','Y','N','Full BP Access',190,190,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.568
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58004,0,53282,540584,545323,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'User Name (ID) in the Mail System','The user name in the mail system is usually the string before the @ of your email address.  Required if the mail server requires authentification to send emails.','Y','N','Y','Y','N','EMail User ID',200,200,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.599
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58005,0,53282,540584,545324,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Passwort Ihrer EMail Nutzer-ID','Notwendig, wenn der EMail-Server eine Anmeldung vor dem Versenden von EMails verlangt.','Y','N','Y','Y','N','Passwort EMail-Nutzer',210,210,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.626
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58006,0,53282,540584,545325,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Supervisor for this user/organization - used for escalation and approval','The Supervisor indicates who will be used for forwarding and escalating issues for this user - or for approvals.','Y','N','Y','Y','N','Supervisor',220,220,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.655
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58007,0,53282,540584,545326,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'User Name used for authorization via LDAP (directory) services','Optional LDAP system user name for the user. If not defined, the normal Name of the user is used. This allows to use the internal (LDAP) user id (e.g. jjanke) and the normal display name (e.g. Jorg Janke).  The LDAP User Name can also be used without LDAP enables (see system window).  This would allow to sign in as jjanke and use the display name of Jorg Janke.','Y','N','Y','Y','N','LDAP User Name',230,230,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.687
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58008,0,53282,540584,545327,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Performing or initiating organization','The organization which performs or initiates this transaction (for another organization).  The owning Organization may not be the transaction organization in a service bureau environment, with centralized services, and inter-organization transactions.','Y','N','Y','Y','N','Trx Organization',240,240,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.717
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58009,0,53282,540584,545328,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'How a Java Client connects to the server(s)','Depending on the connection profile, different protocols are used and tasks are performed on the server rather then the client. Usually the user can select different profiles, unless it is enforced by the User or Role definition. The User level profile overwrites the Role based profile.','Y','N','Y','Y','N','Connection Profile',250,250,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58010,0,53282,540584,545329,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Greeting to print on correspondence','The Greeting identifies the greeting to print on correspondence.','Y','N','Y','Y','N','Greeting',260,260,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.768
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58011,0,53282,540584,545330,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Date Email was verified','Y','N','Y','Y','N','EMail Verify',270,270,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.796
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58013,0,53282,540584,545331,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Verification information of EMail Address','The field contains additional information how the EMail Address has been verified','Y','N','Y','Y','N','Verification Info',280,280,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.830
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53283,540252,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2017-06-03T15:17:07.834
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540252 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-03T15:17:07.863
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540343,540252,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.888
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540343,540585,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.914
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58017,0,53283,540585,545332,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','Client',0,10,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.941
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58018,0,53283,540585,545333,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','Sektion',0,20,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:07.969
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58019,0,53283,540585,545334,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'User within the system - Internal or Business Partner Contact','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','Y','N','Lieferkontakt',0,30,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.002
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58020,0,53283,540585,545335,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100,'Text templates for mailings','The Mail Template indicates the mail template for return messages. Mail text can include variables.  The priority of parsing is User/Contact, Business Partner and then the underlying business object (like Request, Dunning, Workflow object).<br>
So, @Name@ would resolve into the User name (if user is defined defined), then Business Partner name (if business partner is defined) and then the Name of the business object if it has a Name.<br>
For Multi-Lingual systems, the template is translated based on the Business Partner''s language selection.','Y','N','N','Y','N','Mail Template',0,40,0,TO_TIMESTAMP('2017-06-03 15:17:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.037
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58021,0,53283,540585,545336,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Web Store Mail Message Template','Y','N','N','Y','N','Mail Message',0,50,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.071
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58022,0,53283,540585,545337,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Date this record was created','The Created field indicates the date that this record was created.','Y','N','N','Y','N','Created',0,60,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.105
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58023,0,53283,540585,545338,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'EMail Message ID','SMTP Message ID for tracking purposes','Y','N','N','Y','N','Message ID',0,70,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.139
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58024,0,53283,540585,545339,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Mail Betreff','Subject of the Mail ','Y','N','N','Y','N','Betreff',0,80,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.173
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58025,0,53283,540585,545340,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Text used for Mail message','The Mail Text indicates the text used for mail messages.','Y','N','N','Y','N','Mail Text',0,90,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58026,0,53283,540585,545341,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'EMail Delivery confirmation','Y','N','N','Y','N','Delivery Confirmation',0,100,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:17:08.240
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58027,0,53283,540585,545342,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','Delivered',0,110,0,TO_TIMESTAMP('2017-06-03 15:17:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:27:40.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,53282,540253,TO_TIMESTAMP('2017-06-03 15:27:40','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-03 15:27:40','YYYY-MM-DD HH24:MI:SS'),100,'advanced edit')
;

-- 2017-06-03T15:27:40.968
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_UI_Section_ID=540253 AND NOT EXISTS (SELECT * FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2017-06-03T15:27:57.223
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET SeqNo=10, Value='main',Updated=TO_TIMESTAMP('2017-06-03 15:27:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540253
;

-- 2017-06-03T15:28:11.352
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540344,540253,TO_TIMESTAMP('2017-06-03 15:28:11','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2017-06-03 15:28:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:28:13.833
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,540345,540253,TO_TIMESTAMP('2017-06-03 15:28:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2017-06-03 15:28:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:28:38.660
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,540344,540586,TO_TIMESTAMP('2017-06-03 15:28:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2017-06-03 15:28:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:28:57.718
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57986,0,53282,540586,545343,TO_TIMESTAMP('2017-06-03 15:28:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Name',10,0,0,TO_TIMESTAMP('2017-06-03 15:28:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:29:07.543
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57993,0,53282,540586,545344,TO_TIMESTAMP('2017-06-03 15:29:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','eMail',20,0,0,TO_TIMESTAMP('2017-06-03 15:29:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:29:31.552
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57997,0,53282,540586,545345,TO_TIMESTAMP('2017-06-03 15:29:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geburtstag',30,0,0,TO_TIMESTAMP('2017-06-03 15:29:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:30:08.220
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545345
;

-- 2017-06-03T15:30:19.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57994,0,53282,540586,545346,TO_TIMESTAMP('2017-06-03 15:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Passwort',30,0,0,TO_TIMESTAMP('2017-06-03 15:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:30:31.079
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540344,540587,TO_TIMESTAMP('2017-06-03 15:30:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','birthday',20,TO_TIMESTAMP('2017-06-03 15:30:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:30:47.028
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57997,0,53282,540587,545347,TO_TIMESTAMP('2017-06-03 15:30:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Geburtstag',10,0,0,TO_TIMESTAMP('2017-06-03 15:30:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:31:00.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58010,0,53282,540587,545348,TO_TIMESTAMP('2017-06-03 15:31:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Anrede',20,0,0,TO_TIMESTAMP('2017-06-03 15:31:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:31:20.038
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58002,0,53282,540587,545349,TO_TIMESTAMP('2017-06-03 15:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Position',30,0,0,TO_TIMESTAMP('2017-06-03 15:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:31:41.175
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57996,0,53282,540587,545350,TO_TIMESTAMP('2017-06-03 15:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Titel',40,0,0,TO_TIMESTAMP('2017-06-03 15:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:32:53.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540345,540588,TO_TIMESTAMP('2017-06-03 15:32:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2017-06-03 15:32:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:33:07.624
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57990,0,53282,540588,545351,TO_TIMESTAMP('2017-06-03 15:33:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2017-06-03 15:33:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:33:55.924
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,58003,0,53282,540588,545352,TO_TIMESTAMP('2017-06-03 15:33:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Kontenadmin',20,0,0,TO_TIMESTAMP('2017-06-03 15:33:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:34:15.594
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540345,540589,TO_TIMESTAMP('2017-06-03 15:34:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','phone and more',20,TO_TIMESTAMP('2017-06-03 15:34:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:34:26.473
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57998,0,53282,540589,545353,TO_TIMESTAMP('2017-06-03 15:34:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Telefon',10,0,0,TO_TIMESTAMP('2017-06-03 15:34:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:34:45.249
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57999,0,53282,540589,545354,TO_TIMESTAMP('2017-06-03 15:34:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mobil',20,0,0,TO_TIMESTAMP('2017-06-03 15:34:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:34:57.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540345,540590,TO_TIMESTAMP('2017-06-03 15:34:56','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',30,TO_TIMESTAMP('2017-06-03 15:34:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:35:05.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57985,0,53282,540590,545355,TO_TIMESTAMP('2017-06-03 15:35:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2017-06-03 15:35:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:35:18.802
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,57984,0,53282,540590,545356,TO_TIMESTAMP('2017-06-03 15:35:18','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2017-06-03 15:35:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-06-03T15:36:52.967
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET SeqNo=20, Value='advanced edit',Updated=TO_TIMESTAMP('2017-06-03 15:36:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T15:38:38.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=540342
;

-- 2017-06-03T15:38:45.468
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2017-06-03 15:38:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540584
;

-- 2017-06-03T15:39:12.210
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Erweiterte Einstellungen',Updated=TO_TIMESTAMP('2017-06-03 15:39:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T15:39:12.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T15:40:03.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2017-06-03 15:40:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540584
;

-- 2017-06-03T15:42:03.589
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545304
;

-- 2017-06-03T15:42:03.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545305
;

-- 2017-06-03T15:42:03.623
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545306
;

-- 2017-06-03T15:42:03.642
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545310
;

-- 2017-06-03T15:42:11.978
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545312
;

-- 2017-06-03T15:42:11.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545313
;

-- 2017-06-03T15:42:12.001
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545315
;

-- 2017-06-03T15:42:18.753
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545316
;

-- 2017-06-03T15:42:18.775
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545317
;

-- 2017-06-03T15:42:18.786
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545318
;

-- 2017-06-03T15:42:58.290
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545321
;

-- 2017-06-03T15:42:58.309
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545329
;

-- 2017-06-03T16:38:17.722
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545307
;

-- 2017-06-03T16:38:18.206
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545308
;

-- 2017-06-03T16:38:18.650
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545309
;

-- 2017-06-03T16:38:19.078
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545311
;

-- 2017-06-03T16:38:19.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545314
;

-- 2017-06-03T16:38:20.086
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545319
;

-- 2017-06-03T16:38:20.476
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545320
;

-- 2017-06-03T16:38:20.874
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545322
;

-- 2017-06-03T16:38:21.437
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545323
;

-- 2017-06-03T16:38:21.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545324
;

-- 2017-06-03T16:38:22.475
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545325
;

-- 2017-06-03T16:38:22.998
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545326
;

-- 2017-06-03T16:38:23.396
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545327
;

-- 2017-06-03T16:38:23.895
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545328
;

-- 2017-06-03T16:38:24.311
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545330
;

-- 2017-06-03T16:38:25.583
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2017-06-03 16:38:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545331
;

-- 2017-06-03T16:39:23.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_ElementGroup SET UIStyle='',Updated=TO_TIMESTAMP('2017-06-03 16:39:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=540584
;

-- 2017-06-03T16:41:10.297
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Erweiterte Einstellunge',Updated=TO_TIMESTAMP('2017-06-03 16:41:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T16:41:10.298
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T16:41:16.293
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section SET Name='Erweiterte Einstellungen',Updated=TO_TIMESTAMP('2017-06-03 16:41:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T16:41:16.295
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET IsTranslated='N' WHERE AD_UI_Section_ID=540251
;

-- 2017-06-03T16:41:35.873
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Section_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-06-03 16:41:35','YYYY-MM-DD HH24:MI:SS'),Name='Enhanced Settings' WHERE AD_UI_Section_ID=540251 AND AD_Language='en_US'
;

-- 2017-06-03T16:47:04.713
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545308
;

-- 2017-06-03T16:47:04.719
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545311
;

-- 2017-06-03T16:47:04.724
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545309
;

-- 2017-06-03T16:47:04.726
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545328
;

-- 2017-06-03T16:47:04.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545323
;

-- 2017-06-03T16:47:04.729
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545330
;

-- 2017-06-03T16:47:04.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545319
;

-- 2017-06-03T16:47:04.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545322
;

-- 2017-06-03T16:47:04.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545326
;

-- 2017-06-03T16:47:04.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545320
;

-- 2017-06-03T16:47:04.734
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545324
;

-- 2017-06-03T16:47:04.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545307
;

-- 2017-06-03T16:47:04.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545325
;

-- 2017-06-03T16:47:04.737
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545327
;

-- 2017-06-03T16:47:04.738
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545314
;

-- 2017-06-03T16:47:04.739
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545331
;

-- 2017-06-03T16:47:04.740
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545348
;

-- 2017-06-03T16:47:04.741
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545343
;

-- 2017-06-03T16:47:04.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545344
;

-- 2017-06-03T16:47:04.742
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545347
;

-- 2017-06-03T16:47:04.743
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545353
;

-- 2017-06-03T16:47:04.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545354
;

-- 2017-06-03T16:47:04.745
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545350
;

-- 2017-06-03T16:47:04.746
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545349
;

-- 2017-06-03T16:47:04.747
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545355
;

-- 2017-06-03T16:47:04.748
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-06-03 16:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545356
;

-- 2017-06-03T16:47:18.214
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2017-06-03 16:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545351
;

-- 2017-06-03T16:47:18.216
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2017-06-03 16:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545350
;

-- 2017-06-03T16:47:18.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2017-06-03 16:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545349
;

-- 2017-06-03T16:47:18.217
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2017-06-03 16:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545355
;

-- 2017-06-03T16:47:18.219
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2017-06-03 16:47:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545356
;

-- 2017-06-03T16:47:38.728
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=10,Updated=TO_TIMESTAMP('2017-06-03 16:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545348
;

-- 2017-06-03T16:47:38.732
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=20,Updated=TO_TIMESTAMP('2017-06-03 16:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545343
;

-- 2017-06-03T16:47:38.735
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=30,Updated=TO_TIMESTAMP('2017-06-03 16:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545344
;

-- 2017-06-03T16:47:38.736
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET IsDisplayed_SideList='Y', SeqNo_SideList=40,Updated=TO_TIMESTAMP('2017-06-03 16:47:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545347
;

-- 2017-06-03T16:48:06.490
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-06-03 16:48:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545343
;

-- 2017-06-03T16:48:09.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='L',Updated=TO_TIMESTAMP('2017-06-03 16:48:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545344
;

-- 2017-06-03T16:48:13.251
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-06-03 16:48:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545346
;

-- 2017-06-03T16:48:24.797
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-06-03 16:48:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545347
;

-- 2017-06-03T16:48:33.107
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2017-06-03 16:48:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545348
;

-- 2017-06-03T16:48:42.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-06-03 16:48:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545355
;

-- 2017-06-03T16:48:45.315
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_UI_Element SET WidgetSize='M',Updated=TO_TIMESTAMP('2017-06-03 16:48:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=545356
;

