-- Name: Akontozahlung Übersicht
-- Action Type: W
-- Window: Akontozahlung Übersicht(541576,D)
-- 2023-09-18T14:54:59.172Z
DELETE
FROM AD_Menu_Trl
WHERE AD_Menu_ID = 541979
;

-- 2023-09-18T14:55:00.285Z
DELETE
FROM AD_Menu
WHERE AD_Menu_ID = 541979
;

-- 2023-09-18T14:55:00.298Z
DELETE
FROM AD_TreeNodeMM n
WHERE Node_ID = 541979
  AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID = n.AD_Tree_ID AND t.AD_Table_ID = 116)
;

-- Tab: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D)
-- UI Section: main
-- UI Section: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main
-- UI Column: 20
-- UI Column: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20
-- UI Element Group: docs
-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> docs.Akonto Rechnungskandidat
-- Column: C_InterimInvoice_FlatrateTerm.C_Interim_Invoice_Candidate_ID
-- 2023-09-18T15:00:11.142Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610772
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> docs.Rechnungskandidat für Einbehalt
-- Column: C_InterimInvoice_FlatrateTerm.C_Invoice_Candidate_Withholding_ID
-- 2023-09-18T15:00:11.157Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610771
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> docs.Auftragsposition
-- Column: C_InterimInvoice_FlatrateTerm.C_OrderLine_ID
-- 2023-09-18T15:00:11.169Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610769
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> docs.Pauschale - Vertragsperiode
-- Column: C_InterimInvoice_FlatrateTerm.C_Flatrate_Term_ID
-- 2023-09-18T15:00:11.179Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610768
;

-- 2023-09-18T15:00:11.186Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549607
;

-- UI Column: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20
-- UI Element Group: org
-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> org.Mandant
-- Column: C_InterimInvoice_FlatrateTerm.AD_Client_ID
-- 2023-09-18T15:00:14.489Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610782
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> org.Sektion
-- Column: C_InterimInvoice_FlatrateTerm.AD_Org_ID
-- 2023-09-18T15:00:16.758Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610766
;

-- 2023-09-18T15:00:17.860Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549606
;

-- UI Column: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20
-- UI Element Group: flags
-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 20 -> flags.Aktiv
-- Column: C_InterimInvoice_FlatrateTerm.IsActive
-- 2023-09-18T15:00:18.984Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610767
;

-- 2023-09-18T15:00:18.988Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549605
;

-- 2023-09-18T15:00:18.992Z
DELETE
FROM AD_UI_Column
WHERE AD_UI_Column_ID = 546219
;

-- UI Section: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main
-- UI Column: 10
-- UI Column: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10
-- UI Element Group: default
-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10 -> default.Währung
-- Column: C_InterimInvoice_FlatrateTerm.C_Currency_ID
-- 2023-09-18T15:00:22.347Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610778
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10 -> default.Einzelpreis
-- Column: C_InterimInvoice_FlatrateTerm.PriceActual
-- 2023-09-18T15:00:36.897Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610777
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10 -> default.Maßeinheit
-- Column: C_InterimInvoice_FlatrateTerm.C_UOM_ID
-- 2023-09-18T15:00:41.383Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610776
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10 -> default.Berechn. Menge
-- Column: C_InterimInvoice_FlatrateTerm.QtyInvoiced
-- 2023-09-18T15:00:42.507Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610775
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10 -> default.Liefermenge
-- Column: C_InterimInvoice_FlatrateTerm.QtyDeliveredInUOM
-- 2023-09-18T15:00:49.239Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610774
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> main -> 10 -> default.Bestellt/ Beauftragt
-- Column: C_InterimInvoice_FlatrateTerm.QtyOrdered
-- 2023-09-18T15:00:49.250Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610773
;

-- 2023-09-18T15:00:49.254Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549603
;

-- 2023-09-18T15:00:49.258Z
DELETE
FROM AD_UI_Column
WHERE AD_UI_Column_ID = 546218
;

-- 2023-09-18T15:00:49.260Z
DELETE
FROM AD_UI_Section_Trl
WHERE AD_UI_Section_ID = 545121
;

-- 2023-09-18T15:00:49.271Z
DELETE
FROM AD_UI_Section
WHERE AD_UI_Section_ID = 545121
;

-- 2023-09-18T15:02:20.210Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702509
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Produkt
-- Column: C_InterimInvoice_FlatrateTerm.M_Product_ID
-- 2023-09-18T15:02:20.213Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702509
;

-- 2023-09-18T15:02:20.216Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702509
;

