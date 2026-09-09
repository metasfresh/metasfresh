@from:cucumber
@ghActions:run_on_executor2
Feature: Free text above an order line
  A sales order line can carry a free text.
  It prints as a standalone block directly above that line's own article row on the order confirmation.
  No text means no block: an unset value and a whitespace-only value both print nothing.
  A long text wraps onto as many lines as it needs instead of being clipped.

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
    # keep the archived PDF in AD_Archive.BinaryData instead of on the app server's file system
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    # one product per order line: the article rows are told apart by their product name in the PDF
    And metasfresh contains M_Products:
      | Identifier | Name      |
      | productA   | AlphaItem |
      | productB   | BetaItem  |
      | productC   | GammaItem |
      | productD   | DeltaItem |
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
      | ppD        | plv                    | productD     | 10.0     | PCE      |
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

  @Id:S27486_10
  Scenario: The free text of an order line prints above that line on the order confirmation
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | M_Warehouse_ID | M_PricingSystem_ID |
      | order      | true    | customer      | 2025-04-01  | wh             | ps                 |
    # WHITESPACE_ONLY is a sentinel: gherkin trims an all-spaces cell to empty, so the value
    # cannot be written literally -- see C_OrderLine_StepDef
    And metasfresh contains C_OrderLines:
      | Identifier        | C_Order_ID | M_Product_ID | QtyEntered | DescriptionAboveLine   |
      | lineWithText      | order      | productA     | 1          | Bitte gekuehlt liefern |
      | lineWithBlankText | order      | productB     | 1          | WHITESPACE_ONLY        |
      | lineWithLongText  | order      | productC     | 1          | Diese Position wird in mehreren Teillieferungen versandt. Bitte die Ware bei Anlieferung sofort auf Vollstaendigkeit pruefen und Abweichungen innerhalb von drei Werktagen melden |
    # this table has no DescriptionAboveLine column at all, so the column stays NULL on that line
    And metasfresh contains C_OrderLines:
      | Identifier      | C_Order_ID | M_Product_ID | QtyEntered |
      | lineWithoutText | order      | productD     | 1          |
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |

    # the text is printed, and it is the line immediately above its own article row
    Then the PDF archived for the record identified by "order" contains text "Bitte gekuehlt liefern"
    And in the PDF archived for the record identified by "order", exactly 0 lines appear between text "Bitte gekuehlt liefern" and text "AlphaItem"

    # A whitespace-only value prints nothing. Two article rows are always separated by exactly one
    # line -- the earlier position's own ProductDescription -- so 1 is the untouched baseline here,
    # and a printed block would make it 2. The unset line below is measured the very same way.
    And in the PDF archived for the record identified by "order", exactly 1 lines appear between text "AlphaItem" and text "BetaItem"

    # the long text wraps onto further lines instead of being clipped, and its end is still printed
    And in the PDF archived for the record identified by "order", at least 1 lines appear between text "Diese Position wird" and text "GammaItem"
    And the PDF archived for the record identified by "order" contains text "drei Werktagen melden"

    # an unset value prints nothing either -- same baseline separation as above
    And in the PDF archived for the record identified by "order", exactly 1 lines appear between text "GammaItem" and text "DeltaItem"
