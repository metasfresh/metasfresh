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

    And metasfresh contains M_Product_ASI_Data:
      | Identifier     | M_Product_ID      | C_BPartner_ID | SeqNo | GTIN          |
      | asiData_010    | product_S0468_010 | customer1     | 10    | 0575095404663 |
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

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 180s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
       
    # This controls the SSCC18 value such that our LU and desc-pack get SSCC18-value 012345670010000005
    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 180s, M_InOut is found:
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
              "LineItemLine": 10,
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

    And metasfresh contains M_Product_ASI_Data:
      | Identifier    | M_Product_ID | C_BPartner_ID | SeqNo | GTIN              |
      | asiData_main  | mainProduct  | customer1     | 10    | mainProductGTIN   |
      | asiData_sub1  | subProduct1  | customer1     | 10    | subProduct1GTIN   |
      | asiData_sub2  | subProduct2  | customer1     | 10    | subProduct2GTIN   |
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

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 180s, M_ShipmentSchedules are found:
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

    And after not more than 180s, M_InOut is found:
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

    And metasfresh contains M_Product_ASI_Data:
      | Identifier   | M_Product_ID   | C_BPartner_ID | SeqNo |
      | asiData_ms   | prod_multiShip | customer1     | 10    |
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

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 180s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ms_1    | ol_ms_1        | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_ms_1                          | D            | true                | false       |

    And after not more than 180s, M_InOut is found:
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

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 180s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID | IsToRecompute |
      | ss_ms_2    | ol_ms_2        | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=2000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_ms_2                          | D            | true                | false       |

    And after not more than 180s, M_InOut is found:
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

  @Id:S0468_040
  @from:cucumber
  Scenario: DESADV export includes unshipped order lines in DesadvLineWithNoPacking

    # Two products: one will be shipped, one will NOT be shipped at all
    Given metasfresh contains M_Products:
      | Identifier      |
      | prod_shipped    |
      | prod_notShipped |

    And metasfresh contains M_Product_ASI_Data:
      | Identifier        | M_Product_ID    | C_BPartner_ID | SeqNo |
      | asiData_shipped   | prod_shipped    | customer1     | 10    |
      | asiData_notShip   | prod_notShipped | customer1     | 10    |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID | Name        |
      | pm_shipped              | prod_shipped | pmShipped   |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID | PackagingCode | HU_UnitType |
      | huPkgCodeLU_ns        | ISO1          | LU          |
      | huPkgCodeTU_ns        | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID     |
      | huPILU_ns      |
      | huPITU_ns      |
      | huPIVirtual_ns |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID | M_HU_PI_ID     | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID |
      | huPIVerLU_ns       | huPILU_ns      | LU          | Y         |                       |
      | huPIVerTU_ns       | huPITU_ns      | TU          | Y         | huPkgCodeTU_ns        |
      | huPIVerCU_ns       | huPIVirtual_ns | V           | Y         |                       |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID | Qty | ItemType | Included_HU_PI_ID | M_HU_PackingMaterial_ID |
      | huPiItemLU_ns              | huPIVerLU_ns       | 10  | HU       | huPITU_ns         |                         |
      | huPiItemTU_ns              | huPIVerTU_ns       | 0   | PM       |                    | pm_shipped              |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huPiProd_ns             | huPiItemTU_ns    | prod_shipped | 10  | 2025-01-01 | huPkgCodeLU_ns                    | nsLuGTIN                         |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID    | PriceStd | C_UOM_ID |
      | salesPLV               | prod_shipped    | 5.00     | PCE      |
      | salesPLV               | prod_notShipped | 3.00     | PCE      |

    # Order with 2 lines
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | OPT.POReference   |
      | o_ns       | true    | customer1     | 2025-04-17  | 2025-04-18Z  | partialShipRef    |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID | M_Product_ID    | QtyEntered | OPT.M_HU_PI_Item_Product_ID |
      | ol_shipped    | o_ns       | prod_shipped    | 100        | huPiProd_ns                 |
      | ol_notShipped | o_ns       | prod_notShipped | 50         |                             |
    And the order identified by o_ns is completed

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 180s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ss_shipped    | ol_shipped     | N             |
      | ss_notShipped | ol_notShipped  | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    # Only ship the first line — the second line is NOT shipped at all
    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | ss_shipped                       | D            | true                | false       |

    And after not more than 180s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo     |
      | ss_shipped                       | s_ns                  | shipment_ns_ID          | shipment_ns_DocumentNo      |

    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus | REST.Context |
      | d_ns                     | customer1                | o_ns                  | P                | d_ns         |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_ns       | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
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
      "value": "@shipment_ns_ID@"
    }
  ]
}
    """
    # The shipped line should be in Packings, the unshipped line should be in DesadvLineWithNoPacking
    Then verify DESADV JSON export has compensation group packing:
      | PackingCount | MainArticleCount | SubArticleCount | IsDeliveryClosed |
      | 1            | 1                | 0               | true             |

    And verify DESADV JSON export has DesadvLineWithNoPacking:
      | OrderLine | QtyOrderedInDesadvLineUOM | QtyDeliveredInDesadvLineUOM | IsDeliveryClosed | QtyCUsPerTU |
      | 20        | 50                        | 0                           | false            | 0           |

  @Id:S0468_050
  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  Scenario: DESADV export uses M_Product_ASI_Data.EAN13_ProductCode as GTIN_CU fallback when GTIN is empty

    # Product has NO GTIN (column intentionally omitted so p.gtin = NULL)
    # ASI data has EAN13_ProductCode but NO GTIN (so asi_data.gtin = NULL)
    # Migration 5802510 widened M_Product_ASI_Data.EAN13_ProductCode from VARCHAR(4)
    # to VARCHAR(50), so it can now hold a real 13-digit EAN. The COALESCE resolution:
    # COALESCE(dl.gtin_cu=NULL, asi_data.gtin=NULL, asi_data.ean13_productcode='4012345001234', p.gtin=NULL) → '4012345001234'
    Given metasfresh contains M_Products:
      | Identifier         | Value                          | Name                          | Description                          |
      | product_S0468_050  | ean13FallbackProductValue      | ean13FallbackProductName      | ean13FallbackProductDescription      |

    And metasfresh contains M_Product_ASI_Data:
      | Identifier      | M_Product_ID      | C_BPartner_ID | SeqNo | EAN13_ProductCode |
      | asiData_050     | product_S0468_050 | customer1     | 10    | 4012345001234     |
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID | M_Product_ID      | Name                     |
      | pm_1_S0468_050          | product_S0468_050 | packingMaterialEan13Test |
    And load M_HU_PackagingCode:
      | M_HU_PackagingCode_ID       | PackagingCode | HU_UnitType |
      | huPackagingCode_1_S0468_050 | ISO1          | LU          |
      | huPackagingCode_2_S0468_050 | CART          | TU          |
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID                   |
      | huPackingLU_S0468_050        |
      | huPackingTU_S0468_050        |
      | huPackingVirtualPI_S0468_050 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID         | M_HU_PI_ID                   | HU_UnitType | IsCurrent | M_HU_PackagingCode_ID       |
      | packingVersionLU_S0468_050 | huPackingLU_S0468_050        | LU          | Y         |                             |
      | packingVersionTU_S0468_050 | huPackingTU_S0468_050        | TU          | Y         | huPackagingCode_2_S0468_050 |
      | packingVersionCU_S0468_050 | huPackingVirtualPI_S0468_050 | V           | Y         |                             |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID         | Qty | ItemType | Included_HU_PI_ID     | M_HU_PackingMaterial_ID |
      | huPiItemLU_S0468_050       | packingVersionLU_S0468_050 | 10  | HU       | huPackingTU_S0468_050 |                         |
      | huPiItemTU_S0468_050       | packingVersionTU_S0468_050 | 0   | PM       |                       | pm_1_S0468_050          |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID    | M_HU_PI_Item_ID      | M_Product_ID      | Qty | ValidFrom  | M_HU_PackagingCode_LU_Fallback_ID | GTIN_LU_PackingMaterial_Fallback |
      | huAuditProductTU_S0468_050 | huPiItemTU_S0468_050 | product_S0468_050 | 10  | 2025-01-01 | huPackagingCode_1_S0468_050       | gtinPiItemProductEan13           |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0468_050 | 5.00     | PCE      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | OPT.POReference    |
      | o_050      | true    | customer1     | 2025-04-17  | 2025-04-18Z  | ean13FallbackRef   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID      | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_050     | o_050      | product_S0468_050 | 100        | huAuditProductTU_S0468_050             |

    And the order identified by o_050 is completed
    And store order-values in TestContext
      | C_Order_ID | Column     | REST.Context      |
      | o_050      | DocumentNo | o_050_DocumentNo  |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And after not more than 180s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_050    | ol_050                    | N             |

    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_050                          | D            | true                | false       |

    And after not more than 180s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context.M_InOut_ID | REST.Context.DocumentNo       |
      | s_s_050                          | s_050                 | shipment_S0468_050_ID   | shipment_S0468_050_DocumentNo |

    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus | REST.Context |
      | d_050                    | customer1                | o_050                 | P                | d_050        |

    And the following API_Audit_Config records are created:
      | Identifier | SeqNo | OPT.Method | OPT.PathPrefix   | IsForceProcessedAsync | IsSynchronousAuditLoggingEnabled | IsWrapApiResponse |
      | c_050      | 10    | GET        | api/v2/processes | N                     | Y                                | N                 |
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
      "value": "@shipment_S0468_050_ID@"
    }
  ]
}
    """
    # Assert that GTIN_CU is resolved from EAN13_ProductCode (the COALESCE fallback) because:
    #   - EDI_DesadvLine.GTIN_CU is NULL (no GTIN set on the desadv line)
    #   - M_Product_ASI_Data.GTIN is NULL (not provided in fixture)
    #   - M_Product_ASI_Data.EAN13_ProductCode = '4012345001234'  ← this wins
    #   - M_Product.GTIN is NULL (not provided in fixture)
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
                "Name": "ean13FallbackProductName",
                "GTIN_CU": "4012345001234",
                "GTIN_TU": null,
                "NetWeight": 0,
                "Description": "ean13FallbackProductDescription",
                "GrossWeight": null,
                "BuyerProductNo": null,
                "GrossWeightUOM": {},
                "SupplierProductNo": "ean13FallbackProductValue"
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
              "OrderDocumentNo": "@o_050_DocumentNo@",
              "OrderPOReference": "ean13FallbackRef",
              "QtyOrderedInDesadvLineUOM": 100,
              "QtyDeliveredInInvoicingUOM": 100,
              "QtyDeliveredInDesadvLineUOM": 100,
              "IsDeliveryClosed": true
            },
            "QtyCUsPerLU": 100,
            "QtyCUsPerTU": 10,
            "BestBeforeDate": null,
            "GTIN_TU_PackingMaterial": null,
            "QtyCUsPerLU_InInvoiceUOM": 100,
            "QtyCUsPerTU_InInvoiceUOM": 10,
            "M_HU_PackagingCode_TU_Text": "CART",
            "IsSubArticle": false,
            "MainArticleLine": null
          }
        ],
        "IPA_SSCC18": "012345670010000005",
        "GTIN_PackingMaterial": "gtinPiItemProductEan13",
        "M_HU_PackagingCode_Text": "ISO1"
      }
    ],
    "M_InOut_ID": @shipment_S0468_050_ID@,
    "DateOrdered": "2025-04-17T00:00:00",
    "POReference": "ean13FallbackRef",
    "DatePromised": "2025-04-18T00:00:00+00:00",
    "MovementDate": "2025-05-15T00:00:00",
    "EDI_Desadv_ID": @d_050@,
    "DeliveryViaRule": "P",
    "ShipmentDocumentNo": "@shipment_S0468_050_DocumentNo@",
    "InvoicableQtyBasedOn": "Nominal",
    "TechnicalRecipientGLN": "1234567890",
    "DesadvLineWithNoPacking": []
  }
}
    """

    And after not more than 1s, M_InOut records have the following export status
      | M_InOut_ID | EDI_ExportStatus |
      | s_050      | S                |

  @Id:S29231_100
  @from:cucumber
  Scenario: S29231_100 — Two orders, one consolidated shipment → export view emits two DESADV JSONs
  ## me03#29231 — TC1: regression test for the multi-source-order DESADV export fix.
  ## Two EDI-configured orders for the same BPartner, each with a distinct POReference,
  ## are completed (creating one EDI_Desadv per order). Their shipment schedules are
  ## consolidated into a single M_InOut. After the Option-A junction fix the export view
  ## M_InOut_Export_EDI_DESADV_JSON_V must return EXACTLY 2 rows for that M_InOut —
  ## one per source-order DESADV — each carrying its own POReference and EDI_Desadv_ID.

    Given set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier      |
      | p_S29231_100    |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_S29231_100   |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_100   | ps_S29231_100      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID  |
      | plv_S29231_100  | pl_S29231_100   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID   | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_100         | p_S29231_100   | 10.0     | PCE      | Normal           |

    # BPartner: EDI DESADV recipient — the gate condition for DESADV creation at order-complete
    And metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_100   | Y          | ps_S29231_100      | 9900000290010 |
    And the following c_bpartner is changed
      | C_BPartner_ID  | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S29231_100  | true                 | 9900000290010         |

    # HU packing: LU holds up to 20 TUs, each TU holds 10 PCE
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID        |
      | pi_LU_S29231_100  |
      | pi_TU_S29231_100  |
      | pi_VHU_S29231_100 |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID        | HU_UnitType | IsCurrent |
      | piv_LU_S29231_100   | pi_LU_S29231_100  | LU          | Y         |
      | piv_TU_S29231_100   | pi_TU_S29231_100  | TU          | Y         |
      | piv_VHU_S29231_100  | pi_VHU_S29231_100 | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID     | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID  |
      | pii_LU_S29231_100   | piv_LU_S29231_100   | 20  | HU       | pi_TU_S29231_100   |
      | pii_TU_S29231_100   | piv_TU_S29231_100   | 0   | PM       |                    |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S29231_100          | pii_TU_S29231_100 | p_S29231_100  | 10  | 2020-01-01 |

    # Order A — distinct POReference so it gets its own EDI_Desadv at order-complete
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference                 |
      | oA_S29231    | true    | bp_S29231_100  | 2026-05-20  | PO_A_S29231_100_@Date@      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | olA_S29231    | oA_S29231   | p_S29231_100  | 10         | pip_S29231_100          |

    When the order identified by oA_S29231 is completed

    # Order A must now have an EDI_Desadv_ID set (precondition for the export fix to matter)
    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dA_S29231                | bp_S29231_100            | oA_S29231             | P                |

    # Order B — different POReference → its own distinct EDI_Desadv
    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference                 |
      | oB_S29231    | true    | bp_S29231_100  | 2026-05-20  | PO_B_S29231_100_@Date@      |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID  | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | olB_S29231    | oB_S29231   | p_S29231_100  | 10         | pip_S29231_100          |

    When the order identified by oB_S29231 is completed

    # Order B must also have its own EDI_Desadv_ID (distinct from A's)
    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | dB_S29231                | bp_S29231_100            | oB_S29231             | P                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    # Both shipment schedules must be ready before batching
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ssA_S29231    | olA_S29231     | N             |
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | ssB_S29231    | olB_S29231     | N             |

    # Batch-generate ONE shipment covering both schedules (the aggregated M_InOut)
    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ssA_S29231            |
      | ssB_S29231            |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID      |
      | ssA_S29231            | io_S29231_100   |

    # Wait for DESADV pack records to be written (async, triggered by M_InOut BEFORE_COMPLETE)
    # IsManual_IPA_SSCC18=true: this scenario does not set up M_HU_Attribute SSCC18 (no
    # real picked-LU); EDIDesadvPackService.createPackUsingJustInOutLine synthesises the SSCC
    # and sets IsManual_IPA_SSCC18=true. Pack-side SSCC sourced-from-LU is exercised
    # separately in ediDesadvAggregateHU.feature.
    # Consolidated multi-source-order shipment: production creates ONE pack per source DESADV.
    # Disambiguate by EDI_Desadv_ID so each row matches exactly one pack.
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | EDI_Desadv_ID.Identifier | IsManual_IPA_SSCC18 |
      | packA_S29231       | dA_S29231                | true                |
      | packB_S29231       | dB_S29231                | true                |

    # ─── CORE ASSERTION (TC1 falsifiable predicate) ───────────────────────────
    # After the Option-A junction fix the view must return exactly 2 rows for the
    # consolidated shipment — one per source-order DESADV.
    # Before the fix: only 1 row (M_InOut.EDI_Desadv_ID single-FK → only Order-A's DESADV).
    # Strict intersection asserted at both header (POReference / EDI_Desadv_ID pairing) AND
    # line level (every LineItem.DesadvLine.OrderPOReference belongs to the row's source order,
    # never the other) — per PR #24042 review #4335557991.
    Then verify DESADV JSON export view for M_InOut identified by io_S29231_100 has:
      | ExpectedRowCount | DistinctDesadvIds | OrderA_Identifier | OrderB_Identifier | ExpectedQtyDeliveredPerOrder |
      | 2                | 2                 | oA_S29231         | oB_S29231         | 10                           |


  @Id:S29231_110
  @from:cucumber
  Scenario: S29231_110 — One order, one shipment → export view emits exactly one DESADV JSON (1-order regression)
  ## me03#29231 — TC2: Single-order baseline regression. A single EDI-configured order is
  ## completed and its shipment schedule generates one M_InOut. After the Option-A junction
  ## fix the export view M_InOut_Export_EDI_DESADV_JSON_V must still return exactly 1 row
  ## for that M_InOut — proving the fix does not break the pre-existing 1-source-order case.

    Given set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService
    And metasfresh is configured for One-DESADV-Per-ORDERS
    And metasfresh initially has no EDI_Desadv_Pack_Item data
    And metasfresh initially has no EDI_Desadv_Pack data
    And destroy existing M_HUs
    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier      |
      | p_S29231_110    |
    And metasfresh contains M_PricingSystems
      | Identifier      |
      | ps_S29231_110   |
    And metasfresh contains M_PriceLists
      | Identifier      | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_S29231_110   | ps_S29231_110      | DE           | EUR           | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID  |
      | plv_S29231_110  | pl_S29231_110   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID  | PriceStd | C_UOM_ID | C_TaxCategory_ID |
      | plv_S29231_110         | p_S29231_110  | 10.0     | PCE      | Normal           |

    And metasfresh contains C_BPartners:
      | Identifier      | IsCustomer | M_PricingSystem_ID | GLN           |
      | bp_S29231_110   | Y          | ps_S29231_110      | 9900000291100 |
    And the following c_bpartner is changed
      | C_BPartner_ID  | IsEdiDesadvRecipient | EdiDesadvRecipientGLN |
      | bp_S29231_110  | true                 | 9900000291100         |

    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID          |
      | pi_LU_S29231_110    |
      | pi_TU_S29231_110    |
      | pi_VHU_S29231_110   |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID  | M_HU_PI_ID          | HU_UnitType | IsCurrent |
      | piv_LU_S29231_110   | pi_LU_S29231_110    | LU          | Y         |
      | piv_TU_S29231_110   | pi_TU_S29231_110    | TU          | Y         |
      | piv_VHU_S29231_110  | pi_VHU_S29231_110   | V           | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID     | M_HU_PI_Version_ID  | Qty | ItemType | Included_HU_PI_ID   |
      | pii_LU_S29231_110   | piv_LU_S29231_110   | 20  | HU       | pi_TU_S29231_110    |
      | pii_TU_S29231_110   | piv_TU_S29231_110   | 0   | PM       |                     |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID | M_HU_PI_Item_ID   | M_Product_ID  | Qty | ValidFrom  |
      | pip_S29231_110          | pii_TU_S29231_110 | p_S29231_110  | 10  | 2020-01-01 |

    And metasfresh contains C_Orders:
      | Identifier   | IsSOTrx | C_BPartner_ID  | DateOrdered | POReference              |
      | o_S29231_110 | true    | bp_S29231_110  | 2026-05-20  | PO_S29231_110_@Date@     |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID   | M_Product_ID  | QtyEntered | M_HU_PI_Item_Product_ID |
      | ol_S29231_110 | o_S29231_110 | p_S29231_110  | 10         | pip_S29231_110          |

    When the order identified by o_S29231_110 is completed

    Then EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_S29231_110             | bp_S29231_110            | o_S29231_110          | P                |

    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier     | C_OrderLine_ID | IsToRecompute |
      | ss_S29231_110  | ol_S29231_110  | N             |

    When 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID |
      | ss_S29231_110         |

    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    |
      | ss_S29231_110         | io_S29231_110 |

    # IsManual_IPA_SSCC18=true: no M_HU_Attribute SSCC18 in this scenario — see TC1 note.
    And after not more than 60s, EDI_Desadv_Pack records are found:
      | EDI_Desadv_Pack_ID | IsManual_IPA_SSCC18 |
      | pack_S29231_110    | true                |

    # ─── CORE ASSERTION (TC2 regression predicate) ────────────────────────────
    # View must still return exactly 1 row for the single-order case.
    # Line-level intersection asserted: the single emitted row's LineItems must reference
    # the one source order's POReference and carry its delivered qty.
    Then verify DESADV JSON export view for M_InOut identified by io_S29231_110 has exactly 1 row matching:
      | Order_Identifier | ExpectedQtyDelivered |
      | o_S29231_110     | 10                   |


  # me03#29231 TC3 (S29231_120: N=3 generalisation) — removed per PR #24042 review #4335557991
  # comment on the original ediDesadvAggregateOrders.feature file:371. The N=3 case exercises
  # the same production code path as TC1 (DesadvBL.addToDesadvCreateForInOutIfNotExist's
  # per-line loop + sequencesByDesadv map); one extra iteration adds no new branch coverage.
  # TC1 (S29231_100) is the canonical multi-source-order test; TC2 (S29231_110) is the
  # 1-order regression baseline.
