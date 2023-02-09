
-- 2023-01-31T09:54:59.876Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,541091,TO_TIMESTAMP('2023-01-31 10:54:59','YYYY-MM-DD HH24:MI:SS'),100,'MMI',1,'de.metas.handlingunits',1000005,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Shortage','Shortage',TO_TIMESTAMP('2023-01-31 10:54:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T09:54:59.883Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541091 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-01-31T09:54:59.886Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541091 AND rol.IsManual='N')
;

-- 2023-01-31T09:55:28.722Z
UPDATE C_DocType SET DocNoSequence_ID=545472,Updated=TO_TIMESTAMP('2023-01-31 10:55:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541091
;

-- 2023-01-31T09:57:30.273Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,0,541092,TO_TIMESTAMP('2023-01-31 10:57:30','YYYY-MM-DD HH24:MI:SS'),100,'MMI',1,'de.metas.handlingunits',1000005,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Overage','Overage',TO_TIMESTAMP('2023-01-31 10:57:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-31T09:57:30.278Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541092 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-01-31T09:57:30.280Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541092 AND rol.IsManual='N')
;

-- 2023-01-31T09:57:49.328Z
UPDATE C_DocType SET DocNoSequence_ID=545472,Updated=TO_TIMESTAMP('2023-01-31 10:57:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541092
;

-- Reference: C_DocType SubTypeSO
-- Value: ISD
-- ValueName: InventoryShortageDocument
-- 2023-02-03T09:57:12.581Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543399,TO_TIMESTAMP('2023-02-03 10:57:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Shortage',TO_TIMESTAMP('2023-02-03 10:57:11','YYYY-MM-DD HH24:MI:SS'),100,'ISD','InventoryShortageDocument')
;

-- 2023-02-03T09:57:12.584Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543399 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubTypeSO -> ISD_InventoryShortageDocument
-- 2023-02-03T09:57:31.516Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-02-03 10:57:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543399
;

-- Reference: C_DocType SubTypeSO
-- Value: IOD
-- ValueName: InventoryOverageDocument
-- 2023-02-03T09:58:19.444Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,148,543400,TO_TIMESTAMP('2023-02-03 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','Overage',TO_TIMESTAMP('2023-02-03 10:58:19','YYYY-MM-DD HH24:MI:SS'),100,'IOD','InventoryOverageDocument')
;

-- 2023-02-03T09:58:19.445Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543400 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Name: C_DocSubType Compatible
-- 2023-02-03T10:15:06.948Z
UPDATE AD_Val_Rule SET Code='(''@DocBaseType@''=''ARI'' AND AD_Ref_List.ValueName IN (''AQ'', ''AP'', ''Healthcare_CH-EA'', ''Healthcare_CH-GM'', ''Healthcare_CH-KV'', ''Healthcare_CH-KT'', ''RD'', ''LS'')) OR (''@DocBaseType@''=''ARC'' AND AD_Ref_List.Value IN (''CQ'', ''CR'',''CS'', ''RI'', ''RC'')) OR (''@DocBaseType@''=''API'' AND (AD_Ref_List.Value IN (''QI'', ''VI'', ''SI'') OR AD_Ref_List.ValueName IN (''CommissionSettlement''))) OR (''@DocBaseType@''=''MOP'' AND AD_Ref_List.Value IN (''QI'', ''VI'')) OR (''@DocBaseType@'' = ''MMI'' AND AD_Ref_List.Value in (''MD'', ''VIY'', ''ISD'', ''IOD'')) OR (''@DocBaseType@''=''SDD'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''=''SDC'' AND AD_Ref_List.Value = ''NAR'') OR (''@DocBaseType@''= ''CMB'' AND AD_Ref_List.VALUE IN (''CB'', ''BS'')) OR (''@DocBaseType@''=''POO'' AND AD_Ref_List.Value = ''MED'') OR (''@DocBaseType@'' NOT IN (''API'', ''ARI'', ''ARC'', ''MOP'') AND AD_Ref_List.Value NOT IN (''AQ'', ''AP'', ''CQ'', ''CR'', ''QI'', ''MED'')) /* fallback for the rest of the entries */',Updated=TO_TIMESTAMP('2023-02-03 11:15:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540219
;

-- 2023-02-03T08:23:00.092Z
UPDATE C_DocType SET DocSubType='IOD',Updated=TO_TIMESTAMP('2023-02-03 09:23:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541092
;

-- 2023-02-03T08:24:53.189Z
UPDATE C_DocType SET DocSubType='ISD',Updated=TO_TIMESTAMP('2023-02-03 09:24:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_DocType_ID=541091
;

-- Tab: Physical Inventory(168,D) -> Inventory Count
-- Table: M_Inventory
-- 2023-02-03T10:38:24.762Z
UPDATE AD_Tab SET WhereClause='/*whereclause from AD_Tab_ID=255*/ M_Inventory.M_Inventory_ID in (select i.M_Inventory_ID from  M_Inventory i  join C_DocType dt on i.C_DocType_ID  = dt.C_DocType_ID  where  dt.DocBaseType = ''MMI'' and dt.DocSubType IN (''IAH'',''ISH'',''VIY'',''ISD'',''IOD''))',Updated=TO_TIMESTAMP('2023-02-03 11:38:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=255
;