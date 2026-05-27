@from:cucumber
@allure.label.epic:E0140_Purchasing
@allure.label.feature:F00600_Purchase_Order
@ghActions:run_on_executor6
Feature: Purchase candidate — vendor-aware lead time on PurchaseDateOrdered
  ## When a sales order auto-creates a purchase candidate, the candidate's
  ## PurchaseDateOrdered = PurchaseDatePromised − lead-time, with lead-time
  ## resolved via the chain:
  ##   1. C_BPartner_Product.DeliveryTime_Promised (vendor × product)
  ##   2. C_BPartner.PO_TransportDays (vendor default)
  ##   3. PP_Product_Planning.DeliveryTime_Promised (product fallback)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-06-01T13:30:13+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And AD_Scheduler for classname 'de.metas.material.cockpit.stock.process.MD_Stock_Update_From_M_HUs' is disabled

  @from:cucumber
  Scenario: tier 1 — C_BPartner_Product.DeliveryTime_Promised wins
    # Vendor-product = 3 days; BPartner default = 10; PP_Product_Planning = 99 (both must be ignored).
    # DatePromised = 2022-06-15 → PurchaseDateOrdered = 2022-06-12
    Given metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps                 | EUR                 | true  | false         | 2              |
      | pl_po      | ps                 | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
      | plv_po     | pl_po          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.00    | PCE               | Normal                        |
      | pp_po      | plv_po                 | product      | 10.00    | PCE               | Normal                        |
    ## VendorProductInfoService.getVendorProductInfos only considers vendors with a PO_DiscountSchema_ID
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                  | DiscountType | ValidFrom  |
      | ds         | lead_time_tier1_ds    | F            | 2022-01-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb        | ds                  | product      | ps                    | 10    | Y                      | P         | 0          | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete | DeliveryTime_Promised |
      | ppln       | product      | true         | Y           | true          | 99                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_TransportDays | PO_DiscountSchema_ID |
      | vendor     | Y        | N          | ps                 | 10               | ds                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID | DeliveryTime_Promised |
      | vendor        | product      | 3                     |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | so         | true    | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    When the order identified by so is completed

    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID | PurchaseDatePromised | PurchaseDateOrdered |
      | pc         | so           | sol              | product      | 2022-06-15           | 2022-06-12          |

  @from:cucumber
  Scenario: tier 2 — C_BPartner.PO_TransportDays fallback
    # No vendor-product DeliveryTime_Promised; BPartner default = 5; PP_Product_Planning = 99 (ignored).
    # DatePromised = 2022-06-15 → PurchaseDateOrdered = 2022-06-10
    Given metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps                 | EUR                 | true  | false         | 2              |
      | pl_po      | ps                 | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
      | plv_po     | pl_po          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.00    | PCE               | Normal                        |
      | pp_po      | plv_po                 | product      | 10.00    | PCE               | Normal                        |
    ## VendorProductInfoService.getVendorProductInfos only considers vendors with a PO_DiscountSchema_ID
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                  | DiscountType | ValidFrom  |
      | ds         | lead_time_tier2_ds    | F            | 2022-01-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb        | ds                  | product      | ps                    | 10    | Y                      | P         | 0          | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete | DeliveryTime_Promised |
      | ppln       | product      | true         | Y           | true          | 99                    |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_TransportDays | PO_DiscountSchema_ID |
      | vendor     | Y        | N          | ps                 | 5                | ds                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor        | product      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | so         | true    | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    When the order identified by so is completed

    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID | PurchaseDatePromised | PurchaseDateOrdered |
      | pc         | so           | sol              | product      | 2022-06-15           | 2022-06-10          |

  @from:cucumber
  Scenario: tier 3 — PP_Product_Planning.DeliveryTime_Promised fallback
    # No vendor-product DeliveryTime_Promised; no BPartner PO_TransportDays; PP_Product_Planning = 7.
    # DatePromised = 2022-06-15 → PurchaseDateOrdered = 2022-06-08
    Given metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_so      | ps                 | EUR                 | true  | false         | 2              |
      | pl_po      | ps                 | EUR                 | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv_so     | pl_so          |
      | plv_po     | pl_po          |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so      | plv_so                 | product      | 10.00    | PCE               | Normal                        |
      | pp_po      | plv_po                 | product      | 10.00    | PCE               | Normal                        |
    ## VendorProductInfoService.getVendorProductInfos only considers vendors with a PO_DiscountSchema_ID
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                  | DiscountType | ValidFrom  |
      | ds         | lead_time_tier3_ds    | F            | 2022-01-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier | M_DiscountSchema_ID | M_Product_ID | Base_PricingSystem_ID | SeqNo | IsBPartnerFlatDiscount | PriceBase | BreakValue | BreakDiscount |
      | dsb        | ds                  | product      | ps                    | 10    | Y                      | P         | 0          | 0             |
    And metasfresh contains PP_Product_Plannings
      | Identifier | M_Product_ID | IsCreatePlan | IsPurchased | IsDocComplete | DeliveryTime_Promised |
      | ppln       | product      | true         | Y           | true          | 7                     |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | customer   | N        | Y          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | Y               | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PO_DiscountSchema_ID |
      | vendor     | Y        | N          | ps                 | ds                   |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendorLocation | vendor        | Y               | Y               |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID | M_Product_ID |
      | vendor        | product      |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | M_PricingSystem_ID | C_BPartner_Location_ID |
      | so         | true    | customer      | 2022-06-01  | 2022-06-15Z  | ps                 | customerLocation       |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | sol        | so         | product      | 10         |
    When the order identified by so is completed

    And after not more than 60s, C_PurchaseCandidates are found
      | Identifier | C_OrderSO_ID | C_OrderLineSO_ID | M_Product_ID | PurchaseDatePromised | PurchaseDateOrdered |
      | pc         | so           | sol              | product      | 2022-06-15           | 2022-06-08          |
