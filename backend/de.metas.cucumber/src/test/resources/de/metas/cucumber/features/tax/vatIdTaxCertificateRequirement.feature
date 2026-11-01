@from:cucumber
@allure.label.epic:E2200_Automatic_Tax_Determination
@allure.label.feature:F66040_Business_Partner_VAT_ID_Validation
@ghActions:run_on_executor7
Feature: Tax determination requires a valid VAT-ID for the tax-certificate rate
  An intra-EU supply is zero-rated only if the buyer's VAT-ID can be proven valid. C_Tax rows that
  carry RequiresTaxCertificate=Y model that 0% rate; C_Tax rows with RequiresTaxCertificate=N model
  the standard rate that applies when the buyer's VAT-ID does not check out. TaxDAO decides which one
  applies based on the stored VATaxIDStatus of whichever record (location, else partner) supplied the
  VAT-ID in the first place.

  Both scenarios use a French customer of a German-org seller, each with its own VATaxID_Config so
  neither scenario depends on the other's ordering or on ambient state left by a sibling feature.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2026-08-13T10:00:00+02:00[Europe/Berlin]

    And load M_Warehouse:
      | M_Warehouse_ID.Identifier | Value        |
      | warehouseStd              | StdWarehouse |

    # A dedicated C_TaxCategory keeps the two C_Tax rows below fully isolated from every other
    # feature's tax setup, incl. the seeded "Normal" category's own domestic/cross-border rows.
    And metasfresh contains C_TaxCategory
      | Identifier         |
      | taxCategoryVATCert |
    And metasfresh contains C_Tax
      | Identifier      | C_TaxCategory_ID   | Name                  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | RequiresTaxCertificate |
      | taxEuCert0      | taxCategoryVATCert | eu_supply_0_with_cert | 0    | DE                       | FR                        | true                   |
      | taxEuStandard19 | taxCategoryVATCert | eu_supply_19_no_cert  | 19   | DE                       | FR                        | false                  |

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
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID   |
      | pp_FR      | plv_FR                            | product                 | 100.0    | PCE               | taxCategoryVATCert |

    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode |
      | location_france          | FR          |

  @Id:S31060_TC5
  Scenario: TC5 - order for a French customer whose VAT-ID checked out Invalid does not get the 0% tax-certificate rate
    Given no VATaxID_CheckLog records exist for VATaxID 'FRK7399859412'
    And metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | true               | 30               | ServiceUnavailable   |
    And the VAT-ID online checker is stubbed to answer:
      | VATaxID       | VATaxIDStatus |
      | FRK7399859412 | Invalid       |

    And metasfresh contains C_BPartners without locations:
      | Identifier    | Value              | Name                      | M_PricingSystem_ID.Identifier | IsCustomer | VATaxID       |
      | bp_vatinvalid | taxcert_vatinvalid | FrenchCustomer_VATInvalid | ps_1                          | Y          | FRK7399859412 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier     | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | bpl_vatinvalid | 0123456789021 | bp_vatinvalid            | Y               | Y               | location_france              |

    When the VAT-ID check runs for C_BPartner:
      | C_BPartner_ID |
      | bp_vatinvalid |
    Then the VAT-ID check returned status 'Invalid' for C_BPartner 'bp_vatinvalid'
    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID | VATaxIDStatus | HasTaxCertificate |
      | bp_vatinvalid | Invalid       | false             |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | bp_vatinvalid            | 2026-08-13  | bpl_vatinvalid                        | warehouseStd                  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 1          |

    When the order identified by order is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | product                 | 1          | taxEuStandard19         |

  @Id:S31060_TC6
  Scenario: TC6 - order for a French customer whose VAT-ID was never checked still gets the 0% tax-certificate rate (today's behaviour, unchanged)
    Given metasfresh contains VATaxID_Config:
      | IsFormatCheckEnabled | IsVIESCheckEnabled | RecheckAfterDays | OnServiceUnavailable |
      | true                 | false              | 30               | ServiceUnavailable   |

    And metasfresh contains C_BPartners without locations:
      | Identifier       | Value                 | Name                         | M_PricingSystem_ID.Identifier | IsCustomer | VATaxID       |
      | bp_vatnotchecked | taxcert_vatnotchecked | FrenchCustomer_VATNotChecked | ps_1                          | Y          | FR4Z123456782 |
    And metasfresh contains C_BPartner_Locations:
      | Identifier        | GLN           | C_BPartner_ID.Identifier | IsShipToDefault | IsBillToDefault | OPT.C_Location_ID.Identifier |
      | bpl_vatnotchecked | 0123456789022 | bp_vatnotchecked         | Y               | Y               | location_france              |

    And validate C_BPartner VAT-ID status:
      | C_BPartner_ID    | VATaxIDStatus | HasTaxCertificate |
      | bp_vatnotchecked | NotChecked    | true              |

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.M_Warehouse_ID.Identifier |
      | order      | true    | bp_vatnotchecked         | 2026-08-13  | bpl_vatnotchecked                     | warehouseStd                  |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | orderLine  | order                 | product                 | 1          |

    When the order identified by order is completed

    Then validate the created order lines
      | C_OrderLine_ID.Identifier | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | OPT.C_Tax_ID.Identifier |
      | orderLine                 | order                 | product                 | 1          | taxEuCert0              |
