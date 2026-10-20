@report
@from:cucumber
@allure.label.feature:F71030_Cucumber_Integration_Testing
Feature: Asserting on the rendered PDF of an archived document
## F71030: Cucumber Integration Testing
  The steps in AD_Archive_StepDef read the PDF metasfresh actually rendered and archived for a record.
  This feature is their self-test: it prints an ordinary sales order and exercises each of them,
  so the steps cannot silently rot, and so a reader has one worked example of each.
  It asserts nothing about any particular feature's content - only about the steps themselves.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE
    And set sys config boolean value false for sys config de.metas.payment.esr.Enabled
    And set sys config boolean value false for sys config de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant
    And metasfresh has date and time 2025-04-01T13:30:13+01:00[Europe/Berlin]
    # another feature in this suite flips this flag; pin it so this scenario cannot depend on suite order.
    # The steps read the archive through IArchiveBL, which works for both storages.
    And update AD_Client
      | Identifier | StoreArchiveOnFileSystem |
      | 1000000    | false                    |
    And metasfresh contains M_Warehouse:
      | M_Warehouse_ID |
      | wh             |
    And metasfresh contains M_Products:
      | Identifier | Value    | Name      |
      | productA   | ALPHA-NR | AlphaItem |
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
    When the order identified by order is completed
    And The jasper process is run
      | Value            | Record_ID |
      | Auftrag (Jasper) | order     |

    # the document was archived at all
    Then an AD_Archive exists for the record identified by "order"

    # presence and absence of text
    And the PDF archived for the record identified by "order" contains text "AlphaItem"
    And the PDF archived for the record identified by "order" does not contain text "Sortimentsware"
