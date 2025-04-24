-- Value: C_UOM_Conversion_CatchWeight_NotExists
-- 2025-04-24T10:44:49.322Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545537,0,TO_TIMESTAMP('2025-04-24 10:44:49.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','No UOM conversion with catch UOM exists.','I',TO_TIMESTAMP('2025-04-24 10:44:49.318000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'C_UOM_Conversion_CatchWeight_NotExists')
;

-- 2025-04-24T10:44:49.323Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545537 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: C_UOM_Conversion_CatchWeight_NotExists
-- 2025-04-24T10:45:00.658Z
UPDATE AD_Message_Trl SET MsgText='Es gibt noch keine Maßeinheit Umrechnung zu einer Catch-Maßeinheit.',Updated=TO_TIMESTAMP('2025-04-24 10:45:00.658000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545537
;

-- 2025-04-24T10:45:00.660Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_UOM_Conversion_CatchWeight_NotExists
-- 2025-04-24T10:45:02.063Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-24 10:45:02.063000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545537
;

-- Value: C_UOM_Conversion_CatchWeight_NotExists
-- 2025-04-24T10:45:09.170Z
UPDATE AD_Message_Trl SET MsgText='Es gibt noch keine Maßeinheit Umrechnung zu einer Catch-Maßeinheit.',Updated=TO_TIMESTAMP('2025-04-24 10:45:09.170000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545537
;

-- 2025-04-24T10:45:09.171Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: C_UOM_Conversion_CatchWeight_NotExists
-- 2025-04-24T10:45:09.272Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-24 10:45:09.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545537
;

-- Name: M_ProductPrice Catchweight has UOM Conversion
-- 2025-04-24T10:51:46.434Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540716,TO_TIMESTAMP('2025-04-24 10:51:46.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','M_ProductPrice Catchweight has UOM Conversion','S',TO_TIMESTAMP('2025-04-24 10:51:46.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: M_ProductPrice Catchweight has UOM Conversion
-- 2025-04-24T10:51:49.692Z
UPDATE AD_Val_Rule SET Code='EXISTS (
    SELECT 1
    FROM M_ProductPrice pp
             JOIN M_Product p
                  ON pp.M_Product_ID = p.M_Product_ID
    WHERE pp.M_ProductPrice_ID = M_ProductPrice.M_ProductPrice_ID
      and pp.invoicableqtybasedon = ''CatchWeight''
      AND ( (EXISTS (SELECT 1
                                                  FROM C_UOM_Conversion c
                                                  WHERE p.C_UOM_ID = c.C_UOM_ID
                                                    AND p.M_PRODUCT_ID = c.M_Product_ID
                                                    AND c.IsActive = ''Y''
                                                    AND c.iscatchuomforproduct=''Y''
                                                    AND M_ProductPrice.C_UOM_ID = C.C_UOM_TO_ID))
        OR (EXISTS (SELECT 1
                    FROM C_UOM_Conversion c
                    WHERE p.C_UOM_ID = c.C_UOM_ID
                      AND c.M_Product_ID IS NULL
                      AND c.IsActive = ''Y''
                      AND c.iscatchuomforproduct=''Y''
                      AND M_ProductPrice.C_UOM_ID = c.C_UOM_TO_ID))))',Updated=TO_TIMESTAMP('2025-04-24 10:51:49.692000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540716
;

-- 2025-04-24T10:53:10.771Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540015,0,0,251,TO_TIMESTAMP('2025-04-24 10:53:10.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Catchweight Product Price must have catchweight conversion',TO_TIMESTAMP('2025-04-24 10:53:10.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540716,545537)
;



-- 2025-04-24T11:02:45.096Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540015,540020,0,0,TO_TIMESTAMP('2025-04-24 11:02:45.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',251,'M_ProductPrice_ID',TO_TIMESTAMP('2025-04-24 11:02:45.080000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

