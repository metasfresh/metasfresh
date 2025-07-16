@from:cucumber
@ghActions:run_on_executor7
Feature: Purchase order

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    And metasfresh contains M_Products:
      | Identifier          | Name                |
      | product_PO_16072025 | product_PO_16072025 |

    And load M_HU_PI:
      | M_HU_PI_ID.Identifier  | M_HU_PI_ID |
      | huPackingTauschpalette | 1000006    |
    And load M_HU_PI_Version:
      | M_HU_PI_Version_ID.Identifier | M_HU_PI_Version_ID |
      | packingItem_IFCO              | 2002669            |
    And metasfresh contains M_HU_PI_Item:
      | M_HU_PI_Item_ID.Identifier | M_HU_PI_Version_ID.Identifier | Qty | ItemType |
      | huPiItem_IFCO              | packingItem_IFCO              | 0   | MI       |
    And metasfresh contains M_HU_PI_Item_Product:
      | M_HU_PI_Item_Product_ID.Identifier | M_HU_PI_Item_ID.Identifier | M_Product_ID.Identifier | Qty | ValidFrom  |
      | huPiItemProduct_16072025           | huPiItem_IFCO              | product_PO_16072025     | 10  | 2022-03-01 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name  | Value |
      | ps_PO      | ps_PO | ps_PO |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name    | ValidFrom  |
      | plv_PO2    | pl_PO                     | plv_PO2 | 2022-04-01 |
      | plv_PO1    | pl_PO                     | plv_PO1 | 2022-03-01 |

    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO1     | plv_PO1                           | product_PO_16072025     | 10.0     | PCE               | Normal                        |
      | pp_PO2     | plv_PO2                           | product_PO_16072025     | 12.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | supplier_PO | supplier_PO_16072025 | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | 1607202511111 | supplier_PO              | Y                   | Y                   |


  @from:cucumber
  Scenario: When creating a purchase order, the DateOrdered is used to identify the correct price of the product, not the DatePromised.
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | DatePromised | OPT.POReference | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | order_PO   | N       | supplier_PO              | 2022-03-10  | 2022-04-10   | po_ref          | POO             | ps_PO                             | supplierLocation_PO                   | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO | order_PO              | product_PO_16072025     | 10         |

    When the order identified by order_PO is completed

    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | qtydelivered | qtyinvoiced | price | discount | currencyCode | processed | OPT.QtyReserved |
      | orderLine_PO              | order_PO              | product_PO_16072025     | 10         | 0            | 0           | 10    | 0        | EUR          | true      | 10              |
