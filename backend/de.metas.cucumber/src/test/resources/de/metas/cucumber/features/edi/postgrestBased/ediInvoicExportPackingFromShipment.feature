@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00359_EDI_INVOICE_JSON
@F00359
@ghActions:run_on_executor3
Feature: EDI INVOIC JSON of invoices made from shipments with packing material lines
## F00359: EDI INVOIC JSON
##
## Each scenario runs the normal flow: sales order → shipment (goods packed in crates / cartons)
## → invoice candidates → invoice → INVOIC JSON export.
## - The packing material lines come from the shipment, as in production; none is typed into the invoice.
## - A store partner orders and receives the goods; a central partner is billed (the order's bill-to).
## - Taxes are document-level: goods 7 %, packing material 19 %.
## - The shipping warehouse has its own partner: the supplier party (SU) is that partner, because the invoice references the shipment.
## - Lines come in Invoice_Line order; the format sent to the clearing center states it (packing lines in invoice-line order),
##   so the order is asserted, not only the content of each line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    And set project type Sales/Purchase Order to inactive

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_sales   | pricingSystem      | DE           | EUR           | true  |
      | pl_purch   | pricingSystem      | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_sales  | pl_sales       |
      | plv_purch  | pl_purch       |

    And metasfresh contains C_TaxCategory
      | Identifier    |
      | tax7Category  |
      | tax19Category |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | IsDocumentLevel |
      | tax7       | tax7Category     | 7    | DE                       | DE                        | Y               |
      | tax19      | tax19Category    | 19   | DE                       | DE                        | Y               |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1    | OPT.Postal | OPT.City    |
      | loc_store                | DE          | storeStreet 1   | 85080      | storeCity   |
      | loc_central              | DE          | centralStreet 2 | 22297      | centralCity |
      | loc_warehouse            | DE          | depotStreet 3   | 88045      | depotCity   |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | InvoiceRule | REST.Context.Name | REST.Context.Value |
      | store      | Y          | N        | pricingSystem      | D           | storeName         | storeValue         |
      | central    | Y          | N        | pricingSystem      | D           | centralName       | centralValue       |
      | depotOwner | N          | N        | pricingSystem      |             | depotName         | depotValue         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.IsShipToDefault | OPT.IsBillToDefault | OPT.BPartnerName |
      | storeLoc   | 4300000000014 | store                    | loc_store                    | true         | true         | true                | true                | storeSiteName    |
      | centralLoc | 4300000000021 | central                  | loc_central                  | true         | true         | true                | true                | centralSiteName  |
      | depotLoc   | 4300000000045 | depotOwner               | loc_warehouse                | true         | true         | true                | true                | depotSiteName    |

  # ################################################################################################
  # ################################################################################################
  @Id:S32545_RP_A
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00359_EDI_INVOICE_JSON
  @F00359
  Scenario: drop-ship order of two vendors: the INVOIC JSON lists the goods lines and one priced crate line per project
    # Real case: two goods lines of two vendors on a drop-ship warehouse, packed in EPS crates (TU x 11), with size and origin attributes.
    # Test values: the GTIN of one packing instruction and of the crate, the crate's deposit type RC.
    # Two purchase orders → two projects → one crate line per project on shipment and invoice.
    Given temporarily set sys config boolean value true for sys config 'de.metas.handlingunits.inout.SplitShipmentPackingMaterialLinesByProject'
    And temporarily set project type Sales/Purchase Order to active
    And temporarily set sys config boolean value true for sys config "de.metas.order.C_Order_CreatePOFromSOs.CreateDropshipPO"
    And metasfresh contains M_DiscountSchemas:
      | Identifier         | Name                 | DiscountType | ValidFrom  |
      | vendorDiscountPlan | invoic_packing_vndr1 | F            | 2026-01-01 |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor_1   | N          | Y        | pricingSystem      | vendorDiscountPlan   |
      | vendor_2   | N          | Y        | pricingSystem      |                      |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_1   | vendor_1      | Y               | Y               |
      | vendor_2   | vendor_2      | Y               | Y               |
    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse | C_BPartner_ID | C_BPartner_Location_ID |
      | dropshipWarehouse | Y                   | depotOwner    | depotLoc               |
    And metasfresh contains M_Product_Categories:
      | Identifier  |
      | pm_category |
    And metasfresh contains M_Products:
      | Identifier   | M_Product_Category_ID | GTIN          | DepositType | REST.Context.Value | REST.Context.Name |
      | goodsKiku    |                       |               |             | kikuValue          | kikuName          |
      | goodsEvelina |                       |               |             | evelinaValue       | evelinaName       |
      | eps186       | pm_category           | 4000000032540 | RC          | crateValue         | crateName         |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor_1      | eps186       |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_sales              | goodsKiku    | 1.00     | PCE      | tax7Category     |
      | plv_sales              | goodsEvelina | 1.00     | PCE      | tax7Category     |
      | plv_sales              | eps186       | 1.50     | PCE      | tax19Category    |
      | plv_purch              | goodsKiku    | 0.60     | PCE      | tax7Category     |
      | plv_purch              | goodsEvelina | 0.60     | PCE      | tax7Category     |
      | plv_purch              | eps186       | 1.00     | PCE      | tax19Category    |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_eps   |
    And metasfresh contains M_HU_PI_Version:
      | Identifier    | M_HU_PI_ID | HU_UnitType |
      | huVersion_eps | huPI_eps   | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_eps                  | eps186       |
    And metasfresh contains M_HU_PI_Item:
      | Identifier   | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem_eps | huVersion_eps      | 10  | PM       | pm_eps                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID | Qty | M_Product_ID | GTIN          |
      | kiku_eps    | huPIItem_eps    | 11  | goodsKiku    | 4000000032564 |
      | evelina_eps | huPIItem_eps    | 11  | goodsEvelina |               |
    And metasfresh contains M_Attributes:
      | Identifier | Value           | Name            | AttributeValueType |
      | attrSize   | S32545_Groesse  | S32545_Groesse  | S                  |
      | attrOrigin | S32545_Herkunft | S32545_Herkunft | S                  |
    And metasfresh contains M_AttributeSetInstance with identifier "goodsASI":
  """
  {
    "attributeInstances":[
      { "attributeCode":"S32545_Groesse", "valueStr":"70 - 80 mm" },
      { "attributeCode":"S32545_Herkunft", "valueStr":"Deutschland" }
    ]
  }
  """

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | OPT.Bill_Location_ID.Identifier | DeliveryRule | DateOrdered | PreparationDate      | M_Warehouse_ID    | POReference |
      | salesOrder | true    | store         | storeLoc               | centralLoc                      | F            | 2026-07-01  | 2026-06-30T22:00:00Z | dropshipWarehouse | ED04711A    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | C_BPartner_Vendor_ID | M_HU_PI_Item_Product_ID | M_AttributeSetInstance_ID |
      | ol_kiku    | salesOrder | goodsKiku    | 220        | vendor_1             | kiku_eps                | goodsASI                  |
      | ol_evelina | salesOrder | goodsEvelina | 165        | vendor_2             | evelina_eps             | goodsASI                  |
    When the order identified by salesOrder is completed

    Then the order is created:
      | Identifier | Link_Order_ID | C_BPartner_ID | IsSOTrx | DocBaseType | DocStatus | IsDropShip |
      | po_1       | salesOrder    | vendor_1      | false   | POO         | CO        | true       |
      | po_2       | salesOrder    | vendor_2      | false   | POO         | CO        | true       |
    And validate the created orders
      | C_Order_ID | C_Project_ID |
      | po_1       | project_1    |
      | po_2       | project_2    |
    And after not more than 60s, validate C_OrderLine:
      | C_OrderLine_ID | C_Project_ID |
      | ol_kiku        | project_1    |
      | ol_evelina     | project_2    |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_kiku    | ol_kiku        | N             |
      | ss_evelina | ol_evelina     | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_kiku               |
      | ss_evelina            |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | REST.Context.DocumentNo |
      | ss_kiku               | shipment   | shipmentDocNo           |
    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID |
      | ss_evelina            | shipment   |
    # 220 / 11 = 20 crates for project_1, 165 / 11 = 15 crates for project_2
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty | C_Project_ID |
      | crate_project1 | shipment   | eps186       | 20          | project_1    |
      | crate_project2 | shipment   | eps186       | 15          | project_2    |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_kiku        | ic_kiku                |
      | ol_evelina     | ic_evelina             |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID |
      | ic_crate_project1      | crate_project1 |
      | ic_crate_project2      | crate_project2 |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IgnoreInvoiceSchedule |
      | ic_kiku                | Y                     |
      | ic_evelina             | Y                     |
      | ic_crate_project1      | Y                     |
      | ic_crate_project2      | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID | REST.Context | REST.Context.DocumentNo |
      | invoice      | ic_kiku                | invoice_ID   | invoiceDocNo            |
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced | M_InOutLine_ID | C_Project_ID |
      | invoice      | eps186       | 20          | crate_project1 | project_1    |
      | invoice      | eps186       | 15          | crate_project2 | project_2    |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/C_Invoice_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": "@invoice_ID@"
    }
  ]
}
    """
    # - Lines: 2 goods lines (7 %) + 1 crate line per project (19 %, Packaging marker, Order_Line null).
    # - Σ NetAmt 220 + 165 + 30.00 + 22.50 = 437.50 = Invoice_TotalLines; VAT 7 % of 385.00 = 26.95, 19 % of 52.50 = 9.98 (document-level); gross 474.43.
    # - Partners: BY / DP / SN = store location, IV = central (bill-to) location, SU = the shipping warehouse's partner location (the invoice references the shipment); exactly one DP.
    # - Crate: GTIN 4000000032540 as CU and supplier GTIN, no TU GTIN (no packing instruction on the line), deposit type RC.
    # - Goods: the Kiku packing instruction's GTIN is the line's TU GTIN; Evelina's packing instruction has none.
    # - The packing candidates are invoiced in no fixed order: which crate line gets line 30 or 40 varies, so the crate lines are matched by content.
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Invoice_Receiver_Tec_GLN": "4300000000021",
      "Invoice_Sender_Tec_GLN": null,
      "Invoice_Sender_CountryCode": "DE",
      "Invoice_Sender_VATaxId": null,
      "Invoice_DocumentNo": "@invoiceDocNo@",
      "Invoice_Date": "2026-07-01T00:00:00",
      "Invoice_Acct_Date": "2026-07-01T00:00:00",
      "DocType_Base": "ARI",
      "DocType_Sub": null,
      "CreditMemo_Reason": null,
      "CreditMemo_ReasonText": null,
      "Order_POReference": "ED04711A",
      "Order_Date": "2026-07-01T00:00:00",
      "Shipment_Date": "2026-07-01T00:00:00",
      "Shipment_DocumentNo": "@shipmentDocNo@",
      "DESADV_DocumentNo": null,
      "Invoice_Currency_Code": "EUR",
      "Invoice_GrandTotal": 474.43,
      "Invoice_TotalLines": 437.5,
      "Invoice_TotalVAT": 36.93,
      "Invoice_TotalVATBaseAmt": 437.5,
      "Invoice_SurchargeAmt": 0.0,
      "Invoice_TotalLinesWithSurchargeAmt": 437.5,
      "Invoice_TotalVATWithSurchargeAmt": 36.93,
      "Invoice_GrandTotalWithSurchargeAmt": 474.43,
      "Partners": [
        {
          "EANCOM_LocationType": "BY",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "DP",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "IV",
          "GLN": "4300000000021",
          "Name": "@centralName@",
          "Name2": null,
          "PartnerNo": "@centralValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "centralSiteName",
          "Setup_Place_No": null,
          "Address1": "centralStreet 2",
          "Address2": null,
          "Postal": "22297",
          "City": "centralCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SN",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SU",
          "GLN": "4300000000045",
          "Name": "@depotName@",
          "Name2": null,
          "PartnerNo": "@depotValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "depotSiteName",
          "Setup_Place_No": null,
          "Address1": "depotStreet 3",
          "Address2": null,
          "Postal": "88045",
          "City": "depotCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        }
      ],
      "PaymentTerms": [
        {
          "Net_Days": 30
        }
      ],
      "PaymentDiscounts": [
        {
          "Discount_Name": "30 Tage netto",
          "Tax_Percent": 7,
          "Discount_Days": 0,
          "Discount_Percent": 0,
          "Discount_BaseAmt": 385.0,
          "Discount_Amt": 0.0
        },
        {
          "Discount_Name": "30 Tage netto",
          "Tax_Percent": 19,
          "Discount_Days": 0,
          "Discount_Percent": 0,
          "Discount_BaseAmt": 52.5,
          "Discount_Amt": 0.0
        }
      ],
      "Lines": [
        {
          "Invoice_Line": 10,
          "Invoice_QtyInvoiced": 220,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711A",
          "Order_Line": 10,
          "Order_QtyInvoiced": 220,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": 220,
          "Tax_Percent": 7,
          "Tax_Amount": 15.4,
          "Product_Name": "@kikuName@",
          "Product_Description": "@kikuName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": "4000000032564",
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@kikuValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_Line": 20,
          "Invoice_QtyInvoiced": 165,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711A",
          "Order_Line": 20,
          "Order_QtyInvoiced": 165,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": 165,
          "Tax_Percent": 7,
          "Tax_Amount": 11.55,
          "Product_Name": "@evelinaName@",
          "Product_Description": "@evelinaName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@evelinaValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_QtyInvoiced": 15,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711A",
          "Order_Line": null,
          "Order_QtyInvoiced": 15,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1.5,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": null,
          "NetAmt": 22.5,
          "Tax_Percent": 19,
          "Tax_Amount": 4.28,
          "Product_Name": "@crateName@",
          "Product_Description": "@crateName@",
          "Product_Buyer_CU_GTIN": "4000000032540",
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": "4000000032540",
          "Product_Supplier_ProductNo": "@crateValue@",
          "Product_DepositType": "RC",
          "Packaging": {
            "IsPackagingMaterial": true
          }
        },
        {
          "Invoice_QtyInvoiced": 20,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711A",
          "Order_Line": null,
          "Order_QtyInvoiced": 20,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1.5,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": null,
          "NetAmt": 30.0,
          "Tax_Percent": 19,
          "Tax_Amount": 5.7,
          "Product_Name": "@crateName@",
          "Product_Description": "@crateName@",
          "Product_Buyer_CU_GTIN": "4000000032540",
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": "4000000032540",
          "Product_Supplier_ProductNo": "@crateValue@",
          "Product_DepositType": "RC",
          "Packaging": {
            "IsPackagingMaterial": true
          }
        }
      ],
      "Sums": [
        {
          "TotalAmt": 62.48,
          "Tax_Amt": 9.98,
          "Tax_BaseAmt": 52.5,
          "Tax_Percent": 19,
          "Tax_Exempt": false,
          "SurchargeAmt": 0.0,
          "Tax_BaseAmtWithSurchargeAmt": 52.5,
          "Tax_AmtWithSurchargeAmt": 9.98
        },
        {
          "TotalAmt": 411.95,
          "Tax_Amt": 26.95,
          "Tax_BaseAmt": 385,
          "Tax_Percent": 7,
          "Tax_Exempt": false,
          "SurchargeAmt": 0.0,
          "Tax_BaseAmtWithSurchargeAmt": 385.0,
          "Tax_AmtWithSurchargeAmt": 26.95
        }
      ],
      "Version": "0.2"
    }
  ]
}
    """
    And the INVOIC JSON response lists its Lines in this Invoice_Line order:
      | Invoice_Line |
      | 10           |
      | 20           |
      | 30           |
      | 40           |

  # ################################################################################################
  # ################################################################################################
  @Id:S32545_RP_B
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00359_EDI_INVOICE_JSON
  @F00359
  Scenario: order with the contract's bonus line: the INVOIC JSON lists goods, the bonus line and a 0,00 crate line without GTIN
    # Real case: goods packed in EPS crates (TU x 11), the central partner's contract adds a 3 % bonus line on completion.
    # The crate carries no GTIN and no deposit type and is priced 0,00 (packing master data without GTIN / deposit type, not charged).
    # Line order: here the bonus line is on the same shipment as the goods, so the invoice lists goods, bonus, crate.
    # On the source invoice the bonus line comes after the crate line; how it reached that invoice is not known.
    # Only the ascending Invoice_Line order is the rule; which line gets which number follows the invoicing.
    Given metasfresh contains M_Product_Categories:
      | Identifier     |
      | goods_category |
      | pm_category    |
    And metasfresh contains M_Products:
      | Identifier   | M_Product_Category_ID | IsStocked | REST.Context.Value | REST.Context.Name |
      | goodsEvelina | goods_category        | false     | evelinaValue       | evelinaName       |
      | bonusWare    | goods_category        | false     | bonusValue         | bonusName         |
      | eps186       | pm_category           | false     | crateValue         | crateName         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_sales              | goodsEvelina | 1.00     | PCE      | tax7Category     |
      | plv_sales              | bonusWare    | 1.00     | PCE      | tax7Category     |
      | plv_sales              | eps186       | 0.00     | PCE      | tax19Category    |
    And metasfresh contains M_HU_PI:
      | Identifier |
      | huPI_eps   |
    And metasfresh contains M_HU_PI_Version:
      | Identifier    | M_HU_PI_ID | HU_UnitType |
      | huVersion_eps | huPI_eps   | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_eps                  | eps186       |
    And metasfresh contains M_HU_PI_Item:
      | Identifier   | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem_eps | huVersion_eps      | 10  | PM       | pm_eps                  |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier  | M_HU_PI_Item_ID | Qty | M_Product_ID |
      | evelina_eps | huPIItem_eps    | 11  | goodsEvelina |
    And metasfresh contains M_Warehouse:
      | Identifier    | C_BPartner_ID | C_BPartner_Location_ID |
      | mainWarehouse | depotOwner    | depotLoc               |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027                 |
    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier  | Name          | OPT.IsAdditive |
      | bonusSchema | Bonus Ware 3% | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier      | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | bonusSchemaLine | bonusSchema                              | bonusWare               | 3                         | goods_category                       |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier    | Name                   | C_CompensationGroup_Schema_ID.Identifier |
      | bonusSettings | Bonus Ware 3% settings | bonusSchema                              |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | bonusSettings                                      | docTypeSalesOrder       |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier      | Name                     | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | bonusConditions | Bonus Ware 3% conditions | CompensationGroup | zeroDurTrans                            | bonusSettings                                          |
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | bonusTerm  | bonusConditions                     | central                     | 2026-06-15 | 2026-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by bonusTerm is completed

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | OPT.Bill_Location_ID.Identifier | DeliveryRule | DateOrdered | PreparationDate      | M_Warehouse_ID | POReference |
      | salesOrder | true    | store         | storeLoc               | centralLoc                      | F            | 2026-07-01  | 2026-06-30T22:00:00Z | mainWarehouse  | ED04711B    |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_evelina | salesOrder | goodsEvelina | 550        | evelina_eps             |
    When the order identified by salesOrder is completed

    # 3 % of 550,00
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price  | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_bonus                  | salesOrder            | bonusWare               | 1          | true                        | -16.50 | bonusTerm                         |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_evelina | ol_evelina     | N             |
      | ss_bonus   | ol_bonus       | N             |
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_evelina            |
      | ss_bonus              |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | REST.Context.DocumentNo |
      | ss_evelina            | shipment   | shipmentDocNo           |
    # 550 / 11 = 50 crates
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty |
      | crate_line     | shipment   | eps186       | 50          |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_evelina     | ic_evelina             |
      | ol_bonus       | ic_bonus               |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID |
      | ic_crate               | crate_line     |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IgnoreInvoiceSchedule |
      | ic_evelina             | Y                     |
      | ic_bonus               | Y                     |
      | ic_crate               | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID | REST.Context | REST.Context.DocumentNo |
      | invoice      | ic_evelina             | invoice_ID   | invoiceDocNo            |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/C_Invoice_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": "@invoice_ID@"
    }
  ]
}
    """
    # - Lines: goods 550 × 1.00, the contract's bonus line 1 × -16.50 (both 7 %, with an order line), crate 50 × 0.00 (19 %, Packaging marker, Order_Line null).
    # - The crate has no GTIN and no deposit type: identified by Packaging and Product_Supplier_ProductNo.
    # - Σ NetAmt 550 - 16.50 + 0 = 533.50 = Invoice_TotalLines; VAT 7 % of 533.50 = 37.35 (document-level; the line taxes sum to 37.34), 19 % of 0 = 0; gross 570.85.
    # - Partners as in the drop-ship case: BY / DP / SN = store, IV = central, SU = the warehouse partner location; exactly one DP.
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Invoice_Receiver_Tec_GLN": "4300000000021",
      "Invoice_Sender_Tec_GLN": null,
      "Invoice_Sender_CountryCode": "DE",
      "Invoice_Sender_VATaxId": null,
      "Invoice_DocumentNo": "@invoiceDocNo@",
      "Invoice_Date": "2026-07-01T00:00:00",
      "Invoice_Acct_Date": "2026-07-01T00:00:00",
      "DocType_Base": "ARI",
      "DocType_Sub": null,
      "CreditMemo_Reason": null,
      "CreditMemo_ReasonText": null,
      "Order_POReference": "ED04711B",
      "Order_Date": "2026-07-01T00:00:00",
      "Shipment_Date": "2026-07-01T00:00:00",
      "Shipment_DocumentNo": "@shipmentDocNo@",
      "DESADV_DocumentNo": null,
      "Invoice_Currency_Code": "EUR",
      "Invoice_GrandTotal": 570.85,
      "Invoice_TotalLines": 533.5,
      "Invoice_TotalVAT": 37.35,
      "Invoice_TotalVATBaseAmt": 533.5,
      "Invoice_SurchargeAmt": 0.0,
      "Invoice_TotalLinesWithSurchargeAmt": 533.5,
      "Invoice_TotalVATWithSurchargeAmt": 37.35,
      "Invoice_GrandTotalWithSurchargeAmt": 570.85,
      "Partners": [
        {
          "EANCOM_LocationType": "BY",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "DP",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "IV",
          "GLN": "4300000000021",
          "Name": "@centralName@",
          "Name2": null,
          "PartnerNo": "@centralValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "centralSiteName",
          "Setup_Place_No": null,
          "Address1": "centralStreet 2",
          "Address2": null,
          "Postal": "22297",
          "City": "centralCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SN",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SU",
          "GLN": "4300000000045",
          "Name": "@depotName@",
          "Name2": null,
          "PartnerNo": "@depotValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "depotSiteName",
          "Setup_Place_No": null,
          "Address1": "depotStreet 3",
          "Address2": null,
          "Postal": "88045",
          "City": "depotCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        }
      ],
      "PaymentTerms": [
        {
          "Net_Days": 30
        }
      ],
      "PaymentDiscounts": [
        {
          "Discount_Name": "30 Tage netto",
          "Tax_Percent": 7,
          "Discount_Days": 0,
          "Discount_Percent": 0,
          "Discount_BaseAmt": 533.5,
          "Discount_Amt": 0.0
        },
        {
          "Discount_Name": "30 Tage netto",
          "Tax_Percent": 19,
          "Discount_Days": 0,
          "Discount_Percent": 0,
          "Discount_BaseAmt": 0.0,
          "Discount_Amt": 0.0
        }
      ],
      "Lines": [
        {
          "Invoice_Line": 10,
          "Invoice_QtyInvoiced": 550,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711B",
          "Order_Line": 10,
          "Order_QtyInvoiced": 550,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": 550,
          "Tax_Percent": 7,
          "Tax_Amount": 38.5,
          "Product_Name": "@evelinaName@",
          "Product_Description": "@evelinaName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@evelinaValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_Line": 20,
          "Invoice_QtyInvoiced": 1,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711B",
          "Order_Line": 20,
          "Order_QtyInvoiced": 1,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": -16.5,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": -16.5,
          "Tax_Percent": 7,
          "Tax_Amount": -1.16,
          "Product_Name": "@bonusName@",
          "Product_Description": "@bonusName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@bonusValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_Line": 30,
          "Invoice_QtyInvoiced": 50,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711B",
          "Order_Line": null,
          "Order_QtyInvoiced": 50,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 0,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": null,
          "NetAmt": 0,
          "Tax_Percent": 19,
          "Tax_Amount": 0.0,
          "Product_Name": "@crateName@",
          "Product_Description": "@crateName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@crateValue@",
          "Product_DepositType": null,
          "Packaging": {
            "IsPackagingMaterial": true
          }
        }
      ],
      "Sums": [
        {
          "TotalAmt": 0.0,
          "Tax_Amt": 0.0,
          "Tax_BaseAmt": 0,
          "Tax_Percent": 19,
          "Tax_Exempt": false,
          "SurchargeAmt": 0.0,
          "Tax_BaseAmtWithSurchargeAmt": 0.0,
          "Tax_AmtWithSurchargeAmt": 0.0
        },
        {
          "TotalAmt": 570.85,
          "Tax_Amt": 37.35,
          "Tax_BaseAmt": 533.5,
          "Tax_Percent": 7,
          "Tax_Exempt": false,
          "SurchargeAmt": 0.0,
          "Tax_BaseAmtWithSurchargeAmt": 533.5,
          "Tax_AmtWithSurchargeAmt": 37.35
        }
      ],
      "Version": "0.2"
    }
  ]
}
    """
    And the INVOIC JSON response lists its Lines in this Invoice_Line order:
      | Invoice_Line |
      | 10           |
      | 20           |
      | 30           |

  # ################################################################################################
  # ################################################################################################
  @Id:S32545_RP_C
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00359_EDI_INVOICE_JSON
  @F00359
  Scenario: two goods lines in the same crates and one in cartons: the INVOIC JSON lists one crate line for both goods lines and a carton line
    # Real case: two goods lines share one crate line (88 + 48 = 136 crates), a third goods line is packed in cartons.
    # Test values: the crate's price 1,50 and deposit type RC, the carton's deposit type NRC, the GTINs of both; the carton is not charged (0,00).
    Given metasfresh contains M_Product_Categories:
      | Identifier  |
      | pm_category |
    And metasfresh contains M_Products:
      | Identifier    | M_Product_Category_ID | IsStocked | GTIN          | DepositType | REST.Context.Value | REST.Context.Name |
      | goodsBraeburn |                       | false     |               |             | braeburnValue      | braeburnName      |
      | goodsElstar   |                       | false     |               |             | elstarValue        | elstarName        |
      | goodsKiwi     |                       | false     |               |             | kiwiValue          | kiwiName          |
      | eps186        | pm_category           | false     | 4000000032540 | RC          | crateValue         | crateName         |
      | carton        | pm_category           | false     | 4000000032557 | NRC         | cartonValue        | cartonName        |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_sales              | goodsBraeburn | 1.40     | PCE      | tax7Category     |
      | plv_sales              | goodsElstar   | 1.50     | PCE      | tax7Category     |
      | plv_sales              | goodsKiwi     | 1.00     | PCE      | tax7Category     |
      | plv_sales              | eps186        | 1.50     | PCE      | tax19Category    |
      | plv_sales              | carton        | 0.00     | PCE      | tax19Category    |
    And metasfresh contains M_HU_PI:
      | Identifier  |
      | huPI_eps    |
      | huPI_carton |
    And metasfresh contains M_HU_PI_Version:
      | Identifier       | M_HU_PI_ID  | HU_UnitType |
      | huVersion_eps    | huPI_eps    | TU          |
      | huVersion_carton | huPI_carton | TU          |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID |
      | pm_eps                  | eps186       |
      | pm_carton               | carton       |
    And metasfresh contains M_HU_PI_Item:
      | Identifier      | M_HU_PI_Version_ID | Qty | ItemType | M_HU_PackingMaterial_ID |
      | huPIItem_eps    | huVersion_eps      | 10  | PM       | pm_eps                  |
      | huPIItem_carton | huVersion_carton   | 10  | PM       | pm_carton               |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier   | M_HU_PI_Item_ID | Qty | M_Product_ID  |
      | braeburn_eps | huPIItem_eps    | 11  | goodsBraeburn |
      | elstar_eps   | huPIItem_eps    | 10  | goodsElstar   |
      | kiwi_carton  | huPIItem_carton | 12  | goodsKiwi     |
    And metasfresh contains M_Warehouse:
      | Identifier    | C_BPartner_ID | C_BPartner_Location_ID |
      | mainWarehouse | depotOwner    | depotLoc               |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | C_BPartner_Location_ID | OPT.Bill_Location_ID.Identifier | DeliveryRule | DateOrdered | PreparationDate      | M_Warehouse_ID | POReference |
      | salesOrder | true    | store         | storeLoc               | centralLoc                      | F            | 2026-07-01  | 2026-06-30T22:00:00Z | mainWarehouse  | ED04711C    |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_braeburn | salesOrder | goodsBraeburn | 968        | braeburn_eps            |
      | ol_elstar   | salesOrder | goodsElstar   | 480        | elstar_eps              |
      | ol_kiwi     | salesOrder | goodsKiwi     | 120        | kiwi_carton             |
    When the order identified by salesOrder is completed
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier  | C_OrderLine_ID | IsToRecompute |
      | ss_braeburn | ol_braeburn    | N             |
      | ss_elstar   | ol_elstar      | N             |
      | ss_kiwi     | ol_kiwi        | N             |
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_braeburn           |
      | ss_elstar             |
      | ss_kiwi               |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID | REST.Context.DocumentNo |
      | ss_braeburn           | shipment   | shipmentDocNo           |
    # crates: 968 / 11 = 88 and 480 / 10 = 48, in one line of 136; cartons: 120 / 12 = 10
    And validate the created shipment lines
      | M_InOutLine_ID | M_InOut_ID | M_Product_ID | MovementQty |
      | crate_line     | shipment   | eps186       | 136         |
      | carton_line    | shipment   | carton       | 10          |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_braeburn    | ic_braeburn            |
      | ol_elstar      | ic_elstar              |
      | ol_kiwi        | ic_kiwi                |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | M_InOutLine_ID |
      | ic_crate               | crate_line     |
      | ic_carton              | carton_line    |
    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID | IgnoreInvoiceSchedule |
      | ic_braeburn            | Y                     |
      | ic_elstar              | Y                     |
      | ic_kiwi                | Y                     |
      | ic_crate               | Y                     |
      | ic_carton              | Y                     |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID | REST.Context | REST.Context.DocumentNo |
      | invoice      | ic_braeburn            | invoice_ID   | invoiceDocNo            |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |
    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/C_Invoice_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
  "processParameters": [
    {
      "name": "C_Invoice_ID",
      "value": "@invoice_ID@"
    }
  ]
}
    """
    # - Lines: 3 goods lines (7 %), one crate line of 136 for the first two goods lines, one carton line of 10 (19 %, Packaging marker, Order_Line null).
    # - Σ NetAmt 1355.20 + 720.00 + 120.00 + 204.00 + 0 = 2399.20 = Invoice_TotalLines; VAT 7 % of 2195.20 = 153.66, 19 % of 204.00 = 38.76; gross 2591.62.
    # - Crate: GTIN, deposit type RC, 1.50; carton: GTIN, deposit type NRC, 0.00.
    # - Crate and carton lines are matched by content: which one gets line 40 or 50 varies.
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_INVOIC": [
    {
      "Invoice_ID": @invoice_ID@,
      "Invoice_Receiver_Tec_GLN": "4300000000021",
      "Invoice_Sender_Tec_GLN": null,
      "Invoice_Sender_CountryCode": "DE",
      "Invoice_Sender_VATaxId": null,
      "Invoice_DocumentNo": "@invoiceDocNo@",
      "Invoice_Date": "2026-07-01T00:00:00",
      "Invoice_Acct_Date": "2026-07-01T00:00:00",
      "DocType_Base": "ARI",
      "DocType_Sub": null,
      "CreditMemo_Reason": null,
      "CreditMemo_ReasonText": null,
      "Order_POReference": "ED04711C",
      "Order_Date": "2026-07-01T00:00:00",
      "Shipment_Date": "2026-07-01T00:00:00",
      "Shipment_DocumentNo": "@shipmentDocNo@",
      "DESADV_DocumentNo": null,
      "Invoice_Currency_Code": "EUR",
      "Invoice_GrandTotal": 2591.62,
      "Invoice_TotalLines": 2399.2,
      "Invoice_TotalVAT": 192.42,
      "Invoice_TotalVATBaseAmt": 2399.2,
      "Invoice_SurchargeAmt": 0.0,
      "Invoice_TotalLinesWithSurchargeAmt": 2399.2,
      "Invoice_TotalVATWithSurchargeAmt": 192.42,
      "Invoice_GrandTotalWithSurchargeAmt": 2591.62,
      "Partners": [
        {
          "EANCOM_LocationType": "BY",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "DP",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "IV",
          "GLN": "4300000000021",
          "Name": "@centralName@",
          "Name2": null,
          "PartnerNo": "@centralValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "centralSiteName",
          "Setup_Place_No": null,
          "Address1": "centralStreet 2",
          "Address2": null,
          "Postal": "22297",
          "City": "centralCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SN",
          "GLN": "4300000000014",
          "Name": "@storeName@",
          "Name2": null,
          "PartnerNo": "@storeValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "storeSiteName",
          "Setup_Place_No": null,
          "Address1": "storeStreet 1",
          "Address2": null,
          "Postal": "85080",
          "City": "storeCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        },
        {
          "EANCOM_LocationType": "SU",
          "GLN": "4300000000045",
          "Name": "@depotName@",
          "Name2": null,
          "PartnerNo": "@depotValue@",
          "VATaxID": null,
          "ReferenceNo": null,
          "SiteName": "depotSiteName",
          "Setup_Place_No": null,
          "Address1": "depotStreet 3",
          "Address2": null,
          "Postal": "88045",
          "City": "depotCity",
          "CountryCode": "DE",
          "Phone": null,
          "Fax": null,
          "CustomEdiAttributes": null
        }
      ],
      "PaymentTerms": [
        {
          "Net_Days": 30
        }
      ],
      "PaymentDiscounts": [
        {
          "Discount_Name": "30 Tage netto",
          "Tax_Percent": 7,
          "Discount_Days": 0,
          "Discount_Percent": 0,
          "Discount_BaseAmt": 2195.2,
          "Discount_Amt": 0.0
        },
        {
          "Discount_Name": "30 Tage netto",
          "Tax_Percent": 19,
          "Discount_Days": 0,
          "Discount_Percent": 0,
          "Discount_BaseAmt": 204.0,
          "Discount_Amt": 0.0
        }
      ],
      "Lines": [
        {
          "Invoice_Line": 10,
          "Invoice_QtyInvoiced": 968,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711C",
          "Order_Line": 10,
          "Order_QtyInvoiced": 968,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1.4,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": 1355.2,
          "Tax_Percent": 7,
          "Tax_Amount": 94.86,
          "Product_Name": "@braeburnName@",
          "Product_Description": "@braeburnName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@braeburnValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_Line": 20,
          "Invoice_QtyInvoiced": 480,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711C",
          "Order_Line": 20,
          "Order_QtyInvoiced": 480,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1.5,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": 720.0,
          "Tax_Percent": 7,
          "Tax_Amount": 50.4,
          "Product_Name": "@elstarName@",
          "Product_Description": "@elstarName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@elstarValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_Line": 30,
          "Invoice_QtyInvoiced": 120,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711C",
          "Order_Line": 30,
          "Order_QtyInvoiced": 120,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": "Nominal",
          "NetAmt": 120,
          "Tax_Percent": 7,
          "Tax_Amount": 8.4,
          "Product_Name": "@kiwiName@",
          "Product_Description": "@kiwiName@",
          "Product_Buyer_CU_GTIN": null,
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": null,
          "Product_Supplier_ProductNo": "@kiwiValue@",
          "Product_DepositType": null,
          "Packaging": null
        },
        {
          "Invoice_QtyInvoiced": 10,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711C",
          "Order_Line": null,
          "Order_QtyInvoiced": 10,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 0,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": null,
          "NetAmt": 0,
          "Tax_Percent": 19,
          "Tax_Amount": 0.0,
          "Product_Name": "@cartonName@",
          "Product_Description": "@cartonName@",
          "Product_Buyer_CU_GTIN": "4000000032557",
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": "4000000032557",
          "Product_Supplier_ProductNo": "@cartonValue@",
          "Product_DepositType": "NRC",
          "Packaging": {
            "IsPackagingMaterial": true
          }
        },
        {
          "Invoice_QtyInvoiced": 136,
          "Invoice_QtyInvoiced_UOM": "PCE",
          "ORDERS_Line": null,
          "ORDERS_QtyInvoiced": null,
          "ORDERS_QtyInvoiced_UOM": null,
          "Order_POReference": "ED04711C",
          "Order_Line": null,
          "Order_QtyInvoiced": 136,
          "Order_QtyInvoiced_UOM": "PCE",
          "Currency_Code": "EUR",
          "PricePerUnit": 1.5,
          "PriceUOM": "PCE",
          "Discount_Amt": 0,
          "QtyBasedOn": null,
          "NetAmt": 204.0,
          "Tax_Percent": 19,
          "Tax_Amount": 38.76,
          "Product_Name": "@crateName@",
          "Product_Description": "@crateName@",
          "Product_Buyer_CU_GTIN": "4000000032540",
          "Product_Buyer_TU_GTIN": null,
          "Product_Buyer_ProductNo": null,
          "Product_Supplier_TU_GTIN": "4000000032540",
          "Product_Supplier_ProductNo": "@crateValue@",
          "Product_DepositType": "RC",
          "Packaging": {
            "IsPackagingMaterial": true
          }
        }
      ],
      "Sums": [
        {
          "TotalAmt": 242.76,
          "Tax_Amt": 38.76,
          "Tax_BaseAmt": 204.0,
          "Tax_Percent": 19,
          "Tax_Exempt": false,
          "SurchargeAmt": 0.0,
          "Tax_BaseAmtWithSurchargeAmt": 204.0,
          "Tax_AmtWithSurchargeAmt": 38.76
        },
        {
          "TotalAmt": 2348.86,
          "Tax_Amt": 153.66,
          "Tax_BaseAmt": 2195.2,
          "Tax_Percent": 7,
          "Tax_Exempt": false,
          "SurchargeAmt": 0.0,
          "Tax_BaseAmtWithSurchargeAmt": 2195.2,
          "Tax_AmtWithSurchargeAmt": 153.66
        }
      ],
      "Version": "0.2"
    }
  ]
}
    """
    And the INVOIC JSON response lists its Lines in this Invoice_Line order:
      | Invoice_Line |
      | 10           |
      | 20           |
      | 30           |
      | 40           |
      | 50           |
