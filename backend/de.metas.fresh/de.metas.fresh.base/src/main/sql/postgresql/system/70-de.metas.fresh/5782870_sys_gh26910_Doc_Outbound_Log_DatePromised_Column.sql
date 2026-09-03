-- Column: C_Doc_Outbound_Log.DatePromised
-- 2025-10-28T20:30:24.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) SELECT 0,591445,269,0,15,540453,'XX','DatePromised','(SELECT o.DatePromised from C_Order o where o.C_Order_ID = C_Doc_Outbound_Log.Record_ID  AND get_table_id(''C_Order'') = C_Doc_Outbound_Log.AD_Table_ID)',TO_TIMESTAMP('2025-10-28 20:30:23.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Zugesagter Termin für diesen Auftrag','de.metas.document.archive',0,7,'Der "Zugesagte Termin" gibt das Datum an, für den (wenn zutreffend) dieser Auftrag zugesagt wurde.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Zugesagter Termin',0,0,TO_TIMESTAMP('2025-10-28 20:30:23.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0
WHERE NOT EXISTS (
    SELECT 1
    FROM AD_Column
    WHERE AD_Table_ID = 540453
      AND ColumnName = 'DatePromised'
);

-- 2025-10-28T20:30:24.462Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591445 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-10-28T20:30:24.574Z
/* DDL */  select update_Column_Translation_From_AD_Element(269)
;

-- Column: C_Doc_Outbound_Log.DatePromised
-- 2025-10-28T20:32:40.899Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-10-28 20:32:40.899000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591445
;

-- Column: C_Doc_Outbound_Log.DatePromised
-- 2025-10-28T20:40:52.858Z
UPDATE AD_Column SET AD_Reference_ID=16,Updated=TO_TIMESTAMP('2025-10-28 20:40:52.858000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591445
;

-- Column: C_Doc_Outbound_Log.DatePromised
-- Column SQL (old): (SELECT o.DatePromised from C_Order o where o.C_Order_ID = C_Doc_Outbound_Log.Record_ID  AND 259 = C_Doc_Outbound_Log.AD_Table_ID)
-- 2025-10-28T20:42:44.069Z
UPDATE AD_Column SET AD_Reference_ID=15, ColumnSQL='(SELECT o.DatePromised::Date from C_Order o where o.C_Order_ID = C_Doc_Outbound_Log.Record_ID  AND 259 = C_Doc_Outbound_Log.AD_Table_ID)',Updated=TO_TIMESTAMP('2025-10-28 20:42:44.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591445
;

-- Column: C_Doc_Outbound_Log.DatePromised
-- Source Table: C_Order
-- 2026-01-05T15:39:00.693Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Source_Table_ID,SQL_GetTargetRecordIdBySourceRecordId,Updated,UpdatedBy) VALUES (0,591445,0,540192,540453,TO_TIMESTAMP('2026-01-05 15:38:59.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'S','Y',259,'SELECT C_Doc_Outbound_Log_ID FROM C_Doc_Outbound_Log WHERE Record_ID=@Record_ID/-1@',TO_TIMESTAMP('2026-01-05 15:38:59.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
