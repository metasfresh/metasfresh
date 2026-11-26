-- IDs allocated from idserver.metas.de on 2026-07-02:
--   AD_Element 585075 (Kostendifferenz)
--   AD_Column  592918 (PP_Order.Kostendifferenz)
--   AD_SQLColumn_SourceTableColumn 540219 (PP_Order.Kostendifferenz <- PP_Order_Cost)

-- Virtual column: issued cost minus received cost for the order's primary costing-method
-- cost element (per C_AcctSchema). Issued = sum of MI rows valued at -cumulatedqty * (current
-- cost price + landed-cost current price); received = sum of postcalculationamt for MR/CO/BY rows.
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,UpdatedBy,Created,Updated)
VALUES (0,'Y',100,'Kostendifferenz','D','Kostendifferenz',585075 /*From ID Server*/,0,'Kostendifferenz',100,
        TO_TIMESTAMP('2026-07-02 10:00:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-07-02 10:00:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585075
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- de_DE / de_CH keep the base German text (mark as translated); en_US gets the English override.
UPDATE AD_Element_Trl SET Name='Kostendifferenz', PrintName='Kostendifferenz', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-07-02 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585075 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Name='Cost difference', PrintName='Cost difference', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-07-02 10:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585075 AND AD_Language='en_US'
;

INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,
                        AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,
                        IsEncrypted,AD_Table_ID,ColumnSQL,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,
                        Name,EntityType,FieldLength,Version,SeqNo,PersonalDataCategory,IsCalculated,Created,Updated)
VALUES (12,'N','N','N','N',0,'Y',100,
        585075 /*From ID Server*/,'N','N','N','N','Y',
        'N',53027,
        '(coalesce((select sum(-oc.cumulatedqty * (coalesce(oc.currentcostprice,0) + coalesce(oc.currentcostpricell,0)))
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id and ce.costingmethod = acs.costingmethod
   where oc.pp_order_id = PP_Order.PP_Order_ID and oc.pp_order_cost_trxtype = ''MI''), 0)
 -
 coalesce((select sum(oc.postcalculationamt)
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id and ce.costingmethod = acs.costingmethod
   where oc.pp_order_id = PP_Order.PP_Order_ID and oc.pp_order_cost_trxtype in (''MR'',''CO'',''BY'')), 0)
)','Kostendifferenz',592918 /*From ID Server*/,'N',0,100,
        'Kostendifferenz','D',14,0,0,'NP','Y',
        TO_TIMESTAMP('2026-07-02 10:01:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-07-02 10:01:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=592918
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Propagate AD_Element translations (Name/Description/Help) onto AD_Column_Trl.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585075);

-- Source table dependency: PP_Order.Kostendifferenz reads pp_order_cost, correlated via
-- PP_Order_Cost.PP_Order_ID (AD_Column_ID=53551). Registering it lets the WebUI invalidate/refresh
-- the virtual column's cached value whenever a PP_Order_Cost row changes.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy)
VALUES (0,592918,0,540219 /*From ID Server*/,53027,
        TO_TIMESTAMP('2026-07-02 10:02:00','YYYY-MM-DD HH24:MI:SS'),100,
        'L','Y',53551,53551,53024,
        TO_TIMESTAMP('2026-07-02 10:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
