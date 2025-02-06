-- Reference: fresh_xmas_list
-- Value: S
-- ValueName: Kein Geschenk
-- 2025-01-30T15:54:32.947Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540504,543786,TO_TIMESTAMP('2025-01-30 16:54:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Kein Geschenk',TO_TIMESTAMP('2025-01-30 16:54:32','YYYY-MM-DD HH24:MI:SS'),100,'S','Kein Geschenk')
;

-- 2025-01-30T15:54:33.273Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543786 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: fresh_xmas_list -> S_Kein Geschenk
-- 2025-01-30T16:06:01.556Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='No gift',Updated=TO_TIMESTAMP('2025-01-30 17:06:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543786
;

