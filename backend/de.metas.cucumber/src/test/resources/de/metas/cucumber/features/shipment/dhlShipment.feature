@from:cucumber
@ghActions:run_on_executor7
Feature: Dhl Shipment

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2022-12-12T12:12:12+01:00[Europe/Berlin]
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And ensure product accounts exist
    And load M_Shipper:
      | M_Shipper_ID.Identifier | OPT.Name |
      | shipper_DHL             | Dhl      |
    And load C_UOM:
      | C_UOM_ID.Identifier | X12DE355 |
      | cm                  | CM       |
    And metasfresh contains DHL Configuration:
      | DHL_Shipper_Config_ID.Identifier | M_Shipper_ID.Identifier | AccountNumber  |
      | dhl1                             | shipper_DHL             | 33333333330102 |

    #Karton
    And load M_Product:
      | M_Product_ID.Identifier | OPT.M_Product_ID |
      | packing_product_1       | 2003135          |
    #Configure dimensions of packing product
    And metasfresh contains M_HU_PackingMaterial:
      | M_HU_PackingMaterial_ID.Identifier | OPT.M_Product_ID.Identifier | Name   | OPT.Length | OPT.Width | OPT.Height | OPT.C_UOM_Dimension_ID.Identifier |
      | dhl_pm                             | packing_product_1           | Karton | 30         | 20        | 10         | cm                                |

    # Create product
    And metasfresh contains M_Products:
      | Identifier          | Name                | IsStocked |
      | test_product_dhl_01 | test_product_dhl_01 | true      |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps_dhl_1   | pricing_system | pricing_system |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name              | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_dhl_so  | ps_dhl_1                      | DE                        | EUR                 | price_list_dhl_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name       | ValidFrom  |
      | plv_dhl_so | pl_dhl_so                 | plv_dhl_so | 2022-01-20 |
    And metasfresh contains M_ProductPrices
      | Identifier           | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | pp_product_01        | plv_dhl_so                        | test_product_dhl_01     | 10.0     | Normal                        | PCE               |
      | pp_packing_product_1 | plv_dhl_so                        | packing_product_1       | 0.0      | Normal                        | PCE               |

    And metasfresh contains M_Inventories:
      | M_Inventory_ID.Identifier | MovementDate | M_Warehouse_ID |
      | inv_1                     | 2022-12-12   | 540008         |
    And metasfresh contains M_InventoriesLines:
      | M_Inventory_ID.Identifier | M_InventoryLine_ID.Identifier | M_Product_ID.Identifier | QtyBook | QtyCount | UOM.X12DE355 |
      | inv_1                     | inv_l_1                       | test_product_dhl_01     | 0       | 10       | PCE          |
      | inv_1                     | inv_l_2                       | packing_product_1       | 0       | 10       | PCE          |
    When the inventory identified by inv_1 is completed
    And after not more than 60s, there are added M_HUs for inventory
      | M_InventoryLine_ID.Identifier | M_HU_ID.Identifier |
      | inv_l_1                       | hu_1               |
      | inv_l_2                       | hu_2               |
    And M_HU_Storage are validated
      | Identifier | M_HU_ID.Identifier | M_Product_ID.Identifier | Qty |
      | hu_s_1     | hu_1               | test_product_dhl_01     | 10  |
      | hu_s_2     | hu_2               | packing_product_1       | 10  |

    # create bpartner with invoice-rule "immediate", because we need just an invoice without a shipment
    And metasfresh contains C_BPartners:
      | Identifier   | Name         | M_PricingSystem_ID.Identifier | OPT.IsCustomer | OPT.CompanyName | OPT.InvoiceRule | OPT.C_PaymentTerm_ID.Value |
      | dhl_customer | dhl_customer | ps_dhl_1                      | Y              | dhl_customer    | I               | 1000002                    |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | dhl_location             | DE          | addr 22      | 456        | locationCity_2 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo | OPT.BPartnerName | OPT.Name     |
      | dhl_location | 1122334455667 | dhl_customer             | dhl_location                 | true         | true         | locationBPName   | locationName |

    # Create CU-TU Allocation
    And metasfresh contains M_HU_PI:
      | M_HU_PI_ID.Identifier | Name            |
      | dhl_huPackingTU       | dhl_huPackingTU |
    And metasfresh contains M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_ID.Identifier | Name                 | HU_UnitType | IsCurrent |
      | dhl_packingVersionTU          | dhl_huPackingTU       | dhl_packingVersionTU | TU          | Y         |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType | OPT.M_HU_PackingMaterial_ID.Identifier |
      | dhl_huPiItemTU             | dhl_packingVersionTU          | 0   | PM       | dhl_pm                                 |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  | OPT.M_HU_PI_Item_Product_ID |
      | dhl_huProductTU_X                  | dhl_huPiItemTU             | test_product_dhl_01     | 1   | 2022-01-10 | 55667                       |

  @Id:S0335.1_100
  Scenario: Auto-processing of olcand and shipped via DHL
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/bulk' and fulfills with '201' status code
    """
{
  "requests": [
    {
      "orgCode": "001",
      "externalHeaderId": "dhl_01",
      "externalLineId": "dhl_01",
      "dataSource": "int-Shopware",
      "bpartner": {
          "bpartnerIdentifier": "gln-1122334455667",
          "bpartnerLocationIdentifier": "gln-1122334455667"
      },
      "dateRequired": "2022-12-12",
      "dateOrdered": "2022-12-12",
      "orderDocType": "SalesOrder",
      "paymentTerm": "val-1000002",
      "productIdentifier": "val-test_product_dhl_01",
      "qty": 1,
      "currencyCode": "EUR",
      "discount": 0,
      "poReference": "ref_12301",
      "deliveryViaRule": "S",
      "deliveryRule": "F",
      "bpartnerName": "olCandBPartnerName",
      "packingMaterialId": 55667,
      "shipper": "val-Dhl"
    }
  ]
}
"""

    And a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code
