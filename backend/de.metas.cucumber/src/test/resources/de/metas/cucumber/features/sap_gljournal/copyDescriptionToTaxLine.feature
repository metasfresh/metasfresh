@from:cucumber
Feature: SAP GL Journal - copy description from base line to generated tax line

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2023-04-16T13:30:13+01:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION

    And load C_AcctSchema:
      | C_AcctSchema_ID.Identifier | OPT.Name              |
      | acctSchema_desc            | metas fresh UN/34 CHF |

    And metasfresh contains C_Tax
      | Identifier    | C_TaxCategory_ID.InternalName | Name               | ValidFrom  | Rate | C_Country_ID.CountryCode | To_Country_ID.CountryCode |
      | tax_desc_copy | Normal                        | TaxName_desc_copy  | 2023-02-01 | 7.7  | DE                       | DE                        |

  @from:cucumber
  @Id:S0320_300
  Scenario: Generated tax line inherits description from base line
    - Base line has a description
    - After generating tax lines, the tax line should have the same description as its base line

    Given metasfresh contains SAP_GLJournal:
      | Identifier              | Description             | DocBaseType | PostingType | DocStatus | DateDoc    | DateAcct   | C_AcctSchema_ID.Identifier | C_ConversionType_ID | GL_Category_ID |
      | sap_gljournal_S0320_300 | sap_gljournal_S0320_300 | GLJ         | A           | DR        | 2023-02-10 | 2023-02-10 | acctSchema_desc            | 114                 | 1000001        |

    And metasfresh contains SAP_GLJournalLines:
      | Identifier                     | Line | SAP_GLJournal_ID.Identifier | PostingSign | C_ValidCombination_ID | Amount | OPT.C_Tax_ID.Identifier | OPT.IsTaxIncluded | OPT.Description    |
      | sap_gljournalLine_S0320_300_10 | 10   | sap_gljournal_S0320_300     | C           | 1000343               | 100    | tax_desc_copy           | false             | Office supplies Q1 |

    When regenerate tax lines:
      | SAP_GLJournal_ID.Identifier |
      | sap_gljournal_S0320_300     |

    Then validate generated lines:
      | Identifier                     | SAP_GLJournal_ID.Identifier | PostingSign | Amount | OPT.Parent_ID                  | OPT.Description    |
      | sap_gljournalLine_S0320_300_20 | sap_gljournal_S0320_300     | C           | 7.7    | sap_gljournalLine_S0320_300_10 | Office supplies Q1 |

  @from:cucumber
  @Id:S0320_400
  Scenario: Generated tax line has no description when base line has no description
    - Base line has no description
    - After generating tax lines, the tax line should also have no description

    Given metasfresh contains SAP_GLJournal:
      | Identifier              | Description             | DocBaseType | PostingType | DocStatus | DateDoc    | DateAcct   | C_AcctSchema_ID.Identifier | C_ConversionType_ID | GL_Category_ID |
      | sap_gljournal_S0320_400 | sap_gljournal_S0320_400 | GLJ         | A           | DR        | 2023-02-10 | 2023-02-10 | acctSchema_desc            | 114                 | 1000001        |

    And metasfresh contains SAP_GLJournalLines:
      | Identifier                     | Line | SAP_GLJournal_ID.Identifier | PostingSign | C_ValidCombination_ID | Amount | OPT.C_Tax_ID.Identifier | OPT.IsTaxIncluded |
      | sap_gljournalLine_S0320_400_10 | 10   | sap_gljournal_S0320_400     | C           | 1000343               | 100    | tax_desc_copy           | false             |

    When regenerate tax lines:
      | SAP_GLJournal_ID.Identifier |
      | sap_gljournal_S0320_400     |

    Then validate generated lines:
      | Identifier                     | SAP_GLJournal_ID.Identifier | PostingSign | Amount | OPT.Parent_ID                  | OPT.Description |
      | sap_gljournalLine_S0320_400_20 | sap_gljournal_S0320_400     | C           | 7.7    | sap_gljournalLine_S0320_400_10 |                 |
