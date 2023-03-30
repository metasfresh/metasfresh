@from:cucumber
Feature: Invoice candidate separation based on header aggregation key validation

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-03-30T13:30:13+01:00[Europe/Berlin]

    And  metasfresh initially has no I_Invoice_Candidate data
    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |
    And load AD_User:
      | AD_User_ID.Identifier | Login      |
      | loginUser             | metasfresh |
    And update AD_User:
      | AD_User_ID.Identifier | OPT.C_BPartner_ID.Identifier |
      | loginUser             | endCustomer_1                |
    And metasfresh contains M_PricingSystems
      | Identifier    | Name                       | Value       | OPT.IsActive |
      | ps_1_29032023 | PricingSystemName_29032023 | ps_29032023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier    | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name        | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1_29032023 | ps_1_29032023                 | DE                        | EUR                 | pl_29032023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier     | M_PriceList_ID.Identifier | ValidFrom  | Name                          |
      | plv_1_29032023 | pl_1_29032023             | 2022-08-01 | PriceListVersionTest_29032023 |
    And update C_BPartner:
      | Identifier    | OPT.M_PricingSystem_ID.Identifier |
      | endCustomer_1 | ps_1_29032023                     |
    And load AD_ImpFormat:
      | AD_ImpFormat_ID.Identifier | Name                     |
      | importFormat               | Import Invoice Candidate |
    And load AD_Org from AD_ImpFormat config:
      | AD_Org_ID.Identifier | AD_ImpFormat_ID.Identifier | OrgCode.ImportRowFieldName |
      | importFormatOrg      | importFormat               | Default Org Suchschlüssel  |

  @from:cucumber
  Scenario: Verify that when there are a lot of invoice candidates enqueued only one invoice is created per invoice candidate that has the same header aggregation key
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
    And store file content as requestBody in context
      | FileName                  |
      | I_I_InvoiceCandidates.csv |
    And add HTTP header
      | Key          | Value      |
      | Content-Type | text/plain |
      | Accept       | */*        |

    Then the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code

    And multiple I_Invoice_Candidate records are found after not more than 60s: searching by bill partner value
      | Bill_BPartner_Value | I_Invoice_Candidate_ID_List.Identifier | OPT.CandidateBatchSize |
      | G0001               | iInvoiceCandidateList_1                | 1000                   |

    And locate invoice candidate list by record reference after 60s:
      | C_Invoice_Candidate_ID_List.Identifier | TableName           | I_Invoice_Candidate_ID_List.Identifier | OPT.CandidateBatchSize |
      | invoiceCandidateList_1                 | I_Invoice_Candidate | iInvoiceCandidateList_1                | 1000                   |

    And update invoice candidate list
      | C_Invoice_Candidate_ID_List.Identifier | OPT.InvoiceRule_Override |
      | invoiceCandidateList_1                 | I                        |
    And update invoice candidate list external header id
      | C_Invoice_Candidate_ID_List.Identifier | M_Product_ID.Identifier | ExternalHeaderId            |
      | invoiceCandidateList_1                 | product_1_29032023      | externalHeaderId_1_29032023 |
      | invoiceCandidateList_1                 | product_2_29032023      | externalHeaderId_2_29032023 |
      | invoiceCandidateList_1                 | product_3_29032023      | externalHeaderId_3_29032023 |
      | invoiceCandidateList_1                 | product_4_29032023      | externalHeaderId_4_29032023 |
      | invoiceCandidateList_1                 | product_5_29032023      | externalHeaderId_5_29032023 |

    And after not more than 60s check number of invoice candidates having external header id
      | ExternalHeaderId            | NumberOfCandidates |
      | externalHeaderId_1_29032023 | 200                |
      | externalHeaderId_2_29032023 | 200                |
      | externalHeaderId_3_29032023 | 200                |
      | externalHeaderId_4_29032023 | 200                |
      | externalHeaderId_5_29032023 | 200                |

    When build request payload to enqueue invoice candidate list for invoicing via API with DateInvoiced and set it to context
      | C_Invoice_Candidate_ID_List.Identifier | OPT.DateInvoiced |
      | invoiceCandidateList_1                 | 2023-03-28       |
    And add HTTP header
      | Key          | Value            |
      | Content-Type | application/json |
      | Accept       | */*              |

    Then the metasfresh REST-API endpoint path 'api/v2/invoices/enqueueForInvoicing' receives a 'POST' request with the payload from context and responds with '202' status code

    And locate invoice by external id after not more than 300s and validate
      | C_Invoice_ID.Identifier | ExternalId                  | OPT.NumberOfCandidates | OPT.DateInvoiced        |
      | invoice_1               | externalHeaderId_1_29032023 | 200                    | 2023-03-28T01:00:00.000 |
      | invoice_2               | externalHeaderId_2_29032023 | 200                    | 2023-03-28T01:00:00.000 |
      | invoice_3               | externalHeaderId_3_29032023 | 200                    | 2023-03-28T01:00:00.000 |
      | invoice_4               | externalHeaderId_4_29032023 | 200                    | 2023-03-28T01:00:00.000 |
      | invoice_5               | externalHeaderId_5_29032023 | 200                    | 2023-03-28T01:00:00.000 |
