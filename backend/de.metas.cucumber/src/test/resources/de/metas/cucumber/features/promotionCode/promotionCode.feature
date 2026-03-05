@from:cucumber
@ghActions:run_on_executor6
Feature: Validate that C_PromotionCode fields propagate from C_Order through C_Invoice_Candidate to C_Invoice

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-05T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier | Name    |
      | product_1  | product |
    And metasfresh contains M_PricingSystems
      | Identifier   | Name           | Value           |
      | pricingSys_1 | pricingSysName | pricingSysValue |

    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name         | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList_SO | pricingSys_1                  | DE                        | EUR                 | priceList_SO | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID.Identifier | Name              | ValidFrom  |
      | priceListVersion_SO | priceList_SO              | salesOrder-PLV    | 2026-02-01 |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | productPrices_SO | priceListVersion_SO               | product_1               | 10.0     | PCE               | Normal                        |

  @from:cucumber
  Scenario: One promotion code propagates from C_Order to C_Invoice via IC flow
    Given metasfresh contains C_PromotionCode:
      | Identifier  | Value | Name               |
      | promoCode_1 | PC001 | Summer Campaign    |

    And metasfresh contains C_BPartners:
      | Identifier | Name              | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bpartner_1 | PromoCodeCustomer | N            | Y              | pricingSys_1                  | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_1 | 0285656789011 | bpartner_1               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference      | C_PromotionCode_ID |
      | order_1    | true    | bpartner_1               | 2026-03-05  | PromoCode_OneCode_SO | promoCode_1        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_1       | order_1               | product_1               | 10         |

    When the order identified by order_1 is completed

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | OPT.POReference      | processed | DocStatus | C_PromotionCode_ID |
      | order_1               | bpartner_1               | location_1                        | 2026-03-05  | SOO         | EUR          | F            | P               | PromoCode_OneCode_SO | true      | CO        | promoCode_1        |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_1               | ol_1                      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | C_PromotionCode_ID |
      | invoice_candidate_1               | order_1                   | ol_1                          | 10           | promoCode_1        |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_1               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | invoice_candidate_1               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference      | processed | DocStatus | C_PromotionCode_ID |
      | invoice_1               | bpartner_1               | location_1                        | PromoCode_OneCode_SO | true      | CO        | promoCode_1        |

  @from:cucumber
  Scenario: Two different promotion codes propagate from C_Order to C_Invoice via IC flow
    Given metasfresh contains C_PromotionCode:
      | Identifier  | Value | Name               |
      | promoCode_A | PC010 | Autumn Campaign    |
      | promoCode_B | PC020 | Winter Campaign    |

    And metasfresh contains C_BPartners:
      | Identifier | Name               | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | bpartner_2 | PromoCodeCustomer2 | N            | Y              | pricingSys_1                  | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_2 | 0285656780022 | bpartner_2               | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.POReference       | C_PromotionCode_ID | C_PromotionCode2_ID |
      | order_2    | true    | bpartner_2               | 2026-03-05  | PromoCode_TwoCodes_SO | promoCode_A        | promoCode_B         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | ol_2       | order_2               | product_1               | 5          |

    When the order identified by order_2 is completed

    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | DateOrdered | DocBaseType | currencyCode | DeliveryRule | DeliveryViaRule | OPT.POReference       | processed | DocStatus | C_PromotionCode_ID | C_PromotionCode2_ID |
      | order_2               | bpartner_2               | location_2                        | 2026-03-05  | SOO         | EUR          | F            | P               | PromoCode_TwoCodes_SO | true      | CO        | promoCode_A        | promoCode_B         |

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoice_candidate_2               | ol_2                      |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Order_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice | C_PromotionCode_ID | C_PromotionCode2_ID |
      | invoice_candidate_2               | order_2                   | ol_2                          | 5            | promoCode_A        | promoCode_B         |

    And process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoice_candidate_2               |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_2               | invoice_candidate_2               |

    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference       | processed | DocStatus | C_PromotionCode_ID | C_PromotionCode2_ID |
      | invoice_2               | bpartner_2               | location_2                        | PromoCode_TwoCodes_SO | true      | CO        | promoCode_A        | promoCode_B         |
