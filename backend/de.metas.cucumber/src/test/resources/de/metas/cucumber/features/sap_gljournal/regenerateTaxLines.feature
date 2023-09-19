@from:cucumber
@chems
Feature: Regenerate tax lines for SAP GL Journal

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_1               | metas fresh UN/34 CHF |

    And metasfresh contains C_Tax
      | Identifier | C_TaxCategory_ID.InternalName | Name           | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax        | Normal                        | TaxName_150223 | 2023-02-01 | 7.7  | DE                       | DE                        |

    And metasfresh contains M_SectionCode:
      | M_SectionCode_ID.Identifier | Value       |
      | testSection_S0150_100       | testSection |

  @from:cucumber
  #@Id:S0156_600 todo get id from daniela
  Scenario: Regenerate tax lines for SAP GL Journal ( tax amount included and IsTaxIncluded = Y )
  - Generate tax line  with the correct tax amount after deducing the base amount
  - Calculate base tax amount and update base tax line, reset IsTaxIncluded = N

    Given metasfresh contains SAP_GLJournal:
      | Identifier             | Description                 | OPT.DocBaseType | PostingType | DocStatus | DateDoc    | DateAcct   | C_AcctSchema_ID.Identifier |
      | sap_gljournal_19092023 | sap_gljournal_19092023_test | GLJ             | A           | DR        | 2023-02-10 | 2023-02-10 | acctSchema_1               |

    And metasfresh contains SAP_GLJournalLines:
      | Identifier                    | Line | SAP_GLJournal_ID.Identifier | PostingSign | C_ValidCombination_ID | Amount | AmtAcct | M_SectionCode_ID.Identifier | OPT.C_Tax_ID.Identifier | OPT.IsTaxIncluded | OPT.Parent_ID |
      | sap_gljournalLine_19092023_10 | 10   | sap_gljournal_19092023      | C           | 1000343               | 107.7  | 107.7   | testSection_S0150_100       | tax                     | true              |               |

    When regenerate tax lines:
      | SAP_GLJournal_ID.Identifier |
      | sap_gljournal_19092023      |

    Then validate generated lines:
      | Identifier                    | SAP_GLJournal_ID.Identifier | Amount | OPT.Parent_ID                 |
      | sap_gljournalLine_19092023_20 | sap_gljournal_19092023      | 7.7    | sap_gljournalLine_19092023_10 |

    And base tax line updated:
      | Identifier                    | SAP_GLJournal_ID.Identifier | Amount | OPT.IsTaxIncluded |
      | sap_gljournalLine_19092023_10 | sap_gljournal_19092023      | 100    | false             |


  @from:cucumber
  #@Id:S0156_700 todo get id from daniela
  Scenario: Regenerate tax lines for SAP GL Journal ( tax amount not included and IsTaxIncluded = N )
  - Generate tax line  with the correct tax amount
  - Calculate base tax amount and update base tax line, reset IsTaxIncluded = N

    Given metasfresh contains SAP_GLJournal:
      | Identifier             | Description                 | OPT.DocBaseType | PostingType | DocStatus | DateDoc    | DateAcct   | C_AcctSchema_ID.Identifier |
      | sap_gljournal_19092023 | sap_gljournal_19092023_test | GLJ             | A           | DR        | 2023-02-10 | 2023-02-10 | acctSchema_1               |

    And metasfresh contains SAP_GLJournalLines:
      | Identifier                    | Line | SAP_GLJournal_ID.Identifier | PostingSign | C_ValidCombination_ID | Amount | M_SectionCode_ID.Identifier | OPT.C_Tax_ID.Identifier | OPT.IsTaxIncluded | OPT.Parent_ID |
      | sap_gljournalLine_19092023_10 | 10   | sap_gljournal_19092023      | C           | 1000343               | 100    | testSection_S0150_100       | tax                     | false             |               |

    When regenerate tax lines:
      | SAP_GLJournal_ID.Identifier |
      | sap_gljournal_19092023      |

    Then validate generated lines:
      | Identifier                    | SAP_GLJournal_ID.Identifier | Amount | OPT.Parent_ID                 |
      | sap_gljournalLine_19092023_20 | sap_gljournal_19092023      | 7.7    | sap_gljournalLine_19092023_10 |

    And base tax line updated:
      | Identifier                    | SAP_GLJournal_ID.Identifier | Amount | OPT.IsTaxIncluded |
      | sap_gljournalLine_19092023_10 | sap_gljournal_19092023      | 100    | false             |
