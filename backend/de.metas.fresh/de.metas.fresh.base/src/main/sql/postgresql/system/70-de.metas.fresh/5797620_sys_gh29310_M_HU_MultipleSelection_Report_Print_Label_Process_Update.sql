/*
 * #%L
 * de.metas.fresh.base
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

-- Value: M_HU_MultipleSelection_Report_Print_Label
-- Classname: de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label
-- 2026-04-09T09:06:37.657Z
UPDATE AD_Process SET IsActive='Y',Updated=TO_TIMESTAMP('2026-04-09 09:06:37.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585354
;

-- Process: M_HU_MultipleSelection_Report_Print_Label(de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label)
-- ParameterName: IsPrintPreview
-- 2026-04-09T09:07:26.527Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,582572,0,585354,543176,20,'IsPrintPreview',TO_TIMESTAMP('2026-04-09 09:07:26.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Generieren Sie PDFs ohne zu drucken.','de.metas.handlingunits',0,'Generieren Sie PDFs ohne zu drucken.','Y','N','Y','N','Y','N','Druckvorschau',30,'N',TO_TIMESTAMP('2026-04-09 09:07:26.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-09T09:07:26.712Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543176 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Value: M_HU_MultipleSelection_Report_Print_Label
-- Classname: de.metas.handlingunits.process.M_HU_MultipleSelection_Report_Print_Label
-- 2026-04-20T08:25:01.662Z
UPDATE AD_Process SET ShowHelp='Y',Updated=TO_TIMESTAMP('2026-04-20 08:25:01.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=585354
;
