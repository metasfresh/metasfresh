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

-- 2023-07-21T10:36:00.691145100Z
UPDATE C_DocType SET Name='Modular Order',Updated=TO_TIMESTAMP('2023-07-21 13:36:00.687','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=1000037
;

-- 2023-07-21T10:36:00.704140Z
UPDATE C_DocType_Trl trl SET Name='Modular Order' WHERE C_DocType_ID=1000037 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2023-07-21T10:36:04.705876600Z
UPDATE C_DocType SET PrintName='Modular Order',Updated=TO_TIMESTAMP('2023-07-21 13:36:04.705','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=1000037
;

-- 2023-07-21T10:36:04.711880800Z
UPDATE C_DocType_Trl trl SET PrintName='Modular Order' WHERE C_DocType_ID=1000037 AND (IsTranslated='N' OR AD_Language='de_DE')
;

-- 2023-07-21T10:36:09.632098900Z
UPDATE C_DocType SET DocBaseType='MMO',Updated=TO_TIMESTAMP('2023-07-21 13:36:09.632','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=1000037
;

-- 2023-07-21T10:36:13.730983500Z
UPDATE C_DocType SET IsDefault='N',Updated=TO_TIMESTAMP('2023-07-21 13:36:13.729','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE C_DocType_ID=1000037
;

-- Name: C_DocType MFG
-- 2023-07-21T10:40:16.730954800Z
UPDATE AD_Val_Rule SET Code='C_DocType.DocBaseType IN (''MOP'',''MOF'',''MQO'', ''MRO'', ''MMO'')',Updated=TO_TIMESTAMP('2023-07-21 13:40:16.73','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Val_Rule_ID=540528
;
