-- Run mode: WEBUI

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: CostingMethod
-- 2025-07-09T12:24:51.362Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,241,0,585275,1000001,17,'CostingMethod',TO_TIMESTAMP('2025-07-09 12:24:51.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gibt an, wie die Kosten berechnet werden','U',0,'"Kostenrechnungsmethode" gibt an, wie die Kosten berechnet werden (Standard, Durchschnitt, LiFo, FiFo). Die Standardmethode ist auf Ebene des Kontenrahmens definiert und kann optional in der Produktkategorie Ã¼berschrieben werden. Die Methode der Kostenrechnung kann nicht zur Art der Materialbewegung (definiert in der Produktkategorie) im Widerspruch stehen.','Y','N','Y','N','N','N','Kostenrechnungsmethode',20,TO_TIMESTAMP('2025-07-09 12:24:51.352000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-09T12:24:51.364Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=1000001 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: CostingMethod
-- 2025-07-09T12:26:02.400Z
UPDATE AD_Process_Para SET AD_Reference_Value_ID=122,Updated=TO_TIMESTAMP('2025-07-09 12:26:02.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=1000001
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: CostingMethod
-- 2025-07-09T12:26:07.466Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=242,Updated=TO_TIMESTAMP('2025-07-09 12:26:07.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=1000001
;

-- Process: M_Inventory_RecomputeCosts(de.metas.inventory.process.M_Inventory_RecomputeCosts)
-- ParameterName: CostingMethod
-- 2025-07-09T12:27:31.309Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2025-07-09 12:27:31.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=1000001
;
