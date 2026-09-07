-- Run mode: SWING_CLIENT

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyTU
-- 2026-03-07T20:21:25.669Z
UPDATE AD_Process_Para SET SeqNo=100,Updated=TO_TIMESTAMP('2026-03-07 20:21:25.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543136
;

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyOrdered_TU
-- 2026-03-07T20:23:03.231Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,542506,0,585588,543148,11,'QtyOrdered_TU',TO_TIMESTAMP('2026-03-07 20:23:03.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellte Menge (TU)','de.metas.handlingunits',10,'Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','Y','N','N','N','Bestellte Menge (TU)','1=0',10,'N',TO_TIMESTAMP('2026-03-07 20:23:03.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T20:23:03.244Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543148 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

