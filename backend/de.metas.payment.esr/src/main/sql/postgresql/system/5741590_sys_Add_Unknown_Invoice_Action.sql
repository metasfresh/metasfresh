--
-- Script: /tmp/webui_migration_scripts_2024-12-12_4880948788846457504/10000020_migration_2024-12-12_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2024-12-12T14:53:28.267Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540386,1000000,TO_TIMESTAMP('2024-12-12 15:53:28','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Unknown_Invoic',TO_TIMESTAMP('2024-12-12 15:53:28','YYYY-MM-DD HH24:MI:SS'),100,'U','Unknown_Invoic')
;

-- 2024-12-12T14:53:28.269Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=1000000 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-12-12T14:53:32.442Z
UPDATE AD_Ref_List SET ValueName='Unknown_Invoice',Updated=TO_TIMESTAMP('2024-12-12 15:53:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000000
;

-- 2024-12-12T14:53:42.383Z
UPDATE AD_Ref_List SET EntityType='de.metas.payment.esr',Updated=TO_TIMESTAMP('2024-12-12 15:53:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000000
;

-- 2024-12-12T14:54:10.414Z
UPDATE AD_Ref_List SET Name='Systemfremde Rechnung',Updated=TO_TIMESTAMP('2024-12-12 15:54:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=1000000
;