"""
{
    "externalHeaderId": "dhl_01",
    "inputDataSourceName": "int-Shopware",
    "ship": true,
    "invoice": true,
    "closeOrder": false
}
"""
# why TF do we expect two invoices??
    #
    Then process metasfresh response
      | C_Order_ID.Identifier | M_InOut_ID.Identifier | C_Invoice_ID.Identifier |
      | order_1               | shipment_1            | invoice_1               |

    And validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | docbasetype | currencyCode | deliveryRule | deliveryViaRule | poReference | processed | docStatus |
      | order_1               | dhl_customer             | dhl_location                      | 2022-12-12  | SOO         | EUR          | F            | S               | ref_12301   | true      | CO        |

    And validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | dateordered | M_Product_ID.Identifier | qtydelivered | QtyOrdered | qtyinvoiced | price | discount | currencyCode | processed |
      | orderLine_1               | order_1               | 2022-02-02  | test_product_dhl_01     | 1            | 1          | 1           | 10.0  | 0        | EUR          | true      |
      | orderLine_2               | order_1               | 2022-02-02  | packing_product_1       | 0            | 1          | 0           | 0.0   | 0        | EUR          | true      |

    And validate the created shipments
      | M_InOut_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | dateordered | poreference | processed | docStatus |
      | shipment_1            | dhl_customer             | dhl_location                      | 2022-12-12  | ref_12301   | true      | CO        |

    And validate the created shipment lines
      | M_InOutLine_ID.Identifier | M_InOut_ID.Identifier | M_Product_ID.Identifier | movementqty | processed |
      | line1                     | shipment_1            | test_product_dhl_01     | 1           | true      |
      | line2                     | shipment_1            | packing_product_1       | 1           | true      |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm | processed | docStatus | OPT.BPartnerAddress                         |
      | invoice_1               | dhl_customer             | dhl_location                      | ref_12301       | 1000002     | true      | CO        | locationBPName\naddr 22\n456 locationCity_2 |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | il2                         | invoice_1               | packing_product_1       | 1           | true      |
      | il1                         | invoice_1               | test_product_dhl_01     | 1           | true      |
    And load Transportation Order from Shipment
      | M_InOut_ID.Identifier | M_ShipperTransportation_ID.Identifier |
      | shipment_1            | shipTransp_1                          |
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.Value  |
      | orgBP                    | metasfresh |
    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | orgBPLocation                     | 2202690                    |

    And validate M_ShipperTransportation:
      | M_ShipperTransportation_ID.Identifier | M_Shipper_ID.Identifier | Shipper_BPartner_ID.Identifier | Shipper_Location_ID.Identifier | OPT.DocStatus |
      | shipTransp_1                          | shipper_DHL             | orgBP                          | orgBPLocation                  | DR            |
    And load M_Package for M_ShipperTransportation: shipTransp_1
      | M_Package_ID.Identifier | OPT.M_InOut_ID.Identifier |
      | packageDI               | shipment_1                |
    And validate M_Package:
      | M_Package_ID.Identifier | M_Shipper_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | packageDI               | shipper_DHL             | dhl_customer                 | dhl_location                          |
    And load DHL_ShipmentOrder:
      | DHL_ShipmentOrder_ID.Identifier | M_Package_ID.Identifier |
      | shippingPackageDI               | packageDI               |
    And validate DHL_ShipmentOrder:
      | DHL_ShipmentOrder_ID.Identifier | C_BPartner_ID.Identifier | DHL_LengthInCm | DHL_WidthInCm | DHL_HeightInCm | DHL_WeightInKg |
      | shippingPackageDI               | dhl_customer             | 30             | 20            | 10             | 1              |
