
-- 2024-03-21T15:02:45.145Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541859,TO_TIMESTAMP('2024-03-21 16:02:44','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','N','S_ExternalReference',TO_TIMESTAMP('2024-03-21 16:02:44','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2024-03-21T15:02:45.154Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541859 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2024-03-21T15:03:49.696Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,570588,0,541859,541486,540901,TO_TIMESTAMP('2024-03-21 16:03:49','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','Y','N','N',TO_TIMESTAMP('2024-03-21 16:03:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-21T15:04:20.896Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541859,540438,TO_TIMESTAMP('2024-03-21 16:04:20','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','S_ExternalReference_TableRecordIdTarget','Y','Y','S_ExternalReference_TableRecordIdTarget',TO_TIMESTAMP('2024-03-21 16:04:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-03-22T16:54:54.751Z
UPDATE AD_Ref_Table SET AD_Key=570584,Updated=TO_TIMESTAMP('2024-03-22 17:54:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541859
;



create index if not EXISTS s_externalreference_referenced_ad_table_id on s_externalreference(referenced_ad_table_id);
comment on index s_externalreference_referenced_ad_table_id is 'needed for AD_RelationType "S_ExternalReference_TableRecordIdTarget"';
