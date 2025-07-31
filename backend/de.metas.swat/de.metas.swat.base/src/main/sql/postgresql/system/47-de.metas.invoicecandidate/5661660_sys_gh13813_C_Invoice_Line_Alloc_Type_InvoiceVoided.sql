-- Reference: C_Invoice_Line_Alloc_Type
-- Value: InvoiceVoided
-- ValueName: InvoiceVoided
-- 2022-10-24T12:32:20.138Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541501,543315,TO_TIMESTAMP('2022-10-24 14:32:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','InvoiceVoided',TO_TIMESTAMP('2022-10-24 14:32:19','YYYY-MM-DD HH24:MI:SS'),100,'InvoiceVoided','InvoiceVoided')
;

-- 2022-10-24T12:32:20.165Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543315 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

