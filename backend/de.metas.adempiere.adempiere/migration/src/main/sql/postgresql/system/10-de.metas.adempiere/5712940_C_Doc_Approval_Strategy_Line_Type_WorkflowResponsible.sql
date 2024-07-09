-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: WFResponsible
-- ValueName: WorkflowResponsible
-- 2023-12-08T08:53:51.848698800Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541843,543610,TO_TIMESTAMP('2023-12-08 10:53:51.57','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Workflow Responsible',TO_TIMESTAMP('2023-12-08 10:53:51.57','YYYY-MM-DD HH24:MI:SS.US'),100,'WFResponsible','WorkflowResponsible')
;

-- 2023-12-08T08:53:51.851836300Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543610 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Field: Document Approval Strategy(541754,D) -> Document Approval Strategy Line(547340,D) -> Check Supervisor
-- Column: C_Doc_Approval_Strategy_Line.SupervisorCheckStrategy
-- 2023-12-08T08:54:37.267364800Z
UPDATE AD_Field SET DisplayLogic='@Type/-@=WFResponsible | @Type/-@=REQUESTOR | @Type/-@=PM',Updated=TO_TIMESTAMP('2023-12-08 10:54:37.267','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=723121
;

-- Reference: C_Doc_Approval_Strategy_Line_Type
-- Value: INVOKER
-- ValueName: WorkflowInvoker
-- 2023-12-08T08:54:53.612423600Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543609
;

-- 2023-12-08T08:54:53.616574200Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543609
;

