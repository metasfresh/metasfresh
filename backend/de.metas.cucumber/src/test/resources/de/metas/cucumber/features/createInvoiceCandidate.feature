@from:cucumber
@topic:InvoiceCandidate
@ghActions:run_on_executor5
Feature: create invoice candidate via API
  As a user
  I create multiple Invoice candidates and when processing, multiple workpackages are enqueued for each invoic to be generated

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-01-05T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

  @from:cucumber
  @topic:InvoiceCandidate
  @Id:S0239_10
  Scenario: Process IC using externalsystem references
    Given metasfresh contains M_PricingSystems
      | Identifier           | Name                             | Value                            | OPT.IsActive |
      | ps_scenario_05012023 | pricing_system_scenario_05012023 | pricing_system_scenario_05012023 | true         |
    And metasfresh contains M_PriceLists
      | Identifier           | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name                 | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_scenario_05012023 | ps_scenario_05012023          | DE                        | EUR                 | pl_scenario_05012023 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier            | M_PriceList_ID.Identifier | Name                  | ValidFrom  |
      | plv_scenario_05012023 | pl_scenario_05012023      | plv_scenario_05012023 | 2021-04-01 |
    And metasfresh contains M_Products:
      | Identifier                   | Name                         |
      | product_05012023             | product_05012023             |
      | product_priceChange_05012023 | product_priceChange_05012023 |
    And metasfresh contains M_ProductPrices
      | Identifier             | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier      | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_product             | plv_scenario_05012023             | product_05012023             | 10.0     | PCE               | Normal                        |
      | pp_product_priceChange | plv_scenario_05012023             | product_priceChange_05012023 | 20.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners:
      | Identifier           | Name                 | OPT.IsCustomer | OPT.IsVendor | M_PricingSystem_ID.Identifier |
      | IC_Customer_05012023 | IC_Customer_05012022 | Y              | N            | ps_scenario_05012023          |
    And metasfresh contains C_BPartner_Locations:
      | Identifier                    | GLN           | C_BPartner_ID.Identifier |
      | IC_Customer_Location_05012023 | 1234567890123 | IC_Customer_05012023     |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | OPT.C_BPartner_ID.Identifier | Name         | OPT.C_BPartner_Locations.Identifier |
      | IC_User_05012023      | IC_Customer_05012023         | user1_1_name | IC_Customer_Location_05012023       |
    And metasfresh contains S_ExternalReference:
      | S_ExternalReference_ID.Identifier | ExternalSystem | ExternalReference     | Type             | OPT.M_Product_ID.Identifier | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier |
      | productExternalRef                | Other          | productExternalRef    | Product          | product_05012023            |                              |                                       |                           |
      | bpartnerExternalRef               | Other          | bpartnerExternalRef   | BPartner         |                             | IC_Customer_05012023         |                                       |                           |
      | bpLocationExternalRef             | Other          | bpLocationExternalRef | BPartnerLocation |                             |                              | IC_Customer_Location_05012023         |                           |
      | userExternalRef                   | Other          | userExternalRef       | UserID           |                             |                              |                                       | IC_User_05012023          |

    When a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/invoices/createCandidates' and fulfills with '200' status code
"""
  {
  "items": [
  {
  "billContactIdentifier": "ext-Other-userExternalRef",
  "billLocationIdentifier": "ext-Other-bpLocationExternalRef",
  "billPartnerIdentifier": "ext-Other-bpartnerExternalRef",
  "dateOrdered": "2023-01-05",
  "discountOverride": 0,
  "externalHeaderId": "IC_Header_05012023",
  "externalLineId": "IC_Line_05012023",
  "invoiceDetailItems": [
  {
  "date": "2023-01-05",
  "description": "test IC",
  "label": "test Label",
  "note": "test Note",
  "price": 20,
  "seqNo": 10
  }
  ],
  "invoiceDocType": {
     "docBaseType": "ARI"
  },
  "invoiceRuleOverride": "AfterDelivery",
  "lineDescription": "string",
  "orgCode": "001",
  "poReference": "testICByExtRef",
  "presetDateInvoiced": "2023-01-05",
  "productIdentifier": "ext-Other-productExternalRef",
  "qtyDelivered": 2,
  "qtyOrdered": 2,
  "soTrx": "SALES",
  "uomCode": "PCE"
  }
  ]
  }
"""
    And after not more than 30s, locate C_Invoice_Candidates by externalHeaderId
      | C_Invoice_Candidate_ID.Identifier | ExternalHeaderId   |
      | i_c_1                             | IC_Header_05012023 |

    And after not more than 20s, C_Invoice_Candidates are not marked as 'to recompute'
      | C_Invoice_Candidate_ID.Identifier |
      | i_c_1                             |

    And a 'POST' request with the below payload is sent to the metasfresh REST-API '/api/v2/invoices/enqueueForInvoicing' and fulfills with '202' status code
"""
{
  "dateAcct": "2023-01-05",
  "dateInvoiced": "2023-01-05",
  "ignoreInvoiceSchedule": false,
  "invoiceCandidates": [
    {
      "externalHeaderId": "IC_Header_05012023",
      "externalLineIds": ["IC_Line_05012023"]
    }
  ],
  "poReference": "testICByExtRef",
  "supplementMissingPaymentTermIds": true,
  "updateLocationAndContactForInvoice": false
}
"""
    Then after not more than 30s, C_Invoice are found:
      | C_Invoice_ID.Identifier | C_Invoice_Candidate_ID.Identifier |
      | invoice_1               | i_c_1                             |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.POReference | paymentTerm   | processed | docStatus |
      | invoice_1               | IC_Customer_05012023     | IC_Customer_Location_05012023     | testICByExtRef  | 30 Tage netto | true      | CO        |

    And validate created invoice lines
      | C_InvoiceLine_ID.Identifier | C_Invoice_ID.Identifier | M_Product_ID.Identifier | QtyInvoiced | Processed |
      | invoiceLine_1_1             | invoice_1               | product_05012023        | 2           | true      |


