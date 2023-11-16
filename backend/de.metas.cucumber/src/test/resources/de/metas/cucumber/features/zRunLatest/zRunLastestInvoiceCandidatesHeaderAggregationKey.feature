@from:cucumber
@ghActions:run_on_executor2
Feature: ZInvoice candidate separation based on header aggregation key

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-03-30T13:30:13+01:00[Europe/Berlin]

    And metasfresh initially has no I_Invoice_Candidate import data
    And metasfresh contains M_PricingSystems
      | Identifier    | Name                       | Value       | OPT.IsActive |
      | ps_1_29032023 | PricingSystemName_29032023 | ps_29032023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1_29032023 | ps_1_29032023                 | DE                        | EUR                 | pl_29032023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID.Identifier | ValidFrom  | Name                          |
      | plv_1_29032023 | pl_1_29032023             | 2022-08-01 | PriceListVersionTest_29032023 |
    And metasfresh contains C_BPartners without locations:
      | Identifier             | Name                   | Value             | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier |
      | endCustomer_1_29032023 | Endcustomer_1_29032023 | BP_Value_29032023 | N            | Y              | ps_1_29032023                 |
    And metasfresh contains C_Location:
      | C_Location_ID.Identifier | CountryCode | OPT.Address1 | OPT.Postal | OPT.City       |
      | location_1_29032023      | DE          | addr 22      | 456        | locationCity_2 |
    # manually setting C_BPartner_Location_ID to 90000000 as the exact repoId it's needed in the csv file
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | OPT.C_BPartner_Location_ID | GLN           | C_BPartner_ID.Identifier | OPT.C_Location_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | bpLocation_1_29032020 | 90000000                   | 2894678902567 | endCustomer_1_29032023   | location_1_29032023          | true         | true         |
    # manually setting AD_User_ID to 90000000 as the exact repoId it's needed in the csv file
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | OPT.AD_User_ID | Name            | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | endUser_1_29032023    | 90000000       | bpUser_29032023 | endCustomer_1_29032023       | bpLocation_1_29032020                 |

  @from:cucumber
  Scenario: Verify that when there are a lot of invoice candidates enqueued only one invoice is created
  per each batch of invoice candidates that share the same header aggregation key
    Given metasfresh contains M_Products:
      | Identifier         | Name               | Value                    |
      | product_1_29032023 | Product_1_29032023 | Product_1_Value_29032023 |
      | product_2_29032023 | Product_2_29032023 | Product_2_Value_29032023 |
      | product_3_29032023 | Product_3_29032023 | Product_3_Value_29032023 |
      | product_4_29032023 | Product_4_29032023 | Product_4_Value_29032023 |
      | product_5_29032023 | Product_5_29032023 | Product_5_Value_29032023 |
    And metasfresh contains M_ProductPrices
      | Identifier    | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1_29032023 | plv_1_29032023                    | product_1_29032023      | 10.0     | PCE               | Normal                        |
      | pp_2_29032023 | plv_1_29032023                    | product_2_29032023      | 10.0     | PCE               | Normal                        |
      | pp_3_29032023 | plv_1_29032023                    | product_3_29032023      | 10.0     | PCE               | Normal                        |
      | pp_4_29032023 | plv_1_29032023                    | product_4_29032023      | 10.0     | PCE               | Normal                        |
      | pp_5_29032023 | plv_1_29032023                    | product_5_29032023      | 10.0     | PCE               | Normal                        |
    # the file has 1000 rows where the only difference is the `ProductId` => so all invoice candidates should end up in the same Invoice;
    And store file content as requestBody in context
      | FileName                                       |
      | InvoiceCandidatesAggregationTestImportData.csv |
    And add HTTP header
      | Key          | Value      |
      | Content-Type | text/plain |
      | Accept       | */*        |

    Then the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code

    And locate I_Invoice_Candidate list searching by bill partner value
      | Bill_BPartner_Value | I_Invoice_Candidate_ID_List.Identifier | OPT.ExpectedCount |
      | BP_Value_29032023   | iInvoiceCandidateList_1                | 1000              |

    And locate C_Invoice_Candidate records for each import record from list
      | C_Invoice_Candidate_ID_List.Identifier | I_Invoice_Candidate_ID_List.Identifier |
      | invoiceCandidateList_1                 | iInvoiceCandidateList_1                |

    # setting a different externalId for every 200 candidates grouped by product => they should be aggregated into 5 invoices in the end
    And update invoice candidate list: filter by product
      | C_Invoice_Candidate_ID_List.Identifier | M_Product_ID.Identifier | OPT.ExternalHeaderId        |
      | invoiceCandidateList_1                 | product_1_29032023      | externalHeaderId_1_29032023 |
      | invoiceCandidateList_1                 | product_2_29032023      | externalHeaderId_2_29032023 |
      | invoiceCandidateList_1                 | product_3_29032023      | externalHeaderId_3_29032023 |
      | invoiceCandidateList_1                 | product_4_29032023      | externalHeaderId_4_29032023 |
      | invoiceCandidateList_1                 | product_5_29032023      | externalHeaderId_5_29032023 |

    And count invoice candidates by: external header id
      | ExternalHeaderId            | NumberOfCandidates |
      | externalHeaderId_1_29032023 | 200                |
      | externalHeaderId_2_29032023 | 200                |
      | externalHeaderId_3_29032023 | 200                |
      | externalHeaderId_4_29032023 | 200                |
      | externalHeaderId_5_29032023 | 200                |

    When build enqueue invoice candidates REST-API request
      | C_Invoice_Candidate_ID_List.Identifier | OPT.DateInvoiced |
      | invoiceCandidateList_1                 | 2023-03-28       |
    And add HTTP header
      | Key          | Value            |
      | Content-Type | application/json |

    Then the metasfresh REST-API endpoint path 'api/v2/invoices/enqueueForInvoicing' receives a 'POST' request with the payload from context and responds with '202' status code

    And locate invoice by external id after not more than 1200s and validate
      | C_Invoice_ID.Identifier | ExternalId                  | OPT.NumberOfCandidates | OPT.DateInvoiced |
      | invoice_1               | externalHeaderId_1_29032023 | 200                    | 2023-03-28       |
      | invoice_2               | externalHeaderId_2_29032023 | 200                    | 2023-03-28       |
      | invoice_3               | externalHeaderId_3_29032023 | 200                    | 2023-03-28       |
      | invoice_4               | externalHeaderId_4_29032023 | 200                    | 2023-03-28       |
      | invoice_5               | externalHeaderId_5_29032023 | 200                    | 2023-03-28       |
