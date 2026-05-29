
-- Name: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T11:49:36.282Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540730,TO_TIMESTAMP('2025-06-02 11:49:36.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','PP_ProductBOM_CoANDByProducts_NegativeQty','S',TO_TIMESTAMP('2025-06-02 11:49:36.267000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Name: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T11:58:57.980Z
UPDATE AD_Val_Rule SET Code='DocStatus IN (''CO, CL'')
   OR (NOT EXISTS (SELECT 1
                   FROM PP_Product_BOMLine bomline
                   WHERE bomline.PP_Product_BOM_ID = PP_Product_BOM.PP_Product_BOM_ID
                     AND bomline.componentType IN (''BY'', ''CP'')
                     AND ((isqtypercentage = ''Y'' AND qtybatch >= 0) OR (isqtypercentage = ''N'' AND qtybom >= 0))
					 AND bomline.IsActive = ''Y'')
    )',Updated=TO_TIMESTAMP('2025-06-02 11:58:57.980000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540730
;

-- Name: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T11:59:08.240Z
UPDATE AD_Val_Rule SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-02 11:59:08.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Val_Rule_ID=540730
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T11:59:48.330Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545555,0,TO_TIMESTAMP('2025-06-02 11:59:48.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','"For products with component type: By- / Co-Product, the quantity must be negative.
Für Produkte der Art Neben- / Co-Produkt muss die Menge negativ sein."','I',TO_TIMESTAMP('2025-06-02 11:59:48.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'PP_ProductBOM_CoANDByProducts_NegativeQty')
;

-- 2025-06-02T11:59:48.332Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545555 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T11:59:52.710Z
UPDATE AD_Message SET MsgText='"For products with component type: By- / Co-Product, the quantity must be negative.',Updated=TO_TIMESTAMP('2025-06-02 11:59:52.710000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545555
;

-- 2025-06-02T11:59:52.717Z
UPDATE AD_Message_Trl trl SET MsgText='"For products with component type: By- / Co-Product, the quantity must be negative.' WHERE AD_Message_ID=545555 AND AD_Language='de_DE'
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:00:02.585Z
UPDATE AD_Message SET MsgText='For products with component type: By- / Co-Product, the quantity must be negative. {}',Updated=TO_TIMESTAMP('2025-06-02 12:00:02.585000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545555
;

-- 2025-06-02T12:00:02.592Z
UPDATE AD_Message_Trl trl SET MsgText='For products with component type: By- / Co-Product, the quantity must be negative. {}' WHERE AD_Message_ID=545555 AND AD_Language='de_DE'
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:00:06.307Z
UPDATE AD_Message SET EntityType='D',Updated=TO_TIMESTAMP('2025-06-02 12:00:06.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Message_ID=545555
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:00:45.442Z
UPDATE AD_Message_Trl SET MsgText='For products with component type: By- / Co-Product, the quantity must be negative.
Für Produkte der Art Neben- / Co-Produkt muss die Menge negativ sein."',Updated=TO_TIMESTAMP('2025-06-02 12:00:45.442000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545555
;

-- 2025-06-02T12:00:45.449Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:00.219Z
UPDATE AD_Message_Trl SET MsgText='For products with component type: By- / Co-Product, the quantity must be negative. {}',Updated=TO_TIMESTAMP('2025-06-02 12:01:00.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545555
;

-- 2025-06-02T12:01:00.221Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:06.708Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 12:01:06.708000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Message_ID=545555
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:16.828Z
UPDATE AD_Message_Trl SET MsgText='Für Produkte der Art Neben- / Co-Produkt muss die Menge negativ sein. {}',Updated=TO_TIMESTAMP('2025-06-02 12:01:16.828000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545555
;

-- 2025-06-02T12:01:16.835Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:26.404Z
UPDATE AD_Message_Trl SET MsgText='Für Produkte der Art Neben- / Co-Produkt muss die Menge negativ sein. {}',Updated=TO_TIMESTAMP('2025-06-02 12:01:26.403000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545555
;

-- 2025-06-02T12:01:26.405Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:38.718Z
UPDATE AD_Message_Trl SET MsgText='Für Produkte der Art Neben- / Co-Produkt muss die Menge negativ sein. {}',Updated=TO_TIMESTAMP('2025-06-02 12:01:38.717000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Message_ID=545555
;

-- 2025-06-02T12:01:38.725Z
UPDATE AD_Message base SET MsgText=trl.MsgText, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Message_Trl trl  WHERE trl.AD_Message_ID=base.AD_Message_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:47.344Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 12:01:47.344000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Message_ID=545555
;

-- Value: PP_ProductBOM_CoANDByProducts_NegativeQty
-- 2025-06-02T12:01:51.366Z
UPDATE AD_Message_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-06-02 12:01:51.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Message_ID=545555
;

-- 2025-06-02T12:02:21.116Z
INSERT INTO AD_BusinessRule (AD_BusinessRule_ID,AD_Client_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,IsActive,IsDebug,Name,Severity,Updated,UpdatedBy,Validation_Rule_ID,Warning_Message_ID) VALUES (540026,0,0,53018,TO_TIMESTAMP('2025-06-02 12:02:20.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','BOM Version: for component type Co-Product & By-Product the quantity must be set as negative value (also when percentage!)','N',TO_TIMESTAMP('2025-06-02 12:02:20.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540730,545555)
;

-- 2025-06-02T12:02:23.843Z
UPDATE AD_BusinessRule SET Severity='E',Updated=TO_TIMESTAMP('2025-06-02 12:02:23.843000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_BusinessRule_ID=540026
;

-- 2025-06-02T12:03:10.457Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540026,540032,0,0,TO_TIMESTAMP('2025-06-02 12:03:10.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',53019,'PP_Product_BOM_ID',TO_TIMESTAMP('2025-06-02 12:03:10.424000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-06-02T12:03:40.620Z
INSERT INTO AD_BusinessRule_Trigger (AD_BusinessRule_ID,AD_BusinessRule_Trigger_ID,AD_Client_ID,AD_Org_ID,Created,CreatedBy,IsActive,OnDelete,OnNew,OnUpdate,Source_Table_ID,TargetRecordMappingSQL,Updated,UpdatedBy) VALUES (540026,540033,0,0,TO_TIMESTAMP('2025-06-02 12:03:40.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y',53018,'PP_Product_BOM_ID',TO_TIMESTAMP('2025-06-02 12:03:40.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

