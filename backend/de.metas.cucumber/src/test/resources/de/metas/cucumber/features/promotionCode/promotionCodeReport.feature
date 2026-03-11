@from:cucumber
@ghActions:run_on_executor6
Feature: Promotion Code Evaluation Report (gh#28565)

  The report function report.report_promotion_code_evaluation() returns completed/closed
  sales orders that have at least one promotion code set, along with invoice data.
  This test validates the SQL function output after creating orders and invoicing them.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-03-08T13:30:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And metasfresh contains M_Products:
      | Identifier |
      | product_1  |
    And metasfresh contains M_PricingSystems
      | Identifier   |
      | pricingSys_1 |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID | C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList_SO | pricingSys_1       | DE                    | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier          | M_PriceList_ID |
      | priceListVersion_SO | priceList_SO   |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID.X12DE355 |
      | priceListVersion_SO    | product_1    | 25.0     | PCE               |

    And metasfresh contains C_PromotionCode:
      | Identifier   | ValidTo    |
      | promoCode_1  | 2026-12-31 |
      | promoCode_2  | 2026-06-30 |

    And metasfresh contains C_BPartners:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | InvoiceRule |
      | customer_1 | N        | Y          | pricingSys_1       | I           |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | location_1 | 0285656789099 | customer_1    | Y               | Y               |

  @from:cucumber
  Scenario: Report returns invoiced order with both promotion codes
    # Create and complete a sales order with both promotion codes
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PromotionCode_ID | C_PromotionCode2_ID |
      | order_1    | true    | customer_1    | 2026-03-08  | promoCode_1        | promoCode_2         |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_1       | order_1    | product_1    | 4          |
    When the order identified by order_1 is completed

    # Invoice the order
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_1                   | ol_1           |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | ic_1                   |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | invoice_1    | ic_1                   |

    # Validate the report function output — unfiltered
    Then the promotion code report contains:
      | C_Order_ID | C_PromotionCode_ID | C_PromotionCode2_ID | IsInvoiced | HasInvoiceDocumentNo |
      | order_1    | promoCode_1        | promoCode_2         | Y          | true                 |

  @from:cucumber
  Scenario: Report returns non-invoiced order with one promotion code
    # Create and complete a sales order with only one promotion code
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PromotionCode_ID |
      | order_2    | true    | customer_1    | 2026-03-08  | promoCode_1        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_2       | order_2    | product_1    | 2          |
    When the order identified by order_2 is completed

    # Validate the report function output — no invoice yet
    Then the promotion code report contains:
      | C_Order_ID | C_PromotionCode_ID | IsInvoiced |
      | order_2    | promoCode_1        | N          |

  @from:cucumber
  Scenario: Report filters by invoiced status
    # Create two orders: one invoiced, one not
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | C_PromotionCode_ID |
      | order_inv  | true    | customer_1    | 2026-03-08  | promoCode_1        |
      | order_noinv| true    | customer_1    | 2026-03-08  | promoCode_2        |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | ol_inv     | order_inv  | product_1    | 3          |
      | ol_noinv   | order_noinv| product_1    | 5          |
    When the order identified by order_inv is completed
    And the order identified by order_noinv is completed

    # Invoice only the first order
    And after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID | C_OrderLine_ID |
      | ic_inv                 | ol_inv         |
    And process invoice candidates
      | C_Invoice_Candidate_ID |
      | ic_inv                 |
    And after not more than 60s, C_Invoice are found:
      | C_Invoice_ID | C_Invoice_Candidate_ID |
      | inv_1        | ic_inv                 |

    # Filter: Invoiced=Y — should only return invoiced order
    Then the promotion code report with filter Invoiced Y contains:
      | C_Order_ID | IsInvoiced |
      | order_inv  | Y          |
    And the promotion code report with filter Invoiced Y does not contain:
      | C_Order_ID  |
      | order_noinv |

    # Filter: Invoiced=N — should only return non-invoiced order
    Then the promotion code report with filter Invoiced N contains:
      | C_Order_ID  | IsInvoiced |
      | order_noinv | N          |
    And the promotion code report with filter Invoiced N does not contain:
      | C_Order_ID |
      | order_inv  |
