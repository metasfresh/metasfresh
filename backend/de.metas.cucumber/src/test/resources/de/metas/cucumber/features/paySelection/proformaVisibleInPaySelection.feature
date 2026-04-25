@from:cucumber
@PaySelection
@Proforma
@Iteration2
@MF_29368
@ghActions:run_on_executor4
Feature: Proforma invoice appears in Pay Selection alongside regular financial invoices
  # https://github.com/metasfresh/me03/issues/29368
  # TC5: Purchase Proforma (APF) and regular purchase invoice (API) for the same vendor
  # must both appear as lines in Pay Selection.
  #
  # Current state (RED): the C_Invoice_v view filters isfinancial='Y', which excludes APF invoices.
  # PaySelectionUpdater therefore computes OpenAmt=NULL → coalesced to 0 → candidate dropped.
  # This scenario is intentionally RED until the gate fix (Task 9) lands.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has date and time 2026-04-24T10:00:00+02:00[Europe/Berlin]

    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier  | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_purchase | ps                 | DE           | EUR           | false | Y             |
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
      | Identifier | IsVendor | IsCustomer | M_PricingSystem_ID | PaymentRulePO |
      | vendor     | Y        | N          | ps                 | P             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | C_BPartner_ID | IsShipToDefault | IsBillToDefault |
      | vendor_loc     | vendor        | Y               | Y               |

    And metasfresh contains organization bank accounts
      | Identifier         | C_Currency_ID |
      | org_EUR_account    | EUR           |

    And metasfresh contains C_BP_BankAccount
      | Identifier          | C_BPartner_ID | C_Currency_ID |
      | vendor_bank_account | vendor        | EUR           |


  @from:cucumber
  @PaySelection
  @Proforma
  @Iteration2
  @MF_29368
  Scenario: TC5 - Purchase Proforma and regular purchase invoice both become pay-selection lines for the same vendor
    # https://github.com/metasfresh/me03/issues/29368
    # Expected RED until Task 9 (gate fix: include APF in C_Invoice_v / invoiceopen()).

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | C_DocTypeTarget_ID.Name              | DateInvoiced | IsSOTrx | C_Currency_ID |
      | apf_inv    | vendor        | Proforma-Rechnung (Lieferant)        | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | apf_invL1  | apf_inv      | product      | 1 PCE       |
    And the invoice identified by apf_inv is completed

    And metasfresh contains C_Invoice:
      | Identifier | C_BPartner_ID | DateInvoiced | IsSOTrx | C_Currency_ID |
      | api_inv    | vendor        | 2026-04-24   | false   | EUR           |
    And metasfresh contains C_InvoiceLines
      | Identifier | C_Invoice_ID | M_Product_ID | QtyInvoiced |
      | api_invL1  | api_inv      | product      | 1 PCE       |
    And the invoice identified by api_inv is completed

    And metasfresh contains Pay Selection
      | Identifier | C_BP_BankAccount_ID | PaySelectionTrxType | PayDate    |
      | paySel     | org_EUR_account     | CT                  | 2026-04-24 |
    And "Create from..." is invoked for pay selection paySel, using following parameters:
      | MatchRequirement | C_BPartner_ID | OnlyDue |
      | OUT              | vendor        | N       |

    Then the Pay selection identified by paySel has exactly the following lines
      | C_Invoice_ID | OpenAmt  |
      | apf_inv      | 20596.32 |
      | api_inv      | 20596.32 |
