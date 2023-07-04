-- Reference: Conditions_BehaviourWhenExtending
-- Value: Ex
-- ValueName: Extension Not Allowed
-- 2023-07-04T20:27:54.423542825Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,540853,543520,TO_TIMESTAMP('2023-07-04 21:27:52.717','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Extension Not Allowed',TO_TIMESTAMP('2023-07-04 21:27:52.717','YYYY-MM-DD HH24:MI:SS.US'),100,'Ex','Extension Not Allowed')
;

-- 2023-07-04T20:27:54.435621191Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543520 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-04T20:28:12.124452082Z
UPDATE AD_Ref_List_Trl SET Name='Erweiterung nicht zulässig ',Updated=TO_TIMESTAMP('2023-07-04 21:28:12.123','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543520
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-04T20:28:17.909979450Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-07-04 21:28:17.909','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543520
;

-- Reference Item: Conditions_BehaviourWhenExtending -> Ex_Extension Not Allowed
-- 2023-07-04T20:28:25.726950210Z
UPDATE AD_Ref_List_Trl SET Name='Erweiterung nicht zulässig ',Updated=TO_TIMESTAMP('2023-07-04 21:28:25.726','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543520
;

-- 2023-07-04T20:28:25.729527139Z
UPDATE AD_Ref_List SET Name='Erweiterung nicht zulässig ' WHERE AD_Ref_List_ID=543520
;