-- 2023-09-18T15:02:40.465Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702481
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Organisation
-- Column: C_InterimInvoice_FlatrateTerm.AD_Org_ID
-- 2023-09-18T15:02:40.467Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702481
;

-- 2023-09-18T15:02:40.472Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702481
;

-- 2023-09-18T15:02:40.536Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702483
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Aktiv
-- Column: C_InterimInvoice_FlatrateTerm.IsActive
-- 2023-09-18T15:02:40.539Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702483
;

-- 2023-09-18T15:02:40.544Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702483
;

-- 2023-09-18T15:02:49.485Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702494
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Einzelpreis
-- Column: C_InterimInvoice_FlatrateTerm.PriceActual
-- 2023-09-18T15:02:49.487Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702494
;

-- 2023-09-18T15:02:49.491Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702494
;

-- 2023-09-18T15:02:49.544Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702495
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Währung
-- Column: C_InterimInvoice_FlatrateTerm.C_Currency_ID
-- 2023-09-18T15:02:49.547Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702495
;

-- 2023-09-18T15:02:49.550Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702495
;

-- 2023-09-18T15:03:08.643Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702485
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Pauschale - Vertragsperiode
-- Column: C_InterimInvoice_FlatrateTerm.C_Flatrate_Term_ID
-- 2023-09-18T15:03:08.646Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702485
;

-- 2023-09-18T15:03:08.648Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702485
;

-- 2023-09-18T15:03:12.042Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702486
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Auftragsposition
-- Column: C_InterimInvoice_FlatrateTerm.C_OrderLine_ID
-- 2023-09-18T15:03:13.138Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702486
;

-- 2023-09-18T15:03:13.142Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702486
;

-- 2023-09-18T15:03:14.296Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702648
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Auftrag
-- Column: C_InterimInvoice_FlatrateTerm.C_Order_ID
-- 2023-09-18T15:03:14.299Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702648
;

-- 2023-09-18T15:03:14.302Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702648
;

-- 2023-09-18T15:03:22.161Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702488
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Rechnungskandidat für Einbehalt
-- Column: C_InterimInvoice_FlatrateTerm.C_Invoice_Candidate_Withholding_ID
-- 2023-09-18T15:03:32.335Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702488
;

-- 2023-09-18T15:03:32.361Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702488
;

-- 2023-09-18T15:03:39.084Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702489
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Akonto Rechnungskandidat
-- Column: C_InterimInvoice_FlatrateTerm.C_Interim_Invoice_Candidate_ID
-- 2023-09-18T15:03:39.088Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702489
;

-- 2023-09-18T15:03:40.205Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702489
;

-- 2023-09-18T15:03:46.982Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702490
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Bestellt/ Beauftragt
-- Column: C_InterimInvoice_FlatrateTerm.QtyOrdered
-- 2023-09-18T15:03:46.986Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702490
;

-- 2023-09-18T15:03:46.994Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702490
;

-- 2023-09-18T15:03:49.235Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702491
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Liefermenge
-- Column: C_InterimInvoice_FlatrateTerm.QtyDeliveredInUOM
-- 2023-09-18T15:03:51.492Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702491
;

-- 2023-09-18T15:03:51.511Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702491
;

-- 2023-09-18T15:04:06.183Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702492
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Berechn. Menge
-- Column: C_InterimInvoice_FlatrateTerm.QtyInvoiced
-- 2023-09-18T15:04:06.189Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702492
;

-- 2023-09-18T15:04:06.195Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702492
;

-- 2023-09-18T15:04:10.700Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702493
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Maßeinheit
-- Column: C_InterimInvoice_FlatrateTerm.C_UOM_ID
-- 2023-09-18T15:04:10.702Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702493
;

-- 2023-09-18T15:04:10.705Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702493
;

-- 2023-09-18T15:04:12.977Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702484
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Akontozahlung Übersicht
-- Column: C_InterimInvoice_FlatrateTerm.C_InterimInvoice_FlatrateTerm_ID
-- 2023-09-18T15:04:12.980Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702484
;

-- 2023-09-18T15:04:15.204Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702484
;

-- 2023-09-18T15:04:17.486Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702480
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht(546485,D) -> Mandant
-- Column: C_InterimInvoice_FlatrateTerm.AD_Client_ID
-- 2023-09-18T15:04:17.489Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702480
;

-- 2023-09-18T15:04:17.492Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702480
;

-- Tab: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht
-- Table: C_InterimInvoice_FlatrateTerm
-- 2023-09-18T15:07:22.178Z
DELETE
FROM AD_Tab_Trl
WHERE AD_Tab_ID = 546485
;

