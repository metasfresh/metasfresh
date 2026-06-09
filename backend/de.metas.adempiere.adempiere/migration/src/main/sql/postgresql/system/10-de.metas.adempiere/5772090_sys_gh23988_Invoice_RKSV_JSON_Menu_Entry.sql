-- Run mode: SWING_CLIENT

-- 2025-10-02T11:40:16.664Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584061,0,TO_TIMESTAMP('2025-10-02 11:40:16.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zweitbelege RKSV Kassello JSON','Zweitbelege RKSV Kassello JSON',TO_TIMESTAMP('2025-10-02 11:40:16.380000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T11:40:16.680Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584061 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Name: Zweitbelege RKSV Kassello JSON
-- Action Type: P
-- Process: Invoice_RKSV_JSON(de.metas.postgrest.process.json.Invoice_RKSV_JSON)
-- 2025-10-02T11:40:37.777Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Process_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('P',0,584061,542256,0,585506,TO_TIMESTAMP('2025-10-02 11:40:37.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Invoice_RKSV_JSON','Y','N','N','N','N','Zweitbelege RKSV Kassello JSON',TO_TIMESTAMP('2025-10-02 11:40:37.603000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-10-02T11:40:37.781Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542256 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2025-10-02T11:40:37.786Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542256, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542256)
;

-- 2025-10-02T11:40:37.872Z
/* DDL */  select update_menu_translation_from_ad_element(584061)
;

-- Reordering children of `Shipment`
-- Node name: `Shipment (M_InOut)`
-- 2025-10-02T11:40:38.463Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000087 AND AD_Tree_ID=10
;

-- Node name: `Shipment Restrictions (M_Shipment_Constraint)`
-- 2025-10-02T11:40:38.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540970 AND AD_Tree_ID=10
;

-- Node name: `Shipment Disposition (M_ShipmentSchedule)`
-- 2025-10-02T11:40:38.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540728 AND AD_Tree_ID=10
;

-- Node name: `Shipment disposition export revision (M_ShipmentSchedule_ExportAudit_Item)`
-- 2025-10-02T11:40:38.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541483 AND AD_Tree_ID=10
;

-- Node name: `Empties Return (M_InOut)`
-- 2025-10-02T11:40:38.465Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540783 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration Config (M_Shipment_Declaration_Config)`
-- 2025-10-02T11:40:38.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541251 AND AD_Tree_ID=10
;

-- Node name: `Shipment Declaration (M_Shipment_Declaration)`
-- 2025-10-02T11:40:38.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541250 AND AD_Tree_ID=10
;

-- Node name: `Customer Return (M_InOut)`
-- 2025-10-02T11:40:38.466Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540841 AND AD_Tree_ID=10
;

-- Node name: `Service/Repair Customer Return (M_InOut)`
-- 2025-10-02T11:40:38.467Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53243 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (M_Packageable_V)`
-- 2025-10-02T11:40:38.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540868 AND AD_Tree_ID=10
;

-- Node name: `Picking Terminal (v2)`
-- 2025-10-02T11:40:38.468Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541138 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray Clearing (Prototype)`
-- 2025-10-02T11:40:38.469Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540971 AND AD_Tree_ID=10
;

-- Node name: `Picking Tray (M_PickingSlot)`
-- 2025-10-02T11:40:38.469Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540949 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2025-10-02T11:40:38.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540950 AND AD_Tree_ID=10
;

-- Node name: `EDI DESADV (EDI_Desadv)`
-- 2025-10-02T11:40:38.470Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540976 AND AD_Tree_ID=10
;

-- Node name: `Picking candidate (M_Picking_Candidate)`
-- 2025-10-02T11:40:38.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541395 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-02T11:40:38.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000060 AND AD_Tree_ID=10
;

-- Node name: `EDI_Desadv_Pack (EDI_Desadv_Pack)`
-- 2025-10-02T11:40:38.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541406 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-02T11:40:38.472Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000068 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-02T11:40:38.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000079 AND AD_Tree_ID=10
;

-- Node name: `Picking`
-- 2025-10-02T11:40:38.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541856 AND AD_Tree_ID=10
;

-- Node name: `Zweitbelege RKSV Kassello JSON`
-- 2025-10-02T11:40:38.474Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000019, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542256 AND AD_Tree_ID=10
;

-- Reordering children of `Menu`
-- Node name: `Zweitbelege RKSV Kassello JSON`
-- 2025-10-02T11:40:46.129Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542256 AND AD_Tree_ID=10
;

-- Node name: `webUI`
-- 2025-10-02T11:40:46.130Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000007 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2025-10-02T11:40:46.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541085 AND AD_Tree_ID=10
;

-- Node name: `Übersetzung`
-- 2025-10-02T11:40:46.131Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540861 AND AD_Tree_ID=10
;

-- Node name: `Handling Units`
-- 2025-10-02T11:40:46.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540478 AND AD_Tree_ID=10
;

-- Node name: `Application Dictionary`
-- 2025-10-02T11:40:46.132Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=153 AND AD_Tree_ID=10
;

-- Node name: `System Admin`
-- 2025-10-02T11:40:46.133Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=218 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2025-10-02T11:40:46.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540281 AND AD_Tree_ID=10
;

-- Node name: `Partner Relations`
-- 2025-10-02T11:40:46.134Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=263 AND AD_Tree_ID=10
;

-- Node name: `Quote-to-Invoice`
-- 2025-10-02T11:40:46.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=166 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing Management`
-- 2025-10-02T11:40:46.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53014 AND AD_Tree_ID=10
;

-- Node name: `Requisition-to-Invoice`
-- 2025-10-02T11:40:46.135Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=203 AND AD_Tree_ID=10
;

-- Node name: `DPD`
-- 2025-10-02T11:40:46.136Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540092 AND AD_Tree_ID=10
;

-- Node name: `Materialsaldo`
-- 2025-10-02T11:40:46.137Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540627 AND AD_Tree_ID=10
;

-- Node name: `Returns`
-- 2025-10-02T11:40:46.137Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53242 AND AD_Tree_ID=10
;

-- Node name: `Open Items`
-- 2025-10-02T11:40:46.138Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=236 AND AD_Tree_ID=10
;

-- Node name: `Material Management`
-- 2025-10-02T11:40:46.138Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=183 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2025-10-02T11:40:46.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=160 AND AD_Tree_ID=10
;

-- Node name: `Performance Analysis`
-- 2025-10-02T11:40:46.139Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=278 AND AD_Tree_ID=10
;

-- Node name: `Assets`
-- 2025-10-02T11:40:46.140Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=345 AND AD_Tree_ID=10
;

-- Node name: `Call Center`
-- 2025-10-02T11:40:46.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540016 AND AD_Tree_ID=10
;

-- Node name: `Berichte`
-- 2025-10-02T11:40:46.141Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540613 AND AD_Tree_ID=10
;

-- Node name: `Human Resource & Payroll`
-- 2025-10-02T11:40:46.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53108 AND AD_Tree_ID=10
;

-- Node name: `EDI Definition (C_BP_EDI)`
-- 2025-10-02T11:40:46.142Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=518 AND AD_Tree_ID=10
;

-- Node name: `EDI Transaction`
-- 2025-10-02T11:40:46.143Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=519 AND AD_Tree_ID=10
;

-- Node name: `Berichte Materialwirtschaft`
-- 2025-10-02T11:40:46.144Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000004 AND AD_Tree_ID=10
;

-- Node name: `Einstellungen Verkauf`
-- 2025-10-02T11:40:46.144Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000001 AND AD_Tree_ID=10
;

-- Node name: `Berichte Verkauf`
-- 2025-10-02T11:40:46.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000000 AND AD_Tree_ID=10
;

-- Node name: `Berichte Geschäftspartner`
-- 2025-10-02T11:40:46.145Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000002 AND AD_Tree_ID=10
;

-- Node name: `Cockpit`
-- 2025-10-02T11:40:46.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540029 AND AD_Tree_ID=10
;

-- Node name: `Packstück (M_Package)`
-- 2025-10-02T11:40:46.146Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540028 AND AD_Tree_ID=10
;

-- Node name: `Lieferanten Abrufauftrag (C_OrderLine)`
-- 2025-10-02T11:40:46.147Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540030 AND AD_Tree_ID=10
;

-- Node name: `Cash Journal (@PREFIX@de/metas/reports/kassenbuch/report.jasper)`
-- 2025-10-02T11:40:46.147Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540031 AND AD_Tree_ID=10
;

-- Node name: `Nachlieferung (M_SubsequentDelivery_V)`
-- 2025-10-02T11:40:46.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540034 AND AD_Tree_ID=10
;

-- Node name: `Verpackung (M_PackagingContainer)`
-- 2025-10-02T11:40:46.148Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540024 AND AD_Tree_ID=10
;

-- Node name: `Abolieferplan aktualisieren (de.metas.contracts.subscription.process.C_SubscriptionProgress_Evaluate)`
-- 2025-10-02T11:40:46.149Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540015 AND AD_Tree_ID=10
;

-- Node name: `Umsatz pro Kunde (@PREFIX@de/metas/reports/umsatzprokunde/report.jasper)`
-- 2025-10-02T11:40:46.149Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540066 AND AD_Tree_ID=10
;

-- Node name: `Sponsoren Anlegen (de.metas.commission.process.CreateSponsors)`
-- 2025-10-02T11:40:46.150Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540043 AND AD_Tree_ID=10
;

-- Node name: `Arbeitszeit (@PREFIX@de/metas/reports/arbeitszeit/report.jasper)`
-- 2025-10-02T11:40:46.150Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540048 AND AD_Tree_ID=10
;

-- Node name: `Check Tree and Reset Sponsor Depths (de.metas.commission.process.CheckTreeResetDepths)`
-- 2025-10-02T11:40:46.151Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540052 AND AD_Tree_ID=10
;

-- Node name: `Bankeinzug (C_DirectDebit)`
-- 2025-10-02T11:40:46.151Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540058 AND AD_Tree_ID=10
;

-- Node name: `Spezial`
-- 2025-10-02T11:40:46.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540077 AND AD_Tree_ID=10
;

-- Node name: `Belege`
-- 2025-10-02T11:40:46.152Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540076 AND AD_Tree_ID=10
;

-- Node name: `Steuer`
-- 2025-10-02T11:40:46.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540075 AND AD_Tree_ID=10
;

-- Node name: `Währung`
-- 2025-10-02T11:40:46.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540074 AND AD_Tree_ID=10
;

-- Node name: `Hauptbuch`
-- 2025-10-02T11:40:46.153Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540073 AND AD_Tree_ID=10
;

-- Node name: `Steueranmeldung (@PREFIX@de/metas/reports/taxregistration/report.jasper)`
-- 2025-10-02T11:40:46.154Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540070 AND AD_Tree_ID=10
;

-- Node name: `Wiederkehrende Zahlungen (C_RecurrentPayment)`
-- 2025-10-02T11:40:46.154Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540068 AND AD_Tree_ID=10
;

-- Node name: `Verkaufte Artikel (@PREFIX@de/metas/reports/soldproducts/report.jasper)`
-- 2025-10-02T11:40:46.155Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540065 AND AD_Tree_ID=10
;

-- Node name: `Versand (@PREFIX@de/metas/reports/versand/report.jasper)`
-- 2025-10-02T11:40:46.155Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540067 AND AD_Tree_ID=10
;

-- Node name: `Provision_LEGACY`
-- 2025-10-02T11:40:46.156Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540083 AND AD_Tree_ID=10
;

-- Node name: `Vertriebspartnerpunkte (@PREFIX@de/metas/reports/vertriebspartnerpunktzahl/report.jasper)`
-- 2025-10-02T11:40:46.156Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540136 AND AD_Tree_ID=10
;

-- Node name: `C_BPartner Convert Memo (de.metas.adempiere.process.ConvertBPartnerMemo)`
-- 2025-10-02T11:40:46.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540106 AND AD_Tree_ID=10
;

-- Node name: `Ladeliste (Jasper) (@PREFIX@de/metas/docs/sales/shippingorder/report.jasper)`
-- 2025-10-02T11:40:46.157Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540090 AND AD_Tree_ID=10
;

-- Node name: `Wiederkenhrende Zahlungs-Rechnungen erzeugen (de.metas.banking.process.C_RecurrentPaymentCreateInvoice)`
-- 2025-10-02T11:40:46.158Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540109 AND AD_Tree_ID=10
;

-- Node name: `Daten-Bereinigung (de.metas.adempiere.process.SweepTable)`
-- 2025-10-02T11:40:46.158Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540119 AND AD_Tree_ID=10
;

-- Node name: `Geschäftspartner importieren (org.compiere.process.ImportBPartner)`
-- 2025-10-02T11:40:46.159Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540120 AND AD_Tree_ID=10
;

-- Node name: `Update C_BPartner.IsSalesRep (de.metas.process.ExecuteUpdateSQL)`
-- 2025-10-02T11:40:46.159Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540103 AND AD_Tree_ID=10
;

-- Node name: `E/A`
-- 2025-10-02T11:40:46.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540098 AND AD_Tree_ID=10
;

-- Node name: `Sponsor-Statistik aktualisieren (de.metas.commission.process.UpdateSponsorStats)`
-- 2025-10-02T11:40:46.160Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540105 AND AD_Tree_ID=10
;

-- Node name: `Downline Navigator (de.metas.commision.form.zk.WSponsorBrowse)`
-- 2025-10-02T11:40:46.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540139 AND AD_Tree_ID=10
;

-- Node name: `B2B Adressen und Bankverbindung ändern (de.metas.commision.form.zk.WB2BAddressAccount)`
-- 2025-10-02T11:40:46.161Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540187 AND AD_Tree_ID=10
;

-- Node name: `B2B Auftrag erfassen (de.metas.commision.form.zk.WB2BOrder)`
-- 2025-10-02T11:40:46.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540184 AND AD_Tree_ID=10
;

-- Node name: `UserAccountLock`
-- 2025-10-02T11:40:46.162Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540147 AND AD_Tree_ID=10
;

-- Node name: `B2B Bestellübersicht (de.metas.commision.form.zk.WB2BOrderHistory)`
-- 2025-10-02T11:40:46.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540185 AND AD_Tree_ID=10
;

-- Node name: `VP-Ränge (@PREFIX@de/metas/reports/vertriebspartnerraenge/report.jasper)`
-- 2025-10-02T11:40:46.163Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540167 AND AD_Tree_ID=10
;

-- Node name: `User lock expire (de.metas.user.process.AD_User_ExpireLocks)`
-- 2025-10-02T11:40:46.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540148 AND AD_Tree_ID=10
;

-- Node name: `Orders Overview (de.metas.adempiere.form.swing.OrderOverview)`
-- 2025-10-02T11:40:46.164Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540228 AND AD_Tree_ID=10
;

-- Node name: `Kommissionier Terminal (de.metas.picking.terminal.form.swing.PickingTerminal)`
-- 2025-10-02T11:40:46.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540225 AND AD_Tree_ID=10
;

-- Node name: `Tour (M_Tour)`
-- 2025-10-02T11:40:46.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540269 AND AD_Tree_ID=10
;

-- Node name: `UI Trigger (AD_TriggerUI)`
-- 2025-10-02T11:40:46.165Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540246 AND AD_Tree_ID=10
;

-- Node name: `Auftragskandidaten verarbeiten (de.metas.ordercandidate.process.C_OLCandEnqueueForSalesOrderCreation)`
-- 2025-10-02T11:40:46.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540261 AND AD_Tree_ID=10
;

-- Node name: `ESR Zahlungsimport (ESR_Import)`
-- 2025-10-02T11:40:46.166Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540404 AND AD_Tree_ID=10
;

-- Node name: `Liefertage (M_DeliveryDay)`
-- 2025-10-02T11:40:46.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540270 AND AD_Tree_ID=10
;

-- Node name: `Create product costs (de.metas.adempiere.process.CreateProductCosts)`
-- 2025-10-02T11:40:46.167Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540252 AND AD_Tree_ID=10
;

-- Node name: `Document Management`
-- 2025-10-02T11:40:46.168Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540400 AND AD_Tree_ID=10
;

-- Node name: `Update Addresses (de.metas.adempiere.process.UpdateAddresses)`
-- 2025-10-02T11:40:46.168Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540253 AND AD_Tree_ID=10
;

-- Node name: `Massendruck`
-- 2025-10-02T11:40:46.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540443 AND AD_Tree_ID=10
;

-- Node name: `Abrechnung MwSt.-Korrektur (C_Invoice_VAT_Corr_Candidates_v1)`
-- 2025-10-02T11:40:46.169Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=78, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540238 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot (M_PickingSlot)`
-- 2025-10-02T11:40:46.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=79, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540512 AND AD_Tree_ID=10
;

-- Node name: `Picking Slot Trx (M_PickingSlot_Trx)`
-- 2025-10-02T11:40:46.170Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=80, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540544 AND AD_Tree_ID=10
;

-- Node name: `Picking Vorbereitung Liste (Jasper) (@PREFIX@de/metas/reports/pickingpreparation/report.jasper)`
-- 2025-10-02T11:40:46.171Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=81, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540517 AND AD_Tree_ID=10
;

-- Node name: `Parzelle (C_Allotment)`
-- 2025-10-02T11:40:46.171Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=82, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540518 AND AD_Tree_ID=10
;

-- Node name: `Export Format (EXP_Format)`
-- 2025-10-02T11:40:46.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=83, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540440 AND AD_Tree_ID=10
;

-- Node name: `Transportdisposition (M_Tour_Instance)`
-- 2025-10-02T11:40:46.172Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=84, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540559 AND AD_Tree_ID=10
;

-- Node name: `Belegzeile-Sortierung (C_DocLine_Sort)`
-- 2025-10-02T11:40:46.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=85, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540608 AND AD_Tree_ID=10
;

-- Node name: `Zählbestand Einkauf (fresh) (Fresh_QtyOnHand)`
-- 2025-10-02T11:40:46.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=86, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540587 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2025-10-02T11:40:46.173Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=87, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540570 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2025-10-02T11:40:46.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=88, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540576 AND AD_Tree_ID=10
;

-- Node name: `Transparenz zur Status ESR Import in Bankauszug (x_esr_import_in_c_bankstatement_v)`
-- 2025-10-02T11:40:46.174Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=89, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540656 AND AD_Tree_ID=10
;

-- Node name: `Offene Zahlung - Skonto Zuordnung (de.metas.payment.process.C_Payment_MassWriteOff)`
-- 2025-10-02T11:40:46.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=90, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540649 AND AD_Tree_ID=10
;

-- Node name: `Gebinde`
-- 2025-10-02T11:40:46.175Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=91, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000005 AND AD_Tree_ID=10
;

-- Node name: `Parzelle`
-- 2025-10-02T11:40:46.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=92, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540886 AND AD_Tree_ID=10
;

-- Node name: `AD_Table_CreateFromInputFile (org.adempiere.ad.table.process.AD_Table_CreateFromInputFile)`
-- 2025-10-02T11:40:46.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=93, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540997 AND AD_Tree_ID=10
;

-- Node name: `Shipment restrictions (M_Shipment_Constraint)`
-- 2025-10-02T11:40:46.177Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=94, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540967 AND AD_Tree_ID=10
;

-- Node name: `Board Configuration (WEBUI_Board)`
-- 2025-10-02T11:40:46.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=95, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540987 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2025-10-02T11:40:46.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=96, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000023 AND AD_Tree_ID=10
;

-- Node name: `Create Membership Contracts (de.metas.contracts.order.process.C_Order_CreateForAllMembers)`
-- 2025-10-02T11:40:46.179Z
UPDATE AD_TreeNodeMM SET Parent_ID=0, SeqNo=97, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541832 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `Zweitbelege RKSV Kassello JSON`
-- 2025-10-02T11:42:05.570Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542256 AND AD_Tree_ID=10
;

-- Node name: `CRM`
-- 2025-10-02T11:42:05.572Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2025-10-02T11:42:05.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2025-10-02T11:42:05.573Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2025-10-02T11:42:05.574Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2025-10-02T11:42:05.574Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2025-10-02T11:42:05.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2025-10-02T11:42:05.575Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2025-10-02T11:42:05.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2025-10-02T11:42:05.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2025-10-02T11:42:05.576Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2025-10-02T11:42:05.577Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2025-10-02T11:42:05.577Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2025-10-02T11:42:05.578Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2025-10-02T11:42:05.578Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2025-10-02T11:42:05.579Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2025-10-02T11:42:05.579Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2025-10-02T11:42:05.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2025-10-02T11:42:05.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2025-10-02T11:42:05.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2025-10-02T11:42:05.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2025-10-02T11:42:05.582Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `Purchase`
-- Node name: `Zweitbelege RKSV Kassello JSON`
-- 2025-10-02T11:43:17.344Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542256 AND AD_Tree_ID=10
;

-- Node name: `Purchase Order (C_Order)`
-- 2025-10-02T11:43:17.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000041 AND AD_Tree_ID=10
;

-- Node name: `Purchase Disposition (C_PurchaseCandidate)`
-- 2025-10-02T11:43:17.347Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540979 AND AD_Tree_ID=10
;

-- Node name: `Procurement Planning (PMM_PurchaseCandidate)`
-- 2025-10-02T11:43:17.348Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540785 AND AD_Tree_ID=10
;

-- Node name: `Material Tracking (M_Material_Tracking)`
-- 2025-10-02T11:43:17.349Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540786 AND AD_Tree_ID=10
;

-- Node name: `Product for Procurement Contracts (PMM_Product)`
-- 2025-10-02T11:43:17.349Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540869 AND AD_Tree_ID=10
;

-- Node name: `Procurement Availability Trend (PMM_Week)`
-- 2025-10-02T11:43:17.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540870 AND AD_Tree_ID=10
;

-- Node name: `Request for Proposal (C_RfQ)`
-- 2025-10-02T11:43:17.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540877 AND AD_Tree_ID=10
;

-- Node name: `Request for Proposal Response`
-- 2025-10-02T11:43:17.350Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540879 AND AD_Tree_ID=10
;

-- Node name: `Request for Proposal Responseline (C_RfQResponseLine)`
-- 2025-10-02T11:43:17.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540880 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-02T11:43:17.351Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000047 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-02T11:43:17.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000049 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-02T11:43:17.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000011, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000051 AND AD_Tree_ID=10
;

-- Reordering children of `Billing`
-- Node name: `Zweitbelege RKSV Kassello JSON`
-- 2025-10-02T11:43:33.434Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542256 AND AD_Tree_ID=10
;

-- Node name: `Provisionsberechnung (@PREFIX@de/metas/docs/purchase/commission/report.jasper)`
-- 2025-10-02T11:43:33.434Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542203 AND AD_Tree_ID=10
;

-- Node name: `Invoice verifcation`
-- 2025-10-02T11:43:33.435Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541712 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Candidates (C_Invoice_Candidate)`
-- 2025-10-02T11:43:33.435Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000104 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice Candidates (C_Invoice_Candidate)`
-- 2025-10-02T11:43:33.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541537 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice (C_Invoice)`
-- 2025-10-02T11:43:33.436Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000085 AND AD_Tree_ID=10
;

-- Node name: `Purchase Invoice (C_Invoice)`
-- 2025-10-02T11:43:33.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000086 AND AD_Tree_ID=10
;

-- Node name: `Customs Invoice (C_Customs_Invoice)`
-- 2025-10-02T11:43:33.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541271 AND AD_Tree_ID=10
;

-- Node name: `Shipment Line to Customs Invoice Line (M_InOutLine_To_C_Customs_Invoice_Line)`
-- 2025-10-02T11:43:33.437Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541418 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2025-10-02T11:43:33.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000059 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2025-10-02T11:43:33.438Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000067 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2025-10-02T11:43:33.439Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000077 AND AD_Tree_ID=10
;

-- Node name: `Invoicing Pool (C_DocType_Invoicing_Pool)`
-- 2025-10-02T11:43:33.439Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542033 AND AD_Tree_ID=10
;

-- Node name: `Enqueue selection for invoicing and pdf printing (de.metas.printing.process.C_Invoice_Candidate_EnqueueSelectionForInvoicingAndPDFConcatenating)`
-- 2025-10-02T11:43:33.440Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541886 AND AD_Tree_ID=10
;

-- Node name: `Commission Forecast`
-- 2025-10-02T11:43:33.440Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542214 AND AD_Tree_ID=10
;

-- Node name: `Sales Invoice Report (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-10-02T11:43:33.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542186 AND AD_Tree_ID=10
;

-- Node name: `Invoice Payment Allocations Report (Excel) (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2025-10-02T11:43:33.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000018, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542227 AND AD_Tree_ID=10
;

-- Name: Invoice_RKSV
-- 2025-10-02T12:16:36.176Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540747,'C_Invoice.PaymentRule IN (''B'', ''K'') AND C_Invoice.IsSOTrx = ''Y'' AND C_Invoice.Processed = ''Y'' AND C_Invoice.DocStatus IN (''CO'', ''CL'')',TO_TIMESTAMP('2025-10-02 12:16:35.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Invoice_RKSV','S',TO_TIMESTAMP('2025-10-02 12:16:35.985000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Process: Invoice_RKSV_JSON(de.metas.postgrest.process.json.Invoice_RKSV_JSON)
-- ParameterName: C_Invoice_ID
-- 2025-10-02T12:16:55.048Z
UPDATE AD_Process_Para SET AD_Val_Rule_ID=540747,Updated=TO_TIMESTAMP('2025-10-02 12:16:55.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543003
;
