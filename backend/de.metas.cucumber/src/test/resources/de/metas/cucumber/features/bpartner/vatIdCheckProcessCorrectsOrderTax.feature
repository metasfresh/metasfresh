@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: A VAT-ID check corrects order-line tax on a status change
  Where a VAT-ID check writes a status that differs from the one the record already held, it refreshes
  C_OrderLine.C_Tax_ID on that partner's orders that are not yet completed. A completed order is never
  touched, even after its partner's VAT-ID is corrected — rewriting a finished document's tax would
  rewrite history.

  Both ways into a check are covered, because both converge on the same service: the
  C_BPartner_VATaxID_Check process, and the check a save schedules automatically. The save-triggered one
  is the everyday case — a user correcting a wrong VAT-ID on a partner that has an open order — and it
  must not need the process to be run afterwards to put the tax right.

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

    # No Value/Name given on purpose: a fixed literal here would make C_BPartner_StepDef upsert-by-Value
    # reuse the SAME partner row across local reruns (the local DB is never reset between runs). A reused
    # partner keeps every never-completed "orderOpen" order from earlier runs, and when the check below
    # flips its status, refreshOrderLinesTaxForBPartner tries to refresh ALL of its open orders (not just
    # this scenario's) -- including those old orders' tax category, which a later run's C_Tax upsert-by-Name
    # has since repointed elsewhere, so no matching C_Tax exists any more: TaxNotFoundException, which rolls
    # back the whole check transaction and silently reverts the status. Omitting Value/Name (like every
    # other fixture in this Background) makes bp_correct scenario-unique, so it is never reused.
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | VATaxID       |
      | bp_correct | ps_1                          | Y          | FR83404833048 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier  | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | bpl_correct | 0123456789041 | bp_correct               | Y               | Y               | location_france              |

    # The trigger schedules that check in a work package, so it lands some time AFTER the save step
    # returned. Wait for it instead of racing it -- the assertion below reads the database once, without
    # retrying. Same wait, same reason, as the sibling scenarios in vatIdOnlineCheck.feature.
    Then the VAT-ID online checker was called for VATaxID 'FR83404833048'

    # The after-commit trigger already checked the freshly created partner: proves the scenario starts
    # from a genuinely Invalid status, not from a manually poked column.
    And validate C_BPartner VAT-ID status:
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

  @Id:S31060_TC10
  Scenario: Correcting the VAT-ID and saving refreshes an open order's tax without running the check process
    # The everyday sequence the feature owner hit, and the one TC9 above cannot show: nobody runs the
    # C_BPartner_VATaxID_Check process after fixing a VAT-ID -- they just save. The check the save
    # schedules is therefore the one that has to put the open order's tax right, on its own.
    #
    # This scenario deliberately never runs the process. It also cannot be replaced by TC9 with the
    # process step deleted: TC9 suppresses the save-triggered check (IsVIESCheckEnabled=false) around its
    # correction precisely so the process is left something to discover, which is the opposite of what is
    # under test here.
    Given no VATaxID_CheckLog records exist for VATaxID 'FR96552100554'
    And no VATaxID_CheckLog records exist for VATaxID 'FR44732829320'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID       | VATaxIDStatus |
      | FR96552100554 | Invalid       |

    # No Value/Name, for the reason spelled out in TC9 above: a partner reused across local reruns would
    # drag earlier runs' still-open orders into refreshOrderLinesTaxForBPartner.
    And metasfresh contains C_BPartners without locations:
      | Identifier | M_PricingSystem_ID.Identifier | IsCustomer | VATaxID       |
      | bp_save    | ps_1                          | Y          | FR96552100554 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | bpl_save   | 0123456789058 | bp_save                  | Y               | Y               | location_france              |

    # Wait for the save-triggered check instead of racing it -- same wait, same reason, as TC9.
    Then the VAT-ID online checker was called for VATaxID 'FR96552100554'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | HasTaxCertificate |
      | bp_save       | Invalid       | false             |

    And metasfresh contains C_Orders:
      | Identifier     | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | orderOpen      | true    | bp_save                  | 2026-08-13  | bpl_save                              | warehouseStd                  |
      | orderCompleted | true    | bp_save                  | 2026-08-13  | bpl_save                              | warehouseStd                  |
      | orderClosed    | true    | bp_save                  | 2026-08-13  | bpl_save                              | warehouseStd                  |
    And metasfresh contains C_OrderLines:
      | Identifier    | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | lineOpen      | orderOpen             | product                 | 1          |
      | lineCompleted | orderCompleted        | product                 | 1          |
      | lineClosed    | orderClosed           | product                 | 1          |
    And validate C_OrderLine:
      | Identifier    | OPT.C_Tax_ID.Identifier |
      | lineOpen      | taxCorrectStandard19    |
      | lineCompleted | taxCorrectStandard19    |
      | lineClosed    | taxCorrectStandard19    |

    When the order identified by orderCompleted is completed
    And the order identified by orderClosed is completed
    And the order identified by orderClosed is closed

    # The correction, with the online check left ON: the save schedules the check, and that check is the
    # only thing that runs from here on.
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID       | VATaxIDStatus |
      | FR44732829320 | Valid         |
    And update C_BPartner:
      | Identifier | VATaxID       |
      | bp_save    | FR44732829320 |

    Then the VAT-ID online checker was called for VATaxID 'FR44732829320'

    # Polled, and asserted BEFORE the partner's own status: the status write and this refresh share one
    # transaction, so the refreshed tax becoming visible is the signal that the whole unit has committed.
    # Reading the status first would race that commit -- the "was called" step above only waits for the
    # VATaxID_CheckLog row, which is written earlier and separately.
    And after not more than 60s, validate C_OrderLine:
      | Identifier | OPT.C_Tax_ID.Identifier |
      | lineOpen   | taxCorrectCert0         |

    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | HasTaxCertificate |
      | bp_save       | Valid         | true              |

    And validate C_OrderLine:
      | Identifier    | OPT.C_Tax_ID.Identifier |
      | lineCompleted | taxCorrectStandard19    |
      | lineClosed    | taxCorrectStandard19    |
