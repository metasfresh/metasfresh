
DROP INDEX IF EXISTS modcntr_settings_calender_year_product_issotrx_unique_idx;





-- 2023-11-27T16:14:51.065Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,ErrorMsg,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540780,0,542340,TO_TIMESTAMP('2023-11-27 18:14:51.061','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','A set of modular contract settings already exists for the same calendar, year, product and transaction type.','Y','N','modcntr_settings_calender_year_product_issotrx_unique_idx','N',TO_TIMESTAMP('2023-11-27 18:14:51.061','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:14:51.066Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540780 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-11-27T16:15:23.841Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586796,541377,540780,0,TO_TIMESTAMP('2023-11-27 18:15:23.839','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2023-11-27 18:15:23.839','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:15:27.337Z
UPDATE AD_Index_Table SET IsUnique='Y',Updated=TO_TIMESTAMP('2023-11-27 18:15:27.337','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780
;

-- 2023-11-27T16:16:37.462Z
UPDATE AD_Index_Table SET AD_Table_ID=542339,Updated=TO_TIMESTAMP('2023-11-27 18:16:37.462','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780
;

-- 2023-11-27T16:16:45.872Z
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=541377
;

-- 2023-11-27T16:16:55.808Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586783,541379,540780,0,TO_TIMESTAMP('2023-11-27 18:16:55.806','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',10,TO_TIMESTAMP('2023-11-27 18:16:55.806','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:17:04.014Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586791,541380,540780,0,TO_TIMESTAMP('2023-11-27 18:17:04.012','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',20,TO_TIMESTAMP('2023-11-27 18:17:04.012','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:17:12.001Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586792,541381,540780,0,TO_TIMESTAMP('2023-11-27 18:17:12.0','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',30,TO_TIMESTAMP('2023-11-27 18:17:12.0','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:17:19.795Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586790,541382,540780,0,TO_TIMESTAMP('2023-11-27 18:17:19.794','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',40,TO_TIMESTAMP('2023-11-27 18:17:19.794','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:17:36.085Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,587164,541383,540780,0,TO_TIMESTAMP('2023-11-27 18:17:36.084','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y',50,TO_TIMESTAMP('2023-11-27 18:17:36.084','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-27T16:17:47.524Z
UPDATE AD_Index_Table SET ErrorMsg='A set of modular contract settings already exists for the same calendar, year, product and sales transaction.',Updated=TO_TIMESTAMP('2023-11-27 18:17:47.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780
;

-- 2023-11-27T16:17:47.528Z
UPDATE AD_Index_Table_Trl trl SET ErrorMsg='A set of modular contract settings already exists for the same calendar, year, product and sales transaction.' WHERE AD_Index_Table_ID=540780 AND AD_Language='de_DE'
;



-- 2023-11-27T16:19:50.423Z
CREATE UNIQUE INDEX modcntr_settings_calender_year_product_issotrx_unique_idx ON ModCntr_Settings (AD_Org_ID,C_Calendar_ID,C_Year_ID,M_Product_ID,IsSOTrx)
;







-- Run mode: SWING_CLIENT

-- 2023-11-28T09:23:51.553Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='A set of modular contract settings already exists for the same calendar, year, product and sales transaction.',Updated=TO_TIMESTAMP('2023-11-28 11:23:51.551','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780 AND AD_Language='en_US'
;

-- 2023-11-28T09:24:08.759Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='A set of modular contract settings already exists for the same combination of calendar, year, product and sales transaction.',Updated=TO_TIMESTAMP('2023-11-28 11:24:08.758','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780 AND AD_Language='en_US'
;

-- 2023-11-28T09:24:22.751Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es wurden bereits Einstellungen für modulare Verträge festgelegt für dieselbe Kombination von Kalender, Jahr, Produkt und Verkaufsvorgang.',Updated=TO_TIMESTAMP('2023-11-28 11:24:22.749','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780 AND AD_Language='de_CH'
;

-- 2023-11-28T09:24:26.454Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es wurden bereits Einstellungen für modulare Verträge festgelegt für dieselbe Kombination von Kalender, Jahr, Produkt und Verkaufsvorgang.',Updated=TO_TIMESTAMP('2023-11-28 11:24:26.452','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780 AND AD_Language='it_IT'
;

-- 2023-11-28T09:24:29.283Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es wurden bereits Einstellungen für modulare Verträge festgelegt für dieselbe Kombination von Kalender, Jahr, Produkt und Verkaufsvorgang.',Updated=TO_TIMESTAMP('2023-11-28 11:24:29.282','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780 AND AD_Language='de_DE'
;

-- 2023-11-28T09:24:29.284Z
UPDATE AD_Index_Table SET ErrorMsg='Es wurden bereits Einstellungen für modulare Verträge festgelegt für dieselbe Kombination von Kalender, Jahr, Produkt und Verkaufsvorgang.' WHERE AD_Index_Table_ID=540780
;

-- 2023-11-28T09:24:32.170Z
UPDATE AD_Index_Table_Trl SET ErrorMsg='Es wurden bereits Einstellungen für modulare Verträge festgelegt für dieselbe Kombination von Kalender, Jahr, Produkt und Verkaufsvorgang.',Updated=TO_TIMESTAMP('2023-11-28 11:24:32.169','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Index_Table_ID=540780 AND AD_Language='fr_CH'
;






