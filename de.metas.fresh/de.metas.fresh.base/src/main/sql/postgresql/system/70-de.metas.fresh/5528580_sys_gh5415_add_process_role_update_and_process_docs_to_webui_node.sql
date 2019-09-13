-- 2019-08-08T13:03:30.485
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,575815,541326,0,295,TO_TIMESTAMP('2019-08-08 13:03:30','YYYY-MM-DD HH24:MI:SS'),100,'Aktualisieren der Zugriffsrechte einer oder mehrerer Rollen eines Mandanten','U','495_webui','Y','N','N','N','N','Rollen-Zugriff aktualisieren',TO_TIMESTAMP('2019-08-08 13:03:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- add new menu node "Rollenzugriff aktualisieren"
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541326 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2019-08-08T13:03:30.489
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541326, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541326)
;

-- add new menu node "Rollenzugriff aktualisieren" to webui menu node "System"
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=(select max(seqno)+10 from AD_TreeNodeMM where Parent_ID=1000098) , Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

--add existing process "belege verarbeiten" to webui menu node "System"
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=(select max(seqno)+10 from AD_TreeNodeMM where Parent_ID=1000098), Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

