@from:cucumber
@allure.label.epic:E0292_EDI
@allure.label.feature:F00350_EDI
@F00350
@ghActions:run_on_executor3
Feature: EDI INVOIC export via External System

  Background:
    Given infrastructure and metasfresh are running
    And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
    And metasfresh has date and time 2025-05-01T16:30:17+02:00[Europe/Berlin]
    And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
    And documents are accounted immediately

    And metasfresh contains M_PricingSystems
      | Identifier    |
      | pricingSystem |

    And metasfresh contains M_PriceLists
      | Identifier     | M_PricingSystem_ID | C_Country_ID | C_Currency_ID | SOTrx |
      | salesPriceList | pricingSystem      | DE           | EUR           | true  |
    And metasfresh contains M_PriceList_Versions
      | Identifier | M_PriceList_ID |
      | salesPLV   | salesPriceList |

    And metasfresh contains M_Products:
      | Identifier | GTIN        |
      | product    | productGTIN |

    And metasfresh contains M_ProductPrices
      | M_PriceList_Version_ID | M_Product_ID | PriceStd | C_UOM_ID |
      | salesPLV               | product      | 5.00     | PCE      |

    And metasfresh contains ExternalSystem_Config with ScriptedExportConversion
      | ExternalSystem_Config_ID | ExternalSystem_Config_ScriptedExportConversion_ID | AD_Process_OutboundData_ID.Value | TableName |
      | externalSystemConfig_1   | scriptedExportConversion_1                        | C_Invoice_EDI_Export_JSON        | C_Invoice |

    And metasfresh contains C_BPartners without locations:
      | Identifier | IsCustomer | IsVendor | M_PricingSystem_ID |
      | customer1  | Y          | N        | pricingSystem      |
    And the following c_bpartner is changed
      | Identifier | IsEdiInvoicRecipient | EdiInvoicRecipientGLN | EdiINVOICSendingMode | EdiINVOIC_ExternalSystem_Config_ID |
      | customer1  | true                 | 12345678              | E                    | externalSystemConfig_1             |
    And metasfresh contains C_BPartner_Locations:
      | Identifier          | C_BPartner_ID | IsShipToDefault | IsBillToDefault | GLN           |
      | bpartner_location_1 | customer1     | Y               | Y               | 1234567890123 |

    And RabbitMQ MF_TO_ExternalSystem queue is purged


  @from:cucumber
  @allure.label.epic:E0292_EDI
  @allure.label.feature:F00350_EDI
  @F00350
  Scenario: create an invoice and export INVOIC via external system

    And metasfresh contains C_Orders:
      | Identifier | IsSOTrx | C_BPartner_ID | DateOrdered | DatePromised | POReference   |
      | o_1        | true    | customer1     | 2025-04-17  | 2025-04-18Z  | testReference |
    And metasfresh contains C_OrderLines:
      | Identifier | C_Order_ID.Identifier | M_Product_ID | QtyEntered |
      | ol_1       | o_1                   | product      | 100        |

    And the order identified by o_1 is completed

    And after not more than 60s, M_ShipmentSchedules are found:
      | Identifier | C_OrderLine_ID.Identifier | IsToRecompute |
      | s_s_1      | ol_1                      | N             |

    And 'generate shipments' process is invoked individually for each M_ShipmentSchedule
      | M_ShipmentSchedule_ID.Identifier | QuantityType | IsCompleteShipments | IsShipToday |
      | s_s_1                            | D            | true                | false       |

    And after not more than 60s, M_InOut is found:
      | M_ShipmentSchedule_ID.Identifier | M_InOut_ID.Identifier |
      | s_s_1                            | s_1                   |

    And after not more than 60s, C_Invoice_Candidate are found:
      | C_Invoice_Candidate_ID.Identifier | C_OrderLine_ID.Identifier | QtyToInvoice | QtyDelivered |
      | ic1                               | ol_1                      | 100          | 100          |
    When process invoice candidates
      | C_Invoice_Candidate_ID |
      | ic1                    |
    Then after not more than 60s, C_Invoice are found:
      | C_Invoice_Candidate_ID | C_Invoice_ID  |
      | ic1                    | salesInvoice  |

    And invoice is enqueued for EDI export
      | C_Invoice_ID |
      | salesInvoice |

    Then after not more than 120s, C_Invoice records have the following export status
      | C_Invoice_ID | EDI_ExportStatus |
      | salesInvoice | S                |

    Then RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:
      | ExternalSystem_Config_ID.Identifier | ConfigIDOnly |
      | externalSystemConfig_1              | true         |

    And the external system sends an error response for the invoice
      | C_Invoice_ID | ErrorMessage                              |
      | salesInvoice | External system export failed: Test error |

    Then after not more than 120s, C_Invoice records have the following export status
      | C_Invoice_ID | EDI_ExportStatus |
      | salesInvoice | E                |

