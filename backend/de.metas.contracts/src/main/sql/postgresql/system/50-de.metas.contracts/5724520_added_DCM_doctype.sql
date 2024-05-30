-- Run mode: SWING_CLIENT

-- Reference: Computing Methods
-- Value: ProForma
-- ValueName: ProForma
-- 2024-05-27T18:53:52.341Z
DELETE FROM  AD_Ref_List_Trl WHERE AD_Ref_List_ID=543664
;

-- 2024-05-27T18:53:52.345Z
DELETE FROM AD_Ref_List WHERE AD_Ref_List_ID=543664
;

-- Reference: C_DocType SubType
-- Value: DS
-- ValueName: Definitive Schlussabrechnung
-- 2024-05-27T18:59:58.117Z
UPDATE AD_Ref_List SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-05-27 21:59:58.117','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543690
;

-- Reference: C_DocType SubType
-- Value: DCM
-- ValueName: DefinitiveCreditMemo
-- 2024-05-27T19:08:48.759Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543691,TO_TIMESTAMP('2024-05-27 22:08:48.625','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Definitive Rechnungsgutschrift',TO_TIMESTAMP('2024-05-27 22:08:48.625','YYYY-MM-DD HH24:MI:SS.US'),100,'DCM','DefinitiveCreditMemo')
;

-- 2024-05-27T19:08:48.765Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543691 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubType -> DCM_DefinitiveCreditMemo
-- 2024-05-27T19:11:40.347Z
UPDATE AD_Ref_List_Trl SET Name='Definitive Credit Memo',Updated=TO_TIMESTAMP('2024-05-27 22:11:40.347','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543691
;

-- 2024-05-27T19:13:54.022Z
UPDATE C_DocType SET DocSubType='DCM',Updated=TO_TIMESTAMP('2024-05-27 22:13:54.022','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541123
;
