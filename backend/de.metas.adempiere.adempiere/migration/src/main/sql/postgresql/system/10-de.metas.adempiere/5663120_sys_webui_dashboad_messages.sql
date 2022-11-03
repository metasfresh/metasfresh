-- Value: webui.dashboard.kpis.dropContainer.caption
-- 2022-11-03T20:55:47.739Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545192,0,TO_TIMESTAMP('2022-11-03 22:55:47.363','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Drop KPI widget here.','I',TO_TIMESTAMP('2022-11-03 22:55:47.363','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.kpis.dropContainer.caption')
;

-- 2022-11-03T20:55:47.743Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545192 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.targetIndicators.dropContainer.caption
-- 2022-11-03T20:57:51.665Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545193,0,TO_TIMESTAMP('2022-11-03 22:57:51.535','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Drop Target Indicator widget here.','I',TO_TIMESTAMP('2022-11-03 22:57:51.535','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.targetIndicators.dropContainer.caption')
;

-- 2022-11-03T20:57:51.666Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545193 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.addNew.targetIndicator
-- 2022-11-03T21:01:10.546Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545195,0,TO_TIMESTAMP('2022-11-03 23:01:10.416','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Add Target Indicator widget','I',TO_TIMESTAMP('2022-11-03 23:01:10.416','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.addNew.targetIndicator')
;

-- 2022-11-03T21:01:10.547Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545195 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.addNew.kpi
-- 2022-11-03T21:03:03.180Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545196,0,TO_TIMESTAMP('2022-11-03 23:03:03.054','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Add KPI widget','I',TO_TIMESTAMP('2022-11-03 23:03:03.054','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.addNew.kpi')
;

-- 2022-11-03T21:03:03.180Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545196 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.details
-- 2022-11-03T21:05:30.430Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545197,0,TO_TIMESTAMP('2022-11-03 23:05:30.308','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','DETAILS','I',TO_TIMESTAMP('2022-11-03 23:05:30.308','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.details')
;

-- 2022-11-03T21:05:30.431Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545197 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.maximize
-- 2022-11-03T21:08:17.406Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545198,0,TO_TIMESTAMP('2022-11-03 23:08:17.278','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Maximize','I',TO_TIMESTAMP('2022-11-03 23:08:17.278','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.maximize')
;

-- 2022-11-03T21:08:17.407Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545198 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.minimize
-- 2022-11-03T21:08:33.579Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545199,0,TO_TIMESTAMP('2022-11-03 23:08:33.427','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Minimize','I',TO_TIMESTAMP('2022-11-03 23:08:33.427','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.minimize')
;

-- 2022-11-03T21:08:33.579Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545199 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.minimize
-- 2022-11-03T21:08:36.949Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-03 23:08:36.948','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545199
;

-- Value: webui.dashboard.item.settings.caption
-- 2022-11-03T21:13:38.333Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545200,0,TO_TIMESTAMP('2022-11-03 23:13:38.198','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Caption','I',TO_TIMESTAMP('2022-11-03 23:13:38.198','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.caption')
;

-- 2022-11-03T21:13:38.337Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545200 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.settings.interval
-- 2022-11-03T21:13:50.163Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545201,0,TO_TIMESTAMP('2022-11-03 23:13:50.025','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Interval','I',TO_TIMESTAMP('2022-11-03 23:13:50.025','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.interval')
;

-- 2022-11-03T21:13:50.164Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545201 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.settings.interval
-- 2022-11-03T21:13:53.319Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2022-11-03 23:13:53.319','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545201
;

-- Value: webui.dashboard.item.settings.when
-- 2022-11-03T21:14:13.409Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545202,0,TO_TIMESTAMP('2022-11-03 23:14:13.27','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','When','I',TO_TIMESTAMP('2022-11-03 23:14:13.27','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.when')
;

-- 2022-11-03T21:14:13.410Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545202 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.settings.when.now
-- 2022-11-03T21:17:18.928Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545203,0,TO_TIMESTAMP('2022-11-03 23:17:18.795','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Now','I',TO_TIMESTAMP('2022-11-03 23:17:18.795','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.when.now')
;

-- 2022-11-03T21:17:18.930Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545203 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.settings.lastWeek
-- 2022-11-03T21:17:31.730Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545204,0,TO_TIMESTAMP('2022-11-03 23:17:31.612','YYYY-MM-DD HH24:MI:SS.US'),100,'U','Y','Last week','I',TO_TIMESTAMP('2022-11-03 23:17:31.612','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.lastWeek')
;

-- 2022-11-03T21:17:31.731Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545204 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.settings.when.lastWeek
-- 2022-11-03T21:17:36.586Z
UPDATE AD_Message SET Value='webui.dashboard.item.settings.when.lastWeek',Updated=TO_TIMESTAMP('2022-11-03 23:17:36.585','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545204
;

-- Value: webui.dashboard.item.settings.when.caption
-- 2022-11-03T21:19:31.471Z
UPDATE AD_Message SET Value='webui.dashboard.item.settings.when.caption',Updated=TO_TIMESTAMP('2022-11-03 23:19:31.471','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545202
;

-- Value: webui.dashboard.item.settings.interval.caption
-- 2022-11-03T21:20:12.124Z
UPDATE AD_Message SET Value='webui.dashboard.item.settings.interval.caption',Updated=TO_TIMESTAMP('2022-11-03 23:20:12.124','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Message_ID=545201
;

-- Value: webui.dashboard.item.settings.interval.week
-- 2022-11-03T21:20:26.342Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545205,0,TO_TIMESTAMP('2022-11-03 23:20:26.201','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Week','I',TO_TIMESTAMP('2022-11-03 23:20:26.201','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.interval.week')
;

-- 2022-11-03T21:20:26.343Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545205 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: webui.dashboard.item.settings.save
-- 2022-11-03T21:21:57.857Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545206,0,TO_TIMESTAMP('2022-11-03 23:21:57.737','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Save','I',TO_TIMESTAMP('2022-11-03 23:21:57.737','YYYY-MM-DD HH24:MI:SS.US'),100,'webui.dashboard.item.settings.save')
;

-- 2022-11-03T21:21:57.858Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Message_ID=545206 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