-- 2023-09-18T15:07:22.180Z
DELETE
FROM AD_Tab
WHERE AD_Tab_ID = 546485
;

-- Tab: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D)
-- UI Section: main
-- UI Section: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> main
-- UI Column: 10
-- UI Column: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> main -> 10
-- UI Element Group: default
-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> main -> 10 -> default.Aktiv
-- Column: C_InterimInvoice_FlatrateTerm_Line.IsActive
-- 2023-09-18T15:10:47.502Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610785
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> main -> 10 -> default.Sektion
-- Column: C_InterimInvoice_FlatrateTerm_Line.AD_Org_ID
-- 2023-09-18T15:10:48.637Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610786
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> main -> 10 -> default.Rechnungsposition
-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InvoiceLine_ID
-- 2023-09-18T15:10:48.649Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610784
;

-- UI Element: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> main -> 10 -> default.Versand-/Wareneingangsposition
-- Column: C_InterimInvoice_FlatrateTerm_Line.M_InOutLine_ID
-- 2023-09-18T15:10:48.657Z
DELETE
FROM AD_UI_Element
WHERE AD_UI_Element_ID = 610783
;

-- 2023-09-18T15:10:48.660Z
DELETE
FROM AD_UI_ElementGroup
WHERE AD_UI_ElementGroup_ID = 549604
;

-- 2023-09-18T15:10:48.663Z
DELETE
FROM AD_UI_Column
WHERE AD_UI_Column_ID = 546220
;

-- 2023-09-18T15:10:48.664Z
DELETE
FROM AD_UI_Section_Trl
WHERE AD_UI_Section_ID = 545122
;

-- 2023-09-18T15:10:48.667Z
DELETE
FROM AD_UI_Section
WHERE AD_UI_Section_ID = 545122
;

-- 2023-09-18T15:20:15.005Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702650
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Lieferung/Wareneingang
-- Column: C_InterimInvoice_FlatrateTerm_Line.M_InOut_ID
-- 2023-09-18T15:20:16.177Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702650
;

-- 2023-09-18T15:20:16.186Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702650
;

-- 2023-09-18T15:20:29.039Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702504
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Versand-/Wareneingangsposition
-- Column: C_InterimInvoice_FlatrateTerm_Line.M_InOutLine_ID
-- 2023-09-18T15:20:29.042Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702504
;

-- 2023-09-18T15:20:29.044Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702504
;

-- 2023-09-18T15:20:32.536Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702499
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Aktiv
-- Column: C_InterimInvoice_FlatrateTerm_Line.IsActive
-- 2023-09-18T15:20:32.538Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702499
;

-- 2023-09-18T15:20:32.541Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702499
;

-- 2023-09-18T15:20:33.671Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702497
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Organisation
-- Column: C_InterimInvoice_FlatrateTerm_Line.AD_Org_ID
-- 2023-09-18T15:20:33.674Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702497
;

-- 2023-09-18T15:20:33.678Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702497
;

-- 2023-09-18T15:20:33.729Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702649
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Rechnung
-- Column: C_InterimInvoice_FlatrateTerm_Line.C_Invoice_ID
-- 2023-09-18T15:20:33.731Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702649
;

-- 2023-09-18T15:20:33.734Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702649
;

-- 2023-09-18T15:20:34.887Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702502
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Rechnungsposition
-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InvoiceLine_ID
-- 2023-09-18T15:20:34.898Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702502
;

-- 2023-09-18T15:20:37.202Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702502
;

-- 2023-09-18T15:20:38.376Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702505
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Akontozahlung Übersicht
-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InterimInvoice_FlatrateTerm_ID
-- 2023-09-18T15:20:38.378Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702505
;

-- 2023-09-18T15:20:38.382Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702505
;

-- 2023-09-18T15:20:38.433Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702500
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Akontozahlung Übersicht Position
-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InterimInvoice_FlatrateTerm_Line_ID
-- 2023-09-18T15:20:38.435Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702500
;

-- 2023-09-18T15:20:38.438Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702500
;

-- 2023-09-18T15:20:39.542Z
DELETE
FROM AD_Element_Link
WHERE AD_Field_ID = 702496
;

-- Field: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position(546486,D) -> Mandant
-- Column: C_InterimInvoice_FlatrateTerm_Line.AD_Client_ID
-- 2023-09-18T15:20:39.544Z
DELETE
FROM AD_Field_Trl
WHERE AD_Field_ID = 702496
;

-- 2023-09-18T15:20:39.547Z
DELETE
FROM AD_Field
WHERE AD_Field_ID = 702496
;

