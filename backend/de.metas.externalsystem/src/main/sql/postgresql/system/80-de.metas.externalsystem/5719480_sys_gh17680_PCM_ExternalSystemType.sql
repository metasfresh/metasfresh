-- 2024-03-19T17:54:13.161Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543636,541255,TO_TIMESTAMP('2024-03-19 19:54:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Pro Care Management',TO_TIMESTAMP('2024-03-19 19:54:12','YYYY-MM-DD HH24:MI:SS'),100,'PCM','Pro Care Management')
;

-- 2024-03-19T17:54:13.173Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543636 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2024-03-19T17:58:35.951Z
INSERT INTO ExternalSystem_Config (AD_Client_ID,AD_Org_ID,AuditFileFolder,Created,CreatedBy,ExternalSystem_Config_ID,IsActive,Name,Type,Updated,UpdatedBy,WriteAudit) VALUES (1000000,1000000,'/app/data/audit',TO_TIMESTAMP('2024-03-19 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,540018,'N','Pro Care Management','PCM',TO_TIMESTAMP('2024-03-19 19:58:35','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

