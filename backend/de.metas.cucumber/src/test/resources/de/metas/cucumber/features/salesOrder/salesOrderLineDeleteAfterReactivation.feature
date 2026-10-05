@from:cucumber
@allure.label.epic:E0100_Sales
@allure.label.feature:F00105_Sales_Order_Document
@ghActions:run_on_executor4
Feature: Delete a sales order line after the order was reactivated
  Completing a sales order creates, per line, a shipment schedule, an invoice candidate and a
  purchase candidate. Reactivating the order does not remove them. Deleting a re-opened line must
  cascade-remove those planning objects when they never produced a real document, and must
  politely block (no raw database error) when a completed document already references the line.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-07-20T08:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # test C_BPartners are not EDI-flavored; disable the EDI interceptors so plain order creation and
    # invoice-candidate creation don't fail trying to load them as de.metas.edi.model.I_C_BPartner
    # (see AnnotatedModelInterceptorDisabler); EDI recipient flagging is irrelevant to this delete-flow
    # test, so the interceptor that reads it during C_Invoice_Candidate creation is disabled too
    And set sys config String value N for sys config InterceptorEnabled_de.metas.edi.model.validator.C_Order#setEdiEnabledForNewOrder
    And set sys config String value N for sys config InterceptorEnabled_de.metas.edi.model.validator.C_Invoice_Candidate#setIsEDIInvoicRecipient

  @Id:S29172_10
  Scenario: Reactivated line whose planning objects never produced a real document cascades cleanly
    Given add M_AttributeSet:
      | M_AttributeSet_ID.Identifier | Name                   |
      | attributeSet_delLine         | Delete-Line Attributes |
    And metasfresh contains M_Product_Categories:
      | Identifier       | Name       | Value      | OPT.M_AttributeSet_ID.Identifier |
      | category_delLine | DeleteLine | DeleteLine | attributeSet_delLine             |
    And metasfresh contains M_Products:
      | Identifier      | Name            | OPT.M_Product_Category_ID.Identifier | OPT.IsSold | OPT.IsPurchased |
      | product_delLine | product_delLine | category_delLine                     | Y          | Y               |
    And metasfresh contains M_PricingSystems
      | Identifier | Name                   | Value                        | OPT.IsActive |
      | ps_delLine | pricing_system_delLine | pricing_system_value_delLine | true         |
    And metasfresh contains M_PriceLists
      | Identifier   | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_s_delLine | ps_delLine                    | DE                        | EUR                 | s_price_list_delLine | true  | false         | 2              | true         |
      | pl_p_delLine | ps_delLine                    | DE                        | EUR                 | p_price_list_delLine | false | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier    | M_PriceList_ID.Identifier | Name          | ValidFrom  |
      | plv_s_delLine | pl_s_delLine              | s_PLV_delLine | 2026-07-01 |
      | plv_p_delLine | pl_p_delLine              | p_PLV_delLine | 2026-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_s_delLine | plv_s_delLine                     | product_delLine         | 10.0     | PCE               | Normal                        |
      | pp_p_delLine | plv_p_delLine                     | product_delLine         | 10.0     | PCE               | Normal                        |
    And metasfresh contains M_DiscountSchemas:
      | Identifier | Name                    | DiscountType | ValidFrom  |
      | ds_delLine | discount_schema_delLine | F            | 2026-07-01 |
    And metasfresh contains M_DiscountSchemaBreaks:
      | Identifier  | M_DiscountSchema_ID.Identifier | M_Product_ID.Identifier | Base_PricingSystem_ID.Identifier | SeqNo | OPT.IsBPartnerFlatDiscount | OPT.PriceBase | OPT.BreakValue | OPT.BreakDiscount |
      | dsb_delLine | ds_delLine                     | product_delLine         | ps_delLine                       | 10    | Y                          | P             | 10             | 0                 |
    And metasfresh contains PP_Product_Plannings
      | Identifier   | M_Product_ID.Identifier | IsCreatePlan | OPT.IsPurchased |
      | ppln_delLine | product_delLine         | false        | Y               |
    And metasfresh contains C_BPartners without locations:
      | Identifier       | Name             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.PO_DiscountSchema_ID.Identifier |
      | customer_delLine | Customer_delLine | N            | Y              | ps_delLine                    |                                     |
      | vendor_delLine   | Vendor_delLine   | Y            | N              | ps_delLine                    | ds_delLine                          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                | GLN           | C_BPartner_ID.Identifier | OPT.Name         | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | vendor_location_delLine   | 2411250300000 | vendor_delLine           | Vendor_delLine   | Y                   | Y                   |
      | customer_location_delLine | 2411250300001 | customer_delLine         | Customer_delLine | Y                   | Y                   |
    And metasfresh contains C_BPartner_Product
      | C_BPartner_ID.Identifier | M_Product_ID.Identifier |
      | vendor_delLine           | product_delLine         |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered |
      | order_1    | true    | customer_delLine         | 2026-07-20  |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_1 | order_1               | product_delLine         | 5          |
    When the order identified by order_1 is completed

    Then after not more than 60s, C_PurchaseCandidates are found
      | Identifier          | C_OrderSO_ID.Identifier | C_OrderLineSO_ID.Identifier | M_Product_ID.Identifier |
      | purchaseCandidate_1 | order_1                 | orderLine_1                 | product_delLine         |
    And wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes
    And no C_PurchaseCandidate_Alloc are found for:
      | C_PurchaseCandidate_ID.Identifier |
      | purchaseCandidate_1               |

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | schedule_1 | orderLine_1               | N             |
    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate_1                | orderLine_1               | 0            |

    # Reactivating does not neutralize the planning objects (per the real incident): the shipment
    # schedule, invoice candidate and purchase candidate are all still around at this point.
    When the order identified by order_1 is reactivated
    And delete C_OrderLine identified by orderLine_1, but keep its id into identifierIds table

    Then no C_PurchaseCandidate found for orderLine orderLine_1
    And there is no M_ShipmentSchedule for C_Order order_1
    And there is no C_Invoice_Candidate for C_Order order_1

  @Id:S29172_20
  Scenario: Reactivated line still referenced by a completed invoice is blocked with a friendly message
    Given metasfresh contains M_Products:
      | Identifier         | Name               |
      | product_delLineInv | product_delLineInv |
    And metasfresh contains M_PricingSystems
      | Identifier    | Name                      | Value                           | OPT.IsActive |
      | ps_delLineInv | pricing_system_delLineInv | pricing_system_value_delLineInv | true         |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                  | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_delLineInv | ps_delLineInv                 | DE                        | EUR                 | price_list_delLineInv | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID.Identifier | Name           | ValidFrom  |
      | plv_delLineInv | pl_delLineInv             | plv_delLineInv | 2026-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_delLineInv | plv_delLineInv                    | product_delLineInv      | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier          | Name                | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_delLineInv | Customer_delLineInv | N            | Y              | ps_delLineInv                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_delLineInv | 2411250300002 | customer_delLineInv      | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.InvoiceRule |
      | order_2    | true    | customer_delLineInv      | 2026-07-20  | I               |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_2 | order_2               | product_delLineInv      | 5          |
    When the order identified by order_2 is completed

    Then after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoiceCandidate_2                | orderLine_2               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate_2                | orderLine_2                   | 5            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_2                |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_2                | invoice_2               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | processed | DocStatus |
      | invoice_2               | customer_delLineInv      | true      | CO        |

    And load AD_Message:
      | Identifier | Value                                        |
      | blockMsg   | SalesOrderLine_CannotDelete_HasCompletedDocs |

    When the order identified by order_2 is reactivated
    And delete C_OrderLine identified by orderLine_2 expecting error:
      | AD_Message_ID |
      | blockMsg      |

    # Nothing was deleted: the line is still there, unchanged.
    Then validate C_OrderLine:
      | C_OrderLine_ID.Identifier |
      | orderLine_2               |

  @Id:S29172_30
  Scenario: Reactivated line whose invoice was voided is no longer blocked
    Given metasfresh contains M_Products:
      | Identifier          | Name                |
      | product_delLineVoid | product_delLineVoid |
    And metasfresh contains M_PricingSystems
      | Identifier     | Name                       | Value                            | OPT.IsActive |
      | ps_delLineVoid | pricing_system_delLineVoid | pricing_system_value_delLineVoid | true         |
    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                   | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_delLineVoid | ps_delLineVoid                | DE                        | EUR                 | price_list_delLineVoid | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier      | M_PriceList_ID.Identifier | Name            | ValidFrom  |
      | plv_delLineVoid | pl_delLineVoid            | plv_delLineVoid | 2026-07-01 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_delLineVoid | plv_delLineVoid                   | product_delLineVoid     | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customer_delLineVoid | Customer_delLineVoid | N            | Y              | ps_delLineVoid                |
    And metasfresh contains C_BPartner_Locations:
      | Identifier           | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | location_delLineVoid | 2411250300003 | customer_delLineVoid     | Y                   | Y                   |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.InvoiceRule |
      | order_3    | true    | customer_delLineVoid     | 2026-07-20  | I               |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine_3 | order_3               | product_delLineVoid     | 5          |
    When the order identified by order_3 is completed

    Then after not more than 60s locate up2date invoice candidates by order line:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier |
      | invoiceCandidate_3                | orderLine_3               |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_OrderLine_ID.Identifier | QtyToInvoice |
      | invoiceCandidate_3                | orderLine_3                   | 5            |
    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_3                |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_3                | invoice_3               |
    And validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | processed | DocStatus |
      | invoice_3               | customer_delLineVoid     | true      | CO        |

    # Voiding the invoice makes it no longer "real": the delete is no longer blocked.
    When the invoice identified by invoice_3 is voided

    And the order identified by order_3 is reactivated
    And delete C_OrderLine identified by orderLine_3, but keep its id into identifierIds table

    Then no C_PurchaseCandidate found for orderLine orderLine_3
    And there is no M_ShipmentSchedule for C_Order order_3
    And there is no C_Invoice_Candidate for C_Order order_3
