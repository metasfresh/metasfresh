@from:cucumber
@allure.label.epic:E0280_Document_and_Email_Management
@allure.label.feature:F36020_Outbound_Documents
@ghActions:run_on_executor1
@topic:docOutboundFollowsDocument
Feature: Outbound document log follows its document through reactivation and re-completion

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2022-02-03T09:15:00+01:00[Europe/Berlin]
    And set sys config boolean value false for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And set sys config boolean value true for sys config de.metas.report.jasper.IsMockReportService

    And metasfresh contains C_Doc_Outbound_Config:
      | AD_Table.Name | DocBaseType |
      | C_Order       | SOO         |

    And metasfresh contains M_Products:
      | Identifier   | Name         |
      | dofd_product | dofd_product |
    And metasfresh contains M_PricingSystems
      | Identifier | Name      | Value     |
      | dofd_ps    | dofd_ps_1 | dofd_ps_1 |
    And metasfresh contains M_PriceLists
      | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name       | SOTrx | IsTaxIncluded | PricePrecision |
      | dofd_pl    | dofd_ps                       | DE                        | EUR                 | dofd_pl_so | true  | false         | 2              |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID.Identifier | Name        | ValidFrom  |
      | dofd_plv   | dofd_pl                   | dofd_plv_so | 2022-01-10 |
    And metasfresh contains M_ProductPrices
      | Identifier | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_TaxCategory_ID.InternalName | C_UOM_ID.X12DE355 |
      | dofd_pp    | dofd_plv                          | dofd_product            | 10.0     | Normal                        | PCE               |
    # customers A and B are in different business partner groups (Standard 1000000 and Kunde 2000228)
    And metasfresh contains C_BPartners:
      | Identifier | Name      | OPT.C_BP_Group_ID | M_PricingSystem_ID.Identifier | OPT.IsCustomer |
      | dofd_bp_a  | dofd_bp_a | 1000000           | dofd_ps                       | Y              |
      | dofd_bp_b  | dofd_bp_b | 2000228           | dofd_ps                       | Y              |
    And metasfresh contains C_BPartner_Locations:
      | Identifier | GLN           | C_BPartner_ID.Identifier | OPT.IsShipTo | OPT.IsBillTo |
      | dofd_loc_a | 3333323456781 | dofd_bp_a                | true         | true         |
      | dofd_loc_b | 3333323456782 | dofd_bp_b                | true         | true         |
    And metasfresh contains AD_Users:
      | Identifier  | Name        | OPT.EMail         | OPT.C_BPartner_ID.Identifier | OPT.C_BPartner_Location_ID.Identifier |
      | dofd_user_a | dofd_user_a | dofd_a@email.test | dofd_bp_a                    | dofd_loc_a                            |
      | dofd_user_b | dofd_user_b | dofd_b@email.test | dofd_bp_b                    | dofd_loc_b                            |

  Scenario: Reference case replayed - outbound log follows the order through reactivation, customer change and re-completion
    Given metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID.Identifier | DateOrdered | OPT.C_BPartner_Location_ID.Identifier | OPT.AD_User_ID.Identifier | OPT.DeliveryRule | OPT.DeliveryViaRule |
      | dofd_order | true    | dofd_bp_a                | 2022-02-02  | dofd_loc_a                            | dofd_user_a               | F                | S                   |
    And metasfresh contains C_OrderLines:
      | Identifier     | C_Order_ID.Identifier | M_Product_ID.Identifier | QtyEntered |
      | dofd_orderLine | dofd_order            | dofd_product            | 1          |

    # step 1: completed order -> one outbound document, customer A, CO, one PDF line
    When the order identified by dofd_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocBaseType | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_a                | 1000000       | SOO         | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |

    # step 2: reactivate, change customer to B, save without completing -> same log, IP, still customer A, not found by a CO selection
    When the order identified by dofd_order is reactivated
    And update order
      | Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | AD_User_ID.Identifier |
      | dofd_order | dofd_bp_b                | dofd_loc_b                        | dofd_user_b           |
    Then after not more than 30s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_a                | 1000000       | IP        |
    And no C_Doc_Outbound_Log with DocStatus CO exists for record "dofd_order"

    # step 3: mass print while IP -> customer A and IP unchanged, new history line with IP
    When Massendruck PDF is run for C_Doc_Outbound_Log "dofd_log"
    Then after not more than 30s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_a                | 1000000       | IP        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_2                           | dofd_log                         | dofd_order           | C_Order       | SOO         | IP        | dofd_archive_1           | dofd_bp_a                           |

    # step 4: complete again -> still one log, customer B, CO, new history line
    # the re-completion happens the next morning, as in the reference case; with the clock still frozen at the first completion,
    # both PDFs would carry the same Created and "the newest PDF" (ordered by Created) would be ambiguous
    And metasfresh has date and time 2022-02-04T08:39:00+01:00[Europe/Berlin]
    When the order identified by dofd_order is completed
    Then after not more than 60s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_b                | 2000228       | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_2                           | dofd_log                         | dofd_order           | C_Order       | SOO         | IP        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_3                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_2           | dofd_bp_b                           |

    # step 5: mass print again -> prints B's new PDF, header stays B / CO; history CO, IP, CO, CO
    When Massendruck PDF is run for C_Doc_Outbound_Log "dofd_log"
    Then after not more than 30s validate C_Doc_Outbound_Log:
      | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | C_BPartner_ID.Identifier | C_BP_Group_ID | DocStatus |
      | dofd_log                         | dofd_order           | C_Order       | dofd_bp_b                | 2000228       | CO        |
    And validate C_Doc_Outbound_Log_Line:
      | C_Doc_Outbound_Log_Line_ID.Identifier | C_Doc_Outbound_Log_ID.Identifier | Record_ID.Identifier | AD_Table.Name | DocBaseType | DocStatus | AD_Archive_ID.Identifier | AD_Archive.C_BPartner_ID.Identifier |
      | dofd_line_1                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_2                           | dofd_log                         | dofd_order           | C_Order       | SOO         | IP        | dofd_archive_1           | dofd_bp_a                           |
      | dofd_line_3                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_2           | dofd_bp_b                           |
      | dofd_line_4                           | dofd_log                         | dofd_order           | C_Order       | SOO         | CO        | dofd_archive_2           | dofd_bp_b                           |
