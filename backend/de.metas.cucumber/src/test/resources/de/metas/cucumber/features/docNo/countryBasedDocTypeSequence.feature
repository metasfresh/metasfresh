@Id:S0279
@from:cucumber
@Topic:countryBasedDocTypeSequence
Feature: countryBasedDocTypeSequence

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-06-23T12:00:00+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load C_BPartner:
      | C_BPartner_ID.Identifier | OPT.C_BPartner_ID |
      | endCustomer_1            | 2156425           |

    And load C_BPartner_Location:
      | C_BPartner_Location_ID.Identifier | OPT.C_BPartner_Location_ID |
      | endCustomerLocation_1             | 2205175                    |

    And metasfresh contains AD_Sequence:
      | Identifier  | Name         | OPT.Prefix | OPT.CurrentNext |
      | 20230623DE  | 20230623DE   | DE         | 1000            |
      | 20230623RO  | 20230623RO   | RO         | 1000            |
      | 20230623No  | 20230623No   | No         | 1000            |

    And metasfresh contains C_DocType:
      | Identifier          | Name               | DocBaseType | GL_Category_ID.Name | DocNoSequence_ID.Identifier |
      | docType_20230623    | docType_20230623   | ARI         | AR Invoice          | 20230623No                  |

    And metasfresh contains C_DocType_Sequence:
      | Identifier              | C_DocType_ID.Identifier | DocNoSequence_ID.Identifier | OPT.C_Country_ID |
      | docTypeSeq_20230623_DE  | docType_20230623        | 20230623DE                  | 101              |
      | docTypeSeq_20230623_RO  | docType_20230623        | 20230623RO                  | 285              |

  @Id:S0279_100
  @from:cucumber
  Scenario:  countryBasedDocTypeSequence for Sales Invoice documentNo

    Given metasfresh contains C_Invoice:
      | Identifier     | C_BPartner_ID.Identifier | C_DocTypeTarget_ID.Name | DateInvoiced | C_ConversionType_ID.Name | IsSOTrx | C_Currency.ISO_Code | OPT.C_Tax_Departure_Country_ID |
      | inv_20230624_1 | endCustomer_1            | docType_20230623        | 2023-06-23   | Spot                     | true    | EUR                 | 101                            |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.DocumentNo | paymentTerm | processed | docStatus |
      | inv_20230624_1          | endCustomer_1            | endCustomerLocation_1             | DE1000         | 10 Tage 1 % | false     | DR        |

    And update C_Invoice:
      | Identifier     | OPT.C_Tax_Departure_Country_ID |
      | inv_20230624_1 | 285                            |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.DocumentNo | paymentTerm | processed | docStatus |
      | inv_20230624_1          | endCustomer_1            | endCustomerLocation_1             | RO1000         | 10 Tage 1 % | false     | DR        |

    And update C_Invoice:
      | Identifier     | OPT.C_Tax_Departure_Country_ID |
      | inv_20230624_1 | 214                            |

    Then validate created invoices
      | C_Invoice_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | OPT.DocumentNo | paymentTerm | processed | docStatus |
      | inv_20230624_1          | endCustomer_1            | endCustomerLocation_1             | No1000         | 10 Tage 1 % | false     | DR        |

