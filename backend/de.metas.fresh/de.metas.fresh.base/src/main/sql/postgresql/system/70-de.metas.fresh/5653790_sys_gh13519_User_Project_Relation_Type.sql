-- Name: AD_User_Projects
-- 2022-08-30T09:53:11.077Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541648,TO_TIMESTAMP('2022-08-30 10:53:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User_Projects',TO_TIMESTAMP('2022-08-30 10:53:10','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2022-08-30T09:53:11.148Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541648 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_User_Projects
-- Table: C_Project
-- Key: C_Project.C_Project_ID
-- 2022-08-30T10:01:33.721Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,1349,0,541648,203,TO_TIMESTAMP('2022-08-30 11:01:33','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2022-08-30 11:01:33','YYYY-MM-DD HH24:MI:SS'),100,'AD_User_ID = @AD_User_ID/-1@ OR AD_User_InCharge_ID = @AD_User_ID/-1@ OR SalesRep_ID = @AD_User_ID/-1@ AND IsActive = ''Y''')
;

-- 2022-08-30T10:03:00.033Z
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,110,541648,540361,TO_TIMESTAMP('2022-08-30 11:02:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User -> C_Project',TO_TIMESTAMP('2022-08-30 11:02:59','YYYY-MM-DD HH24:MI:SS'),100)
;
