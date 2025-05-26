@from:cucumber
@ghActions:run_on_executor5
Feature: EDI DESADV export via postgREST

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And all the API audit data is reset
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


  @ignore # note yet done making sure that the API's output is always the same (e.g. M_Inout.DocumentNo)
  @Id:S0468_010
  @from:cucumber
  Scenario: create a shipment and export it to JSON

    Given metasfresh contains M_Products:
      | Identifier        | Value                       | Name                       | Description                       | GTIN        |
      | product_S0468_010 | postgRESTExportProductValue | postgRESTExportProductName | postgRESTExportProductDescription | productGTIN |

    And metasfresh contains C_BPartner_Product
      | C_BPartner_Product_ID | C_BPartner_ID | M_Product_ID      | OPT.GTIN            |
      | bp_1_S0468_010        | customer1     | product_S0468_010 | bPartnerProductGTIN |
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

    And metasfresh contains M_AttributeSetInstance with identifier "orderLineASI_S0468_010":
  """
  {
    "attributeInstances":[
      {
        "attributeCode":"Lot-Nummer",
          "valueStr":"lotNumber"
      },
      {
        "attributeCode":"HU_BestBeforeDate",
          "valueStr":"2025-05-25"
      }
    ]
  }
  """

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID      | PriceStd | C_UOM_ID |
      | salesPLV               | product_S0468_010 | 5.00     | PCE      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | OPT.POReference |
      | o_1        | true    | customer1     | 2025-04-17  | testReference   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID      | QtyEntered | OPT.M_HU_PI_Item_Product_ID.Identifier |
      | ol_1       | o_1                   | product_S0468_010 | 100        | huAuditProductTU_S0468_010             |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |
       
         # This controls the SSCC18 value such that our LU and desc-pack get SSCC18-value 012345670010000005
    And setup the SSCC18 code generator with GS1ManufacturerCode 1234567, GS1ExtensionDigit 0 and next sequence number always=1000000.

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier | REST.Context          |
      | s_s_1                            | s_1                   | shipment_id_S0468_010 |

        # cleanup; otherwise, all HUs with an SSCC18 will have the same SSCC18-value for the remainder of this test-run
    And reset the SSCC18 code generator's next sequence number back to its actual sequence.

    And EDI_Desadv is found:
      | EDI_Desadv_ID.Identifier | C_BPartner_ID.Identifier | C_Order_ID.Identifier | EDI_ExportStatus |
      | d_1                      | customer1                | o_1                   | P                |

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
      "value": "@shipment_id_S0468_010@"
    }
  ]
}
    """

    Then the metasfresh REST-API responds with
    """
{
  "DocumentNo": "1013010",
  "M_InOut_ID": 1000000,
  "@SequenceNo": 1000000,
  "DateOrdered": "2025-04-17T00:00:00",
  "POReference": "testReference",
  "DatePromised": "2025-05-15T00:00:00",
  "MovementDate": "2025-05-15T00:00:00",
  "C_BPartner_ID": {
    "GLN": "1234567890",
    "Name": "desadvReceiverName",
    "Value": "desadvReceiverValue",
    "IsVendor": "N",
    "IsCustomer": "Y"
  },
  "C_Currency_ID": {
    "ISO_Code": "EUR",
    "CurSymbol": "€"
  },
  "EDI_Desadv_ID": 1000000,
  "DeliveryViaRule": "P",
  "Bill_Location_ID": {
    "GLN": "1234567890123",
    "City": null,
    "Postal": null,
    "Address1": null,
    "Address2": null,
    "CountryCode": "DE"
  },
  "EDI_ExportStatus": "P",
  "ShipmentDocumentNo": "545147",
  "EDI_Exp_Desadv_Pack": [
    {
      "SeqNo": 1,
      "IPA_SSCC18": "012345670010000005",
      "GTIN_PackingMaterial": "gtinPiItemProduct",
      "M_HU_PackagingCode_Text": "ISO1",
      "EDI_Exp_Desadv_Pack_Item": [
        {
          "Line": 10,
          "QtyTU": 10,
          "LotNumber": null,
          "QtyCUsPerLU": 100,
          "QtyCUsPerTU": 10,
          "BestBeforeDate": null,
          "EDI_DesadvLine_ID": {
            "Line": 10,
            "C_UOM_ID": {
              "Name": "Stück",
              "UOMSymbol": "Stk"
            },
            "IPA_GTIN": null,
            "QtyEntered": 100,
            "MovementQty": 100,
            "M_Product_ID": {
              "UPC": null,
              "Name": "postgRESTExportProductName",
              "Value": "postgRESTExportProductValue"
            },
            "C_OrderLine_ID": 10,
            "M_InOutLine_ID": 10,
            "OrderDocumentNo": "0001"
          },
          "GTIN_TU_PackingMaterial": null,
          "QtyCUsPerLU_InInvoiceUOM": 100,
          "QtyCUsPerTU_InInvoiceUOM": 10,
          "M_HU_PackagingCode_TU_Text": "ISO1"
        }
      ]
    }
  ],
  "HandOver_Partner_ID": {
    "GLN": "1234567890",
    "Name": "desadvReceiverName",
    "Value": "desadvReceiverValue",
    "IsVendor": "N",
    "IsCustomer": "Y"
  },
  "shipment_documentno": "545147",
  "DropShip_BPartner_ID": {
    "GLN": "1234567890",
    "Name": "desadvReceiverName",
    "Value": "desadvReceiverValue",
    "IsVendor": "N",
    "IsCustomer": "Y"
  },
  "DropShip_Location_ID": {
    "GLN": "1234567890123",
    "City": null,
    "Postal": null,
    "Address1": null,
    "Address2": null,
    "CountryCode": "DE"
  },
  "HandOver_Location_ID": {
    "GLN": "1234567890123",
    "City": null,
    "Postal": null,
    "Address1": null,
    "Address2": null,
    "CountryCode": "DE"
  },
  "InvoicableQtyBasedOn": "Nominal",
  "C_BPartner_Location_ID": {
    "GLN": "1234567890123",
    "City": null,
    "Postal": null,
    "Address1": null,
    "Address2": null,
    "CountryCode": "DE"
  },
  "EDI_Exp_DesadvLineWithNoPack": []
}    
    """

    And after not more than 60s, EDI_Desadv records have the following export status
      | EDI_Desadv_ID.Identifier | EDI_ExportStatus |
      | d_1                      | S                |

 

