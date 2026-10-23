-- "CUs annehmen mit Menge" on the receipt-disposition delivery-planning window (AD_Process 585661):
-- its quantity parameter 543304 is no quantity per TU (there is no TU when receiving loose CUs).
-- Move it from element "Menge pro TU" (QtyCUsPerTU, 542492), which 5822600 copied from 541163, to "Menge CU"
-- (QtyCUsReceived, AD_Element 585563) - the element the receipt-schedule window's counterpart (AD_Process 540765)
-- uses. The Java process reads only QtyCUsReceived.
--
-- AD_Element 585563 is also created by 5831320_sys_PP_Order_Receipt_follows_packing_instruction.sql (module
-- de.metas.ui.web.base), whose INSERT is guarded by WHERE NOT EXISTS. So this script creates it, with exactly the
-- same values, only if it is missing: either script may run first.
--
-- Idempotent: the element/translation inserts are guarded and the UPDATEs only touch rows that differ.
-- AD_Process_Para.ColumnName / Name are not synced from the element, hence set explicitly.

INSERT INTO AD_Element (AD_Element_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,ColumnName,EntityType,Name,PrintName)
SELECT 585563 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-10-23 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-10-23 10:00:01','YYYY-MM-DD HH24:MI:SS'),100,'QtyCUsReceived','de.metas.ui.web','Menge CU','Menge CU'
WHERE NOT EXISTS (SELECT 1 FROM AD_Element WHERE AD_Element_ID=585563);

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive,IsUseCustomization)
SELECT l.AD_Language,t.AD_Element_ID,t.Name,t.PrintName,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y','N'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585563
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID);

UPDATE AD_Element_Trl SET Name='Menge CU',PrintName='Menge CU',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585563 AND AD_Language IN ('de_DE','de_CH') AND (Name IS DISTINCT FROM 'Menge CU' OR PrintName IS DISTINCT FROM 'Menge CU' OR IsTranslated IS DISTINCT FROM 'Y');

UPDATE AD_Element_Trl SET Name='Quantity CU',PrintName='Quantity CU',IsTranslated='Y',Updated=TO_TIMESTAMP('2026-10-23 10:00:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE AD_Element_ID=585563 AND AD_Language='en_US' AND (Name IS DISTINCT FROM 'Quantity CU' OR PrintName IS DISTINCT FROM 'Quantity CU' OR IsTranslated IS DISTINCT FROM 'Y');

UPDATE AD_Process_Para
SET AD_Element_ID=585563,
    ColumnName='QtyCUsReceived',
    Name=(SELECT e.Name FROM AD_Element e WHERE e.AD_Element_ID = 585563),
    IsMandatory='Y',
    Updated=TO_TIMESTAMP('2026-10-23 17:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
WHERE AD_Process_Para_ID = 543304
  AND AD_Process_ID = 585661
  AND (AD_Element_ID IS DISTINCT FROM 585563
    OR ColumnName IS DISTINCT FROM 'QtyCUsReceived'
    OR Name IS DISTINCT FROM (SELECT e.Name FROM AD_Element e WHERE e.AD_Element_ID = 585563)
    OR IsMandatory IS DISTINCT FROM 'Y')
;

UPDATE AD_Process_Para_Trl t
SET Name=et.Name,
    IsTranslated=et.IsTranslated,
    Updated=TO_TIMESTAMP('2026-10-23 17:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy=100
FROM AD_Element_Trl et
WHERE t.AD_Process_Para_ID = 543304
  AND et.AD_Element_ID = 585563
  AND et.AD_Language = t.AD_Language
  AND (t.Name IS DISTINCT FROM et.Name OR t.IsTranslated IS DISTINCT FROM et.IsTranslated)
;
