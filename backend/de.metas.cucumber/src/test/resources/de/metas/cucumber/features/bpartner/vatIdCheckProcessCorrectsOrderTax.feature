@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: The VAT-ID check process corrects order-line tax on a status change
  Where the check process finds that a Business Partner's VAT-ID status actually changed, it refreshes
  C_OrderLine.C_Tax_ID on that partner's orders that are not yet completed. A completed order is never
  touched, even after its partner's VAT-ID is corrected — rewriting a finished document's tax would
  rewrite history.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-08-13T10:00:00+02:00[Europe/Berlin]

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # A dedicated C_TaxCategory keeps these two C_Tax rows isolated from every other feature's tax setup.
    And metasfresh contains C_TaxCategory
      | Identifier            |
      | taxCategoryVATCorrect |
    And metasfresh contains C_Tax
      | Identifier           | C_TaxCategory_ID      | Name                          | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | RequiresTaxCertificate |
      | taxCorrectCert0      | taxCategoryVATCorrect | vatcorrect_supply_0_with_cert | 0    | DE                       | FR                        | true                   |
      | taxCorrectStandard19 | taxCategoryVATCorrect | vatcorrect_supply_19_no_cert  | 19   | DE                       | FR                        | false                  |

    And metasfresh contains M_Products:
      | Identifier |
      | product    |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps_1       |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | C_Country_ID | C_Currency_ID | SOTrx | IsTaxIncluded |
      | pl_FR      | ps_1                          | FR           | EUR           | true  | false         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_FR     | pl_FR                     | 2026-01-01 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID      |
      | pp_FR      | plv_FR                            | product                 | 100.0    | PCE               | taxCategoryVATCorrect |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_france          | FR          |

  @Id:S31060_TC9
  Scenario: Correcting an invalid VAT-ID to a valid one refreshes an open order's tax and leaves completed and closed orders untouched
    Given no VATaxID_CheckLog records exist for VATaxID 'FR83404833048'
    And no VATaxID_CheckLog records exist for VATaxID 'FR92918273645'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID       | VATaxIDStatus |
      | FR83404833048 | Invalid       |

    And metasfresh contains C_BPartners without locations:
      | Identifier | Value              | Name                    | M_PricingSystem_ID.Identifier | IsCustomer | VATaxID       |
      | bp_correct | vatcorrect_partner | FrenchCustomer_VATFixed | ps_1                          | Y          | FR83404833048 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | bpl_correct | 0123456789041 | bp_correct               | Y               | Y               | location_france              |

    # The after-commit trigger already checked the freshly created partner: proves the scenario starts
    # from a genuinely Invalid status, not from a manually poked column.
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | HasTaxCertificate |
      | bp_correct    | Invalid       | false              |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderOpen      | true    | bp_correct               | 2026-08-13  | bpl_correct                           | warehouseStd                  |
      | orderCompleted | true    | bp_correct               | 2026-08-13  | bpl_correct                           | warehouseStd                  |
      | orderClosed    | true    | bp_correct               | 2026-08-13  | bpl_correct                           | warehouseStd                  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | lineOpen      | orderOpen             | product                 | 1          |
      | lineCompleted | orderCompleted        | product                 | 1          |
      | lineClosed    | orderClosed           | product                 | 1          |

    # All three lines already reflect the Invalid status at creation time (OrderLineBL#setTax runs on every save).
    And validate C_OrderLine:
      | Identifier    | OPT.C_Tax_ID.Identifier |
      | lineOpen      | taxCorrectStandard19    |
      | lineCompleted | taxCorrectStandard19    |
      | lineClosed    | taxCorrectStandard19    |

    When the order identified by orderCompleted is completed
    And the order identified by orderClosed is completed
    And the order identified by orderClosed is closed

    # Correct the VAT-ID while the online check is disabled, so the after-commit trigger does not consume
    # the status change itself: the check process below must be the one that discovers it.
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |
    And update C_BPartner:
      | Identifier | VATaxID       |
      | bp_correct | FR92918273645 |
    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus |
      | bp_correct    | Invalid       |

    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID       | VATaxIDStatus |
      | FR92918273645 | Valid         |

    When the C_BPartner_VATaxID_Check process is run for selection with MaxChecksPerRun '':
      | C_BPartner_ID |
      | bp_correct    |

    Then validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | HasTaxCertificate |
      | bp_correct    | Valid         | true              |

    And validate C_OrderLine:
      | Identifier    | OPT.C_Tax_ID.Identifier |
      | lineOpen      | taxCorrectCert0         |
      | lineCompleted | taxCorrectStandard19    |
      | lineClosed    | taxCorrectStandard19    |
