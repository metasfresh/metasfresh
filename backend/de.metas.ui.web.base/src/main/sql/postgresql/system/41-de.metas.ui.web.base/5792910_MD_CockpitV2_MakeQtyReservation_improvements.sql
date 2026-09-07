-- Run mode: SWING_CLIENT

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyAvailableTU
-- 2026-03-07T20:44:43.550Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,ReadOnlyLogic,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,584637,0,585588,543149,11,'QtyAvailableTU',TO_TIMESTAMP('2026-03-07 20:44:43.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Verfügbare TU Menge (Lagerbestand - Reserviert)','D',10,'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge','Y','N','Y','N','N','N','Verfügbare TU Menge','1=0',20,'N',TO_TIMESTAMP('2026-03-07 20:44:43.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-07T20:44:43.555Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543149 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyOrdered_TU
-- 2026-03-07T20:44:49.855Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-03-07 20:44:49.855000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543148
;

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyOrderedNotReserved_TU
-- 2026-03-07T20:52:03.432Z
UPDATE AD_Process_Para SET AD_Element_ID=584638, ColumnName='QtyOrderedNotReserved_TU', Description='Bestellte TU-Menge ohne Reservierung', Help='Bestellte TU-Menge abzüglich bereits reservierter TU-Menge', Name='Bestellt, nicht reserviert (TU)',Updated=TO_TIMESTAMP('2026-03-07 20:52:03.432000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543148
;

-- 2026-03-07T20:52:03.434Z
UPDATE AD_Process_Para_Trl trl SET Description='Bestellte TU-Menge ohne Reservierung',Help='Bestellte TU-Menge abzüglich bereits reservierter TU-Menge',Name='Bestellt, nicht reserviert (TU)' WHERE AD_Process_Para_ID=543148 AND AD_Language='de_DE'
;

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyOrderedNotReserved_TU
-- 2026-03-07T21:22:07.309Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2026-03-07 21:22:07.308000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543148
;

-- Process: MD_CockpitV2_MakeQtyReservation(de.metas.ui.web.material.cockpit.v2.reservation.MD_CockpitV2_MakeQtyReservation)
-- ParameterName: QtyAvailableTU
-- 2026-03-07T21:22:08.910Z
UPDATE AD_Process_Para SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2026-03-07 21:22:08.910000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543149
;

