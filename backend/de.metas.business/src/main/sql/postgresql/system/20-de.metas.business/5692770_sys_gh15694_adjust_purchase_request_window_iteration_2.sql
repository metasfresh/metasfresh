-- Field: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> Lieferant
-- Column: M_RequisitionLine.C_BPartner_ID
-- 2023-06-22T16:27:30.575373734Z
UPDATE AD_Field SET AD_Name_ID=542647, Description=NULL, Help=NULL, Name='Lieferant',Updated=TO_TIMESTAMP('2023-06-22 17:27:30.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715440
;

-- 2023-06-22T16:27:30.579286252Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Lieferant' WHERE AD_Field_ID=715440 AND AD_Language='de_DE'
;

-- 2023-06-22T16:27:30.601246414Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542647)
;

-- 2023-06-22T16:27:30.610962610Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715440
;

-- 2023-06-22T16:27:30.613814428Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715440)
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Produkt
-- Column: M_RequisitionLine.M_Product_ID
-- 2023-06-22T16:29:39.210932843Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550715, SeqNo=120,Updated=TO_TIMESTAMP('2023-06-22 17:29:39.21','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617533
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Maßeinheit
-- Column: M_RequisitionLine.C_UOM_ID
-- 2023-06-22T16:29:56.969305513Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550715, SeqNo=130,Updated=TO_TIMESTAMP('2023-06-22 17:29:56.968','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617534
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Menge
-- Column: M_RequisitionLine.Qty
-- 2023-06-22T16:30:06.593090555Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550715, SeqNo=140,Updated=TO_TIMESTAMP('2023-06-22 17:30:06.592','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617535
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Einzelpreis
-- Column: M_RequisitionLine.PriceActual
-- 2023-06-22T16:30:17.911470989Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550715, SeqNo=150,Updated=TO_TIMESTAMP('2023-06-22 17:30:17.911','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617536
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Zeilennetto
-- Column: M_RequisitionLine.LineNetAmt
-- 2023-06-22T16:30:28.023502997Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550715, SeqNo=160,Updated=TO_TIMESTAMP('2023-06-22 17:30:28.023','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617537
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Währung
-- Column: M_RequisitionLine.C_Currency_ID
-- 2023-06-22T16:30:37.578400277Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=550715, SeqNo=170,Updated=TO_TIMESTAMP('2023-06-22 17:30:37.578','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617541
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Standort
-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-06-22T17:22:41.382998008Z
UPDATE AD_UI_Element SET SeqNo=250,Updated=TO_TIMESTAMP('2023-06-22 18:22:41.382','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617524
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.eMail
-- Column: M_RequisitionLine.EMail
-- 2023-06-22T17:22:46.261542973Z
UPDATE AD_UI_Element SET SeqNo=300,Updated=TO_TIMESTAMP('2023-06-22 18:22:46.261','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617525
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Kanal der Bestellung
-- Column: M_RequisitionLine.OrderChannel
-- 2023-06-22T17:22:52.544331955Z
UPDATE AD_UI_Element SET SeqNo=400,Updated=TO_TIMESTAMP('2023-06-22 18:22:52.544','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617526
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Zahlungsweise
-- Column: M_RequisitionLine.PaymentRule
-- 2023-06-22T17:22:58.169549765Z
UPDATE AD_UI_Element SET SeqNo=500,Updated=TO_TIMESTAMP('2023-06-22 18:22:58.169','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617527
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Lieferant genehmigt
-- Column: M_RequisitionLine.IsVendor
-- 2023-06-22T17:23:04.288801437Z
UPDATE AD_UI_Element SET SeqNo=600,Updated=TO_TIMESTAMP('2023-06-22 18:23:04.288','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617528
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Neuer Lieferant
-- Column: M_RequisitionLine.IsNewSupplier
-- 2023-06-22T17:23:10.264289028Z
UPDATE AD_UI_Element SET SeqNo=800,Updated=TO_TIMESTAMP('2023-06-22 18:23:10.264','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617529
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Neuer Lieferant Name
-- Column: M_RequisitionLine.NewSupplierName
-- 2023-06-22T17:23:16.303968527Z
UPDATE AD_UI_Element SET SeqNo=900,Updated=TO_TIMESTAMP('2023-06-22 18:23:16.303','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617530
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Adresse des neuen Lieferanten
-- Column: M_RequisitionLine.IsNewSupplierAddress
-- 2023-06-22T17:23:24.654957702Z
UPDATE AD_UI_Element SET SeqNo=1000,Updated=TO_TIMESTAMP('2023-06-22 18:23:24.654','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617531
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Neue Lieferantenadresse Details
-- Column: M_RequisitionLine.NewSupplierAddress
-- 2023-06-22T17:23:35.887696220Z
UPDATE AD_UI_Element SET SeqNo=1100,Updated=TO_TIMESTAMP('2023-06-22 18:23:35.887','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617532
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Produkt
-- Column: M_RequisitionLine.M_Product_ID
-- 2023-06-22T17:23:47.577956068Z
UPDATE AD_UI_Element SET SeqNo=1200,Updated=TO_TIMESTAMP('2023-06-22 18:23:47.577','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617533
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Maßeinheit
-- Column: M_RequisitionLine.C_UOM_ID
-- 2023-06-22T17:23:53.199609875Z
UPDATE AD_UI_Element SET SeqNo=1300,Updated=TO_TIMESTAMP('2023-06-22 18:23:53.199','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617534
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Menge
-- Column: M_RequisitionLine.Qty
-- 2023-06-22T17:23:58.879857419Z
UPDATE AD_UI_Element SET SeqNo=1400,Updated=TO_TIMESTAMP('2023-06-22 18:23:58.879','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617535
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Einzelpreis
-- Column: M_RequisitionLine.PriceActual
-- 2023-06-22T17:24:04.551327038Z
UPDATE AD_UI_Element SET SeqNo=1500,Updated=TO_TIMESTAMP('2023-06-22 18:24:04.551','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617536
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Zeilennetto
-- Column: M_RequisitionLine.LineNetAmt
-- 2023-06-22T17:24:09.879741358Z
UPDATE AD_UI_Element SET SeqNo=1600,Updated=TO_TIMESTAMP('2023-06-22 18:24:09.879','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617537
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Währung
-- Column: M_RequisitionLine.C_Currency_ID
-- 2023-06-22T17:24:16.576336605Z
UPDATE AD_UI_Element SET SeqNo=1700,Updated=TO_TIMESTAMP('2023-06-22 18:24:16.576','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617541
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Produkt
-- Column: M_RequisitionLine.M_Product_ID
-- 2023-06-22T17:25:08.527821133Z
UPDATE AD_UI_Element SET SeqNo=21,Updated=TO_TIMESTAMP('2023-06-22 18:25:08.527','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617533
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Maßeinheit
-- Column: M_RequisitionLine.C_UOM_ID
-- 2023-06-22T17:25:15.135309077Z
UPDATE AD_UI_Element SET SeqNo=22,Updated=TO_TIMESTAMP('2023-06-22 18:25:15.135','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617534
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Menge
-- Column: M_RequisitionLine.Qty
-- 2023-06-22T17:25:19.901405235Z
UPDATE AD_UI_Element SET SeqNo=23,Updated=TO_TIMESTAMP('2023-06-22 18:25:19.901','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617535
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Einzelpreis
-- Column: M_RequisitionLine.PriceActual
-- 2023-06-22T17:25:24.575689391Z
UPDATE AD_UI_Element SET SeqNo=24,Updated=TO_TIMESTAMP('2023-06-22 18:25:24.575','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617536
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Zeilennetto
-- Column: M_RequisitionLine.LineNetAmt
-- 2023-06-22T17:25:28.959463608Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2023-06-22 18:25:28.959','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617537
;

-- UI Element: Bestellanforderung(541708,D) -> Bedarfs-Position(546979,D) -> main -> 10 -> main.Währung
-- Column: M_RequisitionLine.C_Currency_ID
-- 2023-06-22T17:25:34.510266328Z
UPDATE AD_UI_Element SET SeqNo=26,Updated=TO_TIMESTAMP('2023-06-22 18:25:34.51','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617541
;

-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-06-22T17:42:52.784440815Z
UPDATE AD_Column SET AD_Reference_ID=19, AD_Val_Rule_ID=131,Updated=TO_TIMESTAMP('2023-06-22 18:42:52.784','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586680
;

-- Column: M_RequisitionLine.C_BPartner_Location_ID
-- 2023-06-22T17:43:00.631615089Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2023-06-22 18:43:00.631','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586680
;

-- Reference: OrderChannel
-- Value: P
-- ValueName: Purchasing
-- 2023-06-22T17:57:53.469362159Z
UPDATE AD_Ref_List SET Name='Einkauf',Updated=TO_TIMESTAMP('2023-06-22 18:57:53.469','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Ref_List_ID=543470
;

-- 2023-06-22T17:57:53.470079014Z
UPDATE AD_Ref_List_Trl trl SET Name='Einkauf' WHERE AD_Ref_List_ID=543470 AND AD_Language='de_DE'
;

-- Reference Item: OrderChannel -> P_Purchasing
-- 2023-06-22T17:58:06.654728997Z
UPDATE AD_Ref_List_Trl SET Name='Einkauf',Updated=TO_TIMESTAMP('2023-06-22 18:58:06.654','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543470
;

-- Reference Item: OrderChannel -> P_Purchasing
-- 2023-06-22T17:58:11.865565191Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-06-22 18:58:11.865','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543470
;

-- Reference Item: OrderChannel -> P_Purchasing
-- 2023-06-22T17:58:17.169865249Z
UPDATE AD_Ref_List_Trl SET IsTranslated='N',Updated=TO_TIMESTAMP('2023-06-22 18:58:17.169','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543470
;

-- Reference Item: OrderChannel -> P_Purchasing
-- 2023-06-22T17:58:25.570491762Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Achat',Updated=TO_TIMESTAMP('2023-06-22 18:58:25.57','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543470
;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- Reference Item: OrderChannel -> S_Self
-- 2023-06-22T17:59:57.076205001Z
UPDATE AD_Ref_List_Trl SET Name='Mitarbeiter',Updated=TO_TIMESTAMP('2023-06-22 18:59:57.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543471
;

-- Reference Item: OrderChannel -> S_Self
-- 2023-06-22T18:00:18.204377688Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y', Name='Employees',Updated=TO_TIMESTAMP('2023-06-22 19:00:18.204','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543471
;

-- Reference Item: OrderChannel -> S_Self
-- 2023-06-22T18:00:23.028176184Z
UPDATE AD_Ref_List_Trl SET IsTranslated='N', Name='Employees',Updated=TO_TIMESTAMP('2023-06-22 19:00:23.028','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543471
;

-- 2023-06-22T18:00:23.031920623Z
UPDATE AD_Ref_List SET Name='Employees' WHERE AD_Ref_List_ID=543471
;

-- Reference Item: OrderChannel -> S_Self
-- 2023-06-22T18:00:30.068193116Z
UPDATE AD_Ref_List_Trl SET Name='Mitarbeiter',Updated=TO_TIMESTAMP('2023-06-22 19:00:30.068','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543471
;

-- 2023-06-22T18:00:30.068957979Z
UPDATE AD_Ref_List SET Name='Mitarbeiter' WHERE AD_Ref_List_ID=543471
;

-- Reference Item: OrderChannel -> S_Self
-- 2023-06-22T18:00:41.892688250Z
UPDATE AD_Ref_List_Trl SET Name='Employes',Updated=TO_TIMESTAMP('2023-06-22 19:00:41.892','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543471
;

-- Field: Bestellanforderung(541708,D) -> Bedarf(546978,D) -> Belegart
-- Column: M_Requisition.C_DocType_ID
-- 2023-06-23T09:17:02.600554684Z
UPDATE AD_Field SET DefaultValue='541708',Updated=TO_TIMESTAMP('2023-06-23 10:17:02.599','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=715409
;
