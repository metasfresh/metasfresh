
INSERT INTO ad_element (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, columnname, entitytype, name, printname, description, help) 
SELECT 1000363, 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, null, 'D', 'Projektart', 'Projektart', 'Type of the project', 'Type of the project with optional phases of the project with standard performance information'
where not exists (select 1 from ad_element where ad_element_id=1000363)
;
INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb)
 SELECT 1000363, 'de_CH', 0, 0, 'Y', '2021-07-26 09:18:13.210779 +00:00', -1, '2021-07-26 09:18:13.210779 +00:00', -1, 'Projektart', 'Projektart', 'Type of the project', 'Type of the project with optional phases of the project with standard performance information', null, null, null, null, 'N', null, null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000363 and ad_language='de_CH');

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
SELECT 1000363, 'de_DE', 0, 0, 'Y', '2018-11-26 07:57:00.353716 +00:00', 100, '2018-11-26 07:57:00.353716 +00:00', 100, 'Projektart', 'Projektart', 'Type of the project', 'Type of the project with optional phases of the project with standard performance information', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000363 and ad_language='de_DE');

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
 SELECT 1000363, 'en_US', 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, 'Project Type', 'Project Type', 'Type of the project', 'Type of the project with optional phases of the project with standard performance information', null, null, null, null, 'Y', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000363 and ad_language='en_US');

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
 SELECT 1000363, 'fr_CH', 0, 0, 'Y', '2018-10-15 13:04:04.943585 +00:00', 99, '2018-10-15 13:04:04.943585 +00:00', 99, 'Projektart', 'Projektart', '', '', null, null, null, null, 'Y', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000363 and ad_language='fr_CH');

INSERT INTO ad_element_trl (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby, name, printname, description, help, po_name, po_printname, po_description, po_help, istranslated, commitwarning, webui_namebrowse, webui_namenew, webui_namenewbreadcrumb, isusecustomization, name_customized, description_customized, help_customized)
 SELECT 1000363, 'nl_NL', 0, 0, 'Y', '2021-07-26 09:18:17.647262 +00:00', -1, '2021-07-26 09:18:17.647262 +00:00', -1, 'Projektart', 'Projektart', 'Type of the project', 'Type of the project with optional phases of the project with standard performance information', null, null, null, null, 'N', null, null, null, null, 'N', null, null, null
where not exists (select 1 from ad_element_trl where ad_element_id=1000363 and ad_language='nl_NL');     


-- 2019-08-14T18:17:15.690Z
-- URL zum Konzept
INSERT INTO AD_Menu (AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES (0,574814,541329,0,TO_TIMESTAMP('2019-08-14 20:17:15','YYYY-MM-DD HH24:MI:SS'),100,'U','project_webui','Y','N','N','Y','Y','Projekt-Verwaltung',TO_TIMESTAMP('2019-08-14 20:17:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-14T18:17:15.694Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541329 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-08-14T18:17:15.697Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541329, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541329)
;

-- 2019-08-14T18:17:15.746Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(574814) 
;

-- 2019-08-14T18:17:15.901Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.902Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.903Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.903Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.904Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.905Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.906Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.906Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.907Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.908Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.908Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.909Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.910Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:15.911Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:27.607Z
-- URL zum Konzept
UPDATE AD_Menu SET InternalName='project_webui_541329',Updated=TO_TIMESTAMP('2019-08-14 20:17:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541329
;

-- 2019-08-14T18:17:48.469Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.470Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.472Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.473Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.474Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.475Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.476Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.477Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.478Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.479Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.480Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.481Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.482Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.483Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.484Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.485Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.486Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.487Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.488Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.489Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:17:48.490Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.627Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.628Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.629Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.630Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.631Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.632Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.633Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.634Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.634Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.635Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.636Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.637Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.638Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.639Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.640Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.641Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.642Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.643Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.644Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:24.644Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.760Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.760Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.761Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.762Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.763Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.764Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.764Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.765Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.766Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.766Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.767Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.767Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.768Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.769Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.770Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.770Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.771Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.772Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.772Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:47.773Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.808Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.809Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.809Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.810Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.811Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.812Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.812Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.813Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.813Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.814Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.815Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.816Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.817Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.817Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.818Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.819Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.820Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.821Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.822Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.822Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.823Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:52.824Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:18:55.745Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.528Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.529Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.530Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.531Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.532Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.533Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.534Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.534Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.536Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.537Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.538Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.539Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.540Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.541Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.541Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.542Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.543Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.544Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.545Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.546Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:19:14.546Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.776Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,575002,541330,0,265,TO_TIMESTAMP('2019-08-14 20:20:19','YYYY-MM-DD HH24:MI:SS'),100,'Verwalten von Projektarten und -phasen','U','Projecttype_webui','Y','N','N','Y','N','Projekt - Projektart',TO_TIMESTAMP('2019-08-14 20:20:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2019-08-14T18:20:19.778Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541330 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-08-14T18:20:19.780Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541330, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541330)
;

-- 2019-08-14T18:20:19.783Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(575002) 
;

-- 2019-08-14T18:20:19.839Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=219 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.840Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=364 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.841Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=116 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.841Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=387 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.842Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=402 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.843Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=401 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.843Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=404 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.844Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=405 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.845Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=410 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.845Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=258 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.846Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=260 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.847Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=398 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.848Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=403 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:19.848Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=160, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- 2019-08-14T18:20:27.239Z
-- URL zum Konzept
UPDATE AD_Menu SET InternalName='Projecttype_webui_541330',Updated=TO_TIMESTAMP('2019-08-14 20:20:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541330
;

-- 2019-08-14T18:21:06.920Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.922Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.923Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.924Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.925Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.926Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.927Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.928Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.929Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.930Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.931Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.932Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.933Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.934Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.935Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.936Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.937Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.938Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.939Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.940Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.941Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.942Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:06.943Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.425Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.427Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.428Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.429Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.430Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.430Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.431Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.431Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.432Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.432Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.433Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.434Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.434Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.435Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.435Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.436Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.437Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.437Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.438Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.438Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.439Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.440Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:47.440Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.197Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.199Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.200Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.201Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.202Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.203Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.204Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.205Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.206Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.207Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.208Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.208Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.209Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.210Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.211Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.211Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.212Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.213Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.214Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.214Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.215Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.216Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:50.217Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:54.220Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:59Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541330 AND AD_Tree_ID=10
;

-- 2019-08-14T18:21:59.001Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=541329, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541312 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.765Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.766Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.766Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.767Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.790Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.791Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.792Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.792Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.793Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.794Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.795Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.795Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.796Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.797Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.797Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.798Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.799Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.800Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.800Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.801Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- 2019-08-14T18:22:11.802Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- 2019-08-14T18:29:34.425Z
-- URL zum Konzept
UPDATE AD_Menu SET Name='Projekt',Updated=TO_TIMESTAMP('2019-08-14 20:29:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541312
;

-- 2019-08-14T18:30:16.860Z
-- URL zum Konzept
UPDATE AD_Menu_Trl SET Name='Projekt',Updated=TO_TIMESTAMP('2019-08-14 20:30:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Menu_ID=541312
;

-- 2019-08-14T18:40:51.039Z
-- URL zum Konzept
UPDATE AD_Menu SET AD_Element_ID=1000363, Description='Type of the project', Name='Projektart',Updated=TO_TIMESTAMP('2019-08-14 20:40:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541330
;

-- 2019-08-14T18:40:51.041Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(1000363) 
;
