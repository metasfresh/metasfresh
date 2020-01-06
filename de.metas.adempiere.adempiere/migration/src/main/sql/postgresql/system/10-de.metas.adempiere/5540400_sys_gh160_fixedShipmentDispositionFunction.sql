DROP FUNCTION IF EXISTS shipmentDispositionExcelDownload(TIMESTAMP With Time Zone, numeric);

CREATE OR REPLACE FUNCTION shipmentDispositionExcelDownload(
    IN p_C_ShipmentSchedule_Deliverydate TIMESTAMP With Time Zone,
	IN p_C_BPartner_ID numeric,
    IN p_AD_Org_ID numeric)

  RETURNS TABLE
  (
    Deliverydate TIMESTAMP With Time Zone,
    BPartnerid numeric,
    BPdetails character varying,
    Orderid numeric,
    Documentno character varying,
    Productname character varying ,
    Productvalue character varying,
    Qtyordered numeric,
    Qtydelivered numeric
  )
   AS

$BODY$

SELECT
    sps.deliverydate,
    sps.c_bpartner_id,
    CONCAT(bp.value, ' ', bp.name) as bpdetails,
    o.c_order_id,
    o.documentno,
    pt.name,
    pt.value,
    sps.qtyordered_tu,
    sps.qtydelivered

FROM M_ShipmentSchedule sps
    LEFT OUTER JOIN c_bpartner bp on bp.c_bpartner_id = sps.c_bpartner_id
    LEFT OUTER JOIN c_order o on o.c_order_id = sps.c_order_id
    LEFT OUTER JOIN m_product pt on pt.m_product_id = sps.m_product_id
	WHERE p_C_ShipmentSchedule_Deliverydate >= sps.deliverydate
	AND CASE WHEN p_C_BPartner_ID > 0 THEN bp.c_bpartner_id = p_C_BPartner_ID ELSE 1=1 END
	AND sps.isActive = 'Y'
	AND sps.AD_Org_ID=p_AD_Org_ID

$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000; 

-- 2020-01-06T13:17:08.119Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select * from shipmentdispositionexceldownload(''@DeliveryDate@'', @C_BPartner_ID/-1@, @#AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2020-01-06 15:17:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

-- 2020-01-06T13:23:09.535Z
-- URL zum Konzept
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,113,0,541238,541660,25,'AD_Org_ID',TO_TIMESTAMP('2020-01-06 15:23:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','U',0,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie kÃ¶nnen Daten Ã¼ber Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','N','Sektion',30,TO_TIMESTAMP('2020-01-06 15:23:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-06T13:23:09.579Z
-- URL zum Konzept
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541660 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-01-06T13:24:15.027Z
-- URL zum Konzept
UPDATE AD_Process_Para SET AD_Reference_ID=30, AD_Reference_Value_ID=130,Updated=TO_TIMESTAMP('2020-01-06 15:24:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541660
;

-- 2020-01-06T13:24:41.282Z
-- URL zum Konzept
UPDATE AD_Process_Para SET IsAutocomplete='Y',Updated=TO_TIMESTAMP('2020-01-06 15:24:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541660
;

-- 2020-01-06T13:25:16.445Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID/0@',Updated=TO_TIMESTAMP('2020-01-06 15:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541660
;

-- 2020-01-06T13:26:34.157Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select * from shipmentdispositionexceldownload(''@DeliveryDate@'', @C_BPartner_ID/-1@, @AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2020-01-06 15:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

-- 2020-01-06T13:26:42.721Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@AD_Org_ID/0@',Updated=TO_TIMESTAMP('2020-01-06 15:26:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541660
;

-- 2020-01-06T13:28:00.928Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select * from shipmentdispositionexceldownload(''@#DeliveryDate@'', @C_BPartner_ID/-1@, @#AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2020-01-06 15:28:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

-- 2020-01-06T13:28:11.315Z
-- URL zum Konzept
UPDATE AD_Process_Para SET DefaultValue='@#AD_Org_ID/0@',Updated=TO_TIMESTAMP('2020-01-06 15:28:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541660
;

-- 2020-01-06T13:29:09.492Z
-- URL zum Konzept
UPDATE AD_Process SET SQLStatement='select * from shipmentdispositionexceldownload(''@PreparationDate_Effective@'', @C_BPartner_ID/-1@, @#AD_Org_ID/0@)',Updated=TO_TIMESTAMP('2020-01-06 15:29:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=541238
;

