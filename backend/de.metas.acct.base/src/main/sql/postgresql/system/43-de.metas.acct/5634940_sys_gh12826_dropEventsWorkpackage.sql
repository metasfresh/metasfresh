-- UI Element: Ereignis Log -> Ereignis Log.Asynchrone Verarbeitungswarteschlange
-- Column: AD_EventLog.C_Queue_WorkPackage_ID
-- 2022-04-11T14:39:26.910Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=600476
;

-- 2022-04-11T14:39:26.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=678617
;

-- Field: Ereignis Log -> Ereignis Log -> Asynchrone Verarbeitungswarteschlange
-- Column: AD_EventLog.C_Queue_WorkPackage_ID
-- 2022-04-11T14:39:26.927Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=678617
;

-- 2022-04-11T14:39:26.928Z
DELETE FROM AD_Field WHERE AD_Field_ID=678617
;

-- 2022-04-11T14:39:26.975Z
/* DDL */ SELECT public.db_alter_table('AD_EventLog','ALTER TABLE AD_EventLog DROP COLUMN IF EXISTS C_Queue_WorkPackage_ID')
;

-- Column: AD_EventLog.C_Queue_WorkPackage_ID
-- 2022-04-11T14:39:27.012Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=579208
;

-- 2022-04-11T14:39:27.014Z
DELETE FROM AD_Column WHERE AD_Column_ID=579208
;

-- Deactivate workpackage
UPDATE C_Queue_Processor_Assign SET isactive='N', UpdatedBy=99, Updated='2022-04-14 13:55:47.057538+03' WHERE c_queue_processor_assign_id = 540000;
UPDATE C_Queue_Processor SET isactive='N', UpdatedBy=99, Updated='2022-04-14 13:55:47.057538+03' WHERE c_queue_processor_id = 540064;
UPDATE C_Queue_PackageProcessor SET isactive='N', UpdatedBy=99, Updated='2022-04-14 13:55:47.057538+03' WHERE C_Queue_PackageProcessor_ID = 540094;

-- Column: AD_EventLog.EventTypeName
-- 2022-04-12T18:24:04.101Z
UPDATE AD_Column SET FieldLength=25,Updated=TO_TIMESTAMP('2022-04-12 21:24:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558434
;

-- 2022-04-12T18:24:48.168Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543155,540802,TO_TIMESTAMP('2022-04-12 21:24:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.event','Y','distributed',TO_TIMESTAMP('2022-04-12 21:24:48','YYYY-MM-DD HH24:MI:SS'),100,'DISTRIBUTED','DISTRIBUTED')
;

-- 2022-04-12T18:24:48.171Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543155 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-04-12T18:26:52.564Z
INSERT INTO t_alter_column values('ad_eventlog','EventTypeName','VARCHAR(25)',null,null)
;