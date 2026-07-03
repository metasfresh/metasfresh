-- Run mode: SWING_CLIENT

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- ParameterName: IsConfirmedBySupplier
-- 2026-07-02T08:50:29.886Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584306,0,585538,543260,20,'IsConfirmedBySupplier',TO_TIMESTAMP('2026-07-02 08:50:29.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,'Y','N','Y','N','N','N','Bestätigt durch Lieferant',30,'N',TO_TIMESTAMP('2026-07-02 08:50:29.322000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-02T08:50:29.953Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543260 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2026-07-02T08:50:30.046Z
/* DDL */  select update_Process_Para_Translation_From_AD_Element(584306)
;

-- Process: M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference(de.metas.inoutcandidate.process.M_ReceiptSchedule_ChangeDatePromised_OverrideAndPOReference)
-- ParameterName: IsConfirmedBySupplier
-- 2026-07-02T14:07:32.298Z
UPDATE AD_Process_Para SET AD_Reference_ID=17, AD_Reference_Value_ID=540528, DefaultValue='',Updated=TO_TIMESTAMP('2026-07-02 14:07:32.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543260
;

-- Column: M_ReceiptSchedule.IsConfirmedBySupplier
-- 2026-07-02T08:51:49.330Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-07-02 08:51:49.330000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591673
;
