@from:cucumber
@ghActions:run_on_executor7
Feature: Order price based on date

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID | Value        |
      | warehouseStd   | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |

    And load M_HU_PI:
      | Identifier             | M_HU_PI_ID |
      | huPackingTauschpalette | 1000006    |
    And load M_HU_PI_Version:
      | Identifier       | M_HU_PI_Version_ID |
      | packingItem_IFCO | 2002669            |
    And metasfresh contains M_HU_PI_Item:
      | Identifier    | M_HU_PI_Version_ID | Qty | ItemType |
      | huPiItem_IFCO | packingItem_IFCO   | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | Identifier      | M_HU_PI_Item_ID | M_Product_ID | Qty | ValidFrom  |
      | huPiItemproduct | huPiItem_IFCO   | product      | 10  | 2022-03-01 |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps1        |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_PO      | ps1                | DE                    | EUR           | false | false         |
      | pl_SO      | ps1                | DE                    | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID | ValidFrom  |
      | plv_PO2    | pl_PO          | 2022-04-01 |
      | plv_PO1    | pl_PO          | 2022-03-01 |
      | plv_SO2    | pl_SO          | 2022-04-01 |
      | plv_SO1    | pl_SO          | 2022-03-01 |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | pp_PO1     | plv_PO1                | product      | 10.0     | PCE               |
      | pp_PO2     | plv_PO2                | product      | 12.0     | PCE               |
      | pp_SO1     | plv_SO1                | product      | 20.0     | PCE               |
      | pp_SO2     | plv_SO2                | product      | 24.0     | PCE               |

    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | supplier   | Y        | N          | ps1                |
      | buyer      | N        | Y          | ps1                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | supplierLocation | 1607202511111 | supplier      | Y               | Y               |
      | buyerLocation    | 1607202511112 | buyer         | Y               | Y               |

  @from:cucumber
  Scenario: When creating a purchase order, the DateOrdered is used to identify the correct price of the product, not the DatePromised.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference | DocBaseType | M_PricingSystem_ID | C_BPartner_Location_ID | DeliveryRule | DeliveryViaRule |
      | order_PO   | N       | supplier      | 2022-03-10  | 2022-04-10   | po_ref      | POO         | ps1                | supplierLocation       | F            | S               |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_PO | order_PO   | product      | 10         |

    When the order identified by order_PO is completed

    Then validate C_OrderLine:
      | Identifier   | C_Order_ID | M_Product_ID | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | QtyReserved |
      | orderLine_PO | order_PO   | product      | 10         | 0            | 0           | 10    | 0        | EUR          | true      | 10          |

  @from:cucumber
  Scenario: When creating a sales order, the DatePromised is used to identify the correct price of the product, not the DateOrdered.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID | DeliveryRule | DeliveryViaRule |
      | order_PO   | Y       | buyer         | 2022-03-10  | 2022-04-10   | ps1                | buyerLocation          | F            | S               |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID | M_Product_ID | QtyEntered |
      | orderLine_PO | order_PO   | product      | 10         |

    When the order identified by order_PO is completed

    Then validate C_OrderLine:
      | Identifier   | C_Order_ID | M_Product_ID | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | QtyReserved |
      | orderLine_PO | order_PO   | product      | 10         | 0            | 0           | 24    | 0        | EUR          | true      | 10          |