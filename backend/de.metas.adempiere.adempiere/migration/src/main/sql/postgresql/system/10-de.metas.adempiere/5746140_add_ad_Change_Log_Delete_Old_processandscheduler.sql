/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- Value: AD_ChangeLog_Delete_Old
-- Classname: org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old
-- 2025-02-09T22:05:48.784Z
INSERT INTO AD_Process (AccessLevel,AD_Client_ID,AD_Org_ID,AD_Process_ID,AllowProcessReRun,Classname,CopyFromProcess,Created,CreatedBy,EntityType,IsActive,IsApplySecuritySettings,IsBetaFunctionality,IsDirectPrint,IsFormatExcelFile,IsLogWarning,IsNotifyUserAfterExecution,IsOneInstanceOnly,IsReport,IsTranslateExcelHeaders,IsUpdateExportDate,IsUseBPartnerLanguage,LockWaitTimeout,Name,PostgrestResponseFormat,RefreshAllAfterExecution,ShowHelp,SpreadsheetFormat,TechnicalNote,Type,Updated,UpdatedBy,Value) VALUES ('7',0,0,585451,'Y','org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old','N',TO_TIMESTAMP('2025-02-09 22:05:48.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','Y','N','N','N','N','Y','N','Y',0,'Alte Änderungshistorie Einträge löschen','json','N','N','xls','scheduled','Java',TO_TIMESTAMP('2025-02-09 22:05:48.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'AD_ChangeLog_Delete_Old')
;

-- 2025-02-09T22:05:48.790Z
INSERT INTO AD_Process_Trl (AD_Language,AD_Process_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_ID=585451 AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_ID=t.AD_Process_ID)
;

-- Process: AD_ChangeLog_Delete_Old(org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old)
-- 2025-02-09T22:06:09.672Z
UPDATE AD_Process_Trl SET IsTranslated='Y', Name='Delete old change log entries',Updated=TO_TIMESTAMP('2025-02-09 22:06:09.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Process_ID=585451
;

-- Process: AD_ChangeLog_Delete_Old(org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old)
-- 2025-02-09T22:06:10.429Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-09 22:06:10.429000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Process_ID=585451
;

-- Process: AD_ChangeLog_Delete_Old(org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old)
-- 2025-02-09T22:06:11.461Z
UPDATE AD_Process_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-02-09 22:06:11.461000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Process_ID=585451
;

-- 2025-02-10T07:49:20.153Z
UPDATE AD_Scheduler SET CronPattern=NULL, IsActive='N',Updated=TO_TIMESTAMP('2025-02-10 07:49:20.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=1000010
;

-- 2025-02-10T07:51:07.322Z
INSERT INTO AD_Scheduler (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Role_ID,AD_Scheduler_ID,Created,CreatedBy,CronPattern,EntityType,Frequency,IsActive,IsIgnoreProcessingTime,KeepLogDays,Name,Processing,SchedulerProcessType,ScheduleType,Status,Supervisor_ID,Updated,UpdatedBy) VALUES (1000000,1000000,585451,540024,550117,TO_TIMESTAMP('2025-02-10 07:51:07.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'5 4 * * *','C',0,'Y','N',7,'AD_Change_Log_Delete_Old','N','P','C','NEW',100,TO_TIMESTAMP('2025-02-10 07:51:07.286000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-02-10T07:51:13.291Z
UPDATE AD_Scheduler SET IsActive='N',Updated=TO_TIMESTAMP('2025-02-10 07:51:13.291000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Scheduler_ID=550117
;

-- SysConfig Name: org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old.deleteBatchSize
-- SysConfig Value: 10000
-- 2025-02-10T08:10:29.934Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541752,'S',TO_TIMESTAMP('2025-02-10 08:10:29.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','org.adempiere.ad.table.process.AD_ChangeLog_Delete_Old.deleteBatchSize',TO_TIMESTAMP('2025-02-10 08:10:29.813000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'10000')
;
