
-- 02.11.2015 14:48
-- URL zum Konzept
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,543691,0,TO_TIMESTAMP('2015-11-02 14:48:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh','Y','Das Lager {0} hat keine Produktionsstätte, deshalb kann keine Bestellkontrolle für die Spedition erstellt werden.

Diese Fehlermeldung kann mit AD_SysConfig de.metas.fresh.ordercheckup.FailOnOrderWarehouseHasNoPlant deaktiviert werden.','E',TO_TIMESTAMP('2015-11-02 14:48:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.fresh.ordercheckup.OrderWarehouseHasNoPlant')
;

-- 02.11.2015 14:48
-- URL zum Konzept
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Message t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Message_ID=543691 AND NOT EXISTS (SELECT * FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 02.11.2015 14:52
-- URL zum Konzept
UPDATE AD_Message SET MsgText='Das Lager {0} hat keine Produktionsstätte, deshalb kann keine Bestellkontrolle für die Spedition erstellt werden.
Hinweis: diese Fehlermeldung kann mit AD_SysConfig {1} deaktiviert werden.',Updated=TO_TIMESTAMP('2015-11-02 14:52:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Message_ID=543691
;

-- 02.11.2015 14:52
-- URL zum Konzept
UPDATE AD_Message_Trl SET IsTranslated='N' WHERE AD_Message_ID=543691
;

-- 02.11.2015 14:57
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,540911,'S',TO_TIMESTAMP('2015-11-02 14:57:22','YYYY-MM-DD HH24:MI:SS'),100,'If an order''S M_Warehouse has no plant set, there can be no order-checkup-reports for the transporter. In this case, this SysConfig decides if creating any checkup-report shall fail with an exception.','de.metas.fresh','Y','de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant',TO_TIMESTAMP('2015-11-02 14:57:22','YYYY-MM-DD HH24:MI:SS'),100,'Y')
;

--
-- also update the ET of 2 existing ordercheckup-sysconfig records
--
-- 02.11.2015 14:57
-- URL zum Konzept
UPDATE AD_SysConfig SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2015-11-02 14:57:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540872
;

-- 02.11.2015 14:57
-- URL zum Konzept
UPDATE AD_SysConfig SET EntityType='de.metas.fresh',Updated=TO_TIMESTAMP('2015-11-02 14:57:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540871
;

--
-- also log changes in M_Warehouse and S_Resource (i.e. plant), to make sure we know what went on
--
-- 02.11.2015 15:08
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-02 15:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=190
;
-- 02.11.2015 15:08
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2015-11-02 15:08:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=487
;


