@from:cucumber
@allure.label.feature:F71030_Cucumber_Integration_Testing
@ghActions:run_on_executor2
Feature: Asserting on the rendered PDF of an archived document
## F71030: Cucumber Integration Testing
  The steps in AD_Archive_StepDef read the PDF metasfresh actually rendered and archived for a record.
  This feature is their self-test: it prints an ordinary sales order and exercises every one of them,
  so the steps cannot silently rot, and so a reader has one worked example of each.
  It asserts nothing about any particular feature's content - only about the steps themselves.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    # render the real jasper report; the mock report service would archive a placeholder PDF instead
    And set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    # another feature in this suite flips this flag; pin it so this scenario cannot depend on suite order.
    # It must stay 'false': the steps read the archive back out of the database.
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    # Value differs from Name on purpose: the article row shows the Name, the line below it the Value,
    # which gives the positional steps two distinct anchors per position. Keep the Names short - the
    # product name stretches with overflow, and a wrapping Name would change the line counts below.
    And metasfresh contains M_Products:
      | Identifier | Value    | Name      |
      | productA   | ALPHA-NR | AlphaItem |
      | productB   | BETA-NR  | BetaItem  |
      | productC   | GAMMA-NR | GammaItem |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl         | ps                 | CH           | CHF           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | plv        | pl             |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | ppA        | plv                    | productA     | 10.0     | PCE      |
      | ppB        | plv                    | productB     | 10.0     | PCE      |
      | ppC        | plv                    | productC     | 10.0     | PCE      |
    # dev-note: pin the payment term, so the order does not depend on the branch's default
    And metasfresh contains C_PaymentTerm
      | Identifier          |
      | customerPaymentTerm |
    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | C_PaymentTerm_ID    |
      | customer   | N        | Y          | ps                 | customerPaymentTerm |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | C_BPartner_ID | C_Country_ID | IsShipToDefault | IsBillToDefault |
      | customerLocation | customer      | CH           | Y               | Y               |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Name      | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | de_ch_tax  | Normal                        | de_ch_tax | 2021-04-02 | 2.5  | DE                       | CH                        |
      | ch_ch_tax  | Normal                        | ch_ch_tax | 2021-04-02 | 2.5  | CH                       | CH                        |

  @Id:S71030_10
  Scenario: Every archived-PDF assertion step, exercised on an order confirmation
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID | M_Product_ID | QtyEntered |
      | line10     | order      | productA     | 1          |
      | line20     | order      | productB     | 1          |
      | line30     | order      | productC     | 1          |
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |

    # the document was archived at all
    Then an AD_Archive exists for the record identified by "order"

    # presence and absence of text
    And the PDF archived for the record identified by "order" contains text "AlphaItem"
    And the PDF archived for the record identified by "order" does not contain text "Sortimentsware"

    # WHERE the text sits. Each position prints its article row and then its product number on the next
    # line, so position 20's article row follows position 10's product number with nothing in between.
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"

    # ... and from position 10's name to position 30's name there are at least the three lines of
    # positions 10 and 20 that sit between them
    And in the PDF archived for the record identified by "order", at least 2 lines appear between text "AlphaItem" and text "GammaItem"

    # geometry: one position occupies the same vertical space as the next, since all three are alike
    And in the PDF archived for the record identified by "order", the vertical distance from text "AlphaItem" to text "BetaItem" equals the distance from text "BetaItem" to text "GammaItem"

    # nothing is printed on top of anything else, anywhere in the document
    And the PDF archived for the record identified by "order" has no overlapping text
