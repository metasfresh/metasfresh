-- 2022-11-04T21:42:12.083Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543331,541463,TO_TIMESTAMP('2022-11-04 23:42:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Call External System',TO_TIMESTAMP('2022-11-04 23:42:11','YYYY-MM-DD HH24:MI:SS'),100,'CallExternalSystem','CallExternalSystem')
;

-- 2022-11-04T21:42:12.088Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543331 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-11-04T21:42:21.589Z
UPDATE AD_Ref_List SET EntityType='EE01',Updated=TO_TIMESTAMP('2022-11-04 23:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543331
;

