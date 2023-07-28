-- 2023-07-20T16:41:58.003760200Z
/* DDL */ SELECT public.db_alter_table('PP_Order','ALTER TABLE public.PP_Order ADD COLUMN Modular_Flatrate_Term_ID NUMERIC(10)')
;

-- 2023-07-20T16:41:58.607865200Z
ALTER TABLE PP_Order ADD CONSTRAINT ModularFlatrateTerm_PPOrder FOREIGN KEY (Modular_Flatrate_Term_ID) REFERENCES public.C_Flatrate_Term DEFERRABLE INITIALLY DEFERRED
;

-- Reference: C_DocType DocBaseType
-- Value: MMO
-- ValueName: ModularOrder
-- 2023-07-21T10:23:26.983484100Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543525,183,TO_TIMESTAMP('2023-07-21 13:23:26.424','YYYY-MM-DD HH24:MI:SS.US'),100,'EE01','Y','Modular Order',TO_TIMESTAMP('2023-07-21 13:23:26.424','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO','ModularOrder')
;

-- 2023-07-21T10:23:26.997218800Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543525 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2023-07-26T17:04:20.289819600Z
INSERT INTO C_DocType (AD_Client_ID,AD_Org_ID,C_DocType_ID,Created,CreatedBy,DocBaseType,DocumentCopies,EntityType,GL_Category_ID,HasCharges,HasProforma,IsActive,IsCopyDescriptionToDocument,IsCreateCounter,IsDefault,IsDefaultCounterDoc,IsDocNoControlled,IsExcludeFromCommision,IsIndexed,IsInTransit,IsOverwriteDateOnComplete,IsOverwriteSeqOnComplete,IsPickQAConfirm,IsShipConfirm,IsSOTrx,IsSplitWhenDifference,Name,PrintName,Updated,UpdatedBy) VALUES (1000000,1000000,541107,TO_TIMESTAMP('2023-07-26 20:04:20.262','YYYY-MM-DD HH24:MI:SS.US'),100,'MMO',1,'de.metas.swat',1000000,'N','N','Y','Y','Y','N','N','Y','N','N','N','N','N','N','N','N','N','Modular Order','Modular Order',TO_TIMESTAMP('2023-07-26 20:04:20.262','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-07-26T17:04:20.382505400Z
INSERT INTO C_DocType_Trl (AD_Language,C_DocType_ID, Description,DocumentNote,Name,PrintName, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_DocType_ID, t.Description,t.DocumentNote,t.Name,t.PrintName, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_DocType t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_DocType_ID=541107 AND NOT EXISTS (SELECT 1 FROM C_DocType_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_DocType_ID=t.C_DocType_ID)
;

-- 2023-07-26T17:04:20.390503300Z
INSERT INTO AD_Document_Action_Access (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) (SELECT 1000000,0,'Y', now(),100, now(),100, doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID FROM AD_Client client INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID) WHERE client.AD_Client_ID=1000000 AND doctype.C_DocType_ID=541107 AND rol.IsManual='N')
;

-- 2023-07-26T17:04:25.830586800Z
UPDATE C_DocType SET DocumentCopies=0,Updated=TO_TIMESTAMP('2023-07-26 20:04:25.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541107
;

-- 2023-07-26T17:04:43.003040100Z
UPDATE C_DocType SET DocNoSequence_ID=545473,Updated=TO_TIMESTAMP('2023-07-26 20:04:43.003','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541107
;

-- 2023-07-26T17:05:39.964205Z
UPDATE C_DocType SET AD_Org_ID=0,Updated=TO_TIMESTAMP('2023-07-26 20:05:39.964','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=541107
;

-- Name: C_DocType MFG
-- 2023-07-21T10:40:16.730954800Z
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType IN (''MOP'',''MOF'',''MQO'', ''MRO'', ''MMO'')',Updated=TO_TIMESTAMP('2023-07-21 13:40:16.73','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540528
;