-- Tab: Akontozahlung Übersicht(541576,D) -> Akontozahlung Übersicht Position
-- Table: C_InterimInvoice_FlatrateTerm_Line
-- 2023-09-18T15:20:50.015Z
DELETE
FROM AD_Tab_Trl
WHERE AD_Tab_ID = 546486
;

-- 2023-09-18T15:20:50.017Z
DELETE
FROM AD_Tab
WHERE AD_Tab_ID = 546486
;

-- Window: Akontozahlung Übersicht, InternalName=null
-- 2023-09-18T15:21:05.210Z
DELETE
FROM AD_Element_Link
WHERE AD_Window_ID = 541576
;

-- 2023-09-18T15:21:05.211Z
DELETE
FROM AD_Window_Trl
WHERE AD_Window_ID = 541576
;

-- 2023-09-18T15:21:05.216Z
DELETE
FROM AD_Window
WHERE AD_Window_ID = 541576
;

-- 2023-09-18T15:24:35.049Z
DELETE
FROM AD_RelationType
WHERE AD_RelationType_ID = 540358
;

-- Name: C_Invoice_Candidate via C_InterimInvoice_FlatrateTerm
-- 2023-09-18T15:24:47.717Z
DELETE
FROM AD_Reference_Trl
WHERE AD_Reference_ID = 541645
;

-- 2023-09-18T15:24:47.720Z
DELETE
FROM AD_Reference
WHERE AD_Reference_ID = 541645
;

-- 2023-09-18T20:02:08.055Z
DELETE
FROM AD_RelationType
WHERE AD_RelationType_ID = 540359
;

-- Name: C_Invoice via C_InterimInvoice_FlatrateTerm
-- 2023-09-18T20:02:18.399Z
DELETE
FROM AD_Reference_Trl
WHERE AD_Reference_ID = 541646
;

-- 2023-09-18T20:02:18.403Z
DELETE
FROM AD_Reference
WHERE AD_Reference_ID = 541646
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.AD_Client_ID
-- 2023-09-18T18:19:19.480Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583848
;

-- 2023-09-18T18:19:19.490Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583848
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.AD_Org_ID
-- 2023-09-18T18:19:20.436Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583849
;

-- 2023-09-18T18:19:20.440Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583849
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InterimInvoice_FlatrateTerm_ID
-- 2023-09-18T18:19:21.085Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583861
;

-- 2023-09-18T18:19:21.088Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583861
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InterimInvoice_FlatrateTerm_Line_ID
-- 2023-09-18T18:19:21.722Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583856
;

-- 2023-09-18T18:19:21.727Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583856
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.C_Invoice_ID
-- 2023-09-18T18:19:22.302Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583857
;

-- 2023-09-18T18:19:22.305Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583857
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.C_InvoiceLine_ID
-- 2023-09-18T18:19:22.869Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583858
;

-- 2023-09-18T18:19:22.873Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583858
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.Created
-- 2023-09-18T18:19:23.380Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583851
;

-- 2023-09-18T18:19:23.383Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583851
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.CreatedBy
-- 2023-09-18T18:19:23.950Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583852
;

-- 2023-09-18T18:19:23.954Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583852
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.IsActive
-- 2023-09-18T18:19:24.539Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583853
;

-- 2023-09-18T18:19:24.542Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583853
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.M_InOut_ID
-- 2023-09-18T18:19:25.120Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583859
;

-- 2023-09-18T18:19:25.123Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583859
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.M_InOutLine_ID
-- 2023-09-18T18:19:25.666Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583860
;

-- 2023-09-18T18:19:25.670Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583860
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.Updated
-- 2023-09-18T18:19:26.263Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583854
;

-- 2023-09-18T18:19:26.266Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583854
;

-- Column: C_InterimInvoice_FlatrateTerm_Line.UpdatedBy
-- 2023-09-18T18:19:26.806Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583855
;

-- 2023-09-18T18:19:26.809Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583855
;

-- Table: C_InterimInvoice_FlatrateTerm_Line
-- 2023-09-18T18:19:34.793Z
DELETE
FROM AD_Table_Trl
WHERE AD_Table_ID = 542194
;

-- 2023-09-18T18:19:34.797Z
DELETE
FROM AD_Table
WHERE AD_Table_ID = 542194
;

-- Column: C_InterimInvoice_FlatrateTerm.AD_Client_ID
-- 2023-09-18T18:19:47.444Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583824
;

-- 2023-09-18T18:19:47.449Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583824
;

