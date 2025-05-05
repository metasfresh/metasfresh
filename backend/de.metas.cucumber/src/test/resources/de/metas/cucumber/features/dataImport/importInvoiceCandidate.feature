@Id:S0178
@from:cucumber
@ghActions:run_on_executor5
Feature: Import Invoice Candidates via DataImportRestController (external reference)

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And metasfresh has current date and time
    And metasfresh initially has no I_Invoice_Candidate import data
    And metasfresh contains M_PricingSystems
      | Identifier | Name              | Value     | OPT.IsActive |
      | ps_1       | PricingSystemName | ps_280822 | true         |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name      | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
      | pl_1       | ps_1                          | DE                        | EUR                 | pl_280822 | true  | false         | 2              | true         |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | ValidFrom  | Name                 |
      | plv_1      | pl_1                      | 2022-08-01 | PriceListVersionTest |
    And add HTTP header
      | Key          | Value      |
      | Content-Type | text/plain |
      | Accept       | */*        |
    And load AD_ImpFormat:
      | AD_ImpFormat_ID.Identifier | Name                     |
      | importFormat               | Import Invoice Candidate |
    And load AD_Org from AD_ImpFormat config:
      | AD_Org_ID.Identifier | AD_ImpFormat_ID.Identifier | OrgCode.ImportRowFieldName |
      | importFormatOrg      | importFormat               | Default Org Suchschlüssel  |

  @from:cucumber
  @Id:S0178_10
  Scenario: Import sales I_Invoice_Candidate from csv - setting all available fields
    Given metasfresh initially has no I_Invoice_Candidate import data
    And metasfresh contains M_Products:
      | Identifier | Name                 | Value                      |
      | product_1  | Product_25_08_2022_1 | Product_Value_25_08_2022_1 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_1               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier     | Name                      | Value                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | billBpartner_1 | BillBPartner_25_08_2022_1 | BillBPartner_Value_25_08_2022 | N            | Y              | ps_1                          | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | billBPLocation_1 | 1239977890123 | billBpartner_1           | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                  | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | billBPUser_1          | BillBPartnerContact_1 | billBpartner_1               | billBPLocation_1                      |
    And metasfresh contains C_Activity:
      | C_Activity_ID.Identifier | Value                | Name                |
      | activity                 | ActivityValue_210222 | ActivityName_210222 |
    And store DataImport string requestBody in context
      | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | M_Product_ID.Identifier | OPT.DateOrdered | QtyOrdered | OPT.QtyDelivered | OPT.OrgCode | OPT.X12DE355 | IsSOTrx | OPT.DocBaseType | OPT.DocSubType | OPT.PresetDateInvoiced | OPT.Description | OPT.POReference | OPT.InvoiceRule | OPT.C_Activity_Value |
      | billBpartner_1              | billBPLocation_1            | billBPUser_1                | product_1               | 25.08.2022      | 5          | 3                | 001         | PCE          | true    | ARI             | EA             | 26.08.2022             | DescriptionTest | PORef           | D               | ActivityValue_210222 |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code

    Then load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType |
      | docType                 | ARI             | EA             |
    And load C_UOM:
      | C_UOM_ID.Identifier | X12DE355 |
      | UOM                 | PCE      |
    And load AD_Org:
      | AD_Org_ID.Identifier | Value |
      | importOrg            | 001   |
    And I_Invoice_Candidate is found: searching by product value
      | M_Product_Value            | I_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | Bill_User_ID.Identifier | M_Product_ID.Identifier | OPT.DateOrdered | QtyOrdered | AD_Org_ID.Identifier | OPT.QtyDelivered | OPT.C_UOM_ID.Identifier | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.PresetDateInvoiced | OPT.Description | OPT.POReference | OPT.InvoiceRule | I_IsImported | OPT.C_Activity_ID.Identifier |
      | Product_Value_25_08_2022_1 | iInvoiceCandidate_1               | billBpartner_1              | billBPLocation_1            | billBPUser_1            | product_1               | 2022-08-25      | 5          | importOrg            | 3                | UOM                     | Y       | docType                     | 2022-08-26             | DescriptionTest | PORef           | D               | Y            | activity                     |
    And validate invoice candidates by record reference:
      | TableName           | I_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | AD_Org_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.DateOrdered | OPT.QtyOrdered | OPT.QtyDelivered | OPT.C_UOM_ID.Identifier | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.PresetDateInvoiced | OPT.Description | OPT.POReference | InvoiceRule | OPT.C_Activity_ID.Identifier |
      | I_Invoice_Candidate | iInvoiceCandidate_1               | invoiceCandidate_1                | billBpartner_1              | billBPLocation_1            | importOrg            | billBPUser_1                | product_1                   | 2022-08-25      | 5              | 3                | UOM                     | true    | docType                     | 2022-08-26             | DescriptionTest | PORef           | D           | activity                     |
    # note: updating to invoicing rule immediate in order to verify the invoice creation step
    And update C_Invoice_Candidate:
      | C_Invoice_Candidate_ID.Identifier | OPT.InvoiceRule_Override |
      | invoiceCandidate_1                | I                        |
    And after not more than 60s C_Invoice_Candidate matches:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCandidate_1                | 5                |
    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_1                |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_1                | invoice_1               |

  @from:cucumber
  @Id:S0178_20
  Scenario: Import sales I_Invoice_Candidate from csv - setting only mandatory fields, system will compute the rest
    Given metasfresh contains M_Products:
      | Identifier | Name                 | Value                      |
      | product_2  | Product_25_08_2022_2 | Product_Value_25_08_2022_2 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_2               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier     | Name                      | Value                           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | billBpartner_2 | BillBPartner_25_08_2022_2 | BillBPartner_Value_25_08_2022_2 | N            | Y              | ps_1                          | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | billBPLocation_2 | 1239977890123 | billBpartner_2           | true         | true         |
    And store DataImport string requestBody in context
      | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | IsSOTrx |
      | billBpartner_2              | billBPLocation_2            | product_2               | 2          | true    |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code

    Then load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType | OPT.IsDefault |
      | docType                 | ARI             | null           | true          |
    And load C_UOM for product:
      | C_UOM_ID.Identifier | M_Product_ID.Identifier |
      | UOM_2               | product_2               |
    And I_Invoice_Candidate is found: searching by product value
      | M_Product_Value            | I_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | AD_Org_ID.Identifier | OPT.QtyDelivered | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.C_UOM_ID.Identifier | OPT.InvoiceRule | I_IsImported |
      | Product_Value_25_08_2022_2 | iInvoiceCandidate_2               | billBpartner_2              | billBPLocation_2            | product_2               | 2          | importFormatOrg      | 0                | Y       | docType                     | UOM_2                   | I               | Y            |
    And validate invoice candidates by record reference:
      | TableName           | I_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | AD_Org_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.C_UOM_ID.Identifier | InvoiceRule |
      | I_Invoice_Candidate | iInvoiceCandidate_2               | invoiceCandidate_2                | billBpartner_2              | billBPLocation_2            | importFormatOrg      | billBPUser_2                | product_2                   | 2              | 0                | true    | docType                     | UOM_2                   | I           |


  @from:cucumber
  @Id:S0178_30
  Scenario: Import sales I_Invoice_Candidate from csv - error scenario
  _Given billBPartner and product values that don't match any records
  _And DocBaseType + DocSubType don't reference any existing C_DocType
  _And qtyOrdered and qtyDelivered have different signs
  _And orgCode and uomCode don't match any records
  _And C_Activity_Value doesn't match any records
  _When importing the record
  _Then processing errors are saved in I_ErrorMsg column
    Given metasfresh contains M_Products:
      | Identifier | Name                 | Value                      |
      | product_3  | Product_25_08_2022_3 | Product_Value_25_08_2022_3 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_3               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier     | Name                      | Value                           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | billBpartner_3 | BillBPartner_25_08_2022_3 | BillBPartner_Value_25_08_2022_3 | N            | Y              | ps_1                          | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | billBPLocation_3 | 1239977812123 | billBpartner_3           | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                  | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | billBPUser_3          | BillBPartnerContact_3 | billBpartner_3               | billBPLocation_3                      |
    And store DataImport string requestBody in context
      | OPT.Bill_BPartner_Value       | Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.M_Product_Value                | QtyOrdered | OPT.QtyDelivered | IsSOTrx | OPT.DocBaseType | OPT.DocSubType | OPT.OrgCode | OPT.X12DE355 | OPT.C_Activity_Value |
      | someNonExistingBPValue_280822 | billBPLocation_3            | billBPUser_3                | someNonExistingProductValue_280822 | 2          | -2               | true    | ARI             | VI             | 005         | PCR          | SomeNonExistingValue |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '400' status code

    Then I_Invoice_Candidate is found: searching by product value
      | M_Product_Value                    | I_Invoice_Candidate_ID.Identifier | I_IsImported | OPT.I_ErrorMsg                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
      | someNonExistingProductValue_280822 | iInvoiceCandidate_3               | E            | ERR = Mandatory C_Invoice_Candidate.Bill_BPartner_ID is missing!, ERR = M_Product_ID is missing!, ERR = C_DocType_ID not found for provided ( DocBaseType, DocSubType, IsSOTrx )!, ERR = Could not find any matching AD_Org_ID for provided OrgCode !, ERR = Could not find any matching C_UOM_ID for provided UOMCode !, ERR = QtyOrdered and QtyDelivered must have the same sign!, ERR = Could not find any matching C_Activity_ID for provided activity value!, |


  @from:cucumber
  @Id:S0178_40
  Scenario: Import sales I_Invoice_Candidate from csv - error scenario
  _Given BillLocation_ID and BillUser_ID registered under a different BPartner
  _When importing the record
  _Then processing errors are saved in I_ErrorMsg column
    Given metasfresh contains M_Products:
      | Identifier | Name                 | Value                      |
      | product_4  | Product_25_08_2022_4 | Product_Value_25_08_2022_4 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_4               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier          | Name                          | Value                           | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | billBpartner_4      | BillBPartner_25_08_2022_4     | billBPartner_Value_25_08_2022_4 | N            | Y              | ps_1                          | I               |
      | billBpartner_4_test | BillBPartnerTest_25_08_2022_4 | BillBPartner_Value_25_08_2022_4 | N            | Y              | ps_1                          | I               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier            | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | billBPLocation_4      | 1239977812123 | billBpartner_4           | true         | true         |
      | billBPLocation_4_test | 1239975912123 | billBpartner_4_test      | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                      | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | billBPUser_4          | BillBPartnerContact_4     | billBpartner_4               | billBPLocation_4                      |
      | billBPUser_4_test     | BillBPartnerContactTest_4 | billBpartner_4_test          | billBPLocation_4_test                 |
    And store DataImport string requestBody in context
      | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | IsSOTrx | OPT.DocBaseType |
      | billBpartner_4              | billBPLocation_4_test       | billBPUser_4_test           | product_4               | 2          | true    | ARI             |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '400' status code

    Then I_Invoice_Candidate is found: searching by product value
      | M_Product_Value            | I_Invoice_Candidate_ID.Identifier | I_IsImported | OPT.I_ErrorMsg                                                                                                                |
      | Product_Value_25_08_2022_4 | iInvoiceCandidate_4               | E            | ERR = Provided Bill_Location_ID not found for Bill_BPartner_ID!, ERR = Provided Bill_User_ID not found for Bill_BPartner_ID!, |


  @from:cucumber
  @Id:S0178_50
  Scenario: Import sales I_Invoice_Candidate from csv and validate that qtyToInvoice is properly set on C_Invoice_Candidate
  _Given C_BPartner.InvoiceRule = AfterDelivery and QtyDelivered = 2
  _When importing the record
  _Then validate that created C_Invoice_Candidate is invoicable ( QtyToInvoice = 2)
  _And the invoice can be generated successfully

    Given metasfresh contains M_Products:
      | Identifier | Name               | Value                    |
      | product_5  | Product_31_08_2022 | Product_Value_31_08_2022 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
      | pp_1       | plv_1                             | product_5               | 10.0     | PCE               | Normal                        |
    And metasfresh contains C_BPartners without locations:
      | Identifier     | Name                    | Value                         | OPT.IsVendor | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.InvoiceRule |
      | billBpartner_5 | BillBPartner_31_08_2022 | BillBPartner_Value_31_08_2022 | N            | Y              | ps_1                          | D               |
    And metasfresh contains C_BPartner_Locations:
      | Identifier       | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | billBPLocation_5 | 1239977890123 | billBpartner_5           | true         | true         |
    And metasfresh contains AD_Users:
      | AD_User_ID.Identifier | Name                  | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | billBPUser_5          | BillBPartnerContact_5 | billBpartner_5               | billBPLocation_5                      |
    And store DataImport string requestBody in context
      | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | OPT.Bill_User_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | IsSOTrx | OPT.QtyDelivered | OPT.DateOrdered |
      | billBpartner_5              | billBPLocation_5            | billBPUser_5                | product_5               | 4          | true    | 2                | 25.08.2022      |

    When the metasfresh REST-API endpoint path 'api/v2/import/text?dataImportConfig=InvoiceCandidate&runSynchronous=true' receives a 'POST' request with the payload from context and responds with '200' status code

    Then load C_DocType:
      | C_DocType_ID.Identifier | OPT.DocBaseType | OPT.DocSubType | OPT.IsDefault |
      | docType                 | ARI             | null           | true          |
    And load C_UOM for product:
      | C_UOM_ID.Identifier | M_Product_ID.Identifier |
      | UOM_5               | product_5               |
    And I_Invoice_Candidate is found: searching by product value
      | M_Product_Value          | I_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | Bill_User_ID.Identifier | M_Product_ID.Identifier | QtyOrdered | AD_Org_ID.Identifier | OPT.QtyDelivered | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.C_UOM_ID.Identifier | OPT.InvoiceRule | I_IsImported |
      | Product_Value_31_08_2022 | iInvoiceCandidate_5               | billBpartner_5              | billBPLocation_5            | billBPUser_5            | product_5               | 4          | importFormatOrg      | 2                | Y       | docType                     | UOM_5                   | D               | Y            |
    And validate invoice candidates by record reference:
      | TableName           | I_Invoice_Candidate_ID.Identifier | C_Invoice_Candidate_ID.Identifier | Bill_BPartner_ID.Identifier | Bill_Location_ID.Identifier | AD_Org_ID.Identifier | OPT.Bill_User_ID.Identifier | OPT.M_Product_ID.Identifier | OPT.QtyOrdered | OPT.QtyDelivered | IsSOTrx | OPT.C_DocType_ID.Identifier | OPT.C_UOM_ID.Identifier | InvoiceRule |
      | I_Invoice_Candidate | iInvoiceCandidate_5               | invoiceCandidate_5                | billBpartner_5              | billBPLocation_5            | importFormatOrg      | billBPUser_5                | product_5                   | 4              | 2                | true    | docType                     | UOM_5                   | D           |
    And after not more than 60s C_Invoice_Candidate matches:
      | C_Invoice_Candidate_ID.Identifier | OPT.QtyToInvoice |
      | invoiceCandidate_5                | 2                |

    When process invoice candidates
      | C_Invoice_Candidate_ID.Identifier |
      | invoiceCandidate_5                |

    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID.Identifier | C_Invoice_ID.Identifier |
      | invoiceCandidate_5                | invoice_5               |