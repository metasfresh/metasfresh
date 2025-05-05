-- Reference: C_DocType SubType
-- Value: PRV
-- ValueName: Provision
-- 2023-04-27T19:43:43.941442700Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543439,148,TO_TIMESTAMP('2023-04-27 22:43:43.768','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Beistellung',TO_TIMESTAMP('2023-04-27 22:43:43.768','YYYY-MM-DD HH24:MI:SS.US'),100,'PRV','Provision')
;

-- 2023-04-27T19:43:43.941442700Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543439 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: C_DocType SubType -> PRV_Provision
-- 2023-04-27T19:43:52.183433400Z
UPDATE AD_Ref_List_Trl SET Name='Provision',Updated=TO_TIMESTAMP('2023-04-27 22:43:52.183','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543439
;

-- 2023-04-27T19:29:01.237959300Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541103,TO_TIMESTAMP('2023-04-27 22:29:01.23','YYYY-MM-DD HH24:MI:SS.US'),100,'POO',1,'de.metas.swat',1000001,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Beistellung Rechnung','Beistellung Rechnung',TO_TIMESTAMP('2023-04-27 22:29:01.23','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-27T19:29:01.259960500Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541103 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-04-27T19:29:01.266194500Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541103 AND rol.IsManual='N')
;

-- 2023-04-27T19:29:05.844704100Z
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2023-04-27 22:29:05.844','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T19:29:22.225704Z
UPDATE C_DocType SET DocBaseType='API',Updated=TO_TIMESTAMP('2023-04-27 22:29:22.225','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T19:29:58.182402500Z
UPDATE C_DocType SET DocNoSequence_ID=555719,Updated=TO_TIMESTAMP('2023-04-27 22:29:58.182','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T19:30:01.684500Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000012,Updated=TO_TIMESTAMP('2023-04-27 22:30:01.684','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T19:31:00.367996200Z
UPDATE C_DocType SET GL_Category_ID=1000006,Updated=TO_TIMESTAMP('2023-04-27 22:31:00.367','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T19:32:18.578629200Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541104,TO_TIMESTAMP('2023-04-27 22:32:18.575','YYYY-MM-DD HH24:MI:SS.US'),100,'POO',1,'de.metas.swat',1000001,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Beistellung Bestellen','Beistellung Bestellen',TO_TIMESTAMP('2023-04-27 22:32:18.575','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-04-27T19:32:18.581708800Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541104 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-04-27T19:32:18.584663300Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541104 AND rol.IsManual='N')
;

-- 2023-04-27T19:32:24.619757400Z
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2023-04-27 22:32:24.619','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T19:32:55.003453200Z
UPDATE C_DocType SET DocNoSequence_ID=545465,Updated=TO_TIMESTAMP('2023-04-27 22:32:55.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T19:33:00.361237700Z
UPDATE C_DocType SET AD_PrintFormat_ID=1000011,Updated=TO_TIMESTAMP('2023-04-27 22:33:00.361','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T19:33:37.341369200Z
UPDATE C_DocType SET C_DocTypeInvoice_ID=541103,Updated=TO_TIMESTAMP('2023-04-27 22:33:37.341','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T19:34:24.338864Z
UPDATE C_DocType_Trl SET PrintName='Provision Order',Updated=TO_TIMESTAMP('2023-04-27 22:34:24.338','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541104
;

-- 2023-04-27T19:34:28.750245900Z
UPDATE C_DocType_Trl SET Name='Provision Order',Updated=TO_TIMESTAMP('2023-04-27 22:34:28.75','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541104
;

-- 2023-04-27T19:38:00.036766500Z
UPDATE C_DocType_Trl SET PrintName='Provision Invoice',Updated=TO_TIMESTAMP('2023-04-27 22:38:00.036','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541103
;

-- 2023-04-27T19:38:01.724140500Z
UPDATE C_DocType_Trl SET Name='Provision Invoice',Updated=TO_TIMESTAMP('2023-04-27 22:38:01.724','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541103
;

-- 2023-04-27T19:45:14.949469700Z
UPDATE C_DocType SET DocSubType='PRV',Updated=TO_TIMESTAMP('2023-04-27 22:45:14.949','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T21:37:18.292025900Z
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2023-04-28 00:37:18.28','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T21:38:41.906403500Z
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2023-04-28 00:38:41.906','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T22:55:57.582321200Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-28 01:55:57.579','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541104
;

-- 2023-04-27T22:56:26.690833600Z
UPDATE C_DocType_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-28 01:56:26.69','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541103
;

-- 2023-04-27T22:56:43.009500Z
UPDATE C_DocType SET IsCreateCounter='N',Updated=TO_TIMESTAMP('2023-04-28 01:56:43.009','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-27T22:57:14.420491400Z
UPDATE C_DocType SET IsCreateCounter='N',Updated=TO_TIMESTAMP('2023-04-28 01:57:14.42','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-27T22:57:18.302575200Z
UPDATE C_DocType SET DocSubType=NULL,Updated=TO_TIMESTAMP('2023-04-28 01:57:18.302','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541103
;

-- 2023-04-28T06:29:22.587615700Z
UPDATE C_DocType SET C_DocTypeInvoice_ID=NULL,Updated=TO_TIMESTAMP('2023-04-28 09:29:22.571','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541104
;

-- 2023-04-28T06:29:34.217875700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362258
;

-- 2023-04-28T06:29:34.238895900Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362259
;

-- 2023-04-28T06:29:34.247381300Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362260
;

-- 2023-04-28T06:29:34.254968500Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362261
;

-- 2023-04-28T06:29:34.264429700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362262
;

-- 2023-04-28T06:29:34.271372900Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362263
;

-- 2023-04-28T06:29:34.277871600Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362264
;

-- 2023-04-28T06:29:34.285964600Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362265
;

-- 2023-04-28T06:29:34.293876300Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362266
;

-- 2023-04-28T06:29:34.302380100Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362267
;

-- 2023-04-28T06:29:34.310468900Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362268
;

-- 2023-04-28T06:29:34.317494800Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362269
;

-- 2023-04-28T06:29:34.323381500Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362270
;

-- 2023-04-28T06:29:34.330377700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362271
;

-- 2023-04-28T06:29:34.336979200Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1362272
;

-- 2023-04-28T06:29:34.344377700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363803
;

-- 2023-04-28T06:29:34.352396800Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363804
;

-- 2023-04-28T06:29:34.359932500Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363805
;

-- 2023-04-28T06:29:34.369886200Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363806
;

-- 2023-04-28T06:29:34.379506500Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363807
;

-- 2023-04-28T06:29:34.387899600Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363808
;

-- 2023-04-28T06:29:34.396893300Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363809
;

-- 2023-04-28T06:29:34.402886400Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363810
;

-- 2023-04-28T06:29:34.411879300Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363811
;

-- 2023-04-28T06:29:34.418388100Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363812
;

-- 2023-04-28T06:29:34.426472700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363813
;

-- 2023-04-28T06:29:34.433879500Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363814
;

-- 2023-04-28T06:29:34.439383700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363815
;

-- 2023-04-28T06:29:34.448999700Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363816
;

-- 2023-04-28T06:29:34.457953800Z
DELETE FROM AD_Document_Action_Access WHERE AD_Document_Action_Access_ID=1363817
;

-- 2023-04-28T06:29:34.464386100Z
DELETE FROM  C_DocType_Trl WHERE C_DocType_ID=541103
;

-- 2023-04-28T06:29:34.583379200Z
DELETE FROM AD_Document_Action_Access WHERE C_DocType_ID=541103
;

-- 2023-04-28T06:29:34.469012500Z
DELETE FROM C_DocType WHERE C_DocType_ID=541103
;

-- 2023-05-03T15:02:17.697174300Z
UPDATE C_DocType_Trl SET PrintName='Beistellung',Updated=TO_TIMESTAMP('2023-05-03 18:02:17.678','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541104
;

-- 2023-05-03T15:02:30.677273400Z
UPDATE C_DocType_Trl SET Name='Beistellung',Updated=TO_TIMESTAMP('2023-05-03 18:02:30.677','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND C_DocType_ID=541104
;

-- 2023-05-03T15:02:37.815080300Z
UPDATE C_DocType_Trl SET PrintName='Beistellung',Updated=TO_TIMESTAMP('2023-05-03 18:02:37.814','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541104
;

-- 2023-05-03T15:02:37.825579600Z
UPDATE C_DocType SET PrintName='Beistellung' WHERE C_DocType_ID=541104
;

-- 2023-05-03T15:02:39.961810Z
UPDATE C_DocType_Trl SET Name='Beistellung',Updated=TO_TIMESTAMP('2023-05-03 18:02:39.96','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND C_DocType_ID=541104
;

-- 2023-05-03T15:02:39.964807400Z
UPDATE C_DocType SET Name='Beistellung' WHERE C_DocType_ID=541104
;

-- 2023-05-03T15:03:19.405106800Z
UPDATE C_DocType_Trl SET PrintName='Material Provision',Updated=TO_TIMESTAMP('2023-05-03 18:03:19.404','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541104
;

-- 2023-05-03T15:03:21.063616200Z
UPDATE C_DocType_Trl SET Name='Material Provision',Updated=TO_TIMESTAMP('2023-05-03 18:03:21.062','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND C_DocType_ID=541104
;

-- 2023-05-03T15:03:28.801078200Z
UPDATE C_DocType_Trl SET PrintName='Material Provision',Updated=TO_TIMESTAMP('2023-05-03 18:03:28.8','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541104
;

-- 2023-05-03T15:03:30.555108100Z
UPDATE C_DocType_Trl SET Name='Material Provision',Updated=TO_TIMESTAMP('2023-05-03 18:03:30.554','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND C_DocType_ID=541104
;

