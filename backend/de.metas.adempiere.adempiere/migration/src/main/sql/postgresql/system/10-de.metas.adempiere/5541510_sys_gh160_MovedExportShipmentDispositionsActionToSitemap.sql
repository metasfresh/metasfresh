DROP FUNCTION IF EXISTS shipmentDispositionExcelDownload(TIMESTAMP With Time Zone, numeric, numeric);

CREATE OR REPLACE FUNCTION shipmentDispositionExcelDownload(IN p_C_ShipmentSchedule_Deliverydate TIMESTAMP With Time Zone,
                                                            IN p_C_BPartner_ID numeric,
                                                            IN p_AD_Org_ID numeric)

    RETURNS TABLE
            (
                PreparationDate_Effective TIMESTAMP With Time Zone,
                C_Bpartner_ID             character varying,
                OrderDocumentNo           character varying,
                OrderLine                 int,
                ProductName               character varying,
                ProductValue              character varying,
                QtyOrdered                numeric,
                QtyDelivered              numeric
            )
AS

$BODY$

SELECT COALESCE(sps.preparationdate_override, sps.preparationdate) as preparationdate_effective,
       CONCAT(bp.value, ' ', bp.name)                              as C_Bpartner_ID,
       o.documentno                                                as orderdocumentno,
       col.line::int,
       pt.name,
       pt.value,
       sps.qtyordered,
       sps.qtydelivered

FROM M_ShipmentSchedule sps
         LEFT OUTER JOIN c_bpartner bp on bp.c_bpartner_id = sps.c_bpartner_id
         LEFT OUTER JOIN c_order o on o.c_order_id = sps.c_order_id
         LEFT OUTER JOIN m_product pt on pt.m_product_id = sps.m_product_id
         LEFT OUTER JOIN c_orderline col on col.c_orderline_id = sps.c_orderline_id
WHERE p_C_ShipmentSchedule_Deliverydate >= COALESCE(sps.preparationdate_override, sps.preparationdate)
  AND CASE WHEN p_C_BPartner_ID > 0 THEN bp.c_bpartner_id = p_C_BPartner_ID ELSE 1 = 1 END
  AND sps.processed = 'N'
  AND sps.isActive = 'Y'
  AND sps.AD_Org_ID = p_AD_Org_ID

$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
-- 2020-01-14T17:23:31.321Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,577462,0,TO_TIMESTAMP('2020-01-14 19:23:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ÃœberfÃ¤llige Auftragpositionen Exportieren','ÃœberfÃ¤llige Auftragpositionen Exportieren',TO_TIMESTAMP('2020-01-14 19:23:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-14T17:23:31.695Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=577462 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2020-01-14T17:23:52.781Z
-- URL zum Konzept
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Export Overdue Orders', PrintName='Export Overdue Orders',Updated=TO_TIMESTAMP('2020-01-14 19:23:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=577462 AND AD_Language='en_US'
;

-- 2020-01-14T17:23:52.851Z
-- URL zum Konzept
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(577462,'en_US') 
;

-- 2020-01-14T17:25:07.061Z
-- URL zum Konzept
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,577462,541420,0,541238,TO_TIMESTAMP('2020-01-14 19:25:06','YYYY-MM-DD HH24:MI:SS'),100,'D','ÃœberfÃ¤llige Auftragpositionen Exportieren','Y','N','N','N','N','ÃœberfÃ¤llige Auftragpositionen Exportieren',TO_TIMESTAMP('2020-01-14 19:25:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-01-14T17:25:07.141Z
-- URL zum Konzept
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Menu_ID=541420 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2020-01-14T17:25:07.180Z
-- URL zum Konzept
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541420, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541420)
;

-- 2020-01-14T17:25:07.227Z
-- URL zum Konzept
/* DDL */  select update_menu_translation_from_ad_element(577462) 
;

-- 2020-01-14T17:25:09.835Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541293 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:09.874Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000034 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:09.912Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541391 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:09.949Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000089 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:09.985Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541408 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.025Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540840 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.062Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540990 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.099Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000092 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.135Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541175 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.174Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541376 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.210Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000093 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.248Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541230 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.286Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541062 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.323Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541115 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.359Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541116 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.395Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541126 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.432Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541127 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.470Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541194 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.507Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541411 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.545Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000044 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.581Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000035 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.619Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000037 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:10.656Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000009, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541420 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.628Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.674Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.710Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.748Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.786Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.826Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.866Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.903Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.941Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:33.978Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.015Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.053Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.090Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.130Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.168Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.209Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.247Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.286Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541420 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:34.324Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:40.322Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541420 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:40.358Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541044 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:40.396Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540963 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:45.171Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541044 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:45.208Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540963 AND AD_Tree_ID=10
;

-- 2020-01-14T17:25:45.244Z
-- URL zum Konzept
UPDATE AD_TreeNodeMM SET Parent_ID=1000068, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541420 AND AD_Tree_ID=10
;

-- 2020-01-14T17:27:29.777Z
-- URL zum Konzept
UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='N', WEBUI_ViewAction='N',Updated=TO_TIMESTAMP('2020-01-14 19:27:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_Process_ID=540772
;

