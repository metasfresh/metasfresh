-- Name: AD_Process_HU_Jasper
-- 2023-07-26T10:49:47.049Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541810,TO_TIMESTAMP('2023-07-26 13:49:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.handlingunits','Y','N','AD_Process_HU_Jasper',TO_TIMESTAMP('2023-07-26 13:49:46','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2023-07-26T10:49:47.054Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541810 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: AD_Process_HU_Jasper
-- Table: AD_Process
-- Key: AD_Process.AD_Process_ID
-- 2023-07-26T10:50:23.179Z
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Display,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,OrderByClause,ShowInactiveValues,Updated,UpdatedBy,WhereClause) VALUES (0,2809,2801,0,541810,284,TO_TIMESTAMP('2023-07-26 13:50:23','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','N','AD_Process.Name','N',TO_TIMESTAMP('2023-07-26 13:50:23','YYYY-MM-DD HH24:MI:SS'),100,'AD_Process.IsActive=''Y'' AND AD_Process.JasperReport IS NOT NULL AND AD_Process.JasperReport ilike ''%QR_Label%''')
;

-- Reference: AD_Process_HU_Jasper
-- Table: AD_Process
-- Key: AD_Process.AD_Process_ID
-- 2023-07-26T10:50:50.694Z
UPDATE AD_Ref_Table SET EntityType='de.metas.handlingunits', WhereClause='',Updated=TO_TIMESTAMP('2023-07-26 13:50:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541810
;

-- Process: M_HU_Report_QRCode(de.metas.handlingunits.process.M_HU_Report_QRCode)
-- ParameterName: AD_Process_ID
-- 2023-07-26T10:51:01.287Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=541810,Updated=TO_TIMESTAMP('2023-07-26 13:51:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=542665
;