-- Column: C_InterimInvoice_FlatrateTerm.AD_Org_ID
-- 2023-09-18T18:19:48Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583825
;

-- 2023-09-18T18:19:48.003Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583825
;

-- Column: C_InterimInvoice_FlatrateTerm.C_Currency_ID
-- 2023-09-18T18:19:48.553Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583847
;

-- 2023-09-18T18:19:48.556Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583847
;

-- Column: C_InterimInvoice_FlatrateTerm.C_Flatrate_Term_ID
-- 2023-09-18T18:19:49.185Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583833
;

-- 2023-09-18T18:19:49.189Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583833
;

-- Column: C_InterimInvoice_FlatrateTerm.C_Interim_Invoice_Candidate_ID
-- 2023-09-18T18:19:49.808Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583837
;

-- 2023-09-18T18:19:49.811Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583837
;

-- Column: C_InterimInvoice_FlatrateTerm.C_InterimInvoice_FlatrateTerm_ID
-- 2023-09-18T18:19:50.413Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583832
;

-- 2023-09-18T18:19:50.416Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583832
;

-- Column: C_InterimInvoice_FlatrateTerm.C_Invoice_Candidate_Withholding_ID
-- 2023-09-18T18:19:51.022Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583836
;

-- 2023-09-18T18:19:51.024Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583836
;

-- Column: C_InterimInvoice_FlatrateTerm.C_Order_ID
-- 2023-09-18T18:19:51.622Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583835
;

-- 2023-09-18T18:19:51.625Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583835
;

-- Column: C_InterimInvoice_FlatrateTerm.C_OrderLine_ID
-- 2023-09-18T18:19:52.231Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583834
;

-- 2023-09-18T18:19:52.235Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583834
;

-- Column: C_InterimInvoice_FlatrateTerm.Created
-- 2023-09-18T18:19:52.805Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583827
;

-- 2023-09-18T18:19:52.808Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583827
;

-- Column: C_InterimInvoice_FlatrateTerm.CreatedBy
-- 2023-09-18T18:19:53.396Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583828
;

-- 2023-09-18T18:19:53.400Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583828
;

-- Column: C_InterimInvoice_FlatrateTerm.C_UOM_ID
-- 2023-09-18T18:19:53.986Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583845
;

-- 2023-09-18T18:19:53.989Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583845
;

-- Column: C_InterimInvoice_FlatrateTerm.IsActive
-- 2023-09-18T18:19:54.643Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583829
;

-- 2023-09-18T18:19:54.659Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583829
;

-- Column: C_InterimInvoice_FlatrateTerm.M_Product_ID
-- 2023-09-18T18:19:55.327Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583864
;

-- 2023-09-18T18:19:55.330Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583864
;

-- Column: C_InterimInvoice_FlatrateTerm.PriceActual
-- 2023-09-18T18:19:55.957Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583846
;

-- 2023-09-18T18:19:55.961Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583846
;

-- Column: C_InterimInvoice_FlatrateTerm.QtyDeliveredInUOM
-- 2023-09-18T18:19:56.581Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583843
;

-- 2023-09-18T18:19:56.584Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583843
;

-- Column: C_InterimInvoice_FlatrateTerm.QtyInvoiced
-- 2023-09-18T18:19:57.161Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583844
;

-- 2023-09-18T18:19:57.164Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583844
;

-- Column: C_InterimInvoice_FlatrateTerm.QtyOrdered
-- 2023-09-18T18:19:57.857Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583842
;

-- 2023-09-18T18:19:57.863Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583842
;

-- Column: C_InterimInvoice_FlatrateTerm.Updated
-- 2023-09-18T18:19:58.420Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583830
;

-- 2023-09-18T18:19:58.425Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583830
;

-- Column: C_InterimInvoice_FlatrateTerm.UpdatedBy
-- 2023-09-18T18:19:59.080Z
DELETE
FROM AD_Column_Trl
WHERE AD_Column_ID = 583831
;

-- 2023-09-18T18:19:59.083Z
DELETE
FROM AD_Column
WHERE AD_Column_ID = 583831
;

-- Table: C_InterimInvoice_FlatrateTerm
-- 2023-09-18T18:20:06.636Z
DELETE
FROM AD_Table_Trl
WHERE AD_Table_ID = 542193
;

-- 2023-09-18T18:20:06.640Z
DELETE
FROM AD_Table
WHERE AD_Table_ID = 542193
;

DROP TABLE IF EXISTS C_InterimInvoice_FlatrateTerm_Line
;

DROP TABLE IF EXISTS C_InterimInvoice_FlatrateTerm
;

