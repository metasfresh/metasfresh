-- A dedicated virtual Yes/No column PP_Order.HasCostDifference, so the "Kostenüberwachung Fertigung"
-- window can be narrowed to the orders that actually carry a cost difference.
--
-- Why a NEW column instead of filtering the existing PP_Order.CostDifference (AD_Column 592970):
-- with AD_Column.FilterOperator unset the filter operator defaults to EQUALS, which on a decimal
-- amount is an exact-match box nobody can use. Setting 'B' (Between) would fix that, but
-- FilterOperator lives on AD_Column and is therefore shared by every window that shows PP_Order --
-- it would change the Produktionsauftrag window (AD_Window 53009) too. A column that only the
-- monitor window has an AD_Field for has no such reach: its column-level filter attributes are
-- invisible everywhere else. That is also what makes FilterDefaultValue='Y' below safe.
--
-- The expression is the EXISTING CostDifference expression, reused verbatim and only wrapped in a
-- <> 0 test, so the two columns can never disagree: sum(cumulatedamt - postcalculationamt) over the
-- order's 'MR' PP_Order_Cost rows, restricted to the client's primary accounting schema and that
-- schema's active material cost element. Qualifying the host table as PP_Order.<col> is required
-- (unqualified AD_Client_ID / PP_Order_ID would bind against pp_order_cost inside the subquery) and
-- is alias-safe: SqlEntityBinding.replaceTableNameWithTableAlias rewrites the prefix at query time.
--
-- IDs allocated from idserver.metas.de: AD_Element 585435, AD_Column 593510,
--   AD_SQLColumn_SourceTableColumn 540240

-- 1) AD_Element -- German in the base column, en_US as the translation override.
INSERT INTO AD_Element (AD_Client_ID,IsActive,CreatedBy,PrintName,EntityType,ColumnName,AD_Element_ID,AD_Org_ID,Name,Description,UpdatedBy,Created,Updated)
VALUES (0,'Y',100,'Kostendifferenz vorhanden','D','HasCostDifference',585435 /*From ID Server*/,0,
        'Kostendifferenz vorhanden',
        'Ja, wenn der Fertigungsauftrag eine von null verschiedene Kostendifferenz trägt.',
        100,
        TO_TIMESTAMP('2026-09-08 15:00:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-08 15:00:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, PO_Name,PO_PrintName,PrintName,PO_Description,PO_Help,Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Element_ID, t.PO_Name,t.PO_PrintName,t.PrintName,t.PO_Description,t.PO_Help,t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585435
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Kostendifferenz vorhanden', PrintName='Kostendifferenz vorhanden',
       Description='Ja, wenn der Fertigungsauftrag eine von null verschiedene Kostendifferenz trägt.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-08 15:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585435 AND AD_Language IN ('de_DE','de_CH')
;

UPDATE AD_Element_Trl SET Name='Has cost difference', PrintName='Has cost difference',
       Description='Yes when the manufacturing order carries a cost difference other than zero.',
       IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-09-08 15:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=585435 AND AD_Language='en_US'
;

-- 2) The virtual column (AD_Reference 20 = Yes/No).
--    IsSelectionColumn stays 'N' on purpose: under a tab's default 'Auto' filter strategy the filter
--    set is read from IsSelectionColumn, so setting it here would put this filter on every PP_Order
--    window. The monitor tab (549352) is on 'Explicit' and reads AD_Field.IsFilterField instead.
--    FilterOperator='E' (Equals) and FilterDefaultValue='Y' are AD_Column-level and therefore only
--    reach a tab that has an AD_Field for this column -- which is tab 549352 alone.
INSERT INTO AD_Column (AD_Reference_ID,IsKey,IsParent,IsTranslated,IsIdentifier,AD_Client_ID,IsActive,CreatedBy,
                        AD_Element_ID,IsUpdateable,IsSelectionColumn,IsSyncDatabase,IsAlwaysUpdateable,IsAllowLogging,
                        IsEncrypted,AD_Table_ID,ColumnSQL,ColumnName,AD_Column_ID,IsMandatory,AD_Org_ID,UpdatedBy,
                        Name,Description,EntityType,FieldLength,Version,SeqNo,PersonalDataCategory,IsCalculated,
                        FilterOperator,FilterDefaultValue,Created,Updated)
VALUES (20,'N','N','N','N',0,'Y',100,
        585435 /*From ID Server*/,'N','N','N','N','Y',
        'N',53027,
        '(CASE WHEN coalesce((select sum(oc.cumulatedamt - oc.postcalculationamt)
   from pp_order_cost oc
   join c_acctschema acs on acs.c_acctschema_id = oc.c_acctschema_id
    and acs.c_acctschema_id = (select ci.c_acctschema1_id from ad_clientinfo ci where ci.ad_client_id = PP_Order.AD_Client_ID)
   join m_costelement ce on ce.m_costelement_id = oc.m_costelement_id
    and ce.costingmethod = acs.costingmethod
    and ce.costelementtype = ''M''
    and ce.isactive = ''Y''
   where oc.pp_order_id = PP_Order.PP_Order_ID
     and oc.pp_order_cost_trxtype = ''MR''), 0) <> 0 THEN ''Y'' ELSE ''N'' END)',
        'HasCostDifference',593510 /*From ID Server*/,'N',0,100,
        'Kostendifferenz vorhanden',
        'Ja, wenn der Fertigungsauftrag eine von null verschiedene Kostendifferenz trägt.',
        'D',1,0,0,'NP','Y',
        'E','Y',
        TO_TIMESTAMP('2026-09-08 15:01:00','YYYY-MM-DD HH24:MI:SS'),
        TO_TIMESTAMP('2026-09-08 15:01:00','YYYY-MM-DD HH24:MI:SS'))
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy)
SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593510
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 3) Same source-table dependency the sibling CostDifference column carries (AD_SQLColumn_SourceTableColumn
--    540223): refresh the value whenever a PP_Order_Cost row of the order changes.
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy)
VALUES (0,593510,0,540240 /*From ID Server*/,53027,
        TO_TIMESTAMP('2026-09-08 15:02:00','YYYY-MM-DD HH24:MI:SS'),100,
        'L','Y',53551,53551,53024,
        TO_TIMESTAMP('2026-09-08 15:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585435);
