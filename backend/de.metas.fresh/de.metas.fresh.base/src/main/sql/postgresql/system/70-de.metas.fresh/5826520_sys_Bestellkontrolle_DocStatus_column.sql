-- Run mode: SWING_CLIENT

-- Bestellkontrolle -- give C_Order_MFGWarehouse_Report a DocStatus column defaulting to 'CO' (Completed).
-- The doc-outbound log copies the source record's DocStatus through the document engine
-- (getDocStatusOrNull -> DocumentWrapper.getDocStatus -> the record's DocStatus column). Without this
-- column every Bestellkontrolle outbound log carries a null status. A companion DocumentHandler
-- (de.metas.fresh code) makes the record wrap-able so this value is read.

-- AD metadata: the DocStatus column (element 289 "Belegstatus", List reference 131 "_Document Status")
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version)
SELECT 0,593646 /*From ID Server*/,289,0,17,131,540683,'DocStatus',TO_TIMESTAMP('2026-09-25 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'CO','Der aktuelle Status des Belegs','de.metas.fresh',2,'Der Belegstatus zeigt den aktuellen Status eines Belegs an. Bestellkontrollen werden mit Status ''Fertig gestellt'' (CO) erzeugt.','Y','Y','N','N','N','N','N','N','N','N','N','Belegstatus','NP',TO_TIMESTAMP('2026-09-25 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column c WHERE c.AD_Column_ID=593646)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593646 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Physical DDL: add the column with a non-volatile 'CO' default, which also populates existing rows.
/* DDL */ SELECT public.db_alter_table('C_Order_MFGWarehouse_Report','ALTER TABLE public.C_Order_MFGWarehouse_Report ADD COLUMN DocStatus CHAR(2) DEFAULT ''CO''')
;

-- Backfill: make the intent explicit for any row the ADD COLUMN default did not cover.
UPDATE C_Order_MFGWarehouse_Report
SET DocStatus = 'CO',
    Updated = TO_TIMESTAMP('2026-09-25 12:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 99
WHERE DocStatus IS NULL
;

SELECT add_missing_translations();
