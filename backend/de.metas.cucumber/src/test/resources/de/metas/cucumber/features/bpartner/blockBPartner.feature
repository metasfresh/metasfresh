@from:cucumber
@ghActions:run_on_executor3
Feature: Business Partner block management

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-04-16T13:30:13+01:00[Europe/Berlin]

  @from:cucumber
  Scenario: block and unblock a BPartner record

    Given load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    When C_BPartner is blocked
      | C_BPartner_ID.Identifier | Reason     |
      | endCustomer_1            | test_block |

    Then load latest C_BPartner_BlockStatus for C_BPartner:
      | C_BPartner_BlockStatus_ID.Identifier | C_BPartner_ID.Identifier |
      | bs_b_1                               | endCustomer_1            |

    And validate C_BPartner_BlockStatus:
      | C_BPartner_BlockStatus_ID.Identifier | OPT.Reason | BlockStatus |
      | bs_b_1                               | test_block | B           |

    When C_BPartner is unblocked
      | C_BPartner_ID.Identifier | Reason       |
      | endCustomer_1            | test_unblock |

    Then load latest C_BPartner_BlockStatus for C_BPartner:
      | C_BPartner_BlockStatus_ID.Identifier | C_BPartner_ID.Identifier |
      | bs_ub_1                              | endCustomer_1            |

    And validate C_BPartner_BlockStatus:
      | C_BPartner_BlockStatus_ID.Identifier | OPT.Reason   | BlockStatus |
      | bs_ub_1                              | test_unblock | UB          |

  @from:cucumber
  Scenario: we cannot complete a sales order for a blocked Business Partner
    Given metasfresh has date and time 2021-04-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                    |
      | p_1        | salesProduct_22022023_2 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                           | Value                           | OPT.Description                       | OPT.IsActive |
      | ps_1       | pricing_system_name_22022023_2 | pricing_system_value_22022023_2 | pricing_system_description_22022023_2 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                       | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | price_list_name_22022023_2 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                      | ValidFrom  |
      | plv_1      | pl_1                      | salesOrder-PLV_22022023_2 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                   | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer_1 | Endcustomer_22022023_2 | N            | Y              | ps_1                          |

    When C_BPartner is blocked
      | C_BPartner_ID.Identifier | Reason     |
      | endcustomer_1            | test_block |

    And load latest C_BPartner_BlockStatus for C_BPartner:
      | C_BPartner_BlockStatus_ID.Identifier | C_BPartner_ID.Identifier |
      | bs_b_1                               | endcustomer_1            |

    And validate C_BPartner_BlockStatus:
      | C_BPartner_BlockStatus_ID.Identifier | OPT.Reason | BlockStatus |
      | bs_b_1                               | test_block | B           |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | o_1        | true    | endcustomer_1            | 2021-04-17  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    Then the order identified by o_1 is completed and an exception with error-code CannotCompleteOrderWithBlockedPartner is thrown


  @from:cucumber
  Scenario: we cannot complete a shipment for a blocked Business Partner
    Given metasfresh has date and time 2021-12-16T13:30:13+01:00[Europe/Berlin]
    And metasfresh contains M_Products:
      | Identifier | Name                  |
      | p_1        | hu_product_23022023_3 |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                              | Value                              | OPT.Description                          | OPT.IsActive |
      | ps_1       | hu_pricing_system_name_23022023_3 | hu_pricing_system_value_23022023_3 | hu_pricing_system_description_23022023_3 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                          | OPT.Description | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | hu_price_list_name_23022023_3 | null            | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name                         | ValidFrom  |
      | plv_1      | pl_1                      | hu_salesOrder-PLV_23022023_3 | 2021-04-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | p_1                     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier    | Name                      | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | endcustomer_1 | hu_endcustomer_23022023_3 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | l_1        | 0123456789011 | endcustomer_1            | Y                   | Y                   |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference |
      | o_1        | true    | endcustomer_1            | 2021-04-17  | po_ref_mock     |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | o_1                   | p_1                     | 10         |
    When the order identified by o_1 is completed
    And after not more than 30s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | false               | false       |

    Then after not more than 30s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | shipment_1            |

    And C_BPartner is blocked
      | C_BPartner_ID.Identifier | Reason                |
      | endcustomer_1            | test_block_23022023_3 |

    And load latest C_BPartner_BlockStatus for C_BPartner:
      | C_BPartner_BlockStatus_ID.Identifier | C_BPartner_ID.Identifier |
      | bs_b_1                               | endcustomer_1            |

    And validate C_BPartner_BlockStatus:
      | C_BPartner_BlockStatus_ID.Identifier | OPT.Reason            | BlockStatus |
      | bs_b_1                               | test_block_23022023_3 | B           |

    Then the shipment identified by shipment_1 is completed and an exception with error-code CannotCompleteInOutWithBlockedPartner is thrown


  @from:cucumber
  Scenario: we cannot receive HUs from blocked BP
    Given load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |
    And load M_Locator:
      | M_Locator_ID.Identifier | M_Warehouse_ID.Identifier | Value      |
      | locatorHauptlager       | warehouseStd              | Hauptlager |

    And metasfresh contains M_Products:
      | Identifier      | Value                      | Name                       |
      | purchaseProduct | purchaseProduct_23022023_4 | purchaseProduct_23022023_4 |

    And metasfresh contains M_PricingSystems
      | Identifier | Name             | Value            | OPT.IsActive |
      | ps_PO      | ps_PO_23022023_4 | ps_PO_23022023_4 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_PO      | ps_PO                         | DE                        | EUR                 | pl_PO_name_23022023_4 | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | plv_PO     | pl_PO                     | plv_PO_23022023_4 | 2022-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_PO      | plv_PO                            | purchaseProduct         | 10.0     | PCE               | Normal                        |

    And metasfresh contains C_BPartners:
      | Identifier  | Name                   | Value                  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | supplier_PO | supplier_PO_23022023_4 | supplier_PO_23022023_4 | Y            | N              | ps_PO                         |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN          | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | supplierLocation_PO | supplierPO01 | supplier_PO              | true                | true                |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.DocBaseType | OPT.M_PricingSystem_ID.Identifier |
      | order_PO   | false   | supplier_PO              | 2022-01-05  | POO             | ps_PO                             |

    And metasfresh contains C_OrderLines:
      | Identifier   | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_PO | order_PO              | purchaseProduct         | 18         |

    And the order identified by order_PO is completed

    And after not more than 30s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | receiptSchedule_PO              | order_PO              | orderLine_PO              | supplier_PO              | supplierLocation_PO               | purchaseProduct         | 18         | warehouseStd              |

    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier | OPT.M_LU_HU_PI_ID.Identifier |
      | huLuTuConfig                          | processedLU        | receiptSchedule_PO              | N               | 1     | N               | 1     | N               | 18          | 101                                | 1000006                      |

    When C_BPartner is blocked
      | C_BPartner_ID.Identifier | Reason                |
      | supplier_PO              | test_block_23022023_4 |

    And load latest C_BPartner_BlockStatus for C_BPartner:
      | C_BPartner_BlockStatus_ID.Identifier | C_BPartner_ID.Identifier |
      | bs_b_1                               | supplier_PO              |

    And validate C_BPartner_BlockStatus:
      | C_BPartner_BlockStatus_ID.Identifier | OPT.Reason            | BlockStatus |
      | bs_b_1                               | test_block_23022023_4 | B           |

    Then create material receipt and the following exception is thrown
      | M_InOut_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | ErrorCode                             |
      | inOut_PO              | processedLU        | receiptSchedule_PO              | CannotCompleteInOutWithBlockedPartner |
    