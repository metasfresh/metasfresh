@from:cucumber
@allure.label.epic:E0170_Contract_Management
@allure.label.feature:F2070_Compensation_Group_Contract
@ghActions:run_on_executor3
Feature: Contract-triggered compensation group with a service discount product on a drop-ship sales order and its purchase order
## F2070: Compensation Group Contract
##
## The discount product is a service: the sales order's discount line is never shipped, and the purchase
## order's discount line is never received. Only the goods are received; the vendor invoice ("after
## delivery" for the goods) still carries the purchase discount.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And metasfresh has date and time 2026-07-01T09:00:00+02:00[Europe/Berlin]
    # the discount product is a service, so its tax category must not be restricted to items
    And taxCategory 'Normal' is updated to work with all productTypes

    # unique per run (auto-generated Value/Name): no category from an earlier run is reused
    And metasfresh contains M_Product_Categories:
      | Identifier    |
      | goodsCategory |

    # stocked goods: they are received into handling units
    And metasfresh contains M_Products:
      | Identifier      | OPT.M_Product_Category_ID.Identifier | ProductType | OPT.IsStocked | IsSold | IsPurchased |
      | apple           | goodsCategory                        | I           | true          | Y      | Y           |
      | pear            | goodsCategory                        | I           | true          | Y      | Y           |
      | discountProduct |                                      | S           | false         | Y      | Y           |

    And metasfresh contains M_PricingSystems
      | Identifier |
      | contractPS |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name | SOTrx | IsTaxIncluded | PricePrecision |
      | soPL       | contractPS                    | DE                        | EUR                 | soPL | true  | false         | 2              |
      | poPL       | contractPS                    | DE                        | EUR                 | poPL | false | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name  | ValidFrom  |
      | soPLV      | soPL                      | soPLV | 2026-01-01 |
      | poPLV      | poPL                      | poPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier     | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_so_apple    | soPLV                             | apple                   | 1000     | PCE               | Normal                        |
      | pp_so_pear     | soPLV                             | pear                    | 500      | PCE               | Normal                        |
      | pp_so_discount | soPLV                             | discountProduct         | 1        | PCE               | Normal                        |
      | pp_po_apple    | poPLV                             | apple                   | 1000     | PCE               | Normal                        |
      | pp_po_pear     | poPLV                             | pear                    | 500      | PCE               | Normal                        |
      | pp_po_discount | poPLV                             | discountProduct         | 1        | PCE               | Normal                        |

    And load C_DocType:
      | DocBaseType | DocSubType | C_DocType_ID      |
      | SOO         | SO         | docTypeSalesOrder |
    And load C_DocType:
      | DocBaseType | C_DocType_ID         |
      | POO         | docTypePurchaseOrder |

    And metasfresh contains C_Flatrate_Transition:
      | Identifier   | TermDuration | TermDurationUnit | OPT.TermOfNotice | OPT.TermOfNoticeUnit | OPT.ExtensionType | OPT.EnsurePeriodsForYears |
      | zeroDurTrans | 0            | day              | 0                | day                  | EO                | 2026,2027,2099            |

    And metasfresh contains C_BPartners:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | customerBP | Y              | contractPS                    |
    # the vendor invoices "after delivery": only what was received becomes invoiceable
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID.Identifier | PO_InvoiceRule |
      | vendorBP   | N          | Y        | contractPS                    | D              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier   | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault |
      | vendorBP_loc | vendorBP                 | Y               | Y               |

    And metasfresh contains C_CompensationGroup_Schema:
      | Identifier | Name            | OPT.IsAdditive |
      | soSchema   | Bonus Ware      | true           |
      | poSchema   | Bonus Lieferant | true           |
    And metasfresh contains C_CompensationGroup_SchemaLine:
      | Identifier   | C_CompensationGroup_Schema_ID.Identifier | M_Product_ID.Identifier | OPT.CompleteOrderDiscount | OPT.M_Product_Category_ID.Identifier |
      | soSchemaLine | soSchema                                 | discountProduct         | 3                         | goodsCategory                        |
      | poSchemaLine | poSchema                                 | discountProduct         | 3                         | goodsCategory                        |
    And metasfresh contains C_CompensationGroup_ContractSettings:
      | Identifier | Name        | C_CompensationGroup_Schema_ID.Identifier |
      | soSettings | SO settings | soSchema                                 |
      | poSettings | PO settings | poSchema                                 |
    And metasfresh contains C_CompensationGroup_ContractSettings_DocType:
      | C_CompensationGroup_ContractSettings_ID.Identifier | C_DocType_ID.Identifier |
      | soSettings                                         | docTypeSalesOrder       |
      | poSettings                                         | docTypePurchaseOrder    |
    And metasfresh contains C_Flatrate_Conditions:
      | Identifier   | Name          | Type_Conditions   | OPT.C_Flatrate_Transition_ID.Identifier | OPT.C_CompensationGroup_ContractSettings_ID.Identifier |
      | soConditions | SO conditions | CompensationGroup | zeroDurTrans                            | soSettings                                             |
      | poConditions | PO conditions | CompensationGroup | zeroDurTrans                            | poSettings                                             |
    # poTerm is open-ended: the auto-created drop-ship PO's DateOrdered is stamped from the real wall clock
    # (MOrder's defaulting constructor bypasses the simulated 2026-07-01 clock), so the purchase contract must
    # cover whatever today's date is when the test runs
    And metasfresh contains C_Flatrate_Terms:
      | Identifier | C_Flatrate_Conditions_ID.Identifier | Bill_BPartner_ID.Identifier | StartDate  | EndDate    | OPT.DocStatus | OPT.Processed |
      | soTerm     | soConditions                        | customerBP                  | 2026-06-15 | 2026-12-31 | DR            | false         |
      | poTerm     | poConditions                        | vendorBP                    | 2026-06-15 | 2099-12-31 | DR            | false         |
    And the C_Flatrate_Term identified by soTerm is completed
    And the C_Flatrate_Term identified by poTerm is completed

    And metasfresh contains M_Warehouse:
      | Identifier        | IsDropShipWarehouse |
      | dropshipWarehouse | Y                   |
    # the auto-created drop-ship PO is placed on the organisation's drop-ship warehouse (AD_OrgInfo.DropShip_Warehouse_ID)
    And load M_Warehouse:
      | M_Warehouse_ID       | Value             |
      | orgDropshipWarehouse | DropshipWarehouse |

  # ##############################################################################################
  # Drop-ship: SO completion creates the PO; only the goods are received, the service discount is
  # neither shipped nor received - and the vendor invoice carries the purchase discount
  # ##############################################################################################

  @from:cucumber
  @allure.label.epic:E0170_Contract_Management
  @allure.label.feature:F2070_Compensation_Group_Contract
  Scenario: A drop-ship purchase order's service discount is invoiced with the received goods without being received itself
    Given metasfresh contains C_Orders:
      | Identifier    | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | PreparationDate      | M_Warehouse_ID.Identifier |
      | orderDropship | true    | customerBP               | 2026-07-01  | 2026-06-30T22:00:00Z | dropshipWarehouse         |

    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered | C_BPartner_Vendor_ID.Identifier |
      | ol_apple   | orderDropship         | apple                   | 1          | vendorBP                        |
      | ol_pear    | orderDropship         | pear                    | 1          | vendorBP                        |

    And the order identified by orderDropship is completed

    # SO side: 3% of 1000 + 500 = 45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_soDiscount             | orderDropship         | discountProduct         | 1          | true                        | -45   | soTerm                            |
    And the order identified by orderDropship has 3 order lines
    # only the goods of the sales order get a shipment schedule: the service discount line is never shipped
    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | ss_apple   | ol_apple                  | N             |
      | ss_pear    | ol_pear                   | N             |
    And there is no M_ShipmentSchedule for C_OrderLine ol_soDiscount

    Then the order is created:
      | OPT.Identifier | Link_Order_ID.Identifier | IsSOTrx | DocBaseType | OPT.DocStatus | OPT.IsDropShip |
      | poDropship     | orderDropship            | false   | POO         | CO            | true           |
    And validate the created orders
      | C_Order_ID | C_BPartner_ID |
      | poDropship | vendorBP      |

    # PO side: its own contract ("Bonus Lieferant") on the same goods base: -45
    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.IsGroupCompensationLine | price | OPT.C_Flatrate_Term_ID.Identifier |
      | ol_poApple                | poDropship            | apple                   | 1          | false                       |       |                                   |
      | ol_poPear                 | poDropship            | pear                    | 1          | false                       |       |                                   |
      | ol_poDiscount             | poDropship            | discountProduct         | 1          | true                        | -45   | poTerm                            |
    And the order identified by poDropship has 3 order lines

    And after not more than 60s locate up2date invoice candidates by order line:
      | C_OrderLine_ID | C_Invoice_Candidate_ID |
      | ol_poApple     | ic_poApple             |
      | ol_poPear      | ic_poPear              |
      | ol_poDiscount  | ic_poDiscount          |

    # the goods are invoiced "after delivery" (the vendor's rule), the service discount "immediately" by
    # its own; with nothing received yet, there is nothing to invoice
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | InvoiceRule | InvoiceRule_Override | QtyToInvoice | NetAmtToInvoice |
      | ic_poApple                        | D           | null                 | 0            | 0               |
      | ic_poPear                         | D           | null                 | 0            | 0               |
      | ic_poDiscount                     | D           | I                    | 0            | 0               |

    # the past DateToInvoice_Override clears the date gate only: the auto-created PO's DateOrdered is stamped from
    # the real wall clock (MOrder's defaulting constructor bypasses the simulated 2026-07-01 clock), so its
    # "immediate" discount candidate would otherwise compute a DateToInvoice that is "in the future" relative to
    # the simulated today. It does not change what is invoiceable: that still follows the received quantities.
    And update invoice candidates
      | C_Invoice_Candidate_ID | OPT.DateToInvoice_Override |
      | ic_poApple             | 2026-07-01                 |
      | ic_poPear              | 2026-07-01                 |
      | ic_poDiscount          | 2026-07-01                 |

    # ##########################################################################################
    # Receive the goods only - the discount line is not received
    # ##########################################################################################
    And after not more than 60s, M_ReceiptSchedule are found:
      | M_ReceiptSchedule_ID.Identifier | C_Order_ID.Identifier | C_OrderLine_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | M_Warehouse_ID.Identifier |
      | rs_apple                        | poDropship            | ol_poApple                | vendorBP                 | vendorBP_loc                      | apple                   | 1          | orgDropshipWarehouse      |
      | rs_pear                         | poDropship            | ol_poPear                 | vendorBP                 | vendorBP_loc                      | pear                    | 1          | orgDropshipWarehouse      |
    And create M_HU_LUTU_Configuration for M_ReceiptSchedule and generate M_HUs
      | M_HU_LUTU_Configuration_ID.Identifier | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | IsInfiniteQtyLU | QtyLU | IsInfiniteQtyTU | QtyTU | IsInfiniteQtyCU | QtyCUsPerTU | M_HU_PI_Item_Product_ID.Identifier |
      | luTuConfig_apple                      | hu_apple           | rs_apple                        | N               | 0     | N               | 1     | N               | 1           | 101                                |
      | luTuConfig_pear                       | hu_pear            | rs_pear                         | N               | 0     | N               | 1     | N               | 1           | 101                                |
    And create material receipt
      | M_HU_ID.Identifier | M_ReceiptSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | hu_apple           | rs_apple                        | receipt_apple         |
      | hu_pear            | rs_pear                         | receipt_pear          |

    And after not more than 60s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID |
      | ic_poApple             |
      | ic_poPear              |
      | ic_poDiscount          |

    # the purchase discount is computed on the received goods: 3% of 1000 + 500
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | NetAmtToInvoice |
      | ic_poApple                        | 1000            |
      | ic_poPear                         | 500             |
      | ic_poDiscount                     | -45             |

    And process invoice candidates together and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID.Identifier |
      | ic_poApple                        |
      | ic_poPear                         |
      | ic_poDiscount                     |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | vendorInvoice           | ic_poApple                        |

    # the vendor invoice carries the purchase discount, although its order line was never received
    And validate invoice lines for vendorInvoice:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | LineNetAmt |
      | vendorInvoice_apple         | apple                   | 1           | 1000       |
      | vendorInvoice_pear          | pear                    | 1           | 500        |
      | vendorInvoice_discount      | discountProduct         | 1           | -45        |
