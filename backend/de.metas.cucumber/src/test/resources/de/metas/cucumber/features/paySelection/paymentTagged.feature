@from:cucumber
@PaySelection
@Proforma
@Payment
@Iteration2
@MF_29368
@ghActions:run_on_executor4
Feature: Payment generated from a proforma pay-selection line is auto-tagged
  # https://github.com/metasfresh/me03/issues/29368
  # TC6: Completing a pay-selection whose source is a purchase proforma creates a
  # payment tagged with Proforma_Invoice_ID and IsPrepayment=Y.
  #
  # Current state (RED): no code sets Proforma_Invoice_ID on the C_Payment yet.
  # The column exists physically (Task 13) but the auto-tag logic (Task 17) has not
  # been wired.  This scenario is intentionally RED until Task 17+18 land.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2026-04-24T10:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | pl_purchase | ps                 | DE           | EUR           | false |
    And metasfresh contains M_PriceList_Versions
      | Identifier   | M_PriceList_ID |
      | plv_purchase | pl_purchase    |
    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd  | C_UOM_ID |
      | plv_purchase           | product      | 20596.32  | PCE      |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID |
      | vendor     | Y        | N          | ps                 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_loc | vendor        | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier      | C_Currency_ID |
      | org_EUR_account | EUR           |


  @from:cucumber
  @PaySelection
  @Proforma
  @Payment
  @Iteration2
  @MF_29368
  Scenario: TC6 - Payment created from a proforma pay-selection line is tagged with Proforma_Invoice_ID and IsPrepayment=Y
    # https://github.com/metasfresh/me03/issues/29368
    # Expected RED until Task 17 (auto-tag logic) is wired.

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name       | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apf_inv    | vendor        | Proforma-Rechnung (Lieferant) | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | apf_invL1  | apf_inv      | product      | 1 PCE       |
    And the invoice identified by apf_inv is completed

    And metasfresh contains Pay Selection
      | Identifier | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySel     | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySel, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | N       |

    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | apf_inv      | 20596.32 | -            |

    And the pay selection identified by paySel is completed
    Then "Create Payments" is invoked for pay selection paySel

    And the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  | C_Payment_ID |
      | apf_inv      | 20596.32 | payment      |

    Then validate payments
      | C_Payment_ID.Identifier | IsPrepayment | Proforma_Invoice_ID |
      | payment                 | Y            | apf_inv             |
