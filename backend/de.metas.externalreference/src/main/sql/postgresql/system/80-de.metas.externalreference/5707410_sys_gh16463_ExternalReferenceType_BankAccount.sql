-- Reference: ExternalReferenceType
-- Value: BankAccount
-- ValueName: BankAccount
-- 2023-10-15T10:33:10.885Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543587,541127,TO_TIMESTAMP('2023-10-15 13:33:09.658','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','BankAccount',TO_TIMESTAMP('2023-10-15 13:33:09.658','YYYY-MM-DD HH24:MI:SS.US'),100,'BankAccount','BankAccount')
;

-- 2023-10-15T10:33:10.924Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543587 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

