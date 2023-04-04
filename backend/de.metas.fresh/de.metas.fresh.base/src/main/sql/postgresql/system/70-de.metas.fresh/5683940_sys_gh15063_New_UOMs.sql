--
-- Script: /tmp/webui_migration_scripts_2023-04-04_6138700415176216555/5683940_migration_2023-04-04_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- 2023-04-04T08:01:43.713Z
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,IsActive,IsDefault,Name,StdPrecision,Updated,UpdatedBy,X12DE355) VALUES (1000000,0,0,TO_TIMESTAMP('2023-04-04 10:01:43','YYYY-MM-DD HH24:MI:SS'),100,540058,'Y','N','Drum',0,TO_TIMESTAMP('2023-04-04 10:01:43','YYYY-MM-DD HH24:MI:SS'),100,'DR')
;

-- 2023-04-04T08:01:43.726Z
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_UOM t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_UOM_ID=540058 AND NOT EXISTS (SELECT 1 FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

-- 2023-04-04T08:01:45.949Z
UPDATE C_UOM SET StdPrecision=2,Updated=TO_TIMESTAMP('2023-04-04 10:01:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540058
;

-- 2023-04-04T08:01:47.563Z
UPDATE C_UOM SET CostingPrecision=2,Updated=TO_TIMESTAMP('2023-04-04 10:01:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540058
;

-- 2023-04-04T08:02:12.517Z
UPDATE C_UOM SET UOMSymbol='DR',Updated=TO_TIMESTAMP('2023-04-04 10:02:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_UOM_ID=540058
;

-- 2023-04-04T08:02:12.518Z
UPDATE C_UOM_Trl trl SET UOMSymbol='DR' WHERE C_UOM_ID=540058 AND (IsTranslated='N' OR AD_Language='en_US')
;

-- 2023-04-04T08:02:59.909Z
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,IsActive,IsDefault,Name,StdPrecision,UOMSymbol,Updated,UpdatedBy,X12DE355) VALUES (1000000,0,2,TO_TIMESTAMP('2023-04-04 10:02:59','YYYY-MM-DD HH24:MI:SS'),100,540059,'Y','N','Hectoliter pure alcohol',2,'HLA',TO_TIMESTAMP('2023-04-04 10:02:59','YYYY-MM-DD HH24:MI:SS'),100,'HLA')
;

-- 2023-04-04T08:02:59.912Z
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_UOM t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_UOM_ID=540059 AND NOT EXISTS (SELECT 1 FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

-- 2023-04-04T08:04:42.592Z
INSERT INTO C_UOM (AD_Client_ID,AD_Org_ID,CostingPrecision,Created,CreatedBy,C_UOM_ID,IsActive,IsDefault,Name,StdPrecision,UOMSymbol,Updated,UpdatedBy,X12DE355) VALUES (1000000,0,2,TO_TIMESTAMP('2023-04-04 10:04:42','YYYY-MM-DD HH24:MI:SS'),100,540060,'Y','N','Sheet',2,'SHT',TO_TIMESTAMP('2023-04-04 10:04:42','YYYY-MM-DD HH24:MI:SS'),100,'SHT')
;

-- 2023-04-04T08:04:42.595Z
INSERT INTO C_UOM_Trl (AD_Language,C_UOM_ID, Description,Name,UOMSymbol, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.C_UOM_ID, t.Description,t.Name,t.UOMSymbol, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, C_UOM t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.C_UOM_ID=540060 AND NOT EXISTS (SELECT 1 FROM C_UOM_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.C_UOM_ID=t.C_UOM_ID)
;

