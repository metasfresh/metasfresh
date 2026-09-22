@from:cucumber
@allure.label.epic:E0291_REST_API
@allure.label.feature:F4510_Invoice_Candidate
@ghActions:run_on_executor2
Feature: Invoice-Candidate API — the caller sets the tax
## F4510: Invoice Candidate
  As an external system posting invoice-candidate lines
  I want to state the tax myself — the rate plus the tax category — for a line whose tax is not
  the one the product's tax category implies,
  so that metasfresh resolves it exactly as its own pricing engine would and stores it as the
  candidate's C_Tax_Override_ID.

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-05-10T09:00:00+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    # The org's own warehouse sits in Germany, so DE is the origin country of every tax query below,
    # whatever country the bill partner is in.
    And metasfresh contains M_Products:
      | Identifier | REST.Context.Value |
      | product    | productValue       |
    And metasfresh contains M_PricingSystems
      | Identifier |
      | ps         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | SOTrx | IsTaxIncluded | PricePrecision |
      | pl_DE      | ps                            | DE                        | EUR                 | true  | false         | 2              |
      | pl_AT      | ps                            | AT                        | EUR                 | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  |
      | plv_DE     | pl_DE                     | 2020-01-01 |
      | plv_AT     | pl_AT                     | 2020-01-01 |
    # The product's own tax category is the seeded `Normal`, i.e. deliberately NOT the category the
    # caller names below — that is the whole point of the feature. The scenarios that assert engine
    # parity re-declare the product price with their own category.
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_DE      | plv_DE                            | product                 | 100      | PCE               | Normal                        |
      | pp_AT      | plv_AT                            | product                 | 100      | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | REST.Context.Value |
      | customerDE | Y              | ps                            | customerDEValue    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | C_Country_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | locDE      | customerDE               | DE           | Y                   | Y                   |

  @Id:S31985_TC1
  Scenario: rate plus category resolve to one tax, and the new candidate carries it as C_Tax_Override_ID
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | tax19      | cat                           | 19   | DE                       | 10    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC1_H",
            "externalLineId": "31985_TC1_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC1_H      |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | product                     | tax19                            |

  @Id:S31985_TC2
  Scenario: the origin country governs, not the bill country
    Given metasfresh contains C_BPartners without locations:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | REST.Context.Value |
      | customerAT | Y              | ps                            | customerATValue    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | C_Country_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | locAT      | customerAT               | AT           | Y                   | Y                   |
    And metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    # Same category, same rate, same destination — the two differ ONLY in their origin country.
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | SeqNo |
      | taxFromDE  | cat                           | 19   | DE                       | AT                        | WITHIN_COUNTRY_AREA | 20    |
      | taxFromAT  | cat                           | 19   | AT                       |                           |                     | 10    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC2_H",
            "externalLineId": "31985_TC2_L1",
            "billPartnerIdentifier": "val-@customerATValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC2_H      |
    # taxFromAT has the LOWER SeqNo, so picking taxFromDE can only be the origin-country filter at work.
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | taxFromDE                        |

  @Id:S31985_TC3
  Scenario: engine parity on a cross-border line
    Given metasfresh contains C_BPartners without locations:
      | Identifier | OPT.IsCustomer | M_PricingSystem_ID.Identifier | REST.Context.Value |
      | customerAT | Y              | ps                            | customerATValue    |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | C_BPartner_ID.Identifier | C_Country_ID | OPT.IsShipToDefault | OPT.IsBillToDefault |
      | locAT      | customerAT               | AT           | Y                   | Y                   |
    And metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode | TypeOfDestCountry   | SeqNo |
      | taxDEtoAT  | cat                           | 19   | DE                       | AT                        | WITHIN_COUNTRY_AREA | 10    |
    # Give the PRODUCT the caller's category too. The candidate's own C_Tax_ID is then literally
    # ITaxBL.getTaxNotNull run with the caller's category, so asserting both columns against the same
    # record is the parity assertion — no tax id is hard-coded anywhere.
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_AT      | plv_AT                            | product                 | 100      | PCE               | cat                           |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC3_H",
            "externalLineId": "31985_TC3_L1",
            "billPartnerIdentifier": "val-@customerATValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC3_H      |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | taxDEtoAT               | taxDEtoAT                        |

  @Id:S31985_TC4
  Scenario: the category selects between same-rate taxes, by either identifier form
    Given metasfresh contains C_TaxCategory
      | Identifier   | REST.Context.InternalName | REST.Context.C_TaxCategory_ID |
      | catNormal    | catNormalInternalName     | catNormalId                   |
      | catTransport | catTransportInternalName  | catTransportId                |
    And metasfresh contains C_Tax
      | Identifier   | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | taxNormal    | catNormal                     | 19   | DE                       | 10    |
      | taxTransport | catTransport                  | 19   | DE                       | 10    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC4a_H",
            "externalLineId": "31985_TC4a_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catNormalInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | icNormal                          | 31985_TC4a_H     |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | icNormal                          | taxNormal                        |

    # same rate, other category, named by its internal name
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC4b_H",
            "externalLineId": "31985_TC4b_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catTransportInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | icTransportByName                 | 31985_TC4b_H     |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | icTransportByName                 | taxTransport                     |

    # the very same category, this time named by its bare metasfresh id
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC4c_H",
            "externalLineId": "31985_TC4c_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "@catTransportId@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | icTransportById                   | 31985_TC4c_H     |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | icTransportById                   | taxTransport                     |

  @Id:S31985_TC5
  Scenario: several matches inside one category resolve by SeqNo, exactly as the engine does
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | taxLowSeq  | cat                           | 19   | DE                       | 10    |
      | taxHighSeq | cat                           | 19   | DE                       | 20    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_DE      | plv_DE                            | product                 | 100      | PCE               | cat                           |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC5_H",
            "externalLineId": "31985_TC5_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC5_H      |
    # C_Tax_ID is the engine's own pick for this very category — the override has to agree with it.
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | taxLowSeq               | taxLowSeq                        |

  @Id:S31985_TC6
  Scenario: a tied SeqNo is rejected, and nothing is persisted
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | taxTieA    | cat                           | 19   | DE                       | 30    |
      | taxTieB    | cat                           | 19   | DE                       | 30    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC6_H",
            "externalLineId": "31985_TC6_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    # the message names BOTH offending records, so the master-data defect can be corrected
    Then the metasfresh REST-API error message contains:
      | Value                                         |
      | Multiple taxes have the same seqNo: C_Tax_ID= |
      | and C_Tax_ID=                                 |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC6_H

  @Id:S31985_TC7
  Scenario: no matching tax is rejected with a message naming the rate, the category and the scope
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | tax19      | cat                           | 19   | DE                       | 10    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC7_H",
            "externalLineId": "31985_TC7_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 7, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value                 |
      | int-@catInternalName@ |
      | : 7 -                 |
      | metasfresh AG         |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC7_H

  @Id:S31985_TC8
  Scenario: a tax-category identifier that does not resolve
    Given metasfresh contains C_TaxCategory
      | Identifier  | OPT.IsActive | REST.Context.C_TaxCategory_ID |
      | catInactive | N            | catInactiveId                 |
    # C_TaxCategory_ID=100 is TaxCategoryId.NOT_FOUND: a real, active, system-seeded category on every
    # instance, but without an InternalName. Give it one, so it can be named by the int- form below too.
    And C_TaxCategory is given a fresh InternalName:
      | C_TaxCategory_ID | REST.Context.InternalName |
      | 100              | catNotFoundInternalName   |

    # an internal name nothing carries
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC8a_H",
            "externalLineId": "31985_TC8a_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-DoesNotExist" }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value            |
      | TaxCategory      |
      | int-DoesNotExist |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC8a_H

    # a metasfresh id no category carries
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC8b_H",
            "externalLineId": "31985_TC8b_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "999999999" }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value       |
      | TaxCategory |
      | 999999999   |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC8b_H

    # the id of a category that exists but is INACTIVE — the id form checks IsActive, like the int- form
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC8c_H",
            "externalLineId": "31985_TC8c_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "@catInactiveId@" }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value           |
      | TaxCategory     |
      | @catInactiveId@ |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC8c_H

    # an identifier form this property does not support -> 404, exactly as for every other identifier here
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '404' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC8d_H",
            "externalLineId": "31985_TC8d_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "val-Transport" }
          }
        ]
      }
      """
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC8d_H

    # 100 is TaxCategoryId.NOT_FOUND — it must be rejected, never silently accepted as a real category
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC8e_H",
            "externalLineId": "31985_TC8e_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "100" }
          }
        ]
      }
      """
    # anchored on MissingResourceException's own "resourceIdentifier=" separator, not on a bare "100":
    # the message also echoes the request item as parentResource, so a bare "100" would match that echo
    # and pass even if the rejection came from somewhere other than the identifier-resolution path.
    Then the metasfresh REST-API error message contains:
      | Value                  |
      | TaxCategory            |
      | resourceIdentifier=100 |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC8e_H

    # ... and naming that very same sentinel by its internal name must be rejected just the same: the
    # int- branch resolves it as readily as the id branch, so it needs the same guard.
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC8f_H",
            "externalLineId": "31985_TC8f_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catNotFoundInternalName@" }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value                                            |
      | TaxCategory                                      |
      | resourceIdentifier=int-@catNotFoundInternalName@ |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC8f_H

  @Id:S31985_TC9
  Scenario: a half-specified taxOverride is rejected, naming the missing property
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | tax19      | cat                           | 19   | DE                       | 10    |

    # rate without a category
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC9a_H",
            "externalLineId": "31985_TC9a_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19 }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value                             |
      | taxOverride.taxCategoryIdentifier |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC9a_H

    # category without a rate
    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC9b_H",
            "externalLineId": "31985_TC9b_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    Then the metasfresh REST-API error message contains:
      | Value            |
      | taxOverride.rate |
    And there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC9b_H

  @Id:S31985_TC10
  Scenario: expired and inactive taxes are invisible, and do not even count towards a multi-match
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    # The two invisible ones carry the LOWER SeqNo, so they would win the tiebreak if they were seen
    # at all — and a third visible record at the same SeqNo would be a tie, not a pick.
    And metasfresh contains C_Tax
      | Identifier  | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo | OPT.ValidFrom | OPT.ValidTo | OPT.IsActive |
      | taxExpired  | cat                           | 19   | DE                       | 10    | 2020-01-01    | 2021-12-31  | Y            |
      | taxInactive | cat                           | 19   | DE                       | 10    | 2020-01-01    |             | N            |
      | taxValid    | cat                           | 19   | DE                       | 20    | 2020-01-01    |             | Y            |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC10_H",
            "externalLineId": "31985_TC10_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC10_H     |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | taxValid                         |

  @Id:S31985_TC11
  Scenario: without a taxOverride the candidate is exactly what it is today
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | tax19      | cat                           | 19   | DE                       | 10    |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_DE      | plv_DE                            | product                 | 100      | PCE               | cat                           |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC11_H",
            "externalLineId": "31985_TC11_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort"
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC11_H     |
    Then validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | tax19                   | null                             |

  @Id:S31985_TC12
  Scenario: one bad item rejects the whole batch, leaving no candidate behind
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | tax19      | cat                           | 19   | DE                       | 10    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '422' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC12_H",
            "externalLineId": "31985_TC12_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          },
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC12_H",
            "externalLineId": "31985_TC12_L2",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-DoesNotExist" }
          },
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC12_H",
            "externalLineId": "31985_TC12_L3",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    # not even the first item, which on its own would have resolved
    Then there is no C_Invoice_Candidate with ExternalHeaderId 31985_TC12_H

  @Id:S31985_TC13
  Scenario: the overridden tax reaches the invoice
    Given metasfresh contains C_TaxCategory
      | Identifier | REST.Context.InternalName |
      | cat        | catInternalName           |
    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Rate | C_Country_ID.CountryCode | SeqNo |
      | tax19      | cat                           | 19   | DE                       | 10    |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/invoices/createCandidates' and fulfills with '200' status code
      """
      {
        "items": [
          {
            "orgCode": "001",
            "externalHeaderId": "31985_TC13_H",
            "externalLineId": "31985_TC13_L1",
            "billPartnerIdentifier": "val-@customerDEValue@",
            "productIdentifier": "val-@productValue@",
            "dateOrdered": "2023-05-10",
            "qtyOrdered": 1,
            "qtyDelivered": 1,
            "invoiceRuleOverride": "Immediate",
            "soTrx": "SALES",
            "paymentTerm": "val-sofort",
            "taxOverride": { "rate": 19, "taxCategoryIdentifier": "int-@catInternalName@" }
          }
        ]
      }
      """
    And after not more than 60s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId |
      | ic                                | 31985_TC13_H     |
    And validate C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.C_Tax_Override_ID.Identifier |
      | ic                                | tax19                            |

    When process invoice candidates and wait 60s for C_Invoice_Candidate to be processed
      | C_Invoice_Candidate_ID |
      | ic                     |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice                 | ic                                |
    And validate invoice lines for invoice:
      | C_InvoiceLine_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | OPT.C_Tax_ID.Identifier |
      | invoiceLine                 | product                 | 1           | tax19                   |

  @Id:S31985_TC15
  Scenario: an InternalName belongs to at most one active tax category, and is freed when that category is deactivated
    Given metasfresh contains C_TaxCategory
      | Identifier |
      | cat        |

    # Nothing on the Java side stops a second active category from taking a name that is already in use,
    # so this can only be rejected by the database - by the partial unique index on InternalName.
    Then a second active C_TaxCategory with the same InternalName is rejected:
      | Identifier |
      | cat        |
    # ... which is what lets the int- form of taxCategoryIdentifier resolve to a single category.
    And C_TaxCategory is found by its InternalName:
      | Identifier |
      | cat        |

    # The index is partial on IsActive='Y', and so is the lookup: a retired category no longer holds its
    # InternalName, so the name can be handed to a new one.
    When C_TaxCategory is deactivated:
      | Identifier |
      | cat        |
    Then metasfresh contains C_TaxCategory
      | Identifier | OPT.InternalName.Identifier |
      | catReused  | cat                         |
    And C_TaxCategory is found by its InternalName:
      | Identifier |
      | catReused  |
