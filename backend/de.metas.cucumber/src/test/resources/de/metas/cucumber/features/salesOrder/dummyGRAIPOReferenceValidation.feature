@from:cucumber
@allure.label.epic:E0105_Picking
@allure.label.feature:F00230_MobileUI_Picking
@ghActions:run_on_executor6
Feature: Dummy-GRAI prerequisite — validate the sales order PO reference at the source
  # For a customer in GRAIRequired = YesWithDummyGRAIs ('D') mode, dummy GRAIs are generated at picking
  # completion using the sales order PO reference as the serial prefix (max 10 characters). This validates
  # that prerequisite at sales-order completion, so the back-office actor who can fix the PO reference gets
  # the feedback then — instead of failing only at picking completion with a raw technical message. The PO
  # reference stays editable while the order is a draft (so cloning is unaffected). A non-GRAI customer and a
  # valid PO reference are unaffected.

  Background:
    Given infrastructure and metasfresh are running
    And metasfresh has date and time 2026-06-24T12:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value false for sys config AUTO_SHIP_AND_INVOICE

    And metasfresh contains M_Products:
      | Identifier | Name        | Value       |
      | product    | graiProduct | graiProduct |
    And metasfresh contains M_PricingSystems
      | Identifier | Name           | Value          |
      | ps         | graiPricingSys | graiPricingSys |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name   | SOTrx | IsTaxIncluded | PricePrecision |
      | pl         | ps                            | DE                        | EUR                 | graiPL | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name    | ValidFrom  |
      | plv        | pl                        | graiPLV | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp         | plv                               | product                 | 10.0     | PCE               | Normal                        |

  Scenario: A too-long PO reference is rejected when completing a sales order for a dummy-GRAI customer
    Given metasfresh contains C_BPartners:
      | Identifier        | Name              | OPT.IsCustomer | M_PricingSystem_ID.Identifier | GRAIRequired |
      | dummyGRAICustomer | dummyGRAICustomer | Y              | ps                            | D            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_Warehouse_ID.Identifier | POReference    |
      | order      | true    | dummyGRAICustomer        | 2026-06-24  | 540008                        | TOOLONG-PO-REF |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    Then completing the order identified by order is rejected with error code GRAI_POREFERENCE_TOO_LONG

  Scenario: Cloning a dummy-GRAI sales order is allowed — the clone is a draft whose PO reference is not yet set (the prerequisite is enforced only at completion)
    Given metasfresh contains C_BPartners:
      | Identifier        | Name              | OPT.IsCustomer | M_PricingSystem_ID.Identifier | GRAIRequired |
      | dummyGRAICustomer | dummyGRAICustomer | Y              | ps                            | D            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_Warehouse_ID.Identifier | POReference |
      | order      | true    | dummyGRAICustomer        | 2026-06-24  | 540008                        | PO-123456   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    # Cloning does not copy POReference, so the clone has none — exactly the "missing PO reference" state that
    # must NOT be rejected at save (the picker/back-office fills it before completion).
    When C_Order is cloned
      | C_Order_ID.Identifier | ClonedOrder.C_Order_ID.Identifier |
      | order                 | clonedOrder                       |
    Then validate the created orders
      | C_Order_ID.Identifier | C_BPartner_ID.Identifier | DocStatus |
      | clonedOrder           | dummyGRAICustomer        | DR        |

  Scenario: A valid PO reference (max 10 chars) for a dummy-GRAI customer is accepted and the order completes
    Given metasfresh contains C_BPartners:
      | Identifier        | Name              | OPT.IsCustomer | M_PricingSystem_ID.Identifier | GRAIRequired |
      | dummyGRAICustomer | dummyGRAICustomer | Y              | ps                            | D            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_Warehouse_ID.Identifier | POReference |
      | order      | true    | dummyGRAICustomer        | 2026-06-24  | 540008                        | PO-123456   |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    Then the order identified by order is completed

  Scenario: A too-long PO reference for a non-GRAI customer is accepted (the validation does not apply)
    Given metasfresh contains C_BPartners:
      | Identifier    | Name          | OPT.IsCustomer | M_PricingSystem_ID.Identifier | GRAIRequired |
      | plainCustomer | plainCustomer | Y              | ps                            | N            |
    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.M_Warehouse_ID.Identifier | POReference    |
      | order      | true    | plainCustomer            | 2026-06-24  | 540008                        | TOOLONG-PO-REF |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 10         |
    Then the order identified by order is completed
