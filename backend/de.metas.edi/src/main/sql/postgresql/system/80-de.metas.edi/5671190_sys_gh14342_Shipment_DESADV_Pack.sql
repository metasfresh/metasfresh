-- 2023-01-09T11:58:53.080Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540670,540380,TO_TIMESTAMP('2023-01-09 12:58:52.206','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.esb.edi','Y','N','M_InOut (SOTrx) -> EDI_Desadv_Pack',TO_TIMESTAMP('2023-01-09 12:58:52.206','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-01-09T12:02:11.873Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541704,TO_TIMESTAMP('2023-01-09 13:02:10.657','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.esb.edi','Y','N','EDI_Desadv_Pack_Target_For_M_InOut',TO_TIMESTAMP('2023-01-09 13:02:10.657','YYYY-MM-DD HH24:MI:SS.US'),100,'T')
;

-- 2023-01-09T12:02:12.062Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541704 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2023-01-09T14:56:47.488Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,583425,0,541704,542170,TO_TIMESTAMP('2023-01-09 15:56:47.201','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.esb.edi','Y','N','N',TO_TIMESTAMP('2023-01-09 15:56:47.201','YYYY-MM-DD HH24:MI:SS.US'),100,'edi_desadv_pack_id IN  ( SELECT pi.edi_desadv_pack_id from edi_desadv_pack_item pi  where pi.M_InOut_ID = @M_InOut_ID / -1@ )')
;

-- 2023-01-09T14:58:26.332Z
UPDATE AD_Ref_Table SET AD_Window_ID=541543,Updated=TO_TIMESTAMP('2023-01-09 15:58:26.054','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Reference_ID=541704
;

-- 2023-01-09T14:58:49.512Z
UPDATE AD_RelationType SET AD_Reference_Target_ID=541704,Updated=TO_TIMESTAMP('2023-01-09 15:58:49.136','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_RelationType_ID=540380
;

