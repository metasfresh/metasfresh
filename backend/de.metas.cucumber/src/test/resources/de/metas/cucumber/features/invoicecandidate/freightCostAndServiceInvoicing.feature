@from:cucumber
@allure.label.epic:E0340_Invoicing
@allure.label.feature:F00703_Invoice_Rule
@ghActions:run_on_executor6
Feature: Invoicing of non-item lines on a sales order with invoice rule "after delivery"
## F00703: Invoice Rule
##
## A sales order may carry non-item lines beside the goods. They are treated differently:
## - freight cost (product type Frachtkosten) keeps the order's invoice rule and is invoiced with the first delivery
## - a service (product type Dienstleistung) is overridden to "immediate" and is invoiced without any delivery

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2021-04-17T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    When load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouse                 | StdWarehouse |
    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |
    And metasfresh contains C_BPartners:
      | Identifier  | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endcustomer | N            | Y              | pricingSystem                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | GLN           | C_BPartner_ID.Identifier | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | endcustomerLocation | 0123456789011 | endcustomer              | Y                   | Y                   |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | priceList  | pricingSystem                 | DE                        | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier       | M_PriceList_ID.Identifier | ValidFrom  |
      | priceListVersion | priceList                 | 2021-04-01 |

  @Id:S31036_10
  Scenario: Freight cost is not invoiced before the goods are delivered and lands on the invoice of the first delivery
    Given metasfresh contains M_Products:
      | Identifier  | ProductType | IsStocked |
      | goods       | I           | Y         |
      | freightCost | F           | N         |
    And metasfresh contains M_ProductPrices
      | Identifier       | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | goodsPrice       | priceListVersion                  | goods                   | 10.0     | PCE               | Normal                        |
      | freightCostPrice | priceListVersion                  | freightCost             | 25.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order      | true    | endcustomer              | 2021-04-17  | D           |
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | goodsLine       | order                 | goods                   | 100        |
      | freightCostLine | order                 | freightCost             | 1          |

    When the order identified by order is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID  | QtyToInvoice |
      | goodsCandidate         | goodsLine       | 0            |
      | freightCostCandidate   | freightCostLine | 0            |
    # A non-item line is never delivered — only product type Artikel gets a shipment schedule — so its
    # quantity to invoice is the ORDERED quantity from the start. The freight cost is nevertheless held
    # back at zero while the goods of the same order are not invoiceable yet; a service is not (see below).
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsFreightCost | InvoiceRule | InvoiceRule_Override | QtyOrdered | QtyDelivered | QtyToInvoice |
      | goodsCandidate         | false         | D           | null                 | 100        | 0            | 0            |
      | freightCostCandidate   | true          | D           | null                 | 1          | 0            | 0            |
    And invoice candidates are not billable
      | C_Invoice_Candidate_ID.Identifier |
      | freightCostCandidate              |
      | goodsCandidate                    |

    When after not more than 60s, M_ShipmentSchedules are found:
      | Identifier    | C_OrderLine_ID | IsToRecompute |
      | goodsSchedule | goodsLine      | N             |
    # the first delivery covers 40 of the 100 ordered goods
    And 'generate shipments' process is invoked with QuantityType=D, IsCompleteShipments=true and IsShipToday=false
      | M_ShipmentSchedule_ID | QtyToDeliver_Override_For_M_ShipmentSchedule_ID |
      | goodsSchedule         | 40                                              |
    Then after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID | M_InOut_ID    | DocStatus |
      | goodsSchedule         | firstShipment | CO        |
    And validate the created shipment lines
      | M_InOut_ID    | M_Product_ID | C_OrderLine_ID | movementqty | processed |
      | firstShipment | goods        | goodsLine      | 40          | true      |
    # the goods became invoiceable, so the freight cost is released and takes over their delivery date
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | QtyDelivered | QtyToInvoice | DeliveryDate |
      | goodsCandidate         | 40           | 40           | 2021-04-17   |
      | freightCostCandidate   | 0            | 1            | 2021-04-17   |

    When process invoice candidates together and wait 600s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | goodsCandidate         |
      | freightCostCandidate   |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | goodsCandidate                    | firstInvoice            | CO            |
      | freightCostCandidate              | firstInvoice            | CO            |
    # goods and freight cost end up on one and the same invoice
    And validate created invoice lines
      | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | firstInvoice | goods        | 40          |
      | firstInvoice | freightCost  | 1           |

  @Id:S31036_20
  Scenario: A service line is invoiced immediately, without waiting for the goods to be delivered
    Given metasfresh contains M_Products:
      | Identifier | ProductType | IsStocked |
      | goods      | I           | Y         |
      | seminar    | S           | N         |
    And metasfresh contains M_ProductPrices
      | Identifier   | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | goodsPrice   | priceListVersion                  | goods                   | 10.0     | PCE               | Normal                        |
      | seminarPrice | priceListVersion                  | seminar                 | 80.0     | PCE               | Normal                        |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | InvoiceRule |
      | order      | true    | endcustomer              | 2021-04-17  | D           |
    And metasfresh contains C_OrderLines:
      | Identifier  | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | goodsLine   | order                 | goods                   | 100        |
      | seminarLine | order                 | seminar                 | 1          |

    When the order identified by order is completed

    Then after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID | C_OrderLine_ID | QtyToInvoice |
      | goodsCandidate         | goodsLine      | 0            |
      | seminarCandidate       | seminarLine    | 1            |
    # the service candidate is overridden to "immediate" and — not being held back like a freight cost —
    # carries its full ordered quantity while the goods of the same order are still undelivered
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID | IsFreightCost | InvoiceRule | InvoiceRule_Override | QtyOrdered | QtyDelivered | QtyToInvoice |
      | goodsCandidate         | false         | D           | null                 | 100        | 0            | 0            |
      | seminarCandidate       | false         | D           | I                    | 1          | 0            | 1            |
    And invoice candidates are not billable
      | C_Invoice_Candidate_ID.Identifier |
      | goodsCandidate                    |

    When process invoice candidates and wait 600s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | seminarCandidate       |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier | OPT.DocStatus |
      | seminarCandidate                  | seminarInvoice          | CO            |
    And validate created invoice lines
      | C_Invoice_ID   | M_Product_ID | QtyInvoiced |
      | seminarInvoice | seminar      | 1           |
