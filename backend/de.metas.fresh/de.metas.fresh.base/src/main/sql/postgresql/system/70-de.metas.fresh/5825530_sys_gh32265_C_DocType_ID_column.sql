-- Run mode: SWING_CLIENT

-- Column: C_Order_MFGWarehouse_Report.C_DocType_ID
-- reuses existing AD_Element 196 (ColumnName=C_DocType_ID, Name=Belegart) — no new AD_Element
-- 2026-09-21T12:00:00.000000
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version)
SELECT 0,593637,196,0,30,540683,'C_DocType_ID',TO_TIMESTAMP('2026-09-21 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','de.metas.fresh',10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','Y','N','N','N','N','N','N','N','N','N','Belegart','NP',TO_TIMESTAMP('2026-09-21 12:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column c WHERE c.AD_Column_ID=593637)
;

-- 2026-09-21T12:00:00.000000
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-21T12:00:00.000000
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- DDL
/* DDL */ SELECT public.db_alter_table('C_Order_MFGWarehouse_Report','ALTER TABLE public.C_Order_MFGWarehouse_Report ADD COLUMN C_DocType_ID NUMERIC(10)')
;
