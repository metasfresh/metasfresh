-- IDs allocated from idserver.metas.de on 2026-07-20:
--   AD_Element 585115 (CostDifference)
--   AD_Column  592970 (PP_Order.CostDifference)
--   AD_SQLColumn_SourceTableColumn 540223 (PP_Order.CostDifference <- PP_Order_Cost)
--
-- Renames the virtual column PP_Order.Kostendifferenz -> PP_Order.CostDifference and NEGATES its sign:
-- it now computes received-minus-issued, so the value is negative when more cost was issued than
-- received. Only the two coalesce(...) summands of the ColumnSQL are swapped.
--
-- The displayed German label stays "Kostendifferenz"; en_US is "Cost difference". The column is virtual
-- (ColumnSQL), so there is no stored data to copy: create the new column, rewire the existing header
-- field (AD_Field 781320) to it, then drop the now-unreferenced old objects.

-- 1) New AD_Element CostDifference (German in base column; en_US override below).
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated)
VALUES (0,'Y',100,'Kostendifferenz','D','CostDifference',585115 /*From ID Server*/,0,'Kostendifferenz',100,
        TO_TIMESTAMP('2026-07-20 10:00:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-07-20 10:00:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585115
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- de_DE / de_CH keep the base German text (mark as translated); en_US gets the English override.
UPDATE AD_Element_Trl SET Name='Kostendifferenz', PrintName='Kostendifferenz', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-07-20 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585115 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Name='Cost difference', PrintName='Cost difference', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-07-20 10:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585115 AND AD_Language='en_US'
;

-- 2) New virtual AD_Column CostDifference (AD_Reference 12 = Amount) with the NEGATED expression:
--    received (MR/CO/BY postcalculationamt) minus issued (MI -cumulatedqty * (cost + landed-cost)),
--    i.e. the two summands of the former Kostendifferenz expression swapped.
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,
                        AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,
                        IsEncrypted,AD_Table_ID,ColumnSQL,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,
                        Name,EntityType,FieldLength,Version,SeqNo,PersonalDataCategory,IsCalculated,Created,Updated)
VALUES (12,'N','N','N','N',0,'Y',100,
        585115 /*From ID Server*/,'N','N','N','N','Y',
        'N',53027,
        '(coalesce((select sum(oc.postcalculationamt)
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id and ce.costingmethod = acs.costingmethod
   where oc.pp_order_id = PP_Order.PP_Order_ID and oc.pp_order_cost_trxtype in (''MR'',''CO'',''BY'')), 0)
 -
 coalesce((select sum(-oc.cumulatedqty * (coalesce(oc.currentcostprice,0) + coalesce(oc.currentcostpricell,0)))
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id and ce.costingmethod = acs.costingmethod
   where oc.pp_order_id = PP_Order.PP_Order_ID and oc.pp_order_cost_trxtype = ''MI''), 0)
)','CostDifference',592970 /*From ID Server*/,'N',0,100,
        'Kostendifferenz','D',14,0,0,'NP','Y',
        TO_TIMESTAMP('2026-07-20 10:01:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-07-20 10:01:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592970
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 3) Source table dependency: PP_Order.CostDifference reads pp_order_cost, correlated via
-- PP_Order_Cost.PP_Order_ID (AD_Column_ID=53551). Registering it lets the WebUI invalidate/refresh
-- the virtual column's cached value whenever a PP_Order_Cost row changes.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy)
VALUES (0,592970,0,540223 /*From ID Server*/,53027,
        TO_TIMESTAMP('2026-07-20 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,
        'L','Y',53551,53551,53024,
        TO_TIMESTAMP('2026-07-20 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 4) Rewire the existing Production Order header field (AD_Field 781320 on window 53009 / tab 53054,
-- UI element 652428) from the old Kostendifferenz column (592918) to the new CostDifference column
-- (592970). The field label continues to resolve from the column's element (585115), so the German
-- caption stays "Kostendifferenz" and en_US "Cost difference".
UPDATE AD_Field SET AD_Column_ID=592970,
       Updated=TO_TIMESTAMP('2026-07-20 10:03:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID=781320
;

-- Propagate AD_Element 585115 translations (Name/Description/Help) onto AD_Column_Trl / AD_Field_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585115);

-- 5) Drop the old objects, now fully unreferenced (field repointed above). The old column is
-- virtual (ColumnSQL) so there is no physical column to drop and no data to migrate. Dependency sweep
-- (views / functions / val-rules / other virtual ColumnSQL / EXP_FormatLine / other AD_Fields) was
-- empty: AD_Field 781320 (repointed) was the only referencer.
DELETE FROM AD_Element_Link                WHERE AD_Element_ID=585075;
DELETE FROM AD_SQLColumn_SourceTableColumn WHERE AD_SQLColumn_SourceTableColumn_ID=540219;
DELETE FROM AD_Column_Trl                  WHERE AD_Column_ID=592918;
DELETE FROM AD_Column                      WHERE AD_Column_ID=592918;
DELETE FROM AD_Element_Trl                 WHERE AD_Element_ID=585075;
DELETE FROM AD_Element                     WHERE AD_Element_ID=585075;
