@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor5
Feature: EDI DESADV export via postgREST
## F00350: EDI

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-05-15T16:30:17+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |
    And metasfresh contains C_BPartners without locations:
      | Identifier | Value               | Name               | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | desadvReceiverValue | desadvReceiverName | Y          | N        | pricingSystem      |
    And the following c_bpartner is changed
      | Identifier | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | customer1  | true                 | 1234567890            |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault | GLN           |
      | bpartner_location_1 | customer1     | Y               | Y               | 1234567890123 |


  @Id:S0468_010
  @from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
  Scenario: create a shipment and export it to JSON

    Given metasfresh contains M_Products:
      | Identifier        | Value                       | Name                       | Description                       | GTIN        |
      | product_S0468_010 | postgRESTExportProductValue | postgRESTExportProductName | postgRESTExportProductDescription | productGTIN |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID      | GTIN          |
      | bp_1_S0468_010        | customer1     | product_S0468_010 | 0575095404663 |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID      | Name                |
      | pm_1_S0468_010          | product_S0468_010 | packingMaterialTest |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID       | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0468_010 | ISO1          | LU          |
      | huPackagingCode_2_S0468_010 | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID                   |
      | huPackingLU_S0468_010        |
      | huPackingTU_S0468_010        |
      | huPackingVirtualPI_S0468_010 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID         | M_HU_PI_ID                   | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID       |
      | packingVersionLU_S0468_010 | huPackingLU_S0468_010        | LU          | Y         |                             |
      | packingVersionTU_S0468_010 | huPackingTU_S0468_010        | TU          | Y         | huPackagingCode_2_S0468_010 |
      | packingVersionCU_S0468_010 | huPackingVirtualPI_S0468_010 | V           | Y         |                             |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID         | Qty | ItemType | Included_HU_PI_ID     | M_HU_PackingMaterial_ID |
      | huPiItemLU_S0468_010       | packingVersionLU_S0468_010 | 10  | HU       | huPackingTU_S0468_010 |                         |
      | huPiItemTU_S0468_010       | packingVersionTU_S0468_010 | 0   | PM       |                       | pm_1_S0468_010          |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID    | M_HU_PI_Item_ID      | M_Product_ID      | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huAuditProductTU_S0468_010 | huPiItemTU_S0468_010 | product_S0468_010 | 10  | 2025-01-01 | huPackagingCode_1_S0468_010       | gtinPiItemProduct                |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0468_010 | 5.00     | PCE      |

    # C_Order.DatePromised has type "timestamp with timezone", The DB runs on timezone UTC. 
    # When I set it to 2025-04-18, it would end up as 2025-04-17 22:00 in the DB, because DataTableRow converts it from the current JVM's timezone.
    # Therefore I set it to 2025-04-18Z, to indicate that this already is in UTC and therefore not to be converted
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | OPT.POReference |
      | o_1        | true    | customer1     | 2025-04-17  | 2025-04-18Z  | testReference   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | product_S0468_010 | 100        | huAuditProductTU_S0468_010             |

    And the order identified by o_1 is completed
    And store order-values in TestContext
      | C_Order_ID | Column     | REST.Context   |
      | o_1        | DocumentNo | o_1_DocumentNo |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
       
    # This controls the SSCC18 value such that our LU and desc-pack get SSCC18-value 012345670010000005
    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_1                            | s_1                   | shipment_S0468_010_ID   | shipment_S0468_010_DocumentNo |

    # cleanup; otherwise, all HUs with an SSCC18 will have the same SSCC18-value for the remainder of this test-run
    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus | REST.Context |
      | d_1                      | customer1                | o_1                   | P                | d_1          |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_1        | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{  
    "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@shipment_S0468_010_ID@"
    }
  ]
}
    """
    Then the metasfresh REST-API responds with
    """
{
  "metasfresh_DESADV": {
    "Parties": {
      "Buyer": {
        "Name": "desadvReceiverName",
        "Name2": null,
        "Value": "desadvReceiverValue"
      },
      "Invoicee": {
        "Name": "desadvReceiverName",
        "Name2": null,
        "Value": "desadvReceiverValue"
      },
      "Supplier": {
        "Name": "metasfresh AG",
        "Name2": null,
        "Value": "metasfresh"
      },
      "DeliveryParty": {
        "Name": "desadvReceiverName",
        "Name2": null,
        "Value": "desadvReceiverValue"
      },
      "Buyer_Location": {
        "GLN": "1234567890123",
        "City": null,
        "Postal": null,
        "Address1": null,
        "Address2": null,
        "CountryCode": "DE"
      },
      "Invoicee_Location": {
        "GLN": "1234567890123",
        "City": null,
        "Postal": null,
        "Address1": null,
        "Address2": null,
        "CountryCode": "DE"
      },
      "Supplier_Location": {
        "GLN": null,
        "City": "Bonn",
        "Postal": "53179",
        "Address1": "Am Nossbacher Weg 2",
        "Address2": null,
        "CountryCode": "DE"
      },
      "UltimateConsignee": {
        "Name": "desadvReceiverName",
        "Name2": null,
        "Value": "desadvReceiverValue"
      },
      "DeliveryParty_Location": {
        "GLN": "1234567890123",
        "City": null,
        "Postal": null,
        "Address1": null,
        "Address2": null,
        "CountryCode": "DE"
      },
      "UltimateConsignee_Location": {
        "GLN": "1234567890123",
        "City": null,
        "Postal": null,
        "Address1": null,
        "Address2": null,
        "CountryCode": "DE"
      }
    },
    "Version": "0.2",
    "Currency": {
      "ISO_Code": "EUR",
      "CurSymbol": "€"
    },
    "Packings": [
      {
        "SeqNo": 1,
        "LineItems": [
          {
            "Line": 10,
            "QtyTU": 10,
            "LotNumber": null,
            "DesadvLine": {
              "Product": {
                "Name": "postgRESTExportProductName",
                "GTIN_CU": "0575095404663",
                "GTIN_TU": null,
                "NetWeight": 0,
                "Description": "postgRESTExportProductDescription",
                "GrossWeight": null,
                "BuyerProductNo": null,
                "GrossWeightUOM": {},
                "SupplierProductNo": "postgRESTExportProductValue"
              },
              "OrderLine": 10,
              "DesadvLine": 10,
              "InvoicingUOM": {
                "Name": "Stück",
                "X12DE355": "PCE"
              },
              "ShipmentLine": 10,
              "DesadvLineUOM": {
                "Name": "Stück",
                "X12DE355": "PCE"
              },
              "OrderDocumentNo": "@o_1_DocumentNo@",
              "OrderPOReference": "testReference",
              "QtyOrderedInDesadvLineUOM": 100,
              "QtyDeliveredInInvoicingUOM": 100,
              "QtyDeliveredInDesadvLineUOM": 100,
              "IsDeliveryClosed": true
            },
            "QtyCUsPerLU": 100,
            "QtyCUsPerTU": 10,
            "BestBeforeDate": null,
            "GTIN_TU_PackingMaterial": "0575095404663",
            "QtyCUsPerLU_InInvoiceUOM": 100,
            "QtyCUsPerTU_InInvoiceUOM": 10,
            "M_HU_PackagingCode_TU_Text": "CART",
            "IsSubArticle": false,
            "MainArticleLine": null
          }
        ],
        "IPA_SSCC18": "012345670010000005",
        "GTIN_PackingMaterial": "gtinPiItemProduct",
        "M_HU_PackagingCode_Text": "ISO1"
      }
    ],
    "M_InOut_ID": @shipment_S0468_010_ID@,
    "DateOrdered": "2025-04-17T00:00:00",
    "POReference": "testReference",
    "DatePromised": "2025-04-18T00:00:00+00:00",
    "MovementDate": "2025-05-15T00:00:00",
    "EDI_Desadv_ID": @d_1@,
    "DeliveryViaRule": "P",
    "ShipmentDocumentNo": "@shipment_S0468_010_DocumentNo@",
    "InvoicableQtyBasedOn": "Nominal",
    "TechnicalRecipientGLN": "1234567890",
    "DesadvLineWithNoPacking": []
  }
}
    """

#   no need to wait. the process runs synchronously
    And after not more than 1s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | s_1        | S                |

  @Id:S0468_020
  @from:cucumber
  Scenario: DESADV export merges compensation group sub-articles into main article pack

    # Three products: main article (Mischkarton) and two sub-articles
    Given metasfresh contains M_Products:
      | Identifier    | Value                     | Name                     |
      | mainProduct   | compGroupMainProductValue | Mischkarton 4000g        |
      | subProduct1   | compGroupSubProduct1Value | Tofu geräuchert 200g     |
      | subProduct2   | compGroupSubProduct2Value | Tofu Natur 300g          |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID | OPT.GTIN         |
      | bp_main               | customer1     | mainProduct  | mainProductGTIN   |
      | bp_sub1               | customer1     | subProduct1  | subProduct1GTIN   |
      | bp_sub2               | customer1     | subProduct2  | subProduct2GTIN   |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID | Name            |
      | pm_main                 | mainProduct  | pmMainProduct   |
      | pm_sub1                 | subProduct1  | pmSubProduct1   |
      | pm_sub2                 | subProduct2  | pmSubProduct2   |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID | PackagingCode | HU_UnitType |
      | huPkgCodeLU           | ISO1          | LU          |
      | huPkgCodeTU           | CART          | TU          |

    # HU PI setup for main product
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | huPILU_main       |
      | huPITU_main       |
      | huPIVirtual_main  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID       | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID |
      | huPIVerLU_main     | huPILU_main      | LU          | Y         |                       |
      | huPIVerTU_main     | huPITU_main      | TU          | Y         | huPkgCodeTU           |
      | huPIVerCU_main     | huPIVirtual_main | V           | Y         |                       |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID | M_HU_PackingMaterial_ID |
      | huPiItemLU_main            | huPIVerLU_main     | 10  | HU       | huPITU_main       |                         |
      | huPiItemTU_main            | huPIVerTU_main     | 0   | PM       |                    | pm_main                 |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huPiProd_main           | huPiItemTU_main  | mainProduct  | 10  | 2025-01-01 | huPkgCodeLU                       | mainLuGTIN                       |

    # HU PI setup for sub-product 1
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | huPILU_sub1       |
      | huPITU_sub1       |
      | huPIVirtual_sub1  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID |
      | huPIVerLU_sub1     | huPILU_sub1       | LU          | Y         |                       |
      | huPIVerTU_sub1     | huPITU_sub1       | TU          | Y         | huPkgCodeTU           |
      | huPIVerCU_sub1     | huPIVirtual_sub1  | V           | Y         |                       |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID | M_HU_PackingMaterial_ID |
      | huPiItemLU_sub1            | huPIVerLU_sub1     | 10  | HU       | huPITU_sub1       |                         |
      | huPiItemTU_sub1            | huPIVerTU_sub1     | 0   | PM       |                    | pm_sub1                 |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huPiProd_sub1           | huPiItemTU_sub1  | subProduct1  | 10  | 2025-01-01 | huPkgCodeLU                       | sub1LuGTIN                       |

    # HU PI setup for sub-product 2
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | huPILU_sub2       |
      | huPITU_sub2       |
      | huPIVirtual_sub2  |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID        | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID |
      | huPIVerLU_sub2     | huPILU_sub2       | LU          | Y         |                       |
      | huPIVerTU_sub2     | huPITU_sub2       | TU          | Y         | huPkgCodeTU           |
      | huPIVerCU_sub2     | huPIVirtual_sub2  | V           | Y         |                       |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID | M_HU_PackingMaterial_ID |
      | huPiItemLU_sub2            | huPIVerLU_sub2     | 10  | HU       | huPITU_sub2       |                         |
      | huPiItemTU_sub2            | huPIVerTU_sub2     | 0   | PM       |                    | pm_sub2                 |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID  | M_Product_ID | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huPiProd_sub2           | huPiItemTU_sub2  | subProduct2  | 10  | 2025-01-01 | huPkgCodeLU                       | sub2LuGTIN                       |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | mainProduct  | 10.00    | PCE      |
      | salesPLV               | subProduct1  | 3.00     | PCE      |
      | salesPLV               | subProduct2  | 4.00     | PCE      |

    # Create order with 3 lines in one compensation group
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | OPT.POReference    |
      | o_cg       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | compGroupReference |
    And metasfresh contains C_Order_CompensationGroups:
      | Identifier | C_Order_ID | Name        |
      | cg_1       | o_cg       | Mischkarton |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered | M_HU_PI_Item_Product_ID | C_Order_CompensationGroup_ID |
      | ol_main    | o_cg       | mainProduct  | 10         | huPiProd_main           | cg_1                         |
      | ol_sub1    | o_cg       | subProduct1  | 10         | huPiProd_sub1           | cg_1                         |
      | ol_sub2    | o_cg       | subProduct2  | 10         | huPiProd_sub2           | cg_1                         |

    And the order identified by o_cg is completed
    And store order-values in TestContext
      | C_Order_ID | Column     | REST.Context     |
      | o_cg       | DocumentNo | o_cg_DocumentNo  |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_main    | ol_main        | N             |
      | ss_sub1    | ol_sub1        | N             |
      | ss_sub2    | ol_sub2        | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID.Identifier |
      | ss_main                          |
      | ss_sub1                          |
      | ss_sub2                          |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo     |
      | ss_main                          | s_cg                  | shipment_cg_ID          | shipment_cg_DocumentNo      |

    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus | REST.Context |
      | d_cg                     | customer1                | o_cg                  | P                | d_cg         |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_cg       | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
    "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@shipment_cg_ID@"
    }
  ]
}
    """
    # The compensation group merging should produce 1 pack (from originally 3):
    # the main article's pack absorbs the sub-article packs.
    # Result: 1 main article LineItem (IsSubArticle=false) + 2 sub-article LineItems (IsSubArticle=true with MainArticleLine)
    Then verify DESADV JSON export has compensation group packing:
      | PackingCount | MainArticleCount | SubArticleCount | IsDeliveryClosed |
      | 1            | 1                | 2               | true             |

    And after not more than 1s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | s_cg       | S                |

  @Id:S0468_030
  @from:cucumber
  Scenario: DESADV export with multiple shipments only includes packs from the requested shipment

    # One simple product — no compensation groups needed (that's tested in S0468_020)
    Given metasfresh contains M_Products:
      | Identifier     |
      | prod_multiShip |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID   |
      | bp_ms                 | customer1     | prod_multiShip |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID   | Name       |
      | pm_ms                   | prod_multiShip | pmMultiShip |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID | PackagingCode | HU_UnitType |
      | huPkgCodeLU_ms        | ISO1          | LU          |
      | huPkgCodeTU_ms        | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID     |
      | huPILU_ms      |
      | huPITU_ms      |
      | huPIVirtual_ms |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID     | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID |
      | huPIVerLU_ms       | huPILU_ms      | LU          | Y         |                       |
      | huPIVerTU_ms       | huPITU_ms      | TU          | Y         | huPkgCodeTU_ms        |
      | huPIVerCU_ms       | huPIVirtual_ms | V           | Y         |                       |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID | M_HU_PackingMaterial_ID |
      | huPiItemLU_ms              | huPIVerLU_ms       | 10  | HU       | huPITU_ms         |                         |
      | huPiItemTU_ms              | huPIVerTU_ms       | 0   | PM       |                    | pm_ms                   |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID   | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huPiProd_ms             | huPiItemTU_ms    | prod_multiShip | 10  | 2025-01-01 | huPkgCodeLU_ms                    | msLuGTIN                         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID |
      | salesPLV               | prod_multiShip | 5.00     | PCE      |

    # Order 1 — POReference "multiShipRef"
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference  |
      | o_ms_1     | true    | customer1     | 2025-04-17  | 2025-04-18Z  | multiShipRef |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID   | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_ms_1    | o_ms_1     | prod_multiShip | 10         | huPiProd_ms             |
    And the order identified by o_ms_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ms_1    | ol_ms_1        | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_ms_1                          | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo |
      | ss_ms_1                          | s_ms_1                | shipment_ms1_ID         | shipment_ms1_DocNo      |

    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    # Order 2 — same BPartner, same POReference → reuses the same DESADV
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference  |
      | o_ms_2     | true    | customer1     | 2025-04-17  | 2025-04-18Z  | multiShipRef |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID   | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_ms_2    | o_ms_2     | prod_multiShip | 10         | huPiProd_ms             |
    And the order identified by o_ms_2 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ms_2    | ol_ms_2        | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=2000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_ms_2                          | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo |
      | ss_ms_2                          | s_ms_2                | shipment_ms2_ID         | shipment_ms2_DocNo      |

    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    # Verify both shipments share the same DESADV (test precondition)
    Then M_InOut records share the same EDI_Desadv:
      | M_InOut_ID |
      | s_ms_1     |
      | s_ms_2     |

    # Export shipment 1 and verify it has exactly 1 pack (not 2)
    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_ms_1     | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
    "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@shipment_ms1_ID@"
    }
  ]
}
    """
    Then verify DESADV JSON export has compensation group packing:
      | PackingCount | MainArticleCount | SubArticleCount | IsDeliveryClosed |
      | 1            | 1                | 0               | true             |

    # Export shipment 2 and verify it also has exactly 1 pack (not 2)
    And add HTTP headers
      | Key          | Value                          |
      | Content-Type | application/json;charset=UTF-8 |
      | accept       | application/json;charset=UTF-8 |

    When a 'POST' request with the below payload and headers from context is sent to the metasfresh REST-API 'api/v2/processes/M_InOut_EDI_Export_JSON/invoke' and fulfills with '200' status code
    """
{
    "processParameters": [
    {
      "name": "M_InOut_ID",
      "value": "@shipment_ms2_ID@"
    }
  ]
}
    """
    Then verify DESADV JSON export has compensation group packing:
      | PackingCount | MainArticleCount | SubArticleCount | IsDeliveryClosed |
      | 1            | 1                | 0               | true             |
