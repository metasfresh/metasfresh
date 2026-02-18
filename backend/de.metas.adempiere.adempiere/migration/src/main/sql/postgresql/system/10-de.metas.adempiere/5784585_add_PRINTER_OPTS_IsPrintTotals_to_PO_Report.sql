/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Run mode: SWING_CLIENT

-- Process: Bestellung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-22T10:47:10.627Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-22 10:47:10.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543107
;

-- Process: Bestellung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintTotals
-- 2026-01-22T10:47:42.641Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,578552,0,500010,543108,20,'PRINTER_OPTS_IsPrintTotals',TO_TIMESTAMP('2026-01-22 10:47:42.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','D',0,'Y','N','Y','N','N','N','Gesamtbeträge drucken',30,TO_TIMESTAMP('2026-01-22 10:47:42.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T10:47:42.645Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543108 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Bestellung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-22T10:48:17.826Z
UPDATE AD_Process_Para SET SeqNo=25,Updated=TO_TIMESTAMP('2026-01-22 10:48:17.826000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543107
;

-- Process: Bestellung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintTotals
-- 2026-01-22T10:48:22.625Z
UPDATE AD_Process_Para SET SeqNo=20,Updated=TO_TIMESTAMP('2026-01-22 10:48:22.625000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543108
;

-- Process: Bestellung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-22T10:48:24.943Z
UPDATE AD_Process_Para SET SeqNo=30,Updated=TO_TIMESTAMP('2026-01-22 10:48:24.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543107
;

